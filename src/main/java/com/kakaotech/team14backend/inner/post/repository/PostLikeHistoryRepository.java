package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeHistoryRepository extends JpaRepository<PostLike, Long> {

}
