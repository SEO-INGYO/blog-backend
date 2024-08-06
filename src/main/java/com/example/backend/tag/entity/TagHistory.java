package com.example.backend.tag.entity;

import org.hibernate.annotations.Comment;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="tag_history")
@Data
public class TagHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 20)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "change_time", nullable = false)
    private LocalDateTime changeTime;

    @Column(name = "change_user", length = 100, nullable = false)
    private String changeUser;

    @Column(name = "change_type", length = 100, nullable = false)
    private String changeType;

    @Column(name = "old_data", columnDefinition = "TEXT")
    @Comment("변경 전 Tag 내용 (JSON 형식)")
    private String oldData;

    @Column(name = "new_data", columnDefinition = "TEXT")
    @Comment("변경 후 Tag 내용 (JSON 형식)")
    private String newData;
}