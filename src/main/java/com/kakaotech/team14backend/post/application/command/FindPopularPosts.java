package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.LevelIndexes;
import com.kakaotech.team14backend.post.domain.PostRandomFetcher;
import com.kakaotech.team14backend.post.domain.RandomIndexes;
import com.kakaotech.team14backend.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
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

  private LevelIndexes getLevelIndexes(GetPopularPostListRequestDTO getPopularPostListRequestDTO, int limitSize) {
    PostRandomFetcher postRandomFetcher = new PostRandomFetcher(getPopularPostListRequestDTO.levelSize(), limitSize);
    LevelIndexes levelIndexes = postRandomFetcher.getLevelIndexes();
    return levelIndexes;
  }

  private void loopLevelIndexes(List<GetIncompletePopularPostDTO> incompletePopularPostDTOS, LevelIndexes levelIndexes) {
    for (Map.Entry<Integer, RandomIndexes> entry : levelIndexes.levelIndexes().entrySet()) {
      Integer level = entry.getKey();
      List<Integer> indexes = entry.getValue().getIndexes();
      addIncompletePopularPostDTOs(incompletePopularPostDTOS, level, indexes);
    }
  }

  private void addIncompletePopularPostDTOs(List<GetIncompletePopularPostDTO> incompletePopularPostDTOs, Integer level, List<Integer> indexes) {
    for (Integer index : indexes) {
      Long postId = getPostId(index);
      postRepository.findById(postId).ifPresent(post -> {incompletePopularPostDTOs.add(new GetIncompletePopularPostDTO(
          post.getPostId(),
          post.getImage().getImageUri(),
          post.getHashtag(),
          post.getPostLikeCount().getLikeCount(),
          post.getPopularity(),
          level,
          post.getNickname()));
      });
    }
  }

  private Long getPostId(Integer index) {
    return redisTemplate.opsForZSet().reverseRank(RedisKey.POPULAR_POST_KEY.getKey(), index);
  }

}
