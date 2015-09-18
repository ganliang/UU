<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示所有的订单</title>
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
	function roadlineCheck(roadlineId, userId, roadlineTitle, roadlineGoalArea,
			roadlineContent) {
		var iWidth = 1000; //弹出窗口的宽度;
		var iHeight = 800; //弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
		window
				.open(
						"roadlineCheck.do?roadlineId=" + roadlineId
								+ "&userId=" + userId + "&roadlineTitle="
								+ roadlineTitle + "&roadlineGoalArea="
								+ roadlineGoalArea + "&roadlineContent="
								+ roadlineContent + "",
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
		<form action="<%=request.getContextPath()%>/order/getAllOrder.do"
			method="get">
			<input type="hidden" name="type" value="avatar">
			<table>
				<tr>
					<td>订单状态:</td>
					<td><select name="orderStatus">
							<c:choose>
								<c:when test="${orderStatus=='review'}">
									<option value="order_create" selected="selected">已下单</option>
								</c:when>
								<c:otherwise>
									<option value="order_create">已下单</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_no_pay_cancel'}">
									<option value="order_no_pay_cancel" selected="selected">未付款取消
									</option>
								</c:when>
								<c:otherwise>
									<option value="order_no_pay_cancel">未付款取消</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_invalid'}">
									<option value="order_invalid" selected="selected">已失效
									</option>
								</c:when>
								<c:otherwise>
									<option value="order_invalid">已失效</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_payment'}">
									<option value="order_payment" selected="selected">已支付
									</option>
								</c:when>
								<c:otherwise>
									<option value="order_payment">已支付</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_not_agree_cancel'}">
									<option value="order_not_agree_cancel" selected="selected">付款后
										导游未同意前取消</option>
								</c:when>
								<c:otherwise>
									<option value="order_not_agree_cancel">付款后 导游未同意前取消</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_deny'}">
									<option value="order_deny" selected="selected">已拒绝</option>
								</c:when>
								<c:otherwise>
									<option value="order_deny">已拒绝</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_agree'}">
									<option value="order_agree" selected="selected">已接受</option>
								</c:when>
								<c:otherwise>
									<option value="order_agree">已接受</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_agree_cancel'}">
									<option value="order_agree_cancel" selected="selected">付款后，
										导游同意后取消</option>
								</c:when>
								<c:otherwise>
									<option value="order_agree_cancel">付款后， 导游同意后取消</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_success'}">
									<option value="order_success" selected="selected">订单完成
										付款</option>
								</c:when>
								<c:otherwise>
									<option value="order_success">订单完成 付款</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_drawback'}">
									<option value="order_drawback" selected="selected">退款中</option>
								</c:when>
								<c:otherwise>
									<option value="order_drawback">退款中</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_drawback_failure'}">
									<option value="order_drawback_failure" selected="selected">退款拒绝
										，失败</option>
								</c:when>
								<c:otherwise>
									<option value="order_drawback_failure">退款拒绝 ，失败</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_drawback_success'}">
									<option value="order_drawback_success" selected="selected">退款完成
									</option>
								</c:when>
								<c:otherwise>
									<option value="order_drawback_success">退款完成</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${orderStatus=='order_pay_cancel'}">
									<option value="order_pay_cancel" selected="selected">支付失败
									</option>
								</c:when>
								<c:otherwise>
									<option value="order_pay_cancel">支付失败</option>
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
				<th width="5%">序号</th>
				<th width="8%">头像</th>
				<th width="10%">手机号码</th>
				<th width="5%">昵称</th>
				<th width="10%">身份证号码</th>

				<th width="5%">订单号</th>
				<th width="5%">订单标签</th>
				<th width="5%">订单时间</th>
				<th width="5%">订单价格</th>

				<th width="5%">退款原因</th>
				<th width="5%">退款金额</th>
				<th width="5%">退款时间</th>

				<th width="5%">订单生成日期</th>
			</tr>
			<c:forEach items="${orderList}" var="order" varStatus="status">
				<tr style="height: 40px">
					<td align="center">${status.count }</td>
					<td align="center"><img alt="" ondblclick="expandImage(this)"
						src="http://www.uugty.com:100/${order.userAvatar}" width="80px"
						height="60px"></td>
					<td align="center">${order.userTel }</td>
					<td align="center">${order.userName }</td>
					<td align="center">${order.userIdentityCard }</td>

					<td align="center">${order.orderNo }</td>
					<td align="center">${order.orderMark }</td>
					<td align="center">${order.orderTime }</td>
					<td align="center">${order.orderPrice }</td>

					<td align="center">${order.orderDrawbackReason }</td>
					<td><c:choose>
							<c:when test="${order.orderDrawbackMoney==0.0}"></c:when>
							<c:otherwise>${order.orderDrawbackMoney }</c:otherwise>
						</c:choose></td>
					<td align="center">${order.orderDrawbackDate }</td>

					<td align="center">${order.orderCreateDate }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="height: 20%; margin-top: 5px;">
		当前页:${page.currentPage } 总页数${page.totalPage } 总记录数${page.totalSize }
		<c:choose>
			<c:when test="${page.currentPage>1}">
				<a
					href="<%=request.getContextPath()%>/order/getAllOrder.do?currentPage=${page.currentPage-1}&orderStatus=${orderStatus}">上一页</a>
			</c:when>
			<c:otherwise>上一页</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${page.currentPage<page.totalPage  }">
				<a
					href="<%=request.getContextPath()%>/order/getAllOrder.do?currentPage=${page.currentPage+1}&orderStatus=${orderStatus}">下一页</a>
			</c:when>
			<c:otherwise>下一页</c:otherwise>
		</c:choose>
	</div>
</body>
</html>