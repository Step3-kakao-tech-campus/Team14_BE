package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostListResponseDTO;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import java.util.List;
import lombok.Getter;
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

    PaginationResult paginationResult = fetchPosts(memberId, lastPostId, size);
    return createResponseDTO(memberId, paginationResult);
  }


  private PaginationResult fetchPosts(Long memberId, Long lastPostId, int size) {
    Pageable pageable = PageRequest.of(0, size + 1, Sort.by("postId").descending());
    List<Post> posts = queryPosts(memberId, lastPostId, pageable);

    boolean hasNext = posts.size() > size;
    Long nextLastPostId = hasNext ? posts.get(size).getPostId() : null;

    return new PaginationResult(posts.subList(0, Math.min(size, posts.size())), nextLastPostId,
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
      PaginationResult paginationResult) {
    return new GetPersonalPostListResponseDTO(paginationResult.getNextLastPostId(),
        PostMapper.fromPersonalPostList(paginationResult.getPosts(), memberId),
        paginationResult.isHasNext());
  }

  @Getter
  private static class PaginationResult {

    private final List<Post> posts;
    private final Long nextLastPostId;
    private final boolean hasNext;

    public PaginationResult(List<Post> posts, Long nextLastPostId, boolean hasNext) {
      this.posts = posts;
      this.nextLastPostId = nextLastPostId;
      this.hasNext = hasNext;
    }

  }
}
