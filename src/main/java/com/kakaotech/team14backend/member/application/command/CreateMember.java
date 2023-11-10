package com.kakaotech.team14backend.member.application.command;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.member.domain.Status;
import com.kakaotech.team14backend.point.application.command.CreatePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateMember {

  private final CreatePoint createPointUsecase;

  public Member execute(String userName, String kakaoId, String instaId,
      String profileImageUrl, Role role, Long totalLike, Status userStatus) {
    Member member = Member.builder().memberId(Long.valueOf(kakaoId)).userName(userName)
        .kakaoId(kakaoId).instaId(instaId).role(role).profileImageUrl(profileImageUrl)
        .totalLike(totalLike).userStatus(userStatus).build();
    createPointUsecase.execute(member);
    return member;
  }
}
