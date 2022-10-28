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
  		$("#vst_module_id").val($("#hidden_module_id").val());
  		$("#vst_task_state").val($("#hidden_task_state").val());

  		if('${isAdmin}' == 'true'){
  	  		$("#param_creator").show();
  		}else{
  			$("#param_creator").hide();
  		}
  		
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

  	// 下载文件
	function download(url){
		if(url == null || url == ''){
			alert("下载内容不可为空！");
		}else{
			location.href = "${ctx}/exportTask/downloadFile.action?url="+url;
		}
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
				<form action="${ctx}/exportTask/findExportTasks.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 任务管理 > 导出任务查询</span>
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
												<input class="list-input1" type="text" name="vst_task_id" value="${cutPage._queryParam.vst_task_id }" />
											</li>
											<li>
												<span class="sertitle">模块名称：</span>
												<input type="hidden" id="hidden_module_id" value="${cutPage._queryParam.vst_module_id }" />
												<select class="list-input1" id="vst_module_id" name="vst_module_id">
													<option value="">请选择</option>
													<c:forEach items="${moduleList}" var="module">
														<option value="${module.key }">${module.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">导出表名：</span>
												<input class="list-input1" type="text" name="vst_task_table" value="${cutPage._queryParam.vst_task_table }" />
											</li>
											<li>
												<span class="sertitle">导出字段：</span>
												<input class="list-input1" type="text" name="vst_task_columns" value="${cutPage._queryParam.vst_task_columns }" />
											</li>
											<li>
												<span class="sertitle">导出条件：</span>
												<input class="list-input1" type="text" name="vst_task_condition" value="${cutPage._queryParam.vst_task_condition }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_task_state" value="${cutPage._queryParam.vst_task_state }" />
												<select class="list-input1" id="vst_task_state" name="vst_task_state">
													<option value="">请选择</option>
													<option value="0">进行中</option>
													<option value="1">已完成</option>
													<option value="2">异常</option>
												</select>
											</li>
											<li>
												<span class="sertitle">文件路径：</span>
												<input class="list-input1" type="text" name="vst_task_file_path" value="${cutPage._queryParam.vst_task_file_path }" />
											</li>
											<li>
												<span class="sertitle">文件名：</span>
												<input class="list-input1" type="text" name="vst_task_file_name" value="${cutPage._queryParam.vst_task_file_name }" />
											</li>
											<li id="param_creator" style="display: none;">
												<span class="sertitle">操作人：</span>
												<input class="list-input1" type="text" name="vst_task_creator" value="${cutPage._queryParam.vst_task_creator }" />
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
												<span class="red">提示：点击地址可以下载哦！</span>
												
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
											<div id="tableExample1" class="table_movieList_list">
											
												<div class="table table-hover text-center" style="width: 1820px; max-width: 1820px;">
													<div class="table-thead" style="width: 1820px;">
														<ul id="rowHead" style="width: 1820px; background-color:#d2d6de;">
															<li style="width:70px;">
																<input type="checkbox" name="chkall" id="chkall" onclick="checkAll()" />全选
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_task_id_order">
																    <c:param name="orderBy" value="vst_task_id"/>
																    <c:param name="fieldName" value="任务ID"/>
															    </c:url>
															    <c:import url="${vst_task_id_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_module_id_order">
																    <c:param name="orderBy" value="vst_module_id"/>
																    <c:param name="fieldName" value="<font style='color:orange'>模块ID</font>"/>
															    </c:url>
															    <c:import url="${vst_module_id_order}"/>
															</li>
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_task_table_order">
																    <c:param name="orderBy" value="vst_task_table"/>
																    <c:param name="fieldName" value="导出表名"/>
															    </c:url>
															    <c:import url="${vst_task_table_order}"/>
															</li>
															<li style="width:340px;">
																<c:url value="../share/order.jsp" var="vst_task_columns_order">
																    <c:param name="orderBy" value="vst_task_columns"/>
																    <c:param name="fieldName" value="导出字段"/>
															    </c:url>
															    <c:import url="${vst_task_columns_order}"/>
															</li>
															<li style="width:340px;">
																<c:url value="../share/order.jsp" var="vst_task_condition_order">
																    <c:param name="orderBy" value="vst_task_condition"/>
																    <c:param name="fieldName" value="导出条件"/>
															    </c:url>
															    <c:import url="${vst_task_condition_order}"/>
															</li>
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_task_file_path_order">
																    <c:param name="orderBy" value="vst_task_file_path"/>
																    <c:param name="fieldName" value="下载地址"/>
															    </c:url>
															    <c:import url="${vst_task_file_path_order}"/>
															</li>
															<li style="width:170px;">
																<c:url value="../share/order.jsp" var="vst_task_file_name_order">
																    <c:param name="orderBy" value="vst_task_file_name"/>
																    <c:param name="fieldName" value="文件名"/>
															    </c:url>
															    <c:import url="${vst_task_file_name_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_task_file_size_order">
																    <c:param name="orderBy" value="vst_task_file_size"/>
																    <c:param name="fieldName" value="文件大小(KB)"/>
															    </c:url>
															    <c:import url="${vst_task_file_size_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_task_state_order">
																    <c:param name="orderBy" value="vst_task_state"/>
																    <c:param name="fieldName" value="状态"/>
															    </c:url>
															    <c:import url="${vst_task_state_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_task_creator_order">
																    <c:param name="orderBy" value="vst_task_creator"/>
																    <c:param name="fieldName" value="操作人"/>
															    </c:url>
															    <c:import url="${vst_task_creator_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width:70px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_task_id}"
																	state="${bean.vst_task_state}" />
															</li>
															<li style="width:100px;">${bean.vst_task_id}</li>
															<li style="width:200px;" class="enlargeTextTitle"
																data-title="<table style='color:#eee;'>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>新增时间：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_task_addtime}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>创建人：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_task_creator}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>修改时间：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_task_uptime}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>修改人：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_task_updator}</p></td>
																	</tr>
																	<tr>
																		<td><p style='max-width:150px;word-wrap:break-word;white-space:pre-wrap;'>备注：</p></td>
																		<td><p style='max-width:250px;word-wrap:break-word;white-space:pre-wrap;'>${bean.vst_task_summary}</p></td>
																	</tr>
																</table>">
																[${bean.vst_module_id}]
																<c:if test="${bean.vst_module_id != ''}">
																	<c:forEach items="${moduleList}" var="module">
																		<c:if test="${module.key == bean.vst_module_id}">
																			${module.value }
																		</c:if>
																	</c:forEach>
																</c:if>
															</li>
															<li style="width:150px;" title="${bean.vst_task_table}">
																${bean.vst_task_table}
															</li>
															<li style="width:340px;" title="${bean.vst_task_columns}" class="columns${stuts.index}">
																<script type="text/javascript">
																	var str = formatString("${bean.vst_task_columns}",50);
																	$(".columns${stuts.index}").html(str);
																</script>
															</li>
															<li style="width:340px;" title="${bean.vst_task_condition}" class="condition${stuts.index}">
																<script type="text/javascript">
																	var str = formatString("${bean.vst_task_condition}",50);
																	$(".condition${stuts.index}").html(str);
																</script>
															</li>
															<li style="width:150px;">
																<a href="javascript:download('${bean.vst_task_file_path}');">
																	[点击下载]
																</a>
															</li>
															<li style="width:170px;" title="${bean.vst_task_file_name}">
																${bean.vst_task_file_name}
															</li>
															<li style="width:100px;">
																${bean.vst_task_file_size_kb}
															</li>
															<li style="width:100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_task_state == 0}">
												    					<span>进行中</span>
												    				</c:when>
												    				<c:when test="${bean.vst_task_state == 1}">
												    					<span style="color: green;">已完成</span>
												    				</c:when>
												    				<c:when test="${bean.vst_task_state == 2}">
												    					<span style="color: red;">异常</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_task_state}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
												    		<li style="width:100px;">
																${bean.vst_task_creator}
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
</html>