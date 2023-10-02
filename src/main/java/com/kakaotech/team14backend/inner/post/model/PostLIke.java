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
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED) // 기본 생성자의 접근 권한을 protected로 제한
public class PostLIke {

  @Id
  private Long postId;

  @MapsId  // Post의 PK를 PostLike의 PK로 사용
  @OneToOne
  @JoinColumn(name = "postId")
  private Post post;

  @Column(nullable = false)
  private Long likeCount;

  @Column(nullable = false)
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  private LocalDateTime modifiedAt; // 수정일


  @Builder
  public PostLIke(Post post) {
    this.post = post;
    this.likeCount = 0L;
    this.createdAt = LocalDateTime.now();
    this.modifiedAt = LocalDateTime.now();
  }

  public void updateLikeCount(Long likeCount) {
    this.likeCount = likeCount;
  }
}
