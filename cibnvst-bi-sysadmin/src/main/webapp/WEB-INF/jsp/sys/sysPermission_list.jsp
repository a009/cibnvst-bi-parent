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
  		$("#vst_role_id").val($("#hidden_role_id").val());
  		
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
		$("#listForm").attr("action", "${ctx}/sysPermission/toAdd.action");
		$("#listForm").submit();
	}
	
	// 删除
	function button_delete(){
		var modifyChecked="";
		var n = 0;
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + "_" + $(this).attr("value2") + "_" + $(this).attr("value3") + ",";
			n++;
		});
		
	    if(n == 0){
	       	alert("您还没有选择记录!");
			return false;
	    }else{
	        if(window.confirm("您确定要删除选择的"+n+"条记录吗?")){
	        	$("#recordId").val(modifyChecked);
	        	$("#listForm").attr("action", "${ctx}/sysPermission/deletePermissions.action");
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
				<form action="${ctx}/sysPermission/findPermissions.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统权限查询</span>
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
												<span class="sertitle">角色名称：</span>
												<input type="hidden" id="hidden_role_id" value="${cutPage._queryParam.vst_role_id }" />
							  	 				<select class="list-input1" name="vst_role_id" id="vst_role_id">
							  	 					<option value="">请选择</option>
							  	 					<c:forEach items="${roles}" var="role">
							  	 						<option value="${role.key }">${role.value }</option>
							  	 					</c:forEach>
							  	 				</select>
											</li>
											<li>
												<span class="sertitle">模块名称：</span>
												<input class="list-input1" type="text" name="vst_module_name" value="${cutPage._queryParam.vst_module_name }" />
											</li>
											<li>
												<span class="sertitle">按钮名称：</span>
												<input class="list-input1" type="text" name="vst_button_name" value="${cutPage._queryParam.vst_button_name }" />
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
												<span class="red">提示：红色代表已禁用！</span>
												
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
											
												<div class="table table-hover text-center" style="width: 1820px; max-width: 1840px;">
													<div class="table-thead" style="width: 1820px;">
														<ul id="rowHead" style="width: 1820px; background-color:#d2d6de;">
															<li style="width:80px;">
																<input type="checkbox" name="chkall" id="chkall" onclick="checkAll()" />全选
															</li>
															<li style="width:580px;">
																<c:url value="../share/order.jsp" var="vst_role_name_order">
																    <c:param name="orderBy" value="vst_role_name"/>
																    <c:param name="fieldName" value="角色名称"/>
															    </c:url>
															    <c:import url="${vst_role_name_order}"/>
															</li>
															<li style="width:580px;">
																<c:url value="../share/order.jsp" var="vst_module_name_order">
																    <c:param name="orderBy" value="vst_module_name"/>
																    <c:param name="fieldName" value="模块名称"/>
															    </c:url>
															    <c:import url="${vst_module_name_order}"/>
															</li>
															<li style="width:580px;">
																<c:url value="../share/order.jsp" var="vst_button_name_order">
																    <c:param name="orderBy" value="vst_button_name"/>
																    <c:param name="fieldName" value="按钮名称"/>
															    </c:url>
															    <c:import url="${vst_button_name_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width:80px;">
																<input type="checkbox" id="recordIds" name="recordIds"
																	value="${bean.vst_role_id}"
																	value2="${bean.vst_module_id}"
																	value3="${bean.vst_button_id}" />
															</li>
												    		<li style="width:580px;">
												    			<c:if test="${bean.vst_role_state == 1}">
												    				${bean.vst_role_name}
												    			</c:if>
												    			<c:if test="${bean.vst_role_state == 2}">
												    				<span style="color: red;" title="红色代表已禁用">${bean.vst_role_name}</span>
												    			</c:if>
												    		</li>
												    		<li style="width:580px;">
												    			<c:if test="${bean.vst_module_state == 1}">
												    				${bean.vst_module_name}
												    			</c:if>
												    			<c:if test="${bean.vst_module_state == 2}">
												    				<span style="color: red;" title="红色代表已禁用">${bean.vst_module_name}</span>
												    			</c:if>
												    		</li>
												    		<li style="width:580px;">
												    			<c:if test="${bean.vst_button_state == 1}">
												    				${bean.vst_button_name}
												    			</c:if>
												    			<c:if test="${bean.vst_button_state == 2}">
												    				<span style="color: red;" title="红色代表已禁用">${bean.vst_button_name}</span>
												    			</c:if>
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