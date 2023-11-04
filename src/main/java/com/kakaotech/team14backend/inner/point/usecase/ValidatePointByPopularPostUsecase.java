package com.kakaotech.team14backend.inner.point.usecase;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.exception.Exception400;
import com.kakaotech.team14backend.exception.PostNotFoundException;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLevel;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    Long want = have.longValue();
    return want;
  }

}
