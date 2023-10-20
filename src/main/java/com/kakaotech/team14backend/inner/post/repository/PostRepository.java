package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByPostIdGreaterThan(Long lastPostId, Pageable pageable);

  @Query("SELECT new com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO(p.postId,i.imageUri,p.hashtag,pl.likeCount,p.popularity,p.nickname) " +
      "FROM Post p " +
      "JOIN p.image i " + // Image에 대한 JOIN
      "JOIN p.postLikeCount pl " + // PostLike에 대한 JOIN
      "ORDER BY p.popularity DESC")
  List<GetIncompletePopularPostDTO> findTop300ByOrderByPopularityDesc(Pageable pageable);

  @Query("SELECT p FROM Post p WHERE p.member.memberId = :memberId")
  List<Post> findByMemberId(Long memberId, Pageable pageable);

  @Query("SELECT p FROM Post p WHERE p.member.memberId = :memberId AND p.postId > :lastPostId")
  List<Post> findByMemberIdAndPostIdGreaterThan(Long memberId, Long lastPostId, Pageable pageable);
}
