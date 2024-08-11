package com.example.backend.comment.entity;

import java.time.LocalDateTime;

import com.example.backend.enums.VisibleEnum;
import com.example.backend.post.entity.Post;
import com.example.backend.user.entity.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content",nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;

    @Enumerated(EnumType.STRING)
    @Column(name = "visible", length = 100, nullable = false)
    private VisibleEnum visible;

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "last_modified_user_id", nullable = false)
    private User lastModifiedUser;

    @Column(name = "last_modified_time", nullable = false)
    private LocalDateTime lastModifiedTime;
}
