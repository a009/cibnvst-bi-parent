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
								<i class="fa fa-home"></i><span style="font-size:14px;">产品统计 > 活跃用户(渠道&设备)统计 > 导出</span>
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
				<form action="${ctx}/userActiveChannelBdmodel/findUserActiveChannelBdmodel.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 产品统计 > 活跃用户(渠道&设备)统计
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
												<input class="list-input1" type="text" id="vst_uacb_channel" name="vst_uacb_channel" />
											</li>
											<li>
												<span class="sertitle">设备名称：</span>
												<input class="list-input1" type="text" id="vst_uacb_bdmodel" name="vst_uacb_bdmodel" />
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
		$("#vst_uacb_channel").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_uacb_bdmodel").keydown(function(event){
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
			ajaxGetReport();// 统计图
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	// 统计图
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
		params += "&" + paramFormat("vst_uacb_channel", $("#vst_uacb_channel").val());
		params += "&" + paramFormat("vst_uacb_bdmodel", $("#vst_uacb_bdmodel").val());

		var ajaxUrl;
		if($("#vst_uacb_channel").val() == ''){
			ajaxUrl = "${ctx}/report/json?code=user_active_channel_bdmodel_barByChannel";
		}else if($("#vst_uacb_bdmodel").val() == ''){
			if(startDay == endDay){
				ajaxUrl = "${ctx}/report/json?code=user_active_channel_bdmodel_pieByBdmodel";
			}else{
				ajaxUrl = "${ctx}/userActiveChannelBdmodel/ajaxGetReportByBdmodel.action";
			}
		}else{
			ajaxUrl = "${ctx}/report/json?code=user_active_channel_bdmodel_line";
		}
		
		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : ajaxUrl,
			dataType : "json",
			success : function(msg){
				$("#loading_line").attr("style","display:none");
				if(msg.code == 100){
					if($("#vst_uacb_channel").val() == ''){
						barByChannel(msg);
					}else if($("#vst_uacb_bdmodel").val() == ''){
						if(startDay == endDay){
							pieByBdmodel(msg);
						}else{
							lineByBdmodel(msg);
						}
					}else{
						line(msg);
					}
				}else{
					$("#line-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#loading_line").attr("style","display:none");
			}
		});
	}

	function line(msg){
		var data = msg.data;
		var xaxis = [];
		var vst_uacb_amount = [];
		var vst_uacb_week = [];

		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_uacb_amount[i] = n.vst_uacb_amount;
			vst_uacb_week[i] = n.vst_uacb_week;
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
				name : '用户数量',
				data : vst_uacb_amount
			},
			{
				name : '周活跃',
				data : vst_uacb_week
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

	function barByChannel(msg){
		var data = msg.data;
		var top10data = data;
   		var xaxis = [];
		var series = [];
		if(data.length > 10){
			top10data = data.slice(0, 10);
		}
		$.each(top10data, function(i,n){
			xaxis[i] = n["value"];
			series[i] = n.vst_uacb_amount;
		});
		
		$("#line-chart").highcharts({
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
		            name: '活跃用户',
		            data: series
		        }
	        ]
	    });
	}

	function pieByBdmodel(msg){
   		var data = msg.data;
   		var top20data = data;
		if(data.length > 20){
			top20data = data.slice(0, 20);
		}
		
   		var series = "[";

   		$.each(top20data, function(i,n){
   			series +="{name:\""+n["value"]+"："+n.vst_uacb_amount+"\",y:"+n.vst_uacb_amount+"},";
   		});
   		
   		series += "]";
   		series = eval(series);
   		
   		$("#line-chart").highcharts({
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
                   	allowPointSelect: true,
                   	cursor: 'pointer',
                   	dataLabels: {
               		    enabled: true,
               		    color: '#000000',
               		    connectorColor: '#000000',
               		    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
               		},
                   	showInLegend: true
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

	function lineByBdmodel(msg){
		var data = msg.data;
		var dataLength  = Object.keys(data.dateMap).length;
		var bdmodels = data.bdmodels;	// Top10设备
		
		var xaxis = [];
		var amountArr = new Array();
		for(var i=0; i<bdmodels.length; i++){
			amountArr[i] = new Array();
		}
		
		// 遍历数据
		var index = 0;
		$.each(data.dateMap, function(date,amounts){
			if(dataLength <= 31){
				xaxis[index] = getDateWeek(date+'', 'yyyyMMdd');
				for(var i=0; i<bdmodels.length; i++){
					amountArr[i][index] = amounts[i];
				}
				index++;
			}else if(dataLength <= 92){
				var day = date % 100;
				if(day == 1 || day % 5 == 0){
					xaxis[index] = getDateWeek(date+'', 'yyyyMMdd');
					for(var i=0; i<bdmodels.length; i++){
						amountArr[i][index] = amounts[i];
					}
					index++;
				}
			}else{
				var day = date % 100;
				if(day == 1){
					xaxis[index] = getDateWeek(date+'', 'yyyyMMdd');
					for(var i=0; i<bdmodels.length; i++){
						amountArr[i][index] = amounts[i];
					}
					index++;
				}
			}
		});
		
		// 显示结果
		var series = [];
		for(var i=0; i<bdmodels.length; i++){
			var json = {name : bdmodels[i], data : amountArr[i]};
			series.push(json);
		}

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
			series : series
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
		params += "&" + paramFormat("vst_uacb_channel", $("#vst_uacb_channel").val());
		params += "&" + paramFormat("vst_uacb_bdmodel", $("#vst_uacb_bdmodel").val());

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_active_channel_bdmodel_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_date' id='vst_uacb_date2' order='desc'>日期</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_uacb_channel' id='vst_uacb_channel2' order='desc' style='color:orange;'>渠道</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_uacb_bdmodel' id='vst_uacb_bdmodel2' order='desc'>设备名称</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_amount' id='vst_uacb_amount2' order='desc'>用户数量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_rate' id='vst_uacb_rate2' order='desc'>用户占比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_dod' id='vst_uacb_dod2' order='desc'>天环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_wow' id='vst_uacb_wow2' order='desc'>周环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_mom' id='vst_uacb_mom2' order='desc'>月环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uacb_week' id='vst_uacb_week2' order='desc'>周活跃</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var uabDodColor = "green";
			var uabWowColor = "green";
			var uabMomColor = "green";
			
			if(parseFloat(n.vst_uacb_dod)<0){
				uabDodColor = "red";
			}
			if(parseFloat(n.vst_uacb_wow)<0){
				uabWowColor = "red";
			}
			if(parseFloat(n.vst_uacb_mom)<0){
				uabMomColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td>"+getDateWeek(n.vst_uacb_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_uacb_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_uacb_creator)
						+"<br>修改时间："+formatDate(n.vst_uacb_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_uacb_updator)
						+"<br>备注："+toString(n.vst_uacb_summary)+"' class='enlargeTextTitle'>"
					+toString(n.vst_uacb_channel)+"</td>";
			table += "<td>"+toString(n.vst_uacb_bdmodel)+"</td>";
			table += "<td>"+formatNumber(n.vst_uacb_amount)+"</td>";
			table += "<td>"+toString(n.vst_uacb_rate)+"</td>";
			table += "<td class='"+uabDodColor+"'>"+toString(n.vst_uacb_dod)+"</td>";
			table += "<td class='"+uabWowColor+"'>"+toString(n.vst_uacb_wow)+"</td>";
			table += "<td class='"+uabMomColor+"'>"+toString(n.vst_uacb_mom)+"</td>";
			table += "<td>"+formatNumber(n.vst_uacb_week)+"</td>";
			
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
					params += "&vst_uacb_channel=" + $("#vst_uacb_channel").val();
					params += "&vst_uacb_bdmodel=" + $("#vst_uacb_bdmodel").val();
					params += "&orderBy=" + $("#orderBy").val();
					params += "&order=" + $("#order").val();
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/userActiveChannelBdmodel/exportData.action?" + params
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