package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.domain.PostLikeCount;
import com.kakaotech.team14backend.post.infrastructure.PostLikeCountRepository;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SchedulePostLikeCountUsecase {

  private final PostLikeCountRepository postLikeCountRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private static final String POST_LIKE_KEY_PREFIX = "POST_LIKE::";

  @Transactional // 트랜잭션 종료시, DB에 반영
  @Scheduled(initialDelay = 600000, fixedDelay = 600000)
  public void execute() {
    Set<String> keys = redisTemplate.keys(POST_LIKE_KEY_PREFIX + "*");
    for (String key : keys) {
      Long postId = Long.valueOf(key.replace(POST_LIKE_KEY_PREFIX, ""));
      Long likeCount = getLikeCount(key);
      PostLikeCount postLikeCount = postLikeCountRepository.findById(postId).orElseThrow();
      postLikeCount.updateLikeCount(likeCount);
    }
  }

  private Long getLikeCount(String key) {
    Map<Object, Object> likes = redisTemplate.opsForHash().entries(key);
    return likes.values().stream().filter(status -> (boolean) status).count();
  }
}
