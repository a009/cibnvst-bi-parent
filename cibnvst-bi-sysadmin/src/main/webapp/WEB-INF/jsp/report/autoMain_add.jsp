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
		
		$("#vst_code").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 100,
			onerror : "代码编号不可为空，且最大长度为100个字符"
		}).functionValidator({
			fun :checkCode,
			onerror :"代码编号已存在，换一个试试！"
		});
		
		$("#vst_main_script").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "脚本不可为空！"
		});
		
		$("#vst_main_index").formValidator({
	  		onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator({
			min : 1,
			max : 11,
			onerror:"排序格式错误！至少需要一位数字！"
		}).regexValidator({
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "排序格式错误，必须为纯数字，不能带有小数！"
		});
		
		$("#vst_main_summary").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 255,
			onerror : "描述最大长度为255个字符！"
		});

		// 分页 时，显示分页脚本
		if($("#vst_main_isPaging").val() == 1){
			$("#countScript_li").show();
		}else if($("#vst_main_isPaging").val() == 0){
			$("#countScript_li").hide();
		}
		
		$("#vst_main_isPaging").change(function(){
			if($("#vst_main_isPaging").val() == 1){
				$("#countScript_li").show();
			}else if($("#vst_main_isPaging").val() == 0){
				$("#countScript_li").hide();
			}
		});
	});
	
	//检测代码编号是否存在
	function checkCode(){
		var result = true;
		var vst_code = $.trim($("#vst_code").val());
		$.ajax({
			url:"${ctx}/autoMain/ajaxCheckCode.action",
			data:"vst_code=" + vst_code,
			type:"POST",
			cache:false,
			async:false,
			dataType:"json",
			success:function(data){
				if(data == true){
					result = false;
				}
			}
		});
		return result;
	}

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
				<form id="listForm" action="${ctx}/autoMain/addAutoMain.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 自动化管理 > 自动化(主表)新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">代码编号：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_code" name="vst_code" placeholder="请输入代码编号" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_codeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">脚本：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_main_script" name="vst_main_script" style="height: 100px;" placeholder="请输入脚本"></textarea>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_main_scriptTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">是否分页：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_main_isPaging" name="vst_main_isPaging">
												<option value="0">否</option>
												<option value="1">是</option>
											</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_main_isPagingTip"></span>
									</div>
								</li>
								<li id="countScript_li">
									<div class="add_l">分页脚本：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_main_countScript" name="vst_main_countScript" style="height: 100px;" placeholder="请输入分页脚本"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_main_countScriptTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_main_index" name="vst_main_index" value="0" placeholder="请输入排序,排序越小越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_main_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">描述：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_main_summary" name="vst_main_summary" style="resize: none; height: 100px;" placeholder="请输入描述"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_main_summaryTip"></span>
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