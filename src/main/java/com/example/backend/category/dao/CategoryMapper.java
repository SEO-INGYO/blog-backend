package com.example.backend.category.dao;

import com.example.backend.category.dto.CategoriesResponse;
import com.example.backend.category.dto.CategoryHistoryAllResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {
    List<CategoryHistoryAllResponse> getAllCategoryHistory();
    List<CategoriesResponse> getCategories();
}
