package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindLikeStatusService {

  private final PostLikeRepository postLikeRepository;

  public boolean execute(Long memberId, Long postId) {
    return postLikeRepository.findFirstByMemberAndPostOrderByCreatedAtDesc(memberId, postId)
        .stream()
        .findFirst()
        .map(PostLike::isLiked)
        .orElse(false);
  }
}
