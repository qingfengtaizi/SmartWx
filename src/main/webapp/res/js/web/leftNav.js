// JavaScript Document
$(function(){
	$(".bor").next().css("float","left");
	
	
})


//leftNav高度大于浏览器窗口内容高度时，出现滚动条
$(function(){
	var wh=$(document).height()-60;
	
	var h=$(".leftNav").height();
	
	if(h>wh){
		$(".leftNav").css("height",wh);
		}
	
	})


$(function(){
	$(".firstLi").hover(function(){
		$(this).find(".fTitle").css("font-size","16px");
		},function(){
			$(this).find(".fTitle").css("font-size","15px");
			})
	})


//-获取当前html的title值
$(function(){
	var t=$(document).attr("title");
	
	switch (t)
	{
	case "URL和Token":
	  $(".firstLi").eq(0).css("background","#1e7980");
	  $(".firstLi").eq(0).find(".fTitle").css("color","#eda74b");
	  break;
	case "文本消息":
	  $(".firstLi").eq(1).css("background","#1e7980");
	  $(".firstLi").eq(1).find(".fTitle").css("color","#eda74b");
	  break;
	case "图文消息":
	  $(".firstLi").eq(2).css("background","#1e7980");
	  $(".firstLi").eq(2).find(".fTitle").css("color","#eda74b");
	  break;
	case "菜单管理":
	  $(".firstLi").eq(3).css("background","#1e7980");
	  $(".firstLi").eq(3).find(".fTitle").css("color","#eda74b");
	  break;
	case "粉丝管理":
	  $(".firstLi").eq(4).css("background","#1e7980");
	  $(".firstLi").eq(4).find(".fTitle").css("color","#eda74b");
	  break;
	case "永久素材管理":
	  $(".firstLi").eq(5).css("background","#1e7980");
	  $(".firstLi").eq(5).find(".fTitle").css("color","#eda74b");
	  break;
	case "生成参数二维码":
	  $(".firstLi").eq(6).css("background","#1e7980");
	  $(".firstLi").eq(6).find(".fTitle").css("color","#eda74b");
	  break;
	case "发送消息":
	  $(".firstLi").eq(7).css("background","#1e7980");
	  $(".firstLi").eq(7).find(".fTitle").css("color","#eda74b");
	  break;
	
	case "多图文素材管理":
		  $(".firstLi").eq(8).css("background","#1e7980");
		  $(".firstLi").eq(8).find(".fTitle").css("color","#eda74b");
		  break;
		  
	case "OAuth认证":
		  $(".firstLi").eq(9).css("background","#1e7980");
		  $(".firstLi").eq(9).find(".fTitle").css("color","#eda74b");
		  break;
	case "JS-SDK":
	  $(".firstLi").eq(10).css("background","#1e7980");
	  $(".firstLi").eq(10).find(".fTitle").css("color","#eda74b");
	  break;
	case "WeUI":
	  $(".firstLi").eq(11).css("background","#1e7980");
	  $(".firstLi").eq(11).find(".fTitle").css("color","#eda74b");
	  break;
	} 
	})
	
	
	//判断浏览器的兼容模式
			$(function(){  
	            var is360 = false;  
	            var isIE = false;  
	            if (window.navigator.appName.indexOf("Microsoft") != -1){  
	                isIE= true;  
	            }  
	            if(isIE&&(window.navigator.userProfile+'')=='null'){  
	                is360 = true;  
	            }  
	            if(is360 & window.top.document.compatMode=="BackCompat"){  //判断360浏览器为怪异模式
	               // document.body.innerHTML = "<img class='bodyimg' src='/res/images/Quirks.png'>"; 
	               // var rootElement = document.body;
	               // var image = document.createElement("<img class='bodyimg'>");
	               // image .src = "../../res/images/Quirks.png"; 
	               // var rootElement = document.body;
	               // rootElement.appendChild(image);
	            	document.body.innerHTML = "<div class='bodyimg'>"+"<p style=';margin-left:90px;font-size:24px;color:#a82e2e'>sorry~~360安全浏览器兼容模式下不支持该页面</p>" +
	            	                       "<p style=';margin-left:130px;margin-top:30px;font-size:13px;color:#999;width:400px'>浏览器名称："+navigator.appName+ "</p>" + 
	            	                       "<p style=';margin-left:130px;margin-top:10px;font-size:13px;color:#999;width:400px'>版本号："+navigator.appVersion+ "</p>" +
	            			               "<p style='font-size:13px;color:#86b1e0;margin-top:60px;'>建议使用Firefox  、Google  、IE浏览器等新版本的浏览器或者在360安全浏览器的地址栏将浏览器的模式改为极速模式</p>"+"</div>";
	                $(".bodyimg").css({"width":" ",
	                	                "border":"1px  solid  ",
	                	                "left":"30%",
	                	                "top":"20%",
	                	                "position":"absolute",
	                	                "background":" "
	                	                });
	               // $(".topNav").css({"width":"100%"});
//	                $(".leftNav").css("width","100%");
//	                $(".mainRight").css({"margin-top":"0px"});
//	                $(".utInfo li input").css("line-height","35px","margin-top","10px");
//	                $(".utInfo li span").css({"margin-top":"-20px","line-height":"20px"});
//	                $(".gzhID").css("margin-bottom","20px");
//	                $(".messNum").css("margin-top","20px");
//	                $(".utInfo li input").css("line-height","none");
//	                $(".mainLeft").css({"margin-top":"-10px","height":"750px"});
//	                $(".utInfo h6").css({"width":"100%","":""});
//	                $(".gzhID h6").css({"margin-top":"-25px","margin-left":"45%"});	                
//	                $(".posInfo li h6").css("float","left");
//	                $(".messNum h6").css({"margin-top":"-20px","margin-left":"50%"})
//	                $(".rightInfo").css("width","95%");
//	                
//	                $(".helptext").css("margin-top","60px");
//	                $("input[name='imageFile']").parent().css("margin-top","80px");
//	                $("input[name='showpic']").parent().css({"margin-top":"40px","margin-left":"-240px"});
	               // $(".fm-form ul").css("margin-bottom","-60px")
	            //    $(".btn").parent().css({"margin-top":"100px"});
//	                $(".block-content-content").css("margin-top","-130px");
//	                $(".block-content-nav").css("margin-top","-100px");
//	                $(".bor").css("height","35px")
//	                $("input[name='inputcode']").prev(".bor").css("margin-left","0px");
//	                $("#id_name").parent("li").css("margin-top","160px");
//	                //$("#id_msgs").next().css("margin-bottom","-160px");
//	                $("textarea[name='content']").css("width","35%");
//	                $(".fm-form ul li select").css({"width":"35%","height":"35px"});
	                //$("select[name='sort']").prev(".bor").css("margin-left","-90px");
	                //$("select[name='mtype']").prev(".bor").css("margin-left","-90px");
	               // $(".gray-span").css({"float":"left","width":"55%","margin-left":"5px"});
	                //$("select[name='eventType']").prev(".bor").css("margin-left","-90px");
	               // $("input[name='inputcode']").prev(".bor").css({"margin-left":"-110px"});
	            //    $("#id_keymsg").css("margin-top","55px");
//	                $(".content").find(".block-content-content").css("margin-top","40px");
//	                $("input[name = 'url']").parents(".block-content-content").css({"margin-top":"-120px"});
	               
	                
	                
	              //  $("#queryPageForm").find(".infoTable").css("margin-left","100px")
//	                $(".content input[type='text']").css({"width":"100%","height":"35px"});
//	                $("#textmain").parents("ul").next().css({"margin-top":"200px"});
//	                
//	                $("ul li input[type='text']").css({"width":"35%","height":"35px"});
//	                $(".posIcon").css({"margin-top":"5px","float":"left"});
//	                $(".gzhID").parent().next().css({"margin-top":"120px"});
//	                $("select").css({"width":"35%"});
//	               
//	                
//	                $(".infoTable").css({"margin-left":"100px"});
//	                $("#c_main").css({"width":"35%","cols":"80","rows":"5"});
//	                $("#gjianz").css("width","35%");
//	                $("#gjianz").prev().css("margin-left","20px");
//	                $("#id_fixmsg").css({"margin-top":"50px"})
//	                $("#id_fixmsg").find(".bor").css({"margin-left":"-120px"});
	                
	                //弹出框
	              //  $("div[role='dialog']").css("_margin-top","30%;");
//	                $("#id_fans").css({"_width":"590px"});
	                
	                
	               // $(".ui-widget-header").css({"background":"#ccc","width":"100%","height":"50px"});
	               // $(".ui-dialog").css({"margin-top":"100px","height":"530px"});
	               // $(".ui-dialog-titlebar-close").css({"margin-left":"-200px","width":"20%","position":"absolute","border":"1px  solid "});
	               // $("#id_news_span").css({"margin-top":"20px"})
	               // $("#ui-id-1").css({"margin-top":"-10px"});
	               // $("#id_fans").css({"margin-top":"530px","height":"630px"})
	                // $(". ui-widget-header").css({"width":"100%"})
	                 
	               // $(".ui-widget-overlay").css({"position":"absolute!important","margin-top":"-200px"})
	                
	                 
	                 //首页
	                // $(".nav-top").parent(".main").css({"margin-left":"15%","margin-top":"10px"});
//	                 $(".main-right").css({"float":"none","width":"70%"});
//	                 $(".topRight").css({"overflow":"visible"});
//	                 $(".topPer .right").css({"background":"#404040"});
//	                 $(".top_li").css({"right":"30px"});
	                 
	                 
	                 //logon
	                 
	               //  $(".btn_div").css({"margin-left":"40%"});
//	                 $(".login-input").css({"margin-left":"12%"});
//	                 $(".login-btn").css({"margin-left":"-40px"});
//	                 
//	           
	               
	                
	            }//else if(isIE){  
	                //document.body.innerText = 'IE浏览器';  
	           // }  
	            
	           // var webstoreKeysLength = window.chrome && window.chrome.webstore ? Object.keys(window.chrome.webstore).length : 0;
	           // if (_track) {
	                // 360极速浏览器
	                // 360安全浏览器
	             //   return webstoreKeysLength > 1 ? '360ee' : '360se';
	          //  }
	       })  
			


