document.title = "数据字典";
$(function() {
	
	//load table data and search function
	//species
	function BstpSpeciesTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getSpeciesData',
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
                sortName:"",
                exportDataType:"basic",
                showExport:true,
                exportTypes:['excel','csv'],
                exportOptions:{  
                    ignoreColumn: [0,7],  //Ignore exporting the columns to file
                    fileName: 'data dictionary species',   //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'data dictionary species',  
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
                          {field: 'group.id',visible:false},
                          {field: 'cropCode',title: '作物略号',halign:'center',sortable:true,order:'asc'},
                          {field: 'no',title: 'No',halign:'center',sortable:true,order:'asc'},
                          {field: 'speciesNameEn',title: 'species',halign:'center'},
                          {field: 'speciesNameCn',title: '作物名',halign:'center'},
                          {field: 'speciesNameCn2',title: '作物名２',halign:'center'},
                          {field: 'group.nameCn',title: '分类',halign:'center'},
                         ]
			});
		}
	};
	
	//packing unit
	function BstpPackingUnitTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getPackingUnitData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary packing unit',   //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'data dictionary packing unit',  
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
                          {field: 'specificationCode',title: '规格编码',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'specificationName',title: '包装物名称',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'unit',title: '单位',halign:'center',align:"center",},
                          {field: 'specifications',title: '规格单位',halign:'center',align:"center"},
                          {field: 'length',title: '长L',halign:'center',align:"center"},
                          {field: 'width',title: '宽W',halign:'center',align:"center"},
                          {field: 'height',title: '高H',halign:'center',align:"center"},
                          {field: 'weight',title: '重量',halign:'center',align:"center"},
                          {field: 'purpose',title: '用途',halign:'center',align:"center"},
                          {field: 'sample',title: '样品',halign:'center',align:"center",formatter: function(value,row,index){if(value =='0' )return'否';if(value =='1' )return'是';if(value ==null )return'否'}}
                         ]
			});
		}
	};
	
	//import name
	function BstpImportNameTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getImportNameData',
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
                    fileName: 'data dictionary import name',  //the file name 
                    worksheetName: 'sheet1', //file work sheet name
                    tableName: 'data dictionary import name',  
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
                    onMsoNumberFormat: DoOnMsoNumberFormat  
                },  
                buttonsAlign:'left',
                pageNumber:1,//the default page is 1
                pageSize: 10,//each page display 10 records
                pageList: "[10,25,50,100,all]",//how many records each page can display
                sidePagination: "server",  //pagination type: server side or client side
                showRefresh:true,//refresh button
                columns: [
                          {field: 'state',checkbox:true}, 
                          {field:'id',visible:false},
                          {field: 'name',title: '进口名',halign:'center',sortable:true,order:'asc'},
                          {field: 'species.speciesNameEn',title: '作物英文名',halign:'center'},
                          {field: 'species.speciesNameCn',title: '作物中文名',halign:'center'}
                         ]
			});
		}
	};
	//importer
	function BstpImporterTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getImporterData',
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
                sortOrder: "asc",                   //排序方式
                locale:'zh-CN',//support Chinese
                pagination: true,//Open pagination function
                exportDataType:"basic",
                showExport:true,
                exportTypes:['excel','csv'],
                exportOptions:{  
                    ignoreColumn: [0,7],  //Ignore exporting the columns to file
                    fileName: 'data dictionary importer',   //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'data dictionary importer',  
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
                          {field: 'importerCode',title: '进口商略号',halign:'center',sortable:true,order:'asc'},
                          {field: 'name',title: '进口商名称',halign:'center'},
                          {field: 'zip',title: '邮编',halign:'center'},
                          {field: 'address',title: '地址',halign:'center'},
                          {field: 'tel',title: '电话',halign:'center'},
                          {field: 'fax',title: '传真',halign:'center'}
                         ]
			});
		}
	};
	//supplier
	function BstpSupplierTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getSupplierData',
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
                    fileName: 'data dictionary supplier', //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'data dictionary supplier',  
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
                          {field: 'supplierCode',title: '供应商略号',halign:'center',sortable:true,order:'asc'},
                          {field: 'name',title: '供应商名称',halign:'center'},
                          {field: 'zip',title: '邮编',halign:'center'},
                          {field: 'address',title: '地址',halign:'center'},
                          {field: 'tel',title: '电话',halign:'center'},
                          {field: 'fax',title: '传真',halign:'center'}
                         ]
			});
		}
	};
	//sale manager
	function BstpSaleManagerTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getSaleManagerData',
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
                    fileName: 'data dictionary sale manager',  //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'data dictionary sale manager',  
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
                    onMsoNumberFormat: DoOnMsoNumberFormat  
                },  
                buttonsAlign:'left',
                pageNumber:1,//the default page is 1
                pageSize: 10,//each page display 10 records
                pageList: "[10,25,50,100,all]",//how many records each page can display
                sidePagination: "server",//pagination type: server side or client side
                showRefresh:true,//refresh button
                columns: [
                          {field: 'state',checkbox:true}, 
                          {field:'id',visible:false},
                          {field: 'SMCode',title: '略号',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'name',title: '名称',halign:'center',align:"center"},
                          {field: 'area.nameCn',title: '区域名',halign:'center',align:"center"},
                          {field: 'area.nameEn',title: '区域简称',halign:'center',align:"center"}
                         ]
			});
		}
	};
	//logistics company
	function BstpLogisticsCompanyTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getLogisticsCompanyData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary logistics company',  //the file name 
                    worksheetName: 'sheet1',   //file work sheet name
                    tableName: 'data dictionary logistics company',  
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
                          {field: 'code',title: 'code',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'name',title: '名称',halign:'center',align:"center"}
                         ]
			});
		}
	};
	
	//variety
	function BstpVarietyTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getVarietyData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary variety  ',  //the file name 
                    worksheetName: 'sheet1',   //file work sheet name
                    tableName: 'data dictionary variety  ',  
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
                          {field:'beginDate',visible:false},
                          {field: 'varietyCode',title: '品种简码',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'species.speciesNameCn',title: '作物',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'commercialName',title: '商品名称',halign:'center',align:"center",sortable:true,order:'asc',formatter:function(value){
                        	  if(value == null || value == $.trim("")){
                        		  return "";
                        	  }
                        	  return value;
                          }},
                          {field: 'varietyName',title: '品种名称',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'beginDateShow',title: '开始年',halign:'center',align:"center",formatter: function(value,row,index){return dateFormatterFor_yyyymm(row.beginDate)}}
                         ]
			});
		}
	};
	
	//group
	function BstpGroupTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getGroupsData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary   Group ',  //the file name 
                    worksheetName: 'sheet1',   //file work sheet name
                    tableName: 'data dictionary   Group ',  
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
                          {field: 'nameEn',title: '分类',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'nameCn',title: 'Group',halign:'center',align:"center",sortable:true,order:'asc'}
                         ]
			});
		}
	};
	
	//Region
	function BstpRegionTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getAreaEntityData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary Region',  //the file name 
                    worksheetName: 'sheet1',   //file work sheet name
                    tableName: 'data dictionary Region',  
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
                          {field: 'regionCode',title: '编码',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'nameCn',title: '区域',halign:'center',align:"center"},
                          {field: 'nameEn',title: 'Region',halign:'center',align:"center"}
                         ]
			});
		}
	};
	
	//Province
	function BstpProvinceTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getProvinceEntityData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary Province',  //the file name 
                    worksheetName: 'sheet1',   //file work sheet name
                    tableName: 'data dictionary Province',  
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
                          {field:'area.id',visible:false},
                          {field: 'area.regionCode',title: '区域编码',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'provinceCode',title: '省编码',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'nameCn',title: '名称',halign:'center',align:"center",sortable:true,order:'asc'}
                         ]
			});
		}
	};
	
	//City
	function BstpCityTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../data/getCityEntityData',
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
                    ignoreColumn: [0,7], //Ignore exporting the columns to file
                    fileName: 'data dictionary  City  ',  //the file name 
                    worksheetName: 'sheet1',   //file work sheet name
                    tableName: 'data dictionary   City',  
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
                          {field:'province.id',visible:false},
                          {field: 'province.provinceCode',title: '省编码',halign:'center',align:"center",sortable:true,order:'asc'},
                          {field: 'nameCn',title: '城市名称',halign:'center',align:"center"},
                         ]
			});
		}
	};
	
	//Select different type,then load the relevent table data
	var selectText = $("#selectDic option:selected").val();
	var bstpTable=new BstpSpeciesTable($("#dicTable"));
	bstpTable.init({});
	$("#selectDic").change(function(){
		selectText = $("#selectDic option:selected").val();
		if(selectText === "0"){
			var bstpTable=new BstpSpeciesTable($("#dicTable"));//作物
			bstpTable.init({});
		}
		if(selectText === "1"){
			var bstpTable=new BstpPackingUnitTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "2"){
			var bstpTable=new BstpImportNameTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "3"){
			var bstpTable=new BstpImporterTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "4"){
			var bstpTable=new BstpSupplierTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "5"){
			var bstpTable=new BstpSaleManagerTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "6"){
			var bstpTable=new BstpLogisticsCompanyTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "7"){
			var bstpTable=new BstpVarietyTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "8"){
			var bstpTable=new BstpGroupTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "9"){
			var bstpTable=new BstpRegionTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "10"){
			var bstpTable=new BstpProvinceTable($("#dicTable"));
			bstpTable.init({});
		}
		if(selectText === "11"){
			var bstpTable=new BstpCityTable($("#dicTable"));
			bstpTable.init({});
		}
		
	});
	
	//add or edit function
	var addOrEdit = ""; //flag of add or edit
	$("#dicAdd").click(function(){
		//init all form table
		$('form').each(function(index,value){
			$(this)[0].reset()
		})
		addOrEdit = "add";
		
		if(selectText === "-1"){
			$("#alertModal .modal-body").text("请先选择一项类型！");
			$("#alertModal").modal("show");
		}
		
		if(selectText === "0"){
			$("#addSpeciesModal label.error").html("");
			$("#addSpeciesForm .modal-header h4").html("新建作物");
			$("#addSpeciesModal").modal('show');
		}
		
		if(selectText === "1"){
			//radio默认选中0：非样品
			$('#packingUnitSample').val(0);
			$("input[name='sampleOff']").get(0).checked=true; 
			$("#addPackingUnitModal label.error").html("");
			$("#addPackingUnitModal .modal-header h4").html("新建包装物规格");
			$("#addPackingUnitModal").modal('show');
		}
		
		if(selectText === "2"){
			$("#addImportNameModal label.error").html("");
			$("#addImportNameModal .modal-header h4").html("新建进口名");
			$("#addImportNameModal").modal('show');
		}
		
		if(selectText === "3"){
			$("#addImporterModal label.error").html("");
			$("#addImporterModal .modal-header h4").html("新建进口商");
			$("#addImporterModal").modal('show');
		}
		
		if(selectText === "4"){
			$("#addSupplierModal label.error").html("");
			$("#addSupplierModal .modal-header h4").html("新建供应商");
			$("#addSupplierModal").modal('show');
		}
		
		if(selectText === "5"){
			$("#addSaleManagerModal label.error").html("");
			$("#addSaleManagerModal .modal-header h4").html("新建销售经理");
			$("#addSaleManagerModal").modal('show');
		}
		
		if(selectText === "6"){
			$("#addLogisticsCompanyModal label.error").html("");
			$("#addLogisticsCompanyModal .modal-header h4").html("新建物流公司");
			$("#addLogisticsCompanyModal").modal('show');
		}
		
		if(selectText === "7"){
			$("#addVarietyModal label.error").html("");
			$("#addVarietyModal .modal-header h4").html("新建品种");
			$("#addVarietyModal").modal('show');
		}
		if(selectText === "8"){
			$("#addGroupModal label.error").html("");
			$("#addGroupModal .modal-header h4").html("新建分类");
			$("#addGroupModal").modal('show');
		}
		
		if(selectText === "9"){
			$("#addAreaEntityModal label.error").html("");
			$("#addAreaEntityModal .modal-header h4").html("新建区域");
			$("#addAreaEntityModal").modal('show');
		}
		
		if(selectText === "10"){
			$("#addProvinceEntityModal label.error").html("");
			$("#addProvinceEntityModal .modal-header h4").html("新建省份");
			$("#addProvinceEntityModal").modal('show');
		}
		
		if(selectText === "11"){
			$("#addCityEntityModal label.error").html("");
			$("#addCityEntityModal .modal-header h4").html("新建城市");
			$("#addCityEntityModal").modal('show');
		}
	});
	
	//绑定数据字段的 Radio的按钮数值
	$('.sampleRadio').on('click',function(){
		var radio = $(this).val();
		console.log("radio:"+radio);
		$('#packingUnitSample').val(radio);
		if($(this).data().value == 'sampleOn' ){
			$("#addPackingUnitForm input[name='sampleOff']").get(0).checked=false; 
		}else{
			$("#addPackingUnitForm input[name='sampleOn']").get(0).checked=false; 
		}
	})
	
	$("#dicEdit").click(function(){
		var selections = new Array();
		if(selectText === "-1"){
			$("#alertModal .modal-body").text("请先选择一项类型！");
			$("#alertModal").modal("show");
		}
		selections = $("#dicTable").bootstrapTable('getAllSelections');
		console.log(selections);
		
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			addOrEdit = "edit";
			if(selectText === "0"){
				$("#addSpeciesForm input[name='id']").val(selections[0].id);
				$("#addSpeciesForm input[name='cropCode']").val(selections[0].cropCode);
				$("#addSpeciesForm input[name='no']").val(selections[0].no);
				$("#addSpeciesForm input[name='specifications']").val(selections[0].specifications);
				$("#addSpeciesForm input[name='speciesNameEn']").val(selections[0].speciesNameEn);
				$("#addSpeciesForm input[name='speciesNameCn']").val(selections[0].speciesNameCn);
				$("#addSpeciesForm input[name='speciesNameCn2']").val(selections[0].speciesNameCn2);
				if(typeof(selections[0].nameCn) != "undefined")
				$("#addSpeciesForm input[name='nameCn']").val(selections[0].nameCn);
				if(typeof(selections[0].nameEn) != "undefined")
				$("#addSpeciesForm input[name='nameEn']").val(selections[0].nameEn);
				if(typeof(selections[0].group) != "undefined")
				$("#addSpeciesForm select[name='group.id']").val(selections[0].group.id);
				$("#addSpeciesModal label.error").html("");
				$("#addSpeciesForm .modal-header h4").html("编辑作物");
				$("#addSpeciesModal").modal('show');
			}
			if(selectText === "1"){
				$("#addPackingUnitForm input[name='id']").val(selections[0].id);
				$("#addPackingUnitForm input[name='specifications']").val(selections[0].specifications);
				$("#addPackingUnitForm input[name='specificationCode']").val(selections[0].specificationCode);
				$("#addPackingUnitForm input[name='specificationName']").val(selections[0].specificationName);
				$("#addPackingUnitForm select[name='unit']").val(selections[0].unit);
				$("#addPackingUnitForm input[name='length']").val(selections[0].length);
				$("#addPackingUnitForm input[name='width']").val(selections[0].width);
				$("#addPackingUnitForm input[name='height']").val(selections[0].height);
				$("#addPackingUnitForm input[name='purpose']").val(selections[0].purpose);
				$("#addPackingUnitForm input[name='weight']").val(selections[0].weight);
				$("#addPackingUnitForm input[name='sample']").val(selections[0].sample);
				if(selections[0].sample == '0'){
					$("#addPackingUnitForm input[name='sampleOn']").get(0).checked=false; 
					$("#addPackingUnitForm input[name='sampleOff']").get(0).checked=true; 
				}else{
					$("#addPackingUnitForm input[name='sampleOff']").get(0).checked=false; 
					$("#addPackingUnitForm input[name='sampleOn']").get(0).checked=true; 
				}
				
				$("#addPackingUnitModal label.error").html("");
				$("#addPackingUnitModal .modal-header h4").html("编辑包装物规格");
				$("#addPackingUnitModal").modal('show');
			}
			
			if(selectText === "2"){
				$("#addImportNameForm input[name='id']").val(selections[0].id);
				$("#addImportNameForm input[name='name']").val(selections[0].name);
				$("#addImportNameForm input[name='species.name_en']").val(selections[0].species.name_en);
				$("#addImportNameForm input[name='species.name_cn']").val(selections[0].species.name_cn);
				$("#addImportNameForm select").val(selections[0].species.id);
				$("#addImportNameModal label.error").html("");
				$("#addImportNameModal .modal-header h4").html("编辑进口名");
				$("#addImportNameModal").modal('show');
			}
			
			if(selectText === "3"){
				$("#addImporterForm input[name='id']").val(selections[0].id);
				$("#addImporterForm input[name='name']").val(selections[0].name);
				$("#addImporterForm input[name='importerCode']").val(selections[0].importerCode);
				$("#addImporterForm input[name='zip']").val(selections[0].zip);
				$("#addImporterForm input[name='address']").val(selections[0].address);
				$("#addImporterForm input[name='name']").val(selections[0].name);
				$("#addImporterForm input[name='tel']").val(selections[0].tel);
				$("#addImporterForm input[name='fax']").val(selections[0].fax);
				$("#addImporterModal label.error").html("");
				$("#addImporterModal .modal-header h4").html("编辑进口商");
				$("#addImporterModal").modal('show');
			}
			
			if(selectText === "4"){
				$("#addSupplierForm input[name='id']").val(selections[0].id);
				$("#addSupplierForm input[name='name']").val(selections[0].name);
				$("#addSupplierForm input[name='supplierCode']").val(selections[0].supplierCode);
				$("#addSupplierForm input[name='zip']").val(selections[0].zip);
				$("#addSupplierForm input[name='address']").val(selections[0].address);
				$("#addSupplierForm input[name='tel']").val(selections[0].tel);
				$("#addSupplierForm input[name='fax']").val(selections[0].fax);
				$("#addSupplierModal label.error").html("");
				$("#addSupplierModal .modal-header h4").html("编辑供应商");
				$("#addSupplierModal").modal('show');
			}
			
			if(selectText === "5"){
				$("#addSaleManagerForm input[name='id']").val(selections[0].id);
				$("#addSaleManagerForm input[name='name']").val(selections[0].name);
				$("#addSaleManagerForm input[name='SMCode']").val(selections[0].SMCode);
				$("#addSaleManagerForm select[name='area.id']").val(selections[0].area.id);
				$("#addSaleManagerModal label.error").html("");
				$("#addSaleManagerModal .modal-header h4").html("编辑销售经理");
				$("#addSaleManagerModal").modal('show');
			}
			
			if(selectText === "6"){
				$("#addLogisticsCompanyForm input[name='id']").val(selections[0].id);
				$("#addLogisticsCompanyForm input[name='name']").val(selections[0].name);
				$("#addLogisticsCompanyForm input[name='code']").val(selections[0].code);
				$("#addLogisticsCompanyModal label.error").html("");
				$("#addLogisticsCompanyModal .modal-header h4").html("编辑物流公司");
				$("#addLogisticsCompanyModal").modal('show');
			}
			if(selectText === "7"){
				$("#addVarietyForm input[name='id']").val(selections[0].id);
				$("#addVarietyForm input[name='varietyCode']").val(selections[0].varietyCode);
				$("#addVarietyForm input[name='species.id']").val(selections[0].species.id);
				$("#addVarietyForm input[name='varietyName']").val(selections[0].varietyName);
				$("#addVarietyForm input[name='commercialName']").val(selections[0].commercialName);
				$("#addVarietyForm input[name='beginDateShow']").val(dateFormatter_yyyymmNoText(selections[0].beginDate));
				$("#addVarietyModal label.error").html("");
				$("#addVarietyModal .modal-header h4").html("编辑物流公司");
				$("#addVarietyModal").modal('show');
			}
			if(selectText === "8"){//Groups
				$("#addGroupForm input[name='id']").val(selections[0].id);
				$("#addGroupForm input[name='nameCn']").val(selections[0].nameCn);
				$("#addGroupForm input[name='nameEn']").val(selections[0].nameEn);
				$("#addGroupModal label.error").html("");
				$("#addGroupModal .modal-header h4").html("编辑分类公司");
				$("#addGroupModal").modal('show');
			}
			
			if(selectText === "9"){//AreaEntity
				$("#addAreaEntityForm input[name='id']").val(selections[0].id);
				$("#addAreaEntityForm input[name='nameCn']").val(selections[0].nameCn);
				$("#addAreaEntityForm input[name='nameEn']").val(selections[0].nameEn);
				$("#addAreaEntityForm input[name='regionCode']").val(selections[0].regionCode);
				$("#addAreaEntityModal label.error").html("");
				$("#addAreaEntityModal .modal-header h4").html("编辑区域公司");
				$("#addAreaEntityModal").modal('show');
			}
			
			if(selectText === "10"){// 
				$("#addProvinceEntityForm input[name='id']").val(selections[0].id);
				$("#addProvinceEntityForm input[name='nameCn']").val(selections[0].nameCn);
				$("#addProvinceEntityForm input[name='provinceCode']").val(selections[0].provinceCode);
				$("#addProvinceEntityForm input[name='area.id']").val(selections[0].area.id);
				$("#addProvinceEntityModal label.error").html("");
				$("#addProvinceEntityModal .modal-header h4").html("编辑省份公司");
				$("#addProvinceEntityModal").modal('show');
			}
			
			if(selectText === "11"){
				$("#addCityEntityForm input[name='id']").val(selections[0].id);
				$("#addCityEntityForm input[name='nameCn']").val(selections[0].nameCn);
				$("#addCityEntityForm input[name='province.id']").val(selections[0].province.id);
				$("#addCityEntityModal label.error").html("");
				$("#addCityEntityModal .modal-header h4").html("编辑城市公司");
				$("#addCityEntityModal").modal('show');
			}
		}
	});
	
	//add or edit species
	$("#addSpeciesForm").validate({
		rules:{
			cropCode:{
				required:true,
				notEmpty:/^$/
			},
			no:{
				required:true,
				notEmpty:/^$/
			},
			speciesNameEn:{
				required:true,
				notEmpty:/^$/
			},
			speciesNameCn:{
				required:true,
				notEmpty:/^$/
			},
			speciesNameCn2:{
				required:true,
				notEmpty:/^$/
			},
			'group.id':{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			cropCode:{
				required:'请输入作物略号！',
				notEmpty:'请输入作物略号！'
			},
			no:{
				required:'请输入作物编号！',
				notEmpty:'请输入作物编号！'
			},
			speciesNameEn:{
				required:'请输入作物英文名！',
				notEmpty:'请输入作物英文名！'
			},
			speciesNameCn:{
				required:'请输入作物中文名！',
				notEmpty:'请输入作物中文名！'
			},
			speciesNameCn2:{
				required:'请输入作物中文名！',
				notEmpty:'请输入作物中文名！'
			},
			'group.id':{
				required:'请选择作物分类！',
				notEmpty:'请选择作物分类！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addSpeciesForm input[name='id']").val(null);
				var params = $("#addSpeciesForm").serialize();
				console.log("addSpeciesForm:"+params);
				$.ajax({
					type : "post",
					url : "../data/addSpecies",
					data : params,
					beforeSend:function(){
						$("#addSpeciesModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#addSpeciesForm")[0].reset();
						addOrEdit = "";
						$("#closeId").click(function(){
							window.location.reload();
						});
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addSpeciesForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editSpecies",
					data:params,
					beforeSend:function(){
						$("#addSpeciesModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addSpeciesForm")[0].reset();
						addOrEdit = "";
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	//add or edit packing unit
	$("#addPackingUnitForm").validate({
		rules:{
			specificationCode:{
				required:true,
				notEmpty:/^$/
			},
			specificationName:{
				required:true,
				notEmpty:/^$/
			},
			weight:{
				required:true,
				notEmpty:/^$/
			},
			unit:{
				notZero:0,
				required:true,
				notEmpty:/^$/
			},
			specifications:{
				notZero:0,
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			specificationCode:{
				required:'请输入规格略号！',
				notEmpty:'请输入规格略号！'
			},
			specificationName:{
				required:'请输入规格名称！',
				notEmpty:'请输入规格名称！'
			},
			weight:{
				required:'请输入规格重量！',
				notEmpty:'请输入规格重量！'
			},
			unit:{
				required:'请选择单位！',
				notEmpty:'请选择单位！'
			},
			specifications:{
				required:'请选择类型！',
				notEmpty:'请选择类型！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addPackingUnitForm input[name='id']").val(null);
				var params = $("#addPackingUnitForm").serialize();
				console.log("add params:" + params);
				$.ajax({
					type : "post",
					url : "../data/addPackingUnit",
					data : params,
					beforeSend:function(){
						$("#addPackingUnitModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
							$("#alertModal .modal-body").text(data['message']);
							$("#addPackingUnitForm")[0].reset();
							addOrEdit = "";
							$("#dicTable").bootstrapTable('refresh');
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addPackingUnitForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editPackingUnit",
					data:params,
					beforeSend:function(){
						$("#addPackingUnitModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addPackingUnitForm")[0].reset();
						addOrEdit = "";
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	//add or edit importe name
	$("#addImportNameForm").validate({
		rules:{
			importerCode:{
				required:true,
				notEmpty:/^$/
			},
			name:{
				required:true,
				notEmpty:/^$/
			},
			tel:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			importerCode:{
				required:'请输入进口商略号！',
				notEmpty:'请输入进口商略号！'
			},
			name:{
				required:'请输入进口商名称!',
				notEmpty:'请输入进口商名称！'
			},
			tel:{
				required:'请输入进口商电话！',
				notEmpty:'请输入进口商电话！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addImportNameForm input[name='id']").val(null);
				var params = $("#addImportNameForm").serialize();
				$.ajax({
					type : "post",
					url : "../data/addImportName",
					data : params,
					beforeSend:function(){
						$("#addImportNameModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#dicTable").bootstrapTable('refresh');
						$("#addImportNameForm")[0].reset();
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addImportNameForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editImportName",
					data:params,
					beforeSend:function(){
						$("#addImportNameModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addImportNameForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	//add or edit importer
	$("#addImporterForm").validate({
		rules:{
			importerCode:{
				required:true,
				notEmpty:/^$/
			},
			name:{
				required:true,
				notEmpty:/^$/
			},
			tel:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			importerCode:{
				required:'请输入进口商略号！',
				notEmpty:'请输入进口商略号！'
			},
			name:{
				required:'请输入进口商名称!',
				notEmpty:'请输入进口商名称！'
			},
			tel:{
				required:'请输入进口商电话！',
				notEmpty:'请输入进口商电话！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addImporterForm input[name='id']").val(null);
				var params = $("#addImporterForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type : "post",
					url : "../data/addImporter",
					data : params,
					beforeSend:function(){
						$("#addImporterModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#dicTable").bootstrapTable('refresh');
						$("#addImporterForm")[0].reset();
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addImporterForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editImporter",
					data:params,
					beforeSend:function(){
						$("#addImporterModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#addImporterForm")[0].reset();
						$("#alertModal .modal-body").text(data['message']);
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	//add or edit supplier
	$("#addSupplierForm").validate({
		rules:{
			supplierCode:{
				required:true,
				notEmpty:/^$/
			},
			name:{
				required:true,
				notEmpty:/^$/
			},
			tel:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			supplierCode:{
				required:'请输入供应商略号！',
				notEmpty:'请输入进口商略号！'
			},
			name:{
				required:'请输入供应商名称!',
				notEmpty:'请输入供应商名称！'
			},
			tel:{
				required:'请输入供应商电话！',
				notEmpty:'请输入供应商电话！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addSupplierForm input[name='id']").val(null);
				var params = $("#addSupplierForm").serialize();
				$.ajax({
					type : "post",
					url : "../data/addSupplier",
					data : params,
					beforeSend:function(){
						$("#addSupplierModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#dicTable").bootstrapTable('refresh');
						$("#addSupplierForm")[0].reset();
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addSupplierForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editSupplier",
					data:params,
					beforeSend:function(){
						$("#addSupplierModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addSupplierForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	//add or edit sale manager
	$("#addSaleManagerForm").validate({
		rules:{
			name:{
				required:true,
				notEmpty:/^$/
			},
			SMCode:{
				required:true,
				notEmpty:/^$/
			},
			'area.id':{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			name:{
				required:'请输入销售经理姓名！',
				notEmpty:'请输入销售经理姓名！'
			},
			SMCode:{
				required:'请输入销售经理代号！',
				notEmpty:'请输入销售经理代号！'
			},
			'area.id':{
				required:'请选择区域！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addSaleManagerForm input[name='id']").val(null);
				var params = $("#addSaleManagerForm").serialize();
				$.ajax({
					type : "post",
					url : "../data/addSaleManager",
					data : params,
					beforeSend:function(){
						$("#addSaleManagerModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
							$("#alertModal .modal-body").text(data['message']);
							$("#addSaleManagerForm")[0].reset();
							$("#dicTable").bootstrapTable('refresh');
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addSaleManagerForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editSaleManager",
					data:params,
					beforeSend:function(){
						$("#addSaleManagerModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addSaleManagerForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	$("#addVarietyForm").validate({
		rules:{
			varietyCode:{
				required:true,
				notEmpty:/^$/
			},
			'species.id':{
				required:true,
				notEmpty:/^$/,
				notZero:0
			},
			commercialName:{
				required:true,
				notEmpty:/^$/
			},
			varietyName:{
				required:true,
				notEmpty:/^$/
			},
			beginDateShow:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			varietyCode:{
				required:'请输入品种编码！',
				notEmpty:'请输入品种编码！'
			},
			'species.id':{
				required:'请选择作物！'
			},
			commercialName:{
				required:'请输入商品名！',
				notEmpty:'请输入商品名！'
			},
			varietyName:{
				required:'请输入品种名称!',
				notEmpty:'请输入品种名称!'
			},
			beginDateShow:{
				required:'请选择开始年份！',
				notEmpty:'请选择开始年份！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addVarietyForm input[name='id']").val(null);
				var params = $("#addVarietyForm").serialize();
				$.ajax({
					type : "post",
					url : "../data/addVariety",
					data : params,
					beforeSend:function(){
						$("#addVarietyModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#addVarietyForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addVarietyForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editVariety",
					data:params,
					beforeSend:function(){
						$("#addVarietyModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addVarietyForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					} 
				});
			}
		}
	});
	
	
	//add or edit logistics company
	$("#addLogisticsCompanyForm").validate({
		rules:{
			name:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			name:{
				required:'请输入物流公司名称！',
				notEmpty:'请输入物流公司名称！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addLogisticsCompanyForm input[name='id']").val(null);
				var params = $("#addLogisticsCompanyForm").serialize();
				$.ajax({
					type : "post",
					url : "../data/addLogisticsCompany",
					data : params,
					beforeSend:function(){
						$("#addLogisticsCompanyModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#addLogisticsCompanyForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}else if("edit" === addOrEdit){
				var params = $("#addLogisticsCompanyForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../data/editLogisticsCompany",
					data:params,
					beforeSend:function(){
						$("#addLogisticsCompanyModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addLogisticsCompanyForm")[0].reset();
						$("#dicTable").bootstrapTable('refresh');
					}
				});
			}
		}
	});
	
	$("#addAreaEntityForm").validate({
		rules:{
			regionCode:{
				required:true,
				notEmpty:/^$/
			},
			nameCn:{
				required:true,
				notEmpty:/^$/
			},
			nameEn:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			regionCode:{
				required:'请输入区域略号！',
				notEmpty:'请输入区域略号！'
			},
			nameCn:{
				required:'请输入区域名称！',
				notEmpty:'请输入区域名称！'
				
			},
			nameEn:{
				required:'请输入代号！',
				notEmpty:'请输入代号！'
			}
		},
		submitHandler:function(form){
			var addAreaEntityForm = $("#addAreaEntityForm")
			var addAreaEntityModal = $("#addAreaEntityModal")
			if("add" === addOrEdit){
				$("#addAreaEntityForm input[name='id']").val(null);
				saveOrUpdate('addAreaEntity',addAreaEntityForm,addAreaEntityModal)
			}else if("edit" === addOrEdit){
				saveOrUpdate('editAreaEntity',addAreaEntityForm,addAreaEntityModal)
			}
		}
	});
	
	$("#addCityEntityForm").validate({
		rules:{
			nameCn:{
				required:true,
				notEmpty:/^$/
			},
			'province.id':{
				required:true,
				notEmpty:/^$/,
				notZero:0
			}
		},
		messages:{
			nameCn:{
				required:'请输入城市名称！',
				notEmpty:'请输入城市名称！'
			},
			'province.id':{
				required:'请选择省份！'
			}
		},
		submitHandler:function(form){
			var addCityEntityForm = $("#addCityEntityForm")
			var addCityEntityModal = $("#addCityEntityModal")
			if("add" === addOrEdit){
				$("#addCityEntityForm input[name='id']").val(null);
				saveOrUpdate('addCityEntity',addCityEntityForm,addCityEntityModal)
			}else if("edit" === addOrEdit){
				saveOrUpdate('editCityEntity',addCityEntityForm,addCityEntityModal)
			}
		}
	});
	$("#addProvinceEntityForm").validate({
		rules:{
			provinceCode:{
				required:true,
				notEmpty:/^$/
			},
			nameCn:{
				required:true,
				notEmpty:/^$/
			},
			'area.id':{
				required:true,
				notEmpty:/^$/,
				notZero:0
			}
		},
		messages:{
			provinceCode:{
				required:'请输入省份略号！',
				notEmpty:'请输入省份略号！'
			},
			nameCn:{
				required:'请输入身份名称！',
				notEmpty:'请输入身份名称！'
			},
			'area.id':{
				required:'请选择区域！',
				notEmpty:'请选择区域！'
			}
		},
		submitHandler:function(form){
			var addProvinceEntityForm = $("#addProvinceEntityForm")
			var addProvinceEntityModal = $("#addProvinceEntityModal")
			if("add" === addOrEdit){
				$("#addProvinceEntityForm input[name='id']").val(null);
				saveOrUpdate('addProvinceEntity',addProvinceEntityForm,addProvinceEntityModal)
			}else if("edit" === addOrEdit){
				saveOrUpdate('addProvinceEntity',addProvinceEntityForm,addProvinceEntityModal)
			}
		}
	});
	$("#addGroupForm").validate({
		rules:{
			nameCn:{
				required:true,
				notEmpty:/^$/
			},
			nameEn:{
				required:true,
				notEmpty:/^$/
			}
		},
		messages:{
			nameCn:{
				required:'请输入中文名称！',
				notEmpty:'请输入中文名称！'
			},
			nameEn:{
				required:'请输入英文名称！',
				notEmpty:'请输入英文名称！'
			}
		},
		submitHandler:function(form){
			var addGroupForm = $("#addGroupForm")
			var addGroupModal = $("#addGroupModal")
			if("add" === addOrEdit){
				$("#addGroupForm input[name='id']").val(null);
				saveOrUpdate('addGroups',addGroupForm,addGroupModal)
			}else if("edit" === addOrEdit){
				saveOrUpdate('addGroups',addGroupForm,addGroupModal)
			}
		}
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
	
	$("#closeId").click(function(){
		$("#confirmModal").modal("hide");
	});
	
	$("#dicRemove").click(function(){
		selections = new Array();
		selections = $("#dicTable").bootstrapTable('getAllSelections');
		console.log("selections.length:"+selections.length);
		console.log(selections[0]);
		if(selectText === "-1"){
			$("#alertModal .modal-body").text("请先选择一种类型！");
			$("#alertModal").modal("show");
		}
		
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择删除记录！");
			$("#alertModal").modal("show");
		}else{
			$("#confirmModal").modal("show");
			
		}
	});
	$("#confirmId").on('click',function(){
		$("#confirmModal").modal("hide");
	
		if(selectText == "0"){
			var speciesList = new Array();
	 
			function SpeciesEntity(id,name_en,name_cn){
				this.id = id;
				this.name_en = name_en;
				this.name_cn = name_cn;
			}
			for(var i = 0;i<selections.length;i++){
				var species = new SpeciesEntity(
						selections[i].id,
						selections[i].name_en,
						selections[i].name_cn
				);
				speciesList.push(species);
			}
			$.ajax({
				type:"post",
				url:"../data/deleteSpecies",
				data:JSON.stringify(speciesList),
				contentType : 'application/json;charset=utf-8',
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$("#dicTable").bootstrapTable('refresh');
				}
			});
		}
		
		if(selectText == "1"){
			delEntity('deletePackingUnit',selections)
		}
		if(selectText == "2"){
			var importNameList = new Array();
			function ImportNameEntity(id,specifications){
				this.id = id;
				this.specifications = specifications;
			}
			for(var i = 0;i<selections.length;i++){
				var importName = new ImportNameEntity(
						selections[i].id,
						selections[i].specifications
				);
				importNameList.push(importName);
			}
			$.ajax({
				type:"post",
				url:"../data/deleteImportName",
				data:JSON.stringify(importNameList),
				contentType : 'application/json;charset=utf-8',
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$("#dicTable").bootstrapTable('refresh');
				}
			});
		}
		//
		if(selectText == "3"){
			var importerList = new Array();
			function ImporterEntity(id,name,simple_name){
				this.id = id;
				this.name = name;
				this.simple_name = simple_name;
			}
			for(var i = 0;i<selections.length;i++){
				var importer = new ImporterEntity(
						selections[i].id,
						selections[i].name,
						selections[i].simple_name
				);
				importerList.push(importer);
			}
			$.ajax({
				type:"post",
				url:"../data/deleteImporter",
				data:JSON.stringify(importerList),
				contentType : 'application/json;charset=utf-8',
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$("#dicTable").bootstrapTable('refresh');
				}
			});
		}
		//
		if(selectText == "4"){
			delEntity('deleteSupplier',selections);
		}
		//
		if(selectText == "5"){
			delEntity('deleteSaleManager',selections);
		}
		//
		if(selectText == "6"){
			var logisticCompanyList = new Array();
			function LogisticCompanyEntity(id,name){
				this.id = id;
				this.name = name;
			}
			for(var i = 0;i<selections.length;i++){
				var logisticCompany = new LogisticCompanyEntity(
						selections[i].id,
						selections[i].name
				);
				logisticCompanyList.push(logisticCompany);
			}
			$.ajax({
				type:"post",
				url:"../data/deleteLogisticCompany",
				data:JSON.stringify(logisticCompanyList),
				contentType : 'application/json;charset=utf-8',
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$("#dicTable").bootstrapTable('refresh');
				}
			});
		}
		if(selectText == "7"){
			var varietyEntityList = new Array();
			function VarietyEntity(id,name){
				this.id = id;
				this.name = name;
			}
			for(var i = 0;i<selections.length;i++){
				var varietyEntitys = new VarietyEntity(
						selections[i].id,
						selections[i].varietyName
				);
				varietyEntityList.push(varietyEntitys);
			}
			$.ajax({
				type:"post",
				url:"../data/deleteVariety",
				data:JSON.stringify(varietyEntityList),
				contentType : 'application/json;charset=utf-8',
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					$("#dicTable").bootstrapTable('refresh');
				}
			});
		}
		if(selectText == "8"){
			delEntity('deleteGroups',selections);
		}
		if(selectText == "9"){
			delEntity('deleteAreaEntity',selections);
		}
		if(selectText == "10"){
			delEntity('deleteProvinceEntity',selections);
		}
		if(selectText == "11"){
			delEntity('deleteCityEntity',selections);
		}
	});
	
	/**
	 * saveOrUpdate  obj ，just need URL and object list
	 * @author Justin
	 * @param delURL
	 * @param objList
	 */
	function saveOrUpdate(url,dataForm,dataModal){
		var params = dataForm.serialize();
		var alertModal = $("#alertModal .modal-body");
		$.ajax({
			type : "post",
			url : "../data/"+url,
			data : params,
			beforeSend:function(){
				$(dataModal).modal('hide');
				alertModal.text("处理中，请稍后。。。");
				$("#alertModal").modal("show");
			},
			success : function(data) {
				alertModal.text(data['message']);
				$("#dicTable").bootstrapTable('refresh');
			}
		});
	}
	/**
	 * del  obj ，just need URL and object list
	 * @author Justin
	 * @param delURL
	 * @param objList
	 */
	function delEntity(delURL,objList){
		var  bodyMsg = $("#alertModal .modal-body");
		var modal = $("#alertModal");
		$.ajax({
			type:"post",
			url:"../data/"+delURL,
			data:JSON.stringify(objList),
			contentType : 'application/json;charset=utf-8',
			beforeSend:function(){
				bodyMsg.text("处理中，请稍后。。。");
				modal.modal("show");
			},
			success:function(data){
				bodyMsg.text(data['message']);
				$("#dicTable").bootstrapTable('refresh');
			},
			error:function(data){
				bodyMsg.text(data['message']);
				modal.modal("hide");
			}
		});
	}
	
});