<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script language=javascript>
	parent.document.title = "UU旅游后台图片审核项目";
</script>
<frameset rows="13%,*,2%" frameborder="yes" border="1">
	<frame src="<%=request.getContextPath()%>/layout/top.do"
		frameborder="1" title="UU旅游后台图片审核项目" />
	<frameset cols="15%,*">
		<frame src="<%=request.getContextPath()%>/layout/left.do" />
		<frame src="<%=request.getContextPath()%>/layout/main.do" name="main" />
	</frameset>
	<frame src="<%=request.getContextPath()%>/layout/bottom.do" />
</frameset>
