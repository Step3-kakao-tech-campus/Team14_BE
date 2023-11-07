package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPersonalPostListUsecase {

  private final PostRepository postRepository;

  public GetPersonalPostListResponseDTO execute(Long memberId, Long lastPostId, int size) {

    Pageable pageable = PageRequest.of(0, size + 1);
    boolean hasNext = false;
    Long nextLastPostId = null;

    List<Post> postList = fetchPosts(lastPostId, pageable);

    if (postList.size() > size) {
      hasNext = true;
      nextLastPostId = postList.get(size).getPostId();
      postList.subList(0, size);
    }
    return new GetPersonalPostListResponseDTO(nextLastPostId,
        PostMapper.fromPersonalPostList(postList), hasNext);
  }


  private List<Post> fetchPosts(Long lastPostId, Pageable pageable) {
    return (lastPostId == null) ? new ArrayList<>(postRepository.findAll(pageable).getContent())
        : new ArrayList<>(postRepository.findNextPosts(lastPostId, pageable));
  }

}
