<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@include file="share/taglib.jsp"%>
<%@include file="share/prefix.jsp"%>
<head>
<title>操作失败</title>
<style>
	#errorForm{
		width:100%;
		height:100%;
	}
	.main-wrap{
		width:620px;
		margin:300px auto 0;
	}
	.main-wrap .mesleft{
		float:left;
		padding:10px;
	}
	.main-wrap .main{
		float:left;
	}
	.main-wrap .main h1{
		margin:10px 0;
	}
	.main-wrap .main p{
		font-size:16px;
	}
</style>
<script>
	function turnTo(){
		var url = "${returnUrl}";
		if(url == ''){
			window.history.go(-1);
		}else{
			$("#errorForm").attr("action", "${ctx}/${returnUrl}");
			$("#errorForm").submit();
		}
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="share/header.jsp"%>
		<%@include file="share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form id="errorForm" method="post" class="clearfix">
					<%@ include file="share/sharForm.jsp" %>
					<div class="main-wrap clearfix">
						<div class="mesleft"><img src="${ctx}/pub/images/error.png"  width="49px" height="49px"/></div>
			  			<div class="main">
			  	 			<h1>失败信息</h1>
							<p>异常码:${returnCode}</p>
							<p>
								异常信息:<s:if test="returnCode==0">未知错误，请联系VST客服人员或VST技术人员帮您解决！</s:if><s:else>${returnInfo}</s:else>
							</p>
			   				<p style="margin-top:5px;"><span style=" color:#000;">现在您可以:</span><a href="#" onclick="turnTo();">返回</a></p>
			   			</div>
					</div>
		   		</form>
			</div>
		</div>
	</div>
</body>

</html>