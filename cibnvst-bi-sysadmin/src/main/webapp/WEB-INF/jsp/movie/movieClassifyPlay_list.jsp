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
								<i class="fa fa-home"></i><span style="font-size:14px;">运营统计 > 分类播放统计 > 导出</span>
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
				<form action="${ctx}/movieClassifyPlay/findMovieClassifyPlay.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 运营统计 > 分类播放统计
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
												<span class="sertitle">专区类型：</span>
												<select class="list-input1" id="vst_mcp_special_type" name="vst_mcp_special_type">
													<c:forEach items="${specialTypes}" var="specialType">
														<option value="${specialType.key }">${specialType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">影片分类：</span>
												<select class="list-input1" id="vst_mcp_cid" name="vst_mcp_cid">
													<option value="">请选择</option>
													<c:forEach items="${classifys}" var="classify">
														<option value="${classify.key }">${classify.value }</option>
													</c:forEach>
												</select>
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
		$.enlargePic();
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDay").val() == '' || $("#endDay").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
			ajaxGetReport()// 统计图
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
		params += "&vst_mcp_special_type=" + $("#vst_mcp_special_type").val();
		params += "&vst_mcp_cid=" + $("#vst_mcp_cid").val();

		var ajaxUrl;
		if($("#vst_mcp_cid").val() == ''){
			if(startDay == endDay){
				ajaxUrl = "${ctx}/report/json?code=movie_classify_play_pie";
			}else{
				ajaxUrl = "${ctx}/movieClassifyPlay/ajaxGetReportByCid.action";
			}
		}else{
			ajaxUrl = "${ctx}/report/json?code=movie_classify_play_line";
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
					if($("#vst_mcp_cid").val() == ''){
						if(startDay == endDay){
							pie(msg);
						}else{
							lineByCid(msg);
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
		var vst_mcp_vv = [];
		var vst_mcp_uv = [];
		var vst_mcp_playtime = [];

		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_mcp_vv[i] = n.vst_mcp_vv;
			vst_mcp_uv[i] = n.vst_mcp_uv;
			vst_mcp_playtime[i] = toHour(n.vst_mcp_playtime*1000);
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
				name : '总播放量',
				data : vst_mcp_vv,
			},
			{
				name : '独立用户',
				data : vst_mcp_uv,
			},
			{
				name : '总播放时长(小时)',
				data : vst_mcp_playtime,
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

	function pie(msg){
   		var data = msg.data;
   		var top20data = data;
		if(data.length > 20){
			top20data = data.slice(0, 20);
		}
		
   		var series = "[";

   		$.each(top20data, function(i,n){
   			series +="{name:\""+getMapValue('${classifys}', n["value"])+"："+n.vst_mcp_vv+"\",y:"+n.vst_mcp_vv+"},";
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

	function lineByCid(msg){
		var data = msg.data;
		var dataLength  = Object.keys(data.dateMap).length;
		var classifys = data.classifys;	// Top10分类
		
		var xaxis = [];
		var vvArr = new Array();
		for(var i=0; i<classifys.length; i++){
			vvArr[i] = new Array();
		}

		// 遍历数据
		var index = 0;
		$.each(data.dateMap, function(date,vvs){
			if(dataLength <= 31){
				xaxis[index] = getDateWeek(date+'', 'yyyyMMdd');
				for(var i=0; i<classifys.length; i++){
					vvArr[i][index] = vvs[i];
				}
				index++;
			}else if(dataLength <= 92){
				var day = date % 100;
				if(day == 1 || day % 5 == 0){
					xaxis[index] = getDateWeek(date+'', 'yyyyMMdd');
					for(var i=0; i<classifys.length; i++){
						vvArr[i][index] = vvs[i];
					}
					index++;
				}
			}else{
				var day = date % 100;
				if(day == 1){
					xaxis[index] = getDateWeek(date+'', 'yyyyMMdd');
					for(var i=0; i<classifys.length; i++){
						vvArr[i][index] = vvs[i];
					}
					index++;
				}
			}
		});
		
		// 显示结果
		var series = [];
		for(var i=0; i<classifys.length; i++){
			var cname = getMapValue('${classifys}', classifys[i]);
			var json = {name : cname, data : vvArr[i]};
			series.push(json);
		}

		$("#line-chart").highcharts({
			title:{
				text: false
			},
			chart: {
				type: 'area',
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
			/*tooltip: {
				headerFormat: '&nbsp;&nbsp;&nbsp;时间：{point.key}<br/>',
				shared: true,
				useHTML: true
			},*/
			tooltip: {
	            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.percentage:.1f}%</b> ({point.y:,.0f})<br/>',
	            shared: true
	        },
	        plotOptions: {
	            area: {
	                stacking: 'percent',
	                lineColor: '#ffffff',
	                lineWidth: 1,
	                marker: {
	                    lineWidth: 1,
	                    lineColor: '#ffffff'
	                }
	            }
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
		params += "&vst_mcp_special_type=" + $("#vst_mcp_special_type").val();
		params += "&vst_mcp_cid=" + $("#vst_mcp_cid").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_classify_play_table",
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
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_mcp_date' id='vst_mcp_date2' order='desc'>日期</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_mcp_special_type' id='vst_mcp_special_type2' order='desc'>专区类型</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_mcp_cid' id='vst_mcp_cid2' order='desc' style='color:orange'>影片分类</a></th>";
		
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_mcp_vv' id='vst_mcp_vv2' order='desc'>播放量</a> | <a onclick='changeOrder(this)' orderBy='vst_mcp_vv_dod' id='vst_mcp_vv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_mcp_vv_wow' id='vst_mcp_vv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_mcp_uv' id='vst_mcp_uv2' order='desc'>独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_mcp_uv_dod' id='vst_mcp_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_mcp_uv_wow' id='vst_mcp_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_mcp_avg' id='vst_mcp_avg2' order='desc'>人均播放量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_mcp_playtime' id='vst_mcp_playtime2' order='desc'>播放时长(小时)</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_mcp_playtime_avg' id='vst_mcp_playtime_avg2' order='desc'>人均播放时长(分钟)</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var vvDodColor = "green";
			var vvWowColor = "green";
			var uvDodColor = "green";
			var uvWowColor = "green";
			
			if(parseFloat(n.vst_mcp_vv_dod)<0){
				vvDodColor = "red";
			}
			if(parseFloat(n.vst_mcp_vv_wow)<0){
				vvWowColor = "red";
			}
			if(parseFloat(n.vst_mcp_uv_dod)<0){
				uvDodColor = "red";
			}
			if(parseFloat(n.vst_mcp_uv_wow)<0){
				uvWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td>"+getDateWeek(n.vst_mcp_date+'', 'yyyyMMdd')+"</td>";
			table += "<td>"+getMapValue('${specialTypes}', n.vst_mcp_special_type)+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_mcp_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_mcp_creator)
						+"<br>修改时间："+formatDate(n.vst_mcp_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_mcp_updator)
						+"<br>备注："+toString(n.vst_mcp_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${classifys}', n.vst_mcp_cid)+"</td>";
			//播放量
			table += "<td>"+formatNumber(n.vst_mcp_vv)+" | ";
			table += "<span class='"+vvDodColor+"'>"+toString(n.vst_mcp_vv_dod)+"</span> | ";
			table += "<span class='"+vvWowColor+"'>"+toString(n.vst_mcp_vv_wow)+"</span></td>";
			//独立用户
			table += "<td>"+formatNumber(n.vst_mcp_uv)+" | ";
			table += "<span class='"+uvDodColor+"'>"+toString(n.vst_mcp_uv_dod)+"</span> | ";
			table += "<span class='"+uvWowColor+"'>"+toString(n.vst_mcp_uv_wow)+"</span></td>";
			//人均播放量
			table += "<td>"+formatNumber(n.vst_mcp_avg)+"</td>";
			//播放时长
			table += "<td>"+toHour(n.vst_mcp_playtime*1000)+"</td>";
			table += "<td>"+toMinute(n.vst_mcp_playtime_avg*1000)+"</td>";
			
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
					params += "&vst_mcp_special_type=" + $("#vst_mcp_special_type").val();
					params += "&vst_mcp_cid=" + $("#vst_mcp_cid").val();
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/movieClassifyPlay/exportData.action?" + params
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

	// 导出总量
	function button_exportSum(){
		var startDay = $("#startDay").val();
		var endDay = $("#endDay").val();

		if(!isNotEmpty(startDay) || !isNotEmpty(endDay) || getDateDiff(startDay, endDay, 'day') > 92){
			alert("亲，最多只能导出三个月的数据哦")
			return false;
		}

		var dataType = prompt("请选择你要导出的数据类型       1：播放量、2：观看人数、3：播放时长(H)、4：人均播放量");
		if(dataType == 1 || dataType == 2 || dataType == 3 || dataType == 4){
			if(confirm("导出速度，由数据大小决定，请耐心等待。。")){
				var params = "startDay=" + startDay + "&endDay=" + endDay;
				params += "&dataType=" + dataType;
				
				location.href = "${ctx}/movieClassifyPlay/exportSumData.action?" + params
						+ "&moduleId=" + $("#moduleId").val();
			}
		}else{
			alert("输入错误！");
		}
	}
</script>
</html>