package com.kakaotech.team14backend.inner.post.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetPostLikeUsecaseTest {

  @Autowired
  private PostLikeRepository postLikeRepository;

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private SetPostLikeUsecase setPostLikeUsecase;

  @Autowired
  private ObjectMapper om;

  @BeforeEach
  void setup() {
    Member member1 = new Member("sonny", "sonny1234", "asdf324", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    memberRepository.save(member1);

    Member member2 = new Member("john", "john1234", "qwer567", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    memberRepository.save(member2);

    Image image = new Image("/image/firstPhoto");
    imageRepository.save(image);
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();

    Post post = Post.createPost(member1, image, postLikeCount, "대선대선", true, "#가자", "전남대학교");
    postRepository.save(post);
  }

  @DisplayName("좋아요 토글")
  @Test
  void execute() throws JsonProcessingException {
    //given
    Long postId = 1L;
    Long memberId = 1L;  // 첫 번째 멤버 사용

    //when
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = setPostLikeUsecase.execute(setPostLikeDTO);

    //then
    Assertions.assertThat(setPostLikeResponseDTO.isLiked()).isTrue();
  }

  @DisplayName("좋아요 취소 토글")
  @Test
  void executeUnlike() throws JsonProcessingException {
    //given
    Long postId = 1L;
    Long memberId = 2L;  // 두 번째 멤버 사용

    // 좋아요를 먼저 설정
    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(postId, memberId);
    SetPostLikeResponseDTO setPostLikeResponseDTO = setPostLikeUsecase.execute(setPostLikeDTO);

    //when
    // 좋아요를 취소
    SetPostLikeResponseDTO setPostLikeResponseDTO2 = setPostLikeUsecase.execute(setPostLikeDTO);

    //then
    Assertions.assertThat(setPostLikeResponseDTO2.isLiked()).isFalse();
  }


}
