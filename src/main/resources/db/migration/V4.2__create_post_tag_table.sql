CREATE TABLE post_tag
(
    id      bigint auto_increment
        primary key,
    post_id bigint not null,
    tag_id  bigint not null,
    constraint post_tag_ibfk_1
        foreign key (post_id) references posts (id),
    constraint FK_post_tag_posts
        foreign key (post_id) references posts (id),
    constraint post_tag_ibfk_2
        foreign key (tag_id) references tags (id),
    constraint FK_post_tag_tags
        foreign key (tag_id) references tags (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;