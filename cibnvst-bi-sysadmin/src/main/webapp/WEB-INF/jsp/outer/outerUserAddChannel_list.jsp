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
				<form action="${ctx}/outerUserAddChannel/findOuterUserAddChannel.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 市场数据 > 新增渠道用户(对外)统计
							<i name="showTitle" class="fa fa-question-circle  enlargeTextTitle" data-title="" style="height: 30px;line-height: 30px;font-size: 16px;"></i>
						</span>
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
					
					<!-- 折线图 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <!-- LINE CHART -->
			                <div class="box box-primary">
			                    <div class="box-header with-border">
			                        <h3 class="box-title"></h3>
			                        <div class="box-tools pull-right">
			                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
			                            </button>
			                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
			                        </div>
			                    </div>
			                    <div class="box-body chart-responsive">
			                        <div class="chart" id="line-chart" >
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
			                    </div>
			                    <div id="loading_line" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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
					            	<ul class="contlist" style="cursor: pointer;">
										<c:forEach items="${buttonList}" var="button">
											<li class="action">
												<img src="${ctx}${button.vst_button_img}" width="14" height="14" alt="${button.vst_button_summary }">
												<a onclick="${button.vst_button_onclick}">${button.vst_button_name }</a>
											</li>
										</c:forEach>
										<i name="showTitle" class="fa fa-question-circle  enlargeTextTitle" data-title="" style="height: 30px;line-height: 30px;font-size: 16px;"></i>
									</ul>
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
		$.initDaterangepicker('过去7天');
		// 表描述赋值
		var tableDesc = JSON.parse('${tableDesc}');
		if(tableDesc != null && tableDesc != ''){
			var showTitle = "";
			for(var i in tableDesc){
				showTitle += "<font style='font-size:14; color:#57B1ED;'>" + tableDesc[i].title
						+ "</font> : <font style='font-size:12; color:#E6E6E6;'>" + tableDesc[i].desc + "</font><br/>";
			}
			$("[name='showTitle']").each(function(){
				$(this).attr("data-title", showTitle);
			});
		}else{
			$("[name='showTitle']").each(function(){
				$(this).css("display", "none");
			});
		}
		//进入页面时，自动查询
		button_getdata();
		// 切换包名，调用查询
        $(".pkgName-menu>li").click(function () {
        	//button_getdata();
        	$("#recordPkg").val($("#pkgName").val());
        	$("#listForm").submit();
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
			ajaxGetReport();// 按日期统计(折线图)
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	// 按日期统计(折线图)
	function ajaxGetReport(){
		$("#loading_line").removeAttr("style");
		$("#line-chart").empty();

		var startDays = $("#startDay").val().split("-");
		var startDay = startDays[0] + startDays[1] + startDays[2];
		var endDays = $("#endDay").val().split("-");
		var endDay = endDays[0] + endDays[1] + endDays[2];
		
		var params = "startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&pkgName=" + $("#pkgName").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/outerUserAddChannel/ajaxGetReportByDate.action",
			dataType:"json",
			success:function(msg){
				$("#loading_line").attr("style","display:none");
				if(msg.result == "success"){
					line(msg);
				}else{
					$("#line-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error:function(msg){
				$("#loading_line").attr("style","display:none");
			}
		});
	}

	function line(msg){
		var data = msg.data;
		var xaxis = [];
		var vst_uac_amount = [];

		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_uac_amount[i] = n.vst_uac_amount;
		});
		
		$("#line-chart").highcharts({
			title:{
				text: false
			},
			chart: {
		            height: 235
		    },
			loading: {
	            hideDuration: 1000,
	            showDuration: 1000
	        },
	        legend: {
	            align: 'center',
	            verticalAlign: 'top',
	        },
	        xAxis : {
				categories : xaxis,
				labels: {
			         step: parseInt(data.length / 8),
			         staggerLines: 1,
			         useHTML: true
			    }
			},
			yAxis : {
				title:false,
				tickAmount: 5,
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			credits : {
				enabled: false
			},
			tooltip: {
				headerFormat: '&nbsp;&nbsp;&nbsp;时间：{point.key}<br/>',
				shared: true,
				useHTML: true
			},
			series : [
			{
				name : '新增用户数',
				data : vst_uac_amount,
			}
			],
			plotOptions: {
	        	series: {
					marker: {
						enabled: false
					}
		    	}
			}
		});
	}

	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();

		var startDays = $("#startDay").val().split("-");
		var startDay = startDays[0] + startDays[1] + startDays[2];
		var endDays = $("#endDay").val().split("-");
		var endDay = endDays[0] + endDays[1] + endDays[2];
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&pkgName=" + $("#pkgName").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/outerUserAddChannel/ajaxGetCutPage.action",
			dataType:"json",
			success:function(msg){
				$("#move_loading").attr("style","display:none");
				if(msg.result == "success"){
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
		var totalCount = msg.totalSize;
		var singleCount = msg.singleSize;
		$("#currPage").val(msg.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_date' id='vst_uac_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_channel' id='vst_uac_channel2' order='desc'>渠道</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_uac_amount' id='vst_uac_amount2' order='desc'>新增用户</a></th>";

		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;

			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td>"+getDateWeek(n.vst_uac_date+'', 'yyyyMMdd')+"</td>";
			table += "<td>"+toString(n.vst_uac_channel)+"</td>";
			table += "<td>"+formatNumber(n.vst_uac_amount)+"</td>";

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
			
			location.href = "${ctx}/outerUserAddChannel/exportData.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
		}
	}
</script>
</html>