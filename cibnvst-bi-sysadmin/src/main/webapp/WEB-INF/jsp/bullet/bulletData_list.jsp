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
								<i class="fa fa-home"></i><span style="font-size: 14px;">产品监控 > 弹窗统计 > 导出</span>
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
		    
		    <!-- 导入弹窗 -->
			<div class="modal fade" id="import_myModal" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document" style="margin: 100px auto;">
					<div class="modal-content">
						<div class="modal-header">
							<div class="close" data-dismiss="modal" aria-label="Close" style="margin-top: 0; overflow: hidden;">
								<span aria-hidden="true" style="display: block; line-height: 20px; font-size: 40px;">&times;</span>
							</div>
							<h4 class="modal-title">
								<i class="fa fa-home"></i><span style="font-size: 14px;">产品监控 > 弹窗统计 > 导入</span>
							</h4>
						</div>
						<div class="modal-body" style="padding: 0 15px; max-height: 450px; overflow: auto;">
							<form id="importBoxForm" method="post" enctype="multipart/form-data">
								<table class="line03">
									<tr>
										<td class="tright" width="20%">
											类型<span class="red">*</span>：
										</td>
										<td width="50%">
											<select class="list-input" id="import_type" name="import_type">
												<option value="">请选择</option>
												<c:forEach items="${types}" var="type">
													<option value="${type.key }">
														${type.value }
													</option>
												</c:forEach>
											</select>
										</td>
										<td width="28%">
											<span id="import_typeTip"></span>
										</td>
									</tr>
									<tr>
										<td class="tright">
											操作方式<span class="red">*</span>：
										</td>
										<td>
											<select class="list-input" id="import_way" name="import_way">
												<option value="1">直接新增</option>
												<option value="2">删除后新增</option>
											</select>
										</td>
										<td>
											<span id="import_wayTip"></span>
										</td>
									</tr>
									<tr>
										<td class="tright">
											json数据<span class="red">*</span>：
										</td>
										<td>
											<textarea class="list-input" id="import_json" name="import_json" style="height: 100px;"></textarea>
										</td>
										<td>
											<span id="import_jsonTip"></span>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="modal-footer" style="position: relative; bottom: 0; right: 0;">
							<input type="submit" class="btnSubmit" id="importSubmit" value="导入" />
						</div>
					</div>
				</div>
			</div>
			<!-- 导入弹窗 -->
			
			<div class="content-roll">
				<form action="${ctx}/bulletData/findBulletData.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 产品监控 > 弹窗统计
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
												<select class="list-input1" id="vst_bd_type" name="vst_bd_type">
													<option value="">请选择</option>
													<c:forEach items="${types}" var="type">
														<option value="${type.key }">${type.value }</option>
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
					
					<!-- 表格数据 -->
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
			                <div class="box box-primary">
			                    <div class="box-body chart-responsive">
			                    <div class="clearfix">
			                    	<ul class="contlist" style="cursor: pointer; float:left;">
										<c:forEach items="${buttonList}" var="button">
											<li class="action">
												<img src="${ctx}${button.vst_button_img}" width="14" height="14" alt="${button.vst_button_summary }">
												<a onclick="${button.vst_button_onclick}">${button.vst_button_name }</a>
											</li>
										</c:forEach>
										<i name="showTitle" class="fa fa-question-circle  enlargeTextTitle" data-title="" style="height: 30px;line-height: 30px;font-size: 16px;"></i>
									</ul>
									<ul class="nav nav-pills" role="tablist" style="float:right;">
										<li role="presentation" style="float: right;"><a href="#tab-move4" aria-controls="home4" role="tab" data-toggle="tab" id="tab-name4">连续留存率</a></li>
										<li role="presentation" style="float: right;"><a href="#tab-move3" aria-controls="home3" role="tab" data-toggle="tab" id="tab-name3">连续留存</a></li>
										<li role="presentation" style="float: right;"><a href="#tab-move2" aria-controls="home2" role="tab" data-toggle="tab" id="tab-name2">留存率</a></li>
										<li role="presentation" class="active" style="float: right;"><a href="#tab-move1" aria-controls="home1" role="tab" data-toggle="tab" id="tab-name1">留存</a></li>
									</ul>
			                    </div>
			                    	
									<div class="tab-content" style="width: 100%;">
										<div id="move_loading" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
							          	<!-- MOVE1 -->
										<div role="tabpanel" class="tab-pane active" id="tab-move1" style="width: 100%;">
							          		<div id="move1">
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
										<!-- MOVE2 -->
										<div role="tabpanel" class="tab-pane" id="tab-move2" style="width: 100%;">
											<div id="move2">
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
										<!-- MOVE3 -->
										<div role="tabpanel" class="tab-pane" id="tab-move3" style="width: 100%;">
							          		<div id="move3">
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
										<!-- MOVE4 -->
										<div role="tabpanel" class="tab-pane" id="tab-move4" style="width: 100%;">
							          		<div id="move4">
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
										<%@include file="../share/reportCutPage.jsp" %>
									</div>
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
		var startTime = moment(getNDateTime("yyyy-MM-dd", 8)).valueOf();
		var endTime = moment(getNDateTime("yyyy-MM-dd", 2)).valueOf();
        $.initDaterangepicker({startTime:startTime, endTime:endTime});
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
			if($("#vst_bd_type").val() == '3'){
				$("#tab-name1").html("流失");
				$("#tab-name2").html("流失率");
				$("#tab-name3").html("连续流失");
				$("#tab-name4").html("连续流失率");
			}else{
				$("#tab-name1").html("留存");
				$("#tab-name2").html("留存率");
				$("#tab-name3").html("连续留存");
				$("#tab-name4").html("连续留存率");
			}
			//当前页置1
			$("#currPage").val(1);
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}
	
	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move1").empty();
		$("#move2").empty();
		$("#move3").empty();
		$("#move4").empty();
		
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
		params += "&vst_bd_type=" + $("#vst_bd_type").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=bullet_data_table",
			dataType:"json",
			success:function(msg){
				$("#move_loading").attr("style","display:none");
				if(msg.code == 100){
					showTable1(msg);
					showTable2(msg);
					showTable3(msg);
					showTable4(msg);
				}else{
					$("#move1").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#move2").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#move3").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#move4").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					showPageInfo(0, $("#singleCount").val());
				}
			},
			error:function(msg){
				$("#move_loading").attr("style","display:none");
			}
		});
	}

	function showTable1(msg){
		var data = msg.data;
		var totalCount = msg.info.totalCount;
		var singleCount = msg.info.singleCount;
		$("#currPage").val(msg.info.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='1%'>选择</th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_date' id='vst_bd_date2' order='desc'>日期</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_type' id='vst_bd_type2' order='desc' style='color:orange;'>类型</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_total' id='vst_bd_total2' order='desc'>点击用户数</a></th>";
		if($("#vst_bd_type").val() == '3'){
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_one' id='vst_bd_one2' order='desc'>1日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_two' id='vst_bd_two2' order='desc'>2日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_three' id='vst_bd_three2' order='desc'>3日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_four' id='vst_bd_four2' order='desc'>4日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_five' id='vst_bd_five2' order='desc'>5日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_six' id='vst_bd_six2' order='desc'>6日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_seven' id='vst_bd_seven2' order='desc'>7日流失</a></th>";
		}else{
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_one' id='vst_bd_one2' order='desc'>1日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_two' id='vst_bd_two2' order='desc'>2日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_three' id='vst_bd_three2' order='desc'>3日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_four' id='vst_bd_four2' order='desc'>4日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_five' id='vst_bd_five2' order='desc'>5日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_six' id='vst_bd_six2' order='desc'>6日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_seven' id='vst_bd_seven2' order='desc'>7日留存</a></th>";
		}
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td><input type='checkbox' name='recordIds' value='"+n.vst_bd_id+"' /></td>";
			table += "<td>"+getDateWeek(n.vst_bd_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_bd_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_bd_creator)
						+"<br>修改时间："+formatDate(n.vst_bd_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_bd_updator)
						+"<br>备注："+toString(n.vst_bd_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${types}', n.vst_bd_type)+"</td>";
			// 用户数
			table += "<td>"+formatNumber(n.vst_bd_total)+"</td>";
			if(n.vst_bd_one != null && n.vst_bd_one != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_one)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_two != null && n.vst_bd_two != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_two)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_three != null && n.vst_bd_three != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_three)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_four != null && n.vst_bd_four != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_four)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_five != null && n.vst_bd_five != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_five)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_six != null && n.vst_bd_six != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_six)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_seven != null && n.vst_bd_seven != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_seven)+"</td>";
			}else{
				table += "<td></td>";
			}
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move1").html(table);
		showOrder(orderBy, order);
	}

	function showTable2(msg){
		var data = msg.data;
		var totalCount = msg.info.totalCount;
		var singleCount = msg.info.singleCount;
		$("#currPage").val(msg.info.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='1%'>选择</th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_date' id='vst_bd_date2' order='desc'>日期</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_type' id='vst_bd_type2' order='desc' style='color:orange;'>类型</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_total' id='vst_bd_total2' order='desc'>点击用户数</a></th>";
		if($("#vst_bd_type").val() == '3'){
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_one_ratio' id='vst_bd_one_ratio2' order='desc'>1日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_two_ratio' id='vst_bd_two_ratio2' order='desc'>2日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_three_ratio' id='vst_bd_three_ratio2' order='desc'>3日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_four_ratio' id='vst_bd_four_ratio2' order='desc'>4日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_five_ratio' id='vst_bd_five_ratio2' order='desc'>5日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_six_ratio' id='vst_bd_six_ratio2' order='desc'>6日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_seven_ratio' id='vst_bd_seven_ratio2' order='desc'>7日流失</a></th>";
		}else{
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_one_ratio' id='vst_bd_one_ratio2' order='desc'>1日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_two_ratio' id='vst_bd_two_ratio2' order='desc'>2日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_three_ratio' id='vst_bd_three_ratio2' order='desc'>3日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_four_ratio' id='vst_bd_four_ratio2' order='desc'>4日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_five_ratio' id='vst_bd_five_ratio2' order='desc'>5日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_six_ratio' id='vst_bd_six_ratio2' order='desc'>6日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_seven_ratio' id='vst_bd_seven_ratio2' order='desc'>7日留存</a></th>";
		}
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td><input type='checkbox' name='recordIds' value='"+n.vst_bd_id+"' /></td>";
			table += "<td>"+getDateWeek(n.vst_bd_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_bd_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_bd_creator)
						+"<br>修改时间："+formatDate(n.vst_bd_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_bd_updator)
						+"<br>备注："+toString(n.vst_bd_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${types}', n.vst_bd_type)+"</td>";
			// 用户数
			table += "<td>"+formatNumber(n.vst_bd_total)+"</td>";
			if(n.vst_bd_one_ratio != null && n.vst_bd_one_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_one_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_two_ratio != null && n.vst_bd_two_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_two_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_three_ratio != null && n.vst_bd_three_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_three_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_four_ratio != null && n.vst_bd_four_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_four_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_five_ratio != null && n.vst_bd_five_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_five_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_six_ratio != null && n.vst_bd_six_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_six_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_seven_ratio != null && n.vst_bd_seven_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_seven_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move2").html(table);
		showOrder(orderBy, order);
	}

	function showTable3(msg){
		var data = msg.data;
		var totalCount = msg.info.totalCount;
		var singleCount = msg.info.singleCount;
		$("#currPage").val(msg.info.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='1%'>选择</th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_date' id='vst_bd_date2' order='desc'>日期</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_type' id='vst_bd_type2' order='desc' style='color:orange;'>类型</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_total' id='vst_bd_total2' order='desc'>点击用户数</a></th>";
		if($("#vst_bd_type").val() == '3'){
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_one' id='vst_bd_con_one2' order='desc'>1日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_two' id='vst_bd_con_two2' order='desc'>2日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_three' id='vst_bd_con_three2' order='desc'>3日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_four' id='vst_bd_con_four2' order='desc'>4日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_five' id='vst_bd_con_five2' order='desc'>5日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_six' id='vst_bd_con_six2' order='desc'>6日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_seven' id='vst_bd_con_seven2' order='desc'>7日流失</a></th>";
		}else{
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_one' id='vst_bd_con_one2' order='desc'>1日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_two' id='vst_bd_con_two2' order='desc'>2日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_three' id='vst_bd_con_three2' order='desc'>3日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_four' id='vst_bd_con_four2' order='desc'>4日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_five' id='vst_bd_con_five2' order='desc'>5日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_six' id='vst_bd_con_six2' order='desc'>6日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_seven' id='vst_bd_con_seven2' order='desc'>7日留存</a></th>";
		}
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td><input type='checkbox' name='recordIds' value='"+n.vst_bd_id+"' /></td>";
			table += "<td>"+getDateWeek(n.vst_bd_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_bd_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_bd_creator)
						+"<br>修改时间："+formatDate(n.vst_bd_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_bd_updator)
						+"<br>备注："+toString(n.vst_bd_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${types}', n.vst_bd_type)+"</td>";
			// 用户数
			table += "<td>"+formatNumber(n.vst_bd_total)+"</td>";
			if(n.vst_bd_con_one != null && n.vst_bd_con_one != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_one)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_two != null && n.vst_bd_con_two != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_two)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_three != null && n.vst_bd_con_three != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_three)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_four != null && n.vst_bd_con_four != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_four)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_five != null && n.vst_bd_con_five != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_five)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_six != null && n.vst_bd_con_six != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_six)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_seven != null && n.vst_bd_con_seven != ''){
				table += "<td style='background-color: #D0EEFF;'>"+formatNumber(n.vst_bd_con_seven)+"</td>";
			}else{
				table += "<td></td>";
			}
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move3").html(table);
		showOrder(orderBy, order);
	}

	function showTable4(msg){
		var data = msg.data;
		var totalCount = msg.info.totalCount;
		var singleCount = msg.info.singleCount;
		$("#currPage").val(msg.info.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='1%'>选择</th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_date' id='vst_bd_date2' order='desc'>日期</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_type' id='vst_bd_type2' order='desc' style='color:orange;'>类型</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_bd_total' id='vst_bd_total2' order='desc'>点击用户数</a></th>";
		if($("#vst_bd_type").val() == '3'){
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_one_ratio' id='vst_bd_con_one_ratio2' order='desc'>1日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_two_ratio' id='vst_bd_con_two_ratio2' order='desc'>2日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_three_ratio' id='vst_bd_con_three_ratio2' order='desc'>3日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_four_ratio' id='vst_bd_con_four_ratio2' order='desc'>4日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_five_ratio' id='vst_bd_con_five_ratio2' order='desc'>5日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_six_ratio' id='vst_bd_con_six_ratio2' order='desc'>6日流失</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_seven_ratio' id='vst_bd_con_seven_ratio2' order='desc'>7日流失</a></th>";
		}else{
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_one_ratio' id='vst_bd_con_one_ratio2' order='desc'>1日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_two_ratio' id='vst_bd_con_two_ratio2' order='desc'>2日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_three_ratio' id='vst_bd_con_three_ratio2' order='desc'>3日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_four_ratio' id='vst_bd_con_four_ratio2' order='desc'>4日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_five_ratio' id='vst_bd_con_five_ratio2' order='desc'>5日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_six_ratio' id='vst_bd_con_six_ratio2' order='desc'>6日留存</a></th>";
			table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_bd_con_seven_ratio' id='vst_bd_con_seven_ratio2' order='desc'>7日留存</a></th>";
		}
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td><input type='checkbox' name='recordIds' value='"+n.vst_bd_id+"' /></td>";
			table += "<td>"+getDateWeek(n.vst_bd_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_bd_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_bd_creator)
						+"<br>修改时间："+formatDate(n.vst_bd_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_bd_updator)
						+"<br>备注："+toString(n.vst_bd_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${types}', n.vst_bd_type)+"</td>";
			// 用户数
			table += "<td>"+formatNumber(n.vst_bd_total)+"</td>";
			if(n.vst_bd_con_one_ratio != null && n.vst_bd_con_one_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_one_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_two_ratio != null && n.vst_bd_con_two_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_two_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_three_ratio != null && n.vst_bd_con_three_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_three_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_four_ratio != null && n.vst_bd_con_four_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_four_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_five_ratio != null && n.vst_bd_con_five_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_five_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_six_ratio != null && n.vst_bd_con_six_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_six_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			if(n.vst_bd_con_seven_ratio != null && n.vst_bd_con_seven_ratio != ''){
				table += "<td style='background-color: #D0EEFF;'>"+toString(n.vst_bd_con_seven_ratio)+"</td>";
			}else{
				table += "<td></td>";
			}
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move4").html(table);
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
					params += "&vst_bd_type=" + $("#vst_bd_type").val();
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/bulletData/exportData.action?" + params
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
	
	/****************************导入******************************/
	// 导入数据
	function button_imp(){
		// 打开弹窗
		$("#import_myModal").attr("class","modal fade in");
		$("#import_myModal").attr("aria-hidden","false");
		$("#import_myModal").show();
		$("#importBoxForm")[0].reset();//重置
		$("#importSubmit").removeAttr("disabled");//解除提交按钮禁用状态
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"2",
	  		formid:"importBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});

  		$("#import_type").formValidator({
			validatorgroup : "2",
	  		onshow : "请选择类型！",
	  		onfocus : "类型必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "类型必须选择！"
		});

  		$("#import_way").formValidator({
			validatorgroup : "2",
	  		onshow : "请选择操作方式！",
	  		onfocus : "操作方式必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 0,
			onerror : "操作方式必须选择！"
		});

  		$("#import_json").formValidator({
			validatorgroup : "2",
	  		onshow : "请输入json数据！",
	  		onfocus : "json数据必须输入！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "json数据必须输入！"
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#import_myModal").hide();
		});
		
		$("#importSubmit").click(function(){
			var flag = $.formValidator.pageIsValid("2");// 触发全局校验
			if(flag){
				$.ajax({
					url : "${ctx}/bulletData/ajaxImportData.action",
					type : "POST",
					dataType : "json",
					cache : false,
					data : {"import_type":$("#import_type").val(), "import_way":$("#import_way").val(),
							"import_json":$("#import_json").val(), "moduleId":$("#moduleId").val()},
					success : function(data){
						if(data.code == 100){
							// 关闭弹窗
							$("#import_myModal").hide();
							alert("导入成功，成功添加"+data.result+"条数据！");
						}else{
							alert(data.msg);
						}
					}
				});
			}else{
				return false;
			}
			$("#import_myModal .modal-header .close span").click();
		});
	});
	/****************************导入******************************/
	
	/****************************删除******************************/
	function button_delete(){
		var modifyChecked="";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked == ""){
			if(confirm("您未选择，是否按照条件删除？")){
				var startDay = $("#startDay").val();
				var endDay = $("#endDay").val();
				if(startDay == '' || endDay == ''){
					alert("必选指定日期范围进行删除！");
					return false;
				}
		        var params = "startDay=" + startDay + "&endDay=" + endDay
		        		+ "&pkgName=" + $("#pkgName").val();
						+ "&vst_bd_type=" + $("#vst_bd_type").val();
	        			+ "&moduleId=" + $("#moduleId").val()
	        			+ "&deleteType=2";
		        $.ajax({
					type : "POST",
					data : params,
					async : true,
					cache : false,
					url : "${ctx}/bulletData/ajaxDeleteData.action",
					dataType : "json",
					success : function(msg){
						if(msg.code == 100){
							alert("删除数据成功"+msg.result+"条，请点击查询刷新数据！");
						}else{
							alert("删除数据失败！");
						}
					},
					error : function(msg){
						alert(msg);
					}
				});
			}
	    }else{
	    	if(confirm("是否确认删除选中的数据？")){
	    		var params = "recordIds=" + modifyChecked
		    			+ "&moduleId=" + $("#moduleId").val()
		    			+ "&deleteType=1";
	    		$.ajax({
					type : "POST",
					data : params,
					async : true,
					cache : false,
					url : "${ctx}/bulletData/ajaxDeleteData.action",
					dataType : "json",
					success : function(msg){
						if(msg.code == 100){
							alert("删除数据成功"+msg.result+"条，请点击查询刷新数据！");
						}else{
							alert("删除数据失败！");
						}
					},
					error : function(msg){
						alert(msg);
					}
				});
	    	}
	    }
	}
	/****************************删除******************************/
</script>
</html>