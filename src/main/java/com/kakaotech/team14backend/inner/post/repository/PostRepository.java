package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.Post;
import java.util.List;

import com.kakaotech.team14backend.outer.post.dto.GetPopularPostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByPostIdGreaterThan(Long lastPostId, Pageable pageable);

  @Query("SELECT new com.kakaotech.team14backend.outer.post.dto.GetPopularPostDTO(p.postId,i.imageUri,p.hashtag,pl.likeCount,p.postPoint,p.popularity,p.nickname) " +
      "FROM Post p " +
      "JOIN p.image i " + // Image에 대한 JOIN
      "JOIN p.postLikeCount pl " + // PostLike에 대한 JOIN
      "ORDER BY p.popularity DESC")
  List<GetPopularPostDTO> findTop300ByOrderByPopularityDesc(Pageable pageable);

}
