
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.shinetech.dalian.mikado.entity.SeedEntity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.shinetech.dalian.mikado.entity.UserEntity"%>
<%
	UserEntity user = (UserEntity)request.getSession().getAttribute("userEntity");
	String hello = "hello";
	Long currentTime = System.currentTimeMillis();
%>
 <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath() %>/images/httpLogo.png" />
 <link href="<%=request.getContextPath() %>/css/bootstrap-switch.min.css" rel="stylesheet">
<script src="<%=request.getContextPath() %>/js/resource/bootstrap-switch.min.js"></script>
<link href="<%=request.getContextPath() %>/css/resource/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath() %>/js/resource/bootstrap-select.js"></script>

<!-- 重置密码模态框 -->
<div class="modal fade" id="resetModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false"  
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<form id="resetForm"
				action="<%=request.getContextPath()%>/resetPassword">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h5 class="modal-title" id="myModalLabel">重置密码</h5>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>原密码<small>Original password</small></label> <input
							name="oldPwd" type="password" class="form-control" placeholder="">
					</div>
					<div class="form-group">
						<label>新密码<small>New password</small></label> <input
							class="form-control" name="newPwd" type="password" placeholder="">
					</div>
					<div class="form-group">
						<label>密码确认<small>Confirm password</small></label> <input
							name="confirmPwd" type="password" class="form-control"
							placeholder="">
					</div>
				</div>
				<div class="modal-footer">
					<div class="form-group">
						<button id="resetSubmit" type="submit" class="btn btn-primary">提交</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 重置密码 模态框 ends -->

<!-- user manage modal starts -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false" 
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addForm"
				action="<%=request.getContextPath() %>/user/add" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>编号 <small>job number</small></label>
						<input type="text" name="jobNumber" class="form-control" value="" placeholder="请输入用户编号">
					</div>
					<div class="form-group">
						<label>账号 <small>account</small><span class="required muted" style="color:red">*</span></label>
						<input type="text" name="account" class="form-control" placeholder="请输入用户账号">
					</div>	
					<div class="form-group">
						<label>姓名 <small>name</small><span class="required muted" style="color:red">*</span></label>
						<input type="text" name="name" class="form-control" placeholder="请输入用户姓名">
					</div>
					<div class="form-group">
						<label>职位 <small>position</small></label>
						<input type="text" name="position" class="form-control" placeholder="请输入用户职位">
					</div>
					<div class="form-group">
						<label>电话 <small>telephone</small><span class="required muted" style="color:red">*</span></label>
						<input type="text" name="telephone" class="form-control" placeholder="请输入用户联系方式">
					</div>
					<div class="form-group">
						<label>是否管理员<small>Is Admin</small><span class="required muted" style="color:red">*</span></label>
						<select class="form-control"  name = "isAdmin">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
					<div class="form-group">
						<label>备注 <small>note</small></label>
						<input type="text" name="note" class="form-control" placeholder="请输入备注">
					</div>
				</div>
				<div class="modal-footer">
					<button id="addBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- user manage modal ends -->

<!-- customer manage modal starts -->
<div class="modal fade" id="addCustomerModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
	role="dialog" aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addCustomerForm"
				action="<%=request.getContextPath() %>/customer/add"
				role="form" method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="row" style="margin-bottom:10px">
						<div class="col-lg-6">
							<label>公司全称 <small> Company Name</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name="company" class="form-control"  placeholder="请输入公司名">
						</div>
						<div class="col-lg-6">
							<label>公司简称 <small> simple name</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name="simpleName" class="form-control"  placeholder="请输入公司简称">
						</div>
					</div>
					<div  class="row" style="margin-bottom:10px">
						<div class="col-lg-6">
							<label>联系人<small>Contact</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name="contact" class="form-control"  placeholder="请输入联系人名称">
						</div>
						<div class="col-lg-6">
							<label>联系电话<small>Telephone</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name="telephone" class="form-control"  placeholder="请输入联系电话">
						</div>
					</div>
					<div  class="row" style="margin-bottom:10px">
						<div class="col-lg-6">
							<label>公司地址<small>address</small></label>
							<input type="text" name="address" class="form-control"  placeholder="请输入公司地址">
						</div>
						<div class="col-lg-6">
							<label>邮箱<small>email</small></label>
							<input type="text" name="email" class="form-control"  placeholder="请输入邮箱">
						</div>
					</div>
					<div  class="row" style="margin-bottom:10px">
						<div class="col-lg-6">
							<label>最初月份  <small>First Month</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name="firstMonth" placeholder="请选择月份" class="form-control datetimepicker_yyyyMM2" >
						</div>
						<div class="col-lg-6">
							<label>月份区间<small>Month Interval</small></label>
							<input type="text" name="ago" class="form-control"  placeholder="请输入月份区间" value="以前">
						</div>
					</div>
					<div class="row" style="margin-bottom:10px">
						<div class="col-lg-6">
							<label>地域 <small>Area</small><span class="required muted"
								style="color: red">*</span></label> <select class="form-control"
								id="areaId" name="area.id" required="required">
								<option value="0">--</option>
								<c:forEach items="${areaList}" var="area">
									<option value="${area.id}">${area.nameCn}--
										${area.nameEn }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-lg-6">
							<label>省份<small>Province</small><span
								class="required muted" style="color: red">*</span></label>
							<select class="selectpicker show-tick form-control" id="provinceId" name="province.id"  required data-live-search="true">
								<option value="0">--</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<label>城市 <small>City</small><span class="required muted"
								style="color: red">*</span></label> <select class="selectpicker show-tick form-control"
								id="cityIdID" name="city.id" required  data-live-search="true">
								<option value="0">--</option>
							</select>
						</div>

						<div class="col-lg-6">
							<label>销售人员 <small>Sale Manager</small><span
								class="required muted" style="color: red">*</span></label> <select
								class="form-control" name="saleManager.id" required>
								<option value="0">--</option>
								<c:forEach items="${saleManagerList}" var="saleManager">
									<option value="${saleManager.id}">${saleManager.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="addCustomerBtn" type="submit" class="btn btn-primary right">提交</button>
					<button id="cancelCustomerBtn" type="button" class="btn btn-primary right" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- customer manage modal ends -->


<!-- productions manage modal start -->
	<!-- add productions model start -->
	<div class="modal fade" id="addProductionsModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="addProductionsForm"
					action="<%=request.getContextPath()%>/productions/add" 
					role="form" method="post" enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">入库</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						
						<div class="form-group">
							<label>样品 <small>Sample</small></label>
							<input type="checkbox" name = "isSample" id="isSample" 
								class="form-control" >
						</div>
						
						<div class="row" style="margin-bottom:10px" id="storageFileDiv">
						 	<div class="col-lg-12">
						 		<label>入库文件 <small>Production File</small><span class="required muted" style="color:red">*</span></label> 
								<input type="file" name = "storageFile" id="storageFile" class="form-control required" accept="text/plain">
						 	</div>
						 </div>
						
						<div class="form-group">
							<label>产品批号 <small>Production Lot</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" id="productionLotNumber" name = "productionLotNumber" readonly="readonly"  class="form-control" placeholder="">
						</div>
						
						<div class="form-group" id="seedSelectBody">
							<label>种子信息  <small>Seed Information</small><span class="required muted" style="color:red">*</span></label> 
							<div class="form-group">
							<div class="input-group ">
								<select
									class="selectpicker show-tick form-control" name="seed.id" id="seedDetailSelect" required data-live-search="true">
									<option value="0">--</option>
									<c:forEach items="${seedLists}" var="seed">
										<option class="${seed.species.id}" value="${seed.id}">${seed.lotNumber} - ${seed.species.speciesNameCn } - ${seed.importName} - ${seed.weight}KG</option>
									</c:forEach>
								 </select>
								 
								 <span class="input-group-btn">
				                  		    <button  type="button" class="btn btn-primary " id="addSeedSelectBtn"> 
				                          <span class="glyphicon glyphicon-plus"></span></button >
				                </span>
							 </div>
							 </div>
						</div>
						
						<div class="form-group" id="packageSelectBody">
							<label>包装物规格  <small>Package Information</small><span class="required muted" style="color:red">*</span></label> 
							<div class="form-group">
							<div class="input-group ">
								<select class="selectpicker show-tick form-control col-sm-10" name="packages.id" id="packageDetailSelect" required data-live-search="true">
									<option value="0">--</option>
									<c:forEach items="${packagesList}" var="packages">
										<option value="${packages.id}">${packages.lotNumber}-${packages.packingUnit.specificationCode }-${packages.packingUnit.specificationName }-${packages.packingUnit.weight }${packages.packingUnit.unit }-${packages.packingUnit.specifications }</option>
									</c:forEach>
								 </select>
								 <!-- 
								 <div class="input-group-btn" style="width: 15%">
			                  		    <input type="text" name = "packagingAmount" class="form-control"  placeholder="包装数量"/>
			               		</div>
								  -->
								 <span class="input-group-btn">
				                  		    <button  class="btn btn-primary" type="button" id="addPackageSelectBtn"> 
				                          <span class="glyphicon glyphicon-plus"></span></button >
				                </span>
			                </div>
			                </div>
						</div>
						
						<div class="form-group">
							<label>商品名称 <small>Commercial Name</small><span class="required muted" style="color: red">*</span></label> 
							<select class="selectpicker show-tick form-control" name="variety.id" id = "commercialNameSelect" required data-live-search="true">
								<option value="0">--</option>
								<c:forEach items="${varietyList}" var="variety">
									<option value="${variety.id}">${variety.commercialName}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="form-group">
							<label>入库数量 <small>Quantity</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name = "quantity" 
								class="form-control" placeholder="请输入入库数量">
						</div>
						<div class="form-group">
							<label>入库时间  <small>Storage Day</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name="storageDay" class="form-control form_datetime">
						</div>
						<div class="form-group">
							<label>包装开始时间  <small>Packing Start Day</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name="startDay" class="form-control form_datetime" > <!-- id="datetimepicker" -->
						</div>
						<div class="form-group">
							<label>包装完成时间  <small>Packing End Day</small></label> 
							<input type="text" name="endDay" class="form-control form_datetime">
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="addProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary" id="cancelAddProductionsBtn" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- add productions modal ends -->
	
	<!-- edit productions modal starts -->
	
	<div class="modal fade" id="updateProductionsModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="updateProductionsForm"
					action="<%=request.getContextPath()%>/productions/editProductions" 
					role="form" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">更新</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						<div class="form-group">
							<label>品种信息  <small>Variety Information</small></label> 
							<!-- 
							<input type="text" name="seed.id" class="form-control" readonly="readonly">
							 -->
							<select class="selectpicker show-tick form-control" name="variety.id"  required data-live-search="true">
								<c:forEach items="${varietyList}" var="variety">
									<option value="${variety.id}">${variety.varietyCode} - ${variety.varietyName } - ${variety.species.speciesNameCn }</option>
								</c:forEach>
							 </select>
						</div>
						
						<div class="form-group">
							<label>包装物规格  <small>Packing Unit Specifications</small></label> 
							<!-- 
							<input type="text" name="packingUnit.id" class="form-control" readonly="readonly">
							 -->
							<select
								class="selectpicker show-tick form-control" name="packingUnit.id"  required data-live-search="true">
								<c:forEach items="${packingUnitLists}" var="packingUnit">
									<option value="${packingUnit.id}">${packingUnit.specificationName}</option>
								</c:forEach>
							 </select>
						</div>
						<div class="form-group">
							<label>计划数量 <small>Plan Amount</small></label> 
							<input type="text" name = "planAmount" 
								class="form-control" placeholder="请输入计划数量" readonly="readonly">
						</div>
						<div class="form-group">
							<label>实际数量 <small>Current Amount</small></label>
							<input type="text" name = "currentAmount" 
								class="form-control" placeholder="请输入实际数量">
						</div>
						<div class="form-group">
							<label>入库时间  <small>Storage Day</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name="storageDay" class="form-control form_datetime" id="datetimepicker" readonly="readonly">
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="editProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary" id="cancelEditProductionsBtn" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- edit productions modal ends -->
	
	<!-- delivery productions modal starts -->
	<div class="modal fade" id="deliveryProductionsModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="deliveryProductionsForm"
					action="../productions/delivery" 
					role="form" method="post" enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">出库</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						 <div class="row" style="margin-bottom:10px" id = "orderFileForm">
						 	<div class="col-lg-12">
						 		<label>订单文件 <small>Orders</small><span class="required muted" style="color:red">*</span></label> 
								<input type="file" name = "orderFile" id="orderFile" class="form-control required" accept="text/plain">
						 	</div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
						 	<div class="col-lg-6">
						 		<label>出库批号  <small>Lot Number</small><span class="required muted" style="color:red">*</span></label>
								<input type="text" name = "lotNumber" class="form-control" placeholder="" readonly="readonly">
						 	</div>
						 	<div class="col-lg-6">
						 		<label>出库数量  <small>Delivery Amount</small><span class="required muted" style="color:red">*</span></label>
								<input type="text" name = "deliveryAmount" class="form-control" placeholder="请输入出库数量">
						 	</div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
						 	<div class="col-lg-6">
						 		<label>出库时间  <small>Out of Storage Day</small><span class="required muted" style="color:red">*</span></label> 
								<input type="text" name="outStorageDay" class="form-control form_datetime" id="datetimepicker3">
						 	</div>
						 	<div class="col-lg-6">
						 		<label>发货时间 <small>Delivery Day</small><span class="required muted" style="color:red">*</span></label> 
								<input type="text" name="deliveryTime" class="form-control form_datetime" id="datetimepicker">
						 	</div>
						</div>
						
						<div class="row" style="margin-bottom:10px" >
							<div class="col-lg-6">
						 		<label>物流单号  <small>Tracking Number</small></label>
								<input type="text" name = "oddNumbers" class="form-control" placeholder="">
						 	</div>
						 	<div class="col-lg-6">
								<label>物流公司 <small>Logistics Company</small><span class="required muted" style="color:red">*</span></label> <select
									class="form-control" name="logisticCompany.id">
									<c:forEach items="${ligisticsCompanyLists}" var="logistics">
										<option value="${logistics.id}">${logistics.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
								<label>客户简称  <small>Customer Name</small><span class="required muted" style="color:red">*</span></label> 
								<select class="form-control selectpicker show-tick form-control" id="deliverCustomerId" name = "customer.id" required data-live-search="true">
									<option value="0"> -- </option>
									<c:forEach items="${customerLists}" var="customer">
										<option value="${customer.id}">${customer.simpleName}</option>
									</c:forEach>
								</select>
							</div>
						 	<div class="col-lg-6">
								<label>区域经理  <small>Sales Manager</small><span class="required muted" style="color:red">*</span></label> 
								<input type="text" name = "saleManager" class="form-control" readonly="readonly">
							</div>
							
						</div>
						
						<!-- 
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
								<label>进口商 <small>Impoter</small><span class="required muted" style="color:red">*</span></label> 
								<select class="form-control" name="importer.id">
									<c:forEach items="${importerLists }" var="importer">
										<option value="${importer.id}">${importer.simpleName} - ${importer.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-lg-6">
								<label>供应商 <small>Supplier</small><span class="required muted" style="color:red">*</span></label> 
								<select class="form-control" name="supplier.id">
									<c:forEach items="${supplierLists }" var="supplier">
										<option value="${supplier.id}">${supplier.simpleName} - ${supplier.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						 -->
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="deliveryProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary" id="cancelDeliveryBtn" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- delivery productions modal ends -->
<!-- productions manage modal ends -->

<!-- packing manage modal start -->
<!-- add packaging model start -->
	<div class="modal fade" id="addPackagingModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="addPackagingForm"
					action="<%=request.getContextPath()%>/packaging/add" 
					role="form" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">入库</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						
						<div class="form-group">
							<label>包装批号 <small>Package Lot</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" id="packageLotNumber" name = "lotNumber" readonly="readonly"  class="form-control" placeholder="">
						</div>
						
						<div class="form-group">
							<label>作物信息  <small>Species Information</small><span class="required muted" style="color:red">*</span></label> 
							<select id="packageSpeciesSelectID" 
								class="selectpicker show-tick form-control" name="species.id"  required data-live-search="true">
								<option value="0">--</option>
								<c:forEach items="${speciesLists}" var="species">
									<option value="${species.id}">${species.speciesNameCn }</option>
								</c:forEach>
							 </select>
						</div>
						
						<div class="form-group">
							<label>包装物规格  <small>Packing Unit Specifications</small><span class="required muted" style="color:red">*</span></label> 
							<select id="packageUnitSelectID"
								class="selectpicker show-tick form-control" name="packingUnit.id"  required data-live-search="true">
								<option value="0">--</option>
								<c:forEach items="${packingUnitLists}" var="packingUnit"> 
									<c:if test="${packingUnit.sample == 1}">
									<option value="${packingUnit.id}">${packingUnit.specificationCode} - ${packingUnit.specificationName} - ${packingUnit.weight} ${packingUnit.unit} - ${packingUnit.specifications} - 样品</option>
									</c:if>
									<c:if test="${packingUnit.sample == 0}">
									<option value="${packingUnit.id}">${packingUnit.specificationCode} - ${packingUnit.specificationName} - ${packingUnit.weight} ${packingUnit.unit} - ${packingUnit.specifications}</option>
									</c:if>
								</c:forEach>
							 </select>
						</div>
						<div class="form-group">
							<label>计划数量 <small>Plan Amount</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name = "planAmount" 
								class="form-control" placeholder="请输入计划数量">
						</div>
						<div class="form-group">
							<label>实际数量 <small>Current Amount</small></label>
							<input type="text" name = "currentAmount" 
								class="form-control" placeholder="请输入实际数量">
						</div>
						
						<div class="form-group">
							<label>入库时间  <small>Storage Day</small></label> 
							<input type="text" name="storageDay" class="form-control form_datetime custom" id="datetimepicker" >
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary submit" id="addProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary cancelSubmit" id="cancelAddProductionsBtn" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- add packaging modal ends -->
	
	<!-- update packaging modal starts -->
	<div class="modal fade" id="updatePackagingModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="updatePackagingForm"
					action="<%=request.getContextPath()%>/packaging/update" 
					role="form" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">入库</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						
						<div class="form-group">
							<label>包装批号 <small>Package Lot</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name = "lotNumber" readonly="readonly"
								class="form-control" placeholder="">
						</div>
						
						<div class="form-group">
							<label>作物信息  <small>Species Information</small></label> 
							<select
								class="selectpicker show-tick form-control" name="species.id"  required data-live-search="true">
								<option value="0">--</option>
								<c:forEach items="${speciesLists}" var="species">
									<option value="${species.id}">${species.speciesNameCn }</option>
								</c:forEach>
							 </select>
						</div>
						
						<div class="form-group">
							<label>包装物规格  <small>Packing Unit Specifications</small></label> 
							<select
								class="selectpicker show-tick form-control" name="packingUnit.id" required data-live-search="true">
								<option value="0">--</option>
								<c:forEach items="${packingUnitLists}" var="packingUnit">
									<option value="${packingUnit.id}">${packingUnit.specificationName} - ${packingUnit.weight} ${packingUnit.unit} - ${packingUnit.specifications}</option>
								</c:forEach>
							 </select>
						</div>
						<div class="form-group">
							<label>计划数量 <small>Plan Amount</small></label> 
							<input type="text" name = "planAmount" 
								class="form-control" placeholder="请输入计划数量">
						</div>
						<div class="form-group">
							<label>实际数量 <small>Current Amount</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name = "currentAmount" 
								class="form-control" placeholder="请输入实际数量">
						</div>
						<div class="form-group">
							<label>入库时间  <small>Storage Day</small></label> 
							<input type="text" name="storageDay" class="form-control form_datetime custom" id="datetimepicker" >
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary submit" id="addProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary cancelSumit" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- update packaging modal ends -->

<!-- packing manage modal ends -->

<!-- data manage modal starts -->
<!-- update data manage status starts -->
<div class="modal fade" id="dataManageModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="dataManageForm" action="../data/editStatus" role="form" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">更新订单状态</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						<div class="form-group">
							<label>物流单号  <small>Tracking Number</small><span class="required muted" style="color:red">*</span></label>
							<input type="text" name = "oddNumbers" 
								class="form-control" placeholder="请输入物流单号">
						</div>
						<div class="form-group">
							<label>状态  <small>Status</small><span class="required muted" style="color:red">*</span></label> 
							<select class="form-control" name="status">
								<option value="0">发货</option>
								<option value="1">已签收</option>
							 </select>
						</div>
						<div class="form-group">
							<label>签收时间  <small>Receiving Time</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name="receivingTime" class="form-control form_datetime" id="datetimepicker">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="dataManageBtn">提交</button>
						<button type="button" class="btn btn-primary" id="cancelDataManageBtn" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- update data manage status ends -->
	
	<!-- edit data manage status starts -->
	<div class="modal fade" id="editDataManageModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="editDataManageForm"
					action="../data/editDataManage" 
					role="form" method="post" enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel">编辑订单</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						 <div class="row" style="margin-bottom:10px">
						 	<div class="col-lg-12">
						 		<label>批号 <small>Lot Numbers</small><span class="required muted" style="color:red">*</span></label> 
								<input type="text" name = "lotNumbers" class="form-control required" readonly="readonly">
						 	</div>
						 </div>
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-12">
								<label>客户信息  <small>Customer Information</small><span class="required muted" style="color:red">*</span></label> 
								<select class="form-control data-manage-customer selectpicker show-tick " name="customer.id" required data-live-search="true">
									<c:forEach items="${customerLists}" var="customer">
										<option value="${customer.id}">${customer.simpleName}</option>
									</c:forEach>
								 </select>
							</div>
						</div>
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-12">
								<label>销售信息  <small>SaleManager Information</small><span class="required muted" style="color:red">*</span></label> 
								<input type="text" name = "saleManager" class="form-control required" readonly="readonly">
							</div>
						</div>
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-12">
								<label>物流公司 <small>Logistics Company</small><span class="required muted" style="color:red">*</span></label> <select
									class="form-control" name="logisticCompany.id">
									<c:forEach items="${ligisticsCompanyLists}" var="logistics">
										<option value="${logistics.id}">${logistics.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="editDataManageBtn">提交</button>
						<button type="button" class="btn btn-primary" id="cancelEditDataManage" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- edit data manage status ends -->
<!-- data manage modal ends -->
	
<!-- seed manage modal starts -->
<!-- add/edit seed modal starts -->
<div class="modal fade" id="addSeedModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="addSeedForm" action="../seed/add" role="form" method="post" enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel"></h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id">
						
						<div class="row" style="margin-bottom:10px">
						 	 <div class="col-lg-12">
						 		<label>种子批号  <small>Seed Lot</small><span class="required muted" style="color:red">*</span></label>
								<input type="text" id="seedLotNumber" name = "lotNumber" class="form-control" readonly="readonly" >
						 	 </div>
						 </div>
						
						 <div class="row" style="margin-bottom:10px">
						 	 <div class="col-lg-12">
						 		<label>作物信息  <small>Species Information</small><span class="required muted" style="color:red">*</span></label>
								<select class="selectpicker show-tick form-control" name="species.id" id = "seedSpeciesSelect" required data-live-search="true">
									<option value="0">--</option>
									<c:forEach items="${speciesLists}" var="species">
										<option value="${species.id}"> ${species.speciesNameCn }</option>
									</c:forEach>
							 	</select>
						 	 </div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
						 	 <div class="col-lg-6">
						 		<label>重量 <small>Weight</small> 千克(KG)<span class="required muted" style="color:red">*</span></label>
								<input type="text" name = "weight" class="form-control"  >
						 	 </div>
						 	 <div class="col-lg-6">
						 		<label>千粒重 <small>Thousand  Grain Weight</small> 克(g)</label>
								<input type="text" name = "thousandGrainWeight" class="form-control"  >
						 	 </div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
						 	<div class="col-lg-6">
								<label>进口编号 <small>Import Number</small></label> 
								<input type="text" name="importNumber" class="form-control"  >
							</div>
						 	<div class="col-lg-6">
								<label>进口中文名 <small>Import Name</small></label> 
								<input type="text" name = "importName" class="form-control"  >
								<!-- 
								<select class="form-control" name="importName.id">
									<option value="0">--</option>
									<c:forEach items="${importNameLists}" var="importName">
										<option value="${importName.id}">${importName.name}</option>
									</c:forEach>
							 	</select>
								 -->
							</div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
						 		<label>入库时间  <small>Storage Day</small></label> 
								<input type="text" name="storageDay" class="form-control form_datetime"><!--  id="datetimepicker"  -->
						 	</div>
						 	<div class="col-lg-6 form-group">
								<label>购入时间 <small>Purchase Day</small><span class="required muted" style="color:red">*</span></label>
								<input type="text" name="purchaseDay" class="form-control form_datetime">
							</div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
								<label>原始批号 <small>Original Lot</small><span class="required muted" style="color:red">*</span></label> 
								<input type="text" name="originalLot" class="form-control" placeholder="请输入原始批号">
							</div>
						 	<div class="col-lg-6">
								<label>原产地 <small>Original</small></label> 
								<input type="text" name="original" class="form-control" placeholder="请输入原产地">
							</div>
						 </div>
						 <div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
								<label>发芽率 <small>Germination</small></label> 
								<input name="germination" type="text" class="form-control" placeholder="请输入   XX%  格式" value="">
							</div>
							<div class="col-lg-6">
								<label>测试时间 <small>Test Time</small></label> 
								<input type="text" name="testTime" class="form-control form_datetime"  id="datetimepicker">
							</div>
						</div>
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
								<label>进口审批号 <small>Import Permit No</small></label> 
								<input name="importPermitNo" type="text" class="form-control" placeholder="请输入进口批号" value="">
							</div>
							<div class="col-lg-6">
								<label>检疫审批号 <small>Phyto No</small></label> 
								<input name="phytoNo" type="text" class="form-control" placeholder="请输入检疫批号" value="">
							</div>
						</div>
						
						<div class="row" style="margin-bottom:10px">
							<div class="col-lg-6">
								<label>进口商 <small>Impoter</small><span class="required muted" style="color:red">*</span></label> 
								<select class="form-control" name="importer.id">
									<option value="0">--</option>
									<c:forEach items="${importerLists }" var="importer">
										<option value="${importer.id}">${importer.simpleName} - ${importer.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-lg-6">
								<label>供应商 <small>Supplier</small><span class="required muted" style="color:red">*</span></label> 
								<select class="form-control" name="supplier.id">
									<option value="0">--</option>
									<c:forEach items="${supplierLists }" var="supplier">
										<option value="${supplier.id}">${supplier.simpleName} - ${supplier.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary submit" id="deliveryProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary cancelSubmit" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- add/edit seed modal ends -->
	<!-- update seed surplus weight starts -->
	<div class="modal fade" id="updateSeedModal" tabindex="-1" data-backdrop="static"  data-keyboard="false" 
		role="dialog" aria-labelledby="addModalLabel">
		<div class="modal-dialog" style = "width:350px">
			<div class="modal-content">
				<form id="updateSeedForm"
					action="../seed/update" 
					role="form" method="post" enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addModalLabel"></h4>
					</div>
					<div class="modal-body">
						 <input type="hidden" name="id">
						
						 <div class="row" style="margin-bottom:10px">
						 	 <div class="col-lg-12">
						 		<label>剩余重量(KG) <small>Surplus Weight</small><span class="required muted" style="color:red">*</span></label>
								<input type="text" name = "surplusWeight" class="form-control"  >
						 	 </div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary submit" id="deliveryProductionsBtn">提交</button>
						<button type="button" class="btn btn-primary cancelSubmit" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- update seed surplus weight starts -->
<!-- 
<div class="modal fade" id="addSeedModal" tabindex="-1" role="dialog"
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addSeedForm"
				action="<%=request.getContextPath()%>/seed/add" role="form"
				method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>作物 <small>Species Name</small><span class="required muted" style="color:red">*</span></label> <select
							class="form-control" name="species.id" id="speciesSelect">
							<option value="0">--</option>
							<c:forEach items="${speciesLists }" var="species">
								<option value="${species.id}">${species.nameCn }-
									${species.nameEn }</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group">
						<label>进口名 <small>Import Name</small><span class="required muted" style="color:red">*</span></label> <select
							class="form-control" name="importName.id">
							<option value="0">--</option>
						</select> <input type="hidden" id="primaryTablenameval"
							value="${obj.primaryTablename}">
					</div>
					<div class="form-group">
						<label>进口编号 <small>Import Number</small><span class="required muted" style="color:red">*</span></label> <input
							type="text" name="importNumber" class="form-control"
							placeholder="请输入进口编号">
					</div>
					<div class="form-group">
						<label>商品名 <small>Conmercial Name</small><span class="required muted" style="color:red">*</span></label> <input
							type="text" name="conmercialName" class="form-control"
							placeholder="请输入商品名">
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary" id="addSeedBtn">提交</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="cancelAddSeed">取消</button>
				</div>
			</form>
		</div>
	</div>
</div>
 -->
<!-- seed manage modal ends -->


<!-- data dictionary : species starts -->
<div class="modal fade" id="addSpeciesModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false" 
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addSpeciesForm"
				action="<%=request.getContextPath()%>/data/addSpecies" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group hide">
						<input type="text"name="nameCn"  value="">
						<input type="text"name="nameEn"  value="">
					</div>
					<div class="form-group">
						<label>作物略号 <small>crop_code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="cropCode" class="form-control" value="" placeholder="请输入作物略号">
					</div>
					<div class="form-group">
						<label>编号<small>No. </small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="no" class="form-control" value="" placeholder="请输入No.">
					</div>
					<div class="form-group">
						<label>英文名称 <small>name_en</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="speciesNameEn" class="form-control" value="" placeholder="请输入英文名称"
							required="required">
					</div>
					<div class="form-group">
						<label>作物名 <small>Crop name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="speciesNameCn" class="form-control" value="" placeholder="请输入作物名"
							required="required">
					</div>
					<div class="form-group">
						<label>作物名2 <small>Crop name2</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="speciesNameCn2" class="form-control" placeholder="请输入作物名2">
					</div>
					<div class="form-group">
						<label>分类 <small>group</small><span class="required muted" style="color:red">*</span></label> <select
							class="form-control" name="group.id" id="speciesSelect">
							<c:forEach items="${groupsList}" var="groups">
								<option value="${groups.id}">${groups.nameCn} - ${groups.nameEn}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button id="addSpeciesBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn"
						data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- data dictionary : species ends -->
<div class="modal fade" id="testModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false" 
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addPorm">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" name="id">
					</div>
					<div class="form-group ">
						<div class="row" style="margin-left:15px">
							<div class="input-group input-group-sm col-md-1">
		               	     <input type="text" class="form-control" >
		                	     <span class="input-group-btn">
		                  		    <a href="#" class="btn btn-primary" id="searchBtn"> 
		                          <span class="glyphicon glyphicon-minus"></span></a>
		                      </span>
		      		         </div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="addPackingUnitBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- data dictionary : packing unit starts -->
<div class="modal fade" id="addPackingUnitModal" tabindex="-1" role="dialog"  data-backdrop="static"  data-keyboard="false" aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addPackingUnitForm" method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" name="id">
						<input type="hidden" name="sample" id="packingUnitSample">
					</div>
					<div class="form-group">
					<label>样品选项  <small>sample</small>&nbsp; &nbsp; &nbsp;</label>
						<input type="radio" name="sampleOff" data-value="sampleOff"  class="sampleRadio" value="0" checked> 非样品&nbsp; &nbsp; &nbsp;
						<input type="radio" name="sampleOn" data-value="sampleOn"  class="sampleRadio" value="1"> 样品
					</div>
					<div class="form-group">
						<label>规格略号  <small>specification code</small><span class="required muted" style="color:red">*</span></label><input type="text"
							name="specificationCode" class="form-control" value=""
							placeholder="请输入包装物规格略号" required="required">
					</div>
					<div class="form-group">
						<label>包装物名称  <small>packing name</small><span class="required muted" style="color:red">*</span></label><input type="text"
							name="specificationName" class="form-control" value=""
							placeholder="请输入包装物规格名称" required="required">
					</div>
				
					<div class="form-group">
						<label>长度  <small>length</small></label> <input type="text"
							name="length" class="form-control" value=""
							placeholder="请输入包装物规格长度"  >
					</div>
					<div class="form-group">
						<label>宽度  <small>width</small></label> <input type="text"
							name="width" class="form-control" value=""
							placeholder="请输入包装物规格宽度"  >
					</div>
					<div class="form-group">
						<label>高度  <small>height</small></label> <input type="text"
							name="height" class="form-control" value=""
							placeholder="请输入包装物规格高度" >
					</div>
					<div class="form-group">
						<label>规格数值 <small>Specifications</small><span class="required muted" style="color:red">*</span></label> <input type="number"
							name="weight" class="form-control" value=""  
							placeholder="请输入规格数值" required="required">
					</div>
					<div class="form-group">
						<label>单位  <small>unit</small><span class="required muted" style="color:red">*</span></label>
						<select
							class="form-control" name="unit" >
								<option value="g">g -- 克 </option>
								<option value="kg">kg -- 千克 </option>
								<option value="s">s -- 粒</option>
								<option value="ks">ks -- 千粒</option>
						</select>
					</div>
					<div class="form-group">
						<label>类型  <small>type</small><span class="required muted" style="color:red">*</span></label>
						<!-- <select
							class="form-control" name="specifications" >
								<option value="箱">箱 </option>
								<option value="罐">罐 </option>
								<option value="袋">袋</option>
						</select> -->
						 <input type="text"
							name="specifications" class="form-control" value=""
							placeholder="请输入包装物规格类型 如：箱  罐  袋" >
					</div>
					<div class="form-group">
						<label>用途 <small>purpose</small></label> <input type="text"
							name="purpose" class="form-control" value=""
							placeholder="请输入包装物规格用途" >
					</div>
				</div>
				<div class="modal-footer">
					<button id="addPackingUnitBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- data dictionary : packing unit ends -->

<!-- import name starts -->
<div class="modal fade" id="addImportNameModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog" data-backdrop="static" 
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addImportNameForm"
				action="<%=request.getContextPath()%>/data/addImportName" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>进口名  <small>Import Name</small></label> <input type="text"
							name="name" class="form-control" value=""
							placeholder="请输入进口名" required="required">
					</div>
					<div class="form-group">
						<label>作物名称 <small>Species Name</small></label>
						<select
							class="form-control" name="species.id">
							<c:forEach items="${speciesList}" var="speciesName">
								<option value="${speciesName.id}">${speciesName.speciesNameCn} - ${speciesName.speciesNameEn}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button id="addImportNameBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- import name ends -->

<!-- importer starts -->
<div class="modal fade" id="addImporterModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog"
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addImporterForm"
				action="<%=request.getContextPath()%>/data/addImporter" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>进口商略号  <small>Importer Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="importerCode" class="form-control"
							placeholder="请输入进口商略号">
					</div>
					<div class="form-group">
						<label>进口商名称  <small>Importer Simple Name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="name" class="form-control" 
							placeholder="请输入进口商名称" >
					</div>
					<div class="form-group">
						<label>进口商邮编  <small>Importer Zip</small></label> <input type="text"
							name="zip" class="form-control" 
							placeholder="请输入进口商邮编" >
					</div>
					<div class="form-group">
						<label>进口商地址 <small>Importer Address</small></label> <input type="text"
							name="address" class="form-control"
							placeholder="请输入进口商地址" >
					</div>
					<div class="form-group">
						<label>进口商电话  <small>Importer Tel</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="tel" class="form-control"
							placeholder="请输入进口商电话" >
					</div>
					<div class="form-group">
						<label>进口商传真  <small>Importer Fax</small></label> <input type="text"
							name="fax" class="form-control"
							placeholder="请输入进口商传真"  >
					</div>
				</div>
				<div class="modal-footer">
					<button id="addImporterBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- importer ends -->

<!-- supplier starts -->
<div class="modal fade" id="addSupplierModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog"
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addSupplierForm"
				action="<%=request.getContextPath()%>/data/addSupplier" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>供应商略号  <small>Supplier Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="supplierCode" class="form-control" value=""
							placeholder="请输入供应商略号" required="required">
					</div>
					<div class="form-group">
						<label>供应商名称  <small>Supplier Name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="name" class="form-control" value=""
							placeholder="请输入供应商名称" required="required">
					</div>
			 			<div class="form-group">
						<label>供应商邮编  <small>Supplier Zip</small></label> <input type="text"
							name="zip" class="form-control" value=""
							placeholder="请输入进口商邮编" >
					</div>
					<div class="form-group">
						<label>供应商地址 <small>Supplier Address</small></label> <input type="text"
							name="address" class="form-control" value=""
							placeholder="请输入进口商地址" >
					</div>
					<div class="form-group">
						<label>供应商电话  <small>Supplier Tel</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="tel" class="form-control" value=""
							placeholder="请输入进口商电话" >
					</div>
					<div class="form-group">
						<label>供应商传真  <small>Supplier Fax</small></label> <input type="text"
							name="fax" class="form-control" value=""
							placeholder="请输入进口商传真"  >
					</div>
				</div>
				<div class="modal-footer">
					<button id="addSupplierBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- supplier ends -->

<!-- sale manager starts -->
<div class="modal fade" id="addSaleManagerModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog"
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addSaleManagerForm"
				action="<%=request.getContextPath()%>/data/addSaleManager" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>名称  <small>Sale Manager Name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="name" class="form-control" value=""
							placeholder="请输入销售经理姓名" required="required">
					</div>
					<div class="form-group">
						<label>Code <small>Sale Manager Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="SMCode" class="form-control" value=""
							placeholder="请输入销售经理代号" required="required">
					</div>
					<div class="form-group">
						<label>区域 <small>Area</small><span class="required muted" style="color:red">*</span></label> <select
							class="form-control" name="area.id">
							<c:forEach items="${areaList }" var="area">
								<option value="${area.id}">${area.nameCn} - ${area.nameEn}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button id="addSaleManagerBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- sale manager ends -->

<!-- logistics company starts -->
<div class="modal fade" id="addLogisticsCompanyModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog"
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addLogisticsCompanyForm"
				action="<%=request.getContextPath()%>/data/addLogisticsCompany" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>物流公司略号  <small>Logistics Company Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="code" class="form-control" value=""
							placeholder="请输入物流公司名称" required="required">
					</div>
					<div class="form-group">
						<label>物流公司名称  <small>Logistics Company Name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="name" class="form-control" value=""
							placeholder="请输入物流公司名称" required="required">
					</div>
				</div>
				<div class="modal-footer">
					<button id="addLogisticsCompanyBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- logistics company ends -->

<!-- logistics Group starts -->
<div class="modal fade" id="addGroupModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false"  aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addGroupForm"method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>分类中文名称  <small>Group</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="nameCn" class="form-control" value=""
							placeholder="请输入分类名称" required="required">
					</div>
					<div class="form-group">
						<label>分类英文名称  <small>Group</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="nameEn" class="form-control" value=""
							placeholder="请输入Group名称" required="required">
					</div>
				</div>
				<div class="modal-footer">
					<button id="addGroupBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- logistics company ends -->


<!-- logistics AreaEntity starts -->
<div class="modal fade" id="addAreaEntityModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog" aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addAreaEntityForm"method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>区域略号  <small>Region Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="regionCode" class="form-control" value=""
							placeholder="请输入区域略号 " required="required">
					</div>
					<div class="form-group">
						<label>区域名称  <small>Region</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="nameCn" class="form-control" value=""
							placeholder="请输入Region名称" required="required">
					</div>
						<div class="form-group">
						<label>区域代号  <small>Region</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="nameEn" class="form-control" value=""
							placeholder="请输入Region名称" required="required">
					</div>
				</div>
				
				<div class="modal-footer">
					<button id="addAreaEntityBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- logistics company ends -->

<!--   ProvinceEntity starts -->
<div class="modal fade" id="addProvinceEntityModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog" aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addProvinceEntityForm"method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>省份略号  <small> Province Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="provinceCode" class="form-control" value=""
							placeholder="请输入省份略号">
					</div>
					<div class="form-group">
						<label>区域  <small>Area Code</small><span class="required muted" style="color:red">*</span></label> 
							<select class="form-control" id="area.id" name="area.id" required="required">
							<c:forEach items="${areaList}" var="area">
								<option value="${area.id}">${area.nameCn} -- ${area.nameEn }</option>
							</c:forEach>
						</select>
					</div> 
						<div class="form-group">
						<label>省份名称  <small>Province Name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="nameCn" class="form-control" value=""
							placeholder="请输入省份名称">
					</div>
				</div>
				<div class="modal-footer">
					<button id="addProvinceEntityBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--   ProvinceEntity ends -->
<!--   addVarietyModal   -->
<div class="modal fade" id="addVarietyModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog"
	aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addVarietyForm"
				action="" role="form"
				method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
						<label>品种略号 <small>Variety Code</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="varietyCode" class="form-control" value=""
							placeholder="请输入品种简码" required="required">
					</div>
					<div class="form-group">
						<label>作物名称  <small>Variety Name</small><span class="required muted" style="color:red">*</span></label> 
						<!-- <input type="text" name="varietyName" class="form-control" value="" placeholder="请输入作物名称"  > -->
						<select
							class="form-control" name="species.id" id="speciesSelect">
							<c:forEach items="${speciesList}" var="species">
								<option value="${species.id}">${species.speciesNameCn} - ${species.speciesNameEn}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>商品名称  <small>Commercial Name</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="commercialName" class="form-control" value=""
							placeholder="请输入商品名称"  >
					</div>
					<div class="form-group">
						<label>品种名称  <small>Species</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="varietyName" class="form-control" value=""
							placeholder="请输入品种名称"  required="required">
					</div>
					<div class="form-group">
						<label>开始年  <small>Begin Date</small><span class="required muted" style="color:red">*</span></label> 
							<input type="text" name="beginDateShow" class="form-control datetimepicker_yyyymm" id="datetimepicker_yyyymm" >
					</div>
				</div>
				<div class="modal-footer">
					<button id="addLogisticsCompanyBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--   addVarietyModal end  -->

<!-- logistics CityEntity starts -->
<div class="modal fade" id="addCityEntityModal" tabindex="-1" data-backdrop="static"  data-keyboard="false"  role="dialog" aria-labelledby="addModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addCityEntityForm"method="post">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">新建</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="id">
					<div class="form-group">
					<label>省份  <small>Group</small><span class="required muted" style="color:red">*</span></label>
						<select class="form-control" id="province.id" name="province.id" required="required">
							<c:forEach items="${provinceList}" var="province">
								<option value="${province.id}">${province.provinceCode} -- ${province.nameCn }</option>
							</c:forEach>
						</select>
					<div class="form-group">
						<label>中文名称  <small>nameCn</small><span class="required muted" style="color:red">*</span></label> <input type="text"
							name="nameCn" class="form-control" value=""
							placeholder="请输入中文名称" required="required">
					</div>
				</div>
				</div>
				<div class="modal-footer">
					<button id="addLogisticsCompanyBtn" type="submit" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-primary cancelBtn" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- CityEntity  ends -->
<!-- 弹出提示框 -->
<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false" 
	aria-labelledby="addModalLabel" aria-hidden="true">
	<div class="modal-dialog" style = "width:300px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
					<!-- 
				<h5 class="modal-title" id="myModalLabel"><button type="button" class="btn btn-info" data-dismiss="modal">提示</button></h5>
					 -->
				<h5 class="modal-title" id="myModalLabel">提示</h5>
			</div>
			<div class="modal-body"><span style="padding-left:30px">处理中，请稍后。。。</span></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal" id="closeId">关闭</button>
			</div>
			<!-- 
				<button type="button" class="btn btn-primary">提交更改</button>
			 -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!--  弹出提示框  -->
<!-- 确认框 -->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" data-backdrop="static"  data-keyboard="false" 
	aria-labelledby="addModalLabel" aria-hidden="true">
	<div class="modal-dialog" style = "width:300px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
					<!-- 
				<h5 class="modal-title" id="myModalLabel"><button type="button" class="btn btn-info" data-dismiss="modal">提示</button></h5>
					 -->
				<h5 class="modal-title" id="myModalLabel">提示</h5>
			</div>
			<div class="modal-body"><span style="padding-left:20px">确定要删除吗？</span></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="confirmId">确定</button>
				<button type="button" class="btn btn-info" data-dismiss="modal" id="closeId">取消</button>
			</div>
			<!-- 
			 -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- 确认框 -->