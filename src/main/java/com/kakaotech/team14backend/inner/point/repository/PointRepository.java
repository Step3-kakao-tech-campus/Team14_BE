package com.kakaotech.team14backend.inner.point.repository;

import com.kakaotech.team14backend.inner.point.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Long> {


}
