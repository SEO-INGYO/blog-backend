CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) NOT NULL,
  `last_modified_time` datetime(6) NOT NULL,
  `name` varchar(100) NOT NULL,
  `visible` enum('PUBLISHED','UNPUBLISHED') NOT NULL,
  `created_user_id` bigint(20) NOT NULL,
  `last_modified_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt8o6pivur7nn124jehx7cygw5` (`name`),
  KEY `FKd44ckrw5mltoycak34oppfygf` (`created_user_id`),
  KEY `FKneeyj56v8y1k5adifpt3p3erv` (`last_modified_user_id`),
  CONSTRAINT `FKd44ckrw5mltoycak34oppfygf` FOREIGN KEY (`created_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKneeyj56v8y1k5adifpt3p3erv` FOREIGN KEY (`last_modified_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;