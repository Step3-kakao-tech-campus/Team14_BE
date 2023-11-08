package com.kakaotech.team14backend.inner.post.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.kakaotech.team14backend.exception.MemberNotFoundException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetPostLikeUsecaseTest {

  @InjectMocks// 테스트 대상 클래스에 주입할 Mock 객체를 생성
  private SetPostLikeUsecase setPostLikeUsecase;


  @Mock
  private PostRepository postRepository;

  @Mock
  private MemberRepository memberRepository;


  @Test
  void execute_nonExistentMember() {
    // Given
    Long postId = 1L;
    Long memberId = 1L;
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);

    when(memberRepository.findById(memberId)).thenReturn(
        Optional.empty());  // Assumes member does not exist

    // When
    Exception exception = assertThrows(MemberNotFoundException.class, () -> {
      setPostLikeUsecase.execute(setPostLikeDTO);
    });

    // Then
    assertEquals("존재하지 않는 회원입니다", exception.getMessage());
  }

  @Test
  void execute_nonExistentPost() {
    // Given
    Long postId = 1L;
    Long memberId = 1L;
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);

    Member member = new Member("sonny", "1234", "asdf324", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(postRepository.findById(postId)).thenReturn(
        Optional.empty());  // Assumes post does not exist

    // When
    Exception exception = assertThrows(MemberNotFoundException.class, () -> {
      setPostLikeUsecase.execute(setPostLikeDTO);
    });

    // Then
    assertEquals("존재하지 않는 게시물입니다", exception.getMessage());
  }

}
