package com.example.backend.post.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.*;

@Entity
@Table(name="post_history")
@Data
public class PostHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 20)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "post_id", length = 100, nullable = false)
    @Comment("원본 Post의 ID")
    private Long postId;

    @Column(name = "change_time", length = 100, nullable = false)
    @Comment("변경 일시")
    private Timestamp changeTime;

    @Column(name = "change_user", length = 100, nullable = false)
    @Comment("변경 사용자")
    private String changeUser;

    @Column(name = "change_type", length = 100, nullable = false)
    @Comment("변경 타입")
    private String changeType;

    @Column(name = "old_content", columnDefinition = "TEXT")
    @Comment("이전 Post 내용 (JSON 형식)")
    private String oldPost;

    @Column(name = "new_content", columnDefinition = "TEXT")
    @Comment("새 Post 내용 (JSON 형식)")
    private String newPost;
}
