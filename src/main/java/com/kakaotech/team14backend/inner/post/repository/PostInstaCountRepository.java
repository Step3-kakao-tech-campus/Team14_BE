package com.kakaotech.team14backend.inner.post.repository;

import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostInstaCountRepository extends JpaRepository<PostInstaCount, Long> {

  @Query("select pic from post_insta_count pic where pic.memberId = :memberId")
  List<PostInstaCount> findByMemberId(Long memberId);
}
