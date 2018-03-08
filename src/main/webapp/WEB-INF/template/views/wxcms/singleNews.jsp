<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>多图文素材管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/multiGraphic.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery-1.9.1.min.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
<script type="text/javascript" src="<%=path%>/res/js/plugins/webuploader/webuploader.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/plugins/webuploader/webuploader.css"></script>

<script type="text/javascript" src="<%=path%>/res/js/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/plugins/ueditor/ueditor.all.min.js"></script>

<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=path %>/res/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<!--引入自定义js-->
<script src="<%=path%>/res/js/web/singlenews.js"></script>

<script type="text/javascript" >
var ue = UE.getEditor('content');
window.UEDITOR_HOME_URL = "<%=path %>/res/js/plugins/ueditor/";	
</script>
</head>
	
<body>

<div class="top">
	<jsp:include page="/WEB-INF/template/views/common/top.jsp"></jsp:include>
</div>
<div class="main">
    <jsp:include page="/WEB-INF/template/views/common/leftDemo.jsp"></jsp:include>
	  
    <div class="mainRight">
        <jsp:include page="/WEB-INF/template/views/common/topNavDemo.jsp"></jsp:include>
    	
         <div class="posInfo">
        	<ul>
            	<li><span class="posIcon"><img src="<%=path%>/res/images/pos_icon_03.png">当前位置：</span></li>
                <li><h6>
                     <a href="<%=path%>/wxcms/toMultiGraphic">图文管理</a>
                    </h6>
                    <h6>></h6>
                    <h6>单图文添加</h6>
               </li>
            </ul>
        </div>
       
       
 <div class="rightInfo">
  <div class="graPage">
             <!-- <h2 class="posTitle">素材库/新建图文消息</h2>-->
              <div class="graMain">
                  <dl class="graLeft">
                      <dt>单图文添加</dt>
                      <dd class="graContainer">
                              <!-- 标题 -->
                              <input type="hidden" id="left_title" >
                              <!-- 图文消息的封面图片素材id（必须是永久mediaID） -->
                              <input type="hidden" id="left_thumb_media_id" >
                              <!-- 作者 -->	 
                              <input type="hidden" id="left_author" >
                              <!--图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空 -->
                              <input type="hidden" id="left_digest" >
                              <!--是否显示封面，0为false，即不显示，1为true，即显示  -->
                              <input type="hidden" id="left_show_cover_pic" >
                              <!-- 内容 -->
                              <input type="hidden" id="left_content" >
                              <!-- 图文消息的原文地址，即点击“阅读原文”后的URL -->
                              <input type="hidden" id="left_content_source_url" >
                              
                              <!--封面图片访问地址 -->
                              <input type="hidden" id="left_mainImage" >
                         
                            <div class="graBig">
                              <img src="<%=path%>/res/images/img_default.png">
                              <h6>标题</h6>
                            </div>
                          
                      </dd>
                  </dl>
                 
                  <div class="graMid">
                      
                      <div class="titleInput"><span>请输入标题：</span>
                         <input type="text" placeholder="标题" id="title" style="width:80%" 
                         onkeyup="inputTitle()" onblur="checkTitle(this)" >
                         <br>
                         <!-- 标题的非空提示 -->
                         <span id="title_tip" style="color:red"></span>
                      </div>
                      <div class="titleInput"><span>请输入作者：</span>
                         <input type="text" placeholder="作者" id="author" 
                         style="width:80%" onblur="checkAuthor(this)">
                          <br>
                         <!-- 作者的非空提示 -->
                         <span id="author_tip" style="color:red"></span>
                      </div>
                     <div class="titleInput">
                      <span>请输入内容：</span>
                      <textarea  id="content" 
                         style="width:680px;height:300px; margin-left:0px!important;"></textarea>
                      <!-- 内容的非空提示 -->
                      <span id="content_tip" style="color:red"></span>
                      </div>
                      <ul class="graStyleEdite">
                          <li class="li01">
                              <dl>
                                  <dt>封面<span class="fontDefault">大图片建议尺寸：900像素 * 500像素</span></dt>
                                  <dd>
                                      <!-- 点击可以选择图片<input type="button" value="本地上传" id="uploadBtn">        -->
                                      <div id="uploadImage1" class="btn btn-default" style="padding:0px;width:80px;height:30px;line-height:30px;"></div>
                                      <style type="text/css">
                                          .webuploader-element-invisible{
                                           border:1px solid;   
                                            width: 80px;
										    height: 30px;
										    border: 1px solid;
										    margin-left: -12px;
										    overflow: hidden;
										    padding:0px;
										    margin-left:1px;
										    opacity:0 
										    }
										    
                                      </style>
                                      <input type="button" value="从图片库选择">
                                  </dd>
                                  <dd>
                                      <img id="fileImage1" style="width:110px;height:100px;" src="" alt="多图文封面图片">
                                  </dd>
                                  <dd>
                                       <span class="fontDefault" id="imgShow">
                                                                                                                              封面图片显示在正文中
                                          <!-- 保存imgId -->                                                                          
                                          <input type="checkbox"  id="show_cover_pic">
                                       </span>                                                                   
                                  </dd>
                                  
                              </dl>
                              <!-- 封面图片 -->
                              <span id="thumbMedia_tip" style="color:red"></span>
                          </li>
                          
                          <li class="li02">
                              <dl>
                                  <dt>摘要<span class="fontDefault">必填</span></dt>
                                  <dd>
                                     <textarea  id="digest"  onblur="checkDigest(this)"></textarea>
                                  
                                  </dd>
                              </dl>
                              <span id="digest_tip" style="color:red"></span>
                          </li>
                      </ul>
                  </div>
                  <dl class="graRight">
                      <dt>多媒体</dt>
                      
                      <dd class="graTabs">
                          <div>图片</div>
                          <div>视频</div>
                          <div>音乐</div>
                          <div>音频</div>
                          <div>投票</div>
                      </dd>
                  </dl>
              </div>
            </div>
            
            <div class="graSave">
              <input type="button" value="保存" onclick="checkSingleNews()">
            </div>        	
            
         </div>       	
            
        </div>
    </div>

</body>
<script type="text/javascript">
$(document).ready(function() { 
	
	$("#fileImage1").hide();

}); 

//图片一上传
upload = WebUploader.create({
	
	// 选完文件后，是否自动上传。
    auto: true,
    // 上传图片排重
    duplicate: true,
    // swf文件路径
    swf: '<%=path%>/res/js/plugins/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: '<%=path%>/managerImg/uploadImg',
    id: 'uploadFile',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
    	id: '#uploadImage1',
    	innerHTML: '本地上传',
    	multiple: false
    },
    accept: {
        title: 'image',
        extensions: 'jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    },
    fileSingleSizeLimit:1024*1024*5
	
});
upload.on('error', function(handler){
	if("Q_TYPE_DENIED"==handler){
		alert("请按规定图片格式上传");
	}else if("F_EXCEED_SIZE"==handler){
		alert("图片超过规定大小");
	}
} )
//上传成功的事件
upload.on( 'uploadSuccess', function( file, response ) {
	//封面图片id
	var imgMediaId = response.imgMediaId;
	var imgUrl = response.url;
	
	if(imgUrl == null){
		alert("数据库保存图片失败");
		return false;
	}
	if(imgMediaId == null){
		alert("图片同步到微信失败");
		return false;
	}else{
		//var picURL = path + "/upload/"+imgUrl;
		$("#fileImage1").attr("src",imgUrl);
		
		$("#fileImage1").show();
		$("#left_mainImage").val(imgUrl);
		 $("#thumbMedia_tip").html("");
		
		//媒体id
		$("#left_thumb_media_id").val(imgMediaId);
		//左侧图片显示
		$(".graBig").find("img").attr("src",imgUrl);
	}

});

</script>
</html>
