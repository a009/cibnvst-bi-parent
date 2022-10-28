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
		$("#vst_save_data_type").val($("#hidden_save_data_type").val());
		$("#vst_save_type").val($("#hidden_save_type").val());
		$("#vst_save_is_format").val($("#hidden_save_is_format").val());
		$("#vst_save_format_type").val($("#hidden_save_format_type").val());
	});

	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});

		$("#vst_save_table").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "插入表名称最大长度100个字符！"
		});
		
		$("#vst_save_name").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "列字段名称最大长度为100个字符！"
		});
		
		$("#vst_save_alias").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "列字段别名最大长度为100个字符！"
		});

		$("#vst_save_data_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "字段值类型必须选择！"
		});
		
		$("#vst_save_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "类型必须选择！"
		});

		$("#vst_save_default").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "默认值最大长度为100个字符！"
		});

		$("#vst_save_length").formValidator({
	  		onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator({
			min : 1,
			max : 3,
			onerror:"字段长度在1~3个字符！"
		}).regexValidator({
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "字段长度格式错误，必须为纯数字，不能带有小数！"
		});

		$("#vst_save_is_format").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "是否格式化必须选择！"
		});

		$("#vst_save_format_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "格式化类型必须选择！"
		});

		$("#vst_save_format_union").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "格式化关联字段最大长度为100个字符！"
		});
		
		$("#vst_save_index").formValidator({
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
		
		$("#vst_save_summary").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 512,
			onerror : "备注最大长度为512个字符！"
		});

		// 是否格式化，选择是，需要显示相关项
		if($("#vst_save_is_format").val() == 1){
			$(".isFormat").show();
		}else{
			$(".isFormat").hide();
		}
		
		$("#vst_save_is_format").change(function(){
			if($("#vst_save_is_format").val() == 1){
				$(".isFormat").show();
			}else{
				$(".isFormat").hide();
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
				<form id="listForm" action="${ctx}/sqlSave/copyAddSqlSave.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > sql数据保存配置复制新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">插入表名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_save_table" name="vst_save_table" value="${formMap.vst_save_table }" placeholder="请输入插入表名称" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_tableTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">列字段名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_save_name" name="vst_save_name" value="${formMap.vst_save_name }" placeholder="请输入列字段名称" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">字段值类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_save_data_type" value="${formMap.vst_save_data_type }" />
											<select class="input" id="vst_save_data_type" name="vst_save_data_type">
							    				<option value="">请选择</option>
							 					<c:forEach items="${dataTypes}" var="dataType">
						  	 						<option value="${dataType.key }">${dataType.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_data_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_save_type" value="${formMap.vst_save_type }" />
											<select class="input" id="vst_save_type" name="vst_save_type">
							    				<option value="">请选择</option>
							 					<c:forEach items="${types}" var="type">
						  	 						<option value="${type.key }">${type.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">默认值：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_save_default" name="vst_save_default" value="${formMap.vst_save_default }" placeholder="该字段为空的时候默认值" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_defaultTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">字段长度：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_save_length" name="vst_save_length" value="${formMap.vst_save_length }" placeholder="字段长度范围" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_lengthTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">是否格式化：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_save_is_format" value="${formMap.vst_save_is_format }" />
											<select	class="input" id="vst_save_is_format" name="vst_save_is_format">
							 					<option value="1">是</option>
							 					<option value="2">否</option>
							    			</select>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_is_formatTip"></span>
									</div>
								</li>
								<li class="isFormat">
									<div class="add_l">格式化类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_save_format_type" value="${formMap.vst_save_format_type }" />
											<select class="input" id="vst_save_format_type" name="vst_save_format_type">
							 					<c:forEach items="${formatTypes}" var="formatType">
						  	 						<option value="${formatType.key }">${formatType.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_format_typeTip"></span>
									</div>
								</li>
								<li class="isFormat">
									<div class="add_l">格式化关联字段：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_save_format_union" name="vst_save_format_union" 
												style="resize: none; height: 100px;" placeholder="格式化所需要关联的字段，如果用多个，用英文逗号隔开，通常用于格式化类型5和6">${formMap.vst_save_format_union }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_format_unionTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_save_index" name="vst_save_index" value="${formMap.vst_save_index }" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">状态：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="radio" value="1" name="vst_save_state" />正常&nbsp;&nbsp;
											<input type="radio" value="2" name="vst_save_state" checked="checked" />禁用
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_stateTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_save_summary" name="vst_save_summary" 
												style="resize: none; height: 100px;" placeholder="请输入备注">${formMap.vst_save_summary }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_save_summaryTip"></span>
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