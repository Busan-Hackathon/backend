package com.example.BusanHackathonProject.controller;

import com.example.BusanHackathonProject.domain.Category;
import com.example.BusanHackathonProject.domain.Post;
import com.example.BusanHackathonProject.dto.postDto.PostRequest;
import com.example.BusanHackathonProject.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    @DisplayName("게시글 생성 - 성공")
    void createPostTest() throws Exception {
        PostRequest postRequest = new PostRequest("테스트 제목", "테스트 내용", 1243);
        Post savedPost = new Post(1L, "테스트 제목", Category.donation, "테스트 내용", 1203, 0);

        when(postService.savePost(any(PostRequest.class))).thenReturn(savedPost);

        mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", postRequest.getTitle())
                        .param("content", postRequest.getContent())
                        .param("targetMoney", String.valueOf(postRequest.getTargetMoney()))) // ✅ targetMoney 변환
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    @DisplayName("게시글 상세 조회 - 성공")
    void getPostDetailTest() throws Exception {
        Post post = new Post(1L, "테스트 제목", "테스트 내용", 123, 0);
        when(postService.deatilPost(1L)).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post"))
                .andExpect(view().name("postDetail"))
                .andExpect(model().attribute("post", post));
    }

    @Test
    @DisplayName("게시글 생성 - 유효성 검증 실패")
    void createPostValidationFailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "")
                        .param("content", "테스트 내용"))
                .andExpect(status().isOk())
                .andExpect(view().name("postForm"));
    }
}
