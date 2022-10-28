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
								<i class="fa fa-home"></i><span style="font-size:14px;">友盟数据 > 事件消息统计 > 导出</span>
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
				<form action="${ctx}/umengEventMessage/findUmengEventMessage.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 友盟数据 > 事件消息统计
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
												<span class="sertitle">事件名称：</span>
												<select class="list-input1" id="vst_uem_event_name" name="vst_uem_event_name">
													<option value="live_xiaowei_play_count">小薇直播的播放次数</option>
						  	 						<option value="live_xiaowei_category_play_count">小薇分类的播放次数</option>
												</select>
											</li>
											<li>
												<span class="sertitle">参数类型：</span>
												<select class="list-input1" id="vst_uem_param_type" name="vst_uem_param_type">
													<option value="">请选择</option>
													<option value="channelName">channelName</option>
													<option value="live_xiaowei_category_play_count">live_xiaowei_category_play_count</option>
												</select>
											</li>
											<li>
												<span class="sertitle">参数值：</span>
												<input class="list-input1" type="text" id="vst_uem_param_value" name="vst_uem_param_value" />
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
					
					<!-- 表格汇总数据 -->
					<section class="content">
				    <div class="row">
				        <div class="col-xs-12">
				            <div class="box">
					            <div class="box-header">
					                <ul class="contlist" style="cursor: pointer;">
										<li class="action">
											<img src="${ctx}/pub/listPages/images/t1_export.png" width="14" height="14">
											<a onclick="button_exportSum()">导出总量</a>
										</li>
									</ul>
					            </div>
					            <div class="box-body">
					          		<div id="moveSum_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
					          		<div id="moveSum">
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
					              	<%@include file="../share/reportCutPage2.jsp" %>
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
		//文本框，按回车触发查询
		$("#vst_uem_param_value").keydown(function(event){
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
			ajaxGetCutPage2();// 查询汇总数据
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
		params += "&vst_uem_event_name=" + $("#vst_uem_event_name").val();
		params += "&vst_uem_param_type=" + $("#vst_uem_param_type").val();
		// 参数特殊化处理
		params += "&" + paramFormat("vst_uem_param_value", $("#vst_uem_param_value").val());

		var ajaxUrl;
		if($("#vst_uem_param_value").val() == ''){
			ajaxUrl = "${ctx}/report/json?code=umeng_event_message_bar";
		}else{
			ajaxUrl = "${ctx}/report/json?code=umeng_event_message_line";
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
					if($("#vst_uem_param_value").val() == ''){
						bar(msg);
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

	function bar(msg){
		var data = msg.data;
		var top10data = data;
   		var xaxis = [];
		var series = [];
		if(data.length > 10){
			top10data = data.slice(0, 10);
		}
		$.each(top10data, function(i,n){
			xaxis[i] = getValue(n["value"]);
			series[i] = n.vst_uem_message_num;
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
		            name: '消息数量',
		            data: series
		        }
	        ]
	    });
	}

	function line(msg){
		var data = msg.data;
		var xaxis = [];
		var vst_uem_message_num = [];
		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_uem_message_num[i] = n.vst_uem_message_num;
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
				name : '消息数量',
				data : vst_uem_message_num,
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

	// 查询汇总数据
	function ajaxGetCutPage2(){
		$("#moveSum_loading").removeAttr("style");
		$("#moveSum").empty();

		var startDays = $("#startDay").val().split("-");
		var startDay = startDays[0] + startDays[1] + startDays[2];
		var endDays = $("#endDay").val().split("-");
		var endDay = endDays[0] + endDays[1] + endDays[2];
		
		var params = "orderBy=" + $("#orderBy2").val() + "&order=" + $("#order2").val();
		params += "&currPage=" + $("#currPage2").val() + "&singleCount=" + $("#singleCount2").val();
		params += "&startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&vst_uem_event_name=" + $("#vst_uem_event_name").val();
		params += "&vst_uem_param_type=" + $("#vst_uem_param_type").val();
		params += "&vst_uem_param_value=" + $("#vst_uem_param_value").val();
		
		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/umengEventMessage/ajaxGetCutPageSum.action",
			dataType : "json",
			success : function(msg){
				$("#moveSum_loading").attr("style","display:none");
				if(msg.result == "success"){
					showSumTable(msg);
				}else{
					$("#moveSum").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo2(0, $("#singleCount2").val());
				}
			},
			error : function(msg){
				$("#moveSum_loading").attr("style","display:none");
			}
		});
	}

	function showSumTable(msg){
		var data = msg.data;
		showPageInfo2(msg.totalSize, msg.singleSize);
		var orderBy = $("#orderBy2").val();
		var order = $("#order2").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'>#</th>";
		table += "<th width='30%'>参数值</th>";
		table += "<th width='30%'>消息数量</th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var id = ($("#currPage2").val()-1)*msg.singleSize+i+1;
			table += "<tr>";
			table += "<td>"+id+"</td>";
			table += "<td>"+n.vst_uem_param_value+"</td>";
			table += "<td>"+formatNumber(n.vst_uem_message_num)+"</td>";
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#moveSum").html(table);
		showOrder2(orderBy, order);
	}

	// 导出总量
	function button_exportSum(){
		var startDay = $("#startDay").val();
		var endDay = $("#endDay").val();
		
		if(!isNotEmpty(startDay) || !isNotEmpty(endDay) || getDateDiff(startDay, endDay, 'day') > 92){
			alert("亲，最多只能导出三个月的数据哦")
			return false;
		}else if(confirm("导出速度，由数据大小决定，请耐心等待。。")){
			var params = "startDay=" + startDay + "&endDay=" + endDay;
			params += "&vst_uem_event_name=" + $("#vst_uem_event_name").val();
			params += "&vst_uem_param_type=" + $("#vst_uem_param_type").val();
			params += "&vst_uem_param_value=" + $("#vst_uem_param_value").val();
			
			location.href = "${ctx}/umengEventMessage/exportDataSum.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
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
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&vst_uem_event_name=" + $("#vst_uem_event_name").val();
		params += "&vst_uem_param_type=" + $("#vst_uem_param_type").val();
		params += "&vst_uem_param_value=" + $("#vst_uem_param_value").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/umengEventMessage/ajaxGetCutPage.action",
			dataType : "json",
			success : function(msg){
				$("#move_loading").attr("style","display:none");
				if(msg.result == "success"){
					showTable(msg);
				}else{
					$("#move").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo(0, $("#singleCount").val());
				}
			},
			error : function(msg){
				$("#move_loading").attr("style","display:none");
			}
		});
	}
	
	function showTable(msg){
		var data = msg.data;
		showPageInfo(msg.totalSize, msg.singleSize);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uem_date' id='vst_uem_date2' order='desc'>日期</a></th>";
		table += "<th width='20%'><a onclick='changeOrder(this)' orderBy='vst_uem_param_value' id='vst_uem_param_value2' order='desc'>参数值</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_uem_radio' id='vst_uem_radio2' order='desc'>占比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_uem_message_num' id='vst_uem_message_num2' order='desc'>消息数量</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			table += "<td>"+getDateWeek(n.vst_uem_date+'', 'yyyyMMdd')+"</td>";
			table += "<td>"+toString(n.vst_uem_param_value)+"</td>";
			table += "<td>"+toString(n.vst_uem_radio)+"</td>";
			table += "<td>"+formatNumber(n.vst_uem_message_num)+"</td>";
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move").html(table);
		showOrder(orderBy, order);
	}

	// 抓取
	function button_grasp(){
		$("#listForm").attr("action", "${ctx}/umengEventMessage/toGrasp.action");
		$("#listForm").submit();
	}

	// 删除
	function button_delete(){
		if(confirm("是否删除查询出来的数据？")){
			var params = "startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
			params += "&vst_uem_event_name=" + $("#vst_uem_event_name").val();
			params += "&vst_uem_param_type=" + $("#vst_uem_param_type").val();
			params += "&vst_uem_param_value=" + $("#vst_uem_param_value").val();
			params += "&moduleId=" + $("#moduleId").val();
			
			$("#listForm").attr("action", "${ctx}/umengEventMessage/deleteEventMessage.action?"+params);
			$("#listForm").submit();
		}
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
					params += "&vst_uem_event_name=" + encodeURI(encodeURI($("#vst_uem_event_name").val()));
					params += "&vst_uem_param_type=" + $("#vst_uem_param_type").val();
					params += "&vst_uem_param_value=" + encodeURI(encodeURI($("#vst_uem_param_value").val()));
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/umengEventMessage/exportData.action?" + params
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