package com.kakaotech.team14backend.oauth2.application;


import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.member.domain.Status;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.oauth2.dto.KakaoProfileDTO;
import com.kakaotech.team14backend.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateKakaoUser {
  private final MemberService memberService;
  private final MemberRepository memberRepository;
  @Transactional
  public void execute(KakaoProfileDTO kakaoProfileDTO) {
    String kakaoId = kakaoProfileDTO.getId();
    String userName = kakaoProfileDTO.getProperties().getNickname();
    String profileImage = kakaoProfileDTO.getProperties().getProfileImage();
    Member memberEntity = memberRepository.findByKakaoId(kakaoId);

    if (memberEntity == null) {
      memberEntity = memberService.createMember(
          userName,
          kakaoId,
          "none",
          profileImage,
          Role.ROLE_BEGINNER,
          0L,
          Status.STATUS_ACTIVE);
      memberRepository.save(memberEntity);
    }
    if (memberEntity.getUserStatus().equals(Status.STATUS_INACTIVE)) {
      memberService.makeUserActive(memberEntity.getMemberId());
    }

    PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);

    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
