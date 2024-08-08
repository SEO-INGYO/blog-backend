package com.example.backend.post.service.impl;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dao.CategoryRepository;
import com.example.backend.category.dto.CategoryDto;
import com.example.backend.category.entity.Category;
import com.example.backend.enums.Status;
import com.example.backend.post.dao.PostHistoryRepository;
import com.example.backend.post.dao.PostMapper;
import com.example.backend.post.dao.PostRepository;
import com.example.backend.post.dao.PostTagHistoryRepository;
import com.example.backend.post.dao.PostTagRepository;
import com.example.backend.post.dto.*;
import com.example.backend.post.entity.Post;
import com.example.backend.post.entity.PostHistory;
import com.example.backend.post.entity.PostTag;
import com.example.backend.post.entity.PostTagHistory;
import com.example.backend.post.service.PostService;
import com.example.backend.tag.dao.TagRepository;
import com.example.backend.tag.dto.TagDto;
import com.example.backend.tag.dto.TagNameRequest;
import com.example.backend.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.utils.*;
import com.example.backend.tag.service.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.backend.utils.HTMLUtils.stripHtmlTags;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostHistoryRepository postHistoryRepository;
    private final PostTagRepository postTagRepository;
    private final PostTagHistoryRepository postTagHistoryRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;


    @Override
    @Transactional(readOnly = true)
    public List<PostAllResponse> getAllPosts() {
        List<PostAllResponse> posts = postMapper.getAllPosts();
        for (PostAllResponse post : posts) {
            String content = post.getContent();
            if (content == null) {
                post.setContent("");
            } else {
                post.setContent(stripHtmlTags(post.getContent()));
            }
        }
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostAllResponse> getPostsByCategory(String category) {
        List<PostAllResponse> posts = postMapper.getPostsByCategory(category);
        for (PostAllResponse post : posts) {
            String content = post.getContent();
            if (content == null) {
                post.setContent("");
            } else {
                post.setContent(stripHtmlTags(post.getContent()));
            }
        }
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostHistoryResponse> getPostHistory() {
        List<PostHistoryResponse> posts = postMapper.getPostAllHistory();
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("post not found"));

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        String content = post.getContent();
        if (content == null) {
            postDto.setContent("");
        } else {
            postDto.setContent(content);
        }
        
        CategoryDto categoryDto = new CategoryDto(post.getCategory().getId(), post.getCategory().getName());
        postDto.setCategory(categoryDto);

        List<TagDto> tagDtos = post.getTags().stream()
                .map(postTag -> new TagDto(postTag.getTag().getId(), postTag.getTag().getName()))
                .collect(Collectors.toList());
        postDto.setTags(tagDtos);

        return postDto;
    }

    @Override
    @Transactional
    public BaseResponse createPost(CreatePostRequest createPostRequest, String tags){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Category category = categoryRepository.findById(createPostRequest.getCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // 인증 정보에서 사용자 이름 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                currentUser = ((UserDetails) authentication.getPrincipal()).getUsername();
            }

            if (currentUser == null) {
                baseResponse.setResultMessage("실패 - 작성 시 인증이 필요합니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }

            Post newPost = new Post();
            newPost.setTitle(createPostRequest.getTitle());
            newPost.setContent(HTMLUtils.markdownToHtml(createPostRequest.getContent()));
            newPost.setCategory(category);
            newPost.setStatus(Status.CREATE);
            newPost.setLastModifyUser(currentUser);

            Post savedPost = postRepository.save(newPost);

            // PostHistory 생성
            PostHistory postHistory = new PostHistory();
            postHistory.setPostId(savedPost.getId());
            postHistory.setChangeTime(Timestamp.valueOf(LocalDateTime.now()));
            postHistory.setChangeUser(currentUser);
            postHistory.setChangeType("CREATE");
            postHistory.setOldData(""); // 생성이므로 이전 데이터 없음
            Map<String, String> postData = new HashMap<>();
            postData.put("title", savedPost.getTitle());
            List<PostTag> associatedPostTags = savedPost.getTags();
            String associatedTagNames = StringUtils.joinTagNamesWithComma(associatedPostTags);
            postData.put("tag", associatedTagNames);
            postData.put("content", savedPost.getContent());
            postHistory.setNewData(JsonUtils.convertToJsonString(postData));
            postHistoryRepository.save(postHistory);

            List<String> tagNamesList = createPostRequest.getTags();
            List<Tag> existingTags = tagRepository.findTagsByNameIn(tagNamesList);
            List<Tag> finalTagList = new ArrayList<>();

            for (String tagName : tagNamesList) {
                Tag tagEntity = existingTags.stream()
                        .filter(t -> t.getName().equals(tagName))
                        .findFirst()
                        .orElse(null);
        
                if (tagEntity == null) {
                    tagEntity = new Tag();
                    TagNameRequest tagNameRequest = new TagNameRequest();
                    tagNameRequest.setName(tagName);
                    tagEntity = tagService.createTagResponse(tagNameRequest);
                }
                finalTagList.add(tagEntity);
            }

            // PostTag 및 PostTagHistory 생성
            List<PostTag> postTagList = new ArrayList<>();
            for (Tag tagEntity : finalTagList) {
                PostTag postTagEntity = new PostTag();
                postTagEntity.setPost(savedPost);
                postTagEntity.setTag(tagEntity);
                postTagList.add(postTagEntity);

                PostTagHistory postTagHistory = new PostTagHistory();
                postTagHistory.setPostId(savedPost.getId());
                postTagHistory.setTagId(tagEntity.getId());
                postTagHistory.setChangeTime(LocalDateTime.now());
                postTagHistory.setChangeUser(currentUser);
                postTagHistory.setChangeType("ADD");
                postTagHistory.setOldData(""); // 처음 추가이므로 이전 데이터 없음
                postTagHistory.setNewData(JsonUtils.convertToJsonString("tagName", tagEntity.getName()));
                postTagHistoryRepository.save(postTagHistory);
            }
            postTagRepository.saveAll(postTagList);

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
    public BaseResponse updatePost(UpdatePostRequest updatePostRequest) {
        BaseResponse response = new BaseResponse();
        try {
            // 기존 Post 조회
            Post existingPost = postRepository.findById(updatePostRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
    
            // 카테고리 조회
            Category newCategory = categoryRepository.findCategoryByName(updatePostRequest.getCategory());
            if (newCategory == null) {
                response.setResultMessage("실패 - 해당 카테고리를 찾을 수 없습니다.");
                response.setResultCode(00);
                return response;
            }
    
            // 인증 정보에서 사용자 이름 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                currentUser = ((UserDetails) authentication.getPrincipal()).getUsername();
            }
    
            if (currentUser == null) {
                response.setResultMessage("실패 - 작성 시 인증이 필요합니다.");
                response.setResultCode(00);
                return response;
            }
    
            // 기존 데이터 백업 (히스토리 기록용)
            PostHistory postHistory = new PostHistory();
            postHistory.setPostId(existingPost.getId());
            postHistory.setChangeTime(Timestamp.valueOf(LocalDateTime.now()));
            postHistory.setChangeUser(currentUser);
            postHistory.setChangeType("UPDATE");
    
            // 이전 데이터 기록
            Map<String, String> oldData = new HashMap<>();
            oldData.put("title", existingPost.getTitle());
            oldData.put("content", existingPost.getContent());
            String oldTagNames = StringUtils.joinTagNamesWithComma(existingPost.getTags());
            oldData.put("tag", oldTagNames);
            postHistory.setOldData(JsonUtils.convertToJsonString(oldData));
    
            // Post 업데이트
            existingPost.setTitle(updatePostRequest.getTitle());
            existingPost.setContent(HTMLUtils.markdownToHtml(updatePostRequest.getContent()));
            existingPost.setCategory(newCategory);
            existingPost.setLastModifyUser(currentUser);
    
            Post updatedPost = postRepository.save(existingPost);
    
            // 새로운 데이터 기록
            Map<String, String> newData = new HashMap<>();
            newData.put("title", updatedPost.getTitle());
            newData.put("content", updatedPost.getContent());
            String newTagNames = StringUtils.joinTagNamesWithComma(updatedPost.getTags());
            newData.put("tag", newTagNames);
            postHistory.setNewData(JsonUtils.convertToJsonString(newData));
    
            // 히스토리 저장
            postHistoryRepository.save(postHistory);
    
            // 기존 태그와 새로운 태그 비교 및 업데이트
            List<String> newTagNamesList = updatePostRequest.getTags();
            List<Tag> existingTags = tagRepository.findTagsByNameIn(newTagNamesList);
            List<Tag> finalTags = new ArrayList<>();
    
            for (String tagName : newTagNamesList) {
                Tag tagEntity = existingTags.stream()
                        .filter(t -> t.getName().equals(tagName))
                        .findFirst()
                        .orElse(null);
    
                if (tagEntity == null) {
                    tagEntity = new Tag();
                    TagNameRequest tagNameRequest = new TagNameRequest();
                    tagNameRequest.setName(tagName);
                    tagEntity = tagService.createTagResponse(tagNameRequest);
                }
                finalTags.add(tagEntity);
            }
    
            // 기존 PostTag 삭제 및 PostTagHistory 기록
            List<PostTag> existingPostTags = postTagRepository.findByPostId(updatedPost.getId());
            for (PostTag existingPostTag : existingPostTags) {
                PostTagHistory postTagHistory = new PostTagHistory();
                postTagHistory.setPostId(updatedPost.getId());
                postTagHistory.setTagId(existingPostTag.getTag().getId());
                postTagHistory.setChangeTime(LocalDateTime.now());
                postTagHistory.setChangeUser(currentUser);
                postTagHistory.setChangeType("REMOVE");
                postTagHistory.setOldData(JsonUtils.convertToJsonString("tagName", existingPostTag.getTag().getName()));
                postTagHistory.setNewData(""); // 삭제이므로 새로운 데이터 없음
                postTagHistoryRepository.save(postTagHistory);
    
                postTagRepository.delete(existingPostTag);
            }
    
            // 새로운 PostTag 생성 및 PostTagHistory 기록
            List<PostTag> newPostTags = new ArrayList<>();
            for (Tag tagEntity : finalTags) {
                PostTag postTagEntity = new PostTag();
                postTagEntity.setPost(updatedPost);
                postTagEntity.setTag(tagEntity);
                newPostTags.add(postTagEntity);
    
                PostTagHistory postTagHistory = new PostTagHistory();
                postTagHistory.setPostId(updatedPost.getId());
                postTagHistory.setTagId(tagEntity.getId());
                postTagHistory.setChangeTime(LocalDateTime.now());
                postTagHistory.setChangeUser(currentUser);
                postTagHistory.setChangeType("ADD");
                postTagHistory.setOldData(""); // 새로 추가된 태그이므로 이전 데이터 없음
                postTagHistory.setNewData(JsonUtils.convertToJsonString("tagName", tagEntity.getName()));
                postTagHistoryRepository.save(postTagHistory);
            }
            postTagRepository.saveAll(newPostTags);
    
            response.setResultMessage("성공");
            response.setResultCode(01);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResultMessage("실패");
            response.setResultCode(00);
        }
        return response;
    }    

    @Override
    @Transactional
    public BaseResponse deletePost(DeletePostRequest deletePostRequest) {
        BaseResponse response = new BaseResponse();
        try {
            // 삭제할 Post 조회
            Post post = postRepository.findById(deletePostRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
    
            // 인증 정보에서 사용자 이름 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = null;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                currentUser = ((UserDetails) authentication.getPrincipal()).getUsername();
            }
    
            if (currentUser == null) {
                response.setResultMessage("실패 - 삭제 시 인증이 필요합니다.");
                response.setResultCode(00);
                return response;
            }
    
            // PostHistory 생성
            PostHistory postHistory = new PostHistory();
            postHistory.setPostId(post.getId());
            postHistory.setChangeTime(Timestamp.valueOf(LocalDateTime.now()));
            postHistory.setChangeUser(currentUser);
            postHistory.setChangeType("DELETE");
    
            // 이전 데이터 기록
            Map<String, String> oldData = new HashMap<>();
            oldData.put("title", post.getTitle());
            oldData.put("content", post.getContent());
            String oldTagNames = StringUtils.joinTagNamesWithComma(post.getTags());
            oldData.put("tag", oldTagNames);
            postHistory.setOldData(JsonUtils.convertToJsonString(oldData));
    
            // 소프트 삭제 처리
            post.setStatus(Status.DELETE);  // 'DELETE' 상태로 변경
            post.setLastModifyUser(currentUser);
            postRepository.save(post);
    
            // 새로운 데이터는 소프트 삭제 후에는 없으므로 빈 데이터로 설정
            postHistory.setNewData(""); // 삭제 후에는 새로운 데이터가 없음
            postHistoryRepository.save(postHistory);
    
            // 기존 PostTag의 히스토리 기록 (PostTagHistory)
            List<PostTag> existingPostTags = postTagRepository.findByPostId(post.getId());
            for (PostTag postTag : existingPostTags) {
                PostTagHistory postTagHistory = new PostTagHistory();
                postTagHistory.setPostId(post.getId());
                postTagHistory.setTagId(postTag.getTag().getId());
                postTagHistory.setChangeTime(LocalDateTime.now());
                postTagHistory.setChangeUser(currentUser);
                postTagHistory.setChangeType("DELETE");
                postTagHistory.setOldData(JsonUtils.convertToJsonString("tagName", postTag.getTag().getName()));
                postTagHistory.setNewData(""); // 삭제된 후의 데이터는 없음
                postTagHistoryRepository.save(postTagHistory);
            }
    
            response.setResultMessage("성공");
            response.setResultCode(01);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResultMessage("실패");
            response.setResultCode(00);
        }
        return response;
    }

}
