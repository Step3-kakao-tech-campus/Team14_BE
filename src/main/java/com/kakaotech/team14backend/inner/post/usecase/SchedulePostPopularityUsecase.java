package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class SchedulePostPopularityUsecase {

  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;

  @Transactional
  @Scheduled(fixedDelay = 600000)
  public void execute() {
    //todo Post엔티티와 PostLike엔티티의 연관관계 수정에 대한 고민 필요, 고민 완료 후 리팩토링
    postRepository.findAll().stream().forEach(post -> post.updatePopularity(postLikeRepository.findById(post.getPostId()).orElseThrow(() -> new RuntimeException("Post not found")).getLikeCount(),post.measurePostAge()));
  }

}
