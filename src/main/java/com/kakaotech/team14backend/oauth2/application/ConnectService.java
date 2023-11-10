package com.kakaotech.team14backend.oauth2.application;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
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
