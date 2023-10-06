package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostLikeCountDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

  @BeforeEach
  void setup() {
    Member member = new Member("sonny", "sonny1234", "asdf324", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    memberRepository.save(member);

    Image image = new Image("/image/firstPhoto");
    imageRepository.save(image);
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();

    Post post = Post.createPost(member, image, postLikeCount, "대선대선", true, "#가자", "전남대학교");
    postRepository.save(post);
  }

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
        .isGreaterThan(0L);
  }
}
