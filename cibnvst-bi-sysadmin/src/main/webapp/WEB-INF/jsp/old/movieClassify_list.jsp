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
			ajaxPieData();
		}
	}

	// 按日期统计(折线图)
	function ajaxGetReport(){
		$("#loading_line").removeAttr("style");
		$("#line-chart").empty();
		
		var params = "startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_movie_classify_special_type=" + $("#vst_movie_classify_special_type").val();
		params += "&vst_movie_classify=" + $("#vst_movie_classify").val();

		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_classify_line",
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
		var vst_movie_classify_click_amount = [];
		var vst_movie_classify_play_amount = [];
		var vst_movie_classify_play_duration = [];
		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_movie_classify_click_amount[i] = n.vst_movie_classify_click_amount;
			vst_movie_classify_play_amount[i] = n.vst_movie_classify_play_amount;
			vst_movie_classify_play_duration[i] = toHour(n.vst_movie_classify_play_duration);
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
				name : '总点击量',
				data : vst_movie_classify_click_amount,
			},
			{
				name : '总播放量',
				data : vst_movie_classify_play_amount,
			},
			{
				name : '总播放时长(小时)',
				data : vst_movie_classify_play_duration,
			}
			]
		});
	}

	// 统计占比(根据影片类型)
	function ajaxPieData(){
		var params = "startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_movie_classify_special_type=" + $("#vst_movie_classify_special_type").val();
		params += "&vst_movie_classify=" + $("#vst_movie_classify").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_classify_pie",
			dataType:"json",
			success:function(msg){
				if(msg.code == 100){
					$(".nodate").hide();
					$("#pieId").attr("style","height: 400px");
					pie(msg);
				}else{
					$("#palyTotalPie").empty();
				    $("#userTotalPie").empty();
					$("#clickTotalPie").empty();
					$("#durationTotalPie").empty();
					$("#pieId").attr("style","height: 200px");
					$(".nodate").show();
					//$("#myLoad").find("img").hide();
				}
			},
			error:function(msg){
			
			}
		});
	}
  	
	function pie(msg){
   		var data = msg.data;
   		console.log(data);
   		//播放量屏占比
   		var series = "[";
   		$.each(data,function(i,n){
   			series +="{name:\""+getMapValue('${classifys}', n["cid"])+"："+n.playTotal+"\",y:"+n.playTotal+"},";
   		});
   		
   		series+="]";
   		series = eval(series);
   		getSeries($("#palyTotalPie"),series);
   	
   		//观看人数屏占比
   		var userseries = "[";
   		$.each(data,function(i,n){
   			userseries +="{name:\""+getMapValue('${classifys}', n["cid"])+"："+n.userTotal+"\",y:"+n.userTotal+"},";
   		});
   		userseries+="]";
   		userseries = eval(userseries);
   		getSeries($("#userTotalPie"),userseries);
   		
   		//点击量屏占比
   		var clickTotalseries = "[";
   		$.each(data,function(i,n){
   			clickTotalseries +="{name:\""+getMapValue('${classifys}', n["cid"])+"："+n.clickTotal+"\",y:"+n.clickTotal+"},";
   		});
   		clickTotalseries+="]";
   		clickTotalseries = eval(clickTotalseries);
   		getSeries($("#clickTotalPie"),clickTotalseries);
   		
   		//播放时长屏占比
   		var durationseries = "[";
   		$.each(data,function(i,n){
   			durationseries +="{name:\""+getMapValue('${classifys}', n["cid"])+"："+toHour(n.durationTotal)+"\",y:"+toHour(n.durationTotal)+"},";
   		});
   		durationseries+="]";
   		durationseries = eval(durationseries);
   		getSeries($("#durationTotalPie"),durationseries);
	}
	
  	function getSeries(dom,series){
  		dom.highcharts({
      		chart: {
              	plotBackgroundColor: null,
              	plotBorderWidth: null,
              	plotShadow: false,
              	width : 1643,
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
                  	showInLegend: false
              	}
          	},
          	credits : {
			href : 'http://www.91vst.com',
			text : '91vst.com',
		},
      		series: [{
          		type: 'pie',
          		name: '比重',
          		data: series
      		}]
  		});
  	}
			
    function changeTab(showid,hideid1,hideid2,hideid3,obj){
      	$("#"+showid).attr("style","position: relative; height: 400px");
		var des = "<img class='desMyImg' src='${ctx}/pub/images/desc.gif'/>";
      	$("#"+showid).addClass("active");
      	$("#"+hideid1).removeClass("active"); 
      	$("#"+hideid2).removeClass("active");
      	$("#"+hideid3).removeClass("active");
      	$("li a."+hideid1).find("img.desMyImg").remove();
      	$("li a."+hideid2).find("img.desMyImg").remove();
      	$("li a."+hideid3).find("img.desMyImg").remove();
      	if( $("li a."+showid).find("img.desMyImg").length==0){
      		$("li a."+showid).append(des);
      	}
    }
	
	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_movie_classify_special_type=" + $("#vst_movie_classify_special_type").val();
		params += "&vst_movie_classify=" + $("#vst_movie_classify").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=movie_classify_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_date' id='vst_movie_classify_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_special_type' id='vst_movie_classify_special_type2' order='desc'>专区类型</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify' id='vst_movie_classify2' order='desc'>分类</a></th>";
		
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_click_amount' id='vst_movie_classify_click_amount2' order='desc'>点击量</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_click_amount_dod' id='vst_movie_classify_click_amount_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_click_amount_wow' id='vst_movie_classify_click_amount_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_amount' id='vst_movie_classify_play_amount2' order='desc'>播放量</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_amount_dod' id='vst_movie_classify_play_amount_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_amount_wow' id='vst_movie_classify_play_amount_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_click_uv' id='vst_movie_classify_click_uv2' order='desc'>点击独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_click_uv_dod' id='vst_movie_classify_click_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_click_uv_wow' id='vst_movie_classify_click_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_uv' id='vst_movie_classify_play_uv2' order='desc'>播放独立用户</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_uv_dod' id='vst_movie_classify_play_uv_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_uv_wow' id='vst_movie_classify_play_uv_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_duration' id='vst_movie_classify_play_duration2' order='desc'>播放时长(小时)</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_duration_dod' id='vst_movie_classify_play_duration_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_duration_wow' id='vst_movie_classify_play_duration_wow2' order='desc'>周环比</a></th>";

		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_pc_click_amount' id='vst_movie_classify_pc_click_amount2' order='desc'>人均点击量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_pc_play_amount' id='vst_movie_classify_pc_play_amount2' order='desc'>人均播放量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_pc_play_duration' id='vst_movie_classify_pc_play_duration2' order='desc'>人均播放时长(分钟)</a></th>";
		
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_0s' id='vst_movie_classify_play_time_0s2' order='desc'>[0s,10s)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_10s' id='vst_movie_classify_play_time_10s2' order='desc'>[10s,30s)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_30s' id='vst_movie_classify_play_time_30s2' order='desc'>[30s,60s)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_1m' id='vst_movie_classify_play_time_1m2' order='desc'>[1m,30m)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_30m' id='vst_movie_classify_play_time_30m2' order='desc'>[30m,60m)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_1h' id='vst_movie_classify_play_time_1h2' order='desc'>[1h,2h)/次</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_play_time_2h' id='vst_movie_classify_play_time_2h2' order='desc'>[2h,+)/次</a></th>";
		
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_uptime' id='vst_movie_classify_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_movie_classify_operator' id='vst_movie_classify_operator2' order='desc'>操作人</a></th>";
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
			
			if(parseFloat(n.vst_movie_classify_click_amount_dod)<0){
				clickAmountDodColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_click_amount_wow)<0){
				clickAmountWowColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_play_amount_dod)<0){
				playAmountDodColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_play_amount_wow)<0){
				playAmountWowColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_click_uv_dod)<0){
				clickUvDodColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_click_uv_wow)<0){
				clickUvWowColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_play_uv_dod)<0){
				playUvDodColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_play_uv_wow)<0){
				playUvWowColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_play_duration_dod)<0){
				playDurationDodColor = "red";
			}
			if(parseFloat(n.vst_movie_classify_play_duration_wow)<0){
				playDurationWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_movie_classify_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getDateWeek(n.vst_movie_classify_date+'', 'yyyyMMdd')+"</td>";
			if(typeof(n.vst_movie_classify_special_type)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${specialTypes}', n.vst_movie_classify_special_type)+"</td>";
			if(typeof(n.vst_movie_classify)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getMapValue('${classifys}', n.vst_movie_classify)+"</td>";
			
			//点击量
			if(typeof(n.vst_movie_classify_click_amount)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_click_amount)+" | ";
			if(typeof(n.vst_movie_classify_click_amount_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+clickAmountDodColor+"'>"+n.vst_movie_classify_click_amount_dod+"</span> | ";
			if(typeof(n.vst_movie_classify_click_amount_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+clickAmountWowColor+"'>"+n.vst_movie_classify_click_amount_wow+"</span></td>";
			//播放量
			if(typeof(n.vst_movie_classify_play_amount)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_amount)+" | ";
			if(typeof(n.vst_movie_classify_play_amount_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+playAmountDodColor+"'>"+n.vst_movie_classify_play_amount_dod+"</span> | ";
			if(typeof(n.vst_movie_classify_play_amount_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+playAmountWowColor+"'>"+n.vst_movie_classify_play_amount_wow+"</span></td>";
			//点击独立用户
			if(typeof(n.vst_movie_classify_click_uv)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_click_uv)+" | ";
			if(typeof(n.vst_movie_classify_click_uv_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+clickUvDodColor+"'>"+n.vst_movie_classify_click_uv_dod+"</span> | ";
			if(typeof(n.vst_movie_classify_click_uv_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+clickUvWowColor+"'>"+n.vst_movie_classify_click_uv_wow+"</span></td>";
			//播放独立用户
			if(typeof(n.vst_movie_classify_play_uv)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_uv)+" | ";
			if(typeof(n.vst_movie_classify_play_uv_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+playUvDodColor+"'>"+n.vst_movie_classify_play_uv_dod+"</span> | ";
			if(typeof(n.vst_movie_classify_play_uv_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+playUvWowColor+"'>"+n.vst_movie_classify_play_uv_wow+"</span></td>";
			//播放时长
			if(typeof(n.vst_movie_classify_play_duration)=="undefined")	table += "<td> | ";
			else	table += "<td>"+toHour(n.vst_movie_classify_play_duration)+" | ";
			if(typeof(n.vst_movie_classify_play_duration_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+playDurationDodColor+"'>"+n.vst_movie_classify_play_duration_dod+"</span> | ";
			if(typeof(n.vst_movie_classify_play_duration_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+playDurationWowColor+"'>"+n.vst_movie_classify_play_duration_wow+"</span></td>";
			//人均
			if(typeof(n.vst_movie_classify_pc_click_amount)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_pc_click_amount)+"</td>";
			if(typeof(n.vst_movie_classify_pc_play_amount)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_pc_play_amount)+"</td>";
			if(typeof(n.vst_movie_classify_pc_play_duration)=="undefined")	table += "<td></td>";
			else	table += "<td>"+toMinute(n.vst_movie_classify_pc_play_duration)+"</td>";
			//时间区间
			if(typeof(n.vst_movie_classify_play_time_0s)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_0s)+"</td>";
			if(typeof(n.vst_movie_classify_play_time_10s)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_10s)+"</td>";
			if(typeof(n.vst_movie_classify_play_time_30s)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_30s)+"</td>";
			if(typeof(n.vst_movie_classify_play_time_1m)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_1m)+"</td>";
			if(typeof(n.vst_movie_classify_play_time_30m)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_30m)+"</td>";
			if(typeof(n.vst_movie_classify_play_time_1h)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_1h)+"</td>";
			if(typeof(n.vst_movie_classify_play_time_2h)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatNumber(n.vst_movie_classify_play_time_2h)+"</td>";
			
			if(typeof(n.vst_movie_classify_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatDate(n.vst_movie_classify_uptime, "yyyy-MM-dd HH:mm:ss")+"</td>";
			if(typeof(n.vst_movie_classify_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_movie_classify_operator+"</td>";

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
			params += "&vst_movie_classify_special_type=" + $("#vst_movie_classify_special_type").val();
			params += "&vst_movie_classify=" + $("#vst_movie_classify").val();
			
			$.ajax({
				type: "POST",
				data: params,
				async : true,
				cache : false,
				url:"${ctx}/report/json?code=movie_classify_export",
				dataType:"json",
				success:function(msg){
					if(msg.code == 100){
						location.href= "${ctx}/movieClassify/exportData.action?data="+JSON.stringify(msg.data)
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
				<form action="${ctx}/movieClassify/findMovieClassify.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 影片统计 > 影片分类统计</span>
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
												<select class="list-input1" id="vst_movie_classify_special_type" name="vst_movie_classify_special_type">
													<option value="">请选择</option>
													<c:forEach items="${specialTypes}" var="specialType">
														<option value="${specialType.key }">${specialType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">分类：</span>
												<select class="list-input1" id="vst_movie_classify" name="vst_movie_classify">
													<option value="">请选择</option>
													<c:forEach items="${classifys}" var="classify">
														<option value="${classify.key }">${classify.value }</option>
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
					
					<!-- 饼状图 -->
				    <section class="content">
				    	<div class="row">
				        	<div class="col-xs-12" id="">
				                <div class="nav-tabs-custom">
				           	  		<!--小屏幕-->
				                	<div class="row-screen">
				                    	<div class="pull-left header">
				                        	<i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
				                        	<small class="table-left-title">视频分类播放占比</small>
				                      	</div>
				                      	<div class="small-screen pull-right">
					                      	<div class="table-active act-div">
					                        	<span class="table-active-class">播放量</span>
					                      	</div>
					                      	<ul class="small-screen-table">
					                       		<li class="active bug">
					                        		<a href="javascript:void(0)" class="tab_switch clickTotalPie"  data-toggle="tab" onclick="changeTab('clickTotalPie')">点击量</a>
					                        	</li>
					                         	<li class="active bug">
					                        		<a href="javascript:void(0)" class="tab_switch durationTotalPie"  data-toggle="tab" onclick="changeTab('durationTotalPie')">播放时长</a>
					                        	</li>
					                        	<li class="active bug">
					                        		<a href="javascript:void(0)" class="tab_switch palyTotalPie"  data-toggle="tab" onclick="changeTab('palyTotalPie')">播放量</a>
					                        	</li>
					                        	<li>
					                        		<a href="javascript:void(0)" class="tab_switch userTotalPie"  data-toggle="tab" onclick="changeTab('userTotalPie')">观看人数</a>
					                        	</li>
					                     	</ul>
					                	</div>
				               		</div>
					           		<!-- 大屏幕 -->
					           		<ul class="nav nav-tabs pull-right big-screen">
						             	<li class="active"><a href="javascript:void(0)" data-toggle="tab" class="palyTotalPie" onclick="changeTab('palyTotalPie','userTotalPie','clickTotalPie','durationTotalPie')">播放量</a></li>
						             	<li><a href="javascript:void(0)" data-toggle="tab" class="userTotalPie" onclick="changeTab('userTotalPie','palyTotalPie','clickTotalPie','durationTotalPie')">观看人数</a></li>
						              	<li><a href="javascript:void(0)" data-toggle="tab" class="clickTotalPie" onclick="changeTab('clickTotalPie','palyTotalPie','userTotalPie','durationTotalPie')">点击量</a></li>
						               	<li><a href="javascript:void(0)" data-toggle="tab" class="durationTotalPie" onclick="changeTab('durationTotalPie','clickTotalPie','userTotalPie','palyTotalPie')">播放时长</a></li>
						             	<li class="pull-left header"><i class="fa fa-bar-chart-o"></i> <small>视频分类播放占比</small></li>
					           		</ul>
					           		<div class="tab-content no-padding" id="pieId">
						             	<!-- Morris chart - Sales -->
						             	<div class="nodate" style="display:none">
						             		<table id="noquery" width="100%">
												<tr>
													<td align="center" height="80px;">
														没有找到相关记录，请重新输入条件进行查询
													</td>
												</tr>
											</table>
										</div>
						             	<div class="chart tab-pane active" id="palyTotalPie" style="position: relative; height: 400px;"> <div id="myLoad" ><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/> </div></div>
						             	<div class="chart tab-pane"  id="userTotalPie" style="position: relative; height: 400px;"></div>
						             	<div class="chart tab-pane"  id="clickTotalPie" style="position: relative; height: 400px;"></div>
						             	<div class="chart tab-pane"  id="durationTotalPie" style="position: relative; height: 400px;"></div>
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
</body>
</html>