$(document).ready(function() { 
	

}); 

//验证关键字
function checkCode(thisInput){
	 var codeValue = $(thisInput).val().trim();
	 if(codeValue.length == 0){
		   $("#code_tip").html("关键字不能为空");
	       return false;
	 } else {
		   $("#code_tip").html("");
		   return true;
	 }
}

//内容
function checkContent(thisInput){
	 var contentValue = $(thisInput).val().trim();
	 
	 if(contentValue.length == 0){
		   $("#content_tip").html("内容不能为空");
	       return false;
	 }else{
		   $("#content_tip").html("");
		   return true;
	 }
}




//注册
function checkMsgText(){
	
    //标题
	var codeValue = $("#gjianz").val().trim();
	if(codeValue.length == 0){
		$("#code_tip").html("关键字不能为空");
		return false;
	}else{
		$("#code_tip").html("");
	}
	
	
	//内容
	var contentValue = $("#textmain").val().trim();
	if(contentValue.length == 0){
		$("#content_tip").html("内容不能为空");
		return false;
	}else{
		$("#content_tip").html("");
	}
	
	
	//创建单图文对象
	var textObj = new Object();
	textObj.id = $("#id").val().trim();
	textObj.baseId = $("#baseId").val().trim();
	textObj.inputcode = codeValue;
	textObj.content = contentValue;
		
		
		
	doSaveText(textObj);
}

//进入数据库进行操作
function doSaveText(textObj){
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/msgtext/updateText',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:textObj,
	    success:function(msg){
	    	result = msg;
	    },
	    error:function(msg){
	    	alert("保存异常");
	    }
	});

    if(result == "1"){
       alert("添加成功");
	   window.location.href = path+"/msgtext/list";
	}else if(result == "2"){
       alert("更新成功");
       window.location.href = path+"/msgtext/list";
    }else{
       alert("保存失败");
    }
}

//去除字符串前后空格
String.prototype.trim=function(){   
    return this.replace(/(^\s*)|(\s*$)/g, "");    
}