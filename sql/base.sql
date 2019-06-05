/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_base

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:26:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_city`
-- ----------------------------
DROP TABLE IF EXISTS `tb_city`;
CREATE TABLE `tb_city` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(20) DEFAULT NULL COMMENT '城市名称',
  `ishot` varchar(1) DEFAULT NULL COMMENT '是否热门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

-- ----------------------------
-- Records of tb_city
-- ----------------------------
INSERT INTO `tb_city` VALUES ('1', '北京', '1');
INSERT INTO `tb_city` VALUES ('2', '上海', '1');
INSERT INTO `tb_city` VALUES ('3', '广州', '1');
INSERT INTO `tb_city` VALUES ('4', '深圳', '1');
INSERT INTO `tb_city` VALUES ('5', '天津', '0');
INSERT INTO `tb_city` VALUES ('6', '重庆', '0');
INSERT INTO `tb_city` VALUES ('7', '西安', '0');

-- ----------------------------
-- Table structure for `tb_label`
-- ----------------------------
DROP TABLE IF EXISTS `tb_label`;
CREATE TABLE `tb_label` (
  `id` varchar(20) NOT NULL COMMENT '标签ID',
  `labelname` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `state` varchar(1) DEFAULT '1' COMMENT '状态',
  `count` bigint(20) DEFAULT '0' COMMENT '使用数量',
  `recommend` varchar(1) DEFAULT '0' COMMENT '是否推荐',
  `fans` bigint(20) DEFAULT '0' COMMENT '粉丝数',
  `summarys` varchar(256) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';

-- ----------------------------
-- Records of tb_label
-- ----------------------------
INSERT INTO `tb_label` VALUES ('1', 'JAVA', '1', '0', '1', '0', 'Java 是一种可以撰写跨平台应用软件的面向对象的程序设计语言，是由 Sun Microsystems 公司于 1995 年 5 月推出', '开发语言');
INSERT INTO `tb_label` VALUES ('10', 'Spring', '1', '0', '0', '1', '目前还没有关于这个标签的解释', 'Java 开发');
INSERT INTO `tb_label` VALUES ('11', 'java-ee', '1', '0', '0', '1', '目前还没有关于这个标签的解释', 'Java 开发');
INSERT INTO `tb_label` VALUES ('12', 'tensorflow', '1', '0', '0', '0', '目前还没有关于这个标签的解释', '人工智能');
INSERT INTO `tb_label` VALUES ('13', '机器学习', '1', '0', '0', '1', '目前还没有关于这个标签的解释', '人工智能');
INSERT INTO `tb_label` VALUES ('14', '神经网络', '1', '0', '0', '1', '目前还没有关于这个标签的解释', '人工智能');
INSERT INTO `tb_label` VALUES ('15', '自然语言处理', '1', '0', '0', '0', '自然語言處理（英语：Natural Language Processing，簡稱NLP）是人工智慧和語言學領域的分支學科。', '人工智能');
INSERT INTO `tb_label` VALUES ('16', 'oracle', '1', '0', '0', '1', '甲骨文股份有限公司（NASDAQ：ORCL，Oracle）是全球大型数据库软件公司。总部位于美国加州红木城的红木岸', '数据库');
INSERT INTO `tb_label` VALUES ('17', 'mongodb', '1', '0', '0', '1', 'MongoDB是一个基于分布式文件存储的数据库。由C++语言编写。旨在为WEB应用提供可扩展的高性能数据存储解决方案', '数据库');
INSERT INTO `tb_label` VALUES ('18', 'redis', '1', '0', '0', '0', 'REmote DIctionary Server(Redis) 是一个由Salvatore Sanfilippo写的key-value存储系统。Redis提供了一些丰富的数据结构', '数据库');
INSERT INTO `tb_label` VALUES ('19', 'mysql', '1', '0', '0', '0', 'MySQL是一个小型关系型数据库管理系统，开发者为瑞典MySQL AB公司。在2008年1月16号被Sun公司收购。', '数据库');
INSERT INTO `tb_label` VALUES ('2', 'JavaScript', '1', '0', '1', '0', 'JavaScript 是一门弱类型的动态脚本语言，支持多种编程范式，包括面向对象和函数式编程，被广泛用于 web 开发。', '开发语言');
INSERT INTO `tb_label` VALUES ('20', 'React', '1', '0', '0', '1', 'React (sometimes styled React.js or ReactJS) is an open-source JavaScript library for creating user interfaces that ', '前端开发');
INSERT INTO `tb_label` VALUES ('21', 'Vue.js', '1', '0', '0', '1', 'Reactive Components for Modern Web Interfaces. Vue.js 是一个用于创建 web 交互界面的。', '前端开发');
INSERT INTO `tb_label` VALUES ('22', 'CSS', '1', '0', '0', '1', '层叠样式表（英语：Cascading Style Sheets，简写CSS），又称串样式列表，由W3C定义和维护的标准', '前端开发');
INSERT INTO `tb_label` VALUES ('23', 'html5', '1', '0', '0', '0', 'HTML5 是 HTML 下一个的主要修订版本，现在仍处于发展阶段。', '前端开发');
INSERT INTO `tb_label` VALUES ('24', 'C++', '1', '0', '0', '0', 'C++是一种静态数据类型检查的，支持多范型的通用程序设计语言。C++支持过程化程序设计、数据抽象化', '开发语言');
INSERT INTO `tb_label` VALUES ('25', 'python', '1', '0', '0', '0', 'Python（发音：英[ˈpaɪθən]，美[ˈpaɪθɑ:n]），是一种面向对象、直译式电脑编程语言，也是一种功能强大的通用型语言，已经具有近二十年的发展历史，成熟且稳定。', '开发语言');
INSERT INTO `tb_label` VALUES ('3', 'go', '1', '0', '1', '0', '目前还没有关于这个标签的解释', '开发语言');
INSERT INTO `tb_label` VALUES ('4', 'C#', '1', '0', '0', '0', 'C#是微软推出的一种基于.NET框架的、面向对象的高级编程语言。C#由C语言和C++派生而来，继承了其强大的性能', '开发语言');
INSERT INTO `tb_label` VALUES ('5', 'C', '1', '0', '0', '0', 'C 语言，是一种通用的、过程式的编程语言，广泛用于系统与应用软件的开发。', '开发语言');
INSERT INTO `tb_label` VALUES ('6', 'hibernate', '1', '0', '0', '0', '目前还没有关于这个标签的解释', 'Java 开发');
INSERT INTO `tb_label` VALUES ('7', 'Springboot', '1', '0', '0', '0', '目前还没有关于这个标签的解释', 'Java 开发');
INSERT INTO `tb_label` VALUES ('8', 'maven', '1', '0', '0', '0', '目前还没有关于这个标签的解释', 'Java 开发');
INSERT INTO `tb_label` VALUES ('9', 'tomcat', '1', '0', '0', '0', '目前还没有关于这个标签的解释', 'Java 开发');
