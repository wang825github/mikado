document.title = "订单管理";
$(function() {
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
	function BstpDataManageTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getDataManageData',
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
                    fileName: 'data management',   //the file name 
                    worksheetName: 'sheet1', //file work sheet name 
                    tableName: 'data management',  
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
                          {field: 'storage.variety.species.speciesNameEn',title: '作物英文名',halign:'center'},
                          {field: 'storage.variety.species.speciesNameCn',title: '作物中文名',halign:'center'},
                          {field: 'storage.variety.commercialName',title: '商品名',halign:'center'},
                          {field: 'lotNumbers',title: '订单批号',halign:'center'},
                          {field: 'storage.productionLotNumber',title: '产品批号',halign:'center'},
                          {field: 'isSample',title: '是否样品',halign:'center',formatter:function(value){
                        	  if(value == 0){
                        		  return "否";
                        	  }
                        	  if(value == 1){
                        		  return "是";
                        	  }
                          }},
                          {field: 'storage.packages.packingUnit.specifications',title: '包装规格',halign:'center',align:'left'},
                          {field: 'deliveryAmount',title: '数量',halign:'center',align:'right'},
                          {field: 'customer.company',title: '客户名称',halign:'center',align:'left',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return "";
                        	  }
                        	  return value;
                          }},
                          {field: 'customer.simpleName',title: '公司简称',halign:'center'},
//                          {field: 'customer.city.nameCn',title: '发货城市',halign:'center'},
                          {field: 'customer.city.nameCn',title: '目的地',halign:'center'},
                          {field: 'logisticCompany.name',title: '物流公司',halign:'center'},
                          {field: 'oddNumbers',title: '物流单号',halign:'center',formatter:emptyFormatter},
                          {field: 'outStorageDay',title: '出库日期',halign:'center',formatter: dateFormatter2},
                          {field: 'deliveryTime',title: '发货日期',halign:'center',formatter: dateFormatter2},
                          {field: 'receivingTime',title: '收货日期',halign:'center',formatter: dateFormatter2},
                          {field: 'customer.saleManager.name',title: '销售经理',halign:'center'},
                          {field: 'status',title: '订单状态',halign:'center',formatter:function(value){
                        	  if(value == 0){
                        		  return "发货";
                        	  }
                        	  if(value == 1){
                        		  return "已签收";
                        	  }
                          }}
                         ]
			});
		}
	};
	
	var bstpTable=new BstpDataManageTable($("#dataManageTable"));
    bstpTable.init({});
    
    $("#searchDataManageBtn").click(function(){
        var searchArgs={
        		conmercialName:$("#searchDataManage input[name='conmercialName']").val(),
        		cityId:$("#searchDataManage select[name='cityId'] option:selected").val(),
        		'salesManager.id':$("#searchDataManage select[name='salesManager.id'] option:selected").val()
        };
        bstpTable.init(searchArgs)
        return false;
    });
    
    
    //update order status
    $("#updateDataManage").click(function(){
    	selections = $("#dataManageTable").bootstrapTable('getAllSelections');
		console.log(selections);
		
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条订单状态！");
			$("#alertModal").modal("show");
		}else{
			$("#dataManageForm input[name='id']").val(selections[0].id);
			if(selections[0].status === $.trim("发货")){
				$("#dataManageForm select[name='status']").val(0);
			}
			if(selections[0].status === $.trim("已签收")){
				$("#dataManageForm select[name='status']").val(1);
			}
			$("#dataManageForm input[name='oddNumbers']").val(selections[0].oddNumbers);
			$("#dataManageForm input[name='receivingTime']").val(dateFormatter(selections[0].receivingTime));
			$("#dataManageModal label.error").html("");
			$("#dataManageModal").modal("show");
		}
		
		return false;
    });
    
    $("#dataManageForm").validate({
    	rules:{
    		oddNumbers:{
    			required:true,
    			notEmpty:/^$/
    		},
    		receivingTime:{
    			required:true,
    			notEmpty:/^$/
    		}
    	},
    	messages:{
    		oddNumbers:{
    			required:'请输入物流单号！'
    		},
    		receivingTime:{
    			required:'请选择签收时间！'
    		}
    	},
    	submitHandler:function(form){
    		var params = $("#dataManageForm").serialize();
    		$.ajax({
    			type : "post",
    			url : "../data/updateStatus",
    			data : params,
    			beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    				$("#dataManageModal").modal('hide');
    			},
    			success : function(data) {
    				$("#alertModal .modal-body").text(data['message']);
    				$("#dataManageTable").bootstrapTable("refresh");
    				$('#dataManageForm')[0].reset();
    			}
    		});
    		
    		return false;
    	}
    });  
    
    //edit order information
    $("#editDataManage").click(function(){
    	selections = $("#dataManageTable").bootstrapTable('getAllSelections');
		console.log(selections);
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条订单状态！");
			$("#alertModal").modal("show");
		}else{
			$("#editDataManageForm input[name='id']").val(selections[0].id);
			$("#editDataManageForm input[name='lotNumbers']").val(selections[0].lotNumbers);
			if(selections[0].customer == null){
				$("#editDataManageForm select[name='customer.id']").val(null);
			}else{
				$("#editDataManageForm select[name='customer.id']").val(selections[0].customer.id);
				$("#editDataManageForm select[name='customer.id']").selectpicker('val',selections[0].customer.id);
				$("#editDataManageForm input[name='saleManager']").val(getSaleManagerByCustomerId);
				
			}
			if(selections[0].logisticCompany == null){
				$("#editDataManageForm select[name='logisticCompany.id']").val(null);
			}else{
				$("#editDataManageForm select[name='logisticCompany.id']").val(selections[0].logisticCompany.id);
			}
			
			
			$("#editDataManageModal label.error").html("");
			$("#editDataManageModal").modal("show");
		}
    });
    
    
    function getSaleManagerByCustomerId(){
		  console.log($("#editDataManageForm select[name='customer.id'] option:selected").val());
		  var sel2 = $("#editDataManageForm input[name='saleManager']");
		  sel2.val("");
		  if($("#editDataManageForm select[name='customer.id'] option:selected").val() == "0"){
			  sel2.val("");
			  return;
		  }
		  $.ajax({
				type : "get",
				url : "../productions/getSaleManager?customerId="+$("#editDataManageForm select[name='customer.id'] option:selected").val(),
				success : function(data) {
					sel2.val(data['name']);
				}
			});
  }
    $(".data-manage-customer").change(getSaleManagerByCustomerId);
    
    
    $("#editDataManageForm").validate({
    	rules:{
    		'customer.id':{
    			required:true
    		},
    		'logisticCompany.id':{
    			required:true
    		}
    	},
    	submitHandler:function(form){
    		var params = $("#editDataManageForm").serialize();
    		$.ajax({
    			type : "post",
    			url : "../data/edit",
    			data : params,
    			beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    				$("#editDataManageModal").modal('hide');
    			},
    			success : function(data) {
    				$("#alertModal .modal-body").text(data['message']);
    				$("#dataManageTable").bootstrapTable("refresh");
    				$('#editDataManageForm')[0].reset();
    			}
    		});
    		
    		return false;
    	}
    });  
    
    //when close model,then reset form content
	$(".close").click(function(){
		$("#dataManageForm")[0].reset();
		$("#editDataManageForm")[0].reset();
	});
	$("#cancelDataManageBtn").click(function(){
		$("#dataManageForm")[0].reset();
	});
	$("#cancelEditDataManage").click(function(){
		$("#editDataManageForm")[0].reset();
	});
    
    
	//export excel function
    var exportType = null;
    var saleId,customerId,endTime;
    $("#exportExcelId").change(function(){
    	exportType = $("#exportExcelId option:selected").val();
    	console.log("exportType:"+exportType);
    	if(exportType == "0"){
    			return;
    	}
    	conmercialName = $("#searchDataManage input[name='conmercialName']").val();
    	cityId = $("#searchDataManage select[name='cityId'] option:selected").val();
    	saleId = $("#searchDataManage select[name='salesManager.id'] option:selected").val();
    });
    
    $("#exportExcel").click(function(){
    	if(exportType != 1 && exportType != 2 && exportType != 3){
    		$("#alertModal .modal-body").text("请选择左侧导出类型导出！");
    		$("#alertModal").modal("show");
    		return;
    	}
    	window.open("../data/exportExcel?conmercialName="+conmercialName+"&cityId=" +cityId+"&saleId="+saleId+"&exportType="+exportType, '_self');
    });
    
});