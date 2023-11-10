package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.post.application.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPersonalPostListUsecase {

  private final PostRepository postRepository;

  public GetPersonalPostListResponseDTO execute(Long memberId, Long lastPostId, int size) {

    Pageable pageable = PageRequest.of(0, size + 1, Sort.by("postId").descending());
    boolean hasNext = false;
    Long nextLastPostId = null;

    List<Post> postList = fetchPosts(memberId, lastPostId, pageable);

    if (postList.size() > size) {
      hasNext = true;
      nextLastPostId = postList.get(size).getPostId();
      postList = postList.subList(0, size);
    }
    return new GetPersonalPostListResponseDTO(nextLastPostId,
        PostMapper.fromPersonalPostList(postList,memberId), hasNext);
  }


  private List<Post> fetchPosts(Long memberId, Long lastPostId, Pageable pageable) {
    if (lastPostId == null || lastPostId == 0) {
      return postRepository.findByMemberIdOrderByPostIdDesc(memberId, pageable);
    } else {
      return postRepository.findByMemberIdAndPostIdLessThanOrderByPostIdDesc(memberId, lastPostId,
          pageable);
    }

  }

}
