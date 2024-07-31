package com.example.backend.tag.service;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.tag.dto.*;

import java.util.List;

public interface TagService {
    List<TagsResponse> getAllTags();
    List<TagHistoryAllResponse> getAllTagHistory();
    TagDto getTags(Long id);
    BaseResponse createTag(TagNameRequest tagNameRequest);
    BaseResponse updateTag(TagIdNameRequest tagIdNameRequest);
    BaseResponse deleteTag(TagIdRequest tagIdRequest);
}
