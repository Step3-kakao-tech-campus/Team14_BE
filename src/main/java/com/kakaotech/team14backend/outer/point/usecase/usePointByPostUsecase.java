package com.kakaotech.team14backend.outer.point.usecase;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.point.dto.UsePointByPostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class usePointByPostUsecase {


  private final PostRepository postRepository;

  //  private final Post
  public String getInstaId(UsePointByPostRequestDTO usePointByPostRequestDTO) {
    Long postId = usePointByPostRequestDTO.postId();
    Post post = postRepository.findById(postId).orElseThrow();

    Member memberOfPost = post.getMember();
    return memberOfPost.getInstaId();
  }
}
