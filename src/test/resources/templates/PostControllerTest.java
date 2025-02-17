package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class) // ✅ PostController만 테스트
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService; // ✅ PostService를 Mock으로 사용

    @Autowired
    private ObjectMapper objectMapper; // ✅ JSON 직렬화/역직렬화를 위한 객체

    /**
     * ✅ 게시글 생성 테스트
     */
    @Test
    @DisplayName("게시글 생성 - 성공")
    public void createPostTest() throws Exception {
        // 📌 Mock 데이터 설정
        PostRequest postRequest = new PostRequest("제목", "내용");
        Post savedPost = new Post(1L, "제목", "내용", null, null);

        when(postService.savePost(any(PostRequest.class))).thenReturn(savedPost);

        // ✅ API 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", postRequest.getTitle())
                        .param("content", postRequest.getContent()))
                .andExpect(status().is3xxRedirection()) // ✅ Redirect 확인
                .andExpect(redirectedUrl("/posts/1"));
    }

    /**
     * ✅ 게시글 상세 조회 테스트
     */
    @Test
    @DisplayName("게시글 상세 조회 - 성공")
    public void getPostDetailTest() throws Exception {
        // 📌 Mock 데이터 설정
        Post post = new Post(1L, "제목", "내용", null, null);
        when(postService.deatilPost(1L)).thenReturn(post);

        // ✅ API 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.get("/post/1"))
                .andExpect(status().isOk()) // ✅ HTTP 200 상태 확인
                .andExpect(model().attributeExists("post")) // ✅ 모델 속성 확인
                .andExpect(view().name("postDetail")) // ✅ 반환되는 View 이름 확인
                .andExpect(MockMvcResultMatchers.model().attribute("post", post));
    }

    /**
     * ✅ 게시글 생성 시 검증 실패 테스트
     */
    @Test
    @DisplayName("게시글 생성 - 유효성 검증 실패")
    public void createPostValidationFailTest() throws Exception {
        // ✅ 제목이 없는 요청 전송
        mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "")
                        .param("content", "내용"))
                .andExpect(status().isOk()) // ✅ 다시 폼 페이지 반환
                .andExpect(view().name("postForm")); // ✅ postForm으로 리턴되는지 확인
    }
}
