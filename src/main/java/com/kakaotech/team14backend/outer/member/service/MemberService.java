package com.kakaotech.team14backend.outer.member.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.point.usecase.CreatePointUsecase;
import com.kakaotech.team14backend.outer.member.dto.GetMemberInfoResponseDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreatePointUsecase createPointUsecase;

  //private final CreateMemberUsecase createMemberUsecase;
  private final MemberRepository memberRepository; // 추후 Usecase 로 변경

  public static Member createMember(String userName, String kakaoId, String instaId,
                                    String profileImageUrl, Role role, Long totalLike,
                                    Status userStatus) {
    return Member.builder().userName(userName).kakaoId(kakaoId).instaId(instaId).role(role)
        .profileImageUrl(profileImageUrl).totalLike(totalLike).userStatus(userStatus).build();
  }

//  public Member createMember(){
//    createMemberUsecase.execute();
//    createPointUsecase.execute(member, PointPolicy.GET_100_WHEN_SIGN_UP);
//  }

// todo : createMemberusease를 이용해 주세요! ++ 주석 참고

  public GetMemberInfoResponseDTO getMyPageInfo(Long memberId) {
    Optional<Member> member = memberRepository.findById(memberId);
    if (member.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }
    return new GetMemberInfoResponseDTO(member.get().getMemberId(), member.get().getUserName(),
        member.get().getKakaoId(), member.get().getTotalLike(), member.get().getProfileImageUrl());
  }
}


