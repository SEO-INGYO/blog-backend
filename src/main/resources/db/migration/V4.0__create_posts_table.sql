CREATE TABLE `posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `content` text DEFAULT NULL,
  `created_time` datetime(6) NOT NULL,
  `last_modified_time` datetime(6) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `visible` enum('PUBLISHED','UNPUBLISHED') NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `created_user_id` bigint(20) NOT NULL,
  `last_modified_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKijnwr3brs8vaosl80jg9rp7uc` (`category_id`),
  KEY `FKld60dvpudryik7uvssnjjbjjf` (`created_user_id`),
  KEY `FKsx3u648k9vhhfqd1trx9109xr` (`last_modified_user_id`),
  CONSTRAINT `FKijnwr3brs8vaosl80jg9rp7uc` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FKld60dvpudryik7uvssnjjbjjf` FOREIGN KEY (`created_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsx3u648k9vhhfqd1trx9109xr` FOREIGN KEY (`last_modified_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;