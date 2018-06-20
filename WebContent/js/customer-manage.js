$(function(){
	document.title = "客户管理";
	//load table data and search function
	function BstpCustomerTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../customer/getCustomerData',
                method: 'post',
                contentType: "application/x-www-form-urlencoded",
                
                queryParamsType:'limit', 
                queryParams: function queryParams(params) {  
                  var param = {  
                      limit: params.limit,    
                      offset: params.offset
                  }; 
                  for(var key in searchArgs){
                      param[key]=searchArgs[key]
                  }  
                  return param;                   
                }, 
                
                locale:'zh-CN',//support Chinese
                pagination: true,//Open pagination function
                exportDataType:"basic",
                showExport:true,
                exportTypes:['excel','csv'],
                exportOptions:{  
                    ignoreColumn: [0],  //Ignore exporting the columns to file
                    fileName: 'customer management',  //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'customer management',  
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
                    onMsoNumberFormat: DoOnMsoNumberFormat  
                },  
                buttonsAlign:'left',
                pageNumber:1,//the default page is 1
                pageSize: 10,//each page display 10 records
                pageList: "[10,25,50,100,all]",//how many records each page can display
                sidePagination: "server", //pagination type: server side or client side
                showRefresh:true,//refresh button
                
                columns: [
                          {field: 'state',checkbox:true}, 
                          {field:'id',visible:false},
                          {field: 'company',title: '公司全称',halign:'center'},
                          {field: 'simpleName',title: '公司简称',halign:'center'},
                          {field: 'contact',title: '联系人',halign:'center'},
                          {field: 'telephone',title: '联系电话',halign:'center'},
                          {field: 'address',title: '公司地址',halign:'center'},
                          {field: 'email',title: '邮箱',halign:'center'},
//                          {field: 'firstMonth',title: '最初月份',halign:'center',align:"center",formatter: function(value,row,index){return dateFormatterFor_yyyymm(value)}},
                          {field: 'firstMonth',title: '最初月份',halign:'center',align:"left",formatter: function(value,row,index){
                        	  console.log("row:" + row);
                        	  console.log(row);
                        	  return row.ago == null?dateFormatter_yyyymmNoText2(value):dateFormatter_yyyymmNoText2(value) + row.ago;}
                          },
                          {field: 'city.province.area.nameCn',title: '区域',halign:'center'},
                          {field: 'city.province.area.nameEn',title: '区域简称',halign:'center'},
                          {field: 'city.province.nameCn',title: '省份',halign:'center'},
                          {field: 'city.nameCn',title: '城市',halign:'center'},
                          {field: 'saleManager.name',title: '销售人员',halign:'center'}
                         ]
			});
		}
	};
	
	var bstpTable=new BstpCustomerTable($("#customerTable"));
    bstpTable.init({});
    
    $("#customerSearchBtn").click(function(){
        var searchArgs={
        	simple_name:$("#searchCustomer input[name='simple_name']").val(),
        	'city.id':$("#searchCustomer select[name='city.id'] option:selected").val()
        };
        bstpTable.init(searchArgs)
        return false;
    });
    
    //add customer function
    var addOrEdit; //flag of add or edit customer:add/edit
    
    $("#addCustomer").click(function(){
    	addOrEdit = "add";
//    	$("#addCustomerForm select[name='area.id']").removeAttr("disabled");
//		$("#addCustomerForm select[name='province.id']").removeAttr("disabled");
    	
    	$("#addCustomerModal label.error").html("");
    	$("#addCustomerForm .modal-header h4").html("新建客户");
    	console.log("===delete===");
    	$("#addCustomerForm select[name='province.id']").empty();
    	$("#addCustomerForm select[name='city.id']").empty();
    	$("#addCustomerForm select[name='province.id']").append("<option value='0'> -- </option>");
    	$("#addCustomerForm select[name='city.id']").append("<option value='0'> -- </option>");
    	$("#addCustomerForm select[name='province.id']").selectpicker('refresh');
    	$("#addCustomerForm select[name='city.id']").selectpicker('refresh');
    	$("#addCustomerModal").modal('show');
    	return false;
    });
    
    //edit customer information
    $("#editCustomer").click(function(){
    	selections = $("#customerTable").bootstrapTable('getAllSelections');
		console.log(selections);
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			addOrEdit = "edit";
			var selProvince = $("#addCustomerForm select[name='province.id']");
			selProvince.empty();
			 $.ajax({
					type : "get",
					url : "../customer/getProvince?areaId="+selections[0].city.province.area.id,
					success : function(data) {
						console.log("======data=====");
						console.log(data);
						selProvince.append("<option value=\"" + selections[0].city.province.id + "\" selected>" + selections[0].city.province.nameCn+"</option>");
						for(var i = 0; i< data.length;i++){
							var col = data[i];
							if(data[i].nameCn != selections[0].city.province.nameCn){
								selProvince.append("<option value=" + data[i].id + ">" + data[i].nameCn+"</option>");
							}
						}
						selProvince.selectpicker('refresh');
						
						var selCity = $("#addCustomerForm select[name='city.id']");
						selCity.empty();
						 $.ajax({
								type : "get",
								url : "../customer/getCity?provinceId="+selections[0].city.province.id,
								success : function(data) {
									console.log(data);
									selCity.append("<option value=\"" + selections[0].city.id + "\" selected>" + selections[0].city.nameCn+"</option>");
									for(var i = 0; i< data.length;i++){
										var col = data[i];
										if(data[i].nameCn != selections[0].city.nameCn){
											selCity.append("<option value=" + data[i].id + ">" + data[i].nameCn+"</option>");
										}
									}
									selCity.selectpicker('refresh');
								}
							});
						 
						 var salemanagerSel = $("#addCustomerForm select[name='saleManager.id']");
							salemanagerSel.empty();
							 $.ajax({
									type : "get",
									url : "../customer/getSaleManager?areaId="+selections[0].city.province.area.id,
									success : function(data) {
										console.log(data);
										salemanagerSel.append("<option value='0'> -- </option>");
										salemanagerSel.append("<option value=\"" + selections[0].saleManager.id + "\" selected>" + selections[0].saleManager.name+"</option>");
										for(var i = 1; i< data.length;i++){
											var col = data[i];
											if(data[0].saleManager.id != selections[0].saleManager.id){
												salemanagerSel.append("<option value=" + data[i].id + ">" + data[i].name+"</option>");
											}
										}
									}
								});
					}
				});
			
			$("#addCustomerForm input[name='id']").val(selections[0].id);
			$("#addCustomerForm input[name='company']").val(selections[0].company);
			$("#addCustomerForm input[name='simpleName']").val(selections[0].simpleName);
			$("#addCustomerForm input[name='contact']").val(selections[0].contact);
			$("#addCustomerForm input[name='telephone']").val(selections[0].telephone);
			$("#addCustomerForm input[name='address']").val(selections[0].address);
			$("#addCustomerForm input[name='firstMonth']").val(dateFormatter_yyyymmNoText2(selections[0].firstMonth));
			$("#addCustomerForm input[name='ago']").val(selections[0].ago);
			$("#addCustomerForm input[name='email']").val(selections[0].email);
			
			$("#addCustomerForm select[name='area.id']").val(selections[0].city.province.area.id);
			
			if(selections[0].saleManager == null){
				$("#addCustomerForm select[name='saleManager.id']").val(null);
			}else{
				$("#addCustomerForm select[name='saleManager.id']").val(selections[0].saleManager.id);
			}
			$("#addCustomerModal label.error").html("");
			$("#addCustomerForm .modal-header h4").html("编辑客户");
			$("#addCustomerModal").modal('show');
		}
		return false;
    });
    
    //select the area,then automatically load province data and sale manager data
    function getProvinceByArea(){
		  console.log($("#addCustomerForm select[name='area.id'] option:selected").val());
		  var sel2 = $("#addCustomerForm select[name='province.id']");
		  sel2.empty();
		  var selCity = $("#addCustomerForm select[name='city.id']");
		  selCity.empty();
		  selCity.append("<option value='0'> -- </option>");
		  if($("#addCustomerForm select[name='area.id'] option:selected").val() == "0"){
			  sel2.append("<option value='0'> -- </option>");
			  return;
		  }
		  $.ajax({
				type : "get",
				url : "../customer/getProvince?areaId="+$("#addCustomerForm select[name='area.id'] option:selected").val(),
				success : function(data) {
					console.log(data);
					sel2.append("<option value='0'> -- </option>");
					sel2.append("<option value=\"" + data[0].id + "\">" + data[0].nameCn+"</option>");
					for(var i = 1; i< data.length;i++){
						var col = data[i];
						sel2.append("<option value=" + data[i].id + ">" + data[i].nameCn+"</option>");
					}
					
					
					//=======================
					var salemanagerSel = $("#addCustomerForm select[name='saleManager.id']");
					salemanagerSel.empty();
					if($("#addCustomerForm select[name='area.id'] option:selected").val() == "0"){
						 salemanagerSel.append("<option value='0'> -- </option>");
						  return;
					  }
					 $.ajax({
							type : "get",
							url : "../customer/getSaleManager?areaId="+$("#addCustomerForm select[name='area.id'] option:selected").val(),
							success : function(data) {
								console.log(data);
								salemanagerSel.append("<option value='0'> -- </option>");
								salemanagerSel.append("<option value=\"" + data[0].id + "\">" + data[0].name+"</option>");
								for(var i = 1; i< data.length;i++){
									var col = data[i];
									salemanagerSel.append("<option value=" + data[i].id + ">" + data[i].name+"</option>");
								}
							}
						});
					//======================
					 sel2.selectpicker('refresh');
					 selCity.selectpicker('refresh');
				}
			});
    }
    $("#areaId").change(getProvinceByArea);
    
    //Select province,then automatically load city data
    function getCityByArea(){
		  console.log($("#addCustomerForm select[name='province.id'] option:selected").val());
		  var sel2 = $("#addCustomerForm select[name='city.id']");
		  sel2.empty();
		  if($("#addCustomerForm select[name='province.id'] option:selected").val() == "0"){
			  sel2.append("<option value='0'> -- </option>");
			  return;
		  }
		  $.ajax({
				type : "get",
				url : "../customer/getCity?provinceId="+$("#addCustomerForm select[name='province.id'] option:selected").val(),
				success : function(data) {
					console.log(data);
					sel2.append("<option value='0'> -- </option>");
					sel2.append("<option value=\"" + data[0].id + "\">" + data[0].nameCn+"</option>");
					for(var i = 1; i< data.length;i++){
						var col = data[i];
						sel2.append("<option value=" + data[i].id + ">" + data[i].nameCn+"</option>");
					}
					sel2.selectpicker('refresh');
				}
			});
    }
    $("#provinceId").change(getCityByArea);
    
    
    //Add or edit customer inormation
    $("#addCustomerForm").validate({
    	rules:{
    		company:{
    			required:true,
    			notEmpty:/^$/
    		},
    		simpleName:{
    			required:true,
    			notEmpty:/^$/
    		},
    		contact:{
    			required:true,
    			notEmpty:/^$/
    		},
    		telephone:{
    			required:true,
    			notEmpty:/^$/,
    			phone:/^\d*(\+{0,1}|-{0,1})\d+$/
    		},
    		email:{
    			email:true
    		},
    		firstMonth:{
    			required:true,
    			notEmpty:/^$/
    		},
    		'area.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		'province.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		'city.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		'saleManager.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		}
    	},
    	messages:{
    		company:{
    			required:'请输入公司全称！',
    			notEmpty:'请输入公司全称！'
    		},
    		simpleName:{
    			required:'请输入公司简称！'
    		},
    		contact:{
    			required:'请输入联系人！'
    		},
    		telephone:{
    			required:'请输入联系电话！'
    		},
    		email:{
    			email:'请输入正确的邮箱格式！'
    		},
    		firstMonth:{
    			required:'请选择最初月份！',
    			notEmpty:'请选择最初月份！'
    		},
    		'area.id':{
    			required:'请选择区域信息！'
    		},
    		'province.id':{
    			required:'请选择省份！'
    		},
    		'city.id':{
    			required:'请选择城市',
    		},
    		'saleManager.id':{
    			required:'请选择销售信息！'
    		}
    	},
    	submitHandler:function(form){
    		if("add" === addOrEdit){
    			$("#addCustomerForm input[name='id']").val(null);
    			
    			var first_month = $("#addCustomerForm input[name='firstMonth']").val();
    			first_month = dateFormatter(first_month);
    			console.log("first_month:" + first_month);
    			$("#addCustomerForm input[name='firstMonth']").val(first_month);
    			console.log($("#addCustomerForm input[name='firstMonth']").val());
    			
//    			$("#addCustomerForm select[name='area.id']").attr("disabled","disabled");
//    			$("#addCustomerForm select[name='province.id']").attr("disabled","disabled");
    			
        		var params = $("#addCustomerForm").serialize();
        		console.log("params:" + params);//dateFormatter
        		
        		$.ajax({
        			type : "post",
        			url : "../customer/add",
        			data : params,
        			beforeSend:function(){
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        				$("#addCustomerModal").modal('hide');
        			},
        			success : function(data) {
        				if (data['id'] != null || data['id'] != "") {
        					$("#alertModal .modal-body").text("新建成功！");
        					$("#customerTable").bootstrapTable("refresh");
        					$('#addCustomerForm')[0].reset();
        				}
        			}
        		});
        	}else if("edit" === addOrEdit){
        		var first_month = $("#addCustomerForm input[name='firstMonth']").val();
    			first_month = dateFormatter(first_month);
    			console.log("first_month:" + first_month);
    			$("#addCustomerForm input[name='firstMonth']").val(first_month);
    			
        		var params = $("#addCustomerForm").serialize();
    			console.log("params:"+params);
    			$.ajax({
    				type:"post",
    				url:"../customer/edit",
    				data:params,
    				beforeSend:function(){
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        				$("#addCustomerModal").modal('hide');
        			},
    				success:function(data){
    					$("#alertModal .modal-body").text(data['message']);
    					$('#addCustomerForm')[0].reset();
    					$("#customerTable").bootstrapTable('refresh');
//    					document.getElementById("provinceId").options.selectedIndex = -1; //默认未选择省份 
//    					$("#provinceId").selectpicker('refresh');
//    					document.getElementById("cityIdID").options.selectedIndex = -1; //默认未选择城市
//    					$("#cityIdID").selectpicker('refresh');
    				}
    			});
        	}
    		return false;
    	}
    });
    
    //reset form content when close model
	$(".close").click(function(){
		$("#addCustomerForm")[0].reset();
	});
	$("#cancelCustomerBtn").click(function(){
		$("#addCustomerForm")[0].reset();
	});
    
	//delete customer function
	var pendingRequests = {};
	jQuery.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	var key = options.url;
	console.log(key);
	if (!pendingRequests[key]) {
		pendingRequests[key] = jqXHR;
	}else{
		 jqXHR.abort(); //放弃后触发的提交
	}
	var complete = options.complete;
	options.complete = function(jqXHR, textStatus) {
		pendingRequests[key] = null;
		if (jQuery.isFunction(complete)) {
			complete.apply(this, arguments);
		}
		};
	});
	
	$("#closeId").click(function(){
		$("#confirmModal").modal("hide");
	});
	
    $("#deleteCustomer").click(function(e){
    	var selections = $("#customerTable").bootstrapTable('getAllSelections');
    	if(selections == null || selections.length == 0){
    		$("#alertModal .modal-body").text("请选择删除记录！");
			$("#alertModal").modal("show");
    	}else{
    		$("#confirmModal").modal("show");
			
    		$("#confirmId").one('click',function(){
    				$("#confirmModal").modal("hide");
					var customerList = new Array();
					for(var i = 0;i<selections.length;i++){
						customerList.push(selections[i].id);
					}
					$.ajax({
						type:"post",
						url:"../customer/delete",
						data:JSON.stringify(customerList),
						contentType : 'application/json;charset=utf-8',
						beforeSend:function(){
							$("#alertModal .modal-body").text("处理中，请稍后。。。");
							$("#alertModal").modal("show");
						},
						success:function(data){
							$("#alertModal .modal-body").text(data['message']);
							$("#customerTable").bootstrapTable('refresh');
						}
					});
			});
    	}
    });
    
})