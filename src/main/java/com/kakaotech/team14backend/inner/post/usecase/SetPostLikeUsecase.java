package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.exception.PostNotFoundException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.service.FindMemberService;
import com.kakaotech.team14backend.inner.point.model.GetPointPolicy;
import com.kakaotech.team14backend.inner.point.usecase.GetPointUsecase;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.outer.post.service.FindLikeStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPostLikeUsecase {

  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final FindMemberService findMemberService;
  private final GetPointUsecase getPointUsecase;
  private final FindLikeStatusService findLikeStatusService;

  public SetPostLikeResponseDTO execute(SetPostLikeDTO setPostLikeDTO) {
    Long postId = setPostLikeDTO.postId();
    Long memberId = setPostLikeDTO.memberId();

    Member member = findMemberService.execute(memberId);

    Post post = postRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);
    boolean isLiked = toggleLike(member, post);
    return new SetPostLikeResponseDTO(isLiked);
  }


  private boolean toggleLike(Member member, Post post) {
    final var newPostLike = newPostLike(member, post);
    postLikeRepository.save(newPostLike);
    return newPostLike.isLiked();
  }

  private PostLike newPostLike(final Member member, final Post post) {

    boolean isLiked = findLikeStatusService.execute(member.getMemberId(), post.getPostId());
    if (isLiked) {
      return PostLike.createPostLike(member, post, false);
    } else {
      getPointUsecase.execute(member, GetPointPolicy.GET_20_WHEN_LIKE_UP);
      return PostLike.createPostLike(member, post, true);
    }

  }

}
