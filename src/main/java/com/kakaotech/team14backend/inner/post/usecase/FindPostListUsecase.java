package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindPostListUsecase {

  private final PostRepository postRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  public List<Post> excute(Long lastPostId, int size) {
    Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "postId"));
    return postRepository.findByPostIdGreaterThan(lastPostId, pageable);
  }
}
