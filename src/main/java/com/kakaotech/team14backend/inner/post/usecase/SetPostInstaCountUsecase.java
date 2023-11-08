package com.kakaotech.team14backend.inner.post.usecase;


import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import com.kakaotech.team14backend.inner.post.repository.PostInstaCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPostInstaCountUsecase {

  private final PostInstaCountRepository postInstaCountRepository;

  public void execute(Post post, Member member) {
    PostInstaCount postInstaCount = postInstaCountRepository.findByPostAndMember(post.getPostId(),
        member.getMemberId());

    postInstaCount.updatePostInstaCount(postInstaCount.getInstaCount() + 1);
    postInstaCountRepository.save(postInstaCount);
  }
}
