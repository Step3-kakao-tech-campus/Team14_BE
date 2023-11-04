package com.kakaotech.team14backend.outer.point.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.service.FindMemberService;
import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.point.usecase.UsePointUsecase;
import com.kakaotech.team14backend.inner.point.usecase.ValidatePointByPopularPostUsecase;
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

  public String usePointByPopularPost(UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO, Long memberId) {
    validatePointByPopularPostUsecase.execute(usePointByPopularPostRequestDTO);
    Member member = findMemberService.execute(memberId);
    Long point = UsePointDecider.decidePoint(usePointByPopularPostRequestDTO.postLevel());
    usePointUsecase.execute(member, point);
    return member.getInstaId();
  }

}
