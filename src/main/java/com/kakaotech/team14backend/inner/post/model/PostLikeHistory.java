package com.kakaotech.team14backend.inner.post.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.member.model.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PostLikeHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postLikeHistoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId")
  private Post post;


  public static PostLikeHistory createPostLikeHistory(Member member, Post post) {
    return PostLikeHistory.builder().member(member).post(post).build();
  }

  @Builder
  public PostLikeHistory(Member member, Post post) {
    this.member = member;
    this.post = post;
  }
}
