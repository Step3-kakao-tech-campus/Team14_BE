
package com.kakaotech.team14backend.outer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.inner.member.repository.MemberRepository;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import com.kakaotech.team14backend.inner.post.usecase.SaveTemporaryPopularPostListUsecase;
import java.util.Set;

import com.kakaotech.team14backend.outer.post.dto.GetPopularPostResponseDTO;
import com.kakaotech.team14backend.outer.post.dto.GetPostDTO;
import com.kakaotech.team14backend.outer.post.dto.SetPostLikeDTO;
import com.kakaotech.team14backend.outer.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Sql("classpath:db/testSetup.sql")
@AutoConfigureMockMvc

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)
public class PostControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private SaveTemporaryPopularPostListUsecase saveTemporaryPopularPostListUsecase;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PostService postService;

  /**
   * 추후에 기능 고도화시 홈 피드에서도 Redis를 사용해 게시물을 조회할 수동 있기 때문에 @BeforEach 사용
   */

  @BeforeEach
  void init_start() {
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null && !keys.isEmpty()) {
      redisTemplate.delete(keys);
    }
  }

  @AfterEach
  void init_end() {
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null && !keys.isEmpty()) {
      redisTemplate.delete(keys);
    }
  }

//  @DisplayName("단일 유저가 올린 게시물들을 조회합니다")
//  @Test
//  void getPersonalPostList_Test() throws Exception {
//
//    ResultActions resultActions = mockMvc.perform(
//        get("/api/post/user")
//            .param("userId", "1")
//            .param("lastPostId", "0")
//            .param("size", "10")
//            .contentType(MediaType.APPLICATION_JSON));
//
//    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//
//    System.out.println("getPostByUser_Test : " + responseBody);
//
//    resultActions.andExpect(status().isOk());
//    resultActions.andExpect(jsonPath("$.success").value(true));
//    resultActions.andExpect(jsonPath("$.response").exists());
//  }

  @DisplayName("홈 피드를 조회한다 - 정상 파라미터")
  @Test
  void findAllHomeFeed_Test() throws Exception {

    ResultActions resultActions = mockMvc.perform(
        get("/api/post").param("lastPostId", "0").param("size", "10")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllHomeFeed_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
  }

  @DisplayName("처음 홈피드를 조회한다 - 파라미터 없음")
  @Test
  void findAllHomeFeedNoParam_Test() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        get("/api/post").param("size", "10").contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllHomeFeedNoParam_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
  }

  @DisplayName("홈 피드를 조회한다 - 잘못된 파라미터")
  @Test
  void findAllHomeFeedWrongParam_Test() throws Exception {

    ResultActions resultActions = mockMvc.perform(
        get("/api/post")
            .param("lastPostId", "0")
            .param("size", "-1")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllHomeFeedWrongParam_Test : " + responseBody);

    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.success").value(false));
    resultActions.andExpect(jsonPath("$.response").doesNotExist());
  }

  @DisplayName("홈 피드를 조회한다 - 마지막 게시물 아이디가 없을 때")
  @Test
  void findAllHomeFeedNoLastPostId_Test() throws Exception {

    ResultActions resultActions = mockMvc.perform(
        get("/api/post").param("lastPostId", "15").param("size", "10")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllHomeFeedNoLastPostId_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
  }

  @DisplayName("인기 피드를 조회 - 정상 파라미터")
  @Test
  @WithUserDetails("kakao1")
  void findAllPopularPost_Test() throws Exception {

    saveTemporaryPopularPostListUsecase.execute();

    ResultActions resultActions = mockMvc.perform(
        get("/api/popular-post")
            .param("level3", "4")
            .param("level2", "3")
            .param("level1", "3")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllPopularPost_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
  }

  @DisplayName("인기 피드 상세 조회 - 비정상 파라미터")
  @Test
  @WithUserDetails("kakao1")
  void findPopularPost_Test() throws Exception {

    saveTemporaryPopularPostListUsecase.execute();

    String param = "1";

    ResultActions resultActions = mockMvc.perform(
        get("/api/popular-post/" + param)
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findPopularPost_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response.postPoint").exists());
    resultActions.andExpect(jsonPath("$.response.postLevel").exists());
    resultActions.andExpect(jsonPath("$.response").exists());
  }

  @DisplayName("인기 피드 전체 조회 - 비정상 파라미터로 맥스 사이즈 예외처리")
  @Test
  @WithUserDetails("kakao1")
  void findAllPopularPostMaxSize_Test() throws Exception {

    ResultActions resultActions = mockMvc.perform(
        get("/api/popular-post")
            .param("level3", "10")
            .param("level2", "3")
            .param("level1", "3")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllPopularPost_Test : " + responseBody);

    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.success").value(false));
    resultActions.andExpect(jsonPath("$.response").doesNotExist());
  }

  @DisplayName("인기 피드를 상세 조회 좋아요 요청후 취소한 후에 반영되어있는 지 확인 - 정상 파라미터")
  @Test
  @WithUserDetails("kakao1")
  void findPopularPost_isLike_Test() throws Exception {

    saveTemporaryPopularPostListUsecase.execute();

    GetPostDTO getPostDTO = new GetPostDTO(10L, 1L);
    GetPopularPostResponseDTO getPopularPostResponseDTO = postService.getPopularPost(getPostDTO);


    SetPostLikeDTO setPostLikeDTO = new SetPostLikeDTO(10L,1L);
    postService.setPostLike(setPostLikeDTO);

    String param = "10";

    ResultActions resultActions = mockMvc.perform(
        get("/api/popular-post/" + param)
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllPopularPost_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
    resultActions.andExpect(jsonPath("$.response").exists());
    resultActions.andExpect(jsonPath("$.response.isLiked").value(!getPopularPostResponseDTO.isLiked()));

  }


//  @DisplayName("회원1이 포인트를 사용하여 회원2의 게시판297를 구매하였으나 돈이 부족한 경우- 정상 파라미터")
//  @Test
//  @WithUserDetails("kakao1")
//  void usePopularPost_Test() throws Exception {
//
//    saveTemporaryPopularPostListUsecase.execute();
//
//    UsePointByPopularPostRequestDTO requestDTO = new UsePointByPopularPostRequestDTO(297L, 3);
//
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    String requestBody = objectMapper.writeValueAsString(requestDTO);
//
//
//    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/point/popular-post")
//        .content(requestBody)
//        .contentType(MediaType.APPLICATION_JSON);
//
//    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//
//    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//
//    System.out.println("usePopularPost_Test : " + responseBody);
//
//    resultActions.andExpect(status().isOk());
//    resultActions.andExpect(jsonPath("$.success").value(true));
//    resultActions.andExpect(jsonPath("$.response").doesNotExist());
//  }

//  @DisplayName("회원1이 포인트를 사용하여 회원2의 게시판298를 구매하였으나 돈이 부족한 경우 - 정상 파라미터")
//  @Test
//  @WithUserDetails("kakao1")
//  void usePopularPost_noPoint_Test() throws Exception {
//
//    saveTemporaryPopularPostListUsecase.execute();
//
//    UsePointByPopularPostRequestDTO requestDTO = new UsePointByPopularPostRequestDTO(296L, 3);
//
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    String requestBody = objectMapper.writeValueAsString(requestDTO);
//
//
//    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/point/popular-post")
//        .content(requestBody)
//        .contentType(MediaType.APPLICATION_JSON);
//
//    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//
//    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//
//    System.out.println("usePopularPost_Test : " + responseBody);
//
//
//    resultActions.andExpect(status().is5xxServerError());
//    resultActions.andExpect(jsonPath("$.success").value(false));
//    resultActions.andExpect(jsonPath("$.response").doesNotExist());
//  }
//
//  @DisplayName("요청 파라미터인 게시판 id와 해당 게시팔 레벨이 Redis에 저장되어있는 상태와 같지 않을 경우- 비정상 파라미터")
//  @Test
//  @WithUserDetails("kakao1")
//  void usePopularPost_noMatch_postIdAndPostLevel_Test() throws Exception {
//
//    saveTemporaryPopularPostListUsecase.execute();
//
//    UsePointByPopularPostRequestDTO requestDTO = new UsePointByPopularPostRequestDTO(296L, 2);
//
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    String requestBody = objectMapper.writeValueAsString(requestDTO);
//
//
//    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/point/popular-post")
//        .content(requestBody)
//        .contentType(MediaType.APPLICATION_JSON);
//
//    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//
//    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//
//    System.out.println("usePopularPost_Test : " + responseBody);
//
//    resultActions.andExpect(status().is4xxClientError());
//    resultActions.andExpect(jsonPath("$.success").value(false));
//    resultActions.andExpect(jsonPath("$.response").doesNotExist());
//  }

}

