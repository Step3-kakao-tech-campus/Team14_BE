package com.kakaotech.team14backend.outer.post.mapper;

import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PostMapper {

  public static GetPostResponseDTO from(Post post) {
    return new GetPostResponseDTO(post.getPostId(), post.getImage().getImageUri(),
        splitHashtag(post.getHashtag()), 0L, 0, post.getNickname());
  }

  public static List<GetPostResponseDTO> from(List<Post> postList) {
    List<GetPostResponseDTO> editedPostList = postList.stream().map(PostMapper::from).toList();
    return editedPostList;
  }

  public static List<GetPersonalPostResponseDTO> fromPersonalPostList(List<Post> postList) {
    List<GetPersonalPostResponseDTO> editedPostList = new ArrayList<>();
    for (Post post : postList) {
      editedPostList.add(
          new GetPersonalPostResponseDTO(post.getPostId(), post.getImage().getImageUri(),
              post.getNickname(), post.getCreatedAt(), post.getViewCount(),
              post.getPostLikeCount().getLikeCount()));
    }
    return editedPostList;
  }


  public static GetPopularPostListResponseDTO from(
      Map<Integer, Set<GetIncompletePopularPostDTO>> levelPosts) {
    List<GetPopularPostDTO> popularPosts = new ArrayList<>();

    for (Map.Entry<Integer, Set<GetIncompletePopularPostDTO>> entry : levelPosts.entrySet()) {
      int postLevel = entry.getKey();
      Set<GetIncompletePopularPostDTO> incompletePosts = entry.getValue();

      for (GetIncompletePopularPostDTO incompletePost : incompletePosts) {
        List<String> hashTags = splitHashtag(incompletePost.getHashTag());

        GetPopularPostDTO popularPost = new GetPopularPostDTO(incompletePost.getPostId(),
            incompletePost.getImageUri(), hashTags, incompletePost.getLikeCount(),  getPostPoint(postLevel),
            incompletePost.getNickname(), postLevel);
        popularPosts.add(popularPost);
      }
    }

    return new GetPopularPostListResponseDTO(popularPosts);
  }

  private static Long getPostPoint(int postLevel) {
    Long postPoint = UsePointDecider.decidePoint(postLevel);
    return postPoint;
  }

  private static List<String> splitHashtag(String hashTag) {
    String cuttingHashTag = hashTag.substring(1);
    String[] splitHashtags = cuttingHashTag.split("#");
    return Arrays.stream(splitHashtags).collect(Collectors.toList());
  }
}
