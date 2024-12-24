package com.liamfrager.connect.controller;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.service.UserService;
import com.liamfrager.connect.service.PostService;
import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.TestData;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PostService postService;

    @Test
    void testGetUser() throws Exception {
        User user = TestData.generateUser();

        when(userService.getUser(Mockito.anyLong())).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void testGetAllPostsByUserID() throws Exception {
        Post post = TestData.generatePost();

        List<Post> posts = List.of(post);
        when(postService.getAllPostsByUserID(Mockito.anyLong())).thenReturn(posts);

        mockMvc.perform(get("/users/1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(post.getContent()));
    }

    @Test
    void testFollowUser() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(99L);
            doNothing().when(userService).followUser(Mockito.anyLong(), Mockito.anyLong());

            mockMvc.perform(post("/users/2/follow")
                    .header("Authorization", "validToken"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testUnfollowUser() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(99L);
            doNothing().when(userService).unfollowUser(Mockito.anyLong(), Mockito.anyLong());

            mockMvc.perform(delete("/users/2/follow")
                    .header("Authorization", "validToken"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testIsFollowing() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(99L);
            when(userService.isFollowing(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

            mockMvc.perform(get("/users/1/follow")
                    .header("Authorization", "validToken"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }
    }

    @Test
    void testUpdateUser() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(99L);
            when(userService.updateUser(Mockito.anyLong(), Mockito.any(User.class))).thenReturn(1);

            mockMvc.perform(put("/users")
                    .header("Authorization", "validToken")
                    .contentType("application/json")
                    .content("{\"username\":\"username\",\"email\":\"email@test.com\",\"password\":\"password\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("1"));
        }
    }
}
