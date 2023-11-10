package com.kakaotech.team14backend.post.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulePopularPost {

  private final SavePopularPosts savePopularPosts;
  @Scheduled(initialDelayString = "${schedules.popularPost.initialDelay}",fixedDelayString = "${schedules.popularPost.fixedDelay}")
  public void execute() {
    savePopularPosts.execute();
  }

}
