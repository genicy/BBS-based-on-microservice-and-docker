/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_qa

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:27:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_pl`
-- ----------------------------
DROP TABLE IF EXISTS `tb_pl`;
CREATE TABLE `tb_pl` (
  `id` varchar(20) NOT NULL,
  `problemid` varchar(20) NOT NULL COMMENT '问题ID',
  `labelid` varchar(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`problemid`,`labelid`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_pl
-- ----------------------------
INSERT INTO `tb_pl` VALUES ('11', '1', '1');
INSERT INTO `tb_pl` VALUES ('15', '1', '2');
INSERT INTO `tb_pl` VALUES ('16', '1', '3');
INSERT INTO `tb_pl` VALUES ('12', '2', '1');
INSERT INTO `tb_pl` VALUES ('13', '3', '1');
INSERT INTO `tb_pl` VALUES ('15', '4', '1');
INSERT INTO `tb_pl` VALUES ('14', '4', '10');
INSERT INTO `tb_pl` VALUES ('16', '572814328621830144', '1');

-- ----------------------------
-- Table structure for `tb_problem`
-- ----------------------------
DROP TABLE IF EXISTS `tb_problem`;
CREATE TABLE `tb_problem` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime DEFAULT NULL COMMENT '修改日期',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `visits` bigint(20) DEFAULT NULL COMMENT '浏览量',
  `thumbup` bigint(20) DEFAULT NULL COMMENT '点赞数',
  `reply` bigint(20) DEFAULT NULL COMMENT '回复数',
  `solve` varchar(1) DEFAULT NULL COMMENT '是否解决',
  `replyname` varchar(100) DEFAULT NULL COMMENT '回复人昵称',
  `replytime` datetime DEFAULT NULL COMMENT '回复日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题';

-- ----------------------------
-- Records of tb_problem
-- ----------------------------
INSERT INTO `tb_problem` VALUES ('1', '这是个问题', '代码调试不通咋办？', '2018-01-08 11:50:50', '2018-01-09 11:50:54', '555356895221780480', 'txtx', '101', '0', '1', '1', 'txtx', '2019-04-04 10:33:22');
INSERT INTO `tb_problem` VALUES ('2', '使用travis cli ssh服务器的时候提示输入密码', '使用travis cli ssh服务器的时候提示输入密码', '2019-01-18 10:12:42', '2019-01-18 10:12:46', '555356895221780480', 'txtx', '101', '0', '2', '0', 'txtx', '2019-04-04 10:33:14');
INSERT INTO `tb_problem` VALUES ('3', 'Augular v-html渲染后端返回的a标签 保留单页面跳转', 'React v-html渲染后端返回的a标签 保留单页面跳转', '2019-04-04 10:36:02', null, '555356895221780480', 'txtx', '0', '0', '0', '0', 'txtx', '2019-04-04 10:36:33');
INSERT INTO `tb_problem` VALUES ('4', 'React v-html渲染后端返回的a标签 保留单页面跳转', 'Augular v-html渲染后端返回的a标签 保留单页面跳转', '2019-04-04 10:36:00', null, '555356895221780480', 'txtx', '0', '0', '0', '0', 'txtx', '2019-04-04 10:36:37');
INSERT INTO `tb_problem` VALUES ('5', 'Vue v-html渲染后端返回的a标签 保留单页面跳转', 'Vue v-html渲染后端返回的a标签 保留单页面跳转', '2019-04-04 10:35:58', null, '555356895221780480', 'txtx', '0', '0', '0', '0', 'txtx', '2019-04-04 10:36:31');
INSERT INTO `tb_problem` VALUES ('566922723817820160', 'v-html渲染后端返回的a标签 保留单页面跳转', '<h1 class=\"ql-align-center\">示范标题</h1><p><br></p><ol><li><strong><em>Or drag/paste an imagaasdas啊飒飒打撒打撒 here.</em></strong></li><li><strong><em>rerew</em></strong></li><li><strong><em>rtrete</em></strong></li><li><strong><em>tytrytr</em></strong></li><li><strong><em>uytu</em></strong></li></ol>', '2019-04-14 00:00:00', '2019-04-14 00:00:00', '555356895221780480', 'txtx', '0', '0', '0', '0', null, null);
INSERT INTO `tb_problem` VALUES ('572814328621830144', '如何通过JS去掉文本中的BOM头？', '<p>比如这个二维码，扫码后会得到一个字串，内容是“你好！世界！！！！”，它和静态的“你好！世界！！！！”看起来一样，但是扫码之后字串的第一个字符(scanedText[0])的值是一个不可见的字符</p><p>小程序的官方回复说建议我尝试更换二维码。</p><p>那么问题就来了，如果是扫码的话我不可能控制所有人都使用不包含BOM头的二维码，这就涉及到去除BOM这个字符的操作。</p><p>我先后尝试了以下方法：</p><pre class=\"ql-syntax\" spellcheck=\"false\">scanedText.replace(\'\\ufeff\',\'\');\nscanedText.replace(String.fromCharCode(\'\\ufeff\', \'\'));\n</pre><p>最终扫码到有包含BOM的时候scanedText[0]还是那个看不见的字符……</p><p>请问到底如何通过JS去掉这个该死的BOM头啊……</p><p><br></p>', '2019-04-30 00:00:00', '2019-04-30 00:00:00', '1', '鲁迅', '0', '0', '1', '1', null, null);
INSERT INTO `tb_problem` VALUES ('572820475894435840', 'mysql服务无法启动但是显示服务无法启动是什么原因？', '<p>今天以zip模式在windows10 64位环境下安装mysql5.7，到最后一步提示mysql服务无法启动。</p><p>安装步骤如下：</p><p><strong>1.配置环境变量</strong></p><p>我的电脑-&gt;属性-&gt;高级-&gt;环境变量-&gt;path</p><p>如:C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin&nbsp;</p><p>注意是追加，不要覆盖</p><p><strong>2.修改my-default.ini</strong></p><p>在其中修改或添加配置：&nbsp;</p><p>[mysqld]&nbsp;</p><p>basedir=C:\\Program Files\\MySQL\\MySQL Server 5.6（mysql所在目录）&nbsp;</p><p>datadir=C:\\Program Files\\MySQL\\MySQL Server 5.6\\data （mysql所在目录\\data）</p><p>3.以管理员身份运行cmd（<span style=\"color: rgb(255, 102, 0);\">win10右键左下角开始按钮选择以管理员身份运行cmd即可</span>）</p><p>以管理员身份运行cmd（一定要用管理员身份运行，不然权限不够），</p><p>输入：cd C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin&nbsp;进入mysql的bin文件夹(不管有没有配置过环境变量，也要进入bin文件夹，否则之后启动服务仍然会报错误2)</p><p>输入mysqld -install(如果不用管理员身份运行，将会因为权限不够而出现错误：Install/Remove of the Service Denied!)&nbsp;</p><p>安装成功</p><p>4.运行mysqld&nbsp;--initialize<span style=\"color: rgb(255, 0, 0);\">（标题问题所在，若没有init则不存在data目录，自然无法启动成功）</span></p><p>5.安装成功后就要启动服务了，继续在cmd中输入:net start mysql,服务启动成功！</p><p>服务启动成功之后，就可以登录了，输入mysql -u root -p（第一次登录没有密码，直接按回车过）,登录成功！</p><h1 class=\"ql-align-center\">&nbsp;</h1>', '2019-04-30 00:00:00', '2019-04-30 00:00:00', '1', '鲁迅', '0', '0', '1', '0', null, null);
INSERT INTO `tb_problem` VALUES ('582659595537879040', 'vue 基于excel的导入', '<h3>问题描述</h3><p><br></p><p>实现导入模板，前端写好了，怎么导不进去</p><h3>相关代码</h3><p>// 请把代码文本粘贴到下方（请勿用图片代替代码）</p><p>&lt;import-excel :visible.sync=\"dialogImportVisible\"</p><pre class=\"ql-syntax\" spellcheck=\"false\">              title=\"导入信息\"\n              :post-api=\"postread\"\n              excel-template=\"/static/feils/信息导入.xlsx\"\n              @success=\"getData\"&gt;\n&lt;/import-excel&gt;\n\n</pre><p><img src=\"https://segmentfault.com/img/bVbs5aT?w=1040&amp;h=246\" alt=\"clipboard.png\"></p><p>导入组件</p><p>&lt;template&gt;</p><p>&lt;el-dialog :title=\"title\"</p><pre class=\"ql-syntax\" spellcheck=\"false\">         :visible.sync=\"visible\"\n         append-to-body\n         :before-close=\"handleClose\"&gt;\n&lt;div class=\"upload-wrap\"&gt;\n  &lt;div class=\"upload-input\"&gt;\n    &lt;input type=\"file\"\n           class=\"upload-file-input\"\n           name=\"upload-file\"\n           id=\"orderUpload\"\n           accept=\"application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\"\n           @change=\"handleUploadChange\"&gt;\n    &lt;el-button type=\"primary\"\n               class=\"pull-left\"&gt;\n      &lt;i class=\"fa fa-folder-open-o\"&gt;&lt;/i&gt; {{btnText}}\n    &lt;/el-button&gt;\n    &lt;div class=\"upload-info\"&gt;\n      &lt;span v-if=\"!uploadFile\"\n            style=\"display: inline-block; margin-top: 8px;\"&gt;未选择文件&lt;/span&gt;\n      &lt;span v-else&gt;\n        文件：{{uploadFile.name}}\n        &lt;br&gt; 大小：{{uploadSize}}\n      &lt;/span&gt;\n    &lt;/div&gt;\n  &lt;/div&gt;\n  &lt;p class=\"pull-right\" v-show=\"excelTemplate\"&gt;\n    &lt;a :href=\"excelTemplate\"\n       class=\"btn btn-text\"&gt;\n      &lt;i class=\"fa fa-file-excel-o\"&gt;&lt;/i&gt; {{templateText}}&lt;/a&gt;\n  &lt;/p&gt;\n  &lt;div class=\"upload-hint\"&gt;\n    &lt;slot&gt;&lt;/slot&gt;\n    &lt;ul class=\"list-unstyled\"\n        style=\"color: #999; margin-bottom: 0;\"\n        v-if=\"hintShow&amp;&amp;!$slots.default\"&gt;\n      &lt;li&gt;1、请按模板文件格式填写。&lt;/li&gt;\n      &lt;li&gt;2、只支持上传 .xls .xlsx 文件。&lt;/li&gt;\n    &lt;/ul&gt;\n  &lt;/div&gt;\n&lt;/div&gt;\n&lt;div class=\"text-right\"&gt;\n  &lt;el-button :plain=\"true\"\n             type=\"warning\"\n             @click=\"onDissmis\"&gt;取消&lt;/el-button&gt;\n  &lt;el-button type=\"primary\"\n             @click=\"importOrder\"\n             :disabled=\"!uploadFile\"\n             :loading=\"importLoading\"&gt;\n    &lt;i class=\"fa fa-fw fa-sign-in\"&gt;&lt;/i&gt; 导 入\n  &lt;/el-button&gt;\n&lt;/div&gt;\n</pre><p>&lt;/el-dialog&gt;</p><ol><li>&lt;/template&gt;</li></ol>', '2019-05-27 00:00:00', '2019-05-27 00:00:00', '1', '鲁迅', '0', '0', '0', '0', null, null);
INSERT INTO `tb_problem` VALUES ('6', 'H5实现下拉图片放大？', 'H5实现下拉图片放大？', '2019-04-04 10:35:56', null, '555356895221780480', 'txtx', '0', '0', '0', '0', 'txtx', '2019-04-04 10:35:05');

-- ----------------------------
-- Table structure for `tb_reply`
-- ----------------------------
DROP TABLE IF EXISTS `tb_reply`;
CREATE TABLE `tb_reply` (
  `id` varchar(20) NOT NULL COMMENT '编号',
  `problemid` varchar(20) DEFAULT NULL COMMENT '问题ID',
  `content` text COMMENT '回答内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime DEFAULT NULL COMMENT '更新日期',
  `userid` varchar(20) DEFAULT NULL COMMENT '回答人ID',
  `nickname` varchar(100) DEFAULT NULL COMMENT '回答人昵称',
  `thumbup` int(11) DEFAULT '0',
  `avatar` varchar(100) DEFAULT NULL COMMENT '文章封面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回答';

-- ----------------------------
-- Records of tb_reply
-- ----------------------------
INSERT INTO `tb_reply` VALUES ('1', '566922723817820160', '<h1><strong style=\"color: rgb(0, 0, 0);\"></strong></h1><p><br></p><p>在2018这个寒冬里，我们或多或少都有听到一些消息，许多互联网公司纷纷裁员或收紧社招通道，其中不乏国内大厂的身影。不过对于我们iOS开发者来说，可能早就波澜不惊了，毕竟只要某个跨平台或移动端的新技术火起来的时候，就会出现“iOS开发又没人要了”这样的声音。但iOS开发的需求量其实一直都没怎么减过，至少我身边的朋友或公司都一直苦于招不到合适的人。关键在于这个‘合适’二字， 以前说iOS找不到工作其实大部分是因为门槛提升了，对于iOS开发者的能力要求更严格了，换句话说就是iOS初级开发者没人要了，中高级/资深开发仍然是卖方市场，这是2018年寒冬之前的情况。 而现在整个互联网行业的形势由于资本以及各种环境的影响，确实比以往都要严峻得多，相信大家也能感受到这股凉意。</p><p>之所以前面要提下现在的大环境，是因为最近本人也换了工作，本来打算年底休息一阵，出去散散心之类的。但每天都被各种各样的微博朋友圈裁员文章轰炸，加上身边人一些担忧的气氛，弄得我也心慌了起来，已经完全没有了任何出去玩的心思，毕竟为了生活，还是要恰饭的嘛。</p><p>我从准备面试到最终入职共3周左右，目前就职于字节跳动。 今天我会从几个方面来简单聊下，希望对各位准备换工作或者正在努力找工作的同学们提供一些参考：</p><p>如何准备面试</p><p>简历二三事</p><p>从面试中脱颖而出</p><p><br></p><h2><strong style=\"color: rgb(0, 0, 0);\">如何准备面试</strong></h2><p><img src=\"http://localhost:8004/articles/279f0e0e-8681-438b-8963-d771c4e73454捕获2.JPG\"></p><p><br></p><h3><em>梳理基础知识</em></h3><p>我相信大部分开发者恐惧面试有一部分原因是由于平时忙于公司项目的业务或者界面开发， 对一些计算机基础知识疏于记忆和理解，往往在面试过程中被面试官问到时，明明有印象但却表述不出来。阳所以个人认为在面试准备时做好知识梳理非常重要，我整理了一些基础知识点：</p>', '2019-04-25 19:43:03', null, '1', '鲁迅', '0', 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png');
INSERT INTO `tb_reply` VALUES ('2', '1', '问老师呗', '2018-01-10 14:14:06', null, '1', null, '1', null);
INSERT INTO `tb_reply` VALUES ('3', '2', '明天再调', '2018-01-07 14:14:13', null, '1', null, '0', null);
INSERT INTO `tb_reply` VALUES ('4', '566922723817820160', '<h1><strong style=\"color: rgb(0, 0, 0);\"></strong></h1><p><br></p><p>在2018这个寒冬里，我们或多或少都有听到一些消息，许多互联网公司纷纷裁员或收紧社招通道，其中不乏国内大厂的身影。不过对于我们iOS开发者来说，可能早就波澜不惊了，毕竟只要某个跨平台或移动端的新技术火起来的时候，就会出现“iOS开发又没人要了”这样的声音。但iOS开发的需求量其实一直都没怎么减过，至少我身边的朋友或公司都一直苦于招不到合适的人。关键在于这个‘合适’二字， 以前说iOS找不到工作其实大部分是因为门槛提升了，对于iOS开发者的能力要求更严格了，换句话说就是iOS初级开发者没人要了，中高级/资深开发仍然是卖方市场，这是2018年寒冬之前的情况。 而现在整个互联网行业的形势由于资本以及各种环境的影响，确实比以往都要严峻得多，相信大家也能感受到这股凉意。</p><p>之所以前面要提下现在的大环境，是因为最近本人也换了工作，本来打算年底休息一阵，出去散散心之类的。但每天都被各种各样的微博朋友圈裁员文章轰炸，加上身边人一些担忧的气氛，弄得我也心慌了起来，已经完全没有了任何出去玩的心思，毕竟为了生活，还是要恰饭的嘛。</p><p>我从准备面试到最终入职共3周左右，目前就职于字节跳动。 今天我会从几个方面来简单聊下，希望对各位准备换工作或者正在努力找工作的同学们提供一些参考：</p><p>如何准备面试</p><p>简历二三事</p><p>从面试中脱颖而出</p><p><br></p><h2><strong style=\"color: rgb(0, 0, 0);\">如何准备面试</strong></h2><p><img src=\"http://localhost:8004/articles/279f0e0e-8681-438b-8963-d771c4e73454捕获2.JPG\"></p><p><br></p><h3><em>梳理基础知识</em></h3><p>我相信大部分开发者恐惧面试有一部分原因是由于平时忙于公司项目的业务或者界面开发， 对一些计算机基础知识疏于记忆和理解，往往在面试过程中被面试官问到时，明明有印象但却表述不出来。阳所以个人认为在面试准备时做好知识梳理非常重要，我整理了一些基础知识点：</p>', '2019-04-25 19:54:47', null, '555356895221780480', 'txtx', '0', 'https://img.zcool.cn/community/01a72e5bbc2bc2a801213dead4e6f5.png@1280w_1l_2o_100sh.png');
INSERT INTO `tb_reply` VALUES ('571638133989445632', '566922723817820160', '<p><span style=\"color: gray;\">百度一下</span></p>', '2019-04-27 00:00:00', null, '555356895221780480', 'txtx', '1', 'https://img.zcool.cn/community/01a72e5bbc2bc2a801213dead4e6f5.png@1280w_1l_2o_100sh.png');
INSERT INTO `tb_reply` VALUES ('571638353305407488', '566922723817820160', '<h1><span style=\"color: gray;\">或者Google</span></h1>', '2019-04-27 00:00:00', null, '555356895221780480', 'txtx', '2', 'https://img.zcool.cn/community/01a72e5bbc2bc2a801213dead4e6f5.png@1280w_1l_2o_100sh.png');
INSERT INTO `tb_reply` VALUES ('571649196998922240', '566922723817820160', '<a style=\"color: gray;float: left;font-size: large\">撰写你的答案...</a>', '2019-04-27 00:00:00', null, '555356895221780480', 'txtx', '1', 'https://img.zcool.cn/community/01a72e5bbc2bc2a801213dead4e6f5.png@1280w_1l_2o_100sh.png');
INSERT INTO `tb_reply` VALUES ('572819859545657344', '566922723817820160', '<p><span style=\"color: gray;\">搜狗搜搜</span></p>', '2019-04-30 00:00:00', null, '1', '鲁迅', '0', 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png');
INSERT INTO `tb_reply` VALUES ('572820072301727744', '572814328621830144', '<p><strong>已解决！谢谢</strong><em><span class=\"ql-cursor\">﻿</span></em></p>', '2019-04-30 00:00:00', null, '1', '鲁迅', '1', 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png');
INSERT INTO `tb_reply` VALUES ('572820583583191040', '572820475894435840', '<p><span style=\"color: gray;\">你这不是已经解决了嘛</span></p>', '2019-04-30 00:00:00', null, '1', '鲁迅', '0', 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png');
INSERT INTO `tb_reply` VALUES ('582659040392384512', '572814328621830144', '<p><strong>我明白了谢谢<span class=\"ql-cursor\">﻿</span></strong></p>', '2019-05-27 00:00:00', null, '1', '鲁迅', '0', 'https://pic2.zhimg.com/80/v2-28048ab28fabf61bc408cf17633d0138_hd.jpg');
INSERT INTO `tb_reply` VALUES ('582659426910081024', '572820475894435840', '<h1><strong>我明白了</strong></h1>', '2019-05-27 00:00:00', null, '1', '鲁迅', '0', 'https://pic2.zhimg.com/80/v2-28048ab28fabf61bc408cf17633d0138_hd.jpg');

-- ----------------------------
-- Table structure for `tb_ul`
-- ----------------------------
DROP TABLE IF EXISTS `tb_ul`;
CREATE TABLE `tb_ul` (
  `id` varchar(20) NOT NULL,
  `uid` varchar(20) NOT NULL COMMENT '用户ID',
  `lid` varchar(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`uid`,`lid`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_ul
-- ----------------------------
INSERT INTO `tb_ul` VALUES ('564850491796885504', '1', '10');
INSERT INTO `tb_ul` VALUES ('569123451437191168', '2', '22');
INSERT INTO `tb_ul` VALUES ('569208831578607616', '555356895221780480', '11');
INSERT INTO `tb_ul` VALUES ('565620039743574016', '555356895221780480', '13');
INSERT INTO `tb_ul` VALUES ('569479792303411200', '555356895221780480', '14');
INSERT INTO `tb_ul` VALUES ('569479741837545472', '555356895221780480', '16');
INSERT INTO `tb_ul` VALUES ('569479752637878272', '555356895221780480', '17');
INSERT INTO `tb_ul` VALUES ('569208845671469056', '555356895221780480', '20');
INSERT INTO `tb_ul` VALUES ('569208860557053952', '555356895221780480', '21');
