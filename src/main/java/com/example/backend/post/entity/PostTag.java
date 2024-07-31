package com.example.backend.post.entity;

import com.example.backend.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.envers.Audited;

@Entity
@Table(name="post_tag")
@Data
@Audited
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
