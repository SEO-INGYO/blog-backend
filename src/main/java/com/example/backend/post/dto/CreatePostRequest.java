package com.example.backend.post.dto;

import com.example.backend.category.entity.Category;
import com.example.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    private String title;
    private String content;
    private Long category;
    private List<String> tags;
}