package com.example.backend.comment.entity;

import com.example.backend.enums.VisibleEnum;
import com.example.backend.post.entity.Post;
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

    @Column(name = "last_modified_user", length = 100, nullable = false)
    private String lastModifyUser;
}
