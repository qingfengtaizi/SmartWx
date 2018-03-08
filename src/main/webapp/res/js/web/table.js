// JavaScript Document

/*tbale 奇数行变色*/
$(function(){
	
	var obj=$("table").children("tbody").find("tr");
	var num=obj.length;
	
	for( var i=0; i<num;i++){
		if(i%2==0){
			$(obj).eq(i).css("background","#f9f9f9");
			}
		}
	})

	
	
	
	
//以下部分的js为个人中心部分的js	 


//验证姓名
     function cname(username){
       
        var numberReg = /^[0-9]*$/;
        if(username == ''){
            $("#username").html("请输入姓名");
            
        } else {
        	if(username.indexOf(' ') >= 0){
	            $("#username").html("姓名中不能有空格");
	            return false;
            }else if(numberReg.test(username)){
            	$("#username").html("姓名不能是数字");
                return false;
            } else {
                $("#username").html("");
                return true;
            }
        }
       
     }

  //验证输入的账号
     function caccount(accoutValue){
    	if(accoutValue == ""){
    		$("#useraccount").html("请输入账号");
    		return false;
    	}else{
	        if(accoutValue.length < 6 || accoutValue.length > 12){
	            $("#useraccount").html("账号长度6-12位");
	            return false;
	        }else{
	        	$("#useraccount").html("");
	        	return true;
	        }
    	}
     }
	 
	 //验证输入的手机hao
     function cphone(phoneValue){
证
        var reg=/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(phoneValue == ""){
           $("#userphone").html("请输入手机号");
           return false;
        }else{
           if(!reg.test(phoneValue)){
              $("#userphone").html("手机号码格式不正确");
              return false;
	       } else {
	       	  $("#userphone").html("");
	       	  return true;
	       }
        }
     } 
	 
	 
	 //身份证号码的规则
     function ccarID(ccarIDValue){
     	
     	
     	 if(ccarIDValue != ""){
     	 	 //15位数身份证验证正则表达式 ：
      	     var isIDCardReg=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
             //18位数身份证验证正则表达式 ：
             var isIDCardLongReg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
             
	         if(isIDCardReg.test(ccarIDValue)||isIDCardLongReg.test(ccarIDValue)){
                  $("#usercarID").html("");
                  return true;
			  }else{
			  	  $("#usercarID").html("身份证号格式不正确");
			  	  return false;
			  
     	      }
			  }else{
				  $("#usercarID").html("身份证不能为空");
				  return true;
     	      }
		   
		  
			 }
 
     //验证邮箱
     function cmailbox(mailboxValue){

        var box=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if(mailboxValue == ""){
           $("#usermailbox").html(" ");
           return false;
        }else{
           if(!box.test(mailboxValue)){
              $("#usermailbox").html("邮箱码格式不正确");
              return false;
	       } else {
	       	  $("#usermailbox").html("");
	       	  return true;
	       }
        }
     } 




//以下部分的js为密码修改部分的js	 

 //旧密码验证
     function coldpassword(passvalue){
       
        
        if(passvalue == ""){
			
            $("#username").html("请输入旧密码");
            
        } else {
                $("#username").html("");
                return true;
            }
        }
       
     


  //新密码验证
     function cnewpassword(accoutValue){
		var olFlag = $("#againpassword").val();
		 
    	if(accoutValue == ""){
    		$("#useraccount").html("请输入新密码");
    		return false;
    	}else{
	        	$("#useraccount").html("");
	        	return true;
	        }
    	}
    
	 
	 //再次输入新密码的验证
     function cagainpassword(againValue){
     	var newFlag = $("#newpassword").val();
    
        if(againValue !== newFlag){
           $("#userphone").html("两次新密码不一致");
           return false;
        } else if(againValue ==""){
	       	  $("#userphone").html("请输入新密码");
	       	  return true;
	    }else{
			$("#userphone").html("");
	       	  return true;
			}
     }

	//密码找回的手机号验证
	 
	function checktell(tellvalue){
		
		var reg=/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(tellvalue == ""){
		   $("#postpw").css("display","block")
           $("#postpw").html("请输入手机号");
           return false;
        }else{
           if(!reg.test(tellvalue)){
			  $("#postpw").css("display","block") 
              $("#postpw").html("手机号码格式不正确");
              return false;
	       } else {
			   $("#postpw").css("display","none") 
	       	  $("#postpw").html("");
	       	  return true;
	       }
        }
		
	}
	
	
//密码找回方式的显示隐藏控制
function  selectfunc(){
	
	var cardTypeName = $("#passtype").find("option:selected").val();

	if(cardTypeName == 1){
		
		$("#telldisplay").css("display","none");
		$("#maildisplay").css("display","block");
		
		}else{
			
			$("#telldisplay").css("display","block");
		    $("#maildisplay").css("display","none");
			}
	}

	
	//密码找回方式的邮箱验证
function checkmaill(mailval){
	 var box=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if(mailval == ""){
		   $("#maillpw").css("display","block")	
           $("#maillpw").html("请输入邮箱");
           return false;
        }else{
           if(!box.test(mailval)){
			  $("#maillpw").css("display","block")	 
              $("#maillpw").html("邮箱码格式不正确");
              return false;
	       } else {
			  $("#maillpw").css("display","none")	 
	       	  $("#maillpw").html("");
	       	  return true;
	       }
        }
	} 

//验证码的检测
function cnumber(yvalue){
	if(yvalue==""){
		$("#yanumber").html("请输入验证码")
		}else{
			$("#yanumber").html("")
			}
	
	}

//保存时验证所有选项是否为空	
function checkall(){
	 var  OLDpw=$("#oldpassword").val();
	 var  NEWpw=$("#newpassword").val();
	 var  AGAINpw=$("#againpassword").val();
	 var  TELLnu=$("#cht").val();
	 var  MAILLnu=$("#chmail").val();
	 var  YZMnu=$("#yanzheng").val();
	 
	 if(OLDpw==""){
		  $("#username").html("请输入旧密码");
		 }else{
			  $("#username").html("");
			 }
	 if(NEWpw==""){
		 $("#useraccount").html("请输入新密码");
		 }else{
			 $("#useraccount").html("");
			 }
	  if(AGAINpw==""){
		   $("#userphone").html("请输入新密码");
		  }	else{
			   $("#userphone").html("");
			  }	 
	if(TELLnu==""){
		
		$("#postpw").css("display","block")
        $("#postpw").html("请输入手机号");
		
		}else{
			$("#postpw").css("display","none")
			$("#postpw").html("");
			}
	if(MAILLnu==""){
		$("#maillpw").css("display","block")	
        $("#maillpw").html("请输入邮箱");
           
		}else{
			$("#maillpw").css("display","none")	
            $("#maillpw").html("");
			}
	if(YZMnu==""){
		$("#yanumber").html("请输入验证码")
		}else{
			
			$("#yanumber").html("")
			}					 
	}	
	

