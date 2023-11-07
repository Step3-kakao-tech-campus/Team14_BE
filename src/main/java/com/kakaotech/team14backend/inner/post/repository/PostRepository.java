package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> {


  @Query(
      "SELECT new com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO(p.postId,i.imageUri,p.hashtag,pl.likeCount,p.popularity,p.nickname) "
          +
          "FROM post p " +
          "JOIN p.image i " + // Image에 대한 JOIN
          "JOIN p.postLikeCount pl " + // PostLike에 대한 JOIN
          "ORDER BY p.popularity DESC")
  List<GetIncompletePopularPostDTO> findTop300ByOrderByPopularityDesc(Pageable pageable);


  @Query("SELECT p FROM post p WHERE p.postId > :lastPostId ORDER BY p.postId asc ")
  List<Post> findNextPosts(@Param("lastPostId") Long lastPostId, Pageable pageable);

  @Query("SELECT p FROM post p WHERE p.member.memberId = :memberId AND p.postId = :postId")
  Post findByPostIdAndMemberId(Long memberId, Long postId);

  @Query("SELECT COALESCE(SUM(p.viewCount), 0) FROM post p WHERE p.member.memberId = :memberId")
  Long sumViewCountByMemberId(@Param("memberId") Long memberId);


  @Query("SELECT p FROM post p WHERE p.member.memberId = :memberId AND p.postId < :lastPostId ORDER BY p.postId DESC")
  List<Post> findByMemberIdAndPostIdLessThanOrderByPostIdDesc(Long memberId, Long lastPostId,
      Pageable pageable);

  @Query("SELECT p FROM post p WHERE p.member.memberId = :memberId ORDER BY p.postId DESC")
  List<Post> findByMemberIdOrderByPostIdDesc(Long memberId, Pageable pageable);
}
