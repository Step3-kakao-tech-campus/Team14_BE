package com.kakaotech.team14backend.point.infrastructure;

import com.kakaotech.team14backend.point.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

  @Query("select ph from point_history ph where ph.recieverId = :receiverId and ph.transactionType = 0")
  Optional<List<PointHistory>> receivedFireworksByReceivedId(Long receiverId);
}
