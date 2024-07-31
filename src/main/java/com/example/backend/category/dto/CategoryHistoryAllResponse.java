package com.example.backend.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHistoryAllResponse {
    private long rev;
    private long revType;
    private long id;
    private String name;
    private String timestamp;
    private String username;
}
