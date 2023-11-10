package com.kakaotech.team14backend._core;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakaotech.team14backend.oauth2.domain.PrincipalDetails;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.oauth2.application.command.token.VerifyToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager); // BasicAuthenticationFilter에 필요한 생성자 호출
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String jwt = request.getHeader(VerifyToken.HEADER);
    if (jwt == null) {
      chain.doFilter(request, response);
      return;
    }
    try {
      DecodedJWT decodedJWT = VerifyToken.execute(jwt);
      Long memberId = Long.valueOf(decodedJWT.getClaim("memberId").asString());
      String kakaoId = decodedJWT.getClaim("kakaoId").asString();
      String userName = decodedJWT.getClaim("username").asString();
      String instaId = decodedJWT.getClaim("instaId").asString();
      Role role = Role.valueOf(decodedJWT.getClaim("role").asString());
      Member member = Member.builder().userName(userName).kakaoId(kakaoId).role(role).instaId(instaId).memberId(memberId).build();
      System.out.println("member :" + member);
      PrincipalDetails myUserDetails = new PrincipalDetails(member);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              myUserDetails,
              myUserDetails.getPassword(),
              myUserDetails.getAuthorities()
          );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.debug("디버그 : 인증 객체 만들어짐");
      chain.doFilter(request,response);
    } catch (SignatureVerificationException | JWTDecodeException e) {
      FilterResponseUtils.AccessTokenValidationException(response);
    } catch (TokenExpiredException tee) {
      FilterResponseUtils.AccessTokenExpired(response);
    }
  }
}
