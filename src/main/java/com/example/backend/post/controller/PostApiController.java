package com.example.backend.post.controller;

import com.example.backend.post.dto.PostAllResponse;
import com.example.backend.post.dto.PostDto;
import com.example.backend.post.dto.api.PostReadRequest;
import com.example.backend.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "https://blog.rocd.site")
public class PostApiController {
    private final PostService postService;

    @Tag(name="전체 게시글 조회")
    @Operation(summary = "Read", description = "전체 게시글을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<PostAllResponse>> getPosts(@ModelAttribute PostReadRequest request) {
        List<PostAllResponse> posts = postService.getPosts(request);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Tag(name="특정 게시글 조회")
    @Operation(summary = "Read", description = "특정 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto post = postService.getPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
