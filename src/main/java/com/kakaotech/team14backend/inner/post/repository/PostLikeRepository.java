package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  PostLike findByMemberAndPost(Member member, Post post);
}
