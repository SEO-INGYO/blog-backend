package com.example.backend.post.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.post.entity.PostHistory;

public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
    
}
