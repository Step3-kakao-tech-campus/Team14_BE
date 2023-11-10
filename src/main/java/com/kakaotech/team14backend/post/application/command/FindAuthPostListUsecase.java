package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.post.dto.SetAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindAuthPostListUsecase {

  private final PostRepository postRepository;
  private final FindLikeStatusService findLikeStatusService;

  private static final int PAGE_MULTIPLIER = 2;
  private static final int PAGE_OFFSET = 1;

  public GetHomePostListResponseDTO execute(Long lastPostId, int size, Long memberId) {
    FetchResult postList = fetchPosts(lastPostId, size);
    List<SetAuthenticatedHomePostDTO> postDTOs = mapToDTOs(postList.getPosts(), memberId);

    return new GetHomePostListResponseDTO(postList.nextLastPostId,
        PostMapper.fromAuthenticatedHomePostList(postDTOs), postList.hasNext);
  }

  private List<SetAuthenticatedHomePostDTO> mapToDTOs(List<Post> posts, Long memberId) {
    List<SetAuthenticatedHomePostDTO> postDTOs = new ArrayList<>();

    for (Post post : posts) {
      boolean isLiked = findLikeStatusService.execute(memberId, post.getPostId());
      postDTOs.add(new SetAuthenticatedHomePostDTO(post.getPostId(), post.getImage().getImageUri(),
          post.getHashtag(), 0, post.getNickname(), isLiked));
    }
    return postDTOs;
  }

  private Pageable createPageable(int size) {
    return PageRequest.of(0, size * PAGE_MULTIPLIER + PAGE_OFFSET);
  }

  private FetchResult fetchPosts(Long lastPostId, int size) {

    Pageable pageable = createPageable(size);
    List<Post> posts = queryPosts(lastPostId, pageable);

    boolean hasNext = hasNextPage(posts, size);
    Long nextLastPostId = hasNext ? posts.get(size * PAGE_MULTIPLIER).getPostId() : null;
    List<Post> finalPosts = hasNext ? shuffleAndTrimPosts(posts, size) : posts;

    return new FetchResult(finalPosts, nextLastPostId, hasNext);
  }

  private List<Post> shuffleAndTrimPosts(List<Post> posts, int size) {
    Collections.shuffle(posts);
    return posts.subList(0, size);
  }

  private boolean hasNextPage(List<Post> posts, int size) {
    return posts.size() > size * PAGE_MULTIPLIER;
  }

  private List<Post> queryPosts(Long lastPostId, Pageable pageable) {
    return (lastPostId == null) ? postRepository.findAll(pageable).getContent()
        : postRepository.findNextPosts(lastPostId, pageable);
  }

  @Getter
  private static class FetchResult {

    private final List<Post> posts;
    private final Long nextLastPostId;
    private final boolean hasNext;

    public FetchResult(List<Post> posts, Long nextLastPostId, boolean hasNext) {
      this.posts = posts;
      this.nextLastPostId = nextLastPostId;
      this.hasNext = hasNext;
    }
  }


}
