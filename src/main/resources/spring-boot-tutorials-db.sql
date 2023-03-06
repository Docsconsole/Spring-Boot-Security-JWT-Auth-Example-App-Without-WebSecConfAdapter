-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.13 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for spring-boot-tutorials-db
CREATE DATABASE IF NOT EXISTS `spring-boot-tutorials-db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `spring-boot-tutorials-db`;

-- Dumping structure for table spring-boot-tutorials-db.app_roles
CREATE TABLE IF NOT EXISTS `app_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table spring-boot-tutorials-db.app_roles: ~2 rows (approximately)
/*!40000 ALTER TABLE `app_roles` DISABLE KEYS */;
INSERT INTO `app_roles` (`id`, `name`) VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_ADMIN'),
	(3, 'ROLE_SUPER_ADMIN');
/*!40000 ALTER TABLE `app_roles` ENABLE KEYS */;

-- Dumping structure for table spring-boot-tutorials-db.app_users
CREATE TABLE IF NOT EXISTS `app_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKspsnwr241e9k9c8p5xl4k45ih` (`username`),
  UNIQUE KEY `UK4vj92ux8a2eehds1mdvmks473` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table spring-boot-tutorials-db.app_users: ~0 rows (approximately)
/*!40000 ALTER TABLE `app_users` DISABLE KEYS */;
INSERT INTO `app_users` (`id`, `email`, `password`, `username`) VALUES
	(3, 'amarsivas.b@gmail.com', '$2a$10$ZdScbMa1uSQyYt4XKiQ89edGZAEkOz9ubB2nnYSj9Taa/Q9mqrVgq', 'Amarsivas');
/*!40000 ALTER TABLE `app_users` ENABLE KEYS */;

-- Dumping structure for table spring-boot-tutorials-db.app_user_roles
CREATE TABLE IF NOT EXISTS `app_user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `app_roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table spring-boot-tutorials-db.app_user_roles: ~2 rows (approximately)
/*!40000 ALTER TABLE `app_user_roles` DISABLE KEYS */;
INSERT INTO `app_user_roles` (`user_id`, `role_id`) VALUES
	(3, 1),
	(3, 3);
/*!40000 ALTER TABLE `app_user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
