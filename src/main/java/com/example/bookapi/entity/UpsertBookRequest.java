package com.example.bookapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UpsertBookRequest {
    private String title;
    private String author;
    private String categoryName;
}
