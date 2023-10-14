package com.kakaotech.team14backend.inner.point.usecase;

import com.kakaotech.team14backend.exception.Exception500;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.point.model.Point;
import com.kakaotech.team14backend.inner.point.model.PointPolicy;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class GetPointUsecase {

  private final PointRepository pointRepository;

  @Transactional
  public void execute(Member member, PointPolicy pointPolicy) {
    Point point = pointRepository.findById(member.getMemberId()).orElseThrow(() -> new Exception500("NOT FOUND USER"));
    point.getUserPoint(member,pointPolicy.getPoint());
  }
}
