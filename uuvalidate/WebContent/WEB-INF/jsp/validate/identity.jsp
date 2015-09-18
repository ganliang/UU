<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>身份证审核</title>
<style type="text/css">
a {
	text-decoration: none;
}
</style>

<script type="text/javascript">
	function expandImage(obj) {
		//alert(obj.src);
		var iWidth = 1000; //弹出窗口的宽度;
		var iHeight = 800; //弹出窗口的高度;
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

		/* window.open("showImage.do?imageURL=" + obj.src + "", "_blank",
				"height=400,width=700,scrollbars=no,location=no"); */
	}
	function confimAdopt() {
		return confirm("确定审核通过");
	}
	function confimReject() {
		return confirm("确定审核失败");
	}
</script>
</head>
<body style="background-color: silver">
	<div style="height: 20%;">
		<div align="center">
			<h1>
				<font color="red">${model }</font>
			</h1>
		</div>
		<div align="right">
			<form
				action="<%=request.getContextPath()%>/validate/validateSearch.do"
				method="get">
				<input type="hidden" name="type" value="identity">
				<table>
					<tr>
						<td>姓名:</td>
						<td><input type="text" name="userName" size="11"
							value="${userName }" />&nbsp&nbsp</td>
						<td>手机号码:</td>
						<td><input type="text" name="userTel" size="11"
							value="${userTel }" />&nbsp&nbsp</td>
						<td>状态:</td>
						<td><select name="userIdValidate">
								<c:if test="${userIdValidate==0}">
									<option value="0" selected="selected">未审核</option>
								</c:if>
								<c:if test="${userIdValidate!=0}">
									<option value="0">未审核</option>
								</c:if>

								<c:if test="${userIdValidate==1}">
									<option value="1" selected="selected">正在审核</option>
								</c:if>
								<c:if test="${userIdValidate!=1}">
									<option value="1">正在审核</option>
								</c:if>

								<c:if test="${userIdValidate==2}">
									<option value="2" selected="selected">审核通过</option>
								</c:if>
								<c:if test="${userIdValidate!=2}">
									<option value="2">审核通过</option>
								</c:if>
						</select>&nbsp&nbsp</td>
						<td><input type="submit" size="10"
							style="background-color: red; size: 20px" value=" 查 询 ">&nbsp&nbsp&nbsp&nbsp</td>
					</tr>
				</table>

			</form>
		</div>
	</div>
	<div style="height: 80%; margin-top: 5px;">
		<table border="1px" width="100%">
			<tr>
				<th width="3%">序号</th>
				<th width="8%">姓名</th>
				<th width="5%">性别</th>
				<th width="5%">手机号码</th>
				<th width="5%">状态</th>
				<th width="15%">身份证</th>
				<th width="8%">最后登录的时间</th>
				<th width="5%">拒绝</th>
				<th width="5%">通过</th>
			</tr>
			<c:forEach items="${userlist}" var="user" varStatus="status">
				<tr style="height: 40px">
					<td align="center">${status.count }</td>
					<td align="center">${user.userName }</td>
					<td align="center"><c:choose>
							<c:when test="${user.userSex==1 }">男</c:when>
							<c:when test="${user.userSex==2 }">女</c:when>
							<c:otherwise>未知物种</c:otherwise>
						</c:choose></td>
					<td align="center">${user.userTel }</td>
					<td align="center"><c:choose>
							<c:when test="${user.userIdValidate==0 }">未审核</c:when>
							<c:when test="${user.userIdValidate==1 }">正在审核</c:when>
							<c:otherwise>审核通过</c:otherwise>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${user.userIdentity!=null}">
								<img alt="" ondblclick="expandImage(this)"
									src="http://www.uugty.com:100/${user.userIdentity}"
									width="200px" height="40px">
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose></td>
					<td align="center"><fmt:formatDate
							value="${user.userLastLoginDate }" pattern="yyyy-MM-dd" /></td>
					<td align="center"><c:choose>
							<c:when test="${user.userIdValidate==1}">
								<a onclick="return confimReject()"
									href="<%=request.getContextPath()%>/validate/validateReject.do?type=identity&userId=${user.userId}&currentPage=${page.currentPage}&userIdValidate=${user.userIdValidate}&userName=${userName}&userTel=${userTel}">拒绝</a>
							</c:when>
							<c:otherwise>拒绝</c:otherwise>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${user.userIdValidate==1}">
								<a target="_self"
									href="<%=request.getContextPath()%>/validate/toIdentifyAdopt.do?type=identity&userId=${user.userId}&currentPage=${page.currentPage}&userIdValidate=${user.userIdValidate}&userName=${userName}&userTel=${userTel}&userIdValidate=${userIdValidate}&userIdentity=${user.userIdentity}">通过</a>
							</c:when>
							<c:otherwise>通过</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="height: 20%; margin-top: 5px;">
		当前页:${page.currentPage } 总页数${page.totalPage } 总记录数${page.totalSize }
		<c:choose>
			<c:when test="${page.currentPage!=1}">
				<a
					href="<%=request.getContextPath()%>/validate/validateSearch.do?currentPage=${page.currentPage-1}&type=identity&userIdValidate=${userIdValidate}&userName=${userName}&userTel=${userTel}">上一页</a>
			</c:when>
			<c:otherwise>上一页</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${page.currentPage!=page.totalPage }">
				<a
					href="<%=request.getContextPath()%>/validate/validateSearch.do?currentPage=${page.currentPage+1}&type=identity&userIdValidate=${userIdValidate}&userName=${userName}&userTel=${userTel}">下一页</a>
			</c:when>
			<c:otherwise>下一页</c:otherwise>
		</c:choose>
	</div>
</body>
</html>