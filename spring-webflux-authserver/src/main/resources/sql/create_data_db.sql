CREATE TABLE `authinfo`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_email`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `user_name`     varchar(30) COLLATE utf8mb4_unicode_ci                       DEFAULT NULL,
    `user_password` varchar(255) COLLATE utf8mb4_unicode_ci                      DEFAULT NULL,
    `non_expired`   tinyint         NOT NULL                                     DEFAULT '1',
    `non_locked`    tinyint         NOT NULL                                     DEFAULT '1',
    `enable`        tinyint         NOT NULL                                     DEFAULT '1',
    `created_date`  date            NOT NULL,
    `authorities`   varchar(200) COLLATE utf8mb4_unicode_ci                      DEFAULT NULL,
    `provider`      varchar(10) COLLATE utf8mb4_unicode_ci                       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`user_email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;