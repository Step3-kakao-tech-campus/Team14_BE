package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.post.application.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPostUsecase {

  private final PostRepository postRepository;

  public GetPostResponseDTO execute(GetPostDTO getPostDTO) {
    Post post = postRepository.findById(getPostDTO.postId()).orElseThrow(() -> new RuntimeException("Post not found"));
    GetPostResponseDTO getPostResponseDTO = PostMapper.from(post);
    return getPostResponseDTO;
  }

}
