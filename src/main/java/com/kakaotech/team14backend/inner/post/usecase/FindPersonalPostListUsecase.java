package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPersonalPostListUsecase {

  private final PostRepository postRepository;

  public GetPersonalPostListResponseDTO excute(Long memberId, Long lastPostId, int size) {

    Pageable pageable = PageRequest.of(0, size + 1, Sort.by(Direction.DESC, "postId"));

    List<Post> postList;
    if (lastPostId == null) {
      postList = postRepository.findByMemberId(memberId, pageable);
    } else {
      postList = postRepository.findByMemberIdAndPostIdGreaterThan(memberId, lastPostId, pageable);
    }

    boolean hasNext = false;
    if (postList.size() > size) {
      hasNext = true;
      postList.subList(0, size);
    }
    return new GetPersonalPostListResponseDTO(PostMapper.fromPersonalPostList(postList), hasNext);
  }
}
