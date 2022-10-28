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
		$("#vst_sql_pid").val($("#hidden_sql_pid").val());
		$("#vst_sql_type").val($("#hidden_sql_type").val());
		$("#vst_sql_db").val($("#hidden_sql_db").val());
		$("#vst_sql_run_model").val($("#hidden_sql_run_model").val());
		$("#vst_sql_is_format").val($("#hidden_sql_is_format").val());
		$("#vst_sql_state").val($("#hidden_sql_state").val());
	});

	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});

		$("#vst_sql_pid").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "父级任务必须选择！"
		});
		
		$("#vst_sql_name").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 100,
			onerror : "任务名称不可为空，且最大长度为100个字符！"
		});
		
		$("#vst_sql_type").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "任务类型必须选择！"
		});

		$("#vst_sql_topic").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "topic最大长度为100个字符！"
		});
		
		$("#vst_sql_interval").formValidator({
	  		onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator({
			min : 1,
			max : 11,
			onerror:"任务间隔格式错误！至少需要一位数字！"
		}).regexValidator({
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "任务间隔格式错误，必须为纯数字，不能带有小数！"
		});
		
		$("#vst_sql_data_path").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 200,
			onerror : "sql数据来源最大长度为200个字符！"
		});

		$("#vst_sql_temp_table").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 50,
			onerror : "spark临时表名最大长度为50个字符！"
		});

		$("#vst_sql_db").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			onerror : "计算结果保存数据源必须选择！"
		});

		$("#vst_sql_table").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "计算结果表名最大长度为100个字符！"
		});

		$("#vst_sql_rowkeyColumn").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 100,
			onerror : "rowkey的组装元素最大长度为100个字符！"
		});
		
		$("#vst_sql_desc").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 300,
			onerror : "描述最大长度为300个字符！"
		});

		$("#vst_sql_runtime").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "上一次执行时间可以为空！"
		});

		$("#vst_sql_run_position").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 200,
			onerror : "上一次执行位置最大长度为200个字符！"
		});

		$("#vst_sql_run_model").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "运行模式必须选择！"
		});

		$("#vst_sql_is_format").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "是否格式化必须选择！"
		});

		$("#vst_sql_joinKeys").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 200,
			onerror : "关联key最大长度为200个字符！"
		});

		$("#vst_sql_priority").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).regexValidator({
			regexp : "^(-)?[0-9]{1,11}$",
			datatype : "string",
			onerror : "优先级必须是整数，长度在1~11位！"
		});
		
		$("#vst_sql_index").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 1,
			max : 11,
			onerror:"排序格式错误！至少需要一位数字！"
		}).regexValidator({
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "排序格式错误，必须为纯数字，不能带有小数！"
		});

		$("#vst_sql_state").formValidator({
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			onerror : "状态必须选择！"
		});
		
		$("#vst_sql_summary").formValidator({
	  		onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator({
			min : 0,
			max : 512,
			onerror : "备注最大长度为512个字符！"
		});

		/*// 父级任务和子级任务，对应的任务类型不同
		if($("#vst_sql_pid").val() == "0"){	//父级
			$(".pType").show();
			$(".cType").hide();
		}else{	//子级
			$(".pType").hide();
			$(".cType").show();
		}
		
		$("#vst_sql_pid").change(function(){
			var pid = $("#vst_sql_pid").val();
			if(pid == "0"){	//父级
				$(".pType").show();
				$(".cType").hide();
			}else{	//子级
				$(".pType").hide();
				$(".cType").show();
			}
		});*/
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
				<form id="listForm" action="${ctx}/sql/editSql.action" method="post" enctype="multipart/form-data">
					<input type="hidden" name="vst_sql_id" value="${formMap.vst_sql_id }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > spark任务修改</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">父级任务：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_sql_pid" value="${formMap.vst_sql_pid }" />
											<select class="input" id="vst_sql_pid" name="vst_sql_pid">
							    				<option value="0">无</option>
							 					<c:forEach items="${parents}" var="parent">
						  	 						<option value="${parent.key }">
						  	 							${parent.value.vst_sql_name }
						  	 							<c:if test="${parent.value.vst_sql_state == 2}">(禁用)</c:if>
						  	 						</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_pidTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">任务名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_name" name="vst_sql_name" value="${formMap.vst_sql_name }" placeholder="请输入任务名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">任务类型：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_sql_type" value="${formMap.vst_sql_type }" />
											<select class="input" id="vst_sql_type" name="vst_sql_type">
							    				<option value="">请选择</option>
							    				<c:forEach items="${parentTypes}" var="parentType">
													<option class="pType" value="${parentType.key }">${parentType.value }</option>
												</c:forEach>
												<c:forEach items="${childTypes}" var="childType">
													<option class="cType" value="${childType.key }">${childType.value }</option>
												</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_typeTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">topic：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_topic" name="vst_sql_topic" style="height: 100px;" 
												placeholder="请输入topic">${formMap.vst_sql_topic }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_topicTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">任务间隔(秒)：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_interval" name="vst_sql_interval" value="${formMap.vst_sql_interval }" placeholder="请输入任务间隔" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_intervalTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">sql数据来源：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_data_path" name="vst_sql_data_path" style="resize: none; height: 100px;" 
												placeholder="请输入sql数据来源">${formMap.vst_sql_data_path }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_data_pathTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">spark临时表名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_temp_table" name="vst_sql_temp_table" value="${formMap.vst_sql_temp_table }" placeholder="请输入spark临时表名" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_temp_tableTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">计算结果保存数据源：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_sql_db" value="${formMap.vst_sql_db }" />
											<select class="input" id="vst_sql_db" name="vst_sql_db">
							    				<option value="">请选择</option>
							    				<c:forEach items="${dbs}" var="db">
							    					<option value="${db.key }">${db.value }</option>
							    				</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_dbTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">计算结果表名：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_table" name="vst_sql_table" value="${formMap.vst_sql_table }" placeholder="请输入计算结果表名" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_tableTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">rowkey的组装元素：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_rowkeyColumn" name="vst_sql_rowkeyColumn" style="resize: none; height: 100px;" 
												placeholder="只有当保存数据源是hbase的时候才有意义，如果有多个，用英文逗号隔开">${formMap.vst_sql_rowkeyColumn }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_rowkeyColumnTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">任务描述：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_desc" name="vst_sql_desc" style="resize: none; height: 100px;" 
												placeholder="请输入任务描述">${formMap.vst_sql_desc }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_descTip"></span>
									</div>
								</li>
								<li>
							 		<div class="add_l">上一次执行时间：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_runtime" name="vst_sql_runtime" value="${formMap.vst_sql_runtime }" placeholder="yyyy-MM-dd HH:mm:ss" maxlength="19"/>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_runtimeTip"></span>
									</div>
							 	</li>
								<li>
									<div class="add_l">上一次执行位置：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_run_position" name="vst_sql_run_position"
												style="resize: none; height: 100px;" placeholder="格式：yyyyMMdd|HH|mm">${formMap.vst_sql_run_position }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_run_positionTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">运行模式：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_sql_run_model" value="${formMap.vst_sql_run_model }" />
											<select class="input" id="vst_sql_run_model" name="vst_sql_run_model">
							    				<option value="1">并行</option>
							    				<option value="2">串行</option>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_run_modelTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">是否格式化：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_sql_is_format" value="${formMap.vst_sql_is_format }" />
											<select class="input" id="vst_sql_is_format" name="vst_sql_is_format">
							    				<option value="1">是</option>
							    				<option value="2">否</option>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_is_formatTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">sparksql查询关联key：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_joinKeys" name="vst_sql_joinKeys"
												style="resize: none; height: 100px;" placeholder="若有多个，用“|”隔开">${formMap.vst_sql_joinKeys }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_joinKeysTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">优先级：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_priority" name="vst_sql_priority" value="${formMap.vst_sql_priority }" placeholder="请输入优先级，数值越大优先级越高" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_priorityTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sql_index" name="vst_sql_index" value="${formMap.vst_sql_index }" placeholder="请输入排序,排序越大越靠前" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">状态：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="hidden_sql_state" value="${formMap.vst_sql_state }" />
											<select class="input" id="vst_sql_state" name="vst_sql_state">
							    				<option value="1">正常</option>
							    				<option value="2">禁用</option>
							    			</select>
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_stateTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sql_summary" name="vst_sql_summary" 
												style="resize: none; height: 100px;" placeholder="请输入备注">${formMap.vst_sql_summary }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sql_summaryTip"></span>
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
	
	<!--日历-->
	<script type="text/javascript">
	  laydate.render({
	  	elem: '#vst_sql_runtime', //指定元素
	  	type: 'datetime',
	  	format: 'yyyy-MM-dd HH:mm:ss',
	  	theme: 'molv'
	  });
	</script>
</body>
</html>