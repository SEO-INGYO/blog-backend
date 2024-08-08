package com.example.backend.post.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import com.example.backend.enums.StatusEnum;

@Entity
@Table(name="post_tag_history")
@Data
public class PostTagHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 20)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "post_id", nullable = false)
    @Comment("Post ID")
    private Long postId;

    @Column(name = "tag_id", nullable = false)
    @Comment("Tag ID")
    private Long tagId;

    @Column(name = "change_time", nullable = false)
    @Comment("변경 일시")
    private LocalDateTime changeTime;

    @Column(name = "change_user", length = 100, nullable = false)
    @Comment("변경 사용자")
    private String changeUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100, nullable = false)
    private StatusEnum status;

    @Column(name = "old_data", columnDefinition = "TEXT")
    @Comment("이전 데이터")
    private String oldData;

    @Column(name = "new_data", columnDefinition = "TEXT")
    @Comment("새 데이터")
    private String newData;
}
