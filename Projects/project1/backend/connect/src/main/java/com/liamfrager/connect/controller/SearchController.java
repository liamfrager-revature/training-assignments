package com.liamfrager.connect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.entity.SearchResults;
import com.liamfrager.connect.service.SearchService;

/**
 * The REST controller that exposes the search endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@Controller
public class SearchController {
    SearchService searchService;

    /**
     * Constructor for the service controller.
     */
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Handler for the <code>/search</code> <code>GET</code> endpoint.
     */
    @GetMapping("/search")
    private ResponseEntity<SearchResults> search(@RequestHeader("Authorization") String token, @RequestParam String searchQuery) {
        return ResponseEntity.ok(searchService.search(searchQuery, AuthUtil.extractID(token)));
    }
}
