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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SaveTemporaryPopularPostListUsecaseTest {

  @Autowired
  private SaveTemporaryPopularPostListUsecase saveTemporaryPopularPostListUsecase;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  private final String key = "popularPost";

  @BeforeEach
  void setUp() {
    redisTemplate.delete(key);
    Member member = Member.createMember("Sonny", "1234", "asdfc", Role.ROLE_USER, 12L,
        Status.STATUS_ACTIVE);
    memberRepository.save(member);
    Image image = Image.createImage("image_uri1");
    imageRepository.save(image);

    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();

    Post post = Post.createPost(member, image, postLikeCount, "Sonny", true, "#hashTag", "university4");
    postRepository.save(post);
  }

  @Test
  void execute() {

    saveTemporaryPopularPostListUsecase.execute();
    Long size = redisTemplate.opsForZSet().size(key);
    org.assertj.core.api.Assertions.assertThat(size).isEqualTo(1);
  }
}
