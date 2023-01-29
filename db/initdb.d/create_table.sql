CREATE TABLE `test_user` (
    `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `reg_time` datetime(6) DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `role` varchar(15) DEFAULT 'ROLE_USER',
    `user_email` varchar(255) DEFAULT NULL,
    `user_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `UK_user_email` (`user_email`),
    UNIQUE KEY `UK_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `todo` (
    `todo_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `reg_time` datetime(6) DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `todo_content` varchar(255) DEFAULT NULL,
    `todo_status` varchar(255) DEFAULT NULL,
    `user_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`todo_id`),
    KEY `FK_user_id` (`user_id`),
    CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `test_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;