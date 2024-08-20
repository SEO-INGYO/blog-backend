package com.example.backend.post.service;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.post.dto.*;
import com.example.backend.post.dto.api.PostReadRequest;

import java.util.List;

public interface PostService {
    List<PostAllResponse> getAllPosts();
    List<PostAllResponse> getPosts(PostReadRequest request);
    List<PostAllResponse> getPublishedPosts(PostReadRequest request);
    List<PostHistoryResponse> getPostHistory();
    PostDto getPost(Long id);
    BaseResponse createPost(CreatePostRequest request, String tags);
    BaseResponse updatePost(UpdatePostRequest request);
    BaseResponse deletePost(DeletePostRequest request);
}
