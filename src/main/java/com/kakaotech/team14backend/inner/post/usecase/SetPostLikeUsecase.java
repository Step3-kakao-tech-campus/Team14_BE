package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.outer.post.dto.setPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.setPostLikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPostLikeUsecase {

  private final PostLikeRepository postLikeRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private static final String POST_LIKE_KEY_PREFIX = "POST_LIKE::";

  public setPostLikeResponseDTO execute(setPostLikeDTO setPostLikeDTO) {
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
    return (boolean) redisTemplate.opsForHash().get(key, memberId);
  }

  private setPostLikeResponseDTO toggleLikeStatus(String key, Long memberId, Boolean isLiked) {
    boolean newStatus = (isLiked == null || !isLiked);
    redisTemplate.opsForHash().put(key, memberId, newStatus);
    return new setPostLikeResponseDTO(newStatus);

  }

}
