/*
Navicat MySQL Data Transfer

Source Server         : 39.104.71.127
Source Server Version : 50638
Source Host           : 39.104.71.127:3306
Source Database       : factoryrp

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-01-03 11:03:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contact_company_info
-- ----------------------------
DROP TABLE IF EXISTS `contact_company_info`;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='往来单位信息';

-- ----------------------------
-- Records of contact_company_info
-- ----------------------------

-- ----------------------------
-- Table structure for department_info
-- ----------------------------
DROP TABLE IF EXISTS `department_info`;
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='部门信息';

-- ----------------------------
-- Records of department_info
-- ----------------------------
INSERT INTO `department_info` VALUES ('3', '003', '北京分公司', '-1', '999', '1', '1', '2017-11-16 08:29:27', '2017-12-01 23:43:51');
INSERT INTO `department_info` VALUES ('9', '01', '成都分公司', '-1', '999', '1', '1', '2017-12-02 14:30:08', '2017-12-02 15:38:31');
INSERT INTO `department_info` VALUES ('10', '02', '上海分公司', '-1', '999', '1', '1', '2017-12-02 14:38:11', '2017-12-02 15:38:33');
INSERT INTO `department_info` VALUES ('17', '1', '上海', '-1', '999', '32132132132213', '1', '2017-12-02 15:40:41', '2017-12-02 15:41:23');
INSERT INTO `department_info` VALUES ('22', '0002', '南充分公司', '9', '999', '1', '1', '2017-12-03 10:01:02', '2017-12-03 15:48:02');
INSERT INTO `department_info` VALUES ('24', '0003', 'nazhong', '22', '999', '1', '1', '2017-12-03 15:51:06', '2017-12-03 15:51:34');
INSERT INTO `department_info` VALUES ('25', '00003', '遂宁', '9', '999', '1', '1', '2017-12-03 16:04:50', '2017-12-03 16:04:59');
INSERT INTO `department_info` VALUES ('28', '00005', '绵竹分公司', '9', '999', '1', '1', '2017-12-03 17:52:26', '2017-12-03 17:52:26');
INSERT INTO `department_info` VALUES ('31', '888', '四川分公司', null, '999', '1', '1', '2017-12-16 19:36:39', '2017-12-16 19:36:39');

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_info
-- ----------------------------

-- ----------------------------
-- Table structure for device_info_extend_field
-- ----------------------------
DROP TABLE IF EXISTS `device_info_extend_field`;
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

-- ----------------------------
-- Records of device_info_extend_field
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='设备参数表';

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
INSERT INTO `device_parameter_dictionary` VALUES ('56', '1', '2017-12-27 17:21:22', '2017-12-27 17:21:22', 'device_process_type', '故障报修', '1');
INSERT INTO `device_parameter_dictionary` VALUES ('57', '1', '2017-12-27 17:31:50', '2017-12-27 17:31:50', 'device_process_type', '设备调拨', '2');
INSERT INTO `device_parameter_dictionary` VALUES ('58', '1', '2017-12-27 17:32:04', '2017-12-27 17:32:04', 'device_process_type', '设备报废/封存/状态改变', '3');
INSERT INTO `device_parameter_dictionary` VALUES ('59', '1', '2017-12-27 17:32:14', '2017-12-27 17:32:14', 'device_process_type', '设备领用', '4');
INSERT INTO `device_parameter_dictionary` VALUES ('60', '1', '2017-12-27 17:32:23', '2017-12-27 17:32:23', 'device_process_type', '设备移交', '5');
INSERT INTO `device_parameter_dictionary` VALUES ('61', '1', '2017-12-27 17:32:34', '2017-12-27 17:32:34', 'device_process_type', '设备还回', '6');
INSERT INTO `device_parameter_dictionary` VALUES ('62', '1', '2017-12-27 17:32:46', '2017-12-27 17:32:46', 'device_process_type', '备件购置', '7');

-- ----------------------------
-- Table structure for device_process
-- ----------------------------
DROP TABLE IF EXISTS `device_process`;
CREATE TABLE `device_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `process_name` varchar(20) DEFAULT NULL COMMENT '流程名称',
  `process_remark` varchar(255) DEFAULT NULL COMMENT '流程备注',
  `process_stage` bigint(20) DEFAULT NULL COMMENT '流程阶段',
  `process_type` bigint(20) DEFAULT NULL COMMENT '流程类型',
  `trigger_condition` bigint(20) DEFAULT NULL COMMENT '条件详情',
  `trigger_condition_type` bigint(20) DEFAULT NULL COMMENT '触发条件类型 对应设备类型 金额上限 部门等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='设备流程实体';

-- ----------------------------
-- Records of device_process
-- ----------------------------
INSERT INTO `device_process` VALUES ('1', '1', '2017-12-27 17:12:24', '2017-12-27 17:12:24', '测试流程123', '1', '1', '1', '1', '1');
INSERT INTO `device_process` VALUES ('2', '1', '2017-12-27 17:14:16', '2017-12-27 17:14:16', '今天的流程', '1', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for device_process_detail
-- ----------------------------
DROP TABLE IF EXISTS `device_process_detail`;
CREATE TABLE `device_process_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `process_type` bigint(20) DEFAULT NULL COMMENT '审核类型 标识是指定审核人还是指定审核组',
  `handle_demand_type` bigint(20) DEFAULT NULL COMMENT '处理要求类型 1单人签署生效2多人签署生效',
  `process_auditor` bigint(20) DEFAULT NULL COMMENT '审核人',
  `process_step` bigint(20) DEFAULT NULL COMMENT '流程步骤 对于同一个流程，依次递增',
  `process_id` bigint(20) DEFAULT NULL COMMENT 'process_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备流程详情表';

-- ----------------------------
-- Records of device_process_detail
-- ----------------------------

-- ----------------------------
-- Table structure for device_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `device_process_instance`;
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

-- ----------------------------
-- Records of device_process_instance
-- ----------------------------

-- ----------------------------
-- Table structure for device_process_runtime_info
-- ----------------------------
DROP TABLE IF EXISTS `device_process_runtime_info`;
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

-- ----------------------------
-- Records of device_process_runtime_info
-- ----------------------------

-- ----------------------------
-- Table structure for device_spare_part_rel
-- ----------------------------
DROP TABLE IF EXISTS `device_spare_part_rel`;
CREATE TABLE `device_spare_part_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL COMMENT '设备主键',
  `spare_part_id` bigint(20) NOT NULL COMMENT '备件主键',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='设备—备件关联信息';

-- ----------------------------
-- Records of device_spare_part_rel
-- ----------------------------
INSERT INTO `device_spare_part_rel` VALUES ('6', '7', '2', '1', '2017-12-02 14:14:19', '2017-12-02 14:14:19');

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='备件类型表';

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
INSERT INTO `device_spare_type` VALUES ('19', '1', '2017-12-25 21:01:03', '2017-12-25 21:01:03', '轴承同级', '6', '999', '1');
INSERT INTO `device_spare_type` VALUES ('20', '1', '2017-12-25 21:01:24', '2017-12-25 21:01:24', '轴承同级下级', '19', '999', '1');

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

-- ----------------------------
-- Table structure for device_type
-- ----------------------------
DROP TABLE IF EXISTS `device_type`;
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='设备类型信息';

-- ----------------------------
-- Records of device_type
-- ----------------------------
INSERT INTO `device_type` VALUES ('9', '打印类', '-1', '999', '1', '1', '2017-11-22 19:55:17', '2017-12-01 23:44:16');
INSERT INTO `device_type` VALUES ('19', '打印同级11', '-1', '999', '1', '1', '2017-12-17 22:12:19', '2017-12-17 22:12:19');
INSERT INTO `device_type` VALUES ('24', '打印下级11', '9', '999', '1', '1', '2017-12-18 01:48:32', '2017-12-18 01:48:32');
INSERT INTO `device_type` VALUES ('26', '打印同级下级', '19', '999', '1', '1', '2017-12-18 01:50:11', '2017-12-18 01:50:11');
INSERT INTO `device_type` VALUES ('27', '印刷', '-1', '999', '1', '1', '2017-12-18 01:54:25', '2017-12-18 01:54:25');
INSERT INTO `device_type` VALUES ('28', '印刷下级1122', '27', '999', '1', '1', '2017-12-18 01:54:36', '2017-12-18 01:54:36');
INSERT INTO `device_type` VALUES ('29', '打印下级下级', '24', '999', '1', '1', '2017-12-18 02:03:21', '2017-12-18 02:03:21');
INSERT INTO `device_type` VALUES ('30', '测试空数据', '-1', '999', '1', '1', '2017-12-18 20:56:00', '2017-12-18 20:56:00');
INSERT INTO `device_type` VALUES ('31', '启动配件下级', '5', '999', '1', '1', '2017-12-25 20:50:29', '2017-12-25 20:50:29');
INSERT INTO `device_type` VALUES ('32', '气动配件下级1', '5', '999', '1', '1', '2017-12-25 20:52:44', '2017-12-25 20:52:44');
INSERT INTO `device_type` VALUES ('33', '印刷下级2222', '27', '999', '1', '1', '2017-12-25 21:09:47', '2017-12-25 21:09:47');
INSERT INTO `device_type` VALUES ('34', '测试', '-1', '999', '1', '1', '2017-12-25 21:10:03', '2017-12-25 21:10:03');

-- ----------------------------
-- Table structure for repair_group_info
-- ----------------------------
DROP TABLE IF EXISTS `repair_group_info`;
CREATE TABLE `repair_group_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(10) DEFAULT NULL COMMENT '维修工段/班组编码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '维修工段/班组名称',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='维修班组／工段基础信息';

-- ----------------------------
-- Records of repair_group_info
-- ----------------------------

-- ----------------------------
-- Table structure for repair_record
-- ----------------------------
DROP TABLE IF EXISTS `repair_record`;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='故障维修单';

-- ----------------------------
-- Records of repair_record
-- ----------------------------

-- ----------------------------
-- Table structure for repair_record_spare_part_rel
-- ----------------------------
DROP TABLE IF EXISTS `repair_record_spare_part_rel`;
CREATE TABLE `repair_record_spare_part_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='配件更换信息';

-- ----------------------------
-- Records of repair_record_spare_part_rel
-- ----------------------------

-- ----------------------------
-- Table structure for spare_parts
-- ----------------------------
DROP TABLE IF EXISTS `spare_parts`;
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
  `name` varchar(20) DEFAULT NULL COMMENT '备件名称',
  `reference_price` double DEFAULT NULL COMMENT '参考价',
  `replacement_cycle` int(11) DEFAULT NULL COMMENT '更换周期',
  `spare_part_type` bigint(20) DEFAULT NULL COMMENT '备件类型',
  `specifications_andodels` varchar(20) DEFAULT NULL COMMENT '规格型号',
  `suppliers` bigint(20) DEFAULT NULL COMMENT '供应商',
  `unit_conversion` bigint(20) DEFAULT NULL COMMENT '换算单位',
  `deviceinfo_id` bigint(20) DEFAULT NULL COMMENT '关联设备Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of spare_parts
-- ----------------------------

-- ----------------------------
-- Table structure for system_corporate
-- ----------------------------
DROP TABLE IF EXISTS `system_corporate`;
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

-- ----------------------------
-- Records of system_corporate
-- ----------------------------
INSERT INTO `system_corporate` VALUES ('1', '2017-11-20 13:48:07', '2017-11-20 13:48:07', '1', '阿里巴巴', '1', 'ESSAE86');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `order_number` int(11) DEFAULT NULL COMMENT '菜单排序号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单Id 若为0则为一级菜单',
  `remark` varchar(50) DEFAULT NULL COMMENT '菜单备注',
  `url` varchar(20) DEFAULT NULL COMMENT '菜单访问URL',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('2', '2017-12-02 13:31:11', '2017-12-02 13:31:11', '测试版本', '2', null, '132', '312', '32132132132213');
INSERT INTO `system_menu` VALUES ('3', '2017-12-02 13:39:38', '2017-12-02 13:39:38', '权限管理', '0', null, '权限管理', 'main', '32132132132213');
INSERT INTO `system_menu` VALUES ('4', '2017-12-02 13:48:36', '2017-12-02 13:48:36', '用户管理', '0', '3', '用户管理', 'main.userManagement', '32132132132213');
INSERT INTO `system_menu` VALUES ('5', '2017-12-10 23:17:53', '2017-12-10 23:17:53', '测试', '23', '1', 'dsa', 'main.yu', '32132132132213');
INSERT INTO `system_menu` VALUES ('6', '2017-12-12 20:22:13', '2017-12-12 20:22:13', '呵呵', '1', null, '首页', 'main.home', '1');
INSERT INTO `system_menu` VALUES ('7', '2017-12-12 20:22:46', '2017-12-12 20:22:46', '呵呵哈', '1', '6', '首页1', 'main.home1', '1');
INSERT INTO `system_menu` VALUES ('10', '2017-12-18 21:08:14', '2017-12-18 21:08:14', '测试', '3', '7', '呵呵哈下级', 'main/test', '1');
INSERT INTO `system_menu` VALUES ('11', '2017-12-18 23:30:30', '2017-12-18 23:30:30', '菜单A', '1', '5', '阿里巴巴集团某菜单', '/api/user/list', '1');
INSERT INTO `system_menu` VALUES ('12', '2017-12-19 21:57:25', '2017-12-19 21:57:25', 'dd', '5', '7', 'qq', '321', '1');
INSERT INTO `system_menu` VALUES ('14', '2017-12-20 00:50:00', '2017-12-20 00:50:00', '1级', '2', null, '1', 'main/main', '1');
INSERT INTO `system_menu` VALUES ('15', '2017-12-20 00:53:12', '2017-12-20 00:53:12', '2级', '2', '14', '2', 'main/main', '1');
INSERT INTO `system_menu` VALUES ('16', '2017-12-20 23:05:00', '2017-12-20 23:05:00', '测试下级', '1', '10', '测试下级', 'test/test', '1');
INSERT INTO `system_menu` VALUES ('17', '2017-12-20 23:08:14', '2017-12-20 23:08:14', '3级', '3', '15', 'test', 'test/test', '1');
INSERT INTO `system_menu` VALUES ('18', '2017-12-20 23:10:08', '2017-12-20 23:10:08', '哈哈下级', '2', '8', 'hha', 'haha', '1');
INSERT INTO `system_menu` VALUES ('20', '2017-12-23 00:25:08', '2017-12-23 00:25:08', '打完', '123', '9', '123', 'mian', '1');

-- ----------------------------
-- Table structure for system_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `system_menu_role`;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu_role
-- ----------------------------
INSERT INTO `system_menu_role` VALUES ('3', '1', '2017-12-21 22:25:09', '2017-12-21 22:25:09', '3', '描述1', '3');
INSERT INTO `system_menu_role` VALUES ('8', '1', '2017-12-22 23:28:00', '2017-12-22 23:28:00', '7', null, '3');
INSERT INTO `system_menu_role` VALUES ('10', '1', '2017-12-22 23:39:35', '2017-12-22 23:39:35', '11', null, '3');
INSERT INTO `system_menu_role` VALUES ('11', '1', '2017-12-22 23:39:35', '2017-12-22 23:39:35', '17', null, '3');
INSERT INTO `system_menu_role` VALUES ('13', '1', '2017-12-23 00:08:52', '2017-12-23 00:08:52', '16', null, '3');

-- ----------------------------
-- Table structure for system_permission
-- ----------------------------
DROP TABLE IF EXISTS `system_permission`;
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

-- ----------------------------
-- Records of system_permission
-- ----------------------------
INSERT INTO `system_permission` VALUES ('1', '2017-11-16 18:08:42', '2017-11-16 18:08:46', '1', '1', '1', '1', '1', null);

-- ----------------------------
-- Table structure for system_permission_role
-- ----------------------------
DROP TABLE IF EXISTS `system_permission_role`;
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

-- ----------------------------
-- Records of system_permission_role
-- ----------------------------
INSERT INTO `system_permission_role` VALUES ('5', '2017-11-16 18:09:45', '2017-11-16 18:09:48', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业标识',
  `enable_status` int(11) DEFAULT NULL COMMENT '是否启用 1启用2不启用',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色code',
  `role_description` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('2', '2017-12-04 21:03:28', '2017-12-04 21:03:28', '32132132132213', '1', '123', 'alibaba的管理员角色', '管理员角色');
INSERT INTO `system_role` VALUES ('3', '2017-12-18 23:30:14', '2017-12-18 23:30:14', '1', '1', '123', 'alibaba的管理员角色', '管理员角色');
INSERT INTO `system_role` VALUES ('4', '2017-12-21 20:01:54', '2017-12-21 20:01:54', '1', '1', '31321', 'alibaba的管理员角色', '管理员角色');
INSERT INTO `system_role` VALUES ('5', '2017-12-21 20:02:13', '2017-12-21 20:02:13', '1', '1', '321', 'alibaba的管理员角色', '管理员角色');
INSERT INTO `system_role` VALUES ('6', '2017-12-21 22:38:28', '2017-12-21 22:38:28', '1', null, '1234', '测试新增角色', '角色名');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', '2017-11-16 18:08:42', '2017-11-16 18:08:46', '1', '06a93c778754105925f63056c79b1d247a4088c975c5c50b', '6375026571448750', '1', '张三', 'ESSAE86');
INSERT INTO `system_user` VALUES ('9', '2017-12-19 10:21:33', '2017-12-19 10:21:33', '1', '66f285a1159646e15967e866786733874b20a6840272d86a', '6819657683726026', '942942952127201281', '企业用户6666', 'ESSAE86');
INSERT INTO `system_user` VALUES ('10', '2017-12-19 21:40:20', '2017-12-19 21:40:20', '1', '961b6e113b09611f93085d4803289da91c2f46bb0d74ce89', '6610198439926048', '943113773294551041', '李四', 'ESSAE86');

-- ----------------------------
-- Table structure for system_user_department
-- ----------------------------
DROP TABLE IF EXISTS `system_user_department`;
CREATE TABLE `system_user_department` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `corporate_identify` bigint(20) DEFAULT NULL COMMENT '企业唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user_department
-- ----------------------------

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='用户->角色中间表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES ('32', '2017-12-21 21:02:36', '2017-12-21 21:02:36', '1', '2', '10');
INSERT INTO `system_user_role` VALUES ('33', '2017-12-21 21:02:36', '2017-12-21 21:02:36', '1', '5', '10');
INSERT INTO `system_user_role` VALUES ('34', '2017-12-21 21:40:02', '2017-12-21 21:40:02', '1', '3', '10');
INSERT INTO `system_user_role` VALUES ('35', '2017-12-22 00:13:17', '2017-12-22 00:13:17', '1', '3', '1');
INSERT INTO `system_user_role` VALUES ('36', '2017-12-22 00:13:17', '2017-12-22 00:13:17', '1', '4', '1');

-- ----------------------------
-- Table structure for trouble_record
-- ----------------------------
DROP TABLE IF EXISTS `trouble_record`;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='故障信息';

-- ----------------------------
-- Records of trouble_record
-- ----------------------------

-- ----------------------------
-- Table structure for work_time
-- ----------------------------
DROP TABLE IF EXISTS `work_time`;
CREATE TABLE `work_time` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `repair_record_id` bigint(20) unsigned NOT NULL COMMENT '维修单主键',
  `repair_user_id` bigint(20) DEFAULT NULL COMMENT '维修人员主键',
  `repair_user_name` varchar(10) DEFAULT '' COMMENT '维修人员姓名',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `cost_hour` varchar(10) DEFAULT NULL COMMENT '维修耗时',
  `corporate_identify` bigint(20) NOT NULL COMMENT '企业唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='维修工时信息';

-- ----------------------------
-- Records of work_time
-- ----------------------------
