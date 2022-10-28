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

		$("#vst_sql_id").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "所属任务必须选择！"
		});
		
		$("#vst_column_name").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 300,
			onerror : "列字段名称不可为空，且最大长度为300个字符！"
		});
		
		$("#vst_column_alias").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "列字段别名最大长度为100个字符！"
		});

		$("#vst_column_operateType").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "操作类型必须选择！"
		});
		
		$("#vst_column_dataType").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "数据类型必须选择！"
		});
		
		$("#vst_column_index").formValidator({
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
		
		$("#vst_column_summary").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 512,
			onerror : "备注最大长度为512个字符！"
		});

		// 根据任务类型，筛选所属任务
		$("#vst_sql_type").change(function(){
			$.ajax({
				url : "${ctx}/sql/ajaxGetSql.action",
				data : "vst_sql_type=" + $("#vst_sql_type").val(),
				type : "POST",
				cache : false,
				async : false,
				dataType : "json",
				success : function(result){
					if(result.code == 100){
						var data = result.data;
						var sqlHtml = "<option value=''>请选择</option>";
						$("#vst_sql_id").empty();
						for(var key in data){
							sqlHtml += "<option value='"+key+"'>"+data[key]+"</option>";
						}
						$("#vst_sql_id").html(sqlHtml);
					}else{
						$("#vst_sql_id").html("<option value=''>请选择</option>");
					}
				}
			});
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
				<form id="listForm" action="${ctx}/sqlColumn/addSqlColumn.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > sql列配置新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">任务类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_sql_type">
												<option value="">全部</option>
							 					<c:forEach items="${sqlTypes}" var="sqlType">
						  	 						<option value="${sqlType.key }">${sqlType.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">所属任务：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_sql_id" name="vst_sql_id">
							    				<option value="">请选择</option>
							 					<c:forEach items="${sqls}" var="sql">
						  	 						<option value="${sql.key }">
						  	 							${sql.value.vst_sql_name }
						  	 							<c:if test="${sql.value.vst_sql_state == 2}">(禁用)</c:if>
						  	 						</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_idTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">列字段名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_column_name" name="vst_column_name" 
												style="resize: none; height: 100px;" placeholder="请输入列字段名称"></textarea>
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
									<div class="add_l">列字段别名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_column_alias" name="vst_column_alias" placeholder="请输入列字段别名" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_column_aliasTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">操作类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select	class="input" id="vst_column_operateType" name="vst_column_operateType">
							 					<c:forEach items="${operateTypes}" var="operateType">
													<option value="${operateType.key }">${operateType.value }</option>
												</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_column_operateTypeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">数据类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select	class="input" id="vst_column_dataType" name="vst_column_dataType">
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
										<span id="vst_column_dataTypeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_column_index" name="vst_column_index" value="0" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_column_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_column_summary" name="vst_column_summary" 
												style="resize: none; height: 100px;" placeholder="请输入备注"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_column_summaryTip"></span>
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