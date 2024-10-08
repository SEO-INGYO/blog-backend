package com.example.backend.tag.service.impl;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.enums.StatusEnum;
import com.example.backend.enums.VisibleEnum;
import com.example.backend.tag.dao.*;
import com.example.backend.tag.dto.*;
import com.example.backend.tag.entity.Tag;
import com.example.backend.tag.entity.TagHistory;
import com.example.backend.tag.service.TagService;
import com.example.backend.user.dao.UserRepository;
import com.example.backend.user.entity.User;
import com.example.backend.utils.JsonUtils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    ModelMapper modelMapper = new ModelMapper();
    private final TagRepository tagRepository;
    private final TagHistoryRepository tagHistoryRepository;
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
    @Transactional(readOnly = true)
    public TagDto getTag(Long id) {
        // 인증 정보에서 사용자 이름 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        // 사용자 정보 조회
        User author = userRepository.findByUsername(username);

        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("태그를 찾을 수 없습니다."));

        // 기존 태그 정보를 저장하기 위해 oldData 준비
        String oldData = tag.getName();

        // 태그 변경 이력 기록
        TagHistory tagHistory = new TagHistory();
        tagHistory.setTagId(tag.getId());
        tagHistory.setChangeTime(LocalDateTime.now());
        tagHistory.setChangeUser(author.getUsername());
        tagHistory.setStatus(StatusEnum.READED);
        tagHistory.setOldData(oldData);
        tagHistory.setNewData(oldData);

        tagHistoryRepository.save(tagHistory);

        TagDto tagDto = modelMapper.map(tag, TagDto.class);
        return tagDto;
    }

    @Override
    @Transactional
    public BaseResponse createTag(TagNameRequest tagNameRequest){
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

            // Tag 엔티티 생성 및 저장
            Tag tag = new Tag();
            tag.setName(tagNameRequest.getName());
            tag.setCreatedUser(author);
            tag.setCreatedTime(LocalDateTime.now());
            tag.setLastModifiedUser(author);
            tag.setLastModifiedTime(LocalDateTime.now());
            tag.setVisible(VisibleEnum.PUBLISHED);

            Tag savedTag = tagRepository.save(tag);

            // TagHistory 엔티티 생성 및 저장
            TagHistory tagHistory = new TagHistory();
            tagHistory.setTagId(savedTag.getId());
            tagHistory.setChangeTime(LocalDateTime.now());
            tagHistory.setChangeUser(author.getUsername());
            tagHistory.setStatus(StatusEnum.CREATED);
            tagHistory.setOldData(null);
            tagHistory.setNewData(JsonUtils.convertToJsonString("name", savedTag.getName()));

            tagHistoryRepository.save(tagHistory);

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

            Tag tag = tagRepository.findById(tagIdNameRequest.getId()).orElseThrow(() -> new RuntimeException("태그를 찾을 수 없습니다."));

            // 기존 태그 정보를 저장하기 위해 oldData 준비
            String oldData = JsonUtils.convertToJsonString("name", tag.getName());

            // 태그 정보 업데이트
            tag.setName(tagIdNameRequest.getName());
            tag.setLastModifiedUser(author);
            tag.setLastModifiedTime(LocalDateTime.now());

            Tag savedTag = tagRepository.save(tag);

            // 태그 변경 이력 기록
            TagHistory tagHistory = new TagHistory();
            tagHistory.setTagId(tag.getId());
            tagHistory.setChangeTime(LocalDateTime.now());
            tagHistory.setChangeUser(author.getUsername());
            tagHistory.setStatus(StatusEnum.UPDATEED);
            tagHistory.setOldData(oldData);
            tagHistory.setNewData(JsonUtils.convertToJsonString("name", savedTag.getName()));

            tagHistoryRepository.save(tagHistory);

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

            Tag tag = tagRepository.findById(idRequest.getId()).orElseThrow(() -> new Exception("태그를 찾을 수 없습니다."));

            // 태그 변경 이력 기록
            TagHistory tagHistory = new TagHistory();
            tagHistory.setTagId(tag.getId());
            tagHistory.setChangeTime(LocalDateTime.now());
            tagHistory.setChangeUser(author.getUsername());
            tagHistory.setStatus(StatusEnum.DELETEED);
            tagHistory.setOldData(JsonUtils.convertToJsonString("name", tag.getName())); // 삭제 전 데이터
            tagHistory.setNewData(null); // 삭제 후 데이터는 null

            tagHistoryRepository.save(tagHistory);

            // 태그 상태 업데이트
            tag.setVisible(VisibleEnum.UNPUBLISHED); // 상태를 DELETE로 변경
            tag.setLastModifiedUser(author);
            tag.setLastModifiedTime(LocalDateTime.now());

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
    public Tag createTagResponse(TagNameRequest tagNameRequest){
        try {
            // 인증 정보에서 사용자 이름 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            }

            // 사용자 정보 조회
            User author = userRepository.findByUsername(username);

            // Tag 엔티티 생성 및 저장
            Tag tag = new Tag();
            tag.setName(tagNameRequest.getName());
            tag.setCreatedUser(author);
            tag.setCreatedTime(LocalDateTime.now());
            tag.setLastModifiedUser(author);
            tag.setLastModifiedTime(LocalDateTime.now());
            tag.setVisible(VisibleEnum.PUBLISHED);

            Tag savedTag = tagRepository.save(tag);

            // TagHistory 엔티티 생성 및 저장
            TagHistory tagHistory = new TagHistory();
            tagHistory.setTagId(savedTag.getId());
            tagHistory.setChangeTime(LocalDateTime.now());
            tagHistory.setChangeUser(author.getUsername());
            tagHistory.setStatus(StatusEnum.CREATED);
            tagHistory.setOldData(null);
            tagHistory.setNewData(JsonUtils.convertToJsonString("name", savedTag.getName()));

            tagHistoryRepository.save(tagHistory);

            return savedTag;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}