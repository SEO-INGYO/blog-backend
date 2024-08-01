package com.example.backend.post.service;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.post.dto.*;

import java.util.List;

public interface PostService {
    List<PostAllResponse> getAllPosts();
    List<PostAllResponse> getPostsByCategory(String category);
    List<PostHistoryResponse> getPostHistory();
    PostDto getPost(Long id);
    BaseResponse createPost(CreatePostRequest createPostRequest, String tags);
    BaseResponse updatePost(UpdatePostRequest updatePostRequest);
    BaseResponse deletePost(DeletePostRequest deletePostRequest);
}
