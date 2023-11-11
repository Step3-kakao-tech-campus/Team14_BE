package com.kakaotech.team14backend.point.application.usecase;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.exception.MemberNotFoundException;
import com.kakaotech.team14backend.point.domain.Point;
import com.kakaotech.team14backend.point.domain.PointHistory;
import com.kakaotech.team14backend.point.domain.TransactionType;
import com.kakaotech.team14backend.point.infrastructure.PointHistoryRepository;
import com.kakaotech.team14backend.point.infrastructure.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class UsePoint {

  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;

  @Transactional
  public void execute(Long sender, Long recieved, Long usePoint) {

    Point point = pointRepository.findById(sender)
        .orElseThrow(() -> new MemberNotFoundException(MessageCode.NOT_REGISTER_MEMBER));
    pointHistoryRepository.save(PointHistory.createPointTransferRecord(recieved, sender, usePoint,
        TransactionType.TRANSFER));
    point.useUserPoint(usePoint);

  }

}
