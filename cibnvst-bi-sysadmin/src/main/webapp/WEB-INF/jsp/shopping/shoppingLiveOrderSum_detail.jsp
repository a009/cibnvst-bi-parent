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
		//下拉框赋值
		$("#vst_shopping_live_order_channel").val($("#hidden_shopping_live_order_channel").val());
		$("#vst_shopping_live_order_balance_status").val($("#hidden_shopping_live_order_balance_status").val());
		$("#vst_shopping_live_order_logistics_status").val($("#hidden_shopping_live_order_logistics_status").val());
		
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
			$("#currPage2").val(1);
			ajaxGetCutPage();// 分页统计
		    ajaxGetCutPage2();// 统计总量（根据地址）
		}
	}
	
	// 统计总量(根据地址)
	function ajaxGetCutPage2(){
		$("#address_loading").removeAttr("style");
		$("#address_container").empty();
		
		var params = "orderBy=" + $("#orderBy2").val() + "&order=" + $("#order2").val();
		params += "&currPage=" + $("#currPage2").val() + "&singleCount=" + $("#singleCount2").val();
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
			url:"${ctx}/shoppingLiveOrder/ajaxGetReportDataByAddress.action",
			dataType:"json",
			success:function(msg){
				$("#address_loading").attr("style","display:none");
				if(msg.result == "success"){
					showSumTable(msg);
				}else{
					$("#address_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo2(0, $("#singleCount").val());
				}
			},
			error:function(msg){
				$("#address_loading").attr("style","display:none");
			}
		});
	}

	function showSumTable(msg){
		var data = msg.data;
		showPageInfo2(msg.totalSize, msg.singleSize);
		var orderBy = $("#orderBy2").val();
		var order = $("#order2").val();
		
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'>#</td>";
		table += "<th width='20%'>地址</td>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='num' id='num2' order='desc'>订单数量</a></td>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='qty' id='qty2' order='desc'>购买数量</a></td>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='salePrice' id='salePrice2' order='desc'>销售总金额(元)</a></td>";
		table += "</tr></thead><tbody>";

		$.each(data,function(i,n){
			var row = i%2+1;
			var id = ($("#currPage").val()-1)*msg.singleSize+i+1;
			table += "<tr class='row"+row+"'><td>"+id+"</td>";
			table += "<td>"+n.address+"</td>";
			table += "<td>"+formatNumber(n.num)+"</td>";
			table += "<td>"+formatNumber(n.qty)+"</td>";
			table += "<td>"+formatNumber(n.salePrice)+"</td>";
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#address_container").html(table);
		showOrder2(orderBy, order);
	}
	
	// 导出数据(根据地址)
	function button_exportByAddress(){
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
			
			location.href = "${ctx}/shoppingLiveOrder/exportSumDataByAddress.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
		}
	}

	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();
		
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
			url:"${ctx}/shoppingLiveOrder/ajaxGetCutPage.action",
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
		showPageInfo(msg.totalSize, msg.singleSize);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_date' id='vst_shopping_live_order_date2' order='desc'>日期</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_channel' id='vst_shopping_live_order_channel2' order='desc'>渠道</a></th>";

		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_number' id='vst_shopping_live_order_number2' order='desc'>订单号</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_goods_number' id='vst_shopping_live_order_goods_number2' order='desc'>商品编号</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_goods_category' id='vst_shopping_live_order_goods_category2' order='desc'>商品分类</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_goods_name' id='vst_shopping_live_order_goods_name2' order='desc'>商品名称</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_goods_price' id='vst_shopping_live_order_goods_price2' order='desc'>商品价格(元)</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_qty' id='vst_shopping_live_order_qty2' order='desc'>购买数量</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_sale_price' id='vst_shopping_live_order_sale_price2' order='desc'>销售金额(元)</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_province' id='vst_shopping_live_order_province2' order='desc'>省份</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_address' id='vst_shopping_live_order_address2' order='desc'>收件地址</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_recipient' id='vst_shopping_live_order_recipient2' order='desc'>收件人</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_phone' id='vst_shopping_live_order_phone2' order='desc'>联系电话</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_source' id='vst_shopping_live_order_source2' order='desc'>商品来源</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_balance_status' id='vst_shopping_live_order_balance_status2' order='desc'>对账状态</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_logistics_status' id='vst_shopping_live_order_logistics_status2' order='desc'>物流状态</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_create_time' id='vst_shopping_live_order_create_time2' order='desc'>创建时间</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_balance_time' id='vst_shopping_live_order_balance_time2' order='desc'>对账时间</a></th>";
		
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_addtime' id='vst_shopping_live_order_addtime2' order='desc'>新增时间</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_uptime' id='vst_shopping_live_order_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_live_order_operator' id='vst_shopping_live_order_operator2' order='desc'>操作人</a></th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_shopping_live_order_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_date+"</td>";
			if(typeof(n.vst_shopping_live_order_channel)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${channels}', n.vst_shopping_live_order_channel)+"</td>";
			
			if(typeof(n.vst_shopping_live_order_number)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_number+"</td>";
			if(typeof(n.vst_shopping_live_order_goods_number)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_goods_number+"</td>";
			if(typeof(n.vst_shopping_live_order_goods_category)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_goods_category+"</td>";
			if(typeof(n.vst_shopping_live_order_goods_name)=="undefined")	table += "<td></td>";
			else	table += "<td title='"+n.vst_shopping_live_order_goods_name+"'>"+formatString(n.vst_shopping_live_order_goods_name)+"</td>";
			if(typeof(n.vst_shopping_live_order_goods_price)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_goods_price+"</td>";
			if(typeof(n.vst_shopping_live_order_qty)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_qty+"</td>";
			if(typeof(n.vst_shopping_live_order_sale_price)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_sale_price+"</td>";
			if(typeof(n.vst_shopping_live_order_province)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_province+"</td>";
			if(typeof(n.vst_shopping_live_order_address)=="undefined")	table += "<td></td>";
			else	table += "<td title='"+n.vst_shopping_live_order_address+"'>"+formatString(n.vst_shopping_live_order_address)+"</td>";
			if(typeof(n.vst_shopping_live_order_recipient)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_recipient+"</td>";
			if(typeof(n.vst_shopping_live_order_phone)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_phone+"</td>";
			if(typeof(n.vst_shopping_live_order_source)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_source+"</td>";
			if(typeof(n.vst_shopping_live_order_balance_status)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_balance_status+"</td>";
			if(typeof(n.vst_shopping_live_order_logistics_status)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_logistics_status+"</td>";
			if(typeof(n.vst_shopping_live_order_create_time)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_create_time+"</td>";
			if(typeof(n.vst_shopping_live_order_balance_time)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_balance_time+"</td>";
			
			if(typeof(n.vst_shopping_live_order_addtime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_addtime+"</td>";
			if(typeof(n.vst_shopping_live_order_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_uptime+"</td>";
			if(typeof(n.vst_shopping_live_order_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_live_order_operator+"</td>";

			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move").html(table);
		showOrder(orderBy, order);
	}
	
	// 导出数据
	function button_export(){
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
			
			location.href = "${ctx}/shoppingLiveOrder/exportData.action?" + params
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
												<input class="list-input1 ic-calendar" id="startDate" name="startDate" value="${queryParam.startDate}" readonly="readonly" />
											</li>
											<li>
												<span class="sertitle">结束日期：</span>
												<input class="list-input1 ic-calendar" id="endDate" name="endDate" value="${queryParam.endDate}" readonly="readonly" />
											</li>
											<li>
												<span class="sertitle">渠道：</span>
												<input type="hidden" id="hidden_shopping_live_order_channel" value="${queryParam.vst_shopping_live_order_channel}" />
												<select class="list-input1" id="vst_shopping_live_order_channel" name="vst_shopping_live_order_channel">
													<option value="">请选择</option>
													<c:forEach items="${channels}" var="channel">
														<option value="${channel.key }">${channel.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">订单号：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_number" name="vst_shopping_live_order_number" value="${queryParam.vst_shopping_live_order_number}" />
											</li>
											<li>
												<span class="sertitle">商品编号：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_goods_number" name="vst_shopping_live_order_goods_number" value="${queryParam.vst_shopping_live_order_goods_number}" />
											</li>
											<li>
												<span class="sertitle">商品分类：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_goods_category" name="vst_shopping_live_order_goods_category" value="${queryParam.vst_shopping_live_order_goods_category}" />
											</li>
											<li>
												<span class="sertitle">商品名称：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_goods_name" name="vst_shopping_live_order_goods_name" value="${queryParam.vst_shopping_live_order_goods_name}" />
											</li>
											<li>
												<span class="sertitle">省份：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_province" name="vst_shopping_live_order_province" value="${queryParam.vst_shopping_live_order_province}" />
											</li>
											<li>
												<span class="sertitle">收件地址：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_address" name="vst_shopping_live_order_address" value="${queryParam.vst_shopping_live_order_address}" />
											</li>
											<li>
												<span class="sertitle">收件人：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_recipient" name="vst_shopping_live_order_recipient" value="${queryParam.vst_shopping_live_order_recipient}" />
											</li>
											<li>
												<span class="sertitle">联系电话：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_phone" name="vst_shopping_live_order_phone" value="${queryParam.vst_shopping_live_order_phone}" />
											</li>
											<li>
												<span class="sertitle">对账状态：</span>
												<input type="hidden" id="hidden_shopping_live_order_balance_status" value="${queryParam.vst_shopping_live_order_balance_status}" />
												<select class="list-input1" id="vst_shopping_live_order_balance_status" name="vst_shopping_live_order_balance_status">
													<option value="">请选择</option>
													<c:forEach items="${balanceStatus}" var="balance">
														<option value="${balance.key }">${balance.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">物流状态：</span>
												<input type="hidden" id="hidden_shopping_live_order_logistics_status" value="${queryParam.vst_shopping_live_order_logistics_status}" />
												<select class="list-input1" id="vst_shopping_live_order_logistics_status" name="vst_shopping_live_order_logistics_status">
													<option value="">请选择</option>
													<c:forEach items="${logisticsStatus}" var="logistics">
														<option value="${logistics.key }">${logistics.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">操作人：</span>
												<input class="list-input1" type="text" id="vst_shopping_live_order_operator" name="vst_shopping_live_order_operator" value="${queryParam.vst_shopping_live_order_operator}" />
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
					
					<!-- 按地址统计 -->
					<section class="content">
				    <div class="row">
				        <div class="col-xs-12">
				            <div class="box">
					            <div class="box-header">
					                <h3 class="box-title">地址总量：订单数</h3>
					              	<div style="float: right;">
								   	  	<img src="${ctx}/pub/listPages/images/t1_export.png" width="14" height="14"/>
								    	<a href="#" onclick="button_exportByAddress()">导出总量</a>
								    </div>
					            </div>
					            <div class="box-body">
					          		<div id="address_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
					          		<div id="address_container">
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
					              	<h3 class="box-title">订单详情</h3>
					              	<div style="float: right;">
								   	  	<img src="${ctx}/pub/listPages/images/t1_export.png" width="14" height="14"/>
								    	<a href="#" onclick="button_export()">导出数据</a>
								    </div>
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
</script>

</body>
</html>