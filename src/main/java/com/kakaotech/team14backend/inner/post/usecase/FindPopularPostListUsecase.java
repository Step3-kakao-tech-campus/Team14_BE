package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.inner.post.model.RandomIndexes;
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
  public GetPopularPostListResponseDTO execute(Map<Integer, Integer> levelCounts, int size) {

    List<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new ArrayList<>();
    GetPopularPostListResponseDTO getPopularPostListResponseDTO;

    Map<Integer, RandomIndexes> levelIndexes =  getLevelIndexes(levelCounts, size);

    for (Map.Entry<Integer, RandomIndexes> entry : levelIndexes.entrySet()) {
      Integer level = entry.getKey();
      List<Integer> indexes = entry.getValue().getIndexes();
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

  private Map<Integer, RandomIndexes> getLevelIndexes(Map<Integer, Integer> levelCounts, int limitSize) {
    Map<Integer, RandomIndexes> levelIndexes = postRandomFetcher.fetchRandomIndexesForAllLevels(levelCounts, limitSize);
    return levelIndexes;
  }

}
