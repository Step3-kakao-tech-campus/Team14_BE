package com.kakaotech.team14backend.member.application;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.inner.point.repository.PointHistoryRepository;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import com.kakaotech.team14backend.post.domain.PostInstaCount;
import com.kakaotech.team14backend.inner.post.repository.PostInstaCountRepository;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import com.kakaotech.team14backend.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.member.dto.InstagramDetails;
import com.kakaotech.team14backend.member.dto.InstagramInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMemberInfoUsecase {

  private final PostLikeCountRepository postLikeCountRepository;
  private final PointRepository pointRepository;
  private final PostRepository postRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final PostInstaCountRepository postInstaCountRepository;
  private final FindMemberService findMemberService;
  // todo : 전체 좋아요 수 발행하는 것들 event driven으로 변경 할

  public GetMemberInfoResponseDTO getMyPageInfo(Long memberId) {

    Member member = findMemberService.execute(memberId);
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
