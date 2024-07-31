CREATE TABLE comments_aud
(
    id      bigint       not null,
    rev     bigint       not null,
    revtype tinyint      null,
    content varchar(255) null,
    post_id bigint       null,
    primary key (rev, id),
    constraint fk_comments_aud_revision
        foreign key (rev) references user_revision (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;