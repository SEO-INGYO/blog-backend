CREATE TABLE post_tag_aud
(
    id      bigint  not null,
    rev     bigint  not null,
    revtype tinyint null,
    post_id bigint  null,
    tag_id  bigint  null,
    primary key (rev, id),
    constraint fk_post_tag_aud_revision
        foreign key (rev) references user_revision (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;