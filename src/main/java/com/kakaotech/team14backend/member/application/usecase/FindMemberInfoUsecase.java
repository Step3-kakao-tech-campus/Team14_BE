package com.kakaotech.team14backend.member.application.usecase;

import com.kakaotech.team14backend.member.application.command.FindMember;
import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.member.dto.InstagramDetails;
import com.kakaotech.team14backend.member.dto.InstagramInfo;
import com.kakaotech.team14backend.point.infrastructure.PointRepository;
import com.kakaotech.team14backend.post.domain.PostInstaCount;
import com.kakaotech.team14backend.post.infrastructure.PostInstaCountRepository;
import com.kakaotech.team14backend.post.infrastructure.PostLikeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMemberInfoUsecase {

  private final PostLikeCountRepository postLikeCountRepository;
  private final PointRepository pointRepository;
  private final PostInstaCountRepository postInstaCountRepository;
  private final FindMember findMember;

  public GetMemberInfoResponseDTO execute(Long memberId) {

    Member member = findMember.execute(memberId);
    Long totalLike = calculateTotalLikes(member);
    Long totalPoint = findMemberTotalPoints(memberId);
    Long totalViewCount = postInstaCountRepository.findByMemberId(memberId)
        .stream()
        .mapToLong(PostInstaCount::getInstaCount)
        .sum();

    InstagramInfo instagramInfo = createInstagramInfo(member, totalLike, totalViewCount);
    return new GetMemberInfoResponseDTO(member.getMemberId(), member.getUserName(),
        member.getProfileImageUrl(), totalPoint, instagramInfo);
  }

  private Long findMemberTotalPoints(Long memberId) {
    return pointRepository.findByMemberId(memberId).getNowPoint();
  }

  private InstagramInfo createInstagramInfo(Member member, Long totalLike, Long totalView) {
    boolean isInstagramLinked = !member.getInstaId().equals("none");
    InstagramDetails details = new InstagramDetails(isInstagramLinked ? totalLike : null,
        isInstagramLinked ? totalView : null);
    return new InstagramInfo(isInstagramLinked, details);
  }


  private Long calculateTotalLikes(Member member) {
    return member.getPosts().stream()
        .mapToLong(post -> postLikeCountRepository.findByPostId(post.getPostId()).getLikeCount())
        .sum();
  }

}
