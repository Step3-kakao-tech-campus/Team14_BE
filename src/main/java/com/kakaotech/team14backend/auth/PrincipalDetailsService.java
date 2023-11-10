package com.kakaotech.team14backend.auth;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
    Member member = memberRepository.findByKakaoId(kakaoId);
    if(member == null){
      throw new UsernameNotFoundException("User not found");
    }
    return new PrincipalDetails(member);
  }
}
