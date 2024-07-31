package com.example.backend.user.dto;

import lombok.Data;

@Data
public class UserInfo {
    private String username;
    private String password;
    private String role;
}
