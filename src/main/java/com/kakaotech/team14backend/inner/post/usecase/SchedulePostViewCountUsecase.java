package com.kakaotech.team14backend.inner.post.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class SchedulePostViewCountUsecase {

  private final UpdatePostViewCountUsecase updatePostViewCountUsecase;

  @Scheduled(initialDelayString = "${schedules.initialDelay}",fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    updatePostViewCountUsecase.execute();
  }


}
