package com.example.backend.comment.service;

import com.example.backend.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();
}
