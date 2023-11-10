package com.kakaotech.team14backend.point.application;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.post.domain.PostLevel;
import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidatePoint {

  private final RedisTemplate redisTemplate;

  public void execute(UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    List<Integer> postIds = ((Set<Integer>) getPostIds(usePointByPopularPostRequestDTO)).stream().toList();
    postIds.stream().filter(postId -> castToLong(postId).equals(usePointByPopularPostRequestDTO.postId())).findFirst().orElseThrow(() -> new PostNotFoundException());
  }

  private Set getPostIds(UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    return redisTemplate.opsForZSet().reverseRange(getKey(), getStart(getPostLevel(usePointByPopularPostRequestDTO)), getEnd(getPostLevel(usePointByPopularPostRequestDTO)));
  }

  private int getEnd(PostLevel postLevel) {
    return postLevel.end();
  }

  private int getStart(PostLevel postLevel) {
    return postLevel.start();
  }

  private String getKey() {
    return RedisKey.POPULAR_POST_KEY.getKey();
  }

  private PostLevel getPostLevel(UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    return PostLevel.from(usePointByPopularPostRequestDTO.postLevel());
  }

  private Long castToLong(Integer have){
    return have.longValue();
  }

}
