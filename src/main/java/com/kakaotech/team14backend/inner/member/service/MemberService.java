package com.kakaotech.team14backend.inner.member.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
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
