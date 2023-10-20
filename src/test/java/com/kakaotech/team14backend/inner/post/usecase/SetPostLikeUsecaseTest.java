package com.kakaotech.team14backend.inner.post.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetPostLikeUsecaseTest {

  @InjectMocks// 테스트 대상 클래스에 주입할 Mock 객체를 생성
  private SetPostLikeUsecase setPostLikeUsecase;

  @Mock
  private PostLikeRepository postLikeRepository;

  @Mock
  private PostRepository postRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private ImageRepository imageRepository;

  @Autowired
  private ObjectMapper om;


  @Test
  void execute() {
    // Given
    Long postId = 1L;
    Long memberId = 1L;
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);

    Member member = new Member(memberId,"sonny", "sonny1234", "asdf324","none", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);

    Image image = new Image("/image/firstPhoto");
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    Post post = Post.createPost(member, image, postLikeCount, "대선대선", true, "#가자", "전남대학교");

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));

    // When
    SetPostLikeResponseDTO response = setPostLikeUsecase.execute(setPostLikeDTO);
    System.out.println("response : " + response);
    // Then
    verify(memberRepository, times(1)).findById(memberId);
    verify(postRepository, times(1)).findById(postId);
//    verify(postLikeRepository, times(1)).findFirstByMemberAndPostOrderByCreatedAtDesc(
//        any(Member.class), any(Post.class));
  }

  @Test
  void execute_unlike() {
    // Given
    Long postId = 1L;
    Long memberId = 1L;
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);

    Member member = new Member("sonny", "sonny1234", "asdf324", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    Image image = new Image("/image/firstPhoto");
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    Post post = Post.createPost(member, image, postLikeCount, "대선대선", true, "#가자", "전남대학교");

    PostLike postLike = PostLike.createPostLike(member, post,
        true);  // Assumes a like already exists
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    when(postLikeRepository.findFirstByMemberAndPostOrderByCreatedAtDesc(member.getMemberId(), post.getPostId())).thenReturn(
        Optional.of(postLike));

    // When
    SetPostLikeResponseDTO response = setPostLikeUsecase.execute(setPostLikeDTO);

    // Then
    assertFalse(response.isLiked());  // Assumes the like was toggled to false
  }

  @Test
  void execute_nonExistentMember() {
    // Given
    Long postId = 1L;
    Long memberId = 1L;
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);

    when(memberRepository.findById(memberId)).thenReturn(
        Optional.empty());  // Assumes member does not exist

    // When
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
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

    Member member = new Member("sonny", "sonny1234", "asdf324", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(postRepository.findById(postId)).thenReturn(
        Optional.empty());  // Assumes post does not exist

    // When
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      setPostLikeUsecase.execute(setPostLikeDTO);
    });

    // Then
    assertEquals("존재하지 않는 게시물입니다", exception.getMessage());
  }

}
