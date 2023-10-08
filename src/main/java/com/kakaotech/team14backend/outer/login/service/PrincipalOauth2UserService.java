package com.kakaotech.team14backend.outer.login.service;


import com.kakaotech.team14backend.auth.PrincipalDetails;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.model.Role;
import com.kakaotech.team14backend.inner.member.model.Status;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


  private final MemberRepository memberRepository;

  //후처리 되는 함수
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2User oAuth2User = super.loadUser(userRequest);

    String kakaoId = Long.toString(oAuth2User.getAttribute("id"));
    Map<String, String> map = oAuth2User.getAttribute("properties");
    String userName = map.get("nickname");

    Member memberEntity = memberRepository.findByKakaoId(kakaoId);

    if (memberEntity == null) {
      memberEntity = Member.createMember(userName, kakaoId,"none",Role.ROLE_BEGINNER,0L,Status.STATUS_ACTIVE);
      memberRepository.save(memberEntity);
    }

    return new PrincipalDetails(memberEntity, oAuth2User.getAttributes());
  }
}
