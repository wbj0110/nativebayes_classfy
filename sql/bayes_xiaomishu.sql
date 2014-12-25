/*
Navicat MySQL Data Transfer

Source Server         : bayes_xiaomishu
Source Server Version : 50173
Source Host           : 192.168.4.70:3306
Source Database       : bayes_xiaomishu

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2014-07-16 17:44:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T_DIC
-- ----------------------------
DROP TABLE IF EXISTS `T_DIC`;
CREATE TABLE `T_DIC` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(500) DEFAULT NULL,
  `tag_type_id` int(32) DEFAULT NULL,
  `city_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tag_type_id_index` (`tag_type_id`) USING BTREE,
  KEY `city_id_index` (`city_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `nice_name` varchar(32) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
