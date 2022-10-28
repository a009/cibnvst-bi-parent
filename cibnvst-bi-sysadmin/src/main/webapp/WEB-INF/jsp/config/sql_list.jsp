<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
  	$(function(){
  		//下拉列表框，赋值
  		$("#vst_sql_pid").val($("#hidden_sql_pid").val());
  		$("#vst_sql_type").val($("#hidden_sql_type").val());
  		$("#vst_sql_state").val($("#hidden_sql_state").val());
  		$("#vst_sql_db").val($("#hidden_sql_db").val());
  		$("#vst_sql_run_model").val($("#hidden_sql_run_model").val());
  		$("#vst_sql_is_format").val($("#hidden_sql_is_format").val());

  		$.enlargePic();
  		
  		//展开/隐藏 查询条件
  		var hidden_search = '${hidden_search}';
  		if(hidden_search == 1 || hidden_search == '1'){	//隐藏
			$("#hidden_search").val(1);
		}else{	//展开
			$("#hidden_search").val(2);
		}
		
		//展开/隐藏 tablelist
		var hidden_tablelist = '${hidden_tablelist}';
  		if(hidden_tablelist == 2 || hidden_tablelist == '2'){	//展开
			$("#hidden_tablelist").val(2);
			$("#tableExample1 .table .table-tbody").removeClass("maxHeight").slideDown();
		}else{	//隐藏
			$("#hidden_tablelist").val(1);
			$("#tableExample1 .table .table-tbody").addClass("maxHeight");
		}
  	});
  	
  	// 查询
  	function validQuery(){
		$("#currPage").val(1);
		$("#queryBtn").attr("disabled", "disabled");
		$("#listForm").submit();
	}
  	
  	// 新增
	function button_add(){
		$("#listForm").attr("action", "${ctx}/sql/toAdd.action");
		$("#listForm").submit();
	}
	
	// 修改
	function button_edit(){
		var modifyChecked="";			
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else if(modifyChecked.indexOf(",") != -1){
	    	alert("每次最多选择一条记录进行该操作!");
			return false;
	    }else{
	    	$("#recordId").val(modifyChecked);
	        $("#listForm").attr("action", "${ctx}/sql/toEdit.action");
			$("#listForm").submit();
	    }
	}
	
	// 批量删除
	function button_delete(){
		var modifyChecked="";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else{
	    	if(window.confirm("您确定要删除所选的"+modifyChecked.split(',').length+"条记录吗?")){
		    	$("#recordId").val(modifyChecked);
		        $("#listForm").attr("action", "${ctx}/sql/deleteSql.action");
				$("#listForm").submit();
			}
	    }
	}
	
	// 批量禁用
	function button_stop(){
		var modifyChecked="";
		var state = "";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
			state = state+ $(this).attr("state") + ",";
		});
		modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
		state = state.substring(0,state.length-1);
		if(modifyChecked == ""){
			alert("您还没有选中记录！");
			return false;
		}
		var states = state.split(",");
		for(var i=0; i<states.length; i++){
			if(states[i] == 2){
				alert("所选记录中存在已禁用的记录不能再禁用，请检查！");
				return false;
			}
		}
		if(confirm("您确定要禁用所选的"+modifyChecked.split(',').length+"条记录吗？")){
			$("#recordId").val(modifyChecked);
			$("#recordState").val(2);
			$("#listForm").attr("action", "${ctx}/sql/modifySqlState.action");
			$("#listForm").submit();
		}
	}
	
	// 批量启用
	function button_resume(){
		var modifyChecked="";
		var state = "";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
			state = state+ $(this).attr("state") + ",";
		});
		modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
		state = state.substring(0,state.length-1);
		if(modifyChecked == ""){
			alert("您还没有选中记录！");
			return false;
		}
		var states = state.split(",");
		for(var i=0; i<states.length; i++){
			if(states[i] == 1){
				alert("所选记录中存在已启用的记录不能再启用，请检查！");
				return false;
			}
		}
		if(confirm("您确定要启用所选的"+modifyChecked.split(',').length+"条记录吗？")){
			$("#recordId").val(modifyChecked);
			$("#recordState").val(1);
			$("#listForm").attr("action", "${ctx}/sql/modifySqlState.action");
			$("#listForm").submit();
		}
	}
	
	// 初始化map实例
	var cahceMap = new HashMap(); 
	$(function(){
		$(".myClick").bind("click",function(){
			// 清除所有的.myClick样式下，所有的input文本域
			$(".myClick > input").each(function(i){
				$(this).parent("li").html($(this).val());
			});
			var value = "";
			// 首先判断有没有input子节点
			if($(".myClick > input").length > 0){
				value = $(".myClick > input").val();
			}else{
				value = $(this).text();
			}
			$(this).html("<input type='text' value='"+value.trim()+"'/>");
			$(".myClick > input").focusEnd();
			
		});
		
		$(document).on("blur", ".myClick > input", function() {
			// 清除所有的.myClick样式下，所有的input文本域
			$(".myClick > input").each(function(i){
				var $tr = $(this).parents("ul");
				var newValue = $(this).val();
				$(this).parent("li").html(newValue);
				var firstTD = $tr.find("li:eq(0) :input[type=checkbox][name=recordIds]");
				var oldValue = $(firstTD).attr("index");
				if(newValue != oldValue){
					$("#doIndex").attr("disabled",false);
					$("#doIndex").attr("class","rankBtn");
					cahceMap.put($(firstTD).attr("value"), newValue);
				}
			});
		});
	});
	
	// 生效排序
	function makeIndex(){
		if($("#doIndex").attr("disabled") == undefined || $("#doIndex").attr("disabled") == ""){
			if(!checkIndexs()){
				alert("非常抱歉！您修改的排序结果中有非数字的排序，请认真检查！");
			}else{
				$.ajax({
					url:"${ctx}/sql/modifySqlIndexs.action",
					type:"POST",
					data:"ids="+getKeys()+"&indexs="+getValues()+"&moduleId="+$("#moduleId").val(),
					dataType:"text",
					async:false,
					cache:false,
					success:function(data){
						if(data != null && data == "true"){
							alert("修改成功！请点击查询后查看结果！");
							$("#doIndex").attr("disabled", "disabled");
							$("#doIndex").attr("class","resetBtn");
						}else{
							alert("修改失败！");
						}
					},
				   error:function (XMLHttpRequest, textStatus, e) {
						   alert("修改失败！失败信息：" + e);
					}
				});
			}
		}
	}
	
	// 校验数据是否合法
    function checkIndexs(){
	    var values = cahceMap.values();
	    var reg = /^[0-9]{1,}$/;
		for(var i in values){
			if(!reg.test(values[i])){
				return false;
			}
		}
		return true;
	}

	function getKeys(){
		 var keys = cahceMap.keySet(); 
		 var result = "";
		 for(var i in keys){
			 result += keys[i] + ",";
		 }
		 return result;
	}

	function getValues(){
		 var values = cahceMap.values(); 
		 var result = "";
		 for(var i in values){
			result += values[i] + ",";
		 }
		 return result;
	}

	// 初始化map实例
	var cahceMap2 = new HashMap(); 
	$(function(){
		$(".myClick2").bind("click",function(){
			// 清除所有的.myClick样式下，所有的input文本域
			$(".myClick2 > input").each(function(i){
				$(this).parent("li").html($(this).val());
			});
			var value = "";
			// 首先判断有没有input子节点
			if($(".myClick2 > input").length > 0){
				value = $(".myClick2 > input").val();
			}else{
				value = $(this).text();
			}
			$(this).html("<input type='text' value='"+value.trim()+"'/>");
			$(".myClick2 > input").focusEnd();
			
		});
		
		$(document).on("blur", ".myClick2 > input", function() {
			// 清除所有的.myClick样式下，所有的input文本域
			$(".myClick2 > input").each(function(i){
				var $tr = $(this).parents("ul");
				var newValue = $(this).val();
				$(this).parent("li").html(newValue);
				var firstTD = $tr.find("li:eq(0) :input[type=checkbox][name=recordIds]");
				var oldValue = $(firstTD).attr("priority");
				if(newValue != oldValue){
					$("#doPriority").attr("disabled",false);
					$("#doPriority").attr("class","rankBtn");
					cahceMap2.put($(firstTD).attr("value"), newValue);
				}
			});
		});
	});
	
	// 生效排序
	function makePriority(){
		if($("#doPriority").attr("disabled") == undefined || $("#doPriority").attr("disabled") == ""){
			if(!checkPrioritys()){
				alert("非常抱歉！优先级必须是整数，且不能小于-1，请认真检查！");
			}else{
				$.ajax({
					url:"${ctx}/sql/modifySqlPrioritys.action",
					type:"POST",
					data:"ids="+getKeys2()+"&prioritys="+getValues2()+"&moduleId="+$("#moduleId").val(),
					dataType:"text",
					async:false,
					cache:false,
					success:function(data){
						if(data != null && data == "true"){
							alert("修改成功！请点击查询后查看结果！");
							$("#doPriority").attr("disabled", "disabled");
							$("#doPriority").attr("class","resetBtn");
						}else{
							alert("修改失败！");
						}
					},
				   error:function (XMLHttpRequest, textStatus, e) {
						   alert("修改失败！失败信息：" + e);
					}
				});
			}
		}
	}
	
	// 校验数据是否合法
    function checkPrioritys(){
	    var values = cahceMap2.values();
	    var reg = /^[0-9]{1,}$/;
		for(var i in values){
			if(values[i] != '-1' && !reg.test(values[i])){
				return false;
			}
		}
		return true;
	}

	function getKeys2(){
		 var keys = cahceMap2.keySet(); 
		 var result = "";
		 for(var i in keys){
			 result += keys[i] + ",";
		 }
		 return result;
	}

	function getValues2(){
		 var values = cahceMap2.values(); 
		 var result = "";
		 for(var i in values){
			result += values[i] + ",";
		 }
		 return result;
	}
	
	// 展开/隐藏 查询条件
	function search_open(){
		if($(".searchBar").hasClass("active")){	//展开
			$(".search-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_close.png");
			$(".searchBar").removeClass("active").slideDown();
			$("#hidden_search").val(2);
		}else{	//隐藏
			$(".search-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_open.png");
			$(".searchBar").addClass("active").slideUp(300);
			$("#hidden_search").val(1);
		}
	}
	
	// 展开/隐藏 tablelist
	function tablelist_open(){
		if($("#tableExample1 .table .table-tbody").hasClass("maxHeight")){	//展开
			$(".tablelist-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_close.png");
			$("#tableExample1 .table .table-tbody").removeClass("maxHeight").slideDown();
			$("#hidden_tablelist").val(2);
		}else{	//隐藏
			$(".tablelist-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_open.png");
			$("#tableExample1 .table .table-tbody").addClass("maxHeight");
			$("#hidden_tablelist").val(1);
		}
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form action="${ctx}/sql/findSqls.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > spark任务查询</span>
					</section>
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box">
								<div class="box-header">
								</div>
								<%@include file="../share/message.jsp"%>
								<div class="box-body">
									<div class="box-search">
										<c:choose>
						    				<c:when test="${hidden_search=='1' || hidden_search==1}">
						    					<ul class="searchBar  clearfix active">
						    				</c:when>
						    				<c:otherwise>
						    					<ul class="searchBar  clearfix" style="display: block;">
						    				</c:otherwise>
						    			</c:choose>
											<li>
												<span class="sertitle">任务ID：</span>
												<input class="list-input1" type="text" name="vst_sql_id" value="${cutPage._queryParam.vst_sql_id }" />
											</li>
											<li>
												<span class="sertitle">父级任务：</span>
												<input type="hidden" id="hidden_sql_pid" value="${cutPage._queryParam.vst_sql_pid }" />
												<select class="list-input1" id="vst_sql_pid" name="vst_sql_pid">
													<option value="">请选择</option>
													<c:forEach items="${parents}" var="parent">
														<option value="${parent.key }">
															${parent.value.vst_sql_name }
															<c:if test="${parent.value.vst_sql_state == 2}">(禁用)</c:if>
														</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">任务名称：</span>
												<input class="list-input1" type="text" name="vst_sql_name" value="${cutPage._queryParam.vst_sql_name }" />
											</li>
											<li>
												<span class="sertitle">任务类型：</span>
												<input type="hidden" id="hidden_sql_type" value="${cutPage._queryParam.vst_sql_type }" />
												<select class="list-input1" id="vst_sql_type" name="vst_sql_type">
													<option value="">请选择</option>
													<c:forEach items="${parentTypes}" var="parentType">
														<option value="${parentType.key }">${parentType.value }</option>
													</c:forEach>
													<c:forEach items="${childTypes}" var="childType">
														<option value="${childType.key }">${childType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_sql_state" value="${cutPage._queryParam.vst_sql_state }" />
												<select class="list-input1" id="vst_sql_state" name="vst_sql_state">
													<option value="">请选择</option>
													<option value="1">正常</option>
													<option value="2">禁用</option>
												</select>
											</li>
											<li>
												<span class="sertitle">保存数据源：</span>
												<input type="hidden" id="hidden_sql_db" value="${cutPage._queryParam.vst_sql_db }" />
												<select class="list-input1" id="vst_sql_db" name="vst_sql_db">
													<option value="">请选择</option>
													<c:forEach items="${dbs}" var="db">
														<option value="${db.key }">${db.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">运行模式：</span>
												<input type="hidden" id="hidden_sql_run_model" value="${cutPage._queryParam.vst_sql_run_model }" />
												<select class="list-input1" id="vst_sql_run_model" name="vst_sql_run_model">
													<option value="">请选择</option>
													<option value="1">并行</option>
													<option value="2">串行</option>
												</select>
											</li>
											<li>
												<span class="sertitle">是否格式化：</span>
												<input type="hidden" id="hidden_sql_is_format" value="${cutPage._queryParam.vst_sql_is_format }" />
												<select class="list-input1" id="vst_sql_is_format" name="vst_sql_is_format">
													<option value="">请选择</option>
													<option value="1">是</option>
													<option value="2">否</option>
												</select>
											</li>
											<li>
												<span class="sertitle">计算结果表名：</span>
												<input class="list-input1" type="text" name="vst_sql_table" value="${cutPage._queryParam.vst_sql_table }" />
											</li>
										</ul>
										<c:choose>
						    				<c:when test="${hidden_search=='1' || hidden_search==1}">
												<div class="search-up">
													<input type="hidden" id="hidden_search" name="hidden_search" />
													<div class="search-up-button" onclick="javascript:search_open();">
														<img src="${ctx}/pub/listPages/images/search_open.png"/>
													</div>
												</div>
						    				</c:when>
						    				<c:otherwise>
						    					<div class="search-up">
													<input type="hidden" id="hidden_search" name="hidden_search" />
													<div class="search-up-button" onclick="javascript:search_open();">
														<img src="${ctx}/pub/listPages/images/search_close.png"/>
													</div>
												</div>
						    				</c:otherwise>
						    			</c:choose>
									</div>
									<div style="text-align: center;">
										<input type="submit" id="queryBtn" class="queryBtn" value="查询" onclick="javascript:validQuery();" />
										<input type="reset" class="resetBtn" value="重置" />
										<input type="button" id="doIndex" class="resetBtn" value="排序生效" onclick="makeIndex()" disabled="disabled" />
										<input type="button" id="doPriority" class="resetBtn" value="优先级生效" onclick="makePriority()" disabled="disabled" />
									</div>
								</div>
							</div>
						</div>
					</div>
					</section>
	
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box ">
								<div class="box-header">
									<%@ include file="../share/buttonList.jsp" %>
								</div>
	
								<div class="box-body">
									<%@ include file="../share/noRecord.jsp"%>
									<c:choose>
										<c:when test="${(cutPage != null && cutPage._totalResults!=0) && cutPage._isQuery}">
											<div style="padding-bottom: 10px;">
												<span class="red">提示：点击排序值可以修改哟！任务间隔为"红色"，表示发起警告！</span>
												
												<c:if test="${cutPage._singleCount >= 15}">
													<c:choose>
									    				<c:when test="${hidden_tablelist=='2' || hidden_tablelist==2}">
															<div class="tablelist-up-button" style="float:right" onclick="javascript:tablelist_open();">
																<img src="${ctx}/pub/listPages/images/search_close.png"/>
															</div>
									    				</c:when>
									    				<c:otherwise>
									    					<div class="tablelist-up-button" style="float:right" onclick="javascript:tablelist_open();">
																<img src="${ctx}/pub/listPages/images/search_open.png"/>
															</div>
									    				</c:otherwise>
									    			</c:choose>
									    		</c:if>
												
												<%@include file="../share/cutPage_header.jsp" %>
											</div>
											<div id="tableExample1">
												<div class="table table-hover text-center" style="width: 1820px;max-width: 1820px;">
													<div class="table-thead" style="width: 1820px;">
														<ul id="rowHead" style="background-color:#d2d6de;">
															<li style="width: 100px;">
																<input type="checkbox" name="chkall" id="chkall" onclick="checkAll()" />
																<c:url value="../share/order.jsp" var="vst_sql_id_order">
																    <c:param name="orderBy" value="vst_sql_id"/>
																    <c:param name="fieldName" value="全选"/>
															    </c:url>
															    <c:import url="${vst_sql_id_order}"/>
															</li>
															<li style="width: 200px;">
																<c:url value="../share/order.jsp" var="vst_sql_pid_order">
																    <c:param name="orderBy" value="vst_sql_pid"/>
																    <c:param name="fieldName" value="父ID"/>
															    </c:url>
															    <c:import url="${vst_sql_pid_order}"/>
															</li>
															<li style="width: 200px;">
																<c:url value="../share/order.jsp" var="vst_sql_name_order">
																    <c:param name="orderBy" value="vst_sql_name"/>
																    <c:param name="fieldName" value="<font style='color:orange'>任务名称</font>"/>
															    </c:url>
															    <c:import url="${vst_sql_name_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_sql_type_order">
																    <c:param name="orderBy" value="vst_sql_type"/>
																    <c:param name="fieldName" value="任务类型"/>
															    </c:url>
															    <c:import url="${vst_sql_type_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_sql_interval_order">
																    <c:param name="orderBy" value="vst_sql_interval"/>
																    <c:param name="fieldName" value="任务间隔(s)"/>
															    </c:url>
															    <c:import url="${vst_sql_interval_order}"/>
															</li>
															<li style="width: 120px;">
																<c:url value="../share/order.jsp" var="vst_sql_temp_table_order">
																    <c:param name="orderBy" value="vst_sql_temp_table"/>
																    <c:param name="fieldName" value="spark临时表名"/>
															    </c:url>
															    <c:import url="${vst_sql_temp_table_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_sql_db_order">
																    <c:param name="orderBy" value="vst_sql_db"/>
																    <c:param name="fieldName" value="计算结果保存数据源"/>
															    </c:url>
															    <c:import url="${vst_sql_db_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_sql_table_order">
																    <c:param name="orderBy" value="vst_sql_table"/>
																    <c:param name="fieldName" value="计算结果表名"/>
															    </c:url>
															    <c:import url="${vst_sql_table_order}"/>
															</li>
															<li style="width: 80px;">
																<c:url value="../share/order.jsp" var="vst_sql_priority_order">
																    <c:param name="orderBy" value="vst_sql_priority"/>
																    <c:param name="fieldName" value="优先级"/>
															    </c:url>
															    <c:import url="${vst_sql_priority_order}"/>
															</li>
															<li style="width: 80px;">
																<c:url value="../share/order.jsp" var="vst_sql_index_order">
																    <c:param name="orderBy" value="vst_sql_index"/>
																    <c:param name="fieldName" value="排序"/>
															    </c:url>
															    <c:import url="${vst_sql_index_order}"/>
															</li>
															<li style="width: 80px;">
																<c:url value="../share/order.jsp" var="vst_sql_state_order">
																    <c:param name="orderBy" value="vst_sql_state"/>
																    <c:param name="fieldName" value="状态"/>
															    </c:url>
															    <c:import url="${vst_sql_state_order}"/>
															</li>
															<li style="width: 110px;">
																<c:url value="../share/order.jsp" var="vst_sql_runtime_order">
																    <c:param name="orderBy" value="vst_sql_runtime"/>
																    <c:param name="fieldName" value="上一次执行时间"/>
															    </c:url>
															    <c:import url="${vst_sql_runtime_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_sql_run_position_order">
																    <c:param name="orderBy" value="vst_sql_run_position"/>
																    <c:param name="fieldName" value="上一次执行位置"/>
															    </c:url>
															    <c:import url="${vst_sql_run_position_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_sql_run_model_order">
																    <c:param name="orderBy" value="vst_sql_run_model"/>
																    <c:param name="fieldName" value="运行模式"/>
															    </c:url>
															    <c:import url="${vst_sql_run_model_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_sql_is_format_order">
																    <c:param name="orderBy" value="vst_sql_is_format"/>
																    <c:param name="fieldName" value="是否格式化"/>
															    </c:url>
															    <c:import url="${vst_sql_is_format_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width: 100px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_sql_id}"
																	state="${bean.vst_sql_state}"
																	index="${bean.vst_sql_index}"
																	priority="${bean.vst_sql_priority}"
																	style="width:auto; vertical-align: middle; margin:0 0 0 10px;" />
																<span style="display:inline-block;line-height:32px;">${bean.vst_sql_id}</span>
															</li>
															<li style="width: 200px;">
																<c:choose>
																	<c:when test="${bean.vst_sql_pid == '0'}">无</c:when>
																	<c:otherwise>
																		[${bean.vst_sql_pid}]
																		<c:forEach items="${parents}" var="parent">
																			<c:if test="${bean.vst_sql_pid == parent.key}">
																				<span <c:if test="${parent.value.vst_sql_state == 2}">class='red'</c:if>>
																					${parent.value.vst_sql_name }
																				</span>
																			</c:if>
																		</c:forEach>
																	</c:otherwise>
																</c:choose>
															</li>
															<li style="width: 200px;" class="enlargeTextTitle"
																data-title="<table style='color:#eee;'>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>topic：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_topic}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>sql数据来源：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_data_path}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>任务描述：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_desc}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>关联key：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_joinKeys}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>新增时间：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_addtime}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>创建人：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_creator}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>修改时间：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_uptime}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>修改人：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_updator}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>备注：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_sql_summary}</p></td>
																	</tr>
																</table>">
																${bean.vst_sql_name}
															</li>
															<li style="width: 100px;">
																<c:choose>
												    				<c:when test="${bean.vst_sql_type == 1}">
												    					<span style="color: green;">
												    				</c:when>
												    				<c:when test="${bean.vst_sql_type == 2}">
												    					<span style="color: gray;">
												    				</c:when>
												    				<c:when test="${bean.vst_sql_type == 3}">
												    					<span style="color: blue;">
												    				</c:when>
												    				<c:when test="${bean.vst_sql_type == 4}">
												    					<span style="color: pink;">
												    				</c:when>
												    				<c:otherwise>
												    					<span>
												    				</c:otherwise>
												    			</c:choose>
												    			
												    			<c:forEach items="${parentTypes}" var="parentType">
												    				<c:if test="${bean.vst_sql_type == parentType.key}">
												    					${parentType.value }
												    				</c:if>
												    			</c:forEach>
												    			<c:forEach items="${childTypes}" var="childType">
												    				<c:if test="${bean.vst_sql_type == childType.key}">
												    					${childType.value }
												    				</c:if>
												    			</c:forEach>
												    			</span>
															</li>
															<li style="width: 100px;">
																<c:choose>
																	<c:when test="${bean.isWarn == true}">
																		<span class="red">${bean.vst_sql_interval}</span>
																	</c:when>
																	<c:otherwise>
																		${bean.vst_sql_interval}
																	</c:otherwise>
																</c:choose>
															</li>
															<li style="width: 120px;" title="${bean.vst_sql_temp_table}">
																${bean.vst_sql_temp_table}
															</li>
															<li style="width: 150px;">
												    			<c:forEach items="${dbs}" var="db">
												    				<c:if test="${bean.vst_sql_db == db.key}">
												    					${db.value }
												    				</c:if>
												    			</c:forEach>
															</li>
															<li style="width: 150px;" title="${bean.vst_sql_table}">
																${bean.vst_sql_table}
															</li>
															<li class="myClick2" style="width: 80px;">
																${bean.vst_sql_priority}
															</li>
															<li class="myClick" style="width: 80px;">${bean.vst_sql_index}</li>
															<li style="width: 80px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_sql_state == 1}">
												    					<span style="color: green;">正常</span>
												    				</c:when>
												    				<c:when test="${bean.vst_sql_state == 2}">
												    					<span style="color: red;">禁用</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_sql_state}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
															<li style="width: 110px;">${bean.vst_sql_runtime}</li>
															<li style="width: 150px;" title="${bean.vst_sql_run_position}">
																${bean.vst_sql_run_position}
															</li>
															<li style="width: 100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_sql_run_model == 1}">
												    					<span style="color: green;">并行</span>
												    				</c:when>
												    				<c:when test="${bean.vst_sql_run_model == 2}">
												    					<span style="color: red;">串行</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_sql_run_model}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
												    		<li style="width: 100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_sql_is_format == 1}">
												    					<span style="color: red;">是</span>
												    				</c:when>
												    				<c:when test="${bean.vst_sql_is_format == 2}">
												    					否
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_sql_is_format}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
														</ul>
													</c:forEach>
													</div>
												</div>
											</div>
											<%@include file="../share/cutPage.jsp"%>
										</c:when>
									</c:choose>
									<c:if test="${cutPage._singleCount >= 15}">
										<input type="hidden" id="hidden_tablelist" name="hidden_tablelist" />
										<c:choose>
						    				<c:when test="${hidden_tablelist=='2' || hidden_tablelist==2}">
												<div class="tablelist-up-button" onclick="javascript:tablelist_open();">
													<img src="${ctx}/pub/listPages/images/search_close.png"/>
												</div>
						    				</c:when>
						    				<c:otherwise>
						    					<div class="tablelist-up-button" onclick="javascript:tablelist_open();">
													<img src="${ctx}/pub/listPages/images/search_open.png"/>
												</div>
						    				</c:otherwise>
						    			</c:choose>
						    		</c:if>
								</div>
							</div>
						</div>
					</div>
					</section>
				</form>
			</div>
		</div>
		<%@include file="../share/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
</script>
</html>