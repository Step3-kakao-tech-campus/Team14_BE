package com.kakaotech.team14backend.inner.post.model;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.member.model.Member;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter(PACKAGE)
public class Post {

  // Primary Key
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId; // 게시글 ID

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId")
  private Member member; // 유저 아이디

  @OneToOne
  @JoinColumn(name = "imageId")
  private Image image; // 사진 ID

  @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
  private PostLikeCount postLikeCount;

  @Column(nullable = false, length = 50)
  private String nickname; // 닉네임

  @Column(nullable = false)
  private Instant createdAt; // 생성일

  @Column(nullable = false)
  private Boolean published; // 공개 여부

  @Column(nullable = true, length = 30)
  private String hashtag; // 해시태그


  // Statistics
  @Column(nullable = true)
  private Long viewCount; // 조회수

  @Column(nullable = true)
  private Long popularity; // 인기도 값

  @Column(nullable = false)
  private Integer reportCount; // 제재 횟수

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
  private List<PostLike> postLikeHistories;

  public void mappingMember(Member member) {
    this.member = member;
  }

  public void mappingImage(Image image) {
    this.image = image;
  }

  public void mappingPostLikeCount(PostLikeCount postLikeCount) {
    postLikeCount.mappingPost(this);
    this.postLikeCount = postLikeCount;
  }

  public static Post createPost(Member member, Image image, PostLikeCount postLikeCount,
      String nickname,
      Boolean published,
      String hashtag) {

    Post post = Post.builder()
        .nickname(nickname)
        .published(published)
        .hashtag(hashtag)
        .viewCount(0L)
        .popularity(0L)
        .reportCount(0)
        .build();
    post.mappingPostLikeCount(postLikeCount);
    post.mappingMember(member);
    post.mappingImage(image);
    return post;
  }


  @Builder
  public Post(String nickname, Boolean published, String hashtag,
      String university, Long viewCount, Long popularity, Integer reportCount) {
    this.nickname = nickname;
    this.createdAt = Instant.now();
    this.published = published;
    this.hashtag = hashtag;
    this.viewCount = viewCount;
    this.popularity = popularity;
    this.reportCount = reportCount;
  }

  public void updateViewCount(Long viewCount) {
    this.viewCount = viewCount;
  }

  public void updatePopularity(final Instant now) {
    this.popularity = calculatePopularity(now);
  }

  long calculatePopularity(final Instant now) {
    long postAge = calculatePostAge(now);
    Long likeCount = postLikeCount.getLikeCount();
    return (likeCount.longValue() + viewCount) / postAge;
  }

  long calculatePostAge(final Instant now) {
    Duration between = Duration.between(createdAt, now);
    long minutes = between.toMinutes();
    return minutes / 5 > 1 ? minutes / 5 : 1;
  }


}
