package com.kakaotech.team14backend.post.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class SchedulePostViewCount {

  private final UpdatePostViewCount updatePostViewCount;

  @Scheduled(initialDelayString = "${schedules.initialDelay}",fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    updatePostViewCount.execute();
  }


}
