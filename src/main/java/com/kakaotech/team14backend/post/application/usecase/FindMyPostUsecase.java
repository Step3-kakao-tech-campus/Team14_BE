package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.application.command.FindLikeStatus;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindMyPostUsecase {

  private final PostRepository postRepository;
  private final FindLikeStatus findLikeStatusCommand;

  public GetMyPostResponseDTO execute(GetPostDTO getPostDTO) {
    Long memberId = getPostDTO.memberId();
    Long postId = getPostDTO.postId();

    Post post = postRepository.findByPostIdAndMemberId(memberId, postId);
    boolean isLiked = findLikeStatusCommand.execute(memberId, postId);
    return PostMapper.from(post, isLiked, memberId);
  }
}
