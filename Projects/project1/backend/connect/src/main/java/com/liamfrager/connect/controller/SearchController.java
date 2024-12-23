package com.liamfrager.connect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.entity.SearchResults;
import com.liamfrager.connect.service.SearchService;

@Controller
public class SearchController {

    SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    private ResponseEntity<SearchResults> search(@RequestHeader("Authorization") String token, @RequestParam String searchQuery) {
        return ResponseEntity.ok(searchService.search(searchQuery, AuthUtil.extractID(token)));
    }
}
