package com.example.backend.category.service;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dto.*;
import com.example.backend.category.entity.Category;
import com.example.backend.tag.dto.TagHistoryAllResponse;

import java.util.List;

public interface CategoryService {
    List<CategoriesResponse> getAllCategories();
    List<CategoryHistoryAllResponse> getAllCategoryHistory();
    CategoryDto getCategory(Long id);
    BaseResponse createCategory(CategoryNameRequest categoryNameRequest);
    BaseResponse updateCategory(CategoryIdNameRequest categoryIdNameRequest);
    BaseResponse deleteCategory(CategoryIdRequest categoryIdRequest);
}
