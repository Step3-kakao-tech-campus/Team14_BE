package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostRandomFetcher;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest

@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)

@Sql("classpath:db/testSetup.sql.sql")
class FindPopularPostListUsecaseTest {

  @Autowired
  private FindPopularPostListUsecase findPopularPostListUsecase;

  @Autowired
  private PostRandomFetcher postRandomFetcher;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private PostRepository postRepository;


  /**
   * 게시물 자체가 Redis에 저장되어 있어야함.
   */
  @BeforeEach
  void setUp() {
    redisTemplate.delete(RedisKey.POPULAR_POST_KEY.getKey());
    List<Post> posts = postRepository.findAll();
    posts.forEach(post -> {
      redisTemplate.opsForZSet().add(RedisKey.POPULAR_POST_KEY.getKey(),new GetIncompletePopularPostDTO(post.getPostId(),post.getImage().getImageUri(),post.getHashtag(),post.getPostLikeCount().getLikeCount(),post.getPopularity(),post.getNickname()),post.getPopularity());
    });
  }

  @Test
  void execute() {
    Map<Integer, Integer> levelCounts = new HashMap<>();

    levelCounts.put(3, 4);
    levelCounts.put(2, 3);
    levelCounts.put(1, 3);

    GetPopularPostListResponseDTO getPopularPostListResponseDTO = findPopularPostListUsecase.execute(
        levelCounts);
    int size = getPopularPostListResponseDTO.popularPosts().size();

    org.assertj.core.api.Assertions.assertThat(size).isEqualTo(10);

  }
}
