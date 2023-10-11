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
   * MySQL에서 인기도가 높은 상위 300개의 게시물을 가져와 이를 Redis에 자장한다.
   *
   * @author : hwangdaesun
   */

  @Transactional
  public void execute(){
    List<GetIncompletePopularPostDTO> top300Posts = postRepository.findTop300ByOrderByPopularityDesc(PageRequest.of(0, POPULARITY_SIZE));
    redisTemplate.delete(RedisKey.POPULAR_POST_KEY.getKey());
    top300Posts.stream().forEach(getIncompletePopularPostDTO -> {
      redisTemplate.opsForZSet().add(RedisKey.POPULAR_POST_KEY.getKey(), getIncompletePopularPostDTO, getIncompletePopularPostDTO.getPopularity().doubleValue());
    });
  }

}
