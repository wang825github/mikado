
$.ajaxSetup({
    global: false,
    type: "POST",
    complete: function (XMLHttpRequest, textStatus) {
        var data = XMLHttpRequest.responseText;
        if (data == "timeout") {
        	  window.top.location='/Mikado'; 
//            if( window.top != window.self ){
//                window.top.location = "${pageContext.request.contextPath}";
//               
//            }
        }
    }
});


$.fn.datetimepicker.defaults = {
	//default language: Chinese
	language : 'zh-CN',
	clearBtn : true,
	showSecond: true,
	format : 'yyyy-mm-dd hh:ii:ss',
//	timeFormat: 'hh:mm:ss',
	CustomFormat:'yyyy-MM-dd hh-mm-ss',
	autoclose : true,
	todayBtn : true,
	startView : 2,
	minView : 2,
	defaultDate:getNowFormatDate(),
	pickerPosition : "bottom-left"
};

$('.form_datetime').datetimepicker({
	showSecond: true,
	timeFormat: 'yyyy-MM-dd hh:ii:ss',
	CustomFormat:'yyyy-MM-dd hh-mm-ss'
});



$(' .datetimepicker_yyyymm').datetimepicker({
	format : 'yyyy-mm',
	startView : 3,
	minView : 3
});

$(' .datetimepicker_yyyyMM2').datetimepicker({
	format : 'yyyy/mm',
	startView : 3,
	minView : 3
});
function dateFormatter_yyyymmNoText(value) {
	if(value == null || value == ""){
		return "";
	}
	return new Date(value).format("yyyy-MM");
}

function dateFormatter_yyyymmNoText2(value) {
	if(value == null || value == ""){
		return "";
	}
	return new Date(value).format("yyyy/MM");
}

function dateFormatterFor_yyyymm(value) {
	if(value == null || value == ""){
		return "";
	}
	return new Date(value).format("yyyy-MM")+"以前";
}
// date formatter function
function dateFormatter(value) {
	if(value == null || value == ""){
		return "";
	}
	return new Date(value).format("yyyy-MM-dd");
}

//date formatter function2
function dateFormatter2(value){
	if(value == null || value == ""){
		return "";
	}
	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
}

function adminFormatter(value){
	if(value == 0){
		return "否";
	}
	if(value == 1){
		return "是";
	}
}

function emptyFormatter(value){
	if(value == null || $.trim(value) == ""){
		return "";
	}
	return value;
}

//get current date time,date formatter if yyyy-MM-dd
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

//transform date time
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter (of a year)
		"S" : this.getMilliseconds()//million seconds
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

//text content when export excel file
function DoOnMsoNumberFormat(cell, row, col) {  
    var result = "";  
//    if (row > 0 && col == 0)  
        result = "\\@"; 
    return result;  
}

//form input content cannot be empty
$.validator.addMethod("notEmpty",function(value,element,params){
	if(params.test($.trim(value))){
		return false;
	}else{
		return true;
	}
},"输入内容为空");

//the value formate is dd%
$.validator.addMethod("specification",function(value,element,params){
	if(params.test($.trim(value))){
		return true;
	}else{
		return false;
	}
},"请输入 数字% 格式");

//validate form input content: telephone
$.validator.addMethod("length",function(value,element,params){
	if(value.length == params[0] || value.length == params[1] || value.length == params[2]){
		return true;
	}
	return false;
},"电话号码长度只能是7位，8位或11位");

//validate form input:just positive number
$.validator.addMethod("positive",function(value,element,params){
	if(params.test(value)){
		return true;
	}
	return false;
},'只能输入非负数字!');

$.validator.addMethod("decimalNum",function(value,element,params){
	console.log("params:"+params);
	if(params.test(value)){
		return true;
	}
	return false;
},'只能输入非负数字!');

$.validator.addMethod("decimalNum2",function(value,element,params){
	console.log("params:"+params);
	if(params.test(value)){
		return true;
	}
	return false;
},'只能输入非负数字!');

$.validator.addMethod("phone",function(value,element,params){
	if(params.test(value)){
		return true;
	}
	return false;
},'只能输入数字或与+号或-号组合！');

$.validator.addMethod("smallThanPlanAmount1",function(value,element,params){
	console.log("value:" + value);
	var planamount = $("#addPackagingForm input[name='planAmount']").val();
	if(value == null || value == $.trim("")|| parseInt(value) <= parseInt(planamount)){
		return true;
	}
	return false;
},'实际数量不能大于计划数量！');
$.validator.addMethod("smallThanPlanAmount2",function(value,element,params){
	var planamount = $("#updatePackagingForm input[name='planAmount']").val();
	if(value == null || value == $.trim("")|| parseInt(value) <= parseInt(planamount)){
		return true;
	}
	return false;
},'实际数量不能大于计划数量！');

$.validator.addMethod("bigThanStart",function(value,element,params){
	var startDay = $("#addProductionsForm input[name='startDay']").val()
	console.log("startDay:" + startDay);
	console.log("params:" +params);
	console.log("value:" + value);
	console.log(value > $("#addProductionsForm input[name='startDay']").val());
	if(value != null && value != $.trim("")&& value < $("#addProductionsForm input[name='startDay']").val()){
		return false;
	}
	return true;
},'包装结束时间小于开始时间！');

//$.validator.addMethod("equalPackagingAmount",function(value,element,params){
//	var sum = 0;
//	$("#addProductionsForm input[name='packagingAmount']").each(function(i){
//		sum = sum + parseInt($(this).val());
//		console.log($(this).val());
//	});
//	console.log("sum:" + sum);
//	if(value != sum){
//		return false;
//	}
//	return true;
//},'包装物数量和入库数量不相等！');

$.validator.addMethod("notZero",function(value,element,params){
	if(value == 0 || value == "0"){
		return false;
	}
	return true;
},'请选择一项！');


//reset password
$(function(){
	
	//reset password operation
	$("#resetPwd").click(function(){
		$("#resetModal").modal("show");
		return false;
	});
	
	//validate form before reseting password
	$("#resetForm").validate({
		rules:{
			oldPwd:{
				required:true,
				notEmpty:/^$/
			},
			newPwd:{
				required:true,
				notEmpty:/^$/,
				minlength:6
			},
			confirmPwd:{
				required:true,
				notEmpty:/^$/,
				minlength:6
			}
		},
		messages:{
			
		},
		
		submitHandler:function(form){
			var params = $("#resetForm").serialize();
			console.log(params);
			$.ajax({
				url:"../resetPassword",
				data:params,
				type:'post',
				beforeSend:function(){
    				$("#alertModal .modal-body").text("处理中，请稍后。。。");
    				$("#alertModal").modal("show");
    			},
				success:function(data){
					$("#alertModal .modal-body").text(data['message']);
					if(data['success'] == true){
						$("#resetModal").modal("hide");
					}
				}
			});
			return false;
		}
	});
	
});
	var getAjaxData={
			search:function(url,data){
				 var dfr = $.Deferred();
				 $.ajax({
						type:"post",
						url:url,
						data:data,
//						contentType : 'application/json;charset=utf-8',请注意请求的参数
						success:function(data){
							   success:dfr.resolve
						}
					});
				return dfr.promise();
			}
	}  
//
	$('#addSeedSelectBtn').on('click',function(e){
		e.preventDefault(); 
		var seedSelectBody = $('#seedSelectBody');
		var seedDetailSelect = $('#seedDetailSelect');
		var html = '<div class="form-group">'+
							'<div class="input-group ">'+
								'<select   name="seed.id" class="newAddSelect form-control selectpicker show-tick " required data-live-search="true" >'+seedDetailSelect.html()+'</select>'+
									'<span class="input-group-btn">'+
					          			 '<button    class="btn btn-primary minusBtn" >'+ 
					          		' <span class="glyphicon glyphicon-minus"></span></button >'+
					        		'</span>'+
					      	'</div>'+
						'</div>';
	 
		seedSelectBody.append(html);
		$(".newAddSelect").selectpicker();
		controllBtnDisable();
	})
	
//	$('#addSeedSelectBtn').on('click',function(e){
//		e.preventDefault(); 
//		var seedSelectBody = $('#seedSelectBody');
//		var seedDetailSelect = $('#seedDetailSelect');
//		var reqData={"id":"789"};//测试数据
//		//第一次参数是URL ，第二个请求数据data
//		 
//		$.when( $.ajax({url:'../dataSynchronization/test2',data:reqData}) ).then(function(resData, textStatus, jqXHR){
//			 var optionHtml = '<option value="">--</option>';
//			  $(resData).each(function(index,val){
//				optionHtml = optionHtml+'<option value="'+val+'">'+val+'</option>';
//			  });
//			  var html = '<div class="form-group">'+
//				'<div class="input-group ">'+
//					'<select   name="seed.id" class="form-control" >'+optionHtml+'</select>'+
//						'<span class="input-group-btn">'+
//		          			 '<button    class="btn btn-primary minusBtn" >'+ 
//		          		' <span class="glyphicon glyphicon-minus"></span></button >'+
//		        		'</span>'+
//		        		'</div>'+
//		      	'</div>';
//
//				seedSelectBody.append(html);
//				controllBtnDisable();
//		});
// 
//	})
	$('#addPackageSelectBtn').on('click',function(e){
		e.preventDefault(); 
		var packageSelectBody = $('#packageSelectBody');
		var packageDetailSelect = $('#packageDetailSelect');
		var html = '<div class="form-group">'+
							'<div class="input-group ">'+
								'<select   name="packages.id" class="newAddPackageSelect form-control selectpicker show-tick "  required data-live-search="true">'+packageDetailSelect.html()+'</select>'+
									'<span class="input-group-btn">'+
					          			 '<button   class="btn btn-primary minusBtn" >'+ 
					          		' <span class="glyphicon glyphicon-minus"></span></button >'+
					        		'</span>'+
					      	'</div>'+
						'</div>';
		packageSelectBody.append(html);
		$(".newAddPackageSelect").selectpicker();
		controllBtnDisable();
	})
	$('body').on('click','.minusBtn',function(e){
		e.preventDefault(); 
		$(this).parents('.input-group').remove();
		controllBtnDisable();
	})
	
	function controllBtnDisable(){
		var seedSelectBody = $('#seedSelectBody');
		var packageSelectBody = $('#packageSelectBody');
 
		if(seedSelectBody.find('select').length>1){
			document.getElementById('addPackageSelectBtn').disabled=true
			$('#addPackageSelectBtn').attr('disabled');// 防止jquery 操作按钮disabled 失灵。
		}else{
			document.getElementById('addPackageSelectBtn').disabled=false
			$('#addPackageSelectBtn').removeAttr('disabled');
		}
		
		if(packageSelectBody.find('select').length>1){
			document.getElementById('addSeedSelectBtn').disabled=true
			$('#addSeedSelectBtn').attr('disabled');
		}else{
			document.getElementById('addSeedSelectBtn').disabled=false
			$('#addSeedSelectBtn').removeAttr('disabled');
			
		}
	}
	//modal隐刺之后，恢复模板的状态
	$('#addProductionsModal').on('hidden.bs.modal', function () {
		 var isSample = $('#isSample');
		 var storageFile = $('#storageFile');
		 var storageFileDiv = $('#storageFileDiv')
		storageFile.attr('type','file');
     	storageFile.removeAttr('disabled','disabled');
     	isSample.removeAttr('checked')
     	storageFileDiv.show();
		})
	//初始化按钮
	$('#isSample').bootstrapSwitch({
		onText:'样品',
		offText:'非样品',
		onColor:'danger',
		offColor:'success',
		 onSwitchChange:function(event,state){
			 var isSample = $('#isSample');
			 var storageFile = $('#storageFile');
			 var storageFileDiv = $('#storageFileDiv')
	            if(state==true){
	                storageFile.attr('type','');
		        	storageFile.attr('disabled','disabled');
		        	isSample.attr('checked','')
		        	storageFileDiv.hide();
//		        	storageFile[0].selection.clear(); 
	            }else{
	             	storageFile.attr('type','file');
		        	storageFile.removeAttr('disabled','disabled');
		        	isSample.removeAttr('checked')
		        	storageFileDiv.show();
	            }  
	        } 
	});
	/*$('#isSample').on('change',function(e){
		
		 var checked = $(this).prop('checked');
	        if (checked) {
	        	$('#storageFileDiv').hide();
	        } else {
	        	$('#storageFileDiv').show();
	        }
		
	})*/
	
	  $("#seedDetailSelect").change(function(){
    	if($(this).val() == 0){
    		return;
    	}
    	$.ajax({
			type:"get",
			url:"../packaging/getPackageListBySpecies?seedID="+$(this).val(),
			contentType : 'application/json;charset=utf-8',
			success:function(data){
				$("#packageDetailSelect").empty();
				 for (var i=0; i < data.length; i++) {  
//					 ${packages.lotNumber}-${packages.packingUnit.specificationCode }-${packages.packingUnit.specificationName }-${packages.packingUnit.weight }${packages.packingUnit.unit }-${packages.packingUnit.specifications }
                     $('#packageDetailSelect').append("<option class="+data[i].species.id+" value="+data[i].id+">"+data[i].lotNumber+"-"+data[i].packingUnit.specificationCode+"-"+data[i].packingUnit.specificationName+"-"+data[i].packingUnit.weight+data[i].packingUnit.unit+"-"+data[i].packingUnit.specifications+"</option>");  
                 } 
				 $('#packageDetailSelect').selectpicker('refresh');
			}
    	// 添加 失败提示信息 "无法获取批号，请稍后再试"
		});
    	
    	$.ajax({
			type:"get",
			url:"../packaging/getVarietyListBySeedId?seedID="+$(this).val(),
			contentType : 'application/json;charset=utf-8',
			success:function(data){
				$("#commercialNameSelect").empty();
				 for (var i=0; i < data.length; i++) {  
//					 ${packages.lotNumber}-${packages.packingUnit.specificationCode }-${packages.packingUnit.specificationName }-${packages.packingUnit.weight }${packages.packingUnit.unit }-${packages.packingUnit.specifications }
                     $('#commercialNameSelect').append("<option class="+data[i].id+" value="+data[i].id+">"+data[i].commercialName+"</option>");  
                 } 
				 $('#commercialNameSelect').selectpicker('refresh');
			}
    	// 添加 失败提示信息 "无法获取批号，请稍后再试"
		});
    	
    	$.ajax({
			type:"get",
			url:"../productions/generateLotNumber?seedID="+$(this).val(),
			contentType : 'application/json;charset=utf-8',
			success:function(data){
				$("#productionLotNumber").val(data['lotNumber']);
			}
    	// 添加 失败提示信息 "无法获取批号，请稍后再试"
		});
    });
