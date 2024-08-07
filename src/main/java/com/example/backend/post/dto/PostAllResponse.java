package com.example.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAllResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String status;
    private String lastModifyUser;
    private List<String> tags;
}
