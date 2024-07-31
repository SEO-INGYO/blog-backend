package com.example.backend.tag.dao;

import com.example.backend.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByName(String name);
    List<Tag> findTagsByNameIn(List<String> names);
}
