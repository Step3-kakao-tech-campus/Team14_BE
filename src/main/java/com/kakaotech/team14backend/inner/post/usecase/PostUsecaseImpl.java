package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.port.PostUseCasePort;
import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostUsecaseImpl implements PostUseCasePort {

  private final FindPostListUsecase findPostListUsecase;

  @Override
  public GetHomePostListResponseDTO getAuthenticatedPostList(Long lastPostId, int size,
      Long memberId) {
    return findPostListUsecase.execute(lastPostId, size, memberId);
  }
}
