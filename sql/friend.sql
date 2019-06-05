/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_friend

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:26:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_friend`
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend`;
CREATE TABLE `tb_friend` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `friendid` varchar(20) NOT NULL COMMENT '好友ID',
  `islike` varchar(1) DEFAULT NULL COMMENT '是否互相喜欢',
  PRIMARY KEY (`userid`,`friendid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_friend
-- ----------------------------
INSERT INTO `tb_friend` VALUES ('1', '100', '0');
INSERT INTO `tb_friend` VALUES ('1', '2', '0');

-- ----------------------------
-- Table structure for `tb_nofriend`
-- ----------------------------
DROP TABLE IF EXISTS `tb_nofriend`;
CREATE TABLE `tb_nofriend` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `friendid` varchar(20) NOT NULL COMMENT '好友ID',
  PRIMARY KEY (`userid`,`friendid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_nofriend
-- ----------------------------
INSERT INTO `tb_nofriend` VALUES ('1', '2');
