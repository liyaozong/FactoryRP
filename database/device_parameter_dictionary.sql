/*
Navicat MySQL Data Transfer

Source Server         : LoalMySQL
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : factoryrp

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2017-12-14 15:56:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_parameter_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `device_parameter_dictionary`;
CREATE TABLE `device_parameter_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT 'createTime',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `code` varchar(50) DEFAULT NULL COMMENT '设备参数code，区分不同种类的设备参数 code值可以相同',
  `name` varchar(20) DEFAULT NULL COMMENT '设备参数name，同一个code下面的name不可相同',
  `type` int(20) DEFAULT NULL COMMENT '对应枚举的类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_parameter_dictionary
-- ----------------------------
INSERT INTO `device_parameter_dictionary` VALUES ('6', '1', '2017-12-13 12:04:19', '2017-12-13 12:04:19', 'device_measuring_unit', '个', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('7', '1', '2017-12-13 12:04:36', '2017-12-13 12:04:36', 'device_measuring_unit', '台', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('8', '1', '2017-12-13 12:04:45', '2017-12-13 12:04:45', 'device_measuring_unit', '条', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('9', '1', '2017-12-13 12:04:58', '2017-12-13 12:04:58', 'device_measuring_unit', '箱', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('10', '1', '2017-12-13 12:05:41', '2017-12-13 12:05:41', 'device_measuring_unit', '块', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('11', '1', '2017-12-13 12:05:57', '2017-12-13 12:05:57', 'device_measuring_unit', '套', '6');
INSERT INTO `device_parameter_dictionary` VALUES ('12', '1', '2017-12-13 12:06:07', '2017-12-13 12:06:07', 'device_measuring_unit', '次', '7');
INSERT INTO `device_parameter_dictionary` VALUES ('14', '1', '2017-12-13 14:41:30', '2017-12-13 14:41:30', 'device_measuring_unit', '辆', '8');
INSERT INTO `device_parameter_dictionary` VALUES ('15', '1', '2017-12-14 14:18:38', '2017-12-14 14:18:38', 'device_use_status', '停机待修', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('16', '1', '2017-12-14 14:19:16', '2017-12-14 14:19:16', 'device_use_status', '带病运行', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('17', '1', '2017-12-14 14:19:34', '2017-12-14 14:19:34', 'device_use_status', '其它', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('18', '1', '2017-12-14 14:20:21', '2017-12-14 14:20:21', 'device_use_status', '在用', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('19', '1', '2017-12-14 14:20:27', '2017-12-14 14:20:27', 'device_use_status', '待检', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('20', '1', '2017-12-14 14:20:39', '2017-12-14 14:20:39', 'device_use_status', '停用', '6');
INSERT INTO `device_parameter_dictionary` VALUES ('21', '1', '2017-12-14 14:20:45', '2017-12-14 14:20:45', 'device_use_status', '报废', '7');
INSERT INTO `device_parameter_dictionary` VALUES ('22', '1', '2017-12-14 14:20:52', '2017-12-14 14:20:52', 'device_use_status', '未验收入账', '8');
INSERT INTO `device_parameter_dictionary` VALUES ('23', '1', '2017-12-14 14:48:10', '2017-12-14 14:48:10', 'device_trouble_level', '紧急', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('24', '1', '2017-12-14 14:48:20', '2017-12-14 14:48:20', 'device_trouble_level', '一般', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('25', '1', '2017-12-14 14:48:27', '2017-12-14 14:48:27', 'device_trouble_level', '其他', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('26', '1', '2017-12-14 15:04:05', '2017-12-14 15:04:05', 'device_trouble_reason', '自然磨损', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('27', '1', '2017-12-14 15:04:12', '2017-12-14 15:04:12', 'device_trouble_reason', '违章操作', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('28', '1', '2017-12-14 15:04:17', '2017-12-14 15:04:17', 'device_trouble_reason', '配件质量差', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('29', '1', '2017-12-14 15:04:23', '2017-12-14 15:04:23', 'device_trouble_reason', '维护保养不到位', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('30', '1', '2017-12-14 15:04:28', '2017-12-14 15:04:28', 'device_trouble_reason', '其他', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('31', '1', '2017-12-14 15:06:50', '2017-12-14 15:06:50', 'device_repair_level', '常见故障维修', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('32', '1', '2017-12-14 15:07:01', '2017-12-14 15:07:01', 'device_repair_level', '突发性故障维修', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('33', '1', '2017-12-14 15:07:14', '2017-12-14 15:07:14', 'device_repair_level', '计划项目维修', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('34', '1', '2017-12-14 15:07:21', '2017-12-14 15:07:21', 'device_repair_level', '不正当使用维修', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('35', '1', '2017-12-14 15:09:46', '2017-12-14 15:09:46', 'device_maintenance_level', '日常保养', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('36', '1', '2017-12-14 15:09:53', '2017-12-14 15:09:53', 'device_maintenance_level', '常规润滑', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('37', '1', '2017-12-14 15:09:59', '2017-12-14 15:09:59', 'device_maintenance_level', '二级检修保养', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('38', '1', '2017-12-14 15:10:07', '2017-12-14 15:10:07', 'device_maintenance_level', '三级检修保养', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('39', '1', '2017-12-14 15:10:12', '2017-12-14 15:10:12', 'device_maintenance_level', '项目检修保养', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('40', '1', '2017-12-14 15:10:18', '2017-12-14 15:10:18', 'device_maintenance_level', '年度检修保养', '6');
INSERT INTO `device_parameter_dictionary` VALUES ('41', '1', '2017-12-14 15:13:15', '2017-12-14 15:13:15', 'device_device_flag', '重点设备', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('42', '1', '2017-12-14 15:13:22', '2017-12-14 15:13:22', 'device_device_flag', '主要设备', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('43', '1', '2017-12-14 15:13:27', '2017-12-14 15:13:27', 'device_device_flag', '一般设备', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('44', '1', '2017-12-14 15:13:31', '2017-12-14 15:13:31', 'device_device_flag', 'AAA类', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('45', '1', '2017-12-14 15:13:37', '2017-12-14 15:13:37', 'device_device_flag', 'B类', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('46', '1', '2017-12-14 15:15:35', '2017-12-14 15:15:35', 'device_bad_review_reason', '返修率高，不能一次处理完好。', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('47', '1', '2017-12-14 15:15:45', '2017-12-14 15:15:45', 'device_bad_review_reason', '维修速度慢。', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('48', '1', '2017-12-14 15:15:50', '2017-12-14 15:15:50', 'device_bad_review_reason', '技术能力差。', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('49', '1', '2017-12-14 15:15:58', '2017-12-14 15:15:58', 'device_bad_review_reason', '野蛮处理维修事务。', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('50', '1', '2017-12-14 15:16:04', '2017-12-14 15:16:04', 'device_bad_review_reason', '不能兑现承诺维修时间', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('51', '1', '2017-12-14 15:16:09', '2017-12-14 15:16:09', 'device_bad_review_reason', '维修质量差', '6');
INSERT INTO `device_parameter_dictionary` VALUES ('52', '1', '2017-12-14 15:16:14', '2017-12-14 15:16:14', 'device_bad_review_reason', '存在安全隐患', '7');
INSERT INTO `device_parameter_dictionary` VALUES ('53', '1', '2017-12-14 15:16:21', '2017-12-14 15:16:21', 'device_bad_review_reason', '服务态度差', '8');
INSERT INTO `device_parameter_dictionary` VALUES ('54', '1', '2017-12-14 15:16:28', '2017-12-14 15:16:28', 'device_bad_review_reason', '其他', '9');
