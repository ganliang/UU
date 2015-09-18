<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>路线审核</title>
</head>
<body>
	<center>
		<h1>
			<font color="red"> 路线审核</font>
		</h1>
		<form action="<%=request.getContextPath()%>/roadline/validateCheck.do"
			method="post">
			<input type="hidden" name="roadlineId" value="${roadline.roadlineId}">
			<input type="hidden" name="roadlineTitle"
				value="${roadline.roadlineTitle}"> <input type="hidden"
				name="roadlineContent" value="${roadline.roadlineContent}">
			<input type="hidden" name="roadlineGoalArea"
				value="${roadline.roadlineGoalArea}"> <input type="hidden"
				name="userId" value="${roadline.userId}">
			<table>
				<tr>
					<td>审核原因</td>
					<td><input type="text" name="validateResaon" value=""></td>
				</tr>
				<tr>
					<td align="center"><input type="submit" value="审核成功"
						name="type"></td>
					<td align="center"><input type="submit" value="审核失败"
						name="type"></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>