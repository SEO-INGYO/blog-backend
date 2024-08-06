package com.example.backend.post.service.impl;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.category.dao.CategoryRepository;
import com.example.backend.category.dto.CategoryDto;
import com.example.backend.category.entity.Category;
import com.example.backend.post.dao.PostMapper;
import com.example.backend.post.dao.PostRepository;
import com.example.backend.post.dao.PostTagRepository;
import com.example.backend.post.dto.*;
import com.example.backend.post.entity.Post;
import com.example.backend.post.entity.PostTag;
import com.example.backend.post.service.PostService;
import com.example.backend.tag.dao.TagRepository;
import com.example.backend.tag.dto.TagDto;
import com.example.backend.tag.entity.Tag;
import com.example.backend.user.dao.UserRepository;
import com.example.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.utils.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.backend.utils.HTMLUtils.stripHtmlTags;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostTagRepository postTagRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

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
        for (PostHistoryResponse post : posts) {
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

            Post post = new Post();
            post.setTitle(createPostRequest.getTitle());
            post.setContent(HTMLUtils.markdownToHtml(createPostRequest.getContent()));
            post.setCategory(category);

            postRepository.save(post);

            List<String> tagNames = createPostRequest.getTags();
            List<Tag> existingTags = tagRepository.findTagsByNameIn(tagNames);
            List<Tag> finalTags = new ArrayList<>();

            for (String tagName : tagNames) {
                Tag tag = existingTags.stream()
                        .filter(t -> t.getName().equals(tagName))
                        .findFirst()
                        .orElse(null);

                if (tag == null) {
                    tag = new Tag();
                    tag.setName(tagName);
                    tagRepository.save(tag);
                }
                finalTags.add(tag);
            }

            List<PostTag> postTags = new ArrayList<>();
            for (Tag tag : finalTags) {
                PostTag postTag = new PostTag();
                postTag.setPost(post);
                postTag.setTag(tag);
                postTags.add(postTag);
            }
            postTagRepository.saveAll(postTags);

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
    public BaseResponse updatePost(UpdatePostRequest updatePostRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Post post = postRepository.findById(updatePostRequest.getId()).orElseThrow(() -> new RuntimeException("Post not found"));
            post.setTitle(updatePostRequest.getTitle());
            post.setContent(updatePostRequest.getContent());
            Category category = categoryRepository.findCategoryByName(updatePostRequest.getCategory());
            if (category == null) {
                baseResponse.setResultMessage("실패 - 해당 카테고리를 찾을 수 없습니다.");
                baseResponse.setResultCode(00);
                return baseResponse;
            }
            post.setCategory(category);

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

            postRepository.save(post);
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
    public BaseResponse deletePost(DeletePostRequest deletePostRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Post post = postRepository.findById(deletePostRequest.getId()).orElseThrow(() -> new Exception("Post not found"));
            postRepository.save(post);

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
