package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.outer.post.service.FindLikeStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindMyPostUsecase {

  private final PostRepository postRepository;
  private final FindLikeStatusService findLikeStatusService;
  public GetMyPostResponseDTO execute(Long memberId, Long postId) {
    Post post = postRepository.findByPostIdAndMemberId(memberId, postId);
    boolean isLiked = findLikeStatusService.execute(memberId, postId);
    GetMyPostResponseDTO getPostResponseDTO = PostMapper.from(post, isLiked,memberId);
    return getPostResponseDTO;
  }
}
