package com.kakaotech.team14backend.outer.post.mapper;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

  public static GetPostResponseDTO from(Post post){
    return new GetPostResponseDTO(post.getPostId(),post.getImage().getImageUri(), splitHashtag(post.getHashtag()), 0, 0, post.getNickname());
  }
  public static List<GetPostResponseDTO> from(List<Post> postList) {
    List<GetPostResponseDTO> editedPostList = postList.stream().map(PostMapper::from).toList();
    return editedPostList;
  }
  private static List<String> splitHashtag(String hashTag){
    String cuttingHashTag = hashTag.substring(1);
    String[] splitHashtags = cuttingHashTag.split("#");
    return Arrays.stream(splitHashtags).collect(Collectors.toList());
  }
}
