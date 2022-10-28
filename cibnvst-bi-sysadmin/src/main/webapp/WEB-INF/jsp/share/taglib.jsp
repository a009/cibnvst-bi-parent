<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var ctx = '${ctx}';
	
	function downExcelHandler() {
		var flag = false;
		if(queryConditionChgListener() || "false" == $("#down_noQuery").val()) {
			alert("下载前，请先点击查询查看是否有符合下载的记录!");
		} else if("true" == $("#down_noRecord").val()) {
			alert("没有找到相关记录，请重新输入条件进行查询!");
		} else if("true" == $("#down_OverMax").val()) {
			alert("当前下载的记录数已超过系统默认最大值，请重新输入条件下载!");
		} else {
			flag = true;
			showDownExcelProgress();
		}
		return flag;
	}
</script>
