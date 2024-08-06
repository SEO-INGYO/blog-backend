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
    private String status;
    private Long createdUserName;
    private String createdTime;
    private Long lastModifiedUserName;
    private String lastModifiedTime;
}
