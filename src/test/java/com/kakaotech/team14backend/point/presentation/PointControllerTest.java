package com.kakaotech.team14backend.point.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.point.dto.UsePointByPopularPostRequestDTO;
import com.kakaotech.team14backend.post.application.command.SavePopularPosts;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Sql("classpath:db/testSetup.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'local'}", loadContext = true)
class PointControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private SavePopularPosts saveTemporaryPopularPostListUsecase;


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

  @DisplayName("회원1이 포인트를 사용하여 회원2의 게시판297를 구매하였으나 돈이 부족한 경우- 정상 파라미터")
  @Test
  @WithUserDetails("kakao1")
  void usePopularPost_Test() throws Exception {

    saveTemporaryPopularPostListUsecase.execute();

    UsePointByPopularPostRequestDTO requestDTO = new UsePointByPopularPostRequestDTO(297L, 3);

    ObjectMapper objectMapper = new ObjectMapper();
    String requestBody = objectMapper.writeValueAsString(requestDTO);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(
            "/api/point/popular-post")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("usePopularPost_Test : " + responseBody);

    resultActions.andExpect(jsonPath("$.success").value(false));
    resultActions.andExpect(jsonPath("$.error.code").value("446"));
  }

  @DisplayName("회원1이 포인트를 사용하여 회원2의 게시판22를 구매하였으나 돈이 부족한 경우- 정상 파라미터")
  @Test
  @WithUserDetails("kakao1")
  void usePopularPost_Test_2() throws Exception {

    saveTemporaryPopularPostListUsecase.execute();

    UsePointByPopularPostRequestDTO requestDTO = new UsePointByPopularPostRequestDTO(277L, 1);

    ObjectMapper objectMapper = new ObjectMapper();
    String requestBody = objectMapper.writeValueAsString(requestDTO);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(
            "/api/point/popular-post")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();

    System.out.println("usePopularPost_Test : " + responseBody);

    resultActions.andExpect(jsonPath("$.success").value(true));
  }


}
