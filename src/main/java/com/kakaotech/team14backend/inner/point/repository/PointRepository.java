package com.kakaotech.team14backend.inner.point.repository;

import com.kakaotech.team14backend.inner.point.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {

  @Query("select p from point p where p.memberId = ?1")
  Point findByMemberId(Long memberId);
}
