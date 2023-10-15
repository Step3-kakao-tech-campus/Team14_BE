package com.kakaotech.team14backend.outer.member.service;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.point.usecase.CreatePointUsecase;
import com.kakaotech.team14backend.outer.member.dto.MyPageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreatePointUsecase createPointUsecase;

  //private final CreateMemberUsecase createMemberUsecase;
  private final MemberRepository memberRepository; // 추후 Usecase 로 변경

  public static Member createMember(String userName, String kakaoId, String instaId, Role role,
      Long totalLike, Status userStatus) {
    return Member.builder()
        .userName(userName)
        .kakaoId(kakaoId)
        .instaId(instaId)
        .role(role)
        .totalLike(totalLike)
        .userStatus(userStatus)
        .build();
  }

//  public Member createMember(){
//    createMemberUsecase.execute();
//    createPointUsecase.execute(member, PointPolicy.GET_100_WHEN_SIGN_UP);
//  }

// todo : createMemberusease를 이용해 주세요! ++ 주석 참고

  public MyPageResponseDTO getMyPageInfo(String kakaoId) {
    Member member = memberRepository.findByKakaoId(kakaoId);

    return MyPageResponseDTO.builder()
        .memberId(member.getMemberId())
        .userName(member.getUserName())
        .kakaoId(member.getKakaoId())
        .build();
  }
}


