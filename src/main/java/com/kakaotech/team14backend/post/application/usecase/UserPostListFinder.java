package com.kakaotech.team14backend.post.application.usecase;

import com.kakaotech.team14backend.post.application.PostFetchResponse;
import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostListFinder {

  private final PostRepository postRepository;

  public GetPersonalPostListResponseDTO execute(Long memberId, Long lastPostId, int size) {

    PostFetchResponse paginationResult = fetchPosts(memberId, lastPostId, size);
    return createResponseDTO(memberId, paginationResult);
  }


  private PostFetchResponse fetchPosts(Long memberId, Long lastPostId, int size) {
    Pageable pageable = PageRequest.of(0, size + 1, Sort.by("postId").descending());
    List<Post> posts = queryPosts(memberId, lastPostId, pageable);

    boolean hasNext = posts.size() > size;
    Long nextLastPostId = hasNext ? posts.get(size).getPostId() : null;

    return new PostFetchResponse(posts.subList(0, Math.min(size, posts.size())), nextLastPostId,
        hasNext);

  }

  private List<Post> queryPosts(Long memberId, Long lastPostId, Pageable pageable) {
    return isFirstPageFetch(lastPostId) ? postRepository.findByMemberIdOrderByPostIdDesc(memberId,
        pageable)
        : postRepository.findByMemberIdAndPostIdLessThanOrderByPostIdDesc(memberId, lastPostId,
            pageable);
  }

  private boolean isFirstPageFetch(Long lastPostId) {
    return lastPostId == null || lastPostId == 0;
  }

  private GetPersonalPostListResponseDTO createResponseDTO(Long memberId,
      PostFetchResponse paginationResult) {
    return new GetPersonalPostListResponseDTO(paginationResult.getNextLastPostId(),
        PostMapper.fromPersonalPostList(paginationResult.getPosts(), memberId),
        paginationResult.isHasNext());
  }


}
