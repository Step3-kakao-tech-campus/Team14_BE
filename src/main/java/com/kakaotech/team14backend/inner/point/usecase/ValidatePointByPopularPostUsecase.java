package com.kakaotech.team14backend.inner.point.usecase;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.exception.PostNotFoundException;
import com.kakaotech.team14backend.inner.post.model.PostLevel;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
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
    postIds.stream().filter(postId -> castToLong(postId).equals(usePointByPopularPostRequestDTO.postId())).findFirst().orElseThrow(() -> new PostNotFoundException(MessageCode.NOT_REGISTER_POPULARPOST));
  }
  private Long castToLong(Integer have){
    return have.longValue();
  }

}
