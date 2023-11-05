package com.kakaotech.team14backend.outer.point.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.point.usecase.UsePointUsecase;
import com.kakaotech.team14backend.inner.point.usecase.ValidatePointByPopularPostUsecase;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {

  private final ValidatePointByPopularPostUsecase validatePointByPopularPostUsecase;
  private final UsePointUsecase usePointUsecase;

  private final PostRepository postRepository;

  public String usePointByPopularPost(
      UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO, Long senderId) {
    validatePointByPopularPostUsecase.execute(usePointByPopularPostRequestDTO);

    Post post = postRepository.findById(usePointByPopularPostRequestDTO.postId())
        .orElseThrow(() -> new RuntimeException("NOT FOUND POST"));
    Member receiver = post.getMember();

    Long point = UsePointDecider.decidePoint(usePointByPopularPostRequestDTO.postLevel());

    usePointUsecase.execute(senderId, receiver.getMemberId(), point);
    return receiver.getInstaId();
  }

}
