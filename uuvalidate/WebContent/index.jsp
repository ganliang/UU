<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆账户</title>
<script type="text/javascript" src="./script/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#logon").click(function() {
			//用户信息的验证
			if ("" == $("#name").val() || "" == $("#password").val()) {
				alert("用户名和密码不能为空");
			} else {
				$("form").submit();
			}
		});

		$("#check").click(function() {
			$.post("person/check.action", {
				'name' : $("#name").val()
			}, function(data) {
				if (data == 1) {
					$("#check").hide();
					$("#code").text("这个号码是账号");
				} else {
					alert("你输入的账号不存在");
				}
			});
		});
		$("#register").click(function() {
			window.open("register.jsp", "_self");
		});

	});
</script>
</head>
<body>
	<h1 align="center">
		<font color="red">用户登陆</font>
	</h1>
	<form action="<%=request.getContextPath()%>/user/login.do"
		method="post">
		<h4 align="center">
			<font color="green">${login_error }</font>
		</h4>
		<table align="center">
			<tr>
				<td>用户名</td>
				<td><input type="text" id="name" name="userName"
					value="adminUU"></td>
			</tr>
			<tr>
				<td>用户密码</td>
				<td><input type="password" id="password" name="userPassword"
					value="uugty123456"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" id="logon"
					value="登陆"></td>
			</tr>
		</table>
	</form>
</body>
</html>