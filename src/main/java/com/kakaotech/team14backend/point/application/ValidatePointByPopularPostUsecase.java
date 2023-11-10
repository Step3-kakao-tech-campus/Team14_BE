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
public class ValidatePointByPopularPostUsecase {

  private final RedisTemplate redisTemplate;

  public void execute(UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO) {
    PostLevel postLevel = PostLevel.from(usePointByPopularPostRequestDTO.postLevel());
    Set<Integer> setPostId= redisTemplate.opsForZSet().reverseRange(RedisKey.POPULAR_POST_KEY.getKey(), postLevel.start(), postLevel.end());
    List<Integer> postIds = setPostId.stream().toList();
    postIds.stream().filter(postId -> castToLong(postId).equals(usePointByPopularPostRequestDTO.postId())).findFirst().orElseThrow(() -> new PostNotFoundException());
  }
  private Long castToLong(Integer have){
    return have.longValue();
  }

}
