-- MySQL dump 10.13  Distrib 5.5.13, for Win64 (x86)
--
-- Host: localhost    Database: prometheus
-- ------------------------------------------------------
-- Server version	5.5.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` bigint(20) NOT NULL,
  `patientId` bigint(20) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updatedBy` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `path` varchar(100) NOT NULL,
  `busy` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,19,3781,'2014-10-21 00:00:00','2014-10-21 00:00:00',19,'MTQxMzg5MjUxNTkwNQaa.xlsx','./appointment/3781/',0),(2,19,3781,'2014-10-21 12:01:21','2014-10-21 12:01:21',19,'MTQxMzg5Mjg4MTc5Nwaa.xlsx','./appointment/3781/',0);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c3po`
--

DROP TABLE IF EXISTS `c3po`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c3po` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c3po`
--

LOCK TABLES `c3po` WRITE;
/*!40000 ALTER TABLE `c3po` DISABLE KEYS */;
/*!40000 ALTER TABLE `c3po` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journal`
--

DROP TABLE IF EXISTS `journal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL DEFAULT '0',
  `updated_by` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL,
  `path` varchar(100) NOT NULL,
  `busy` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journal`
--

LOCK TABLES `journal` WRITE;
/*!40000 ALTER TABLE `journal` DISABLE KEYS */;
INSERT INTO `journal` VALUES (1,'2014-10-21 11:55:37',19,19,'Журнал_генеральных_уборок.xlsx','./journal/MTQxMzg5MjUzNzUxNgaa.xlsx',0),(2,'2014-10-21 11:55:49',19,19,'Журнал_генеральных_уборок.xlsx','./journal/MTQxMzg5MjU0OTgxNAaa.xlsx',0);
/*!40000 ALTER TABLE `journal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `next_visit`
--

DROP TABLE IF EXISTS `next_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `next_visit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `visit_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `patient_id` bigint(20) NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `next_visit`
--

LOCK TABLES `next_visit` WRITE;
/*!40000 ALTER TABLE `next_visit` DISABLE KEYS */;
INSERT INTO `next_visit` VALUES (1,'2014-10-22 15:00:00',3781,19,0);
/*!40000 ALTER TABLE `next_visit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organisation`
--

DROP TABLE IF EXISTS `organisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organisation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organisation`
--

LOCK TABLES `organisation` WRITE;
/*!40000 ALTER TABLE `organisation` DISABLE KEYS */;
INSERT INTO `organisation` VALUES (36,'SHOH_MED');
/*!40000 ALTER TABLE `organisation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nfp` varchar(50) NOT NULL,
  `sex` int(1) DEFAULT NULL,
  `birthday` date NOT NULL,
  `type` int(1) DEFAULT NULL,
  `email` varchar(88) NOT NULL,
  `mobile_phone` varchar(18) NOT NULL,
  `discount` int(10) NOT NULL DEFAULT '0',
  `token` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nfp` (`nfp`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=3782 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (3780,'Тян Саша',1,'1900-01-01',1,'','',0,'0KLRj9C9INCh0LDRiNCw'),(3781,'Алекс Шох',1,'1900-01-01',0,'','',0,'0JDQu9C10LrRgSDQqNC+0YU=');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_visit`
--

DROP TABLE IF EXISTS `patient_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_visit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `visit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `view_time` timestamp NULL DEFAULT NULL,
  `patient_id` bigint(20) NOT NULL,
  `department` int(2) DEFAULT NULL,
  `course_group_name` varchar(100) NOT NULL,
  `course_analys_name` varchar(100) NOT NULL,
  `paying_status` int(2) DEFAULT NULL,
  `patient_type` int(2) DEFAULT NULL,
  `visit_type` int(2) DEFAULT NULL,
  `visit_status` int(2) DEFAULT NULL,
  `doctor_id` bigint(20) NOT NULL,
  `till_date` varchar(100) NOT NULL DEFAULT '',
  `from_organisation` varchar(100) NOT NULL DEFAULT '',
  `from_doctor` varchar(100) NOT NULL DEFAULT '',
  `discount` int(3) NOT NULL DEFAULT '0',
  `created_by` varchar(100) NOT NULL DEFAULT '',
  `payback` tinyint(1) DEFAULT NULL,
  `filename` varchar(100) NOT NULL DEFAULT '',
  `filepath` varchar(100) NOT NULL DEFAULT '',
  `released` tinyint(1) DEFAULT '0',
  `room` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8313 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_visit`
--

LOCK TABLES `patient_visit` WRITE;
/*!40000 ALTER TABLE `patient_visit` DISABLE KEYS */;
INSERT INTO `patient_visit` VALUES (8301,'2014-10-20 00:00:00',NULL,3781,5,'Лаборатория','Гармон',0,0,0,1,0,'','SHOH_MED','',0,'shin',0,'MTQxMzgxMzUxMzg3Ngaa.xlsx','5/3781/',0,NULL),(8304,'2014-10-21 00:00:00',NULL,3781,5,'Лабораторный_исследования_Клинические_исследование_крови','Anti-HBsHBsAb.xlsx',0,0,0,1,0,'','SHOH_MED','',0,'shin',0,'TVRReE16ZzVNalF5TVRNMU1BYWFBbnRpLUhCc0hCc0FiLnhsc3g=.xlsx','5/3781/',0,NULL),(8305,'2014-10-21 00:00:00',NULL,3781,5,'Дополнительные_и_функциональные_исследования','Гастроскопия_(ФЭГДС).docx',0,0,0,1,0,'','SHOH_MED','',0,'shin',0,'TVRReE16ZzVNalF5TVRNMk1RYWHQk9Cw0YHRgtGA0L7RgdC60L7Qv9C40Y9fKNCk0K3Qk9CU0KEpLmRvY3g=.docx','5/3781/',0,NULL),(8306,'2014-10-21 11:54:35',NULL,3781,2,'Неизвестно','Неизвестно',0,0,0,0,22,'','SHOH_MED','',0,'shin',0,'','',0,NULL),(8307,'2014-10-21 00:00:00',NULL,3781,5,'УЛЬТРАЗВУКОВОЕ-ДОППЛЕР_ИССЛЕДОВАНИЕ_НА_АППАРАТЕ_ЭКСПЕРТНОГО_КЛАССА','УЗИ_молочной_железы.docx',0,0,0,1,0,'','SHOH_MED','',0,'shin',0,'TVRReE16ZzVNell3TkRFMk5RYWHQo9CX0Jhf0LzQvtC70L7Rh9C90L7QuV_QttC10LvQtdC30YsuZG9jeA==.docx','5/3781/',0,NULL),(8308,'2014-10-23 09:19:07',NULL,3780,5,'Лабораторный_исследования_Клинические_исследование_крови','Gardnerella_vaginalis.xlsx',0,0,0,0,0,'','SHOH_MED','',0,'gine',0,'TVRReE5EQTFOVGswTnprMU13YWFHYXJkbmVyZWxsYV92YWdpbmFsaXMueGxzeA==.xlsx','5/3780/',0,NULL),(8309,'2014-10-23 09:26:05',NULL,3781,2,'Неизвестно','Неизвестно',0,0,0,0,22,'','SHOH_MED','',0,'gine',0,'','',0,NULL),(8310,'2014-10-23 11:33:07',NULL,3780,4,'Неизвестно','Неизвестно',0,0,0,0,21,'','SHOH_MED','',0,'shin',0,'','',0,NULL),(8311,'2014-10-23 11:36:32',NULL,3780,3,'Неизвестно','Неизвестно',0,0,0,0,23,'','SHOH_MED','',0,'shin',0,'','',0,NULL),(8312,'2014-10-24 02:00:08',NULL,3780,2,'Неизвестно','Неизвестно',0,0,0,0,22,'','SHOH_MED','',0,'shin',0,'','',0,NULL);
/*!40000 ALTER TABLE `patient_visit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` bigint(20) DEFAULT '0',
  `patientId` bigint(20) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedBy` bigint(20) DEFAULT '0',
  `name` varchar(100) NOT NULL,
  `path` varchar(100) NOT NULL,
  `busy` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (1,19,3781,'2014-10-21 00:00:00',19,'MTQxMzg5MjUwMTA3NQaa.doc','./record/3781/',0),(2,19,3781,'2014-10-21 00:00:00',19,'MTQxMzg5Mjg1MjMyNwaa.doc','./record/3781/',0);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `capacity` int(3) NOT NULL DEFAULT '0',
  `limitation` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (3,'1A',1,1);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nfp` varchar(50) NOT NULL DEFAULT '­¥¨§¢¥áâ­®',
  `birthday` date NOT NULL DEFAULT '2013-06-18',
  `username` varchar(10) NOT NULL,
  `password` varchar(100) NOT NULL,
  `privelegy` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (19,'Владимир Шин Геннадиевич','1988-06-12','shin','YWRtaW4=',0),(20,'Vladimir Shin Gennadievich','1988-06-12','admin','YWRtaW4=',0),(21,'uzi Uzi uzi','1988-06-12','uzi','dXpp',4),(22,'uro Uro uro','1988-06-12','uro','dXJv',2),(23,'gine Gine gine','1988-06-12','gine','Z2luZQ==',3),(25,'stati Stati stati','1988-06-12','stati','c3RhdGk=',6),(26,'hiru Hiru hiru','1988-06-12','hiru','aGlydQ==',12),(27,'oku Oku oku','1988-06-12','oku','b2t1',13),(28,'fizio Fizio fizio','1988-06-12','fizio','Zml6aW8=',14),(29,'tera Tera tera','1988-06-12','tera','dGVyYQ==',15),(30,'endo endo endo','1988-06-12','endo','ZW5kbw==',16),(31,'nevro nevro nevro','1988-12-06','nevro','bmV2cm8=',17),(32,'verte verte verte','1988-06-12','verte','dmVydGU=',18),(33,'mri mri mri','1988-06-12','mri','bXJp',21);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-28 10:48:35
