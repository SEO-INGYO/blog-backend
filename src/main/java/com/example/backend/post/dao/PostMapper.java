package com.example.backend.post.dao;

import com.example.backend.post.dto.PostAllResponse;
import com.example.backend.post.dto.PostHistoryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PostMapper {
    List<PostAllResponse> getAllPosts();

    List<PostAllResponse> getPostsByCategory(String category);

    List<PostHistoryResponse> getPostAllHistory();
}
