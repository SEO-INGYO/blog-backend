package com.example.backend.tag.dao;

import com.example.backend.tag.dto.TagHistoryAllResponse;
import com.example.backend.tag.dto.TagsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagMapper {
    List<TagHistoryAllResponse> getAllTagHistory();
    List<TagsResponse> getAllTags();
}
