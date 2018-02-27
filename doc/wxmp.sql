CREATE DATABASE /*!32312 IF NOT EXISTS*/`wxmp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wxmp`;

/*Table structure for table `t_wxcms_account` */

DROP TABLE IF EXISTS `t_wxcms_account`;

CREATE TABLE `t_wxcms_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `account` varchar(100) NOT NULL,
  `appid` varchar(100) DEFAULT NULL,
  `appsecret` varchar(100) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `msgCount` int(11) DEFAULT '1',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_account_fans` */

DROP TABLE IF EXISTS `t_wxcms_account_fans`;

CREATE TABLE `t_wxcms_account_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) DEFAULT NULL,
  `subscribeStatus` int(1) DEFAULT '1',
  `subscribeTime` varchar(50) DEFAULT NULL,
  `nickname` varbinary(50) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT '1',
  `language` varchar(50) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `remark` varchar(50) DEFAULT NULL,
  `wxid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_account_menu` */

DROP TABLE IF EXISTS `t_wxcms_account_menu`;

CREATE TABLE `t_wxcms_account_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mtype` varchar(50) DEFAULT NULL,
  `eventType` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `inputCode` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `msgId` varchar(100) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_account_menu_group` */

DROP TABLE IF EXISTS `t_wxcms_account_menu_group`;

CREATE TABLE `t_wxcms_account_menu_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_article` */

DROP TABLE IF EXISTS `t_wxcms_article`;

CREATE TABLE `t_wxcms_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `digest` varchar(100) DEFAULT NULL,
  `show_cover_pic` int(1) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `thumb_media_id` varchar(150) DEFAULT NULL,
  `content_source_url` varchar(200) DEFAULT NULL,
  `media_id` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_img_resource` */

DROP TABLE IF EXISTS `t_wxcms_img_resource`;

CREATE TABLE `t_wxcms_img_resource` (
  `id` varchar(32) NOT NULL,
  `mediaId` varchar(100) DEFAULT NULL,
  `trueName` varchar(100) NOT NULL,
  `type` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(200) NOT NULL,
  `httpUrl` varchar(200) DEFAULT NULL,
  `size` int(9) NOT NULL,
  `createTime` mediumtext NOT NULL,
  `updateTime` mediumtext NOT NULL,
  `flag` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_media_files` */

DROP TABLE IF EXISTS `t_wxcms_media_files`;

CREATE TABLE `t_wxcms_media_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mediaType` varchar(20) DEFAULT NULL COMMENT '媒体类型',
  `title` varchar(20) DEFAULT NULL COMMENT '标题',
  `introduction` varchar(500) DEFAULT NULL COMMENT '简介说明',
  `logicClass` varchar(50) DEFAULT NULL COMMENT '标签_逻辑分类',
  `media_id` varchar(100) DEFAULT NULL COMMENT '返回的media_id',
  `uploadUrl` varchar(200) DEFAULT NULL COMMENT '返回的wx服务器url',
  `rmk` varchar(500) DEFAULT NULL COMMENT '备注_预留',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_msg_base` */

DROP TABLE IF EXISTS `t_wxcms_msg_base`;

CREATE TABLE `t_wxcms_msg_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msgType` varchar(20) DEFAULT NULL,
  `inputCode` varchar(20) DEFAULT NULL,
  `rule` varchar(20) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `readCount` int(11) DEFAULT '0',
  `favourCount` int(11) unsigned zerofill DEFAULT '00000000000',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_msg_news` */

DROP TABLE IF EXISTS `t_wxcms_msg_news`;

CREATE TABLE `t_wxcms_msg_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `multType` varchar(5) DEFAULT NULL COMMENT '单图文多图文类型',
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `brief` varchar(255) DEFAULT NULL,
  `description` longtext,
  `picPath` varchar(255) DEFAULT NULL,
  `showPic` int(11) DEFAULT '0',
  `url` varchar(255) DEFAULT NULL,
  `fromurl` varchar(255) DEFAULT NULL,
  `base_id` int(11) DEFAULT NULL,
  `media_id` varchar(100) DEFAULT NULL COMMENT '上传后返回的媒体素材id',
  `thumbMediaId` varchar(150) DEFAULT NULL COMMENT '封面图片id',
  `news_index` int(11) DEFAULT NULL COMMENT '多图文中的第几条',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_msg_news_combin` */

DROP TABLE IF EXISTS `t_wxcms_msg_news_combin`;

CREATE TABLE `t_wxcms_msg_news_combin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `main_id` int(11) NOT NULL,
  `msgnews_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`main_id`,`msgnews_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_msg_text` */

DROP TABLE IF EXISTS `t_wxcms_msg_text`;

CREATE TABLE `t_wxcms_msg_text` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `base_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wxcms_sys_user` */

DROP TABLE IF EXISTS `t_wxcms_sys_user`;

CREATE TABLE `t_wxcms_sys_user` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键id',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `trueName` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(1) DEFAULT '0' COMMENT '性别：0是男 1是女',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `flag` int(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `event_update_adv_state` */

/*!50106 DROP EVENT IF EXISTS `event_update_adv_state`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`%` EVENT `event_update_adv_state` ON SCHEDULE EVERY 1 DAY STARTS '2017-10-10 01:15:24' ON COMPLETION PRESERVE ENABLE DO call reback() */$$
DELIMITER ;

/* Procedure structure for procedure `reback` */

/*!50003 DROP PROCEDURE IF EXISTS  `reback` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `reback`()
BEGIN
	UPDATE adv_advert SET STATUS='0',update_date=now() WHERE STATUS='5';
    END */$$
DELIMITER ;

/* Procedure structure for procedure `updateadvert` */

/*!50003 DROP PROCEDURE IF EXISTS  `updateadvert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `updateadvert`()
BEGIN
-- 定义一个变量接受广告主日限额
	DECLARE
		advRule DOUBLE default 0.00;
-- 定义一个变量接受广告主日消费总额
DECLARE result VARCHAR(200);
DECLARE
	advTrans DOUBLE DEFAULT 0.00;
DECLARE counts INT;
-- 消费总额是否大于广告主日限额 1 标示大于 默认为0
DECLARE
	flag INT DEFAULT 0;
DECLARE
	countt INT DEFAULT 0;
-- 广告主id
DECLARE 
advertiserId VARCHAR(50);
-- 遍历数据结束标志
DECLARE
	done INT DEFAULT 0;
-- 定义一个游标 接受来自sql语句返回的多条广告主id
DECLARE
	cur_account CURSOR FOR SELECT
		DISTINCT(user_id) 
	FROM
		adv_advert AS d
	WHERE
		d.`status` = '0' AND d.del_flag='0';
-- 将结束标志绑定到游标
DECLARE
	CONTINUE HANDLER FOR NOT FOUND
SET done = 1;
-- 打开游标
OPEN cur_account;
-- 使用loop遍历
read_loop :LOOP
	-- 取值 取多个字段
	FETCH 
-- 取到此时的广告主id 赋值给变量
	cur_account INTO advertiserId;
-- 如果遍历次数到了 自动停止遍历
IF done = 1 THEN
	LEAVE read_loop;
END IF;
-- 查询出广告主的日限额
SELECT
	count(daily_money) INTO counts
FROM
	adv_advertiser_dailyrule AS b
WHERE
	b.advertiser_id = advertiserId and b.del_flag='0';
IF counts >0
THEN
SELECT
	daily_money INTO advRule
FROM
	adv_advertiser_dailyrule AS b
WHERE
	b.advertiser_id = advertiserId and b.del_flag='0';
ELSE
	ITERATE read_loop;
end IF;
-- 查询出广告主日消费总额
SELECT
	count(trans_amount) INTO counts
FROM
	adv_advertiser_trans_log AS a
WHERE
-- 条件为当天数据
	DATE_FORMAT(a.create_date, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d')
AND a.user_id = advertiserId and a.del_flag='0' and a.trans_type='3';
IF counts >0
THEN
SELECT
	sum(trans_amount) INTO advTrans
FROM
	adv_advertiser_trans_log AS a
WHERE
-- 条件为当天数据
	DATE_FORMAT(a.create_date, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d')
AND a.user_id = advertiserId and a.del_flag='0' and a.trans_type='3';
ELSE
	ITERATE read_loop;
END IF;
IF advTrans >0 and advRule >0
THEN
-- 如果广告主日消费额度大于或者等于广告主限制额度
IF   advTrans >=advRule
 THEN
-- 把此广告主的所有广告置为失效
UPDATE adv_advert
SET `status` = 3
WHERE
	user_id = advertiserId AND del_flag='0';
ELSE
UPDATE adv_advert
SET `status` = 0
WHERE
	user_id = advertiserId AND del_flag='0';
END IF;
END IF;
set done = 0;
-- 关闭loop循环
END LOOP;
-- 关闭游标
CLOSE cur_account;
END */$$
DELIMITER ;

/*Table structure for table `user_view` */

DROP TABLE IF EXISTS `user_view`;

/*!50001 DROP VIEW IF EXISTS `user_view` */;
/*!50001 DROP TABLE IF EXISTS `user_view` */;

/*!50001 CREATE TABLE  `user_view`(
 `id` varchar(64) ,
 `company_id` varchar(64) ,
 `office_id` varchar(64) ,
 `login_name` varchar(100) ,
 `PASSWORD` varchar(100) ,
 `NO` varchar(100) ,
 `NAME` varchar(100) ,
 `email` varchar(200) ,
 `phone` varchar(200) ,
 `mobile` varchar(200) ,
 `user_type` char(1) ,
 `login_ip` varchar(100) ,
 `login_date` datetime ,
 `remarks` varchar(255) ,
 `login_flag` varchar(64) ,
 `company_name` varchar(100) ,
 `company_addr` varchar(100) ,
 `company_url` varchar(100) ,
 `abbreviation` varchar(100) ,
 `adv_user_type` char(1) ,
 `expected_costs` varchar(64) ,
 `adv_company_id` varchar(64) ,
 `photo` text ,
 `create_by` varchar(64) ,
 `create_date` datetime ,
 `update_by` varchar(64) ,
 `update_date` datetime ,
 `del_flag` char(1) 
)*/;
