function table(pnum,psize){
    $.ajax({
   type:"post",
   url:"../../login/action/LoginActionFindAll.mc",
   data:{
   pageNum:pnum,
   pageSize:psize
   },
   dataType:"json",
   success:function(data){
	   $("#reTable tr").eq(1).nextAll().remove();
	   var tr = $("#reTr");  
     	$.each(data.pagelist, function(i,item){
     		var reTr = tr.clone();  
            var _index = i;
            reTr.children("td").each(function(inner_index){
            	switch(inner_index){  
                case(0):   
                   $(this).html(_index + 1);  
                   break;  
                case(1):
                   $(this).html(item.username);  
                   break;  
                case(2):  
                   $(this).html(item.password);  
                   break;  
                 
         }
            });
            reTr.insertBefore(tr);  
     	});
     	$("#reTable tr:last").hide();
  
   },
   error:function(xhr, statue, error) {
		alert("error");
  },
  cache : false
    });

}
function jqPaginator(Pages){
	  $.jqPaginator('#pagination', {
	        totalPages: Pages,
	        currentPage: 1,
	        wrapper:'<ul class="pagination"></ul>',
	        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
	        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
	        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
	        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
	        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
	        onPageChange: function (num) {
	           table(num,5);
	        }
	    });
	
}
function initTable(){
	 $.ajax({
		   type:"post",
		   url:"../../login/action/LoginActionFindAll.mc",
		   data:{
		   pageNum:1,
		   pageSize:5
		   },
		   success:function(data){
		      jqPaginator(data.totalPage);
		    },
		   error:function(xhr, statue, error) {
				alert("wrong");
		   },
		   cache : false
		   });
}