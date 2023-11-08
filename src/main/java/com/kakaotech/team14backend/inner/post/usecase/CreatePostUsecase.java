package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.common.HashTagUtils;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePostUsecase {

  //우리가 알던 서비스
  private final PostRepository postRepository;

  @Transactional
  public Post execute(CreatePostDTO createPostDTO) {
    String attachedHashTag = HashTagUtils.attachHashTags(
        createPostDTO.uploadPostRequestDTO().getHashTags());
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    PostInstaCount postInstaCount = PostInstaCount.createPostInstaCount();

    Post post = Post.createPost(createPostDTO.member(), createPostDTO.image(), postLikeCount,
        createPostDTO.uploadPostRequestDTO().getNickname(), true, attachedHashTag, postInstaCount);
    return postRepository.save(post);
  }

}
