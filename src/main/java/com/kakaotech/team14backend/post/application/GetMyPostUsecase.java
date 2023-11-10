package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMyPostUsecase {

  private final FindMyPostUsecase findMyPostUsecase;

  public GetMyPostResponseDTO excute(Long memberId, Long postId) {
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    return findMyPostUsecase.execute(memberId, postId);
  }
}
