package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.member.application.FindMemberService;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.point.application.GetPointUsecase;
import com.kakaotech.team14backend.point.domain.GetPointPolicy;
import com.kakaotech.team14backend.post.application.command.FindLikeStatusCommand;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.domain.PostLike;
import com.kakaotech.team14backend.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.post.dto.SetPostLikeResponseDTO;
import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import com.kakaotech.team14backend.post.infrastructure.PostLikeRepository;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPostLikeCommand {

  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final FindMemberService findMemberService;
  private final GetPointUsecase getPointUsecase;
  private final FindLikeStatusCommand findLikeStatusCommand;

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

    boolean isLiked = findLikeStatusCommand.execute(member.getMemberId(), post.getPostId());
    if (isLiked) {
      return PostLike.createPostLike(member, post, false);
    } else {
      getPointUsecase.execute(member, GetPointPolicy.GET_20_WHEN_LIKE_UP);
      return PostLike.createPostLike(member, post, true);
    }

  }

}
