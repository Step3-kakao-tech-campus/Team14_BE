package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.usecase.FindPopularPosts;
import com.kakaotech.team14backend.inner.post.usecase.SetPostLikeService;
import com.kakaotech.team14backend.inner.post.usecase.UpdatePostLikeCountUsecase;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SetPostLikeUsecase {

  private final SetPostLikeService setPostLikeService;
  private final UpdatePostLikeCountUsecase updatePostLikeCountUsecase;

  @Transactional
  public SetPostLikeResponseDTO execute(SetPostLikeDTO setPostLikeDTO) {
    SetPostLikeResponseDTO isLiked = setPostLikeService.execute(setPostLikeDTO);

    Long postId = setPostLikeDTO.postId();
    GetPostLikeCountDTO getPostLikeCountDTO = new GetPostLikeCountDTO(postId, isLiked.isLiked());

    // 좋아요 상태를 반환 할 때 카운트를 업데이트
    updatePostLikeCountUsecase.execute(getPostLikeCountDTO);
    return isLiked;
  }
}
