package com.kakaotech.team14backend.post.application.command;

import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.post.dto.SetAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class FindAuthPostList extends AbstractHomePostList {

  private final FindLikeStatus findLikeStatusCommand;

  public FindAuthPostList(PostRepository postRepository,
      FindLikeStatus findLikeStatusCommand) {
    super(postRepository);
    this.findLikeStatusCommand = findLikeStatusCommand;
  }

  @Override
  public GetHomePostListResponseDTO execute(Long lastPostId, int size, Long memberId) {
    FetchResult postList = fetchPosts(lastPostId, size);
    List<SetAuthenticatedHomePostDTO> postDTOs = mapToDTOs(postList.getPosts(), memberId);

    return new GetHomePostListResponseDTO(postList.getNextLastPostId(),
        PostMapper.fromAuthenticatedHomePostList(postDTOs), postList.isHasNext());
  }

  private List<SetAuthenticatedHomePostDTO> mapToDTOs(List<Post> posts, Long memberId) {
    List<SetAuthenticatedHomePostDTO> postDTOs = new ArrayList<>();

    for (Post post : posts) {
      boolean isLiked = findLikeStatusCommand.execute(memberId, post.getPostId());
      postDTOs.add(new SetAuthenticatedHomePostDTO(post.getPostId(), post.getImage().getImageUri(),
          post.getHashtag(), 0, post.getNickname(), isLiked));
    }
    return postDTOs;
  }


}
