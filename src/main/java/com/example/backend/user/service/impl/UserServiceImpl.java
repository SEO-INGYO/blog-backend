package com.example.backend.user.service.impl;

import com.example.backend.user.dao.UserMapper;
import com.example.backend.user.dao.UserRepository;
import com.example.backend.user.dto.UserInfo;
import com.example.backend.user.entity.User;
import com.example.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // JPA Repository
    private final UserMapper userMapper; // MyBatis Mapper
    @Override
    public User findByUsername(String username) {
         return userRepository.findByUsername(username);
    }

    @Override
    public void createUser(UserInfo userInfo) {
        userMapper.createUser(userInfo);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        userMapper.updateUser(userInfo);
    }

    @Override
    public void deleteUser(String username) {
        userMapper.deleteUser(username);
    }
}
