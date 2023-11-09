package com.kakaotech.team14backend.inner.post.usecase;


import com.kakaotech.team14backend.post.domain.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetHomePostListResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.SetNonAuthenticatedHomePostDTO;
import com.kakaotech.team14backend.outer.post.mapper.PostMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

  /**
   * 홈 피드의 게시글들을 가져오는 유즈케이스. lastPostId와 size를 사용하여 페이지네이션을 지원합니다.
   */
  private static final int PAGE_MULTIPLIER = 2;
  private static final int PAGE_OFFSET = 1;

  public GetHomePostListResponseDTO execute(Long lastPostId, int size) {
    Pageable pageable = createPageable(size);
    List<Post> postList = fetchPosts(lastPostId, pageable);

    boolean hasNext = postList.size() > size * PAGE_MULTIPLIER;
    Long nextLastPostId = hasNext ? postList.get(size * PAGE_MULTIPLIER).getPostId() : null;

    if (hasNext) {
      Collections.shuffle(postList);
    }
    List<Post> selectedPosts = hasNext ? postList.subList(0, size) : postList;

    List<SetNonAuthenticatedHomePostDTO> postDTOs = new ArrayList<>();
    for (Post post : selectedPosts) {
      SetNonAuthenticatedHomePostDTO postDTO = new SetNonAuthenticatedHomePostDTO(post.getPostId(),
          post.getImage().getImageUri(), post.getHashtag(), post.getNickname());

      postDTOs.add(postDTO);
    }

    return new GetHomePostListResponseDTO(nextLastPostId,
        PostMapper.fromNonAuthenticatedHomePostList(postDTOs), hasNext);
  }

  private Pageable createPageable(int size) {
    return PageRequest.of(0, size * PAGE_MULTIPLIER + PAGE_OFFSET);
  }

  private List<Post> fetchPosts(Long lastPostId, Pageable pageable) {
    return (lastPostId == null) ? new ArrayList<>(postRepository.findAll(pageable).getContent())
        : new ArrayList<>(postRepository.findNextPosts(lastPostId, pageable));
  }
}
