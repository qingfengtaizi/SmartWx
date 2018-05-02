
/*
SQLyog Ultimate v12.3.2 (64 bit)
MySQL - 5.7.18 : Database - wxmp2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wxmp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wxmp`;

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `jkey` varchar(100) NOT NULL DEFAULT '',
  `jvalue` varchar(500) DEFAULT '',
  `description` varchar(255) DEFAULT '',
  PRIMARY KEY (`jkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_config` */

insert  into `sys_config`(`jkey`,`jvalue`,`description`) values
('system_update_time','2018-04-19','系统当前版本发布时间'),
('system_version','2.0.0','系统当前版本');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键id',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `true_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(1) DEFAULT '0' COMMENT '性别：0是男 1是女',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `flag` int(1) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`account`,`pwd`,`true_name`,`sex`,`phone`,`email`,`avatar`,`flag`,`create_time`,`update_time`) values
('1','smartwx','fe5c291e5b5a8d8843ee455d163e1447','smartwx','0','','',NULL,NULL,NULL,NULL);

/*Table structure for table `wxcms_account` */

DROP TABLE IF EXISTS `wxcms_account`;

CREATE TABLE `wxcms_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `account` varchar(100) NOT NULL,
  `appid` varchar(100) DEFAULT NULL,
  `appsecret` varchar(100) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `msg_count` int(11) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_account` */

/*Table structure for table `wxcms_account_fans` */

DROP TABLE IF EXISTS `wxcms_account_fans`;

CREATE TABLE `wxcms_account_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(100) DEFAULT NULL,
  `subscribe_status` int(1) DEFAULT '1',
  `subscribe_time` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT '1',
  `language` varchar(50) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `remark` varchar(50) DEFAULT NULL,
  `wx_id` varchar(50) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_account_fans` */

/*Table structure for table `wxcms_account_menu` */

DROP TABLE IF EXISTS `wxcms_account_menu`;

CREATE TABLE `wxcms_account_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mtype` varchar(50) DEFAULT NULL,
  `event_type` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `input_code` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `msg_type` varchar(64) DEFAULT NULL,
  `msg_id` varchar(100) DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_account_menu` */

/*Table structure for table `wxcms_account_menu_group` */

DROP TABLE IF EXISTS `wxcms_account_menu_group`;

CREATE TABLE `wxcms_account_menu_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_account_menu_group` */

/*Table structure for table `wxcms_article` */

DROP TABLE IF EXISTS `wxcms_article`;

CREATE TABLE `wxcms_article` (
  `ar_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `digest` varchar(100) DEFAULT NULL,
  `show_cover_pic` int(1) DEFAULT '0',
  `url` varchar(200) DEFAULT NULL,
  `thumb_media_id` varchar(150) DEFAULT NULL,
  `content_source_url` varchar(200) DEFAULT NULL,
  `media_id` varchar(150) DEFAULT NULL,
  `news_id` int(11) DEFAULT NULL,
  `news_index` int(11) DEFAULT NULL,
  `pic_url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ar_id`),
  KEY `news_id` (`news_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_article` */

/*Table structure for table `wxcms_img_resource` */

DROP TABLE IF EXISTS `wxcms_img_resource`;

CREATE TABLE `wxcms_img_resource` (
  `id` varchar(32) NOT NULL,
  `media_id` varchar(100) DEFAULT NULL,
  `true_name` varchar(100) NOT NULL,
  `type` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(200) NOT NULL,
  `http_url` varchar(200) DEFAULT NULL,
  `size` int(9) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `flag` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_img_resource` */

/*Table structure for table `wxcms_login_info` */

DROP TABLE IF EXISTS `wxcms_login_info`;

CREATE TABLE `wxcms_login_info` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `user_id` varchar(40) DEFAULT NULL COMMENT '用户id',
  `account` varchar(50) DEFAULT NULL COMMENT '用户名',
  `phone` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `curr_login_time` datetime DEFAULT NULL COMMENT '本次登录时间',
  `curr_login_ip` varchar(15) DEFAULT NULL COMMENT '本次登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '上次登录IP',
  `login_out_time` datetime DEFAULT NULL COMMENT '退出时间',
  `login_online` bigint(20) DEFAULT '20' COMMENT '在线时间',
  `login_online_show` varchar(20) DEFAULT NULL COMMENT '在线时间：时分秒',
  `session_id` varchar(100) DEFAULT NULL COMMENT 'session id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录信息表';

/*Data for the table `wxcms_login_info` */

/*Table structure for table `wxcms_media_files` */

DROP TABLE IF EXISTS `wxcms_media_files`;

CREATE TABLE `wxcms_media_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `media_type` varchar(20) DEFAULT NULL COMMENT '媒体类型',
  `title` varchar(20) DEFAULT NULL COMMENT '标题',
  `introduction` varchar(500) DEFAULT NULL COMMENT '简介说明',
  `logic_class` varchar(50) DEFAULT NULL COMMENT '标签_逻辑分类',
  `media_id` varchar(100) DEFAULT NULL COMMENT '返回的media_id',
  `upload_url` varchar(200) DEFAULT NULL COMMENT '返回的wx服务器url',
  `rmk` varchar(500) DEFAULT NULL COMMENT '备注_预留',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_media_files` */

/*Table structure for table `wxcms_msg_base` */

DROP TABLE IF EXISTS `wxcms_msg_base`;

CREATE TABLE `wxcms_msg_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_type` varchar(20) DEFAULT NULL,
  `input_code` varchar(20) DEFAULT NULL,
  `rule` varchar(20) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `read_count` int(11) DEFAULT '0',
  `favour_count` int(11) unsigned zerofill DEFAULT '00000000000',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_msg_base` */

/*Table structure for table `wxcms_msg_news` */

DROP TABLE IF EXISTS `wxcms_msg_news`;

CREATE TABLE `wxcms_msg_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mult_type` varchar(5) DEFAULT NULL COMMENT '单图文多图文类型',
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `brief` varchar(255) DEFAULT NULL,
  `description` longtext,
  `pic_path` varchar(255) DEFAULT NULL,
  `show_pic` int(11) DEFAULT '0',
  `url` varchar(255) DEFAULT NULL,
  `from_url` varchar(255) DEFAULT NULL,
  `base_id` int(11) DEFAULT NULL,
  `media_id` varchar(100) DEFAULT NULL COMMENT '上传后返回的媒体素材id',
  `thumb_media_id` varchar(150) DEFAULT NULL COMMENT '封面图片id',
  `news_index` int(11) DEFAULT NULL COMMENT '多图文中的第几条',
  `account` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_msg_news` */

/*Table structure for table `wxcms_msg_news_combin` */

DROP TABLE IF EXISTS `wxcms_msg_news_combin`;

CREATE TABLE `wxcms_msg_news_combin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `main_id` int(11) NOT NULL,
  `msg_news_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`main_id`,`msg_news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_msg_news_combin` */

/*Table structure for table `wxcms_msg_text` */

DROP TABLE IF EXISTS `wxcms_msg_text`;

CREATE TABLE `wxcms_msg_text` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(10) DEFAULT NULL,
  `content` longtext,
  `base_id` int(11) NOT NULL,
  `account` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `wxcms_tpl_msg_text`;
CREATE TABLE `wxcms_tpl_msg_text` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tpl_id` varchar(50) DEFAULT NULL,
  `title` varchar(10) DEFAULT NULL,
  `content` longtext,
  `base_id` int(11) NOT NULL,
  `account` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `wxcms_msg_text` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
