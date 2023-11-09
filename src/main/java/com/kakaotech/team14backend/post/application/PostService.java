package com.kakaotech.team14backend.post.application;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.usecase.CreateImage;
import com.kakaotech.team14backend.inner.point.model.GetPointPolicy;
import com.kakaotech.team14backend.inner.point.usecase.GetPointUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindMyPostUsecase;
import com.kakaotech.team14backend.inner.post.usecase.FindPostUsecase;
import com.kakaotech.team14backend.outer.post.dto.CreatePostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetMyPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListRequestDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPopularPostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.UploadPostDTO;
import com.kakaotech.team14backend.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final CreateImage createImage;
  private final CreatePost createPostUsecase;
  private final FindPostUsecase findPostUsecase;
  private final SavePostViewCount savePostViewCount;
  private final FindPopularPosts findPopularPosts;


  private final FindMyPostUsecase findMyPostUsecase;
  private final GetPointUsecase getPointUsecase;

  private final PostRepository postRepository;

  @Transactional
  public void uploadPost(UploadPostDTO uploadPostDTO) {
    Image savedImage = createImage.execute(uploadPostDTO.uploadPostRequestDTO().getImage());
    createPostUsecase.execute(makeCreatePostDTO(uploadPostDTO, savedImage));
    getPointUsecase.execute(uploadPostDTO.member(), GetPointPolicy.GIVE_300_WHEN_UPLOAD);
  }

  private static CreatePostDTO makeCreatePostDTO(UploadPostDTO uploadPostDTO, Image savedImage) {
    return new CreatePostDTO(savedImage, uploadPostDTO.uploadPostRequestDTO(),
        uploadPostDTO.member());
  }

  public GetPostResponseDTO getPost(GetPostDTO getPostDTO) {
    savePostViewCount.execute(getPostDTO);
    return findPostUsecase.execute(getPostDTO);
  }

  /**
   * 인기 게시물을 상세조회한다.
   *
   * @return : 인기 게시물 상세 조회시 반환 값
   * @author : hwangdaesun
   * @see : saveTemporaryPostViewCountUsecase는 게시물 조회시 게시물의 조회수를 늘려주는 클래스
   */

  /*
   * 게시물 좋아요를 설정한다.
   * @param : 게시물 구분자, 유저 구분자
   * @return : 게시물 좋아요 설정시 반환 값
   * @see : setPostLikeUsecase는 게시물 좋아요를 설정하는 클래스
   */


  /**
   * 인기 게시물 전체 조회
   *
   * @return : 인기 게시물들 응답
   * @author : hwangdaesun
   */

  public GetPopularPostListResponseDTO getPopularPostList(
      GetPopularPostListRequestDTO getPopularPostListRequestDTO) {
    int size = postRepository.findAll().size();
    return findPopularPosts.execute(getPopularPostListRequestDTO, size);
  }


  public GetMyPostResponseDTO getMyPost(Long memberId, Long postId) {
    GetPostDTO getPostDTO = new GetPostDTO(postId, memberId);
    savePostViewCount.execute(getPostDTO);
    return findMyPostUsecase.execute(memberId, postId);
  }
}
