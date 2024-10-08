package com.example.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAllResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String visible;
    private String createdUser;
    private String createdTime;
    private String lastModifiedUser;
    private String lastModifiedTime;
    private String tags;
}
