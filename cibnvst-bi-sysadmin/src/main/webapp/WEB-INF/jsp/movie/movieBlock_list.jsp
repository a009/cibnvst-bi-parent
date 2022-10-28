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
								<i class="fa fa-home"></i><span style="font-size:14px;">运营统计 > 影片区块播放 > 导出</span>
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
				<form action="${ctx}/movieBlock/findMovieBlock.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 运营统计 > 影片区块播放
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
												<span class="sertitle">类型：</span>
												<select class="list-input1" id="vst_mb_type" name="vst_mb_type">
													<option value="">请选择</option>
													<c:forEach items="${types}" var="type">
														<option value="${type.key }">${type.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">区块ID：</span>
												<input class="list-input1" type="text" id="vst_mb_block" name="vst_mb_block" />
											</li>
											<li>
												<span class="sertitle">位置编号：</span>
												<input class="list-input1" type="text" id="vst_mb_index" name="vst_mb_index" />
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
			                <!-- BAR CHART -->
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
		// 进入页面时，自动查询
		button_getdata();
		// 切换包名，调用查询
        $(".pkgName-menu>li").click(function () {
        	//button_getdata();
        	$("#recordPkg").val($("#pkgName").val());
        	$("#listForm").submit();
        });
		//文本框，按回车触发查询
		$("#vst_mb_block").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_mb_index").keydown(function(event){
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
		params += "&vst_mb_type=" + $("#vst_mb_type").val();
		// 参数特殊化处理
		params += "&" + paramFormat("vst_mb_block", $("#vst_mb_block").val());
		params += "&vst_mb_index=" + $("#vst_mb_index").val();

		var ajaxUrl;
		if($("#vst_mb_block").val() == ''){
			ajaxUrl = "${ctx}/report/json?code=movie_block_barByBlock";
		}else{
			ajaxUrl = "${ctx}/report/json?code=movie_block_barByIndex";
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
					bar(msg);
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
			xaxis[i] = n["value"];
			series[i] = n.vst_mb_vv;
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
		            name: '播放量',
		            data: series
		        }
	        ]
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
		params += "&vst_mb_type=" + $("#vst_mb_type").val();
		// 参数特殊化处理
		params += "&" + paramFormat("vst_mb_block", $("#vst_mb_block").val());
		params += "&vst_mb_index=" + $("#vst_mb_index").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_block_table",
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
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_date' id='vst_mb_date2' order='desc'>日期</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_type' id='vst_mb_type2' order='desc' style='color:orange;'>类型</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_block' id='vst_mb_block2' order='desc'>区块ID</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_index' id='vst_mb_index2' order='desc'>位置编号</a></th>";

		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_mb_vv' id='vst_mb_vv2' order='desc'>播放量</a> | <a onclick='changeOrder(this)' orderBy='vst_mb_vv_dod' id='vst_mb_vv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_mb_vv_wow' id='vst_mb_vv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_mb_uv' id='vst_mb_uv2' order='desc'>独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_mb_uv_dod' id='vst_mb_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_mb_uv_wow' id='vst_mb_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_avg' id='vst_mb_avg2' order='desc'>人均播放量</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_playtime' id='vst_mb_playtime2' order='desc'>播放时长(小时)</a></th>";
		table += "<th width='8%'><a onclick='changeOrder(this)' orderBy='vst_mb_playtime_avg' id='vst_mb_playtime_avg2' order='desc'>人均播放时长(分钟)</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var vvDodColor = "green";
			var vvWowColor = "green";
			var uvDodColor = "green";
			var uvWowColor = "green";
			
			if(parseFloat(n.vst_mb_vv_dod)<0){
				vvDodColor = "red";
			}
			if(parseFloat(n.vst_mb_vv_wow)<0){
				vvWowColor = "red";
			}
			if(parseFloat(n.vst_mb_uv_dod)<0){
				uvDodColor = "red";
			}
			if(parseFloat(n.vst_mb_uv_wow)<0){
				uvWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td>"+getDateWeek(n.vst_mb_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_mb_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_mb_creator)
						+"<br>修改时间："+formatDate(n.vst_mb_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_mb_updator)
						+"<br>备注："+toString(n.vst_mb_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${types}', n.vst_mb_type)+"</td>";
			table += "<td>"+toString(n.vst_mb_block)+"</td>";
			table += "<td>"+toString(n.vst_mb_index)+"</td>";
			//播放量
			table += "<td>"+formatNumber(n.vst_mb_vv)+" | ";
			table += "<span class='"+vvDodColor+"'>"+toString(n.vst_mb_vv_dod)+"</span> | ";
			table += "<span class='"+vvWowColor+"'>"+toString(n.vst_mb_vv_wow)+"</span></td>";
			//独立用户
			table += "<td>"+formatNumber(n.vst_mb_uv)+" | ";
			table += "<span class='"+uvDodColor+"'>"+toString(n.vst_mb_uv_dod)+"</span> | ";
			table += "<span class='"+uvWowColor+"'>"+toString(n.vst_mb_uv_wow)+"</span></td>";
			//人均播放量
			table += "<td>"+toString(n.vst_mb_avg)+"</td>";
			//播放时长
			table += "<td>"+toHour(n.vst_mb_playtime*1000)+"</td>";
			table += "<td>"+toMinute(n.vst_mb_playtime_avg*1000)+"</td>";
			
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
					params += "&vst_mb_type=" + $("#vst_mb_type").val();
					params += "&vst_mb_block=" + $("#vst_mb_block").val();
					params += "&vst_mb_index=" + $("#vst_mb_index").val();
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					params += "&moduleId=" + $("#moduleId").val();
					
					location.href = "${ctx}/movieBlock/exportData.action?" + params;
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