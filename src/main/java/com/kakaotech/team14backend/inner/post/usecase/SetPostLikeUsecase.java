package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPostLikeUsecase {

  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private static final String POST_LIKE_KEY_PREFIX = "POST_LIKE::";

  public SetPostLikeResponseDTO execute(SetPostLikeDTO setPostLikeDTO) {
    Long postId = setPostLikeDTO.postId();
    Long memberId = setPostLikeDTO.memberId();

//    String key = getRedisKeyForPost(postId);
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));
    boolean isLiked = toggleLike(member, post);

    return new SetPostLikeResponseDTO(isLiked);
  }

  private boolean toggleLike(Member member, Post post) {
    PostLike postLike = postLikeRepository.findByMemberAndPost(member, post);
    if (postLike == null) {
      postLikeRepository.save(PostLike.createPostLike(member, post));
      return true;
    } else {
      postLikeRepository.delete(postLike);
      return false;
    }
  }

}
