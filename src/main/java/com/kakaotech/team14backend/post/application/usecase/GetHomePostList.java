package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.command.FindAuthPostList;
import com.kakaotech.team14backend.post.application.command.FindNonAuthPostList;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomePostList {

  private final FindNonAuthPostList findNonAuthPostList;
  private final FindAuthPostList findAuthPostList;

  public GetHomePostListResponseDTO execute(Long lastPostId, int size,
                                            Long memberId) {
    if (memberId == null) {
      return findNonAuthPostList.execute(lastPostId, size, memberId);
    }
    return findAuthPostList.execute(lastPostId, size, memberId);
  }

}
