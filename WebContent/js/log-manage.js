document.title = "日志管理";
$(function() {
	$.fn.datetimepicker.defaults = {
			//default language: Chinese
            language: 'zh-CN',
            clearBtn: true,
          //default date format
            format: "yyyy-MM-dd hh:mm:ss",
            autoclose: true,
            todayBtn: true,
            startView: 2,
            minView: 2,
            pickerPosition: "bottom-left"
        };
    
    $('#datetimepicker').datetimepicker({
        format: 'yyyy-MM-dd hh:mm:ss'
    });
    
  //load table data and search function
	function BstpLogTable(obj){
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../log/getLogData',
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
                    fileName: 'log management',   //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'log management',  
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
                          {field: 'userAccount',title: '账号',halign:'center'},
                          {field: 'userName',title: '姓名',halign:'center'},
                          {field: 'content',title: '操作',halign:'center'},
                          {field: 'type',title: '类型',halign:'center'},
                          {field: 'date',title: '时间',halign:'center',formatter: dateFormatter2}
                         ]
			});
		}
	};
	
	var bstpTable=new BstpLogTable($("#logTable"));
    bstpTable.init({});
    
    $("#logSearchBtn").click(function(){
        var searchArgs={
             account:$("#logSearchForm input[name='account']").val(),
             content:$("#logSearchForm input[name='content']").val(),
             date:$("#logSearchForm input[name='date']").val()
        };
        bstpTable.init(searchArgs)
        return false;
    });

});