package com.kakaotech.team14backend.post.infrastructure;

import com.kakaotech.team14backend.post.domain.PostLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeCountRepository extends JpaRepository<PostLikeCount, Long> {

  PostLikeCount findByPostId(Long postId);
}
