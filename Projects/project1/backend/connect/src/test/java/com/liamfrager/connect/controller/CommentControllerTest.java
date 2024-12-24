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

import java.util.List;
import java.util.Optional;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.service.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private PostRepository postRepository;

    @Test
    void testPostComment() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            Post post = TestData.generatePost();
            Comment comment = TestData.generateNewComment(post);

            mockedStatic.when(() -> AuthUtil.isValidToken(Mockito.anyString())).thenReturn(true);
            mockedStatic.when(() -> AuthUtil.getUserFromToken(Mockito.anyString())).thenReturn(comment.getUser());

            Mockito.when(commentService.postComment(Mockito.any(Comment.class))).thenReturn(comment);
            Mockito.when(postRepository.findById(comment.getPost().getId())).thenReturn(Optional.of(comment.getPost()));

            mockMvc.perform(post("/posts/" + comment.getPost().getId() + "/comments")
                    .header("Authorization", "validToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"content\": \"Content\", \"post\": {\"id\": 1}}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value(comment.getContent()));
        }
    }

    @Test
    void testGetAllComments() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(99L);
            Comment comment = TestData.generateComment();
            List<Comment> comments = List.of(comment);
            Mockito.when(commentService.getAllCommentsByPostID(comment.getPost().getId(), 99L)).thenReturn(comments);

            mockMvc.perform(get("/posts/" + comment.getPost().getId() + "/comments")
                    .header("Authorization", "validToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].content").value(comment.getContent()));
        }
    }

    @Test
    void testDeleteComment() throws Exception {
        Mockito.when(commentService.deleteCommentByID(1L)).thenReturn(1);

        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}