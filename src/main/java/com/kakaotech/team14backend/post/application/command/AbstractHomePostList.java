package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.post.application.PostFetchResponse;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public abstract class AbstractHomePostList {

  protected final PostRepository postRepository;

  protected static final int PAGE_MULTIPLIER = 2;
  protected static final int PAGE_OFFSET = 1;


  public abstract GetHomePostListResponseDTO execute(Long lastPostId, int size, Long memberId);

  protected PostFetchResponse fetchPosts(Long lastPostId, int size) {
    Pageable pageable = createPageable(size);
    List<Post> posts = queryPosts(lastPostId, pageable);
    boolean hasNext = hasNextPage(posts, size);
    Long nextLastPostId = hasNext ? posts.get(size).getPostId() : null;

    List<Post> finalPosts = hasNext ? shuffleAndTrimPosts(posts, size) : posts;

    return new PostFetchResponse(finalPosts, nextLastPostId, hasNext);
  }

  protected Pageable createPageable(int size) {
    return PageRequest.of(0, size * PAGE_MULTIPLIER + PAGE_OFFSET);
  }

  protected List<Post> queryPosts(Long lastPostId, Pageable pageable) {
    return (lastPostId == null) ? new ArrayList<>(postRepository.findAll(pageable).getContent())
        : new ArrayList<>(postRepository.findNextPosts(lastPostId, pageable));
  }

  protected boolean hasNextPage(List<Post> posts, int size) {
    return posts.size() > size;
  }

  protected List<Post> shuffleAndTrimPosts(List<Post> posts, int size) {
    Collections.shuffle(posts);
    return posts.subList(0, Math.min(posts.size(), size));
  }


}
