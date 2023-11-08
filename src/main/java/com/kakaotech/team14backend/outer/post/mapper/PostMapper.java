package com.kakaotech.team14backend.outer.post.mapper;

import com.kakaotech.team14backend.inner.point.model.UsePointDecider;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.outer.post.dto.GetAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetNonAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPersonalPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.inner.post.model.RandomIndexes;
import com.kakaotech.team14backend.outer.post.dto.SetAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.outer.post.dto.SetNonAuthenticatedHomePostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.kakaotech.team14backend.common.HashTagUtils.splitHashtag;

@Component
public class PostMapper {

  private static String url;

  @Value("${url}")
  public void setUrl(String url) {
    this.url = url;
  }

  public static GetPostResponseDTO from(Post post) {
    return new GetPostResponseDTO(post.getPostId(),makeUrl(post.getImage().getImageUri()),
        splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), 0,
        post.getNickname());
  }

  public static GetPopularPostResponseDTO from(Post post, Boolean isLiked, PostLevelPoint postLevelPoint) {
    return new GetPopularPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
        splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), postLevelPoint.postPoint(),
        post.getNickname(), isLiked, postLevelPoint.postLevel());
  }

  public static List<GetAuthenticatedHomePostDTO> fromAuthenticatedHomePostList(
      List<SetAuthenticatedHomePostDTO> postList) {
    List<GetAuthenticatedHomePostDTO> editedPostList = new ArrayList<>();  // 빈 리스트로 초기화

    for (SetAuthenticatedHomePostDTO post : postList) {
      editedPostList.add(new GetAuthenticatedHomePostDTO(post.postId(), makeUrl(post.imageUri()),
          splitHashtag(post.hashTags()), 0, post.nickname(), post.isLiked()));
    }
    return editedPostList;
  }

  public static List<GetNonAuthenticatedHomePostDTO> fromNonAuthenticatedHomePostList(
      List<SetNonAuthenticatedHomePostDTO> postList) {

    List<GetNonAuthenticatedHomePostDTO> editedPostList = new ArrayList<>();  // 빈 리스트로 초기화
    for (SetNonAuthenticatedHomePostDTO post : postList) {
      editedPostList.add(new GetNonAuthenticatedHomePostDTO(post.postId(), makeUrl(post.imageUri()),
          splitHashtag(post.hashTags()), post.nickname()));
    }
    return editedPostList;
  }


  public static GetMyPostResponseDTO from(final Post post, final boolean isLiked) {
    return new GetMyPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
        post.getNickname(), splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(),
        isLiked, post.getViewCount());
  }

  public static List<GetPostResponseDTO> from(List<Post> postList) {
    List<GetPostResponseDTO> editedPostList = new ArrayList<>();  // 빈 리스트로 초기화
    for (Post post : postList) {
      editedPostList.add(new GetPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
          splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), 0,
          post.getNickname()));
    }
    return editedPostList;
  }

  public static List<GetPersonalPostResponseDTO> fromPersonalPostList(List<Post> postList) {
    List<GetPersonalPostResponseDTO> editedPostList = new ArrayList<>();
    for (Post post : postList) {
      editedPostList.add(
          new GetPersonalPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
              post.getNickname(), formatDate(post.getCreatedAt()), post.getViewCount(),
              post.getPostLikeCount().getLikeCount()));
    }
    return editedPostList;
  }


  public static GetPopularPostListResponseDTO from(
      List<GetIncompletePopularPostDTO> getIncompletePopularPostDTOS,
      Map<Integer, RandomIndexes> levelIndexes) {
    List<GetPopularPostDTO> popularPosts = new ArrayList<>();
    int h = 0;
    for (Map.Entry<Integer, RandomIndexes> entry : levelIndexes.entrySet()) {
      for(Integer index : entry.getValue().getIndexes()){
        GetIncompletePopularPostDTO getIncompletePopularPostDTO = getIncompletePopularPostDTOS.get(
            h++);
        GetPopularPostDTO getPopularPostDTO = new GetPopularPostDTO(
            getIncompletePopularPostDTO.getPostId(), makeUrl(getIncompletePopularPostDTO.getImageUri()),
            splitHashtag(getIncompletePopularPostDTO.getHashTag()),
            getIncompletePopularPostDTO.getLikeCount(), getIncompletePopularPostDTO.getNickname(), getIncompletePopularPostDTO.getPostLevel());
        popularPosts.add(getPopularPostDTO);
      }
    }
    return new GetPopularPostListResponseDTO(popularPosts);
  }


  private static Long getPostPoint(int postLevel) {
    return UsePointDecider.decidePoint(postLevel);
  }

  private static String formatDate(Instant createdAt) {
    return createdAt.atZone(ZoneId.of("Asia/Seoul"))
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  private static String makeUrl(String uri){
    return url + uri;
  }
}
