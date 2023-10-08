package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class SaveTemporaryPostViewCountUsecase {

  private final RedisTemplate<String,Object> redisTemplate;

  /**
   *
   * Set 자료구조에 key는 postId, value는 memberId를 저장
   * 캐시에 key는 postId value에 해당 postId의 조회수 저장
   */

  @Transactional
  @CachePut(value = "viewCnt", key = "#getPostDTO.postId().toString()")
  public Long execute(GetPostDTO getPostDTO) {
    // 데이터를 Redis Set에 추가하는 코드
    redisTemplate.opsForSet().add(String.valueOf(getPostDTO.postId()), getPostDTO.memberId());

    // Redis Set 자료구조에서 해당 key 즉 postId에 있는 value들의 갯수 반환
    long setSize = redisTemplate.opsForSet().size(String.valueOf(getPostDTO.postId()));

    return setSize;
  }

}
