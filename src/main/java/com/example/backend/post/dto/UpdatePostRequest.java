package com.example.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {
    private Long id;
    private String title;
    private String content;
    private Long category;
    private List<String> tags;
}