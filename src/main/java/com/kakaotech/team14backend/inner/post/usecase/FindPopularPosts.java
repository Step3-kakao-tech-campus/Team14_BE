package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.LevelIndexes;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.inner.post.model.RandomIndexes;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
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
public class FindPopularPosts{

  private final RedisTemplate redisTemplate;
  private final PostRepository postRepository;

  public GetPopularPostListResponseDTO execute(GetPopularPostListRequestDTO getPopularPostListRequestDTO, int size) {

    List<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new ArrayList<>();
    LevelIndexes levelIndexes = getLevelIndexes(getPopularPostListRequestDTO, size);
    loopLevelIndexes(incompletePopularPostDTOS, levelIndexes);
    return PostMapper.from(incompletePopularPostDTOS, levelIndexes.levelIndexes());
  }

  private void loopLevelIndexes(List<GetIncompletePopularPostDTO> incompletePopularPostDTOS, LevelIndexes levelIndexes) {
    for (Map.Entry<Integer, RandomIndexes> entry : levelIndexes.levelIndexes().entrySet()) {
      Integer level = entry.getKey();
      List<Integer> indexes = entry.getValue().getIndexes();
      toGetPopularPostListResponseDTO(incompletePopularPostDTOS, level, indexes);
    }
  }

  private LevelIndexes getLevelIndexes(GetPopularPostListRequestDTO getPopularPostListRequestDTO, int limitSize) {
    PostRandomFetcher postRandomFetcher1 = new PostRandomFetcher(getPopularPostListRequestDTO.levelSize(), limitSize);
    LevelIndexes levelIndexes = postRandomFetcher1.getLevelIndexes();
    return levelIndexes;
  }

  private void toGetPopularPostListResponseDTO(List<GetIncompletePopularPostDTO> incompletePopularPostDTOS, Integer level, List<Integer> indexes) {
    for (Integer index : indexes) {
      Long postId = getPostId(index);
      Optional<Post> optionalPost = postRepository.findById(postId);
      if (optionalPost.isPresent()) {
        incompletePopularPostDTOS.add(new GetIncompletePopularPostDTO(getPost(optionalPost).getPostId(), getPost(optionalPost).getImage().getImageUri(), getPost(optionalPost).getHashtag(), getPost(optionalPost).getPostLikeCount().getLikeCount(), getPost(optionalPost).getPopularity(), level, getPost(optionalPost).getNickname()));
      }
    }
  }

  private Post getPost(Optional<Post> optionalPost) {
    return optionalPost.get();
  }

  private Long getPostId(Integer index) {
    return redisTemplate.opsForZSet().reverseRank(RedisKey.POPULAR_POST_KEY.getKey(), index);
  }


}
