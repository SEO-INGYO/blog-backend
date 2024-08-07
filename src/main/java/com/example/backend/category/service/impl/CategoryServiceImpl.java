package com.example.backend.category.service.impl;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dao.*;
import com.example.backend.category.dto.*;
import com.example.backend.category.entity.Category;
import com.example.backend.category.entity.CategoryHistory;
import com.example.backend.category.service.CategoryService;
import com.example.backend.enums.Status;
import com.example.backend.tag.entity.Tag;
import com.example.backend.tag.entity.TagHistory;
import com.example.backend.user.dao.UserRepository;
import com.example.backend.user.entity.User;
import com.example.backend.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryHistoryRepository categoryHistoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriesResponse> getAllCategories(){
        return categoryMapper.getCategories();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryHistoryAllResponse> getAllCategoryHistory(){
        return categoryMapper.getAllCategoryHistory();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
    @Override
    @Transactional
    public BaseResponse createCategory(CategoryNameRequest categoryNameRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            }

            if (username == null) {
                baseResponse.setResultMessage("실패 - 작성 시 인증이 필요합니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            User author = userRepository.findByUsername(username);
            if (author == null) {
                baseResponse.setResultMessage("실패 - 작성자를 찾을 수 없습니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            // Tag 엔티티 생성 및 저장
            Category category = new Category();
            category.setName(categoryNameRequest.getName());
            category.setCreatedUser(author);
            category.setCreatedTime(LocalDateTime.now());
            category.setLastModifiedUser(author);
            category.setLastModifiedTime(LocalDateTime.now());
            category.setStatus(Status.CREATE);

            Category savedCategory = categoryRepository.save(category);

            // TagHistory 엔티티 생성 및 저장
            CategoryHistory categoryHistory = new CategoryHistory();
            categoryHistory.setCategoryId(savedCategory.getId());
            categoryHistory.setChangeTime(LocalDateTime.now());
            categoryHistory.setChangeUser(author.getUsername());
            categoryHistory.setChangeType("CREATE");
            categoryHistory.setOldData(null);
            categoryHistory.setNewData(JsonUtils.convertToJsonString("name", savedCategory.getName()));

            categoryHistoryRepository.save(categoryHistory);

            baseResponse.setResultMessage("성공");
            baseResponse.setResultCode(01);
            return baseResponse;
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResultMessage("실패");
            baseResponse.setResultCode(00);
            return baseResponse;
        }
    }

    @Override
    @Transactional
    public BaseResponse updateCategory(CategoryIdNameRequest categoryIdNameRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            }

            if (username == null) {
                baseResponse.setResultMessage("실패 - 작성 시 인증이 필요합니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            User author = userRepository.findByUsername(username);
            if (author == null) {
                baseResponse.setResultMessage("실패 - 작성자를 찾을 수 없습니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            Category category = categoryRepository.findById(categoryIdNameRequest.getId()).orElseThrow(() -> new RuntimeException("태그를 찾을 수 없습니다."));

            String oldData = JsonUtils.convertToJsonString("name", category.getName());

            // 태그 정보 업데이트
            category.setName(categoryIdNameRequest.getName());
            category.setLastModifiedUser(author);
            category.setLastModifiedTime(LocalDateTime.now());

            Category savedCategory = categoryRepository.save(category);

            // 태그 변경 이력 기록
            CategoryHistory categoryHistory = new CategoryHistory();
            categoryHistory.setCategoryId(category.getId());
            categoryHistory.setChangeTime(LocalDateTime.now());
            categoryHistory.setChangeUser(author.getUsername());
            categoryHistory.setChangeType("UPDATE");
            categoryHistory.setOldData(oldData);
            categoryHistory.setNewData(JsonUtils.convertToJsonString("name", savedCategory.getName()));

            categoryHistoryRepository.save(categoryHistory);

            baseResponse.setResultMessage("성공");
            baseResponse.setResultCode(01);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResultMessage("실패");
            baseResponse.setResultCode(00);
        }
        return baseResponse;
    }
    @Override
    @Transactional
    public BaseResponse deleteCategory(CategoryIdRequest categoryIdRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            // 인증 정보에서 사용자 이름 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            }

            // 인증 정보에서 사용자 이름 가져오기
            if (username == null) {
                baseResponse.setResultMessage("실패 - 작성 시 인증이 필요합니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            // 사용자 정보 조회
            User author = userRepository.findByUsername(username);
            if (author == null) {
                baseResponse.setResultMessage("실패 - 작성자를 찾을 수 없습니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            Category category = categoryRepository.findById(categoryIdRequest.getId()).orElseThrow(() -> new Exception("Category not found"));

            // 태그 변경 이력 기록
            CategoryHistory categoryHistory = new CategoryHistory();
            categoryHistory.setCategoryId(category.getId());
            categoryHistory.setChangeTime(LocalDateTime.now());
            categoryHistory.setChangeUser(author.getUsername());
            categoryHistory.setChangeType("DELETE");
            categoryHistory.setOldData(JsonUtils.convertToJsonString("name", category.getName())); // 삭제 전 데이터
            categoryHistory.setNewData(null); // 삭제 후 데이터는 null

            categoryHistoryRepository.save(categoryHistory);

            // 태그 상태 업데이트
            category.setStatus(Status.DELETE); // 상태를 DELETE로 변경
            category.setLastModifiedUser(author);
            category.setLastModifiedTime(LocalDateTime.now());

            baseResponse.setResultMessage("성공");
            baseResponse.setResultCode(01);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResultMessage("실패");
            baseResponse.setResultCode(00);
        }
        return baseResponse;
    }
}
