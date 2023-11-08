package com.kakaotech.team14backend.inner.post.model;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
  private Long postId; // 게시글 ID

  @MapsId  // Post의 PK를 PostLike의 PK로 사용
  @OneToOne
  @JoinColumn(name = "postId")
  private Post post;


  @Column(nullable = false)
  private Long instaCount;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime modifiedAt; // 수정일


  public void updateInstaCount(Long instaCount) {
    this.instaCount = instaCount;
  }

  public static PostInstaCount createPostInstaCount() {

    return PostInstaCount.builder().instaCount(0L).build();
  }

  @Builder
  public PostInstaCount(Long instaCount) {
    this.instaCount = 0L;
  }

  public void mappingPost(Post post) {
    this.post = post;
  }
}
