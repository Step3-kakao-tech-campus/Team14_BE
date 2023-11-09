package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePostLikeCountUsecase {

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
