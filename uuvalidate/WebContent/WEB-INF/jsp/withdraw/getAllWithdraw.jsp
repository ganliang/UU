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
	function roadlineCheck(roadlineId) {
		var iWidth = 1000; //弹出窗口的宽度;
		var iHeight = 800; //弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
		window
				.open(
						"roadlineCheck.do?roadlineId=" + roadlineId + "",
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
			action="<%=request.getContextPath()%>/withdraw/getAllWithdraw.do"
			method="get">
			<table>
				<tr>
					<td>提现状态:</td>
					<td><select name="withdrawStatus">
							<c:choose>
								<c:when test="${withdrawStatus=='1'}">
									<option value="1" selected="selected">正在提现</option>
								</c:when>
								<c:otherwise>
									<option value="1">正在提现</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${withdrawStatus=='3'}">
									<option value="3" selected="selected">提现完成</option>
								</c:when>
								<c:otherwise>
									<option value="3">提现完成</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${withdrawStatus=='4'}">
									<option value="4" selected="selected">提现失败</option>
								</c:when>
								<c:otherwise>
									<option value="4">提现失败</option>
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
				<th width="15%">银行卡号</th>
				<th width="10%">所属银行</th>
				<th width="5%">金额(CYN)</th>
				<th width="5%">流水号</th>
				<th width="10%">提现日期</th>
				<th width="10%">提现</th>
			</tr>
			<c:forEach items="${WithdrawCashlist}" var="withdraw"
				varStatus="status">
				<tr style="height: 40px">
					<td align="center">${status.count }</td>
					<td align="center">${withdraw.withdrawBankCard }</td>
					<td align="center">
					<c:choose>
							<c:when test="${withdraw.withdrawBankCardType==1}">
								中国银行
							</c:when>
							<c:when test="${withdraw.withdrawBankCardType==2}">
								中国农业银行
							</c:when>
							<c:when test="${withdraw.withdrawBankCardType==3}">
								中国工商银行
							</c:when>
							<c:when test="${withdraw.withdrawBankCardType==4}">
								中国建设银行
							</c:when>
							<c:when test="${withdraw.withdrawBankCardType==5}">
								中国交通银行
							</c:when>
							<c:when test="${withdraw.withdrawBankCardType==6}">
								中国招商银行
							</c:when>
							<c:when test="${withdraw.withdrawBankCardType==7}">
								中国广大银行
							</c:when>
							<c:otherwise>--</c:otherwise>
						</c:choose>
					</td>
					<td align="center">${withdraw.withdrawMoney }</td>
					<td align="center">${withdraw.outTradeNo }</td>
					<td align="center">${withdraw.withdrawDate }</td>
					<td align="center"><c:choose>
							<c:when test="${withdrawStatus==1}">
								<a onclick="return confimAdopt()"
									href="<%=request.getContextPath()%>/withdraw/adoptWithdraw.do?currentPage=${page.currentPage-1}&withdrawStatus=${withdrawStatus}&withdrawId=${withdraw.withdrawId}&userId=${withdraw.userId}">成功</a>
								<a onclick="return confimReject()"
									href="<%=request.getContextPath()%>/withdraw/rejectWithdraw.do?currentPage=${page.currentPage-1}&withdrawStatus=${withdrawStatus}&withdrawId=${withdraw.withdrawId}&userId=${withdraw.userId}">失败</a>
							</c:when>
							<c:otherwise>--</c:otherwise>
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
					href="<%=request.getContextPath()%>/withdraw/getAllWithdraw.do?currentPage=${page.currentPage-1}&withdrawStatus=${withdrawStatus}">上一页</a>
			</c:when>
			<c:otherwise>上一页</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${page.currentPage<page.totalPage  }">
				<a
					href="<%=request.getContextPath()%>/withdraw/getAllWithdraw.do?currentPage=${page.currentPage+1}&withdrawStatus=${withdrawStatus}">下一页</a>
			</c:when>
			<c:otherwise>下一页</c:otherwise>
		</c:choose>
	</div>
</body>
</html>