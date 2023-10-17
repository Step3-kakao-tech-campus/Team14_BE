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
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.member.service.MemberService;
import com.kakaotech.team14backend.outer.post.schedule.SchedulePostPopularity;
import java.time.Duration;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.EnabledIf;



@SpringBootTest(properties = {
    "schedules.initialDelay:1000"
    , "schedules.fixedDelay:1000"
})

@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)

class SchedulePostPopularityTest {

  @Autowired
  private SchedulePostPopularity schedulePostPopularity;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberService memberService;

  @Autowired
  private ImageRepository imageRepository;



  @Autowired
  private EntityManager em;


  @BeforeEach
  void setUp() {
    Member member = memberService.createMember("Sonny", "1234", "asdfc", Role.ROLE_USER, 12L,
        Status.STATUS_ACTIVE);
    memberRepository.save(member);
    Image image = Image.createImage("image_uri1");
    imageRepository.save(image);

    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();

    Post post = Post.createPost(member, image, postLikeCount, "Sonny", true, "#hashTag", "university4");
    postRepository.save(post);
  }

  @Test
  void execute_schedule() {

    await()
        .atMost(Duration.ofMinutes(10L)) // 최대 대기 시간
        .untilAsserted(() -> {
          Post post = postRepository.findById(1L).get();
          post.updateViewCount(500L);
          postRepository.save(post);

          try {
            Thread.sleep(1500);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

          schedulePostPopularity.execute();
          Post updatedPost = postRepository.findById(1L).get();
          Assertions.assertThat(updatedPost.getPopularity()).isEqualTo(500);

        });

  }

  @Test
  void execute() {
    Post post = postRepository.findById(1L).get();
    post.updateViewCount(500L);
    postRepository.save(post);

    schedulePostPopularity.execute();
    Post updatedPost = postRepository.findById(1L).get();
    Assertions.assertThat(updatedPost.getPopularity()).isEqualTo(500);
  }

}
