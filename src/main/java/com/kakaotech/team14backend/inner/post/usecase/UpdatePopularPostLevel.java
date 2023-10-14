package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UpdatePopularPostLevel {

  private final PostRepository postRepository;

  public void execute(GetPopularPostListResponseDTO getPopularPostListResponseDTO) {
    getPopularPostListResponseDTO.popularPosts().forEach(getPopularPostDTO -> {
      postRepository.findById(getPopularPostDTO.postId()).ifPresent(post -> {
        post.updatePostLevel(getPopularPostDTO.postLevel());
      });
    });
  }
}
