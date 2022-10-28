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
								<i class="fa fa-home"></i><span style="font-size:14px;">全球购统计 > 点播订单统计 > 导出</span>
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
				<form action="${ctx}/shoppingPlayOrder/findShoppingPlayOrder.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 全球购统计 > 点播订单统计
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
					
					<!-- 按天统计 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <!-- LINE CHART -->
			                <div class="box box-primary">
			                    <div class="box-header with-border">
			                        <h3 class="box-title">
			                        	按天统计
			                        	<span id="day_msg" style="font-size: 14px;"></span>
			                        </h3>
			                        <h5 id="day_msg"></h5>
			                        <div class="box-tools pull-right">
			                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
			                            </button>
			                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
			                        </div>
			                    </div>
			                    <div class="box-body chart-responsive">
			                        <div class="chart" id="day_container" >
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
			                    <div id="day_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
			                </div>
			            </div>
			        </div>
					</section>
					
					<!-- 按小时统计 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <!-- LINE CHART -->
			                <div class="box box-primary">
			                    <div class="box-header with-border">
			                        <h3 class="box-title">按小时统计</h3>
			                        <div class="box-tools pull-right">
			                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
			                            </button>
			                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
			                        </div>
			                    </div>
			                    <div class="box-body chart-responsive">
			                        <div class="chart" id="hour_container" >
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
			                    <div id="hour_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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
			                        <h3 class="box-title">按渠道统计</h3>
			                        <ul class="nav nav-pills" role="tablist" style="float: right;">
										<li role="presentation" style="float: right;"><a href="#tab-price" aria-controls="messages" role="tab" data-toggle="tab">销售金额</a></li>
										<li role="presentation" class="active" style="float: right;"><a href="#tab-num" aria-controls="profile" role="tab" data-toggle="tab">订单数</a></li>
									</ul>
			                    </div>
			                    <div class="box-body chart-responsive">
									<div class="tab-content" style="width: 100%;">
										<!-- 订单数 -->
										<div role="tabpanel" class="tab-pane active" id="tab-num" style="width: 100%;">
											<div id="loading_num" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
											<div class="chart" id="num_container" >
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
										<!-- 销售金额 -->
										<div role="tabpanel" class="tab-pane" id="tab-price" style="width: 100%;">
											<div id="loading_price" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
											<div class="chart" id="price_container" >
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
					
					<!-- 商品总量 -->
					<section class="content">
				    <div class="row">
				        <div class="col-xs-12">
				            <div class="box">
					            <div class="box-header">
									<h3 class="box-title">商品销售总量</h3>
					              	<div style="float: right;">
								   	  	<img src="${ctx}/pub/listPages/images/t1_export.png" width="14" height="14"/>
								    	<a href="#" onclick="button_exportSum()">导出总量</a>
								    </div>
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
		$.enlargePic();
		//切换tab重绘highcharts图
        $('ul.nav > li').click(function () {
            setTimeout(function () {
                $('#num_container').highcharts().reflow();
                $('#price_container').highcharts().reflow();
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
			$("#currPage2").val(1);
			ajaxGetData();// 查看统计图
			ajaxGetDataByChannel();// 查看统计图(根据渠道)
			ajaxGetCutPage();// 查询数据(显示分页信息)
			ajaxGetCutPage2();// 查询商品总量
		}
	}

	// 查看统计图
	function ajaxGetData(){
		$("#day_loading").removeAttr("style");
		$("#day_container").empty();
		$("#hour_loading").removeAttr("style");
		$("#hour_container").empty();

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
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/shoppingPlayOrder/ajaxGetReportByDate.action",
			dataType : "json",
			success : function(msg){
				$("#day_loading").attr("style","display:none");
				$("#hour_loading").attr("style","display:none");
				if(msg.result == "success"){
					line_day(msg);
					line_hour(msg);
				}else{
					$("#day_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#hour_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#day_loading").attr("style","display:none");
				$("#hour_loading").attr("style","display:none");
			}
		});
	}

	function line_day(msg){
		var data = msg.data;
   		var xaxis = [];
		var num = [];
		var qty = [];
		var salePrice = [];
		var price = [];
		//总量
		var numSum = 0;
		var qtySum = 0;
		var salePriceSum = 0;
		var priceSum = 0;
		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			num[i] = n.num;
			qty[i] = n.qty;
			salePrice[i] = n.salePrice;
			price[i] = Math.round(n.salePrice/n.qty*100)/100;
			numSum += n.num;
			qtySum += n.qty;
			salePriceSum += n.salePrice;
		});
		priceSum = Math.round(salePriceSum/qtySum*100)/100;
		salePriceSum = Math.round(salePriceSum*100)/100;

		$("#day_msg").html("(订单数量:<font class='red'>"+numSum+"</font>, 购买数量:<font class='red'>"+qtySum+"</font>, 销售总金额:<font class='red'>"+salePriceSum+"</font>元, 客单价:<font class='red'>"+priceSum+"</font>)");

		$("#day_container").highcharts({
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
				name : '订单数量',
				data : num,
			},
			{
				name : '购买数量',
				data : qty,
			},
			{
				name : '销售总金额',
				data : salePrice,
				tooltip : {valueSuffix : ' 元'}
			},
			{
				name : '客单价',
				data : price,
				tooltip : {valueSuffix : ' 元'}
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
	
	function line_hour(msg){
		var data = msg.data2;
   		var xaxis = [];
		var num = [];
		var qty = [];
		var salePrice = [];
		var price = [];
		$.each(data,function(i,n){
			xaxis[i] = n.date;
			num[i] = n.num;
			qty[i] = n.qty;
			salePrice[i] = n.salePrice;
			price[i] = Math.round(n.salePrice/n.qty*100)/100;
			if(isNaN(price[i])){
				price[i] = 0;
			}
		});

		$("#hour_container").highcharts({
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
				categories : xaxis
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
				headerFormat: '&nbsp;&nbsp;&nbsp;小时：{point.key}<br/>',
				shared: true,
				useHTML: true
			},
			series : [
			{
				name : '订单数量',
				data : num,
			},
			{
				name : '购买数量',
				data : qty,
			},
			{
				name : '销售总金额',
				data : salePrice,
				tooltip : {valueSuffix : '元'}
			},
			{
				name : '客单价',
				data : price,
				tooltip : {valueSuffix : '元'}
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

	// 查看统计图(根据渠道)
	function ajaxGetDataByChannel(){
		$("#loading_num").removeAttr("style");
		$("#num_container").empty();
		$("#loading_price").removeAttr("style");
		$("#price_container").empty();

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
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/shoppingPlayOrder/ajaxGetReportByChannel.action",
			dataType : "json",
			success : function(msg){
				$("#loading_num").attr("style","display:none");
				$("#loading_price").attr("style","display:none");
				if(msg.result == "success"){
					line_num(msg);
					line_price(msg);
				}else{
					$("#num_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#price_container").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#loading_num").attr("style","display:none");
				$("#loading_price").attr("style","display:none");
			}
		});
	}

	function line_num(msg){
		var data = msg.data;
		var dataLength  = Object.keys(data).length;
		var xaxis = [];
		var yaxis = new Array();
		var channels = [];
		
		for(var i=0; i<dataLength; i++){
			yaxis[i] = new Array();
		}
		
		// 遍历数据
		var index = 0;
		$.each(data, function(channel,list){
			channels[index] = channel;
			for(var i=0; i<list.length; i++){
				xaxis[i] = list[i].date;
				yaxis[index][i] = list[i].num;
			}
			index++;
		});

		// 显示结果
		var series = [];
		for(var i=0; i<dataLength; i++){
			var json = {name : channels[i], data : yaxis[i]};
			series.push(json);
		}

		$("#num_container").highcharts({
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
				categories : xaxis
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
				headerFormat: '&nbsp;&nbsp;&nbsp;小时：{point.key}<br/>',
				shared: true,
				useHTML: true
			},
			plotOptions: {
	        	series: {
					marker: {
						enabled: false
					}
		    	}
			},
			series : series
		});
	}

	function line_price(msg){
		var data = msg.data;
		var dataLength  = Object.keys(data).length;
		var xaxis = [];
		var yaxis = new Array();
		var channels = [];
		
		for(var i=0; i<dataLength; i++){
			yaxis[i] = new Array();
		}
		
		// 遍历数据
		var index = 0;
		$.each(data, function(channel,list){
			channels[index] = channel;
			for(var i=0; i<list.length; i++){
				xaxis[i] = list[i].date;
				yaxis[index][i] = list[i].price;
			}
			index++;
		});

		// 显示结果
		var series = [];
		for(var i=0; i<dataLength; i++){
			var json = {name : channels[i], data : yaxis[i], tooltip : {valueSuffix : ' 元'}};
			series.push(json);
		}

		$("#price_container").highcharts({
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
				categories : xaxis
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
				headerFormat: '&nbsp;&nbsp;&nbsp;小时：{point.key}<br/>',
				shared: true,
				useHTML: true
			},
			plotOptions: {
	        	series: {
					marker: {
						enabled: false
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
			url:"${ctx}/shoppingPlayOrder/ajaxGetCutPage.action",
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
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_date' id='vst_shopping_play_order_date2' order='desc'>日期</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_channel' id='vst_shopping_play_order_channel2' order='desc'>渠道</a></th>";

		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_good_id' id='vst_shopping_play_order_good_id2' order='desc'>商品编号</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_good_name' id='vst_shopping_play_order_good_name2' order='desc'>商品名称</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_good_price' id='vst_shopping_play_order_good_price2' order='desc'>商品价格(元)</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_qty' id='vst_shopping_play_order_qty2' order='desc'>购买数量</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_sale_price' id='vst_shopping_play_order_sale_price2' order='desc'>销售金额(元)</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_province' id='vst_shopping_play_order_province2' order='desc'>省份</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_address' id='vst_shopping_play_order_address2' order='desc'>收件地址</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_recipient' id='vst_shopping_play_order_recipient2' order='desc'>收件人</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_phone' id='vst_shopping_play_order_phone2' order='desc'>联系电话</a></th>";
		
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_addtime' id='vst_shopping_play_order_addtime2' order='desc'>新增时间</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_uptime' id='vst_shopping_play_order_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_shopping_play_order_operator' id='vst_shopping_play_order_operator2' order='desc'>操作人</a></th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_shopping_play_order_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_date+"</td>";
			if(typeof(n.vst_shopping_play_order_channel)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${channels}', n.vst_shopping_play_order_channel)+"</td>";
			
			if(typeof(n.vst_shopping_play_order_good_id)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_good_id+"</td>";
			if(typeof(n.vst_shopping_play_order_good_name)=="undefined")	table += "<td></td>";
			else	table += "<td title='"+n.vst_shopping_play_order_good_name+"'>"+formatString(n.vst_shopping_play_order_good_name)+"</td>";
			if(typeof(n.vst_shopping_play_order_good_price)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_good_price+"</td>";
			if(typeof(n.vst_shopping_play_order_qty)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_qty+"</td>";
			if(typeof(n.vst_shopping_play_order_sale_price)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_sale_price+"</td>";
			if(typeof(n.vst_shopping_play_order_province)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_province+"</td>";
			if(typeof(n.vst_shopping_play_order_address)=="undefined")	table += "<td></td>";
			else	table += "<td title='"+n.vst_shopping_play_order_address+"'>"+formatString(n.vst_shopping_play_order_address)+"</td>";
			if(typeof(n.vst_shopping_play_order_recipient)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_recipient+"</td>";
			if(typeof(n.vst_shopping_play_order_phone)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_phone+"</td>";
			
			if(typeof(n.vst_shopping_play_order_addtime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_addtime+"</td>";
			if(typeof(n.vst_shopping_play_order_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_uptime+"</td>";
			if(typeof(n.vst_shopping_play_order_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_shopping_play_order_operator+"</td>";

			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move").html(table);
		showOrder(orderBy, order);
	}
	
	// 查询商品总量
	function ajaxGetCutPage2(){
		$("#moveSum_loading").removeAttr("style");
		$("#moveSum").empty();
		
		var params = "orderBy=" + $("#orderBy2").val() + "&order=" + $("#order2").val();
		params += "&currPage=" + $("#currPage2").val() + "&singleCount=" + $("#singleCount2").val();
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
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/shoppingPlayOrder/ajaxGetReportDataByGood.action",
			dataType:"json",
			success:function(msg){
				$("#moveSum_loading").attr("style","display:none");
				if(msg.result == "success"){
					showSumTable(msg);
				}else{
					$("#moveSum").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo2(0, $("#singleCount2").val());
				}
			},
			error:function(msg){
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
		table += "<th width='10%'>#</td>";
		table += "<th width='10%'>商品编号</td>";
		table += "<th width='20%'>商品名称</td>";
		table += "<th width='10%'><a onclick='changeOrder2(this)' orderBy='num' id='num2' order='desc'>订单数量</a></td>";
		table += "<th width='10%'><a onclick='changeOrder2(this)' orderBy='qty' id='qty2' order='desc'>购买数量</a></td>";
		table += "<th width='10%'><a onclick='changeOrder2(this)' orderBy='salePrice' id='salePrice2' order='desc'>销售总金额(元)</a></td>";
		table += "</tr></thead><tbody>";

		$.each(data,function(i,n){
			var row = i%2+1;
			var id = ($("#currPage2").val()-1)*msg.singleSize+i+1;
			table += "<tr class='row"+row+"'><td>"+id+"</td>";
			table += "<td>"+n.goodId+"</td>";
			table += "<td>"+n.goodName+"</td>";
			table += "<td>"+formatNumber(n.num)+"</td>";
			table += "<td>"+formatNumber(n.qty)+"</td>";
			table += "<td>"+formatNumber(n.salePrice)+"</td>";
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#moveSum").html(table);
		showOrder2(orderBy, order);
	}

	// 导出总量
	function button_exportSum(){
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
			
			location.href = "${ctx}/shoppingPlayOrder/exportSumDataByGood.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
		}
	}

	// 抓取
	function button_grasp(){
		$("#listForm").attr("action", "${ctx}/shoppingPlayOrder/toGrasp.action");
		$("#listForm").submit();
	}

	// 删除
	function button_delete(){
		if(confirm("是否删除查询出来的数据？")){
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
			params += "&moduleId=" + $("#moduleId").val();
			
			$("#listForm").attr("action", "${ctx}/shoppingPlayOrder/deletePlayOrder.action?"+params);
			$("#listForm").submit();
		}
	}

	/****************************导出******************************/
	// 导出数据
	function button_export(){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		
		if(!isNotEmpty(startDate) || !isNotEmpty(endDate) || getDateDiff(startDate, endDate, 'day') > 92){
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
					var startDate = $("#startDate").val();
					var endDate = $("#endDate").val();
					
					var params = "startDate=" + startDate + "&endDate=" + endDate;
					params += "&vst_shopping_play_order_channel=" + $("#vst_shopping_play_order_channel").val();
					params += "&vst_shopping_play_order_good_id=" + encodeURI(encodeURI($("#vst_shopping_play_order_good_id").val()));
					params += "&vst_shopping_play_order_good_name=" + encodeURI(encodeURI($("#vst_shopping_play_order_good_name").val()));
					params += "&vst_shopping_play_order_supplier_type=" + $("#vst_shopping_play_order_supplier_type").val();
					params += "&vst_shopping_play_order_province=" + encodeURI(encodeURI($("#vst_shopping_play_order_province").val()));
					params += "&vst_shopping_play_order_address=" + encodeURI(encodeURI($("#vst_shopping_play_order_address").val()));
					params += "&vst_shopping_play_order_recipient=" + encodeURI(encodeURI($("#vst_shopping_play_order_recipient").val()));
					params += "&vst_shopping_play_order_phone=" + encodeURI(encodeURI($("#vst_shopping_play_order_phone").val()));
					params += "&vst_shopping_play_order_operator=" + encodeURI(encodeURI($("#vst_shopping_play_order_operator").val()));
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/shoppingPlayOrder/exportData.action?" + params
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