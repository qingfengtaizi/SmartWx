$(document).ready(function() { 
	//输入框失去焦点事件
	inputOnBlur();

	//点击立即注册按钮
	$("#loginpwdBtn").click(function(){
		updateLoginPwd();
	});
}); 

//输入框失去焦点事件
function inputOnBlur(){
	//账号
	$("#pwd").blur(function(){
		//输入账号
		var oldpwdInput = $("#pwd").val().trim();
		checkOldPwd(oldpwdInput);
	});
	//请输入密码
	$("#newpwd").blur(function(){
		//输入密码
		var passwordInput = $("#password").val().trim();
		checkNewPwd(passwordInput);
	});
	//请确认密码
	$("#newpwdagain").blur(function(){
		//确认密码
		var againPassInput = $("#againpass").val().trim();
		checkNewPwdAagin(againPassInput);
	});
}

//注册
function updateLoginPwd(){

	var oldpwdInput = $("#pwd").val().trim();
	var oldCheckFlag = checkOldPwd(oldpwdInput);
	
	if(!oldCheckFlag){
		return false;
	}
	//输入密码
    var newpwdInput = $("#newpwd").val().trim();
	//验证输入的密码
	var passFlag = checkNewPwd(newpwdInput);
	if(!passFlag){
		return false;
	}
	
	//再次输入密码
	var againPassInput = $("#newpwdagain").val().trim();
	//确认密码
	var confirmPwdFlag = checkNewPwdAagin(againPassInput);
	if(!confirmPwdFlag){
		return false;
	}else{
		var userId = $("#sysuserId").val().trim();
    	var user = new Object();
    	user.id = userId;
    	user.pwd = oldpwdInput;
    	user.newpwd = newpwdInput;

    	doUpdate(user);
	}

}

//进入数据库进行操作
function doUpdate(user){
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/login/updateLoginPwd',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:user,
	    success:function(msg){
	    	result = msg;
	    },
	    error:function(msg){
	    	alert(msg);
	    }
	});

    if(result == 1){
	   //注册成功，跳转到预约列表页面
	   window.location.href = path+"/sysuser/updatepwdTip";
	}else{
	   alert("密码修改失败");
	}
}

//验证新密码
function checkOldPwd(oldPwdInput) {
	var flag=false;
    //之前保存的
    var oldPwdValue = $("#oldPwd").val().trim();

    if (oldPwdInput.length == 0) {
        $("#oldpwd_Tip").html("请输入旧密码");
        return false;
    } else {
        $.ajax({
            url: path + '/login/checkPwd',
            type: 'POST',
            async: false,
            dataType: 'json',
            data: {pwd: oldPwdInput},
            success: function (result) {
                if (result == "1") {
                    $("#oldpwd_Tip").html("旧密码输入有误");
                } else {
                    $("#oldpwd_Tip").html("");
                    flag=true;
                }
            },
            error: function (msg) {
                alert(msg);
            }
        });
        return flag;
    }
}


//验证新密码
function checkNewPwd(passwordValue){
   //只能输入6-18个字母、数字、下划线的组合   
   var passReg = /^[a-zA-Z_0-9]{6,18}$/; 
   if(passwordValue.length == 0){
       $("#newpwd_Tip").html("请输入新密码");
       return false;
   } else {
	   if(passwordValue.indexOf(' ') >= 0){
		   $("#newpwd_Tip").html("密码中不能有空格");
	       return false;
	   }else if(!passReg.test(passwordValue)){
		   $("#newpwd_Tip").html("密码由6-18位字母、数字组成");
	       return false;
	   } else {
		   $("#newpwd_Tip").html("");
	       return true;
	   }
   }
}
//请确认密码
function checkNewPwdAagin(againPassValue){
	//第一次输入密码
	var newPwdValue = $("#newpwd").val().trim();

	if(againPassValue.length == 0){
		$("#newpwdagain_Tip").html("请确认新密码");
	    return false;
	}else{
		if(newPwdValue != againPassValue){
		    $("#newpwdagain_Tip").html("两次输入密码不一致,请重新输入");
		    return false;
		}else{
			$("#newpwdagain_Tip").html("");
		    return true;
		}
	}
}

//去除字符串前后空格
String.prototype.trim=function(){   
    return this.replace(/(^\s*)|(\s*$)/g, "");    
}