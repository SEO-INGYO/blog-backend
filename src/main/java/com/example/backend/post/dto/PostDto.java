package com.example.backend.post.dto;

import com.example.backend.category.dto.CategoryDto;
import com.example.backend.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private CategoryDto category;
    private List<TagDto> tags;
}
