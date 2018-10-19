//page title
document.title="产品管理";
$(function() {
    $(".nav-pills > li:nth-child(1)").addClass("active");
    
    $.fn.datetimepicker.defaults = {
    		//default language: Chinese
            language: 'zh-CN',
            clearBtn: true,
            //default date format
            format: "yyyy-mm-dd",
            autoclose: true,
            todayBtn: true,
            startView: 2,
            minView: 2,
            pickerPosition: "bottom-left"
        };
    
    $('#datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd'
    });
    
    //load table data and search function
    function BstpProductionsTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../productions/getProductionsData',
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
                    fileName: 'productions management',  //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'productions management',  
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
                          {field: 'productionLotNumber',title: '产品批号',halign:'center'},
                          {field: 'seed.species.speciesNameCn',title: '作物中文名',halign:'center'},
                          {field: 'variety.commercialName',title: '商品名',halign:'center'},
                          {field: 'packages.packingUnit.specifications',title: '包装物规格',halign:'center',align:'left',formatter:function(value,row,index){
                        	  console.log(row);
                        	  console.log(value);
                        	  if (row.packages.packingUnit.sample == 1) {
									return  row.packages.lotNumber + "-"
											+ row.packages.packingUnit.specificationName + "-"
											+ row.packages.packingUnit.weight
											+ row.packages.packingUnit.unit + "-"
											+ row.packages.packingUnit.specifications + "-"
											+ "样品";
										}
                        	  if (row.packages.packingUnit.sample == 0) {
									return  row.packages.lotNumber + "-"
											+ row.packages.packingUnit.specificationName + "-"
											+ row.packages.packingUnit.weight
											+ row.packages.packingUnit.unit + "-"
											+ row.packages.packingUnit.specifications; + "-"
										}
                          }},
                          {field: 'quantity',title: '数量',halign:'center',align:'right'},
                          {field: 'surplusQuantity',title: '剩余数量',halign:'center',align:'right'},
                          {field: 'isSample',title: '是否样品',halign:'center',formatter:function(value){
                        	  if(value == 0){
                        		  return "否";
                        	  }
                        	  if(value == 1){
                        		  return "是";
                        	  }
                          
                          }},
                          {field: 'storageDay',title: '入库时间',halign:'center',formatter: dateFormatter2},
                          {field: 'startDay',title: '包装开始时间',halign:'center',formatter: dateFormatter2},
                          {field: 'endDay',title: '包装结束时间',halign:'center',formatter: dateFormatter2},
                          {field: 'operate',
                              title: '操作',
                              align: 'center',
                              events: operateEvents,
                              formatter: operateFormatter}
                         ]
                
			});
		}
	};
	
	  $("#ProductionsSearchBtn").click(function(){
	    	console.log($("#searchProductions select[name='conmercial_name'] option:selected").val());
	        var searchArgs={
	        	 conmercialName:$("#searchProductions input[name='conmercialName']").val(),
	             'packingUnit.id':$("#searchProductions select[name='packingUnit.id'] option:selected").val()
	        };
	        bstpTable.init(searchArgs)
	        return false;
	    });
	
	var bstpTable=new BstpProductionsTable($("#productionsTable"));
	var ffData = new FormData();
    bstpTable.init(
    	function operateFormatter(value, row, index) {
	        return [
//	            '<a class="exportTxt btn btn-success" href="javascript:void(0)" style="marging-right:10px" title="export txt">',
//	            '导出txt数据</a>',
	            '<a class="delivery btn btn-warning" href="javascript:void(0)" title="出库">',
	            '出库</a>  '
	        ].join('');
	    },
		window.operateEvents = {
	    		'click .delivery': function (e, value, row, index) {
//	    			if(row.surplusAmount == 0 || row.surplusAmount == "0"){
//	    				$("#alertModal .modal-body").text("剩余数量为0！");
//        				$("#alertModal").modal("show");
//        				return;
//	    			}
	    			
	    	        if (row.isSample == 1) {
	    	        	$('#orderFileForm').hide();
	    	        } else {
	    	        	$('#orderFileForm').show();
	    	        }
	    			console.log("row:");
	    			console.log(row);
//	    			var seedInfo = row.seed.variety.species.nameCn + "--" + row.seed.variety.species.nameEn + "--" + row.seed.variety.conmercialName + "--" + row.packages.packingUnit.specifications;
//	    			$("#deliveryProductionsForm input[name='seedInfo']").val(seedInfo);
//	    			$("#deliveryProductionsForm input[name='importNumber']").val(row.seed.importNumber);
//	    			$("#deliveryProductionsForm input[name='importName']").val(row.seed.importName.name);
//	    			$("#deliveryProductionsForm input[name='storageDay']").val(dateFormatter(row.storageDay));
	    			console.log("isSample:" + row.isSample);
	    			
	    			ffData.append("isSample",row.isSample);
	    			ffData.append("storageId",row.id);
	    			
	    			console.log('row.id:' + row.id);
	    			console.log(row);
	    			console.log(row.seed.species.id);
	    			$.ajax({
	    				type : "get",
	    				url : "../data/generateLotNumber?seedID="+row.seed.id,
	    				success : function(data) {
	    					$("#deliveryProductionsForm input[name='lotNumber']").val(data['lotNumber']);
	    					$("#deliveryProductionsModal label.error").html("");
	    					$("#deliveryProductionsModal .modal-header h4").html("产品出库");
	    					$("#deliveryProductionsModal").modal("show");
	    				}
	    			});
	    			
	    		},
	    		'click .exportTxt': function (e, value, row, index) {
	    			console.log(row);
	    			console.log(row.id);
	    			window.open("../productions/exportTxt?id="+row.id, '_self');
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
    		outStorageDay:{
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
    		},
    		saleManager:{
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
    			required:'请选择出库时间！'
    		},
    		outStorageDay:{
    			required:'请选择发货时间！'
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
    		},
    		saleManager:{
    			required:'请输入销售经理信息！'
    		}
    	},
    	submitHandler:function(form){
//    		if(row.isSample == 0){
//    			ffData.append("uploadFile",$("#orderFile")[0].files[0]);
//    		}
    		if($("#orderFile")[0]){
    			ffData.append("uploadFile",$("#orderFile")[0].files[0]);
    		}
    		ffData.append("lotNumber",$("#deliveryProductionsForm input[name='lotNumber']").val());
    		ffData.append("oddNumbers",$("#deliveryProductionsForm input[name='oddNumbers']").val());
    		ffData.append("deliveryAmount",$("#deliveryProductionsForm input[name='deliveryAmount']").val());
    		ffData.append("deliveryTime",$("#deliveryProductionsForm input[name='deliveryTime']").val());
    		ffData.append("outStorageDay",$("#deliveryProductionsForm input[name='outStorageDay']").val());
    		ffData.append("logisticId",$("#deliveryProductionsForm select[name='logisticCompany.id']").val());
    		ffData.append("customerId",$("#deliveryProductionsForm select[name='customer.id']").val());
    		ffData.append("saleManagerId",$("#deliveryProductionsForm input[name='saleManager']").val());
    		console.log("hello hello:" + $("#deliveryProductionsForm input[name='oddNumbers']").val());
    		
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
        				$("#alertModal").css("width:500px");
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
        				$("#deliveryProductionsForm select[name='customer.id']").selectpicker('val',0);
        		 	    $("#deliveryProductionsForm select[name='customer.id']").selectpicker('refersh');
        				$("#productionsTable").bootstrapTable("refresh");
        			}
        		});
    		
    		return false;
    	}
    });
    
    function operateFormatter(value, row, index) {
        return [
//                '<a class="exportTxt btn btn-success" href="javascript:void(0)" style="margin-right:10px" title="export txt">',
//                '导出txt数据</a>',
                '<a class="delivery btn btn-warning" href="javascript:void(0)" title="出库">',
                '出库</a>  '
        ].join('');
    }
    
  
    
    
    //add productions
    var addOrEdit;
    $("#addProductions").click(function(){
    	selections = $("#productionsTable").bootstrapTable('getAllSelections');
    	console.log(selections);
    	addOrEdit = "add";
    	$("#addProductionsModal label.error").html("");
    	$("#addProductionsModal .modal-header h4").html("产品入库");
    	$("#addProductionsModal").modal('show');
    	$('#addProductionsForm')[0].reset();
    	$("#addProductionsForm select[name='packages.id']").selectpicker("refresh");
		$("#addProductionsForm select[name='seed.id']").selectpicker("refresh");
		$("#addProductionsForm select[name='variety.id']").selectpicker("refresh");
		
    	return false;
    });
    
    
    //edit productions
    $("#editProductions").click(function(){
    	selections = $("#productionsTable").bootstrapTable('getAllSelections');
    	console.log(selections);
    	if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			addOrEdit = "edit";
			$("#addProductionsForm input[name='id']").val(selections[0].id);
			$("#addProductionsForm select[name='seed.id']").val(selections[0].seed.id);
			$("#addProductionsForm select[name='packages.id']").val(selections[0].packages.id);
			$("#addProductionsForm select[name='variety.id']").val(selections[0].variety.id);
			$("#addProductionsForm input[name='quantity']").val(selections[0].quantity);
//			$("#addProductionsForm input[name='planAmount']").attr("readonly","readonly");
			$("#addProductionsForm input[name='storageDay']").val(dateFormatter2(selections[0].storageDay));
//			$("#addProductionsForm input[name='storageDay']").val(dateFormatter(selections[0].storageDay));
//			$("#addProductionsForm select[name='seed.id']").attr("disabled","disabled");
			
			$("#addProductionsModal label.error").html("");
			$("#addProductionsModal .modal-header h4").html("编辑产品");
			$("#addProductionsModal").modal("show");
		}
    });
    
    //update productions
    $("#updateProductions").click(function(){
    	selections = $("#productionsTable").bootstrapTable('getAllSelections');
		console.log(selections);
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			$("#updateProductionsForm input[name='id']").val(selections[0].id);
			$("#updateProductionsForm input[name='planAmount']").val(selections[0].planAmount);
			$("#updateProductionsForm input[name='currentAmount']").val(selections[0].currentAmount);
			$("#updateProductionsForm select[name='seed.id']").val(selections[0].seed.id);
			$("#updateProductionsForm select[name='packingUnit.id']").val(selections[0].packingUnit.id);
			$("#updateProductionsForm input[name='storageDay']").val(dateFormatter(selections[0].storageDay));
			
			$("#updateProductionsModal label.error").html("");
			$("#updateProductionsModal .modal-header h4").html("更新实际数量");
			$("#updateProductionsModal").modal("show");
			var params = $("#updateProductionsForm").serialize();
			console.log("paramsaa:"+params);
		}
    	return false;
    });
    
    //add or edit productions
    var storageData = new FormData();
    $("#addProductionsForm").validate({
    	rules:{
    		storageFile:{
    			required:true
    		},
    		'seed.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		'variety.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
    		'packages.id':{
    			required:true,
    			notEmpty:/^$/,
    			notZero:0
    		},
//    		packagingAmount:{
//    			required:true,
//    			positive:/^[0-9]+$/,
//    			min:1,
//    			number:true
//    		},
    		quantity:{
    			required:true,
    			notEmpty:/^$/,
    			number:true,
    			min:1,
    			positive:/^[0-9]+$/
//    			equalPackagingAmount:$("#addProductionsForm input[name='packagingAmount']")
    		},
    		storageDay:{
    			required:true
    		},
    		startDay:{
    			required:true
    		},
    		endDay:{
    			bigThanStart:$("#addProductionsForm input[name='startDay']").val()
    		}
    	},
    	messages:{
    		storageFile:{
    			required:"请选择入库文件！"
    		},
    		'seed.id':{
    			required:'请选择种子信息！'
    		},
    		'variety.id':{
    			required:'请选择品种信息！'
    		},
    		'packages.id':{
    			required:'请选择包装物信息！'
    		},
    		packagingAmount:{
    			required:'请输入包装数量！',
    			positive:'包装数量只能为正整数！',
    			min:'请输入大于0的数！'
    		},
    		quantity:{
    			required:'请输入入库数量！',
    			positive:'只能输入正整数！',
    			notEmpty:'请输入入库数量！'
    		},
    		storageDay:{
    			required:'请选择入库时间！'
    		},
    		startDay:{
    			required:'请选择包装开始时间！'
    		},
    		endDay:{
    			bigThanStart:'包装结束时间小于开始时间！'
    		}
    	},
    	submitHandler:function(form){
    			
    			var speciesIdList = new Array(),sIdInPackage = new Array() ;
    			$("#addProductionsForm select[name='seed.id']").each(function(i){
		  			console.log("speciesId=" + $(this).find("option:selected").attr("class"));
		  			speciesIdList[i] = $(this).find("option:selected").attr("class");
		  		});
    			
    			
    			$("#addProductionsForm select[name='packages.id']").each(function(i){
    				console.log("sIdInPackage=" + $(this).find("option:selected").attr("class"));
    				sIdInPackage[i] = $(this).find("option:selected").attr("class");
  		  		});
    			
//    			console.log("vIdInPackage[0]:" + vIdInPackage[0]);
//    			console.log("vIdInPackage[1]:" + vIdInPackage[1]);
    			//varietyIdList
    			console.log("speciesIdList.length:" + speciesIdList.length);
    			if(speciesIdList.length >1){
    				for(var i = 1;i<speciesIdList.length;i++){
    					if (parseInt(speciesIdList[i]) != parseInt(speciesIdList[i - 1])){
    						$("#alertModal .modal-body").text("请选择相同作物种子！");
    						$("#alertModal").modal("show");
//    						$("#addProductionsModal").modal('hide');
    						return ;
    					}
    				}
    			}
    			
    			console.log("sIdInPackage:" + sIdInPackage);
    			if(sIdInPackage.length >1){
    				for(var i = 1;i<sIdInPackage.length;i++){
    					if (parseInt(sIdInPackage[i]) != parseInt(sIdInPackage[i-1])){
    						$("#alertModal .modal-body").text("请选择相同作物包装物！");
    						$("#alertModal").modal("show");
//    						$("#addProductionsModal").modal('hide');
    						return ;
    					}
    				}
    			}
    			
      		  	if(addOrEdit == "add"){
      		  		$("#addProductionsForm input[name='id']").val(null);
      		  		var params = $("#addProductionsForm").serialize();
      		  		if ($('#isSample').is(':checked')) {
      		  			storageData.append("isSample",1);
      		  		} else {
      		  			storageData.append("isSample",0);
      		  			storageData.append("uploadFile",$("#storageFile")[0].files[0]);
      		  		}
      		  		storageData.append("quantity",$("#addProductionsForm input[name='quantity']").val());
      		  		storageData.append("storageDay",$("#addProductionsForm input[name='storageDay']").val());
      		  		storageData.append("startDay",$("#addProductionsForm input[name='startDay']").val());
      		  		storageData.append("endDay",$("#addProductionsForm input[name='endDay']").val());
      		  		
      		  		var packagesIdList = new Array(),seedIdList = new Array();
      		  		var packagingAmountList = new Array;
      		  		$("#addProductionsForm select[name='seed.id']").each(function(i){
//      		  		console.log("seed="+$(this).val());
      		  			seedIdList[i] = $(this).val();
      		  		});
      		  		
      		  		$("#addProductionsForm select[name='packages.id']").each(function(i){
      		  			packagesIdList[i] = $(this).val();
      		  		});
      		  		
      		  		
      		  		storageData.append("varietyId",$("#addProductionsForm select[name='variety.id']").val());
      		  		storageData.append("seedIdList",seedIdList);
      		  		storageData.append("packagesIdList",packagesIdList);
      		  		storageData.append("productionLotNumber",$("#addProductionsForm input[name='productionLotNumber']").val());
      		  	$.ajax({
        			type : "post",
        			url : "../productions/add",
        			data:storageData,
        			processData:false,
        			scriptCharset:'utf-8',
        			contentType:false,
        			beforeSend:function(){
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        				$("#addProductionsModal").modal('hide');
        			},
        			success : function(data) {
        				$("#alertModal .modal-dialog").css("width","500px");
        				$("#alertModal .modal-body").text(data['message']);
        				$("#alertModal").modal("show");
        				storageData = new FormData();
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
        				$('#addProductionsForm')[0].reset();
        				$("#productionsTable").bootstrapTable("refresh");
        				$('#storageFileDiv').show();
        				$('.isSample').attr("checked",false);
        				$('.isSample').removeAttr("checked");
        			}
        		});
      		  		
      		  	}
      		  	
      		  	if(addOrEdit == "edit"){
      		  		var params = $("#addProductionsForm").serialize();
      		  		console.log(params);
      		  		$.ajax({
      		  			type : "post",
      		  			url : "../productions/edit",
      		  			data : params,
      		  			beforeSend:function(){
      		  				$("#alertModal .modal-body").text("处理中，请稍后。。。");
      		  				$("#alertModal").modal("show");
      		  				$("#addProductionsModal").modal('hide');
      		  			},
      		  			success : function(data) {
      		  				$("#alertModal .modal-body").text(data['message']);
      		  				$("#productionsTable").bootstrapTable("refresh");
      		  				$('#addProductionsForm')[0].reset();
      		  				$("#addProductionsForm input[name='planAmount']").attr("readonly",false);
      		  			}
      		  		});
      		  	}
        	
      		  	return false;
    		}
    });
    
    $("#updateProductionsForm").validate({
    	rules:{
    		currentAmount:{
    			required:true,
    			notEmpty:/^$/,
    			number:true,
    			positive:/^[0-9]+$/,
    			min:1,
    			smallThanPlanAmount2:$("#updateProductionsForm input[name='planAmount']").val()
    		},
    		storageDay:{
    			required:true,
    			notEmpty:/^$/
    		}
    	},
    	messages:{
    		currentAmount:{
    			required:'请输入实际数量！',
    		    min:'请输入大于0的数！'
    		},
    		storageDay:{
    			required:'请选择入库日期！'
    		}	
    	},
    	submitHandler:function(form){
    		var params = $("#updateProductionsForm").serialize();
			console.log("params:"+params);
			$.ajax({
				type:"post",
				url:"../productions/update",
				data:params,
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    				$("#updateProductionsModal").modal('hide');
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$('#updateProductionsForm')[0].reset();
					$("#productionsTable").bootstrapTable('refresh');
				}
			});
    		
    		return false;
    	}
    });
    
    //when close model,reset form content
    $("#cancelAddProductionsBtn").click(function(){
    	$('#addProductionsForm')[0].reset();
    	$("#addProductionsForm input[name='planAmount']").attr("readonly",false);
    	$('#storageFileDiv').show();
		$('.isSample').removeAttr("checked");
    });
    $("#cancelEditProductionsBtn").click(function(){
    	$('#updateProductionsForm')[0].reset();
    	$("#addProductionsForm input[name='planAmount']").attr("readonly",false);
    });
    $("#cancelDeliveryBtn").click(function(){
//    	document.getElementById("deliverCustomerId").options.selectedIndex = 0; //默认未选择品种信息 
    	$("#deliveryProductionsForm select[name='customer.id']").selectpicker('val',0);
 	    $("#deliveryProductionsForm select[name='customer.id']").selectpicker('refersh');
    	$('#deliveryProductionsForm')[0].reset();
    });
    $(".close").click(function(){
    	$('#addProductionsForm')[0].reset();
    	$('#updateProductionsForm')[0].reset();
    	$('#deliveryProductionsForm')[0].reset();
    	$("#addProductionsForm input[name='planAmount']").attr("readonly",false);
//    	document.getElementById("deliverCustomerId").options.selectedIndex = 0; //默认未选择品种信息
    	 $("#deliveryProductionsForm select[name='customer.id']").selectpicker('val',0);
 	    $("#deliveryProductionsForm select[name='customer.id']").selectpicker('refersh');
    	$('#storageFileDiv').show();
    	$('.isSample').removeAttr("checked");
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
	
    $("#deleteProductions").click(function(){
    	 var selections = $("#productionsTable").bootstrapTable('getAllSelections');
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
    						url:"../productions/delete",
    						data:JSON.stringify(idList),
    						contentType : 'application/json;charset=utf-8',
    						beforeSend:function(){
    							$("#alertModal .modal-body").text("处理中，请稍后。。。");
    							$("#alertModal").modal("show");
    						},
    						success:function(data){
    							$("#alertModal .modal-body").text(data['message']);
    							$("#productionsTable").bootstrapTable('refresh');
    						}
    					});
    			});
    		} 
    });

});