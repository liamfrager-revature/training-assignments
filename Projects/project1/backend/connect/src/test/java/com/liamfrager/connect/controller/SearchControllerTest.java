package com.liamfrager.connect.controller;

import com.liamfrager.connect.entity.SearchResults;
import com.liamfrager.connect.service.SearchService;
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

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SearchService searchService;

    @Test
    void testSearch() throws Exception {
        try (MockedStatic<AuthUtil> mockedStatic = mockStatic(AuthUtil.class)) {
            mockedStatic.when(() -> AuthUtil.extractID(Mockito.anyString())).thenReturn(99L);

            SearchResults searchResults = TestData.generateSearchResults();
            when(searchService.search(Mockito.anyString(), Mockito.anyLong())).thenReturn(searchResults);

            mockMvc.perform(get("/search")
                    .header("Authorization", "validToken")
                    .param("searchQuery", "test"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.users").isArray())
                    .andExpect(jsonPath("$.users.size()").value(searchResults.getUsers().size()))
                    .andExpect(jsonPath("$.posts").isArray())
                    .andExpect(jsonPath("$.posts.size()").value(searchResults.getPosts().size()))
                    .andReturn();
        }
    }
}