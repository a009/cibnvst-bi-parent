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
		
		$("#oldPass").formValidator( {
			defaultvalue:$(this).val(),
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :6,
			empty : {
			leftempty :false,
			rightempty :false,
			emptyerror :"旧密码不能为空！"
			},
			onerror :"旧密码的长度在6~15位，不能为空！"
		}).ajaxValidator( {
			type :"post",
			url :"${ctx}/sysUser/checkOldPassword.action",
			datatype :"json",
			success : function(data) {
				return data;
			},
			buttons :$(".button"),
			error : function() {
				alert("服务器繁忙，请稍后再试！");
			},
			onerror :"密码错误，请重新输入！",
			onwait :"服务器正在进行校验，请耐心等待..."
		});

		$("#newPass").formValidator( {
			defaultvalue:$("#newPass").val(),
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :6,
			max :15,
			onerror :"格式错误！密码长度在6~15个字符！"
		}).regexValidator({
			regexp :"password",
			datatype:"enum",
			onerror :"密码不能全是数字或者字母！"
		}).compareValidator( {
			desid :"oldPass",
			operateor :"!=",
			onerror :"新密码和旧密码相同，请重新换一个！"
		});
		
		$("#renewPass").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :6,
			max :15,
			onerror :"格式错误！密码长度在6~15个字符！"
		}).compareValidator( {
			desid :"newPass",
			operateor :"=",
			onerror :"确认密码和新密码不相同！请确认"
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
				<form id="listForm" action="${ctx}/sysUser/savePassword.action" method="post">
					<input type="hidden" name="vst_sys_id" value="${formMap.vst_sys_id }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 修改密码</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">旧密码：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="password" class="input" id="oldPass" name="oldPassword" placeholder="请输入旧密码" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="oldPassTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">新密码：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="password" class="input" id="newPass" name="vst_sys_password" placeholder="请输入新密码" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="newPassTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">确认密码：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="password" class="input" id="renewPass" placeholder="请输入确认密码" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="renewPassTip"></span>
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