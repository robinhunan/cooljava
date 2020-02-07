<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>分配用户列表</title>
    <meta name="keywords" content=""/>
    <meta name="description" content="CoolJava Version:2.0"/>
    <meta name="Author" content=""/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="Shortcut Icon" href="/favicon.ico"/>
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="${path }/res/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css"/>
</head>
<body>
<div class="">
    <div class="layui-btn-group one-group" id="one_group">
        <button class="layui-btn layui-btn-sm" data-type="selectUser"><i class="layui-icon">&#xe654;</i>添加用户</button>
        <button class="layui-btn layui-btn-danger layui-btn-sm" data-type="cancelAssignUserAll"><i class="layui-icon">&#x1006;</i>批量取消授权
        </button>
        <button class="layui-btn layui-btn-sm" data-type="closeItem"><i class="fa fa-reply-all"></i>关闭</button>
    </div>
</div>

<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
    <table id="assignUserTables" lay-filter="assignUserTables"></table>
</div>

<!-- 加载js文件 -->
<script type="text/html" id="assignUser">
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="cancelAssignUser"> <i class="layui-icon">&#x1006;</i>取消授权</a>
</script>
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
</body>
</html>