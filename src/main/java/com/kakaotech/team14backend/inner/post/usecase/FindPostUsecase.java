package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPostUsecase {

  private final PostRepository postRepository;
  private final RedisTemplate<String,Object> redisTemplate;

  public Post execute(GetPostDTO getPostDTO) {
    redisTemplate.opsForSet().add(String.valueOf(getPostDTO.postId()),getPostDTO.memberId());
    // todo 캐시 서버에 해당 게시글이 존재하는 지 확인하는 메서드
    // todo 있다면, 캐시 서버에서 해당 게시글을 가져오는 메서드
    Post post = postRepository.findById(getPostDTO.postId()).orElseThrow(() -> new RuntimeException("Post not found"));
    return post;
  }

}
