package com.kakaotech.team14backend.member.infrastructure;

import com.kakaotech.team14backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  public Member findByKakaoId(String kakaoId);
}
