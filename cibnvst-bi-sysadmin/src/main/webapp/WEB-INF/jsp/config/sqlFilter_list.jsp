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
  		$("#vst_sql_id").val($("#hidden_sql_id").val());
  		$("#vst_filter_state").val($("#hidden_filter_state").val());
  		$("#vst_filter_match_type").val($("#hidden_filter_match_type").val());
  		$("#vst_filter_yes_type").val($("#hidden_filter_yes_type").val());
  		$("#vst_filter_no_type").val($("#hidden_filter_no_type").val());
  		$("#vst_filter_action_type").val($("#hidden_filter_action_type").val());
  		$("#vst_filter_pkg").val($("#hidden_filter_pkg").val());
  		$("#vst_filter_pkg_block").val($("#hidden_filter_pkg_block").val());

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
		$("#listForm").attr("action", "${ctx}/sqlFilter/toAdd.action");
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
	        $("#listForm").attr("action", "${ctx}/sqlFilter/toEdit.action");
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
		        $("#listForm").attr("action", "${ctx}/sqlFilter/deleteSqlFilter.action");
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
			$("#listForm").attr("action", "${ctx}/sqlFilter/modifySqlFilterState.action");
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
			$("#listForm").attr("action", "${ctx}/sqlFilter/modifySqlFilterState.action");
			$("#listForm").submit();
		}
	}

	// 批量添加
	function button_batchAdd(){
		$("#listForm").attr("action", "${ctx}/sqlFilter/toBatchAdd.action");
		$("#listForm").submit();
	}

	// 复制新增
	function button_copyAdd(){
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
	        $("#listForm").attr("action", "${ctx}/sqlFilter/toCopyAdd.action");
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
					url:"${ctx}/sqlFilter/modifySqlFilterIndexs.action",
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
				<form action="${ctx}/sqlFilter/findSqlFilters.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > sql筛选配置查询</span>
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
												<span class="sertitle">筛选ID：</span>
												<input class="list-input1" type="text" name="vst_filter_id" value="${cutPage._queryParam.vst_filter_id }" />
											</li>
											<li>
												<span class="sertitle">所属任务：</span>
												<input type="hidden" id="hidden_sql_id" value="${cutPage._queryParam.vst_sql_id }" />
												<select class="list-input1" id="vst_sql_id" name="vst_sql_id">
													<option value="">请选择</option>
													<c:forEach items="${sqls}" var="sql">
														<option value="${sql.key }">
															${sql.value.vst_sql_name }
															<c:if test="${sql.value.vst_sql_state == 2}">(禁用)</c:if>
														</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">筛选key：</span>
												<input class="list-input1" type="text" name="vst_filter_key" value="${cutPage._queryParam.vst_filter_key }" />
											</li>
											<li>
												<span class="sertitle">筛选value：</span>
												<input class="list-input1" type="text" name="vst_filter_value" value="${cutPage._queryParam.vst_filter_value }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_filter_state" value="${cutPage._queryParam.vst_filter_state }" />
												<select class="list-input1" id="vst_filter_state" name="vst_filter_state">
													<option value="">请选择</option>
													<option value="1">正常</option>
													<option value="2">禁用</option>
												</select>
											</li>
											<li>
												<span class="sertitle">匹配类型：</span>
												<input type="hidden" id="hidden_filter_match_type" value="${cutPage._queryParam.vst_filter_match_type }" />
												<select class="list-input1" id="vst_filter_match_type" name="vst_filter_match_type">
													<option value="">请选择</option>
													<c:forEach items="${matchTypes}" var="matchType">
														<option value="${matchType.key }">${matchType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">匹配上处理类型：</span>
												<input type="hidden" id="hidden_filter_yes_type" value="${cutPage._queryParam.vst_filter_yes_type }" />
												<select class="list-input1" id="vst_filter_yes_type" name="vst_filter_yes_type">
													<option value="">请选择</option>
													<option value="1">通过</option>
													<option value="2">不通过</option>
												</select>
											</li>
											<li>
												<span class="sertitle">未匹配处理类型：</span>
												<input type="hidden" id="hidden_filter_no_type" value="${cutPage._queryParam.vst_filter_no_type }" />
												<select class="list-input1" id="vst_filter_no_type" name="vst_filter_no_type">
													<option value="">请选择</option>
													<option value="1">通过</option>
													<option value="2">不通过</option>
												</select>
											</li>
											<li>
												<span class="sertitle">操作类型：</span>
												<input type="hidden" id="hidden_filter_action_type" value="${cutPage._queryParam.vst_filter_action_type }" />
												<select class="list-input1" id="vst_filter_action_type" name="vst_filter_action_type">
													<option value="">请选择</option>
													<c:forEach items="${actionTypes}" var="actionType">
														<option value="${actionType.key }">${actionType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">动作key：</span>
												<input class="list-input1" type="text" name="vst_filter_action_key" value="${cutPage._queryParam.vst_filter_action_key }" />
											</li>
											<li>
												<span class="sertitle">动作value：</span>
												<input class="list-input1" type="text" name="vst_filter_action_value" value="${cutPage._queryParam.vst_filter_action_value }" />
											</li>
											<li>
												<span class="sertitle">适用包名：</span>
												<input type="hidden" id="hidden_filter_pkg" value="${cutPage._queryParam.vst_filter_pkg }" />
												<select class="list-input1" id="vst_filter_pkg" name="vst_filter_pkg">
													<option value="">请选择</option>
													<option value="0">全部</option>
													<c:forEach items="${pkgs}" var="pkg">
														<option value="${pkg.key }">${pkg.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">屏蔽包名：</span>
												<input type="hidden" id="hidden_filter_pkg_block" value="${cutPage._queryParam.vst_filter_pkg_block }" />
												<select class="list-input1" id="vst_filter_pkg_block" name="vst_filter_pkg_block">
													<option value="">请选择</option>
													<option value="0">没有</option>
													<c:forEach items="${pkgs}" var="pkg">
														<option value="${pkg.key }">${pkg.value }</option>
													</c:forEach>
												</select>
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
												<span class="red">提示：点击排序值可以修改哟！效果还不错！试试看吧！</span>
												
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
												<div class="table table-hover text-center" style="width: 2020px;max-width: 2020px;">
													<div class="table-thead" style="width: 2020px;">
														<ul id="rowHead" style="background-color:#d2d6de;">
															<li style="width: 100px;">
																<input type="checkbox" name="chkall" id="chkall" onclick="checkAll()" />
																<c:url value="../share/order.jsp" var="vst_filter_id_order">
																    <c:param name="orderBy" value="vst_filter_id"/>
																    <c:param name="fieldName" value="全选"/>
															    </c:url>
															    <c:import url="${vst_filter_id_order}"/>
															</li>
															<li style="width: 200px;">
																<c:url value="../share/order.jsp" var="vst_sql_id_order">
																    <c:param name="orderBy" value="vst_sql_id"/>
																    <c:param name="fieldName" value="[ID]所属任务"/>
															    </c:url>
															    <c:import url="${vst_sql_id_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_key_order">
																    <c:param name="orderBy" value="vst_filter_key"/>
																    <c:param name="fieldName" value="筛选key"/>
															    </c:url>
															    <c:import url="${vst_filter_key_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_value_order">
																    <c:param name="orderBy" value="vst_filter_value"/>
																    <c:param name="fieldName" value="筛选value"/>
															    </c:url>
															    <c:import url="${vst_filter_value_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_match_type_order">
																    <c:param name="orderBy" value="vst_filter_match_type"/>
																    <c:param name="fieldName" value="匹配类型"/>
															    </c:url>
															    <c:import url="${vst_filter_match_type_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_yes_type_order">
																    <c:param name="orderBy" value="vst_filter_yes_type"/>
																    <c:param name="fieldName" value="匹配上处理类型"/>
															    </c:url>
															    <c:import url="${vst_filter_yes_type_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_no_type_order">
																    <c:param name="orderBy" value="vst_filter_no_type"/>
																    <c:param name="fieldName" value="未匹配处理类型"/>
															    </c:url>
															    <c:import url="${vst_filter_no_type_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_action_type_order">
																    <c:param name="orderBy" value="vst_filter_action_type"/>
																    <c:param name="fieldName" value="操作类型"/>
															    </c:url>
															    <c:import url="${vst_filter_action_type_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_action_key_order">
																    <c:param name="orderBy" value="vst_filter_action_key"/>
																    <c:param name="fieldName" value="动作key"/>
															    </c:url>
															    <c:import url="${vst_filter_action_key_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_action_value_order">
																    <c:param name="orderBy" value="vst_filter_action_value"/>
																    <c:param name="fieldName" value="动作value"/>
															    </c:url>
															    <c:import url="${vst_filter_action_value_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_filter_index_order">
																    <c:param name="orderBy" value="vst_filter_index"/>
																    <c:param name="fieldName" value="排序"/>
															    </c:url>
															    <c:import url="${vst_filter_index_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_filter_state_order">
																    <c:param name="orderBy" value="vst_filter_state"/>
																    <c:param name="fieldName" value="状态"/>
															    </c:url>
															    <c:import url="${vst_filter_state_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_pkg_order">
																    <c:param name="orderBy" value="vst_filter_pkg"/>
																    <c:param name="fieldName" value="<font style='color:orange'>适用包名</font>"/>
															    </c:url>
															    <c:import url="${vst_filter_pkg_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_pkg_block_order">
																    <c:param name="orderBy" value="vst_filter_pkg_block"/>
																    <c:param name="fieldName" value="<font style='color:orange'>屏蔽包名</font>"/>
															    </c:url>
															    <c:import url="${vst_filter_pkg_block_order}"/>
															</li>
															<li style="width: 110px;">
																<c:url value="../share/order.jsp" var="vst_filter_addtime_order">
																    <c:param name="orderBy" value="vst_filter_addtime"/>
																    <c:param name="fieldName" value="新增时间"/>
															    </c:url>
															    <c:import url="${vst_filter_addtime_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_creator_order">
																    <c:param name="orderBy" value="vst_filter_creator"/>
																    <c:param name="fieldName" value="创建人"/>
															    </c:url>
															    <c:import url="${vst_filter_creator_order}"/>
															</li>
															<li style="width: 110px;">
																<c:url value="../share/order.jsp" var="vst_filter_uptime_order">
																    <c:param name="orderBy" value="vst_filter_uptime"/>
																    <c:param name="fieldName" value="修改时间"/>
															    </c:url>
															    <c:import url="${vst_filter_uptime_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_filter_updator_order">
																    <c:param name="orderBy" value="vst_filter_updator"/>
																    <c:param name="fieldName" value="修改人"/>
															    </c:url>
															    <c:import url="${vst_filter_updator_order}"/>
															</li>
															<li style="width: 160px;">
																<c:url value="../share/order.jsp" var="vst_filter_summary_order">
																    <c:param name="orderBy" value="vst_filter_summary"/>
																    <c:param name="fieldName" value="备注"/>
															    </c:url>
															    <c:import url="${vst_filter_summary_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 2040px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 2020px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width: 100px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_filter_id}"
																	state="${bean.vst_filter_state}"
																	index="${bean.vst_filter_index}"
																	style="width:auto; vertical-align: middle; margin:0 0 0 10px;" />
																<span style="display:inline-block;line-height:32px;">${bean.vst_filter_id}</span>
															</li>
															<li style="width: 200px;">
																[${bean.vst_sql_id}]
																<c:forEach items="${sqls}" var="sql">
																	<c:if test="${bean.vst_sql_id == sql.key}">
																		<span <c:if test="${sql.value.vst_sql_state == 2}">class='red'</c:if>>
																			${sql.value.vst_sql_name }
																		</span>
																	</c:if>
																</c:forEach>
															</li>
															<li style="width: 100px;" title="${bean.vst_filter_key}">${bean.vst_filter_key}</li>
															<li style="width: 100px;" title="${bean.vst_filter_value}">${bean.vst_filter_value}</li>
															<li style="width: 100px;">
																<c:forEach items="${matchTypes}" var="matchType">
																	<c:if test="${bean.vst_filter_match_type == matchType.key}">
																		${matchType.value }
																	</c:if>
																</c:forEach>
															</li>
															<li style="width: 100px;">
																<c:choose>
																	<c:when test="${bean.vst_filter_yes_type == 1}">
																		<span style="color: green;">通过</span>
																	</c:when>
																	<c:when test="${bean.vst_filter_yes_type == 2}">
																		<span style="color: red;">不通过</span>
																	</c:when>
																	<c:otherwise>${bean.vst_filter_yes_type}</c:otherwise>
																</c:choose>
															</li>
															<li style="width: 100px;">
																<c:choose>
																	<c:when test="${bean.vst_filter_no_type == 1}">
																		<span style="color: green;">通过</span>
																	</c:when>
																	<c:when test="${bean.vst_filter_no_type == 2}">
																		<span style="color: red;">不通过</span>
																	</c:when>
																	<c:otherwise>${bean.vst_filter_no_type}</c:otherwise>
																</c:choose>
															</li>
															<li style="width: 100px;">
																<c:forEach items="${actionTypes}" var="actionType">
																	<c:if test="${bean.vst_filter_action_type == actionType.key}">
																		${actionType.value }
																	</c:if>
																</c:forEach>
															</li>
															<li style="width: 100px;" title="${bean.vst_filter_action_key}">${bean.vst_filter_action_key}</li>
															<li style="width: 100px;" title="${bean.vst_filter_action_value}">${bean.vst_filter_action_value}</li>
															<li class="myClick" style="width: 70px;">
																${bean.vst_filter_index}
															</li>
															<li style="width: 70px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_filter_state == 1}">
												    					<span style="color: green;">正常</span>
												    				</c:when>
												    				<c:when test="${bean.vst_filter_state == 2}">
												    					<span style="color: red;">禁用</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_filter_state}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
												    		<li style="width: 100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_filter_pkg == '0'}">全部</c:when>
												    				<c:otherwise>
												    					<span class="enlargeTextCon">${bean.vst_filter_pkg_name}</span>
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
												    		<li style="width: 100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_filter_pkg_block == '0'}">没有</c:when>
												    				<c:otherwise>
												    					<span class="enlargeTextCon">${bean.vst_filter_pkg_block_name}</span>
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
															<li style="width: 110px;">${bean.vst_filter_addtime}</li>
															<li style="width: 100px;">${bean.vst_filter_creator}</li>
												    		<li style="width: 110px;">${bean.vst_filter_uptime}</li>
												    		<li style="width: 100px;">${bean.vst_filter_updator}</li>
												    		<li style="width: 160px;" title="${bean.vst_filter_summary}">
																${bean.vst_filter_summary}
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