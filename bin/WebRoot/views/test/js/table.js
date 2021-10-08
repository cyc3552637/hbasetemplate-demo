$(function() {
    querys();
})

function querys() {
	alert('aaaa');
    $("#edit").attr({"disabled":"disabled"});
    $("#delete").attr({"disabled":"disabled"});
    $("#loginList").bootstrapTable({
        url : '../../login/action/LoginActionFindAll.mc',
        height : '500',
        undefinedText : '-',
        pagination : true, // 分页
        striped : true, // 是否显示行间隔色
        queryParams : queryParams,
        cache : false, // 是否使用缓存
        pageList : [ 5, 10, 20 ],
        toolbar : "#toolbar",// 指定工具栏
        showColumns : true, // 显示隐藏列
        showRefresh : true, // 显示刷新按钮
        uniqueId : "userName", // 每一行的唯一标识
        sidePagination : "server", // 服务端处理分页
        columns : [ {
            field : 'state',
            checkbox : true,
            align : 'center',
            valign : 'middle'
        }, {
            title : '用户名',
            field : 'name', //
            align : 'center', // 
            valign : 'middle', //
            sortable : true
        }, {
            title : '密码',
            field : 'password',
            align : 'center',
            valign : 'middle',
            sortable : true
        }],
        responseHandler : function(res) {
            return {
                total : res.total,
                rows : res.records
            };
        },
        onCheck:function(){
            buttonControl('#empUserList','#edit','#delete');
        },
        onCheckAll:function(){
            buttonControl('#empUserList','#edit','#delete');
        },
        onUncheckAll:function(){
            buttonControl('#empUserList','#edit','#delete');
        },
        onUncheck:function(){
            buttonControl('#empUserList','#edit','#delete');
        }
    })
}
/** 替换数据为文字 */
function genderFormatter(value) {
    if (value == null || value == undefined) {
        return "-";
    } else if (value==1) {
        return "已删除";
    } else if(value==0){
        return "正常";
    }
}
/** 刷新页面 */
function refresh() {
    $('#empUserList').bootstrapTable('refresh');
}
/**查询条件与分页数据 */
function queryParams(pageReqeust) {
    pageReqeust.enabled = $("#enabled").val();
    pageReqeust.querys = $("#querys").val();
    pageReqeust.pageNo = this.offset;
    pageReqeust.pageSize = this.pageNumber;
    return pageReqeust;
}
/** 编辑数据 */
function editHr() {
    var selectRow = $("#empUserList").bootstrapTable('getSelections');
    if (selectRow.length != 1) {
        layer.alert('请选择并只能选择一条数据进行编辑！', {icon: 2});
        return false;
    } else {
        window.location = createUrl("admin/hrEmployee/view?username=" + selectRow[0].userName);
    }
}
/**
 * 删除数据
 */
function deleteHr() {
    var hrs = $("#empUserList").bootstrapTable('getSelections');
    if (hrs.length < 1) {
        layer.alert('请选择一条或多条数据进行删除！', {icon: 2});
    } else {
        layer.confirm('确定要删除所选数据？', {icon: 3, title:'提示'}, function(){
            var userNames = [];
            for (var i=0;i<hrs.length;i++){
                    userNames.push(hrs[i].userName);
            }
            $.ajax({
                url:'../../../admin/hrEmployee/delete',
                traditional: true,  //阻止深度序列化，向后台传送数组
                data:{userNames:userNames},
                contentType:'application/json',
                success:function(msg){
                    if(msg.success){
                        layer.alert(msg.msg,{icon:1});
                    }else{
                        layer.alert(msg.msg,{icon:2});
                    }
                    refresh();
                    $("#edit").attr({"disabled":"disabled"});
                    $("#delete").attr({"disabled":"disabled"});
                }
            })
          });
    }
}
