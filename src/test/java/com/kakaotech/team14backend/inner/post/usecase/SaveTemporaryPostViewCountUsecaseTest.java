package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SaveTemporaryPostViewCountUsecaseTest {

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private SaveTemporaryPostViewCountUsecase saveTemporaryPostViewCountUsecase;

  @BeforeEach
  @DisplayName("게시글 1을 회원 2와 회원 3이 방문한 경우")
  void setUp() {
    redisTemplate.delete(RedisKey.VIEW_COUNT_PREFIX + "1");
    redisTemplate.opsForSet().add(RedisKey.VIEW_COUNT_PREFIX + "1",2L);
    redisTemplate.opsForSet().add(RedisKey.VIEW_COUNT_PREFIX + "1",3L);
  }

  @Test
  @DisplayName("게시물당 조회수 count 기능 검증")
  void execute_updateUsingSet1() {
    GetPostDTO getPostDTO = new GetPostDTO(1L,1L);
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);
    Long size = redisTemplate.opsForSet().size(RedisKey.VIEW_COUNT_PREFIX+ "1");
    Assertions.assertThat(size).isEqualTo(3);
  }

  @Test
  @DisplayName("게시물당 조회수 count 기능 검즘 - 10분동안 동일 게시물 동일 유저는 조회수는 증가되지 않는다.")
  void execute_updateUsingSet2() {
    GetPostDTO getPostDTO = new GetPostDTO(1L,2L);
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);
    Long size = redisTemplate.opsForSet().size(RedisKey.VIEW_COUNT_PREFIX + "1");
    Assertions.assertThat(size).isEqualTo(2);
  }



}
