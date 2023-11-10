package com.kakaotech.team14backend.post.application.usecase;


import com.kakaotech.team14backend.post.domain.PostInstaCount;
import com.kakaotech.team14backend.post.infrastructure.PostInstaCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SetPostInstaCount {

  private final PostInstaCountRepository postInstaCountRepository;

  public void execute(Long postId, Long memberId) {

    PostInstaCount postInstaCount = postInstaCountRepository.findByPostAndMember(postId, memberId);
    //postInsta 없으면 예외 던지자
    if (postInstaCount == null) {
      throw new RuntimeException("postInstaCount가 없습니다.");
    }

    postInstaCount.updatePostInstaCount(postInstaCount.getInstaCount() + 1);
  }
}

