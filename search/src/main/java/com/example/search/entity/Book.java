package com.example.search.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private long id;
    private String title;
    @JsonIgnoreProperties("books")
    private Set<Author> authors;
    private LocalDate publicationDate;
}
