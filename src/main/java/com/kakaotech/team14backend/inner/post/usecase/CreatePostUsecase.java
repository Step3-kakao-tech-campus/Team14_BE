package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.UploadPostRequestDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CreatePostUsecase {

  //우리가 알던 서비스
  private final PostRepository postRepository;

  public Post execute(Image image, UploadPostRequestDTO uploadPostRequestDTO, Member member) {
    String attachedHashTag = attachHashTags(uploadPostRequestDTO.getHashTags());

    Post post = Post.createPost(member, image, uploadPostRequestDTO.getNickname(), true,
        attachedHashTag, uploadPostRequestDTO.getUniversity());
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
