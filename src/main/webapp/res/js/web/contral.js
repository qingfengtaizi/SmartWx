// JavaScript Document




   //验证输入的账号是否唯一
     function checkaccound(accoutValue){
    	if(accoutValue == ""){
    		$("#username").html("请输入账号");
    		return false;
    	}else{
	        if(accoutValue.length < 6 || accoutValue.length > 12){
	            $("#username").html("账号长度6-12位");
	            return false;
	        }else{
	        	$("#username").html("");
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
	
	//密码找回的手机号验证
	 
	function checkt(tellvalue){
		
		var reg=/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(tellvalue == ""){
           $("#postpw").css("display","block");
		   $("#postpw").html("请输入手机号");
		   $(".getyz").css("margin-top","30px");
		   
           return false;
        }else{
           if(!reg.test(tellvalue)){
			  $("#postpw").css("display","block"); 
              $("#postpw").html("号码格式不正确");
			  $(".getyz").css("margin-top","30px");
              return false;
	       } else {
	       	  $("#postpw").html("");
			  $(".getyz").css("margin-top","10px");
	       	  return true;
	       }
        }
		
	}
	
	
	//密码找回方式的邮箱验证
function checkm(mailval){
	 var box=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if(mailval == ""){
           
		   $("#maillpw").css("display","block");
		   $("#maillpw").html("请输入邮箱");
		   $(".getyz").css("margin-top","30px");
           return false;
        }else{
           if(!box.test(mailval)){
              $("#maillpw").html("邮箱格式不正确");
			  $("#maillpw").css("display","block");
			  $(".getyz").css("margin-top","30px");
              return false;
	       } else {
	       	  $("#maillpw").html("");
			  $(".getyz").css("margin-top","10px");
	       	  return true;
	       }
        }
	} 
	
	//验证码的验证
function  yam(yvalue){
	if(yvalue==""){
		$("#yanumber").css("display","block");
		$("#yanumber").html("请输入验证码")
		return true;
		
		}else {
			  $("#yanumber").css("display","none");
	       	  
	       	  return true;
	       }
	
	}	
	
	
//点击下一步时进行判断所有选项是否为空

function next(){
	
	 var  oldpw=$("#oldpassword").val();
	 var  tellcheck=$("#cht").val();
	 var  mailcheck=$("#chmail").val();
	 var  yacheck=$("#yanzheng").val();
 
	
	if(oldpw == ""){
		
    		$("#username").html("请输入账号");
    		
	    }else{
			$("#username").html("");
			
			}
		
	if(tellcheck == ""){
		
           $("#postpw").css("display","block");
		   $("#postpw").html("请输入手机号");
		   $(".getyz").css("margin-top","30px");
		   
          
        }else{
			 $("#postpw").css("display","none");
			 $(".getyz").css("margin-top","10px");
			
			}
	
	 if(mailcheck == ""){
           
		   $("#maillpw").css("display","block");
		   $("#maillpw").html("请输入邮箱");
		   $(".getyz").css("margin-top","30px");
          
        }else{
			 $("#maillpw").css("display","none");
			 $(".getyz").css("margin-top","10px");
			
			}
	
	
	if(yacheck==""){
		$("#yanumber").css("display","block");
		$("#yanumber").html("请输入验证码")
		
		}else{
			$("#yanumber").css("display","none");
			
			}
				
	
	}
	
//密码层显示-->

function zhaohpw(){
	
	$(".backpw").css("display","block")
	$(".bg_center").css("display","block")
	
	
	}	
	