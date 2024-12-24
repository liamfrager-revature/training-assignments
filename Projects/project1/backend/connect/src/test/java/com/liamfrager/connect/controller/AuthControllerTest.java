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
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidLoginException;
import com.liamfrager.connect.service.AuthService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void testRegister() throws Exception {
        User user = TestData.generateUser();
        Mockito.when(authService.register(Mockito.any(User.class))).thenReturn(user);
        
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\", \"email\":\"test@email.com\", \"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void testLogin() throws Exception {
        User user = TestData.generateUser();
        Mockito.when(authService.login(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void testIsValidToken() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.isValidToken(Mockito.anyString())).thenReturn(true);

            String token = "Bearer my.secret.token";
            mockMvc.perform(get("/auth")
                    .header("Authorization", token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(true));
        }
    }

    @Test
    void testInvalidLoginException() throws Exception {
        Mockito.when(authService.login(Mockito.any(User.class))).thenThrow(new InvalidLoginException());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());
    }
}
