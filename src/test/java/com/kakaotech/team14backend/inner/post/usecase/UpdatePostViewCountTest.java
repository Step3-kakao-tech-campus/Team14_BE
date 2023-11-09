package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.RedisKey;
import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.application.SavePostViewCount;
import com.kakaotech.team14backend.post.application.SchedulePostViewCount;
import com.kakaotech.team14backend.post.application.UpdatePostViewCount;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.util.Set;

@SpringBootTest
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)
class UpdatePostViewCountTest {

  @Autowired
  private SavePostViewCount saveTemporaryPostViewCountUsecase;

  @Autowired
  private UpdatePostViewCount updatePostViewCountUsecase;


  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private SchedulePostViewCount schedulePostViewCount;


  @BeforeEach
  void setup() {

    Set keys = redisTemplate.keys(RedisKey.VIEW_COUNT_PREFIX + "*");
    for (Object key : keys) {
      redisTemplate.delete(key);
    }

    Member member = new Member("sonny", "1234", "asdf324", Role.ROLE_BEGINNER, 0L,
        Status.STATUS_ACTIVE);
    memberRepository.save(member);

    Image image = new Image("/image/firstPhoto");
    imageRepository.save(image);

    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    PostInstaCount postInstaCount = PostInstaCount.createPostInstaCount(member);
    Post post = Post.createPost(member, image, postLikeCount, "대선대선", true, "#가자");
    postRepository.save(post);

  }

  @DisplayName("게시물의 조회수 update 테스트")
  @Test
  void execute() {

    GetPostDTO getPostDTO = new GetPostDTO(1L, 1L);
    saveTemporaryPostViewCountUsecase.execute(getPostDTO);

    GetPostDTO getPostDTO1 = new GetPostDTO(1L, 2L);
    saveTemporaryPostViewCountUsecase.execute(getPostDTO1);

    updatePostViewCountUsecase.execute();

    Post post = postRepository.findById(1L).get();
    schedulePostViewCount.execute();

    Assertions.assertThat(post.getViewCount()).isEqualTo(2);
  }

}
