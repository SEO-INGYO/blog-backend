package com.example.backend.category.dao;

import com.example.backend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String category);
    Category findCategoryById(Long category);
    Category findById(Category category);
}
