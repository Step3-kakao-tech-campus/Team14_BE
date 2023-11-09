package com.kakaotech.team14backend.outer.post.service;

import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.SavePostViewCount;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetHomePostUsecase {

  private final FindPostUsecase findPostUsecase;
  private final SavePostViewCount savePostViewCount;

  public GetPostResponseDTO excute(GetPostDTO getPostDTO) {
    savePostViewCount.execute(getPostDTO);
    return findPostUsecase.execute(getPostDTO);
  }
}
