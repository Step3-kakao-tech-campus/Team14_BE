package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.post.domain.PostLike;
import com.kakaotech.team14backend.post.infrastructure.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindLikeStatus {

  private final PostLikeRepository postLikeRepository;

  public boolean execute(Long memberId, Long postId) {
    return postLikeRepository.findFirstByMemberAndPostOrderByCreatedAtDesc(memberId, postId)
        .stream()
        .findFirst()
        .map(PostLike::isLiked)
        .orElse(false);
  }
}
