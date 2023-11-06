package com.kakaotech.team14backend.inner.point.repository;

import com.kakaotech.team14backend.inner.point.model.PointHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

  @Query("select ph from point_history ph where ph.recieverId = :receiverId")
  Optional<List<PointHistory>> receivedFireworksByReceivedId(Long receiverId);
}
