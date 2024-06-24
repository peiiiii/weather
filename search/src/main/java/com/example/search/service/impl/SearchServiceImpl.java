package com.example.search.service.impl;

import com.example.search.entity.GeneralResponse;
import com.example.search.entity.Book;
import com.example.search.service.SearchService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@Service
public class SearchServiceImpl implements SearchService {

    @Value("${book.server}")
    private String bookURL;

    @Value("${details}")
    private String detailsURL;

    private final RestTemplate restTemplate;
    private final ExecutorService pool;

    public SearchServiceImpl(RestTemplate restTemplate, ExecutorService pool) {
        this.restTemplate = restTemplate;
        this.pool = pool;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<GeneralResponse> search(){
        List<Object> list = new ArrayList<>();
        CompletableFuture<Book[]> bookCF = CompletableFuture.supplyAsync(() -> {
            return restTemplate.getForObject(bookURL + "/api/v1/book", Book[].class);
        }, pool).whenComplete((r, e)-> {
            if (e == null) {
                list.add(r);
            }
        });
        CompletableFuture<String> detailsCF = CompletableFuture.supplyAsync(() -> {
            return restTemplate.getForObject(detailsURL + "/details/port", String.class);
        }, pool).whenComplete((r, e) -> {
            if (e == null) {
                list.add(r);
            }
        });

        CompletableFuture.allOf(bookCF, detailsCF).join();

        GeneralResponse response = new GeneralResponse(200, list, new Date(System.currentTimeMillis()));
        return new ResponseEntity<GeneralResponse>(response, HttpStatus.OK);
    }

    public ResponseEntity<GeneralResponse> fallback(){
        GeneralResponse response = new GeneralResponse(404, "Fallback", new Date(System.currentTimeMillis()));
        return new ResponseEntity<GeneralResponse>(response, HttpStatus.BAD_REQUEST);
    }
}
