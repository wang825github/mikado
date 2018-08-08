<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.shinetech.dalian.mikado.entity.UserEntity" %>
<!DOCTYPE HTML>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    
    <link href="<%=request.getContextPath() %>/css/all.vendor.css" rel="stylesheet" >
    <link href="<%=request.getContextPath() %>/css/all.css" rel="stylesheet">

    <script src="<%=request.getContextPath() %>/js/resource/all.vendor.js"></script>
	<script src="<%=request.getContextPath() %>/js/resource/all.js"></script>
    <script src="<%=request.getContextPath() %>/js/resource/jquery.validate.min.js"></script>
	<%@include file="public.jsp" %>
	<script type="text/javascript" title="/js/public.js" src="<%=request.getContextPath()%>/js/public.js" ></script>
    
	<title>数据同步</title>
</head>
<body>
	<div class="header">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-4">
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
				<li><a href="<%=request.getContextPath()%>/productions/manage">产品管理<small>Production Management</small></a></li>
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
				<li><a href="<%=request.getContextPath()%>/customer/manage">客户管理<small>Customer Management</small></a></li>
				<li ><a href="<%=request.getContextPath()%>/log/manage">日志管理<small>Log Management</small></a></li>
				<li><a href="<%=request.getContextPath()%>/data/dictionary">数据字典<small>Data Dictionary</small></a></li>
				<li class="active"><a href="<%=request.getContextPath()%>/dataSynchronization/view">数据同步<small>Data Synchronization</small></a></li>
			</ul>

		</div>
		<div class="content">
			<div class="detail">
				<h4 class="title">
					<strong>数据同步</strong><small>Data Synchronization</small>
				</h4>

				<form id="DataSynchronizationForm" class="form-inline">
					<div class="form-group">
						<label>同步方式<small>Syn Mode</small></label>
						<select id="SynModeValue" class="form-control">
							<option value="0">加工批次</option>
							<option value="1">单元识别码</option>
							<!-- <option value="2">单元识别码所有产品</option> -->
						</select>
					</div>

					<div class="form-group">
						<label>编码<small>Code</small></label> 
						<input id="codeValue" type="text" Maxlength="200" name="content"class="form-control" placeholder="" required >
					</div>
					
					<div class="form-group">
						<button id="startSynbtn" type="button" class="btn btn-primary">
							开始同步 <small>start syn</small>
						</button>
					</div>
				</form>
		 
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="container-fluid">
			<p class="text-center">© 2005-2016 大连米可多国际种苗有限公司 版权所有</p>
		</div>
	</div>
	
		<!--提示信息 模态框（Modal） -->
	<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width:300px">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						提示
					</h4>
				</div>
				<div class="modal-body" id="msgContent">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</body>

<script type="text/javascript">
$('#startSynbtn').on('click',function(e){
	var SynModeValue = $('#SynModeValue').val();
	var codeValue = $('#codeValue').val();
	if(codeValue == null||codeValue ==""){
		$('#msgContent').text('编码不能为空！')
		$('#msgModal').modal('show');
		setTimeout(function(){$("#msgModal").modal("hide")},3000);
		return;
	}
	startSyn(SynModeValue,codeValue);	
})
function showMSG(msg){
	$('#msgContent').text(msg);
	if($('#msgModal').css('display') =='none'){
		$('#msgModal').modal('show');	
	}
}
function hideMSG(){
	$('#msgModal').modal('hide');
}
function startSyn(SynModeValue,codeValue){
	$.ajax({
		type : 'post',
		url : '../dataSynchronization/startSyn',
		data : {
			synModel:SynModeValue,
			code:codeValue
		},
		beforeSend:showMSG('开始数据同步'),
		dataType : 'json',
		success : function(data) {
		},
		complete:function(data){
			if(data.responseJSON == '0'){showMSG('数据同步成功！');}
			if(data.responseJSON == '1'){showMSG('当前产品还没有出库！');}
			if(data.responseJSON == '2'){showMSG('当前批次还没有生成！')}
			if(data.responseJSON == '3'){showMSG('当前产品还没有出库！')}
			setTimeout(function(){$("#msgModal").modal("hide")},3000);
		}
	});
}
</script>
</html>