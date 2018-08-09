-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: factoryrp
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `contact_company_info`
--

DROP TABLE IF EXISTS `contact_company_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_company_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '单位编码',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '单位名称',
  `contact_name` varchar(20) DEFAULT '' COMMENT '默认联系人',
  `contact_phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(11) DEFAULT NULL COMMENT '传真',
  `tax_number` varchar(20) DEFAULT NULL COMMENT '税号',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `zip_code` varchar(10) DEFAULT NULL COMMENT '邮编',
  `bank_name` varchar(30) DEFAULT NULL COMMENT '开户银行名称',
  `bank_number` varchar(30) DEFAULT NULL COMMENT '开户银行账号',
  `web_site` varchar(50) DEFAULT NULL COMMENT '单位网址',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注信息',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `status_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，1:有效；0:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='往来单位信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_company_info`
--

LOCK TABLES `contact_company_info` WRITE;
/*!40000 ALTER TABLE `contact_company_info` DISABLE KEYS */;
INSERT INTO `contact_company_info` VALUES (7,'01','大连矿山起重机械有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:32:58','2018-01-17 05:32:58'),(8,'02','山西东方智能物流股份有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:42:05','2018-01-17 05:42:05'),(9,'03','河南卫华起重机械有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:44:02','2018-01-17 05:44:02'),(10,'04','setaram',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:44:25','2018-01-17 05:44:25'),(11,'05','吉林市吉炭工程有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:55:25','2018-01-17 05:55:25'),(12,'06','上海震鸣电子设备有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:55:43','2018-01-17 05:55:43'),(13,'07','江苏海安振动机械厂',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:55:56','2018-01-17 05:55:56'),(14,'08','河南矿山重型起重机械有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 05:56:17','2018-01-17 05:56:17'),(15,'09','洛阳东信中央空调公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:09:58','2018-01-17 06:09:58'),(16,'10','常州能源设备总厂有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:10:16','2018-01-17 06:10:16'),(17,'11','辽宁朝阳宏达机械有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:10:39','2018-01-17 06:10:39'),(18,'12','辽宁矿山设备机械有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:10:50','2018-01-17 06:10:50'),(19,'13','西安机电研究所',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:21:52','2018-01-17 06:21:52'),(20,'14','靖江市双轮水泵厂',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:22:06','2018-01-17 06:22:06'),(21,'15','郑州江河机电设备工程有限责任公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:22:16','2018-01-17 06:22:16'),(22,'16','大连第二电机厂',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:31:56','2018-01-17 06:31:56'),(23,'17','大连宏大电机配件厂',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:32:05','2018-01-17 06:32:05'),(24,'18','江苏泰隆减速机股份有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:32:15','2018-01-17 06:32:15'),(25,'19','天津SEW',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:32:29','2018-01-17 06:32:29'),(26,'20','焦作市制动器有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:32:38','2018-01-17 06:32:38'),(27,'21','岳阳华立电缆卷筒厂',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:32:50','2018-01-17 06:32:50'),(28,'22','上海英格索兰空压机有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:33:01','2018-01-17 06:33:01'),(29,'23','山西盛源昌电机有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:57:26','2018-01-17 06:57:26'),(30,'24','天津大明电机股份',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:57:38','2018-01-17 06:57:38'),(31,'25','天津减速机股份有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-18 05:32:22','2018-01-18 05:32:22'),(33,'26','榆次液压有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-18 05:32:27','2018-01-18 05:32:27'),(34,'27','江苏市奥林斯帮热能有限公司',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-04-09 03:41:55','2018-04-09 03:41:55');
/*!40000 ALTER TABLE `contact_company_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_info`
--

DROP TABLE IF EXISTS `department_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '部门编码',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门',
  `show_order` int(3) DEFAULT '999' COMMENT '显示顺序，值越小显示越靠前',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `status_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，1:有效；0:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='部门信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_info`
--

LOCK TABLES `department_info` WRITE;
/*!40000 ALTER TABLE `department_info` DISABLE KEYS */;
INSERT INTO `department_info` VALUES (35,'101','电解厂',-1,999,1,1,'2018-01-16 07:32:58','2018-01-16 07:32:58'),(38,'10103','设备安环科',35,999,1,1,'2018-01-16 07:35:31','2018-01-16 07:35:31'),(40,'1010301','电解一车间',38,999,1,1,'2018-01-16 07:36:57','2018-01-16 07:36:57'),(42,'1010302','电解二车间',38,999,1,1,'2018-01-16 07:37:37','2018-01-16 07:37:37'),(43,'1010303','供电车间',38,999,1,1,'2018-01-16 07:37:53','2018-01-16 07:37:53'),(44,'1010304','净化车间',38,999,1,1,'2018-01-16 07:38:11','2018-01-16 07:38:11'),(45,'1010305','铸造车间',38,999,1,1,'2018-01-16 07:38:24','2018-01-16 07:38:24'),(46,'101030101','一班',40,999,1,1,'2018-01-16 07:40:28','2018-01-16 07:40:28'),(47,'101030102','二班',40,999,1,1,'2018-01-16 07:40:46','2018-01-16 07:40:46'),(48,'101030201','一班',42,999,1,1,'2018-01-16 07:41:02','2018-01-16 07:41:02'),(49,'101030202','二班',42,999,1,1,'2018-01-16 07:41:09','2018-01-16 07:41:09'),(50,'102','炭素厂',-1,999,1,1,'2018-01-16 07:41:57','2018-01-16 07:41:57'),(51,'103','运维中心',-1,999,1,1,'2018-01-16 07:42:22','2018-01-16 07:42:22'),(52,'10201','设备科',50,999,1,1,'2018-01-16 09:04:54','2018-01-16 09:04:54'),(54,'1020101','煅烧车间',52,999,1,1,'2018-01-16 09:05:42','2018-01-16 09:05:42'),(55,'1020102','成型车间',52,999,1,1,'2018-01-16 09:05:58','2018-01-16 09:05:58'),(56,'1020103','焙烧车间',52,999,1,1,'2018-01-16 09:06:17','2018-01-16 09:06:17'),(57,'1020104','组装车间',52,999,1,1,'2018-01-16 09:06:32','2018-01-16 09:06:32'),(58,'102010101','热媒一班',54,999,1,1,'2018-01-16 09:15:26','2018-01-16 09:15:26'),(59,'102010102','热媒二班',54,999,1,1,'2018-01-16 09:15:45','2018-01-16 09:15:45'),(60,'102010103','热媒三班',54,999,1,1,'2018-01-16 09:16:00','2018-01-16 09:16:00'),(61,'102010104','热媒四班',54,999,1,1,'2018-01-16 09:16:13','2018-01-16 09:16:13'),(62,'102010105','锻后班',54,999,1,1,'2018-01-16 09:16:29','2018-01-16 09:16:29'),(64,'102010201','成型一班',55,999,1,1,'2018-01-16 09:18:11','2018-01-16 09:18:11'),(65,'102010202','成型二班',55,999,1,1,'2018-01-16 09:18:38','2018-01-16 09:18:38'),(66,'102010203','成型三班',55,999,1,1,'2018-01-16 09:18:48','2018-01-16 09:18:48'),(67,'102010204','成型四班',55,999,1,1,'2018-01-16 09:18:57','2018-01-16 09:18:57'),(68,'102010301','焙烧一班',56,999,1,1,'2018-01-16 09:20:24','2018-01-16 09:20:24'),(69,'102010302','焙烧二班',56,999,1,1,'2018-01-16 09:21:06','2018-01-16 09:21:06'),(70,'102010303','焙烧三班',56,999,1,1,'2018-01-16 09:21:17','2018-01-16 09:21:17'),(71,'102010304','焙烧四班',56,999,1,1,'2018-01-16 09:21:30','2018-01-16 09:21:30'),(72,'102010401','组装一班',57,999,1,1,'2018-01-16 09:22:13','2018-01-16 09:22:13'),(73,'102010402','组装二班',57,999,1,1,'2018-01-16 09:22:55','2018-01-16 09:22:55'),(74,'102010403','组装三班',57,999,1,1,'2018-01-16 09:23:07','2018-01-16 09:23:07'),(75,'102010404','组装四班',57,999,1,1,'2018-01-16 09:23:20','2018-01-16 09:23:20'),(76,'102010405','悬链班',57,999,1,1,'2018-01-16 09:24:07','2018-01-16 09:24:07'),(77,'102010406','破碎一班',57,999,1,1,'2018-01-16 09:24:27','2018-01-16 09:24:27'),(78,'102010407','破碎二班',57,999,1,1,'2018-01-16 09:24:43','2018-01-16 09:24:43'),(79,'102010408','破碎三班',57,999,1,1,'2018-01-16 09:24:55','2018-01-16 09:24:55'),(80,'102010409','破碎四班',57,999,1,1,'2018-01-16 09:25:06','2018-01-16 09:25:06'),(81,'10301','综合安全科',51,999,1,1,'2018-01-16 09:27:20','2018-01-16 09:27:20'),(83,'1030101','能源车间',81,999,1,1,'2018-01-16 09:28:57','2018-01-16 09:28:57'),(84,'1030102','检修车间',81,999,1,1,'2018-01-16 09:29:20','2018-01-16 09:29:20'),(85,'1030103','包保车间',81,999,1,1,'2018-01-16 09:29:35','2018-01-16 09:29:35'),(86,'103010101','一班',83,999,1,1,'2018-01-16 09:30:21','2018-01-16 09:30:21'),(87,'103010201','一班',84,999,1,1,'2018-01-16 09:30:39','2018-01-16 09:30:39'),(88,'103010301','煅烧车间包保班组',85,999,1,1,'2018-01-16 09:30:54','2018-01-16 09:30:54'),(89,'103010302','成型车间包保班组',85,999,1,1,'2018-04-10 09:33:23','2018-04-10 09:33:23'),(90,'103010303','焙烧车间包保班组',85,999,1,1,'2018-04-10 09:33:36','2018-04-10 09:33:36'),(91,'103010304','组装车间包保班组',85,999,1,1,'2018-04-10 09:33:44','2018-04-10 09:33:44');
/*!40000 ALTER TABLE `department_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_info`
--

DROP TABLE IF EXISTS `device_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '设备名称',
  `specification` varchar(20) DEFAULT NULL COMMENT '规格型号',
  `code` varchar(17) NOT NULL DEFAULT '' COMMENT '设备编号',
  `manufacturer` bigint(20) DEFAULT NULL COMMENT '生产厂商',
  `supplier` bigint(20) DEFAULT NULL COMMENT '供应商',
  `device_type` bigint(20) DEFAULT NULL COMMENT '设备类别',
  `buy_date` date DEFAULT NULL COMMENT '购置时间',
  `source_price` int(7) DEFAULT NULL COMMENT '资产原值',
  `net_worth` int(7) DEFAULT NULL COMMENT '资产净值',
  `useful_life` int(3) DEFAULT NULL COMMENT '折旧年限',
  `net_residue_rate` double(4,2) DEFAULT NULL COMMENT '净残率',
  `device_flag` varchar(20) DEFAULT NULL COMMENT '设备标识',
  `head` varchar(10) DEFAULT NULL COMMENT '负责人',
  `use_status` int(2) DEFAULT NULL COMMENT '使用状况',
  `installation_address` varchar(80) DEFAULT NULL COMMENT '安装地点',
  `operator` varchar(10) DEFAULT NULL COMMENT '操作员',
  `use_dept` bigint(20) DEFAULT NULL COMMENT '使用部门',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `extend_field_one` varchar(50) DEFAULT NULL COMMENT '自定义字段1',
  `extend_field_two` varchar(50) DEFAULT NULL COMMENT '自定义字段2',
  `extend_field_three` varchar(50) DEFAULT NULL COMMENT '自定义字段3',
  `extend_field_four` varchar(50) DEFAULT NULL COMMENT '自定义字段4',
  `extend_field_five` varchar(50) DEFAULT NULL COMMENT '自定义字段5',
  `extend_field_six` varchar(50) DEFAULT NULL COMMENT '自定义字段6',
  `extend_field_seven` varchar(50) DEFAULT NULL COMMENT '自定义字段7',
  `extend_field_eight` varchar(50) DEFAULT NULL COMMENT '自定义字段8',
  `extend_field_nine` varchar(50) DEFAULT NULL COMMENT '自定义字段9',
  `extend_field_ten` varchar(50) DEFAULT NULL COMMENT '自定义字段10',
  `extend_number_field` int(11) DEFAULT NULL COMMENT '自定义数字',
  `extend_date_field` date DEFAULT NULL COMMENT '自定义日期',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `status_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，1:有效；0:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_info`
--

LOCK TABLES `device_info` WRITE;
/*!40000 ALTER TABLE `device_info` DISABLE KEYS */;
INSERT INTO `device_info` VALUES (57,'热媒锅炉','YY(Q)W-3500Y(Q)','HS-TS-DS-RMGL',34,34,45,'2017-07-12',NULL,NULL,NULL,NULL,'重点设备','曹强',18,'热媒锅炉房','田伟',54,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-01-17 06:15:06','2018-04-09 03:45:44'),(75,'2#沥青泵','BTB32-200','2#',NULL,NULL,45,NULL,NULL,NULL,NULL,NULL,'重点设备',NULL,3,'沥青熔化库','',54,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-04-08 07:03:26','2018-04-08 07:03:26'),(76,'混捏锅','HND-4000L','1#',NULL,NULL,52,'2003-08-20',NULL,NULL,NULL,NULL,'重点设备','李克敏',18,'成型一楼','李明亮/宋建飞',55,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'2018-05-22 11:51:55','2018-05-22 12:01:13');
/*!40000 ALTER TABLE `device_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_info_extend_field`
--

DROP TABLE IF EXISTS `device_info_extend_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_info_extend_field` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `extend_field_one` varchar(50) DEFAULT NULL COMMENT '自定义字段1',
  `extend_field_two` varchar(50) DEFAULT NULL COMMENT '自定义字段2',
  `extend_field_three` varchar(50) DEFAULT NULL COMMENT '自定义字段3',
  `extend_field_four` varchar(50) DEFAULT NULL COMMENT '自定义字段4',
  `extend_field_five` varchar(50) DEFAULT NULL COMMENT '自定义字段5',
  `extend_field_six` varchar(50) DEFAULT NULL COMMENT '自定义字段6',
  `extend_field_seven` varchar(50) DEFAULT NULL COMMENT '自定义字段7',
  `extend_field_eight` varchar(50) DEFAULT NULL COMMENT '自定义字段8',
  `extend_field_nine` varchar(50) DEFAULT NULL COMMENT '自定义字段9',
  `extend_field_ten` varchar(50) DEFAULT NULL COMMENT '自定义字段10',
  `extend_number_field` varchar(50) DEFAULT NULL COMMENT '自定义数字',
  `extend_date_field` varchar(50) DEFAULT NULL COMMENT '自定义日期',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `status_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，1:有效；0:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_info_extend_field`
--

LOCK TABLES `device_info_extend_field` WRITE;
/*!40000 ALTER TABLE `device_info_extend_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_info_extend_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_parameter_dictionary`
--

DROP TABLE IF EXISTS `device_parameter_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_parameter_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT 'createTime',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `code` varchar(50) DEFAULT NULL COMMENT '设备参数code，区分不同种类的设备参数 code值可以相同',
  `name` varchar(20) DEFAULT NULL COMMENT '设备参数name，同一个code下面的name不可相同',
  `type` int(20) DEFAULT NULL COMMENT '对应枚举的类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='设备参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_parameter_dictionary`
--

LOCK TABLES `device_parameter_dictionary` WRITE;
/*!40000 ALTER TABLE `device_parameter_dictionary` DISABLE KEYS */;
INSERT INTO `device_parameter_dictionary` VALUES (6,1,'2017-12-13 12:04:19','2017-12-13 12:04:19','device_measuring_unit','个',1),(7,1,'2017-12-13 12:04:36','2017-12-13 12:04:36','device_measuring_unit','台',2),(8,1,'2017-12-13 12:04:45','2017-12-13 12:04:45','device_measuring_unit','条',3),(9,1,'2017-12-13 12:04:58','2017-12-13 12:04:58','device_measuring_unit','箱',4),(10,1,'2017-12-13 12:05:41','2017-12-13 12:05:41','device_measuring_unit','块',5),(11,1,'2017-12-13 12:05:57','2017-12-13 12:05:57','device_measuring_unit','套',6),(12,1,'2017-12-13 12:06:07','2017-12-13 12:06:07','device_measuring_unit','次',7),(14,1,'2017-12-13 14:41:30','2017-12-13 14:41:30','device_measuring_unit','辆',8),(17,1,'2017-12-14 14:19:34','2017-12-14 14:19:34','device_use_status','其它',3),(18,1,'2017-12-14 14:20:21','2017-12-14 14:20:21','device_use_status','在用',4),(19,1,'2017-12-14 14:20:27','2017-12-14 14:20:27','device_use_status','待检',5),(20,1,'2017-12-14 14:20:39','2017-12-14 14:20:39','device_use_status','停用',6),(21,1,'2017-12-14 14:20:45','2017-12-14 14:20:45','device_use_status','报废',7),(22,1,'2017-12-14 14:20:52','2017-12-14 14:20:52','device_use_status','未验收入账',8),(23,1,'2017-12-14 14:48:10','2017-12-14 14:48:10','device_trouble_level','紧急',1),(24,1,'2017-12-14 14:48:20','2017-12-14 14:48:20','device_trouble_level','一般',2),(25,1,'2017-12-14 14:48:27','2017-12-14 14:48:27','device_trouble_level','其他',3),(26,1,'2017-12-14 15:04:05','2017-12-14 15:04:05','device_trouble_reason','自然磨损',1),(27,1,'2017-12-14 15:04:12','2017-12-14 15:04:12','device_trouble_reason','违章操作',2),(28,1,'2017-12-14 15:04:17','2017-12-14 15:04:17','device_trouble_reason','配件质量差',3),(29,1,'2017-12-14 15:04:23','2017-12-14 15:04:23','device_trouble_reason','维护保养不到位',4),(30,1,'2017-12-14 15:04:28','2017-12-14 15:04:28','device_trouble_reason','其他',5),(31,1,'2017-12-14 15:06:50','2017-12-14 15:06:50','device_repair_level','常见故障维修',1),(32,1,'2017-12-14 15:07:01','2017-12-14 15:07:01','device_repair_level','突发性故障维修',2),(33,1,'2017-12-14 15:07:14','2017-12-14 15:07:14','device_repair_level','计划项目维修',3),(34,1,'2017-12-14 15:07:21','2017-12-14 15:07:21','device_repair_level','不正当使用维修',4),(35,1,'2017-12-14 15:09:46','2017-12-14 15:09:46','device_maintenance_level','日常保养',1),(36,1,'2017-12-14 15:09:53','2017-12-14 15:09:53','device_maintenance_level','常规润滑',2),(37,1,'2017-12-14 15:09:59','2017-12-14 15:09:59','device_maintenance_level','二级检修保养',3),(38,1,'2017-12-14 15:10:07','2017-12-14 15:10:07','device_maintenance_level','三级检修保养',4),(39,1,'2017-12-14 15:10:12','2017-12-14 15:10:12','device_maintenance_level','项目检修保养',5),(40,1,'2017-12-14 15:10:18','2017-12-14 15:10:18','device_maintenance_level','年度检修保养',6),(41,1,'2017-12-14 15:13:15','2017-12-14 15:13:15','device_device_flag','重点设备',1),(42,1,'2017-12-14 15:13:22','2017-12-14 15:13:22','device_device_flag','主要设备',2),(43,1,'2017-12-14 15:13:27','2017-12-14 15:13:27','device_device_flag','一般设备',3),(44,1,'2017-12-14 15:13:31','2017-12-14 15:13:31','device_device_flag','AAA类',4),(45,1,'2017-12-14 15:13:37','2017-12-14 15:13:37','device_device_flag','B类',5),(46,1,'2017-12-14 15:15:35','2017-12-14 15:15:35','device_bad_review_reason','返修率高，不能一次处理完好。',1),(47,1,'2017-12-14 15:15:45','2017-12-14 15:15:45','device_bad_review_reason','维修速度慢。',2),(48,1,'2017-12-14 15:15:50','2017-12-14 15:15:50','device_bad_review_reason','技术能力差。',3),(49,1,'2017-12-14 15:15:58','2017-12-14 15:15:58','device_bad_review_reason','野蛮处理维修事务。',4),(50,1,'2017-12-14 15:16:04','2017-12-14 15:16:04','device_bad_review_reason','不能兑现承诺维修时间',5),(51,1,'2017-12-14 15:16:09','2017-12-14 15:16:09','device_bad_review_reason','维修质量差',6),(52,1,'2017-12-14 15:16:14','2017-12-14 15:16:14','device_bad_review_reason','存在安全隐患',7),(53,1,'2017-12-14 15:16:21','2017-12-14 15:16:21','device_bad_review_reason','服务态度差',8),(54,1,'2017-12-14 15:16:28','2017-12-14 15:16:28','device_bad_review_reason','其他',9),(56,1,'2017-12-27 17:21:22','2017-12-27 17:21:22','device_process_type','故障报修',1),(57,1,'2017-12-27 17:31:50','2017-12-27 17:31:50','device_process_type','设备调拨',2),(58,1,'2017-12-27 17:32:04','2017-12-27 17:32:04','device_process_type','设备报废/封存/状态改变',3),(59,1,'2017-12-27 17:32:14','2017-12-27 17:32:14','device_process_type','设备领用',4),(60,1,'2017-12-27 17:32:23','2017-12-27 17:32:23','device_process_type','设备移交',5),(61,1,'2017-12-27 17:32:34','2017-12-27 17:32:34','device_process_type','设备还回',6),(62,1,'2017-12-27 17:32:46','2017-12-27 17:32:46','device_process_type','备件购置',7),(63,1,'2017-12-27 17:32:46','2017-12-27 17:32:46','device_running_status','带病运行',1),(64,1,'2017-12-27 17:32:46','2017-12-27 17:32:46','device_running_status','停机维修',2),(65,1,'2017-12-27 17:32:46','2017-12-27 17:32:46','device_running_status','其他',3);
/*!40000 ALTER TABLE `device_parameter_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process`
--

DROP TABLE IF EXISTS `device_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `process_name` varchar(20) DEFAULT NULL COMMENT '流程名称',
  `process_remark` varchar(255) DEFAULT NULL COMMENT '流程备注',
  `process_stage` varchar(50) DEFAULT NULL COMMENT '流程阶段',
  `process_type` varchar(50) DEFAULT NULL COMMENT '流程类型',
  `trigger_condition` bigint(20) DEFAULT NULL COMMENT '条件详情',
  `trigger_condition_type` bigint(20) DEFAULT NULL COMMENT '触发条件类型 对应设备类型 金额上限 部门等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='设备流程实体';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process`
--

LOCK TABLES `device_process` WRITE;
/*!40000 ALTER TABLE `device_process` DISABLE KEYS */;
INSERT INTO `device_process` VALUES (10,1,'2018-04-08 20:52:33','2018-04-08 20:52:33','煅烧车间报修','煅烧车间报修审核流程','device_process_phase_application_approval','device_process_type_malfunction_repair',54,3),(11,1,'2018-04-09 10:32:42','2018-04-09 10:32:42','煅烧车间验收','','device_process_repaired_ended_verifying','device_process_type_malfunction_repair',54,3),(13,1,'2018-05-22 19:24:13','2018-05-22 19:24:13','成型车间报修','','device_process_phase_application_approval','device_process_type_malfunction_repair',55,3),(14,1,'2018-05-22 19:28:27','2018-05-22 19:28:27','成型车间验收','','device_process_repaired_ended_verifying','device_process_type_malfunction_repair',55,3);
/*!40000 ALTER TABLE `device_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process_detail`
--

DROP TABLE IF EXISTS `device_process_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `process_audit_type` int(20) DEFAULT NULL COMMENT '审核类型 标识是指定审核人还是指定审核组 1审核人2审核组 默认审核人',
  `handle_demand_type` bigint(20) DEFAULT NULL COMMENT '处理要求类型 1单人签署生效2多人签署生效',
  `process_auditor` text COMMENT '审核人 这个流程审核关联表的id 多个用逗号分隔',
  `process_step` int(20) DEFAULT NULL COMMENT '流程步骤 对于同一个流程，依次递增',
  `process_id` bigint(20) DEFAULT NULL COMMENT 'process_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='设备流程详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process_detail`
--

LOCK TABLES `device_process_detail` WRITE;
/*!40000 ALTER TABLE `device_process_detail` DISABLE KEYS */;
INSERT INTO `device_process_detail` VALUES (11,1,'2018-04-08 20:52:33','2018-04-08 20:52:33',1,1,'[19520]',1,10),(12,1,'2018-04-09 10:32:42','2018-04-09 10:32:42',1,1,'[19520,400512,786496,813056,73728,867584]',1,11),(14,1,'2018-05-22 19:24:13','2018-05-22 19:24:13',1,1,'[595008,158912,958784]',1,13),(15,1,'2018-05-22 19:28:27','2018-05-22 19:28:27',1,1,'[595008,158912,958784,116416,353280,985920,628736]',1,14);
/*!40000 ALTER TABLE `device_process_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process_handler_info`
--

DROP TABLE IF EXISTS `device_process_handler_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process_handler_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `corporate_identify` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `process_detail` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process_handler_info`
--

LOCK TABLES `device_process_handler_info` WRITE;
/*!40000 ALTER TABLE `device_process_handler_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_process_handler_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process_instance`
--

DROP TABLE IF EXISTS `device_process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT 'update_time',
  `current_step` int(11) DEFAULT NULL COMMENT '当前实例所处步骤',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程id,表明开启哪种流程',
  `process_status` int(11) DEFAULT NULL COMMENT '流程实例状态，1开启，2正在进行，3正常结束，4终止流程,5流程设置因为中途改变而作废',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process_instance`
--

LOCK TABLES `device_process_instance` WRITE;
/*!40000 ALTER TABLE `device_process_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_process_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process_phase`
--

DROP TABLE IF EXISTS `device_process_phase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process_phase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '企业唯一标识主键',
  `corporate_identify` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `device_process_phase` varchar(50) DEFAULT NULL COMMENT '流程阶段',
  `device_process_type_id` bigint(20) DEFAULT NULL COMMENT '用于管理效能device_process_type表主键的id',
  `code` varchar(50) DEFAULT NULL COMMENT '阶段枚举代码',
  `order_number` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `流程类型外键和阶段code唯一索引` (`device_process_type_id`,`code`) USING BTREE,
  KEY `FKbwll8fxfi2cyipgae193l8bvi` (`device_process_type_id`),
  CONSTRAINT `FKbwll8fxfi2cyipgae193l8bvi` FOREIGN KEY (`device_process_type_id`) REFERENCES `device_process_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='流程阶段表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process_phase`
--

LOCK TABLES `device_process_phase` WRITE;
/*!40000 ALTER TABLE `device_process_phase` DISABLE KEYS */;
INSERT INTO `device_process_phase` VALUES (1,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',1,'device_process_phase_application_approval',1),(2,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',2,'device_process_phase_application_approval',2),(3,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','维修结束待验证',2,'device_process_repaired_ended_verifying',3),(4,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',3,'device_process_phase_application_approval',4),(5,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','维修结束待验证',3,'device_process_repaired_ended_verifying',5),(7,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',4,'device_process_phase_application_approval',7),(8,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',5,'device_process_phase_application_approval',8),(9,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',6,'device_process_phase_application_approval',9),(11,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',8,'device_process_phase_application_approval',11),(12,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','维修结束待验证',9,'device_process_repaired_ended_verifying',12),(13,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',9,'device_process_phase_application_approval',13),(14,1,'2018-01-10 17:08:16','2018-01-10 17:08:16','申请审核',10,'device_process_phase_application_approval',14),(15,NULL,'2018-04-09 16:59:39','2018-04-09 16:59:41','故障分析',2,'device_process_repaired_malfunction_analysis',4);
/*!40000 ALTER TABLE `device_process_phase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process_runtime_info`
--

DROP TABLE IF EXISTS `device_process_runtime_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process_runtime_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `handler_success_number` int(11) DEFAULT NULL COMMENT '处理成功的人数',
  `process_detail_id` bigint(20) DEFAULT NULL COMMENT '流程详情id，表明正在进行哪个流程具体步骤',
  `process_instance_id` bigint(20) DEFAULT NULL COMMENT '流程实例ID，表明是属于哪个流程实例的',
  `process_runtime_status` int(11) DEFAULT NULL COMMENT '流程运行时状态 1正在运行2成功3失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程运行时数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process_runtime_info`
--

LOCK TABLES `device_process_runtime_info` WRITE;
/*!40000 ALTER TABLE `device_process_runtime_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_process_runtime_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_process_type`
--

DROP TABLE IF EXISTS `device_process_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_process_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `device_process_type` varchar(50) DEFAULT NULL COMMENT '流程类型',
  `code` varchar(50) DEFAULT NULL COMMENT '类型枚举代码',
  `order_number` int(11) DEFAULT NULL COMMENT '流程类型排序号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `流程类型code唯一索引` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='设备流程类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_process_type`
--

LOCK TABLES `device_process_type` WRITE;
/*!40000 ALTER TABLE `device_process_type` DISABLE KEYS */;
INSERT INTO `device_process_type` VALUES (1,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','设备领用','device_process_type_receive',1),(2,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','故障报修','device_process_type_malfunction_repair',2),(3,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','外委维修','device_process_entrust_repair',3),(4,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','设备购置','device_process_purchase',4),(5,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','设备调拨','device_process_allot',5),(6,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','设备报废/封存/状态改变','device_process_unused',6),(8,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','设备移交','device_process_transfer',8),(9,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','设备还回','device_process_give_back',9),(10,1,'2018-01-10 17:00:23','2018-01-10 17:00:23','备件购置','device_process_spare_part_purchase',10);
/*!40000 ALTER TABLE `device_process_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_spare_part_rel`
--

DROP TABLE IF EXISTS `device_spare_part_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_spare_part_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL COMMENT '设备主键',
  `spare_part_id` bigint(20) NOT NULL COMMENT '备件主键',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='设备—备件关联信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_spare_part_rel`
--

LOCK TABLES `device_spare_part_rel` WRITE;
/*!40000 ALTER TABLE `device_spare_part_rel` DISABLE KEYS */;
INSERT INTO `device_spare_part_rel` VALUES (9,57,51,1,'2018-04-09 03:21:37','2018-04-09 03:21:37'),(10,57,50,1,'2018-04-09 03:21:37','2018-04-09 03:21:37'),(11,57,49,1,'2018-04-09 03:21:37','2018-04-09 03:21:37'),(12,57,48,1,'2018-04-09 03:21:37','2018-04-09 03:21:37'),(13,57,47,1,'2018-04-09 03:21:37','2018-04-09 03:21:37'),(14,57,46,1,'2018-04-09 03:23:18','2018-04-09 03:23:18'),(15,57,45,1,'2018-04-09 03:23:18','2018-04-09 03:23:18'),(16,57,44,1,'2018-04-09 03:23:18','2018-04-09 03:23:18'),(17,57,43,1,'2018-04-09 03:23:18','2018-04-09 03:23:18'),(18,57,38,1,'2018-04-09 03:23:59','2018-04-09 03:23:59'),(19,57,36,1,'2018-04-09 03:24:21','2018-04-09 03:24:21'),(20,57,35,1,'2018-04-09 03:24:21','2018-04-09 03:24:21'),(21,57,34,1,'2018-04-09 03:24:21','2018-04-09 03:24:21'),(22,57,33,1,'2018-04-09 03:24:21','2018-04-09 03:24:21'),(23,57,32,1,'2018-04-09 03:24:21','2018-04-09 03:24:21'),(24,57,31,1,'2018-04-09 03:24:37','2018-04-09 03:24:37'),(25,57,30,1,'2018-04-09 03:24:37','2018-04-09 03:24:37'),(26,57,29,1,'2018-04-09 03:24:37','2018-04-09 03:24:37'),(27,57,28,1,'2018-04-09 03:24:37','2018-04-09 03:24:37'),(28,57,27,1,'2018-04-09 03:24:37','2018-04-09 03:24:37'),(29,75,28,1,'2018-04-09 03:28:20','2018-04-09 03:28:20'),(30,75,42,1,'2018-04-09 03:28:31','2018-04-09 03:28:31'),(31,75,40,1,'2018-04-09 03:28:39','2018-04-09 03:28:39'),(32,75,39,1,'2018-04-09 03:28:39','2018-04-09 03:28:39'),(33,75,38,1,'2018-04-09 03:28:39','2018-04-09 03:28:39');
/*!40000 ALTER TABLE `device_spare_part_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_spare_type`
--

DROP TABLE IF EXISTS `device_spare_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_spare_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) DEFAULT NULL COMMENT '备件名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级ID',
  `show_order` int(11) DEFAULT NULL COMMENT '显示顺序',
  `status_flag` int(11) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='备件类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_spare_type`
--

LOCK TABLES `device_spare_type` WRITE;
/*!40000 ALTER TABLE `device_spare_type` DISABLE KEYS */;
INSERT INTO `device_spare_type` VALUES (20,1,'2017-12-25 21:01:24','2017-12-25 21:01:24','常用备件',-1,999,1);
/*!40000 ALTER TABLE `device_spare_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_trouble_type`
--

DROP TABLE IF EXISTS `device_trouble_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_trouble_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(50) DEFAULT NULL COMMENT '企业唯一标识',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(50) DEFAULT NULL COMMENT '设备故障类型名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级ID',
  `show_order` int(20) DEFAULT NULL COMMENT '显示顺序',
  `status_flag` int(20) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_trouble_type`
--

LOCK TABLES `device_trouble_type` WRITE;
/*!40000 ALTER TABLE `device_trouble_type` DISABLE KEYS */;
INSERT INTO `device_trouble_type` VALUES (5,1,'2017-12-14 17:02:27','2017-12-14 17:02:27','机械故障',-1,999,1),(6,1,'2017-12-14 17:02:51','2017-12-14 17:02:51','部件磨损',5,999,1),(7,1,'2017-12-14 17:03:08','2017-12-14 17:03:08','锈蚀卡死',5,999,1),(8,1,'2017-12-14 17:03:14','2017-12-14 17:03:14','密封件泄漏',5,999,1),(9,1,'2017-12-14 17:03:45','2017-12-14 17:03:45','电气故障',-1,999,1),(10,1,'2017-12-14 17:04:02','2017-12-14 17:04:02','电路故障',9,999,1),(11,1,'2017-12-14 17:04:12','2017-12-14 17:04:12','控制阀故障',9,999,1),(12,1,'2017-12-14 17:04:19','2017-12-14 17:04:19','气路接头故障',9,999,1),(13,1,'2017-12-14 17:04:33','2017-12-14 17:04:33','物料原因故障',-1,999,1),(14,1,'2017-12-14 17:04:42','2017-12-14 17:04:42','能源供给故障',-1,999,1),(15,1,'2017-12-14 17:04:48','2017-12-14 17:04:48','其它故障',-1,999,1);
/*!40000 ALTER TABLE `device_trouble_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_type`
--

DROP TABLE IF EXISTS `device_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '设备类型名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `show_order` int(3) DEFAULT '999' COMMENT '显示顺序，值越小显示越靠前',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `status_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，1:有效；0:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='设备类型信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_type`
--

LOCK TABLES `device_type` WRITE;
/*!40000 ALTER TABLE `device_type` DISABLE KEYS */;
INSERT INTO `device_type` VALUES (51,'特种设备',-1,999,1,1,'2018-05-22 11:15:54','2018-05-22 11:15:54'),(52,'非特种设备',-1,999,1,1,'2018-05-22 11:16:02','2018-05-22 11:16:02');
/*!40000 ALTER TABLE `device_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintain_plan`
--

DROP TABLE IF EXISTS `maintain_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maintain_plan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_status` int(11) DEFAULT NULL COMMENT '计划状态(1:今日可以执行计划；2：明日可执行计划；3：已过期计划)',
  `device_id` bigint(20) NOT NULL COMMENT '设备主键',
  `maintain_level` int(1) DEFAULT NULL COMMENT '保养级别',
  `repair_group_id` bigint(20) DEFAULT NULL COMMENT '维修班组',
  `cycle_type` int(1) NOT NULL COMMENT '循环方式（1:单次；2:循环多次）',
  `cycle_time_value` varchar(10) DEFAULT NULL COMMENT '循环周期值',
  `cycle_time_unit` varchar(11) DEFAULT NULL COMMENT '循环周期单位（天，月，年）',
  `maintain_part` varchar(50) DEFAULT NULL COMMENT '保养部位',
  `maintain_standard` varchar(200) DEFAULT NULL COMMENT '保养标准',
  `last_maintain_time` timestamp NULL DEFAULT NULL COMMENT '上次保养时间',
  `plan_maintain_time_start` timestamp NULL DEFAULT NULL COMMENT '计划保养开始时间',
  `plan_maintain_time_end` timestamp NULL DEFAULT NULL COMMENT '计划保养结束时间',
  `plan_manager_id` bigint(20) unsigned NOT NULL COMMENT '保养负责人主键',
  `plan_manager_name` varchar(20) DEFAULT NULL COMMENT '保养负责人姓名',
  `plan_remark` varchar(500) DEFAULT NULL COMMENT '计划描述',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `device_name` varchar(50) NOT NULL DEFAULT '' COMMENT '设备名称',
  `device_spec` varchar(20) DEFAULT NULL COMMENT '规格型号',
  `device_code` varchar(17) NOT NULL DEFAULT '' COMMENT '设备编号',
  `device_type` bigint(20) DEFAULT NULL COMMENT '设备类别',
  `use_dept` bigint(20) DEFAULT NULL COMMENT '使用部门',
  `device_address` varchar(80) DEFAULT NULL COMMENT '安装地点',
  `use_dept_name` varchar(30) DEFAULT NULL COMMENT '使用部门名称',
  `device_type_name` varchar(30) DEFAULT NULL COMMENT '设备类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='保养计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintain_plan`
--

LOCK TABLES `maintain_plan` WRITE;
/*!40000 ALTER TABLE `maintain_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `maintain_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintain_record`
--

DROP TABLE IF EXISTS `maintain_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maintain_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `maintain_plan_id` bigint(20) NOT NULL COMMENT '保养计划主键',
  `maintain_status` int(1) DEFAULT NULL COMMENT '保养状态',
  `maintain_no` varchar(20) DEFAULT NULL COMMENT '保养单号',
  `maintain_content` varchar(100) DEFAULT NULL COMMENT '保养过程',
  `stoped` int(1) DEFAULT NULL COMMENT '是否停机（0:否；1:是）',
  `stoped_hour` int(3) DEFAULT NULL COMMENT '停机时间（单位小时）',
  `cost_hour` int(3) DEFAULT NULL COMMENT '保养耗时',
  `maintain_amount` varchar(10) DEFAULT NULL COMMENT '保养费用',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='保养记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintain_record`
--

LOCK TABLES `maintain_record` WRITE;
/*!40000 ALTER TABLE `maintain_record` DISABLE KEYS */;
INSERT INTO `maintain_record` VALUES (2,12,1,'BY1522747184478','',NULL,NULL,NULL,'',1,'2018-04-03 09:19:44','2018-04-03 09:19:44',NULL,NULL),(3,13,1,'BY1523176815390',NULL,NULL,NULL,NULL,NULL,1,'2018-04-08 08:40:15','2018-04-08 08:40:15',NULL,NULL),(4,14,1,'BY1523243597850',NULL,NULL,NULL,NULL,NULL,1,'2018-04-09 03:13:17','2018-04-09 03:13:17',NULL,NULL),(5,15,1,'BY1525831876911',NULL,NULL,NULL,NULL,NULL,1,'2018-05-09 02:11:16','2018-05-09 02:11:16',NULL,NULL);
/*!40000 ALTER TABLE `maintain_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair_group_info`
--

DROP TABLE IF EXISTS `repair_group_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair_group_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(10) DEFAULT NULL COMMENT '维修工段/班组编码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '维修工段/班组名称',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='维修班组／工段基础信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_group_info`
--

LOCK TABLES `repair_group_info` WRITE;
/*!40000 ALTER TABLE `repair_group_info` DISABLE KEYS */;
INSERT INTO `repair_group_info` VALUES (29,'BBDS','煅烧车间包保班组',1,'2018-05-22 11:19:52','2018-05-22 11:19:52'),(30,'BBCX','成型车间包保班组',1,'2018-05-22 11:20:04','2018-05-22 11:20:04'),(31,'BBBS','焙烧车间包保班组',1,'2018-05-22 11:20:13','2018-05-22 11:20:13'),(32,'BBZZ','组装车间包保班组',1,'2018-05-22 11:20:23','2018-05-22 11:20:23');
/*!40000 ALTER TABLE `repair_group_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair_record`
--

DROP TABLE IF EXISTS `repair_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trouble_record_id` bigint(20) NOT NULL COMMENT '故障主键',
  `repair_status` int(1) DEFAULT NULL COMMENT '维修状态',
  `trouble_type_id` bigint(20) DEFAULT NULL COMMENT '故障类别',
  `trouble_reason` int(1) DEFAULT NULL COMMENT '故障原因',
  `repair_level` int(1) DEFAULT NULL COMMENT '维修级别',
  `repair_amount` varchar(10) DEFAULT NULL COMMENT '维修费用',
  `work_remark` varchar(200) DEFAULT NULL COMMENT '工作描述',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `repair_type` int(1) DEFAULT NULL COMMENT '维修类型：1:内部维修；2:外委维修',
  `out_repair_company` varchar(50) DEFAULT NULL COMMENT '外委维修公司名称',
  `out_repair_men` varchar(50) DEFAULT NULL COMMENT '外委维修人员名单，逗号分隔',
  `stoped` int(1) DEFAULT NULL COMMENT '是否停机（0:否；1:是）',
  `stoped_hour` varchar(10) DEFAULT NULL COMMENT '停机时间（单位小时）',
  `cost_hour` varchar(10) DEFAULT NULL COMMENT '维修耗时',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `FKld39d7mmlhvrr4jnt3bgiroj2` (`trouble_type_id`),
  CONSTRAINT `FKld39d7mmlhvrr4jnt3bgiroj2` FOREIGN KEY (`trouble_type_id`) REFERENCES `device_trouble_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='故障维修单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_record`
--

LOCK TABLES `repair_record` WRITE;
/*!40000 ALTER TABLE `repair_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `repair_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair_record_spare_part_rel`
--

DROP TABLE IF EXISTS `repair_record_spare_part_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair_record_spare_part_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(1) NOT NULL COMMENT '类型：1:故障维修；2:保养计划',
  `amount` int(3) NOT NULL COMMENT '使用数量',
  `repair_record_id` bigint(20) NOT NULL COMMENT '维修单号主键',
  `code` varchar(30) DEFAULT '' COMMENT '备件编码',
  `name` varchar(20) DEFAULT '' COMMENT '备件名称',
  `inventory_upper_limit` int(11) DEFAULT NULL COMMENT '当前库存',
  `specification` varchar(30) DEFAULT '' COMMENT '规格型号',
  `old_order_num` varchar(30) DEFAULT NULL COMMENT '原配件序号',
  `new_order_num` varchar(30) DEFAULT '' COMMENT '新换上的序号',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配件更换信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_record_spare_part_rel`
--

LOCK TABLES `repair_record_spare_part_rel` WRITE;
/*!40000 ALTER TABLE `repair_record_spare_part_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `repair_record_spare_part_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spare_parts`
--

DROP TABLE IF EXISTS `spare_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spare_parts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `conversion_ratio` double DEFAULT NULL COMMENT '换算比例',
  `bar_code` varchar(20) DEFAULT NULL COMMENT '条形码',
  `code` varchar(20) DEFAULT NULL COMMENT '备件编号',
  `extend_date_field_one` datetime DEFAULT NULL COMMENT '自定义日期1',
  `remark` varchar(255) DEFAULT NULL COMMENT '备件备注',
  `extend_date_field_two` datetime DEFAULT NULL COMMENT '自定义日期2',
  `extend_field_five` varchar(30) DEFAULT NULL COMMENT '自定义字段5',
  `extend_field_four` varchar(30) DEFAULT NULL COMMENT '自定义字段4',
  `extend_field_one` varchar(30) DEFAULT NULL COMMENT '自定义字段1',
  `extend_field_seven` varchar(30) DEFAULT NULL COMMENT '自定义字段7',
  `extend_field_six` varchar(30) DEFAULT NULL COMMENT '自定义字段6',
  `extend_field_three` varchar(30) DEFAULT NULL COMMENT '自定义字段3',
  `extend_field_two` varchar(30) DEFAULT NULL COMMENT '自定义字段2',
  `inventory_floor` int(11) DEFAULT NULL COMMENT '库存下限',
  `inventory_upper_limit` int(11) DEFAULT NULL COMMENT '库存上限',
  `manufacturer` varchar(20) DEFAULT NULL COMMENT '生产厂商',
  `material_properties` varchar(20) DEFAULT NULL COMMENT '物料属性',
  `measuring_unit` bigint(20) DEFAULT NULL COMMENT '计量单位',
  `name` varchar(50) DEFAULT NULL COMMENT '备件名称',
  `reference_price` double DEFAULT NULL COMMENT '参考价',
  `replacement_cycle` int(11) DEFAULT NULL COMMENT '更换周期',
  `spare_part_type` bigint(20) DEFAULT NULL COMMENT '备件类型',
  `specifications_andodels` varchar(20) DEFAULT NULL COMMENT '规格型号',
  `suppliers` bigint(20) DEFAULT NULL COMMENT '供应商',
  `unit_conversion` bigint(20) DEFAULT NULL COMMENT '换算单位',
  `deviceinfo_id` bigint(20) DEFAULT NULL COMMENT '关联设备Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spare_parts`
--

LOCK TABLES `spare_parts` WRITE;
/*!40000 ALTER TABLE `spare_parts` DISABLE KEYS */;
INSERT INTO `spare_parts` VALUES (13,1,'2018-01-17 14:38:50','2018-01-17 14:38:50',2,'2','01','2018-01-17 00:00:00','无','2018-01-17 00:00:00','2','2','1','2','2','2','2',2,200,'22','2',7,'主提升电动机',20000,2,5,'YZP200L2-4C',22,7,35),(14,1,'2018-01-17 14:43:35','2018-01-17 14:43:35',2,'02','02','2018-01-17 00:00:00','无','2018-01-17 00:00:00','02','02','1','02','02','02','02',1,2,'23','02',7,'电机风机',2,2,6,'GP-200C',23,7,35),(15,1,'2018-01-17 14:45:16','2018-01-17 14:45:16',3,'3','03','2018-01-17 00:00:00','无','2018-01-17 00:00:00','3','3','1','3','3','3','3',1,3,'25','3',7,'大车行走电动机',333333,3,6,'GDV132M4',25,7,35),(16,1,'2018-01-17 14:46:42','2018-01-17 14:46:42',4.02,'04','04','2018-01-17 00:00:00','无','2018-01-17 00:00:00','4','4','1','4','4','4','4',4,44,'24','4',7,'主提升减速机',444444,4,8,'ZFY450-200-7',24,7,35),(17,1,'2018-01-17 14:49:27','2018-01-17 14:49:27',5,'05','05','2018-01-17 00:00:00','无','2018-01-17 00:00:00','05','05','1','05','05','05','05',5,5,'25','05',7,'大车行走减速机',5,5,6,'FA97/GDV132M4/BM/HF',25,7,35),(18,1,'2018-01-17 14:51:03','2018-01-17 14:51:03',6,'06','06','2018-01-10 00:00:00','无','2018-01-17 00:00:00','6','6','1','6','6','6','6',6,66,'26','6',7,'电力液压推动器',6,6,5,'YTD5-500/60',26,7,35),(19,1,'2018-01-17 14:52:34','2018-01-17 14:52:34',7,'07','07','2018-01-17 00:00:00','无','2018-01-17 00:00:00','07','07','1','07','07','07','07',7,77,'27','07',7,'电缆卷筒',77777,7,5,'JTA20-15-15-Ⅰ',27,7,35),(20,1,'2018-01-17 14:54:04','2018-01-17 14:54:04',8,'08','08','2018-01-17 00:00:00','无','2018-01-17 00:00:00','08','08','1','08','08','08','08',8,8,'28','08',7,'空压机',8,8,17,'UP5-22-10',28,7,35),(21,1,'2018-01-17 15:00:36','2018-01-17 15:00:36',9,'09','09','2018-01-17 00:00:00','无','2018-01-17 00:00:00','09','09','1','09','09','09','09',1,9,'29','09',6,'液压站',9,9,9,'Y132M-4-B35',29,6,36),(22,1,'2018-01-17 15:06:40','2018-01-17 15:06:40',10,'10','10','2018-01-17 00:00:00','10','2018-01-17 00:00:00','10','10','1','10','10','10','10',10,10,'33','10',6,'液压站#2',10,10,9,'Y160M-4',33,6,36),(23,1,'2018-01-17 15:07:47','2018-01-17 15:07:47',11,'11','11','2018-01-17 00:00:00','11','2018-01-17 00:00:00','11','11','1','11','11','11','11',11,11,'31','11',7,'板式输送机',11,11,17,'Y132S-4-X6/W',31,7,36),(24,1,'2018-01-17 15:08:33','2018-01-17 15:08:33',12,'12','12','2018-01-17 00:00:00','12','2018-01-17 00:00:00','12','12','1','12','12','12','12',12,12,'25','12',7,'螺旋输送机',12,12,6,'FA77DT90L4',25,7,36),(25,1,'2018-01-17 15:09:40','2018-01-17 15:09:40',13,'13','13','2018-01-17 00:00:00','13','2018-01-17 00:00:00','13','13','1','13','13','13','13',13,13,'31','13',7,'行星摆线针轮减速机',13,13,6,'XWE-106',31,7,36),(26,1,'2018-03-29 17:03:27','2018-03-29 17:03:27',11,'00011','00011','2018-03-30 00:00:00','00011','2018-03-30 00:00:00','00011','00011','1','00011','00011','00011','00011',10,40,'29','00011',6,'盖板',11,7,10,'00011',29,6,45),(27,1,'2018-03-29 17:04:37','2018-03-29 17:04:37',12,'00012','00012','2018-03-27 00:00:00','00012','2018-04-03 00:00:00','00012','00012','1','00012','00012','00012','00012',12,12,'29','00012',6,'私服马达',12,12,17,'00012',29,6,45),(28,1,'2018-03-29 17:05:27','2018-03-29 17:05:27',13,'00013','00013','2018-03-30 00:00:00','00013','2018-04-06 00:00:00','00013','00013','1','00013','00013','00013','00013',13,13,'24','00013',7,'电气接线盒',13,13,6,'00013',25,7,32),(29,1,'2018-03-29 17:08:31','2018-03-29 17:08:31',14,'00014','00014','2018-03-25 00:00:00','00014','2018-04-03 00:00:00','00014','00014','1','00014','00014','00014','00014',14,14,'27','00014',7,'锅炉连接法兰',14,14,17,'00014',27,7,45),(30,1,'2018-03-29 17:09:11','2018-03-29 17:09:11',15,'00015','00015','2018-03-19 00:00:00','00015','2018-04-05 00:00:00','00015','00015','1','00015','00015','00015','00015',15,15,'23','00015',7,'燃烧头',15,15,17,'00015',25,7,45),(31,1,'2018-03-29 17:09:56','2018-03-29 17:09:56',16,'00016','00016','2018-03-25 00:00:00','00016','2018-04-05 00:00:00','00016','00016','1','00016','00016','00016','00016',16,16,'24','00016',7,'燃烧头燃气压力测试点',16,16,17,'00016',24,7,45),(32,1,'2018-03-29 17:10:42','2018-03-29 17:10:42',17,'000017','00017','2018-03-27 00:00:00','000017','2018-04-05 00:00:00','000017','000017','1','000017','000017','000017','000017',17,17,'24','000017',6,'点火电极/点火枪',17,17,12,'000017',23,6,45),(33,1,'2018-03-29 17:11:19','2018-03-29 17:11:19',18,'00018','00018','2018-03-22 00:00:00','00018','2018-04-05 00:00:00','00018','00018','1','00018','00018','00018','00018',18,18,'23','00018',6,'风管连接法兰',18,18,17,'00018',26,6,45),(34,1,'2018-03-29 17:12:01','2018-03-29 17:12:01',19,'00019','00019','2018-03-27 00:00:00','00019','2018-03-28 00:00:00','00019','00019','1','00019','00019','00019','00019',19,19,'28','00019',7,'UV电眼',19,19,17,'00019',27,7,45),(35,1,'2018-03-29 17:12:48','2018-03-29 17:12:48',110,'000110','000110','2018-03-26 00:00:00','000110','2018-04-05 00:00:00','000110','000110','1','000110','000110','000110','000110',110,110,'23','000110',7,'风压开关',110,110,5,'000110',28,6,45),(36,1,'2018-03-29 17:13:33','2018-03-29 17:13:33',111,'000111','000111','2018-03-28 00:00:00','000111','2018-03-31 00:00:00','000111','000111','1','000111','000111','000111','000111',111,111,'24','000111',6,'燃气管路连接法兰',111,111,17,'000111',26,6,45),(37,1,'2018-03-29 17:14:14','2018-03-29 17:14:14',112,'000112','000112','2018-03-04 00:00:00','000112','2018-03-14 00:00:00','000112','000112','1','000112','000112','000112','000112',112,112,'23','000112',6,'火焰检查窗',112,112,17,'000112',30,6,45),(38,1,'2018-03-29 17:14:57','2018-03-29 17:14:57',21,'00021','00021','2018-02-26 00:00:00','00021','2018-03-06 00:00:00','00021','00021','1','00021','00021','00021','00021',21,21,'25','00021',6,'隔离网',21,21,17,'00021',28,6,45),(39,1,'2018-03-29 17:15:35','2018-03-29 17:15:35',22,'00022','00022','2018-02-26 00:00:00','00022','2018-02-28 00:00:00','00022','00022','1','00022','00022','00022','00022',22,22,'23','00022',6,'风叶罩',22,22,17,'00022',24,6,45),(40,1,'2018-03-29 17:16:09','2018-03-29 17:16:09',23,'00023','00023','2018-02-26 00:00:00','00023','2018-03-08 00:00:00','00023','00023','1','00023','00023','00023','00023',23,23,'29','00023',6,'风叶',23,23,17,'00023',26,6,45),(41,1,'2018-03-29 17:16:55','2018-03-29 17:16:55',24,'00024','00024','2018-03-18 00:00:00','00024','2018-03-24 00:00:00','00024','00024','1','00024','00024','00024','00024',24,24,'25','00024',6,'加长轴',24,24,17,'00024',25,6,45),(42,1,'2018-03-29 17:17:30','2018-03-29 17:17:30',25,'00025','00025','2018-03-06 00:00:00','00025','2018-03-31 00:00:00','00025','00025','1','00025','00025','00025','00025',25,25,'24','00025',6,'电机',25,25,17,'00025',27,6,45),(43,1,'2018-03-29 17:18:10','2018-03-29 17:18:10',31,'00031','00031','2018-03-12 00:00:00','00031','2018-03-31 00:00:00','00031','00031','1','00031','00031','00031','00031',31,31,'29','00031',6,'过滤器',31,31,17,'00031',30,6,45),(44,1,'2018-03-29 17:18:46','2018-03-29 17:18:46',32,'00032','00032','2018-03-13 00:00:00','00032','2018-03-31 00:00:00','00032','00032','1','00032','00032','00032','00032',32,32,'27','00032',6,'气管',32,32,5,'00032',27,6,46),(45,1,'2018-03-29 17:19:35','2018-03-29 17:19:35',33,'00033','00033','2018-03-11 00:00:00','00033','2018-03-30 00:00:00','00033','00033','1','00033','00033','00033','00033',33,33,'26','00033',6,'二连体',33,33,5,'00033',28,6,45),(46,1,'2018-03-29 17:22:54','2018-03-29 17:22:54',34.01,'00034','00034','2018-03-19 00:00:00','00034','2018-04-03 00:00:00','00034','00034','1','00034','00034','00034','00034',34,34,'24','00034',6,'安全阀',34,34,17,'00034',30,6,45),(47,1,'2018-03-29 17:23:43','2018-03-29 17:23:43',35,'00035','00035','2018-03-18 00:00:00','00035','2018-03-27 00:00:00','00035','00035','1','00035','00035','00035','00035',35,35,'27','00035',6,'压力表',35,35,5,'00035',25,6,45),(48,1,'2018-03-29 17:24:20','2018-03-29 17:24:20',42,'00042','00042','2018-03-26 00:00:00','00042','2018-04-05 00:00:00','00042','00042','1','00042','00042','00042','00036',42,42,'27','00042',6,'阀门',42,42,17,'00042',26,6,45),(49,1,'2018-03-29 17:24:55','2018-03-29 17:24:55',41,'00041','00041','2018-03-05 00:00:00','00041','2018-04-05 00:00:00','00041','00041','1','00041','00041','00041','00037',41,41,'26','00041',6,'钢瓶',41,37,17,'00041',29,6,45),(50,1,'2018-03-29 17:26:45','2018-03-29 17:26:45',43,'00043','00043','2018-03-26 00:00:00','00043','2018-04-03 00:00:00','00043','00043','1','00043','00043','00043','00043',43,43,'26','00043',7,'减压阀',43,43,17,'00043',27,6,45),(51,1,'2018-03-29 17:27:29','2018-03-29 17:27:29',51,'00051','00051','2018-03-26 00:00:00','00051','2018-04-06 00:00:00','00051','00051','1','00051','00051','00051','00051',51,51,'26','00051',6,'观火孔',51,51,17,'00051',24,6,45);
/*!40000 ALTER TABLE `spare_parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_image_info`
--

DROP TABLE IF EXISTS `spot_inspection_image_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_image_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `spot_inspection_plan` bigint(20) DEFAULT NULL COMMENT '点检计划ID',
  `spot_inspection_standard` bigint(20) DEFAULT NULL COMMENT '点检标准ID',
  `record_id` bigint(20) DEFAULT NULL COMMENT '巡检记录ID',
  `image_key` varchar(50) DEFAULT NULL COMMENT '图片KEY',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点巡检图片信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_image_info`
--

LOCK TABLES `spot_inspection_image_info` WRITE;
/*!40000 ALTER TABLE `spot_inspection_image_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `spot_inspection_image_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_items`
--

DROP TABLE IF EXISTS `spot_inspection_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `name` varchar(20) DEFAULT NULL COMMENT '巡检项目名称',
  `record_type` varchar(20) DEFAULT NULL COMMENT '记录方式',
  `standard` bigint(20) DEFAULT NULL COMMENT '关联巡检标准的ID',
  `vaildate_regular` varchar(50) DEFAULT NULL COMMENT 'standard',
  `upper_limit` int(255) DEFAULT NULL COMMENT '上限值',
  `device_place` varchar(255) DEFAULT NULL,
  `spot_inspection_way` varchar(255) DEFAULT NULL,
  `lower_limit` int(255) DEFAULT NULL COMMENT '下限值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COMMENT='巡检项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_items`
--

LOCK TABLES `spot_inspection_items` WRITE;
/*!40000 ALTER TABLE `spot_inspection_items` DISABLE KEYS */;
INSERT INTO `spot_inspection_items` VALUES (64,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液力耦合器无泄漏、无窜动','options',14,'[\"正常\",\"异常\"]',NULL,'传动部分','目视',NULL),(65,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','梅花联轴器梅花垫无磨损','options',14,'[\"正常\",\"异常\"]',NULL,'传动部分','目视',NULL),(66,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','减速机无泄漏、无异音','options',14,'[\"正常\",\"异常\"]',NULL,'减速机','目视、耳听',NULL),(67,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','减速机油位在油窥镜中线偏上','options',14,'[\"正常\",\"异常\"]',NULL,'减速机','目视',NULL),(68,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','电机运转正常无杂音（动态）','options',14,'[\"正常\",\"异常\"]',NULL,'电机','耳听',NULL),(69,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','电机温度不烫手','options',14,'[\"正常\",\"异常\"]',NULL,'电机','测温',NULL),(70,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','热媒管线无泄漏','options',14,'[\"正常\",\"异常\"]',NULL,'锅体','目视',NULL),(71,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','进出料口无漏料、冒灰现象','options',14,'[\"正常\",\"异常\"]',NULL,'锅体','目视',NULL),(72,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液压泵无异响，无渗漏','options',14,'[\"正常\",\"异常\"]',NULL,'液压系统','目视、耳听',NULL),(73,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液压管线无渗漏','options',14,'[\"正常\",\"异常\"]',NULL,'液压系统','目视',NULL),(74,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','油位在油标视窗½处','options',14,'[\"正常\",\"偏上\",\"偏下\"]',NULL,'液压系统','目视',NULL),(75,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液压电机运转正常无杂音（动态）','options',14,'[\"正常\",\"异常\"]',NULL,'液压系统','耳听',NULL),(76,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液压电机螺栓无缺失、无松动','options',14,'[\"正常\",\"异常\"]',NULL,'液压系统','目视',NULL),(77,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液压系统油温≤50℃','number',14,'[\"\"]',54,'液压系统','测温',46),(78,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','液压缸底座螺栓无缺失、无松动','options',14,'[\"正常\",\"异常\"]',NULL,'液压系统','目视',NULL),(79,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','限位动作灵活可靠','options',14,'[\"正常\",\"异常\"]',NULL,'电气部分','目视',NULL),(80,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','仪表指示正常','options',14,'[\"正常\",\"异常\"]',NULL,'电气部分','目视',NULL),(81,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','按钮','options',14,'[\"正常\",\"异常\"]',NULL,'电气部分','目视',NULL),(82,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','设备卫生无浮灰积料、无漏油','options',14,'[\"无\",\"有\"]',NULL,'设备卫生','目视',NULL),(83,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','发现问题','verbal_description',14,NULL,NULL,'','',NULL),(84,1,'2018-05-22 20:16:16','2018-05-22 20:16:16','解决问题','verbal_description',14,NULL,NULL,'','',NULL);
/*!40000 ALTER TABLE `spot_inspection_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_plan`
--

DROP TABLE IF EXISTS `spot_inspection_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `department` bigint(20) DEFAULT NULL COMMENT 'department',
  `device_type` bigint(20) DEFAULT NULL COMMENT 'deviceType',
  `end_time` datetime DEFAULT NULL COMMENT '截止时间--如果为空表示计划长期有效',
  `executors` text COMMENT '执行者集合',
  `name` varchar(20) DEFAULT NULL COMMENT '巡检项目名称',
  `last_execute_time` datetime DEFAULT NULL COMMENT '上次执行时间',
  `next_execute_time` datetime DEFAULT NULL COMMENT '下次执行时间',
  `plan_status` int(11) DEFAULT NULL COMMENT '计划状态 1启用 2停用 3编辑中',
  `recycle_period` int(11) DEFAULT NULL,
  `recycle_period_type` varchar(20) DEFAULT NULL,
  `spot_inspection_plan_level` int(10) DEFAULT NULL COMMENT '巡检计划等级,1,2,3等',
  `spot_inspection_range` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='巡检计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_plan`
--

LOCK TABLES `spot_inspection_plan` WRITE;
/*!40000 ALTER TABLE `spot_inspection_plan` DISABLE KEYS */;
INSERT INTO `spot_inspection_plan` VALUES (6,1,'2018-05-22 20:18:58','2018-05-22 20:18:58',55,NULL,'2019-05-31 00:00:00','[\"818304\",\"331584\"]','1#混捏锅一级点检',NULL,'2018-05-23 00:00:00',1,4,'hour',1,'成型一楼');
/*!40000 ALTER TABLE `spot_inspection_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_plan_device`
--

DROP TABLE IF EXISTS `spot_inspection_plan_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_plan_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `spot_inspection_plan` bigint(20) DEFAULT NULL COMMENT '点检计划ID',
  `spot_inspection_standard` bigint(20) DEFAULT NULL COMMENT '点检标准ID',
  `device_type` bigint(20) DEFAULT NULL COMMENT '适用设备类型',
  `line_order` int(11) DEFAULT NULL COMMENT '路线顺序',
  `device_id` bigint(20) DEFAULT NULL COMMENT '适用设备ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='点检计划-设备关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_plan_device`
--

LOCK TABLES `spot_inspection_plan_device` WRITE;
/*!40000 ALTER TABLE `spot_inspection_plan_device` DISABLE KEYS */;
INSERT INTO `spot_inspection_plan_device` VALUES (15,1,'2018-05-22 20:18:58','2018-05-22 20:18:58',6,14,52,1,76);
/*!40000 ALTER TABLE `spot_inspection_plan_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_record`
--

DROP TABLE IF EXISTS `spot_inspection_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `execute_time` datetime DEFAULT NULL COMMENT '巡检时间',
  `executor` bigint(20) DEFAULT NULL COMMENT '执行者',
  `plan_id` bigint(20) DEFAULT NULL COMMENT '巡检计划ID',
  `plan_name` varchar(50) DEFAULT NULL COMMENT '巡检计划名称',
  `plan_time` datetime DEFAULT NULL COMMENT '计划时间',
  `device_id` bigint(11) DEFAULT NULL COMMENT '设备ID',
  `recycle_period` int(11) DEFAULT NULL COMMENT '循环周期',
  `recycle_period_type` varchar(20) DEFAULT NULL COMMENT '循环周期类型',
  `standard` bigint(20) DEFAULT NULL COMMENT '巡检标准ID',
  `department` bigint(255) DEFAULT NULL COMMENT '部门主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_record`
--

LOCK TABLES `spot_inspection_record` WRITE;
/*!40000 ALTER TABLE `spot_inspection_record` DISABLE KEYS */;
INSERT INTO `spot_inspection_record` VALUES (2,1,'2018-05-09 16:18:25','2018-05-09 16:18:25','2018-05-09 16:18:25',786496,4,'天燃气锅炉点检','2018-05-09 20:18:25',NULL,4,'hour',NULL,54);
/*!40000 ALTER TABLE `spot_inspection_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_record_detail`
--

DROP TABLE IF EXISTS `spot_inspection_record_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_record_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `abnormal_desc` varchar(20) DEFAULT NULL COMMENT '异常情况描述 异常情况描述 1无异常2有异常',
  `record_id` bigint(20) DEFAULT NULL COMMENT '巡检记录ID',
  `record_result` varchar(20) DEFAULT NULL COMMENT '记录结果 是否有异常 数值等',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `standard` bigint(20) DEFAULT NULL COMMENT '巡检标准ID',
  `device_id` bigint(20) DEFAULT NULL COMMENT '设备ID',
  `standard_item_id` bigint(20) DEFAULT NULL COMMENT '巡检项ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='巡检记录详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_record_detail`
--

LOCK TABLES `spot_inspection_record_detail` WRITE;
/*!40000 ALTER TABLE `spot_inspection_record_detail` DISABLE KEYS */;
INSERT INTO `spot_inspection_record_detail` VALUES (8,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'正常',NULL,12,NULL,53),(9,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'正常',NULL,12,NULL,54),(10,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'有泄漏',NULL,12,NULL,55),(11,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'正常',NULL,12,NULL,56),(12,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'100',NULL,12,NULL,57),(13,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'无',NULL,12,NULL,58),(14,1,'2018-05-09 16:18:25','2018-05-09 16:18:25',NULL,2,'无',NULL,12,NULL,59);
/*!40000 ALTER TABLE `spot_inspection_record_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_inspection_standard`
--

DROP TABLE IF EXISTS `spot_inspection_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spot_inspection_standard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `device_type` bigint(20) DEFAULT NULL COMMENT '设备类型关联ID',
  `name` varchar(20) DEFAULT NULL COMMENT '巡检标准名称',
  `relate_devices` varchar(200) DEFAULT NULL COMMENT '适用设备列表 [1,2,3] 这种格式，存放设备ID',
  `remark` varchar(255) DEFAULT NULL,
  `spotInspection_way` varchar(255) DEFAULT NULL COMMENT '点检方法',
  `device_place` varchar(255) DEFAULT NULL COMMENT '设备部位',
  `requirement` text COMMENT '巡检要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='巡检标准实体表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_inspection_standard`
--

LOCK TABLES `spot_inspection_standard` WRITE;
/*!40000 ALTER TABLE `spot_inspection_standard` DISABLE KEYS */;
INSERT INTO `spot_inspection_standard` VALUES (14,1,'2018-05-22 20:16:16','2018-05-22 20:16:16',52,'混捏锅一级点检标准','[76]','设备运转正常，无异音，设备温度正常，无泄漏',NULL,NULL,'设备运转正常，无异音，设备温度正常，无泄漏');
/*!40000 ALTER TABLE `spot_inspection_standard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_corporate`
--

DROP TABLE IF EXISTS `system_corporate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_corporate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `corporate_name` varchar(20) DEFAULT NULL COMMENT '企业名称',
  `enable_status` int(11) DEFAULT NULL COMMENT '是否启用 1启用2不启用',
  `corporate_code` varchar(255) DEFAULT NULL COMMENT '企业code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='企业表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_corporate`
--

LOCK TABLES `system_corporate` WRITE;
/*!40000 ALTER TABLE `system_corporate` DISABLE KEYS */;
INSERT INTO `system_corporate` VALUES (1,'2017-11-20 13:48:07','2017-11-20 13:48:07',1,'山西华圣铝业有限公司',1,'ESSAE86');
/*!40000 ALTER TABLE `system_corporate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_menu`
--

DROP TABLE IF EXISTS `system_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `order_number` int(11) DEFAULT NULL COMMENT '菜单排序号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单Id 若为0则为一级菜单',
  `remark` varchar(50) DEFAULT NULL COMMENT '菜单备注',
  `url` varchar(50) DEFAULT NULL COMMENT '菜单访问URL',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_menu`
--

LOCK TABLES `system_menu` WRITE;
/*!40000 ALTER TABLE `system_menu` DISABLE KEYS */;
INSERT INTO `system_menu` VALUES (30,'2018-01-16 17:33:01','2018-01-16 17:33:01','首页',0,NULL,'首页','home',1),(31,'2018-01-16 17:33:36','2018-01-16 17:33:36','用户管理',6,NULL,'用户管理','userManagements',1),(32,'2018-01-16 17:34:13','2018-01-16 17:34:13','用户/用户组',15,31,'用户/用户组','userManagements',1),(33,'2018-01-16 17:34:45','2018-01-16 17:34:45','角色管理',16,31,'角色管理','roleManagements',1),(34,'2018-01-16 17:35:10','2018-01-16 17:35:10','菜单管理',17,31,'菜单管理','menuManagements',1),(35,'2018-01-16 17:35:56','2018-01-16 17:35:56','设备管理',4,NULL,'设备管理','deviceManage',1),(36,'2018-01-16 17:36:40','2018-01-16 17:36:40','备件管理',5,NULL,'备件管理','sparePartsManage',1),(37,'2018-01-16 17:37:11','2018-01-16 17:37:11','参数设置',7,NULL,'参数设置','departmentManage',1),(38,'2018-01-16 17:38:00','2018-01-16 17:38:00','设备台账',18,35,'设备台账','deviceManage',1),(40,'2018-01-16 17:39:13','2018-01-16 17:39:13','配件台账',19,36,'配件台账','sparePartsManage',1),(41,'2018-01-16 17:40:05','2018-01-16 17:40:05','部门设置',20,37,'部门设置','departmentManage',1),(42,'2018-01-16 17:40:43','2018-01-16 17:40:43','往来单位',21,37,'往来单位','contactCompany',1),(43,'2018-01-16 17:41:28','2018-01-16 17:41:28','维修工段/班组',22,37,'维修工段/班组','repairGroup',1),(44,'2018-01-16 17:42:17','2018-01-16 17:42:17','设备类型设置',23,37,'设备类型设置','deviceType',1),(45,'2018-01-16 17:42:41','2018-01-16 17:42:41','备件类型设置',24,37,'备件类型设置','deviceSpares',1),(46,'2018-01-16 17:43:17','2018-01-16 17:43:17','故障类型设置',25,37,'故障类型设置','deviceTroubleType',1),(47,'2018-01-16 17:43:52','2018-01-16 17:43:52','审核流程设置',26,37,'审核流程设置','deviceProcess',1),(48,'2018-01-16 17:44:40','2018-01-16 17:44:40','其它选项设置',27,37,'其它选项设置','otherOptionsSetting',1),(55,'2018-01-25 22:35:02','2018-01-25 22:35:02','巡检计划',2,NULL,'巡检计划','inspectionPlan',1),(56,'2018-01-25 22:35:34','2018-01-25 22:35:34','巡检计划',0,55,'巡检计划','inspectionPlan',1),(57,'2018-01-25 23:12:29','2018-01-25 23:12:29','巡检标准',2,55,'巡检标准','spotInspectionStandard',1),(58,'2018-03-04 16:40:51','2018-03-04 16:40:51','巡检记录',29,55,'巡检记录','spotInspectionRecord',1),(59,'2018-03-05 22:40:44','2018-03-05 22:40:44','故障报修（内修）',0,63,'故障报修（内修）','InternalRepair',1),(60,'2018-03-05 22:42:36','2018-03-05 22:42:36','外委维修',1,63,'外委维修','outSideRepair',1),(61,'2018-03-08 13:59:11','2018-03-08 13:59:11','润滑计划',3,NULL,'润滑计划','deviceMaintenanceHome',1),(62,'2018-03-08 14:00:12','2018-03-08 14:00:12','润滑计划',0,61,'润滑计划','deviceMaintenanceHome',1),(63,'2018-04-04 11:11:38','2018-04-04 11:11:38','故障维修',1,NULL,'故障维修','InternalRepair',1);
/*!40000 ALTER TABLE `system_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_menu_role`
--

DROP TABLE IF EXISTS `system_menu_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单Id',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `FK6fbbfiu8njr0yj2fip0drvfvt` (`menu_id`),
  KEY `FKi5bt12i0pjyfi95rfv8p6fet1` (`role_id`),
  CONSTRAINT `FK6fbbfiu8njr0yj2fip0drvfvt` FOREIGN KEY (`menu_id`) REFERENCES `system_menu` (`id`),
  CONSTRAINT `FKi5bt12i0pjyfi95rfv8p6fet1` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_menu_role`
--

LOCK TABLES `system_menu_role` WRITE;
/*!40000 ALTER TABLE `system_menu_role` DISABLE KEYS */;
INSERT INTO `system_menu_role` VALUES (17,1,'2018-01-16 17:47:53','2018-01-16 17:47:53',30,NULL,8),(18,1,'2018-01-16 17:47:53','2018-01-16 17:47:53',31,NULL,8),(19,1,'2018-01-16 17:47:53','2018-01-16 17:47:53',33,NULL,8),(20,1,'2018-01-16 17:47:53','2018-01-16 17:47:53',34,NULL,8),(21,1,'2018-01-16 17:47:53','2018-01-16 17:47:53',35,NULL,8),(22,1,'2018-02-11 13:58:00','2018-02-11 13:58:00',31,NULL,8),(23,1,'2018-02-11 13:58:00','2018-02-11 13:58:00',32,NULL,8),(24,1,'2018-02-11 13:59:26','2018-02-11 13:59:26',35,NULL,8),(25,1,'2018-02-11 13:59:26','2018-02-11 13:59:26',38,NULL,8),(26,1,'2018-02-11 13:59:26','2018-02-11 13:59:26',36,NULL,8),(27,1,'2018-02-11 13:59:26','2018-02-11 13:59:26',40,NULL,8),(28,1,'2018-03-05 13:22:45','2018-03-05 13:22:45',37,NULL,8),(29,1,'2018-03-05 13:22:45','2018-03-05 13:22:45',47,NULL,8),(30,1,'2018-03-05 13:53:34','2018-03-05 13:53:34',30,NULL,17),(31,1,'2018-03-05 13:53:34','2018-03-05 13:53:34',35,NULL,17),(32,1,'2018-03-05 13:53:34','2018-03-05 13:53:34',38,NULL,17),(33,1,'2018-03-05 13:53:34','2018-03-05 13:53:34',36,NULL,17),(34,1,'2018-03-05 13:53:34','2018-03-05 13:53:34',40,NULL,17),(35,1,'2018-03-05 13:53:50','2018-03-05 13:53:50',30,NULL,18),(36,1,'2018-03-05 13:53:50','2018-03-05 13:53:50',35,NULL,18),(37,1,'2018-03-05 13:53:50','2018-03-05 13:53:50',38,NULL,18),(38,1,'2018-03-05 13:53:50','2018-03-05 13:53:50',36,NULL,18),(39,1,'2018-03-05 13:53:50','2018-03-05 13:53:50',40,NULL,18),(58,1,'2018-03-04 16:41:44','2018-03-04 16:41:44',55,NULL,8),(60,1,'2018-01-31 21:43:42','2018-01-31 21:43:42',56,NULL,8),(62,1,'2018-01-25 23:36:00','2018-01-25 23:36:00',57,NULL,8),(67,1,'2018-03-05 22:50:28','2018-03-05 22:50:28',60,NULL,8),(69,1,'2018-03-12 14:24:29','2018-03-12 14:24:29',61,NULL,8),(70,1,'2018-03-12 14:24:29','2018-03-12 14:24:29',62,NULL,8),(71,1,'2018-04-03 17:09:26','2018-04-03 17:09:26',55,NULL,22),(72,1,'2018-04-03 17:09:26','2018-04-03 17:09:26',56,NULL,22),(74,1,'2018-04-03 18:55:37','2018-04-03 18:55:37',37,NULL,8),(75,1,'2018-04-03 18:55:37','2018-04-03 18:55:37',41,NULL,8),(76,1,'2018-04-03 18:55:37','2018-04-03 18:55:37',42,NULL,8),(77,1,'2018-04-03 18:55:37','2018-04-03 18:55:37',43,NULL,8),(78,1,'2018-04-03 18:55:37','2018-04-03 18:55:37',44,NULL,8),(79,1,'2018-04-03 18:55:37','2018-04-03 18:55:37',45,NULL,8),(80,1,'2018-04-03 19:27:00','2018-04-03 19:27:00',55,NULL,18),(81,1,'2018-04-03 19:27:00','2018-04-03 19:27:00',56,NULL,18),(83,1,'2018-04-03 19:27:00','2018-04-03 19:27:00',61,NULL,18),(84,1,'2018-04-03 19:27:00','2018-04-03 19:27:00',62,NULL,18),(87,1,'2018-04-04 14:36:25','2018-04-04 14:36:25',63,NULL,8),(88,1,'2018-04-04 14:36:25','2018-04-04 14:36:25',59,NULL,8),(89,1,'2018-04-04 14:36:25','2018-04-04 14:36:25',60,NULL,8),(90,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',30,NULL,3),(91,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',31,NULL,3),(92,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',32,NULL,3),(93,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',33,NULL,3),(94,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',34,NULL,3),(95,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',35,NULL,3),(96,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',38,NULL,3),(97,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',36,NULL,3),(98,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',40,NULL,3),(99,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',37,NULL,3),(100,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',41,NULL,3),(101,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',42,NULL,3),(102,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',43,NULL,3),(103,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',44,NULL,3),(104,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',45,NULL,3),(105,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',46,NULL,3),(106,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',47,NULL,3),(107,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',48,NULL,3),(108,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',55,NULL,3),(109,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',56,NULL,3),(110,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',57,NULL,3),(112,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',61,NULL,3),(113,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',62,NULL,3),(114,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',63,NULL,3),(115,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',59,NULL,3),(116,1,'2018-04-08 20:13:59','2018-04-08 20:13:59',60,NULL,3);
/*!40000 ALTER TABLE `system_menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_permission`
--

DROP TABLE IF EXISTS `system_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `method` varchar(20) DEFAULT NULL COMMENT '权限访问方法',
  `permission_url` varchar(20) DEFAULT NULL COMMENT '权限访问地址',
  `perssion_type` int(11) DEFAULT NULL COMMENT '权限类型  1菜单权限 2其他权限',
  `remark` varchar(50) DEFAULT NULL COMMENT '权限备注',
  `name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_permission`
--

LOCK TABLES `system_permission` WRITE;
/*!40000 ALTER TABLE `system_permission` DISABLE KEYS */;
INSERT INTO `system_permission` VALUES (1,'2017-11-16 18:08:42','2017-11-16 18:08:46',1,'1','1',1,'1',NULL);
/*!40000 ALTER TABLE `system_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_permission_role`
--

DROP TABLE IF EXISTS `system_permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_permission_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业标识',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注描述',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色标识',
  PRIMARY KEY (`id`),
  KEY `FKmoetrlqbjy17fsv7hrfo315e0` (`permission_id`),
  KEY `FKn2yup9v55hyy67kl0s5xlbrj9` (`role_id`),
  CONSTRAINT `FKmoetrlqbjy17fsv7hrfo315e0` FOREIGN KEY (`permission_id`) REFERENCES `system_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_permission_role`
--

LOCK TABLES `system_permission_role` WRITE;
/*!40000 ALTER TABLE `system_permission_role` DISABLE KEYS */;
INSERT INTO `system_permission_role` VALUES (5,'2017-11-16 18:09:45','2017-11-16 18:09:48',1,1,'1',1);
/*!40000 ALTER TABLE `system_permission_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_role`
--

DROP TABLE IF EXISTS `system_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业标识',
  `enable_status` int(11) DEFAULT NULL COMMENT '是否启用 1启用2不启用',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色code',
  `role_description` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role`
--

LOCK TABLES `system_role` WRITE;
/*!40000 ALTER TABLE `system_role` DISABLE KEYS */;
INSERT INTO `system_role` VALUES (3,'2017-12-18 23:30:14','2017-12-18 23:30:14',1,1,'123','管理员角色','管理员角色'),(8,'2018-01-16 10:30:14','2018-01-16 10:30:14',1,1,'10001','电解厂与碳素厂','厂长'),(11,'2018-01-16 10:58:58','2018-01-16 10:58:58',1,1,'10004','各科室科长','科长'),(12,'2018-01-16 10:59:49','2018-01-16 10:59:49',1,1,'10005','各科室科员','科员'),(13,'2018-01-16 11:04:33','2018-01-16 11:04:33',1,1,'10006','各车间主任','车间主任'),(17,'2018-03-05 13:52:15','2018-03-05 13:52:15',1,1,'110','维修人员','维修工'),(18,'2018-03-05 13:52:37','2018-03-05 13:52:37',1,1,'111','设备操作人员','操作工'),(20,'2018-03-10 20:38:16','2018-03-10 20:38:16',1,1,'cjsby','车间设备员','车间设备员'),(21,'2018-03-11 08:57:43','2018-03-11 08:57:43',1,1,'cjdjy','点检设备','车间点检员'),(22,'2018-03-11 09:03:05','2018-03-11 09:03:05',1,1,'cjbz','车间班长','车间班长'),(23,'2018-04-10 16:32:36','2018-04-10 16:32:36',1,1,'ALLOCATE_WORKER','该角色可以派工','派工角色'),(24,'2018-04-10 16:32:55','2018-04-10 16:32:55',1,1,'MAINTENANCE_PERSONNEL','维修员','维修员');
/*!40000 ALTER TABLE `system_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sault` varchar(20) DEFAULT NULL COMMENT '盐',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID 工具类生成',
  `user_name` varchar(20) DEFAULT NULL COMMENT '登陆用户名',
  `corporate_code` varchar(255) DEFAULT NULL COMMENT '企业code',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qiqa94nygf4mhu5w11hef2fe8` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user`
--

LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
INSERT INTO `system_user` VALUES (1,'2018-04-08 16:39:57','2018-04-08 16:39:57',1,'381e28551e02c5598946585483fe5be4f182b5cd73455a2b','8250586535485752',123584,'admin','ESSAE86'),(18,'2018-03-10 20:34:27','2018-03-10 20:34:27',1,'51696c440814698634a59f19e8e105e41756589106848103','2925857967503991',1768877,'董国胜','ESSAE86'),(19,'2018-03-10 20:39:09','2018-03-10 20:39:09',1,'35273ac21226d1fd78e29f5c58768ac1c18e978c0b10363c','5322172588187003',1768879,'史占斌','ESSAE86'),(22,'2018-03-10 20:40:16','2018-03-10 20:40:16',1,'04d80dd18201d0327f439921074e4429700b73055710c200','4010073274903500',1768880,'张智勇','ESSAE86'),(23,'2018-03-10 20:42:28','2018-03-10 20:42:28',1,'c57735b2446a20e44ac4a32ec2a994252307f68e3f18f035','5326044229506383',1768881,'吕伟光','ESSAE86'),(29,'2018-03-11 08:42:08','2018-03-11 08:42:08',1,'92623757ce7e08fb6aa95d3d37a55fc0173792e56da75a08','2377869375032670',700864,'马淑珍','ESSAE86'),(30,'2018-03-11 08:42:21','2018-03-11 08:42:21',1,'35491c570f72a5e21c871293e58b37f4c602430e5a29d68e','5177517953403598',678656,'祁合生','ESSAE86'),(31,'2018-03-11 08:52:37','2018-03-11 08:52:37',1,'51061208fb0bb0276800d39c48c99b42b707a6f75a80ea5d','1180060989206505',350976,'陈小马','ESSAE86'),(32,'2018-03-11 08:52:55','2018-03-11 08:52:55',1,'d39619c0503c229b80511a96b69f42b4c971c2a341d4ba7f','3103281964472447',445824,'王月萍','ESSAE86'),(33,'2018-03-11 08:53:04','2018-03-11 08:53:04',1,'316118d0d317119207b2f88c548194c12e1d772865c1cd1b','1101102849117611',134976,'卫相如','ESSAE86'),(34,'2018-03-11 08:53:37','2018-03-11 08:53:37',1,'562f8ab0735818b06020043c42f19c467f3c755c4d085023','6805860329635482',19520,'曹强','ESSAE86'),(35,'2018-03-11 08:54:10','2018-03-11 08:54:10',1,'74d50c22dd77b8113bd1dd51e4dc7c40fc1933973a406d03','4027831547013300',595008,'李克敏','ESSAE86'),(36,'2018-03-11 08:54:29','2018-03-11 08:54:29',1,'88500a92745c23476dc7b37887f00c79fb3d55079ce3f11c','8025367770935931',328704,'黄邵斌','ESSAE86'),(37,'2018-03-11 08:54:43','2018-03-11 08:54:43',1,'a5cb6965ea53d9ec7a56097ab9e203f36f00706869331a11','5655976790300631',691712,'茹学渊','ESSAE86'),(38,'2018-03-11 08:55:10','2018-03-11 08:55:10',1,'552b9566f576274b1a32988d37a09663d78507452f047333','5967712879387243',383872,'宋海军','ESSAE86'),(39,'2018-03-11 08:55:31','2018-03-11 08:55:31',1,'b4436398ed8c430446664f2047763c977e5b752a6d377823','4688346273755672',279168,'张爱军','ESSAE86'),(40,'2018-03-11 08:55:45','2018-03-11 08:55:45',1,'b4d00258404f69394437384087161935c28365f29e617e0e','4084947471585910',400512,'贾志刚','ESSAE86'),(41,'2018-03-11 08:55:56','2018-03-11 08:55:56',1,'f1f16038885251f80fb59919e5933df8ff5726805f97fe16','1685105153856571',158912,'刘泽峰','ESSAE86'),(42,'2018-03-11 08:56:10','2018-03-11 08:56:10',1,'e9a26e00b77240042dd3237299e529d42819092802b07425','9607023792419002',218240,'侯津州','ESSAE86'),(43,'2018-03-11 08:56:37','2018-03-11 08:56:37',1,'375b00f96d45532490978124185c8c900f8488023566e302','7094397288088360',456576,'吴智勇','ESSAE86'),(44,'2018-03-11 08:57:07','2018-03-11 08:57:07',1,'73f27c15f59f007781f0f83ee8f900755803f5ea3b74a119','3759080380505341',958784,'杨发卫','ESSAE86'),(45,'2018-03-11 08:57:54','2018-03-11 08:57:54',1,'b6b471a5335900386076fd9428b008e0c05f348821b83940','6755066980054284',978496,'卢亚鹏','ESSAE86'),(46,'2018-03-11 08:58:03','2018-03-11 08:58:03',1,'f33f20b2f45ba32631c1a734139f3fc05d4e624a25421348','3225331333042224',269440,'胡建波','ESSAE86'),(47,'2018-03-11 08:58:45','2018-03-11 08:58:45',1,'a4387317b707e1b691921a91f3681e627652a23a02a73781','4770192931252078',504576,'李全胜','ESSAE86'),(48,'2018-03-11 08:58:55','2018-03-11 08:58:55',1,'a19c38e1932b97346251fe7ff2842943151319cd01f36493','1312761722319039',73728,'常海峰','ESSAE86'),(49,'2018-03-11 08:59:02','2018-03-11 08:59:02',1,'a5d25476b098539c12f57a34e7349854607bb6075a807905','5569315379476500',786496,'田伟','ESSAE86'),(50,'2018-03-11 08:59:08','2018-03-11 08:59:08',1,'25d997020648b41568603e2335182438532fd19e7c598368','5924460252821796',813056,'张勇勇','ESSAE86'),(51,'2018-03-11 08:59:15','2018-03-11 08:59:15',1,'59b06cd78d0784385455f81de3675a46171e789d5ed6350e','9670455135618560',867584,'黄海斌','ESSAE86'),(52,'2018-03-11 08:59:21','2018-03-11 08:59:21',1,'c14d19b5f440491392991e0026ed30967628b91a97c6e95e','1154999063629965',116416,'杜永善','ESSAE86'),(53,'2018-03-11 08:59:28','2018-03-11 08:59:28',1,'15c16f217414430431c22148c4c78048092eb58498e66e0c','5611332448825960',353280,'姚飞','ESSAE86'),(54,'2018-03-11 08:59:35','2018-03-11 08:59:35',1,'55737cb9b473f59282d0611257873e73901136a070d56111','5797580173316751',628736,'尚晓龙','ESSAE86'),(55,'2018-03-11 08:59:43','2018-03-11 08:59:43',1,'028c7872fb7244687f42e22451e57e640f2490a152a6891a','2727472217420561',985920,'牛云龙','ESSAE86'),(56,'2018-03-11 08:59:50','2018-03-11 08:59:50',1,'b2567fc36b48008376996841b68504f74531e9666cb9560f','2734079460739690',328448,'罗国峰','ESSAE86'),(57,'2018-03-11 08:59:58','2018-03-11 08:59:58',1,'214e8fa4342061b220a2be62f1d22565af13337a21e3c17a','1842122612513237',571200,'张振洲','ESSAE86'),(58,'2018-03-11 09:00:04','2018-03-11 09:00:04',1,'f58789c2ee5a420b1bd8c65875578966df05c60a3123e90f','5825218558606330',747904,'杜斌','ESSAE86'),(59,'2018-03-11 09:00:11','2018-03-11 09:00:11',1,'63f82eb4a63408c707b48d25e65b9d05cb03616c4f585203','3243804269501480',465728,'赵立信','ESSAE86'),(60,'2018-03-11 09:00:17','2018-03-11 09:00:17',1,'238f0508d041a8a10b60ed9b24278fa17818b2962997d47e','3084800948112277',395392,'郭建红','ESSAE86'),(61,'2018-03-11 09:00:24','2018-03-11 09:00:24',1,'55731349a06456ca6367fd4a77ce39067608906923429e0e','5196667473600220',946880,'杨杰创','ESSAE86'),(62,'2018-03-11 09:00:31','2018-03-11 09:00:31',1,'91ef6034162104503eb0641f896175a3426880e157886d0b','1642430197360580',343168,'王东升','ESSAE86'),(63,'2018-03-11 09:00:38','2018-03-11 09:00:38',1,'e8a37295398fe9f00602794247d107d6e75b890f4c177456','8758902470659475',680640,'潘瑞博','ESSAE86'),(64,'2018-03-11 09:00:48','2018-03-11 09:00:48',1,'69bd1909fd48c1301552758083247b57d14491390a779546','9194112837741074',33920,'邵太福','ESSAE86'),(65,'2018-03-11 09:00:55','2018-03-11 09:00:55',1,'c8f648c9c44c121d1ed4b756063387c7e727a27785e2c797','8494214568722829',158720,'温温','ESSAE86'),(66,'2018-03-11 09:01:02','2018-03-11 09:01:02',1,'88436241726ce98976127a7161de1c06bd74a2bd03853d4e','8616972711672054',924736,'李晓康','ESSAE86'),(67,'2018-03-11 09:01:10','2018-03-11 09:01:10',1,'99f92d77559754502ba58876c8483b233c6bc0b27104c195','9279425783360749',735232,'畅金峰','ESSAE86'),(68,'2018-03-11 09:01:16','2018-03-11 09:01:16',1,'27ba48b38e49f5386c70425119bf54575b6625935c025b88','7434560595765528',421888,'春喜','ESSAE86'),(69,'2018-03-11 09:06:49','2018-03-11 09:06:49',1,'96638396498cb6c93196bd2406ab01794553b44d7b097f09','6868636260954790',343808,'赵磊','ESSAE86'),(70,'2018-03-11 09:07:16','2018-03-11 09:07:16',1,'03bd83853d5219502db3401a782f4739794ec52509681f01','3855923184945080',791616,'张贵社','ESSAE86'),(71,'2018-03-11 09:07:55','2018-03-11 09:07:55',1,'41eb6cb6e25925a051722f8a49d45105b118e6b63145cd8c','1665552895516358',638848,'朱晓东','ESSAE86'),(72,'2018-03-11 09:08:02','2018-03-11 09:08:02',1,'356e72d5755e684f4f04a10d947158318d0776460e239c2d','5755844045106032',6656,'张波','ESSAE86'),(73,'2018-03-11 09:08:09','2018-03-11 09:08:09',1,'c3a85fd45c57834895e92406282673c5572f55069155c568','3545399087525956',61184,'罗卫林','ESSAE86'),(74,'2018-03-11 09:08:16','2018-03-11 09:08:16',1,'a7cd04b6190e48ff9ad4b45573e13fe4117907cf9fb40574','7060894533477947',440448,'侯军龙','ESSAE86'),(75,'2018-03-11 09:08:22','2018-03-11 09:08:22',1,'38b12ac6fb5c040955b5d19e84c94a63937bf6b80bf94c57','8265455944376095',220288,'张跃兵','ESSAE86'),(76,'2018-03-11 09:08:29','2018-03-11 09:08:29',1,'051b6d575d23a03e5c88ab51f6f454596a1449c201f06974','5672058565919007',948736,'朱彦武','ESSAE86'),(77,'2018-03-11 09:08:36','2018-03-11 09:08:36',1,'02229d16af6ab22638e2ad19a1c744d7182629de93a59e55','2966232114729955',918656,'吴丽霞','ESSAE86'),(78,'2018-03-11 09:08:44','2018-03-11 09:08:44',1,'33a08c98b018b45761d6092a504a22a0130d92660e740400','3881466202002040',560640,'范丁丁','ESSAE86'),(79,'2018-03-11 09:08:55','2018-03-11 09:08:55',1,'090e7fa9c14911501c67570577f68348769be1e153a82909','9794117078891580',112256,'樊平','ESSAE86'),(80,'2018-03-11 09:09:00','2018-03-11 09:09:00',1,'e98550c74739d50c0a53ce8878f05b32181eb76b04572c98','9573503885217079',114048,'王凯弘','ESSAE86'),(81,'2018-03-11 09:09:06','2018-03-11 09:09:06',1,'38ac20f98c17686d17493786e8288489c335c3363f42e500','8291819888933320',353792,'王凡','ESSAE86'),(82,'2018-03-11 09:09:12','2018-03-11 09:09:12',1,'04429ae04223b22d9df2886695d93344069276bb85822569','4902292653496826',113920,'毋晓凡','ESSAE86'),(83,'2018-03-11 09:09:25','2018-03-11 09:09:25',1,'198552186d9918b30177002733380ef32085487d14037e44','9589807230388134',453184,'李延','ESSAE86'),(84,'2018-03-11 09:09:36','2018-03-11 09:09:36',1,'53c77909582768ce92a71f2db6d743915369904b7ec8f514','3792897264160781',790784,'张雷','ESSAE86'),(85,'2018-03-11 09:09:43','2018-03-11 09:09:43',1,'18682197976781210644ea59391b4dc2cb6d75e832f4006b','8276104594265346',346816,'张小龙','ESSAE86'),(86,'2018-03-11 09:09:49','2018-03-11 09:09:49',1,'192f7c29b19b848a7698526e01350fd21c25420f0229937f','9799478610222097',616384,'张凯凯','ESSAE86'),(87,'2018-03-11 09:09:56','2018-03-11 09:09:56',1,'84e082623c4766580a017636f14f92c19b27c0aa4d22dd0f','4824601319120420',280448,'王红芳','ESSAE86'),(88,'2018-03-11 09:10:02','2018-03-11 09:10:02',1,'d1fe98047066a9b48316a453c9fa0af2eb1df83498318073','1946986590218917',818304,'李明亮','ESSAE86'),(89,'2018-03-11 09:10:08','2018-03-11 09:10:08',1,'47b67d91dc9b74b35194e77dc1392580ec6b33cf1bf0890a','7719454712063100',786560,'冯特特','ESSAE86'),(90,'2018-03-11 09:10:14','2018-03-11 09:10:14',1,'97744c78892e20f58a11e96e08ab87314813633a17723d65','7482081688113126',396032,'寇博青','ESSAE86'),(91,'2018-03-11 09:10:20','2018-03-11 09:10:20',1,'f87d8f61375ea76779441d77d3510b38c191e7fb2661d28e','8815774730897218',102912,'翟建昌','ESSAE86'),(92,'2018-03-11 09:10:26','2018-03-11 09:10:26',1,'924c50f8b54377e67333970f064c55643924a36e1ba8d37c','2584773065423187',47744,'程英杰','ESSAE86'),(93,'2018-03-11 09:10:32','2018-03-11 09:10:32',1,'44ad5ae0df74c92e7942699cd5897e26020d69a25f239749','4507972957609534',809728,'陈佳庆','ESSAE86'),(94,'2018-03-11 09:10:37','2018-03-11 09:10:37',1,'389406c00902e7fb65252f98912f8b869f47779a24a4a43b','8000765918647243',868480,'杜泽峰','ESSAE86'),(95,'2018-03-11 09:10:48','2018-03-11 09:10:48',1,'e95c0e179f8b295a16401f10e32986a7c953f79e66849d07','9078910138757640',52224,'薛罡','ESSAE86'),(96,'2018-03-11 09:10:54','2018-03-11 09:10:54',1,'267098c6de98c9f716a9823fb8772c46cc8ed11a3b599167','6969919382681396',197760,'张世杰','ESSAE86'),(97,'2018-03-11 09:11:02','2018-03-11 09:11:02',1,'94ba69a6e02291bd3703e74d56017ec7ae6c25ce0262de0d','4662133467765020',974720,'朱淑焕','ESSAE86'),(98,'2018-03-11 09:11:08','2018-03-11 09:11:08',1,'46b13e091d8a66b83455fc98688705261884a3169528c465','6398635980683986',331584,'宋建飞','ESSAE86'),(99,'2018-03-11 09:11:13','2018-03-11 09:11:13',1,'331a0387ed0205ab9fb4df3c54c185c00400431c62b75563','3070594348003676',785856,'吕亚超','ESSAE86'),(100,'2018-03-11 09:11:20','2018-03-11 09:11:20',1,'e34c6246089688a87535240bc9062ee54a5119627ec50305','3669875092559750',292352,'张勇','ESSAE86'),(101,'2018-03-11 09:11:28','2018-03-11 09:11:28',1,'74ca2245c36168023c912e44046274e8a20a88529985dc65','4256831447808956',454720,'翟瑞环','ESSAE86'),(102,'2018-03-11 09:11:34','2018-03-11 09:11:34',1,'b1849335670d19fa28f9ba04f97b04628971c46e4b71077f','1950929090274417',607168,'谢晓龙','ESSAE86'),(103,'2018-03-11 09:11:40','2018-03-11 09:11:40',1,'a6b04f442f30b77b7290f040a35867471e0463290850f849','6443770436703004',581824,'凌伟','ESSAE86'),(104,'2018-03-11 09:11:46','2018-03-11 09:11:46',1,'c1ff7c81357c565d9d50238a178856f8254de4d08ba2f987','1717690875844828',752640,'胡晓辉','ESSAE86'),(105,'2018-03-11 09:11:53','2018-03-11 09:11:53',1,'b32a71c5dd59a7894681ea9b045033e30049b2bc7845bb67','3755741943342756',150272,'罗虎林','ESSAE86'),(106,'2018-03-11 09:11:59','2018-03-11 09:11:59',1,'462c45e1445370d04fc0684df4059f293a0716788fd5a681','6415040449906858',34944,'樊博博','ESSAE86'),(107,'2018-03-11 09:12:06','2018-03-11 09:12:06',1,'06ba0da17148c6c702437e3bc36569f9286e826713d64a57','6014603336962165',754112,'李汝军','ESSAE86'),(108,'2018-04-03 16:43:22','2018-04-03 16:43:22',1,'39d00e68fd48853634c32a2f364c43f3a18aa9119035550f','9084533264389950',852416,'石永文','ESSAE86'),(110,'2018-04-10 16:15:07','2018-04-10 16:15:07',1,'99f067d4173cf41d29a4870c53e52803e246e5130ce91b88','9643424032345098',61760,'闫文杰','ESSAE86'),(111,'2018-04-10 16:15:28','2018-04-10 16:15:28',1,'b84668e4972053a565418b2fc5af1401bd5564b32628537b','8642361251154287',814656,'石祥勇','ESSAE86'),(112,'2018-04-10 16:17:17','2018-04-10 16:17:17',1,'e2ce66f8153d165a1a42d34025c57173166465f60fd4c662','2683612457365046',943744,'高小平','ESSAE86');
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user_department`
--

DROP TABLE IF EXISTS `system_user_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user_department` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user_department`
--

LOCK TABLES `system_user_department` WRITE;
/*!40000 ALTER TABLE `system_user_department` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_user_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user_role`
--

DROP TABLE IF EXISTS `system_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `FKnp61n3syn415rmbwvhnw87u3a` (`role_id`),
  KEY `FKkc6ik04bm9v9kldgbt3kkgfac` (`user_id`),
  CONSTRAINT `FKkc6ik04bm9v9kldgbt3kkgfac` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FKnp61n3syn415rmbwvhnw87u3a` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COMMENT='用户->角色中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user_role`
--

LOCK TABLES `system_user_role` WRITE;
/*!40000 ALTER TABLE `system_user_role` DISABLE KEYS */;
INSERT INTO `system_user_role` VALUES (65,'2018-03-10 20:34:32','2018-03-10 20:34:32',1,8,18),(69,'2018-03-10 20:40:21','2018-03-10 20:40:21',1,20,22),(75,'2018-03-11 08:52:43','2018-03-11 08:52:43',1,11,31),(76,'2018-03-11 08:53:19','2018-03-11 08:53:19',1,12,32),(77,'2018-03-11 08:53:22','2018-03-11 08:53:22',1,12,33),(78,'2018-03-11 08:54:01','2018-03-11 08:54:01',1,13,34),(79,'2018-03-11 08:54:18','2018-03-11 08:54:18',1,13,35),(80,'2018-03-11 08:54:34','2018-03-11 08:54:34',1,13,36),(82,'2018-03-11 08:54:49','2018-03-11 08:54:49',1,13,37),(83,'2018-03-11 08:55:21','2018-03-11 08:55:21',1,13,38),(84,'2018-03-11 08:55:37','2018-03-11 08:55:37',1,13,39),(85,'2018-03-11 08:56:23','2018-03-11 08:56:23',1,20,41),(86,'2018-03-11 08:56:27','2018-03-11 08:56:27',1,20,42),(87,'2018-03-11 08:56:46','2018-03-11 08:56:46',1,20,43),(88,'2018-03-11 08:58:07','2018-03-11 08:58:07',1,21,44),(89,'2018-03-11 08:58:11','2018-03-11 08:58:11',1,21,45),(90,'2018-03-11 08:58:14','2018-03-11 08:58:14',1,21,46),(91,'2018-03-11 09:03:16','2018-03-11 09:03:16',1,22,47),(92,'2018-03-11 09:03:24','2018-03-11 09:03:24',1,22,48),(93,'2018-03-11 09:03:36','2018-03-11 09:03:36',1,22,49),(94,'2018-03-11 09:03:43','2018-03-11 09:03:43',1,22,50),(95,'2018-03-11 09:03:47','2018-03-11 09:03:47',1,22,51),(96,'2018-03-11 09:03:50','2018-03-11 09:03:50',1,22,52),(97,'2018-03-11 09:03:55','2018-03-11 09:03:55',1,22,53),(98,'2018-03-11 09:03:58','2018-03-11 09:03:58',1,22,53),(99,'2018-03-11 09:04:02','2018-03-11 09:04:02',1,22,54),(100,'2018-03-11 09:04:06','2018-03-11 09:04:06',1,22,55),(101,'2018-03-11 09:04:09','2018-03-11 09:04:09',1,22,56),(102,'2018-03-11 09:04:13','2018-03-11 09:04:13',1,22,57),(103,'2018-03-11 09:04:19','2018-03-11 09:04:19',1,22,58),(104,'2018-03-11 09:04:23','2018-03-11 09:04:23',1,22,59),(105,'2018-03-11 09:05:03','2018-03-11 09:05:03',1,22,56),(106,'2018-03-11 09:05:30','2018-03-11 09:05:30',1,22,57),(107,'2018-03-11 09:05:34','2018-03-11 09:05:34',1,22,57),(108,'2018-03-11 09:05:37','2018-03-11 09:05:37',1,22,58),(109,'2018-03-11 09:05:43','2018-03-11 09:05:43',1,22,59),(110,'2018-03-11 09:05:48','2018-03-11 09:05:48',1,22,60),(111,'2018-03-11 09:05:52','2018-03-11 09:05:52',1,22,61),(112,'2018-03-11 09:05:57','2018-03-11 09:05:57',1,22,62),(113,'2018-03-11 09:06:03','2018-03-11 09:06:03',1,22,64),(114,'2018-03-11 09:06:08','2018-03-11 09:06:08',1,22,65),(115,'2018-03-11 09:06:12','2018-03-11 09:06:12',1,22,66),(116,'2018-03-11 09:06:16','2018-03-11 09:06:16',1,22,67),(117,'2018-03-11 09:06:20','2018-03-11 09:06:20',1,22,68),(118,'2018-03-11 09:07:27','2018-03-11 09:07:27',1,18,69),(119,'2018-03-11 09:07:32','2018-03-11 09:07:32',1,18,70),(122,'2018-04-03 16:42:27','2018-04-03 16:42:27',1,13,34),(123,'2018-04-03 16:42:49','2018-04-03 16:42:49',1,20,40),(124,'2018-04-03 16:42:49','2018-04-03 16:42:49',1,21,40),(125,'2018-04-03 16:43:02','2018-04-03 16:43:02',1,22,49),(126,'2018-04-03 16:43:32','2018-04-03 16:43:32',1,22,108),(127,'2018-04-03 16:43:41','2018-04-03 16:43:41',1,22,50),(128,'2018-04-03 16:43:56','2018-04-03 16:43:56',1,22,51),(129,'2018-04-03 17:02:58','2018-04-03 17:02:58',1,22,49),(130,'2018-04-03 17:03:20','2018-04-03 17:03:20',1,18,49),(131,'2018-04-03 17:03:20','2018-04-03 17:03:20',1,22,49),(132,'2018-04-03 17:17:23','2018-04-03 17:17:23',1,18,108),(133,'2018-04-03 17:17:23','2018-04-03 17:17:23',1,22,108),(134,'2018-04-03 19:27:21','2018-04-03 19:27:21',1,18,49),(135,'2018-04-03 19:39:04','2018-04-03 19:39:04',1,18,49),(136,'2018-04-03 19:39:04','2018-04-03 19:39:04',1,22,49),(137,'2018-04-03 19:43:42','2018-04-03 19:43:42',1,17,49),(139,'2018-04-08 15:47:10','2018-04-08 15:47:10',1,18,49),(140,'2018-04-08 15:47:10','2018-04-08 15:47:10',1,22,49),(141,'2018-04-08 16:00:15','2018-04-08 16:00:15',1,18,49),(142,'2018-04-08 16:00:15','2018-04-08 16:00:15',1,22,49),(143,'2018-04-08 16:03:15','2018-04-08 16:03:15',1,17,49),(144,'2018-04-08 16:03:15','2018-04-08 16:03:15',1,18,49),(145,'2018-04-08 16:03:15','2018-04-08 16:03:15',1,22,49),(146,'2018-04-08 16:05:44','2018-04-08 16:05:44',1,18,49),(147,'2018-04-08 16:05:44','2018-04-08 16:05:44',1,22,49),(150,'2018-04-08 20:26:43','2018-04-08 20:26:43',1,3,1),(151,'2018-04-09 08:51:51','2018-04-09 08:51:51',1,18,49),(152,'2018-04-09 08:51:51','2018-04-09 08:51:51',1,22,49),(153,'2018-04-10 16:17:30','2018-04-10 16:17:30',1,17,112),(154,'2018-04-10 16:17:35','2018-04-10 16:17:35',1,17,111),(155,'2018-04-10 16:18:52','2018-04-10 16:18:52',1,17,110),(156,'2018-04-10 16:18:52','2018-04-10 16:18:52',1,22,110),(157,'2018-04-10 16:19:17','2018-04-10 16:19:17',1,18,49),(158,'2018-04-10 16:19:17','2018-04-10 16:19:17',1,22,49),(159,'2018-04-10 16:49:02','2018-04-10 16:49:02',1,17,110),(160,'2018-04-10 16:49:02','2018-04-10 16:49:02',1,23,110),(161,'2018-04-10 16:49:02','2018-04-10 16:49:02',1,24,110),(162,'2018-04-10 16:49:11','2018-04-10 16:49:11',1,17,111),(163,'2018-04-10 16:49:15','2018-04-10 16:49:15',1,17,112),(164,'2018-04-10 16:50:38','2018-04-10 16:50:38',1,17,112),(165,'2018-04-10 16:50:38','2018-04-10 16:50:38',1,24,112),(166,'2018-04-10 16:50:44','2018-04-10 16:50:44',1,17,111),(167,'2018-04-10 16:50:44','2018-04-10 16:50:44',1,24,111);
/*!40000 ALTER TABLE `system_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trouble_record`
--

DROP TABLE IF EXISTS `trouble_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trouble_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` bigint(20) NOT NULL COMMENT '设备主键',
  `trouble_level` int(1) DEFAULT NULL COMMENT '故障等级(1:紧急；2:一般；3:其它)',
  `trouble_type` bigint(20) DEFAULT NULL COMMENT '故障类型',
  `repair_group_id` bigint(20) DEFAULT NULL COMMENT '维修班组',
  `device_status` int(1) NOT NULL COMMENT '设备状态（1:停机待修；2:带病运行；3:其它）',
  `device_user` varchar(10) DEFAULT NULL COMMENT '设备使用者',
  `phone` varchar(11) DEFAULT NULL COMMENT '设备使用者电话',
  `device_address` varchar(50) DEFAULT NULL COMMENT '设备位置',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '故障描述',
  `happen_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发生时间',
  `status` int(1) NOT NULL COMMENT '0:待审核;1:需要立刻维修；2:维修中；3:维修完成，待验证；4:验证完成',
  `create_user` varchar(10) NOT NULL DEFAULT '' COMMENT '报修人',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `order_no` varchar(20) DEFAULT NULL COMMENT '维修单号',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '上报人id',
  `repair_user_id` bigint(20) DEFAULT NULL COMMENT '当前维修人员id',
  `repair_user_name` varchar(10) DEFAULT NULL COMMENT '当前维修人姓名',
  `validate_user_id` bigint(20) DEFAULT NULL COMMENT '当前验证人员id',
  `repaired` int(1) DEFAULT NULL COMMENT '故障是否修复（0:否；1:是）',
  `suggest` varchar(100) DEFAULT NULL COMMENT '评价和建议',
  `star_level` int(1) DEFAULT NULL COMMENT '星际打分，1，2，3，4，5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='故障信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trouble_record`
--

LOCK TABLES `trouble_record` WRITE;
/*!40000 ALTER TABLE `trouble_record` DISABLE KEYS */;
INSERT INTO `trouble_record` VALUES (1,75,1,6,29,1,'法大赛','13543254663',NULL,'方法大的方法','2018-05-23 15:18:09',0,'王浩',1,'2018-05-23 15:18:09','2018-05-23 15:18:09','WX1527088689083',1,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `trouble_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trouble_record_image_info`
--

DROP TABLE IF EXISTS `trouble_record_image_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trouble_record_image_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trouble_record_id` bigint(20) DEFAULT NULL COMMENT '故障ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `image_key` varchar(50) DEFAULT NULL COMMENT '图片KEY',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='故障检图片信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trouble_record_image_info`
--

LOCK TABLES `trouble_record_image_info` WRITE;
/*!40000 ALTER TABLE `trouble_record_image_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `trouble_record_image_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trouble_record_user_rel`
--

DROP TABLE IF EXISTS `trouble_record_user_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trouble_record_user_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trouble_record_id` bigint(20) NOT NULL COMMENT '故障记录主键',
  `deal_user_id` bigint(20) NOT NULL COMMENT '处理人主键',
  `deal_user_name` varchar(10) DEFAULT '' COMMENT '处理人姓名',
  `deal_phase` int(1) DEFAULT NULL COMMENT '处理的阶段（申请审核、维修完成待验证）',
  `deal_step` int(11) DEFAULT NULL COMMENT '处理步骤，每一个阶段都有不同的步骤',
  `execute_type` int(1) DEFAULT NULL COMMENT '执行类型,1:一人处理;2:全部处理',
  `deal_status` int(1) DEFAULT NULL COMMENT '处理结果,不同的处理阶段对应的值不同.',
  `deal_step_status` int(11) DEFAULT NULL COMMENT '当前步骤处理状态，0:处理中；1:等待处理；2:处理完成',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='故障处理人员关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trouble_record_user_rel`
--

LOCK TABLES `trouble_record_user_rel` WRITE;
/*!40000 ALTER TABLE `trouble_record_user_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `trouble_record_user_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_time`
--

DROP TABLE IF EXISTS `work_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_time` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(1) NOT NULL COMMENT '类型：1:故障维修；2:保养计划',
  `repair_record_id` bigint(20) unsigned NOT NULL COMMENT '维修单主键',
  `repair_user_id` bigint(20) DEFAULT NULL COMMENT '维修人员主键',
  `repair_user_name` varchar(10) DEFAULT '' COMMENT '维修人员姓名',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `cost_hour` int(5) DEFAULT NULL COMMENT '维修耗时',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修工时信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_time`
--

LOCK TABLES `work_time` WRITE;
/*!40000 ALTER TABLE `work_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `work_time` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-24  0:02:52
