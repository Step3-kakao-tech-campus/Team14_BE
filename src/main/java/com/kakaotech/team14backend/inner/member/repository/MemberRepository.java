package com.kakaotech.team14backend.inner.member.repository;

import com.kakaotech.team14backend.inner.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  public Member findByKakaoId(String kakaoId);

}
