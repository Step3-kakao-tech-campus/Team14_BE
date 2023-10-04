package com.kakaotech.team14backend.inner.post.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class SchedulePostPopularityUsecase {

  private final UpdatePostPopularityUsecase updatePostPopularityUsecase;

  @Scheduled(initialDelayString = "${schedules.initialDelay}",fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    postRepository.findAll().stream().forEach(post -> post.updatePopularity(post.getPostLikeCount().getLikeCount(),post.measurePostAge()));
  }

}
