package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.command.FindAuthPostList;
import com.kakaotech.team14backend.post.application.command.FindNonAuthPostList;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomePostList {

  private final FindNonAuthPostList findNonAuthPostListCommand;
  private final FindAuthPostList findAuthPostListCommand;

  public GetHomePostListResponseDTO excute(Long lastPostId, int size,
      Long memberId) {
    if (memberId == null) {
      return findNonAuthPostListCommand.execute(lastPostId, size, memberId);
    }
    return findAuthPostListCommand.execute(lastPostId, size, memberId);
  }

}
