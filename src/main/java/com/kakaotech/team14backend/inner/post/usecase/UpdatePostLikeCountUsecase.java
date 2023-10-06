package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePostLikeCountUsecase {

  private final PostLikeCountRepository postLikeCountRepository;
  private final RedisTemplate<String, Long> redisTemplate;
  private static final String POST_LIKE_KEY_PREFIX = "POST_LIKE::";

  // 좋아요 상태를 반환 할 때 그 타이밍에 카운트를 업데이트하면 될거 같은데
  public void execute(GetPostLikeCountDTO getPostLikeCount) {
    Long postId = getPostLikeCount.postId();
    boolean isLiked = getPostLikeCount.isLiked();

    Long cachedCount = redisTemplate.opsForValue().get(POST_LIKE_KEY_PREFIX + postId);

    if (cachedCount == null) {
      cachedCount = postLikeCountRepository.findByPostId(postId).getLikeCount();
    }
    cachedCount = isLiked ? cachedCount + 1 : cachedCount - 1;
    redisTemplate.opsForValue().set(POST_LIKE_KEY_PREFIX + postId, cachedCount);
  }


}
