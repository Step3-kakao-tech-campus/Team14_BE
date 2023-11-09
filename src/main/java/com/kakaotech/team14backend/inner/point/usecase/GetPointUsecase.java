package com.kakaotech.team14backend.inner.point.usecase;

import com.kakaotech.team14backend.common.MessageCode;
import com.kakaotech.team14backend.exception.MemberNotFoundException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.point.domain.GetPointPolicy;
import com.kakaotech.team14backend.point.domain.Point;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
