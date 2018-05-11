# SmartWx

开发语言：JAVA

数据库：MYSQL

JAVA开发框架：Spring MVC+Spring+Mybatis

缓存框架：j2cache

前端开发框架：Layui+JQuery+html

前台模板引擎：art-template

## 简介
本项目是一个完整的微信公众号web操作版，直接编译即可运行。让用户不再用关心微信的底层接口，直接使用页面进行操作，简单方便。
1. QQ群：[![加入QQ群](https://img.shields.io/badge/QQ群-671585861-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=b7f4442a2a6b369a55aaa549bc0fbf14c478543d6a9c8f74eafca0378fcfcf40) 或 [![加入QQ群](https://img.shields.io/badge/QQ群-671585861-blue.svg)](https://jq.qq.com/?_wv=1027&k=5bGtRX8)，推荐点击按钮入群，当然如果无法成功操作，请自行搜索群号`671585861`进行添加
1. 官方网址：[http://smartwx.webcsn.com](http://smartwx.webcsn.com/)
1. 本项目在以下代码托管网站同步更新:
* 码云：https://gitee.com/qingfengtaizi/wxmp
* github：https://github.com/qingfengtaizi/wxmp-web


## 应用场景
- SmartWx是一款基于JAVA企业级平台研发的微信公众号管理系统， 依托企业级JAVA的高效、安全、稳定等优势，开创国内JAVA版开源微信公众号管理系统先河。
- SmartWx采用最流行的Spring语言，来实现多公众号的管理。
- 如果您要需要搭建一个微信公众号管理系统,那么您可以用SmartWx
- 如果您厌烦了微信公众号管理后台枯燥的页面，那么您可以用SmartWx
- 如果您手中有很多公众号，那么您可以用SmartWx

## 功能模块
- 账号信息
1. 绑定公众号信息

- 文本信息
1. 新建消息
2. 消息发送

- 图文管理
1. 多图文
2. 单图文

- 菜单管理
1. 支持几乎所有的微信菜单类型
2. 可视化管理
3. 保存&同步

- 粉丝管理
1. 批量同步粉丝
2. 单个粉丝同步
3. 发送文本消息和图文消息

- 多账号管理
1. 添加公众号
2. 选择公众号

## 环境要求

- JDK7或更高版本
- Tomcat7.0或更高版本
- MySQL5.1或更高版本

## 部署说明

1. 创建数据库。如使用MySQL，字符集选择为`utf8`。
1. 执行数据库脚本。数据库脚本在`/doc`目录下。
1. 在eclipse中导入maven项目。点击eclipse菜单`File` - `Import`，选择`Maven` - `Existing Maven Projects`。
1. 设置项目编码为utf-8，选择jdk1.7版本或以上，不要选择jre。
1. 修改数据库连接。打开`/src/main/resources/property/jdbc.properties`文件，根据实际情况修改`jdbc.url`、`jdbc.username`、`jdbc.password`的值
1. 上传图片设置。upload.properties 如属性文件所描述，如图片想放到项目中，res.upload.url注释即可
1. j2cache缓存设置。j2cache.properties 如果不需要启用二级缓存，属性文件中 13行j2cache.broadcast设置为jgroups，31行j2cache.L2.provider_class设置为none。如需启用redis，反之即可
1. 七牛云配置。app.properties 项目中暂时未用到，可废弃
1. 编译项目。在eclipse中，右键点击项目名，选择`Run as` - `Maven build...`，`Goals`填入`clean package`，然后点击`Run`，第一次运行需要下载jar包，请耐心等待。
1. 部署项目。将项目部署到Tomcat7或以上版本，启动Tomcat。也可使用maven tomcat7插件运行，jetty插件运行暂时有问题
1. 访问系统。地址：[http://localhost:8080/](http://localhost:8080/)；用户名：smartwx，密码：smartwx
1. 由于项目采用前后台分离，也为了将来nginx读写分离，项目需root根目录运行，如若不然可能会js，css加载不到的情况（自己可修改）

## 相关连接
官方网址：[https://smartwx.webcsn.com](https://smartwx.webcsn.com/)

版主QQ：[794890569](http://wpa.qq.com/msgrd?v=3&uin=794890569&site=qq&menu=yes/)

服务器支持：[http://webcsn.com](http://webcsn.com/)

---------------------------------
## 2.0版新页面

![登陆页](https://gitee.com/uploads/images/2018/0416/121618_e13f2f9a_1256378.png "登陆页.png")
![首页](https://gitee.com/uploads/images/2018/0416/121734_4481f09b_1256378.png "首页.png")
![运营数据](https://gitee.com/uploads/images/2018/0416/121758_c768271b_1256378.png "运营数据.png")
![账号信息](https://gitee.com/uploads/images/2018/0416/121832_438ad9c0_1256378.png "账号信息.png")
![菜单](https://gitee.com/uploads/images/2018/0416/121900_097ad23f_1256378.png "菜单.png")
![单图文](https://gitee.com/uploads/images/2018/0416/121924_d1f66f51_1256378.png "单图文.png")
![多图文](https://gitee.com/uploads/images/2018/0416/121955_118b9807_1256378.png "多图文.png")
![粉丝](https://gitee.com/uploads/images/2018/0416/122033_0ba4b847_1256378.png "粉丝.png")