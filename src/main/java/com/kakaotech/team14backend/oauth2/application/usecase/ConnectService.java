package com.kakaotech.team14backend.oauth2.application.usecase;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.oauth2.application.command.ChangeMemberRole;
import com.kakaotech.team14backend.oauth2.application.command.ConnectSuccessHandler;
import com.kakaotech.team14backend.oauth2.application.command.GetInstagramAccessToken;
import com.kakaotech.team14backend.oauth2.application.command.GetInstagramUserInfo;
import com.kakaotech.team14backend.oauth2.dto.GetInstagramCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class ConnectService {
  private final MemberRepository memberRepository;
  private final GetInstagramAccessToken getInstagramAccessToken;
  private final GetInstagramUserInfo getInstagramUserInfo;
  private final ChangeMemberRole changeMemberRole;
  private final ConnectSuccessHandler connectSuccessHandler;

  public void connectInstagram(String kakaoId, GetInstagramCodeDTO instagramCodeDTO, HttpServletResponse response){
    Member member = memberRepository.findByKakaoId(kakaoId);
    String instagramAccessToken =  getInstagramAccessToken.getAccessToken(instagramCodeDTO.getCode());
    String instagramId = getInstagramUserInfo.execute(instagramAccessToken);
    changeMemberRole.execute(member,instagramId);
    connectSuccessHandler.execute(response,member);
  }
}
