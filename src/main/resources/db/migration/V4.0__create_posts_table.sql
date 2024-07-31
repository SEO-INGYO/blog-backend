CREATE TABLE posts
(
    id          bigint auto_increment
        primary key,
    title       varchar(255) null,
    content     text         null,
    category_id bigint       not null,
    constraint posts_ibfk_1
        foreign key (category_id) references categories (id),
    constraint FK_posts_categories
        foreign key (category_id) references categories (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;

