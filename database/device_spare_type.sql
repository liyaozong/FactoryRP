/*
Navicat MySQL Data Transfer

Source Server         : 47.96.28.88
Source Server Version : 50638
Source Host           : 47.96.28.88:3306
Source Database       : factoryrp

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2017-12-14 17:26:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_spare_type
-- ----------------------------
DROP TABLE IF EXISTS `device_spare_type`;
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='备件类型表';

-- ----------------------------
-- Records of device_spare_type
-- ----------------------------
INSERT INTO `device_spare_type` VALUES ('5', '1', '2017-12-14 17:20:46', '2017-12-14 17:20:46', '气动配件', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('6', '1', '2017-12-14 17:22:01', '2017-12-14 17:22:01', '机械配件', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('8', '1', '2017-12-14 17:23:48', '2017-12-14 17:23:48', '轴承类', '6', '0', '1');
INSERT INTO `device_spare_type` VALUES ('9', '1', '2017-12-14 17:24:03', '2017-12-14 17:24:03', '专用类', '6', '0', '1');
INSERT INTO `device_spare_type` VALUES ('10', '1', '2017-12-14 17:24:16', '2017-12-14 17:24:16', '工具', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('11', '1', '2017-12-14 17:24:21', '2017-12-14 17:24:21', '油类', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('12', '1', '2017-12-14 17:24:26', '2017-12-14 17:24:26', '低值易耗品', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('13', '1', '2017-12-14 17:24:33', '2017-12-14 17:24:33', '办公配件', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('14', '1', '2017-12-14 17:24:39', '2017-12-14 17:24:39', '劳保', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('15', '1', '2017-12-14 17:24:43', '2017-12-14 17:24:43', '厂务', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('16', '1', '2017-12-14 17:24:48', '2017-12-14 17:24:48', '车辆', '-1', '0', '1');
INSERT INTO `device_spare_type` VALUES ('17', '1', '2017-12-14 17:24:52', '2017-12-14 17:24:52', '其它', '-1', '0', '1');
