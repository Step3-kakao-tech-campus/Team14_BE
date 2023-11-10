package com.kakaotech.team14backend.post.application.command;


import com.kakaotech.team14backend.post.application.PostMapper;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.post.dto.SetNonAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional(readOnly = true)
public class FindNonAuthPostListCommand extends AbstractHomePostListCommand {


  public FindNonAuthPostListCommand(PostRepository postRepository) {
    super(postRepository);
  }

  @Override
  public GetHomePostListResponseDTO execute(Long lastPostId, int size, Long memberId) {
    FetchResult fetchResult = fetchPosts(lastPostId, size);
    List<SetNonAuthenticatedHomePostDTO> postDTOs = mapToDTOs(fetchResult.getPosts());

    return new GetHomePostListResponseDTO(fetchResult.getNextLastPostId(),
        PostMapper.fromNonAuthenticatedHomePostList(postDTOs), fetchResult.isHasNext());
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

}
