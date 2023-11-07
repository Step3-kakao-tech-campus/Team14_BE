package com.kakaotech.team14backend.inner.point.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.NotEnoughPointException;
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

  @MapsId
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

  public static Point createUserPoint(Member member, Long point){
    return Point.builder()
        .member(member)
        .nowPoint(point)
        .build();
  }

  public void useUserPoint(Long usePoint){
    if(this.nowPoint - usePoint < 0){
      throw new NotEnoughPointException(MessageCode.NOT_ENOUGH_POINT);
    }
    this.nowPoint -= usePoint;
  }

  public void getUserPoint(Long usePoint){
    this.nowPoint += usePoint;
  }


}
