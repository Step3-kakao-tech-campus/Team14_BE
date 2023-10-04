package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

@SpringBootTest
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

    PostLikeCount postLikeCount = PostLikeCount.createPostLike();

    Post post = Post.createPost(member, image, postLikeCount, "Sonny", true, "#hashTag", "university4");
    postRepository.save(post);

  }

  @Test
  void execute() {
    Callable<Boolean> popularirity = (Callable<Boolean>) () -> {
      Post post = postRepository.findById(1L).get();
      post.updateViewCount(10L);
      postRepository.save(post);

      schedulePostPopularityUsecase.execute();

      if(post.getPopularity() !=0){
        return false;
      }
      return true;
    };
    await()
        .atMost(Duration.ofMinutes(21L)) // 최대 대기 시간
        .pollDelay(Duration.ofMinutes(20L)) // Awaitility가 첫 번째로 결과를 확인하기 전에 기다릴 지연 시간
        .until(popularirity);

  }
}
