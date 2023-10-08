package com.kakaotech.team14backend.outer.post.schedule;

import com.kakaotech.team14backend.inner.post.usecase.UpdatePostViewCountUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class SchedulePostViewCount {

  private final UpdatePostViewCountUsecase updatePostViewCountUsecase;

  @Scheduled(initialDelayString = "${schedules.initialDelay}",fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    updatePostViewCountUsecase.execute();
  }


}
