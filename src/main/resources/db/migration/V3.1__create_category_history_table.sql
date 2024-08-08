CREATE TABLE `category_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `category_id` bigint(20) NOT NULL,
  `change_time` datetime(6) NOT NULL,
  `change_user` varchar(100) NOT NULL,
  `new_data` text DEFAULT NULL COMMENT '변경 후 Tag 내용 (JSON 형식)',
  `old_data` text DEFAULT NULL COMMENT '변경 전 Tag 내용 (JSON 형식)',
  `status` enum('CREATED','DELETEED','READED','UPDATEED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;