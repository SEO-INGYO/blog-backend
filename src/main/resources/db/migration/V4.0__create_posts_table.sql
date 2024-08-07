CREATE TABLE `posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `content` text DEFAULT NULL,
  `last_modified_user` varchar(100) NOT NULL,
  `status` enum('CREATE','DELETE','READ','UPDATE') NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKijnwr3brs8vaosl80jg9rp7uc` (`category_id`),
  CONSTRAINT `FKijnwr3brs8vaosl80jg9rp7uc` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;