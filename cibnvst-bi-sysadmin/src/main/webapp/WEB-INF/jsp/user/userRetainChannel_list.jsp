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
<style>
	.export-table{
		width: 100%;
		max-width: 100%;
    	text-align: left;
	}
	.export-table>thead>tr>th {
    	vertical-align: bottom;
    	padding: 5px;
    	white-space: nowrap;
    	background: #e4eaec;
    	color: #58666e;
    	border-bottom: 1px solid #e4eaec;
    	border-left: 1px solid #e4eaec;
    	border-right: 1px solid #e4eaec;
	}
	.export-table>tbody>tr>td {
    	padding: 5px;
    	font-weight: 400;
    	font-size: 12px;
    	empty-cells: show;
    	border-top: 0;
    	border-right: 0;
    	border-bottom: 1px solid #e4eaec;
    	border-left: 1px solid #e4eaec;
    	border-right: 1px solid #e4eaec;
	}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			
			<!-- 导出弹窗 -->
			<div class="modal fade" id="export_myModal" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document" style="margin: 100px auto;">
					<div class="modal-content">
						<div class="modal-header">
							<div class="close" data-dismiss="modal" aria-label="Close" style="margin-top: 0; overflow: hidden;">
								<span aria-hidden="true" style="display: block; line-height: 20px; font-size: 40px;">&times;</span>
							</div>
							<h4 class="modal-title">
								<i class="fa fa-home"></i><span style="font-size:14px;">产品统计 > 留存用户(渠道)统计 > 导出</span>
							</h4>
						</div>
						<div class="modal-body" style="padding: 0 15px; max-height: 450px; overflow: auto;">
							<form id="exportBoxForm" method="post" enctype="multipart/form-data">
								<table class="export-table">
									<thead>
										<tr>
											<th width="20%">
												<input type="checkbox" id="exportBoxForm_selectAll" checked="checked" />全选
											</th>
											<th width="20%"></th>
											<th width="20%"></th>
											<th width="20%"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${columns}" var="column" varStatus="i">
											<c:if test="${i.index % 4 == 0}">
												<tr>
											</c:if>
											<td>
												<input class="exportBoxForm_selectItem" type="checkbox" name="export_column" value="${column.key } '${column.value }'" checked="checked" />${column.value }
											</td>
											<c:if test="${i.index % 4 == 3}">
												</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</form>
						</div>
						<div class="modal-footer" style="position: relative; bottom: 0; right: 0;">
							<input type="submit" class="btnSubmit" id="exportSubmit" value="导出" />
						</div>
					</div>
				</div>
			</div>
			<!-- 导出弹窗 -->
			
			<div class="content-roll">
				<form action="${ctx}/userRetainChannel/findUserRetainChannel.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 产品统计 > 留存用户(渠道)统计
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
											<li>
												<span class="sertitle">渠道：</span>
												<input class="list-input1" type="text" id="vst_urc_channel" name="vst_urc_channel" />
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
					
					<!-- 统计图 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <div class="box box-primary">
			                    <div class="box-body chart-responsive">
									<ul class="nav nav-pills" role="tablist">
										<li role="presentation" style="float: right;"><a href="#tab-pie" aria-controls="messages" role="tab" data-toggle="tab">饼状图</a></li>
										<li role="presentation" class="active" style="float: right;"><a href="#tab-bar" aria-controls="profile" role="tab" data-toggle="tab">柱状图</a></li>
										<li role="presentation" style="float: right;"><a href="#tab-line" aria-controls="home" role="tab" data-toggle="tab">折线图</a></li>
									</ul>
									<div class="tab-content" style="width: 100%;">
										<!-- LINE CHART -->
										<div role="tabpanel" class="tab-pane" id="tab-line" style="width: 100%;">
											<div id="loading_line" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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
										<!-- BAR CHART -->
										<div role="tabpanel" class="tab-pane active" id="tab-bar" style="width: 100%;">
											<div id="loading_bar" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
											<div class="chart" id="bar-chart" >
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
										<!-- PIE CHART -->
										<div role="tabpanel" class="tab-pane" id="tab-pie" style="width: 100%;">
											<div id="loading_pie" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
											<div class="chart" id="pie-chart" >
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
		//文本框，按回车触发查询
		$("#vst_urc_channel").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$.enlargePic();
		//切换tab重绘highcharts图
        $('ul.nav > li').click(function () {
            setTimeout(function () {
                $('#line-chart').highcharts().reflow();
                $('#bar-chart').highcharts().reflow();
                $('#pie-chart').highcharts().reflow();
            },50)
        });
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDay").val() == '' || $("#endDay").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
			ajaxGetReport();// 按日期统计(折线图)
			ajaxGetReportByDim();// 按维度统计
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
		// 参数特殊化处理
		params += "&" + paramFormat("vst_urc_channel", $("#vst_urc_channel").val());
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_retain_channel_line",
			dataType:"json",
			success:function(msg){
				$("#loading_line").attr("style","display:none");
				if(msg.code == 100){
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
		var vst_urc_amount = [];
		var vst_urc_week_amount = [];
		var vst_urc_month_amount = [];
		var vst_urc_season_amount = [];

		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_urc_amount[i] = n.vst_urc_amount;
			vst_urc_week_amount[i] = n.vst_urc_week_amount;
			vst_urc_month_amount[i] = n.vst_urc_month_amount;
			vst_urc_season_amount[i] = n.vst_urc_season_amount;
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
				name : '次日留存用户',
				data : vst_urc_amount,
			},
			{
				name : '周留存用户',
				data : vst_urc_week_amount,
				visible : false
			},
			{
				name : '月留存用户',
				data : vst_urc_month_amount,
				visible : false
			},
			{
				name : '季留存用户',
				data : vst_urc_season_amount,
				visible : false
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

	// 按维度统计
	function ajaxGetReportByDim(){
		$("#loading_bar").removeAttr("style");
		$("#bar-chart").empty();
		$("#loading_pie").removeAttr("style");
		$("#pie-chart").empty();

		var startDays = $("#startDay").val().split("-");
		var startDay = startDays[0] + startDays[1] + startDays[2];
		var endDays = $("#endDay").val().split("-");
		var endDay = endDays[0] + endDays[1] + endDays[2];
		
		var params = "startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&pkgName=" + $("#pkgName").val();
		// 参数特殊化处理
		params += "&" + paramFormat("vst_urc_channel", $("#vst_urc_channel").val());
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_retain_channel_dim",
			dataType:"json",
			success:function(msg){
				$("#loading_bar").attr("style","display:none");
				$("#loading_pie").attr("style","display:none");
				if(msg.code == 100){
					bar(msg);
					pie(msg);
				}else{
					$("#bar-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#pie-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error:function(msg){
				$("#loading_bar").attr("style","display:none");
				$("#loading_pie").attr("style","display:none");
			}
		});
	}

	function bar(msg){
		var data = msg.data;
		var top10data = data;
   		var xaxis = [];
		var series = [];
		if(data.length > 10){
			top10data = data.slice(0, 10);
		}
		$.each(top10data,function(i,n){
			xaxis[i] = getValue(n["value"]);
			series[i] = n.vst_urc_amount;
		});
		
		$("#bar-chart").highcharts({
			chart: {
	            type: 'bar',
	            height: 360
	        },
	        title: {
	            text: false
	        },
	        subtitle: {
	            text: false
	        },
	        xAxis: {
	            categories: xaxis
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: false,
	                rotation: 0,
	            },
	            labels: {
	                overflow: 'justify'
	            }
	        },
	        plotOptions: {
	            bar: {
	                dataLabels: {
	                    enabled: true,
	                    allowOverlap: true
	                }
	            }
	        },
	        legend: {
	            align: 'center',
	            verticalAlign: 'top',
	            labelFormat: '{name}Top10'
	        },
	        credits: {
	            enabled: false
	        },
	        series: [
		     	{
		            name: '次日留存用户总量',
		            data: series
		        }
	        ]
	    });
	}
	
	function pie(msg){
   		var data = msg.data;
   		var top20data = data;
		if(data.length > 20){
			top20data = data.slice(0, 20);
		}
		
   		var series = "[";

   		$.each(top20data,function(i,n){
   			series +="{name:\""+getValue(n["value"])+"："+n.vst_urc_amount+"\",y:"+n.vst_urc_amount+"},";
   		});
   		
   		series += "]";
   		series = eval(series);
   		
   		$("#pie-chart").highcharts({
       		chart: {
               	plotBackgroundColor: null,
               	plotBorderWidth: null,
               	plotShadow: false,
               	height: 360
           	},
           	legend: {
           		align: 'right',
                   verticalAlign: 'top',
                   layout: 'vertical',
                   x: -50,
                   y: 0
	           
	        },
           	title: {
               	text: false
           	},
           	tooltip: {
       	    	pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
           	},
           	plotOptions: {
               	pie: {
                   	allowPointSelect: true/*,
                   	cursor: 'pointer',
                   	dataLabels: {
               		    enabled: true,
               		    color: '#000000',
               		    connectorColor: '#000000',
               		    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
               		},
                   	showInLegend: true*/
               	}
           	},
           	credits : {
           		enabled: false
			},
       		series: [{
           		type: 'pie',
           		name: '比重',
           		data: series
       		}]
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
		// 参数特殊化处理
		params += "&" + paramFormat("vst_urc_channel", $("#vst_urc_channel").val());

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_retain_channel_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_urc_date' id='vst_urc_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_urc_channel' id='vst_urc_channel2' order='desc' style='color:orange;'>渠道</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_urc_amount' id='vst_urc_amount2' order='desc'>次日留存</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_dod' id='vst_urc_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_wow' id='vst_urc_wow2' order='desc'>周环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_rate' id='vst_urc_rate2' order='desc'>留存率</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_urc_week_amount' id='vst_urc_week_amount2' order='desc'>周留存</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_week_dod' id='vst_urc_week_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_week_wow' id='vst_urc_week_wow2' order='desc'>周环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_week_rate' id='vst_urc_week_rate2' order='desc'>留存率</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_urc_month_amount' id='vst_urc_month_amount2' order='desc'>月留存</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_month_dod' id='vst_urc_month_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_month_wow' id='vst_urc_month_wow2' order='desc'>周环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_month_rate' id='vst_urc_month_rate2' order='desc'>留存率</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_urc_season_amount' id='vst_urc_season_amount2' order='desc'>季留存</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_season_dod' id='vst_urc_season_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_season_wow' id='vst_urc_season_wow2' order='desc'>周环比</a> | <a onclick='changeOrder(this)' orderBy='vst_urc_season_rate' id='vst_urc_season_rate2' order='desc'>留存率</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var urDodColor = "green";
			var urWowColor = "green";
			var urWeekDodColor = "green";
			var urWeekWowColor = "green";
			var urMonthDodColor = "green";
			var urMonthWowColor = "green";
			var urSeasonDodColor = "green";
			var urSeasonWowColor = "green";
			
			if(parseFloat(n.vst_urc_dod)<0){
				urDodColor = "red";
			}
			if(parseFloat(n.vst_urc_wow)<0){
				urWowColor = "red";
			}
			if(parseFloat(n.vst_urc_week_dod)<0){
				urWeekDodColor = "red";
			}
			if(parseFloat(n.vst_urc_week_wow)<0){
				urWeekWowColor = "red";
			}
			if(parseFloat(n.vst_urc_month_dod)<0){
				urMonthDodColor = "red";
			}
			if(parseFloat(n.vst_urc_month_wow)<0){
				urMonthWowColor = "red";
			}
			if(parseFloat(n.vst_urc_season_dod)<0){
				urSeasonDodColor = "red";
			}
			if(parseFloat(n.vst_urc_season_wow)<0){
				urSeasonWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			table += "<td>"+getDateWeek(n.vst_urc_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_urc_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_urc_creator)
						+"<br>修改时间："+formatDate(n.vst_urc_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_urc_updator)
						+"<br>备注："+toString(n.vst_urc_summary)+"' class='enlargeTextTitle'>"
					+toString(n.vst_urc_channel)+"</td>";
			//次日留存
			table += "<td>"+formatNumber(n.vst_urc_amount)+" | ";
			table += "<span class='"+urDodColor+"'>"+toString(n.vst_urc_dod)+"</span> | ";
			table += "<span class='"+urWowColor+"'>"+toString(n.vst_urc_wow)+"</span> | ";
			table += toString(n.vst_urc_rate)+"</td>";
			//周留存
			table += "<td>"+formatNumber(n.vst_urc_week_amount)+" | ";
			table += "<span class='"+urWeekDodColor+"'>"+toString(n.vst_urc_week_dod)+"</span> | ";
			table += "<span class='"+urWeekWowColor+"'>"+toString(n.vst_urc_week_wow)+"</span> | ";
			table += toString(n.vst_urc_week_rate)+"</td>";
			//月留存
			table += "<td>"+formatNumber(n.vst_urc_month_amount)+" | ";
			table += "<span class='"+urMonthDodColor+"'>"+toString(n.vst_urc_month_dod)+"</span> | ";
			table += "<span class='"+urMonthWowColor+"'>"+toString(n.vst_urc_month_wow)+"</span> | ";
			table += toString(n.vst_urc_month_rate)+"</td>";
			//季留存
			table += "<td>"+formatNumber(n.vst_urc_season_amount)+" | ";
			table += "<span class='"+urSeasonDodColor+"'>"+toString(n.vst_urc_season_dod)+"</span> | ";
			table += "<span class='"+urSeasonWowColor+"'>"+toString(n.vst_urc_season_wow)+"</span> | ";
			table += toString(n.vst_urc_season_rate)+"</td>";
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move").html(table);
		showOrder(orderBy, order);
	}

	/****************************导出******************************/
	// 导出数据
	function button_export(){
		var startDay = $("#startDay").val();
		var endDay = $("#endDay").val();
		
		if(!isNotEmpty(startDay) || !isNotEmpty(endDay) || getDateDiff(startDay, endDay, 'day') > 92){
			alert("亲，最多只能导出三个月的数据哦")
			return false;
		}else{
			// 打开弹窗
			$("#export_myModal").attr("class","modal fade in");
			$("#export_myModal").attr("aria-hidden","false");
			$("#export_myModal").show();
			$("#exportBoxForm")[0].reset();//重置
			$("#exportSubmit").removeAttr("disabled");//解除提交按钮禁用状态
		}
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"1",
	  		formid:"exportBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#export_myModal").hide();
		});
		
		// 导出的全选功能
		$("#exportBoxForm #exportBoxForm_selectAll").change(function(){
			$("#exportBoxForm .exportBoxForm_selectItem").prop("checked",$(this).prop("checked"));
		});
		$("#exportSubmit").click(function(){
			var flag = $.formValidator.pageIsValid("1");// 触发全局校验
			if(flag){
				var export_column = "";
				$("input[name='export_column']").each(function(){
					if($(this).is(':checked')){
						export_column += $(this).val() + ",";
					}
				});
				if(export_column == null || export_column == ''){
					alert("您未勾选要导出的字段！");
					$("#exportSubmit").removeAttr("disabled");
					return false;
				}else{
					export_column = export_column.substring(0,export_column.length-1);
				}
				if(confirm("导出速度，由数据大小决定，请耐心等待。。")){
					var startDay = $("#startDay").val();
					var endDay = $("#endDay").val();
					
					var params = "startDay=" + startDay + "&endDay=" + endDay;
					params += "&pkgName=" + $("#pkgName").val();
					params += "&vst_urc_channel=" + $("#vst_urc_channel").val();
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/userRetainChannel/exportData.action?" + params
							+ "&moduleId=" + $("#moduleId").val();
				}
			}else{
				return false;
			}
			$("#export_myModal .modal-header .close span").click();
		});
	});
	
	// 提交处理
	function doSubmit() {
		//提交后禁用提交按钮，避免多次提交
		$("#exportSubmit").attr("disabled", "disabled");
		return true;
	}
	/****************************导出******************************/
</script>
</html>