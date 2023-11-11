package com.kakaotech.team14backend.point.application.command;


import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.point.domain.GetPointPolicy;
import com.kakaotech.team14backend.point.domain.Point;
import com.kakaotech.team14backend.point.infrastructure.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePoint {

  private final PointRepository pointRepository;

  @Transactional
  public void execute(Member member) {
    Point userPoint = Point.createUserPoint(member, GetPointPolicy.GET_100_WHEN_SIGN_UP.getPoint());
    pointRepository.save(userPoint);
  }

}
