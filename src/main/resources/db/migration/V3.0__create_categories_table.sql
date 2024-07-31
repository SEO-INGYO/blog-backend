CREATE TABLE categories
(
    id   bigint auto_increment primary key,
    name varchar(100) not null,
    constraint UK_categories_name
    unique (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 collate = utf8mb4_unicode_ci;