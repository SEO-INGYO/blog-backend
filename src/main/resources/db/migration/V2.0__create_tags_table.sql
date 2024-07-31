CREATE TABLE tags
(
    id   bigint auto_increment
        primary key,
    name varchar(100) not null,
    constraint UK_tags_name
        unique (name),
    constraint name
        unique (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;