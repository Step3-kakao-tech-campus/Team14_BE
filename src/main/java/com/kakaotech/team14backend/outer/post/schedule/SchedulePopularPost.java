package com.kakaotech.team14backend.outer.post.schedule;

import com.kakaotech.team14backend.inner.post.usecase.SaveTemporaryPopularPostListUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulePopularPost {

  private final SaveTemporaryPopularPostListUsecase saveTemporaryPopularPostListUsecase;
  @Scheduled(initialDelayString = "${schedules.popularPost.initialDelay}",fixedDelayString = "${schedules.popularPost.fixedDelay}")
  public void execute() {
    saveTemporaryPopularPostListUsecase.execute();
  }

}
