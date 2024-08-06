package com.example.backend.post.entity;

import com.example.backend.category.entity.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
@Data
@Audited
public class Post {
    @Id
    @Column(name = "id",length = 20) @Comment("Primary Key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255) @Comment("Post Title")
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT", name = "content") @Comment("Post Content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<PostTag> tags = new ArrayList<>();
}