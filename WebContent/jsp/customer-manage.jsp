<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<%
	UserEntity userss = (UserEntity)request.getSession().getAttribute("user");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    
    <link href="<%=request.getContextPath() %>/css/all.vendor.css" rel="stylesheet" >
    <link href="<%=request.getContextPath() %>/css/all.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/resource/bootstrap-table.css" rel="stylesheet">
     
    <!-- 
	<script src="<%=request.getContextPath() %>/js/resource/jquery-2.1.4.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/bootstrap.js"></script>
     -->
    <script src="<%=request.getContextPath() %>/js/resource/all.vendor.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/all.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/jquery.validate.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/messages_zh.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/bootstrap-editable.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/bootstrap-table.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/bootstrap-table-zh-CN.js"></script>
		<script src="<%=request.getContextPath() %>/js/resource/tableExport.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/tableExport.js"></script>
	<!-- 
	<script src="http://rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
	 -->
    
	<%@include file="public.jsp" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/customer-manage.js${currentTime}"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/public.js${currentTime}"></script>
    
	<title>Customer Manage</title>
</head>

<body>
	<div class="header">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-4">
					<!-- 
					<h2 class="strong logo">LOGO</h2>
					 -->
					 <img class="strong logo" alt="" src="<%=request.getContextPath()%>/images/companylogo2.png">
				</div>
				<div class="col-xs-8">
					<div class="dropdown text-right">
						欢迎您
						<button type="button" class="btn dropdown-toggle"
							id="dropdownMenu1" data-toggle="dropdown">
							<%=user.getAccount()%>
							<span class="caret"></span>
						</button>
						<ul id="userResetPwd" class="dropdown-menu pull-right" role="menu"
							aria-labelledby="dropdownMenu1">
							<li role="presentation">
								<a id="resetPwd" role="menuitem" tabindex="-1" href="#">
									<span style="margin-right:10px;" class="glyphicon glyphicon-lock"></span>重置密码
								</a>
							</li>
							<li role="presentation">
								<a id="logout" role="menuitem" tabindex="-1"  href="<%=request.getContextPath()%>/logout">
									<span style="margin-right:10px;" class="glyphicon glyphicon-off"></span>退出登录
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid page">
		<div class="sidebar">
			<ul class="nav nav-pills nav-stacked">
				<li><a href="<%=request.getContextPath()%>/productions/manage">产品管理<small>Production
							Management</small></a></li>
							<!-- 
				<li><a href="<%=request.getContextPath()%>/storage/manage">库存管理<small>Storage Management</small></a></li>
				 -->			
				<li><a href="<%=request.getContextPath()%>/seed/manage">种子管理<small>Seed Management</small></a></li>
				<li><a href="<%=request.getContextPath()%>/packaging/manage">包装管理<small>Package
							Management</small></a></li>
				<li><a href="<%=request.getContextPath()%>/data/manage">订单管理<small>Order Management</small></a></li>
				<% if(user.getIsAdmin() == 1){ %>
				<li><a href="<%=request.getContextPath()%>/user/manage">用户管理<small>User
							Management</small></a></li>
				<% }%>
				<li class="active"><a href="<%=request.getContextPath()%>/customer/manage">客户管理<small>Customer
							Management</small></a></li>
				<li><a href="<%=request.getContextPath()%>/log/manage">日志管理<small>Log
							Management</small></a></li>
				<li><a href="<%=request.getContextPath()%>/data/dictionary">数据字典<small>Data
							Dictionary</small></a></li>
				<li><a href="<%=request.getContextPath()%>/dataSynchronization/view">数据同步<small>Data Synchronization</small></a></li>
			</ul>

		</div>
		<div class="content">
			<div class="detail">
				<h4 class="title">
					<strong>客户管理</strong><small>Customer Management</small>
				</h4>

				<form id="searchCustomer" class="form-inline">
					<div class="form-group">
						<label>公司简称<small>Company Simple Name</small></label> <input type="text" name="simple_name" 
							class="form-control" placeholder="">
					</div>

					<div class="form-group">
						<label>城市<small>City</small></label> 
						<select class="selectpicker show-tick form-control" name="city.id" required data-live-search="true">
							<option value="0"> -- </option>
							<c:forEach items="${cityList}" var="city">
								<option value="${city.id}">${city.nameCn} -- ${city.nameEn }</option>
							</c:forEach>
						 </select>
					</div>

					<div class="form-group">
						<button id="customerSearchBtn" type="submit" class="btn btn-primary">
							搜索 <small>search</small>
						</button>
					</div>
				</form>
				<div class="search-result">
					<p class="search-result-title pull-left">查询结果</p>
				</div>
				<div class="table-responsive">
					<div class="pull-left btn-group">
						<button id="addCustomer" class="btn btn-default" data-toggle="modal" title="新建">
							<!-- 新建 <small>Add</small> -->
							<span class="glyphicon glyphicon-plus"></span>
						</button>
						<button id="editCustomer" class="btn btn-default" data-toggle="modal" title="编辑">
							<!-- 编辑 <small>Update</small> -->
							<span class="glyphicon glyphicon-pencil"></span>
						</button>
						<button id="deleteCustomer" class="btn btn-default" title="删除">
							<!--  删除 <small>Delete</small>-->
							<span class="glyphicon glyphicon-minus"></span>
						</button>
					</div>
					<table id="customerTable" class="table table-striped table-bordered table-hover">
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="container-fluid">
			<p class="text-center">© 2005-2016 大连米可多国际种苗有限公司 版权所有</p>
		</div>
	</div>
	<!-- 
	<script src="<%=request.getContextPath() %>/js/all.vendor.js"></script>
	<script src="<%=request.getContextPath() %>/js/all.js"></script>
	 -->
</body>

</html>