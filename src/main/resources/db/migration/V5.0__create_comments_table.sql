CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `last_modified_time` datetime(6) NOT NULL,
  `visible` enum('PUBLISHED','UNPUBLISHED') NOT NULL,
  `created_user_id` bigint(20) NOT NULL,
  `last_modified_user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3penry40606gtq1g98nbxvhvc` (`created_user_id`),
  KEY `FK45yiym4v7w38sg55ggk4x9fs4` (`last_modified_user_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  CONSTRAINT `FK3penry40606gtq1g98nbxvhvc` FOREIGN KEY (`created_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK45yiym4v7w38sg55ggk4x9fs4` FOREIGN KEY (`last_modified_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;