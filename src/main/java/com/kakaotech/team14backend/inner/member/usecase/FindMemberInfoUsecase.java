package com.kakaotech.team14backend.inner.member.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.outer.member.dto.GetMemberInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Component : 스프링이 관리하는 객체로 등록
@Component
// RequiredArgsConstructor : final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어줌
@RequiredArgsConstructor
public class FindMemberInfoUsecase {

  // MemberRepository 등록 : Member 엔티티를 관리하는 레포지토리
  private final MemberRepository memberRepository;

  //  "user_id":"12345",
  //  "name":"카카오",
  //  "kakao":"kakao@example.com",

  GetMemberInfoDTO execute(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(
        () -> new RuntimeException("Member not found")
    );
    return new GetMemberInfoDTO(member.getMemberId(), member.getUserName(), member.getKakaoId());
  }
}
