package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:db/teardown.sql")
@SpringBootTest
public class UpdatePostLikeCountUsecaseTest {

  @Autowired
  private UpdatePostLikeCountUsecase updatePostLikeCountUsecase;

  @Autowired
  private PostLikeCountRepository postLikeCountRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private SetPostLikeUsecase setPostLikeUsecase;
  @Autowired
  private ImageRepository imageRepository;

  @Test
  void execute() {
    //given
    Long postId = 1L;
    Long memberId = 1L;  // 첫 번째 멤버 사용

    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = setPostLikeUsecase.execute(setPostLikeDTO);

    GetPostLikeCountDTO getPostLikeCountDTO = new GetPostLikeCountDTO(1L, true);
    updatePostLikeCountUsecase.execute(getPostLikeCountDTO);

    // when
    Assertions.assertThat(postRepository.findById(postId).get().getPostLikeCount().getLikeCount())
        .isEqualTo(1L);
  }
}
