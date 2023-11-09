package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.usecase.FindNonAuthPostListUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomePostListUsecase {

  private final FindNonAuthPostListUsecase findNonAuthPostListUsecase;
  private final FindPostListUsecase findPostListUsecase;

  public GetHomePostListResponseDTO excute(Long lastPostId, int size,
      Long memberId) {
    if (memberId == null) {
      return findNonAuthPostListUsecase.execute(lastPostId, size);
    }
    return findPostListUsecase.execute(lastPostId, size, memberId);
  }

}
