/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50724
Source Host           : 192.168.1.137:3306
Source Database       : tensquare_article

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-03 13:26:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_article`
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `columnid` varchar(20) DEFAULT NULL COMMENT '专栏ID',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '文章正文',
  `image` varchar(100) DEFAULT NULL COMMENT '文章封面',
  `createtime` datetime DEFAULT NULL COMMENT '发表日期',
  `updatetime` datetime DEFAULT NULL COMMENT '修改日期',
  `ispublic` varchar(1) DEFAULT NULL COMMENT '是否公开',
  `istop` varchar(1) DEFAULT NULL COMMENT '是否置顶',
  `visits` int(20) DEFAULT NULL COMMENT '浏览量',
  `thumbup` int(20) DEFAULT NULL COMMENT '点赞数',
  `comment` int(20) DEFAULT NULL COMMENT '评论数',
  `state` varchar(1) DEFAULT NULL COMMENT '审核状态',
  `channelid` varchar(20) DEFAULT NULL COMMENT '所属频道',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES ('1', '1', '1', 'iOS 开发者如何在互联网寒冬中杀出重围', '<h1><strong>题记：</strong></h1><p>Elasticsearch研究有一段时间了，现特将Elasticsearch相关核心知识、原理从初学者认知、学习的角度，从以下9个方面进行详细梳理。欢迎讨论……</p><h1><strong>0. 带着问题上路——ES是如何产生的？</strong></h1><h2><strong>（1）思考：大规模数据如何检索？</strong></h2><p>如：当系统数据量上了10亿、100亿条的时候，我们在做系统架构的时候通常会从以下角度去考虑问题：&nbsp;</p><p>1）用什么数据库好？(mysql、sybase、oracle、达梦、神通、mongodb、hbase…)&nbsp;</p><p><br></p><h1><img src=\"https://img-blog.csdn.net/20160818205953345\" alt=\"è¿éåå¾çæè¿°\"></h1><ol><li><br></li></ol>', '', null, null, null, null, null, '3', null, null, null, null, null);
INSERT INTO `tb_article` VALUES ('2', '1', '1', 'JAVA应该如何入门', '<h1><strong style=\"color: rgb(0, 0, 0);\">前言</strong></h1><p><br></p><p>在2018这个寒冬里，我们或多或少都有听到一些消息，许多互联网公司纷纷裁员或收紧社招通道，其中不乏国内大厂的身影。不过对于我们iOS开发者来说，可能早就波澜不惊了，毕竟只要某个跨平台或移动端的新技术火起来的时候，就会出现“iOS开发又没人要了”这样的声音。但iOS开发的需求量其实一直都没怎么减过，至少我身边的朋友或公司都一直苦于招不到合适的人。关键在于这个‘合适’二字， 以前说iOS找不到工作其实大部分是因为门槛提升了，对于iOS开发者的能力要求更严格了，换句话说就是iOS初级开发者没人要了，中高级/资深开发仍然是卖方市场，这是2018年寒冬之前的情况。 而现在整个互联网行业的形势由于资本以及各种环境的影响，确实比以往都要严峻得多，相信大家也能感受到这股凉意。</p><p>之所以前面要提下现在的大环境，是因为最近本人也换了工作，本来打算年底休息一阵，出去散散心之类的。但每天都被各种各样的微博朋友圈裁员文章轰炸，加上身边人一些担忧的气氛，弄得我也心慌了起来，已经完全没有了任何出去玩的心思，毕竟为了生活，还是要恰饭的嘛。</p><p>我从准备面试到最终入职共3周左右，目前就职于字节跳动。 今天我会从几个方面来简单聊下，希望对各位准备换工作或者正在努力找工作的同学们提供一些参考：</p><p>如何准备面试</p><p>简历二三事</p><p>从面试中脱颖而出</p><p><br></p><h2><strong style=\"color: rgb(0, 0, 0);\">如何准备面试</strong></h2><p><img src=\"http://localhost:8004/articles/279f0e0e-8681-438b-8963-d771c4e73454捕获2.JPG\"></p><p><br></p><h3><em>梳理基础知识</em></h3><p>我相信大部分开发者恐惧面试有一部分原因是由于平时忙于公司项目的业务或者界面开发， 对一些计算机基础知识疏于记忆和理解，往往在面试过程中被面试官问到时，明明有印象但却表述不出来。阳绪城所以个人认为在面试准备时做好知识梳理非常重要，我整理了一些基础知识点：</p>', 'http://localhost:8004/cover/a263cda2-32fd-4018-a28f-4dba29874c50c874dee30a9c4442b01127bc70c56536.png', '2019-03-18 17:23:31', null, '是', '否', '2', '2', null, null, '4', null, null);
INSERT INTO `tb_article` VALUES ('552594588188151808', '1', '1', 'Android开发者如何在互联网寒冬中杀出重围', '<h1><strong style=\"color: rgb(0, 0, 0);\">前言</strong></h1><p><br></p><p>在2018这个寒冬里，我们或多或少都有听到一些消息，许多互联网公司纷纷裁员或收紧社招通道，其中不乏国内大厂的身影。不过对于我们iOS开发者来说，可能早就波澜不惊了，毕竟只要某个跨平台或移动端的新技术火起来的时候，就会出现“iOS开发又没人要了”这样的声音。但iOS开发的需求量其实一直都没怎么减过，至少我身边的朋友或公司都一直苦于招不到合适的人。关键在于这个‘合适’二字， 以前说iOS找不到工作其实大部分是因为门槛提升了，对于iOS开发者的能力要求更严格了，换句话说就是iOS初级开发者没人要了，中高级/资深开发仍然是卖方市场，这是2018年寒冬之前的情况。 而现在整个互联网行业的形势由于资本以及各种环境的影响，确实比以往都要严峻得多，相信大家也能感受到这股凉意。</p><p>之所以前面要提下现在的大环境，是因为最近本人也换了工作，本来打算年底休息一阵，出去散散心之类的。但每天都被各种各样的微博朋友圈裁员文章轰炸，加上身边人一些担忧的气氛，弄得我也心慌了起来，已经完全没有了任何出去玩的心思，毕竟为了生活，还是要恰饭的嘛。</p><p>我从准备面试到最终入职共3周左右，目前就职于字节跳动。 今天我会从几个方面来简单聊下，希望对各位准备换工作或者正在努力找工作的同学们提供一些参考：</p><p>如何准备面试</p><p>简历二三事</p><p>从面试中脱颖而出</p><p><br></p><h2><strong style=\"color: rgb(0, 0, 0);\">如何准备面试</strong></h2><p><img src=\"http://localhost:8004/articles/279f0e0e-8681-438b-8963-d771c4e73454捕获2.JPG\"></p><p><br></p><h3><em>梳理基础知识</em></h3><p>我相信大部分开发者恐惧面试有一部分原因是由于平时忙于公司项目的业务或者界面开发， 对一些计算机基础知识疏于记忆和理解，往往在面试过程中被面试官问到时，明明有印象但却表述不出来阳。所以个人认为在面试准备时做好知识梳理非常重要，我整理了一些基础知识点：</p>', 'http://localhost:8004/cover/a263cda2-32fd-4018-a28f-4dba29874c50c874dee30a9c4442b01127bc70c56536.png', '2019-03-14 11:47:10', null, '是', '否', '2', '3', null, '', '4', null, null);
INSERT INTO `tb_article` VALUES ('559752501067386880', '2', '2', 'Java：并发不易，先学会用', '<h1>01.我们为什么要学习并发？</h1><p>我的脑袋没有被如来佛祖开过光，所以喜欢一件事接着一件事的想，做不到“一脑两用”。但有些大佬就不一样，比如说诸葛亮，就能够一边想着琴谱一边谈着弹着琴，还能夹带着盘算出司马懿退兵后的打算。</p><p><br></p><p>诸葛大佬就有着超强的“并发”能力啊。换做是我，面对司马懿的千万大军，不仅弹不了琴，弄不好还被吓得屁滚尿流。</p><p><br></p><p>每个人都只有一个脑子，就像电脑只有一个CPU一样。但一个脑子并不意味着不能“一脑两用”，关键就在于脑子有没有“并发”的能力。</p><p><br></p><p>脑子要是有了并发能力，那真的是厉害到飞起啊，想想司马懿被气定神闲的诸葛大佬吓跑的样子就知道了。</p><p><br></p><p>对于程序来说，如果具有并发的能力，效率就能够大幅度地提升。你一定注册过不少网站，收到过不少验证码，如果网站的服务器端在发送验证码的时候，没有专门起一个线程来处理（并发），假如网络不好发生阻塞的话，那服务器端岂不是要从天亮等到天黑才知道你有没有收到验证码？如果就你一个用户也就算了，但假如有一百个用户呢？这一百个用户难道也要在那傻傻地等着，那真要等到花都谢了。</p><p><br></p><p>可想而知，并发编程是多么的重要！况且，懂不懂Java虚拟机和会不会并发编程，几乎是判定一个Java开发人员是不是高手的不三法则。所以要想挣得多，还得会并发啊！</p><p><br></p><h1>02、并发第一步，创建一个线程</h1><p>通常，启动一个程序，就相当于起了一个进程。每个电脑都会运行很多程序，所以你会在进程管理器中看到很多进程。你会说，这不废话吗？</p><p><br></p><p>不不不，在我刚学习编程的很长一段时间内，我都想当然地以为这些进程就是线程；但后来我知道不是那么回事儿。一个进程里，可能会有很多线程在运行，也可能只有一个。</p><p><br></p><p>main函数其实就是一个主线程。我们可以在这个主线程当中创建很多其他的线程。来看下面这段代码。</p><pre class=\"ql-syntax\" spellcheck=\"false\">public class Wanger {\n	public static void main(String[] args) {\n		for (int i = 0; i &lt; 10; i++) {\n			Thread t = new Thread(new Runnable() {\n				\n				@Override\n				public void run() {\n					System.out.println(\"我叫\" + Thread.currentThread().getName() + \"，我超喜欢沉默王二的写作风格\");\n				}\n			});\n			t.start();\n		}\n	}\n\n</pre><h1>03、并发第二步，创建线程池</h1><p><img src=\"http://localhost:8004/articles/0083921b-eb5c-42f2-97f2-f28833f6b03a1179389-ac3c2c57bafd5d10.png\"></p><p><span style=\"color: rgb(79, 79, 79);\">“</span><strong style=\"color: rgb(79, 79, 79);\">冰冻三尺非一日之寒</strong><span style=\"color: rgb(79, 79, 79);\">”，学习是一件循序渐进的事情。只要你学会了怎么创建一个线程，学会了怎么创建线程池，学会了怎么解决共享资源竞争的问题，你已经在并发编程的领域里迈出去了一大步。</span></p>', 'http://localhost:8004/cover/b15988b5-2cf4-491f-aa7a-38382df85715201903051022574024.png', '2019-03-25 14:56:36', null, '是', '否', null, null, null, '', '后端开发', null, '并发;java;');
INSERT INTO `tb_article` VALUES ('579987430661296128', '1', '1', 'Elasticsearch学习，请先看这一篇！', '<h1><strong>题记：</strong></h1><p>Elasticsearch研究有一段时间了，现特将Elasticsearch相关核心知识、原理从初学者认知、学习的角度，从以下9个方面进行详细梳理。欢迎讨论……</p><h1><strong>0. 带着问题上路——ES是如何产生的？</strong></h1><h2><strong>（1）思考：大规模数据如何检索？</strong></h2><p>如：当系统数据量上了10亿、100亿条的时候，我们在做系统架构的时候通常会从以下角度去考虑问题：&nbsp;</p><p>1）用什么数据库好？(mysql、sybase、oracle、达梦、神通、mongodb、hbase…)&nbsp;</p><p><br></p><h1><img src=\"https://img-blog.csdn.net/20160818205953345\" alt=\"è¿éåå¾çæè¿°\"></h1><ol><li><br></li></ol>', '', '2019-02-20 00:00:00', null, '', '', null, null, null, '', '', null, '');

-- ----------------------------
-- Table structure for `tb_channel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_channel`;
CREATE TABLE `tb_channel` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '频道名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='频道';

-- ----------------------------
-- Records of tb_channel
-- ----------------------------
INSERT INTO `tb_channel` VALUES ('1', '人工智能', '1');
INSERT INTO `tb_channel` VALUES ('3', '前端开发', '1');
INSERT INTO `tb_channel` VALUES ('4', '移动开发', '1');
INSERT INTO `tb_channel` VALUES ('5', '程序哲学', '1');
INSERT INTO `tb_channel` VALUES ('552516897686556672', '计算机系统', '1');

-- ----------------------------
-- Table structure for `tb_column`
-- ----------------------------
DROP TABLE IF EXISTS `tb_column`;
CREATE TABLE `tb_column` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '专栏名称',
  `summary` varchar(1000) DEFAULT NULL COMMENT '专栏简介',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `createtime` datetime DEFAULT NULL COMMENT '申请日期',
  `checktime` datetime DEFAULT NULL COMMENT '审核日期',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专栏';

-- ----------------------------
-- Records of tb_column
-- ----------------------------
INSERT INTO `tb_column` VALUES ('1', '网易科技', '网易科技官方媒体', '1', '2019-03-05 17:38:16', '2019-03-05 17:38:19', '1');
INSERT INTO `tb_column` VALUES ('2', '36氪', '36氪官方账号。', '2', '2019-03-05 17:38:56', '2019-03-05 17:38:58', '1');
INSERT INTO `tb_column` VALUES ('3', '\r\n每天一道LeetCode', '每天一道LeetCode算法问题，每天更新 如果你需要的只是一堆题解，那么请出门右拐 因为我的专栏从来不是贴题解的', '3', '2019-03-05 17:40:38', '2019-03-05 17:40:41', '1');
INSERT INTO `tb_column` VALUES ('4', '科技美学', '领先的科技新媒体,给你有价值的信息 ', '4', '2019-03-26 10:45:48', '2019-03-26 10:45:51', '1');
INSERT INTO `tb_column` VALUES ('5', '数据结构与算法', '分享数据结构与算法', '5', '2019-03-26 10:47:27', '2019-03-26 10:47:31', '1');
INSERT INTO `tb_column` VALUES ('560432676817997824', 'asd', 'asdasd', '555356895221780480', '2019-03-27 00:00:00', null, '0');

-- ----------------------------
-- Table structure for `tb_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` varchar(30) NOT NULL,
  `articleid` varchar(30) NOT NULL,
  `userid` varchar(30) NOT NULL,
  `comment` varchar(256) NOT NULL,
  `createtime` datetime NOT NULL,
  `replyid` varchar(30) NOT NULL COMMENT '向谁回复',
  `thumbup` int(8) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `taskid` varchar(30) NOT NULL COMMENT '层主id（即评论id，会包含多级评论）,0代表自己是层主',
  `state` enum('0','1') DEFAULT '1' COMMENT '评论状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES ('1111', '1', '1', '有一次咸丰带着大臣在圆明园闲逛，她重金买通了跟班太监，把皇帝带到了天地一家春，她略施粉黛，唱了小曲\"艳阳天\"，引起了咸丰皇帝的注意，后来慢慢的就成为了垂帘听政的慈禧太后。', '2019-03-20 16:53:29', '0', '3', '0', '1');
INSERT INTO `tb_comment` VALUES ('1112', '1', '2', '哼着歌，吹着风，沿路边买朵鲜艳的花，在风亭大街上等你来。夏天的夜晚，将花儿插到你头上，拉着你的手和你一起去看烟火大会。夏夜、烟花和你都很美好，值得我去爱一辈子。', '2019-03-20 16:54:46', '1', '2', '1111', '1');
INSERT INTO `tb_comment` VALUES ('1114', '1', '2', '“行色匆匆让生命变得短暂，气定神闲才能走过漫长的旅程”，不要着急不要害怕，世途险远，你终会遇到那个陪你一起走的人。', '2019-03-20 16:54:32', '0', '1', '0', '1');
INSERT INTO `tb_comment` VALUES ('1115', '1', '1', '戴着耳机听歌听到耳朵痛了，躺着看视频看到眼睛疼了，王者玩到腻了，手机玩到没电了 头晕想吐了，我不是悲观主义者，只是有时候，真的感觉自己孤独得像一条狗。', '2019-03-20 16:56:46', '2', '2', '1114', '1');
INSERT INTO `tb_comment` VALUES ('1116', '1', '2', '直到这个春天，遇到了一个和我左手腕有着同款伤疤的人，我终于再次有勇气听完这首歌，在心里赋予其新的感受与意义。', '2019-03-20 17:00:26', '1', '2', '1114', '1');
INSERT INTO `tb_comment` VALUES ('558254860974297088', '1', '2', '今晚的派比·丹尼尔面粉时间\"给饼干\"节目就要结束了 我是派比·丹尼尔 记住当你煎烙饼或者烤饼干时要用干净的冷水和上优良纯正的派比·丹尼尔面粉', '2019-03-21 11:45:31', '0', '1', '0', '1');
INSERT INTO `tb_comment` VALUES ('558255359077257216', '1', '2', '大学过的最辛苦的时候，和妹妹在金凤呈祥打工，每天这首歌都要听几十遍，听了一个多月。结果就是已经刻到骨子里了，也不是觉得多好听，但每次听到，都会发呆，想起以前一天打两份工，吃一星期白水挂面的日子。', '2019-03-21 11:47:29', '0', '1', '0', '1');
INSERT INTO `tb_comment` VALUES ('558305130252996608', '1', '2', '1991，双城故事，电影中曾志伟患了癌症一个人去了美国，后来在一所敬老院曾志伟在那唱歌，回头发现校长站在他的身后，此时：如真，如假。曾志伟哭了。这个镜头一直在他心里，每次他听这首歌，他都会情不自禁的哭，两人从发小到影视巨霸，歌坛巨星，不变的是他们的友情。', '2019-03-21 15:05:16', '0', '1', '0', '1');
INSERT INTO `tb_comment` VALUES ('558305221445554176', '1', '2', '用了这么多年网易云音乐，还是一个人打开这个播放器，一个人听歌，一个人倔强地留在网易。还有你们从来不给我赞多年以后一定会变化的吧', '2019-03-21 15:05:38', '2', '2', '558305130252996608', '1');
INSERT INTO `tb_comment` VALUES ('558306961750364160', '1', '2', '幸运听过伍佰的演唱会！洒脱到炸！拜拜俩字说完就没影儿了，我从兴奋状突然成了蒙蔽状！这就没啦？好突然的自我！', '2019-03-21 15:12:32', '1', '3', '1111', '1');
INSERT INTO `tb_comment` VALUES ('558309696902139904', '2', '2', '回的啥玩意', '2019-03-21 15:23:25', '2', '1', '558308589299699712', '1');
INSERT INTO `tb_comment` VALUES ('559767730111254528', '559752501067386880', '2', '等你拿掉我手中的酒 掐掉我手里的烟 依偎在我的怀里 给你做你爱吃的菜 给你买着漂亮的衣服 带你去看春暖花开 秋意盎然 在这个薄情的世界里 我一直都深情的活着 卑微的爱着～', '2019-03-25 00:00:00', '0', '1', '0', '1');
INSERT INTO `tb_comment` VALUES ('569478451606720512', '2', '555356895221780480', '说的什么玩意儿', '2019-04-21 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575461989820796928', '1', '1', '个人倔强地留', '2019-05-07 00:00:00', '0', '1', '0', '1');
INSERT INTO `tb_comment` VALUES ('575462536355385344', '1', '555356895221780480', '一个人倔强地留在网易。还有你们从来不给我赞多年以后一定会变化的吧', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575462586682839040', '1', '555356895221780480', '一个人倔强地留在网易。还有你们从来不给我赞多年以后一定会变化的吧', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575462771408375808', '1', '555356895221780480', '一个人倔强地留在网易。还有你们从来不给我赞多年以后一定会变化的吧', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575465579276800000', '1', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575465627729399808', '1', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575466473062010880', '1', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575466523016171520', '1', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575466573150687232', '1', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575467040555536384', '2', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575467731554537472', '2', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575467778891452416', '2', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575468821935165440', '2', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575469269488373760', '2', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575469962257371136', '2', '555356895221780480', '强地留在网易。还有你们从来不给我赞多', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575470104691740672', '1', '555356895221780480', '', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575470169124638720', '1', '555356895221780480', 'asd', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575470488722214912', '1', '555356895221780480', 'asd', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575470778653478912', '1', '555356895221780480', 'asd', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('575471061425065984', '1', '555356895221780480', 'asd', '2019-05-07 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('580058470720606208', '579987430661296128', '555356895221780480', '文章文采斐然，辞藻华美，语言朴实，文笔清新，情感丰富，人物形象饱满，条理清晰，结构层次分明，情节合理，跌宕起伏，描写生动，引人入胜，艺术感染力强，结尾画龙点睛，寓意深刻，发人深思，意味悠长。', '2019-05-20 00:00:00', '0', '0', '0', '1');
INSERT INTO `tb_comment` VALUES ('580064131923709952', '579987430661296128', '555356895221780480', '开章简洁有特色。传统说法的“凤头”在新媒体时代变得极其变态的重要，因为日常泛滥的信息量太多太杂刷新太快，迫使人养成快速浏览的习惯，如果没能被文章开头抓住，马上就会弃读。', '2019-05-20 00:00:00', '0', '0', '0', '1');
