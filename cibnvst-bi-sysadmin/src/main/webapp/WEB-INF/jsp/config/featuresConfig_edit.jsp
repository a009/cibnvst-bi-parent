<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	// 下拉列表框，赋值
	$(function(){
		$("#vst_features_pkg").val($("#hidden_features_pkg").val());
		$("#vst_features_special_type").val($("#hidden_features_special_type").val());
		$("#vst_features_cid").val($("#hidden_features_cid").val());
		$("#vst_features_type").val($("#hidden_features_type").val());
	});
	
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});
		
		$("#vst_features_pkg").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "所属包名必须选择！"
		});
		
		$("#vst_features_special_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "所属专区必须选择！"
		});
		
		$("#vst_features_cid").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "所属分类必须选择！"
		});
		
		$("#vst_features_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "特征类型必须选择！"
		});
		
		$("#vst_features_name").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 100,
			onerror : "特征名称不可为空，且最大长度为100个字符！"
		});
		
		$("#vst_features_value").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 19,
			onerror : "特征值长度在1~19位！"
		}).regexValidator( {
			regexp : "^[0-9]{1,}(\.[0-9]{1,4})?$",
			datatype : "string",
			onerror : "特征值必须是数字，最多四位小数！"
		});
		
		$("#vst_features_index").formValidator({
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
		
		$("#vst_features_summary").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 512,
			onerror : "备注最大长度为512个字符！"
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
				<form id="listForm" action="${ctx}/featuresConfig/editFeaturesConfig.action" method="post" enctype="multipart/form-data">
					<input type="hidden" name="vst_features_id" value="${formMap.vst_features_id }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > 用户指标特征配置修改</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">所属包名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_features_pkg" value="${formMap.vst_features_pkg }" />
											<select class="input" id="vst_features_pkg" name="vst_features_pkg">
												<option value="">请选择</option>
							 					<c:forEach items="${pkgNames}" var="pkg">
													<option value="${pkg.key }">${pkg.value }</option>
												</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_pkgTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">所属专区：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_features_special_type" value="${formMap.vst_features_special_type }" />
											<select class="input" id="vst_features_special_type" name="vst_features_special_type">
												<option value="">请选择</option>
												<c:forEach items="${specialTypes}" var="specialType">
													<option value="${specialType.key }">${specialType.value }</option>
												</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_special_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">所属分类：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_features_cid" value="${formMap.vst_features_cid }" />
											<select class="input" id="vst_features_cid" name="vst_features_cid">
												<option value="">请选择</option>
												<c:forEach items="${cids}" var="cid">
													<option value="${cid.key }">${cid.value }</option>
												</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_cidTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">特征类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_features_type" value="${formMap.vst_features_type }" />
											<select class="input" id="vst_features_type" name="vst_features_type">
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
										<span id="vst_features_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">特征名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_features_name" name="vst_features_name" value="${formMap.vst_features_name }" placeholder="请输入特征名称，-2:全部,-1:其它" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">特征值：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_features_value" name="vst_features_value" value="${formMap.vst_features_value }" placeholder="请输入特征名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_valueTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_features_index" name="vst_features_index" value="${formMap.vst_features_index }" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_features_summary" name="vst_features_summary" placeholder="请输入备注"
												style="resize: none; height: 100px;">${formMap.vst_features_summary }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_features_summaryTip"></span>
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