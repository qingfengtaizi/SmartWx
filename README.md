## 本项目是一个完整的微信公众号web操作版，直接编译即可运行。让用户不再用关心微信的底层接口，直接使用页面进行操作，简单方便。
### 包括服务器绑定、文本管理、图文管理、菜单管理、粉丝管理、群发消息等
---------------------------------
[![QQ](https://img.shields.io/badge/chat-on%20QQ-ff69b4.svg?style=flat-square)](https://jq.qq.com/?_wv=1027&k=5bGtRX8)
[![Apache-2.0](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![使用IntelliJ IDEA开发维护](https://img.shields.io/badge/IntelliJ%20IDEA-提供支持-blue.svg)](https://www.jetbrains.com/idea/)

---------------------------------
### 重要信息
1. 项目首发：**2018-2-25 发布[【1.0.1BETA】](https://gitee.com/qingfengtaizi/wxmp.git)**！
1. [开源中国本项目的首页](https://www.oschina.net/p/wxmp-web)，欢迎大家积极留言评分
1. 项目需要的数据库脚本在doc中，直接导入即可，后续的文章也会放在此文件夹下，请多关注
1. 本项目推荐使用tomcat运行，jetty也可运行（maven已配置，直接插件运行即可），相关配置可参考pom文件
1. 温馨提示由于微信一些接口需要认证授权，建议在开发阶段，申请微信测试账号进行开发
1. ___项目1.2.1BETA版本正在紧张的研发中，预计将在3月中旬开发完成，此次更新将会带给大家全新的页面，逻辑更加清晰的后台代码。
此外备受大家期待的多公众号支持，缓存优化，也会在3月底和大家见面，敬请期待！___
--------------------------------
### 其他说明
1. 如有新功能需求，发现BUG，或者由于微信官方接口调整导致的代码问题，可以直接在[【Issues】](https://gitee.com/qingfengtaizi/wxmp/issues)页提出issue，便于讨论追踪问题；
1. **捐助渠道已开通，如有意向请直接前往[【托管于码云的项目首页】](https://gitee.com/qingfengtaizi/wxmp)，在评论区上方可以找到“捐助”按钮。非常感谢各位捐助的同学！**
1. 本项目要求的最低JDK版本是1.7，其他更早的JDK版本则需要自己改造实现。
1. 本项目在以下代码托管网站同步更新:
* 码云：https://gitee.com/qingfengtaizi/wxmp

---------------------------------
### 技术交流方式
1. QQ群： [![加入QQ群](https://img.shields.io/badge/QQ群-671585861-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=b7f4442a2a6b369a55aaa549bc0fbf14c478543d6a9c8f74eafca0378fcfcf40) 或 [![加入QQ群](https://img.shields.io/badge/QQ群-671585861-blue.svg)](https://jq.qq.com/?_wv=1027&k=5bGtRX8)，推荐点击按钮入群，当然如果无法成功操作，请自行搜索群号`671585861`进行添加
1. 其他的帮助方式以及论坛我们也会逐步完善

---------------------------------
### 版本说明
1. 本项目定为大约每两个月发布一次正式版，版本号格式为X.X.0（如2.1.0，2.2.0等），遇到重大问题需修复会及时提交新版本，欢迎大家随时提交Pull Request；
1. BUG修复和新特性一般会先发布成小版本作为临时测试版本（如2.4.5.BETA，2.4.6.BETA等，即尾号不为0，并添加BETA字样，以区别于正式版）；
分别查看所有最新的版本。
 
---------------------------------
### 我们能做什么
![登陆页](https://gitee.com/uploads/images/2018/0227/205432_227caccc_1256378.png "登陆页.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0227/205930_03bba9d6_1256378.png "首页.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0227/210122_bdc251e3_1256378.png "功能页.png")