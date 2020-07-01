-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: localhost    Database: tenant1
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
-- Table structure for table `TutorialClientDetails_additionalInformation`
--

DROP TABLE IF EXISTS `TutorialClientDetails_additionalInformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TutorialClientDetails_additionalInformation` (
  `TutorialClientDetails_clientId` varchar(255) NOT NULL,
  `additionalInformation` varchar(255) DEFAULT NULL,
  `additionalInformation_KEY` varchar(255) NOT NULL,
  PRIMARY KEY (`TutorialClientDetails_clientId`,`additionalInformation_KEY`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialClientDetails_additionalInformation`
--

LOCK TABLES `TutorialClientDetails_additionalInformation` WRITE;
/*!40000 ALTER TABLE `TutorialClientDetails_additionalInformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `TutorialClientDetails_additionalInformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialClientDetails_authorities`
--

DROP TABLE IF EXISTS `TutorialClientDetails_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TutorialClientDetails_authorities` (
  `TutorialClientDetails_clientId` varchar(255) NOT NULL,
  `authorities` tinyblob,
  KEY `FK12s0u6i191dfjkuxq6b6h6li6` (`TutorialClientDetails_clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialClientDetails_authorities`
--

LOCK TABLES `TutorialClientDetails_authorities` WRITE;
/*!40000 ALTER TABLE `TutorialClientDetails_authorities` DISABLE KEYS */;
INSERT INTO `TutorialClientDetails_authorities` VALUES ('supersecretclient',_binary '¨\Ì\0sr\0@in.springframework.blog.tutorials.pojos.TutorialGrantedAuthority≤\ﬂ\‰TU≤`\0L\0	authorityt\0Ljava/lang/String;xpt\0TENANT_ADMIN'),('supersecretclient',_binary '¨\Ì\0sr\0@in.springframework.blog.tutorials.pojos.TutorialGrantedAuthority≤\ﬂ\‰TU≤`\0L\0	authorityt\0Ljava/lang/String;xpt\0ADMIN'),('supersecretclient',_binary '¨\Ì\0sr\0@in.springframework.blog.tutorials.pojos.TutorialGrantedAuthority≤\ﬂ\‰TU≤`\0L\0	authorityt\0Ljava/lang/String;xpt\0USER');
/*!40000 ALTER TABLE `TutorialClientDetails_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialClientDetails_authorizedGrantTypes`
--

DROP TABLE IF EXISTS `TutorialClientDetails_authorizedGrantTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TutorialClientDetails_authorizedGrantTypes` (
  `TutorialClientDetails_clientId` varchar(255) NOT NULL,
  `authorizedGrantTypes` varchar(255) DEFAULT NULL,
  KEY `FKtldjgamwcdevyakgm5gg6d7mr` (`TutorialClientDetails_clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialClientDetails_authorizedGrantTypes`
--

LOCK TABLES `TutorialClientDetails_authorizedGrantTypes` WRITE;
/*!40000 ALTER TABLE `TutorialClientDetails_authorizedGrantTypes` DISABLE KEYS */;
INSERT INTO `TutorialClientDetails_authorizedGrantTypes` VALUES ('supersecretclient','refresh_token'),('supersecretclient','password'),('supersecretclient','client_credentials'),('supersecretclient','authorization_code');
/*!40000 ALTER TABLE `TutorialClientDetails_authorizedGrantTypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialClientDetails_resourceIds`
--

DROP TABLE IF EXISTS `TutorialClientDetails_resourceIds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TutorialClientDetails_resourceIds` (
  `TutorialClientDetails_clientId` varchar(255) NOT NULL,
  `resourceIds` varchar(255) DEFAULT NULL,
  KEY `FK9na498px67puyjgbpkfeowsrs` (`TutorialClientDetails_clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialClientDetails_resourceIds`
--

LOCK TABLES `TutorialClientDetails_resourceIds` WRITE;
/*!40000 ALTER TABLE `TutorialClientDetails_resourceIds` DISABLE KEYS */;
/*!40000 ALTER TABLE `TutorialClientDetails_resourceIds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialClientDetails_scope`
--

DROP TABLE IF EXISTS `TutorialClientDetails_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TutorialClientDetails_scope` (
  `TutorialClientDetails_clientId` varchar(255) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  KEY `FKqupoto45ophh2k3eh62x0asgm` (`TutorialClientDetails_clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialClientDetails_scope`
--

LOCK TABLES `TutorialClientDetails_scope` WRITE;
/*!40000 ALTER TABLE `TutorialClientDetails_scope` DISABLE KEYS */;
INSERT INTO `TutorialClientDetails_scope` VALUES ('supersecretclient','read'),('supersecretclient','write'),('supersecretclient','code');
/*!40000 ALTER TABLE `TutorialClientDetails_scope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TutorialClientDetails_webServerRedirectUri`
--

DROP TABLE IF EXISTS `TutorialClientDetails_webServerRedirectUri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TutorialClientDetails_webServerRedirectUri` (
  `TutorialClientDetails_clientId` varchar(255) NOT NULL,
  `webServerRedirectUri` varchar(255) DEFAULT NULL,
  KEY `FKnib2xuu02ktpuni2ls0mv1jrm` (`TutorialClientDetails_clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TutorialClientDetails_webServerRedirectUri`
--

LOCK TABLES `TutorialClientDetails_webServerRedirectUri` WRITE;
/*!40000 ALTER TABLE `TutorialClientDetails_webServerRedirectUri` DISABLE KEYS */;
/*!40000 ALTER TABLE `TutorialClientDetails_webServerRedirectUri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_grantedAuthorities`
--

DROP TABLE IF EXISTS `User_grantedAuthorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User_grantedAuthorities` (
  `User_id` bigint NOT NULL,
  `grantedAuthorities` varchar(255) DEFAULT NULL,
  KEY `FK3if4sdarl8m5ctwgpk5xjkke5` (`User_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_grantedAuthorities`
--

LOCK TABLES `User_grantedAuthorities` WRITE;
/*!40000 ALTER TABLE `User_grantedAuthorities` DISABLE KEYS */;
INSERT INTO `User_grantedAuthorities` VALUES (6,'ADMIN'),(6,'USER');
/*!40000 ALTER TABLE `User_grantedAuthorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `clientId` varchar(255) NOT NULL,
  `accessTokenValidity` int NOT NULL,
  `autoApprove` bit(1) NOT NULL,
  `clientSecret` varchar(255) DEFAULT NULL,
  `refreshTokenValidity` int NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `access_token_validity` int NOT NULL,
  `auto_approve` bit(1) NOT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `refresh_token_validity` int NOT NULL,
  PRIMARY KEY (`clientId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES ('supersecretclient',500,_binary '','$e0801$Hn6jcSKKfymDU6O63DGP9TtbfAsqk+Qa0L+RrrEkM1vDPt9hjXczEmcRHYFT15RvTRtaxXC4iAJgfRSphp2jpQ==$pbOjE2fFao3A9D355tzB/k2dy5z7So0WFlOeCKVKwz0=',1000,'',0,_binary '\0',NULL,0);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (7),(7),(7);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `id` bigint NOT NULL,
  `createdAt` datetime DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenants`
--

DROP TABLE IF EXISTS `tenants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenants` (
  `id` bigint NOT NULL,
  `createdAt` datetime DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `defaultValue` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discriminator` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `default_value` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_discriminator` (`discriminator`),
  UNIQUE KEY `uq_name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenants`
--

LOCK TABLES `tenants` WRITE;
/*!40000 ALTER TABLE `tenants` DISABLE KEYS */;
INSERT INTO `tenants` VALUES (1,'2020-06-23 15:04:56','UnAuthenticated','2020-06-23 15:04:56','UnAuthenticated',_binary '','Default tenant for the system','tenant1','Default Tenant',NULL,NULL,NULL,NULL,_binary '\0');
/*!40000 ALTER TABLE `tenants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_client_details_additional_information`
--

DROP TABLE IF EXISTS `tutorial_client_details_additional_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_client_details_additional_information` (
  `tutorial_client_details_client_id` varchar(255) NOT NULL,
  `additional_information` varchar(255) DEFAULT NULL,
  `additional_information_key` varchar(255) NOT NULL,
  PRIMARY KEY (`tutorial_client_details_client_id`,`additional_information_key`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_client_details_additional_information`
--

LOCK TABLES `tutorial_client_details_additional_information` WRITE;
/*!40000 ALTER TABLE `tutorial_client_details_additional_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_client_details_additional_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_client_details_authorities`
--

DROP TABLE IF EXISTS `tutorial_client_details_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_client_details_authorities` (
  `tutorial_client_details_client_id` varchar(255) NOT NULL,
  `authorities` tinyblob,
  KEY `FK7o15exjwiomr8exkvhx52nyly` (`tutorial_client_details_client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_client_details_authorities`
--

LOCK TABLES `tutorial_client_details_authorities` WRITE;
/*!40000 ALTER TABLE `tutorial_client_details_authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_client_details_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_client_details_authorized_grant_types`
--

DROP TABLE IF EXISTS `tutorial_client_details_authorized_grant_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_client_details_authorized_grant_types` (
  `tutorial_client_details_client_id` varchar(255) NOT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  KEY `FKeb85io6vjp6xmxjbt7rt8l18g` (`tutorial_client_details_client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_client_details_authorized_grant_types`
--

LOCK TABLES `tutorial_client_details_authorized_grant_types` WRITE;
/*!40000 ALTER TABLE `tutorial_client_details_authorized_grant_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_client_details_authorized_grant_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_client_details_resource_ids`
--

DROP TABLE IF EXISTS `tutorial_client_details_resource_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_client_details_resource_ids` (
  `tutorial_client_details_client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  KEY `FKm4ccupcigl1c5wx9ilq5a1cv3` (`tutorial_client_details_client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_client_details_resource_ids`
--

LOCK TABLES `tutorial_client_details_resource_ids` WRITE;
/*!40000 ALTER TABLE `tutorial_client_details_resource_ids` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_client_details_resource_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_client_details_scope`
--

DROP TABLE IF EXISTS `tutorial_client_details_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_client_details_scope` (
  `tutorial_client_details_client_id` varchar(255) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  KEY `FK7j3jawy50ak9pw5oo9c4lkel3` (`tutorial_client_details_client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_client_details_scope`
--

LOCK TABLES `tutorial_client_details_scope` WRITE;
/*!40000 ALTER TABLE `tutorial_client_details_scope` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_client_details_scope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_client_details_web_server_redirect_uri`
--

DROP TABLE IF EXISTS `tutorial_client_details_web_server_redirect_uri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_client_details_web_server_redirect_uri` (
  `tutorial_client_details_client_id` varchar(255) NOT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  KEY `FK4v59ae8tgkv484581vrbis8ed` (`tutorial_client_details_client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_client_details_web_server_redirect_uri`
--

LOCK TABLES `tutorial_client_details_web_server_redirect_uri` WRITE;
/*!40000 ALTER TABLE `tutorial_client_details_web_server_redirect_uri` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_client_details_web_server_redirect_uri` ENABLE KEYS */;
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
  KEY `FKabh7nyetaaiin2lb4tokajj4c` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
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
  `authToken` varchar(255) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expiry` datetime DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `mask` bigint NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `tenantId` bigint NOT NULL,
  `auth_token` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_email` (`email`),
  UNIQUE KEY `uq_authToken` (`authToken`),
  UNIQUE KEY `uq_username` (`username`),
  KEY `FKgjoyjb2q5wm78xhfiaoraq3g4` (`tenantId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,NULL,'2020-06-23 15:04:56','UnAuthenticated','defaultuser@defaultadmin.com',NULL,'Default User',0,'default123','2020-06-23 15:04:56','UnAuthenticated','defaultuser',1,NULL,NULL,NULL,NULL,NULL),(3,NULL,'2020-06-23 15:05:00','UnAuthenticated','vinay@avasthi.com',NULL,'Vinay Avasthi',1,'$e0801$NywiFpWMh0XNv0knOxhk+OsUNtefXZfO3u05w8Ty+CG6yeu/GClTk+pMeTtQsgZbIOKjznc9y/5+U8mLNi1nwg==$yKbaNMtoc+XMVNa0ZB2AJQLmat2/yHut8NPHcaJ8wuw=','2020-06-23 15:05:00','UnAuthenticated','vavasthi',1,NULL,NULL,NULL,NULL,NULL),(6,NULL,'2020-06-23 23:57:49','UnAuthenticated','admin@springframework.in',NULL,'Spring Tutorial Admin',1,'$e0801$tuWaUlGdxYXgg8sXTkjY6Jtd29W+iYf4AZVOkm+RKJMEiOpvfynqjq23qAMKx5zqRSrhF3V+Cug8phasQNoXCQ==$psssuCq+ChavYyvfupY9GD4SPuhjVZil5xoU7C3bS5A=','2020-06-23 23:57:49','UnAuthenticated','admin',1,NULL,NULL,NULL,NULL,NULL);
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

-- Dump completed on 2020-06-25  1:35:36
