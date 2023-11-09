package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import com.kakaotech.team14backend.outer.post.service.FindLikeStatusService;
import java.util.Optional;
import javax.swing.text.StyledEditorKit.BoldAction;
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
    System.out.println("FindMyPostUsecase 호출 되었음 = " + memberId + " " + postId + " " + isLiked);
    GetMyPostResponseDTO getPostResponseDTO = PostMapper.from(post, isLiked,memberId);
    return getPostResponseDTO;
  }
}
