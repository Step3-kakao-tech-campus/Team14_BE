package com.kakaotech.team14backend.inner.point.repository;

import com.kakaotech.team14backend.inner.point.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

  @Query("select sum(ph.transferPoint) from point_history ph where ph.recieverId = :receivedId")
  Long sumReceivedFireworksByReceivedId(Long receivedId);

}
