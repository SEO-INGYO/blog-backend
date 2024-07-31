CREATE TABLE users_aud
(
    id       bigint       not null,
    rev      bigint       not null,
    revtype  tinyint      null,
    email    varchar(255) null,
    password varchar(255) null,
    role     varchar(255) null,
    username varchar(255) null,
    primary key (rev, id),
    constraint fk_users_aud_revision
        foreign key (rev) references user_revision (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;

