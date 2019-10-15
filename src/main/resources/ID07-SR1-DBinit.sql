-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 172.16.0.182    Database: sr1
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `action_description` varchar(255) DEFAULT NULL,
  `action_status` int(11) DEFAULT NULL,
  `action_assignee` varchar(36) DEFAULT NULL,
  `action_creator` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9m2aarhhqej7v43bgdub6fww5` (`code`),
  KEY `FKiybuws8kfy9k8k80lt5tvf9fs` (`action_assignee`),
  KEY `FKbtce3e49p0jknlbgk587ysi5n` (`action_creator`),
  CONSTRAINT `FKbtce3e49p0jknlbgk587ysi5n` FOREIGN KEY (`action_creator`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKiybuws8kfy9k8k80lt5tvf9fs` FOREIGN KEY (`action_assignee`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action`
--

LOCK TABLES `action` WRITE;
/*!40000 ALTER TABLE `action` DISABLE KEYS */;
/*!40000 ALTER TABLE `action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actuals_table`
--

DROP TABLE IF EXISTS `actuals_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actuals_table` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `for_year` int(11) DEFAULT NULL,
  `cost_center_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qymqmr3ugiqw1xruy7xqiyxp4` (`code`),
  KEY `FKpg9yh3v2owul20x61vrdfavy7` (`cost_center_id`),
  CONSTRAINT `FKpg9yh3v2owul20x61vrdfavy7` FOREIGN KEY (`cost_center_id`) REFERENCES `cost_center` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuals_table`
--

LOCK TABLES `actuals_table` WRITE;
/*!40000 ALTER TABLE `actuals_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `actuals_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `approval_for_request`
--

DROP TABLE IF EXISTS `approval_for_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `approval_for_request` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `approval_status` int(11) DEFAULT NULL,
  `approval_type` int(11) DEFAULT NULL,
  `comment_by_approver` varchar(255) DEFAULT NULL,
  `comment_by_requester` varchar(255) DEFAULT NULL,
  `requested_item_id` varchar(255) DEFAULT NULL,
  `approver_id` varchar(36) DEFAULT NULL,
  `requester_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pcf7yo81kqf1yldducqp7lpui` (`code`),
  KEY `FKe2pv1n5u4uvgs3i9hbuae5qiq` (`approver_id`),
  KEY `FK34br3b4so4cqr1lvvogo9f0eg` (`requester_id`),
  CONSTRAINT `FK34br3b4so4cqr1lvvogo9f0eg` FOREIGN KEY (`requester_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKe2pv1n5u4uvgs3i9hbuae5qiq` FOREIGN KEY (`approver_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_for_request`
--

LOCK TABLES `approval_for_request` WRITE;
/*!40000 ALTER TABLE `approval_for_request` DISABLE KEYS */;
INSERT INTO `approval_for_request` VALUES ('0305703a-b3bb-42fc-b062-c514703334a0','RT-ADMIN-15','admin','2019-10-04 18:47:30',NULL,_binary '\0',NULL,'2019-10-04 18:47:31','RT-ADMIN-15','',NULL,0,2,'','BM Approver please view the budget plan.','7d1797f7-a185-4798-b555-563ddf66d61b','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),('05a4ebb7-cf11-449d-9a4f-af4e45379f15','RT-YINGSHI2502-8','yingshi2502','2019-10-04 16:51:33',NULL,_binary '\0','yingshi2502','2019-10-04 16:51:55','RT-YINGSHI2502-8','',NULL,2,2,'fff','BM Approver please view the budget plan.','17071e6d-692f-4840-937c-1868072adf37','dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750'),('0b855b65-e4d5-4636-b1eb-b1fe32e7d208','RT-XUHONG-2','xuhong','2019-10-04 16:21:50',NULL,_binary '\0',NULL,'2019-10-04 16:21:50','RT-XUHONG-2','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','669ccc57-ae77-41cf-99f9-2ded822cbcf1','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('216c7373-f7e8-4876-b275-41cb8e6b3f60','RT-XUHONG-27','xuhong','2019-10-05 03:22:50',NULL,_binary '\0','xuhong','2019-10-05 03:23:11','RT-XUHONG-27','',NULL,1,2,'asdfds','BM Approver please view the budget plan.','218dbe9c-71fe-4b86-9b31-3a3fc9eedecd','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('259bb368-fe65-48b4-9b78-38c31b954435','RT-XUHONG-7','xuhong','2019-10-04 16:46:11',NULL,_binary '\0','xuhong','2019-10-04 16:49:10','RT-XUHONG-7','',NULL,2,2,'rejjjjjject','BM Approver please view the budget plan.','26a96f83-3689-4a6b-8fd9-2dc90d503d38','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('369bee2c-eb65-42a3-a492-9f5d312f8afd','RT-XUHONG-28','xuhong','2019-10-05 03:23:11',NULL,_binary '\0',NULL,'2019-10-05 03:23:12','RT-XUHONG-28','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','218dbe9c-71fe-4b86-9b31-3a3fc9eedecd','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('381e0faf-feed-4431-8a6a-646f46e1aed5','RT-XUHONG-29','xuhong','2019-10-05 03:25:15',NULL,_binary '\0','xuhong','2019-10-05 03:25:46','RT-XUHONG-29','',NULL,1,2,'bm approved','BM Approver please view the budget plan.','69141a51-b072-4f1c-94df-df4af18a62e6','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('3d2a1714-db39-4254-aae3-d6caa1c24a22','RT-ADMIN-16','admin','2019-10-04 18:51:04',NULL,_binary '\0',NULL,'2019-10-04 18:51:04','RT-ADMIN-16','',NULL,0,2,'','BM Approver please view the budget plan.','6f393b36-0298-44f9-ab69-a2c3fa64aecb','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),('455db2a1-5744-47d4-b7b4-6ae26a11e9c4','RT-XUHONG-32','xuhong','2019-10-05 03:30:55',NULL,_binary '\0','xuhong','2019-10-05 03:31:34','RT-XUHONG-32','',NULL,1,2,NULL,'BM Approver please view the budget plan.','89863b27-cb3c-4d53-94af-f42161bac3cc','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('4f7cfc31-d8fb-4b5e-aa42-b202f11028f7','RT-XUHONG-20','xuhong','2019-10-05 03:07:00',NULL,_binary '\0','xuhong','2019-10-05 03:07:47','RT-XUHONG-20','',NULL,2,2,'not good','BM Approver please view the budget plan.','55e07d7c-5a82-40eb-b19b-d9d1a1894cc8','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('52c25f61-e823-4258-9398-19372a056166','RT-YINGSHI2502-38','yingshi2502','2019-10-05 06:35:57',NULL,_binary '\0','yingshi2502','2019-10-05 06:36:21','RT-YINGSHI2502-38','',NULL,1,2,'Yingshi: Approve BM','BM Approver please view the budget plan.','7d0cd03d-9150-4060-81f1-a7fe2bdfdc8c','dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750'),('54b14c56-e743-43a7-8ee1-1657e75d2805','RT-XUHONG-36','xuhong','2019-10-05 03:36:25',NULL,_binary '\0',NULL,'2019-10-05 03:36:25','RT-XUHONG-36','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','aa656742-765d-4202-968a-732d1d2e6d93','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('57922504-6676-48bf-b839-bd355fa09642','RT-YINGSHI2502-39','yingshi2502','2019-10-05 06:36:21',NULL,_binary '\0',NULL,'2019-10-05 06:36:21','RT-YINGSHI2502-39','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','7d0cd03d-9150-4060-81f1-a7fe2bdfdc8c','f56ad9c8-e92a-43ff-b101-be4cfa20558f','dd650097-4b59-4dae-8d6f-eec585813750'),('5b7cf4a4-0994-421d-b1b9-94be0ccf232d','RT-XUHONG-3','xuhong','2019-10-04 16:23:37',NULL,_binary '\0','xuhong','2019-10-04 16:37:01','RT-XUHONG-3','',NULL,1,2,'adfpp','BM Approver please view the budget plan.','0945a3ed-81d7-43d7-b8ec-f4d904cc9f4c','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('600902bb-e465-459c-9282-a6d2512f6315','RT-ADMIN-34','admin','2019-10-05 03:34:13',NULL,_binary '\0',NULL,'2019-10-05 03:34:13','RT-ADMIN-34','',NULL,0,2,'','BM Approver please view the budget plan.','27d6abe2-c756-41aa-8a2b-cee8b7e5c0b0','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),('678c479a-b81b-439c-bee8-a5e2e987a6eb','RT-YINGSHI2502-13','yingshi2502','2019-10-04 17:31:15',NULL,_binary '\0',NULL,'2019-10-04 17:31:16','RT-YINGSHI2502-13','',NULL,0,2,'','BM Approver please view the budget plan.','ef318018-df64-4717-963a-b306b0741b27','dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750'),('69550ef9-c95b-4d03-9cbf-1d98770cc3c7','RT-XUHONG-1','xuhong','2019-10-04 16:21:18',NULL,_binary '\0','xuhong','2019-10-04 16:21:50','RT-XUHONG-1','',NULL,1,2,'Yingshi: BM Approve!','BM Approver please view the budget plan.','669ccc57-ae77-41cf-99f9-2ded822cbcf1','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('69ace335-d82b-4873-a625-8742d4dfe064','RT-XUHONG-35','xuhong','2019-10-05 03:36:09',NULL,_binary '\0','xuhong','2019-10-05 03:36:25','RT-XUHONG-35','',NULL,1,2,NULL,'BM Approver please view the budget plan.','aa656742-765d-4202-968a-732d1d2e6d93','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('6cdc8735-2c42-4cdb-82bc-c790d3fa82bf','RT-XUHONG-30','xuhong','2019-10-05 03:25:46',NULL,_binary '\0',NULL,'2019-10-05 03:25:46','RT-XUHONG-30','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','69141a51-b072-4f1c-94df-df4af18a62e6','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('6e30ee79-bff3-435d-abc7-fd709460c525','RT-XUHONG-6','xuhong','2019-10-04 16:37:01',NULL,_binary '\0',NULL,'2019-10-04 16:37:02','RT-XUHONG-6','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','0945a3ed-81d7-43d7-b8ec-f4d904cc9f4c','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('75423417-eefa-4f60-b5b0-7097035d6cfc','RT-XUHONG-26','xuhong','2019-10-05 03:20:37',NULL,_binary '\0',NULL,'2019-10-05 03:20:37','RT-XUHONG-26','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','f5917493-c610-41f9-8833-7b2cdf3c6347','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('81f24f3d-efe2-4061-ad6d-92ad3a42a30c','RT-YINGSHI2502-37','yingshi2502','2019-10-05 06:34:07',NULL,_binary '\0',NULL,'2019-10-05 06:34:07','RT-YINGSHI2502-37','',NULL,0,2,'','BM Approver please view the budget plan.','a088a495-4e57-48af-931e-9747fbf155f8','dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750'),('851de56e-26fb-4a01-aa18-da85543742d1','RT-ADMIN-14','admin','2019-10-04 18:46:48',NULL,_binary '\0',NULL,'2019-10-04 18:46:49','RT-ADMIN-14','',NULL,0,2,'','BM Approver please view the budget plan.','1df6100c-cffe-447d-8467-c3c59fbad3cb','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),('853dee5c-b855-4a62-9871-0613fb82f18b','RT-XUHONG-12','xuhong','2019-10-04 17:04:54',NULL,_binary '\0','xuhong','2019-10-04 17:05:14','RT-XUHONG-12','',NULL,2,2,'afd','BM Approver please view the budget plan.','6144ed12-2d1c-46aa-818e-86c2f6dc6bdd','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('99b14f99-1b13-424f-90b1-483080fa8ec8','RT-XUHONG-31','xuhong','2019-10-05 03:29:49',NULL,_binary '\0',NULL,'2019-10-05 03:29:49','RT-XUHONG-31','',NULL,0,2,'','BM Approver please view the budget plan.','7d1352d9-97c0-4a6b-9545-683e0a61f7f4','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('9d5e798b-7088-4507-9ad9-82a2e54b0a45','RT-XUHONG-10','xuhong','2019-10-04 17:02:45',NULL,_binary '\0','xuhong','2019-10-04 17:03:16','RT-XUHONG-10','',NULL,1,2,'a','BM Approver please view the budget plan.','71fa566c-6858-402d-9eae-660fc9deddef','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('ad6e6930-b645-4b9f-84ba-d803bafc9a4c','RT-XUHONG-19','xuhong','2019-10-05 03:05:02',NULL,_binary '\0',NULL,'2019-10-05 03:05:02','RT-XUHONG-19','',NULL,0,2,'','BM Approver please view the budget plan.','49d17b0d-34dd-4efe-ac68-e021d39bd307','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('ae80618d-fdb2-49f5-aa24-bc8a9b7898a3','RT-XUHONG-24','xuhong','2019-10-05 03:19:40',NULL,_binary '\0','xuhong','2019-10-05 03:20:05','RT-XUHONG-24','',NULL,1,2,NULL,'BM Approver please view the budget plan.','bf5cd707-5442-4015-be5a-e611fe9885c9','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('bdeb5efb-f007-4ed0-8f60-524cc1390891','RT-XUHONG-25','xuhong','2019-10-05 03:20:05',NULL,_binary '\0',NULL,'2019-10-05 03:20:05','RT-XUHONG-25','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','bf5cd707-5442-4015-be5a-e611fe9885c9','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('be0b8662-a8b5-4e3e-a0f3-4d888a754fef','RT-XUHONG-21','xuhong','2019-10-05 03:08:29',NULL,_binary '\0','xuhong','2019-10-05 03:08:57','RT-XUHONG-21','',NULL,1,2,'approve, this it better','BM Approver please view the budget plan.','595b2958-d3b6-4514-8818-9e5a379f6eb1','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('d3dbb026-f137-4a94-a98a-bd2cf0cde24c','RT-XUHONG-33','xuhong','2019-10-05 03:31:34',NULL,_binary '\0',NULL,'2019-10-05 03:31:35','RT-XUHONG-33','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','89863b27-cb3c-4d53-94af-f42161bac3cc','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('d4099ccd-940d-4a0e-883c-a881cb38732c','RT-XUHONG-4','xuhong','2019-10-04 16:25:15',NULL,_binary '\0','xuhong','2019-10-04 16:36:19','RT-XUHONG-4','',NULL,1,2,'bm approved: Yingshi2502','BM Approver please view the budget plan.','f5686d11-0c34-40bd-8798-43ef820ba53d','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('d79b394f-ef87-499b-8636-1cdfeeadda4f','RT-XUHONG-23','xuhong','2019-10-05 03:18:01',NULL,_binary '\0','xuhong','2019-10-05 03:20:36','RT-XUHONG-23','',NULL,1,2,NULL,'BM Approver please view the budget plan.','f5917493-c610-41f9-8833-7b2cdf3c6347','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('dd30e791-a333-4770-95dc-5004fc76bf1c','RT-XUHONG-5','xuhong','2019-10-04 16:36:19',NULL,_binary '\0',NULL,'2019-10-04 16:36:19','RT-XUHONG-5','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','f5686d11-0c34-40bd-8798-43ef820ba53d','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('de8851bf-e131-448f-8e79-5a675244b0fa','RT-XUHONG-18','xuhong','2019-10-05 03:02:42',NULL,_binary '\0',NULL,'2019-10-05 03:02:42','RT-XUHONG-18','',NULL,0,2,'','BM Approver please view the budget plan.','b5eaff66-48da-42eb-897f-2eb8735bed53','dd650097-4b59-4dae-8d6f-eec585813750','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('e5cc4458-3424-4beb-9aa7-f20ac6606786','RT-XUHONG-22','xuhong','2019-10-05 03:08:57',NULL,_binary '\0',NULL,'2019-10-05 03:08:57','RT-XUHONG-22','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','595b2958-d3b6-4514-8818-9e5a379f6eb1','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('e5de04f5-3656-4de1-ada2-c6dac3b094a3','RT-XUHONG-11','xuhong','2019-10-04 17:03:16',NULL,_binary '\0',NULL,'2019-10-04 17:03:17','RT-XUHONG-11','',NULL,0,2,'','BM Approver approved, Function approver please view the budget plan.','71fa566c-6858-402d-9eae-660fc9deddef','f56ad9c8-e92a-43ff-b101-be4cfa20558f','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),('e926ae81-fa94-49eb-b5f5-39d68dca4a6e','RT-ADMIN-17','admin','2019-10-04 19:56:49',NULL,_binary '\0',NULL,'2019-10-04 19:56:49','RT-ADMIN-17','',NULL,0,2,'','BM Approver please view the budget plan.','78f48f1d-41f0-4a81-b863-d7d8852b2b46','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),('f7994577-1143-458b-b8f4-8cf83a6aee9b','RT-YINGSHI2502-9','yingshi2502','2019-10-04 17:01:06',NULL,_binary '\0','yingshi2502','2019-10-04 17:01:26','RT-YINGSHI2502-9','',NULL,2,2,'reject myself','BM Approver please view the budget plan.','92dbf151-bd04-435a-92d9-d7c4ed2c874d','dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750');
/*!40000 ALTER TABLE `approval_for_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_trail_activity`
--

DROP TABLE IF EXISTS `audit_trail_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_trail_activity` (
  `id` int(11) NOT NULL,
  `activity` varchar(255) DEFAULT NULL,
  `time_stamp` datetime DEFAULT NULL,
  `url_visited` varchar(255) DEFAULT NULL,
  `user_uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_trail_activity`
--

LOCK TABLES `audit_trail_activity` WRITE;
/*!40000 ALTER TABLE `audit_trail_activity` DISABLE KEYS */;
INSERT INTO `audit_trail_activity` VALUES (125,'getAllAuditTrailRecordsWithUsername','2019-10-04 17:28:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(126,'getAllCurrencies','2019-10-04 17:28:17',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(127,'getAllCurrencies','2019-10-04 17:29:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(128,'getAllCurrencies','2019-10-04 17:31:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(129,'getAllCurrencies','2019-10-04 17:33:53',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(130,'getAllCurrencies','2019-10-04 17:38:52',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(131,'viewFxRecords','2019-10-04 17:38:57',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(132,'viewFxRecords','2019-10-04 17:39:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(133,'viewFxRecords','2019-10-04 17:40:51',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(134,'getAllCountries','2019-10-04 17:54:30',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(135,'getAllCurrencies','2019-10-04 18:34:42',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(136,'retrieveSimpleCostCentersByUser','2019-10-04 18:34:42',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(137,'getAllCurrencies','2019-10-04 18:38:53',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(138,'retrieveSimpleCostCentersByUser','2019-10-04 18:38:53',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(139,'retrieveSimpleCostCentersByUser','2019-10-04 18:40:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(140,'retrieveSimpleCostCentersByUser','2019-10-04 18:40:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(141,'getAllCurrencies','2019-10-04 18:40:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(142,'retrieveSimpleCostCentersByUser','2019-10-04 18:42:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(143,'getAllCurrencies','2019-10-04 18:42:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(144,'retrieveSimpleCostCentersByUser','2019-10-04 18:43:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(145,'getAllCurrencies','2019-10-04 18:43:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(146,'getAllCurrencies','2019-10-04 18:43:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(147,'retrieveSimpleCostCentersByUser','2019-10-04 18:43:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(148,'getAllCurrencies','2019-10-04 18:44:22',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(149,'getAllCurrencies','2019-10-04 18:44:30',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(150,'retrieveSimpleCostCentersByUser','2019-10-04 18:44:30',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(151,'getAllEmployees','2019-10-04 18:44:38',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(152,'getAllCurrencies','2019-10-04 18:44:44',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(153,'retrieveSimpleCostCentersByUser','2019-10-04 18:44:44',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(154,'getAllCurrencies','2019-10-04 18:46:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(155,'retrieveSimpleCostCentersByUser','2019-10-04 18:46:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(156,'retrieveSimpleCostCentersByUser','2019-10-04 18:47:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(157,'getAllCurrencies','2019-10-04 18:47:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(158,'getAllCurrencies','2019-10-04 18:49:27',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(159,'retrieveSimpleCostCentersByUser','2019-10-04 18:49:27',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(160,'getAllCurrencies','2019-10-04 18:50:44',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(161,'getAllCurrencies','2019-10-04 18:51:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(162,'retrieveSimpleCostCentersByUser','2019-10-04 18:51:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(165,'getAllEmployees','2019-10-04 18:53:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(166,'getAllEmployees','2019-10-04 18:55:22',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(167,'getAllCurrencies','2019-10-04 18:57:39',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(168,'getAllCountries','2019-10-04 18:57:51',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(169,'getAllCountries','2019-10-04 18:57:51',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(170,'getAllCountries','2019-10-04 19:01:09',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(171,'getAllCountries','2019-10-04 19:01:09',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(172,'getAllCountries','2019-10-04 19:03:38',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(173,'getAllCountries','2019-10-04 19:05:16',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(174,'getAllCountries','2019-10-04 19:05:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(175,'getAllCountries','2019-10-04 19:06:45',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(176,'getAllCountries','2019-10-04 19:06:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(177,'getAllCountries','2019-10-04 19:12:45',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(178,'getAllCountries','2019-10-04 19:12:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(179,'getAllCountries','2019-10-04 19:13:09',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(180,'getAllCountries','2019-10-04 19:13:09',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(181,'getAllCountries','2019-10-04 19:13:33',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(182,'getAllCountries','2019-10-04 19:13:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(183,'getAllCountries','2019-10-04 19:14:37',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(184,'getAllCountries','2019-10-04 19:14:37',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(185,'getAllCountries','2019-10-04 19:15:18',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(186,'getAllCountries','2019-10-04 19:15:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(187,'retrieveSimpleCostCentersByUser','2019-10-04 19:15:23',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(188,'getAllCurrencies','2019-10-04 19:15:23',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(189,'getAllCurrencies','2019-10-04 19:15:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(190,'retrieveSimpleCostCentersByUser','2019-10-04 19:15:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(191,'retrieveSimpleCostCentersByUser','2019-10-04 19:16:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(192,'getAllCurrencies','2019-10-04 19:16:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(193,'getAllCurrencies','2019-10-04 19:17:02',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(194,'retrieveSimpleCostCentersByUser','2019-10-04 19:17:02',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(195,'retrieveSimpleCostCentersByUser','2019-10-04 19:17:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(196,'getAllCurrencies','2019-10-04 19:17:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(197,'getAllCurrencies','2019-10-04 19:18:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(198,'retrieveSimpleCostCentersByUser','2019-10-04 19:18:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(199,'getCountryByUuid','2019-10-04 19:32:51',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(201,'getAllCurrencies','2019-10-04 19:44:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(202,'retrieveSimpleCostCentersByUser','2019-10-04 19:44:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(203,'retrieveSimpleCostCentersByUser','2019-10-04 19:49:21',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(204,'getAllCurrencies','2019-10-04 19:49:21',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(205,'getAllCurrencies','2019-10-04 19:53:46',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(206,'retrieveSimpleCostCentersByUser','2019-10-04 19:53:46',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(207,'retrieveSimpleCostCentersByUser','2019-10-04 19:54:12',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(208,'getAllCurrencies','2019-10-04 19:54:12',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(209,'retrieveSimpleCostCentersByUser','2019-10-04 19:54:21',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(210,'getAllCurrencies','2019-10-04 19:54:21',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(211,'retrieveSimpleCostCentersByUser','2019-10-04 19:54:39',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(212,'getAllCurrencies','2019-10-04 19:54:39',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(213,'getAllCurrencies','2019-10-04 19:56:06',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(215,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:08:19',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(216,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:08:38',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(217,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:08:44',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(218,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:10:29',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(219,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:11:29',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(220,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:11:59',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(221,'getAllCurrencies','2019-10-04 22:12:47',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(222,'viewFxRecords','2019-10-04 22:12:52',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(223,'getAllCurrencies','2019-10-04 22:16:00',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(224,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:16:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(225,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:16:23',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(226,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:16:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(227,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:18:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(228,'getAllCurrencies','2019-10-04 22:19:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(229,'viewFxRecords','2019-10-04 22:19:34',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(230,'getAllCurrencies','2019-10-04 22:19:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(231,'getAllCountries','2019-10-04 22:20:02',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(232,'getAllCountries','2019-10-04 22:20:02',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(233,'getAllCountries','2019-10-04 22:20:21',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(234,'getAllCountries','2019-10-04 22:20:21',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(236,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:24:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(237,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:24:34',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(238,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:24:59',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(239,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:25:07',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(240,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:26:12',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(241,'getAllAuditTrailRecordsWithUsername','2019-10-04 22:26:54',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(245,'getAllCountries','2019-10-04 23:05:57',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(246,'getAllCountries','2019-10-04 23:57:20',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(247,'getAllRegions','2019-10-04 23:57:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(248,'getAllOffices','2019-10-04 23:57:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(249,'getAllCountries','2019-10-04 23:57:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(250,'getAllOffices','2019-10-04 23:58:41',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(251,'getAllRegions','2019-10-04 23:58:41',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(252,'getAllCountries','2019-10-04 23:58:41',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(253,'getAllCountries','2019-10-04 23:58:41',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(254,'getAllRegions','2019-10-05 00:00:40',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(255,'getAllCountries','2019-10-05 00:00:40',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(256,'getAllOffices','2019-10-05 00:00:40',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(257,'getAllCountries','2019-10-05 00:00:40',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(258,'getAllCurrencies','2019-10-05 00:17:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(259,'getAllCountries','2019-10-05 00:17:18',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(260,'getAllCountries','2019-10-05 00:26:26',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(261,'getAllRegions','2019-10-05 00:31:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(262,'getAllCountries','2019-10-05 00:31:49',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(263,'getAllOffices','2019-10-05 00:31:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(264,'getAllCountries','2019-10-05 00:31:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(265,'getAllOffices','2019-10-05 00:34:31',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(266,'getAllRegions','2019-10-05 00:34:31',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(267,'getAllCountries','2019-10-05 00:34:31',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(268,'getAllCountries','2019-10-05 00:34:31',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(269,'getAllCountries','2019-10-05 01:27:07',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(270,'getCountryByUuid','2019-10-05 01:28:30',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(271,'getCountryByUuid','2019-10-05 01:44:55',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(272,'getCountryByUuid','2019-10-05 02:02:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(273,'getCountryByUuid','2019-10-05 02:02:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(274,'getCountryByUuid','2019-10-05 02:23:38',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(275,'getCountryByUuid','2019-10-05 02:27:41',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(279,'retrieveSimpleCostCentersByUser','2019-10-05 02:41:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(280,'getAllCurrencies','2019-10-05 02:41:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(281,'getAllCurrencies','2019-10-05 02:41:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(282,'retrieveSimpleCostCentersByUser','2019-10-05 02:41:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(283,'retrieveSimpleCostCentersByUser','2019-10-05 02:48:34',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(284,'getAllCurrencies','2019-10-05 02:48:34',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(285,'getAllCurrencies','2019-10-05 02:48:42',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(286,'retrieveSimpleCostCentersByUser','2019-10-05 02:48:42',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(287,'getAllCurrencies','2019-10-05 02:50:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(288,'retrieveSimpleCostCentersByUser','2019-10-05 02:50:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(289,'getAllCurrencies','2019-10-05 02:52:24',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(290,'retrieveSimpleCostCentersByUser','2019-10-05 02:52:24',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(291,'getAllCurrencies','2019-10-05 02:54:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(292,'retrieveSimpleCostCentersByUser','2019-10-05 02:54:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(293,'retrieveSimpleCostCentersByUser','2019-10-05 02:58:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(294,'getAllCurrencies','2019-10-05 02:58:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(295,'retrieveSimpleCostCentersByUser','2019-10-05 02:58:23',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(296,'getAllCurrencies','2019-10-05 02:58:23',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(297,'getAllCurrencies','2019-10-05 02:58:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(298,'retrieveSimpleCostCentersByUser','2019-10-05 02:58:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(299,'retrieveSimpleCostCentersByUser','2019-10-05 03:00:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(300,'getAllCurrencies','2019-10-05 03:00:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(301,'getAllCurrencies','2019-10-05 03:01:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(302,'retrieveSimpleCostCentersByUser','2019-10-05 03:01:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(303,'getCountryByUuid','2019-10-05 03:03:06',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(304,'retrieveSimpleCostCentersByUser','2019-10-05 03:03:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(305,'getAllCurrencies','2019-10-05 03:03:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(306,'getAllCurrencies','2019-10-05 03:04:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(307,'getAllCurrencies','2019-10-05 03:04:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(308,'getAllCurrencies','2019-10-05 03:04:44',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(311,'retrieveSimpleCostCentersByUser','2019-10-05 03:06:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(312,'getAllCurrencies','2019-10-05 03:06:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(313,'retrieveSimpleCostCentersByUser','2019-10-05 03:06:29',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(314,'getAllCurrencies','2019-10-05 03:06:29',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(315,'getAllCurrencies','2019-10-05 03:08:05',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(316,'getCountryByUuid','2019-10-05 03:10:19',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(317,'getAllCurrencies','2019-10-05 03:16:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(318,'retrieveSimpleCostCentersByUser','2019-10-05 03:16:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(319,'retrieveSimpleCostCentersByUser','2019-10-05 03:16:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(320,'getAllCurrencies','2019-10-05 03:16:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(321,'getCountryByUuid','2019-10-05 03:25:06',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(322,'retrieveSimpleCostCentersByUser','2019-10-05 03:33:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(323,'getAllCurrencies','2019-10-05 03:33:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(324,'retrieveSimpleCostCentersByUser','2019-10-05 03:33:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(325,'getAllCurrencies','2019-10-05 03:33:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(326,'getAllCurrencies','2019-10-05 03:35:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(327,'retrieveSimpleCostCentersByUser','2019-10-05 03:35:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(328,'getAllCurrencies','2019-10-05 03:38:25',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(329,'getAllCurrencies','2019-10-05 03:39:28',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(330,'viewFxRecords','2019-10-05 03:39:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(331,'viewFxRecords','2019-10-05 03:41:46',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(332,'viewFxRecords','2019-10-05 03:42:01',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(333,'viewFxRecords','2019-10-05 03:42:05',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(334,'viewFxRecords','2019-10-05 03:42:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(335,'viewFxRecords','2019-10-05 03:42:12',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(336,'viewFxRecords','2019-10-05 03:42:37',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(337,'viewFxRecords','2019-10-05 03:42:43',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(338,'viewFxRecords','2019-10-05 03:42:46',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(339,'getAllCountries','2019-10-05 03:42:52',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(340,'getAllCountries','2019-10-05 03:42:52',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(341,'getAllEmployees','2019-10-05 03:48:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(342,'getCountryByUuid','2019-10-05 03:51:33',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(343,'getCountryByUuid','2019-10-05 03:55:17',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(344,'getCountryByUuid','2019-10-05 03:55:28',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(345,'getCountryByUuid','2019-10-05 03:55:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(346,'getAllCountries','2019-10-05 03:55:49',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(347,'getAllCountries','2019-10-05 03:55:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(348,'getCountryByUuid','2019-10-05 03:56:42',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(349,'getAllCountries','2019-10-05 03:57:46',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(350,'getAllCountries','2019-10-05 03:57:46',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(351,'getCountryByUuid','2019-10-05 04:01:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(352,'getCountryByUuid','2019-10-05 04:03:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(353,'getCountryByUuid','2019-10-05 04:04:35',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(354,'getCountryByUuid','2019-10-05 04:07:28',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(355,'getCountryByUuid','2019-10-05 04:08:56',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(356,'getCountryByUuid','2019-10-05 04:11:26',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(357,'getCountryByUuid','2019-10-05 04:12:52',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(358,'getAllCountries','2019-10-05 04:12:56',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(359,'getAllCountries','2019-10-05 04:12:56',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(360,'getCountryByUuid','2019-10-05 04:14:31',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(361,'getCountryByUuid','2019-10-05 04:17:57',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(362,'getCountryByUuid','2019-10-05 04:21:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(363,'getCountryByUuid','2019-10-05 04:26:34',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(364,'getCountryByUuid','2019-10-05 04:29:15',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(365,'getCountryByUuid','2019-10-05 04:31:46',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(366,'getCountryByUuid','2019-10-05 04:33:00',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(367,'getAllCurrencies','2019-10-05 04:32:57',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(368,'retrieveSimpleCostCentersByUser','2019-10-05 04:32:57',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(369,'getAllCurrencies','2019-10-05 04:33:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(370,'retrieveSimpleCostCentersByUser','2019-10-05 04:33:18',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(371,'retrieveSimpleCostCentersByUser','2019-10-05 04:36:54',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(372,'getAllCurrencies','2019-10-05 04:36:54',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(373,'getCountryByUuid','2019-10-05 04:37:13',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(374,'getCountryByUuid','2019-10-05 04:40:27',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(375,'getCountryByUuid','2019-10-05 04:41:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(376,'getAllCurrencies','2019-10-05 04:43:59',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(377,'retrieveSimpleCostCentersByUser','2019-10-05 04:43:59',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(378,'getAllCurrencies','2019-10-05 04:43:59',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(379,'retrieveSimpleCostCentersByUser','2019-10-05 04:58:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(380,'getAllCurrencies','2019-10-05 04:58:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(381,'getAllCurrencies','2019-10-05 05:00:47',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(382,'getAllCurrencies','2019-10-05 05:00:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(383,'retrieveSimpleCostCentersByUser','2019-10-05 05:00:48',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(384,'getAllCurrencies','2019-10-05 05:01:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(385,'retrieveSimpleCostCentersByUser','2019-10-05 05:01:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(386,'getAllCurrencies','2019-10-05 05:01:10',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(387,'getAllAuditTrailRecordsWithUsername','2019-10-05 05:05:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(388,'getCountryByUuid','2019-10-05 05:09:36',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(389,'getCountryByUuid','2019-10-05 05:14:36',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(390,'getCountryByUuid','2019-10-05 05:29:45',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(391,'getCountryByUuid','2019-10-05 05:30:09',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(392,'getAllCurrencies','2019-10-05 05:40:52',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(393,'retrieveSimpleCostCentersByUser','2019-10-05 05:40:52',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(394,'getCountryByUuid','2019-10-05 05:46:27',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(395,'getCountryByUuid','2019-10-05 05:48:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(396,'getCountryByUuid','2019-10-05 05:48:56',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(401,'getAllCountries','2019-10-05 06:14:32',NULL,'f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(402,'getAllCountries','2019-10-05 06:14:32',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(403,'getCountryByUuid','2019-10-05 06:19:27',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(404,'retrieveSimpleCostCentersByUser','2019-10-05 06:19:56',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(405,'getAllCurrencies','2019-10-05 06:19:56',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(406,'getCountryByUuid','2019-10-05 06:21:35',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(407,'getCountryByUuid','2019-10-05 06:25:54',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(408,'retrieveSimpleCostCentersByUser','2019-10-05 06:27:12',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(409,'getAllCurrencies','2019-10-05 06:27:12',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(410,'getCountryByUuid','2019-10-05 06:29:27',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(411,'getCountryByUuid','2019-10-05 06:31:49',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(412,'retrieveSimpleCostCentersByUser','2019-10-05 06:33:54',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(413,'getAllCurrencies','2019-10-05 06:33:54',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(414,'getCountryByUuid','2019-10-05 06:35:14',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(415,'getAllCurrencies','2019-10-05 06:35:01',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(416,'retrieveSimpleCostCentersByUser','2019-10-05 06:35:01',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(417,'getAllCurrencies','2019-10-05 06:35:36',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(418,'getCountryByUuid','2019-10-05 06:36:20',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(420,'getCountryByUuid','2019-10-05 06:38:44',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(421,'getAllCurrencies','2019-10-05 06:39:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(422,'retrieveSimpleCostCentersByUser','2019-10-05 06:39:04',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(423,'retrieveSimpleCostCentersByUser','2019-10-05 06:39:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(424,'getAllCurrencies','2019-10-05 06:39:08',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(425,'getAllCurrencies','2019-10-05 06:39:43',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(426,'getAllCurrencies','2019-10-05 06:40:00',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(427,'retrieveSimpleCostCentersByUser','2019-10-05 06:40:00',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(428,'retrieveSimpleCostCentersByUser','2019-10-05 06:40:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(429,'getAllCurrencies','2019-10-05 06:40:16',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(430,'getAllCurrencies','2019-10-05 06:40:30',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(431,'retrieveSimpleCostCentersByUser','2019-10-05 06:40:30',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(432,'getAllCurrencies','2019-10-05 06:41:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(433,'retrieveSimpleCostCentersByUser','2019-10-05 06:41:03',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(434,'getAllCurrencies','2019-10-05 06:41:29',NULL,'dd650097-4b59-4dae-8d6f-eec585813750'),(435,'retrieveSimpleCostCentersByUser','2019-10-05 06:41:29',NULL,'dd650097-4b59-4dae-8d6f-eec585813750');
/*!40000 ALTER TABLE `audit_trail_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bjf`
--

DROP TABLE IF EXISTS `bjf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bjf` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `bjf_type` int(11) DEFAULT NULL,
  `budget_approval_ticket_code` varchar(255) DEFAULT NULL,
  `business_approval_ticket_code` varchar(255) DEFAULT NULL,
  `cost_center_code` varchar(255) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `justification` varchar(255) DEFAULT NULL,
  `ongoing_cost` decimal(19,2) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `project_cost` decimal(19,2) DEFAULT NULL,
  `purchase_orders_number` varchar(255) DEFAULT NULL,
  `total_amt` decimal(19,2) DEFAULT NULL,
  `merchandise_id` varchar(36) DEFAULT NULL,
  `employee_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d8l7uscngvqvdlaww6h26tve1` (`code`),
  KEY `FKmu14d5gihqwhbbfho7x8vsrr7` (`merchandise_id`),
  KEY `FKivijsqhltk0dwadefqjvkfgcn` (`employee_id`),
  CONSTRAINT `FKivijsqhltk0dwadefqjvkfgcn` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKmu14d5gihqwhbbfho7x8vsrr7` FOREIGN KEY (`merchandise_id`) REFERENCES `merchandise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bjf`
--

LOCK TABLES `bjf` WRITE;
/*!40000 ALTER TABLE `bjf` DISABLE KEYS */;
/*!40000 ALTER TABLE `bjf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_category`
--

DROP TABLE IF EXISTS `budget_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_category` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `country_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7d2ase71tojur7vc3h0y2n78q` (`code`),
  KEY `FK62qcve2yvwi8yvhjmfv4tj571` (`country_id`),
  CONSTRAINT `FK62qcve2yvwi8yvhjmfv4tj571` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_category`
--

LOCK TABLES `budget_category` WRITE;
/*!40000 ALTER TABLE `budget_category` DISABLE KEYS */;
INSERT INTO `budget_category` VALUES ('07b971c0-ee31-4b4a-a3b4-c4eb895c6920','CAT-MISCELLANEOUS-2','yingshi2502','2019-10-03 09:22:00','APAC-SG-MISCELLANEOUS',_binary '\0',NULL,'2019-10-03 09:22:01','Miscellaneous','GRANT,all_user',2,'9b0c3c04-7213-4299-9220-a67b31e342dd'),('2f34164e-e5d3-4f7b-a484-e7e083ce3063','CAT-OFFICE_SUPPLIES-5','yingshi2502','2019-10-04 14:11:18','APAC-HK-OFFICE_SUPPLIES',_binary '\0',NULL,'2019-10-04 14:11:19','Office Supplies','GRANT,all_user',5,'092b323e-81d5-4329-8471-4fd9f389cc46'),('4212254d-3905-4344-bf44-73f9b224cbd2','CAT-TELECOM-3','yingshi2502','2019-10-03 09:22:11','APAC-SG-TELECOM',_binary '\0',NULL,'2019-10-03 09:22:11','Telecom','GRANT,all_user',3,'9b0c3c04-7213-4299-9220-a67b31e342dd'),('43b9dc9d-9985-452e-9993-6544b7bf9545','CAT-TELECOM-7','yingshi2502','2019-10-04 14:12:03','APAC-HK-TELECOM',_binary '\0',NULL,'2019-10-04 14:12:03','Telecom','GRANT,all_user',7,'092b323e-81d5-4329-8471-4fd9f389cc46'),('7e093809-d025-476f-9642-d681ae59fe80','CAT-MISCELLANEOUS-6','yingshi2502','2019-10-04 14:11:59','APAC-HK-MISCELLANEOUS',_binary '\0',NULL,'2019-10-04 14:11:59','Miscellaneous','GRANT,all_user',6,'092b323e-81d5-4329-8471-4fd9f389cc46'),('8ad38a94-402c-41b2-96be-a75822cc619e','CAT-IT_ASSET-8','yingshi2502','2019-10-04 14:12:08','APAC-HK-IT_ASSET',_binary '\0',NULL,'2019-10-04 14:12:08','IT Asset','GRANT,all_user',8,'092b323e-81d5-4329-8471-4fd9f389cc46'),('9b39fd3d-8edc-4262-9bca-3907b713672d','CAT-UPDATE_CAT1-9','Viet','2019-10-05 06:15:40','APAC-SG-UPDATE_CAT1',_binary '','Viet','2019-10-05 06:16:14','update cat1','GRANT,all_user',9,'9b0c3c04-7213-4299-9220-a67b31e342dd'),('d168c0b6-f728-4093-8d93-fa667ddf601b','CAT-IT_ASSET-1','yingshi2502','2019-10-03 09:21:35','APAC-SG-IT_ASSET',_binary '\0',NULL,'2019-10-03 09:21:35','IT Asset','GRANT,all_user',1,'9b0c3c04-7213-4299-9220-a67b31e342dd'),('dc2512f4-dd4f-4368-87f0-8d931b4f291f','CAT-OFFICE_SUPPLIES-4','yingshi2502','2019-10-03 09:22:18','APAC-SG-OFFICE_SUPPLIES',_binary '\0',NULL,'2019-10-03 09:22:18','Office Supplies','GRANT,all_user',4,'9b0c3c04-7213-4299-9220-a67b31e342dd');
/*!40000 ALTER TABLE `budget_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_sub1`
--

DROP TABLE IF EXISTS `budget_sub1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_sub1` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `budget_category_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eb0vnt5ghwvuit9nmmug6x5nw` (`code`),
  KEY `FK6ly7ipqbifnl9k2mts26c5gml` (`budget_category_id`),
  CONSTRAINT `FK6ly7ipqbifnl9k2mts26c5gml` FOREIGN KEY (`budget_category_id`) REFERENCES `budget_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_sub1`
--

LOCK TABLES `budget_sub1` WRITE;
/*!40000 ALTER TABLE `budget_sub1` DISABLE KEYS */;
INSERT INTO `budget_sub1` VALUES ('05344981-db18-4066-ae2b-98b9d69d93c9','CATS1-HK-TELECOM-VOICE-11','Viet','2019-10-05 04:12:20','APAC-HK-TELECOM-HK-TELECOM-VOICE',_binary '\0','Viet','2019-10-05 04:12:20','HK-Telecom-Voice','GRANT,all_user',11,'43b9dc9d-9985-452e-9993-6544b7bf9545'),('09c64b0b-3a89-47cb-acd4-4332c6f473f5','CATS1-PERONAL_DESKTOP-7','yingshi2502','2019-10-03 09:32:05','APAC-SG-IT_ASSET-PERONAL_DESKTOP',_binary '\0','yingshi2502','2019-10-03 09:32:05','Peronal Desktop','GRANT,all_user',7,'d168c0b6-f728-4093-8d93-fa667ddf601b'),('187d273e-8b8e-4d26-a78e-eda9c6c575d6','CATS1-TRAVEL-8','yingshi2502','2019-10-04 14:14:47','APAC-HK-MISCELLANEOUS-TRAVEL',_binary '\0','yingshi2502','2019-10-04 14:14:47','Travel','GRANT,all_user',8,'7e093809-d025-476f-9642-d681ae59fe80'),('3df5d8c1-8f53-49e7-9a7b-c3c953a06c64','CATS1-UPDATE_SUB1-13','Viet','2019-10-05 06:16:40','APAC-SG-TELECOM-UPDATE_SUB1',_binary '','Viet','2019-10-05 06:17:33','update sub1','GRANT,all_user',13,'4212254d-3905-4344-bf44-73f9b224cbd2'),('69d3e92d-19a7-466a-a115-c3b13d3e939f','CATS1-TRAINING-9','yingshi2502','2019-10-04 14:14:51','APAC-HK-MISCELLANEOUS-TRAINING',_binary '\0','yingshi2502','2019-10-04 14:14:51','Training','GRANT,all_user',9,'7e093809-d025-476f-9642-d681ae59fe80'),('6a38bf6a-9d26-4d59-8277-5c77cdc6a7b9','CATS1-TRAVEL-5','yingshi2502','2019-10-03 09:30:12','APAC-SG-MISCELLANEOUS-TRAVEL',_binary '\0','yingshi2502','2019-10-03 09:30:12','Travel','GRANT,all_user',5,'07b971c0-ee31-4b4a-a3b4-c4eb895c6920'),('6aceb5b1-8ec2-4e72-8a65-98ccba379a74','CATS1-SCREENING-3','yingshi2502','2019-10-03 09:27:18','APAC-SG-IT_ASSET-SCREENING',_binary '\0','yingshi2502','2019-10-03 09:27:18','Screening','GRANT,all_user',3,'d168c0b6-f728-4093-8d93-fa667ddf601b'),('7a5797e5-47df-4a6a-a8ea-e9b7d9af2381','CATS1-HK_TELECOM_-_DATA-10','Viet','2019-10-05 04:12:07','APAC-HK-TELECOM-HK_TELECOM_-_DATA',_binary '\0','Viet','2019-10-05 04:12:07','HK Telecom - Data','GRANT,all_user',10,'43b9dc9d-9985-452e-9993-6544b7bf9545'),('ae918773-80a8-4940-aaab-2ecaa88b87f1','CATS1-ACCESS_CONTROL-4','yingshi2502','2019-10-03 09:27:40','APAC-SG-IT_ASSET-ACCESS_CONTROL',_binary '\0','yingshi2502','2019-10-03 09:27:40','Access Control','GRANT,all_user',4,'d168c0b6-f728-4093-8d93-fa667ddf601b'),('b2973748-f706-4373-b330-67cfbc59f394','CATS1-CHAIR-12','Viet','2019-10-05 04:13:22','APAC-HK-OFFICE_SUPPLIES-CHAIR',_binary '\0','Viet','2019-10-05 04:13:22','Chair','GRANT,all_user',12,'2f34164e-e5d3-4f7b-a484-e7e083ce3063'),('bce7a2db-836c-4ec5-ace5-b71a6a5fae21','CATS1-DATA-1','yingshi2502','2019-10-03 09:24:57','APAC-SG-TELECOM-DATA',_binary '\0','yingshi2502','2019-10-03 09:24:57','Data','GRANT,all_user',1,'4212254d-3905-4344-bf44-73f9b224cbd2'),('ddd75fd1-9d70-4c14-8ce2-ebf29f7e9e84','CATS1-TRAINING-6','yingshi2502','2019-10-03 09:30:16','APAC-SG-MISCELLANEOUS-TRAINING',_binary '\0','yingshi2502','2019-10-03 09:30:16','Training','GRANT,all_user',6,'07b971c0-ee31-4b4a-a3b4-c4eb895c6920'),('ea469685-169c-496e-aed1-eede8f1ac5e2','CATS1-VOICE-2','yingshi2502','2019-10-03 09:25:02','APAC-SG-TELECOM-VOICE',_binary '\0','yingshi2502','2019-10-03 09:25:02','Voice','GRANT,all_user',2,'4212254d-3905-4344-bf44-73f9b224cbd2');
/*!40000 ALTER TABLE `budget_sub1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_sub2`
--

DROP TABLE IF EXISTS `budget_sub2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_sub2` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `budget_sub1_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fievgv8avxs7w9uat8abccj3q` (`code`),
  KEY `FKte9lr6n0jm7qc2it73cwsaogy` (`budget_sub1_id`),
  CONSTRAINT `FKte9lr6n0jm7qc2it73cwsaogy` FOREIGN KEY (`budget_sub1_id`) REFERENCES `budget_sub1` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_sub2`
--

LOCK TABLES `budget_sub2` WRITE;
/*!40000 ALTER TABLE `budget_sub2` DISABLE KEYS */;
INSERT INTO `budget_sub2` VALUES ('0eb8ab4c-9090-45cb-873b-f5cebb874e6e','CATS2-PLANNED_TRIPS-9','yingshi2502','2019-10-03 09:30:39','APAC-SG-MISCELLANEOUS-TRAVEL-PLANNED_TRIPS',_binary '\0','yingshi2502','2019-10-03 09:30:39','Planned Trips','GRANT,all_user',9,'6a38bf6a-9d26-4d59-8277-5c77cdc6a7b9'),('247d1318-1be6-4865-aac1-06eb8093182d','CATS2-HEADSET-3','yingshi2502','2019-10-03 09:26:08','APAC-SG-TELECOM-VOICE-HEADSET',_binary '\0','yingshi2502','2019-10-03 09:26:08','Headset','GRANT,all_user',3,'ea469685-169c-496e-aed1-eede8f1ac5e2'),('40a5938a-5c4e-461f-944b-ae677fb98caa','CATS2-WINDOWS_PC-13','yingshi2502','2019-10-03 09:32:19','APAC-SG-IT_ASSET-PERONAL_DESKTOP-WINDOWS_PC',_binary '\0','yingshi2502','2019-10-03 09:32:19','Windows PC','GRANT,all_user',13,'09c64b0b-3a89-47cb-acd4-4332c6f473f5'),('4b4bdfdd-689c-46d9-99a3-05590596dc3b','CATS2-DESKTOP_MONITOR-15','yingshi2502','2019-10-03 09:40:09','APAC-SG-IT_ASSET-PERONAL_DESKTOP-DESKTOP_MONITOR',_binary '\0','yingshi2502','2019-10-03 09:40:10','Desktop Monitor','GRANT,all_user',15,'09c64b0b-3a89-47cb-acd4-4332c6f473f5'),('4d86c134-7181-4317-ac26-9e808c34d9bd','CATS2-MAC-14','yingshi2502','2019-10-03 09:32:29','APAC-SG-IT_ASSET-PERONAL_DESKTOP-MAC',_binary '\0','yingshi2502','2019-10-03 09:32:29','Mac','GRANT,all_user',14,'09c64b0b-3a89-47cb-acd4-4332c6f473f5'),('536f4b4b-8133-4a6c-86ba-9abba071fee8','CATS2-DATA_LINK-1','yingshi2502','2019-10-03 09:25:34','APAC-SG-TELECOM-DATA-DATA_LINK',_binary '\0','yingshi2502','2019-10-03 09:25:34','Data Link','GRANT,all_user',1,'bce7a2db-836c-4ec5-ace5-b71a6a5fae21'),('5b8753c3-6827-41f1-9b57-d7a0a58a6dc5','CATS2-AD-HOC_TRAVEL-10','yingshi2502','2019-10-03 09:30:46','APAC-SG-MISCELLANEOUS-TRAVEL-AD-HOC_TRAVEL',_binary '\0','yingshi2502','2019-10-03 09:30:46','Ad-hoc Travel','GRANT,all_user',10,'6a38bf6a-9d26-4d59-8277-5c77cdc6a7b9'),('758b89f5-d8bb-44aa-8b07-d593abc63595','CATS2-PLANNED_TRAINING-12','yingshi2502','2019-10-03 09:31:06','APAC-SG-MISCELLANEOUS-TRAINING-PLANNED_TRAINING',_binary '\0','yingshi2502','2019-10-03 09:31:06','Planned Training','GRANT,all_user',12,'ddd75fd1-9d70-4c14-8ce2-ebf29f7e9e84'),('84ba63ff-1a69-4f76-8c25-b32fe909d381','CATS2-DATA_WIRE-2','yingshi2502','2019-10-03 09:25:54','APAC-SG-TELECOM-DATA-DATA_WIRE',_binary '\0','yingshi2502','2019-10-03 09:25:54','Data Wire','GRANT,all_user',2,'bce7a2db-836c-4ec5-ace5-b71a6a5fae21'),('991c40d1-c60c-497f-b069-5c5715d40af4','CATS2-COMPUTER_CHAIR-18','Viet','2019-10-05 04:13:55','APAC-HK-OFFICE_SUPPLIES-CHAIR-COMPUTER_CHAIR',_binary '\0','Viet','2019-10-05 04:13:55','Computer Chair','GRANT,all_user',18,'b2973748-f706-4373-b330-67cfbc59f394'),('a6a738f7-923d-4228-8669-e51d1ed835a5','CATS2-HEADSET-16','Viet','2019-10-05 04:12:35','APAC-HK-TELECOM-HK-TELECOM-VOICE-HEADSET',_binary '\0','Viet','2019-10-05 04:12:35','Headset','GRANT,all_user',16,'05344981-db18-4066-ae2b-98b9d69d93c9'),('ae502fca-91e1-4f43-8caf-474c0182e6c3','CATS2-FINGER_RECOGNITION-8','yingshi2502','2019-10-03 09:29:46','APAC-SG-IT_ASSET-ACCESS_CONTROL-FINGER_RECOGNITION',_binary '\0','yingshi2502','2019-10-03 09:29:46','Finger Recognition','GRANT,all_user',8,'ae918773-80a8-4940-aaab-2ecaa88b87f1'),('b3c18021-243d-484e-a5c8-44921a98e747','CATS2-PHONE-4','yingshi2502','2019-10-03 09:26:43','APAC-SG-TELECOM-VOICE-PHONE',_binary '\0','yingshi2502','2019-10-03 09:26:43','Phone','GRANT,all_user',4,'ea469685-169c-496e-aed1-eede8f1ac5e2'),('bdcc8900-ce2a-4b78-9fb8-41002896dbe1','CATS2-AD-HOC_TRAINING-11','yingshi2502','2019-10-03 09:30:57','APAC-SG-MISCELLANEOUS-TRAINING-AD-HOC_TRAINING',_binary '\0','yingshi2502','2019-10-03 09:30:57','Ad-hoc Training','GRANT,all_user',11,'ddd75fd1-9d70-4c14-8ce2-ebf29f7e9e84'),('bf88e9d9-d51d-4094-a2fd-ca96611bbbb9','CATS2-CARD_READER-7','yingshi2502','2019-10-03 09:28:37','APAC-SG-IT_ASSET-ACCESS_CONTROL-CARD_READER',_binary '\0','yingshi2502','2019-10-03 09:28:37','Card Reader','GRANT,all_user',7,'ae918773-80a8-4940-aaab-2ecaa88b87f1'),('dfbd7a86-7894-4f68-b198-0b46cd57bcaf','CATS2-DATA_LINK-17','Viet','2019-10-05 04:13:11','APAC-HK-TELECOM-HK_TELECOM_-_DATA-DATA_LINK',_binary '\0','Viet','2019-10-05 04:13:11','Data Link','GRANT,all_user',17,'7a5797e5-47df-4a6a-a8ea-e9b7d9af2381'),('e0dd0538-d598-445a-b49a-223df9be6a10','CATS2-PROJECTOR-5','yingshi2502','2019-10-03 09:28:04','APAC-SG-IT_ASSET-SCREENING-PROJECTOR',_binary '\0','yingshi2502','2019-10-03 09:28:04','Projector','GRANT,all_user',5,'6aceb5b1-8ec2-4e72-8a65-98ccba379a74'),('e8b0f0e9-6a71-4b0e-8f73-71524d9e03f3','CATS2-KITCHEN_CHAIR-19','Viet','2019-10-05 04:14:12','APAC-HK-OFFICE_SUPPLIES-CHAIR-KITCHEN_CHAIR',_binary '\0','Viet','2019-10-05 04:14:12','Kitchen Chair','GRANT,all_user',19,'b2973748-f706-4373-b330-67cfbc59f394'),('eb8de338-ff2e-4f9e-bb40-e8a80d4ebdce','CATS2-REMOTE_DESKTOP-6','yingshi2502','2019-10-03 09:28:18','APAC-SG-IT_ASSET-SCREENING-REMOTE_DESKTOP',_binary '\0','yingshi2502','2019-10-03 09:28:19','Remote Desktop','GRANT,all_user',6,'6aceb5b1-8ec2-4e72-8a65-98ccba379a74'),('f4d1c1d2-0269-4f06-8e76-ed372d556bb0','CATS2-SUB2-20','Viet','2019-10-05 06:17:05','APAC-SG-TELECOM-UPDATE_SUB1-SUB2',_binary '','Viet','2019-10-05 06:17:20','sub2','GRANT,all_user',20,'3df5d8c1-8f53-49e7-9a7b-c3c953a06c64');
/*!40000 ALTER TABLE `budget_sub2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_function`
--

DROP TABLE IF EXISTS `company_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_function` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `offices_code_of_function` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gteuu67bbl23ik33mxovpdixo` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_function`
--

LOCK TABLES `company_function` WRITE;
/*!40000 ALTER TABLE `company_function` DISABLE KEYS */;
INSERT INTO `company_function` VALUES ('28b84501-6590-409a-8002-c39258193f67','RM','admin','2019-10-03 16:57:57',NULL,_binary '\0',NULL,NULL,'Risk Management','',4,''),('37fdd5aa-25fd-42f2-9736-d226f6c62363','TECH','admin','2019-10-03 16:55:20',NULL,_binary '\0',NULL,NULL,'Technology','',1,''),('65fff968-a5be-49e9-90a4-6f33928b04da','FIN','admin','2019-10-03 16:57:29',NULL,_binary '\0',NULL,NULL,'Finance','',3,''),('b6b0dcb9-0a60-43fc-b464-d1634434c27c','HR','admin','2019-10-03 16:56:52',NULL,_binary '\0',NULL,NULL,'Human Resource','',2,'');
/*!40000 ALTER TABLE `company_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `contract_status` int(11) DEFAULT NULL,
  `contract_term` varchar(255) DEFAULT NULL,
  `contract_type` int(11) DEFAULT NULL,
  `cpg_review_alert_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `notice_days_to_exit` int(11) DEFAULT NULL,
  `purchase_type` int(11) DEFAULT NULL,
  `renewal_start_date` date DEFAULT NULL,
  `spend_type` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `total_contract_value` int(11) DEFAULT NULL,
  `employee_incharge` varchar(36) DEFAULT NULL,
  `team_id` varchar(36) DEFAULT NULL,
  `vendor_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1iouaf2huua6pv0ogbbm62bfe` (`code`),
  KEY `FKbli1w406nio06vc4230pivsht` (`employee_incharge`),
  KEY `FKbn635wl9n66gu3nbou9r0bvey` (`team_id`),
  KEY `FKshfkbo84dmjtff60xrwjk6rk9` (`vendor_id`),
  CONSTRAINT `FKbli1w406nio06vc4230pivsht` FOREIGN KEY (`employee_incharge`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKbn635wl9n66gu3nbou9r0bvey` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FKshfkbo84dmjtff60xrwjk6rk9` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES ('63ae43d7-0e85-4f47-9ab0-4cb63b5e0fa8','CONTRACT-5','admin','2019-10-03 16:11:46',NULL,_binary '\0','yingshi2502','2019-10-04 18:50:02','contract-SONY','GRANT,all_user',5,0,'36 months',1,'2019-10-23','2022-11-14',10,1,'2019-11-06','DummyType','2019-11-14',60000,'dd650097-4b59-4dae-8d6f-eec585813750','bcfc9d4a-e5bc-11e9-8b11-00155d03b61a','2f9f20d3-715a-4905-92d8-8c33a97ff06b'),('65fc16cb-1a10-4efe-a4b5-1f0baf224128','CONTRACT-7','yingshi2502','2019-10-04 18:47:03',NULL,_binary '\0','yingshi2502','2019-10-04 18:47:05','Contract-NUS','GRANT,all_user',7,0,'12 months',1,'2019-10-29','2020-10-15',10,0,'2019-10-21','spend type','2019-10-15',6000,'dd650097-4b59-4dae-8d6f-eec585813750','eddad274-e5bc-11e9-8b11-00155d03b61a','2aaa5be8-f9cd-4674-bb8f-b8c74eeda21a'),('a4a76a4f-72d3-4a0b-bb34-b6d84ac8417e','CONTRACT-1','admin','2019-10-03 16:06:49',NULL,_binary '\0','yingshi2502','2019-10-04 12:46:21','contract-Singtel','GRANT,all_user',1,0,'12 months',1,'2019-10-09','2020-11-14',10,1,'2019-10-18','DummyType','2019-11-14',10000,'dd650097-4b59-4dae-8d6f-eec585813750','6b5a89b3-e5bc-11e9-8b11-00155d03b61a','88554bc4-6eff-4466-8efc-64ffba3a2e17'),('d67c3ed3-88b7-4475-bcb6-4e25a76881a3','CONTRACT-3','admin','2019-10-03 16:09:49',NULL,_binary '\0','yingshi2502','2019-10-04 18:54:04','contract-Dell','GRANT,all_user',3,1,'24 months',1,'2019-10-16','2021-11-14',10,1,'2019-10-29','DummyType','2019-11-14',60000,'dd650097-4b59-4dae-8d6f-eec585813750','6b5a89b3-e5bc-11e9-8b11-00155d03b61a','b81c911c-839a-477f-bc78-5f2566fa4492'),('e40b4c68-fae0-4b5c-8f7b-ffc48d771717','CONTRACT-6','admin','2019-10-03 16:12:26',NULL,_binary '\0','yingshi2502','2019-10-04 18:48:45','contract-CISCO','GRANT,all_user',6,0,'12 months',2,'2019-10-29','2020-11-14',10,1,'2019-10-30','DummyType','2019-11-14',60000,'dd650097-4b59-4dae-8d6f-eec585813750','bcfc9d4a-e5bc-11e9-8b11-00155d03b61a','7a299ce2-0cd3-4c9c-975b-46fb132e5085'),('e577398e-044d-4de6-9b78-82dd5286d54b','CONTRACT-2','admin','2019-10-03 16:08:54',NULL,_binary '\0','yingshi2502','2019-10-04 18:54:25','contract-Apple','GRANT,all_user',2,0,'12 months',0,'2019-10-13','2020-11-14',10,1,'2019-10-23','DummyType','2019-11-14',80000,'dd650097-4b59-4dae-8d6f-eec585813750','6b5a89b3-e5bc-11e9-8b11-00155d03b61a','154aec3b-7eef-4f74-bc20-268605ccfd11'),('ef0b27c3-354a-4ffd-ab57-c83b2c165542','CONTRACT-4','admin','2019-10-03 16:11:05',NULL,_binary '\0','yingshi2502','2019-10-04 18:54:33','contract-Samsung','GRANT,all_user',4,2,'24 months',2,'2019-10-29','2021-11-14',10,1,'2019-11-09','DummyType','2019-11-14',60000,'dd650097-4b59-4dae-8d6f-eec585813750','bcfc9d4a-e5bc-11e9-8b11-00155d03b61a','08ad83b4-5d10-481d-8704-03762f2c4a51');
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_line`
--

DROP TABLE IF EXISTS `contract_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract_line` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `merchandise_code` varchar(255) NOT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `contract` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9vs53x6qeg6ciwdomcme22tvi` (`code`),
  KEY `FKcjy4w97femsj1xetesipboq2a` (`contract`),
  CONSTRAINT `FKcjy4w97femsj1xetesipboq2a` FOREIGN KEY (`contract`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_line`
--

LOCK TABLES `contract_line` WRITE;
/*!40000 ALTER TABLE `contract_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost_center`
--

DROP TABLE IF EXISTS `cost_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cost_center` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `bm_approver_id` varchar(36) DEFAULT NULL,
  `ccmanager` varchar(36) DEFAULT NULL,
  `function_approver_id` varchar(36) DEFAULT NULL,
  `team_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ic26x9vlauggmkfmj67guoly9` (`code`),
  KEY `FKaqbkbt1t1c8vqop3glixxj686` (`bm_approver_id`),
  KEY `FK14aggjbmbps64ruh175n62jbl` (`ccmanager`),
  KEY `FK3i3l46mgi2p4x52tfmk6916i4` (`function_approver_id`),
  KEY `FK6s7t4jjcdmx5c7bb78jya4jnd` (`team_id`),
  CONSTRAINT `FK14aggjbmbps64ruh175n62jbl` FOREIGN KEY (`ccmanager`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK3i3l46mgi2p4x52tfmk6916i4` FOREIGN KEY (`function_approver_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK6s7t4jjcdmx5c7bb78jya4jnd` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FKaqbkbt1t1c8vqop3glixxj686` FOREIGN KEY (`bm_approver_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost_center`
--

LOCK TABLES `cost_center` WRITE;
/*!40000 ALTER TABLE `cost_center` DISABLE KEYS */;
INSERT INTO `cost_center` VALUES ('5e85591f-e71e-11e9-8d2a-00155d03b61a','CC314-SGFIN-ACCT-S','admin','2019-10-05 11:15:23',NULL,_binary '\0',NULL,NULL,'SG-FIN-ACCT-STAFF','',6,'dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f','0812bae7-e5bd-11e9-8b11-00155d03b61a'),('62cb9144-e5be-11e9-8b11-00155d03b61a','CC01HK0DB','admin','2019-10-03 17:15:47',NULL,_binary '\0',NULL,NULL,'HKDB-CC-Device','',5,'dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f','bcfc9d4a-e5bc-11e9-8b11-00155d03b61a'),('8a3e7627-e5bd-11e9-8b11-00155d03b61a','CC04-SGGCP-S','admin','2019-10-03 17:09:44',NULL,_binary '\0',NULL,NULL,'GCP-SG-Staff CC','',1,'dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f','6b5a89b3-e5bc-11e9-8b11-00155d03b61a'),('a1e9e434-e5bd-11e9-8b11-00155d03b61a','CC1235-SGGCP-PMT','admin','2019-10-03 17:10:23',NULL,_binary '\0',NULL,NULL,'GCP-SG-PMT CC','',2,'dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f','6b5a89b3-e5bc-11e9-8b11-00155d03b61a'),('bc6a930d-e5bd-11e9-8b11-00155d03b61a','CC02-SGHR-S','admin','2019-10-03 17:11:08',NULL,_binary '\0',NULL,NULL,'HRCampus-SG-STAFF','',3,'dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f','eddad274-e5bc-11e9-8b11-00155d03b61a'),('da681cda-e5bd-11e9-8b11-00155d03b61a','CC301-SGHR-T','admin','2019-10-03 17:11:58',NULL,_binary '\0',NULL,NULL,'HRCampus-SG-Travel','',4,'dd650097-4b59-4dae-8d6f-eec585813750','dd650097-4b59-4dae-8d6f-eec585813750','f56ad9c8-e92a-43ff-b101-be4cfa20558f','eddad274-e5bc-11e9-8b11-00155d03b61a');
/*!40000 ALTER TABLE `cost_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `region_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5s4ptnuqtd24d4p9au2rv53qm` (`code`),
  KEY `FKs3bda8801uhqtttuaur9r6eic` (`region_id`),
  CONSTRAINT `FKs3bda8801uhqtttuaur9r6eic` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES ('092b323e-81d5-4329-8471-4fd9f389cc46','HK',NULL,'2019-10-03 08:40:03','APAC-HK',_binary '\0',NULL,'2019-10-03 08:40:03','Hong Kong','',4,NULL,'3eda022c-151a-447f-8cf9-0f885044c6c0'),('14f645e8-7893-40b4-b3e6-3d555aab9d25','CA',NULL,'2019-10-03 08:39:42','AMER-CA',_binary '\0',NULL,'2019-10-03 08:39:42','Canada','',2,NULL,'0e9fd28b-5a48-4d26-bb6c-b6a2836aaf8b'),('93d723dd-ddb5-4834-b2b4-673314d88df8','VN',NULL,'2019-10-03 08:40:52','APAC-VN',_binary '\0',NULL,'2019-10-03 08:40:52','Vietnam','',6,NULL,'3eda022c-151a-447f-8cf9-0f885044c6c0'),('9b0c3c04-7213-4299-9220-a67b31e342dd','SG',NULL,'2019-10-03 08:40:13','APAC-SG',_binary '\0',NULL,'2019-10-03 08:40:13','Singapore','',5,NULL,'3eda022c-151a-447f-8cf9-0f885044c6c0'),('ec719fce-e236-4d5e-b136-868351527d01','CN',NULL,'2019-10-03 08:39:57','APAC-CN',_binary '\0',NULL,'2019-10-03 08:39:57','China','',3,NULL,'3eda022c-151a-447f-8cf9-0f885044c6c0'),('f970de5b-d9f8-432a-b626-702b4076b9a5','US',NULL,'2019-10-03 08:39:26','AMER-US',_binary '\0',NULL,'2019-10-03 08:39:26','United States of America','',1,NULL,'0e9fd28b-5a48-4d26-bb6c-b6a2836aaf8b');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country_function`
--

DROP TABLE IF EXISTS `country_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_function` (
  `country_id` varchar(36) NOT NULL,
  `function_id` varchar(36) NOT NULL,
  KEY `FK2u85k7vqwqqb08k5b72uy6qmi` (`function_id`),
  KEY `FK8t9qyhjts5aif28n5l98skl25` (`country_id`),
  CONSTRAINT `FK2u85k7vqwqqb08k5b72uy6qmi` FOREIGN KEY (`function_id`) REFERENCES `company_function` (`id`),
  CONSTRAINT `FK8t9qyhjts5aif28n5l98skl25` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country_function`
--

LOCK TABLES `country_function` WRITE;
/*!40000 ALTER TABLE `country_function` DISABLE KEYS */;
INSERT INTO `country_function` VALUES ('14f645e8-7893-40b4-b3e6-3d555aab9d25','65fff968-a5be-49e9-90a4-6f33928b04da'),('14f645e8-7893-40b4-b3e6-3d555aab9d25','b6b0dcb9-0a60-43fc-b464-d1634434c27c'),('14f645e8-7893-40b4-b3e6-3d555aab9d25','28b84501-6590-409a-8002-c39258193f67'),('14f645e8-7893-40b4-b3e6-3d555aab9d25','37fdd5aa-25fd-42f2-9736-d226f6c62363'),('f970de5b-d9f8-432a-b626-702b4076b9a5','65fff968-a5be-49e9-90a4-6f33928b04da'),('f970de5b-d9f8-432a-b626-702b4076b9a5','b6b0dcb9-0a60-43fc-b464-d1634434c27c'),('f970de5b-d9f8-432a-b626-702b4076b9a5','28b84501-6590-409a-8002-c39258193f67'),('f970de5b-d9f8-432a-b626-702b4076b9a5','37fdd5aa-25fd-42f2-9736-d226f6c62363'),('092b323e-81d5-4329-8471-4fd9f389cc46','65fff968-a5be-49e9-90a4-6f33928b04da'),('092b323e-81d5-4329-8471-4fd9f389cc46','b6b0dcb9-0a60-43fc-b464-d1634434c27c'),('092b323e-81d5-4329-8471-4fd9f389cc46','28b84501-6590-409a-8002-c39258193f67'),('092b323e-81d5-4329-8471-4fd9f389cc46','37fdd5aa-25fd-42f2-9736-d226f6c62363'),('93d723dd-ddb5-4834-b2b4-673314d88df8','65fff968-a5be-49e9-90a4-6f33928b04da'),('93d723dd-ddb5-4834-b2b4-673314d88df8','b6b0dcb9-0a60-43fc-b464-d1634434c27c'),('93d723dd-ddb5-4834-b2b4-673314d88df8','28b84501-6590-409a-8002-c39258193f67'),('93d723dd-ddb5-4834-b2b4-673314d88df8','37fdd5aa-25fd-42f2-9736-d226f6c62363'),('9b0c3c04-7213-4299-9220-a67b31e342dd','65fff968-a5be-49e9-90a4-6f33928b04da'),('9b0c3c04-7213-4299-9220-a67b31e342dd','b6b0dcb9-0a60-43fc-b464-d1634434c27c'),('9b0c3c04-7213-4299-9220-a67b31e342dd','28b84501-6590-409a-8002-c39258193f67'),('9b0c3c04-7213-4299-9220-a67b31e342dd','37fdd5aa-25fd-42f2-9736-d226f6c62363'),('ec719fce-e236-4d5e-b136-868351527d01','65fff968-a5be-49e9-90a4-6f33928b04da'),('ec719fce-e236-4d5e-b136-868351527d01','b6b0dcb9-0a60-43fc-b464-d1634434c27c'),('ec719fce-e236-4d5e-b136-868351527d01','28b84501-6590-409a-8002-c39258193f67'),('ec719fce-e236-4d5e-b136-868351527d01','37fdd5aa-25fd-42f2-9736-d226f6c62363');
/*!40000 ALTER TABLE `country_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currency` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h84pd2rtr12isnifnj655rkra` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES ('14779d48-b3c3-49e8-aebe-645e34f0c2d4','USD',NULL,'2019-10-03 08:23:54',NULL,_binary '\0',NULL,'2019-10-03 08:23:54','US Dollar','',3,'US',NULL),('2003b8c3-64a3-4df5-bed6-bd150c820bab','SGD',NULL,'2019-10-03 08:24:05',NULL,_binary '\0',NULL,'2019-10-03 08:24:05','Singapore Dollar','',4,'SG',NULL),('79d26155-faa2-403b-a28a-9ce42623d546','CNY',NULL,'2019-10-03 08:23:32',NULL,_binary '\0',NULL,'2019-10-03 08:23:32','Chinese Yuan','',2,'CN',NULL),('a50e3663-5689-4483-bd25-7304c240a40c','HKD',NULL,'2019-10-03 08:24:22',NULL,_binary '\0',NULL,'2019-10-03 08:24:22','Hong Kong Dollar','',5,'HK',NULL),('f864438b-611a-4b50-a2d4-25a05790b772','VND',NULL,'2019-10-03 08:23:22',NULL,_binary '\0',NULL,'2019-10-03 08:23:22','Vietnamese đồng','',1,'VN',NULL);
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demo_entity`
--

DROP TABLE IF EXISTS `demo_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo_entity` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3y3j1t2c7jy66sq6di1c8j9ai` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo_entity`
--

LOCK TABLES `demo_entity` WRITE;
/*!40000 ALTER TABLE `demo_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `demo_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dispute`
--

DROP TABLE IF EXISTS `dispute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dispute` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `dispute_description` varchar(255) DEFAULT NULL,
  `dispute_status` int(11) DEFAULT NULL,
  `dispute_creator` varchar(36) DEFAULT NULL,
  `dispute_handler` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_auxvybnwdqd5agyjc6nbxovl3` (`code`),
  KEY `FK94aobhjj0uqgx4vm9hs4gk6c3` (`dispute_creator`),
  KEY `FKddmrdbvfikbjmrmhnf8md390l` (`dispute_handler`),
  CONSTRAINT `FK94aobhjj0uqgx4vm9hs4gk6c3` FOREIGN KEY (`dispute_creator`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKddmrdbvfikbjmrmhnf8md390l` FOREIGN KEY (`dispute_handler`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispute`
--

LOCK TABLES `dispute` WRITE;
/*!40000 ALTER TABLE `dispute` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `employee_type` int(11) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `my_approvals` varchar(255) DEFAULT NULL,
  `my_request_tickets` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `security_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `costcenter_id` varchar(36) DEFAULT NULL,
  `manager_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nbyivu8qgmx0r7wtbplf01gf8` (`code`),
  UNIQUE KEY `UK_o885fqgb71dmn4hp0p6rs4ms4` (`user_name`),
  KEY `FKrvgbvpeyqipkfdx58oqsx4ek9` (`costcenter_id`),
  KEY `FKou6wbxug1d0qf9mabut3xqblo` (`manager_id`),
  CONSTRAINT `FKou6wbxug1d0qf9mabut3xqblo` FOREIGN KEY (`manager_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKrvgbvpeyqipkfdx58oqsx4ek9` FOREIGN KEY (`costcenter_id`) REFERENCES `cost_center` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('235ef575-83ef-4ca0-b779-3a9207d69018','EMP-5',NULL,'2019-10-04 10:35:40',NULL,_binary '\0',NULL,'2019-10-04 10:35:40',NULL,'',5,'huangyingshi@gmail.com',NULL,'Function First','Function Last','','','','$argon2id$v=19$m=131072,t=6,p=1$LFvh/L6JKsziTojJeJEdvw$2sC6CJTgP/hHnMz4VBDNw4RV1he4/5sOpQ3aKas8U3o','S-e2a488ed-e4f9-4bfe-aaab-4ff786c0822e','functionapprover',NULL,NULL),('3c649a80-1cc0-4eff-9d6b-fb55a4a44498','EMP-4',NULL,'2019-10-04 10:35:04',NULL,_binary '\0',NULL,'2019-10-04 10:35:04',NULL,'',4,'huangyingshi@gmail.com',NULL,'BM First','BM Last','','','','$argon2id$v=19$m=131072,t=6,p=1$QC8TOsoQhqvsdBLpn69K+Q$6XDsU307r3Yj1bKPAULFjM5QXimd0m68XCh4jRrEF8g','S-34b8de6e-792c-457a-a0cb-be08e85b586d','bmapprover',NULL,NULL),('8e0928fe-49ee-48de-ae9d-ba5f855aed56','EMP-3',NULL,'2019-10-03 16:04:43',NULL,_binary '\0',NULL,'2019-10-03 16:04:43','xuhong','',3,'huangyingshi@gmail.com',0,'hong','xu','','','','$argon2id$v=19$m=131072,t=6,p=1$0wrehtoDFCRYMBuN2k0YPQ$nPcVbq+yq9VhQYUb5oOeTxH/l3vkNNjxU8KVRKNiUzU','S-9ad37fa4-efe3-4731-b54d-adcc9b04c7dd','xuhong',NULL,NULL),('dd650097-4b59-4dae-8d6f-eec585813750','EMP-2',NULL,'2019-10-03 08:19:18',NULL,_binary '\0',NULL,'2019-10-03 08:19:18',NULL,'',2,'huangyingshi@gmail.com',NULL,'Yingshi','Huang','','','','$argon2id$v=19$m=131072,t=6,p=1$w2QGpkt+iD+tOgW8Xqjw9g$sJSmhqteWqMdxdTtEpYHeOFjBHuExVAWWIr645VhIzY','S-a8c5b61a-f079-4d05-9024-e0d2dbcd66ae','yingshi2502',NULL,NULL),('f56ad9c8-e92a-43ff-b101-be4cfa20558f','EMP-1',NULL,'2019-10-03 08:16:44',NULL,_binary '\0',NULL,'2019-10-03 08:16:44',NULL,'',1,'huangyingshi@gmail.com',NULL,'First','Last','Middle','','','$argon2id$v=19$m=131072,t=6,p=1$raIrxDLt50frNkGkJqZplg$AtawdPa9LPcJa1GtJxDs1WenLKwH16Kkp0/JocuHlM4','S-40e2f8a8-595a-4c3a-b42f-7c7bc8f81273','admin',NULL,NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fx_record`
--

DROP TABLE IF EXISTS `fx_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fx_record` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `base_currency_abbr` varchar(255) DEFAULT NULL,
  `effective_date` date DEFAULT NULL,
  `exchange_rate` decimal(19,6) DEFAULT NULL,
  `expire_date` date DEFAULT NULL,
  `has_expired` bit(1) DEFAULT NULL,
  `price_currency_abbr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6gsp19ooxmk8wt8qsdtrs53xw` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fx_record`
--

LOCK TABLES `fx_record` WRITE;
/*!40000 ALTER TABLE `fx_record` DISABLE KEYS */;
INSERT INTO `fx_record` VALUES ('0ee38b2c-6983-4406-b74b-0184f9b9ad01','FX-USD-CNY-021019-7','admin','2019-10-03 08:30:26',NULL,_binary '\0',NULL,'2019-10-03 08:30:26','USD-CNY-021019','',7,'USD','2019-10-01',7.150000,'9999-12-30',_binary '\0','CNY'),('1255e1b1-f4dc-404e-865b-037860129475','FX-USD-CNY-310719-6','admin','2019-10-03 08:30:10',NULL,_binary '\0',NULL,'2019-10-03 08:30:26','USD-CNY-310719','',6,'USD','2019-07-30',6.880000,'2019-10-01',_binary '','CNY'),('425c2551-fc37-403f-9ef2-f0c503bd4c1f','FX-SGD-CNY-040519-9','admin','2019-10-03 08:33:27',NULL,_binary '\0',NULL,'2019-10-03 08:33:39','SGD-CNY-040519','',9,'SGD','2019-05-03',4.950000,'2019-10-01',_binary '','CNY'),('42a38049-aa3a-4ed5-a2a0-c95e4ba771bb','FX-USD-SGD-170619-3','admin','2019-10-03 08:22:42',NULL,_binary '\0',NULL,'2019-10-03 08:22:58','USD-SGD-170619','',3,'USD','2019-06-16',1.370000,'2019-10-02',_binary '','SGD'),('48b68653-178a-4f10-be06-c3ab9c4c3a0d','FX-USD-SGD-031019-4','admin','2019-10-03 08:22:58',NULL,_binary '\0',NULL,'2019-10-03 08:22:58','USD-SGD-031019','',4,'USD','2019-10-02',1.380000,'9999-12-30',_binary '\0','SGD'),('4a68b4c2-f7ba-4a8c-bb5d-3e4d2580c3f2','FX-SGD-CNY-021019-10','admin','2019-10-03 08:33:39',NULL,_binary '\0',NULL,'2019-10-05 03:42:43','SGD-CNY-021019','',10,'SGD','2019-10-01',5.160000,'2019-10-04',_binary '','CNY'),('8f645ec8-5b5e-45ba-88c4-48268ca90945','FX-SGD-USD-270919-1','yingshi2502','2019-10-03 04:32:37',NULL,_binary '\0',NULL,'2019-10-03 04:32:38','SGD-USD-270919','',1,'SGD','2019-09-26',0.720000,'9999-12-30',_binary '\0','USD'),('acca6382-b50d-447e-a357-27602cfbeedd','FX-USD-CNY-260219-5','admin','2019-10-03 08:29:53',NULL,_binary '\0',NULL,'2019-10-03 08:30:10','USD-CNY-260219','',5,'USD','2019-02-25',6.700000,'2019-07-30',_binary '','CNY'),('b00a4276-82f0-4f68-bf7c-c4e7937fb9c8','FX-SGD-CNY-051019-11','Viet','2019-10-05 03:42:43',NULL,_binary '\0',NULL,'2019-10-05 03:42:43','SGD-CNY-051019','',11,'SGD','2019-10-04',5.190000,'9999-12-30',_binary '\0','CNY'),('c449351d-ab44-4489-bcb6-8da2c505657d','FX-SGD-CNY-061018-8','admin','2019-10-03 08:33:09',NULL,_binary '\0',NULL,'2019-10-03 08:33:27','SGD-CNY-061018','',8,'SGD','2018-10-05',4.960000,'2019-05-03',_binary '','CNY'),('d4b87fb3-c017-4c19-b385-4423f9f1ed83','FX-USD-SGD-031018-2','admin','2019-10-03 08:22:18',NULL,_binary '\0',NULL,'2019-10-03 08:22:42','USD-SGD-031018','',2,'USD','2018-10-02',1.380000,'2019-06-16',_binary '','SGD');
/*!40000 ALTER TABLE `fx_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (436),(436);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `payment_amount` decimal(19,2) DEFAULT NULL,
  `po_id` varchar(36) DEFAULT NULL,
  `vendor_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7jq4q4t65on0ftpce6rjb17m9` (`code`),
  KEY `FKd7tiwqicvnr7vk16q41nu0l1q` (`po_id`),
  KEY `FKonm3ex5ggu1593oiqimy4ea7n` (`vendor_id`),
  CONSTRAINT `FKd7tiwqicvnr7vk16q41nu0l1q` FOREIGN KEY (`po_id`) REFERENCES `purchase_order` (`id`),
  CONSTRAINT `FKonm3ex5ggu1593oiqimy4ea7n` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchandise`
--

DROP TABLE IF EXISTS `merchandise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchandise` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `current_contract_code` varchar(255) DEFAULT NULL,
  `current_price` decimal(19,2) DEFAULT NULL,
  `measure_unit` varchar(255) NOT NULL,
  `budget_sub2_id` varchar(36) DEFAULT NULL,
  `vendor_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_alk29srn4k6kqpjvs8xkek15x` (`code`),
  KEY `FKhoo79xtd0s2j3h4t7lohhsrkd` (`budget_sub2_id`),
  KEY `FKkg0dcx9y1w3s0h9wv7ocyne7k` (`vendor_id`),
  CONSTRAINT `FKhoo79xtd0s2j3h4t7lohhsrkd` FOREIGN KEY (`budget_sub2_id`) REFERENCES `budget_sub2` (`id`),
  CONSTRAINT `FKkg0dcx9y1w3s0h9wv7ocyne7k` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchandise`
--

LOCK TABLES `merchandise` WRITE;
/*!40000 ALTER TABLE `merchandise` DISABLE KEYS */;
INSERT INTO `merchandise` VALUES ('00f949e7-653e-4a36-affc-c811b2a12897','M-STARHUB_DATA_LINK-6','yingshi2502','2019-10-03 09:41:51','APAC-SG-TELECOM-DATA-DATA_LINK-STARHUB_DATA_LINK',_binary '\0',NULL,'2019-10-03 09:41:51','Starhub Data Link','GRANT,all_user',6,NULL,NULL,NULL,'meter','536f4b4b-8133-4a6c-86ba-9abba071fee8','0d330fed-9741-4182-bb46-c71ca38f53e3'),('04cdf4ba-96a7-4c88-8ed5-4a6d78892a2a','M-SAMSUNG_HEADSET-7','yingshi2502','2019-10-03 09:42:24','APAC-SG-TELECOM-VOICE-HEADSET-SAMSUNG_DATA_LINK',_binary '\0',NULL,'2019-10-03 09:42:24','Samsung Headset','GRANT,all_user',7,NULL,NULL,NULL,'piece','247d1318-1be6-4865-aac1-06eb8093182d','08ad83b4-5d10-481d-8704-03762f2c4a51'),('1a6afb0f-d586-40a8-b4b0-b6b33a12821f','M-SG_PLANNED_TRAVELING-19','yingshi2502','2019-10-05 03:55:35','APAC-SG-MISCELLANEOUS-TRAVEL-PLANNED_TRIPS-SG_PLANNED_TRAVELING',_binary '\0',NULL,'2019-10-05 03:55:35','SG Planned traveling','GRANT,all_user',19,NULL,NULL,NULL,'dollar','0eb8ab4c-9090-45cb-873b-f5cebb874e6e','65b6940e-0f98-4a63-934e-b512b537fd72'),('23492614-1b9d-4a49-a225-c61bf16b580f','M-AD-HOC_TRAVEL-11','yingshi2502','2019-10-05 03:50:11','APAC-SG-MISCELLANEOUS-TRAVEL-AD-HOC_TRAVEL-AD-HOC_TRAVEL',_binary '\0',NULL,'2019-10-05 03:50:11','Ad-hoc travel','GRANT,all_user',11,NULL,NULL,NULL,'dollar','5b8753c3-6827-41f1-9b57-d7a0a58a6dc5','65b6940e-0f98-4a63-934e-b512b537fd72'),('24cba712-4288-4972-aa1c-2f1d31eb0b43','M-DELL_MONITOR-3','yingshi2502','2019-10-03 09:40:30','APAC-SG-IT_ASSET-PERONAL_DESKTOP-DESKTOP_MONITOR-DELL_MONITOR',_binary '\0',NULL,'2019-10-03 09:40:30','Dell Monitor','GRANT,all_user',3,NULL,NULL,NULL,'ppl','4b4bdfdd-689c-46d9-99a3-05590596dc3b','b81c911c-839a-477f-bc78-5f2566fa4492'),('2ae1044f-388b-4405-b7d8-d7cc0b3ded7c','M-CISO_CARD_READER-12','yingshi2502','2019-10-05 03:52:30','APAC-SG-IT_ASSET-ACCESS_CONTROL-CARD_READER-CISO_CARD_READER',_binary '\0',NULL,'2019-10-05 03:52:31','CISO Card Reader','GRANT,all_user',12,NULL,NULL,NULL,'piece','bf88e9d9-d51d-4094-a2fd-ca96611bbbb9','7a299ce2-0cd3-4c9c-975b-46fb132e5085'),('37596bd7-6272-4a80-a2ae-cdae69fcf847','M-CISCO_PHONE-2','yingshi2502','2019-10-03 09:38:47','APAC-SG-TELECOM-VOICE-HEADSET-CISCO_PHONE',_binary '\0',NULL,'2019-10-03 09:38:47','CISCO Phone','GRANT,all_user',2,NULL,NULL,NULL,'ppl','247d1318-1be6-4865-aac1-06eb8093182d','7a299ce2-0cd3-4c9c-975b-46fb132e5085'),('4be455fb-becb-453f-8feb-dcb638fbaedc','M-DELL_PROJECTOR-4','yingshi2502','2019-10-03 09:40:49','APAC-SG-IT_ASSET-SCREENING-PROJECTOR-DELL_PROJECTOR',_binary '\0',NULL,'2019-10-03 09:40:49','Dell Projector','GRANT,all_user',4,NULL,NULL,NULL,'piece','e0dd0538-d598-445a-b49a-223df9be6a10','b81c911c-839a-477f-bc78-5f2566fa4492'),('56e901ba-d865-4d03-a854-f86a37f66e53','M-IPHONE_SE-16','yingshi2502','2019-10-05 03:54:06','APAC-SG-TELECOM-VOICE-PHONE-IPHONE_SE',_binary '\0',NULL,'2019-10-05 03:54:06','iPhone SE','GRANT,all_user',16,NULL,NULL,NULL,'piece','b3c18021-243d-484e-a5c8-44921a98e747','154aec3b-7eef-4f74-bc20-268605ccfd11'),('61da381c-0242-47b8-8f77-76cb3a5cb2c5','M-MACBOOK_13-8','yingshi2502','2019-10-03 09:43:40','APAC-SG-IT_ASSET-PERONAL_DESKTOP-MAC-MACBOOK_13',_binary '\0',NULL,'2019-10-03 09:43:40','Macbook 13','GRANT,all_user',8,NULL,NULL,NULL,'piece','4d86c134-7181-4317-ac26-9e808c34d9bd','154aec3b-7eef-4f74-bc20-268605ccfd11'),('6adfbdb3-0eb3-406d-a033-9aedcd9e15b0','M-SINGTEL_DATA_LINK-5','yingshi2502','2019-10-03 09:41:37','APAC-SG-TELECOM-DATA-DATA_LINK-SINGTEL_DATA_LINK',_binary '\0',NULL,'2019-10-03 09:41:37','Singtel Data Link','GRANT,all_user',5,NULL,NULL,NULL,'meter','536f4b4b-8133-4a6c-86ba-9abba071fee8','e88caeb9-d993-4ac9-9ca3-f19131c69a8f'),('6fc52938-57ee-40e1-8216-702a315526b7','M-FINDER_RECOGNITION_MACHINE-14','yingshi2502','2019-10-05 03:53:29','APAC-SG-IT_ASSET-ACCESS_CONTROL-FINGER_RECOGNITION-FINDER_RECOGNITION_MACHINE',_binary '\0',NULL,'2019-10-05 03:53:29','finder recognition machine','GRANT,all_user',14,NULL,NULL,NULL,'piece','ae502fca-91e1-4f43-8caf-474c0182e6c3','88554bc4-6eff-4466-8efc-64ffba3a2e17'),('880cd9e4-500b-40f6-bbf3-87f1460a179b','M-DELL_9010-9','yingshi2502','2019-10-03 09:44:33','APAC-SG-IT_ASSET-PERONAL_DESKTOP-WINDOWS_PC-DELL_9010',_binary '\0',NULL,'2019-10-03 09:44:34','Dell 9010','GRANT,all_user',9,NULL,NULL,NULL,'piece','40a5938a-5c4e-461f-944b-ae677fb98caa','b81c911c-839a-477f-bc78-5f2566fa4492'),('a02188c6-65b5-4c16-b0b3-1b8d41dd2ba7','M-SONY_HEADSET-1','yingshi2502','2019-10-03 09:38:10','APAC-SG-TELECOM-VOICE-HEADSET-SONY_HEADSET',_binary '\0',NULL,'2019-10-03 09:38:10','Sony Headset','GRANT,all_user',1,NULL,NULL,NULL,'piece','247d1318-1be6-4865-aac1-06eb8093182d','2f9f20d3-715a-4905-92d8-8c33a97ff06b'),('a8381e72-d49d-4999-8126-d3ed2e83273d','M-IPHONE_X-15','yingshi2502','2019-10-05 03:53:58','APAC-SG-TELECOM-VOICE-PHONE-IPHONE_X',_binary '\0',NULL,'2019-10-05 03:53:58','iPhone X','GRANT,all_user',15,NULL,NULL,NULL,'piece','b3c18021-243d-484e-a5c8-44921a98e747','154aec3b-7eef-4f74-bc20-268605ccfd11'),('c7aee941-9734-4adb-86f3-a9ed627bf22a','M-CHALLENGER_DATA_WIRE-13','yingshi2502','2019-10-05 03:53:06','APAC-SG-TELECOM-DATA-DATA_WIRE-CHALLENGER_DATA_WIRE',_binary '\0',NULL,'2019-10-05 03:53:06','Challenger Data wire','GRANT,all_user',13,NULL,NULL,NULL,'meter','84ba63ff-1a69-4f76-8c25-b32fe909d381','88554bc4-6eff-4466-8efc-64ffba3a2e17'),('cf02b4ab-83f2-4fbb-84fe-b62fd05e9dc5','M-SG_PLANNED_TRAINING-18','yingshi2502','2019-10-05 03:55:23','APAC-SG-MISCELLANEOUS-TRAINING-PLANNED_TRAINING-SG_PLANNED_TRAINING',_binary '\0',NULL,'2019-10-05 03:55:23','SG Planned training','GRANT,all_user',18,NULL,NULL,NULL,'dollar','758b89f5-d8bb-44aa-8b07-d593abc63595','65b6940e-0f98-4a63-934e-b512b537fd72'),('d1a0ff33-5f61-4c8b-951d-98cb11b0d223','M-AD-HOC_TRAINING-10','yingshi2502','2019-10-05 03:49:42','APAC-SG-MISCELLANEOUS-TRAINING-AD-HOC_TRAINING-AD-HOC_TRAINING',_binary '\0',NULL,'2019-10-05 03:49:42','Ad-hoc training','GRANT,all_user',10,NULL,NULL,NULL,'dollar','bdcc8900-ce2a-4b78-9fb8-41002896dbe1','65b6940e-0f98-4a63-934e-b512b537fd72'),('e81380a7-aec3-4fb6-9706-cf9007024a58','M-DELL_REMOTE_DESKTOP_MACHINE-17','yingshi2502','2019-10-05 03:54:43','APAC-SG-IT_ASSET-SCREENING-REMOTE_DESKTOP-DELL_REMOTE_DESKTOP_MACHINE',_binary '\0',NULL,'2019-10-05 03:54:44','Dell remote desktop machine','GRANT,all_user',17,NULL,NULL,NULL,'piece','eb8de338-ff2e-4f9e-bb40-e8a80d4ebdce','b81c911c-839a-477f-bc78-5f2566fa4492');
/*!40000 ALTER TABLE `merchandise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office`
--

DROP TABLE IF EXISTS `office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `functions_code_in_office` varchar(255) DEFAULT NULL,
  `num_of_floors` int(11) DEFAULT NULL,
  `country_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3bvn5cjvi8e2nj6l509qxvnh1` (`code`),
  KEY `FKio9q8gq963195i6i6qiy83qfd` (`country_id`),
  CONSTRAINT `FKio9q8gq963195i6i6qiy83qfd` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office`
--

LOCK TABLES `office` WRITE;
/*!40000 ALTER TABLE `office` DISABLE KEYS */;
INSERT INTO `office` VALUES ('0013b4d3-4e86-4f55-a576-3ba2d9a46b8e','LHT',NULL,'2019-10-03 08:50:49',NULL,_binary '\0',NULL,'2019-10-03 08:50:49','LHT Tower','',2,'31 Queen\'s Road','Unit 702 Central Hong Kong','Hong Kong','HK','000','TECH,HR,RM,FIN',NULL,'092b323e-81d5-4329-8471-4fd9f389cc46'),('10d1a3f7-e410-4b19-9b9a-0cf9563c42fc','ORQ',NULL,'2019-10-03 08:44:07',NULL,_binary '\0',NULL,'2019-10-03 08:44:07','One Raffles Quay','',1,' 1 Raffles Place','','Singapore','SG','048616','TECH,HR,RM,FIN',NULL,'9b0c3c04-7213-4299-9220-a67b31e342dd');
/*!40000 ALTER TABLE `office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office_floors`
--

DROP TABLE IF EXISTS `office_floors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office_floors` (
  `office_id` varchar(36) NOT NULL,
  `floors` varchar(255) DEFAULT NULL,
  KEY `FK5q89l8cybwk9pu5w9rane235b` (`office_id`),
  CONSTRAINT `FK5q89l8cybwk9pu5w9rane235b` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office_floors`
--

LOCK TABLES `office_floors` WRITE;
/*!40000 ALTER TABLE `office_floors` DISABLE KEYS */;
INSERT INTO `office_floors` VALUES ('10d1a3f7-e410-4b19-9b9a-0cf9563c42fc','23'),('10d1a3f7-e410-4b19-9b9a-0cf9563c42fc','24'),('0013b4d3-4e86-4f55-a576-3ba2d9a46b8e','7');
/*!40000 ALTER TABLE `office_floors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outsourcing`
--

DROP TABLE IF EXISTS `outsourcing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outsourcing` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `outsourcing_description` varchar(255) DEFAULT NULL,
  `employee_in_charge_outsourcing` varchar(36) DEFAULT NULL,
  `outsourcing_vendor` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lbtti1tjpojdvllg4c5ww462d` (`code`),
  KEY `FKh8t7ekmfjc2q5lq1xwgp3teio` (`employee_in_charge_outsourcing`),
  KEY `FK78tftpmd13l277vfaap1puk7u` (`outsourcing_vendor`),
  CONSTRAINT `FK78tftpmd13l277vfaap1puk7u` FOREIGN KEY (`outsourcing_vendor`) REFERENCES `vendor` (`id`),
  CONSTRAINT `FKh8t7ekmfjc2q5lq1xwgp3teio` FOREIGN KEY (`employee_in_charge_outsourcing`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outsourcing`
--

LOCK TABLES `outsourcing` WRITE;
/*!40000 ALTER TABLE `outsourcing` DISABLE KEYS */;
/*!40000 ALTER TABLE `outsourcing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outsourcing_assessment`
--

DROP TABLE IF EXISTS `outsourcing_assessment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outsourcing_assessment` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `business_case_description` varchar(255) DEFAULT NULL,
  `outsourcing_assessment_status` int(11) DEFAULT NULL,
  `employee_assess` varchar(36) DEFAULT NULL,
  `outsourcing` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3glm7vh70b31d4ph9jqpibu1o` (`code`),
  KEY `FK4e6hg286vmqnlna61p9nkh80p` (`employee_assess`),
  KEY `FKkxvv2veeslxrglwcnhrw71hj4` (`outsourcing`),
  CONSTRAINT `FK4e6hg286vmqnlna61p9nkh80p` FOREIGN KEY (`employee_assess`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKkxvv2veeslxrglwcnhrw71hj4` FOREIGN KEY (`outsourcing`) REFERENCES `outsourcing` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outsourcing_assessment`
--

LOCK TABLES `outsourcing_assessment` WRITE;
/*!40000 ALTER TABLE `outsourcing_assessment` DISABLE KEYS */;
/*!40000 ALTER TABLE `outsourcing_assessment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outsourcing_assessment_line`
--

DROP TABLE IF EXISTS `outsourcing_assessment_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outsourcing_assessment_line` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `outsourcing_assessment_section` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f9nfsn5khcqktw8xulpwwpm9k` (`code`),
  KEY `FKqnp1paka71pakvklhdbn42ylk` (`outsourcing_assessment_section`),
  CONSTRAINT `FKqnp1paka71pakvklhdbn42ylk` FOREIGN KEY (`outsourcing_assessment_section`) REFERENCES `outsourcing_assessment_section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outsourcing_assessment_line`
--

LOCK TABLES `outsourcing_assessment_line` WRITE;
/*!40000 ALTER TABLE `outsourcing_assessment_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `outsourcing_assessment_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outsourcing_assessment_section`
--

DROP TABLE IF EXISTS `outsourcing_assessment_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outsourcing_assessment_section` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `outsourcing_assessment` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_76e6a2rwsyjau82ysxub4a72p` (`code`),
  KEY `FKb5tg1en0cbj0u0g9kxdd55rr0` (`outsourcing_assessment`),
  CONSTRAINT `FKb5tg1en0cbj0u0g9kxdd55rr0` FOREIGN KEY (`outsourcing_assessment`) REFERENCES `outsourcing_assessment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outsourcing_assessment_section`
--

LOCK TABLES `outsourcing_assessment_section` WRITE;
/*!40000 ALTER TABLE `outsourcing_assessment_section` DISABLE KEYS */;
/*!40000 ALTER TABLE `outsourcing_assessment_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `budget_plan_status` int(11) DEFAULT NULL,
  `for_month` int(11) DEFAULT NULL,
  `for_year` int(11) DEFAULT NULL,
  `plan_description` varchar(255) DEFAULT NULL,
  `plan_type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `cost_center_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6jenoap3aq7hiikqf7t0g6mqa` (`code`),
  KEY `FKnfw9mgeem1p1gfwyuq8b6d4e6` (`cost_center_id`),
  CONSTRAINT `FKnfw9mgeem1p1gfwyuq8b6d4e6` FOREIGN KEY (`cost_center_id`) REFERENCES `cost_center` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES ('218dbe9c-71fe-4b86-9b31-3a3fc9eedecd','2018-OCT-REFORECAST-27','xuhong','2019-10-05 03:22:49','CC1235-SGGCP-PMT-REFORECAST-2018',_binary '\0',NULL,'2019-10-05 03:23:30','2018-Oct-REFORECAST','',27,4,10,2018,'Reforecast Plan for year 2018, past year test data',1,1,'a1e9e434-e5bd-11e9-8b11-00155d03b61a'),('89863b27-cb3c-4d53-94af-f42161bac3cc','2019-BUDGET-30','xuhong','2019-10-05 03:30:54','CC1235-SGGCP-PMT-BUDGET-2019',_binary '\0',NULL,'2019-10-05 03:31:54','2019-BUDGET','',30,3,NULL,2019,'Budget Plan for year 2019, past year test data',0,1,'a1e9e434-e5bd-11e9-8b11-00155d03b61a'),('aa656742-765d-4202-968a-732d1d2e6d93','2019-BUDGET-32','xuhong','2019-10-05 03:36:07','CC02-SGHR-S-BUDGET-2019',_binary '\0',NULL,'2019-10-05 03:36:36','2019-BUDGET','',32,3,NULL,2019,'Budget Plan for year 2019, past year test data',0,1,'bc6a930d-e5bd-11e9-8b11-00155d03b61a'),('bf5cd707-5442-4015-be5a-e611fe9885c9','2018-BUDGET-26','xuhong','2019-10-05 03:19:38','CC1235-SGGCP-PMT-BUDGET-2018',_binary '\0',NULL,'2019-10-05 03:20:21','2018-BUDGET','',26,3,NULL,2018,'Budget Plan for year 2018, past year test data',0,1,'a1e9e434-e5bd-11e9-8b11-00155d03b61a'),('f5917493-c610-41f9-8833-7b2cdf3c6347','2018-BUDGET-25','xuhong','2019-10-05 03:18:00','CC04-SGGCP-S-BUDGET-2018',_binary '\0',NULL,'2019-10-05 03:20:53','2018-BUDGET','',25,3,NULL,2018,'Budget Plan for year 2018, past year test data',0,1,'8a3e7627-e5bd-11e9-8b11-00155d03b61a');
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_line_item`
--

DROP TABLE IF EXISTS `plan_line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_line_item` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `budget_amount` decimal(19,2) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `currency_abbr` varchar(255) DEFAULT NULL,
  `item_type` int(11) DEFAULT NULL,
  `merchandise_code` varchar(255) DEFAULT NULL,
  `in_plan` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8eli412irgnqj6l0k762jpjdl` (`code`),
  KEY `FKovvfbkhm8ssle8g1vo53w2gi4` (`in_plan`),
  CONSTRAINT `FKovvfbkhm8ssle8g1vo53w2gi4` FOREIGN KEY (`in_plan`) REFERENCES `plan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_line_item`
--

LOCK TABLES `plan_line_item` WRITE;
/*!40000 ALTER TABLE `plan_line_item` DISABLE KEYS */;
INSERT INTO `plan_line_item` VALUES ('05f7c395-54f2-412a-a7d6-ef606759c702','PLINE-69',NULL,'2019-10-05 03:19:39','null-M-SONY_HEADSET-1',_binary '\0',NULL,'2019-10-05 03:19:39',NULL,'',69,54.00,NULL,'USD',NULL,'M-SONY_HEADSET-1','bf5cd707-5442-4015-be5a-e611fe9885c9'),('09018a7f-2da5-42bc-9f86-a62e9976c9d0','PLINE-70',NULL,'2019-10-05 03:22:49','null-M-DELL_PROJECTOR-4',_binary '\0',NULL,'2019-10-05 03:22:49',NULL,'',70,120.00,NULL,'SGD',NULL,'M-DELL_PROJECTOR-4','218dbe9c-71fe-4b86-9b31-3a3fc9eedecd'),('0ba0b432-9219-42a7-9397-dccbf7926fc3','PLINE-68',NULL,'2019-10-05 03:19:39','null-M-MACBOOK_13-8',_binary '\0',NULL,'2019-10-05 03:19:39',NULL,'',68,104.60,NULL,'USD',NULL,'M-MACBOOK_13-8','bf5cd707-5442-4015-be5a-e611fe9885c9'),('20770a23-b1b6-4284-9863-eb4dbdbfb73d','PLINE-63',NULL,'2019-10-05 03:18:00','null-M-STARHUB_DATA_LINK-6',_binary '\0',NULL,'2019-10-05 03:18:00',NULL,'',63,111.10,NULL,'SGD',NULL,'M-STARHUB_DATA_LINK-6','f5917493-c610-41f9-8833-7b2cdf3c6347'),('30cf7026-65b2-4b2d-a928-a14cc400dd82','PLINE-84',NULL,'2019-10-05 03:30:54','null-M-MACBOOK_13-8',_binary '\0',NULL,'2019-10-05 03:30:54',NULL,'',84,1320.00,NULL,'USD',NULL,'M-MACBOOK_13-8','89863b27-cb3c-4d53-94af-f42161bac3cc'),('38f4e0fe-c087-4504-8c05-ec79a9f4dd5d','PLINE-66',NULL,'2019-10-05 03:19:38','null-M-DELL_PROJECTOR-4',_binary '\0',NULL,'2019-10-05 03:19:38',NULL,'',66,111.10,NULL,'SGD',NULL,'M-DELL_PROJECTOR-4','bf5cd707-5442-4015-be5a-e611fe9885c9'),('4c48eccf-56b8-48b2-aa63-3b9a336b1977','PLINE-72',NULL,'2019-10-05 03:22:49','null-M-MACBOOK_13-8',_binary '\0',NULL,'2019-10-05 03:22:49',NULL,'',72,120.00,NULL,'USD',NULL,'M-MACBOOK_13-8','218dbe9c-71fe-4b86-9b31-3a3fc9eedecd'),('5f9660af-9f4f-4864-85a9-84e05166e86b','PLINE-64',NULL,'2019-10-05 03:18:00','null-M-DELL_MONITOR-3',_binary '\0',NULL,'2019-10-05 03:18:01',NULL,'',64,1094.60,NULL,'USD',NULL,'M-DELL_MONITOR-3','f5917493-c610-41f9-8833-7b2cdf3c6347'),('639e5062-79da-4b1d-adad-22d57db9eb41','PLINE-94',NULL,'2019-10-05 03:36:08','null-M-MACBOOK_13-8',_binary '\0',NULL,'2019-10-05 03:36:08',NULL,'',94,1320.00,NULL,'USD',NULL,'M-MACBOOK_13-8','aa656742-765d-4202-968a-732d1d2e6d93'),('84031eef-c5ee-49fd-b942-ea4ac6eae8b7','PLINE-83',NULL,'2019-10-05 03:30:54','null-M-DELL_MONITOR-3',_binary '\0',NULL,'2019-10-05 03:30:54',NULL,'',83,1320.00,NULL,'USD',NULL,'M-DELL_MONITOR-3','89863b27-cb3c-4d53-94af-f42161bac3cc'),('8472dfbc-5750-4764-be4e-04ed182dbf86','PLINE-71',NULL,'2019-10-05 03:22:49','null-M-DELL_MONITOR-3',_binary '\0',NULL,'2019-10-05 03:22:49',NULL,'',71,120.00,NULL,'USD',NULL,'M-DELL_MONITOR-3','218dbe9c-71fe-4b86-9b31-3a3fc9eedecd'),('96cf7deb-1b79-4499-a9bf-e658265c982a','PLINE-73',NULL,'2019-10-05 03:22:49','null-M-SONY_HEADSET-1',_binary '\0',NULL,'2019-10-05 03:22:49',NULL,'',73,120.00,NULL,'USD',NULL,'M-SONY_HEADSET-1','218dbe9c-71fe-4b86-9b31-3a3fc9eedecd'),('a7b2b9dd-e58a-414e-b2ec-07d21f3124de','PLINE-93',NULL,'2019-10-05 03:36:08','null-M-DELL_MONITOR-3',_binary '\0',NULL,'2019-10-05 03:36:08',NULL,'',93,1320.00,NULL,'USD',NULL,'M-DELL_MONITOR-3','aa656742-765d-4202-968a-732d1d2e6d93'),('af1024b3-07aa-47e4-bc57-475ebe73e302','PLINE-82',NULL,'2019-10-05 03:30:54','null-M-DELL_PROJECTOR-4',_binary '\0',NULL,'2019-10-05 03:30:54',NULL,'',82,1320.00,NULL,'SGD',NULL,'M-DELL_PROJECTOR-4','89863b27-cb3c-4d53-94af-f42161bac3cc'),('bfeb5a7f-89e7-48a3-8ee8-5d487cc093e1','PLINE-85',NULL,'2019-10-05 03:30:54','null-M-SONY_HEADSET-1',_binary '\0',NULL,'2019-10-05 03:30:54',NULL,'',85,1320.00,NULL,'USD',NULL,'M-SONY_HEADSET-1','89863b27-cb3c-4d53-94af-f42161bac3cc'),('c7c72b94-5efb-4416-8b91-2013c86bd268','PLINE-92',NULL,'2019-10-05 03:36:07','null-M-DELL_PROJECTOR-4',_binary '\0',NULL,'2019-10-05 03:36:08',NULL,'',92,1320.00,NULL,'SGD',NULL,'M-DELL_PROJECTOR-4','aa656742-765d-4202-968a-732d1d2e6d93'),('d04206f4-0b98-46f9-abfa-908b08b7a75b','PLINE-65',NULL,'2019-10-05 03:18:01','null-M-MACBOOK_13-8',_binary '\0',NULL,'2019-10-05 03:18:01',NULL,'',65,104.60,NULL,'USD',NULL,'M-MACBOOK_13-8','f5917493-c610-41f9-8833-7b2cdf3c6347'),('e4db213c-6571-4259-9b11-edf6eef2b864','PLINE-67',NULL,'2019-10-05 03:19:38','null-M-DELL_MONITOR-3',_binary '\0',NULL,'2019-10-05 03:19:39',NULL,'',67,1094.60,NULL,'USD',NULL,'M-DELL_MONITOR-3','bf5cd707-5442-4015-be5a-e611fe9885c9'),('ee8e61f9-4282-4fa4-b6ef-8713465f7442','PLINE-95',NULL,'2019-10-05 03:36:08','null-M-SONY_HEADSET-1',_binary '\0',NULL,'2019-10-05 03:36:08',NULL,'',95,1320.00,NULL,'USD',NULL,'M-SONY_HEADSET-1','aa656742-765d-4202-968a-732d1d2e6d93');
/*!40000 ALTER TABLE `plan_line_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `budget_amt` decimal(19,2) DEFAULT NULL,
  `cost_center_code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `project_owner_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eh3nusutt0qy84a4yr9pfxkyg` (`code`),
  KEY `FK5ncxpise26mery2r3ufh3bhc3` (`project_owner_id`),
  CONSTRAINT `FK5ncxpise26mery2r3ufh3bhc3` FOREIGN KEY (`project_owner_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `relatedbjf` varchar(255) DEFAULT NULL,
  `vendor_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lyhuui3e3rh2a6itktx3rwrpe` (`code`),
  KEY `FK20jcn7pw6hvx0uo0sh4y1d9xv` (`vendor_id`),
  CONSTRAINT `FK20jcn7pw6hvx0uo0sh4y1d9xv` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order_line_item`
--

DROP TABLE IF EXISTS `purchase_order_line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order_line_item` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `merchandise_code` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `purchase_order_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_np63c6aq5t4tmjtkncebvufwi` (`code`),
  KEY `FKoysfy1bhrmvpptpde4agqev8o` (`purchase_order_id`),
  CONSTRAINT `FKoysfy1bhrmvpptpde4agqev8o` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_line_item`
--

LOCK TABLES `purchase_order_line_item` WRITE;
/*!40000 ALTER TABLE `purchase_order_line_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_order_line_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kntnlvx8xwbfrg1flw2mnwa9v` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES ('0e9fd28b-5a48-4d26-bb6c-b6a2836aaf8b','AMER',NULL,'2019-10-03 08:38:23','AMER',_binary '\0',NULL,'2019-10-03 08:38:23','North, Central and South America','',3),('3eda022c-151a-447f-8cf9-0f885044c6c0','APAC',NULL,'2019-10-03 08:36:35','APAC',_binary '\0',NULL,'2019-10-03 08:36:35','Asia Pacific','',1),('d1523fb9-28a4-4f77-840e-6692322c1e09','EMER',NULL,'2019-10-03 08:37:31','EMER',_binary '\0',NULL,'2019-10-03 08:37:31','Europe & Middle-East','',2);
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `end_date_time` datetime DEFAULT NULL,
  `is_recurring` bit(1) NOT NULL,
  `recurring_basis` int(11) DEFAULT NULL,
  `recurring_end_time` time DEFAULT NULL,
  `recurring_start_time` time DEFAULT NULL,
  `start_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mfoqwlcsgf6qa1v6lauq33taq` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_recurring_dates`
--

DROP TABLE IF EXISTS `schedule_recurring_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule_recurring_dates` (
  `schedule_id` varchar(36) NOT NULL,
  `recurring_dates` datetime DEFAULT NULL,
  KEY `FKvj9wc2g7c1v8qy1srjr9c751` (`schedule_id`),
  CONSTRAINT `FKvj9wc2g7c1v8qy1srjr9c751` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_recurring_dates`
--

LOCK TABLES `schedule_recurring_dates` WRITE;
/*!40000 ALTER TABLE `schedule_recurring_dates` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_recurring_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_recurring_weekdays`
--

DROP TABLE IF EXISTS `schedule_recurring_weekdays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule_recurring_weekdays` (
  `schedule_id` varchar(36) NOT NULL,
  `recurring_weekdays` int(11) DEFAULT NULL,
  KEY `FK1ojlbt5d2ifwo0lnywqk4rtqd` (`schedule_id`),
  CONSTRAINT `FK1ojlbt5d2ifwo0lnywqk4rtqd` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_recurring_weekdays`
--

LOCK TABLES `schedule_recurring_weekdays` WRITE;
/*!40000 ALTER TABLE `schedule_recurring_weekdays` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_recurring_weekdays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `original_seat_map_id` varchar(255) DEFAULT NULL,
  `serial_number` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `x_coordinate` int(11) NOT NULL,
  `y_coordinate` int(11) NOT NULL,
  `current_occupancy_id` varchar(36) DEFAULT NULL,
  `function_id` varchar(36) DEFAULT NULL,
  `seatmap_id` varchar(36) DEFAULT NULL,
  `team_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_907nb3oi14ohi2ubfqfk7gj4g` (`code`),
  KEY `FKqy38tt3dx5fcv9yobux397vfb` (`current_occupancy_id`),
  KEY `FKftfjk8c584o4l07673x2eeeog` (`function_id`),
  KEY `FK51m8rpr29qxwxlk99drkafcsf` (`seatmap_id`),
  KEY `FK26anyupg9npcffnhadl66mbh6` (`team_id`),
  CONSTRAINT `FK26anyupg9npcffnhadl66mbh6` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FK51m8rpr29qxwxlk99drkafcsf` FOREIGN KEY (`seatmap_id`) REFERENCES `seat_map` (`id`),
  CONSTRAINT `FKftfjk8c584o4l07673x2eeeog` FOREIGN KEY (`function_id`) REFERENCES `company_function` (`id`),
  CONSTRAINT `FKqy38tt3dx5fcv9yobux397vfb` FOREIGN KEY (`current_occupancy_id`) REFERENCES `seat_allocation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
INSERT INTO `seat` VALUES ('122a9cbd-c3e3-4c6c-b7ff-8e2c2d3966f2','SG-ORQ-26-03',NULL,'2019-10-03 15:24:04','SG-ORQ-26-03',_binary '\0',NULL,'2019-10-05 06:26:00','SG-ORQ-26-03','',3,NULL,3,'HOTDESK',200,0,NULL,'37fdd5aa-25fd-42f2-9736-d226f6c62363','ba78e5e9-b9c7-4d4e-8d4b-e816e53bebc7',NULL),('2322c3b5-74f7-43b8-ae3e-c09a73c49a25','SG-ORQ-26-02',NULL,'2019-10-03 15:24:04','SG-ORQ-26-02',_binary '\0',NULL,'2019-10-05 06:25:59','SG-ORQ-26-02','',2,NULL,2,'HOTDESK',100,0,NULL,'37fdd5aa-25fd-42f2-9736-d226f6c62363','ba78e5e9-b9c7-4d4e-8d4b-e816e53bebc7',NULL),('55034d7b-3fc7-4d5a-bec5-818eff9da626','SG-ORQ-26-04',NULL,'2019-10-03 15:24:04','SG-ORQ-26-04',_binary '\0',NULL,'2019-10-03 15:24:04','SG-ORQ-26-04','',4,NULL,4,'HOTDESK',300,0,NULL,NULL,'ba78e5e9-b9c7-4d4e-8d4b-e816e53bebc7',NULL),('b62d2f07-7e4e-444f-9799-0c463fa04943','SG-ORQ-26-01',NULL,'2019-10-03 15:24:03','SG-ORQ-26-01',_binary '\0',NULL,'2019-10-03 15:24:03','SG-ORQ-26-01','',1,NULL,1,'HOTDESK',0,0,NULL,NULL,'ba78e5e9-b9c7-4d4e-8d4b-e816e53bebc7',NULL);
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_activeallocations`
--

DROP TABLE IF EXISTS `seat_activeallocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_activeallocations` (
  `seat_id` varchar(36) NOT NULL,
  `allocation_id` varchar(36) NOT NULL,
  UNIQUE KEY `UK_ptw7o88vvkijng7bucejlesyo` (`allocation_id`),
  KEY `FKaigjj47d0mlrmxto23y77vkbl` (`seat_id`),
  CONSTRAINT `FK6jyskx51ghsus667cqactnmq8` FOREIGN KEY (`allocation_id`) REFERENCES `seat_allocation` (`id`),
  CONSTRAINT `FKaigjj47d0mlrmxto23y77vkbl` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_activeallocations`
--

LOCK TABLES `seat_activeallocations` WRITE;
/*!40000 ALTER TABLE `seat_activeallocations` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat_activeallocations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_allocation`
--

DROP TABLE IF EXISTS `seat_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_allocation` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `allocation_type` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `employee_id` varchar(36) DEFAULT NULL,
  `schedule_id` varchar(36) DEFAULT NULL,
  `seat_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5kf6g47ahlv0c8vhh6aweprhl` (`code`),
  KEY `FKofpex3yxijqbnrj74yqgkh1mh` (`employee_id`),
  KEY `FK7nuh0aa55cwk074sonekxphd8` (`schedule_id`),
  KEY `FK3q1hpab08qsnaq909xlco7jqe` (`seat_id`),
  CONSTRAINT `FK3q1hpab08qsnaq909xlco7jqe` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`),
  CONSTRAINT `FK7nuh0aa55cwk074sonekxphd8` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
  CONSTRAINT `FKofpex3yxijqbnrj74yqgkh1mh` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_allocation`
--

LOCK TABLES `seat_allocation` WRITE;
/*!40000 ALTER TABLE `seat_allocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat_allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_allocation_inactivation_log`
--

DROP TABLE IF EXISTS `seat_allocation_inactivation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_allocation_inactivation_log` (
  `id` varchar(36) NOT NULL,
  `allocation_id` varchar(255) DEFAULT NULL,
  `completion_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `inactivate_time` datetime DEFAULT NULL,
  `is_done` bit(1) NOT NULL,
  `is_cancelled` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_allocation_inactivation_log`
--

LOCK TABLES `seat_allocation_inactivation_log` WRITE;
/*!40000 ALTER TABLE `seat_allocation_inactivation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat_allocation_inactivation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_inactiveallocations`
--

DROP TABLE IF EXISTS `seat_inactiveallocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_inactiveallocations` (
  `seat_id` varchar(36) NOT NULL,
  `allocation_id` varchar(36) NOT NULL,
  UNIQUE KEY `UK_tey4jai8hi8yfep3qdhqnn2i6` (`allocation_id`),
  KEY `FK6e5ye7pll4hn9awbfsoyhn4x5` (`seat_id`),
  CONSTRAINT `FK6e5ye7pll4hn9awbfsoyhn4x5` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`),
  CONSTRAINT `FKcds4lftont9pxjls1jd5en15g` FOREIGN KEY (`allocation_id`) REFERENCES `seat_allocation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_inactiveallocations`
--

LOCK TABLES `seat_inactiveallocations` WRITE;
/*!40000 ALTER TABLE `seat_inactiveallocations` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat_inactiveallocations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_map`
--

DROP TABLE IF EXISTS `seat_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_map` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `floor` varchar(255) NOT NULL,
  `num_of_seats` int(11) DEFAULT NULL,
  `office_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8p12py2676qx7ljo66i6stjq8` (`code`),
  KEY `FK5fkd1ckmk1d7v1oa4umd0pxoi` (`office_id`),
  CONSTRAINT `FK5fkd1ckmk1d7v1oa4umd0pxoi` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_map`
--

LOCK TABLES `seat_map` WRITE;
/*!40000 ALTER TABLE `seat_map` DISABLE KEYS */;
INSERT INTO `seat_map` VALUES ('ba78e5e9-b9c7-4d4e-8d4b-e816e53bebc7','SG-ORQ-26',NULL,'2019-10-03 15:24:03','SG-ORQ-26',_binary '\0',NULL,'2019-10-03 15:24:03','SG-ORQ-26','',1,'26',4,'10d1a3f7-e410-4b19-9b9a-0cf9563c42fc');
/*!40000 ALTER TABLE `seat_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security_group`
--

DROP TABLE IF EXISTS `security_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_group` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `security_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qyqpccnc9d6wiipw9ilt5irg8` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_group`
--

LOCK TABLES `security_group` WRITE;
/*!40000 ALTER TABLE `security_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `security_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_key`
--

DROP TABLE IF EXISTS `session_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_key` (
  `id` int(11) NOT NULL,
  `last_authenticated` datetime DEFAULT NULL,
  `session_key` varchar(255) DEFAULT NULL,
  `linked_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKksouitr3ab4i1hg37v395beh7` (`linked_user_id`),
  CONSTRAINT `FKksouitr3ab4i1hg37v395beh7` FOREIGN KEY (`linked_user_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_key`
--

LOCK TABLES `session_key` WRITE;
/*!40000 ALTER TABLE `session_key` DISABLE KEYS */;
INSERT INTO `session_key` VALUES (81,'2019-10-04 16:11:25','39e416a2-b5f7-46c8-9246-96e6044a7f77','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),(85,'2019-10-04 16:12:11','ee3f9ae4-d38a-4d8f-bb2d-67afb6da8af0','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(96,'2019-10-04 16:20:33','0704e7af-9859-4dae-8844-5d26d6811423','dd650097-4b59-4dae-8d6f-eec585813750'),(163,'2019-10-04 18:53:38','eb72c7b0-f11f-4bbc-9462-922a8e29d4dd','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(164,'2019-10-04 18:53:41','c802f380-b038-4830-aa9d-da868555b9bd','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(200,'2019-10-04 19:43:41','b1c6d77a-f563-4684-bf0a-a128f38ab06d','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(214,'2019-10-04 21:42:42','be2c1750-6bba-4a6f-a1d1-754e963115fd','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(235,'2019-10-04 22:22:19','9a99743a-39ba-4c7b-8ee9-2d008e630389','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(242,'2019-10-04 22:28:44','e5a0f753-6206-4882-a4c5-8ff5f5fb8aab','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(243,'2019-10-04 22:45:31','c1191c6b-de26-4e0d-8d8f-07ba83cd465e','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(244,'2019-10-04 22:45:38','d730231e-03af-433c-a5de-2b6ccef15894','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(276,'2019-10-05 02:38:57','025fd1b3-6c85-4ed0-82d6-f3948780acf7','dd650097-4b59-4dae-8d6f-eec585813750'),(277,'2019-10-05 02:39:26','b95c4c6e-c685-4db2-b2e7-15369b0051a7','f56ad9c8-e92a-43ff-b101-be4cfa20558f'),(278,'2019-10-05 02:39:40','ad379134-c82c-4d38-9448-41fb53c3e242','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),(309,'2019-10-05 03:05:30','62e23340-8be3-4a58-8d11-a1c58c1d6a81','dd650097-4b59-4dae-8d6f-eec585813750'),(310,'2019-10-05 03:05:32','412e1388-c71b-4963-9ab4-f4c650fc2b2f','dd650097-4b59-4dae-8d6f-eec585813750'),(397,'2019-10-05 06:11:31','1c410a46-f007-4d65-b861-f33c1112eb43','dd650097-4b59-4dae-8d6f-eec585813750'),(398,'2019-10-05 06:13:11','7572dc0d-20f0-491c-a997-690faf59d4a1','dd650097-4b59-4dae-8d6f-eec585813750'),(399,'2019-10-05 06:13:37','f26dfdea-76b2-4d5e-847f-f865e056e2b9','8e0928fe-49ee-48de-ae9d-ba5f855aed56'),(400,'2019-10-05 06:13:56','1d621f21-54e6-46b9-969a-fa660cebda61','dd650097-4b59-4dae-8d6f-eec585813750'),(419,'2019-10-05 06:36:45','9c1eea81-92d4-486a-a28a-cefb2c51bbbb','f56ad9c8-e92a-43ff-b101-be4cfa20558f');
/*!40000 ALTER TABLE `session_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statement_of_acct_line_item`
--

DROP TABLE IF EXISTS `statement_of_acct_line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statement_of_acct_line_item` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `accruals` decimal(19,2) DEFAULT NULL,
  `actual_pmt` decimal(19,2) DEFAULT NULL,
  `paid_amt` decimal(19,2) DEFAULT NULL,
  `schedule_date` date DEFAULT NULL,
  `purchase_order_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hmr5tcr17exdxkm58gj8d5i4i` (`code`),
  KEY `FKj927rxdyy4ito14l74n5mkley` (`purchase_order_id`),
  CONSTRAINT `FKj927rxdyy4ito14l74n5mkley` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statement_of_acct_line_item`
--

LOCK TABLES `statement_of_acct_line_item` WRITE;
/*!40000 ALTER TABLE `statement_of_acct_line_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `statement_of_acct_line_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `country_id` varchar(36) DEFAULT NULL,
  `function_id` varchar(36) DEFAULT NULL,
  `team_leader_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rnln7n3qqo11sl7tkpkrqf78i` (`code`),
  KEY `FKqv6wvrq3qclb3gvo92gg2y6q7` (`country_id`),
  KEY `FKd65pajp3gd3pkechplo5uo35v` (`function_id`),
  KEY `FK2y5sau22rxdtbj85hjdt7psb8` (`team_leader_id`),
  CONSTRAINT `FK2y5sau22rxdtbj85hjdt7psb8` FOREIGN KEY (`team_leader_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKd65pajp3gd3pkechplo5uo35v` FOREIGN KEY (`function_id`) REFERENCES `company_function` (`id`),
  CONSTRAINT `FKqv6wvrq3qclb3gvo92gg2y6q7` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES ('0812bae7-e5bd-11e9-8b11-00155d03b61a','T-FIN-ACCT-SG-6','admin','2019-10-03 17:06:05',NULL,_binary '\0',NULL,NULL,'Accounting','',6,'9b0c3c04-7213-4299-9220-a67b31e342dd','65fff968-a5be-49e9-90a4-6f33928b04da','dd650097-4b59-4dae-8d6f-eec585813750'),('6b5a89b3-e5bc-11e9-8b11-00155d03b61a','T-GCP-SG-1','admin','2019-10-03 17:01:42',NULL,_binary '\0',NULL,NULL,'Google Cloud Platform Singapore','',1,'9b0c3c04-7213-4299-9220-a67b31e342dd','37fdd5aa-25fd-42f2-9736-d226f6c62363','dd650097-4b59-4dae-8d6f-eec585813750'),('9d78623d-e5bc-11e9-8b11-00155d03b61a','T-GCP-HK-2','admin','2019-10-03 17:03:07',NULL,_binary '\0',NULL,NULL,'Google Cloud Platform Hong Kong','',2,'092b323e-81d5-4329-8471-4fd9f389cc46','37fdd5aa-25fd-42f2-9736-d226f6c62363','dd650097-4b59-4dae-8d6f-eec585813750'),('bcfc9d4a-e5bc-11e9-8b11-00155d03b61a','T-DB-HK-3','admin','2019-10-03 17:03:59',NULL,_binary '\0',NULL,NULL,'Data Base HK','',3,'092b323e-81d5-4329-8471-4fd9f389cc46','37fdd5aa-25fd-42f2-9736-d226f6c62363','dd650097-4b59-4dae-8d6f-eec585813750'),('d24c65bc-e5bc-11e9-8b11-00155d03b61a','T-DB-SG-4','admin','2019-10-03 17:04:35',NULL,_binary '\0',NULL,NULL,'Data Base SG','',4,'9b0c3c04-7213-4299-9220-a67b31e342dd','37fdd5aa-25fd-42f2-9736-d226f6c62363','dd650097-4b59-4dae-8d6f-eec585813750'),('eddad274-e5bc-11e9-8b11-00155d03b61a','T-HRCAMPUS-SG-5','admin','2019-10-03 17:05:21',NULL,_binary '\0',NULL,NULL,'Campus Talent Aquisition','',5,'9b0c3c04-7213-4299-9220-a67b31e342dd','b6b0dcb9-0a60-43fc-b464-d1634434c27c','dd650097-4b59-4dae-8d6f-eec585813750');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_employee`
--

DROP TABLE IF EXISTS `team_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_employee` (
  `team_id` varchar(36) NOT NULL,
  `employee_id` varchar(36) NOT NULL,
  KEY `FK7vlhtqnfmyn6egrq2low85sq3` (`employee_id`),
  KEY `FKmwr48kt8uprsismgfgt3r6v8v` (`team_id`),
  CONSTRAINT `FK7vlhtqnfmyn6egrq2low85sq3` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKmwr48kt8uprsismgfgt3r6v8v` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_employee`
--

LOCK TABLES `team_employee` WRITE;
/*!40000 ALTER TABLE `team_employee` DISABLE KEYS */;
INSERT INTO `team_employee` VALUES ('6b5a89b3-e5bc-11e9-8b11-00155d03b61a','235ef575-83ef-4ca0-b779-3a9207d69018'),('6b5a89b3-e5bc-11e9-8b11-00155d03b61a','3c649a80-1cc0-4eff-9d6b-fb55a4a44498');
/*!40000 ALTER TABLE `team_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_vendor`
--

DROP TABLE IF EXISTS `team_vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_vendor` (
  `team_id` varchar(36) NOT NULL,
  `vendor_id` varchar(36) NOT NULL,
  KEY `FKfrdihjbss2sox2v49aw5bnyk5` (`vendor_id`),
  KEY `FKnf3vu5mc7yh97vv435mgny4p2` (`team_id`),
  CONSTRAINT `FKfrdihjbss2sox2v49aw5bnyk5` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`),
  CONSTRAINT `FKnf3vu5mc7yh97vv435mgny4p2` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_vendor`
--

LOCK TABLES `team_vendor` WRITE;
/*!40000 ALTER TABLE `team_vendor` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_securitygroup`
--

DROP TABLE IF EXISTS `user_securitygroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_securitygroup` (
  `employee_id` varchar(36) NOT NULL,
  `securitygroup_id` varchar(36) NOT NULL,
  KEY `FKgr7c9yi300ngubtmclpd0rob1` (`securitygroup_id`),
  KEY `FKqdapy0sm57tbpc7g63swn53vr` (`employee_id`),
  CONSTRAINT `FKgr7c9yi300ngubtmclpd0rob1` FOREIGN KEY (`securitygroup_id`) REFERENCES `security_group` (`id`),
  CONSTRAINT `FKqdapy0sm57tbpc7g63swn53vr` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_securitygroup`
--

LOCK TABLES `user_securitygroup` WRITE;
/*!40000 ALTER TABLE `user_securitygroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_securitygroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date_time` datetime DEFAULT NULL,
  `hierachy_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date_time` datetime DEFAULT NULL,
  `object_name` varchar(255) DEFAULT NULL,
  `permission_map` varchar(255) DEFAULT NULL,
  `seq_no` bigint(20) DEFAULT NULL,
  `billing_contact_email` varchar(255) DEFAULT NULL,
  `billing_contact_name` varchar(255) DEFAULT NULL,
  `escalation_contact_email` varchar(255) DEFAULT NULL,
  `escalation_contact_name` varchar(255) DEFAULT NULL,
  `relationship_manager_email` varchar(255) DEFAULT NULL,
  `relationship_manager_name` varchar(255) DEFAULT NULL,
  `service_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bylgbejpglm23j5mkuwfcwd5o` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES ('08ad83b4-5d10-481d-8704-03762f2c4a51','VENDOR-8-SAMSUNG','yingshi2502','2019-10-03 09:37:12',NULL,_binary '\0','yingshi2502','2019-10-03 09:37:13','Samsung','GRANT,all_user',8,'Samsung@gmail.com','Samsung Billing Name','EscalationStaff@gmail.com','External','Samsung@gmail.com','Samsung Manager','Headset, Desktop devices'),('0d330fed-9741-4182-bb46-c71ca38f53e3','VENDOR-4-STARHUB','yingshi2502','2019-10-03 09:34:38',NULL,_binary '\0','yingshi2502','2019-10-03 09:34:38','Starhub','GRANT,all_user',4,'StarhubBillStaff@gmail.com','Starhub Billing Name','EscalationStaff@gmail.com','External','StarhubManager1@gmail.com','Starhub Manager','Telecom service and Wireless'),('154aec3b-7eef-4f74-bc20-268605ccfd11','VENDOR-2-APPLE','yingshi2502','2019-10-03 09:34:01',NULL,_binary '\0','yingshi2502','2019-10-03 09:34:01','Apple','GRANT,all_user',2,'AppleBillStaff@gmail.com','Apple Billing Name','EscalationStaff@gmail.com','External','AppleManager1@gmail.com','Apple Manager','Mac PC'),('2aaa5be8-f9cd-4674-bb8f-b8c74eeda21a','VENDOR-9-NUS','yingshi2502','2019-10-04 17:10:34',NULL,_binary '\0','yingshi2502','2019-10-04 18:42:32','NUS','GRANT,all_user',9,'a@gmail.com','Huang','aaaa@gmail.com','full name','huangyingshi@gmail.com','Yingshi','Education Institution'),('2f9f20d3-715a-4905-92d8-8c33a97ff06b','VENDOR-7-SONY','yingshi2502','2019-10-03 09:37:01',NULL,_binary '\0','yingshi2502','2019-10-03 09:37:01','Sony','GRANT,all_user',7,'Sony@gmail.com','Sony Billing Name','EscalationStaff@gmail.com','External','Sony@gmail.com','Sony Manager','Headset, Desktop devices'),('4d48be7d-0847-44d6-b7ae-d2bed4c8a68c','VENDOR-10-NTU','yingshi2502','2019-10-04 18:43:46',NULL,_binary '\0','yingshi2502','2019-10-04 18:44:31','NTU','GRANT,all_user',10,'xuhong@gmail.com','Xu Hong','viet@gamil.com','Viet','huangyingshi@gmail.com','Huang Yingshi','Education Institution'),('65b6940e-0f98-4a63-934e-b512b537fd72','VENDOR-11-DUMMY','yingshi2502','2019-10-05 03:48:52',NULL,_binary '\0','yingshi2502','2019-10-05 03:48:52','Dummy','GRANT,all_user',11,'dummy@gmail.com','dummy Biling','dummy@gmail.com','dummy escalation','dummy@gmail.com','dummy RM','Dummy service'),('7a299ce2-0cd3-4c9c-975b-46fb132e5085','VENDOR-6-CISCO','yingshi2502','2019-10-03 09:36:36',NULL,_binary '\0','yingshi2502','2019-10-03 09:36:37','CISCO','GRANT,all_user',6,'CISCO@gmail.com','CISCO Billing Name','EscalationStaff@gmail.com','External','CISCO@gmail.com','CISCO Manager',' IT and networking'),('88554bc4-6eff-4466-8efc-64ffba3a2e17','VENDOR-5-CHALLENGER','yingshi2502','2019-10-03 09:35:08',NULL,_binary '\0','yingshi2502','2019-10-03 09:35:08','Challenger','GRANT,all_user',5,'ChallengerBillStaff@gmail.com','Challenger Billing Name','EscalationStaff@gmail.com','External','ChallengerManager1@gmail.com','Challenger Manager','Hardware provider Singapore'),('b81c911c-839a-477f-bc78-5f2566fa4492','VENDOR-1-DELL','yingshi2502','2019-10-03 09:33:14',NULL,_binary '\0','yingshi2502','2019-10-04 17:09:33','Dell','GRANT,all_user',1,'DellBillStaff@gmail.com','Dell Billing Name2','EscalationStaff@gmail.com','External','DellManager1@gmail.com','Del1 Manager','Windows PC and monitors'),('e88caeb9-d993-4ac9-9ca3-f19131c69a8f','VENDOR-3-SINGTEL','yingshi2502','2019-10-03 09:34:25',NULL,_binary '\0','yingshi2502','2019-10-03 09:34:26','Singtel','GRANT,all_user',3,'SingtelBillStaff@gmail.com','Singtel Billing Name','EscalationStaff@gmail.com','External','SingtelManager1@gmail.com','Singtel Manager','Telecom service and Wireless');
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-05 14:52:24
