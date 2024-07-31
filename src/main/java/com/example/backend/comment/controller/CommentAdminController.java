package com.example.backend.comment.controller;

import com.example.backend.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/comments")
public class CommentAdminController {
    private final CommentService commentService;

    @Tag(name="전체 댓글 조회")
    @Operation(summary = "Read", description = "전체 댓글 조회")
    @GetMapping("")
    public String readCategories(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("comments", commentService.getAllComments());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "comment/comments";
    }
}
