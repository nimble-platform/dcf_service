-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: holDatachannelfilteringservicedb
-- ------------------------------------------------------
-- Server version	5.6.33-0ubuntu0.14.04.1

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
-- Table structure for table `Channel`
--

DROP TABLE IF EXISTS `Channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Channel` (
  `id` int(10) unsigned NOT NULL,
  `idProducer` int(10) unsigned NOT NULL,
  `topicname` varchar(45) NOT NULL,
  `idDataFormat` int(10) unsigned NOT NULL,
  `fieldTypeList` varchar(5000) DEFAULT NULL,
  `key` varchar(45) DEFAULT NULL,
  `description` varchar(1200) NOT NULL,
  `openSince` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Channel`
--

LOCK TABLES `Channel` WRITE;
/*!40000 ALTER TABLE `Channel` DISABLE KEYS */;
INSERT INTO `Channel` VALUES (1,1,'PRODUCTIONDATA',1,'TRACKINGTASKDESCR VARCHAR, SERIALNUMBER VARCHAR, ACTIONTIME VARCHAR','SERIALNUMBER','demo PmlData','2018-06-01 13:06:10'),(2,1,'QUALITYDATA',1,'INSERTDATE VARCHAR,SERIALNUMBER VARCHAR,DEFECTSRCDESSTD VARCHAR,REPAIRDESSTD VARCHAR','SERIALNUMBER','demo PmlData','2018-06-01 13:06:10'),(3,1,'TICKETDATA',1,'SERVICEAREA VARCHAR,SERVICETYPE VARCHAR,SYMPTOMCODE VARCHAR,SERIALNUMBER VARCHAR,SOCS1REPAIRCODE VARCHAR,SOCS2COMPONENTCODE VARCHAR,SOCS3DEFECTCODE VARCHAR,NRINTERVENTION VARCHAR','SERIALNUMBER','demo PmlData','2018-06-01 13:06:10'),(4,1,'PRODUCTDATA',1,'SALESCOUNTRY VARCHAR,CALENDARYEARMONTH VARCHAR,PRODUCT VARCHAR,PRDGLOBALFAMILY VARCHAR,PRDGLOBALGROUP VARCHAR,PRDIOBJBRAND VARCHAR,PRODUCTIONMONTH VARCHAR,PRODUCTIONWEEK VARCHAR,PRODUCTIONYEAR VARCHAR,SERIALNUMBER VARCHAR','SERIALNUMBER','demo PmlData','2018-06-01 13:06:10');
/*!40000 ALTER TABLE `Channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataChannel`
--

DROP TABLE IF EXISTS `DataChannel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataChannel` (
  `id` int(10) unsigned NOT NULL,
  `idChannel` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `fieldList` varchar(5000) DEFAULT NULL,
  `description` varchar(1200) NOT NULL,
  `openSince` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataChannel`
--

LOCK TABLES `DataChannel` WRITE;
/*!40000 ALTER TABLE `DataChannel` DISABLE KEYS */;
INSERT INTO `DataChannel` VALUES (1,1,'PRODUCTIONDATA','*','from Channel ProductionData','2018-06-01 13:06:10'),(2,2,'QUALITYDATA','*','from Channel QualityData','2018-06-01 13:06:10'),(3,4,'PRODUCTDATA','*','from Channel ProductData','2018-06-01 13:06:10'),(4,3,'INTERVENTDATA','SERIALNUMBER,SERVICETYPE,SYMPTOMCODE','subset from TicketData','2018-06-12 13:18:22'),(5,3,'ISSUEDATA','SERIALNUMBER,SOCS1REPAIRCODE,SOCS2COMPONENTCODE,SOCS3DEFECTCODE,NRINTERVENTION','subset from TicketData','2018-06-12 13:18:22');
/*!40000 ALTER TABLE `DataChannel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFormat`
--

DROP TABLE IF EXISTS `DataFormat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFormat` (
  `id` int(10) unsigned NOT NULL,
  `format` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='in order to manage all message layer';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFormat`
--

LOCK TABLES `DataFormat` WRITE;
/*!40000 ALTER TABLE `DataFormat` DISABLE KEYS */;
INSERT INTO `DataFormat` VALUES (1,'DELIMITED'),(2,'JSON'),(3,'BIN-SERDES');
/*!40000 ALTER TABLE `DataFormat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FilterGroupChannel`
--

DROP TABLE IF EXISTS `FilterGroupChannel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FilterGroupChannel` (
  `id` int(10) unsigned NOT NULL,
  `idDataChannel` int(10) unsigned NOT NULL,
  `idGroupConsumer` int(10) unsigned NOT NULL,
  `fieldList` varchar(5000) DEFAULT NULL,
  `filterValue` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='every Producer in each his data channel can have the opportunity to show only a subset of data, for policy of security, privacy, business secret, and so on\nthis will be the data stream created for each role which consume the topic';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FilterGroupChannel`
--

LOCK TABLES `FilterGroupChannel` WRITE;
/*!40000 ALTER TABLE `FilterGroupChannel` DISABLE KEYS */;
INSERT INTO `FilterGroupChannel` VALUES (1,1,1,'*','NULL'),(2,2,1,'*','NULL'),(3,3,1,'*','NULL'),(4,4,1,'*','NULL'),(5,5,1,'*','NULL'),(6,1,2,'*','NULL'),(7,2,2,'*','NULL'),(8,3,2,'*','NULL'),(9,4,2,'*','NULL'),(10,5,2,'*','NULL'),(11,3,3,'*','NULL'),(12,4,3,'*','NULL'),(13,5,3,'*','NULL'),(14,3,4,'*','PRDIOBJBRAND=\'HOLCHEF\''),(15,4,4,'*','PRDIOBJBRAND=\'HOLCHEF\''),(16,5,4,'*','PRDIOBJBRAND=\'HOLCHEF\''),(17,3,5,'*','PRDIOBJBRAND=\'HOLCHEF\''),(18,4,5,'*','PRDIOBJBRAND=\'HOLCHEF\''),(19,5,5,'*','PRDIOBJBRAND=\'HOLCHEF\'');
/*!40000 ALTER TABLE `FilterGroupChannel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FilteredSubscriptionChannel`
--

DROP TABLE IF EXISTS `FilteredSubscriptionChannel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FilteredSubscriptionChannel` (
  `id` int(10) unsigned NOT NULL,
  `idDataChannel` int(10) unsigned NOT NULL,
  `idConsumer` int(10) unsigned NOT NULL,
  `idGroup` int(10) unsigned NOT NULL,
  `fieldList` varchar(5000) DEFAULT NULL,
  `filterValue` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='additional filtering rules for single consumer; this will be a query on the relative Data Stream';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FilteredSubscriptionChannel`
--

LOCK TABLES `FilteredSubscriptionChannel` WRITE;
/*!40000 ALTER TABLE `FilteredSubscriptionChannel` DISABLE KEYS */;
INSERT INTO `FilteredSubscriptionChannel` VALUES (1,0,2,1,'*',''),(2,0,3,2,'*',''),(3,0,4,3,'*',''),(4,0,5,4,'*',''),(5,0,6,5,'*',''),(6,3,7,0,'*',''),(7,4,7,0,'*','SYMPTOMCODE=\'Other problem\''),(8,5,7,0,'*','');
/*!40000 ALTER TABLE `FilteredSubscriptionChannel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GroupConsumer`
--

DROP TABLE IF EXISTS `GroupConsumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GroupConsumer` (
  `id` int(10) unsigned NOT NULL,
  `idProducer` int(10) unsigned NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='every data producer in his organization can have may users group';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GroupConsumer`
--

LOCK TABLES `GroupConsumer` WRITE;
/*!40000 ALTER TABLE `GroupConsumer` DISABLE KEYS */;
INSERT INTO `GroupConsumer` VALUES (1,1,'Customer Service'),(2,1,'Quality Service'),(3,1,'Field Service Technicians'),(4,1,'Customer Service HOLCHEF'),(5,1,'Customer Service HOLHOME');
/*!40000 ALTER TABLE `GroupConsumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `idNimbleUser` int(11) unsigned zerofill NOT NULL,
  `brand` varchar(250) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isProducer` int(1) NOT NULL,
  `isConsumer` int(1) NOT NULL,
  `producerNamespace` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='in future this will become deprecated in order to link to single sign on on Nimble';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,00000000000,'NimbleDemo','Data Producer','DP','pwd',1,0,'IT_NIMBLE_DCFSDEMO'),(2,00000000000,'NimbleDemo','CS','CS','pwd',0,1,NULL),(3,00000000000,'NimbleDemo','Quality Service','QS','pwd',0,1,NULL),(4,00000000000,'NimbleDemo','Field Service Tech','FST','pwd',0,1,NULL),(5,00000000000,'NimbleDemo','CS_HOLCHEF','CSI','pwd',0,1,NULL),(6,00000000000,'NimbleDemo','CS_HOLHOME','CSK','pwd',0,1,NULL),(7,00000000000,'NimbleDemo','FST_Freelance HOLHOME','FSTF','pwd',0,1,NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-28 16:06:32
