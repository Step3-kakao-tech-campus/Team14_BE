package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.post.domain.PostLikeCount;
import com.kakaotech.team14backend.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.post.infrastructure.PostLikeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UpdatePostLikeCount {

  private final PostLikeCountRepository postLikeCountRepository;

  public void execute(GetPostLikeCountDTO getPostLikeCountDTO) {
    Long postId = getPostLikeCountDTO.postId();
    boolean isLiked = getPostLikeCountDTO.isLiked();

    PostLikeCount postLikeCount = postLikeCountRepository.findByPostId(postId);
    Long likeCount = postLikeCount.getLikeCount();

    likeCount = isLiked ? likeCount + 1 : likeCount - 1;
    postLikeCount.updateLikeCount(likeCount);
  }


}
