package com.kakaotech.team14backend.point.appliation;

import com.kakaotech.team14backend.post.exception.PostNotFoundException;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.point.usecase.UsePointUsecase;
import com.kakaotech.team14backend.inner.point.usecase.ValidatePointByPopularPostUsecase;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import com.kakaotech.team14backend.inner.post.usecase.SetPostInstaCountUsecase;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPopularPostRequestDTO;
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
