package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.config.CacheProperties;
import com.kakaotech.team14backend.config.RedisConfig;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("local")
@Import({RedisConfig.class, CacheProperties.class})
@DataJpaTest
class FindPostUsecaseTest {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private RedisTemplate<String, Long> redisTemplate;

  @BeforeEach
  @DisplayName("게시글 1을 회원 2와 회원 3이 방문한 경우")
  void setUp() {

    redisTemplate.delete("1");
    redisTemplate.opsForSet().add("1",2L);
    redisTemplate.opsForSet().add("1",3L);
  }

  @Test
  @DisplayName("게시글 1을 회원 2가 다시 방문한 경우 이는 기록이 되지 않는다.")
  void execute_NotCount() {
    redisTemplate.opsForSet().add("1",2L);
    Long size = redisTemplate.opsForSet().size("1");
    Assertions.assertThat(size).isEqualTo(2);
  }

  @Test
  @DisplayName("게시글 1을 회원 4가 다시 방문한 경우 이는 기록이 된다.")
  void execute_findPost(){
    redisTemplate.opsForSet().add("1",4L);
    Long size = redisTemplate.opsForSet().size("1");
    Assertions.assertThat(size).isEqualTo(3);
  }


}
