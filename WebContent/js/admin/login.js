/**
 * 
 */
var errormsg = $("#error_msg");
$("input[name='sysuser.username']").click(function(){
	errormsg.hide();
});
$("input[name='sysuser.password']").click(function(){
	errormsg.hide();
});
$("#login_button").click(function(){
	var username = $("input[name='sysuser.username']").val();
	var password = $("input[name='sysuser.password']").val();
	if(username == "" || password == ""){
		errormsg.html("用户名密码不能为空");
		errormsg.show();
		return false;
	}
});

$(".input-group-addon").click(function(){
	$("#verifycode_img").attr("src", "/verifyCode?v=" + Math.random());
});
