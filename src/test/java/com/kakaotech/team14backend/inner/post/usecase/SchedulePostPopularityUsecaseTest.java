package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = {
    "schedules.initialDelay:1000"
    ,"schedules.fixedDelay:1000"
})
class SchedulePostPopularityUsecaseTest {

  @Autowired
  private SchedulePostPopularityUsecase schedulePostPopularityUsecase;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ImageRepository imageRepository;


  @BeforeEach
  void setUp() {
    Member member = Member.createMember("Sonny", "1234", "asdfc", Role.ROLE_USER, 12L, Status.STATUS_ACTIVE);
    memberRepository.save(member);
    Image image = Image.createImage("image_uri1");
    imageRepository.save(image);

    PostLike postLike = PostLike.createPostLike();

    Post post = Post.createPost(member, image,postLike, "Sonny", true, "#hashTag", "university4");
    postRepository.save(post);

  }

  @Test
  void execute() {
    Callable<Boolean> popularirity = (Callable<Boolean>) () -> {
      Post post = postRepository.findById(1L).get();
      post.updateViewCount(500L);
      postRepository.save(post);

      schedulePostPopularityUsecase.execute();

      Post updatedPost = postRepository.findById(1L).get();

      Assertions.assertEquals(updatedPost.getPopularity() !=0,true);
      return true;
    };
    await()
        .atMost(Duration.ofMinutes(3L)) // 최대 대기 시간
        .until(popularirity);
  }

}
