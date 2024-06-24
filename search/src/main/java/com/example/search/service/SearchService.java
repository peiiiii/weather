package com.example.search.service;

import com.example.search.entity.GeneralResponse;
import org.springframework.http.ResponseEntity;

public interface SearchService {
    public ResponseEntity<GeneralResponse> search();
}
