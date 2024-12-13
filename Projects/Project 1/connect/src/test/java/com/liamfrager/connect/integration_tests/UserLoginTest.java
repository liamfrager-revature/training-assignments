// package com.liamfrager.connect.integration_tests;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.liamfrager.connect.entity.User;
// import com.liamfrager.connect.repository.UserRepository;

// import com.fasterxml.jackson.databind.ObjectMapper;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class UserLoginTest {
//     @Autowired
//     ObjectMapper objectMapper;

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private UserRepository userRepository;

//     @BeforeEach
//     void setUp() {
//         userRepository.deleteAll();
//     }

//     @Test
//     void createUserSuccessfulTest() throws Exception {
//         User requestBody = new User();
//         requestBody.setUsername("testUser");
//         requestBody.setEmail("test@email.com");
//         requestBody.setPassword("password");

//         User expectedOutput = new User();
//         expectedOutput.setId(1L);
//         expectedOutput.setUsername("testUser");
//         expectedOutput.setEmail("test@email.com");
//         expectedOutput.setPassword("password");

//         mockMvc.perform(post("/register")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(requestBody)))
//             .andExpect(status().isCreated())
//             .andExpect(content().string(objectMapper.writeValueAsString(expectedOutput)));
//     }

//     @Test
//     void createUserFailureTest() throws Exception {
//         User newUser = new User();
//         newUser.setUsername("testUser");
//         newUser.setEmail("test@email.com");
//         newUser.setPassword("abc");

//         mockMvc.perform(post("/register")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(newUser)))
//             .andExpect(status().is(400));
//     }

//     @Test
//     void loginUserSuccessfulTest() throws Exception {
//         String requestBody = "\"username\":\"testUser\",\"email\":\"test@email.com\",\"password\":\"password\"";

//         User expectedOutput = new User();
//         expectedOutput.setId(1L);
//         expectedOutput.setUsername("testUser");
//         expectedOutput.setEmail("test@email.com");
//         expectedOutput.setPassword("password");

//         createUserSuccessfulTest();

//         mockMvc.perform(post("/login")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(requestBody)))
//             .andExpect(status().isOk())
//             .andExpect(content().string(objectMapper.writeValueAsString(expectedOutput)));
//     }

//     @Test
//     void loginUserFailureTest() throws Exception {
//         User newUser = new User();
//         newUser.setUsername("testUser");
//         newUser.setEmail("test@email.com");
//         newUser.setPassword("abc");

//         mockMvc.perform(post("/login")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(newUser)))
//             .andExpect(status().is(400));
//     }
// }
