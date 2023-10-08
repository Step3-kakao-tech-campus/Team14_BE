package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByPostIdGreaterThan(Long lastPostId, Pageable pageable);
}
