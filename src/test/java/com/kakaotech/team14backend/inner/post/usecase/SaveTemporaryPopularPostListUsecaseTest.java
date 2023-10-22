package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest

@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)

@Sql(value = "classpath:db/testSetup.sql")
class SaveTemporaryPopularPostListUsecaseTest {

  @Autowired
  private SaveTemporaryPopularPostListUsecase saveTemporaryPopularPostListUsecase;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @BeforeEach
  void init_start(){
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null && !keys.isEmpty()) {
      redisTemplate.delete(keys);
    }
  }

  @AfterEach
  void init_end(){
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null && !keys.isEmpty()) {
      redisTemplate.delete(keys);
    }
  }

  @Test
  void execute() {
    List<Post> posts = postRepository.findAll();
    System.out.println(posts.size());
    saveTemporaryPopularPostListUsecase.execute();
    Long size = redisTemplate.opsForZSet().size(RedisKey.POPULAR_POST_KEY.getKey());
    org.assertj.core.api.Assertions.assertThat(size).isEqualTo(300);
  }

}
