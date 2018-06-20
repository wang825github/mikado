<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML>

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
	<!-- 
	<script src="http://rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
	 -->
	
	<%@include file="public.jsp" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/packaging-manage.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/public.js"></script>
	<title>Packaging Manage</title>
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
				<li><a href="<%=request.getContextPath()%>/seed/manage">种子管理<small>Seed Management</small></a></li>
							
				<li class="active"><a href="<%=request.getContextPath()%>/packaging/manage">包装管理<small>Package
							Management</small></a></li>
							
				<li><a href="<%=request.getContextPath()%>/data/manage">订单管理<small>Order Management</small></a></li>
				<% if(user.getIsAdmin() == 1){ %>
				<li><a href="<%=request.getContextPath()%>/user/manage">用户管理<small>User
							Management</small></a></li>
				<% }%>
				<li><a href="<%=request.getContextPath()%>/customer/manage">客户管理<small>Customer
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
					<strong>包装管理</strong><small>Package Management</small>
				</h4>

				<form class="form-inline" id="searchPackaging" method="get" >
					<div class="form-group">
						<label>作物名 <small>Species Name</small></label> 
						<input type="text" name = "speciesNameCn" 
								class="form-control" placeholder="请输入作物中文名">
						<!-- 
						<select class="form-control" name="conmercial_name">
							<option value="0">--</option>
							<c:forEach items="${conmercialNameList }" var="commercialName">
								<option value="${commercialName}">${commercialName}</option>
							</c:forEach>
						</select>
						 -->
					</div>
					<div class="form-group">
						<label>包装规格 <small>Packing Unit</small></label> 
						<select class="selectpicker show-tick form-control" name="packingUnit.id"  required data-live-search="true">
							<option value="0">--</option>
							<c:forEach items="${packingUnitLists}" var="packingUnit"> 
									<c:if test="${packingUnit.sample == 1}">
									<option value="${packingUnit.id}">${packingUnit.specificationName} - ${packingUnit.weight} ${packingUnit.unit} - ${packingUnit.specifications} - ${packingUnit.specificationCode} - 样品</option>
									</c:if>
									<c:if test="${packingUnit.sample == 0}">
									<option value="${packingUnit.id}">${packingUnit.specificationName} - ${packingUnit.weight} ${packingUnit.unit} - ${packingUnit.specifications} - ${packingUnit.specificationCode}</option>
									</c:if>
							</c:forEach>
							<!-- 
							<c:forEach items="${packingUnitLists }" var="packingUnit">
								<option value="${packingUnit.id }">${packingUnit.specificationName}-${packingUnit.weight}${packingUnit.unit}-${packingUnit.specifications}</option>
							</c:forEach>
							 -->
						</select>
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-primary" id="packagingSearchBtn">
							搜索 <small>search</small>
						</button>
					</div>
				</form>
				<div class="search-result">
					<p class="search-result-title pull-left">查询结果</p>
				</div>
				<div class="table-responsive">
					<div class="pull-left btn-group">
						<button id="addPackaging" class="btn btn-default j-packaging-management-new" title="入库产品信息">
							<!-- 入库 <small>Add</small> -->
							<span class="glyphicon glyphicon-plus"></span>
						</button>
						<!-- 
						<button id="editPackaging" class="btn btn-default j-packaging-management-edit" title="编辑产品信息">
							<span class="glyphicon glyphicon-pencil"></span>
						</button>
						 -->
						<button id="deletePackaging" class="btn btn-default" title="删除产品">
							<!--  删除 <small>Delete</small>-->
							<span class="glyphicon glyphicon-minus"></span>
						</button>
						<button id="updatePackaging" class="btn btn-default j-packaging-management-edit" title="入库后更新扫码成功实际数量">
							<!-- 更新 <small>Update</small> -->
							<span class="glyphicon glyphicon-edit"></span>
						</button>
					</div>
					<table id="packagingTable" class="table table-striped table-bordered table-hover">
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
</body>
</html>