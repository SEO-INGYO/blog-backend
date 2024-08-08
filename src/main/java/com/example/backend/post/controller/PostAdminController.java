package com.example.backend.post.controller;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dto.CategoriesResponse;
import com.example.backend.category.service.CategoryService;
import com.example.backend.post.dto.CreatePostRequest;
import com.example.backend.post.dto.DeletePostRequest;
import com.example.backend.post.dto.PostDto;
import com.example.backend.post.dto.UpdatePostRequest;
import com.example.backend.post.entity.Post;
import com.example.backend.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/posts")
public class PostAdminController {
    private final PostService postService;
    private final CategoryService categoryService;

    @Operation(summary = "Create", description = "새로운 게시글을 작성합니다.")
    @PostMapping("")
    public String createPost(@ModelAttribute("post") CreatePostRequest createPostRequest, @RequestParam("content") String content, @RequestParam String tags, Model model) {
        try {
            System.out.println(content);
            BaseResponse baseResponse = postService.createPost(createPostRequest, tags);
            model.addAttribute("response", baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/posts";
    }

    @Operation(summary = "Read", description = "전체 게시글을 조회합니다.")
    @GetMapping("")
    public String getAllPosts(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("posts", postService.getAllPosts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "post/posts";
    }
    @Operation(summary = "Read", description = "전체 게시글 이력 조회합니다.")
    @GetMapping("/history")
    public String getPostHistory(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("postHistory", postService.getPostHistory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "post/posts_history";
    }

    @Operation(summary = "Read", description = "상세 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public String getDetailPost(@PathVariable Long id, HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("post", postService.getPost(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "post/detail_post";
    }

    @Operation(summary = "Update", description = "기존 게시글을 수정합니다.")
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute("post") UpdatePostRequest updatePostRequest, Model model) {
        try {
            updatePostRequest.setId(id);
            System.out.println(updatePostRequest);
            BaseResponse baseResponse = postService.updatePost(updatePostRequest);
            System.out.println(baseResponse);
            System.out.println("---------------------결과-------------------------");
            model.addAttribute("response", baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/posts";
    }

    @Operation(summary = "Delete", description = "게시글을 삭제합니다.")
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Model model) {
        try {
            DeletePostRequest deletePostRequest = new DeletePostRequest();
            deletePostRequest.setId(id);
            BaseResponse baseResponse = postService.deletePost(deletePostRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/posts";
    }

    @Operation(summary = "Form", description = "게시글 작성 폼을 보여줍니다.")
    @GetMapping("/new")
    public String createPostForm(Model model) {
        try {
            model.addAttribute("categories" ,categoryService.getAllCategories());
            model.addAttribute("post", new Post());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "post/create_post";
    }

    @Operation(summary = "Form", description = "게시글 수정 폼을 보여줍니다.")
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        try {
            PostDto post = postService.getPost(id);
            if (post == null) {
                return "redirect:/admin/posts";
            }
            List<CategoriesResponse> categories = categoryService.getAllCategories();
            model.addAttribute("post", post);
            model.addAttribute("categories" , categories);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "post/edit_post";
    }
}