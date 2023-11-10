package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.post.domain.PostLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {


  @Query("select pl from post_like pl where pl.member.memberId = ?1 and pl.post.postId = ?2 order by pl.createdAt desc")
  List<PostLike> findFirstByMemberAndPostOrderByCreatedAtDesc(Long memberId, Long postId);
}
