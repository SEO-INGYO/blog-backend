package com.example.backend.tag.dto;

import com.example.backend.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagAllResponse {
    private Long id;
    private String name;
    private Set<Post> posts;
}
