package com.kakaotech.team14backend.member.application.usecase;

import com.kakaotech.team14backend.member.application.command.FindMember;
import com.kakaotech.team14backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteMember {

  private final FindMember findMemberService;

  @Transactional
  public void execute(Long memberId) {
    Member member = findMemberService.execute(memberId);
    member.makeUserInactive();
  }

}
