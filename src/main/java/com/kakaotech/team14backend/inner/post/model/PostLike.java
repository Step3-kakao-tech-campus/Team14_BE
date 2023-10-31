package com.kakaotech.team14backend.inner.post.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.member.model.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "post_like")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PostLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postLikeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId")
  private Post post;

  private boolean isLiked;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일


  public static PostLike createPostLike(Member member, Post post, boolean isLiked) {
    return PostLike.builder().member(member).post(post).isLiked(isLiked).build();
  }

  @Builder
  public PostLike(Member member, Post post, boolean isLiked) {
    this.member = member;
    this.post = post;
    this.isLiked = isLiked;
  }
}
