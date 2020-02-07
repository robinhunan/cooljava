<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>未分配用户列表</title>
    <meta name="keywords" content=""/>
    <meta name="description" content="CoolJava Version:2.0"/>
    <meta name="Author" content=""/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="${path }/res/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css"/>
</head>
<body>
<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    <table id="noAssignUserTables" lay-filter="noAssignUserTables"></table>
</div>

<!-- 加载js文件 -->
<script type="text/html" id="status">
    {{#  if(d.status === undefined){ }}
    未知
    {{#  } else { }}
    {{#  if(d.status === '0'){ }}
    可用
    {{#  } else { }}
    禁用
    {{#  } }}
    {{#  } }}
</script>
<script type="text/javascript" src="${path }/res/layui/layui.js"></script>
<script type="text/javascript" src="${path }/res/js/cool-js/assignUser.js"></script>
<script type="text/javascript">
    var path = "${path}";
    var roleId = "${roleId}";
</script>
<script type="text/javascript">

        /* 添加用户-选择用户-提交 */
        function submitHandler() {
            var checkStatus = layui.table.checkStatus('noAssignUserTables'),
                data = checkStatus.data,
                linkId = [];
            if (data.length > 0) {
                for (var i in data) {
                    linkId.push(data[i].id);
                }
                top.layer.confirm('确定对选中的用户进行授权？', {icon: 3, title: '提示信息'}, function (index) {
                    var ajaxReturnData;
                    layui.$.ajax({
                        url: path + '/user/assignUserAll.do',
                        type: 'post',
                        async: false,
                        data: {userIds: linkId.toString(), roleId: roleId},
                        success: function (data) {
                            ajaxReturnData = data;
                            //授权结果
                            if (ajaxReturnData.code == '0') {
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                                top.layer.msg('操作成功', {icon: 1});
                            } else {
                                top.layer.msg('操作失败', {icon: 5});
                            }
                        }
                    });
                })
            } else {
                top.layer.msg("请至少选择一条记录");
            }
        }
</script>
</body>
</html>