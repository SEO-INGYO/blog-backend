package com.example.backend.post.entity;

import com.example.backend.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Entity
@Table(name="post_tag")
@Getter
@Setter
@ToString(exclude = {"post", "tag"})
public class PostTag {
    @Id
    @Column(name = "id",length = 20) @Comment("Primary Key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
