CREATE TABLE `post_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `post_id` bigint(20) DEFAULT NULL,
  `tag_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKogo3xicgxxbhoekuj3i4aiatb` (`post_id`),
  KEY `FK98d0eqovrn75s8a74oebe4sn1` (`tag_id`),
  CONSTRAINT `FK98d0eqovrn75s8a74oebe4sn1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  CONSTRAINT `FKogo3xicgxxbhoekuj3i4aiatb` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;