package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.inner.post.repository.PostLikeRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeResponseDTO;
import java.util.Optional;
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

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));
    boolean isLiked = toggleLike(member, post);

    return new SetPostLikeResponseDTO(isLiked);
  }

  // todo : like를 계속 추가하는데 상태를 변경 할 것이다, 근데 이 경우에 처음 좋아요를 하면 Full Scan을 하는 이슈가 생길거 같은데
  // 이를 해결하기 위해 인덱스를 사용하는게 어떤가
  private boolean toggleLike(Member member, Post post) {
    Optional<PostLike> postLike = postLikeRepository.findFirstByMemberAndPostOrderByCreatedAtDesc(
        member, post);
    PostLike newPostLike;
    if (postLike.isEmpty() || !postLike.get().isLiked()) {
      newPostLike = PostLike.createPostLike(member, post, true);

    } else {
      newPostLike = PostLike.createPostLike(member, post, false);
    }
    postLikeRepository.save(newPostLike);
    return newPostLike.isLiked();
  }

}
