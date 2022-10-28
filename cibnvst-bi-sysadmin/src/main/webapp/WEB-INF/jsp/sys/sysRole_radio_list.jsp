<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>角色列表</title>

<script type="text/javascript">
  	$(function(){
  		//下拉列表框，赋值
  		$("#vst_role_state").val($("#hidden_role_state").val());
  		
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
	
  	function validAdd(){
		var arrayObj = new Array(2);　//创建一个数组并指定长度,注意不是上限,是长度
		var modifyChecked="";
		var properChecked="";
		
		$("input:checked[type=checkbox][name='recordIds']").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
        	properChecked = properChecked + $(this).attr("proper") + ",";
		});		

	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    properChecked = properChecked.substring(0,properChecked.length-1);

	    arrayObj[0]=modifyChecked;
	    arrayObj[1]=properChecked;

	    if(modifyChecked==""){
			window.returnValue  = "";
			window.close();
			return;
		}else{
			if(navigator.userAgent.indexOf("Chrome") >0){// 这里是为了兼容谷歌浏览器
				window.opener.returnValue = arrayObj;
				window.opener.checkValue(arrayObj);
			}else{
				window.returnValue = arrayObj;
			}
			window.close();
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
		
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
			<form action="${ctx}/sysRole/getRoleList.action" id="listForm" method="post">
				<%@include file="../share/sharForm.jsp"%>
				<section class="content-header">
					<i class="fa fa-home"></i>
					<a href="#">首页</a>
					<span> > 系统管理 > 角色列表</span>
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
											<span class="sertitle">角色ID：</span>
											<input class="list-input1" type="text" name="vst_role_id" value="${cutPage._queryParam.vst_role_id }" />
										</li>
										<li>
											<span class="sertitle">角色名称：</span>
											<input class="list-input1" type="text" name="vst_role_name" value="${cutPage._queryParam.vst_role_name }" />
										</li>
										<li>
											<span class="sertitle">状态：</span>
											<input type="hidden" id="hidden_role_state" value="${cutPage._queryParam.vst_role_state }" />
											<select class="list-input1" id="vst_role_state" name="vst_role_state">
												<option value="">请选择</option>
												<option value="1">正常</option>
												<option value="2">禁用</option>
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
							<div class="box-body">
								<%@ include file="../share/noRecord.jsp"%>
								<c:choose>
									<c:when test="${(cutPage != null && cutPage._totalResults!=0) && cutPage._isQuery}">
										<div style="padding-bottom: 10px;">
											<span class="red">提示：请选择对应的角色点击确定按钮哟！效果还不错！试试看吧！</span>
											
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
														<input type="checkbox" name="chkall" id="chkall" onclick="checkAll()" />选择
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_id_order">
														    <c:param name="orderBy" value="vst_role_id"/>
														    <c:param name="fieldName" value="角色ID"/>
													    </c:url>
													    <c:import url="${vst_role_id_order}"/>
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_name_order">
														    <c:param name="orderBy" value="vst_role_name"/>
														    <c:param name="fieldName" value="角色名称"/>
													    </c:url>
													    <c:import url="${vst_role_name_order}"/>
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_state_order">
														    <c:param name="orderBy" value="vst_role_state"/>
														    <c:param name="fieldName" value="状态"/>
													    </c:url>
													    <c:import url="${vst_role_state_order}"/>
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_addtime_order">
														    <c:param name="orderBy" value="vst_role_addtime"/>
														    <c:param name="fieldName" value="新增时间"/>
													    </c:url>
													    <c:import url="${vst_role_addtime_order}"/>
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_uptime_order">
														    <c:param name="orderBy" value="vst_role_uptime"/>
														    <c:param name="fieldName" value="修改时间"/>
													    </c:url>
													    <c:import url="${vst_role_uptime_order}"/>
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_operator_order">
														    <c:param name="orderBy" value="vst_role_operator"/>
														    <c:param name="fieldName" value="操作人"/>
													    </c:url>
													    <c:import url="${vst_role_operator_order}"/>
													</li>
													<li style="width:250px;">
														<c:url value="../share/order.jsp" var="vst_role_summary_order">
														    <c:param name="orderBy" value="vst_role_summary"/>
														    <c:param name="fieldName" value="备注"/>
													    </c:url>
													    <c:import url="${vst_role_summary_order}"/>
													</li>
													</ul>
												</div>
												
												<div class="table-tbody maxHeight" style="width: 1840px;">
												<c:forEach items="${dataList}" var="bean" varStatus="stuts">
													<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
														<li style="width:70px;">
															<input type="checkbox" id="recordIds" name="recordIds"
																value="${bean.vst_role_id}"
																proper="${bean.vst_role_name}" />
														</li>
														<li style="width:250px;">${bean.vst_role_id}</li>
														<li style="width:250px;">${bean.vst_role_name}</li>
														<li style="width:250px;">
											    			<c:choose>
											    				<c:when test="${bean.vst_role_state == 1}">
											    					<span style="color: green;">正常</span>
											    				</c:when>
											    				<c:when test="${bean.vst_role_state == 2}">
											    					<span style="color: red;">禁用</span>
											    				</c:when>
											    				<c:otherwise>
											    					${bean.vst_role_state}
											    				</c:otherwise>
											    			</c:choose>
											    		</li>
														<li style="width:250px;">${bean.vst_role_addtime}</li>
											    		<li style="width:250px;">${bean.vst_role_uptime}</li>
											    		<li style="width:250px;">${bean.vst_role_operator}</li>
											    		<li style="width:250px;">
											    			<c:url value="../share/remark_handler.jsp" var="vst_role_summary_long">
															    <c:param name="remark" value="${bean.vst_role_summary}"/>
														    </c:url>
														    <c:import url="${vst_role_summary_long}"/>
											    		</li>
													</ul>
												</c:forEach>
												</div>
											</div>
											
										</div>
										<div class="mainbottom"></div>
<%--										<div style="width: 98.2%;padding: 4px 0 8px 8px;">--%>
										<div style="text-align: center;">
<%--											<ul>--%>
<%--												<li style="width:160px;">--%>
<%--													&nbsp;&nbsp;	&nbsp;&nbsp;  	&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;  &nbsp;&nbsp;	&nbsp;&nbsp;  	&nbsp;&nbsp; --%>
													<input type="button" name="btnAdd" value="确定" class="queryBtn"  onclick="validAdd();" />
<%--																		&nbsp;&nbsp;--%>
													<input type="button" value="取消" class="resetBtn" onclick="javascript:window.close();" />
<%--												</li>--%>
<%--											</ul>--%>
										</div>
<%--										</div>--%>
										
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