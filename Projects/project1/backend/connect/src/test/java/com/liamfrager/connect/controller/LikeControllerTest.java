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

import java.util.Map;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.service.LikeService;

@SpringBootTest
@AutoConfigureMockMvc
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LikeService likeService;

    @SuppressWarnings("unchecked")
    @Test
    void testPostLike() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            User user = TestData.generateUser();
            Like like = TestData.generatePostLike();

            mockedStatic.when(() -> AuthUtil.getUserFromToken(Mockito.anyString())).thenReturn(user);
            Mockito.when(likeService.postLike(Mockito.any(Map.class), Mockito.any(User.class))).thenReturn(like);

            mockMvc.perform(post("/likes")
                    .header("Authorization", "validToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"postId\":1}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("" + like.getId()));
        }
    }

    @Test
    void testDeleteLike() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            User user = TestData.generateUser();
            Like like = TestData.generateCommentLike();
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(user.getId());
            Mockito.when(likeService.deleteLike(1L, user.getId())).thenReturn(1);

            mockMvc.perform(delete("/likes/" + like.getId())
                    .header("Authorization", "validToken"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("1"));
        }
    }
}
