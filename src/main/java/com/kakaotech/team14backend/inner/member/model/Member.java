package com.kakaotech.team14backend.inner.member.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.post.model.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access=PROTECTED)
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @OneToMany(mappedBy = "member")
  private List<Post> posts = new ArrayList<>();

  @Column(nullable = false, length = 50)
  private String userName; // 유저 이름

  @Column(nullable = false, length = 50)
  private String kakaoId; // 카카오 아이디

  @Column(nullable = false, length = 50)
  private String instaId; // 인스타그램 아이디

  @Column(nullable = false)
  private Long totalLike = 0L; // 보유 좋아요 수

  @Column(nullable = false)
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  private LocalDateTime updatedAt; // 수정일

  @Column(nullable = false,length = 20)
  private String userStatus; // 제재, 탈퇴, 정상 등등

  @Builder
  public Member(String userName,String kakaoId) {
    this.userName = userName;
    this.kakaoId = kakaoId;
  }

}
