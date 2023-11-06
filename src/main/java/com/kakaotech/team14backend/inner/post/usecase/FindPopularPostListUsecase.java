package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindPopularPostListUsecase {

  private final RedisTemplate redisTemplate;
  private final PostRepository postRepository;
  private final PostRandomFetcher postRandomFetcher;
  private static final int MINIMUM_SIZE = 30;
  public GetPopularPostListResponseDTO execute(Map<Integer, Integer> levelCounts, int size) {

    List<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new ArrayList<>();
    GetPopularPostListResponseDTO getPopularPostListResponseDTO;

    Map<Integer, List<Integer>> levelIndexes =  getLevelIndexes(levelCounts, size);

    for (Map.Entry<Integer, List<Integer>> entry : levelIndexes.entrySet()) {
      Integer level = entry.getKey();
      List<Integer> indexes = entry.getValue();
      for (Integer index : indexes) {
        Long postId = redisTemplate.opsForZSet().reverseRank(RedisKey.POPULAR_POST_KEY.getKey(), index);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
          Post post = optionalPost.get();
          incompletePopularPostDTOS.add(new GetIncompletePopularPostDTO(post.getPostId(), post.getImage().getImageUri(), post.getHashtag(), post.getPostLikeCount().getLikeCount(), post.getPopularity(),level, post.getNickname()));
        }
      }
    }
    getPopularPostListResponseDTO = PostMapper.from(incompletePopularPostDTOS, levelIndexes);

    return getPopularPostListResponseDTO;
  }

  private Map<Integer, List<Integer>> getLevelIndexes(Map<Integer, Integer> levelCounts, int size) {
    Map<Integer, List<Integer>> levelIndexes;
    if(size < MINIMUM_SIZE){
      levelIndexes = postRandomFetcher.fetchRandomIndexesUnder30ForAllLevels(levelCounts, size);

    }else{
      levelIndexes = postRandomFetcher.fetchRandomIndexesForAllLevels(levelCounts);

    }
    return levelIndexes;
  }

}
