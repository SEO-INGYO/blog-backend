package com.example.backend.tag.entity;

import com.example.backend.post.entity.PostTag;
import com.example.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import com.example.backend.enums.VisibleEnum;

@Entity
@Table(name="tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    @Comment("JOIN 테이블")
    private List<PostTag> posts = new ArrayList<>();

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