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
		$("#vst_shopping_play_order_good_id").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_play_order_good_name").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_play_order_province").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_play_order_address").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_play_order_recipient").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_play_order_phone").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_shopping_play_order_operator").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});

		//切换tab重绘highcharts图
        $('ul.nav > li').click(function () {
            setTimeout(function () {
                $('#nums-chart').highcharts().reflow();
                $('#salePrice-chart').highcharts().reflow();
            },50)
        });
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDate").val() == '' || $("#endDate").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
			ajaxGetData();// 查看统计图(根据渠道)
		    ajaxGetCutPage();// 统计总量(根据省份)
			ajaxGetCutPage2();// 月统计总量
		}
	}

	// 查看统计图(根据渠道)
	function ajaxGetData(){
		$("#loading_nums").removeAttr("style");
		$("#nums-chart").empty();
		$("#loading_salePrice").removeAttr("style");
		$("#salePrice-chart").empty();

		var params = "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
		params += "&vst_shopping_play_order_channel=" + $("#vst_shopping_play_order_channel").val();
		params += "&vst_shopping_play_order_good_id=" + $("#vst_shopping_play_order_good_id").val();
		params += "&vst_shopping_play_order_good_name=" + $("#vst_shopping_play_order_good_name").val();
		params += "&vst_shopping_play_order_supplier_type=" + $("#vst_shopping_play_order_supplier_type").val();
		params += "&vst_shopping_play_order_province=" + $("#vst_shopping_play_order_province").val();
		params += "&vst_shopping_play_order_address=" + $("#vst_shopping_play_order_address").val();
		params += "&vst_shopping_play_order_recipient=" + $("#vst_shopping_play_order_recipient").val();
		params += "&vst_shopping_play_order_phone=" + $("#vst_shopping_play_order_phone").val();
		params += "&vst_shopping_play_order_operator=" + $("#vst_shopping_play_order_operator").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/shoppingPlayOrder/ajaxGetReportSumData.action",
			dataType : "json",
			success : function(msg){
				$("#loading_nums").attr("style","display:none");
				$("#loading_salePrice").attr("style","display:none");
				if(msg.result == "success"){
					barNums(msg);
					barSalePrice(msg);
				}else{
					$("#nums-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#salePrice-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#loading_nums").attr("style","display:none");
				$("#loading_salePrice").attr("style","display:none");
			}
		});
	}

	function barNums(msg){
		var data = msg.data;
   		var xaxis = [];
		var fasionNums = [];
		var globalbuyNums = [];
		var hailaiNums = [];
		var ziyingNums = [];
		var tollNums = [];
		var enjoyNums = [];
		var youtaiNums = [];
		var cnrmallNums = [];
		var jiayouNums = [];
		// 是否显示
		var fasionVisible = false;
		var globalbuyVisible = false;
		var hailaiVisible = false;
		var ziyingVisible = false;
		var tollVisible = false;
		var enjoyVisible = false;
		var youtaiVisible = false;
		var cnrmallVisible = false;
		var jiayouVisible = false;
		
		$.each(data,function(i,n){
			xaxis[i] = n.date;
			fasionNums[i] = n.nums1;
			globalbuyNums[i] = n.nums2;
			hailaiNums[i] = n.nums3;
			ziyingNums[i] = n.nums4;
			tollNums[i] = n.nums5;
			enjoyNums[i] = n.nums6;
			youtaiNums[i] = n.nums7;
			cnrmallNums[i] = n.nums8;
			jiayouNums[i] = n.nums9;

			if(fasionNums[i] > 0){
				fasionVisible = true;
			}
			if(globalbuyNums[i] > 0){
				globalbuyVisible = true;
			}
			if(hailaiNums[i] > 0){
				hailaiVisible = true;
			}
			if(ziyingNums[i] > 0){
				ziyingVisible = true;
			}
			if(tollNums[i] > 0){
				tollVisible = true;
			}
			if(enjoyNums[i] > 0){
				enjoyVisible = true;
			}
			if(youtaiNums[i] > 0){
				youtaiVisible = true;
			}
			if(cnrmallNums[i] > 0){
				cnrmallVisible = true;
			}
			if(jiayouNums[i] > 0){
				jiayouVisible = true;
			}
		});
		
		$("#nums-chart").highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: false
	        },
	        xAxis: {
	            categories : xaxis
	        },
	        yAxis: {
	            title:false,
				min : 0,
				tickAmount: 5,
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
	        },
	        plotOptions: {
	            column: {
	                stacking: 'normal'
	            }
	        },
	        series: [{
	            name: '总数量(风尚)',
	            data: fasionNums,
	            stack: 'fasion',
	            visible : fasionVisible
	        }, {
	            name: '总数量(环球)',
	            data: globalbuyNums,
	            stack: 'globalbuy',
	            visible : globalbuyVisible
	        }, {
	            name: '总数量(海莱)',
	            data: hailaiNums,
	            stack: 'hailai',
	            visible : hailaiVisible
	        }, {
	            name: '总数量(自营)',
	            data: ziyingNums,
	            stack: 'ziying',
	            visible : ziyingVisible
	        }, {
	            name: '总数量(拓亚)',
	            data: tollNums,
	            stack: 'toll',
	            visible : tollVisible
	        }, {
	            name: '总数量(好享购)',
	            data: enjoyNums,
	            stack: 'enjoy',
	            visible : enjoyVisible
	        }, {
	            name: '总数量(悠态)',
	            data: youtaiNums,
	            stack: 'youtai',
	            visible : youtaiVisible
	        }, {
	            name: '总数量(央广)',
	            data: cnrmallNums,
	            stack: 'cnrmall',
	            visible : cnrmallVisible
	        }, {
	            name: '总数量(家有)',
	            data: jiayouNums,
	            stack: 'jiayou',
	            visible : jiayouVisible
	        }
	        ]
	    });
	}

	function barSalePrice(msg){
		var data = msg.data;
   		var xaxis = [];
		var fasionSalePrices = [];
		var globalbuySalePrices = [];
		var hailaiSalePrices = [];
		var ziyingSalePrices = [];
		var tollSalePrices = [];
		var enjoySalePrices = [];
		var youtaiSalePrices = [];
		var cnrmallSalePrices = [];
		var jiayouSalePrices = [];
		// 是否显示
		var fasionVisible = false;
		var globalbuyVisible = false;
		var hailaiVisible = false;
		var ziyingVisible = false;
		var tollVisible = false;
		var enjoyVisible = false;
		var youtaiVisible = false;
		var cnrmallVisible = false;
		var jiayouVisible = false;
		
		$.each(data,function(i,n){
			xaxis[i] = n.date;
			fasionSalePrices[i] = n.salePrices1;
			globalbuySalePrices[i] = n.salePrices2;
			hailaiSalePrices[i] = n.salePrices3;
			ziyingSalePrices[i] = n.salePrices4;
			tollSalePrices[i] = n.salePrices5;
			enjoySalePrices[i] = n.salePrices6;
			youtaiSalePrices[i] = n.salePrices7;
			cnrmallSalePrices[i] = n.salePrices8;
			jiayouSalePrices[i] = n.salePrices9;
			
			if(fasionSalePrices[i] > 0){
				fasionVisible = true;
			}
			if(globalbuySalePrices[i] > 0){
				globalbuyVisible = true;
			}
			if(hailaiSalePrices[i] > 0){
				hailaiVisible = true;
			}
			if(ziyingSalePrices[i] > 0){
				ziyingVisible = true;
			}
			if(tollSalePrices[i] > 0){
				tollVisible = true;
			}
			if(enjoySalePrices[i] > 0){
				enjoyVisible = true;
			}
			if(youtaiSalePrices[i] > 0){
				youtaiVisible = true;
			}
			if(cnrmallSalePrices[i] > 0){
				cnrmallVisible = true;
			}
			if(jiayouSalePrices[i] > 0){
				jiayouVisible = true;
			}
		});
		
		$("#salePrice-chart").highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: false
	        },
	        xAxis: {
	            categories : xaxis
	        },
	        yAxis: {
	            title:false,
				min : 0,
				tickAmount: 5,
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
	        },
	        plotOptions: {
	            column: {
	                stacking: 'normal'
	            }
	        },
	        series: [{
	            name: '销售总金额(风尚)',
	            data: fasionSalePrices,
	            stack: 'fasion',
	            visible : fasionVisible
	        }, {
	            name: '销售总金额(环球)',
	            data: globalbuySalePrices,
	            stack: 'globalbuy',
	            visible : globalbuyVisible
	        }, {
	            name: '销售总金额(海莱)',
	            data: hailaiSalePrices,
	            stack: 'hailai',
	            visible : hailaiVisible
	        }, {
	            name: '销售总金额(自营)',
	            data: ziyingSalePrices,
	            stack: 'ziying',
	            visible : ziyingVisible
	        }, {
	            name: '销售总金额(拓亚)',
	            data: tollSalePrices,
	            stack: 'toll',
	            visible : tollVisible
	        }, {
	            name: '销售总金额(好享购)',
	            data: enjoySalePrices,
	            stack: 'enjoy',
	            visible : enjoyVisible
	        }, {
	            name: '销售总金额(悠态)',
	            data: youtaiSalePrices,
	            stack: 'youtai',
	            visible : youtaiVisible
	        }, {
	            name: '销售总金额(央广)',
	            data: cnrmallSalePrices,
	            stack: 'cnrmall',
	            visible : cnrmallVisible
	        }, {
	            name: '销售总金额(家有)',
	            data: jiayouSalePrices,
	            stack: 'jiayou',
	            visible : jiayouVisible
	        }
	        ]
	    });
	}

	// 月统计总量
	function ajaxGetCutPage2(){
		$("#month_loading").removeAttr("style");
		$("#month_container").empty();
		
		var params = "startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
		params += "&vst_shopping_play_order_channel=" + $("#vst_shopping_play_order_channel").val();
		params += "&vst_shopping_play_order_good_id=" + $("#vst_shopping_play_order_good_id").val();
		params += "&vst_shopping_play_order_good_name=" + $("#vst_shopping_play_order_good_name").val();
		params += "&vst_shopping_play_order_supplier_type=" + $("#vst_shopping_play_order_supplier_type").val();
		params += "&vst_shopping_play_order_province=" + $("#vst_shopping_play_order_province").val();
		params += "&vst_shopping_play_order_address=" + $("#vst_shopping_play_order_address").val();
		params += "&vst_shopping_play_order_recipient=" + $("#vst_shopping_play_order_recipient").val();
		params += "&vst_shopping_play_order_phone=" + $("#vst_shopping_play_order_phone").val();
		params += "&vst_shopping_play_order_operator=" + $("#vst_shopping_play_order_operator").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,	
			url:"${ctx}/shoppingPlayOrder/ajaxGetSumCutPage.action",
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
		params += "&vst_shopping_play_order_channel=" + $("#vst_shopping_play_order_channel").val();
		params += "&vst_shopping_play_order_good_id=" + $("#vst_shopping_play_order_good_id").val();
		params += "&vst_shopping_play_order_good_name=" + $("#vst_shopping_play_order_good_name").val();
		params += "&vst_shopping_play_order_supplier_type=" + $("#vst_shopping_play_order_supplier_type").val();
		params += "&vst_shopping_play_order_province=" + $("#vst_shopping_play_order_province").val();
		params += "&vst_shopping_play_order_address=" + $("#vst_shopping_play_order_address").val();
		params += "&vst_shopping_play_order_recipient=" + $("#vst_shopping_play_order_recipient").val();
		params += "&vst_shopping_play_order_phone=" + $("#vst_shopping_play_order_phone").val();
		params += "&vst_shopping_play_order_operator=" + $("#vst_shopping_play_order_operator").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/shoppingPlayOrder/ajaxGetReportDataByProvince.action",
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
			table += "<td><a href='${ctx}/shoppingPlayOrder/findOrderSumDetail.action?vst_shopping_play_order_province=" + n.province
						+ "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val()
						+ "&vst_shopping_play_order_channel=" + $("#vst_shopping_play_order_channel").val()
						+ "&vst_shopping_play_order_good_id=" + $("#vst_shopping_play_order_good_id").val()
						+ "&vst_shopping_play_order_good_name=" + $("#vst_shopping_play_order_good_name").val()
						+ "&vst_shopping_play_order_supplier_type=" + $("#vst_shopping_play_order_supplier_type").val()
						+ "&vst_shopping_play_order_address=" + $("#vst_shopping_play_order_address").val()
						+ "&vst_shopping_play_order_recipient=" + $("#vst_shopping_play_order_recipient").val()
						+ "&vst_shopping_play_order_phone=" + $("#vst_shopping_play_order_phone").val()
						+ "&vst_shopping_play_order_operator=" + $("#vst_shopping_play_order_operator").val()
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
			params += "&vst_shopping_play_order_channel=" + $("#vst_shopping_play_order_channel").val();
			params += "&vst_shopping_play_order_good_id=" + $("#vst_shopping_play_order_good_id").val();
			params += "&vst_shopping_play_order_good_name=" + $("#vst_shopping_play_order_good_name").val();
			params += "&vst_shopping_play_order_supplier_type=" + $("#vst_shopping_play_order_supplier_type").val();
			params += "&vst_shopping_play_order_province=" + $("#vst_shopping_play_order_province").val();
			params += "&vst_shopping_play_order_address=" + $("#vst_shopping_play_order_address").val();
			params += "&vst_shopping_play_order_recipient=" + $("#vst_shopping_play_order_recipient").val();
			params += "&vst_shopping_play_order_phone=" + $("#vst_shopping_play_order_phone").val();
			params += "&vst_shopping_play_order_operator=" + $("#vst_shopping_play_order_operator").val();
			
			location.href = "${ctx}/shoppingPlayOrder/exportSumDataByProvince.action?" + params
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
				<form action="${ctx}/shoppingPlayOrder/findShoppingPlayOrderSum.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 全球购统计 > 点播订单汇总</span>
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
												<select class="list-input1" id="vst_shopping_play_order_channel" name="vst_shopping_play_order_channel">
													<option value="">请选择</option>
													<c:forEach items="${channels}" var="channel">
														<option value="${channel.key }">${channel.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">商品编号：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_good_id" name="vst_shopping_play_order_good_id" />
											</li>
											<li>
												<span class="sertitle">商品名称：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_good_name" name="vst_shopping_play_order_good_name" />
											</li>
											<li>
												<span class="sertitle">供应商类型：</span>
												<select class="list-input1" id="vst_shopping_play_order_supplier_type" name="vst_shopping_play_order_supplier_type">
													<option value="">供应商</option>
													<c:forEach items="${supplierTypes}" var="supplierType">
														<option value="${supplierType.key }">${supplierType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">省份：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_province" name="vst_shopping_play_order_province" />
											</li>
											<li>
												<span class="sertitle">收件地址：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_address" name="vst_shopping_play_order_address" />
											</li>
											<li>
												<span class="sertitle">收件人：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_recipient" name="vst_shopping_play_order_recipient" />
											</li>
											<li>
												<span class="sertitle">联系电话：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_phone" name="vst_shopping_play_order_phone" />
											</li>
											<li>
												<span class="sertitle">操作人：</span>
												<input class="list-input1" type="text" id="vst_shopping_play_order_operator" name="vst_shopping_play_order_operator" />
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
					
					<!-- 按渠道统计 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <div class="box box-primary">
			                	<div class="box-header">
			                        <h3 class="box-title">渠道统计</h3>
			                        <ul class="nav nav-pills" role="tablist" style="float: right;">
										<li role="presentation" style="float: right;"><a href="#tab-salePrice" aria-controls="messages" role="tab" data-toggle="tab">总金额</a></li>
										<li role="presentation" class="active" style="float: right;"><a href="#tab-nums" aria-controls="profile" role="tab" data-toggle="tab">总数量</a></li>
									</ul>
			                    </div>
			                    <div class="box-body chart-responsive">
									<div class="tab-content" style="width: 100%;">
										<!-- 总数量 -->
										<div role="tabpanel" class="tab-pane active" id="tab-nums" style="width: 100%;">
											<div id="loading_nums" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
											<div class="chart" id="nums-chart" >
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
										<!-- 总金额 -->
										<div role="tabpanel" class="tab-pane" id="tab-salePrice" style="width: 100%;">
											<div id="loading_salePrice" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
											<div class="chart" id="salePrice-chart" >
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