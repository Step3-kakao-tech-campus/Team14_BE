package com.kakaotech.team14backend.post.application.service;


import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.SetNonAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindNonAuthPostListUsecase {

  private final PostRepository postRepository;
  static final int PAGE_MULTIPLIER = 2;
  static final int PAGE_OFFSET = 1;

  public GetHomePostListResponseDTO execute(Long lastPostId, int size) {
    FetchResult fetchResult = fetchPosts(lastPostId, size);
    List<SetNonAuthenticatedHomePostDTO> postDTOs = mapToDTOs(fetchResult.getPosts());

    return new GetHomePostListResponseDTO(fetchResult.getNextLastPostId(),
        PostMapper.fromNonAuthenticatedHomePostList(postDTOs), fetchResult.isHasNext());
  }

  private FetchResult fetchPosts(Long lastPostId, int size) {
    Pageable pageable = PageRequest.of(0, size * PAGE_MULTIPLIER + PAGE_OFFSET);

    List<Post> posts = queryPosts(lastPostId, pageable);
    boolean hasNext = hasNextPage(posts, size);
    Long nextLastPostId = hasNext ? posts.get(size).getPostId() : null;

    List<Post> finalPosts = hasNext ? shuffleAndTrimPosts(posts, size) : posts;

    return new FetchResult(finalPosts, nextLastPostId, hasNext);
  }

  private List<SetNonAuthenticatedHomePostDTO> mapToDTOs(List<Post> posts) {
    List<SetNonAuthenticatedHomePostDTO> postDTOs = new ArrayList<>();
    for (Post post : posts) {
      postDTOs.add(
          new SetNonAuthenticatedHomePostDTO(post.getPostId(), post.getImage().getImageUri(),
              post.getHashtag(), post.getNickname()));
    }
    return postDTOs;
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

  private List<Post> queryPosts(Long lastPostId, Pageable pageable) {
    return (lastPostId == null) ? new ArrayList<>(postRepository.findAll(pageable).getContent())
        : new ArrayList<>(postRepository.findNextPosts(lastPostId, pageable));
  }

  private boolean hasNextPage(List<Post> posts, int size) {
    return posts.size() > size * PAGE_MULTIPLIER;
  }

  private List<Post> shuffleAndTrimPosts(List<Post> posts, int size) {
    Collections.shuffle(posts);
    return posts.subList(0, size);
  }
}
