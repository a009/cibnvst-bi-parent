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
								<i class="fa fa-home"></i><span style="font-size:14px;">产品统计 > 渠道质量统计 > 导出</span>
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
		    
			<!--同步弹窗-->
		    <div class="modal fade" id="sync_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		        <div class="modal-dialog modalGrap">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                    	&times;
		                  	</button>
		                  	<h4 class="modal-title">
		                     	<i class="fa fa-home"></i><a href="#">产品统计</a><span>
									> 渠道质量统计 > 同步</span>
								</section>
		                  	</h4>
		                </div>
		                <form id="syncBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
							 		<td class="tright" width="20%">开始日期：</td>
							 		<td width="50%">
							 			<input type="text" class="list-input" id="startDate" name="startDate"
											placeholder="yyyy-MM-dd" readonly="readonly" />
							 		</td>
							 		<td width="28%"><span id="startDateTip"></span></td>
							 	</tr>
							 	<tr>
							 		<td class="tright">结束日期：</td>
							 		<td>
							 			<input type="text" class="list-input" id="endDate" name="endDate"
											placeholder="yyyy-MM-dd" readonly="readonly" />
							 		</td>
							 		<td><span id="endDateTip"></span></td>
							 	</tr>
							</table>
							<div style="padding-bottom:10px; margin-right:10px; text-align:right;">
								<button type="submit" class="btn btn-primary" id="syncSubmit" data-loading-text="同步中..." autocomplete="off">提交</button>
							</div>
						</form>
		            </div>
		        </div>
		    </div>
		    <!--同步弹窗-->
		    
			<div class="content-roll">
				<form action="${ctx}/channelLevel/findChannelLevel.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 产品统计 > 渠道质量统计
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
												<input class="list-input1" type="text" id="vst_cl_channel" name="vst_cl_channel" />
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
	
	<!--日历-->
	<script type="text/javascript">
		laydate.render({
		  	elem: '#startDate', //指定元素
		  	type: 'date',
		  	format: 'yyyy-MM-dd',
		  	theme: 'molv'
	  	});
	  	
		laydate.render({
		  	elem: '#endDate', //指定元素
		  	type: 'date',
		  	format: 'yyyy-MM-dd',
		  	theme: 'molv'
	  	});
	  	
	  	$("#startDate").val(getNDateTime("yyyy-MM-dd",0));
	  	$("#endDate").val(getNDateTime("yyyy-MM-dd",0));
	</script>
</body>

<script type="text/javascript">
	$(document).ready(function(){
		$.initDaterangepicker('昨日');
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
		$("#vst_cl_channel").keydown(function(event){
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
			ajaxGetCutPage();// 查询数据(显示分页信息)
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
		params += "&pkgName=" + $("#pkgName").val();
		// 参数特殊化处理
		params += "&" + paramFormat("vst_cl_channel", $("#vst_cl_channel").val());
		params += "&vst_cl_channel_not_null=notNull";

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=channel_level_table",
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
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_date' id='vst_cl_date2' order='desc'>日期</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_channel' id='vst_cl_channel2' order='desc' style='color:orange'>渠道</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_orders' id='vst_cl_orders2' order='desc'>订单数</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_orders_amount' id='vst_cl_orders_amount2' order='desc'>订单金额(分)</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_orders_price' id='vst_cl_orders_price2' order='desc'>客单价(分)</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_orders_price_avg' id='vst_cl_orders_price_avg2' order='desc'>均价(分)</a></th>";
		//用户数据
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_sum' id='vst_cl_user_sum2' order='desc'>累计</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_active' id='vst_cl_user_active2' order='desc'>活跃</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_add' id='vst_cl_user_add2' order='desc'>新增</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_add_season' id='vst_cl_user_add_season2' order='desc'>季新增</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_level_season' id='vst_cl_user_level_season2' order='desc'>季质量</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_radio' id='vst_cl_user_radio2' order='desc'>质量率</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_retain_day' id='vst_cl_user_retain_day2' order='desc'>次日留存</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_retain_week' id='vst_cl_user_retain_week2' order='desc'>周留存</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_retain_month' id='vst_cl_user_retain_month2' order='desc'>月留存</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_user_retain_season' id='vst_cl_user_retain_season2' order='desc'>季留存</a></th>";
		//播放数据
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_vv' id='vst_cl_vv2' order='desc'>播放量</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_uv' id='vst_cl_uv2' order='desc'>播放人数</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_avg' id='vst_cl_avg2' order='desc'>人均播放量</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_playtime' id='vst_cl_playtime2' order='desc'>播放时长(小时)</a></th>";
		table += "<th width='5%'><a onclick='changeOrder(this)' orderBy='vst_cl_playtime_avg' id='vst_cl_playtime_avg2' order='desc'>人均播放时长(分钟)</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			table += "<td>"+getDateWeek(n.vst_cl_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_cl_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_cl_creator)
						+"<br>修改时间："+formatDate(n.vst_cl_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_cl_updator)
						+"<br>备注："+toString(n.vst_cl_summary)+"' class='enlargeTextTitle'>"
					+toString(n.vst_cl_channel)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_orders)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_orders_amount)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_orders_price)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_orders_price_avg)+"</td>";
			//用户数据
			table += "<td>"+formatNumber(n.vst_cl_user_sum)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_active)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_add)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_add_season)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_level_season)+"</td>";
			table += "<td>"+toString(n.vst_cl_user_radio)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_retain_day)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_retain_week)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_retain_month)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_user_retain_season)+"</td>";
			//播放数据
			table += "<td>"+formatNumber(n.vst_cl_vv)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_uv)+"</td>";
			table += "<td>"+formatNumber(n.vst_cl_avg)+"</td>";
			table += "<td>"+toHour(n.vst_cl_playtime*1000)+"</td>";
			table += "<td>"+toMinute(n.vst_cl_playtime_avg*1000)+"</td>";
			
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
					params += "&vst_cl_channel=" + $("#vst_cl_channel").val();
					params += "&vst_cl_channel_not_null=notNull";
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/channelLevel/exportData.action?" + params
							+ "&moduleId=" + $("#moduleId").val();
				}
			}else{
				return false;
			}
			$("#export_myModal .modal-header .close span").click();
		});
	});
	/****************************导出******************************/

	/****************************同步******************************/
	function button_sync(){
		// 打开弹窗
		$("#sync_myModal").attr("class","modal fade in");
		$("#sync_myModal").attr("aria-hidden","false");
		$("#sync_myModal").show();
		$("#syncBoxForm")[0].reset();//重置
		$("#syncSubmit").removeAttr("disabled");//解除提交按钮禁用状态
		$("#startDate").val(getNDateTime("yyyy-MM-dd",0));
		$("#endDate").val(getNDateTime("yyyy-MM-dd",0));
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"2",
	  		formid:"syncBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
		$("#startDate").formValidator({
			validatorgroup : "2",
	  		onshow : "请选择日期！",
	  		onfocus : "开始日期必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "开始日期必须选择！"
		});
		
		$("#endDate").formValidator({
			validatorgroup : "2",
	  		onshow : "请选择日期！",
	  		onfocus : "结束日期必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "结束日期必须选择！"
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#sync_myModal").hide();
		});
		
		$("#syncSubmit").click(function(){
			if(!confirm("同步时，将会先删除已选日期的数据，然后再进行导入。是否确认？")){
				return false;
			}
			var flag = $.formValidator.pageIsValid("2");// 触发全局校验
			if(flag){
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
                var $btn = $(this).button('loading');
				$.ajax({
					url:"${ctx}/channelLevel/ajaxSyncData.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"startDate="+startDate+"&endDate="+endDate+"&moduleId="+$("#moduleId").val(),
					success:function(data){
						if(data.code == 200){
							// 关闭弹窗
							$("#sync_myModal").hide();
							alert(data.msg);
						}else{
							alert(data.msg);
						}
                        $btn.button('reset');
					}
				});
			}else{
				return false;
			}
		});
	});

	// 提交处理
	function doSubmit() {
		//提交后禁用提交按钮，避免多次提交
		$("#exportSubmit").attr("disabled", "disabled");
		$("#syncSubmit").attr("disabled", "disabled");
		return true;
	}
	/****************************同步******************************/
</script>
</html>