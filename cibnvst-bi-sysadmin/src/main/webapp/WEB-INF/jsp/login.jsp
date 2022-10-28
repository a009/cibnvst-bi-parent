<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="share/taglib.jsp" %>
<head>
	<base href="<%=basePath%>">
	
	<title>报表后台登录</title>
	   
	<meta http-equiv="pragma" content="no-cache">	
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/pub/listPages/css/pintuer.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/pub/listPages/css/admin.css">
	<script src="${pageContext.request.contextPath}/pub/listPages/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/pub/listPages/js/pintuer.js"></script>
	
	<script type="text/javascript">
		$(function() {
			// 切换验证码
			$("#rcImg").click(function(){
				$(this).prop("src", "${pageContext.request.contextPath}/randomCode?"+(new Date()));
			});
			
			// 连续登录错误两次以上，显示验证码
			var loginTimes = "${sessionScope.longinTimes}";
			if(loginTimes != "" && parseInt(loginTimes) >= 2){
			 	$("#checkCodeId").show();
			}
		});
		
		// 登录验证
		function validLogin(){
			var loginTimes = "${sessionScope.longinTimes}";
			if(loginTimes != ""  && parseInt(loginTimes) >= 2){
				if($.trim($("#randomCode").val()) == ""){
		        	$("#randomCode").val("");
		        	$("#randomCode").focus();
		       		 return false;
		   	   }
			}
			return true;
		}
	</script>
</head>

<body>
	<div class="bg"></div>
	<div class="container">
	    <div class="line bouncein">
	        <div class="xs6 xm4 xs3-move xm4-move">
	            <div style="height:150px;"></div>
	            <div class="media media-y margin-big-bottom">
	            </div>         
				<form action="${pageContext.request.contextPath}/sysMain/login.action" method="post" id="loginFrom" onsubmit="return validLogin();">
	            <div class="panel loginbox">
	                <div class="text-center margin-big padding-big-top"><h1>CIBN报表统计后台</h1></div>
	                <div style="color: red; text-align: center;">${requestScope.errorMsg }</div>
	                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
	                    <div class="form-group">
	                        <div class="field field-icon-right">
								<input type="text" class="input input-big" name="vst_sys_username" placeholder="登录账号" data-validate="required:请填写账号" />
	                            <span class="icon icon-user margin-small"></span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <div class="field field-icon-right">
	                            <input type="password" class="input input-big" name="vst_sys_password" placeholder="登录密码" data-validate="required:请填写密码" />
	                            <span class="icon icon-key margin-small"></span>
	                        </div>
	                    </div>
	                    <div class="form-group" id="checkCodeId" style="display:none">
	                        <div class="field">
		                        <input type="text" class="input input-big" name="randomCode" id="randomCode" maxlength="4" placeholder="填写右侧的验证码" />
		                        <img src="${pageContext.request.contextPath}/randomCode" title="看不清，换一组" alt="" width="100" height="32" class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?new Date()'">   
	                        </div>
	                    </div>
	                </div>
	                <div style="padding:30px;"><input type="submit" class="button button-block bg-main text-big input-big" value="登录"></div>
	            </div>
	            </form>     
	        </div>
	    </div>
	</div>
</body>
</html>
