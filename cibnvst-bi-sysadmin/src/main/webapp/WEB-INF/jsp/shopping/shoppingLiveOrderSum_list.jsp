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
		$("#vst_shopping_live_order_goods_number").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_live_order_goods_name").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_live_order_province").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_live_order_address").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_live_order_recipient").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_live_order_phone").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_live_order_operator").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDate").val() == '' || $("#endDate").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
			ajaxGetData();// 统计占比(根据商品分类)
			ajaxGetData2();// 统计总量(根据商品来源)
		    ajaxGetCutPage();// 统计总量(根据省份)
			ajaxGetCutPage2();// 月统计总量
		}
	}

	// 统计占比(根据商品分类)
	function ajaxGetData(){
		$("#category_loading").removeAttr("style");
		$("#category_container").empty();

		var params = "startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
		params += "&vst_shopping_live_order_channel=" + $("#vst_shopping_live_order_channel").val();
		params += "&vst_shopping_live_order_number=" + $("#vst_shopping_live_order_number").val();
		params += "&vst_shopping_live_order_goods_number=" + $("#vst_shopping_live_order_goods_number").val();
		params += "&vst_shopping_live_order_goods_category=" + $("#vst_shopping_live_order_goods_category").val();
		params += "&vst_shopping_live_order_goods_name=" + $("#vst_shopping_live_order_goods_name").val();
		params += "&vst_shopping_live_order_province=" + $("#vst_shopping_live_order_province").val();
		params += "&vst_shopping_live_order_address=" + $("#vst_shopping_live_order_address").val();
		params += "&vst_shopping_live_order_recipient=" + $("#vst_shopping_live_order_recipient").val();
		params += "&vst_shopping_live_order_phone=" + $("#vst_shopping_live_order_phone").val();
		params += "&vst_shopping_live_order_balance_status=" + $("#vst_shopping_live_order_balance_status").val();
		params += "&vst_shopping_live_order_logistics_status=" + $("#vst_shopping_live_order_logistics_status").val();
		params += "&vst_shopping_live_order_operator=" + $("#vst_shopping_live_order_operator").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/shoppingLiveOrder/ajaxGetReportDataByCategory.action",
			dataType : "json",
			success : function(msg){
				$("#category_loading").attr("style","display:none");
				if(msg.result == "success"){
					pie(msg);
				}else{
					$("#category_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#category_loading").attr("style","display:none");
			}
		});
	}

	function pie(msg){
   		var data = msg.data;
   		var series = "[";

   		$.each(data,function(i,n){
   			series +="{name:\""+getValue(n["category"])+"："+n.qty+"\",y:"+n.qty+"},";
   		});
   		
   		series+="]";
   		series = eval(series);
   		
   		$("#category_container").highcharts({
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
       	    	pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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

	// 统计总量(根据来源)
	function ajaxGetData2(){
		$("#source_loading").removeAttr("style");
		$("#source_container").empty();

		var params = "startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
		params += "&vst_shopping_live_order_channel=" + $("#vst_shopping_live_order_channel").val();
		params += "&vst_shopping_live_order_number=" + $("#vst_shopping_live_order_number").val();
		params += "&vst_shopping_live_order_goods_number=" + $("#vst_shopping_live_order_goods_number").val();
		params += "&vst_shopping_live_order_goods_category=" + $("#vst_shopping_live_order_goods_category").val();
		params += "&vst_shopping_live_order_goods_name=" + $("#vst_shopping_live_order_goods_name").val();
		params += "&vst_shopping_live_order_province=" + $("#vst_shopping_live_order_province").val();
		params += "&vst_shopping_live_order_address=" + $("#vst_shopping_live_order_address").val();
		params += "&vst_shopping_live_order_recipient=" + $("#vst_shopping_live_order_recipient").val();
		params += "&vst_shopping_live_order_phone=" + $("#vst_shopping_live_order_phone").val();
		params += "&vst_shopping_live_order_balance_status=" + $("#vst_shopping_live_order_balance_status").val();
		params += "&vst_shopping_live_order_logistics_status=" + $("#vst_shopping_live_order_logistics_status").val();
		params += "&vst_shopping_live_order_operator=" + $("#vst_shopping_live_order_operator").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/shoppingLiveOrder/ajaxGetReportDataBySource.action",
			dataType : "json",
			success : function(msg){
				$("#source_loading").attr("style","display:none");
				if(msg.result == "success"){
					barBySource(msg);
				}else{
					$("#source_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#source_loading").attr("style","display:none");
			}
		});
	}

	function barBySource(msg){
		var data = msg.data;
   		var xaxis = [];
		var qtys = [];
		var salePrices = [];
		$.each(data,function(i,n){
			xaxis[i] = getValue(n["source"]);
			qtys[i] = n.qty;
			salePrices[i] = n.salePrice;
		});
		
		$("#source_container").highcharts({
			chart: {
	            type: 'column',
	            height: 235
	        },
			title: {
	            text: false,
	        },
	        subtitle: {
	            text: false,
	        },
			xAxis:{
				categories: xaxis
			},
	        yAxis: [{ // Primary yAxis
	            labels: {
	                format: '{value} 元',
	                style: {
	                    color: Highcharts.getOptions().colors[1]
	                }
	            },
	            title: {
	                text: '销售金额',
	                style: {
	                    color: Highcharts.getOptions().colors[1]
	                }
	            }
	        }, { // Secondary yAxis
	            title: {
	                text: '购买数量',
	                style: {
	                    color: Highcharts.getOptions().colors[0]
	                }
	            },
	            labels: {
	                format: '{value}',
	                style: {
	                    color: Highcharts.getOptions().colors[0]
	                }
	            },
	            opposite: true
	        }],
	        tooltip: {
	            shared: true
	        },
	        legend: {
	            align: 'center',
	            verticalAlign: 'top',
	            labelFormat: '{name}'
	        },
	        credits: {
	        	enabled: false
		    },
	        series: [{
	            name: '购买数量',
	            type: 'column',
	            yAxis: 1,
	            data: qtys,
	            tooltip: {
	                valueSuffix: ''
	            }
	        }, {
	            name: '销售金额',
	            type: 'spline',
	            data: salePrices,
	            tooltip: {
	                valueSuffix: ' 元'
	            }
	        }]
	    });
	}
	
	// 月统计总量
	function ajaxGetCutPage2(){
		$("#month_loading").removeAttr("style");
		$("#month_container").empty();
		
		var params = "startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
		params += "&vst_shopping_live_order_channel=" + $("#vst_shopping_live_order_channel").val();
		params += "&vst_shopping_live_order_number=" + $("#vst_shopping_live_order_number").val();
		params += "&vst_shopping_live_order_goods_number=" + $("#vst_shopping_live_order_goods_number").val();
		params += "&vst_shopping_live_order_goods_category=" + $("#vst_shopping_live_order_goods_category").val();
		params += "&vst_shopping_live_order_goods_name=" + $("#vst_shopping_live_order_goods_name").val();
		params += "&vst_shopping_live_order_province=" + $("#vst_shopping_live_order_province").val();
		params += "&vst_shopping_live_order_address=" + $("#vst_shopping_live_order_address").val();
		params += "&vst_shopping_live_order_recipient=" + $("#vst_shopping_live_order_recipient").val();
		params += "&vst_shopping_live_order_phone=" + $("#vst_shopping_live_order_phone").val();
		params += "&vst_shopping_live_order_balance_status=" + $("#vst_shopping_live_order_balance_status").val();
		params += "&vst_shopping_live_order_logistics_status=" + $("#vst_shopping_live_order_logistics_status").val();
		params += "&vst_shopping_live_order_operator=" + $("#vst_shopping_live_order_operator").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,	
			url:"${ctx}/shoppingLiveOrder/ajaxGetSumCutPage.action",
			dataType:"json",
			success:function(msg){
				$("#month_loading").attr("style","display:none");
				if(msg.result == "success"){
					showSumTable(msg);
				}else{
					$("#month_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error:function(msg){
				$("#month_loading").attr("style","display:none");
			}
		});
	}
	
	function showSumTable(msg){
		var data = msg.data;
		
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='20%'>日期</td>";
		table += "<th width='25%'>订单数量</td>";
		table += "<th width='25%'>购买数量</td>";
		table += "<th width='25%'>销售总金额(元)</td>";
		table += "</tr></thead><tbody>";

		$.each(data,function(i,n){
			table += "<tr>";
			table += "<td>"+n.date+"</td>";
			table += "<td>"+formatNumber(n.nums)+"</td>";
			table += "<td>"+formatNumber(n.qtys)+"</td>";
			table += "<td>"+formatNumber(n.salePrices, 2)+"</td>";
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#month_container").html(table);
	}
	
	// 统计总量(根据省份)
	function ajaxGetCutPage(){
		$("#province_loading").removeAttr("style");
		$("#province_container").empty();
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
		params += "&vst_shopping_live_order_channel=" + $("#vst_shopping_live_order_channel").val();
		params += "&vst_shopping_live_order_number=" + $("#vst_shopping_live_order_number").val();
		params += "&vst_shopping_live_order_goods_number=" + $("#vst_shopping_live_order_goods_number").val();
		params += "&vst_shopping_live_order_goods_category=" + $("#vst_shopping_live_order_goods_category").val();
		params += "&vst_shopping_live_order_goods_name=" + $("#vst_shopping_live_order_goods_name").val();
		params += "&vst_shopping_live_order_province=" + $("#vst_shopping_live_order_province").val();
		params += "&vst_shopping_live_order_address=" + $("#vst_shopping_live_order_address").val();
		params += "&vst_shopping_live_order_recipient=" + $("#vst_shopping_live_order_recipient").val();
		params += "&vst_shopping_live_order_phone=" + $("#vst_shopping_live_order_phone").val();
		params += "&vst_shopping_live_order_balance_status=" + $("#vst_shopping_live_order_balance_status").val();
		params += "&vst_shopping_live_order_logistics_status=" + $("#vst_shopping_live_order_logistics_status").val();
		params += "&vst_shopping_live_order_operator=" + $("#vst_shopping_live_order_operator").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/shoppingLiveOrder/ajaxGetReportDataByProvince.action",
			dataType:"json",
			success:function(msg){
				$("#province_loading").attr("style","display:none");
				if(msg.result == "success"){
					showTable(msg);
				}else{
					$("#province_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo(0, $("#singleCount").val());
				}
			},
			error:function(msg){
				$("#province_loading").attr("style","display:none");
			}
		});
	}

	function showTable(msg){
		var data = msg.data;
		showPageInfo(msg.totalSize, msg.singleSize);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'>#</td>";
		table += "<th width='20%'>省份</td>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='num' id='num2' order='desc'>订单数量</a></td>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='qty' id='qty2' order='desc'>购买数量</a></td>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='salePrice' id='salePrice2' order='desc'>销售总金额(元)</a></td>";
		table += "<th width='10%'>操作</th>";
		table += "</tr></thead><tbody>";

		$.each(data,function(i,n){
			var row = i%2+1;
			var id = ($("#currPage").val()-1)*msg.singleSize+i+1;
			table += "<tr class='row"+row+"'><td>"+id+"</td>";
			table += "<td>"+n.province+"</td>";
			table += "<td>"+formatNumber(n.num)+"</td>";
			table += "<td>"+formatNumber(n.qty)+"</td>";
			table += "<td>"+formatNumber(n.salePrice)+"</td>";
			table += "<td><a href='${ctx}/shoppingLiveOrder/findOrderSumDetail.action?vst_shopping_live_order_province=" + n.province
						+ "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val()
						+ "&vst_shopping_live_order_channel=" + $("#vst_shopping_live_order_channel").val()
						+ "&vst_shopping_live_order_number=" + $("#vst_shopping_live_order_number").val()
						+ "&vst_shopping_live_order_goods_number=" + $("#vst_shopping_live_order_goods_number").val()
						+ "&vst_shopping_live_order_goods_category=" + $("#vst_shopping_live_order_goods_category").val()
						+ "&vst_shopping_live_order_goods_name=" + $("#vst_shopping_live_order_goods_name").val()
						+ "&vst_shopping_live_order_address=" + $("#vst_shopping_live_order_address").val()
						+ "&vst_shopping_live_order_recipient=" + $("#vst_shopping_live_order_recipient").val()
						+ "&vst_shopping_live_order_phone=" + $("#vst_shopping_live_order_phone").val()
						+ "&vst_shopping_live_order_balance_status=" + $("#vst_shopping_live_order_balance_status").val()
						+ "&vst_shopping_live_order_logistics_status=" + $("#vst_shopping_live_order_logistics_status").val()
						+ "&vst_shopping_live_order_operator=" + $("#vst_shopping_live_order_operator").val()
						+ "&moduleId=" + $("#moduleId").val() + "' target='_black'>查看详情</a></td>";
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#province_container").html(table);
		showOrder(orderBy, order);
	}
	
	// 导出数据(根据省份)
	function button_exportByProvince(){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		
		if(!isNotEmpty(startDate) || !isNotEmpty(endDate) || getDateDiff(startDate, endDate, 'day') > 92){
			alert("亲，最多只能导出三个月的数据哦")
			return false;
		}else if(confirm("导出速度，由数据大小决定，请耐心等待。。")){
			var params = "startDate=" + startDate + "&endDate=" + endDate;
			params += "&vst_shopping_live_order_channel=" + $("#vst_shopping_live_order_channel").val();
			params += "&vst_shopping_live_order_number=" + $("#vst_shopping_live_order_number").val();
			params += "&vst_shopping_live_order_goods_number=" + $("#vst_shopping_live_order_goods_number").val();
			params += "&vst_shopping_live_order_goods_category=" + $("#vst_shopping_live_order_goods_category").val();
			params += "&vst_shopping_live_order_goods_name=" + $("#vst_shopping_live_order_goods_name").val();
			params += "&vst_shopping_live_order_province=" + $("#vst_shopping_live_order_province").val();
			params += "&vst_shopping_live_order_address=" + $("#vst_shopping_live_order_address").val();
			params += "&vst_shopping_live_order_recipient=" + $("#vst_shopping_live_order_recipient").val();
			params += "&vst_shopping_live_order_phone=" + $("#vst_shopping_live_order_phone").val();
			params += "&vst_shopping_live_order_balance_status=" + $("#vst_shopping_live_order_balance_status").val();
			params += "&vst_shopping_live_order_logistics_status=" + $("#vst_shopping_live_order_logistics_status").val();
			params += "&vst_shopping_live_order_operator=" + $("#vst_shopping_live_order_operator").val();
			
			location.href = "${ctx}/shoppingLiveOrder/exportSumDataByProvince.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
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
				<form action="${ctx}/shoppingLiveOrder/findShoppingLiveOrderSum.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 全球购统计 > 直播订单汇总</span>
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
												<span class="sertitle">起始日期：</span>
												<input class="list-input1 ic-calendar" id="startDate" name="startDate" readonly="readonly" />
											</li>
											<li>
												<span class="sertitle">结束日期：</span>
												<input class="list-input1 ic-calendar" id="endDate" name="endDate" readonly="readonly" />
											</li>
											<li>
												<span class="sertitle">渠道：</span>
												<select class="list-input1" id="vst_shopping_live_order_channel" name="vst_shopping_live_order_channel">
													<option value="">请选择</option>
													<c:forEach items="${channels}" var="channel">
														<option value="${channel.key }">${channel.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">订单号：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_number" name="vst_shopping_live_order_number" />
											</li>
											<li>
												<span class="sertitle">商品编号：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_goods_number" name="vst_shopping_live_order_goods_number" />
											</li>
											<li>
												<span class="sertitle">商品分类：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_goods_category" name="vst_shopping_live_order_goods_category" />
											</li>
											<li>
												<span class="sertitle">商品名称：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_goods_name" name="vst_shopping_live_order_goods_name" />
											</li>
											<li>
												<span class="sertitle">省份：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_province" name="vst_shopping_live_order_province" />
											</li>
											<li>
												<span class="sertitle">收件地址：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_address" name="vst_shopping_live_order_address" />
											</li>
											<li>
												<span class="sertitle">收件人：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_recipient" name="vst_shopping_live_order_recipient" />
											</li>
											<li>
												<span class="sertitle">联系电话：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_phone" name="vst_shopping_live_order_phone" />
											</li>
											<li>
												<span class="sertitle">对账状态：</span>
												<select class="list-input1" id="vst_shopping_live_order_balance_status" name="vst_shopping_live_order_balance_status">
													<option value="">请选择</option>
													<c:forEach items="${balanceStatus}" var="balance">
														<option value="${balance.key }">${balance.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">物流状态：</span>
												<select class="list-input1" id="vst_shopping_live_order_logistics_status" name="vst_shopping_live_order_logistics_status">
													<option value="">请选择</option>
													<c:forEach items="${logisticsStatus}" var="logistics">
														<option value="${logistics.key }">${logistics.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">操作人：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_operator" name="vst_shopping_live_order_operator" />
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
					
					<!-- 月统计总量 -->
					<section class="content">
				    <div class="row">
				        <div class="col-xs-12">
				            <div class="box">
					            <div class="box-header">
			                        <h3 class="box-title">月统计总量</h3>
			                    </div>
					            <div class="box-body">
					          		<div id="month_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
					          		<div id="month_container">
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
				    </section>
				    
				    <!-- 按商品分类统计 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <!-- PIE CHART -->
			                <div class="box box-primary">
			                    <div class="box-header with-border">
			                        <h3 class="box-title">商品分类占比：购买数量</h3>
			                        <div class="box-tools pull-right">
			                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
			                            </button>
			                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
			                        </div>
			                    </div>
			                    <div class="box-body chart-responsive">
			                        <div class="chart" id="category_container" >
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
			                    <div id="category_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
			                </div>
			            </div>
			        </div>
					</section>
					
					<!-- 按商品来源统计 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <!-- BAR LINE CHART -->
			                <div class="box box-primary">
			                    <div class="box-header with-border">
			                        <h3 class="box-title">来源总量：购买数量</h3>
			                        <div class="box-tools pull-right">
			                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
			                            </button>
			                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
			                        </div>
			                    </div>
			                    <div class="box-body chart-responsive">
			                        <div class="chart" id="source_container" >
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
			                    <div id="source_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
			                </div>
			            </div>
			        </div>
					</section>
					
					<!-- 按省份统计 -->
					<section class="content">
				    <div class="row">
				        <div class="col-xs-12">
				            <div class="box">
					            <div class="box-header">
					                <h3 class="box-title">省份总量：订单数</h3>
					              	<div style="float: right;">
								   	  	<img src="${ctx}/pub/listPages/images/t1_export.png" width="14" height="14"/>
								    	<a href="#" onclick="button_exportByProvince()">导出总量</a>
								    </div>
					            </div>
					            <div class="box-body">
					          		<div id="province_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
					          		<div id="province_container">
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
	
<!--日历-->
<script type="text/javascript">
  	laydate.render({
	  	elem: '#startDate',
	  	type: 'datetime',
	  	format: 'yyyy-MM-dd HH:mm',
	  	theme: 'molv'
  	});

  	laydate.render({
	  	elem: '#endDate',
	  	type: 'datetime',
	  	format: 'yyyy-MM-dd HH:mm',
	  	theme: 'molv'
	});
	
  	$("#startDate").val(getNDateTime("yyyy-MM-dd",7)+" 00:00");
  	$("#endDate").val(getNDateTime("yyyy-MM-dd",0)+" 00:00");
</script>

</body>
</html>