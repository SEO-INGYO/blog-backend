package com.example.backend.comment.entity;

import org.hibernate.annotations.Comment;

import com.example.backend.enums.StatusEnum;

import lombok.Data;
import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name="comment_history")
@Data
public class CommentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 20)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "source_id", length = 100, nullable = false)
    @Comment("원본 Comment의 ID")
    private Long sourceId;

    @Column(name = "change_time", length = 100, nullable = false)
    @Comment("변경 일시")
    private Timestamp changeTime;

    @Column(name = "change_user", length = 100, nullable = false)
    @Comment("변경 사용자")
    private String changeUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100, nullable = false)
    private StatusEnum status;

    @Column(name = "old_content", length = 100, nullable = true)
    private String oldContent;

    @Column(name = "new_content", length = 100, nullable = true)
    private String newContent;
}