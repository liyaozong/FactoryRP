/*
Navicat MySQL Data Transfer

Source Server         : 47.96.28.88
Source Server Version : 50638
Source Host           : 47.96.28.88:3306
Source Database       : factoryrp

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2017-12-14 17:06:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_trouble_type
-- ----------------------------
DROP TABLE IF EXISTS `device_trouble_type`;
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

-- ----------------------------
-- Records of device_trouble_type
-- ----------------------------
INSERT INTO `device_trouble_type` VALUES ('5', '1', '2017-12-14 17:02:27', '2017-12-14 17:02:27', '机械故障', '-1', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('6', '1', '2017-12-14 17:02:51', '2017-12-14 17:02:51', '部件磨损', '5', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('7', '1', '2017-12-14 17:03:08', '2017-12-14 17:03:08', '锈蚀卡死', '5', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('8', '1', '2017-12-14 17:03:14', '2017-12-14 17:03:14', '密封件泄漏', '5', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('9', '1', '2017-12-14 17:03:45', '2017-12-14 17:03:45', '电气故障', '-1', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('10', '1', '2017-12-14 17:04:02', '2017-12-14 17:04:02', '电路故障', '9', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('11', '1', '2017-12-14 17:04:12', '2017-12-14 17:04:12', '控制阀故障', '9', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('12', '1', '2017-12-14 17:04:19', '2017-12-14 17:04:19', '气路接头故障', '9', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('13', '1', '2017-12-14 17:04:33', '2017-12-14 17:04:33', '物料原因故障', '-1', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('14', '1', '2017-12-14 17:04:42', '2017-12-14 17:04:42', '能源供给故障', '-1', '999', '1');
INSERT INTO `device_trouble_type` VALUES ('15', '1', '2017-12-14 17:04:48', '2017-12-14 17:04:48', '其它故障', '-1', '999', '1');
