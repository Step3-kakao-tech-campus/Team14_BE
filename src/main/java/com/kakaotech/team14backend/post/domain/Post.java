package com.kakaotech.team14backend.post.domain;

import com.kakaotech.team14backend.image.domain.Image;
import com.kakaotech.team14backend.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

@Entity(name = "post")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter(PACKAGE)
public class Post {

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

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<PostInstaCount> postInstaCount = new ArrayList<>();

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
  private List<PostLike> postLikeHistories = new ArrayList<>();

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

  public void mapppingPostInstaCount(PostInstaCount postInstaCount) {
    postInstaCount.mappingPost(this);
    this.postInstaCount.add(postInstaCount);
  }

  public static Post createPost(Member member, Image image, PostLikeCount postLikeCount, PostInstaCount postInstaCount,
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
    post.mapppingPostInstaCount(postInstaCount);
    post.mappingMember(member);
    post.mappingImage(image);
    return post;
  }


  @Builder
  public Post(String nickname, Boolean published, String hashtag,
      Long viewCount, Long popularity, Integer reportCount) {
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
