document.title = "用户管理";
$(function() {
	
	var addOrEdit; //add or edit flag
	//add user
	$("#userAdd").click(function(){
		addOrEdit = "add";
		$("#addModal label.error").html("");
		$("#addForm .modal-header h4").html("新建用户");
		$("#addModal").modal('show');
		
	});
	
	//edit user
	$("#userEdit").click(function(){
		var selections = new Array();
		selections = $("#userTable").bootstrapTable('getAllSelections');
		console.log(selections);
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择一条记录！");
			$("#alertModal").modal("show");
		}else if(selections.length >1){
			$("#alertModal .modal-body").text("一次只能编辑一条记录！");
			$("#alertModal").modal("show");
		}else{
			addOrEdit = "edit";
			$("#addForm input[name='id']").val(selections[0].id);
			$("#addForm input[name='jobNumber']").val(selections[0].jobNumber);
			$("#addForm input[name='account']").val(selections[0].account);
			$("#addForm input[name='name']").val(selections[0].name);
			$("#addForm input[name='position']").val(selections[0].position);
			$("#addForm input[name='telephone']").val(selections[0].telephone);
			$("#addForm select[name='isAdmin']").val(selections[0].isAdmin);
			$("#addForm input[name='note']").val(selections[0].note);
			
			$("#addModal label.error").html("");
			$("#addForm .modal-header h4").html("编辑用户");
			$("#addModal").modal('show');
			console.log(selections);
		}
	});
	
	//add or edit user
	$("#addForm").validate({
		rules:{
			account:{
				required:true,
				notEmpty:/^$/
			},
			name:{
				required:true,
				notEmpty:/^$/
			},
			telephone:{
				required:true,
				notEmpty:/^$/,
				phone:/^\d*(\+{0,1}|-{0,2})\d+$/
			}
		},
		messages:{
			account:{
				required:'请输入账号！'
			},
			name:{
				required:'请输入姓名！'
			},
			telephone:{
				required:'请输入电话！'
			}
		},
		submitHandler:function(form){
			if("add" === addOrEdit){
				$("#addForm input[name='id']").val(null);
				var params = $("#addForm").serialize();
				$.ajax({
					type : "post",
					url : "../user/addUser",
					data : params,
					beforeSend:function(){
						$("#addModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success : function(data) {
						$("#alertModal .modal-body").text(data['message']);
						$("#addForm")[0].reset();
						if(data['success'] == true){
							$("#userTable").bootstrapTable('refresh');
						}
					}
				});
			}
			if("edit" === addOrEdit){
				var params = $("#addForm").serialize();
				console.log("params:"+params);
				$.ajax({
					type:"post",
					url:"../user/editUser",
					data:params,
					beforeSend:function(){
						$("#addModal").modal('hide');
        				$("#alertModal .modal-body").text("处理中，请稍后。。。");
        				$("#alertModal").modal("show");
        			},
					success:function(data){
						$("#alertModal .modal-body").text(data['message']);
						$("#addForm")[0].reset();
						if(data['success'] == true){
							$("#userTable").bootstrapTable('refresh');
						}
					}
				});
			}
			return false;
        } 
	});
	
	//when close model,reset form content
	$(".close").click(function(){
		$("#addForm")[0].reset();
	});
	
	//delete user,can delete multiple user at the same time
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
	
	$("#closeId").one('click',function(){
		$("#confirmModal").modal("hide");
		$(this).unbind("click");
	});
	
	$("#userRemove").click(function(){
		var selections = new Array();
		selections = $("#userTable").bootstrapTable('getAllSelections');
		console.log("selections.length:"+selections.length);
		console.log(selections[0]);
		if(selections == null || selections.length == 0){
			$("#alertModal .modal-body").text("请先选择删除记录！");
			$("#alertModal").modal("show");
		}else{
			$("#confirmModal").modal("show");
			
			$("#confirmId").one('click',function(){
				$("#confirmModal").modal("hide");
					var userList = new Array();
					function UserEntity(id,job_number,account,name,position,telephone,note){
						this.id = id;
						this.job_number = job_number;
						this.account = account;
						this.name = name;
						this.position = position;
						this.telephone = telephone;
						this.note = note;
					}
					for(var i = 0;i<selections.length;i++){
						var user = new UserEntity(
								selections[i].id,
								selections[i].job_number,
								selections[i].account,
								selections[i].name,
								selections[i].position,
								selections[i].telephone,
								selections[i].note
						);
						userList.push(user);
					}
					$.ajax({
						type:"post",
						url:"../user/removeUser",
						data:JSON.stringify(userList),
						contentType : 'application/json;charset=utf-8',
						beforeSend:function(){
							$("#alertModal .modal-body").text("处理中，请稍后。。。");
							$("#alertModal").modal("show");
						},
						success:function(data){
							$("#alertModal .modal-body").text(data['message']);
							$("#userTable").bootstrapTable('refresh');
						}
					});
			});
		}
	});
	
	
	
	//load table data and search function
	function BstpUserTable(obj){
		
		this.obj = obj;
		
		this.init = function(searchArgs){
			this.obj.bootstrapTable("destroy");
			this.obj.bootstrapTable({
				url: '../user/getUserData',
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
                sortOrder: "id desc", //sort by id,type:desc
                exportDataType:"basic",
                showExport:true,
                exportTypes:['excel','csv'],
//                excelFileFormat: 'xlshtml',
//                outputMode: 'file',
                exportOptions:{  
                    ignoreColumn: [0],   //Ignore exporting the columns to file
                    fileName: 'user management',  //the file name 
                    worksheetName: 'sheet1',  //file work sheet name
                    tableName: 'user management',  
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
                    excelFileFormat:'xmlss',
                    onMsoNumberFormat: DoOnMsoNumberFormat  
                },  
                buttonsAlign:'left',
                pageNumber:1,//the default page is 1
                pageSize: 10,//each page display 10 records
                pageList: "[5,10,25,50,100,all]",//how many records each page can display
                sidePagination: "server",//pagination type: server side or client side
                showRefresh:true,//refresh button
                columns: [
                          {field: 'state',checkbox:true}, 
                          {field:'id',visible:false,sortable:true},
                          {field: 'jobNumber',title: '编号',halign:'center',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return "";
                        	  }
                        	  return value;
                          }},
                          {field: 'account',title: '账号',halign:'center',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return '';
                        	  }
                        	  return value;
                          }},
                          {field: 'name',title: '姓名',halign:'center',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return "";
                        	  }
                        	  return value;
                          }},
                          {field:'position',title:'职位',halign:'center',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return "";
                        	  }
                        	  return value;
                          }},
                          {field:'telephone',title:'电话',halign:'center',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return "";
                        	  }
                        	  return value;
                          }},
                          {field:'isAdmin',title:'是否管理员',halign:'center',formatter:adminFormatter},
                          {field:'note',title:'备注',halign:'center',formatter:function(value){
                        	  if(value == null || $.trim(value) == ''){
                        		  return "";
                        	  }
                        	  return value;
                          }}
                         ]
			});
		}
	};
	
	var bstpUserTable=new BstpUserTable($("#userTable"));
	bstpUserTable.init({});
    
    $("#searchUserBtn").click(function(){
        var searchArgs={
             account:$("#searchUser input[name='account']").val(),
             name:$("#searchUser input[name='name']").val()
        };
        bstpUserTable.init(searchArgs)
        return false;
    });
    
});