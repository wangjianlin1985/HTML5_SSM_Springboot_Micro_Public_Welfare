/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : school_activity_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-06-29 14:43:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_activityinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_activityinfo`;
CREATE TABLE `t_activityinfo` (
  `activityId` int(11) NOT NULL auto_increment COMMENT '活动id',
  `typeObj` int(11) NOT NULL COMMENT '活动类型',
  `title` varchar(60) NOT NULL COMMENT '活动主题',
  `activityPhoto` varchar(60) NOT NULL COMMENT '活动图片',
  `content` varchar(5000) NOT NULL COMMENT '活动内容',
  `activityTime` varchar(50) NOT NULL COMMENT '活动时间',
  PRIMARY KEY  (`activityId`),
  KEY `typeObj` (`typeObj`),
  CONSTRAINT `t_activityinfo_ibfk_1` FOREIGN KEY (`typeObj`) REFERENCES `t_activitytype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_activityinfo
-- ----------------------------
INSERT INTO `t_activityinfo` VALUES ('1', '1', '周末一起去扫马路', 'upload/58ce0ab0-fd35-42c0-8a6b-a2cddeb46313.jpg', '<p>老人院附近的马路太脏了，大家一起去搞卫生吧！</p>', '2018-02-04下午3点');
INSERT INTO `t_activityinfo` VALUES ('2', '2', '一起去养老院看望老人', 'upload/df4f8b11-d3f1-4d1a-9a95-587d8d6b4951.jpg', '<p>老人们需要关爱，等这2周抽个空时间一起去看望老人，带好吃的给他们！</p>', '2018年2月10日');

-- ----------------------------
-- Table structure for `t_activitytype`
-- ----------------------------
DROP TABLE IF EXISTS `t_activitytype`;
CREATE TABLE `t_activitytype` (
  `typeId` int(11) NOT NULL auto_increment COMMENT '活动类型id',
  `typeName` varchar(20) NOT NULL COMMENT '活动类型名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_activitytype
-- ----------------------------
INSERT INTO `t_activitytype` VALUES ('1', '环境保护');
INSERT INTO `t_activitytype` VALUES ('2', '义工服务');

-- ----------------------------
-- Table structure for `t_donation`
-- ----------------------------
DROP TABLE IF EXISTS `t_donation`;
CREATE TABLE `t_donation` (
  `donationId` int(11) NOT NULL auto_increment COMMENT '捐款id',
  `userObj` varchar(30) NOT NULL COMMENT '捐款人',
  `donationMoney` float NOT NULL COMMENT '捐款金额',
  `dunationTime` varchar(20) default NULL COMMENT '捐款时间',
  `dunationMemo` varchar(500) default NULL COMMENT '捐款备注',
  `sheHeState` varchar(20) NOT NULL COMMENT '审核状态',
  PRIMARY KEY  (`donationId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_donation_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_donation
-- ----------------------------
INSERT INTO `t_donation` VALUES ('1', 'user1', '200', '2018-01-28 16:20:08', '我用我支付宝打的，我的支付宝账号是dashen@126.com', '已审核');
INSERT INTO `t_donation` VALUES ('2', 'user2', '100', '2018-01-30 18:07:24', '我也要为公益做点贡献，已经通过支付宝转账，管理员审核下', '待审核');
INSERT INTO `t_donation` VALUES ('3', 'user1', '500', '2018-06-29 14:33:09', '给予山区的儿童关爱', '已审核');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '找到组织了', '以后我有发言地了哦', 'user1', '2018-01-28 16:32:25', '对的', '2018-01-28 16:32:30');
INSERT INTO `t_leaveword` VALUES ('2', '我想要要参加公益活动方便了', '这个网站不错哦，使用它可以看到我感兴趣的公益活动', 'user2', '2018-01-30 18:18:01', '--', '--');

-- ----------------------------
-- Table structure for `t_news`
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `newsId` int(11) NOT NULL auto_increment COMMENT '新闻id',
  `title` varchar(80) NOT NULL COMMENT '新闻标题',
  `newClass` varchar(30) NOT NULL COMMENT '新闻分类',
  `content` varchar(5000) NOT NULL COMMENT '新闻内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES ('1', '都需要爱护环境', '环境保护', '<p>全世界人民团结起来，一起爱护咱们的地球环境！</p>', '2018-01-28 16:33:05');
INSERT INTO `t_news` VALUES ('2', '关于义工服务', '义工服务', '<p>义工服务要认真</p>', '2018-01-28 16:33:33');
INSERT INTO `t_news` VALUES ('3', '关爱留守儿童', '公益资讯', '<p>中国留守儿童和老人太多了，希望大家多关心下它们</p>', '2018-01-28 16:33:54');

-- ----------------------------
-- Table structure for `t_signup`
-- ----------------------------
DROP TABLE IF EXISTS `t_signup`;
CREATE TABLE `t_signup` (
  `signId` int(11) NOT NULL auto_increment COMMENT '报名id',
  `activityObj` int(11) NOT NULL COMMENT '报名的活动',
  `userObj` varchar(30) NOT NULL COMMENT '报名人',
  `signUpVow` varchar(500) NOT NULL COMMENT '报名宣誓',
  `signUpTime` varchar(20) default NULL COMMENT '报名时间',
  PRIMARY KEY  (`signId`),
  KEY `activityObj` (`activityObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_signup_ibfk_1` FOREIGN KEY (`activityObj`) REFERENCES `t_activityinfo` (`activityId`),
  CONSTRAINT `t_signup_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_signup
-- ----------------------------
INSERT INTO `t_signup` VALUES ('1', '1', 'user1', '爱护环境 从我做起！', '2018-01-28 16:19:59');
INSERT INTO `t_signup` VALUES ('2', '1', 'user2', '小女子也喜欢爱护环境哦！', '2018-01-30 17:38:19');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '双鱼林', '男', '2018-01-02', 'upload/9bd20c2f-9c36-4966-bb8e-431ce903ac5f.jpg', '13573598343', 'dashen@163.com', '四川成都红星路13号', '2018-01-28 16:14:42');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '李晓霞', '女', '2018-01-04', 'upload/bf95e49b-be94-4b5e-b4b6-720bbc9cee62.jpg', '15129893233', 'xiaoxia@163.com', '福建福州滨海路', '2018-01-30 17:37:32');
