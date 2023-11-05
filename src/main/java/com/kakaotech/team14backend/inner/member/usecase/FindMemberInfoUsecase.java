package com.kakaotech.team14backend.inner.member.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.point.repository.PointHistoryRepository;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import com.kakaotech.team14backend.inner.point.usecase.CreatePointUsecase;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.outer.member.dto.InstagramDetails;
import com.kakaotech.team14backend.outer.member.dto.InstagramInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMemberInfoUsecase {

  private final MemberRepository memberRepository;

  private final CreatePointUsecase createPointUsecase;

  private final PostLikeCountRepository postLikeCountRepository;
  private final PointRepository pointRepository;
  private final PostRepository postRepository;
  private final PointHistoryRepository pointHistoryRepository;

  // todo : createMemberusease를 이용해 주세요! ++ 주석 참고
  // todo : 전체 좋아요 수 발행하는 것들 event driven으로 변경 할
  public GetMemberInfoResponseDTO getMyPageInfo(Long memberId) {

    // 회원 정보를 조회하고, 없다면 예외를 발생시킵니다.
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

    // 회원이 작성한 게시물의 ID 목록을 스트림을 이용해 가져옵니다.
    List<Long> postIds = member.getPosts().stream().map(Post::getPostId)
        .collect(Collectors.toList());

    // 좋아요의 총합을 스트림을 이용하여 계산합니다.
    Long totalLike = postIds.stream().map(postLikeCountRepository::findByPostId)
        .mapToLong(PostLikeCount::getLikeCount).sum();
    boolean isLinked = !member.getInstaId().equals("none");

    // todo: 어색한 도메인의 getter 고치기
    Long totalPoint = pointRepository.findByMemberId(memberId).getNowPoint();

    Long totalView = postRepository.sumViewCountByMemberId(memberId);
    // DTO를 생성하여 반환합니다.
//    return new GetMemberInfoResponseDTO(member.getMemberId(), member.getUserName(),
//        member.getKakaoId(), totalLike, member.getProfileImageUrl(), isInstaConnected, totalPoint);
    Long totalReceivedfireworks = pointHistoryRepository.sumReceivedFireworksByReceivedId(memberId);

    if (isLinked) {
      return new GetMemberInfoResponseDTO(member.getMemberId(), member.getUserName(),
          member.getProfileImageUrl(), totalPoint,
          new InstagramInfo(true,
              new InstagramDetails(totalLike, totalView, totalReceivedfireworks)));
    } else {
      return new GetMemberInfoResponseDTO(member.getMemberId(), member.getUserName(),
          member.getProfileImageUrl(), totalPoint,
          new InstagramInfo(false,
              new InstagramDetails(null, null, null)));
    }
  }

}
