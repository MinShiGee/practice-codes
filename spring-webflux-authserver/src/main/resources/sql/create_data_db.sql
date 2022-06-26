CREATE TABLE `tblAuthInfoes` (
                                 `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                                 `user_email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 `non_expired` tinyint(1) NOT NULL DEFAULT '0',
                                 `non_locked` tinyint(1) NOT NULL DEFAULT '0',
                                 `created_date` date DEFAULT NULL,
                                 `authorities` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ROLE_GUEST',
                                 `enable` tinyint(1) NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `tblLocalAccounts` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                                    `user_email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    `user_password` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    `fk_authinfoes` bigint unsigned NOT NULL,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `user_email` (`user_email`),
                                    KEY `fk_authinfoes` (`fk_authinfoes`),
                                    CONSTRAINT `tblLocalAccounts_ibfk_1` FOREIGN KEY (`fk_authinfoes`) REFERENCES `tblAuthInfoes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `tblOAuth2Accounts` (
                                     `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                                     `code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                     `provider` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'GOOGLE',
                                     `user_email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                     `fk_authinfoes` bigint unsigned DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `code` (`code`),
                                     UNIQUE KEY `user_email` (`user_email`),
                                     KEY `fk_authinfoes` (`fk_authinfoes`),
                                     CONSTRAINT `tblOAuth2Accounts_ibfk_1` FOREIGN KEY (`fk_authinfoes`) REFERENCES `tblAuthInfoes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;