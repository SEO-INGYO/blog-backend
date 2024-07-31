CREATE TABLE posts_aud
(
    id          bigint       not null,
    rev         bigint       not null,
    revtype     tinyint      null,
    content     varchar(255) null,
    title       varchar(255) null,
    category_id bigint       null,
    primary key (rev, id),
    constraint fk_posts_aud_revision
        foreign key (rev) references user_revision (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;