package com.example.backend.user.entity;

import com.example.backend.user.listener.UserRevisionListener;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Data
@Entity
@RevisionEntity(UserRevisionListener.class)
public class UserRevision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private long id;
    @RevisionTimestamp
    @Column(name = "timestamp")
    private long timestamp;
    private String username;
}