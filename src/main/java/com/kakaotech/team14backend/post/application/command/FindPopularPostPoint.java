package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.point.domain.LevelToPointMapper;
import com.kakaotech.team14backend.post.domain.PostLevel;
import com.kakaotech.team14backend.post.dto.PostLevelPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindPopularPostPoint {

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
    return LevelToPointMapper.getPoint(postLevel);
  }

}
