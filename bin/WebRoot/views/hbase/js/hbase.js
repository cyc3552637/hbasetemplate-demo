function init(){
	 alert('aaaaaa');
	 $.ajax({
		   type:"post",
		   url:"../../hbase/action/doHbase.mc",
		   data:{
		   },
		   success:function(data){
		   alert(data.mapList1);
		   $.each(data.mapList1, function(i,item){
		   });
		   }
		   });
}