package com.kakaotech.team14backend.outer.post.schedule;

import com.kakaotech.team14backend.inner.post.usecase.UpdatePostPopularityUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SchedulePostPopularity {

  private final UpdatePostPopularityUsecase updatePostPopularityUsecase;

  @Scheduled(initialDelayString = "${schedules.initialDelay}", fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    updatePostPopularityUsecase.execute();
  }

}
