package com.example.backend.post.dao;

import com.example.backend.post.dto.PostAllResponse;
import com.example.backend.post.dto.PostHistoryResponse;
import com.example.backend.post.dto.api.PostReadRequest;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PostMapper {
    List<PostAllResponse> getAllPosts();

    List<PostAllResponse> getPosts(PostReadRequest request);

    List<PostAllResponse> getPublishedPosts(PostReadRequest request);

    List<PostHistoryResponse> getPostAllHistory();
}
