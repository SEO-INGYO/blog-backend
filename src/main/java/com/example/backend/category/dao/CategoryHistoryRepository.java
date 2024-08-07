package com.example.backend.category.dao;

import com.example.backend.category.entity.CategoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryHistoryRepository extends JpaRepository<CategoryHistory, Long> {
    
}