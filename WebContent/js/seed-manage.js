document.title="种子管理";
$(function(){
	
//	$.fn.datetimepicker.defaults = {
//			//default language: Chinese
//            language: 'zh-CN',
//            clearBtn: true,
//            //default date format
//            format: "yyyy-mm-dd",
//            autoclose: true,
//            todayBtn: true,
//            startView: 2,
//            minView: 2,
//            defaultDate:getNowFormatDate(),
//            pickerPosition: "bottom-left"
//        };
    
//    $('#datetimepicker').datetimepicker({
//        format: 'yyyy-mm-dd'
//    });
    
  //Get current time，date formatter :YYYY-MM-DD
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
	
  //load table data and search function
	function BstpSeedTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../seed/getSeedData',
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
                    ignoreColumn: [0,7],  //Ignore exporting the columns to file
                    fileName: 'seed management',  //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'seed management',  
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
//                          {field: 'species.nameEn',title: '作物英文名',halign:'center'},
                          {field: 'lotNumber',title: '种子批号',halign:'center'},
                          {field: 'species.speciesNameCn',title: '作物中文名',halign:'center'},
//                          {field: 'variety.commercialName',title: '商品名',halign:'center'},
                          {field: 'importNumber',title: '进口编号',halign:'center'},
                          {field: 'importName',title: '进口中文名',halign:'center'},
                          {field: 'weight',title: '重量(KG)',halign:'center',align:'right',formatter:function(value){
                        	  return value.toFixed(3).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');}},
                          {field: 'surplusWeight',title: '剩余重量(KG)',halign:'center',align:'right',formatter:function(value){
                        	  return value.toFixed(3).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
                        	  //a.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
                          }},
                          {field: 'thousandGrainWeight',title: '千粒重(G)',halign:'center',align:'right',formatter:function(value){
                        	  if(value == 0 || value == "0"){
                        		  return value;
                        	  }
                        	  if(value != null && value != $.trim("")){
                        		  return value.toFixed(3).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
                        	  }
                        	  return "";
                          }},
                          {field: 'purchaseDay',title: '购入时间',halign:'center',formatter: dateFormatter2}
                         ]
                
//                种子批号(Auto Numbering)=  Right(Year,1) &作物略号&序数(4 digit sequence code)
//                购入时间(Purchase Date)
//                供应商 (Supplier)
//                进口商 (Importer)
//
//                作物略号/作物名称
//                品种略号/品种名称
//
//                进口中文名(Import Name)
//                商品名称(Commercial Name)
//                原来的品种名称(Original Name)                    
//
//                生产商批号 Original Lot
//                原产地(Origin)
//                发芽率(Germination)
//                检疫审批号 (Phyto No)
//                进口编号(Import No)?????????
//                进口审批号(Import permission No)
//                测试时间(Inspection date)
//                入库时间(Storage Date)

                
			});
		}
	};
	
	
	var bstpTable=new BstpSeedTable($("#seedTable"));
    bstpTable.init({});
    
    $("#seedSearchBtn").click(function(){
        var searchArgs={
        		speciesName:$("#searchSeed input[name='speciesName']").val(),
        		importNumber:$("#searchSeed input[name='importNumber']").val()
        };
        bstpTable.init(searchArgs)
        return false;
    });
    
    
//   function operateFormatter(value,row,index){
//	   return [
//	           '<button type="button" class="btn btn-warning">出库</button>',
//	           '<button type="button" class="btn btn-success" style="margin-left:20px">导出txt数据</button>'
//	           ].join('');
//   }
   
//   function getImportNameBySpecies(){
//		  console.log($("#addSeedForm select[name='species.id'] option:selected").val());
//		  var sel2 = $("#addSeedForm select[name='importName.id']");
//		  sel2.empty();
//		  if($("#addSeedForm select[name='species.id'] option:selected").val() == "0"){
//			  sel2.append("<option value='0'> -- </option>");
//			  return;
//		  }
//		  $.ajax({
//				type : "get",
//				url : "../seed/getImportName?speciesId="+$("#addSeedForm select[name='species.id'] option:selected").val(),
//				success : function(data) {
//					console.log(data);
//					sel2.append("<option value=\"" + data[0].id + "\" selected>" + data[0].name+"</option>");
//					for(var i = 1; i< data.length;i++){
//						var col = data[i];
//						sel2.append("<option value=" + data[i].id + ">" + data[i].name+"</option>");
//					}
//				}
//			});
//   }
//   $("#speciesSelect").change(getImportNameBySpecies);
   
   //add seed function
   var addOrEdit;
   $("#addSeed").click(function(){
	   addOrEdit = "add";
	   $('#addSeedForm')[0].reset();
	   $("#addSeedModal label.error").html("");
	   $("#addSeedModal .modal-header h4").html("种子入库");
	   document.getElementById("seedSpeciesSelect").options.selectedIndex = 0; //默认未选择品种信息 
	   $("#addSeedForm select[name='species.id']").selectpicker('refresh');
	   $("#addSeedModal").modal("show");
	   return false;
   });
   
   $("#addSeedForm input[name='thousandGrainWeight']").focus(function(){
	  if($(this).val().indexOf(",")){
		  $(this).val().replace(/,/gi,'');
	  } 
   });
   
   
 //Ajax get packaging lot number
   $("#seedSpeciesSelect").change(function(){
   	if($(this).val() == 0){
   		$("#seedLotNumber").val(null);
   		return;
   	}
   	$.ajax({
			type:"get",
			url:"../seed/generateLotNumber?speciesID="+$(this).val(),
//			data:JSON.stringify(idList),
			contentType : 'application/json;charset=utf-8',
			success:function(data){
				$("#seedLotNumber").val(data['lotNumber']);
			}
   	// 添加 失败提示信息 "无法获取批号，请稍后再试"
		});
   	
   });
   
   //edit seed function
   $("#editSeed").click(function(){
	   	selections = $("#seedTable").bootstrapTable('getAllSelections');
	   	console.log(selections); 
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			addOrEdit = "edit";
			
			$("#addSeedForm input[name='id']").val(selections[0].id);
			$("#addSeedForm input[name='lotNumber']").val(selections[0].lotNumber);
			selections[0].species == null?$("#addSeedForm select[name='species.id']").val(0):$("#addSeedForm select[name='species.id']").val(selections[0].species.id);
			document.getElementById("seedSpeciesSelect").options.selectedIndex = selections[0].species.id; //回到初始状态
			$("#seedSpeciesSelect").selectpicker('refresh');
			$('#seedSpeciesSelect').selectpicker('val',selections[0].species.id);
//			$("#addSeedForm select[name='variety.id']").val(selections[0].variety.id);
			$("#addSeedForm input[name='weight']").val(selections[0].weight);
			$("#addSeedForm input[name='thousandGrainWeight']").val(selections[0].thousandGrainWeight);
			$("#addSeedForm input[name='importNumber']").val(selections[0].importNumber);
			$("#addSeedForm input[name='importName']").val(selections[0].importName);
//			$("#addSeedForm select[name='importName.id']").val(selections[0].importName.id);
			$("#addSeedForm input[name='storageDay']").val(dateFormatter2(selections[0].storageDay));
			$("#addSeedForm input[name='purchaseDay']").val(dateFormatter2(selections[0].purchaseDay));
			$("#addSeedForm input[name='originalLot']").val(selections[0].originalLot);
			$("#addSeedForm input[name='original']").val(selections[0].original);
			$("#addSeedForm input[name='germination']").val(selections[0].germination);
			$("#addSeedForm input[name='testTime']").val(dateFormatter2(selections[0].testTime));
			$("#addSeedForm input[name='importPermitNo']").val(selections[0].importPermitNo);
			$("#addSeedForm input[name='phytoNo']").val(selections[0].phytoNo);
			
			selections[0].importer == null?$("#addSeedForm select[name='importer.id']").val(0):$("#addSeedForm select[name='importer.id']").val(selections[0].importer.id);
//			if(selections[0].importer == null){
//				$("#addSeedForm select[name='importer.id']").val(null);
//			}else{
//				$("#addSeedForm select[name='importer.id']").val(selections[0].importer.id);
//			}
			selections[0].supplier == null?$("#addSeedForm select[name='supplier.id']").val(0):$("#addSeedForm select[name='supplier.id']").val(selections[0].supplier.id);
//			if(selections[0].supplier == null){
//				$("#addSeedForm select[name='supplier.id']").val(null);
//			}else{
//				$("#addSeedForm select[name='supplier.id']").val(selections[0].supplier.id);
//			}
			
			$("#addSeedModal label.error").html("");
			$("#addSeedModal .modal-header h4").html("编辑种子信息");
			$("#addSeedModal").modal("show");
		}
	   return false;
   });
   
   //update surplus weight  #updateSurplusWeight
   $("#updateSurplusWeight").click(function(){
	   	selections = $("#seedTable").bootstrapTable('getAllSelections');
	   	console.log(selections); 
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			$("#updateSeedForm input[name='id']").val(selections[0].id);
			$("#updateSeedForm input[name='surplusWeight']").val(selections[0].surplusWeight);
			
			$("#updateSeedModal label.error").html("");
			$("#updateSeedModal .modal-header h4").html("更新剩余重量");
			$("#updateSeedModal").modal("show");
		}
	   return false;
  });
   
   
   //add or edit seed information
   $("#addSeedForm").validate({
	  rules:{
		  'species.id':{
			  required:true,
			  notZero:0
		  },
		  weight:{
			  notEmpty:/^$/,
//			  positive:/^[1-9][0-9]*$/,
			  decimalNum:/^0|^(([0-9]+.[0-9]*[1-9][0-9]*)|^([0-9]*[1-9][0-9]*.[0-9]+)|^([0-9]*[1-9][0-9]*))$/
		  },
		  thousandGrainWeight:{
//			  required:true,
//			  notEmpty:/^$/,
//			  positive:/^[0-9]*.*[0-9]{0,n}$/,  // /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/
			  decimalNum:/^|^0|^(([0-9]+.[0-9]*[1-9][0-9]*)|^([0-9]*[1-9][0-9]*.[0-9]+)|^([0-9]*[1-9][0-9]*))$/
		  },
		  purchaseDay:{
			  required:true
		  },
		  originalLot:{
			  required:true
		  },
		  "importer.id":{
			  required:true,
			  notEmpty:/^$/,
			  notZero:0
		  },
		  "supplier.id":{
			  required:true,
			  notEmpty:/^$/,
			  notZero:0
		  }
	  },
	  messages:{
		  'species.id':{
			  required:'请选择作物信息！'
		  },
		  weight:{
		  },
		  purchaseDay:{
			  required:"请选择购入时间！"
		  },
		  originalLot:{
			  required:"请输入原始批号"
		  },
		  "importer.id":{
			  required:"请选择进口商！"
		  },
		  "supplier.id":{
			  required:"请选择供应！"
		  }
	  },
	  submitHandler:function(form){
		  var thousandGrainWeight = $("#addSeedForm input[name='thousandGrainWeight']").val();
		  if(thousandGrainWeight != null && thousandGrainWeight != $.trim("")){
			  thousandGrainWeight = parseFloat(thousandGrainWeight.replace(/[^\d\.-]/g, ""));
			  $("#addSeedForm input[name='thousandGrainWeight']").val(thousandGrainWeight);
		  }else{
			  $("#addSeedForm input[name='thousandGrainWeight']").val(null);
		  }
		  var params = $("#addSeedForm").serialize();
		  
		  console.log("params:"+params);
		  if($("#addSeedForm select[name='species.id'] option:selected").val() == "0"){
			  $("#alertModal .modal-body").text("请选择作物！");
			  $("#alertModal").modal("show");
			  return;
		  }
		  if($("#addSeedForm select[name='importName.id'] option:selected").val() == "0"){
			  $("#alertModal .modal-body").text("请选择进口名！");
			  $("#alertModal").modal("show");
			  return;
		  }
		  if(addOrEdit === "add"){
			  $("#addSeedForm input[name='id']").val(null);
			  var params = $("#addSeedForm").serialize();
			  params = params+"&surplusWeight="+$("#addSeedForm input[name='weight']").val();
	  		  $.ajax({
	  			type : "post",
	  			url : "../seed/add",
	  			data : params,
	  			beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    				$("#addSeedModal").modal('hide');
    			},
	  			success : function(data) {
	  				$("#seedTable").bootstrapTable("refresh");
	  				$("#alertModal .modal-body").text(data['message']);
	  				$('#addSeedForm')[0].reset();
	  			}
	  		});
		  }
		  
		  if(addOrEdit === "edit"){
			  var params = $("#addSeedForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../seed/edit",
					data:params,
					beforeSend:function(){
	    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
	    				$("#alertModal").modal("show");
	    				$("#addSeedModal").modal('hide');
	    			},
					success:function(data){
						$('#addSeedForm')[0].reset();
						$("#alertModal .modal-body").text(data['message']);
						$("#seedTable").bootstrapTable('refresh');
						$('#addSeedForm')[0].reset();
					}
				});
		  }
		   return false;
	  }
   });
   
   //add or edit seed information
	   $("#updateSeedForm").validate({
		rules : {
			surplusWeight : {
				notEmpty : /^$/,
//				decimalNum:/^0|(-?([0-9]+.[0-9]*[1-9][0-9]*)|-?([0-9]*[1-9][0-9]*.[0-9]+)|-?([0-9]*[1-9][0-9]*))$/
				decimalNum2:/^(-?\d+)(\.\d+)?$/
			}
		},
		messages : {
			surplusWeight : {
				notEmpty : '重量不能为空！',
			}
		},
		submitHandler : function(form) {
			var params = $("#addSeedForm").serialize();
			console.log("params:" + params);

			var params = $("#updateSeedForm").serialize();
			console.log("params:" + params);
			$.ajax({
				type : "post",
				url : "../seed/update",
				data : params,
				beforeSend : function() {
					$("#alertModal .modal-body").text("处理中，请稍后。。。");
					$("#alertModal").modal("show");
					$("#updateSeedModal").modal('hide');
				},
				success : function(data) {
					$('#updateSeedForm')[0].reset();
					$("#alertModal .modal-body").text(data['message']);
					$("#seedTable").bootstrapTable('refresh');
					$('#updateSeedForm')[0].reset();
				}
			});
			return false;
		}
	});
   
   
   
   //when close model,reset form content
	$(".close").click(function(){
		$("#addSeedForm")[0].reset();
		$("#addSeedForm select[name='species.id']").val(0)
		$("#updateSeedForm")[0].reset();
	});
	
	$(".cancelSubmit").click(function(){
		$("#addSeedForm")[0].reset();
		$("#addSeedForm select[name='species.id']").val(0)
		$("#updateSeedForm")[0].reset();
	});
   
   //delete function
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
	
	$("#closeId").bind('click',function(){
		$("#confirmModal").modal("hide");
	});
	
	$("#deleteSeed").click(function(){
		var selections = $("#seedTable").bootstrapTable('getAllSelections');
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
   						url:"../seed/delete",
   						data:JSON.stringify(idList),
   						contentType : 'application/json;charset=utf-8',
   						beforeSend:function(){
   							$("#alertModal .modal-body").text("处理中，请稍后。。。");
   							$("#alertModal").modal("show");
   						},
   						success:function(data){
   							$("#alertModal .modal-body").text(data['message']);
   							$("#seedTable").bootstrapTable('refresh');
   						}
   					});
   			});
   		} 
   	});
	
});