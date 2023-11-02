package com.kakaotech.team14backend.inner.member.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberRepositoryTest {

  @Mock
  private MemberRepository memberRepository;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("KakaoId로 회원을 찾는 테스트")
  public void testFindByKakaoId() {
    // Given
    Member mockMember = new Member(
        "TestUser",
        "1111",
        "testInstaId",
        Role.ROLE_USER,
        0L,
        Status.STATUS_ACTIVE
    );

    // When
    when(memberRepository.findByKakaoId("testKakaoId")).thenReturn(mockMember);

    // Then
    Member foundMember = memberRepository.findByKakaoId("testKakaoId");
    assertEquals("TestUser", foundMember.getUserName());
    assertEquals("1111", foundMember.getKakaoId());
    assertEquals("testInstaId", foundMember.getInstaId());
    assertEquals(Role.ROLE_USER, foundMember.getRole());
    assertEquals(0L, foundMember.getTotalLike());
    assertEquals(Status.STATUS_ACTIVE, foundMember.getUserStatus());
  }

  @Test
  @DisplayName("MemberId로 회원을 찾는 테스트")
  public void testFindById() {
    // Given
    Long testId = 1L;
    Member mockMember = new Member(
        "TestUser",
        "1111",
        "testInstaId",
        Role.ROLE_USER,
        0L,
        Status.STATUS_ACTIVE
    );

    // When
    when(memberRepository.findById(testId)).thenReturn(Optional.of(mockMember));

    // Then
    Member foundMember = memberRepository.findById(testId).orElse(null);
    assertNotNull(foundMember);
  }

  @Test
  @DisplayName("모든 회원을 찾는 테스트")
  public void testFindAll() {
    // Given
    Member mockMember1 = new Member(
        "TestUser1",
        "1111",
        "testInstaId1",
        Role.ROLE_USER,
        0L,
        Status.STATUS_ACTIVE
    );
    Member mockMember2 = new Member(
        "TestUser2",
        "2222",
        "testInstaId2",
        Role.ROLE_USER,
        0L,
        Status.STATUS_ACTIVE
    );

    List<Member> mockMembers = Arrays.asList(mockMember1, mockMember2);

    // When
    when(memberRepository.findAll()).thenReturn(mockMembers);

    // Then
    List<Member> foundMembers = memberRepository.findAll();
    assertEquals(2, foundMembers.size());
  }

  @Test
  @DisplayName("MemberId로 회원을 삭제하는 테스트")
  public void testDeleteById() {
    // Given
    Long testId = 1L;
    Member mockMember = new Member(
        "TestUser",
        "1111",
        "testInstaId",
        Role.ROLE_USER,
        0L,
        Status.STATUS_ACTIVE
    );

    // When
    when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
    memberRepository.deleteById(testId);

    // Then
    verify(memberRepository).deleteById(testId);
    Member foundMember = memberRepository.findById(testId).orElse(null);
    assertNull(foundMember);
  }

}
