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
		
		$("#vst_sys_password").formValidator( {
			onshow:"请输入重置的新密码",
			onfocus:"重置密码至少为6位，不超过15位！",
			oncorrect:"验证通过！"
		}).inputValidator( {
			min:6,
			max:15,
			onerror :"密码长度在6~15个字符之间，包括6和15！"
		}).regexValidator({
			regexp:"^\\S{6,15}$",
			datatype:"string",
			onerror:"格式错误！"
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
				<form id="listForm" action="${ctx}/sysUser/resetPassword.action" method="post">
					<input type="hidden" name="vst_sys_id" value="${formMap.vst_sys_id }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统用户 > 重置密码</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<table id='example2' class="line03">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">登录帐号：</div>
									<div class="add_m">
										<div class="add_m_l">
											${formMap.vst_sys_username }
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										
									</div>
								</li>
								<li>
									<div class="add_l">重置密码：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sys_password" name="vst_sys_password" value="${formMap.vst_sys_password }" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_passwordTip"></span>
									</div>
								</li>
							</ul>
							
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