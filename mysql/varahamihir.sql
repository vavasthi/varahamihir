-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: localhost    Database: varahamihir
-- ------------------------------------------------------
-- Server version	8.0.20-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
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
  `id` varchar(255) NOT NULL,
  `access_token_validity` int DEFAULT NULL,
  `auto_approve` bit(1) DEFAULT NULL,
  `client_id` varchar(64) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `credentials_locked` bit(1) NOT NULL,
  `expired` bit(1) NOT NULL,
  `locked` bit(1) NOT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_clientId` (`client_id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES ('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae',500,_binary '','supersecretclient','{bcrypt}$2a$10$izGZwhKI.Gh0FUx27WxwzO47rJIbJU.uxIan6LiNuJZPm7jPN3SFO',_binary '\0',_binary '\0',_binary '\0',1000,'a5c8cbe0-bce5-440b-b18e-ffc96d253092');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_addl_info`
--

DROP TABLE IF EXISTS `clients_addl_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_addl_info` (
  `client_id` varchar(255) NOT NULL,
  `k` varchar(255) DEFAULT NULL,
  `v` varchar(255) DEFAULT NULL,
  KEY `FK6o27eo3fsf7cycdvxrq5nwwkm` (`client_id`),
  CONSTRAINT `FK6o27eo3fsf7cycdvxrq5nwwkm` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ;
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
  `client_id` varchar(255) NOT NULL,
  `authorities` tinyblob,
  KEY `FKqld3s3ts8yu3vlmnpkwl0jheg` (`client_id`),
  CONSTRAINT `FKqld3s3ts8yu3vlmnpkwl0jheg` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_authorities`
--

LOCK TABLES `clients_authorities` WRITE;
/*!40000 ALTER TABLE `clients_authorities` DISABLE KEYS */;
INSERT INTO `clients_authorities` VALUES ('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae',_binary '¬\í\0sr\0@com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthorityIf\Ø\Æ\Ùh\0L\0	authorityt\0Ljava/lang/String;xpt\0ADMIN'),('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae',_binary '¬\í\0sr\0@com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthorityIf\Ø\Æ\Ùh\0L\0	authorityt\0Ljava/lang/String;xpt\0STUDENT'),('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae',_binary '¬\í\0sr\0@com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthorityIf\Ø\Æ\Ùh\0L\0	authorityt\0Ljava/lang/String;xpt\0GUARDIAN');
/*!40000 ALTER TABLE `clients_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_authorized_grant_types`
--

DROP TABLE IF EXISTS `clients_authorized_grant_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_authorized_grant_types` (
  `client_id` varchar(255) NOT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  KEY `FKc6237qwoo3q8wmk6mkrt5evcy` (`client_id`),
  CONSTRAINT `FKc6237qwoo3q8wmk6mkrt5evcy` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_authorized_grant_types`
--

LOCK TABLES `clients_authorized_grant_types` WRITE;
/*!40000 ALTER TABLE `clients_authorized_grant_types` DISABLE KEYS */;
INSERT INTO `clients_authorized_grant_types` VALUES ('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae','refresh_token'),('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae','password'),('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae','client_credentials'),('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae','authorization_code');
/*!40000 ALTER TABLE `clients_authorized_grant_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_redirect_uri`
--

DROP TABLE IF EXISTS `clients_redirect_uri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_redirect_uri` (
  `client_id` varchar(255) NOT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  KEY `FKkakvevehwvyh3c6kl0vpvot3l` (`client_id`),
  CONSTRAINT `FKkakvevehwvyh3c6kl0vpvot3l` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ;
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
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  KEY `FKmbihou86j52f14ws3llg138id` (`client_id`),
  CONSTRAINT `FKmbihou86j52f14ws3llg138id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ;
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
  `client_id` varchar(255) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  KEY `FK82axfpv8eiv4guicthkyv92lk` (`client_id`),
  CONSTRAINT `FK82axfpv8eiv4guicthkyv92lk` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_scope`
--

LOCK TABLES `clients_scope` WRITE;
/*!40000 ALTER TABLE `clients_scope` DISABLE KEYS */;
INSERT INTO `clients_scope` VALUES ('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae','read'),('dfe4a150-fe31-45c5-9ca1-f4585d9c5cae','write');
/*!40000 ALTER TABLE `clients_scope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenants`
--

DROP TABLE IF EXISTS `tenants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenants` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `default_value` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discriminator` varchar(255) DEFAULT NULL,
  `expiry` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `refresh_expiry` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_discriminator` (`discriminator`),
  UNIQUE KEY `uq_name` (`name`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenants`
--

LOCK TABLES `tenants` WRITE;
/*!40000 ALTER TABLE `tenants` DISABLE KEYS */;
INSERT INTO `tenants` VALUES ('a5c8cbe0-bce5-440b-b18e-ffc96d253092','2020-06-30 21:27:24','UnAuthenticated','2020-06-30 21:27:24','UnAuthenticated',_binary '','Default tenant for the system','default',500,'Default Tenant',1000);
/*!40000 ALTER TABLE `tenants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_granted_authorities`
--

DROP TABLE IF EXISTS `user_granted_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_granted_authorities` (
  `user_id` varchar(255) NOT NULL,
  `granted_authorities` varchar(255) DEFAULT NULL,
  KEY `FKabh7nyetaaiin2lb4tokajj4c` (`user_id`),
  CONSTRAINT `FKabh7nyetaaiin2lb4tokajj4c` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_granted_authorities`
--

LOCK TABLES `user_granted_authorities` WRITE;
/*!40000 ALTER TABLE `user_granted_authorities` DISABLE KEYS */;
INSERT INTO `user_granted_authorities` VALUES ('8d47f18c-1998-429d-a596-d0dfd1a46d64','USER'),('8d47f18c-1998-429d-a596-d0dfd1a46d64','ADMIN'),('d6d16c9c-8c66-4e1b-a345-f2fa41209695','USER');
/*!40000 ALTER TABLE `user_granted_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `credentials_locked` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expired` bit(1) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `locked` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_email` (`tenant_id`,`email`),
  UNIQUE KEY `uq_username` (`tenant_id`,`username`)
) ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('8d47f18c-1998-429d-a596-d0dfd1a46d64','2020-06-30 21:30:23','UnAuthenticated',_binary '\0',NULL,_binary '\0','Adminitrator for a tenant',_binary '\0','{bcrypt}$2a$10$ccZRy2DmexD7buEutBnSOOZKh5bfHrwHnFKS4YD5dAxd2znfbptPq','a5c8cbe0-bce5-440b-b18e-ffc96d253092','2020-06-30 21:30:23','UnAuthenticated','admin'),('9af7a442-bc25-4d2c-b5d1-341dcf6fe2da','2020-07-01 14:47:27','UnAuthenticated',_binary '\0',NULL,_binary '\0','Adminitrator for a tenant',_binary '\0','{bcrypt}$2a$10$RbU8mHM9DEE5PobvM.Jj6OxPhPnEIAfDzfXgpULh4T9HXfdAi5hDa','a5c8cbe0-bce5-440b-b18e-ffc96d253092','2020-07-01 14:47:27','UnAuthenticated','admin2'),('d6d16c9c-8c66-4e1b-a345-f2fa41209695','2020-06-30 23:01:04','UnAuthenticated',_binary '\0',NULL,_binary '\0','Adminitrator for a tenant',_binary '\0','{bcrypt}$2a$10$L4wroQmLhVsHST6C1JvlnukWckJMWJSyYVZr2iXabNdIP59wqtYFq','a5c8cbe0-bce5-440b-b18e-ffc96d253092','2020-06-30 23:01:04','UnAuthenticated','admin1');
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

-- Dump completed on 2020-07-01 18:39:00
