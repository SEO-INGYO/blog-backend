package com.example.backend.post.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReadRequest {
    private String category;
    private List<String> tag;
    private Integer limit;
    private String sortType; // 정렬 타입 추가 ("latest", "oldest", "none")
}