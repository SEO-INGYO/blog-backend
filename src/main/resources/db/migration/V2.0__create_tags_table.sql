CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `created_time` datetime(6) NOT NULL,
  `last_modified_time` datetime(6) NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` enum('CREATE','DELETE','READ','UPDATE') NOT NULL,
  `created_user_id` bigint(20) NOT NULL,
  `last_modified_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt48xdq560gs3gap9g7jg36kgc` (`name`),
  KEY `FKb4o4pbgxx4u9hgt26uj46342k` (`created_user_id`),
  KEY `FKdjdhcdy8g7v49e7euc4ryhkhy` (`last_modified_user_id`),
  CONSTRAINT `FKb4o4pbgxx4u9hgt26uj46342k` FOREIGN KEY (`created_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKdjdhcdy8g7v49e7euc4ryhkhy` FOREIGN KEY (`last_modified_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='JOIN 테이블';