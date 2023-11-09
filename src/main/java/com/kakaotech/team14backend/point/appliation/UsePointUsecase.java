package com.kakaotech.team14backend.point.appliation;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.MemberNotFoundException;
import com.kakaotech.team14backend.point.domain.Point;
import com.kakaotech.team14backend.point.domain.PointHistory;
import com.kakaotech.team14backend.point.domain.TransactionType;
import com.kakaotech.team14backend.post.infrastructure.PointHistoryRepository;
import com.kakaotech.team14backend.point.infrastructure.PointRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsePointUsecase {

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
