package com.kakaotech.team14backend.member.application;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.domain.Role;
import com.kakaotech.team14backend.member.domain.Status;
import com.kakaotech.team14backend.member.infrastructure.MemberRepository;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import com.kakaotech.team14backend.inner.point.usecase.CreatePointUsecase;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreatePointUsecase createPointUsecase;

  //private final CreateMemberUsecase createMemberUsecase;
  private final MemberRepository memberRepository; // 추후 Usecase 로 변경
  private final PostLikeCountRepository postLikeCountRepository;
  private final PointRepository pointRepository;

  public Member createMember(String userName, String kakaoId, String instaId,
                             String profileImageUrl, Role role, Long totalLike, Status userStatus) {
    Member member = Member.builder().memberId(Long.valueOf(kakaoId)).userName(userName)
        .kakaoId(kakaoId).instaId(instaId).role(role).profileImageUrl(profileImageUrl)
        .totalLike(totalLike).userStatus(userStatus).build();
    createPointUsecase.execute(member);
    return member;
  }


  @Transactional
  public void deleteAccount(Long memberId) {
    Optional<Member> member = memberRepository.findById(memberId);
    if (member.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }
    member.get().makeUserInactive();
  }

  @Transactional
  public void makeUserActive(Long memberId) {
    Optional<Member> member = memberRepository.findById(memberId);
    if (member.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }
    member.get().makeUserActive();
  }
}
