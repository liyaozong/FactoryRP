/*
Navicat MySQL Data Transfer

Source Server         : 47.96.28.88
Source Server Version : 50638
Source Host           : 47.96.28.88:3306
Source Database       : factoryrp

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-01-10 17:24:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_process_phase
-- ----------------------------
DROP TABLE IF EXISTS `device_process_phase`;
CREATE TABLE `device_process_phase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '企业唯一标识主键',
  `corporate_identify` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `device_process_phase` varchar(50) DEFAULT NULL COMMENT '流程阶段',
  `device_process_type_id` bigint(20) DEFAULT NULL COMMENT '用于管理效能device_process_type表主键的id',
  `order_number` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`),
  KEY `FKbwll8fxfi2cyipgae193l8bvi` (`device_process_type_id`),
  CONSTRAINT `FKbwll8fxfi2cyipgae193l8bvi` FOREIGN KEY (`device_process_type_id`) REFERENCES `device_process_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='流程阶段表';

-- ----------------------------
-- Records of device_process_phase
-- ----------------------------
INSERT INTO `device_process_phase` VALUES ('1', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '1', '1');
INSERT INTO `device_process_phase` VALUES ('2', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核12123', '1', '2');
INSERT INTO `device_process_phase` VALUES ('3', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '维修结束待验证', '2', '3');
INSERT INTO `device_process_phase` VALUES ('4', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '3', '4');
INSERT INTO `device_process_phase` VALUES ('5', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '维修结束待验证', '3', '5');
INSERT INTO `device_process_phase` VALUES ('6', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '2', '6');
INSERT INTO `device_process_phase` VALUES ('7', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '4', '7');
INSERT INTO `device_process_phase` VALUES ('8', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '5', '8');
INSERT INTO `device_process_phase` VALUES ('9', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '6', '9');
INSERT INTO `device_process_phase` VALUES ('10', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '7', '10');
INSERT INTO `device_process_phase` VALUES ('11', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '8', '11');
INSERT INTO `device_process_phase` VALUES ('12', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '维修结束待验证', '9', '12');
INSERT INTO `device_process_phase` VALUES ('13', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '9', '13');
INSERT INTO `device_process_phase` VALUES ('14', '1', '2018-01-10 17:08:16', '2018-01-10 17:08:16', '申请审核', '10', '14');
