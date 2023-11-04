package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.post.model.PostLevel;
import com.kakaotech.team14backend.outer.post.dto.PostLevelPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPopularPostPointUsecase {

  private final RedisTemplate redisTemplate;

  public PostLevelPoint execute(Long postId) {
    Long rank = redisTemplate.opsForZSet().reverseRank(RedisKey.POPULAR_POST_KEY.getKey(), postId);
    Integer postLevel = PostLevel.to(rank.intValue());
    Long postPoint = UsePointDecider.decidePoint(postLevel);
    return new PostLevelPoint(postLevel, postPoint);
  }


}
