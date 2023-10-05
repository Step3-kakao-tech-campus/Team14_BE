package com.kakaotech.team14backend.inner.post.usecase;

import static org.awaitility.Awaitility.await;

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
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import java.time.Duration;
import java.util.concurrent.Callable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "schedules.initialDelay:1000"
    ,"schedules.fixedDelay:1000"
})
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

  @Autowired
  private PostLikeCountRepository postLikeRepository;

  @BeforeEach
  void setup(){
    Member member = new Member("sonny", "sonny1234","asdf324", Role.ROLE_BEGINNER,0L, Status.STATUS_ACTIVE);
    memberRepository.save(member);

    Image image = new Image("/image/firstPhoto");
    imageRepository.save(image);

    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();

    Post post = Post.createPost(member, image, postLikeCount, "대선대선", true, "#가자", "전남대학교");
    postRepository.save(post);

  }

  @DisplayName("1번 게시물을 각각 1번과 2번 유저가 조회한 상황에서 레디스에 저장되어있는 게시글 당 조회수를 스케줄링을 사용하여 MySQL에 업데이트")
  @Test
  void execute() {

    Callable<Boolean> viewCount = (Callable<Boolean>) () -> {

      GetPostDTO getPostDTO = new GetPostDTO(1L,1L);
      updatePostViewCountUsecase.execute(getPostDTO);

      GetPostDTO getPostDTO1 = new GetPostDTO(1L,2L);
      updatePostViewCountUsecase.execute(getPostDTO1);

      schedulePostViewCountUsecase.execute();

      Post post = postRepository.findById(1L).get();

      Assertions.assertThat(post.getViewCount()).isEqualTo(3);
      return true;
    };

    await()
        .atMost(Duration.ofMinutes(1L))
        .until(viewCount);
  }

}
