//page title
document.title="包装管理";
$(function() {
    $(".nav-pills > li:nth-child(3)").addClass("active");
    
    $.fn.datetimepicker.defaults = {
    		//default language: Chinese
            language: 'zh-CN',
            clearBtn: true,
            //default date format
            CustomFormat:'yyyy-MM-dd hh-mm-ss',
            autoclose: true,
            todayBtn: true,
            startView: 2,
            minView: 2,
            pickerPosition: "bottom-left"
        };
    
    $('.form_datetime').datetimepicker({
    	showSecond: true,
    	CustomFormat:'yyyy-MM-dd hh-mm-ss'
    });
    
    //load table data and search function
    function BstpPackagingTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../packaging/getPackagingData',
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
                    ignoreColumn: [0], //Ignore exporting the columns to file
                    fileName: 'Packaging management',  //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'Packaging management',  
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
                          {field: 'id',title: 'id',visible:false,halign:'center'},
//                          {field: 'lotNumber',title: '批号',halign:'center',formatter:function(value, row, index){
//                        	  return [
//      								'2017A007000'+index
//      								].join('');
//                                }},
                          {field: 'lotNumber',title: '批号',halign:'center'},
                          {field: 'species.speciesNameCn',title: '作物',halign:'center'},
//                          {field: 'variety.varietyName',title: '品种',halign:'center'},
//                          {field: 'variety.commercialName',title: '商品名',halign:'center'},
                          {field: 'packingUnit.specificationName',title: '包装物名称',halign:'center',align:'left'},
                          {field: 'packingUnit.specifications',title: '包装物规格',halign:'center',align:'left'},
                          {field: 'packingUnit.specificationCode',title: '包装物规格编码',halign:'center',align:'left'},
                          {field: 'planAmount',title: '计划数量',halign:'center',align:'right'},
                          {field: 'currentAmount',title: '实际数量',halign:'center',align:'right'},
                          {field: 'surplusAmount',title: '剩余数量',halign:'center',align:'right',formatter: function ( data,  row, meta ) {
                        	  console.log("data:" + data);
                        	  var realamount=data+row.currentAmount-row.planAmount;
                        	  if(realamount<0)
                        		  realamount=0;
                              return realamount;
                          }},
                          {field: 'packingUnit.sample',title: '样品',halign:'center',align:'right',formatter: function(value,row,index){if(value =='0' )return'否';if(value =='1' )return'是';if(value ==null )return'否'}},
                          {field: 'storageDay',title: '入库时间',halign:'center',formatter: dateFormatter2},
                          {field: 'operate',
                              title: '操作',
                              align: 'center',
                              events: operateEvents,
                              formatter: operateFormatter}
                         ]
                
			});
		}
	};
	
	 $("#packagingSearchBtn").click(function(){
	    	console.log($("#searchPackaging select[name='conmercial_name'] option:selected").val());
	        var searchArgs={
	        	  speciesNameCn:$("#searchPackaging input[name='speciesNameCn']").val(),
	        	  packingUnitId:$("#searchPackaging select[name='packingUnit.id'] option:selected").val()
	        };
	        bstpTable.init(searchArgs)
	        return false;
	    });
	
	var bstpTable=new BstpPackagingTable($("#packagingTable"));
	var ffData = new FormData();
    bstpTable.init(
    	function operateFormatter(value, row, index) {
	        return [
	            '<a class="exportTxt btn btn-success" href="javascript:void(0)" style="marging-right:10px" title="export txt">',
	            '导出txt数据</a>'
//	            ,'<a class="delivery btn btn-warning" href="javascript:void(0)" title="出库">',
//	            '出库</a>  '
	        ].join('');
	    },
		window.operateEvents = {
//	    		'click .delivery': function (e, value, row, index) {
//	    			if(row.surplusAmount == 0 || row.surplusAmount == "0"){
//	    				$("#alertModal .modal-body").text("剩余数量为0！");
//        				$("#alertModal").modal("show");
//        				return;
//	    			}
//	    			
//	    			var seedInfo = row.seed.species.nameCn + "--" + row.seed.species.nameEn + "--" + row.seed.conmercialName + "--" + row.packingUnit.specifications;
//	    			$("#deliveryProductionsForm input[name='seedInfo']").val(seedInfo);
//	    			$("#deliveryProductionsForm input[name='importNumber']").val(row.seed.importNumber);
//	    			$("#deliveryProductionsForm input[name='importName']").val(row.seed.importName.name);
//	    			$("#deliveryProductionsForm input[name='storageDay']").val(dateFormatter(row.storageDay));
//	    			
//	    			ffData.append("storageId",row.id);
//	    			ffData.append("seedId",row.seed.id);
//	    			ffData.append("packingUnitId",row.packingUnit.id);
//	    			ffData.append("storageDay",dateFormatter(row.storageDay));
//	    			console.log('row.id:' + row.id);
//	    			console.log(row);
//	    			$.ajax({
//	    				type : "get",
//	    				url : "../productions/generateLotNumber?seedId="+row.seed.id ,
//	    				success : function(data) {
//	    					$("#deliveryProductionsForm input[name='lotNumber']").val(data['message']);
//	    					$("#deliveryProductionsModal label.error").html("");
//	    					$("#deliveryProductionsModal .modal-header h4").html("产品出库");
//	    					$("#deliveryProductionsModal").modal("show");
//	    				}
//	    			});
//	    			
//	    		},
	    		'click .exportTxt': function (e, value, row, index) {
	    			console.log(row);
	    			console.log(row.id);
	    			window.open("../packaging/exportTxt?id="+row.id, '_self');
	    		}
	    	}
	 );
    
    function getSaleManagerByCustomerId(){
		  console.log($("#deliveryProductionsForm select[name='customer.id'] option:selected").val());
		  var sel2 = $("#deliveryProductionsForm input[name='saleManager']");
		  sel2.val("");
		  if($("#deliveryProductionsForm select[name='customer.id'] option:selected").val() == "0"){
			  sel2.val("");
			  return;
		  }
		  $.ajax({
				type : "get",
				url : "../productions/getSaleManager?customerId="+$("#deliveryProductionsForm select[name='customer.id'] option:selected").val(),
				success : function(data) {
					sel2.val(data['name']);
				}
			});
    }
   $("#deliverCustomerId").change(getSaleManagerByCustomerId);
    
    //delivery productions
    $("#deliveryProductionsForm").validate({
    	rules:{
    		orderFile:{
    			required:true
    		},
    		purchase_day:{
    			required:true,
    			notEmpty:/^$/
    		},
    		original_lot:{
    			required:true,
    			notEmpty:/^$/
    		},
    		original:{
    			required:true,
    			notEmpty:/^$/
    		},
    		import_permit_no:{
    			required:true,
    			notEmpty:/^$/
    		},
    		phyto_no:{
    			required:true,
    			notEmpty:/^$/
    		},
    		deliveryAmount:{
    			required:true,
    			notEmpty:/^$/,
    			number:true,
    			positive:/^[0-9]+$/
    		},
    		deliveryTime:{
    			required:true,
    			notEmpty:/^$/
    		},
    		logisticCompany:{
    			required:true
    		},
    		'customer.id':{
    			required:true,
    			notZero:0
    		},
    		'importer.id':{
    			required:true
    		},
    		'supplier.id':{
    			required:true
    		}
    	},
    	messages:{
    		orderFile:{
    			required:'请导入订单文件！'
    		},
    		purchase_day:{
    			required:'请选择购入时间！'
    		},
    		original_lot:{
    			required:'请输入原始批号！'
    		},
    		original:{
    			required:'请输入原产地！'
    		},
    		import_permit_no:{
    			required:'请输入进口审批号！',
    		},
    		phyto_no:{
    			required:'请输入检疫审批号！',
    		},
    		deliveryAmount:{
    			required:'请输入出库数量！',
    			positive:'只能输入正整数！'
    		},
    		deliveryTime:{
    			required:'请选择出库日期！'
    		},
    		logisticCompany:{
    			required:'请选择物流公司！'
    		},
    		'customer.id':{
    			required:'请选择客户信息！'
    		},
    		'importer.id':{
    			required:'请选择进口商信息！'
    		},
    		'supplier.id':{
    			required:'请选择供应商信息！'
    		}
    	},
    	submitHandler:function(form){
    		ffData.append("uploadFile",$("#orderFile")[0].files[0]);
    		ffData.append("deliveryAmount",$("#deliveryProductionsForm input[name='deliveryAmount']").val());
    		ffData.append("deliveryTime",$("#deliveryProductionsForm input[name='deliveryTime']").val());
    		ffData.append("customerId",$("#deliveryProductionsForm select[name='customer.id']").val());
    		ffData.append("original_lot",$("#deliveryProductionsForm input[name='original_lot']").val());
    		ffData.append("original",$("#deliveryProductionsForm input[name='original']").val());
    		ffData.append("lotNumber",$("#deliveryProductionsForm input[name='lotNumber']").val());
    		ffData.append("purchase_day",$("#deliveryProductionsForm input[name='purchase_day']").val());
    		if($("#deliveryProductionsForm input[name='germination']").val()){
    			ffData.append("germination",$("#deliveryProductionsForm input[name='germination']").val());
    		}
    		if($("#deliveryProductionsForm input[name='test_time']").val()){
    			ffData.append("test_time",$("#deliveryProductionsForm input[name='test_time']").val());
    		}
    		ffData.append("importer.id",$("#deliveryProductionsForm select[name='importer.id']").val());
    		ffData.append("phyto_no",$("#deliveryProductionsForm input[name='phyto_no']").val());
    		ffData.append("import_permit_no",$("#deliveryProductionsForm input[name='import_permit_no']").val());
    		ffData.append("logisticId",$("#deliveryProductionsForm select[name='logisticCompany']").val());
    		ffData.append("supplier.id",$("#deliveryProductionsForm select[name='supplier.id']").val());
    		
    		$.ajax({
        			type : "post",
        			url : "../productions/delivery",
        			data:ffData,
        			processData:false,
        			scriptCharset:'utf-8',
        			contentType:false,
        			beforeSend:function(){
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        				$("#deliveryProductionsModal").modal('hide');
        			},
        			success : function(data) {
        				console.log('Save Tracking Web Site!!!!!!!');
        				console.log(data['result']);
        				$("#alertModal .modal-body").text(data['result']);
        				$("#alertModal").modal("show");
        				$("#deliveryProductionsForm")[0].reset();
        				ffData = new FormData();
        				if(data['dataManage'] != null){
        					dataManage = new Object();
        					dataManage = data['dataManage'];
        					console.log(dataManage.lotNumbers);
        					$.ajax({
        						type : "post",
        	        			url : "../dataSynchronization/startSyn",
        	        			data:{synModel:0,code:dataManage.lotNumbers},
        	        			dataType : 'json',
        	        			success:function(data){
        	        				console.log(data['message']);
        	        			}
        					});
        				}
        				$('#deliveryProductionsForm')[0].reset();
        				$("#productionsTable").bootstrapTable("refresh");
        			},
        			error: function (jqXHR, textStatus, errorThrown) {
        				$("#alertModal .modal-body").text('出库失败: '+jqXHR.responseText);
        			}
        		});
    		
    		return false;
    	}
    });
    
    function operateFormatter(value, row, index) {
        return [
                '<a class="exportTxt btn btn-success" href="javascript:void(0)" style="margin-right:10px" title="export txt">',
                '导出txt数据</a>'
//                ,'<a class="delivery btn btn-warning" href="javascript:void(0)" title="出库">',
//                '出库</a>  '
        ].join('');
    }
    
   
    
    
    //add productions
    var addOrEdit;
    $("#addPackaging").click(function(){
//    	$.ajax({
//	  			type : "post",
//	  			url : "../packaging/generateLotNumber",
//	  			data : params,
//	  			beforeSend:function(){
//	  				$("#alertModal .modal-body").text("处理中，请稍后。。。");
//	  				$("#alertModal").modal("show");
//	  				$("#addPackagingModal").modal('hide');
//	  			},
//	  			success : function(data) {
//	  				$("#alertModal .modal-body").text(data['message']);
//	  				$("#packagingTable").bootstrapTable("refresh");
//	  				$('#addPackagingForm')[0].reset();
//	  			}
//	  		});
    	
    	addOrEdit = "add";
    	$("#addPackagingModal label.error").html("");
    	$("#addPackagingModal .modal-header h4").html("包装订购");
    	$("#addPackagingModal").modal("show");
    	$("#addPackagingForm select[name='species.id']").attr("disabled",false);
    	document.getElementById("packageSpeciesSelectID").options.selectedIndex = 0; //默认未选择品种信息 
		$("#packageSpeciesSelectID").selectpicker('refresh');
		document.getElementById("packageUnitSelectID").options.selectedIndex = 0; //默认未选择包装信息 
		$("#packageUnitSelectID").selectpicker('refresh');
    	$('#addPackagingForm')[0].reset();
    	return false;
    });
    
    //edit productions
    $("#editPackaging").click(function(){
    	selections = $("#packagingTable").bootstrapTable('getAllSelections');
    	console.log(selections);
    	if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			addOrEdit = "edit";
			$("#addPackagingForm input[name='id']").val(selections[0].id);
			$("#addPackagingForm input[name='lotNumber']").val(selections[0].lotNumber);
			$("#addPackagingForm select[name='species.id']").val(selections[0].species.id);
			$("#addPackagingForm select[name='species.id']").selectpicker('val',selections[0].species.id);
			$("#addPackagingForm select[name='packingUnit.id']").val(selections[0].packingUnit.id);
			$("#addPackagingForm select[name='packingUnit.id']").selectpicker('val',selections[0].packingUnit.id);
			$("#addPackagingForm input[name='planAmount']").val(selections[0].planAmount);
			$("#addPackagingForm input[name='planAmount']").attr("readonly","readonly");
			$("#addPackagingForm input[name='currentAmount']").val(selections[0].currentAmount);
			$("#addPackagingForm input[name='storageDay']").val(dateFormatter2(selections[0].storageDay));
//			$("#addPackingForm select[name='variety.id']").attr("disabled","disabled");
			
			$("#addPackagingModal label.error").html("");
			$("#addPackagingModal .modal-header h4").html("编辑包装");
			$("#addPackagingModal").modal("show");
		}
    });
    
    //update productions
    $("#updatePackaging").click(function(){
    	selections = $("#packagingTable").bootstrapTable('getAllSelections');
		console.log(selections);
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			$("#updatePackagingForm input[name='id']").val(selections[0].id);
			$("#updatePackagingForm input[name='lotNumber']").val(selections[0].lotNumber);
			$("#updatePackagingForm input[name='planAmount']").val(selections[0].planAmount);
			$("#updatePackagingForm input[name='planAmount']").attr("readonly","readonly");
			$("#updatePackagingForm input[name='currentAmount']").val(selections[0].currentAmount);
			
			$("#updatePackagingForm select[name='species.id']").val(selections[0].species.id);
			$("#updatePackagingForm select[name='species.id']").selectpicker('val',selections[0].species.id);
			
			$("#updatePackagingForm select[name='species.id']").attr("readonly","readonly");
			
			$("#updatePackagingForm select[name='packingUnit.id']").val(selections[0].packingUnit.id);
			$("#updatePackagingForm select[name='packingUnit.id']").selectpicker('val',selections[0].packingUnit.id);
			
			$("#updatePackagingForm select[name='packingUnit.id']").attr("readonly","readonly");
			$("#updatePackagingForm input[name='storageDay']").val(dateFormatter2(selections[0].storageDay));
//			$("#updatePackagingForm input[name='storageDay']").attr("readonly","readonly");
			
			$("#updatePackagingModal label.error").html("");
			$("#updatePackagingModal .modal-header h4").html("更新实际数量");
			$("#updatePackagingModal").modal("show");
			var params = $("#updatePackagingForm").serialize();
			console.log("paramsaa:"+params);
		}
    	return false;
    });
    
    //add or edit productions
    $("#addPackagingForm").validate({
    	rules:{
    		'species.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		'packingUnit.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		planAmount:{
    			required:true,
    			notEmpty:/^$/,
    			number:true,
    			min:1,
    			positive:/^[0-9]+$/
    		},
    		currentAmount:{
    			required:false,
    			number:true,
    			positive:/^[0-9]*$/,
    			smallThanPlanAmount1:$("#addPackagingForm input[name='planAmount']").val()
    		}
    	},
    	messages:{
    		'species.id':{
    			required:'请选择作物信息！'
    		},
    		'packingUnit.id':{
    			required:'请选择包装物信息！'
    		},
    		planAmount:{
    			required:'请输入计划数量！',
    			positive:'计划数量只能为正整数！',
    			min:'请输入大于0的数！'
    		},
    		currentAmount:{
    			positive:'只能输入正整数！'
    		}
    	},
    	submitHandler:function(form){
      		  	if(addOrEdit == "add"){
      		  		$("#addPackagingForm input[name='id']").val(null);
      		  		var params = $("#addPackagingForm").serialize();
      		  		console.log(params);
      		  		$.ajax({
      		  			type : "post",
      		  			url : "../packaging/add",
      		  			data : params,
      		  			beforeSend:function(){
      		  				$("#alertModal .modal-body").text("处理中，请稍后。。。");
      		  				$("#alertModal").modal("show");
      		  				$("#addPackagingModal").modal('hide');
      		  			},
      		  			success : function(data) {
      		  				$("#alertModal .modal-body").text(data['message']);
      		  				$("#packagingTable").bootstrapTable("refresh");
      		  				$('#addPackagingForm')[0].reset();
      		  			}
      		  		});
      		  	}
      		  	
      		  	if(addOrEdit == "edit"){
      		  		var params = $("#addPackagingForm").serialize();
      		  		console.log(params);
      		  		$.ajax({
      		  			type : "post",
      		  			url : "../packaging/edit",
      		  			data : params,
      		  			beforeSend:function(){
      		  				$("#alertModal .modal-body").text("处理中，请稍后。。。");
      		  				$("#alertModal").modal("show");
      		  				$("#addPackagingModal").modal('hide');
      		  			},
      		  			success : function(data) {
      		  				$("#alertModal .modal-body").text(data['message']);
      		  				$("#packagingTable").bootstrapTable("refresh");
      		  				$('#addPackagingForm')[0].reset();
      		  				$("#addPackagingForm input[name='planAmount']").attr("readonly",false);
      		  			}
      		  		});
      		  	}
        	
      		  	return false;
    		}
    });
    
    $("#updatePackagingForm").validate({
    	rules:{
    		currentAmount:{
    			required:true,
    			notEmpty:/^$/,
    			number:true,
    			positive:/^[0-9]+$/,
    			min:1,
    			smallThanPlanAmount2:$("#updatePackagingForm input[name='planAmount']").val()
    		}
    	},
    	messages:{
    		currentAmount:{
    			required:'请输入实际数量！',
    		    min:'请输入大于0的数！'
    		}	
    	},
    	submitHandler:function(form){
    		var params = $("#updatePackagingForm").serialize();
			console.log("params:"+params);
			$.ajax({
				type:"post",
				url:"../packaging/update",
				data:params,
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    				$("#updatePackagingModal").modal('hide');
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$('#updatePackagingForm')[0].reset();
					$("#packagingTable").bootstrapTable('refresh');
				}
			});
    		
    		return false;
    	}
    });
    
    //when close model,reset form content
    $(".cancelSubmit").click(function(){
    	$('#addPackagingForm')[0].reset();
    	$("#addPackagingForm input[name='planAmount']").attr("readonly",false);
    	$('#updatePackagingForm')[0].reset();
    	$("#updatePackagingForm input[name='planAmount']").attr("readonly",false);
    });
//    $("#cancelEditProductionsBtn").click(function(){
//    });
//    $("#cancelDeliveryBtn").click(function(){
//    	$('#deliveryProductionsForm')[0].reset();
//    });
    $(".close").click(function(){
    	$('#addPackagingForm')[0].reset();
    	$('#updatePackagingForm')[0].reset();
//    	$('#deliveryProductionsForm')[0].reset();
    	$("#addPackagingForm input[name='planAmount']").attr("readonly",false);
    });
    
    
   // delete productions
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
	
    $("#deletePackaging").click(function(){
    	 var selections = $("#packagingTable").bootstrapTable('getAllSelections');
    		if(selections == null || selections.length == 0){
    			$("#alertModal .modal-body").text("请选择删除记录！");
    			$("#alertModal").modal("show");
    		}else{
    			$("#confirmModal").modal("show");
    			
    			$("#confirmId").one('click',function(){
    					$("#confirmModal").modal("hide");
    					var idList = new Array();
    					for(var i = 0;i<selections.length;i++){
    						idList.push(selections[i].id);
    					}
    					$.ajax({
    						type:"post",
    						url:"../packaging/delete",
    						data:JSON.stringify(idList),
    						contentType : 'application/json;charset=utf-8',
    						beforeSend:function(){
    							$("#alertModal .modal-body").text("处理中，请稍后。。。");
    							$("#alertModal").modal("show");
    						},
    						success:function(data){
    							$("#alertModal .modal-body").text(data['message']);
    							$("#packagingTable").bootstrapTable('refresh');
    						}
    					});
    			});
    		} 
    });

  //Ajax get packaging lot number
    $("#packageSpeciesSelectID").change(function(){
    	if($(this).val() == 0){
    		$("#packageLotNumber").val(null);
    		return;
    	}
    	$.ajax({
			type:"get",
			url:"../packaging/generateLotNumber?speciesID="+$(this).val(),
			contentType : 'application/json;charset=utf-8',
			success:function(data){
				$("#packageLotNumber").val(data['lotNumber']);
			}
    	// 添加 失败提示信息 "无法获取批号，请稍后再试"
		});
    });
});