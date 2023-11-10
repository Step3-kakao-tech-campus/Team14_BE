package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.command.UpdatePostLike;
import com.kakaotech.team14backend.post.application.command.UpdatePostLikeCount;
import com.kakaotech.team14backend.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SetPostLikeUsecase {

  private final UpdatePostLike updatePostLike;
  private final UpdatePostLikeCount updatePostLikeCount;

  @Transactional
  public SetPostLikeResponseDTO execute(SetPostLikeDTO setPostLikeDTO) {
    SetPostLikeResponseDTO isLiked = updatePostLike.execute(setPostLikeDTO);

    Long postId = setPostLikeDTO.postId();
    GetPostLikeCountDTO getPostLikeCountDTO = new GetPostLikeCountDTO(postId, isLiked.isLiked());

    // 좋아요 상태를 반환 할 때 카운트를 업데이트
    updatePostLikeCount.execute(getPostLikeCountDTO);
    return isLiked;
  }
}
