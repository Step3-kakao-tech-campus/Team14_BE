package com.kakaotech.team14backend.point.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity(name = "point_history")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PointHistory {

  @Id
  private Long recieverId;

  @Column()
  private Long senderId;

  @Column()
  private Long transferPoint;

  @Column(nullable = false)
  private TransactionType transactionType;  // 거래 유형

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일

  @Builder
  public PointHistory(Long recieverId, Long senderId, Long transferPoint,
      TransactionType transactionType) {
    this.recieverId = recieverId;
    this.senderId = senderId;
    this.transferPoint = transferPoint;
    this.transactionType = transactionType;
    this.createdAt = LocalDateTime.now();
  }

  public static PointHistory createPointTransferRecord(Long recieverId, Long senderId,
      Long transferPoint,
      TransactionType transactionType) {
    return PointHistory.builder()
        .recieverId(recieverId)
        .senderId(senderId)
        .transferPoint(transferPoint)
        .transactionType(transactionType)
        .build();
  }


}
