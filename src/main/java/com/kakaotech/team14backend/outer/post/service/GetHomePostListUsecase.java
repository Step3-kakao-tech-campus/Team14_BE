package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.post.application.FindNonAuthPostListUsecase;
import com.kakaotech.team14backend.post.application.FindAuthPostListUsecase;
import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomePostListUsecase {

  private final FindNonAuthPostListUsecase findNonAuthPostListUsecase;
  private final FindAuthPostListUsecase findAuthPostListUsecase;

  public GetHomePostListResponseDTO excute(Long lastPostId, int size,
      Long memberId) {
    if (memberId == null) {
      return findNonAuthPostListUsecase.execute(lastPostId, size);
    }
    return findAuthPostListUsecase.execute(lastPostId, size, memberId);
  }

}
