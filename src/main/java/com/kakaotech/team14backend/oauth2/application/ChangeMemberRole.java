package com.kakaotech.team14backend.oauth2.application;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeMemberRole {
  private final MemberRepository memberRepository;
  public void execute(Member member, String instaId) {
    member.updateInstagram(Role.ROLE_USER, instaId);
    memberRepository.save(member);
  }
}
