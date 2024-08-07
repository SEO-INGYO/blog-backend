package com.example.backend.post.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.post.entity.PostTagHistory;

public interface PostTagHistoryRepository extends JpaRepository<PostTagHistory, Long> {
    
}
