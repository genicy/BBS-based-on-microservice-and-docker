/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_gathering

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:26:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_gathering`
-- ----------------------------
DROP TABLE IF EXISTS `tb_gathering`;
CREATE TABLE `tb_gathering` (
  `id` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `summary` text COMMENT '大会简介',
  `detail` text COMMENT '详细说明',
  `sponsor` varchar(100) DEFAULT NULL COMMENT '主办方',
  `image` varchar(256) DEFAULT NULL COMMENT '活动图片',
  `starttime` datetime DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '截止时间',
  `address` varchar(100) DEFAULT NULL COMMENT '举办地点',
  `enrolltime` datetime DEFAULT NULL COMMENT '报名截止',
  `state` varchar(1) DEFAULT NULL COMMENT '是否可见',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动';

-- ----------------------------
-- Records of tb_gathering
-- ----------------------------
INSERT INTO `tb_gathering` VALUES ('553175591826886656', '2019 NAVIGATE 领航者峰会', '由紫光集团和新华三集团主办的2019 NAVIGATE 领航者峰会，以“数字领航 智绘未来”为主题，将于2019年4月19日-20日在重庆悦来国际博览中心及国际会议中心举办。', '<p>本届峰会得到重庆市人民政府、重庆市经信委、重庆两江新区的大力支持。届时将有7,000余名专家学者、政企代表、权威媒体人士相聚山城重庆，针对数字化技术创新、数字化转型实践、数字经济发展阶段性探索以及数字未来前景展开广泛讨论与交流。</p><p>当今，中国数字经济已进入深度发展阶段，数字化由后台走向前端，由封闭走向开放，由单点发力走向融合制胜；而数字中台的兴起，智慧产业的突进，数字化需求决策的整体上移，加速刷新着人们对数字化发展的理解。这一切的变化正不断催生出新技术、新模式与新业态。</p><p>这是一个无限可能的时代，也将是一个大有可为的时代！作为“数字化解决方案领导者”，新华三以整合的一站式数字化解决方案，帮助百行百业布局数字化时代的战略变革，繁荣产业生态，激活数字潜能，推动中国<em>数字经济发展进程！</em></p><p><strong><em>我们期待，与您一起用数字创想时代，用智慧描绘未来！</em></strong></p>', 'H3C', 'http://localhost:8004/cover/ffc7c40d-6936-45d7-9b73-2e27566fce8620190301_3986673_2019_1154436_30008_0.jpg', '2019-03-07 00:00:00', '2019-10-01 00:00:00', '重庆国际博览中心', '2019-08-01 00:00:00', '1', '重庆');

-- ----------------------------
-- Table structure for `tb_usergath`
-- ----------------------------
DROP TABLE IF EXISTS `tb_usergath`;
CREATE TABLE `tb_usergath` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `gathid` varchar(20) NOT NULL COMMENT '活动ID',
  `exetime` datetime DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`userid`,`gathid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注活动';

-- ----------------------------
-- Records of tb_usergath
-- ----------------------------
INSERT INTO `tb_usergath` VALUES ('1', '200', '2018-01-06 15:44:04');
