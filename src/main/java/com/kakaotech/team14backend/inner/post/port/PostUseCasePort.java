package com.kakaotech.team14backend.inner.post.port;

import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;

public interface PostUseCasePort {

  GetHomePostListResponseDTO getAuthenticatedPostList(Long lastPostId, int size, Long memberId);
}
