package com.example.backend.user.service;
import com.example.backend.user.dto.UserInfo;
import com.example.backend.user.entity.User;

public interface UserService {
    User findByUsername(String username);
    void createUser(UserInfo userInfo);
    void updateUser(UserInfo userInfo);
    void deleteUser(String username);
}