package com.kakaotech.team14backend.outer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.inner.image.model.Image;
import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;
import com.kakaotech.team14backend.inner.post.usecase.FindPostListUsecase;
import com.kakaotech.team14backend.outer.post.dto.GetPostResponseDTO;
import com.kakaotech.team14backend.outer.post.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class PostControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  @Autowired
  private PostService postService;
  @Autowired
  private FindPostListUsecase findPostListUsecase;

  @DisplayName("홈 피드를 조회한다")
  @Test
  void findAllHomeFeed_Test() throws Exception {
    // Prepare test data
    List<GetPostResponseDTO> postResponseDTOs = new ArrayList<>();

    for (int i = 1; i <= 10; i++) {
      Post post = Post.createPost(new Member("username" + i, "kakaoId" + i),
          new Image("imageUri" + i), "nickname" + i, true, "hashtag" + i, "univ");

      GetPostResponseDTO dto = new GetPostResponseDTO(post.getPostId(),
          post.getImage().getImageUri(), List.of("hashtag" + i), 0, 0, post.getNickname());
      postResponseDTOs.add(dto);  // Add the dto to the list
    }
    List<GetPostResponseDTO> mockResponse = new ArrayList<>(postResponseDTOs);
    when(postService.getPostList()).thenReturn(mockResponse);

    // Perform the behavior being tested
    ResultActions resultActions = mockMvc.perform(
        get("/api/post").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("Join Test: " + responseBody);

    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.success").value(true));
    resultActions.andExpect(jsonPath("$.response").exists());
  }
}
