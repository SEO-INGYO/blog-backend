CREATE TABLE `post_tag_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `change_time` datetime(6) NOT NULL COMMENT '변경 일시',
  `change_user` varchar(100) NOT NULL COMMENT '변경 사용자',
  `new_data` text DEFAULT NULL COMMENT '새 데이터',
  `old_data` text DEFAULT NULL COMMENT '이전 데이터',
  `post_id` bigint(20) NOT NULL COMMENT 'Post ID',
  `status` enum('CREATED','DELETEED','READED','UPDATEED') NOT NULL,
  `tag_id` bigint(20) NOT NULL COMMENT 'Tag ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;