package com.example.backend.category.dto;

import com.example.backend.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAllResponse {
    private Long id;
    private String name;
    private List<Post> posts;
}
