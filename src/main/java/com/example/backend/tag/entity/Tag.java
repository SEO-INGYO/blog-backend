package com.example.backend.tag.entity;

import com.example.backend.post.entity.PostTag;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@Audited
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> posts = new ArrayList<>();
}