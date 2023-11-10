package com.kakaotech.team14backend.point.application;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.exception.MemberNotFoundException;
import com.kakaotech.team14backend.point.domain.GetPointPolicy;
import com.kakaotech.team14backend.point.domain.Point;
import com.kakaotech.team14backend.point.infrastructure.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GetPointUsecase {

  private final PointRepository pointRepository;

  @Transactional
  public void execute(Member member, GetPointPolicy getPointPolicy) {
    Point point = pointRepository.findById(member.getMemberId())
        .orElseThrow(() -> new MemberNotFoundException(
            MessageCode.NOT_REGISTER_MEMBER));
    point.getUserPoint(getPointPolicy.getPoint());
  }
}
