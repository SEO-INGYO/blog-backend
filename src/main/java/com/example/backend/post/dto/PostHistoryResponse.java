package com.example.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostHistoryResponse {
    private long rev;
    private long revType;
    private long id;
    private String title;
    private String content;
    private String categoryId;
    private String categoryName;
    private String timestamp;
    private String username;
}
