package com.example.backend.comment.service.impl;

import com.example.backend.comment.dao.CommentRepository;
import com.example.backend.comment.entity.Comment;
import com.example.backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    @Override
    public List<Comment> getAllComments(){
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }
}
