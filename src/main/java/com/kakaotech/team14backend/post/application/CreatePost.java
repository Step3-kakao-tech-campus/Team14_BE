package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.common.HashTagUtils;
import com.kakaotech.team14backend.inner.post.model.PostInstaCount;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePost {

  //우리가 알던 서비스
  private final PostRepository postRepository;

  @Transactional
  public Post execute(CreatePostDTO createPostDTO) {
    String attachedHashTag = HashTagUtils.attachHashTags(
        createPostDTO.uploadPostRequestDTO().getHashTags());
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    PostInstaCount postInstaCount = PostInstaCount.createPostInstaCount(createPostDTO.member());

    Post post = Post.createPost(createPostDTO.member(), createPostDTO.image(), postLikeCount,
        createPostDTO.uploadPostRequestDTO().getNickname(), true, attachedHashTag);
    post = postRepository.save(post);

    postInstaCount.mappingPost(post);
    post.mapppingPostInstaCount(postInstaCount);
    return post;
  }

}
