$(function(){
    var option = {
        url: '../sys/log/list',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页  client  客户端分页
        showRefresh: true,  //显示刷新按钮
        search: true,
        //toolbar: '#toolbar',
        striped : true,     //设置为true会有隔行变色效果
        columns: [
            {
                field: 'id',
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {checkbox:true},
            {field:'username', title:'操作人'},
            { title: '操作', field: 'operation'},
            { title: '方法', field: 'method'},
            { title: '参数', field: 'params'},
            { title: '操作电脑ip', field: 'ip'},
            { title: '创建时间', field: 'createDate'}
        ]};
    $('#table').bootstrapTable(option);
});              