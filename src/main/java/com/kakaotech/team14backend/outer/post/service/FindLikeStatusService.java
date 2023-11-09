package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FindLikeStatusService {

  private final PostLikeRepository postLikeRepository;

  public boolean execute(Long memberId, Long postId) {
    Optional<PostLike> latestPostLike = postLikeRepository
        .findFirstByMemberAndPostOrderByCreatedAtDesc(memberId, postId);

    return latestPostLike.map(PostLike::isLiked).orElse(false);
  }
}
