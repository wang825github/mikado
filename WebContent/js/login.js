$(function(){
	
	$.validator.addMethod("notEmpty",function(value,element,params){
		if(params.test($.trim(value))){
			return false;
		}else{
			return true;
		}
	},"输入内容为空!");
	
	$("#loginForm").validate({
		rules:{
			account:{
				required:true,
				notEmpty:/^$/
			},
			password:{
			    required:true,
			    minlength:6,
			    notEmpty:/^$/
			}
		},
		messages:{
			account:{
				required:'请输入用户账号！'
			},
			password:{
				required:'请输入用户密码！',
				minlength:'请输入至少6位长度字符！'
			}
		},
		submitHandler:function(form){
            form.submit();
        } 
	});
	
	
});