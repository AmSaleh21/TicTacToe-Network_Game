-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: tictactoe
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `id` int NOT NULL AUTO_INCREMENT,
  `from_player` int NOT NULL,
  `to_player` int NOT NULL,
  `winner` int DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('REQUEST','COMPLETE','INPROGRESS','FAIL','PAUSE','') NOT NULL DEFAULT 'REQUEST',
  `board` text,
  PRIMARY KEY (`id`),
  KEY `player1` (`from_player`),
  KEY `player2` (`to_player`),
  KEY `winner` (`winner`),
  CONSTRAINT `games_ibfk_1` FOREIGN KEY (`from_player`) REFERENCES `players` (`id`),
  CONSTRAINT `games_ibfk_2` FOREIGN KEY (`to_player`) REFERENCES `players` (`id`),
  CONSTRAINT `games_ibfk_3` FOREIGN KEY (`winner`) REFERENCES `players` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (19,1,2,NULL,'2020-01-14 04:58:11','REQUEST',NULL),(20,1,2,NULL,'2020-01-14 05:19:36','REQUEST',NULL),(21,1,2,NULL,'2020-01-14 05:24:04','REQUEST',NULL),(22,1,2,NULL,'2020-01-14 05:25:54','REQUEST',NULL),(23,1,2,NULL,'2020-01-14 05:28:20','REQUEST',NULL),(24,2,2,NULL,'2020-01-14 05:28:31','REQUEST',NULL),(25,1,2,NULL,'2020-01-14 05:32:46','REQUEST',NULL),(26,1,2,NULL,'2020-01-14 05:33:18','REQUEST',NULL),(27,1,2,NULL,'2020-01-14 05:33:59','REQUEST',NULL),(28,1,2,NULL,'2020-01-14 05:35:48','REQUEST',NULL),(29,1,2,NULL,'2020-01-14 13:08:57','FAIL',NULL),(30,1,2,NULL,'2020-01-14 13:09:46','FAIL',NULL),(31,1,2,NULL,'2020-01-14 13:10:19','FAIL',NULL),(33,1,2,NULL,'2020-01-14 13:15:32','FAIL',NULL),(34,1,2,NULL,'2020-01-14 13:19:31','FAIL',NULL),(35,1,2,NULL,'2020-01-14 13:28:54','REQUEST',NULL),(36,1,2,NULL,'2020-01-14 13:29:53','REQUEST',NULL),(37,1,2,NULL,'2020-01-14 13:32:08','REQUEST',NULL),(38,1,2,NULL,'2020-01-14 13:33:56','REQUEST',NULL),(39,1,2,NULL,'2020-01-14 14:13:46','FAIL',NULL),(40,1,2,NULL,'2020-01-14 14:14:32','FAIL',NULL),(41,2,2,NULL,'2020-01-14 14:28:05','FAIL',NULL),(43,1,2,NULL,'2020-01-14 15:08:26','REQUEST',NULL),(44,3,2,NULL,'2020-01-14 15:13:51','REQUEST',NULL),(45,3,2,NULL,'2020-01-14 15:15:26','REQUEST',NULL),(46,3,2,NULL,'2020-01-14 15:16:12','REQUEST',NULL),(47,3,2,NULL,'2020-01-14 15:16:13','REQUEST',NULL),(48,3,2,NULL,'2020-01-14 15:16:16','REQUEST',NULL),(49,3,2,NULL,'2020-01-14 15:16:16','REQUEST',NULL),(50,3,2,NULL,'2020-01-14 15:16:17','REQUEST',NULL),(51,3,2,NULL,'2020-01-14 15:16:17','REQUEST',NULL),(52,3,2,NULL,'2020-01-14 15:16:17','REQUEST',NULL),(53,3,2,NULL,'2020-01-14 15:16:18','REQUEST',NULL),(54,3,2,NULL,'2020-01-14 15:16:18','REQUEST',NULL),(55,3,2,NULL,'2020-01-14 15:16:18','REQUEST',NULL),(56,3,2,NULL,'2020-01-14 15:16:18','REQUEST',NULL),(57,3,2,NULL,'2020-01-14 15:16:19','REQUEST',NULL),(58,3,2,NULL,'2020-01-14 15:16:19','REQUEST',NULL),(59,3,2,NULL,'2020-01-14 15:16:21','REQUEST',NULL),(60,2,2,NULL,'2020-01-14 15:16:21','REQUEST',NULL),(61,3,2,NULL,'2020-01-14 15:16:21','REQUEST',NULL),(62,3,2,NULL,'2020-01-14 15:16:21','REQUEST',NULL),(63,3,2,NULL,'2020-01-14 15:16:21','REQUEST',NULL),(64,2,2,NULL,'2020-01-14 15:16:24','REQUEST',NULL),(65,2,2,NULL,'2020-01-14 15:16:24','REQUEST',NULL),(66,2,2,NULL,'2020-01-14 15:16:25','REQUEST',NULL),(67,2,2,NULL,'2020-01-14 15:16:25','REQUEST',NULL),(68,2,2,NULL,'2020-01-14 15:16:27','REQUEST',NULL),(69,2,2,NULL,'2020-01-14 15:16:28','REQUEST',NULL),(70,2,2,NULL,'2020-01-14 15:16:35','REQUEST',NULL),(71,2,2,NULL,'2020-01-14 15:16:35','REQUEST',NULL),(72,2,2,NULL,'2020-01-14 15:16:36','REQUEST',NULL),(73,2,2,NULL,'2020-01-14 15:16:36','REQUEST',NULL),(74,2,2,NULL,'2020-01-14 15:31:53','REQUEST',NULL),(75,1,2,NULL,'2020-01-14 15:31:54','REQUEST',NULL),(76,1,2,NULL,'2020-01-14 15:34:41','REQUEST',NULL),(77,1,2,NULL,'2020-01-14 16:20:57','REQUEST',NULL),(78,1,2,NULL,'2020-01-14 16:21:49','REQUEST',NULL),(79,1,2,NULL,'2020-01-16 00:08:38','REQUEST',NULL),(80,2,1,NULL,'2020-01-16 00:27:44','FAIL',NULL),(81,1,2,NULL,'2020-01-16 00:30:12','REQUEST',NULL),(82,1,2,NULL,'2020-01-16 00:46:56','REQUEST',NULL);
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(50) NOT NULL,
  `score` int DEFAULT '0',
  `status` enum('0','1','2') NOT NULL DEFAULT '0',
  `avatar` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'ahmed','1','a',250,'1',NULL),(2,'halim','1','h',0,'1',NULL),(3,'salah','123','salah@gmail.com',300,'1',NULL);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `username` varchar(50) NOT NULL,
  `password` varchar(10) NOT NULL,
  `status` int NOT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES ('a','a',0,'a','a','Male'),('Abdelrahman','1234',0,'Abdelrahman','Abdelrahman','Male'),('monica','monica',0,'monica','j','Female'),('nada','nada',0,'nada','nada','Female'),('nagwa','nagwa',0,'nagwa','talaat','Female'),('sa','123',0,'saleh','ali','Male');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scores`
--

DROP TABLE IF EXISTS `scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scores` (
  `id` int NOT NULL,
  `player` varchar(50) DEFAULT NULL,
  `opponent` varchar(50) DEFAULT NULL,
  `score` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scores`
--

LOCK TABLES `scores` WRITE;
/*!40000 ALTER TABLE `scores` DISABLE KEYS */;
/*!40000 ALTER TABLE `scores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-07 18:08:24
