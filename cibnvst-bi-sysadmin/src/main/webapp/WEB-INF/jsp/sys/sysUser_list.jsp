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
  		$("#vst_sys_state").val($("#hidden_sys_state").val());
  		$("#vst_role_id").val($("#hidden_role_id").val());
  		$("#vst_sys_division").val($("#hidden_sys_division").val());
  		$("#vst_sys_job").val($("#hidden_sys_job").val());
  		
  		//点击图片放大
  		$.enlargePic(".single_img img");
  		
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
		$("#listForm").attr("action", "${ctx}/sysUser/toAdd.action");
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
	        $("#listForm").attr("action", "${ctx}/sysUser/toEdit.action");
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
		        $("#listForm").attr("action", "${ctx}/sysUser/deleteUser.action");
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
			$("#listForm").attr("action", "${ctx}/sysUser/modifyUserState.action");
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
			$("#listForm").attr("action", "${ctx}/sysUser/modifyUserState.action");
			$("#listForm").submit();
		}
	}
	
	// 重置密码
	function button_reset(){
		var modifyChecked="";
		var username = "";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
			username += $(this).attr("value2")+",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    username = username.substring(0, username.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else if(modifyChecked.indexOf(",") != -1){
	    	alert("每次最多选择一条记录进行该操作!");
			return false;
	    }else{
		    $("#recordId").val(modifyChecked);
	    	$("#username").val(username);
	        $("#listForm").attr("action", "${ctx}/sysUser/toReset.action");
			$("#listForm").submit();
	    }
	}

	// 发送邮件
	function button_sendEmail(){
		var date = prompt("请输入日期(yyyyMMdd)：", "");
		if(date == ""){
			alert("输入不能为空！");
		}else if(date != null){
			var r = date.match(/^(\d{8})$/); 
			if(r == null){
				alert("日期请输入正确的格式(yyyyMMdd)！");
			}else{
				$.ajax({
					url:"${ctx}/sysUser/ajaxSendEmail.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"date="+date,
					success:function(data){
						if(data.code == 100){
							alert("发送成功！请注意查收！");
						}else{
							alert("发送失败！");
						}
					}
				});
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
				<form action="${ctx}/sysUser/findUsers.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<input type="hidden" id="username" name="username" />
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统用户查询</span>
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
												<span class="sertitle">用户ID：</span>
												<input class="list-input1" type="text" name="vst_sys_id" value="${cutPage._queryParam.vst_sys_id }" />
											</li>
											<li>
												<span class="sertitle">用户名称：</span>
												<input class="list-input1" type="text" name="vst_sys_name" value="${cutPage._queryParam.vst_sys_name }" />
											</li>
											<li>
												<span class="sertitle">登录账号：</span>
												<input class="list-input1" type="text" name="vst_sys_username" value="${cutPage._queryParam.vst_sys_username }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_sys_state" value="${cutPage._queryParam.vst_sys_state }" />
												<select class="list-input1" id="vst_sys_state" name="vst_sys_state">
													<option value="">请选择</option>
													<option value="1">正常</option>
													<option value="2">禁用</option>
												</select>
											</li>
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
												<span class="sertitle">所属部门：</span>
												<input type="hidden" id="hidden_sys_division" value="${cutPage._queryParam.vst_sys_division }" />
							  	 				<select class="list-input1" name="vst_sys_division" id="vst_sys_division">
							  	 					<option value="">请选择</option>
							  	 					<c:forEach items="${divisions}" var="division">
							  	 						<option value="${division.key }">${division.value }</option>
							  	 					</c:forEach>
							  	 				</select>
							  	 			</li>
											<li>
												<span class="sertitle">职位：</span>
												<input type="hidden" id="hidden_sys_job" value="${cutPage._queryParam.vst_sys_job }" />
							  	 				<select class="list-input1" name="vst_sys_job" id="vst_sys_job">
							  	 					<option value="">请选择</option>
							  	 					<c:forEach items="${jobs}" var="job">
							  	 						<option value="${job.key }">${job.value }</option>
							  	 					</c:forEach>
							  	 				</select>
											</li>
											<li>
												<span class="sertitle">渠道号：</span>
												<input class="list-input1" type="text" name="vst_sys_channel" value="${cutPage._queryParam.vst_sys_channel }" />
											</li>
											<li>
												<span class="sertitle">备注：</span>
												<input class="list-input1" type="text" name="vst_sys_summary" value="${cutPage._queryParam.vst_sys_summary }" />
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
												<span class="red">提示：点击头像可以放大哟！效果还不错！试试看吧！</span>
												
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
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_sys_id_order">
																    <c:param name="orderBy" value="vst_sys_id"/>
																    <c:param name="fieldName" value="用户ID"/>
															    </c:url>
															    <c:import url="${vst_sys_id_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_sys_name_order">
																    <c:param name="orderBy" value="vst_sys_name"/>
																    <c:param name="fieldName" value="用户名称"/>
															    </c:url>
															    <c:import url="${vst_sys_name_order}"/>
															</li>
															<li style="width:200px;">
																<c:url value="../share/order.jsp" var="vst_sys_username_order">
																    <c:param name="orderBy" value="vst_sys_username"/>
																    <c:param name="fieldName" value="登录账号"/>
															    </c:url>
															    <c:import url="${vst_sys_username_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_sys_division_order">
																    <c:param name="orderBy" value="vst_sys_division"/>
																    <c:param name="fieldName" value="所属部门"/>
															    </c:url>
															    <c:import url="${vst_sys_division_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_sys_job_order">
																    <c:param name="orderBy" value="vst_sys_job"/>
																    <c:param name="fieldName" value="职位"/>
															    </c:url>
															    <c:import url="${vst_sys_job_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_sys_channel_order">
																    <c:param name="orderBy" value="vst_sys_channel"/>
																    <c:param name="fieldName" value="渠道号"/>
															    </c:url>
															    <c:import url="${vst_sys_channel_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_sys_photo_order">
																    <c:param name="orderBy" value="vst_sys_photo"/>
																    <c:param name="fieldName" value="头像"/>
															    </c:url>
															    <c:import url="${vst_sys_photo_order}"/>
															</li>
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_role_id_order">
																    <c:param name="orderBy" value="vst_role_id"/>
																    <c:param name="fieldName" value="角色"/>
															    </c:url>
															    <c:import url="${vst_role_id_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_sys_state_order">
																    <c:param name="orderBy" value="vst_sys_state"/>
																    <c:param name="fieldName" value="状态"/>
															    </c:url>
															    <c:import url="${vst_sys_state_order}"/>
															</li>
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_sys_addtime_order">
																    <c:param name="orderBy" value="vst_sys_addtime"/>
																    <c:param name="fieldName" value="新增时间"/>
															    </c:url>
															    <c:import url="${vst_sys_addtime_order}"/>
															</li>
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_sys_uptime_order">
																    <c:param name="orderBy" value="vst_sys_uptime"/>
																    <c:param name="fieldName" value="修改时间"/>
															    </c:url>
															    <c:import url="${vst_sys_uptime_order}"/>
															</li>
															<li style="width:100px;">
																<c:url value="../share/order.jsp" var="vst_sys_operator_order">
																    <c:param name="orderBy" value="vst_sys_operator"/>
																    <c:param name="fieldName" value="操作人"/>
															    </c:url>
															    <c:import url="${vst_sys_operator_order}"/>
															</li>
															<li style="width:150px;">
																<c:url value="../share/order.jsp" var="vst_sys_summary_order">
																    <c:param name="orderBy" value="vst_sys_summary"/>
																    <c:param name="fieldName" value="备注"/>
															    </c:url>
															    <c:import url="${vst_sys_summary_order}"/>
															</li>
														</ul>
													</div>
													
													<div class="table-tbody maxHeight" style="width: 1840px;">
													<c:forEach items="${dataList}" var="bean" varStatus="stuts">
														<ul style="width: 1820px;" <c:if test="${(stuts.index+1)%2!=0}">class="row1"</c:if><c:if test="${(stuts.index+1)%2==0}">class="row2"</c:if>>
															<li style="width:70px;">
																<c:if test="${bean.vst_sys_type!=0}">
																	<input type="checkbox" id="recordIds" name="recordIds"
																		value="${bean.vst_sys_id}"
																		state="${bean.vst_sys_state}"
																		value2="${bean.vst_sys_username }" />
																</c:if>
															</li>
															<li style="width:150px;">${bean.vst_sys_id}</li>
															<li style="width:200px;">${bean.vst_sys_name}</li>
												    		<li style="width:200px;">${bean.vst_sys_username}</li>
												    		<li style="width:100px;">
												    			<c:forEach items="${divisions}" var="division">
												    				<c:if test="${division.key == bean.vst_sys_division}">
												    					${division.value}
												    				</c:if>
												    			</c:forEach>
												    		</li>
												    		<li style="width:100px;">
												    			<c:forEach items="${jobs}" var="job">
												    				<c:choose>
												    					<c:when test="${bean.vst_sys_job == 1}">
												    						<span style="color: red;">
												    					</c:when>
												    					<c:when test="${bean.vst_sys_job == 2}">
												    						<span style="color: purple;">
												    					</c:when>
												    					<c:when test="${bean.vst_sys_job == 3}">
												    						<span style="color: blue;">
												    					</c:when>
												    					<c:when test="${bean.vst_sys_job == 4}">
												    						<span style="color: green;">
												    					</c:when>
												    					<c:when test="${bean.vst_sys_job == 5}">
												    						<span style="color: orange;">
												    					</c:when>
												    					<c:otherwise>
												    						<span></span>
												    					</c:otherwise>
												    				</c:choose>
												    				<c:if test="${job.key == bean.vst_sys_job}">
												    					${job.value}
												    				</c:if>
												    				</span>
												    			</c:forEach>
												    		</li>
												    		<li style="width:100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_sys_channel == 'all'}">全部</c:when>
												    				<c:otherwise>
												    					${bean.vst_sys_channel}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
												    		<li style="width:100px;">
												    			<a class="single_img">
							                     					<img src="${bean.vst_sys_photo}" alt="" style="width: 50px;height: 30px"/>
							                     				</a>
												    		</li>
												    		<li style="width:150px;">${bean.roleName}</li>
															<li style="width:100px;">
												    			<c:choose>
												    				<c:when test="${bean.vst_sys_state == 1}">
												    					<span style="color: green;">正常</span>
												    				</c:when>
												    				<c:when test="${bean.vst_sys_state == 2}">
												    					<span style="color: red;">禁用</span>
												    				</c:when>
												    				<c:otherwise>
												    					${bean.vst_sys_state}
												    				</c:otherwise>
												    			</c:choose>
												    		</li>
															<li style="width:150px;">${bean.vst_sys_addtime}</li>
												    		<li style="width:150px;">${bean.vst_sys_uptime}</li>
												    		<li style="width:100px;">${bean.vst_sys_operator}</li>
												    		<li style="width:150px;">
												    			<c:url value="../share/remark_handler.jsp" var="vst_sys_summary_long">
																    <c:param name="remark" value="${bean.vst_sys_summary}"/>
															    </c:url>
															    <c:import url="${vst_sys_summary_long}"/>
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