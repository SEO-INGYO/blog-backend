package com.example.backend.category.service.impl;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dao.CategoryMapper;
import com.example.backend.category.dao.CategoryRepository;
import com.example.backend.category.dto.*;
import com.example.backend.category.entity.Category;
import com.example.backend.category.service.CategoryService;
import com.example.backend.user.dao.UserRepository;
import com.example.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    ModelMapper modelMapper = new ModelMapper();
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
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
            Category category = new Category();
            category.setName(categoryNameRequest.getName());

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

            categoryRepository.save(category);

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
            Category category = categoryRepository.findById(categoryIdNameRequest.getId()).orElseThrow(() -> new RuntimeException("Category not found"));
            category.setName(categoryIdNameRequest.getName());

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

            categoryRepository.save(category);

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
            Category category = categoryRepository.findById(categoryIdRequest.getId()).orElseThrow(() -> new Exception("Category not found"));

            categoryRepository.deleteById(category.getId());

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
