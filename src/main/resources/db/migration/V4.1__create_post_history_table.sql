CREATE TABLE `post_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `change_time` datetime(6) NOT NULL COMMENT '변경 일시',
  `change_type` varchar(100) NOT NULL COMMENT '변경 타입',
  `change_user` varchar(100) NOT NULL COMMENT '변경 사용자',
  `new_content` text DEFAULT NULL COMMENT '새 Post 내용 (JSON 형식)',
  `old_content` text DEFAULT NULL COMMENT '이전 Post 내용 (JSON 형식)',
  `post_id` bigint(20) NOT NULL COMMENT '원본 Post의 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;