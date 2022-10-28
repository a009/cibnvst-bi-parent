<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});
		
		$("#targetCode").formValidator({
			onshow :"请填写代码编号",
			onfocus :"代码编号必须填写！",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :1,
			max :100,
			onerror :"代码编号不可为空，且最大长度为100个字符！"
		});
	});

	// 提交处理
	function doSubmit() {
		$("#btnSubmit").attr("disabled", "disabled");
		return true;
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper">
			<div class="content-roll">
				<form id="listForm" action="${ctx}/autoCondition/copyCondition.action" method="post">
					<input type="hidden" name="conditionIds" value="${requestScope.conditionIds }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 自动化管理管理 > 自动化(条件) > 复制</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<div style="color: red;">
								功能介绍：将选中的对象进行复制，只修改代码编号！
							</div>
							<table id='example2' class="line03">
								<tr>
									<td width="20%" class="tright">
										目标代码编号：
									</td>
									<td width="25%">
										<input class="input" id="targetCode" name="targetCode" />
										<span class="red">*</span>
									</td>
									<td width="28%">
										<span id="targetCodeTip"></span>
									</td>
								</tr>
							</table>
						</div>
						<%@ include file="../share/submitButton.jsp"%>
					</div>
					</section>
				</form>
			</div>
		</div>
		<%@include file="../share/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
</html>
