package com.example.search.controller;

import com.example.search.entity.GeneralResponse;
import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/weather/search")
    public ResponseEntity<GeneralResponse> getDetails() {
        return searchService.search();
    }
}
