package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.post.domain.Post;
import java.util.List;
import lombok.Getter;

@Getter
public class PostFetchResponse {

  private final List<Post> posts;
  private final Long nextLastPostId;
  private final boolean hasNext;

  public PostFetchResponse(List<Post> posts, Long nextLastPostId, boolean hasNext) {
    this.posts = posts;
    this.nextLastPostId = nextLastPostId;
    this.hasNext = hasNext;
  }
}
