package com.kakaotech.team14backend.inner.member.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.member.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberTest {

  private Member member;

  @BeforeEach
  public void setUp() {
    member = Member.builder()
        .userName("TestUser")
        .kakaoId("testKakaoId")
        .instaId("testInstaId")
        .profileImageUrl("testUrl")
        .role(Role.ROLE_USER)
        .totalLike(0L)
        .userStatus(Status.STATUS_ACTIVE)
        .build();
  }

  @Test
  public void testMemberFields() {
    assertEquals("TestUser", member.getUserName());
    assertEquals("testKakaoId", member.getKakaoId());
    assertEquals("testInstaId", member.getInstaId());
    assertEquals("testUrl", member.getProfileImageUrl());
    assertEquals(Role.ROLE_USER, member.getRole());
    assertEquals(0L, member.getTotalLike());
    assertEquals(Status.STATUS_ACTIVE, member.getUserStatus());
  }

  @Test
  public void testUpdateInstagram() {
    member.updateInstagram(Role.ROLE_BEGINNER, "newInstaId");
    assertEquals(Role.ROLE_BEGINNER, member.getRole());
    assertEquals("newInstaId", member.getInstaId());
  }
}
