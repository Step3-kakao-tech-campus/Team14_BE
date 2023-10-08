package com.kakaotech.team14backend.inner.post.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.member.model.Member;
import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Post {

  // Primary Key
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId; // 게시글 ID


  @ManyToOne
  @JoinColumn(name = "memberId")
  private Member member; // 유저 아이디


  @OneToOne
  @JoinColumn(name = "imageId")
  private Image image; // 사진 ID

  @Column(nullable = false, length = 50)
  private String nickname; // 닉네임

  @Column(nullable = false)
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  private Boolean published; // 공개 여부

  @Column(nullable = true, length = 30)
  private String hashtag; // 해시태그

  @Column(nullable = true, length = 20)
  private String university; // 대학교

  // Statistics
  @Column(nullable = true)
  private Long viewCount = 0L; // 조회수

  @Column(nullable = false)
  private Long popularity; // 인기도 값

  @Column(nullable = false)
  private Integer reportCount = 0; // 제재 횟수

  public void mappingMember(Member member) {
    this.member = member;
  }

  public void mappingImage(Image image) {
    this.image = image;
  }

  public static Post createPost(Member member, Image image, String nickname, Boolean published,
      String hashtag, String university, Long viewCount, Long popularity, Integer reportCount) {

    Post post = Post.builder().nickname(nickname).published(published).hashtag(hashtag)
        .university(university).viewCount(viewCount).popularity(popularity).reportCount(reportCount)
        .build();
    post.mappingMember(member);
    post.mappingImage(image);
    return post;
  }


  @Builder
  public Post(String boothName, String nickname, Boolean published, String hashtag,
      String university, Long viewCount, Long popularity, Integer reportCount) {
    this.nickname = nickname;
    this.createdAt = LocalDateTime.now();
    this.published = published;
    this.hashtag = hashtag;
    this.university = university;
    this.viewCount = viewCount;
    this.popularity = popularity;
    this.reportCount = reportCount;
  }
}
