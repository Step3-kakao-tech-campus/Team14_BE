package com.kakaotech.team14backend.inner.post.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
  private final long likeCount = 100L;
  private final long viewCount = 1000L;
  private final LocalDateTime postCreatedAt = LocalDateTime.of(2023, 10, 10, 18, 0, 0);

  @Test
  @DisplayName("좋아요 100, 조회수 1000, 생성으로부터 1시간 경과했을 때 인기도는 1100이다.")
  void calculatePopularity_case_0() {
    // given
    final var now = postCreatedAt.plusHours(1);
    final var post = createPostWith(likeCount, viewCount, postCreatedAt);

    // when
    final long popularity = post.calculatePopularity(toInstant(now));

    // then
    assertThat(popularity).isEqualTo(1100L);
  }

  @Test
  @DisplayName("좋아요 100, 조회수 1000, 생성으로부터 10시간 경과했을 때 인기도는 550이다.")
  void calculatePopularity_case_1() {
    // given
    final var now = postCreatedAt.plusHours(10);
    final var post = createPostWith(likeCount, viewCount, postCreatedAt);

    // when
    final long popularity = post.calculatePopularity(toInstant(now));

    // then
    assertThat(popularity).isEqualTo(550L);
  }

  private Post createPostWith(final long likeCount, final long viewCount, final LocalDateTime postCreatedAt) {
    final var count = new PostLikeCount();
    count.updateLikeCount(likeCount);
    final var post = new Post();
    post.setPostLikeCount(count);
    post.setViewCount(viewCount);
    post.setCreatedAt(toInstant(postCreatedAt));
    return post;
  }

  private Instant toInstant(final LocalDateTime dateTime) {
    return dateTime.atZone(ZoneId.systemDefault()).toInstant();
  }
}
