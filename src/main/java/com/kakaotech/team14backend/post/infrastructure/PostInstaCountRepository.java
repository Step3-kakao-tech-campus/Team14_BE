package com.kakaotech.team14backend.post.infrastructure;

import com.kakaotech.team14backend.post.domain.PostInstaCount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostInstaCountRepository extends JpaRepository<PostInstaCount, Long> {

  @Query("select pic from post_insta_count pic where pic.member.memberId = :memberId")
  List<PostInstaCount> findByMemberId(Long memberId);

  @Query("select pic from post_insta_count pic where pic.post.postId = :postId and pic.member.memberId = :memberId")
  PostInstaCount findByPostAndMember(Long postId, Long memberId);
}
