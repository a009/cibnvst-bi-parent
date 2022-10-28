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
		$("#vst_pkg").val($("#hidden_pkg").val());
	});
	
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});

		$("#vst_pkg").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 0,
			onerror : "包名必须选择！"
		});

		$("#vst_table_name").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 0,
			max : 100,
			onerror : "表名最大长度为100个字符！"
		});
		
		$("#vst_column_name").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 0,
			max : 100,
			onerror : "字段名最大长度为100个字符！"
		});
		
		$("#vst_property_key").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 0,
			max : 100,
			onerror : "属性键最大长度为100个字符！"
		});
		
		$("#vst_property_value").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 1,
			max : 100,
			onerror : "属性值不可为空，且最大长度为100个字符！"
		});

		$("#vst_index").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 1,
			max : 11,
			onerror : "排序格式错误！至少需要1位数字且不超过11位数字！"
		}).regexValidator( {
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "排序格式错误，必须为纯数字，不能带有小数！"
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
				<form id="listForm" action="${ctx}/dictionary/editDictionary.action" method="post">
					<input type="hidden" name="vst_id" value="${formMap.vst_id }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 字典修改</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">包名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_pkg" value="${formMap.vst_pkg }" />
											<select class="input" id="vst_pkg" name="vst_pkg">
							    				<option value="0">全部</option>
							 					<c:forEach items="${pkgList}" var="pkg">
						  	 						<option value="${pkg.key }">${pkg.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_pkgTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">表名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_table_name" name="vst_table_name" value="${formMap.vst_table_name }" placeholder="请输入表名" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_table_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">字段名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_column_name" name="vst_column_name" value="${formMap.vst_column_name }" placeholder="请输入字段名" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_column_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">属性键：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_property_key" name="vst_property_key" value="${formMap.vst_property_key }" placeholder="请输入属性键" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_property_keyTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">属性值：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_property_value" name="vst_property_value" value="${formMap.vst_property_value }" placeholder="请输入属性值" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_property_valueTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_index" name="vst_index" value="${formMap.vst_index }" placeholder="请输入排序" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_indexTip"></span>
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