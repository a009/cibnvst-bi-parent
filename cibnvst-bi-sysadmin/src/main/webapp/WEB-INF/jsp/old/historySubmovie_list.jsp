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
		$("#vst_history_submovie_movie_name").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_history_submovie_name").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
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
		params += "&vst_history_submovie_special_type=" + $("#vst_history_submovie_special_type").val();
		params += "&vst_history_submovie_movie_classify=" + $("#vst_history_submovie_movie_classify").val();
		params += "&vst_history_submovie_movie_name=" + $("#vst_history_submovie_movie_name").val();
		params += "&vst_history_submovie_name=" + $("#vst_history_submovie_name").val();

		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=history_submovie_line",
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
		var vst_history_submovie_click_amount = [];
		var vst_history_submovie_play_amount = [];
		var vst_history_submovie_play_duration = [];
		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_history_submovie_click_amount[i] = n.vst_history_submovie_click_amount;
			vst_history_submovie_play_amount[i] = n.vst_history_submovie_play_amount;
			vst_history_submovie_play_duration[i] = toHour(n.vst_history_submovie_play_duration);
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
				name : '总点击量',
				data : vst_history_submovie_click_amount,
			},
			{
				name : '总播放量',
				data : vst_history_submovie_play_amount,
			},
			{
				name : '总播放时长(小时)',
				data : vst_history_submovie_play_duration,
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
		params += "&vst_history_submovie_special_type=" + $("#vst_history_submovie_special_type").val();
		params += "&vst_history_submovie_movie_classify=" + $("#vst_history_submovie_movie_classify").val();
		params += "&vst_history_submovie_movie_name=" + $("#vst_history_submovie_movie_name").val();
		params += "&vst_history_submovie_name=" + $("#vst_history_submovie_name").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=history_submovie_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_date' id='vst_history_submovie_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_special_type' id='vst_history_submovie_special_type2' order='desc'>专区类型</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_movie_classify' id='vst_history_submovie_movie_classify2' order='desc'>分类</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_movie_name' id='vst_history_submovie_movie_name2' order='desc'>影片名称</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_name' id='vst_history_submovie_name2' order='desc'>选集名称</a></th>";
		
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_click_amount' id='vst_history_submovie_click_amount2' order='desc'>点击量</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_click_amount_dod' id='vst_history_submovie_click_amount_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_click_amount_wow' id='vst_history_submovie_click_amount_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_amount' id='vst_history_submovie_play_amount2' order='desc'>播放量</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_amount_dod' id='vst_history_submovie_play_amount_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_amount_wow' id='vst_history_submovie_play_amount_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_click_uv' id='vst_history_submovie_click_uv2' order='desc'>点击独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_click_uv_dod' id='vst_history_submovie_click_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_click_uv_wow' id='vst_history_submovie_click_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_uv' id='vst_history_submovie_play_uv2' order='desc'>播放独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_uv_dod' id='vst_history_submovie_play_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_uv_wow' id='vst_history_submovie_play_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_duration' id='vst_history_submovie_play_duration2' order='desc'>播放时长(小时)</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_duration_dod' id='vst_history_submovie_play_duration_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_duration_wow' id='vst_history_submovie_play_duration_wow2' order='desc'>周环比</a></th>";

		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_pc_click_amount' id='vst_history_submovie_pc_click_amount2' order='desc'>人均点击量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_pc_play_amount' id='vst_history_submovie_pc_play_amount2' order='desc'>人均播放量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_pc_play_duration' id='vst_history_submovie_pc_play_duration2' order='desc'>人均播放时长(分钟)</a></th>";
		
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_0s' id='vst_history_submovie_play_time_0s2' order='desc'>[0s,10s)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_10s' id='vst_history_submovie_play_time_10s2' order='desc'>[10s,30s)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_30s' id='vst_history_submovie_play_time_30s2' order='desc'>[30s,60s)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_1m' id='vst_history_submovie_play_time_1m2' order='desc'>[1m,30m)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_30m' id='vst_history_submovie_play_time_30m2' order='desc'>[30m,60m)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_1h' id='vst_history_submovie_play_time_1h2' order='desc'>[1h,2h)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_play_time_2h' id='vst_history_submovie_play_time_2h2' order='desc'>[2h,+)/次</a></th>";
		
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_uptime' id='vst_history_submovie_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_history_submovie_operator' id='vst_history_submovie_operator2' order='desc'>操作人</a></th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var clickAmountDodColor = "green";
			var clickAmountWowColor = "green";
			var playAmountDodColor = "green";
			var playAmountWowColor = "green";
			var clickUvDodColor = "green";
			var clickUvWowColor = "green";
			var playUvDodColor = "green";
			var playUvWowColor = "green";
			var playDurationDodColor = "green";
			var playDurationWowColor = "green";
			
			if(parseFloat(n.vst_history_submovie_click_amount_dod)<0){
				clickAmountDodColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_click_amount_wow)<0){
				clickAmountWowColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_play_amount_dod)<0){
				playAmountDodColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_play_amount_wow)<0){
				playAmountWowColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_click_uv_dod)<0){
				clickUvDodColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_click_uv_wow)<0){
				clickUvWowColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_play_uv_dod)<0){
				playUvDodColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_play_uv_wow)<0){
				playUvWowColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_play_duration_dod)<0){
				playDurationDodColor = "red";
			}
			if(parseFloat(n.vst_history_submovie_play_duration_wow)<0){
				playDurationWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_history_submovie_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getDateWeek(n.vst_history_submovie_date+'', 'yyyyMMdd')+"</td>";
			if(typeof(n.vst_history_submovie_special_type)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${specialTypes}', n.vst_history_submovie_special_type)+"</td>";
			if(typeof(n.vst_history_submovie_movie_classify)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${classifys}', n.vst_history_submovie_movie_classify)+"</td>";
			if(typeof(n.vst_history_submovie_movie_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_history_submovie_movie_name+"</td>";
			if(typeof(n.vst_history_submovie_name)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_history_submovie_name+"</td>";
			
			//点击量
			if(typeof(n.vst_history_submovie_click_amount)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_click_amount)+" | ";
			if(typeof(n.vst_history_submovie_click_amount_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+clickAmountDodColor+"'>"+n.vst_history_submovie_click_amount_dod+"</span> | ";
			if(typeof(n.vst_history_submovie_click_amount_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+clickAmountWowColor+"'>"+n.vst_history_submovie_click_amount_wow+"</span></td>";
			//播放量
			if(typeof(n.vst_history_submovie_play_amount)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_amount)+" | ";
			if(typeof(n.vst_history_submovie_play_amount_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+playAmountDodColor+"'>"+n.vst_history_submovie_play_amount_dod+"</span> | ";
			if(typeof(n.vst_history_submovie_play_amount_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+playAmountWowColor+"'>"+n.vst_history_submovie_play_amount_wow+"</span></td>";
			//点击独立用户
			if(typeof(n.vst_history_submovie_click_uv)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_click_uv)+" | ";
			if(typeof(n.vst_history_submovie_click_uv_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+clickUvDodColor+"'>"+n.vst_history_submovie_click_uv_dod+"</span> | ";
			if(typeof(n.vst_history_submovie_click_uv_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+clickUvWowColor+"'>"+n.vst_history_submovie_click_uv_wow+"</span></td>";
			//播放独立用户
			if(typeof(n.vst_history_submovie_play_uv)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_uv)+" | ";
			if(typeof(n.vst_history_submovie_play_uv_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+playUvDodColor+"'>"+n.vst_history_submovie_play_uv_dod+"</span> | ";
			if(typeof(n.vst_history_submovie_play_uv_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+playUvWowColor+"'>"+n.vst_history_submovie_play_uv_wow+"</span></td>";
			//播放时长
			if(typeof(n.vst_history_submovie_play_duration)=="undefined")	table += "<td> | ";
			else	table += "<td>"+toHour(n.vst_history_submovie_play_duration)+" | ";
			if(typeof(n.vst_history_submovie_play_duration_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+playDurationDodColor+"'>"+n.vst_history_submovie_play_duration_dod+"</span> | ";
			if(typeof(n.vst_history_submovie_play_duration_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+playDurationWowColor+"'>"+n.vst_history_submovie_play_duration_wow+"</span></td>";
			//人均
			if(typeof(n.vst_history_submovie_pc_click_amount)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_pc_click_amount)+"</td>";
			if(typeof(n.vst_history_submovie_pc_play_amount)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_pc_play_amount)+"</td>";
			if(typeof(n.vst_history_submovie_pc_play_duration)=="undefined")	table += "<td></td>";
			else	table += "<td>"+toMinute(n.vst_history_submovie_pc_play_duration)+"</td>";
			//时间区间
			if(typeof(n.vst_history_submovie_play_time_0s)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_0s)+"</td>";
			if(typeof(n.vst_history_submovie_play_time_10s)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_10s)+"</td>";
			if(typeof(n.vst_history_submovie_play_time_30s)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_30s)+"</td>";
			if(typeof(n.vst_history_submovie_play_time_1m)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_1m)+"</td>";
			if(typeof(n.vst_history_submovie_play_time_30m)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_30m)+"</td>";
			if(typeof(n.vst_history_submovie_play_time_1h)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_1h)+"</td>";
			if(typeof(n.vst_history_submovie_play_time_2h)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_history_submovie_play_time_2h)+"</td>";
			
			if(typeof(n.vst_history_submovie_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatDate(n.vst_history_submovie_uptime, "yyyy-MM-dd HH:mm:ss")+"</td>";
			if(typeof(n.vst_history_submovie_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_history_submovie_operator+"</td>";

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
			params += "&vst_history_submovie_special_type=" + $("#vst_history_submovie_special_type").val();
			params += "&vst_history_submovie_movie_classify=" + $("#vst_history_submovie_movie_classify").val();
			params += "&vst_history_submovie_movie_name=" + $("#vst_history_submovie_movie_name").val();
			params += "&vst_history_submovie_name=" + $("#vst_history_submovie_name").val();
			
			$.ajax({
				type: "POST",
				data: params,
				async : true,
				cache : false,
				url:"${ctx}/report/json?code=history_submovie_export",
				dataType:"json",
				success:function(msg){
					if(msg.code == 100){
						location.href= "${ctx}/historySubmovie/exportData.action?data="+JSON.stringify(msg.data)
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
				<form action="${ctx}/historySubmovie/findHistorySubmovie.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 历史统计 > 选集统计</span>
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
												<select class="list-input1" id="vst_history_submovie_special_type" name="vst_history_submovie_special_type">
													<option value="">请选择</option>
													<c:forEach items="${specialTypes}" var="specialType">
														<option value="${specialType.key }">${specialType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">分类：</span>
												<select class="list-input1" id="vst_history_submovie_movie_classify" name="vst_history_submovie_movie_classify">
													<option value="">请选择</option>
													<c:forEach items="${classifys}" var="classify">
														<option value="${classify.key }">${classify.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">影片名称：</span>
												<input class="list-input1" type="text" id="vst_history_submovie_movie_name" name="vst_history_submovie_movie_name" />
											</li>
											<li>
												<span class="sertitle">选集名称：</span>
												<input class="list-input1" type="text" id="vst_history_submovie_name" name="vst_history_submovie_name" />
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