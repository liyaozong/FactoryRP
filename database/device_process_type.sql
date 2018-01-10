/*
Navicat MySQL Data Transfer

Source Server         : 47.96.28.88
Source Server Version : 50638
Source Host           : 47.96.28.88:3306
Source Database       : factoryrp

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-01-10 17:25:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_process_type
-- ----------------------------
DROP TABLE IF EXISTS `device_process_type`;
CREATE TABLE `device_process_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `device_process_type` varchar(50) DEFAULT NULL COMMENT '流程类型',
  `order_number` int(11) DEFAULT NULL COMMENT '流程类型排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='设备流程类型表';

-- ----------------------------
-- Records of device_process_type
-- ----------------------------
INSERT INTO `device_process_type` VALUES ('1', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备领用', '1');
INSERT INTO `device_process_type` VALUES ('2', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '故障报修', '2');
INSERT INTO `device_process_type` VALUES ('3', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '外委维修', '3');
INSERT INTO `device_process_type` VALUES ('4', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备购置', '4');
INSERT INTO `device_process_type` VALUES ('5', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备调拨', '5');
INSERT INTO `device_process_type` VALUES ('6', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备报废/封存/状态改变', '6');
INSERT INTO `device_process_type` VALUES ('7', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备领用', '7');
INSERT INTO `device_process_type` VALUES ('8', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备移交', '8');
INSERT INTO `device_process_type` VALUES ('9', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '设备还回', '9');
INSERT INTO `device_process_type` VALUES ('10', '1', '2018-01-10 17:00:23', '2018-01-10 17:00:23', '备件购置', '10');
