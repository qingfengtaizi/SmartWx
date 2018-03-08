$(document).ready(function() { 
	
	//是否显示封面图片
	if($("#show_cover_pic").is(':checked')){
		$("#left_show_cover_pic").val(1);
	}else{
		$("#left_show_cover_pic").val(0);
	}
	
	
	//对于编辑器失去焦点
	UE.getEditor('content').addListener('blur',
			function(editor){
	    //使用editor.hasContents()方法判断编辑器里是否有内容
	    var hasFlag = UE.getEditor('content').hasContents();
	    
	    if(!hasFlag){
	        $("#content_tip").html("内容不能为空");
	        //使富文本编辑器获得焦点
	        UE.getEditor('content').focus();
	        return false;
	    }else{
	    	//使用editor.getContentTxt()方法可以获得编辑器的纯文本内容
	    	var contentText = UE.getEditor('content').getContent();
	    	$("#left_content").val(contentText);
	    	$("#content_tip").html("");
	    	return true;
	    }
	    
	});

}); 

//标题
function inputTitle(){
	var titleInput = $("#title").val().trim();
	$(".graBig").find("h6").html("");
	$(".graBig").find("h6").html(titleInput);
}
function checkTitle(thisInput){
	 var titleValue = $(thisInput).val().trim();
	 if(titleValue.length == 0){
		   $(thisInput).focus();
		   $("#title_tip").html("标题不能为空");
	       return false;
	 } else {
		   $("#title_tip").html("");
		   $("#left_title").val(titleValue);
		   $(".graBig").find("h6").html("");
		   $(".graBig").find("h6").html(titleValue);
		   return true;
	 }
}

//作者
function checkAuthor(thisInput){
	 var authorValue = $(thisInput).val().trim();
	 
	 if(authorValue.length == 0){
		   $(thisInput).focus();
		   $("#author_tip").html("作者不能为空");
	       return false;
	 }else{
		   $("#author_tip").html("");
		   $("#left_author").val(authorValue);
		   return true;
	 }
}


//摘要
function checkDigest(thisInput){
	 var digestValue = $(thisInput).val().trim();
	 
	 if(digestValue.length == 0){
		   $("#digest_tip").html("摘要不能为空");
		   $(thisInput).focus();
	       return false;
	 }else{
		   $("#digest_tip").html("");
		   $("#left_digest").val(digestValue);
		   return true;
	 }
}


//注册
function checkSingleNews(){
	
    //标题
	var leftTitleValue = $("#left_title").val().trim();
	if(leftTitleValue.length == 0){
		$("#title_tip").html("标题不能为空");
		$("#title").focus();
		return false;
	}else{
		$("#title_tip").html("");
	}
	
	
	//作者
	var leftAuthorValue = $("#left_author").val().trim();
	if(leftAuthorValue.length == 0){
		$("#author_tip").html("作者不能为空");
		$("#author").focus();
		return false;
	}else{
		$("#author_tip").html("");
	}
	
	//内容
	var leftContentValue = $("#left_content").val().trim();
	
	if(leftContentValue.length == 0){
		UE.getEditor('content').focus();
		$("#content_tip").html("内容不能为空");
		return false;
	}else{
		$("#content_tip").html("");
	}
	
	
	//封面图片id
	var leftThumbMediaId = $("#left_thumb_media_id").val().trim();
	if(leftThumbMediaId.length == 0){
	    $("#thumbMedia_tip").html("封面图片不能为空");
		return false;
	}else{
		
	    $("#thumbMedia_tip").html("");
	}
	
	
	//摘要
	var leftDigestValue = $("#left_digest").val().trim();
	if(leftDigestValue.length == 0){
		$("#digest_tip").html("摘要不能为空");
		$("#digest").focus();
		return false;
	}else{
		$("#digest_tip").html("");
	}

	//封面图片是否显示在正文中
	var leftShowCoverPicValue = $("#left_show_cover_pic").val().trim();
	
	if($("#show_cover_pic").is(':checked')){
		leftShowCoverPicValue = 1;
	}else{
		leftShowCoverPicValue = 0;
	}
	
	//原文链接
	var leftContentSourceUrl = $("#left_content_source_url").val().trim();

	
	var picPathValue = $("#left_mainImage").val();
	
	
	//创建单图文对象
	var articleObj = new Object();
	articleObj.title = leftTitleValue;
	articleObj.thumbMediaId = leftThumbMediaId;
	articleObj.author = leftAuthorValue;
	articleObj.brief = leftDigestValue;
	articleObj.showpic = leftShowCoverPicValue;
	articleObj.description = leftContentValue;
	articleObj.fromurl = leftContentSourceUrl;
	//封面图片url
	articleObj.picpath = picPathValue;
	doSaveSingleNews(articleObj);
}

//进入数据库进行操作
function doSaveSingleNews(articleObj){
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/msgnews/addSingleNews',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:articleObj,
	    success:function(msg){
	    	result = msg;
	    },
	    error:function(msg){
	    	alert("保存异常");
	    }
	});

    if(result == "1"){
       alert("保存成功");
       //跳转到多图文素材管理列表
	   window.location.href = path+"/wxcms/toMultiGraphic";
	}
    
    
    if(result == "-1"){
    	alert("保存失败");
    }
}

//去除字符串前后空格
String.prototype.trim=function(){   
    return this.replace(/(^\s*)|(\s*$)/g, "");    
}