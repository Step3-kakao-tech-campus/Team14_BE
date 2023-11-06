package com.kakaotech.team14backend.inner.point.usecase;

import com.kakaotech.team14backend.exception.Exception500;
import com.kakaotech.team14backend.inner.point.model.Point;
import com.kakaotech.team14backend.inner.point.model.PointHistory;
import com.kakaotech.team14backend.inner.point.model.TransactionType;
import com.kakaotech.team14backend.inner.point.repository.PointHistoryRepository;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
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
        .orElseThrow(() -> new Exception500("NOT FOUND USER"));

    pointHistoryRepository.save(PointHistory.createPointTransferRecord(recieved, sender, usePoint,
        TransactionType.TRANSFER));
    point.useUserPoint(usePoint);

  }

}
