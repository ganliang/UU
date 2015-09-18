<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>证件审核详情</title>
</head>
<body style="background-color: silver">
	<center>
		<table border="1px">
			<c:forEach items="${describeImages}" var="describeImage">
				<tr style="height: 100px">
					<td align="center"><img alt="" src="${describeImage } "
						height="200px"></td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>