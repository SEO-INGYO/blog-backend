package com.example.backend.post.dao;

import com.example.backend.post.entity.PostTag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByPostId(Long id);
}