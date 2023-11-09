package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.outer.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.post.domain.PostLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPopularPostPoint {

  private final RedisTemplate redisTemplate;

  public PostLevelPoint execute(Long postId) {
    Long rank = getRank(postId);
    Integer postLevel = getPostLevel(rank);
    Long postPoint = getPostPoint(postLevel);
    return new PostLevelPoint(postLevel, postPoint);
  }

  private Long getRank(Long postId) {
    return redisTemplate.opsForZSet().reverseRank(RedisKey.POPULAR_POST_KEY.getKey(), postId);
  }

  private static Integer getPostLevel(Long rank) {
    return PostLevel.to(rank.intValue());
  }

  private static Long getPostPoint(Integer postLevel) {
    return UsePointDecider.decidePoint(postLevel);
  }

}
