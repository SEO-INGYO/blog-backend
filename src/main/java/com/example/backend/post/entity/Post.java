package com.example.backend.post.entity;

import com.example.backend.category.entity.Category;
import com.example.backend.enums.VisibleEnum;
import com.example.backend.user.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name="posts")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"category", "tags", "createdUser", "lastModifiedUser"})
public class Post {
    @Id
    @Column(name = "id",length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Primary Key")
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT", name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<PostTag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "visible", length = 100, nullable = false)
    private VisibleEnum visible;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "last_modified_user_id", nullable = false)
    private User lastModifiedUser;

    @Column(name = "last_modified_time", nullable = false)
    private LocalDateTime lastModifiedTime;
}