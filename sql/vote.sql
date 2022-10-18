-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (arm64)
--
-- Host: 127.0.0.1    Database: vote
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
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '候选人姓名',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '候选人手机号',
  `id_card` varchar(20) DEFAULT NULL COMMENT '候选人身份证号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT NULL COMMENT '0正常 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
INSERT INTO `candidate` VALUES (1581922170022445058,'xiaoxue','17680465903','A123456(2)','2022-10-17 16:16:51','2022-10-17 16:16:51',0),(1582004133244796929,'adadad','17680465904','A223456(2)','2022-10-17 21:42:32','2022-10-17 21:42:32',0);
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `del_flag` int DEFAULT NULL COMMENT '0正常 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1582009295539539970,'admin222','123@qq.com','A223456(2)','2022-10-17 22:03:03','2022-10-17 23:28:02',0);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_to_voter`
--

DROP TABLE IF EXISTS `contact_to_voter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_to_voter` (
  `id` bigint NOT NULL COMMENT '主键',
  `contact_id` bigint DEFAULT NULL COMMENT '用户id',
  `voter_to_contact_id` bigint DEFAULT NULL COMMENT '投票对应候选人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `del_flag` int DEFAULT NULL COMMENT '0正常 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_to_voter`
--

LOCK TABLES `contact_to_voter` WRITE;
/*!40000 ALTER TABLE `contact_to_voter` DISABLE KEYS */;
INSERT INTO `contact_to_voter` VALUES (1582030682274693121,1582009295539539970,1582004209329471490,'2022-10-17 23:28:02','2022-10-17 23:28:02',0);
/*!40000 ALTER TABLE `contact_to_voter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voter`
--

DROP TABLE IF EXISTS `voter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voter` (
  `id` bigint NOT NULL,
  `title` varchar(100) DEFAULT NULL COMMENT '投票标题',
  `status` int DEFAULT NULL COMMENT '0待开始 1已开始 2已结束',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT NULL COMMENT '0正常 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voter`
--

LOCK TABLES `voter` WRITE;
/*!40000 ALTER TABLE `voter` DISABLE KEYS */;
INSERT INTO `voter` VALUES (1581997539274530818,'asd',1,'2022-10-17 21:16:20','2022-10-17 21:16:20',0);
/*!40000 ALTER TABLE `voter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voter_to_candidate`
--

DROP TABLE IF EXISTS `voter_to_candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voter_to_candidate` (
  `id` bigint NOT NULL COMMENT '主键',
  `voter_id` bigint NOT NULL COMMENT '投票Id',
  `candidate_id` bigint NOT NULL COMMENT '候选人Id',
  `poll_number` int DEFAULT NULL COMMENT '候选人票数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int DEFAULT NULL COMMENT '0正常 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voter_to_candidate`
--

LOCK TABLES `voter_to_candidate` WRITE;
/*!40000 ALTER TABLE `voter_to_candidate` DISABLE KEYS */;
INSERT INTO `voter_to_candidate` VALUES (1582004209329471490,1581997539274530818,1581922170022445058,1,'2022-10-17 21:42:50','2022-10-17 23:28:02',0),(1582004209342054402,1581997539274530818,1582004133244796929,NULL,'2022-10-17 21:42:50','2022-10-17 21:42:50',0);
/*!40000 ALTER TABLE `voter_to_candidate` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-18 11:50:57
