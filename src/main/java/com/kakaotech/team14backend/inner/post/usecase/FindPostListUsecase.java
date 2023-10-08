package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindPostListUsecase {

  private final PostRepository postRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * 홈 피드의 게시글들을 가져오는 유즈케이스. lastPostId와 size를 사용하여 페이지네이션을 지원합니다.
   */
  public GetPostListResponseDTO excute(Long lastPostId, int size) {
    Pageable pageable = PageRequest.of(0, size + 1, Sort.by(Direction.DESC, "postId"));

    List<Post> postList;

    // lastPostId가 null인지 확인 (첫 페이지를 가져오는 것을 나타냄)
    // 그렇지 않으면 lastPostId를 기반으로 다음 페이지를 가져옴
    if (lastPostId == null) {
      postList = postRepository.findAll(pageable).getContent();
    } else {
      postList = postRepository.findByPostIdGreaterThan(lastPostId, pageable);
    }

    boolean hasNext = false;
    // 가져온 게시물 수가 요청된 크기보다 큰지 확인
    // 참이면 hasNext 플래그를 true로 설정하고 목록을 요청된 크기로 자름
    if (postList.size() > size) {
      hasNext = true;
      postList.subList(0, size);
    }
    return new GetPostListResponseDTO(PostMapper.from(postList), hasNext);
  }
}
