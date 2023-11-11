package com.kakaotech.team14backend.point.infrastructure;

import com.kakaotech.team14backend.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {

  @Query("select p from point p where p.memberId = ?1")
  Point findByMemberId(Long memberId);
}
