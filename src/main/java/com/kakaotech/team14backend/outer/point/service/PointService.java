package com.kakaotech.team14backend.outer.point.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.service.FindMemberService;
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
@Transactional(readOnly = true)
public class PointService {

  private final ValidatePointByPopularPostUsecase validatePointByPopularPostUsecase;
  private final UsePointUsecase usePointUsecase;
  private final FindMemberService findMemberService;

  private final PostRepository postRepository;

  public String usePointByPopularPost(
      UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO, Long memberId) {
    validatePointByPopularPostUsecase.execute(usePointByPopularPostRequestDTO);
    Member member = findMemberService.execute(memberId);
    Long point = UsePointDecider.decidePoint(usePointByPopularPostRequestDTO.postLevel());

    Post post = postRepository.findById(usePointByPopularPostRequestDTO.postId()).orElseThrow();
    Member received = post.getMember();
    usePointUsecase.execute(received.getMemberId(), member.getMemberId(), point);
    return member.getInstaId();
  }

}
