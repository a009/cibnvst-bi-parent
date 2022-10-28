<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<%@include file="../share/biPlugin.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	$(document).ready(function(){
		//进入页面时，自动查询
		button_getdata();
		//文本框，按回车触发查询
		$("#vst_version_name").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDay").val() == '' || $("#endDay").val() == ''){
			alert("查询日期不可为空！");
		}else{
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_version_name=" + $("#vst_version_name").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=version_table",
			dataType:"json",
			success:function(msg){
				$("#move_loading").attr("style","display:none");
				if(msg.code == 100){
					showTable(msg);
				}else{
					$("#move").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo(0, $("#singleCount").val());
				}
			},
			error:function(msg){
				$("#move_loading").attr("style","display:none");
			}
		});
	}
	
	function showTable(msg){
		var data = msg.data;
		var totalCount = msg.info.totalCount;
		var singleCount = msg.info.singleCount;
		$("#currPage").val(msg.info.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_date' id='vst_version_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_name' id='vst_version_name2' order='desc'>版本</a></th>";

		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_total_user_number' id='vst_version_total_user_number2' order='desc'>累计用户数</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_total_user_rate' id='vst_version_total_user_rate2' order='desc'>累计用户率</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_new_user_number' id='vst_version_new_user_number2' order='desc'>新增用户数</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_upgrade_user_number' id='vst_version_upgrade_user_number2' order='desc'>升级用户数</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_active_user_number' id='vst_version_active_user_number2' order='desc'>活跃用户数</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_active_user_rate' id='vst_version_active_user_rate2' order='desc'>活跃用户率</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_startup_times' id='vst_version_startup_times2' order='desc'>启动次数</a></th>";
		
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_uptime' id='vst_version_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_version_operator' id='vst_version_operator2' order='desc'>操作人</a></th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_version_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getDateWeek(n.vst_version_date+'', 'yyyyMMdd')+"</td>";
			if(typeof(n.vst_version_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_version_name+"</td>";
			
			if(typeof(n.vst_version_total_user_number)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_version_total_user_number)+"</td>";
			if(typeof(n.vst_version_total_user_rate)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_version_total_user_rate+"</td>";
			if(typeof(n.vst_version_new_user_number)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_version_new_user_number)+"</td>";
			if(typeof(n.vst_version_upgrade_user_number)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_version_upgrade_user_number)+"</td>";
			if(typeof(n.vst_version_active_user_number)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_version_active_user_number)+"</td>";
			if(typeof(n.vst_version_active_user_rate)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_version_active_user_rate+"</td>";
			if(typeof(n.vst_version_startup_times)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_version_startup_times)+"</td>";
			
			if(typeof(n.vst_version_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatDate(n.vst_version_uptime, "yyyy-MM-dd HH:mm:ss")+"</td>";
			if(typeof(n.vst_version_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_version_operator+"</td>";

			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move").html(table);
		showOrder(orderBy, order);
	}
	
	// 导出数据
	function button_export(){
		var startDay = $("#startDay").val();
		var endDay = $("#endDay").val();
		
		if(!isNotEmpty(startDay) || !isNotEmpty(endDay) || getDateDiff(startDay, endDay, 'day') > 92){
			alert("亲，最多只能导出三个月的数据哦")
			return false;
		}else if(confirm("导出速度，由数据大小决定，请耐心等待。。")){
			var params = "startDay=" + startDay + "&endDay=" + endDay;
			params += "&pkgName=" + $("#pkgName").val();
			params += "&vst_version_name=" + $("#vst_version_name").val();
			
			$.ajax({
				type: "POST",
				data: params,
				async : true,
				cache : false,
				url:"${ctx}/report/json?code=version_export",
				dataType:"json",
				success:function(msg){
					if(msg.code == 100){
						location.href= "${ctx}/version/exportData.action?data="+JSON.stringify(msg.data)
								+"&moduleId="+$("#moduleId").val()+"&startDay=" + startDay + "&endDay=" + endDay;
					}else{
						alert(msg.msg);
					}
				},
				error:function(msg){
					alert(msg);
				}
			});
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
				<form action="${ctx}/version/findVersion.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 用户统计 > 版本统计</span>
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
												<span class="sertitle">起止日期：</span>
												<input id="daterangepickerInputDate" type="text" class="list-input1" readonly="readonly">
											</li>
											<li style="display: none;">
												<span class="sertitle">起始日期：</span>
												<input type="text" class="list-input1" id="startDay" name="startDay"
													   placeholder="yyyy-MM-dd" style="width: 150px;" maxlength="10"/>
											</li>
											<li style="display: none;">
												<span class="sertitle">结束日期：</span>
												<input type="text" class="list-input1" id="endDay" name="endDay"
													   placeholder="yyyy-MM-dd" style="width: 150px;" maxlength="10"/>
											</li>
											<li>
												<span class="sertitle">版本：</span>
												<input class="list-input1" type="text" id="vst_version_name" name="vst_version_name" />
											</li>
										</ul>
									</div>
									<div style="text-align: center;">
										<input type="button" class="queryBtn" value="查询" onclick="javascript:button_getdata();">
									</div>
								</div>
							</div>
						</div>
					</div>
					</section>
					
					<!-- 表格数据 -->
					<section class="content">
				    <div class="row">
				        <div class="col-xs-12">
				            <div class="box">
					            <div class="box-header">
					              <%@ include file="../share/buttonList.jsp" %>
					            </div>
					            <div class="box-body">
					          		<div id="move_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
					          		<div id="move">
					          			<c:if test="${!query}">
											<table id="noquery" width="100%">
												<tr>
													<td align="center" height="80px;">
														请输入查询条件，点击查询记录
													</td>
												</tr>
											</table>
										</c:if>
					          		</div>
					              	<%@include file="../share/reportCutPage.jsp" %>
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