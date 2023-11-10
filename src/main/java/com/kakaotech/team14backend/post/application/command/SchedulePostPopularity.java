package com.kakaotech.team14backend.post.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SchedulePostPopularity {

  private final UpdatePostPopularity updatePostPopularityUsecase;

  @Scheduled(initialDelayString = "${schedules.initialDelay}", fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    updatePostPopularityUsecase.execute();
  }

}
