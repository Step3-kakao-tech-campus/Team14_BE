package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  PostLike findByMemberAndPost(Member member, Post post);

  @Query(
      value = "SELECT * FROM post_like pl WHERE pl.member_id = :memberId AND pl.post_id = :postId ORDER BY pl.created_at DESC LIMIT 1",
      nativeQuery = true
  )
  Optional<PostLike> findFirstByMemberAndPostOrderByCreatedAtDesc(Long memberId, Long postId);
}
