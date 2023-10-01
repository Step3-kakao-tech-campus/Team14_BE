package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchedulePostViewCountUsecaseTest {

  @Autowired
  private UpdatePostViewCountUsecase updatePostViewCountUsecase;

  @Autowired
  private SchedulePostViewCountUsecase schedulePostViewCountUsecase;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ImageRepository imageRepository;

  @BeforeEach
  void setup(){
    Member member = new Member("sonny", "sonny1234","asdf324","ROLE_BEGINNER",0L,"active");
    memberRepository.save(member);

    Image image = new Image("/image/firstPhoto");
    imageRepository.save(image);

    Post post = Post.createPost(member, image, "대선대선", true, "#가자", "전남대학교");
    postRepository.save(post);
  }
  @Test
  void execute() {
    GetPostDTO getPostDTO = new GetPostDTO(1L,1L);
    updatePostViewCountUsecase.execute(getPostDTO);

    GetPostDTO getPostDTO1 = new GetPostDTO(1L,2L);
    updatePostViewCountUsecase.execute(getPostDTO1);

    schedulePostViewCountUsecase.execute();

    Long viewCount = postRepository.findById(1L).get().getViewCount();
    Assertions.assertThat(viewCount).isEqualTo(3);
  }

}
