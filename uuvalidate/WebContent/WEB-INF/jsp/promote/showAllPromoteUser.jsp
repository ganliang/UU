<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示所有的路线</title>
<style type="text/css">
a {
	text-decoration: none;
}
</style>
<script type="text/javascript">
	function expandImage(obj) {
		//alert(obj.src);
		var iWidth = 500; //弹出窗口的宽度;
		var iHeight = 300; //弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
		window
				.open(
						"showImage.do?imageURL=" + obj.src + "",
						"_blank",
						'height='
								+ iHeight
								+ ',,innerHeight='
								+ iHeight
								+ ',width='
								+ iWidth
								+ ',innerWidth='
								+ iWidth
								+ ',top='
								+ iTop
								+ ',left='
								+ iLeft
								+ ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	}
</script>
</head>
<body style="background-color: silver">


	<div style="float: right;">
		<span style="color: red; display: block;"> <a
			href="<%=request.getContextPath()%>/promote/toAddPromoteUser.do">添加用户</a></span>
	</div>
	<div style="height: 80%; margin-top: 5px;">
		<table border="1px" width="94%">
			<tr>
				<th width="5%">序号</th>
				<th width="10%">推广人员ID</th>
				<th width="10%">推广人员姓名</th>
				<th width="30%">二维码</th>
				<th width="30%">APK地址</th>
				<th width="10%">更新</th>
			</tr>
			<c:forEach items="${promoteUserList}" var="promoteUser"
				varStatus="status">
				<tr style="height: 40px">
					<td align="center">${status.count }</td>
					<td align="center">${promoteUser.promoteUserId }</td>
					<td align="center">${promoteUser.promoteUserName }</td>
					<td align="center"><img alt="" ondblclick="expandImage(this)"
						src="http://www.uugty.com:100/images/qrcode/${promoteUser.qrCode}"
						width="200px" height="60px"></td>
					<td align="center"><a
						href="http://www.uugty.com:100/apk/${promoteUser.apkDest}">APK下载</a></td>
					<td align="center"><a
						href="<%=request.getContextPath()%>/promote/refresh.do?promoteUserId=${promoteUser.promoteUserId }">更新</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="height: 20%; margin-top: 5px;">
		当前页:${page.currentPage } 总页数${page.totalPage } 总记录数${page.totalSize }
		<c:choose>
			<c:when test="${page.currentPage>1}">
				<a
					href="<%=request.getContextPath()%>/promote/getAllPromoteUser.do?currentPage=${page.currentPage-1}">上一页</a>
			</c:when>
			<c:otherwise>上一页</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${page.currentPage<page.totalPage  }">
				<a
					href="<%=request.getContextPath()%>/promote/getAllPromoteUser.do?currentPage=${page.currentPage+1}">下一页</a>
			</c:when>
			<c:otherwise>下一页</c:otherwise>
		</c:choose>
	</div>
</body>
</html>