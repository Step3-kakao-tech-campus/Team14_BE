package com.kakaotech.team14backend.inner.post.model;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED) // 기본 생성자의 접근 권한을 protected로 제한
public class PostLikeCount {

  @Id
  private Long postId;

  @MapsId  // Post의 PK를 PostLike의 PK로 사용
  @OneToOne
  @JoinColumn(name = "postId")
  private Post post;

  @Column(nullable = false)
  private Long likeCount = 0L;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime modifiedAt; // 수정일

  public static PostLikeCount createPostLikeCount() {
    return new PostLikeCount();
  }

  public void updateLikeCount(Long likeCount) {
    this.likeCount = likeCount;
  }

  public void mappingPost(Post post) {
    this.post = post;
  }
}
