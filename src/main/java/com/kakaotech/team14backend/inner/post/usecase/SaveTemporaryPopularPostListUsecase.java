package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveTemporaryPopularPostListUsecase {

  private final PostRepository postRepository;
  private final RedisTemplate redisTemplate;
  private final int POPULARITY_SIZE = 300;

  /**
   *  MySQL에서 상위 300개 포스트를 찾는다.
   *  이를 Redis에서 관리한다.
   */

  @Transactional
  public void execute(){
    List<GetIncompletePopularPostDTO> top300Posts = postRepository.findTop300ByOrderByPopularityDesc(PageRequest.of(0, POPULARITY_SIZE));
    top300Posts.stream().forEach(getIncompletePopularPostDTO -> {
      redisTemplate.opsForZSet().add(RedisKey.POPULAR_POST.getKey(), getIncompletePopularPostDTO, getIncompletePopularPostDTO.getPopularity().doubleValue());
    });

  }

}
