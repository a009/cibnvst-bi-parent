<%@ page language="java" isELIgnored="false" pageEncoding="utf-8"%>
<html>
  <head>
    
    <title>退出</title>
	<meta http-equiv='pragma' content='no-cache'> 
	<meta http-equiv='Cache-Control' content='no-cache, must-revalidate'> 
	<meta http-equiv='expires' content='0'>
	<script type="text/javascript">
		var logoutFlag="<%=request.getAttribute("logoutFlag")%>";
		if (logoutFlag!="normalExit"){
			alert("您登录超时,或被强制下线!");
		}
		//top.window.location="${ctx}/";
		top.window.location="${pageContext.request.contextPath}/";
	</script>
  </head>
  
  <body>
  </body>
</html>
