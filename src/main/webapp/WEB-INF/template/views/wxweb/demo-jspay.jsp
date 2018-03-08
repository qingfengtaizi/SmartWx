<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>微信JS支付 Demo</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
  <link rel="stylesheet" href="http://demo.open.weixin.qq.com/jssdk/css/style.css?ts=1420774989">
  <script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
</head>
<body ontouchstart="">
<div class="wxapi_container">
    <div class="wxapi_index_container">
      <ul class="label_box lbox_close wxapi_index_list">
        <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-basic">基础接口</a></li>
        <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-pay">微信支付接口</a></li>
      </ul>
    </div>
    <div class="lbox_close wxapi_form">
      <h3 id="menu-basic">基础接口</h3>
      <span class="desc">判断当前客户端是否支持指定JS接口</span>
      <button class="btn btn_primary" id="checkJsApi">checkJsApi</button>

      <h3 id="menu-pay">微信支付接口</h3>
      <span class="desc">发起一个微信支付请求</span>
      <button class="btn btn_primary" id="chooseWXPay">chooseWXPay</button>
    </div>
  </div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"> </script>
<script>
	
//	var oldTime = (new Date("2012/12/25 20:11:11")).getTime(); //得到毫秒数
  var oldTime = (new Date()).getTime(); //得到毫秒数
   oldTime=   Math.round(oldTime/1000);
	//alert("==>"+oldTime);
	//document.write("=========>>"+oldTime);

var g_timestamp="";//支付的时候还需要用到
var g_nonceStr="";//支付的时候还需要用到
var url = location.href.split('#')[0];
$.ajax({
	url:path+'/wxapi/jsTicket' ,
    type:'POST',
    data:{url:url},
    success:function(data){
    	var errcode = data.errcode;
    	var sign = data.data;
    	if(errcode == 0){//没有错误
    		g_timestamp=sign.timestamp;
    		g_nonceStr=sign.nonceStr;
    		wx.config({
    		    debug: true,//true的时候可以alert信息 
    		    appId: sign.appId,
    		    timestamp: sign.timestamp,
    		    nonceStr: sign.nonceStr, 
    		    signature: sign.signature,  //sha1签名得到signature
    		      jsApiList: [
    		                  'checkJsApi',
    		                  'chooseWXPay'
    		                ]
    		});
    	}
    } ,
    dataType:'json'
});	

</script>
<script src="http://demo.open.weixin.qq.com/jssdk/js/api-6.1.js?ts=1420774989"> </script>

<script>
	wx.ready(function () {
 // 1 判断当前版本是否支持指定 JS 接口，支持批量判断
 document.querySelector('#checkJsApi').onclick = function () {
   wx.checkJsApi({
     jsApiList: [
       'getNetworkType',
       'previewImage'
     ],
     success: function (res) {
       alert(JSON.stringify(res));
     }
   });
 };

  // 10 微信支付接口===> jsapi支付==>jsskd 微信支付demo
  // 10.1 发起一个支付请求
var v_openid="otLBWs_uiGnrWBGgHEemPZTQLatE"; //应该放在隐藏域中	 
var v_paySign="";
var v_package="";
var v_timestamp="";  
var v_nonceStr="";
var v_appid="";
var v_signType="MD5"//默认新方式为MD5;
function doPayBefore(){
	var retmsg="";
	 $.ajax({
		url:path+'/wxapi/pay' ,
	    type:'POST',
	    async:false,
	    data:{
	    	'openid': v_openid,
	    	'timestamp': g_timestamp,
	    	'nonceStr': g_nonceStr
	    },
	    success:function(res){
	    	alert(JSON.stringify(res));
	    	var data=res.data;
	    	if(data.errMsg == "0") 	{//没有错
	    		v_paySign=data.paySign;
	    		v_package=data.package;
	    		//v_timestamp=data.timestamp;
	    		v_timestamp=g_timestamp;
	    		//v_nonceStr=data.nonceStr;
	    		v_nonceStr=g_nonceStr;
	    		v_appid=data.appid;
	    		v_signType=data.signType;
	    		
	    		/* alert("v_timestamp:"+v_timestamp+"|"+"g_timestamp:"+g_timestamp+"|");
	    		alert("v_paySign:"+v_paySign+"|"+"v_package:"+v_package+"|"+"v_paySign:"+v_paySign+"|"+"v_timestamp:"+v_timestamp+"|"+"v_nonceStr:"+v_nonceStr+"|"+"v_appid:"+v_appid+"|"); */
	    	}
	    	else{
	    		//alert(""+data.errMsg );
	    		retmsg=data.errMsg;
	    	}
	    } ,
	    dataType:'json'
	});
	 
	 return retmsg;
  } 
  
  document.querySelector('#chooseWXPay').onclick = function () {
/*     wx.chooseWXPay({
      timestamp: 1414723227,
      nonceStr: 'noncestr',
      package: 'addition=action_id%3dgaby1234%26limit_pay%3d&bank_type=WX&body=innertest&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F120.204.206.246%2Fcgi-bin%2Fmmsupport-bin%2Fnotifypay&out_trade_no=1414723227818375338&partner=1900000109&spbill_create_ip=127.0.0.1&total_fee=1&sign=432B647FE95C7BF73BCD177CEECBEF8D',
      paySign: 'bd5b1933cda6e9548862944836a9b52e8c9a2b69'
    }); */
    
    var  getParaListByFlag=doPayBefore();
    if(getParaListByFlag!=""){
    	alert(""+getParaListByFlag);
    	return;
    }
	
/**  
      jsapi支付
     支付授权目录一定要写对，否则js一直会提示 {chooseWXPay:fail}
     支付授权目录指的是调用jsapi支付页面的文件目录（且一定要以/结尾）
     现在jsapi支付的支付页面是为http://www.yjydt.cn/wxweb/jssdk.jsp
     故支付授权目录为http://www.yjydt.cn/wxweb/ 
*/
    
 wx.chooseWXPay({
	    debug: true,//true的时候可以alert信息 
	 appId: v_appid,
     timestamp: v_timestamp,
     nonceStr: v_nonceStr,
     package: v_package,
     signType:v_signType,
     paySign:v_paySign,
     success: function (res) {
  	     alert("success==>"+JSON.stringify(res));
     },
     fail: function (res) {
  	     alert("fail==>"+JSON.stringify(res));
     }
   });	 

  };

 

  var shareData = {
    title: '微信JS-SDK DEMO',
    desc: '微信JS-SDK',
    link: 'http://www.163.com/',
    imgUrl: 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0'
  };
  wx.onMenuShareAppMessage(shareData);
  wx.onMenuShareTimeline(shareData);
});

wx.error(function (res) {
  alert(res.errMsg);
});
</script>
</html>