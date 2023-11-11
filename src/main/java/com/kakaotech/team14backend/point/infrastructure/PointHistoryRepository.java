package com.kakaotech.team14backend.point.infrastructure;

import com.kakaotech.team14backend.point.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

}
