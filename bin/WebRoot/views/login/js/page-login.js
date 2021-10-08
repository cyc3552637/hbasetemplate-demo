/* TYPE YOUR JAVA SCRIPT HERE */
function login(){
    $.ajax({
   type:"post",
   url:"../../login/action/LoginActionFind.mc",
   data:{
   username:$("#username").val(),
   password:$("#pwd").val()
   },
   success:function(data){
        var flag = String($.trim(data));
   	 	if(flag == "true"){
   	 	alertify.alert("登录成功",function(e){
   	 	location.href = "../main/default.html";
   	 	});
   	 	
   	 	}
   	 	else {
   	 	alertify.alert("登录失败",function(e){
   	 	location.href = "login.html";
   	 	});
   	 	}
     	
   },
   error:function(xhr, statue, error) {
	    alertify.alert("登录失败",function(e){
   	 	location.href = "login.html";
   	 	});
   },
   cache : false
   });

}	