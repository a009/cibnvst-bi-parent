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

		$("#vst_role_id").formValidator( {
			onshow : "请选择角色名称！",
			onfocus : "角色名称必须选择！",
			oncorrect : "验证通过！"
		}).inputValidator( {
			min : 1,
			onerror : "角色名称必须选择！"
		});
		
		$("#vst_module_id").formValidator( {
			onshow : "请选择模块名称！",
			onfocus : "模块名称必须选择！",
			oncorrect : "验证通过！"
		}).inputValidator( {
			min : 1,
			onerror : "模块名称必须选择！"
		});
		
		/*$("[name='vst_button_id']").formValidator( {
			onshow : "请选择按钮名称！",
			onfocus : "按钮名称必须选择！",
			oncorrect : "验证通过！"
		}).inputValidator( {
			min : 1,
			onerror : "按钮名称必须选择！"
		});*/
	});

	// 提交处理
	function doSubmit() {
		$("#btnSubmit").attr("disabled", "disabled");
		return true;
	}
	
	// 搜索角色
	function selectRole(){
		//角色搜索框
		var roleName = $("#roleName").val();
		//获取所有角色
		var roles = '${roles}';
		roles = roles.substring(1,roles.length-1);
		var roleArr = roles.split(", ");
		
		var rolesHtml = "<option value=''>请选择</option>"
		$("#vst_role_id").empty();
		
		if(roleName == ''){	//如果为空，显示所有角色
			for(var i=0; i<roleArr.length; i++){
				var role = roleArr[i];
				var result = role.split("=");
				var key = result[0];
				var value = result[1];
				
				rolesHtml += "<option value='"+key+"'>"+value+"</option>";
			}
		}else{	//搜索相应角色
			for(var i=0; i<roleArr.length; i++){
				var role = roleArr[i];
				var result = role.split("=");
				var key = result[0];
				var value = result[1];
				
				if(value.indexOf(roleName) >= 0){
					rolesHtml += "<option value='"+key+"'>"+value+"</option>";
				}
			}
		}
		$("#vst_role_id").html(rolesHtml);
	}
	
	// 搜索模块
	function selectModule(){
		//模块搜索框
		var moduleName = $("#moduleName").val();
		//获取所有模块
		var modules = '${modules}';
		modules = modules.substring(1,modules.length-1);
		var moduleArr = modules.split(", ");
		
		var modulesHtml = "<option value=''>请选择</option>"
		$("#vst_module_id").empty();
		
		if(moduleName == ''){	//如果为空，显示所有模块
			for(var i=0; i<moduleArr.length; i++){
				var module = moduleArr[i];
				var result = module.split("=");
				var key = result[0];
				var value = result[1];
				
				modulesHtml += "<option value='"+key+"'>"+value+"</option>";
			}
		}else{	//搜索相应模块
			for(var i=0; i<moduleArr.length; i++){
				var module = moduleArr[i];
				var result = module.split("=");
				var key = result[0];
				var value = result[1];
				
				if(value.indexOf(moduleName) >= 0){
					modulesHtml += "<option value='"+key+"'>"+value+"</option>";
				}
			}
		}
		$("#vst_module_id").html(modulesHtml);
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper">
			<div class="content-roll">
				<form id="listForm" action="${ctx}/sysPermission/addPermission.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统权限新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<table id='example2' class="line03">
								<tr>
									<td width="20%" class="tright">角色名称：</td>
									<td width="40%">
										<select class="input" id="vst_role_id" name="vst_role_id">
						 					<option value="">请选择</option>
						 					<c:forEach items="${roles}" var="role">
						 						<option value="${role.key }">${role.value }</option>
						 					</c:forEach>
						 				</select>
										<span class="red">*</span>
										
										<span style="color: blue;">角色搜索：</span>
										<input type="text" id="roleName" onblur="selectRole();" />
									</td>
									<td width="28%">
										<span id="vst_role_idTip"></span>
									</td>
								</tr>
								<tr>
									<td class="tright">模块名称：</td>
									<td>
										<select class="input" id="vst_module_id" name="vst_module_id">
						 					<option value="">请选择</option>
						 					<c:forEach items="${modules}" var="module">
						 						<option value="${module.key }">${module.value }</option>
						 					</c:forEach>
						 				</select>
										<span class="red">*</span>
										
										<span style="color: blue;">模块搜索：</span>
										<input type="text" id="moduleName" onblur="selectModule();" />
									</td>
									<td>
										<span id="vst_module_idTip"></span>
									</td>
								</tr>
								<tr>
									<td class="tright">按钮名称：</td>
									<td>
										<table>
											<tr>
												<td>
													<input type="checkbox" name="vst_button_id" value="0" />查询
												</td>
											</tr>
							 				<c:forEach items="${buttons}" var="button" varStatus="i">
												<c:if test="${i.index % 5 == 0}"><tr></c:if>
												<td>
													<input type="checkbox" name="vst_button_id" value="${button.key }" />${button.value }
												</td>
												<c:if test="${i.index % 5 == 4}"></tr></c:if>
											</c:forEach>
										</table>
									</td>
									<td>
										<span id="vst_button_idTip"></span>
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