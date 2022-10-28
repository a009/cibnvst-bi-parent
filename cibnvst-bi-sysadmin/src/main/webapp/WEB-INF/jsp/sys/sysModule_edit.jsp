<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	//下拉列表框，赋值
	$(function(){
  		$("#vst_module_parent").val($("#hidden_module_parent").val());
  	});
	
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});

		$("#vst_module_name").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 50,
			onerror : "模块名称不可为空，且最大长度为50个字符！"
		});
		
		$("#vst_module_parent").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 0,
			onerror : "不选默认为父级！"
		});
		
		$("#vst_module_url").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 300,
			onerror : "模块URL不可为空，且最大长度为300个字符！"
		});
		
		$("#vst_module_icon").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 0,
			max : 50,
			onerror : "图标最大长度为50个字符！"
		});

		$("#vst_module_index").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 11,
			onerror : "排序格式错误！至少需要1位数字且不超过11位数字！"
		}).regexValidator( {
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "排序格式错误，必须为纯数字，不能带有小数！"
		});

		$("#vst_module_summary").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 0,
			max : 512,
			onerror : "备注最大长度为512个字符！"
		});
		
		// 父模块修改时，更换图标
		$("#vst_module_parent").change(function(){
			if($("#vst_module_parent").val() == '0'){	//父模块
				$("#vst_module_icon").val('fa-link')
			}else{	//子模块
				$("#vst_module_icon").val('fa-circle-o')
			}
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
				<form id="listForm" action="${ctx}/sysModule/editModule.action" method="post">
					<input type="hidden" name="vst_module_id" value="${formMap.vst_module_id }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统模块修改</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">模块名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_module_name" name="vst_module_name" value="${formMap.vst_module_name }" placeholder="请输入模块名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_module_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">父模块：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_module_parent" value="${formMap.vst_module_parent }" />
											<select id="vst_module_parent" name="vst_module_parent" class="list-input">
							 					<option value="0">无</option>
							 					<c:forEach items="${parentModules}" var="module">
							 						<option value="${module.key }">${module.value }</option>
							 					</c:forEach>
							 				</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_module_parentTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">模块URL：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_module_url" name="vst_module_url" value="${formMap.vst_module_url }" placeholder="请输入模块URL" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_module_urlTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">图标：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id=vst_module_icon name="vst_module_icon" value="${formMap.vst_module_icon }" placeholder="请输入图标" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_module_iconTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_module_index" name="vst_module_index" value="${formMap.vst_module_index }" placeholder="请输入排序" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_module_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_module_summary" name="vst_module_summary" placeholder="请输入备注"
												style="resize: none; height: 100px;">${formMap.vst_module_summary }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_module_summaryTip"></span>
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