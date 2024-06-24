package com.example.search.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    private int code;
    private Object data;
    private Date timestamp;
}
