package com.example.backend.tag.service.impl;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.tag.dao.TagMapper;
import com.example.backend.tag.dao.TagRepository;
import com.example.backend.tag.dto.*;
import com.example.backend.tag.entity.Tag;
import com.example.backend.tag.service.TagService;
import com.example.backend.user.dao.UserRepository;
import com.example.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    ModelMapper modelMapper = new ModelMapper();
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public List<TagsResponse> getAllTags(){
        return tagMapper.getAllTags();
    }
    @Override
    @Transactional(readOnly = true)
    public List<TagHistoryAllResponse> getAllTagHistory(){
        return tagMapper.getAllTagHistory();
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTags(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("태그를 찾을 수 없습니다."));
        TagDto tagDto = modelMapper.map(tag, TagDto.class);
        return tagDto;
    }

    @Override
    @Transactional
    public BaseResponse createTag(TagNameRequest tagNameRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Tag tag = new Tag();
            tag.setName(tagNameRequest.getName());

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

            tagRepository.save(tag);

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
    public BaseResponse updateTag(TagIdNameRequest tagIdNameRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Tag tag = tagRepository.findById(tagIdNameRequest.getId()).orElseThrow(() -> new RuntimeException("태그를 찾을 수 없습니다."));
            tag.setName(tagIdNameRequest.getName());

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

            tagRepository.save(tag);

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
    public BaseResponse deleteTag(TagIdRequest idRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Tag tag = tagRepository.findById(idRequest.getId()).orElseThrow(() -> new Exception("태그를 찾을 수 없습니다."));

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

            tagRepository.deleteById(tag.getId());

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