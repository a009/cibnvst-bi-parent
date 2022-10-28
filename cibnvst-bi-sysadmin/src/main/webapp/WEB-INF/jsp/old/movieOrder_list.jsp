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
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDay").val() == '' || $("#endDay").val() == ''){
			alert("查询日期不可为空！");
		}else{
			ajaxGetReport();// 按日期统计(折线图)
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	// 按日期统计(折线图)
	function ajaxGetReport(){
		$("#loading_line").removeAttr("style");
		$("#line-chart").empty();
		
		var params = "startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_movie_order_special_type=" + $("#vst_movie_order_special_type").val();
		params += "&vst_movie_order_movie_classify=" + $("#vst_movie_order_movie_classify").val();
		params += "&vst_movie_order_movie_name=" + $("#vst_movie_order_movie_name").val();
		params += "&vst_movie_order_entry1_name=" + $("#vst_movie_order_entry1_name").val();
		params += "&vst_movie_order_entry2_name=" + $("#vst_movie_order_entry2_name").val();
		params += "&vst_movie_order_entry3_name=" + $("#vst_movie_order_entry3_name").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_order_line",
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
		var vst_movie_order_amount = [];
		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_movie_order_amount[i] = n.vst_movie_order_amount;
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
				href : 'http://www.91vst.com',
				text : '91vst.com',
			},
			tooltip: {
				headerFormat: '&nbsp;&nbsp;&nbsp;时间：{point.key}<br/>',
				shared: true,
				useHTML: true
			},
			series : [
			{
				name : '总预约量',
				data : vst_movie_order_amount,
			}
			]
		});
	}
	
	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_movie_order_special_type=" + $("#vst_movie_order_special_type").val();
		params += "&vst_movie_order_movie_classify=" + $("#vst_movie_order_movie_classify").val();
		params += "&vst_movie_order_movie_name=" + $("#vst_movie_order_movie_name").val();
		params += "&vst_movie_order_entry1_name=" + $("#vst_movie_order_entry1_name").val();
		params += "&vst_movie_order_entry2_name=" + $("#vst_movie_order_entry2_name").val();
		params += "&vst_movie_order_entry3_name=" + $("#vst_movie_order_entry3_name").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_order_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_date' id='vst_movie_order_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_special_type' id='vst_movie_order_special_type2' order='desc'>专区类型</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_movie_classify' id='vst_movie_order_movie_classify2' order='desc'>分类</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_movie_name' id='vst_movie_order_movie_name2' order='desc'>影片名称</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_entry1_name' id='vst_movie_order_entry1_name2' order='desc'>一级入口</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_entry2_name' id='vst_movie_order_entry2_name2' order='desc'>二级入口</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_entry3_name' id='vst_movie_order_entry3_name2' order='desc'>三级入口</a></th>";
		
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_amount' id='vst_movie_order_amount2' order='desc'>预约量</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_order_amount_dod' id='vst_movie_order_amount_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_order_amount_wow' id='vst_movie_order_amount_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_uv' id='vst_movie_order_uv2' order='desc'>预约独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_order_uv_dod' id='vst_movie_order_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_order_uv_wow' id='vst_movie_order_uv_wow2' order='desc'>周环比</a></th>";

		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_pc_order_amount' id='vst_movie_pc_order_amount2' order='desc'>人均预约量</a></th>";
		
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_uptime' id='vst_movie_order_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_order_operator' id='vst_movie_order_operator2' order='desc'>操作人</a></th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var amountDodColor = "green";
			var amountWowColor = "green";
			var uvDodColor = "green";
			var uvWowColor = "green";
			
			if(parseFloat(n.vst_movie_order_amount_dod)<0){
				amountDodColor = "red";
			}
			if(parseFloat(n.vst_movie_order_amount_wow)<0){
				amountWowColor = "red";
			}
			if(parseFloat(n.vst_movie_order_uv_dod)<0){
				uvDodColor = "red";
			}
			if(parseFloat(n.vst_movie_order_uv_wow)<0){
				uvWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_movie_order_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getDateWeek(n.vst_movie_order_date+'', 'yyyyMMdd')+"</td>";
			if(typeof(n.vst_movie_order_special_type)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${specialTypes}', n.vst_movie_order_special_type)+"</td>";
			if(typeof(n.vst_movie_order_movie_classify)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${classifys}', n.vst_movie_order_movie_classify)+"</td>";
			if(typeof(n.vst_movie_order_movie_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_movie_order_movie_name+"</td>";
			if(typeof(n.vst_movie_order_entry1_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_movie_order_entry1_name+"</td>";
			if(typeof(n.vst_movie_order_entry2_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_movie_order_entry2_name+"</td>";
			if(typeof(n.vst_movie_order_entry3_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_movie_order_entry3_name+"</td>";
			
			//预约量
			if(typeof(n.vst_movie_order_amount)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_movie_order_amount)+" | ";
			if(typeof(n.vst_movie_order_amount_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+amountDodColor+"'>"+n.vst_movie_order_amount_dod+"</span> | ";
			if(typeof(n.vst_movie_order_amount_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+amountWowColor+"'>"+n.vst_movie_order_amount_wow+"</span></td>";
			//预约独立用户
			if(typeof(n.vst_movie_order_uv)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_movie_order_uv)+" | ";
			if(typeof(n.vst_movie_order_uv_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+uvDodColor+"'>"+n.vst_movie_order_uv_dod+"</span> | ";
			if(typeof(n.vst_movie_order_uv_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+uvWowColor+"'>"+n.vst_movie_order_uv_wow+"</span></td>";
			//人均
			if(typeof(n.vst_movie_pc_order_amount)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_pc_order_amount)+"</td>";
			
			if(typeof(n.vst_movie_order_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatDate(n.vst_movie_order_uptime, "yyyy-MM-dd HH:mm:ss")+"</td>";
			if(typeof(n.vst_movie_order_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_movie_order_operator+"</td>";

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
			params += "&vst_movie_order_special_type=" + $("#vst_movie_order_special_type").val();
			params += "&vst_movie_order_movie_classify=" + $("#vst_movie_order_movie_classify").val();
			params += "&vst_movie_order_movie_name=" + $("#vst_movie_order_movie_name").val();
			params += "&vst_movie_order_entry1_name=" + $("#vst_movie_order_entry1_name").val();
			params += "&vst_movie_order_entry2_name=" + $("#vst_movie_order_entry2_name").val();
			params += "&vst_movie_order_entry3_name=" + $("#vst_movie_order_entry3_name").val();
			
			$.ajax({
				type: "POST",
				data: params,
				async : true,
				cache : false,
				url:"${ctx}/report/json?code=movie_order_export",
				dataType:"json",
				success:function(msg){
					if(msg.code == 100){
						location.href= "${ctx}/movieOrder/exportData.action?data="+JSON.stringify(msg.data)
								+"&moduleId="+$("#moduleId").val()+"&startDay=" + startDay + "&endDay=" + endDay;
					}else{
						alert(msg.msg);
					}
				},
				error:function(msg){
					alert(msg);
				}
			});
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
				<form action="${ctx}/movieOrder/findMovieOrder.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 影片统计 > 影片预约统计</span>
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
												<select class="list-input1" id="vst_movie_order_special_type" name="vst_movie_order_special_type">
													<option value="">请选择</option>
													<c:forEach items="${specialTypes}" var="specialType">
														<option value="${specialType.key }">${specialType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">分类：</span>
												<select class="list-input1" id="vst_movie_order_movie_classify" name="vst_movie_order_movie_classify">
													<option value="">请选择</option>
													<c:forEach items="${classifys}" var="classify">
														<option value="${classify.key }">${classify.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">影片名称：</span>
												<input class="list-input1" type="text" id="vst_movie_order_movie_name" name="vst_movie_order_movie_name" />
											</li>
											<li>
												<span class="sertitle">一级入口：</span>
												<input class="list-input1" type="text" id="vst_movie_order_entry1_name" name="vst_movie_order_entry1_name" />
											</li>
											<li>
												<span class="sertitle">二级入口：</span>
												<input class="list-input1" type="text" id="vst_movie_order_entry2_name" name="vst_movie_order_entry2_name" />
											</li>
											<li>
												<span class="sertitle">三级入口：</span>
												<input class="list-input1" type="text" id="vst_movie_order_entry3_name" name="vst_movie_order_entry3_name" />
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
</html>