CREATE TABLE tags_aud
(
    id      bigint       not null,
    rev     bigint       not null,
    revtype tinyint      null,
    name    varchar(100) null,
    primary key (rev, id),
    constraint fk_tags_aud_revision
        foreign key (rev) references user_revision (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;