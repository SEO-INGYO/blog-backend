package com.example.backend.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagHistoryAllResponse {
    private long id;
    private long tagId;
    private LocalDateTime changeTime;
    private String changeUser;
    private String status;
    private String oldData;
    private String newData;
}
