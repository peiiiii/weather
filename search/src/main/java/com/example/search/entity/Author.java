package com.example.search.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private long id;
    private String firstName;
    private String lastName;
    @JsonIgnoreProperties("authors")
    private Set<Book> books;
}
