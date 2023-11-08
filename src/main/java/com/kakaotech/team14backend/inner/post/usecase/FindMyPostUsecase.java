package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMyPostUsecase {

  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;

  public GetMyPostResponseDTO execute(Long memberId, Long postId) {
    Post post = postRepository.findByPostIdAndMemberId(memberId, postId);
    Optional<PostLike> latestPostLike = postLikeRepository
        .findFirstByMemberAndPostOrderByCreatedAtDesc(memberId, post.getPostId());
    boolean isLiked = latestPostLike.map(PostLike::isLiked).orElse(false);
    System.out.println("FindMyPostUsecase 호출 되었음 = " + memberId + " " + postId + " " + isLiked);
    GetMyPostResponseDTO getPostResponseDTO = PostMapper.from(post, isLiked);
    return getPostResponseDTO;
  }
}
