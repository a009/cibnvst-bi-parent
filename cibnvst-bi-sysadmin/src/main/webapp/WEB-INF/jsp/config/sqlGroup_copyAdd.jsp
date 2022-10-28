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
		$("#vst_sql_id").val($("#hidden_sql_id").val());
	});
	
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
		
		$("#vst_group_name").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 100,
			onerror : "分组字段名称不可为空，且最大长度为100个字符！"
		});
		
		$("#vst_group_alias").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "分组字段别名最大长度为100个字符！"
		});

		$("#vst_group_desc").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 300,
			onerror : "分组描述最大长度为300个字符！"
		});
		
		$("#vst_group_index").formValidator({
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
		
		$("#vst_group_summary").formValidator({
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
				<form id="listForm" action="${ctx}/sqlGroup/copyAddSqlGroup.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > sql分组配置复制新增</span>
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
											<input type="hidden" id="hidden_sql_id" value="${formMap.vst_sql_id }" />
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
									<div class="add_l">分组字段名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_group_name" name="vst_group_name" value="${formMap.vst_group_name }" placeholder="请输入分组字段名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_group_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">分组字段别名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_group_alias" name="vst_group_alias" value="${formMap.vst_group_alias }" placeholder="请输入分组字段别名" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_group_aliasTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">分组描述：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_group_desc" name="vst_group_desc" 
												style="resize: none; height: 100px;" placeholder="请输入分组描述">${formMap.vst_group_desc }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_group_descTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_group_index" name="vst_group_index" value="${formMap.vst_group_index }" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_group_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">状态：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="radio" value="1" name="vst_group_state" />正常&nbsp;&nbsp;
											<input type="radio" value="2" name="vst_group_state" checked="checked" />禁用
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_group_stateTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_group_summary" name="vst_group_summary" 
												style="resize: none; height: 100px;" placeholder="请输入备注">${formMap.vst_group_summary }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_group_summaryTip"></span>
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