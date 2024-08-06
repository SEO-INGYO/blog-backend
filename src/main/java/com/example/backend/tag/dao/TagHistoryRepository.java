package com.example.backend.tag.dao;

import com.example.backend.tag.entity.TagHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagHistoryRepository extends JpaRepository<TagHistory, Long>{
    
}