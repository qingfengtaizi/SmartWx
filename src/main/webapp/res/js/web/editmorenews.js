$(document).ready(function() { 
	var ue = UE.getEditor('content');
    window.UEDITOR_HOME_URL = path + "/res/js/plugins/ueditor/";	
    
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
	    	var divIndex = $("#now_div").index();
	    	
	    	$("#now_div").find("#left_content_"+divIndex).val(contentText);
	    	$("#content_tip").html("");
	    	return true;
	    }
	    
	});
	

	
	//封面图片是否选中checkbox
	$("#show_cover_pic").change(function() { 
		var divIndex = $("#now_div").index();
		//是否显示封面图片
		if($("#show_cover_pic").is(':checked')){
			$("#now_div").find("#left_show_cover_pic_" + divIndex).val(1);
		}else{
			$("#now_div").find("#left_show_cover_pic_" + divIndex).val(0);
		}
	}); 
}); 

function addGra(){	
	
    //获得左侧div数量
    var leftDivCount =  $(".graContainer").find("div").length;
	//左边隐藏的数量
	var hiddenCount = leftDivCount - 1;
	
	
    var hiddenDiv = "<input type='hidden' id='left_title_"+hiddenCount+"' >"
				    +"<input type='hidden' id='left_thumb_media_id_"+hiddenCount+"' >"
				    +"<input type='hidden' id='left_author_"+hiddenCount+"' >"
					+"<input type='hidden' id='left_digest_"+hiddenCount+"' >"
					+"<input type='hidden' id='left_show_cover_pic_"+hiddenCount+"' >"
					+"<input type='hidden' id='left_content_"+hiddenCount+"' >" 
					+"<input type='hidden' id='left_content_source_url_"+hiddenCount+"' >"
					+"<input type='hidden' id='left_mainImage_"+hiddenCount+"' >";
	
	var divId = "now_div";
	var newDiv = "<div class='graSmall' id='"+divId+"' onClick='fnClick(this)'  "
	                +"  onMouseOver='mouseOver(this)' onMouseOut='mouseOut(this)'>"
	                + hiddenDiv
	                +" <ul>"
		            +" <li class='smallTitle'><h6>标题</h6></li>"
		            +" <li class='smallImg'><img src="+path+"/res/images/small_img.png>"
		            +" </li>"
		            +" </ul>"
		            +" <h6 class='deleteBtn' style='display:none' onClick='deleteDiv(this)'>删除</h6>"
		            +"</div>"
	
	 $(".graAdd").before(newDiv);
	 $(".graSmall").prevAll().removeAttr ("id");
	 
	 
	 $("#now_div").click();
}

//鼠标滑过，显示"删除标签"
function mouseOver(obj){
	$(obj).find(".deleteBtn").css("display","block");
}

function mouseOut(obj){
	$(obj).find(".deleteBtn").css("display","none");
}

//点击删除
function deleteDiv(obj){
	var newId= $(obj).parent().attr('id');
	$("#"+newId).prev().attr('id',newId);
		//若不是第一个元素，则删除包含该元素的父元素div	
    $(obj).parent().remove();
}

//单击左侧图文列表框时，右侧显示相应信息
function fnClick(obj){
	
	$(obj).css("border","1px solid green!important");
	
	var nowDivId = $(obj).attr("id");
	if(!nowDivId){
		$(obj).attr('id',"now_div");
	}
	
	var otherDivClass = $(obj).siblings().attr("class");
	
	if(otherDivClass != "graAdd"){
		var otherDivId = $(obj).siblings().attr("id");
		if(otherDivId){
			$(obj).siblings().removeAttr ("id");
		}
	}
	
	
	var divIndex = $("#now_div").index();

	//点击左侧，右侧显示相应标题
	var leftTitle=$("#now_div").find("#left_title_" + divIndex).val();
	$("#newsTitle").val(leftTitle);
	
	//作者
	var leftAuthor = $(obj).find("#left_author_" + divIndex).val();
	$("#author").val(leftAuthor);
	
	//内容
	var leftContent = $("#now_div").find("#left_content_"+divIndex).val();
	UE.getEditor('content').reset();
	UE.getEditor('content').setContent(leftContent);

	
	//是否显示封面图片
	var leftShowCoverPicValue = $("#now_div").find("#left_show_cover_pic_" + divIndex).val();
	
	if(leftShowCoverPicValue == 1){
		$("#show_cover_pic").attr("checked", true);
	}else{
		$("#show_cover_pic").attr("checked", false);
	}
	
	//左侧图片url
	var leftImageUrl =  $("#now_div").find("#left_mainImage_" +  divIndex).val();
	
	if(leftImageUrl !=null && leftImageUrl != ""){
		$("#fileImage1").attr("src",leftImageUrl);
		$("#now_div").find("img").attr("src",leftImageUrl);
	}

}


//标题
function inputTitle(){
	var titleInput = $("#newsTitle").val().trim();
    //当前操作的div
    $("#now_div").find("h6").eq(0).text(titleInput);
}
//标题失去焦点
function checkTitle(thisInput){
	 var titleValue = $(thisInput).val().trim();
	 //当前操作的
	 var divIndex = $("#now_div").index();
	 if(titleValue.length == 0){
		   $("#title_tip").html("标题不能为空");
		   $(thisInput).focus();
	       return false;
	 } else {
		   $("#title_tip").html("");
		   //当前操作的div
		   $("#now_div").find("h6").eq(0).text(titleValue);
		   $("#now_div").find("#left_title_"+divIndex).val(titleValue);
		   return true;
	 }
}

//作者
function checkAuthor(thisInput){
	 var authorValue = $(thisInput).val().trim();
	 var divIndex = $("#now_div").index();
	 
	 if(authorValue.length == 0){
		   $("#author_tip").html("作者不能为空");
		   $(thisInput).focus();
	       return false;
	 }else{
		   $("#author_tip").html("");
		   $("#now_div").find("#left_author_"+divIndex).val(authorValue);
		   return true;
	 }
}

//对内容验证
function checkContent(){
	
}
//保存多图文
function checkEditMoreNews(){
    var divLength = $(".graContainer").find(".graBig,.graSmall").length;

	var newsArray = new Array();
	for(var i=0;i<divLength;i++){
		var newDiv = $(".graContainer").find("div")[i];
		var newTitle = $(newDiv).find("#left_title_" + i).val().trim();
		
		if(newTitle.length == 0){
		    $("#title_tip").html("第"+(i+1)+"条图文的标题不能为空");
		    $(newDiv).click();
			return false;
		}
		
		var newAuthor = $(newDiv).find("#left_author_" + i).val().trim();
		
		if(newAuthor.length == 0){
		    $("#author_tip").html("第"+(i+1)+"条图文的作者不能为空");
		    $(newDiv).click();
			return false;
		}
		
		var newContent = $(newDiv).find("#left_content_" + i).val().trim();
		
		if(newContent.length == 0){
		    $("#content_tip").html("第"+(i+1)+"条图文的内容不能为空");
		    $(newDiv).click();
			return false;
		}
		
		
		//封面图片id
		var leftThumbMediaId = $("#left_thumb_media_id_" + i).val().trim();
		if(leftThumbMediaId.length == 0){
		    $("#thumbMedia_tip").html("第"+(i+1)+"条图文的封面图片不能为空");
		    $("#fileImage1").attr("src","");
		    $("#fileImage1").hide();
		    $(newDiv).click();
			return false;
		}
		

		//原文链接
		var leftContentSourceUrl = $("#left_content_source_url_" + i).val().trim();
		

		//封面图片是否显示在正文中
		var leftShowCoverPicValue = $(newDiv).find("#left_show_cover_pic_" + i).val().trim();
		
        if(leftShowCoverPicValue.length == 0){
        	leftShowCoverPicValue = 0;
        }
		
       
		var picPathValue = $("#left_mainImage_" +  i).val();
		
		var newsIdValue = $("#newsId_" +  i).val();
		var mediaIdValue = $("#mediaId_" +  i).val();
		
		//多图文时候摘要可为空
		var newDigest = "";
		var newObj = new Object();
		
		newObj.id = newsIdValue;
		newObj.mediaId = mediaIdValue;
		newObj.title = newTitle;
		newObj.author = newAuthor;
		newObj.brief = newDigest;
		newObj.description = newContent;
		newObj.thumbMediaId = leftThumbMediaId;
		newObj.fromurl = leftContentSourceUrl;
		newObj.showpic = leftShowCoverPicValue;
		//封面图片url
		newObj.picpath = picPathValue;
		
		newsArray[i] = newObj;
	}
		
	var jsonStr =  JSON.stringify(newsArray);
	//alert(jsonStr+ "数组");
	doSaveMoreNews(jsonStr);
}



//进入数据库进行操作
function doSaveMoreNews(jsonStr){
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/msgnews/updateMoreNews',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:{"rows":jsonStr},
	    success:function(msg){
	    	result = msg;
	    },
	    error:function(msg){
	    	alert("保存异常");
	    }
	});

    if(result == "1"){
       alert("更新成功");
       //跳转到多图文素材管理列表
	   window.location.href = path+"/wxcms/toMultiGraphic";
	}
    
    
    if(result == "-1"){
    	alert("更新失败");
    }
}

//去除字符串前后空格
String.prototype.trim=function(){   
    return this.replace(/(^\s*)|(\s*$)/g, "");    
}