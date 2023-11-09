package com.kakaotech.team14backend.inner.member.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity(name = "member")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member {
  @Id
  private Long memberId;

  @OneToMany(mappedBy = "member")
  private List<Post> posts = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<PostInstaCount> postInstas = new ArrayList<>();

  @Column(nullable = false, length = 50)
  private String userName; // 유저 이름

  @Column(nullable = false, length = 50, unique = true)
  private String kakaoId; // 카카오 아이디

  @Column(nullable = false, length = 50)
  private String instaId; // 인스타그램 아이디

  @Column(nullable = false)
  private String profileImageUrl;

  @Column(nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private Role role;


  @Column(nullable = false)
  private Long totalLike; // 보유 좋아요 수

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt; // 수정일

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private Status userStatus; // 제재, 탈퇴, 정상 등등


  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private List<PostLike> postLikeHistories = new ArrayList<>();

  public void updateInstagram(Role newRole, String instaId) {
    // 선택적: 유효성 검사를 수행하려면 여기에 코드 추가
    this.instaId = instaId;
    this.role = newRole;
  }

  @Builder
  public Member(Long memberId, String userName, String kakaoId, String instaId,
                String profileImageUrl, Role role, Long totalLike, Status userStatus) {
    this.memberId = memberId;
    this.userName = userName;
    this.kakaoId = kakaoId;
    this.instaId = instaId;
    this.profileImageUrl = profileImageUrl;
    this.role = role;
    this.totalLike = totalLike;
    this.userStatus = userStatus;
  }


  @Override
  public String toString() {
    return "Member{" +
        "memberId=" + memberId +
        ", posts=" + posts +
        ", userName='" + userName + '\'' +
        ", kakaoId='" + kakaoId + '\'' +
        ", instaId='" + instaId + '\'' +
        ", profileImageUrl='" + profileImageUrl + '\'' +
        ", role=" + role +
        '}';
  }

  public void makeUserInactive() {
    this.userStatus = Status.STATUS_INACTIVE;
  }

  public void makeUserActive() {
    this.userStatus = Status.STATUS_ACTIVE;
  }

  public Member(String userName, String kakaoId, String instaId, Role role, Long totalLike, Status userStatus) {
    this.memberId = Long.valueOf(kakaoId);
    this.userName = userName;
    this.kakaoId = kakaoId;
    this.instaId = instaId;
    this.profileImageUrl = "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg";
    this.role = role;
    this.totalLike = totalLike;
    this.userStatus = userStatus;
  }
}
