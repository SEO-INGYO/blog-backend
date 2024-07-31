CREATE TABLE comments
(
    id      bigint auto_increment
        primary key,
    content text   not null,
    post_id bigint not null,
    constraint comments_ibfk_1
        foreign key (post_id) references posts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE INDEX post_id
    on comments (post_id);