package com.example.backend.user.dao;

import com.example.backend.user.dto.UserInfo;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (username, password, role) VALUES (#{username}, #{password}, #{role})")
    void createUser(UserInfo userInfo);

    @Update("UPDATE users SET password = #{password}, role = #{role} WHERE username = #{username}")
    void updateUser(UserInfo userInfo);

    @Delete("DELETE FROM users WHERE username = #{username}")
    void deleteUser(String username);
}
