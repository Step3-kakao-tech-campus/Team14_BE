package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.usecase.FindMyPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SavePostViewCount;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMyPostUsecase {

  private final FindMyPostUsecase findMyPostUsecase;
  private final SavePostViewCount savePostViewCount;

  public GetMyPostResponseDTO excute(Long memberId, Long postId) {
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    savePostViewCount.execute(getPostDTO);
    return findMyPostUsecase.execute(memberId, postId);
  }
}
