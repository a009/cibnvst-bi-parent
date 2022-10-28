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
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form action="${ctx}/homeClick/findHomeClick.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 运营统计 > 首页点击统计</span>
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
												<span class="sertitle">名称：</span>
												<input class="list-input1" type="text" id="vst_hc_name" name="vst_hc_name" />
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

<script type="text/javascript">
	$(document).ready(function(){
		//进入页面时，自动查询
		button_getdata();
		//文本框，按回车触发查询
		$("#vst_hc_name").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$.enlargePic();
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDay").val() == '' || $("#endDay").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}
	
	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();

		var startDays = $("#startDay").val().split("-");
		var startDay = startDays[0] + startDays[1] + startDays[2];
		var endDays = $("#endDay").val().split("-");
		var endDay = endDays[0] + endDays[1] + endDays[2];
		// 参数&符替换
		var vst_hc_name = $("#vst_hc_name").val().replace("&","<@_@>");
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&pkgName=" + $("#pkgName").val();
		// 参数特殊化处理
		params += "&" + paramFormat("vst_hc_name", vst_hc_name);

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=home_click_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_hc_date' id='vst_hc_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_hc_name' id='vst_hc_name2' order='desc' style='color:orange;'>名称</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_hc_vv' id='vst_hc_vv2' order='desc'>点击量</a> | <a onclick='changeOrder(this)' orderBy='vst_hc_vv_dod' id='vst_hc_vv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_hc_vv_wow' id='vst_hc_vv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_hc_uv' id='vst_hc_uv2' order='desc'>独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_hc_uv_dod' id='vst_hc_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_hc_uv_wow' id='vst_hc_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_hc_avg' id='vst_hc_avg2' order='desc'>人均点击量</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var hecVvDodColor = "green";
			var hecVvWowColor = "green";
			var hecUvDodColor = "green";
			var hecUvWowColor = "green";
			
			if(parseFloat(n.vst_hc_vv_dod)<0){
				hecVvDodColor = "red";
			}
			if(parseFloat(n.vst_hc_vv_wow)<0){
				hecVvWowColor = "red";
			}
			if(parseFloat(n.vst_hc_uv_dod)<0){
				hecUvDodColor = "red";
			}
			if(parseFloat(n.vst_hc_uv_wow)<0){
				hecUvWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td>"+getDateWeek(n.vst_hc_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_hc_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_hc_creator)
						+"<br>修改时间："+formatDate(n.vst_hc_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_hc_updator)
						+"<br>备注："+toString(n.vst_hc_summary)+"' class='enlargeTextTitle'>"
					+toString(n.vst_hc_name)+"</td>";
			//点击量
			table += "<td>"+formatNumber(n.vst_hc_vv)+" | ";
			table += "<span class='"+hecVvDodColor+"'>"+toString(n.vst_hc_vv_dod)+"</span> | ";
			table += "<span class='"+hecVvWowColor+"'>"+toString(n.vst_hc_vv_wow)+"</span></td>";
			//独立用户
			table += "<td>"+formatNumber(n.vst_hc_uv)+" | ";
			table += "<span class='"+hecUvDodColor+"'>"+toString(n.vst_hc_uv_dod)+"</span> | ";
			table += "<span class='"+hecUvWowColor+"'>"+toString(n.vst_hc_uv_wow)+"</span></td>";
			//人均点击量
			table += "<td>"+toString(n.vst_hc_avg)+"</td>";
			
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
			// 参数&符替换
			var vst_hc_name = $("#vst_hc_name").val().replace("&","<@_@>");
			
			var params = "startDay=" + startDay + "&endDay=" + endDay;
			params += "&pkgName=" + $("#pkgName").val();
			params += "&vst_hc_name=" + vst_hc_name;
			
			location.href = "${ctx}/homeClick/exportData.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
		}
	}
</script>
</html>