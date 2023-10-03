package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;


@Component
@RequiredArgsConstructor
public class SchedulePostPopularityUsecase {

  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;

  @Transactional
  @Scheduled(initialDelay = 600000,fixedDelay = 600000)
  public void execute() {
    postRepository.findAll().stream().forEach(post -> post.updatePopularity(post.getPostLike().getLikeCount(),post.measurePostAge()));
  }

}
