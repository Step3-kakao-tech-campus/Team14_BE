package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPostLikeUsecase {

  private final PostLikeCountRepository postLikeCountRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private static final String POST_LIKE_KEY_PREFIX = "POST_LIKE::";

  public SetPostLikeResponseDTO execute(SetPostLikeDTO setPostLikeDTO) {
    Long postId = setPostLikeDTO.postId();
    Long memberId = setPostLikeDTO.memberId();

    String key = getRedisKeyForPost(postId);
    Boolean isLiked = getUserLikeStatus(key, memberId);

    return toggleLikeStatus(key, memberId, isLiked);

  }

  private String getRedisKeyForPost(Long postId) {
    return POST_LIKE_KEY_PREFIX + postId;
  }

  private boolean getUserLikeStatus(String key, Long memberId) {
    return redisTemplate.opsForZSet().score(key, memberId) != null;
  }

  // 좋아요를 눌렀다면, set에 member 저장, 좋아요를 취소했다면, set에서 member 삭제합니다
  private SetPostLikeResponseDTO toggleLikeStatus(String key, Long memberId, Boolean isLiked) {
    boolean newStatus = (isLiked == null || !isLiked);
    if (newStatus) {
      redisTemplate.opsForZSet().add(key, memberId, 0);
    } else {
      redisTemplate.opsForZSet().remove(key, memberId);
    }
    boolean actualStatus = redisTemplate.opsForZSet().score(key, memberId) != null;
    return new SetPostLikeResponseDTO(actualStatus);
  }

}
