package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.member.application.command.FindMember;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.member.domain.Status;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.post.application.command.UpdatePostLike;
import com.kakaotech.team14backend.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SetPostLikeUsecaseTest {

  @InjectMocks// 테스트 대상 클래스에 주입할 Mock 객체를 생성
  private UpdatePostLike setPostLikeUsecase;
  @Mock
  private FindMember findMemberService;

  @Mock
  private PostRepository postRepository;

  @Mock
  private MemberRepository memberRepository;


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
    Exception exception = assertThrows(PostNotFoundException.class, () -> {
      setPostLikeUsecase.execute(setPostLikeDTO);
    });

    // Then
    assertNull(exception.getMessage());
  }

}
