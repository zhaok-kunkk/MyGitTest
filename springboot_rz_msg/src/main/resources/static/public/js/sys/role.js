$(function(){
    var option = {
        url: '../sys/role/list',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        showRefresh: true,  //显示刷新按钮
        search: true,
        toolbar: '#toolbar',
        striped : true,     //设置为true会有隔行变色效果
        //idField: 'menuId',
        columns: [
            {
                field: 'roleId',
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {checkbox:true},
            { title: '角色ID', field: 'roleId'},
            { field:'roleName', title:'角色名称'},
            { title: '备注', field: 'remark'},
            { title: '创建者id', field: 'createUserId'},
            { title: '创建时间', field: 'createTime'},
            
        ]};
    $('#table').bootstrapTable(option);
});
var ztree;

var vm = new Vue({
	el:'#dtapp',
    data:{
        showList: true,
        title: null,
        menu:{}
    },
    methods:{
        del: function(){
            var rows = getSelectedRows();
            if(rows == null){
                return ;
            }
            var id = 'roleId';
            //提示确认框
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function(index, layero){
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function(i, row) {
                    ids[i] = row[id];
                });

                $.ajax({
                    type: "POST",
                    url: "role/del",
                    data: JSON.stringify(ids),
                    success : function(r) {
                        if(r.code === 0){
                            layer.alert('删除成功');
                            $('#table').bootstrapTable('refresh');
                        }else{
                            layer.alert(r.msg);
                        }
                    },
                    error : function() {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {parentName:null,parentId:0,type:1,orderNum:0};
            vm.getMenu();
        },
        update: function (event) {
            var id = 'roleId';
            var menuId = getSelectedRow()[id];
            if(menuId == null){
                return ;
            }

            $.get("../sys/role/info/"+menuId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;

                vm.getMenu();
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.menu.roleId == null ? "../sys/role/save" : "../sys/role/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.menu),
                success: function(r){
                    if(r.code === 0){
                        layer.alert('操作成功', function(index){
                            layer.close(index);
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.parentId = node[0].menuId;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        getMenu: function(menuId){

            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "menuId",
                        pIdKey: "parentId",
                        rootPId: -1
                    },
                    key: {
                        url:"nourl"
                    }
                }
            };

            //加载菜单树
            $.get("../sys/menu/select", function(r){
                //设置ztree的数据
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);

                //编辑（update）时，打开tree，自动高亮选择的条目
                var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
                //选中tree菜单中的对应节点
                ztree.selectNode(node);
                //编辑（update）时，根据当前的选中节点，为编辑表单的“上级菜单”回填值
                vm.menu.parentName = node.name;
            });
        }
    }
});