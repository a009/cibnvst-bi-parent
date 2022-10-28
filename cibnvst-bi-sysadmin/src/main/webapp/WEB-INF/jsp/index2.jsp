<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="share/taglib.jsp" %>
<%@include file="share/prefix.jsp" %>
<head>
<title>报表后台中心</title>

<!-- Theme style -->
<link rel="stylesheet" href="${ctx}/pub/listPages/css/listCommon.css">

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<%@include file="share/header.jsp" %>
	<%@include file="share/leftMenu.jsp"%>
    <%@ include file="share/sharForm.jsp" %>
	
    <div class="content-wrapper" style="background-color: #ecf0f5;">
        <div class="listSlogans col-xs-12">欢迎进入CIBN&VST报表后台！</div>
    </div>
    
    <%@include file="share/footer.jsp" %>
    <div class="control-sidebar-bg"></div>
</div>
</body>
</html>
