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

		$("#vst_topic_id").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "所属配置必须选择！"
		});
		
		$("#vst_prop_name").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 100,
			onerror : "属性名称不可为空，且最大长度为100个字符！"
		});
		
		$("#vst_prop_value_default").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "属性对应默认值最大长度为100个字符！"
		});

		$("#vst_prop_value_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "属性值类型必须选择！"
		});
		
		$("#vst_prop_value_required").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "是否必填必须选择！"
		});
		
		$("#vst_prop_value_range").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "属性值范围最大长度为100个字符！"
		});
		
		$("#vst_prop_index").formValidator({
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
		
		$("#vst_prop_summary").formValidator({
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
				<form id="listForm" action="${ctx}/sysTopicProp/addSysTopicProp.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > topic属性配置新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">所属配置：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_topic_id" name="vst_topic_id">
							    				<option value="">请选择</option>
							 					<c:forEach items="${topics}" var="topic">
						  	 						<option value="${topic.key }">${topic.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_topic_idTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">属性名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_prop_name" name="vst_prop_name" placeholder="请输入属性名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">属性对应默认值：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_prop_value_default" name="vst_prop_value_default" style="height: 100px;" placeholder="请输入属性对应默认值"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_value_defaultTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">属性值类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select	class="input" id="vst_prop_value_type" name="vst_prop_value_type">
							 					<c:forEach items="${valueTypes}" var="valueType">
							 						<option value="${valueType.key }">${valueType.value }</option>
							 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_value_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">是否必填：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select	class="input" id="vst_prop_value_required" name="vst_prop_value_required">
							 					<option value="1">是</option>
							 					<option value="2">否</option>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_value_requiredTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">属性值范围：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_prop_value_range" name="vst_prop_value_range" 
												placeholder="如果是数字类型，用[-, +]表示负无穷大到正无穷大；如果是字符串或boolean类型，用{&quot;a&quot;,&quot;b&quot;}来枚举，用![0, 10]来表示本身字符串长度的限定"
												style="height: 100px;"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_value_rangeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_prop_index" name="vst_prop_index" value="0" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_prop_summary" name="vst_prop_summary" 
												style="resize: none; height: 100px;" placeholder="请输入备注"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_prop_summaryTip"></span>
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