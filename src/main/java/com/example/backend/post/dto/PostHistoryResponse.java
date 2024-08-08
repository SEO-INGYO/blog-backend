package com.example.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostHistoryResponse {
    private Long id;
    private Long postId;
    private Timestamp changeTime;
    private String changeUser;
    private String changeType;
    private String oldData;
    private String newData;
}
