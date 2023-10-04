package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLikeHistory;
import com.kakaotech.team14backend.inner.post.repository.PostLikeHistoryRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePostLikeHistoryUsecase {

  private final PostLikeHistoryRepository postLikeHistoryRepository;

  // Post에 좋아요가 추가 되고, Redis에서 스케줄링 되어, DB에 저장 되는 순간에 PostLikeHistory를 생성한다.
  // 만약, 추후 좋아요가 취소 된다면 PostLikeHistory를 삭제한다.


  @Transactional
  public PostLikeHistory execute(GetPostLikeHistoryDTO getPostLikeHistoryDTO) {
    Member member = getPostLikeHistoryDTO.member();
    Post post = getPostLikeHistoryDTO.post();
    PostLikeHistory postLikeHistory = PostLikeHistory.createPostLikeHistory(member, post);
    return postLikeHistoryRepository.save(postLikeHistory);

  }
}
