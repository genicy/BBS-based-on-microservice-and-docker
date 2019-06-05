/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_user

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:27:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_admin`
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `loginname` varchar(100) DEFAULT NULL COMMENT '登陆名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES ('537941681799041024', 'admin', '$2a$10$k3kpkplhgzUfpo9jOPEURO0BDX0WKXeLFPEPUHXP/KvrUB7/yByx6', null);

-- ----------------------------
-- Table structure for `tb_follow`
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow`;
CREATE TABLE `tb_follow` (
  `id` varchar(30) NOT NULL,
  `userid` varchar(30) NOT NULL COMMENT '用户ID',
  `targetuser` varchar(30) NOT NULL COMMENT '被关注专栏用户ID；此表好与交友表分开',
  PRIMARY KEY (`userid`,`targetuser`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_follow
-- ----------------------------
INSERT INTO `tb_follow` VALUES ('1', '1', '10');
INSERT INTO `tb_follow` VALUES ('2', '1', '2');
INSERT INTO `tb_follow` VALUES ('3', '2', '1');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `loginname` varchar(100) DEFAULT NULL COMMENT '登陆名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月日',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT 'E-Mail',
  `regdate` datetime DEFAULT NULL COMMENT '注册日期',
  `updatedate` datetime DEFAULT NULL COMMENT '修改日期',
  `lastdate` datetime DEFAULT NULL COMMENT '最后登陆日期',
  `online` bigint(20) DEFAULT NULL COMMENT '在线时长（分钟）',
  `interest` varchar(100) DEFAULT NULL COMMENT '兴趣',
  `personality` varchar(100) DEFAULT NULL COMMENT '个性',
  `fanscount` int(20) DEFAULT NULL COMMENT '粉丝数',
  `followcount` int(20) DEFAULT NULL COMMENT '关注数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '15556931209', '250738437', '$2a$10$2wrNCpycua5UXz82dW6r0OCZ4sctJ4VgiAuL8IkI9A74m1lieb/8a', '鲁迅', null, '2019-01-23 17:00:39', 'https://pic2.zhimg.com/80/v2-28048ab28fabf61bc408cf17633d0138_hd.jpg', null, '2019-01-23 17:04:58', '2019-01-23 17:04:58', '2019-01-23 17:04:58', '0', null, ' 天地玄黄 宇宙洪荒 日月盈昃 辰宿列张 寒来暑往 秋收冬藏 闰馀成岁 律吕调阳', '1', '1');
INSERT INTO `tb_user` VALUES ('2', '13013062836', 'yangxucheng', '$2a$10$2wrNCpycua5UXz82dW6r0OCZ4sctJ4VgiAuL8IkI9A74m1lieb/8a', 'genv', null, null, 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png', null, '2019-01-24 15:35:40', '2019-01-24 15:35:40', '2019-01-24 15:35:40', '0', null, null, '1', '1');
INSERT INTO `tb_user` VALUES ('3', '13511112222', 'leetcode', '$2a$10$2wrNCpycua5UXz82dW6r0OCZ4sctJ4VgiAuL8IkI9A74m1lieb/8a', '珂珂', null, null, 'https://img.zcool.cn/community/01a72e5bbc2bc2a801213dead4e6f5.png@1280w_1l_2o_100sh.png', null, '2019-03-26 10:40:23', '2019-03-26 10:40:25', '2019-03-26 10:40:29', '0', null, null, '0', '0');
INSERT INTO `tb_user` VALUES ('4', '13411112222', 'user1', '$2a$10$2wrNCpycua5UXz82dW6r0OCZ4sctJ4VgiAuL8IkI9A74m1lieb/8a', 'tree', null, null, 'https://img.zcool.cn/community/01dad55bbc2bc1a8012099c8b97126.png@1280w_1l_2o_100sh.png', null, null, null, null, '0', null, null, '0', '0');
INSERT INTO `tb_user` VALUES ('5', '13811112222', 'user2', '$2a$10$2wrNCpycua5UXz82dW6r0OCZ4sctJ4VgiAuL8IkI9A74m1lieb/8a', 'forest', null, null, 'https://img.zcool.cn/community/0167bd5a827a03a8012192310178bf.jpeg@260w_195h_1c_1e_1o_100sh.jpg', null, null, null, null, '0', null, null, '0', '0');
INSERT INTO `tb_user` VALUES ('555356895221780480', '13611112222', 'user', '$2a$10$2wrNCpycua5UXz82dW6r0OCZ4sctJ4VgiAuL8IkI9A74m1lieb/8a', 'txtx', null, '2014-03-27 08:00:00', 'https://img.zcool.cn/community/01a72e5bbc2bc2a801213dead4e6f5.png@1280w_1l_2o_100sh.png', null, '2019-03-13 11:50:02', '2019-03-13 11:50:02', '2019-03-13 11:50:02', '0', null, '云对雨，雪对风，晚照对晴空。\r\n来鸿对去雁，宿鸟对鸣虫。\r\n三尺剑，六钧弓，岭北对江东。\r\n人间清暑殿，天上广寒宫。', '0', '0');
