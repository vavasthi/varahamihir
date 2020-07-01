-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: localhost    Database: varahamihir
-- ------------------------------------------------------
-- Server version	8.0.20-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `client_id` varchar(64) NOT NULL,
  `access_token_validity` int DEFAULT NULL,
  `auto_approve` bit(1) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES ('supersecretclient',500,_binary '','$e0801$nU3fiEmEzCp0LtU12SQF2kPtzslxPvbbhDYi9frckKV6fCrhMxZ2rjs/2JHdlM5CLaft97mMbwLK5Y9wDHMbcA==$sRkpjdNxt8Ejx9aJ0U4lPC4lY45KXg0R49QnMFGnXeQ=',1000);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_addl_info`
--

DROP TABLE IF EXISTS `clients_addl_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_addl_info` (
  `client_id` varchar(64) NOT NULL,
  `k` varchar(255) DEFAULT NULL,
  `v` varchar(255) DEFAULT NULL,
  KEY `FK6o27eo3fsf7cycdvxrq5nwwkm` (`client_id`),
  CONSTRAINT `FK6o27eo3fsf7cycdvxrq5nwwkm` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_addl_info`
--

LOCK TABLES `clients_addl_info` WRITE;
/*!40000 ALTER TABLE `clients_addl_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `clients_addl_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_authorities`
--

DROP TABLE IF EXISTS `clients_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_authorities` (
  `client_id` varchar(64) NOT NULL,
  `authorities` tinyblob,
  KEY `FKqld3s3ts8yu3vlmnpkwl0jheg` (`client_id`),
  CONSTRAINT `FKqld3s3ts8yu3vlmnpkwl0jheg` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_authorities`
--

LOCK TABLES `clients_authorities` WRITE;
/*!40000 ALTER TABLE `clients_authorities` DISABLE KEYS */;
INSERT INTO `clients_authorities` VALUES ('supersecretclient',_binary '¬\í\0sr\0@com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthorityIf\Ø\Æ\Ùh\0L\0	authorityt\0Ljava/lang/String;xpt\0STUDENT'),('supersecretclient',_binary '¬\í\0sr\0@com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthorityIf\Ø\Æ\Ùh\0L\0	authorityt\0Ljava/lang/String;xpt\0GUARDIAN'),('supersecretclient',_binary '¬\í\0sr\0@com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthorityIf\Ø\Æ\Ùh\0L\0	authorityt\0Ljava/lang/String;xpt\0ADMIN');
/*!40000 ALTER TABLE `clients_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_authorized_grant_types`
--

DROP TABLE IF EXISTS `clients_authorized_grant_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_authorized_grant_types` (
  `client_id` varchar(64) NOT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  KEY `FKc6237qwoo3q8wmk6mkrt5evcy` (`client_id`),
  CONSTRAINT `FKc6237qwoo3q8wmk6mkrt5evcy` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_authorized_grant_types`
--

LOCK TABLES `clients_authorized_grant_types` WRITE;
/*!40000 ALTER TABLE `clients_authorized_grant_types` DISABLE KEYS */;
INSERT INTO `clients_authorized_grant_types` VALUES ('supersecretclient','refresh_token'),('supersecretclient','password'),('supersecretclient','client_credentials'),('supersecretclient','authorization_code');
/*!40000 ALTER TABLE `clients_authorized_grant_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_redirect_uri`
--

DROP TABLE IF EXISTS `clients_redirect_uri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_redirect_uri` (
  `client_id` varchar(64) NOT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  KEY `FKkakvevehwvyh3c6kl0vpvot3l` (`client_id`),
  CONSTRAINT `FKkakvevehwvyh3c6kl0vpvot3l` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_redirect_uri`
--

LOCK TABLES `clients_redirect_uri` WRITE;
/*!40000 ALTER TABLE `clients_redirect_uri` DISABLE KEYS */;
/*!40000 ALTER TABLE `clients_redirect_uri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_resource_ids`
--

DROP TABLE IF EXISTS `clients_resource_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_resource_ids` (
  `client_id` varchar(64) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  KEY `FKmbihou86j52f14ws3llg138id` (`client_id`),
  CONSTRAINT `FKmbihou86j52f14ws3llg138id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_resource_ids`
--

LOCK TABLES `clients_resource_ids` WRITE;
/*!40000 ALTER TABLE `clients_resource_ids` DISABLE KEYS */;
/*!40000 ALTER TABLE `clients_resource_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_scope`
--

DROP TABLE IF EXISTS `clients_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_scope` (
  `client_id` varchar(64) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  KEY `FK82axfpv8eiv4guicthkyv92lk` (`client_id`),
  CONSTRAINT `FK82axfpv8eiv4guicthkyv92lk` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_scope`
--

LOCK TABLES `clients_scope` WRITE;
/*!40000 ALTER TABLE `clients_scope` DISABLE KEYS */;
INSERT INTO `clients_scope` VALUES ('supersecretclient','read'),('supersecretclient','write');
/*!40000 ALTER TABLE `clients_scope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (3),(3);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenants`
--

DROP TABLE IF EXISTS `tenants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenants` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `default_value` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discriminator` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_discriminator` (`discriminator`),
  UNIQUE KEY `uq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenants`
--

LOCK TABLES `tenants` WRITE;
/*!40000 ALTER TABLE `tenants` DISABLE KEYS */;
INSERT INTO `tenants` VALUES (1,'2020-06-25 03:41:59','UnAuthenticated','2020-06-25 03:41:59','UnAuthenticated',_binary '','Default tenant for the system','default','Default Tenant');
/*!40000 ALTER TABLE `tenants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_granted_authorities`
--

DROP TABLE IF EXISTS `user_granted_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_granted_authorities` (
  `user_id` bigint NOT NULL,
  `granted_authorities` varchar(255) DEFAULT NULL,
  KEY `FKabh7nyetaaiin2lb4tokajj4c` (`user_id`),
  CONSTRAINT `FKabh7nyetaaiin2lb4tokajj4c` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_granted_authorities`
--

LOCK TABLES `user_granted_authorities` WRITE;
/*!40000 ALTER TABLE `user_granted_authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_granted_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `auth_token` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expiry` datetime DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `mask` bigint DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `tenant_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_email` (`email`),
  UNIQUE KEY `uq_username` (`username`),
  KEY `FK21hn1a5ja1tve7ae02fnn4cld` (`tenant_id`),
  CONSTRAINT `FK21hn1a5ja1tve7ae02fnn4cld` FOREIGN KEY (`tenant_id`) REFERENCES `tenants` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,NULL,'2020-06-25 03:42:55','UnAuthenticated','admin@varahamihir.com',NULL,'Admin for default tenant',0,'$e0801$osUskS0wx+oNuR687wfG09Iha1zoEu2yujYK4ehm4XW2vSEG7J11jzm53ePi9Xp7VowIbl1D/zVPHKJDPu6Xrw==$5rb/hRtyQKLwkkQrRQpWltuNWefurM15D3mVj7N7PpA=','2020-06-25 03:42:55','UnAuthenticated','admin',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-25 12:32:54
