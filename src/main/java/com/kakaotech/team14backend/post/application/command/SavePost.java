package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.common.HashTagUtils;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.domain.PostInstaCount;
import com.kakaotech.team14backend.post.domain.PostLikeCount;
import com.kakaotech.team14backend.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SavePost {

  private final PostRepository postRepository;

  @Transactional
  public Post execute(CreatePostDTO createPostDTO) {
    String attachedHashTag = HashTagUtils.attachHashTags(createPostDTO.uploadPostRequestDTO().getHashTags());
    PostLikeCount postLikeCount = PostLikeCount.createPostLikeCount();
    PostInstaCount postInstaCount = PostInstaCount.createPostInstaCount(createPostDTO.member());
    Post post = Post.createPost(createPostDTO.member(), createPostDTO.image(), postLikeCount,postInstaCount, createPostDTO.uploadPostRequestDTO().getNickname(), true, attachedHashTag);
    post = postRepository.save(post);
    return post;
  }

}
