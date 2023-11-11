package com.kakaotech.team14backend.post.domain;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "post_insta_count")
@Getter
@NoArgsConstructor(access = PROTECTED) // 기본 생성자의 접근 권한을 protected로 제한
public class PostInstaCount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long postInstaCountId;


  @ManyToOne
  @JoinColumn(name = "postId")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "memberId") // Member 엔티티의 기본 키 필드 이름으로 변경
  private Member member;

  @Column(nullable = false)
  private Long instaCount;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime modifiedAt; // 수정일

  public static PostInstaCount createPostInstaCount(Member member) {

    return PostInstaCount.builder().instaCount(0L).member(member).build();
  }

  @Builder
  public PostInstaCount(Long instaCount, Member member) {
    this.instaCount = 0L;
    this.member = member;
  }

  public void updatePostInstaCount(Long instaCount) {
    this.instaCount = instaCount;
  }

  public void mappingPost(Post post) {
    this.post = post;
  }
}
