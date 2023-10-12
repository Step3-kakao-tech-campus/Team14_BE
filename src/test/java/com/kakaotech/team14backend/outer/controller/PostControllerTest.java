package com.kakaotech.team14backend.outer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.inner.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class PostControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @DisplayName("단일 유저가 올린 게시물들을 조회합니다")
  @Test
  void getPersonalPostList_Test() throws Exception {

    ResultActions resultActions = mockMvc.perform(
        get("/api/post/user")
            .param("userId", "1")
            .param("lastPostId", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("getPostByUser_Test : " + responseBody);

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
  }

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
  void findAllPopularPost_Test() throws Exception {

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

  @DisplayName("인기 피드를 조회 - 비정상 파라미터")
  @Test
  void findAllPopularPostExeedLevelSize_Test() throws Exception {

    ResultActions resultActions = mockMvc.perform(
        get("/api/popular-post")
            .param("level3", "20")
            .param("level2", "3")
            .param("level1", "3")
            .contentType(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("findAllPopularPost_Test : " + responseBody);

    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.success").value(false));
    resultActions.andExpect(jsonPath("$.response").doesNotExist());
  }

}
