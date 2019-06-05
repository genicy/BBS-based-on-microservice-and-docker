/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_recruit

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:27:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `tb_enterprise`;
CREATE TABLE `tb_enterprise` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '企业名称',
  `summary` varchar(1000) DEFAULT NULL COMMENT '企业简介',
  `address` varchar(100) DEFAULT NULL COMMENT '企业地址',
  `labels` varchar(100) DEFAULT NULL COMMENT '标签列表',
  `coordinate` varchar(100) DEFAULT NULL COMMENT '坐标',
  `ishot` varchar(1) DEFAULT NULL COMMENT '是否热门',
  `logo` varchar(100) DEFAULT NULL COMMENT 'LOGO',
  `jobcount` int(11) DEFAULT NULL COMMENT '职位数',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业';

-- ----------------------------
-- Records of tb_enterprise
-- ----------------------------
INSERT INTO `tb_enterprise` VALUES ('1', '百度', '成为最懂用户，并能帮助人们成长的全球顶级高科技公司。', '北京市朝阳区', '人工智能；中文搜索', '106.70228,26.556212', '1', null, null, null);
INSERT INTO `tb_enterprise` VALUES ('555341769831747584', '字节跳动', '字节跳动成立于2012年3月，是最早把人工智能技术大规模应用于信息分发的公司之一。\n随着大众用户的阅读行为广泛向移动设备迁移，字节跳动帮助内容创造者们更方便的进行内容的分发，助力各类媒体更好地适应移动互联网时代。', '北京市海淀区', '人工智能;算法', '116.338977,39.974608', null, 'http://localhost:8004/cover/5298b805-198f-4890-bd07-fa56d733a3e5bytedance.JPG', null, null);
INSERT INTO `tb_enterprise` VALUES ('569536972935270400', '阿里巴巴', '阿里巴巴', null, '阿里巴巴', '120.011082,30.268284', null, 'http://localhost:8004/cover/b1be397e-5352-45f5-a5b0-f67773bf6e81alibaba.jpg', null, null);

-- ----------------------------
-- Table structure for `tb_recruit`
-- ----------------------------
DROP TABLE IF EXISTS `tb_recruit`;
CREATE TABLE `tb_recruit` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `jobname` varchar(100) DEFAULT NULL COMMENT '职位名称',
  `salary` varchar(100) DEFAULT NULL COMMENT '薪资范围',
  `conditions` varchar(100) DEFAULT NULL COMMENT '经验要求',
  `education` varchar(100) DEFAULT NULL COMMENT '学历要求',
  `type` varchar(1) DEFAULT NULL COMMENT '任职方式',
  `address` varchar(100) DEFAULT NULL COMMENT '办公地址',
  `eid` varchar(20) DEFAULT NULL COMMENT '企业ID',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `url` varchar(100) DEFAULT NULL COMMENT '网址',
  `label` varchar(100) DEFAULT NULL COMMENT '标签',
  `content1` varchar(100) DEFAULT NULL COMMENT '职位描述',
  `content2` varchar(100) DEFAULT NULL COMMENT '职位要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位';

-- ----------------------------
-- Records of tb_recruit
-- ----------------------------
INSERT INTO `tb_recruit` VALUES ('1', '算法工程师', '20000-30000', null, '??', '1', '??', '1', '2018-01-06 16:21:12', '1', null, null, null, null);
INSERT INTO `tb_recruit` VALUES ('2', 'php开发工程师', '4000-6000', '一年以上开发经验', '专科', '1', '王府街宏福创业园', '1', '2018-01-07 16:10:20', '1', 'http://www.baidu.com', null, null, null);
INSERT INTO `tb_recruit` VALUES ('3', '.net开发工程师', '2000-3000', '一年以上开发经验', '专科', '1', '大望路', '1', '2018-01-06 16:20:27', '2', null, null, null, null);
