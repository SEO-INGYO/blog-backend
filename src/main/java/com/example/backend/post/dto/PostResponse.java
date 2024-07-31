package com.example.backend.post.dto;

import com.example.backend.category.entity.Category;
import com.example.backend.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Set<Category> category;
    private Set<Tag> tags;
}