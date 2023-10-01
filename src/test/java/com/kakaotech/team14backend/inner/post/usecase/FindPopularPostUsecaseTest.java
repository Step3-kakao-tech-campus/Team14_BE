package com.kakaotech.team14backend.inner.post.usecase;

import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.image.repository.ImageRepository;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import javax.persistence.EntityManager;


@SpringBootTest
class FindPopularPostUsecaseTest {

  @Autowired
  private FindPopularPostUsecase findPopularPostUsecase;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private CacheManager cacheManager;

  @BeforeEach
  @DisplayName("게시물1 저장")
  void setUp() {
    Member member = new Member("sonny", "sonny1234","asdf324","ROLE_BEGINNER",0L,"active");
    memberRepository.save(member);

    Image image = new Image("/image/firstPhoto");
    imageRepository.save(image);

    Post post = Post.createPost(member, image, "대선대선", true, "#가자", "전남대학교");
    postRepository.save(post);

    entityManager.clear();
    Cache cache = cacheManager.getCache("popularPost");
    cache.clear();
  }

  @Test
  @DisplayName("DB에서 데이터를 가져온다.")
  void execute() {
    GetPostDTO getPostDTO = new GetPostDTO(1L,2L);
    GetPostResponseDTO getPostResponseDTO = findPopularPostUsecase.execute(getPostDTO);
  }

  @Test
  @DisplayName("캐시서버에서 데이터를 가져온다. - sql 실행 안 됨")
  void execute_cache() {
    GetPostDTO getPostDTO = new GetPostDTO(1L,2L);
    findPopularPostUsecase.execute(getPostDTO);

    entityManager.clear();

    GetPostDTO getPostDTO1 = new GetPostDTO(1L,2L);
    findPopularPostUsecase.execute(getPostDTO1);
  }



  }



