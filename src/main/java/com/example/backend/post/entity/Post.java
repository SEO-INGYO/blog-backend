package com.example.backend.post.entity;

import com.example.backend.category.entity.Category;
import com.example.backend.enums.Status;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
@Data
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

    @OneToMany(mappedBy = "post")
    private List<PostTag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100, nullable = false)
    private Status status;

    @Column(name = "last_modified_user", length = 100, nullable = false)
    private String lastModifyUser;
}