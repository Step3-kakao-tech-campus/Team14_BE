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
public class FindPopularPostListUsecase {

  private final RedisTemplate redisTemplate;
  private final PostRepository postRepository;

  public GetPopularPostListResponseDTO execute(GetPopularPostListRequestDTO getPopularPostListRequestDTO, int size) {

    List<GetIncompletePopularPostDTO> incompletePopularPostDTOS = new ArrayList<>();
    GetPopularPostListResponseDTO getPopularPostListResponseDTO;

    LevelIndexes levelIndexes = getLevelIndexes(getPopularPostListRequestDTO, size);

    for (Map.Entry<Integer, RandomIndexes> entry : levelIndexes.levelIndexes().entrySet()) {
      Integer level = entry.getKey();
      List<Integer> indexes = entry.getValue().getIndexes();
      for (Integer index : indexes) {
        Long postId = getPostId(index);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
          Post post = optionalPost.get();
          incompletePopularPostDTOS.add(new GetIncompletePopularPostDTO(post.getPostId(), post.getImage().getImageUri(), post.getHashtag(), post.getPostLikeCount().getLikeCount(), post.getPopularity(), level, post.getNickname()));
        }
      }
    }
    getPopularPostListResponseDTO = PostMapper.from(incompletePopularPostDTOS, levelIndexes.levelIndexes());

    return getPopularPostListResponseDTO;
  }

  private Long getPostId(Integer index) {
    return redisTemplate.opsForZSet().reverseRank(RedisKey.POPULAR_POST_KEY.getKey(), index);
  }

  private LevelIndexes getLevelIndexes(GetPopularPostListRequestDTO getPopularPostListRequestDTO, int limitSize) {
    PostRandomFetcher postRandomFetcher1 = new PostRandomFetcher(getPopularPostListRequestDTO.levelSize(), limitSize);
    LevelIndexes levelIndexes = postRandomFetcher1.getLevelIndexes();
    return levelIndexes;
  }

}
