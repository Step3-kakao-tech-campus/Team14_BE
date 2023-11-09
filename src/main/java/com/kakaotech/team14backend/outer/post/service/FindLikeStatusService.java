package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FindLikeStatusService {

  private final PostLikeRepository postLikeRepository;

  public boolean execute(Long memberId, Long postId) {

    List<PostLike> latestPostLike = postLikeRepository
        .findFirstByMemberAndPostOrderByCreatedAtDesc(memberId, postId);
    if (latestPostLike.isEmpty()) {
      return false;
    } else {
      return latestPostLike.get(0).isLiked();
    }
  }
}
