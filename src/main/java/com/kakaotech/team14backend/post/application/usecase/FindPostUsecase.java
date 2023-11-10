package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPostUsecase {

  private final PostRepository postRepository;

  public GetPostResponseDTO execute(GetPostDTO getPostDTO) {
    Post post = postRepository.findById(getPostDTO.postId())
        .orElseThrow(() -> new RuntimeException("Post not found"));
    return PostMapper.from(post);
  }

}
