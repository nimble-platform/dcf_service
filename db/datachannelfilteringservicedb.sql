CREATE DATABASE  IF NOT EXISTS `datachannelfilteringservicedb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `datachannelfilteringservicedb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 149.202.69.235    Database: datachannelfilteringservicedb
-- ------------------------------------------------------
-- Server version	5.7.22

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
INSERT INTO `Channel` VALUES (1,1,'IT_WHIRPOOL_PRODUCTIONDATA',1,'TRACKINGTASKDESCR VARCHAR, SERIALNUMBER VARCHAR, ACTIONTIME VARCHAR','SERIALNUMBER','Whirpool PmlData','2018-06-01 13:06:10'),(2,1,'IT_WHIRPOOL_QUALITYDATA',1,'INSERTDATE VARCHAR, FGNum BIGINT, FGDes VARCHAR, SERIALNUMBER VARCHAR, MatNum BIGINT, MATDES VARCHAR, FamilyDesStd VARCHAR, DefectGrpDesStd VARCHAR, DefectDesSTD VARCHAR, DefectSrcDesStd VARCHAR, RepairDesSTD VARCHAR, Note VARCHAR, UserName VARCHAR','SERIALNUMBER','Whirpool QualityData','2018-06-01 13:06:10'),(3,1,'IT_WHIRPOOL_ASSISTANCEDATA',1,'SALESCOUNTRY VARCHAR, CALENDARYEAR_MONTH VARCHAR, SO_TRANSACTION__NUMBER VARCHAR, SO_BOOKING_DATE VARCHAR, SOC_ACCOUNTING_INDICATOR VARCHAR, SO_PAYMENT_DETAILS VARCHAR, BILL_TO_PARTY VARCHAR, PRD_12NC_FG_PRODUCT VARCHAR, PRD_GLOBAL_CATEGORY VARCHAR, PRD_GLOBAL_FAMILY VARCHAR, PRD_GLOBAL_GROUP VARCHAR, PRD_GLOBAL_PLATFORM VARCHAR, PRD_GLOBAL_PRODUCT_CODE VARCHAR, PRD_IOBJ__BRAND VARCHAR, PRD_IOBJ_COMM_CODE VARCHAR, PRD_IOBJ_SERIAL_NUMBER VARCHAR, PRD_LOGISTICS_BRAND VARCHAR, PRODUCTION_MONTH VARCHAR, PRODUCTION_WEEK VARCHAR, PRODUCTION_YEAR VARCHAR, PURCHASE_DATE VARCHAR, SERIALNUMBER VARCHAR, SERVICE_AREA VARCHAR, CUSTOMER VARCHAR, BAS__STREET_4 VARCHAR, BASCITYDISTRICT VARCHAR , BAS_DISTRICT VARCHAR, BAS_FLOOR VARCHAR, BAS_HOUSE_NUMBER VARCHAR, BAS_POSTAL_CODE VARCHAR, BAS_STREET VARCHAR, BAS_TEL_NUMBER VARCHAR, SERVICE_AREA_MANAGER VARCHAR, SERVICE_PARTNER VARCHAR, SERVICE_TYPE VARCHAR, SOC_JOBSHEET_NUMBER VARCHAR, SYMPTOM_CODE VARCHAR, SOC_S1__REPAIR_CODE VARCHAR, SOC_S2__COMPONENT_CODE VARCHAR, SOC_S3__DEFECT_CODE VARCHAR, NR_INTERVENTION VARCHAR','SERIALNUMBER','Whirpool CrmData','2018-06-01 13:06:10'),(4,0,'messaging_service_topic',2,'SESSIONID VARCHAR, SOURCE VARCHAR, TARGET VARCHAR, KEY VARCHAR, DATA VARCHAR','KEY','Nimble Message Service Data','2019-02-18 13:06:10'),(5,1,'IT_WHIRPOOL_RECYCLERDATA',2,'GROUP VARCHAR, PRODUCTTYPE VARCHAR, SERIALNUMBER VARCHAR,  PHOTO VARCHAR,  DESCRIPTION VARCHAR, DATA[0]_LABEL VARCHAR, DATA[0]_UNITOFMEASURE VARCHAR,  DATA[0]_VALUE VARCHAR, DATA[1]_LABEL VARCHAR, DATA[1]_UNITOFMEASURE VARCHAR,  DATA[1]_VALUE VARCHAR, DATA[2]_LABEL VARCHAR, DATA[2]_UNITOFMEASURE VARCHAR,  DATA[2]_VALUE VARCHAR, DATA[3]_LABEL VARCHAR, DATA[3]_UNITOFMEASURE VARCHAR,  DATA[3]_VALUE VARCHAR, DATA[4]_LABEL VARCHAR, DATA[4]_UNITOFMEASURE VARCHAR,  DATA[4]_VALUE VARCHAR, DATA[5]_LABEL VARCHAR, DATA[5]_UNITOFMEASURE VARCHAR,  DATA[5]_VALUE VARCHAR, DATA[6]_LABEL VARCHAR, DATA[6]_UNITOFMEASURE VARCHAR,  DATA[6]_VALUE VARCHAR, DATA[7]_LABEL VARCHAR, DATA[7]_UNITOFMEASURE VARCHAR,  DATA[7]_VALUE VARCHAR, DATA[8]_LABEL VARCHAR, DATA[8]_UNITOFMEASURE VARCHAR,  DATA[8]_VALUE VARCHAR, DATA[9]_LABEL VARCHAR, DATA[9]_UNITOFMEASURE VARCHAR,  DATA[9]_VALUE VARCHAR, DATA[10]_LABEL VARCHAR, DATA[10]_UNITOFMEASURE VARCHAR,  DATA[10]_VALUE VARCHAR, DATA[11]_LABEL VARCHAR, DATA[11]_UNITOFMEASURE VARCHAR,  DATA[11]_VALUE VARCHAR, DATA[12]_LABEL VARCHAR, DATA[12]_UNITOFMEASURE VARCHAR,  DATA[12]_VALUE VARCHAR, DATA[13]_LABEL VARCHAR, DATA[13]_UNITOFMEASURE VARCHAR,  DATA[13]_VALUE VARCHAR, DATA[14]_LABEL VARCHAR, DATA[14]_UNITOFMEASURE VARCHAR,  DATA[14]_VALUE VARCHAR, DATA[15]_LABEL VARCHAR, DATA[15]_UNITOFMEASURE VARCHAR,  DATA[15]_VALUE VARCHAR','SERIALNUMBER','Recycler Data','2019-05-27 09:06:10');
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
INSERT INTO `DataChannel` VALUES (1,1,'PRODUCTIONDATA','*','Whirpool PmlData','2018-06-01 13:06:10'),(2,2,'QUALITYDATA','*','Whirpool QualityData','2018-06-01 13:06:10'),(3,3,'PRODUCTDATA','SERIALNUMBER,PRD_IOBJ__BRAND,BASCITYDISTRICT,SALESCOUNTRY,CALENDARYEAR_MONTH,PRD_12NC_FG_PRODUCT,PRD_GLOBAL_CATEGORY,PRD_GLOBAL_FAMILY,PRD_GLOBAL_GROUP,PRD_GLOBAL_PLATFORM,PRD_GLOBAL_PRODUCT_CODE,PRD_IOBJ_COMM_CODE,PRD_IOBJ_SERIAL_NUMBER,PRD_LOGISTICS_BRAND,PRODUCTION_MONTH,PRODUCTION_WEEK,PRODUCTION_YEAR','from Whirpool AsistenceData','2018-06-01 13:06:10'),(4,3,'CUSTOMERDATA','SERIALNUMBER,PRD_IOBJ__BRAND,BASCITYDISTRICT,BILL_TO_PARTY,PRD_LOGISTICS_BRAND,PURCHASE_DATE,SERVICE_AREA,CUSTOMER,BAS__STREET_4,BAS_DISTRICT,BAS_FLOOR,BAS_HOUSE_NUMBER,BAS_POSTAL_CODE,BAS_STREET,BAS_TEL_NUMBER,SERVICE_AREA_MANAGER','from Whirpool AsistenceData','2018-06-12 13:18:22'),(5,3,'INTERVENTDATA','SERIALNUMBER,PRD_IOBJ__BRAND,BASCITYDISTRICT,SOC_S1__REPAIR_CODE,SOC_S2__COMPONENT_CODE,SOC_S3__DEFECT_CODE,NR_INTERVENTION','from Whirpool AsistenceData','2018-06-12 13:18:22'),(6,3,'ISSUEDATA','SERIALNUMBER,PRD_IOBJ__BRAND,BASCITYDISTRICT,SO_TRANSACTION__NUMBER,SO_BOOKING_DATE,SOC_ACCOUNTING_INDICATOR,SO_PAYMENT_DETAILS,SERVICE_AREA_MANAGER,SERVICE_PARTNER,SERVICE_TYPE,SOC_JOBSHEET_NUMBER,SYMPTOM_CODE','from Whirpool AsistenceData','2018-06-12 13:18:22'),(7,4,'MESSAGESERVICE','*','data integration example','2019-02-18 13:06:10'),(8,4,'MESSAGESERVICEWHI','*','data integration example filtered','2019-02-18 13:06:10'),(9,5,'RECYCLERWHI','*','Whirpool Recycler Data','2019-05-27 09:06:10');
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
INSERT INTO `FilterGroupChannel` VALUES (0,0,0,'fieldList','filterValue'),(1,1,1,'*','NULL'),(2,2,1,'*','NULL'),(3,3,1,'*','NULL'),(4,4,1,'*','NULL'),(5,5,1,'*','NULL'),(6,6,1,'*','NULL'),(7,1,2,'*','NULL'),(8,2,2,'*','NULL'),(9,3,2,'*','NULL'),(10,4,2,'*','NULL'),(11,5,2,'*','NULL'),(12,6,2,'*','NULL'),(13,3,3,'*','NULL'),(14,4,3,'*','NULL'),(15,5,3,'*','NULL'),(16,6,3,'*','NULL'),(17,3,4,'*','PRD_IOBJ__BRAND=\'IKEA\''),(18,4,4,'*','PRD_IOBJ__BRAND=\'IKEA\''),(19,5,4,'*','PRD_IOBJ__BRAND=\'IKEA\''),(20,6,4,'*','PRD_IOBJ__BRAND=\'IKEA\''),(21,3,5,'*','PRD_IOBJ__BRAND=\'KitchenAid\''),(22,4,5,'*','PRD_IOBJ__BRAND=\'KitchenAid\''),(23,5,5,'*','PRD_IOBJ__BRAND=\'KitchenAid\''),(24,6,5,'*','PRD_IOBJ__BRAND=\'KitchenAid\'');
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
INSERT INTO `FilteredSubscriptionChannel` VALUES (1,0,2,1,'NULL','NULL'),(2,0,3,2,'NULL','NULL'),(3,0,4,3,'NULL','NULL'),(4,0,5,4,'NULL','NULL'),(5,0,6,5,'NULL','NULL'),(6,3,7,0,'*','BASCITYDISTRICT=\'MILANO\''),(7,4,7,0,'*','BASCITYDISTRICT=\'MILANO\''),(8,5,7,0,'*','BASCITYDISTRICT=\'MILANO\''),(9,6,7,0,'*','BASCITYDISTRICT=\'MILANO\''),(10,8,7,0,'*','`SOURCE`=\'whi\'');
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
INSERT INTO `GroupConsumer` VALUES (1,1,'Customer Service'),(2,1,'Quality Service'),(3,1,'Field Service Technicians'),(4,1,'Customer Service Ikea'),(5,1,'Customer Service KitchenAid');
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
INSERT INTO `User` VALUES (0,00000000000,'Nimble','Nimble','nimble','nimble',1,1,'DCFS'),(1,00000000000,'Whirpool','Data Producer','DP','pwd',1,0,'IT_WHIRPOOL'),(2,00000000000,'Whirpool','CS','CS','pwd',0,1,NULL),(3,00000000000,'Whirpool','Quality Service','QS','pwd',0,1,NULL),(4,00000000000,'Whirpool','Field Service Tech','FST','pwd',0,1,NULL),(5,00000000000,'Whirpool','CS_IKEA','CSI','pwd',0,1,NULL),(6,00000000000,'Whirpool','CS_KitchenAd','CSK','pwd',0,1,NULL),(7,00000000000,'Whirpool','FST_Freelance IKEA','FSTF','pwd',0,1,NULL);
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

-- Dump completed on 2019-05-27 10:14:01
