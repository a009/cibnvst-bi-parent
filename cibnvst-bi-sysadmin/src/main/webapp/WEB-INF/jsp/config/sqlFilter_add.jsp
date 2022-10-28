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
		
		$("#vst_filter_key").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 100,
			onerror : "筛选key不可为空，且最大长度为100个字符！"
		});

		$("#vst_filter_value").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "筛选value最大长度为100个字符！"
		});

		$("#vst_filter_match_type").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "匹配类型必须选择！"
		});
		
		$("#vst_filter_yes_type").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "匹配上处理类型必须选择！"
		});

		$("#vst_filter_no_type").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "未匹配处理类型必须选择！"
		});

		$("#vst_filter_action_type").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "操作类型必须选择！"
		});

		$("#vst_filter_action_key").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "动作key最大长度为100个字符！"
		});

		$("#vst_filter_action_value").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "动作value最大长度为100个字符！"
		});

		$("#vst_filter_pkg").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).functionValidator({
			fun : function(){
				var flag = false;
				$("input[name='vst_filter_pkg']").each(function (){
					if($(this).is(':checked')){
						flag = true;
					}
				});
				return flag;
			},
			onerror : "适用包名必须选择！"
		});

		$("#vst_filter_pkg_block").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).functionValidator({
			fun : function(){
				var flag = false;
				$("input[name='vst_filter_pkg_block']").each(function (){
					if($(this).is(':checked')){
						flag = true;
					}
				});
				return flag;
			},
			onerror : "屏蔽包名必须选择！"
		});

		$("#vst_filter_index").formValidator({
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
		
		$("#vst_filter_summary").formValidator({
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

		// 适用包名
		$("#all_pkg").click(function(){
			if($("#all_pkg").is(':checked')){
				$(".sun_pkg").removeAttr("checked");
				$(".sun_pkg").attr("disabled", "disabled");
			}else{
				$(".sun_pkg").removeAttr("disabled");
			}
		});

		// 屏蔽包名
		$("#all_pkg_block").click(function(){
			if($("#all_pkg_block").is(':checked')){
				$(".sun_pkg_block").removeAttr("checked");
				$(".sun_pkg_block").attr("disabled", "disabled");
			}else{
				$(".sun_pkg_block").removeAttr("disabled");
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
				<form id="listForm" action="${ctx}/sqlFilter/addSqlFilter.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > sql筛选配置新增</span>
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
									<div class="add_l">筛选key：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_filter_key" name="vst_filter_key" placeholder="请输入筛选key" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_keyTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">筛选value：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_filter_value" name="vst_filter_value" placeholder="请输入筛选key" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_valueTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">匹配类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_filter_match_type" name="vst_filter_match_type">
							    				<option value="">请选择</option>
							 					<c:forEach items="${matchTypes}" var="matchType">
						  	 						<option value="${matchType.key }">${matchType.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_match_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">匹配上处理类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_filter_yes_type" name="vst_filter_yes_type">
												<option value="1">通过</option>
												<option value="2">不通过</option>
											</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_yes_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">未匹配处理类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_filter_no_type" name="vst_filter_no_type">
												<option value="1">通过</option>
												<option value="2">不通过</option>
											</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_no_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">操作类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_filter_action_type" name="vst_filter_action_type">
							    				<option value="">请选择</option>
							 					<c:forEach items="${actionTypes}" var="actionType">
						  	 						<option value="${actionType.key }">${actionType.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_action_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">动作key：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_filter_action_key" name="vst_filter_action_key" placeholder="请输入动作key" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_action_keyTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">动作value：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_filter_action_value" name="vst_filter_action_value" placeholder="请输入动作key" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_action_valueTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">适用包名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="checkbox" value="0" name="vst_filter_pkg" id="all_pkg" checked="checked" />全部<br/>
											<c:forEach items="${pkgs}" var="pkg" varStatus="st">
												<div style="width: 150px; float: left;">
													<input type="checkbox" value="${pkg.key}" name="vst_filter_pkg" class="sun_pkg" disabled="disabled" />${pkg.value}
												</div>
												<c:if test="${st.index % 2 == 1}"><br/></c:if>
											</c:forEach>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<input type="hidden" id="vst_filter_pkg" value="此处只用作表单验证" />
										<span id="vst_filter_pkgTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">屏蔽包名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="checkbox" value="0" name="vst_filter_pkg_block" id="all_pkg_block" checked="checked" />没有<br/>
											<c:forEach items="${pkgs}" var="pkg" varStatus="st">
												<div style="width: 150px; float: left;">
													<input type="checkbox" value="${pkg.key}" name="vst_filter_pkg_block" class="sun_pkg_block" disabled="disabled" />${pkg.value}
												</div>
												<c:if test="${st.index % 2 == 1}"><br/></c:if>
											</c:forEach>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<input type="hidden" id="vst_filter_pkg_block" value="此处只用作表单验证" />
										<span id="vst_filter_pkg_blockTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_filter_index" name="vst_filter_index" value="0" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_filter_summary" name="vst_filter_summary" 
												style="resize: none; height: 100px;" placeholder="请输入备注"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_filter_summaryTip"></span>
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