<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>报表管理中心</title>
<style>
/**常用网址**/
.tool-title{
	font-size:20px;
}
.tool-webImg{
	width:200px;
	height:100px;
	margin-bottom:10px;
}
table tbody tr td{
	text-align:center;
	float:left;
	margin-left:10px;
	margin-bottom:10px;
}
.web-line{
	border-bottom:1px solid #ddd;
	margin:0 10px;
}
a:hover{
	color:#00a7d0;
	text-decoration: underline;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form action="" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 常用工具 > 常用网址</span>
					</section>
	
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box ">
								<div class="box-header">
									<i class="fa  fa-internet-explorer"></i>
									<span class="tool-title">基本网址</span>
								</div>
	
								<div class="box-body">
									
									<table>
										<c:forEach items="${basicUrls}" var="url" varStatus="st">
											<c:if test="${st.index % 8 == 0}"><tr></c:if>
											<td>
												<a href="${url.vst_url_address }" target="_black" title="${url.vst_url_address }">
													<img class="tool-webImg" src="${url.vst_url_img }" alt=""/>
												</a><br/>
												<a href="${url.vst_url_address }" target="_black">${url.vst_url_name }</a>
											</td>
											<c:if test="${st.index % 8 == 7}"></tr></c:if>
										</c:forEach>
									</table>
								</div>
								
								<div class="web-line"></div>
								
								<div class="box-header">
									<i class="fa  fa-internet-explorer"></i>
									<span class="tool-title">VST网址</span>
								</div>
								<div class="box-body">
									<table>
										<c:forEach items="${vstUrls}" var="url" varStatus="st">
											<c:if test="${st.index % 8 == 0}"><tr></c:if>
											<td>
												<a href="${url.vst_url_address }" target="_black" title="${url.vst_url_address }">
													<img class="tool-webImg" src="${url.vst_url_img }" alt=""/>
												</a><br/>
												<a href="${url.vst_url_address }" target="_black">${url.vst_url_name }</a>
											</td>
											<c:if test="${st.index % 8 == 7}"></tr></c:if>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</div>
					</section>
				</form>
			</div>
		</div>
		<%@include file="../share/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
</script>
</html>