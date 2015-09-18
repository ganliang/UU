<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加推广人员</title>
<style type="text/css">
a {
	text-decoration: none;
}
</style>
</head>
<body style="background-color: silver">
	<center>
		<h1>
			<font color="red">添加推广人员</font>
		</h1>
		<form action="<%=request.getContextPath()%>/promote/addPromoteUser.do"
			method="post">
			<table>
				<tr>
					<td>推广人员姓名</td>
					<td><input type="text" name="promoteUserName"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="添加">
					</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>