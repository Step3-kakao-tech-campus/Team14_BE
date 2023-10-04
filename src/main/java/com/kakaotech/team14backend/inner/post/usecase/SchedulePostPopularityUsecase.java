package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@RequiredArgsConstructor
public class SchedulePostPopularityUsecase {

  private final PostRepository postRepository;

  @Transactional
  @Scheduled(initialDelayString = "${schedules.initialDelay}",fixedDelayString = "${schedules.fixedDelay}")
  public void execute() {
    postRepository.findAll().stream().forEach(post -> {
      Long likeCount = post.getPostLike().getLikeCount();
      long postAge = post.measurePostAge();
      post.updatePopularity(likeCount.longValue(), postAge);
    });
  }
}
