layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        form = layui.form,
        table = layui.table;


    var assignUserTables = table.render({
        elem: '#assignUserTables',
        id: 'assignUserTables',
        height: "full",
        width: 1150,
        cols: [
            [{
                checkbox: true,
                width: 40,
                fixed: true
            }, {
                field: 'loginName',
                width: 150,
                title: '用户名',
                align: 'center'
            }, {
                field: 'name',
                width: 150,
                title: '真实姓名',
                align: 'center'
            }, {
                field: 'email',
                width: 225,
                title: '邮箱',
                align: 'center'
            }, {
                field: 'mobile',
                width: 180,
                title: '手机号',
                align: 'center'
            }, {
                field: 'orgName',
                width: 180,
                title: '机构',
                align: 'center'
            }, {
                field: 'status',
                width: 100,
                title: '状态',
                align: 'center',
                templet: '#status'
            }, {
                title: '操作',
                width: 110,
                align: 'center',
                toolbar: '#assignUser',
                fixed: "right"
            }]

        ],
        url: path + '/user/assignUserData.do',
        where: {id: roleId},
        page: true,
        even: true
    });
    //监听授权用户列表工具条
    table.on('tool(assignUserTables)', function (obj) {
        var data = obj.data;
        if (obj.event === 'cancelAssignUser') {
            layer.confirm('真的取消该用户的角色授权吗?', function (index) {
                var ajaxReturnData;
                $.ajax({
                    url: path + '/user/cancelAssignUser.do',
                    type: 'post',
                    async: false,
                    data: {userId: data.id, roleId: roleId},
                    success: function (data) {
                        ajaxReturnData = data;
                    }
                });
                //取消授权结果
                if (ajaxReturnData.code == '0') {
                    table.reload('assignUserTables');
                    layer.msg('操作成功', {icon: 1});
                } else {
                    layer.msg('操作失败', {icon: 5});
                }

            })
        }
    });



    var noAssignUserTables = table.render({
        elem: '#noAssignUserTables',
        id: 'noAssignUserTables',
        height: "full",
        width: 1050,
        cols: [
            [{
                checkbox: true,
                width: 40,
                fixed: true
            }, {
                field: 'loginName',
                width: 150,
                title: '用户名',
                align: 'center'
            }, {
                field: 'name',
                width: 150,
                title: '真实姓名',
                align: 'center'
            }, {
                field: 'email',
                width: 225,
                title: '邮箱',
                align: 'center'
            }, {
                field: 'mobile',
                width: 180,
                title: '手机号',
                align: 'center'
            }, {
                field: 'orgName',
                width: 180,
                title: '机构',
                align: 'center'
            }, {
                field: 'status',
                width: 100,
                title: '状态',
                align: 'center',
                templet: '#status'
            }]

        ],
        url: path + '/user/noAssignUserData.do',
        where: {id: roleId},
        page: true,
        even: true
    });

    $('#one_group .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var active = {
        selectUser: function () {

            var index = layer.open({
                title: "未授权用户列表",
                type: 2,
                skin: '',
                area: ['1050px', '430px'],
                content: path + "/user/NoAssignUser/" + roleId,
                btn: ['确定', '关闭'],
                // 弹层外区域关闭
                shadeClose: true,
                end: function () {
                    table.reload('assignUserTables');
                },
                yes: function(index, layero) {
                    var iframeWin = layero.find('iframe')[0];
                    iframeWin.contentWindow.submitHandler(index, layero);
                },
                cancel: function(index) {
                    return true;
                }
            });
        },
        cancelAssignUserAll: function () {
            var checkStatus = table.checkStatus('assignUserTables'),
                data = checkStatus.data,
                linkId = [];
            if (data.length > 0) {
                for (var i in data) {
                    linkId.push(data[i].id);
                }
                layer.confirm('确定对选中的用户取消授权？', {icon: 3, title: '提示信息'}, function (index) {
                    var ajaxReturnData;
                    $.ajax({
                        url: path + '/user/cancelAssignUserAll.do',
                        type: 'post',
                        async: false,
                        data: {userIds: linkId.toString(), roleId: roleId},
                        success: function (data) {
                            ajaxReturnData = data;
                            //删除结果
                            if (ajaxReturnData.code == '0') {
                                table.reload('assignUserTables');
                                layer.msg('批量取消授权成功', {icon: 1});
                            } else {
                                layer.msg('批量取消授权失败', {icon: 5});
                            }
                        }
                    });
                })
            } else {
                layer.msg("请选择需要取消授权的用户");
            }
        },
        closeItem: function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }
    };
});

