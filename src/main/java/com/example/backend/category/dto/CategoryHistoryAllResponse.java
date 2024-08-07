package com.example.backend.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHistoryAllResponse {
    private long id;
    private long categoryId;
    private LocalDateTime changeTime;
    private String changeUser;
    private String changeType;
    private String oldData;
    private String newData;
}
