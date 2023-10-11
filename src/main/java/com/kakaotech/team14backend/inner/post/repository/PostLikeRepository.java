package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  PostLike findByMemberAndPost(Member member, Post post);

  @Query("select pl from PostLike pl where pl.member.memberId = :memberId and pl.post.postId = :postId")
  Optional<PostLike> findFirstByMemberAndPostOrderByCreatedAtDesc(Member member, Post post);
}
