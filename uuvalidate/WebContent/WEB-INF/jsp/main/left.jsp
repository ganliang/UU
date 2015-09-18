<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>左边框</title>
<style type="text/css">
#photoDiv {
	margin: 0px;
	padding: 0px;
}

#photoDiv ul {
	list-style: none;
	margin-left: -20px;
	margin-top: -15px;
}

#photoDiv ul li {
	width: 80px;
	background-color: silver;
	margin-bottom: 10px;
	border: 2px solid silver;
	margin-bottom: 10px;
	margin-bottom: 10px;
}

#photoDiv ul li a {
	text-decoration: none;
}

#photoDiv ul li a:hover {
	color: red;
	cursor: pointer;
}
</style>
<script type="text/javascript">
	var number = 0;
	function showValidate() {
		number++;
		var doms = document.getElementById("validate");
		if (number % 2 == 0) {
			doms.style.display = "none";
			number = 0;
		} else {
			doms.style.display = "block";
		}
	}

	function roadlineValidate() {
		number++;
		var doms = document.getElementById("roadlineValidate");
		if (number % 2 == 0) {
			doms.style.display = "none";
			number = 0;
		} else {
			doms.style.display = "block";
		}
	}

	function withdraw() {
		number++;
		var doms = document.getElementById("withdrawCach");
		if (number % 2 == 0) {
			doms.style.display = "none";
			number = 0;
		} else {
			doms.style.display = "block";
		}
	}
	function order() {
		number++;
		var doms = document.getElementById("orderCommit");
		if (number % 2 == 0) {
			doms.style.display = "none";
			number = 0;
		} else {
			doms.style.display = "block";
		}
	}
	function promote() {
		number++;
		var doms = document.getElementById("promoteCommit");
		if (number % 2 == 0) {
			doms.style.display = "none";
			number = 0;
		} else {
			doms.style.display = "block";
		}
	}
	function changeColor(obj) {

	}
</script>
</head>
<body onload="showValidate()">
	<div id="photoDiv" align="left">
		<h3 id="showValidate" onclick="showValidate()">
			<b style="background-color: silver">证件审核</b>
		</h3>
		<ul id="validate">
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/validate/identity.do?currentPage=1"
				target="main">身份证审核</a></li>
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/validate/certificate.do?currentPage=1"
				target="main">学历证审核</a></li>
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/validate/tourcard.do?currentPage=1"
				target="main">导游证审核</a></li>
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/validate/avatar.do?currentPage=1"
				target="main">头像审核</a></li>
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/validate/car.do?currentPage=1"
				target="main">车辆审核</a></li>
		</ul>

		<h3 id="roadline" onclick="roadlineValidate()">
			<b style="background-color: silver">路线审核</b>
		</h3>
		<ul id="roadlineValidate">
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/roadline/showAllRoadline.do?currentPage=1&roadlineStatus=review"
				target="main">路线审核</a></li>
		</ul>

		<h3 id="withdraw" onclick="withdraw(this)">
			<b style="background-color: silver">用户提现</b>
		</h3>
		<ul id="withdrawCach">
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/withdraw/getAllWithdraw.do?currentPage=1&withdrawStatus=1"
				target="main">提现列表</a></li>
		</ul>

		<h3 id="order" onclick="order(this)">
			<b style="background-color: silver">订单成交</b>
		</h3>
		<ul id="orderCommit">
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/order/getAllOrder.do?currentPage=1&orderStatus=order_success"
				target="main">订单成交</a></li>
		</ul>

		<h3 id="promote" onclick="promote(this)">
			<b style="background-color: silver">推广管理</b>
		</h3>
		<ul id="promoteCommit">
			<li><a onclick="changeColor(this)"
				href="<%=request.getContextPath()%>/promote/getAllPromoteUser.do?currentPage=1"
				target="main">推广管理</a></li>
		</ul>
	</div>
</body>
</html>