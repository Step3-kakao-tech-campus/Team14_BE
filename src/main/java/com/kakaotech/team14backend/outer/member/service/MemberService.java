package com.kakaotech.team14backend.outer.member.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.point.model.PointPolicy;
import com.kakaotech.team14backend.inner.point.usecase.CreatePointUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreatePointUsecase createPointUsecase;

  //private final CreateMemberUsecase createMemberUsecase;

  public static Member createMember(String userName, String kakaoId, String instaId, Role role, Long totalLike, Status userStatus) {
    return Member.builder()
        .userName(userName)
        .kakaoId(kakaoId)
        .instaId(instaId)
        .role(role)
        .totalLike(totalLike)
        .userStatus(userStatus)
        .build();
  }
}
