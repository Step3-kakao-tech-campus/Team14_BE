package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdatePostPopularityUsecase {

  private final PostRepository postRepository;

  @Transactional
  public void execute() {
    List<Post> posts = postRepository.findAll();
    for (Post post : posts) {
      updatePopularity(post);
      postRepository.save(post);
    }
  }

  private void updatePopularity(final Post post) {
    Long likeCount = post.getPostLikeCount().getLikeCount();
    long postAge = post.measurePostAge();
    post.updatePopularity(likeCount.longValue(), postAge);
  }
}
