package com.kakaotech.team14backend.inner.member.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.service.FindMemberService;
import com.kakaotech.team14backend.inner.point.model.PointHistory;
import com.kakaotech.team14backend.inner.point.repository.PointHistoryRepository;
import com.kakaotech.team14backend.inner.point.repository.PointRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostLikeCountRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.member.dto.GetMemberInfoResponseDTO;
import com.kakaotech.team14backend.outer.member.dto.InstagramDetails;
import com.kakaotech.team14backend.outer.member.dto.InstagramInfo;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMemberInfoUsecase {

  private final PostLikeCountRepository postLikeCountRepository;
  private final PointRepository pointRepository;
  private final PostRepository postRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final FindMemberService findMemberService;

  // todo : 전체 좋아요 수 발행하는 것들 event driven으로 변경 할

  public GetMemberInfoResponseDTO getMyPageInfo(Long memberId) {

    Member member = findMemberService.execute(memberId);
    Long totalLike = calculateTotalLikes(member);
    Long totalPoint = findMemberTotalPoints(memberId);
    Long totalViewCount = postRepository.sumViewCountByMemberId(memberId);

    List<Long> postIds = member.getPosts().stream().map(Post::getPostId)
        .collect(Collectors.toList());

    boolean isLinked = !member.getInstaId().equals("none");

    Long totalReceivedFireworks = pointHistoryRepository.receivedFireworksByReceivedId(memberId)
        .map(list -> list.stream() // Optional<List<PointHistory>>를 Stream<PointHistory>로 변환
            .map(PointHistory::getTransferPoint).filter(Objects::nonNull) // null 값 필터링
            .mapToLong(Long::longValue).sum()).orElse(0L); // 리스트가 없거나 비어있을 경우 0을 반환

    if (isLinked) {
      return new GetMemberInfoResponseDTO(member.getMemberId(), member.getUserName(),
          member.getProfileImageUrl(), totalPoint, new InstagramInfo(true,
          new InstagramDetails(totalLike, totalViewCount, totalReceivedFireworks)));
    } else {
      return new GetMemberInfoResponseDTO(member.getMemberId(), member.getUserName(),
          member.getProfileImageUrl(), totalPoint,
          new InstagramInfo(false, new InstagramDetails(null, null, null)));
    }
  }


  private Long findMemberTotalPoints(Long memberId) {
    return pointRepository.findByMemberId(memberId).getNowPoint();
  }

  private Long calculateTotalLikes(Member member) {
    return member.getPosts().stream()
        .mapToLong(post -> postLikeCountRepository.findByPostId(post.getPostId()).getLikeCount())
        .sum();
  }

}
