package com.kakaotech.team14backend.inner.point.usecase;


import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.point.model.Point;
import com.kakaotech.team14backend.inner.point.model.PointPolicy;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePointUsecase {

  private final PointRepository pointRepository;

  @Transactional
  public void execute(Member member) {
    Point userPoint = Point.createUserPoint(member, PointPolicy.GET_100_WHEN_SIGN_UP.getPoint());
    pointRepository.save(userPoint);
  }

  // create : 처음 유저가 포인트를 다룰 때, 회원가입 밖에 없긴함
  // give, use
  // 우려 사항 :
}
