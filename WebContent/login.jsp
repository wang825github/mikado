<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" isELIgnored="false" %>
<%@ page import="com.shinetech.dalian.mikado.entity.UserEntity" %>

<%   
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;   
%> 
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登录</title>

    <link href="<%=request.getContextPath() %>/css/all.vendor.css" rel="stylesheet" >
    <link href="<%=request.getContextPath() %>/css/all.css" rel="stylesheet">
    
    <script src="<%=request.getContextPath() %>/js/resource/all.vendor.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/all.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/jquery.validate.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/messages_zh.js"></script>
	<%@include file="jsp/public.jsp" %>
	<script src="<%=request.getContextPath() %>/js/login.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/public.js"></script>
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<style>
	body {
		background: #f4f4f4;
	}

	.footer {
		position: fixed;
		bottom: 0;
	}
	</style>
</head>

<body>
	<div class="header">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-4">
					<img class="strong logo" alt="" src="<%=request.getContextPath()%>/images/companylogo2.png" style="margin-top:15px">
				</div>
				<div class="col-xs-8">
					<!-- 
					<h2 class="strong logo">LOGO</h2>
					<p class="text-right">欢迎您 大连米可多国际种苗有限公司</p>
					 -->
				</div>
			</div>
		</div>
	</div>
	
	<div class="login-page container">
		<!-- 
		<h1 class="logo strong text-center">LOGO</h1>
		 -->
		<h6 class="logo strong text-center">大连米可多产品管理系统</h6>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<form id="loginForm" action="<%=request.getContextPath()%>/login" method="post">
					<div class="form-group">
						<span style="color:red;">${loginError}</span>
					</div>
					<div class="form-group">
						<label class="sr-only">用户名</label> 
						<input type="text" class="form-control input-lg" name="account" placeholder="请输入用户账号">
					</div>
					<div class="form-group">
						<label class="sr-only">密码</label> 
						<input type="password" class="form-control input-lg" name="password" placeholder="请输入密码">
					</div>
					<button type="submit" class="btn btn-info btn-lg btn-block">登录</button>
				</form>
				<br/>
			</div>
		</div>
	</div>

	<div class="footer">
		<div class="container-fluid">
			<p class="text-center">© 2005-2016 大连米可多国际种苗有限公司 版权所有</p>
		</div>
	</div>

</body>
</html>