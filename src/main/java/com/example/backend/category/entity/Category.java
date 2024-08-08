package com.example.backend.category.entity;

import com.example.backend.enums.VisibleEnum;
import com.example.backend.post.entity.Post;
import com.example.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name="categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

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