package com.kakaotech.team14backend.inner.post.usecase;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdatePostViewCountUsecaseTest {

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private UpdatePostViewCountUsecase updatePostViewCountUsecase;

  @Autowired
  private CacheManager cacheManager;

  @BeforeEach
  @DisplayName("게시글 1을 회원 2와 회원 3이 방문한 경우")
  void setUp() {
    redisTemplate.delete("1");
    redisTemplate.opsForSet().add("1",2L);
    redisTemplate.opsForSet().add("1",3L);
  }

  @Test
  @DisplayName("updatePostViewCountUsecase.execute(getPostDTO) 에서 Set 자료구조를 활용한 로직이 잘 동작하는 지 확인")
  void execute_updateUsingSet1() {
    Cache cache = cacheManager.getCache("viewCnt");
    cache.clear();

    GetPostDTO getPostDTO = new GetPostDTO(1L,1L);
    updatePostViewCountUsecase.execute(getPostDTO);
    Long size = redisTemplate.opsForSet().size("1");
    Assertions.assertThat(size).isEqualTo(3);
  }

  @Test
  @DisplayName("updatePostViewCountUsecase.execute(getPostDTO) 에서 Set 자료구조를 활용한 로직이 잘 동작하는 지 확인")
  void execute_updateUsingSet2() {
    GetPostDTO getPostDTO = new GetPostDTO(2L,1L);
    updatePostViewCountUsecase.execute(getPostDTO);
    Long size = redisTemplate.opsForSet().size("1");
    Assertions.assertThat(size).isEqualTo(2);
  }

  @Test
  @DisplayName("캐시가 잘 저장되어 있는 지 확인")
  void execute_saveCache() {
    GetPostDTO getPostDTO = new GetPostDTO(1L,1L);
    Long result = updatePostViewCountUsecase.execute(getPostDTO);
    assertEquals(result,3);
  }


}
