package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdatePostLikeCountUsecaseTest {

  @InjectMocks
  private UpdatePostLikeCountUsecase updatePostLikeCountUsecase;

  @Mock
  private SetPostLikeUsecase setPostLikeUsecase;

  @Mock
  private PostLikeCountRepository postLikeCountRepository;
  @Mock
  private PostLikeRepository postLikeRepository;

  @Mock
  private PostRepository postRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private ImageRepository imageRepository;

  @Mock // Post 클래스를 모의 객체로 만듭니다.
  private Post post;

  @Mock // PostLikeCount 클래스를 모의 객체로 만듭니다.
  private PostLikeCount postLikeCount;

  @Test
  void execute() {
    //given
    Long postId = 1L;
    Long memberId = 1L;

    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = new SetPostLikeResponseDTO(true);
    ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

    // 모의 행동 설정
    when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));
    when(postLikeCountRepository.findByPostId(postId)).thenReturn(postLikeCount);
    when(post.getPostLikeCount()).thenReturn(postLikeCount);
    when(postLikeCount.getLikeCount()).thenReturn(1L);
    when(setPostLikeUsecase.execute(setPostLikeDTO)).thenReturn(setPostLikeResponseDTO);

    SetPostLikeResponseDTO isLiked = setPostLikeUsecase.execute(setPostLikeDTO);

    GetPostLikeCountDTO getPostLikeCountDTO = new GetPostLikeCountDTO(postId, isLiked.isLiked());
    //when
    updatePostLikeCountUsecase.execute(getPostLikeCountDTO);
    // then
    System.out.println("Current likeCount: " + postLikeCount.getLikeCount());

    // verify(postLikeCount).updateLikeCount(2L);
    verify(postLikeCount).updateLikeCount(captor.capture());
    Long updatedLikeCount = captor.getValue();
    System.out.println("Updated likeCount: " + updatedLikeCount);
  }
}


