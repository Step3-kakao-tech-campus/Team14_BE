package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdatePostPopularity {

  private final PostRepository postRepository;

  @Transactional
  public void execute() {
    List<Post> posts = postRepository.findAll();
    for (Post post : posts) {
      post.updatePopularity(Instant.now());
      postRepository.save(post);
    }
  }
}
