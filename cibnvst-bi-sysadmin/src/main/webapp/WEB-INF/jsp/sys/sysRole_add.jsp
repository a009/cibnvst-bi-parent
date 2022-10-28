<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript" src="${ctx}/pub/js/dtree.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.formValidator.initConfig({
			formid:"listForm",
			wideword: false,
			onerror:function(msg){},
			onsuccess:isSelectRights
		});

		$("#vst_role_name").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :1,
			max :15,
			onerror :"角色名称不能为空，角色名称长度在1~15个字符之间！"
		}).regexValidator( {
			regexp :"rolename",
			datatype :"enum",
			onerror :"角色名称格式错误！"
		}).functionValidator({
			fun:ajaxCheckRoleName,
			onerror: "同一渠道下，角色名称不能重复，请换一个试试！"
		});
 
		$("#vst_role_summary").formValidator({
			empty :true,
			onshow :"",
			onfocus :"",
			oncorrect :"",
			onempty :"备注为空！"
		}).inputValidator( {
			max :512,
			onerror :"备注最长不能超过512个字符！"
		});
	});
	
	// 设置是否发送AJAX请求
	function isSend() {
		if($("#oldRoleName").val() == $("#vst_role_name").val()) {
			return false;
		} else {
			return true;
		}
	}
	
	// 是否存在同名AJAX请求处理
	function ajaxCheckRoleName() {
		var flag = true;
		if(isSend()) {
			$.ajax({
			   type: "POST",
			   url: "${ctx}/sysRole/ajaxCheckRoleName.action",
			   data: "roleName=" + $("#vst_role_name").val(),
			   async :false,	
		       cache : false,  		
		       dataType : "json",  //返回json数据
			   success: function(data){
					flag = ((String(data) == "true") ? false : true);
			   }
			});
		}
		return flag;
	}
	
	// 提交前判断是否为角色选择了权限
	function isSelectRights() {
		var flag = false;
		var domEles = $("input:not(:hidden)[type=checkbox][name=powerList][id!=root][checked]");
		flag = domEles.length > 0 ? true : false;
		//if(false == flag) {
			//alert("您还没有为该角色选择权限!");
		//} else {
			$("#btnAdd").attr("disabled", "disabled");
		//}
		//return flag;
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
				<form id="listForm" action="${ctx}/sysRole/addRole.action" method="post" focus="cutPage\\._param['vst_role_name']">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统角色新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">角色名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_role_name" name="vst_role_name" placeholder="请输入角色名称，不可重复" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_role_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_role_summary" name="vst_role_summary"
												style="resize: none; height: 100px;" placeholder="请输入备注"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_role_summaryTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">角色权限：</div>
									<div class="add_m">
										<div class="add_m_l">
											<script type="text/javascript">
												d = new dTree('d','${ctx}');
												d.add(0,-1,'角色权限选择','','','','','','',true,'root', 'root','','全选','','');
												<c:forEach items="${requestScope.modules}" var="p">
													d.add('${p.moduleId}',0,'${p.moduleName}','javascript:selectPowerChb(\'${p.moduleId}\');','','','','','',true,'powerList','${p.moduleId}','${p.moduleId}_0_0','0','','selectPowerChb(\'${p.moduleId}\')');
													<c:forEach items="${p.moduleList}" var="c">
														d.add('${c.moduleId}','${p.moduleId}', '${c.moduleName}','javascript:selectPowerChb(\'${c.moduleId}\');','','','','','',true,'powerList','${c.moduleId}','${p.moduleId}_${c.moduleId}_0','${p.moduleId}','','selectPowerChb(\'${c.moduleId}\')');
														<c:forEach items="${c.buttonList}" var="button">
															d.add('${button.vst_button_id}','${c.moduleId}', '${button.vst_button_name}' ,'javascript:selectPowerChb(\'${button.vst_button_id}\');','','','','','',true,'powerList','${button.vst_button_id}','${p.moduleId}_${c.moduleId}_${button.vst_button_id}','${c.moduleId}','','selectPowerChb(\'${button.vst_button_id}\')');
														</c:forEach>
													</c:forEach>
							 					</c:forEach>
							 					document.write(d);
											</script>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										
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
	
<script type="text/javascript">
	$(function() {
		$("#root").click(function(){
			$("input:checkbox[name=powerList]").prop("checked",$(this).prop("checked"));
		});
	});	
		
	function selectPowerName(powerId){
		document.getElementById(powerId).click();
	}

	function selectPowerChb(powerId){
		var chb = document.getElementById(powerId);
		if(chb.alt!=0)
			selectPower(chb);
		selectSubPower(chb);
	}
	
	function selectPower(obj){
		if(obj.alt==0) return;
		var pObj = document.getElementById(obj.alt);
		pObj.indeterminate=true;
		pObj.checked=true;
			
		var allElement = document.getElementsByTagName('input');
		var checkFlag = true;
		for(var i=0;i<allElement.length;i++){
			if(allElement[i].type=="checkbox"){
				if(allElement[i].alt==pObj.id){
					if(!allElement[i].checked){
							checkFlag=false;
							break;
					}
				}
			}
		}
		
		if(checkFlag){
			pObj.indeterminate=false;
			pObj.checked=true;
		}
		selectPower(pObj);
	}
	
	function selectSubPower(obj){
		var allElement = document.getElementsByTagName('input');
		for(var i=0;i<allElement.length;i++){
			if(allElement[i].type=="checkbox"){
				if(allElement[i].alt==obj.id){
					if(obj.checked)
						allElement[i].checked=true;
					else
						allElement[i].checked=false;
					selectSubPower(allElement[i]);
				}
			}
		}
	}
</script>
</body>
</html>