package com.kakaotech.team14backend.inner.post.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PostTest {

  private final long likeCount = 100;
  private final long viewCount = 300;

  private final LocalDateTime createdAt = LocalDateTime.of(2023,10,1,19,10);

  @DisplayName("게시물의 생성일은 2023년 10월 1일 19시 10분이다. 현재 시간은 2023년 10월 1일 20시 20분일 때,게시물의 나이는 14이다. ")
  @Test
  void calculatePostAge_case_0(){
    // given
    LocalDateTime nowDateTime = createdAt.plusHours(1).plusMinutes(10);
    Instant now = toInstant(nowDateTime);

    Post post = createPost();

    //when
    long postAge = post.calculatePostAge(now);

    //then
    assertThat(postAge).isEqualTo(14);
  }

  @DisplayName("게시물의 생성일은 2023년 10월 1일 19시 10분이다. 현재 시간은 2023년 10월 1일 19시 11분일 때,게시물의 나이는 1이다. ")
  @Test
  void calculatePostAge_case_1(){
    // given
    LocalDateTime nowDateTime = createdAt.plusMinutes(1);
    Instant now = toInstant(nowDateTime);

    Post post = createPost();

    //when
    long postAge = post.calculatePostAge(now);

    //then
    assertThat(postAge).isEqualTo(1);
  }

  @DisplayName("좋아요 100, 조회수 300, 생성으로부터 70분 경과하였을 때 인기도는 28이다.")
  @Test
  void calculatePopularity_case_0(){

    //given
    LocalDateTime nowDateTime = createdAt.plusHours(1).plusMinutes(10);
    Instant now = toInstant(nowDateTime);

    Post post = createPost();

    long popularity = post.calculatePopularity(now);

    assertThat(popularity).isEqualTo(28);

  }

  @DisplayName("좋아요 100, 조회수 300, 생성으로부터 10분 경과하였을 때 인기도는 28이다.")
  @Test
  void calculatePopularity_case_1(){

    //given
    LocalDateTime nowDateTime = createdAt.plusMinutes(10);
    Instant now = toInstant(nowDateTime);

    Post post = createPost();

    long popularity = post.calculatePopularity(now);

    assertThat(popularity).isEqualTo(200);

  }

  private Instant toInstant(final LocalDateTime nowLocalDateTime) {
    return nowLocalDateTime.atZone(ZoneId.systemDefault()).toInstant();
  }

  private Post createPost(){
    Post post = new Post();
    post.setCreatedAt(toInstant(createdAt));
    post.setViewCount(viewCount);

    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    postLikeCount.updateLikeCount(likeCount);

    post.mappingPostLikeCount(postLikeCount);
    return post;
  }

}
