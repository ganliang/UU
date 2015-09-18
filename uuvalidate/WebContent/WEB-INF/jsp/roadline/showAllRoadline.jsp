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
		var iWidth = 1000; //弹出窗口的宽度;
		var iHeight = 800; //弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
		window
				.open(
						"/uuvalidate/validate/showImage.do?imageURL=" + obj.src
								+ "",
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
	function roadlineCheck(roadlineId, userId) {
		var iWidth = 1000; //弹出窗口的宽度;
		var iHeight = 800; //弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
		window
				.open(
						"roadlineCheck.do?roadlineId=" + roadlineId
								+ "&userId=" + userId,
						"_self",
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

	function confimAdopt() {
		return confirm("确定审核通过");
	}
	function confimReject() {
		return confirm("确定审核失败");
	}
</script>
</head>
<body style="background-color: silver">
	<div align="right">
		<form
			action="<%=request.getContextPath()%>/roadline/showAllRoadline.do"
			method="get">
			<input type="hidden" name="type" value="avatar">
			<table>
				<tr>
					<%-- <td>路线标题:</td>
					<td><input type="text" name="roadlineTitle" size="11"
						value="${roadlineTitle }" />&nbsp&nbsp</td>
					<td>路线目的地:</td>
					<td><input type="text" name="roadlineGoalArea" size="11"
						value="${roadlineGoalArea }" />&nbsp&nbsp</td> --%>
					<td>审核状态:</td>
					<td><select name="roadlineStatus">
							<c:choose>
								<c:when test="${roadlineStatus=='review'}">
									<option value="review" selected="selected">正在审核</option>
								</c:when>
								<c:otherwise>
									<option value="review">正在审核</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${roadlineStatus=='success'}">
									<option value="success" selected="selected">审核成功</option>
								</c:when>
								<c:otherwise>
									<option value="success">审核成功</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${roadlineStatus=='failure'}">
									<option value="failure" selected="selected">审核失败</option>
								</c:when>
								<c:otherwise>
									<option value="failure">审核失败</option>
								</c:otherwise>
							</c:choose>
					</select>&nbsp&nbsp</td>
					<td><input type="submit" size="10"
						style="background-color: red; size: 20px" value=" 查 询 ">&nbsp&nbsp&nbsp&nbsp</td>
				</tr>
			</table>

		</form>
	</div>
	<div style="height: 80%; margin-top: 5px;">
		<table border="1px" width="100%">
			<tr>
				<th width="2%">序号</th>
				<th width="8%">头像</th>
				<th width="5%">标题</th>
				<th width="5%">价格(CNY)</th>
				<th width="30%">内容</th>
				<th width="4%">目的地</th>

				<th width="4%">手机</th>
				<th width="4%">头像</th>
				<th width="4%">学历证</th>
				<th width="4%">身份证</th>
				<th width="4%">导游证</th>
				<th width="4%">车辆</th>

				<th width="8%">路线描述</th>
				<th width="8%">背景图片</th>
				<th width="6%">审核</th>
			</tr>
			<c:forEach items="${roadlineList}" var="roadline" varStatus="status">
				<tr style="height: 40px">
					<td align="center">${status.count }</td>
					<td align="center"><img alt="" ondblclick="expandImage(this)"
						src="http://www.uugty.com:100/${roadline.userAvatar}" width="80px"></td>
					<td align="center">${roadline.roadlineTitle }</td>
					<td align="center">${roadline.roadlinePrice }</td>
					<td align="center">${roadline.roadlineContent }</td>
					<td align="center">${roadline.roadlineGoalArea }</td>

					<td align="center"><c:choose>
							<c:when test="${roadline.userTelValidate ==0}">未审核</c:when>
							<c:when test="${roadline.userTelValidate ==1}">审核成功</c:when>
							<c:when test="${roadline.userTelValidate ==2}">审核失败</c:when>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${roadline.userAvatarValidate ==0}">未审核</c:when>
							<c:when test="${roadline.userAvatarValidate ==1}">审核成功</c:when>
							<c:when test="${roadline.userAvatarValidate ==2}">审核失败</c:when>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${roadline.userCertificateValidate ==0}">未审核</c:when>
							<c:when test="${roadline.userCertificateValidate ==1}">审核成功</c:when>
							<c:when test="${roadline.userCertificateValidate ==2}">审核失败</c:when>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${roadline.userIdValidate ==0}">未审核</c:when>
							<c:when test="${roadline.userIdValidate ==1}">审核成功</c:when>
							<c:when test="${roadline.userIdValidate ==2}">审核失败</c:when>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${roadline.userTourValidate ==0}">未审核</c:when>
							<c:when test="${roadline.userTourValidate ==1}">审核成功</c:when>
							<c:when test="${roadline.userTourValidate ==2}">审核失败</c:when>
						</c:choose></td>
					<td align="center"><c:choose>
							<c:when test="${roadline.userCarValidate ==0}">未审核</c:when>
							<c:when test="${roadline.userCarValidate ==1}">审核成功</c:when>
							<c:when test="${roadline.userCarValidate ==2}">审核失败</c:when>
						</c:choose></td>

					<td align="center"><a
						href="<%=request.getContextPath()%>/roadline/showDescribeImages.do?currentPage=${page.currentPage}&roadlineStatus=${roadlineStatus}&describeImages=${roadline.describeImages }"
						target="_blank">点击查看</a></td>
					<td align="center"><img alt="" ondblclick="expandImage(this)"
						src="http://www.uugty.com:100/images/roadlineDescribe/${roadline.roadlineBackground}"
						width="80px"></td>
					<td align="center"><c:choose>
							<c:when test="${roadlineStatus=='review'}">
								<a
									onclick="return roadlineCheck('${roadline.roadlineId}','${roadline.userId }')"
									href="#">审核</a>
							</c:when>
							<c:when test="${roadlineStatus=='success'}">
								<a onclick="return confimReject()"
									href="<%=request.getContextPath()%>/roadline/roadlineReject.do?currentPage=${page.currentPage}&roadlineStatus=${roadlineStatus}&roadlineId=${roadline.roadlineId }&userId=${roadline.userId}">失败</a>
							</c:when>
							<c:when test="${roadlineStatus=='failure'}">
								<a onclick="return confimAdopt()"
									href="<%=request.getContextPath()%>/roadline/roadlineAdopt.do?currentPage=${page.currentPage}&roadlineStatus=${roadlineStatus}&roadlineId=${roadline.roadlineId }&userId=${roadline.userId}">成功</a>
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="height: 20%; margin-top: 5px;">
		当前页:${page.currentPage } 总页数${page.totalPage } 总记录数${page.totalSize }
		<c:choose>
			<c:when test="${page.currentPage>1}">
				<a
					href="<%=request.getContextPath()%>/roadline/showAllRoadline.do?currentPage=${page.currentPage-1}&roadlineStatus=${roadlineStatus}">上一页</a>
			</c:when>
			<c:otherwise>上一页</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${page.currentPage<page.totalPage  }">
				<a
					href="<%=request.getContextPath()%>/roadline/showAllRoadline.do?currentPage=${page.currentPage+1}&roadlineStatus=${roadlineStatus}">下一页</a>
			</c:when>
			<c:otherwise>下一页</c:otherwise>
		</c:choose>
	</div>
</body>
</html>