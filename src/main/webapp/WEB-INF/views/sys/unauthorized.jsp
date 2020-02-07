<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>CoolJava后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css"/>

    <style>
        .page-error {
            display: -webkit-box;
            display: flex;
            -webkit-box-align: center;
            align-items: center;
            -webkit-box-pack: center;
            justify-content: center;
            -webkit-box-orient: vertical;
            -webkit-box-direction: normal;
            flex-direction: column;
            min-height: calc(100vh - 110px);
            margin-bottom: 0;
        }
    </style>
</head>
<body>
<div class="page-error" style="color: #009688">
    <i class="fa fa-exclamation-circle" style="font-size: 100px"></i>
    <div style="font-size: 24px; margin-top: 14px">您的权限不足，无法访问本页面！</div>
</div>
</body>
</html>