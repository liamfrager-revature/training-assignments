package com.liamfrager.connect.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.service.PostService;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    void testCreatePost() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            User user = TestData.generateUser();
            Post post = TestData.generateNewPost(user);

            mockedStatic.when(() -> AuthUtil.getUserFromToken(Mockito.anyString())).thenReturn(user);
            Mockito.when(postService.postPost(Mockito.any(Post.class))).thenReturn(post);

            mockMvc.perform(post("/posts")
                    .header("Authorization", "validToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"content\":\"Content\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.content").value("Content"));
        }
    }

    @Test
    void testGetPostByID() throws Exception {
        Post post = TestData.generatePost();
        Mockito.when(postService.getPostByID(post.getId())).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Content"));
    }

    @Test
    void testUpdatePost() throws Exception {
        Post updatedPost = TestData.generatePost();
        updatedPost.setContent("New Content");
        Mockito.when(postService.updatePostByID(updatedPost.getId(), updatedPost)).thenReturn(1);

        mockMvc.perform(put("/posts/" + updatedPost.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"New Content\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePost() throws Exception {
        Mockito.when(postService.deletePostByID(1L)).thenReturn(1);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
