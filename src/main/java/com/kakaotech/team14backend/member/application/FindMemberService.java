package com.kakaotech.team14backend.member.application;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.exception.MemberNotFoundException;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMemberService {

  private final MemberRepository memberRepository;

  public Member execute(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(
        () -> new MemberNotFoundException(MessageCode.NOT_REGISTER_MEMBER));
  }

}
