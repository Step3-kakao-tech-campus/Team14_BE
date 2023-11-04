package com.kakaotech.team14backend.outer.point.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.service.FindMemberService;
import com.kakaotech.team14backend.inner.point.usecase.UsePointUsecase;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class usePointByPostUsecase {

  private final FindMemberService findMemberService;
  private final UsePointUsecase usePointUsecase;

  //  private final Post
  public String getInstaId(UsePointByPostRequestDTO usePointByPostRequestDTO,
      Long memberId) {
    Long postId = usePointByPostRequestDTO.postId();
    Member member = findMemberService.execute(memberId);

    final Long GET_POINT = 1L;
    usePointUsecase.execute(member, GET_POINT);
    return member.getInstaId();
  }
}
