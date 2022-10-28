<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@include file="share/taglib.jsp"%>
<%@include file="share/prefix.jsp"%>
<head>
<title>提示信息</title>
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
	function turnTo() {
		$("#infoForm").attr("action", "${ctx}/${returnUrl}");
		$("#infoForm").submit();
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="share/header.jsp"%>
		<%@include file="share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form id="infoForm" method="post" class="clearfix">
					<%@ include file="share/sharForm.jsp" %>
					<div class="main-wrap clearfix">
						<div class="mesleft"><img src="${ctx}/pub/images/info.png"  width="49px" height="49px"/></div>
			  			<div class="main">
			  	 			<h1>提示信息</h1>
							<p>信息码:${returnCode}</p>
							<p>描 述:${returnInfo}</p>
							<p style="margin-top: 5px;"><span style="color: #000;">现在您可以:</span><a href="####" onclick="turnTo();">返回</a></p>
			   			</div>
					</div>
		   		</form>
			</div>
		</div>
	</div>
</body>

</html>