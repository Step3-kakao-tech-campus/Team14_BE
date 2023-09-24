package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
