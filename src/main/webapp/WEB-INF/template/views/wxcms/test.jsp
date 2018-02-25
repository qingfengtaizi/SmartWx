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

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
<script  src="<%=path%>/res/js/plugins/webuploader/webuploader.js"></script>
<script  src="<%=path %>/res/js/plugins/webuploader/webuploader.css"></script>
<style type="text/css">
body{
	background-color: #ffffff;
	color:  #FFCCCC;
}
.uploadImages{
	display: inline-block;
}	
</style>
</head>
<body>
	
	
		<div style="margin-top:20px; text-align: center;">
			<div class="uploadImages">
				<div >
					<img style="width: 100px;height: 100px;" id="fileImage1"  
					      src="<%=path%>/res/images/icon_nav_toast.png" >
				</div>
				<div id="uploadImage1"></div>
				<input id="mainImage" type="hidden" name="mainImage" value="">
			</div>		
			
		</div>
	
</body>
<script type="text/javascript">
//图片一上传
upload = WebUploader.create({
	
	// 选完文件后，是否自动上传。
    auto: true,
    // 上传图片排重
    duplicate: true,
    // swf文件路径
    swf: '<%=path%>/res/js/plugins/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: '<%=path%>/wxcms/saveFile',
    id: 'uploadFile',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
    	id: '#uploadImage1',
    	innerHTML: '宝贝主图',
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
	
	if(imgMediaId == null){
		alert("图片同步到微信失败");
		return false;
	}else{
		$("#fileImage1").attr("src","<%=path%>/upload/"+response.url);
		$("#mainImage").val(response.url);
	}

});

</script>
</html>