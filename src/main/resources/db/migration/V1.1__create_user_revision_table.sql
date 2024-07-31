CREATE TABLE user_revision
(
    id        bigint auto_increment
        primary key,
    number    bigint default 0 not null,
    timestamp bigint           not null,
    username  varchar(255)     not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;