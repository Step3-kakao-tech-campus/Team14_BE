package com.kakaotech.team14backend.outer.post.mapper;

import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.model.PostLike;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostMapper {

  public static GetPostResponseDTO from(Post post) {
    return new GetPostResponseDTO(post.getPostId(), post.getImage().getImageUri(),
        splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), 0,
        post.getNickname());
  }

  public static GetMyPostResponseDTO from(Post post, PostLike postLike) {
    return new GetMyPostResponseDTO(post.getPostId(), post.getImage().getImageUri(),
        splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(),
        postLike.isLiked(), post.getViewCount());
  }

  public static List<GetPostResponseDTO> from(List<Post> postList) {
    List<GetPostResponseDTO> editedPostList = new ArrayList<>();  // 빈 리스트로 초기화
    for (Post post : postList) {
      editedPostList.add(
          new GetPostResponseDTO(post.getPostId(), post.getImage().getImageUri(),
              splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), 0,
              post.getNickname()));
    }
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
      List<GetIncompletePopularPostDTO> getIncompletePopularPostDTOS,
      Map<Integer, List<Integer>> levelIndexes) {
    List<GetPopularPostDTO> popularPosts = new ArrayList<>();
    int h = 0;
    for (int i = 1; i <= levelIndexes.size(); i++) {
      for (int j = 0; j < levelIndexes.get(i).size(); j++) {
        GetIncompletePopularPostDTO getIncompletePopularPostDTO = getIncompletePopularPostDTOS.get(
            h++);
        GetPopularPostDTO getPopularPostDTO = new GetPopularPostDTO(
            getIncompletePopularPostDTO.getPostId(), getIncompletePopularPostDTO.getImageUri(),
            splitHashtag(getIncompletePopularPostDTO.getHashTag()),
            getIncompletePopularPostDTO.getLikeCount(), getPostPoint(i),
            getIncompletePopularPostDTO.getNickname(), i);
        popularPosts.add(getPopularPostDTO);
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
