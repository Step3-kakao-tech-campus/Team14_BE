package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.point.domain.UsePointDecider;
import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.post.domain.PostInstaCount;
import com.kakaotech.team14backend.post.domain.RandomIndexes;
import com.kakaotech.team14backend.post.dto.GetAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.dto.GetIncompletePopularPostDTO;
import com.kakaotech.team14backend.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetNonAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.dto.GetPersonalPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.post.dto.PostLevelPoint;
import com.kakaotech.team14backend.post.dto.SetAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.post.dto.SetNonAuthenticatedHomePostDTO;
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
    return new GetPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
        splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), 0,
        post.getNickname());
  }

  public static GetPopularPostResponseDTO from(Post post, Boolean isLiked,
      PostLevelPoint postLevelPoint) {
    return new GetPopularPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
        splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(),
        postLevelPoint.postPoint(),
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


  public static GetMyPostResponseDTO from(final Post post, final boolean isLiked, Long memberId) {
    PostInstaCount filteredInstaCount = post.getPostInstaCount().stream()
        .filter(instaCount -> instaCount.getMember().getMemberId().equals(memberId))
        .findFirst() // 첫 번째 일치하는 객체를 가져오거나, 없다면 empty를 반환합니다.
        .orElse(null); // 일치하는 객체가 없다면 null을 반환합니다.

    long instaCountValue = filteredInstaCount != null ? filteredInstaCount.getInstaCount() : 0;

    return new GetMyPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
        post.getNickname(), splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(),
        isLiked, instaCountValue);
  }

  public static List<GetPostResponseDTO> from(List<Post> postList) {
    List<GetPostResponseDTO> editedPostList = new ArrayList<>();  // 빈 리스트로 초기화
    for (Post post : postList) {
      editedPostList.add(
          new GetPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
              splitHashtag(post.getHashtag()), post.getPostLikeCount().getLikeCount(), 0,
              post.getNickname()));
    }
    return editedPostList;
  }

  public static List<GetPersonalPostResponseDTO> fromPersonalPostList(List<Post> postList,
      Long memberId) {
    List<GetPersonalPostResponseDTO> editedPostList = new ArrayList<>();
    for (Post post : postList) {
      PostInstaCount filteredInstaCount = post.getPostInstaCount().stream()
          .filter(instaCount -> instaCount.getMember().getMemberId().equals(memberId))
          .findFirst() // 첫 번째 일치하는 객체를 가져오거나, 없다면 empty를 반환합니다.
          .orElse(null); // 일치하는 객체가 없다면 null을 반환합니다.

      Long instaCountValue = filteredInstaCount != null ? filteredInstaCount.getInstaCount() : 0;

      editedPostList.add(
          new GetPersonalPostResponseDTO(post.getPostId(), makeUrl(post.getImage().getImageUri()),
              post.getNickname(), formatDate(post.getCreatedAt()),
              instaCountValue,
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
      for (Integer index : entry.getValue().getIndexes()) {
        GetIncompletePopularPostDTO getIncompletePopularPostDTO = getIncompletePopularPostDTOS.get(
            h++);
        GetPopularPostDTO getPopularPostDTO = new GetPopularPostDTO(
            getIncompletePopularPostDTO.getPostId(),
            makeUrl(getIncompletePopularPostDTO.getImageUri()),
            splitHashtag(getIncompletePopularPostDTO.getHashTag()),
            getIncompletePopularPostDTO.getLikeCount(), getIncompletePopularPostDTO.getNickname(),
            getIncompletePopularPostDTO.getPostLevel());
        popularPosts.add(getPopularPostDTO);
      }
    }
    return new GetPopularPostListResponseDTO(popularPosts);
  }


  private static Long getPostPoint(int postLevel) {
    return UsePointDecider.getPoint(postLevel);
  }

  private static String formatDate(Instant createdAt) {
    return createdAt.atZone(ZoneId.of("Asia/Seoul"))
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  private static String makeUrl(String uri) {
    return url + uri;
  }
}
