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
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">

			<!-- 批量修改弹窗 -->
		    <div class="modal fade" id="batchUpdate_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		        <div class="modal-dialog modalGrap">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                    	&times;
		                  	</button>
		                  	<h4 class="modal-title">
		                     	<i class="fa fa-home"></i><a href="#">市场数据</a><span>
									> 二次活跃渠道用户(对内) > 批量修改</span>
								</section>
		                  	</h4>
		                </div>
		                <form id="batchUpdateBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
							 		<td class="tright" width="20%">调整系数：</td>
							 		<td width="50%">
							 			<input class="list-input" id="vst_usacd_modulus1" name="vst_usacd_modulus" value="0.35" />
							 			<span class="red">*</span>
							 		</td>
							 		<td width="28%"><span id="vst_usacd_modulus1Tip"></span></td>
							 	</tr>
							</table>
							<div style="padding-bottom:10px; margin-right:10px; text-align:right;">
						    	<input type="submit" class="btnSubmit" id="batchUpdateSubmit" value="提交" />
							</div>
						</form>
		            </div>
		        </div>
		    </div>
		    <!-- 批量修改弹窗 -->
			
			<div class="content-roll">
				<form action="${ctx}/innerUserSecondaryActiveChannelDiff/findInnerUserSecondaryActiveChannelDiff.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 市场数据 > 二次活跃渠道用户(对内)统计
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
												<input class="list-input1" type="text" id="vst_usacd_channel" name="vst_usacd_channel" />
											</li>
											<li>
												<span class="sertitle">修改人：</span>
												<input class="list-input1" type="text" id="vst_usacd_updator" name="vst_usacd_updator" />
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
		// 切换包名，调用查询
        $(".pkgName-menu>li").click(function () {
        	//button_getdata();
        	$("#recordPkg").val($("#pkgName").val());
        	$("#listForm").submit();
        });
		//文本框，按回车触发查询
		$("#vst_usacd_channel").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_usacd_updator").keydown(function(event){
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
			ajaxGetReport();// 按日期统计(折线图)
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	/****************************批量修改******************************/
	// 批量修改
	function button_batchUpdate(){
		var modifyChecked="";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() +",";
		});
		modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked == ""){
	       	alert("您还没有选择记录！");
			return false;
	    }
		$("#recordId").val(modifyChecked);
		// 打开弹窗
		$("#batchUpdate_myModal").attr("class","modal fade in");
		$("#batchUpdate_myModal").attr("aria-hidden","false");
		$("#batchUpdate_myModal").show();
		$("#batchUpdateBoxForm")[0].reset();//重置
		$("#batchUpdateSubmit").removeAttr("disabled");//解除提交按钮禁用状态
	}
	
	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup : "1",
	  		formid : "batchUpdateBoxForm",
	  		wideword : false,
	  		onerror : function(msg) {},
			onsuccess : doSubmit
		});
		
		$("#vst_usacd_modulus1").formValidator({
			validatorgroup : "1",
	  		onshow : "请填写调整系数！",
	  		onfocus : "调整系数不能为空，必须为数字！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			max : 10,
			onerror : "调整系数不能为空，且不能超过10个字符！"
		}).functionValidator({
			fun :function(){
				var patrn = /^\d+(\.\d{1,2})?$/;
	            if(!patrn.exec($("#vst_usacd_modulus1").val())){
		            return false;
	            }
	            return true;
			},
			onerror :"格式错误！必须是数字，最多保留两位小数！"
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#batchUpdate_myModal").hide();
		});
		
		$("#batchUpdateSubmit").click(function(){
			var flag = $.formValidator.pageIsValid("1");// 触发全局校验
			if(flag){
				var modulus = $("#vst_usacd_modulus1").val();
				var recordId = $("#recordId").val();
				var moduleId = $("#moduleId").val();
				$.ajax({
					url : "${ctx}/innerUserSecondaryActiveChannelDiff/ajaxBatchUpdate.action",
					type : "POST",
					dataType : "json",
					cache : false,
					data : "recordId="+recordId+"&modulus="+modulus+"&moduleId="+moduleId,
					success : function(data){
						if(data.code == 100){
							// 关闭弹窗
							$("#batchUpdate_myModal").hide();
							alert("操作成功！点击查询才能看到最新的操作结果哟！");
						}else{
							alert("操作失败！请重试，若还是不成功，请联系VST后台管理人员！");
						}
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
		$("#batchUpdateSubmit").attr("disabled", "disabled");
		return true;
	}
	/****************************批量修改******************************/

	// 按日期统计(折线图)
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
		params += "&vst_usacd_channel=" + $("#vst_usacd_channel").val();
		params += "&vst_usacd_updator=" + $("#vst_usacd_updator").val();
		
		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/innerUserSecondaryActiveChannelDiff/ajaxGetReportByDate.action",
			dataType : "json",
			success : function(msg){
				$("#loading_line").attr("style","display:none");
				if(msg.result == "success"){
					line(msg);
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
		var vst_usacd_new_amount = [];

		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_usacd_new_amount[i] = n.vst_usacd_new_amount;
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
				name : '新二次活跃用户数',
				data : vst_usacd_new_amount,
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
		params += "&vst_usacd_channel=" + $("#vst_usacd_channel").val();
		params += "&vst_usacd_updator=" + $("#vst_usacd_updator").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/innerUserSecondaryActiveChannelDiff/ajaxGetCutPage.action",
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
		var totalCount = msg.totalSize;
		var singleCount = msg.singleSize;
		$("#currPage").val(msg.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='5%'><input type='checkbox' name='chkall' id='chkall' onclick='checkAll()' />全选</th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_date' id='vst_usacd_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_channel' id='vst_usacd_channel2' order='desc' style='color:orange;'>渠道</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_uv' id='vst_usacd_uv2' order='desc'>当日新增用户</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_amount' id='vst_usacd_amount2' order='desc'>二次活跃用户</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_umeng_uv' id='vst_usacd_umeng_uv2' order='desc'>友盟新增用户</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_modulus' id='vst_usacd_modulus2' order='desc'>调整系数</a></th>";
		table += "<th width='10%' title='新二活 = 友盟新增 * vst二活 / vst新增 * 系数'>新二次活跃用户</th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_dod' id='vst_usacd_dod2' order='desc'>天环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_wow' id='vst_usacd_wow2' order='desc'>周环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_usacd_mom' id='vst_usacd_mom2' order='desc'>月环比</a></th>";

		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var dodColor = "green";
			var wowColor = "green";
			var momColor = "green";
			
			if(parseFloat(n.vst_usacd_dod)<0){
				dodColor = "red";
			}
			if(parseFloat(n.vst_usacd_wow)<0){
				wowColor = "red";
			}
			if(parseFloat(n.vst_usacd_mom)<0){
				momColor = "red";
			}

			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td><input type='checkbox' id='recordIds' name='recordIds' "
							+ "value='" + n.vst_usacd_id + "' "
							+ "modulus='" + n.vst_usacd_modulus + "' "
							+ " /></td>";
			table += "<td>"+getDateWeek(n.vst_usacd_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='新增时间："+toString(n.vst_usacd_addtime)
						+"<br>创建人："+toString(n.vst_usacd_creator)
						+"<br>修改时间："+toString(n.vst_usacd_uptime)
						+"<br>修改人："+toString(n.vst_usacd_updator)
						+"<br>备注："+toString(n.vst_usacd_summary)+"' class='enlargeTextTitle'>"
					+toString(n.vst_usacd_channel)+"</td>";
			table += "<td>"+formatNumber(n.vst_usacd_uv)+"</td>";
			table += "<td>"+formatNumber(n.vst_usacd_amount)+"</td>";
			table += "<td>"+formatNumber(n.vst_usacd_umeng_uv)+"</td>";
			table += "<td>"+toString(n.vst_usacd_modulus)+"</td>";
			table += "<td>"+formatNumber(n.vst_usacd_new_amount)+"</td>";
			table += "<td class='"+dodColor+"'>"+toString(n.vst_usacd_dod)+"</td>";
			table += "<td class='"+wowColor+"'>"+toString(n.vst_usacd_wow)+"</td>";
			table += "<td class='"+momColor+"'>"+toString(n.vst_usacd_mom)+"</td>";

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
			params += "&vst_usacd_channel=" + $("#vst_usacd_channel").val();
			params += "&vst_usacd_updator=" + $("#vst_usacd_updator").val();
			
			location.href = "${ctx}/innerUserSecondaryActiveChannelDiff/exportData.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
		}
	}
</script>
</html>