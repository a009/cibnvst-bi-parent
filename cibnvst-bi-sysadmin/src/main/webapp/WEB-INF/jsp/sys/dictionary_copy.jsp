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
		
		$("#dataType").formValidator({
			onshow :"请选择数据类型",
			onfocus :"数据类型必须选择！",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :1,
			onerror :"数据类型必须选择！"
		});

		$("#dataValue").formValidator({
			onshow :"请填写数据值",
			onfocus :"数据值必须填写！",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :1,
			max :100,
			onerror :"数据值不可为空，且最大长度为100个字符！"
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
				<form id="listForm" action="${ctx}/dictionary/copyDictionary.action" method="post">
					<input type="hidden" name="ids" value="${requestScope.ids }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 字典管理 > 复制</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<div style="color: red;">
								功能介绍：将选中的对象进行复制，只修改所选数据类型的值！
							</div>
							<table id='example2' class="line03">
								<tr>
									<td width="20%" class="tright">
										数据类型：
									</td>
									<td width="25%">
										<select class="input" id="dataType" name="dataType">
											<option value="">请选择</option>
											<option value="1">表名</option>
											<option value="2">字段名</option>
											<option value="3">包名</option>
										</select>
										<span class="red">*</span>
									</td>
									<td width="28%">
										<span id="dataTypeTip"></span>
									</td>
								</tr>
								<tr>
									<td width="20%" class="tright">
										数据值：
									</td>
									<td width="25%">
										<input class="input" id="dataValue" name="dataValue" />
										<span class="red">*</span>
									</td>
									<td width="28%">
										<span id="dataValueTip"></span>
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
