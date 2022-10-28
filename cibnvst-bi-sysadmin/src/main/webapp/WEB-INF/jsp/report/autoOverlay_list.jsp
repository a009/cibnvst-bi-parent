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
  		$("#vst_overlay_state").val($("#hidden_overlay_state").val());
  		
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
		$("#listForm").attr("action", "${ctx}/autoOverlay/toAdd.action");
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
	        $("#listForm").attr("action", "${ctx}/autoOverlay/toEdit.action");
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
		        $("#listForm").attr("action", "${ctx}/autoOverlay/deleteAutoOverlay.action");
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
			$("#listForm").attr("action", "${ctx}/autoOverlay/modifyAutoOverlayState.action");
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
			$("#listForm").attr("action", "${ctx}/autoOverlay/modifyAutoOverlayState.action");
			$("#listForm").submit();
		}
	}

	// 复制
	function button_copy(){
		var modifyChecked="";			
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else{
	    	$("#recordId").val(modifyChecked);
	        $("#listForm").attr("action", "${ctx}/autoOverlay/toCopy.action");
			$("#listForm").submit();
	    }
	}
	
	/* ------------------------------ 替换 ---------------------------- */
	// 替换
	function button_replace(){
		var modifyChecked="";			
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else{
	    	$("#recordId").val(modifyChecked);
			// 打开弹窗
			$("#replace_myModal").attr("class","modal fade in");
			$("#replace_myModal").attr("aria-hidden","false");
			$("#replace_myModal").show();
			$("#replaceBoxForm")[0].reset();//重置
			$("#replaceSubmit").removeAttr("disabled");//解除提交按钮禁用状态
	    }
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"1",
	  		formid:"replaceBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
  		$("#replace_type").formValidator({
			validatorgroup:"1",
			onshow :"请选择属性类型！",
			onfocus :"请选择属性类型！",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :1,
			onerror :"属性类型必须选择！"
		});
		
		$("#replace_before").formValidator({
			validatorgroup:"1",
			onshow :"请输入替换前的值！",
			onfocus :"请输入替换前的值！",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :1,
			onerror :"替换前的值不允许为空！"
		});
		
		$("#replace_after").formValidator({
			validatorgroup:"1",
			onshow :"请输入替换后的值！",
			onfocus :"请输入替换后的值！",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :0,
			onerror :"替换后的值允许为空！"
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#replace_myModal").hide();
		});
		
		$("#replaceSubmit").click(function(){
			var flag = $.formValidator.pageIsValid("1");// 触发全局校验
			if(flag){
				var replace_type = $("#replace_type").val();
				var replace_before = $.trim($("#replace_before").val());
				var replace_after = $.trim($("#replace_after").val());
				
				var recordId = $("#recordId").val();
				var moduleId = $("#moduleId").val();
				$.ajax({
					url:"${ctx}/autoOverlay/ajaxReplaceOverlay.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"recordId=" + recordId+"&moduleId="+moduleId
						+"&replace_type="+replace_type+"&replace_before="+replace_before+"&replace_after="+replace_after,
					success:function(data){
						if(data != null && data != ''){
							// 关闭弹窗
							$("#replace_myModal").hide();
							alert("替换成功"+data+"条！点击查询才能看到最新的操作结果哟！");
						}else{
							alert("操作失败！请重试，若还是不成功，请联系VST后台管理人员！");
						}
					}
				});
			}else{
				return false;
			}
		});
	});


	/* ------------------------------ 排序生效 ---------------------------- */
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
			$(this).html("<input type='text' value='"+value+"'/>");
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
					url:"${ctx}/autoOverlay/modifyAutoOverlayIndexs.action",
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

	// 提交处理
	function doSubmit() {
		//提交后禁用提交按钮，避免多次提交
		$("#replaceSubmit").attr("disabled", "disabled");
		return true;
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			
			<!--替换弹窗-->
		    <div class="modal fade" id="replace_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		      <div class="modal-dialog modalGrap">
		          <div class="modal-content">
		              <div class="modal-header">
		                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                      &times;
		                  </button>
		                  <h4 class="modal-title">
	                     	<i class="fa fa-home"></i><a href="#">自动化管理</a><span>
								> 自动化(续加)> 替换</span>
							</section>
		                  </h4>
		              </div>
		              <form id="replaceBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
							 		<td class="tright" width="20%">属性选择:</td>
							 		<td width="50%">
										<select id="replace_type" name="replace_type">
											<option value="">请选择</option>
											<option value="1">代码编号</option>
											<option value="2">脚本</option>
										</select>
							 		</td>
							 		<td width="28%"><span id="replace_typeTip"></span></td>
							 	</tr>
							 	<tr>
							 		<td class="tright">替换前的值:</td>
							 		<td>
							 			<input class="list-input" type="text" id="replace_before" name="replace_before" />
							 		</td>
							 		<td><span id="replace_beforeTip"></span></td>
							 	</tr>
							 	<tr>
							 		<td class="tright">替换后的值:</td>
							 		<td>
							 			<input class="list-input" type="text" id="replace_after" name="replace_after" />
							 		</td>
							 		<td><span id="replace_afterTip"></span></td>
							 	</tr>
							</table>
							<div style="padding-bottom: 10px;text-align: center;">
							    <input type="submit" class="btnSubmit" id="replaceSubmit" value="提交" />
							    <input type="reset" class="btnReset" value="重置" />
							</div>
					  </form>
		          </div>
		      </div>
		    </div>
		    <!--替换弹窗-->
		    
			<div class="content-roll">
				<form action="${ctx}/autoOverlay/findAutoOverlays.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 自动化管理 > 自动化(续加)查询</span>
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
												<span class="sertitle">续加ID：</span>
												<input class="list-input1" type="text" name="vst_overlay_id" value="${cutPage._queryParam.vst_overlay_id }" />
											</li>
											<li>
												<span class="sertitle">代码编号：</span>
												<input class="list-input1" type="text" name="vst_code" value="${cutPage._queryParam.vst_code }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_overlay_state" value="${cutPage._queryParam.vst_overlay_state }" />
												<select class="list-input1" id="vst_overlay_state" name="vst_overlay_state">
													<option value="">请选择</option>
													<option value="1">正常</option>
													<option value="2">禁用</option>
												</select>
											</li>
											<li>
												<span class="sertitle">脚本：</span>
												<input class="list-input1" type="text" name="vst_overlay_script" value="${cutPage._queryParam.vst_overlay_script }" />
											</li>
											<li>
												<span class="sertitle">描述：</span>
												<input class="list-input1" type="text" name="vst_overlay_summary" value="${cutPage._queryParam.vst_overlay_summary }" />
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
																<c:url value="../share/order.jsp" var="vst_overlay_id_order">
																    <c:param name="orderBy" value="vst_overlay_id"/>
																    <c:param name="fieldName" value="续加ID"/>
															    </c:url>
															    <c:import url="${vst_overlay_id_order}"/>
															</li>
															<li style="width: 200px;">
																<c:url value="../share/order.jsp" var="vst_code_order">
																    <c:param name="orderBy" value="vst_code"/>
																    <c:param name="fieldName" value="代码编号"/>
															    </c:url>
															    <c:import url="${vst_code_order}"/>
															</li>
															<li style="width: 600px;">
																<c:url value="../share/order.jsp" var="vst_overlay_script_order">
																    <c:param name="orderBy" value="vst_overlay_script"/>
																    <c:param name="fieldName" value="脚本"/>
															    </c:url>
															    <c:import url="${vst_overlay_script_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_overlay_index_order">
																    <c:param name="orderBy" value="vst_overlay_index"/>
																    <c:param name="fieldName" value="排序"/>
															    </c:url>
															    <c:import url="${vst_overlay_index_order}"/>
															</li>
															<li style="width: 70px;">
																<c:url value="../share/order.jsp" var="vst_overlay_state_order">
																    <c:param name="orderBy" value="vst_overlay_state"/>
																    <c:param name="fieldName" value="状态"/>
															    </c:url>
															    <c:import url="${vst_overlay_state_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_overlay_addtime_order">
																    <c:param name="orderBy" value="vst_overlay_addtime"/>
																    <c:param name="fieldName" value="新增时间"/>
															    </c:url>
															    <c:import url="${vst_overlay_addtime_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_overlay_creator_order">
																    <c:param name="orderBy" value="vst_overlay_creator"/>
																    <c:param name="fieldName" value="创建人"/>
															    </c:url>
															    <c:import url="${vst_overlay_creator_order}"/>
															</li>
															<li style="width: 150px;">
																<c:url value="../share/order.jsp" var="vst_overlay_uptime_order">
																    <c:param name="orderBy" value="vst_overlay_uptime"/>
																    <c:param name="fieldName" value="修改时间"/>
															    </c:url>
															    <c:import url="${vst_overlay_uptime_order}"/>
															</li>
															<li style="width: 100px;">
																<c:url value="../share/order.jsp" var="vst_overlay_updator_order">
																    <c:param name="orderBy" value="vst_overlay_updator"/>
																    <c:param name="fieldName" value="修改人"/>
															    </c:url>
															    <c:import url="${vst_overlay_updator_order}"/>
															</li>
															<li style="width: 210px;">
																<c:url value="../share/order.jsp" var="vst_overlay_summary_order">
																    <c:param name="orderBy" value="vst_overlay_summary"/>
																    <c:param name="fieldName" value="描述"/>
															    </c:url>
															    <c:import url="${vst_overlay_summary_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width: 70px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_overlay_id}"
																	state="${bean.vst_overlay_state}"
																	index="${bean.vst_overlay_index}" />
															</li>
															<li style="width: 100px;">${bean.vst_overlay_id}</li>
															<li style="width: 200px;" title="${bean.vst_code}">
																${bean.vst_code}
															</li>
															<li style="width: 600px;" class="enlargeTextCon">
																${bean.vst_overlay_script}
															</li>
															<li class="myClick" style="width: 70px;">${bean.vst_overlay_index}</li>
															<li style="width: 70px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_overlay_state == 1}">
												    					<span style="color: green;">正常</span>
												    				</c:when>
												    				<c:when test="${bean.vst_overlay_state == 2}">
												    					<span style="color: red;">禁用</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_overlay_state}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
															<li style="width: 150px;">${bean.vst_overlay_addtime}</li>
															<li style="width: 100px;">${bean.vst_overlay_creator}</li>
												    		<li style="width: 150px;">${bean.vst_overlay_uptime}</li>
												    		<li style="width: 100px;">${bean.vst_overlay_updator}</li>
												    		<li style="width: 210px;" title="${bean.vst_overlay_summary}">
																${bean.vst_overlay_summary}
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