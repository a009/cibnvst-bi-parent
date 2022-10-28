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
  		$.enlargePic();
  		
  		//下拉列表框，赋值
  		$("#vst_topic_id").val($("#hidden_topic_id").val());
  		$("#vst_prop_state").val($("#hidden_prop_state").val());
  		$("#vst_prop_value_type").val($("#hidden_prop_value_type").val());
  		$("#vst_prop_value_required").val($("#hidden_prop_value_required").val());
  		
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
		$("#listForm").attr("action", "${ctx}/sysTopicProp/toAdd.action");
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
	        $("#listForm").attr("action", "${ctx}/sysTopicProp/toEdit.action");
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
		        $("#listForm").attr("action", "${ctx}/sysTopicProp/deleteSysTopicProp.action");
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
			$("#listForm").attr("action", "${ctx}/sysTopicProp/modifySysTopicPropState.action");
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
			$("#listForm").attr("action", "${ctx}/sysTopicProp/modifySysTopicPropState.action");
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
					url:"${ctx}/sysTopicProp/modifySysTopicPropIndexs.action",
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
				<form action="${ctx}/sysTopicProp/findSysTopicProps.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > topic属性配置查询</span>
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
												<span class="sertitle">属性ID：</span>
												<input class="list-input1" type="text" name="vst_prop_id" value="${cutPage._queryParam.vst_prop_id }" />
											</li>
											<li>
												<span class="sertitle">所属配置：</span>
												<input type="hidden" id="hidden_topic_id" value="${cutPage._queryParam.vst_topic_id }" />
												<select class="list-input1" id="vst_topic_id" name="vst_topic_id">
													<option value="">请选择</option>
													<c:forEach items="${topics}" var="topic">
														<option value="${topic.key }">${topic.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">属性名称：</span>
												<input class="list-input1" type="text" name="vst_prop_name" value="${cutPage._queryParam.vst_prop_name }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_prop_state" value="${cutPage._queryParam.vst_prop_state }" />
												<select class="list-input1" id="vst_prop_state" name="vst_prop_state">
													<option value="">请选择</option>
													<option value="1">正常</option>
													<option value="2">禁用</option>
												</select>
											</li>
											<li>
												<span class="sertitle">属性对应默认值：</span>
												<input class="list-input1" type="text" name="vst_prop_value_default" value="${cutPage._queryParam.vst_prop_value_default }" />
											</li>
											<li>
												<span class="sertitle">属性值类型：</span>
												<input type="hidden" id="hidden_prop_value_type" value="${cutPage._queryParam.vst_prop_value_type }" />
												<select class="list-input1" id="vst_prop_value_type" name="vst_prop_value_type">
													<option value="">请选择</option>
													<c:forEach items="${valueTypes}" var="valueType">
														<option value="${valueType.key }">${valueType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">是否必填：</span>
												<input type="hidden" id="hidden_prop_value_required" value="${cutPage._queryParam.vst_prop_value_required }" />
												<select class="list-input1" id="vst_prop_value_required" name="vst_prop_value_required">
													<option value="">请选择</option>
													<option value="1">是</option>
													<option value="2">否</option>
												</select>
											</li>
											<li>
												<span class="sertitle">属性值范围：</span>
												<input class="list-input1" type="text" name="vst_prop_value_range" value="${cutPage._queryParam.vst_prop_value_range }" />
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
												<div class="table table-hover text-center" style="width: 1820px;max-width: 1820px;">
													<div class="table-thead" style="width: 1820px;">
														<ul id="rowHead" style="background-color:#d2d6de;">
															<li style="width: 70px;">
																<input type="checkbox" name="chkall" id="chkall" onclick="checkAll()" />全选
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_prop_id_order">
																    <c:param name="orderBy" value="vst_prop_id"/>
																    <c:param name="fieldName" value="属性ID"/>
															    </c:url>
															    <c:import url="${vst_prop_id_order}"/>
															</li>
															<li style="width: 200px;">
																<c:url value="../share/order.jsp" var="vst_topic_id_order">
																    <c:param name="orderBy" value="vst_topic_id"/>
																    <c:param name="fieldName" value="[ID]所属配置"/>
															    </c:url>
															    <c:import url="${vst_topic_id_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_prop_name_order">
																    <c:param name="orderBy" value="vst_prop_name"/>
																    <c:param name="fieldName" value="属性名称"/>
															    </c:url>
															    <c:import url="${vst_prop_name_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_prop_value_default_order">
																    <c:param name="orderBy" value="vst_prop_value_default"/>
																    <c:param name="fieldName" value="属性对应默认值"/>
															    </c:url>
															    <c:import url="${vst_prop_value_default_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_prop_value_type_order">
																    <c:param name="orderBy" value="vst_prop_value_type"/>
																    <c:param name="fieldName" value="属性值类型"/>
															    </c:url>
															    <c:import url="${vst_prop_value_type_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_prop_value_required_order">
																    <c:param name="orderBy" value="vst_prop_value_required"/>
																    <c:param name="fieldName" value="是否必填"/>
															    </c:url>
															    <c:import url="${vst_prop_value_required_order}"/>
															</li>
															<li style="width: 140px;">
																<c:url value="../share/order.jsp" var="vst_prop_value_range_order">
																    <c:param name="orderBy" value="vst_prop_value_range"/>
																    <c:param name="fieldName" value="属性值范围"/>
															    </c:url>
															    <c:import url="${vst_prop_value_range_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_prop_index_order">
																    <c:param name="orderBy" value="vst_prop_index"/>
																    <c:param name="fieldName" value="排序"/>
															    </c:url>
															    <c:import url="${vst_prop_index_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_prop_state_order">
																    <c:param name="orderBy" value="vst_prop_state"/>
																    <c:param name="fieldName" value="状态"/>
															    </c:url>
															    <c:import url="${vst_prop_state_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_prop_addtime_order">
																    <c:param name="orderBy" value="vst_prop_addtime"/>
																    <c:param name="fieldName" value="新增时间"/>
															    </c:url>
															    <c:import url="${vst_prop_addtime_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_prop_creator_order">
																    <c:param name="orderBy" value="vst_prop_creator"/>
																    <c:param name="fieldName" value="创建人"/>
															    </c:url>
															    <c:import url="${vst_prop_creator_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_prop_uptime_order">
																    <c:param name="orderBy" value="vst_prop_uptime"/>
																    <c:param name="fieldName" value="修改时间"/>
															    </c:url>
															    <c:import url="${vst_prop_uptime_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_prop_updator_order">
																    <c:param name="orderBy" value="vst_prop_updator"/>
																    <c:param name="fieldName" value="修改人"/>
															    </c:url>
															    <c:import url="${vst_prop_updator_order}"/>
															</li>
															<li style="width: 200px;">
																<c:url value="../share/order.jsp" var="vst_prop_summary_order">
																    <c:param name="orderBy" value="vst_prop_summary"/>
																    <c:param name="fieldName" value="备注"/>
															    </c:url>
															    <c:import url="${vst_prop_summary_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width: 70px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_prop_id}"
																	state="${bean.vst_prop_state}"
																	index="${bean.vst_prop_index}" />
															</li>
															<li style="width: 100px;">${bean.vst_prop_id}</li>
															<li style="width: 200px;">
																[${bean.vst_topic_id}]
																<c:forEach items="${topics}" var="topic">
																	<c:if test="${bean.vst_topic_id == topic.key}">
																		${topic.value }
																	</c:if>
																</c:forEach>
															</li>
															<li style="width: 150px;" title="${bean.vst_prop_name}">
																${bean.vst_prop_name}
															</li>
															<li style="width: 150px;" title="${bean.vst_prop_value_default}">
																${bean.vst_prop_value_default}
															</li>
															<li style="width: 100px;">${bean.vst_prop_value_type}</li>
															<li style="width: 70px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_prop_value_required == 1}">
												    					<span style="color: green;">是</span>
												    				</c:when>
												    				<c:when test="${bean.vst_prop_value_required == 2}">
												    					<span style="color: red;">否</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_prop_value_required}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
												    		<li style="width: 140px;" class="enlargeTextCon">
												    			${bean.vst_prop_value_range}
												    		</li>
															<li class="myClick" style="width: 70px;">${bean.vst_prop_index}</li>
															<li style="width: 70px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_prop_state == 1}">
												    					<span style="color: green;">正常</span>
												    				</c:when>
												    				<c:when test="${bean.vst_prop_state == 2}">
												    					<span style="color: red;">禁用</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_prop_state}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
															<li style="width: 150px;">${bean.vst_prop_addtime}</li>
															<li style="width: 100px;">${bean.vst_prop_creator}</li>
												    		<li style="width: 150px;">${bean.vst_prop_uptime}</li>
												    		<li style="width: 100px;">${bean.vst_prop_updator}</li>
												    		<li style="width: 200px;" title="${bean.vst_prop_summary}">
																${bean.vst_prop_summary}
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