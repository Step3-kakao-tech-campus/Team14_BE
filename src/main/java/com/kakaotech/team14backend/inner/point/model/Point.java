package com.kakaotech.team14backend.inner.point.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.exception.Exception500;
import com.kakaotech.team14backend.inner.member.model.Member;
import java.time.Instant;
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

@Entity(name = "point")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Point {


  @Id
  private Long memberId;

  @MapsId  // Post의 PK를 PostLike의 PK로 사용
  @OneToOne
  @JoinColumn(name = "memberId")
  private Member member;

  @Column(nullable = false)
  private Long nowPoint;

  @Column(nullable = false)
  @CreationTimestamp
  private Instant createdAt; // 생성일

  @Column(nullable = false)
  @UpdateTimestamp
  private Instant updatedAt; // 생성일

  @Builder
  public Point(Member member, Long nowPoint) {
    this.member = member;
    this.nowPoint = nowPoint;
  }

  /*
    도메인 로직을 엔터티 클래스에 위치시키는 것은 도메인 주도 설계(DDD: Domain-Driven Design)의 전략
  */

  public static Point createUserPoint(Member member, Long point){
    return Point.builder()
        .member(member)
        .nowPoint(point)
        .build();
  }

  public void useUserPoint(Long usePoint){
    if(this.nowPoint - usePoint < 0){
      throw new Exception500("보유 폭죽이 부족합니다.");
    }
    this.nowPoint -= usePoint;
  }

  public void getUserPoint(Long usePoint){
    this.nowPoint += usePoint;
  }


}
