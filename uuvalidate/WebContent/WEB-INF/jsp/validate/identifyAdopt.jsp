<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>身份证信息录入</title>
</head>
<body>
	<center>
		<h2>
			<font color="red">身份证信息录入 </font>
		</h2>

		<div>
			<img alt="" src="http://www.uugty.com:100/${user.userIdentity}"
				width="60%" height="40%">
		</div>
		<div>
			<form
				action="<%=request.getContextPath()%>/validate/validateIdentifyAdopt.do"
				method="get">
				<input type="hidden" name="userId" value="${user.userId}"> <input
					type="hidden" name="currentPage" value="${user.currentPage}">
				<input type="hidden" name="userIdValidate"
					value="${user.userIdValidate}"> <input type="hidden"
					name="userName" value="${user.userName}"> <input
					type="hidden" name="userTel" value="${user.userTel}"> <input
					type="hidden" name="type" value="${user.type}">
				<table style="color: green;">
					<tr>
						<td>真实姓名</td>
						<td><input name="userRealname" value="" type="text"></td>
					</tr>
					<tr>
						<td>生日</td>
						<td><input name="userBirthday" value="" type="text"></td>
					</tr>
					<tr>
						<td>性别</td>
						<td><select name="userSex">
								<option value="1">&nbsp&nbsp男&nbsp&nbsp</option>
								<option value="2">&nbsp&nbsp女&nbsp&nbsp</option>
						</select></td>
					</tr>
					<tr>
						<td>身份证号</td>
						<td><input name="userIdentityCard" value="" type="text"></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input value="录入"
							type="submit"></td>
					</tr>
				</table>
			</form>
		</div>
	</center>
</body>
</html>