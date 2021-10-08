function init(){
	 $.ajax({
		   type:"post",
		   url:"../../login/action/LoginRedis.mc",
		   data:{
		   pageNum:1,
		   pageSize:5
		   },
		   success:function(data){
		   alert(data.loginredis.username);
		   alert(data.loginredis.password);
		   }
		   });
}