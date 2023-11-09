package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPopularPostFacade {

  private final FindPopularPosts findPopularPosts;
  private final FindPostListUsecase findPostListUsecase;

  public GetPopularPostListResponseDTO getPopularPostList(
      GetPopularPostListRequestDTO getPopularPostListRequestDTO) {
    int size = findPostListUsecase.findPostListSize();
    return findPopularPosts.execute(getPopularPostListRequestDTO, size);
  }
}
