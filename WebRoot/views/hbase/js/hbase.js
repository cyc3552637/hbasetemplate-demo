function init(){
	 $.ajax({
		   type:"post",
		   url:"../../hbase/action/doHbase.mc",
		   data:{
		   },
		   success:function(data){
		   var result=eval(data);
		   for(var i = 0;i < result.length;i++){
			   alert(result[i].info_service);
			   alert(result[i].row);
		   }
		   
		   },

	 error:function(xhr, statue, error) {
		   alert("失败");
	   },
	   cache : false
	   });
}