CREATE TABLE `comment_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `change_time` datetime(6) NOT NULL COMMENT '변경 일시',
  `change_type` varchar(100) NOT NULL COMMENT '변경 타입',
  `change_user` varchar(100) NOT NULL COMMENT '변경 사용자',
  `new_content` varchar(100) DEFAULT NULL,
  `old_content` varchar(100) DEFAULT NULL,
  `source_id` bigint(20) NOT NULL COMMENT '원본 Comment의 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;