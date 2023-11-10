package com.kakaotech.team14backend.oauth2.application.command.login;


import com.kakaotech.team14backend.member.application.command.CreateMember;
import com.kakaotech.team14backend.member.application.usecase.MakeUserActive;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.member.domain.Status;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.oauth2.domain.PrincipalDetails;
import com.kakaotech.team14backend.oauth2.dto.KakaoProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateKakaoUser {

  private final MakeUserActive memberService;
  private final MemberRepository memberRepository;
  private final CreateMember createMemberUsecase;

  @Transactional
  public void execute(KakaoProfileDTO kakaoProfileDTO) {
    String kakaoId = kakaoProfileDTO.getId();
    String userName = kakaoProfileDTO.getProperties().getNickname();
    String profileImage = kakaoProfileDTO.getProperties().getProfileImage();
    Member memberEntity = memberRepository.findByKakaoId(kakaoId);

    if (memberEntity == null) {
      memberEntity = createMemberUsecase.execute(
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
      memberService.execute(memberEntity.getMemberId());
    }

    PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);

    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
        principalDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
