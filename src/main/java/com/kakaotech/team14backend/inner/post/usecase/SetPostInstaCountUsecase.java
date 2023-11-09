package com.kakaotech.team14backend.inner.post.usecase;


import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import com.kakaotech.team14backend.inner.post.repository.PostInstaCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class SetPostInstaCountUsecase {

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

