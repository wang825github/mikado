document.title = "库存管理";
$(function() {
	//default language: Chinese
	function BstpStorageTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../storage/getStorageInfo',
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
                sortable: true, //open sorting function
                sortOrder: "id desc", //sort by id,desc type
                exportDataType:"basic",
                showExport:true,
                exportTypes:['excel','csv'],
                exportOptions:{  
                    ignoreColumn: [0],  //Ignore exporting the columns to file
                    fileName: 'Storage management', //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'Storage management',  
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
//                    onMsoNumberFormat: DoOnMsoNumberFormat  
                },  
                buttonsAlign:'left',
                pageNumber:1,//the default page is 1
                pageSize: 10,//each page display 10 records
                pageList: "[5,10,25,50,100,all]",//how many records each page can display
                sidePagination: "server", //pagination type: server side or client side
                showRefresh:true,//refresh button
                
                columns: [
                          {field: 'state',checkbox:true}, 
//                          {field:'id',visible:false,sortable:true},
                          {field: 'storageId.conmercial_name',title: '商品名',halign:'center'},
                          {field: 'storageId.name_en',title: '作物英文名',halign:'center'},
                          {field: 'storageId.name_cn',title: '作物中文名',halign:'center'},
                          {field: 'storageId.specifications',title: '包装物规格',halign:'center',align:'right'},
                          {field: 'planAmount',title: '计划数量',halign:'center',align:'right'},
                          {field: 'currentAmounts',title: '实际数量',halign:'center',align:'right'},
                          {field:'surplusAmount',title:'剩余数量',halign:'center',align:'right'}
                         ]
			});
		}
	};
	
	var bstpTable=new BstpStorageTable($("#storageTableId"));
    bstpTable.init({});
    
    $("#searchStorageBtn").click(function(){
        var searchArgs={
        	speciesId:$("#searchDataManage select[name = 'speciesId']").val(),
        	conmercial_name:$("#searchDataManage select[name = 'conmercialName']").val(),
        	packingUnitId:$("#searchDataManage select[name = 'packingUnitId']").val()
        };
        bstpTable.init(searchArgs)
        return false;
    });
	
	function getCommercialNameBySpeciesId(){
		  var sel2 = $("#searchDataManage select[name='conmercialName']");
		  sel2.empty();
		  if($("#searchDataManage select[name='speciesId'] option:selected").val() == "0"){
			  sel2.append("<option value='0'> -- </option>");
			  return;
		  }
		  $.ajax({
				type : "get",
				url : "../storage/getConmercialName?speciesId="+$("#searchDataManage select[name='speciesId'] option:selected").val(),
				success : function(data) {
					sel2.append("<option value='0'> -- </option>");
					sel2.append("<option value=\"" + data[0]+ "\" selected>" + data[0]+"</option>");
					for(var i = 1; i< data.length;i++){
						var col = data[i];
						sel2.append("<option value=" + data[i] + ">" + data[i]+"</option>");
					}
				}
			});
	}
	$("#speciesId").change(getCommercialNameBySpeciesId);
    
});