package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)
@Sql("classpath:db/testSetup.sql")
public class FindPopularPostListUnder30Test {

  @Autowired
  private FindPopularPostListUsecase findPopularPostListUsecase;

  @Autowired
  private PostRandomFetcher postRandomFetcher;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private SaveTemporaryPopularPostListUsecase saveTemporaryPopularPostListUsecase;

  @BeforeEach
  void setUp() {
    redisTemplate.delete(RedisKey.POPULAR_POST_KEY.getKey());
    saveTemporaryPopularPostListUsecase.execute();
  }

  @Test
  void execute() {

    Map<Integer, Integer> levelCounts = new HashMap<>();

    levelCounts.put(3, 4);
    levelCounts.put(2, 3);
    levelCounts.put(1, 3);

    GetPopularPostListResponseDTO getPopularPostListResponseDTO = findPopularPostListUsecase.execute(
        levelCounts, 20);

    System.out.println(getPopularPostListResponseDTO);

  }


}
