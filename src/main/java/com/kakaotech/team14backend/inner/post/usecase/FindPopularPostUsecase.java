package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class FindPopularPostUsecase {

  private final PostRepository postRepository;

  /**
   * Redis에 해당 popularPost가 있다면 Redis에서 가져오고, 존재하지 않는다면 DB에서 가져온 후 Redis에 반영한다.
   *
   * @author : hwangdaesun
   * @param : 게시물 구분자, 유저 구분자
   * @return : 인기 게시물 상세 조회시 반환 값
   */

  @Transactional(readOnly = true)
  public GetPostResponseDTO execute(GetPostDTO getPostDTO) {
    Post popularPost = postRepository.findById(getPostDTO.postId()).orElseThrow(() -> new RuntimeException("Post not found"));
    GetPostResponseDTO getPostResponseDTO = PostMapper.from(popularPost);
    return getPostResponseDTO;
  }

}
