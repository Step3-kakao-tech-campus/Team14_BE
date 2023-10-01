package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class FindPopularPostUsecase {

  private final PostRepository postRepository;

  /**
   * 캐시 서버에 popularPost가 있다면, 캐시 서버에서 가져오고
   * 존재하지 않는다면 DB에서 가져온다.
   * 가져온 값은 캐시 서버에 반영한다.
   */

  @Transactional(readOnly = true)
  @Cacheable(value = "popularPost", key = "#getPostDTO.postId().toString()")
  public GetPostResponseDTO execute(GetPostDTO getPostDTO) {
    Post popularPost = postRepository.findById(getPostDTO.postId()).orElseThrow(() -> new RuntimeException("Post not found"));
    GetPostResponseDTO getPostResponseDTO = PostMapper.from(popularPost);
    return getPostResponseDTO;
  }

}
