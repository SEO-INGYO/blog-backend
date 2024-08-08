package com.example.backend.category.entity;

import org.hibernate.annotations.Comment;

import com.example.backend.enums.StatusEnum;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="category_history")
@Data
public class CategoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 20)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "category_id", length = 100, nullable = false)
    private Long categoryId;

    @Column(name = "change_time", nullable = false)
    private LocalDateTime changeTime;

    @Column(name = "change_user", length = 100, nullable = false)
    private String changeUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100, nullable = false)
    private StatusEnum status;

    @Column(name = "old_data", columnDefinition = "TEXT")
    @Comment("변경 전 Tag 내용 (JSON 형식)")
    private String oldData;

    @Column(name = "new_data", columnDefinition = "TEXT")
    @Comment("변경 후 Tag 내용 (JSON 형식)")
    private String newData;
}