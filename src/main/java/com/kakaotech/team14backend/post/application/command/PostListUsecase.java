package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;

public interface PostListUsecase {

  GetHomePostListResponseDTO execute(Long lastPostId, int size, Long memberId);
}
