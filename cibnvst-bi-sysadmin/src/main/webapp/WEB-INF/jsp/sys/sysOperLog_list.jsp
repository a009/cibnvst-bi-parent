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
  		$("#vst_log_type").val($("#hidden_log_type").val());
  		
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
	
	// 删除
	function button_delete(){
		var starttime = $.trim($("#operate_starttime").val());
		var endtime = $.trim($("#operate_endtime").val());
		
		if(starttime == "" && endtime == ""){
	       	if(window.confirm("您确定要删除一个月内的记录吗?")){
	       		$("#listForm").attr("action", "${ctx}/sysOperateLog/deleteOperLogs.action");
			    $("#listForm").submit();
	       	}
	    }else if(starttime == "" && endtime != ""){
	        if(window.confirm("您确定要删除"+endtime+"之前的记录吗?")){
	        	$("#listForm").attr("action", "${ctx}/sysOperateLog/deleteOperLogs.action");
			    $("#listForm").submit();
			}
	    }else if(starttime != "" && endtime == ""){
	        if(window.confirm("您确定要删除"+starttime+"之后的记录吗?")){
	        	$("#listForm").attr("action", "${ctx}/sysOperateLog/deleteOperLogs.action");
			    $("#listForm").submit();
			}
	    }else if(starttime != "" && endtime != ""){
	        if(window.confirm("您确定要删除从"+starttime+"到"+endtime+"之间的记录吗?")){
	        	$("#listForm").attr("action", "${ctx}/sysOperateLog/deleteOperLogs.action");
			    $("#listForm").submit();
			}
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
				<form action="${ctx}/sysOperateLog/findOperLogs.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 操作日志查询</span>
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
												<span class="sertitle">日志ID：</span>
												<input class="list-input1" type="text" name="vst_log_id" value="${cutPage._queryParam.vst_log_id }" />
											</li>
											<li>
												<span class="sertitle">用户ID：</span>
												<input class="list-input1" type="text" name="vst_sys_id" value="${cutPage._queryParam.vst_sys_id }" />
											</li>
											<li>
												<span class="sertitle">登录账号：</span>
												<input class="list-input1" type="text" name="vst_sys_username" value="${cutPage._queryParam.vst_sys_username }" />
											</li>
											<li>
												<span class="sertitle">操作类型：</span>
												<input type="hidden" id="hidden_log_type" value="${cutPage._queryParam.vst_log_type }" />
												<select class="list-input1" id="vst_log_type" name="vst_log_type">
													<option value="">请选择</option>
													<option value="1">新增</option>
													<option value="2">修改</option>
													<option value="3">删除</option>
													<option value="4">导出</option>
												</select>
											</li>
											<li>
												<span class="sertitle">模块ID：</span>
												<input class="list-input1" type="text" name="vst_module_id" value="${cutPage._queryParam.vst_module_id }" />
											</li>
											<li>
												<span class="sertitle">操作模块：</span>
												<input class="list-input1" type="text" name="vst_module_name" value="${cutPage._queryParam.vst_module_name }" />
											</li>
											<li>
												<span class="sertitle">登录IP：</span>
												<input class="list-input1" type="text" name="vst_log_ip" value="${cutPage._queryParam.vst_log_ip }" />
											</li>
											<li>
												<span class="sertitle">开始时间：</span>
												<input class="list-input1 ic-calendar" id="operate_starttime" name="operate_starttime" value="${cutPage._queryParam.operate_starttime }" readonly="readonly" />
											</li>
											<li>
												<span class="sertitle">结束时间：</span>
												<input class="list-input1 ic-calendar" id="operate_endtime" name="operate_endtime" value="${cutPage._queryParam.operate_endtime }" readonly="readonly" />
											</li>
											<li>
												<span class="sertitle">操作内容：</span>
												<input class="list-input1" type="text" name="vst_log_content" value="${cutPage._queryParam.vst_log_content }" />
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
												<span class="red">提示：按照开始时间和结束时间删除，默认删除一个月内的数据！</span>
												
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
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_log_id_order">
																    <c:param name="orderBy" value="vst_log_id"/>
																    <c:param name="fieldName" value="日志ID"/>
															    </c:url>
															    <c:import url="${vst_log_id_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_sys_id_order">
																    <c:param name="orderBy" value="vst_sys_id"/>
																    <c:param name="fieldName" value="用户ID"/>
															    </c:url>
															    <c:import url="${vst_sys_id_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_sys_username_order">
																    <c:param name="orderBy" value="vst_sys_username"/>
																    <c:param name="fieldName" value="登录账号"/>
															    </c:url>
															    <c:import url="${vst_sys_username_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_log_type_order">
																    <c:param name="orderBy" value="vst_log_type"/>
																    <c:param name="fieldName" value="操作类型"/>
															    </c:url>
															    <c:import url="${vst_log_type_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_module_name_order">
																    <c:param name="orderBy" value="vst_module_name"/>
																    <c:param name="fieldName" value="操作模块"/>
															    </c:url>
															    <c:import url="${vst_module_name_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_log_ip_order">
																    <c:param name="orderBy" value="vst_log_ip"/>
																    <c:param name="fieldName" value="登录IP"/>
															    </c:url>
															    <c:import url="${vst_log_ip_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_log_addtime_order">
																    <c:param name="orderBy" value="vst_log_addtime"/>
																    <c:param name="fieldName" value="操作时间"/>
															    </c:url>
															    <c:import url="${vst_log_addtime_order}"/>
															</li>
															<li style="width:350px;">
																<c:url value="../share/order.jsp" var="vst_log_content_order">
																    <c:param name="orderBy" value="vst_log_content"/>
																    <c:param name="fieldName" value="操作内容"/>
															    </c:url>
															    <c:import url="${vst_log_content_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width:70px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_log_id}" />
															</li>
															<li style="width:200px;">${bean.vst_log_id}</li>
															<li style="width:200px;">${bean.vst_sys_id}</li>
															<li style="width:200px;">${bean.vst_sys_username}</li>
															<li style="width:200px;">
																<c:choose>
												    				<c:when test="${bean.vst_log_type == 1}">
												    					<span style="color: green;">新增</span>
												    				</c:when>
												    				<c:when test="${bean.vst_log_type == 2}">
												    					<span style="color: blue;">修改</span>
												    				</c:when>
												    				<c:when test="${bean.vst_log_type == 3}">
												    					<span style="color: red;">删除</span>
												    				</c:when>
												    				<c:when test="${bean.vst_log_type == 4}">
												    					<span style="color: purple;">导出</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_log_type}
												    				</c:otherwise>
												    			</c:choose>
															</li>
															<li style="width:200px;" title="${bean.vst_module_id}">
																${bean.vst_module_name}
															</li>
															<li style="width:200px;">${bean.vst_log_ip}</li>
												    		<li style="width:200px;">${bean.vst_log_addtime}</li>
												    		<li style="width:350px;text-align: left;">
												    			<c:url value="../share/remark_handler.jsp" var="vst_log_content_long">
																    <c:param name="remark" value="${bean.vst_log_content}"/>
															    </c:url>
															    <c:import url="${vst_log_content_long}"/>
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
	
<!--日历-->
<script type="text/javascript">
  	laydate.render({
	  	elem: '#operate_starttime',
	  	type: 'datetime',
	  	format: 'yyyy-MM-dd HH:mm:ss',
	  	theme: 'molv'
  	});
  	
  	laydate.render({
	  	elem: '#operate_endtime',
	  	type: 'datetime',
	  	format: 'yyyy-MM-dd HH:mm:ss',
	  	theme: 'molv'
  	});
</script>

</body>
</html>