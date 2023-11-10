package com.kakaotech.team14backend.point.application;

import com.kakaotech.team14backend.member.domain.Member;
import com.kakaotech.team14backend.point.domain.UsePointDecider;
import com.kakaotech.team14backend.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.post.application.SetPostInstaCountUsecase;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {

  private final ValidatePointByPopularPostUsecase validatePointByPopularPostUsecase;
  private final UsePointUsecase usePointUsecase;
  private final PostRepository postRepository;
  private final SetPostInstaCountUsecase setPostInstaUsecase;

  public String usePointByPopularPost(
      UsePointByPopularPostRequestDTO usePointByPopularPostRequestDTO, Long senderId) {

    validatePointByPopularPostUsecase.execute(usePointByPopularPostRequestDTO);
    Post post = postRepository.findById(usePointByPopularPostRequestDTO.postId())
        .orElseThrow(() -> new PostNotFoundException());
    Member receiver = post.getMember();
    Long point = UsePointDecider.decidePoint(usePointByPopularPostRequestDTO.postLevel());

    usePointUsecase.execute(senderId, receiver.getMemberId(), point);

    setPostInstaUsecase.execute(post.getPostId(), receiver.getMemberId());
    return receiver.getInstaId();
  }

}
