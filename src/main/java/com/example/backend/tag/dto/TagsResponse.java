package com.example.backend.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsResponse {
    private Long id;
    private String name;
    private String visible;
    private String createdUserName;
    private String createdTime;
    private String lastModifiedUserName;
    private String lastModifiedTime;
}