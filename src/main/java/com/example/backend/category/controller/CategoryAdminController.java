package com.example.backend.category.controller;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dto.*;
import com.example.backend.category.entity.Category;
import com.example.backend.category.service.CategoryService;
import com.example.backend.enums.CategoryTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @Tag(name="카테고리 생성")
    @Operation(summary = "Create", description = "새로운 카테고리를 생성합니다.")
    @PostMapping("")
    public String createPost(@ModelAttribute("category") CategoryNameRequest categoryNameRequest, Model model) {
        try {
            BaseResponse baseResponse = categoryService.createCategory(categoryNameRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/categories";
    }

    @Tag(name="전체 카테고리 조회")
    @Operation(summary = "Read", description = "공개된 카테고리를 조회합니다.")
    @GetMapping("")
    public String readCategories(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("categories", categoryService.getAllCategories());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "category/categories";
    }

    @Operation(summary = "Read", description = "카테고리 이력 조회합니다.")
    @GetMapping("/history")
    public String readTagHistory(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("categoryHistory", categoryService.getAllCategoryHistory());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "category/categories_history";
    }

    @Tag(name="카테고리 수정")
    @Operation(summary = "Update", description = "기존 카테고리를 수정합니다.")
    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute("post") CategoryIdNameRequest categoryIdNameRequest, Model model) {
        try {
            categoryIdNameRequest.setId(id);
            BaseResponse baseResponse = categoryService.updateCategory(categoryIdNameRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/categories";
    }

    @Tag(name="카테고리 삭제")
    @Operation(summary = "Delete", description = "카테고리를 삭제합니다.")
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            CategoryIdRequest categoryIdRequest = new CategoryIdRequest();
            categoryIdRequest.setId(id);
            BaseResponse baseResponse = categoryService.deleteCategory(categoryIdRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/categories";
    }

    @Tag(name="카테고리 생성 폼")
    @Operation(summary = "Form", description = "게시글 작성 폼을 보여줍니다.")
    @GetMapping("/new")
    public String createPostForm(Model model) {
        try {
            model.addAttribute("category", new Category());
            model.addAttribute("types", Arrays.asList(CategoryTypeEnum.values()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return "category/create_category";
    }

    @Tag(name="카테고리 수정 폼")
    @Operation(summary = "Form", description = "게시글 수정 폼을 보여줍니다.")
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        try {
            CategoryDto categoryDto = categoryService.getCategory(id);
            if (categoryDto == null) {
                return "redirect:/admin/categories";
            }
            model.addAttribute("category", categoryDto);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "category/edit_category";
    }
}
