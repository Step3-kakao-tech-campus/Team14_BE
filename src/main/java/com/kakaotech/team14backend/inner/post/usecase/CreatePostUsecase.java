package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLikeCount;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePostUsecase {

  //우리가 알던 서비스
  private final PostRepository postRepository;

  @Transactional
  public Post execute(CreatePostDTO createPostDTO) {
    String attachedHashTag = attachHashTags(createPostDTO.uploadPostRequestDTO().getHashTags());
    PostLikeCount postLikeCount = PostLikeCount.createPostLike();
    Post post = Post.createPost(createPostDTO.member(), createPostDTO.image(), postLikeCount, createPostDTO.uploadPostRequestDTO().getNickname(), true, attachedHashTag, createPostDTO.uploadPostRequestDTO().getUniversity());
    return postRepository.save(post);
  }

  // 들어온 해시태그들을 하나로 합치는 메소드
  private String attachHashTags(List<String> hashTags) {
    StringBuilder sb = new StringBuilder();
    for (String hashTag : hashTags) {
      sb.append(hashTag);
    }
    return sb.toString();
  }
}
