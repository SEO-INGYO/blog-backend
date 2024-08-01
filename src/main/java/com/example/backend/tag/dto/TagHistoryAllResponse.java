package com.example.backend.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagHistoryAllResponse {
    private long rev;
    private long revType;
    private long id;
    private String name;
    private String timestamp;
    private String username;
}
