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
		$("#vst_user_reactivate_dim_value1").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_user_reactivate_dim_value2").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		
    	//维度1为渠道的时候，维度2显示
		$("#vst_user_reactivate_dim_name1").change(function(){
			var dim = $("#vst_user_reactivate_dim_name1").val();
			if(dim == "渠道"){
				$(".dim2Condition").show();
			}else{
				$(".dim2Condition").hide();
				$("#vst_user_reactivate_dim_name2").val("");
				$("#vst_user_reactivate_dim_value2").val("");
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
			if($("#vst_user_reactivate_dim_name1").val() == "全部"){
				$("#barChartBlock").hide();
				$("#pieChartBlock").hide();
				$("#mapChartBlock").hide();
			}else if($("#vst_user_reactivate_dim_name1").val() == "省份"){
				$("#barChartBlock").hide();
				$("#pieChartBlock").show();
				$("#mapChartBlock").show();
				ajaxGetReportByDimValue();// 按维度统计
			}else{
				$("#barChartBlock").show();
				$("#pieChartBlock").show();
				$("#mapChartBlock").hide();
				ajaxGetReportByDimValue();// 按维度统计
			}
		}
	}

	// 按日期统计(折线图)
	function ajaxGetReport(){
		$("#loading_line").removeAttr("style");
		$("#line-chart").empty();
		
		var params = "startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_user_reactivate_dim_name1=" + $("#vst_user_reactivate_dim_name1").val();
		params += "&vst_user_reactivate_dim_value1=" + $("#vst_user_reactivate_dim_value1").val();
		params += "&vst_user_reactivate_dim_name2=" + $("#vst_user_reactivate_dim_name2").val();
		params += "&vst_user_reactivate_dim_value2=" + $("#vst_user_reactivate_dim_value2").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_reactivate_line",
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
		var vst_user_reactivate_dru = [];
		var vst_user_reactivate_qru = [];
		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_user_reactivate_dru[i] = n.vst_user_reactivate_dru;
			vst_user_reactivate_qru[i] = n.vst_user_reactivate_qru;
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
				name : '日二次激活数',
				data : vst_user_reactivate_dru,
			},
			{
				name : '季二次激活数',
				data : vst_user_reactivate_qru,
			}
			]
		});
	}

	// 按维度统计
	function ajaxGetReportByDimValue(){
		$("#loading_pie").removeAttr("style");
		$("#pie-chart").empty();

		if($("#vst_user_reactivate_dim_name1").val() == "省份"){
			$("#loading_map").removeAttr("style");
			$("#map-chart").empty();
		}else{
			$("#loading_bar").removeAttr("style");
			$("#bar-chart").empty();
		}
					
		var params = "startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_user_reactivate_dim_name1=" + $("#vst_user_reactivate_dim_name1").val();
		params += "&vst_user_reactivate_dim_value1=" + $("#vst_user_reactivate_dim_value1").val();
		params += "&vst_user_reactivate_dim_name2=" + $("#vst_user_reactivate_dim_name2").val();
		params += "&vst_user_reactivate_dim_value2=" + $("#vst_user_reactivate_dim_value2").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_reactivate_dimValue",
			dataType:"json",
			success:function(msg){
				if(msg.code == 100){
					$("#loading_pie").attr("style","display:none");
					pie(msg);
					if($("#vst_user_reactivate_dim_name1").val() == "省份"){
						$("#loading_map").attr("style","display:none");
						chinamap(msg);
					}else{
						$("#loading_bar").attr("style","display:none");
						bar(msg);
					}
				}else{
					$("#loading_pie").attr("style","display:none");
					$("#loading_bar").attr("style","display:none");
					$("#loading_map").attr("style","display:none");
					$("#pie-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#bar-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#map-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error:function(msg){
				$("#loading_pie").attr("style","display:none");
				$("#loading_bar").attr("style","display:none");
				$("#loading_map").attr("style","display:none");
			}
		});
	}

	function bar(msg){
		var data = msg.data;
		var top10data = data;
   		var xaxis = [];
		var series = [];
		if(data.length>10){
			top10data = data.slice(0, 10);
		}
		$.each(top10data,function(i,n){
			xaxis[i] = getValue(n["value"]);
			series[i] = n.vst_user_reactivate_dru;
		});
		
		$("#bar-chart").highcharts({
			chart: {
	            type: 'column',
	            height: 235
	        },
			title: {
	            text: false,
	        },
	        subtitle: {
	            text: false,
	        },
			xAxis:{
				categories: xaxis
			},
			yAxis: {
				tickAmount: 5,
	            title: {
	                text: false,
	                	rotation:0,
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
               plotOptions: {  
                   line: {  
                       cursor: 'pointer',  
                       dataLabels: {  
                           enabled: true  
                       },  
                       enableMouseTracking: true  
                   }  
               },
	        legend: {
	            align: 'center',
	            verticalAlign: 'top',
	            labelFormat: '{name}Top10'
	        },
	        credits: {
			      href: 'http://www.91vst.com',
			      text: '91vst.com',
			    },
			series:[{
					name: '二次激活数总量',
					data: series,
				}
			]
		});
	}
	
	function pie(msg){
   		var data = msg.data;
   		var top20data = data;
		if(data.length>20){
			top20data = data.slice(0, 20);
		}
   		
   		var series = "[";

   		$.each(top20data,function(i,n){
   			series +="{name:\""+getValue(n["value"])+"："+n.vst_user_reactivate_dru+"\",y:"+n.vst_user_reactivate_dru+"},";
   		});
   		
   		series+="]";
   		series = eval(series);
   		
   		$("#pie-chart").highcharts({
       		chart: {
               	plotBackgroundColor: null,
               	plotBorderWidth: null,
               	plotShadow: false,
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
                   	allowPointSelect: true/*,
                   	cursor: 'pointer',
                   	dataLabels: {
               		    enabled: true,
               		    color: '#000000',
               		    connectorColor: '#000000',
               		    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
               		},
                   	showInLegend: true*/
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
	
	function chinamap(msg){
		//按省份排行数据
        var data = msg.data;
		
        $.ajax({
			type:"POST",
			async : true,
			cache : false,
			url:"${ctx}/report/ajaxGetProvinceCode.action?data="+JSON.stringify(msg.data),
			dataType:"json",
			success:function(result){
				if(result.code == 100){
					data = result.data;
				}
			}
		});
		
        var maxTotal = 0;
        
        var data_province = "[";
   		$.each(data,function(i,n){
   			data_province+="{value:"+n.vst_user_reactivate_dru+",text:\""+n.vst_user_reactivate_dru+"\","+"name:\""+n.value+"\",code:\""+n.code+"\",enname:\""+n.enname+"\"},";
   			if(n.vst_user_reactivate_dru > maxTotal){
   	   			maxTotal = n.vst_user_reactivate_dru;
   	   		}
   		});
   		
   		data_province+="]";
   		data_province = eval(data_province);
   	    
        $("#map-chart").css("height","500px");
        var myChart = echarts.init(document.getElementById('map-chart'));
        var option = {
            title: {
                text: '按省份排行',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    var value = params.value||0;
                    return params.name + ' : ' + value;
                }
            },
            dataRange: {
                min: 0,
                max: maxTotal,
                x: 'left',
                y: 'bottom',
                text: ['高', '低'],
                calculable: true,
                color: ["#ff4500", "#ff0", "#87cefa"]
            },
            series: [
                {
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    itemStyle: {
                        normal: {
                            label: {show: true},
                            borderColor: "#eee"
                        },
                        emphasis: {label: {show: true}},
                    },
                    data: data_province,
                },
            ]
        };
        myChart.setOption(option);
	}

	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startDay=" + $("#startDay").val() + "&endDay=" + $("#endDay").val();
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_user_reactivate_dim_name1=" + $("#vst_user_reactivate_dim_name1").val();
		params += "&vst_user_reactivate_dim_value1=" + $("#vst_user_reactivate_dim_value1").val();
		params += "&vst_user_reactivate_dim_name2=" + $("#vst_user_reactivate_dim_name2").val();
		params += "&vst_user_reactivate_dim_value2=" + $("#vst_user_reactivate_dim_value2").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/report/json?code=user_reactivate_table",
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
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_date' id='vst_user_reactivate_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dim_name1' id='vst_user_reactivate_dim_name12' order='desc'>维度名</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dim_value1' id='vst_user_reactivate_dim_value12' order='desc'>维度值</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dim_name2' id='vst_user_reactivate_dim_name22' order='desc'>维度名2</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dim_value2' id='vst_user_reactivate_dim_value22' order='desc'>维度值2</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dru' id='vst_user_reactivate_dru2' order='desc'>日二次激活数</a> | <a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dru_dod' id='vst_user_reactivate_dru_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_user_reactivate_dru_wow' id='vst_user_reactivate_dru_wow2' order='desc'>周环比</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_qru' id='vst_user_reactivate_qru2' order='desc'>季二次激活数</a> | <a onclick='changeOrder(this)' orderBy='vst_user_reactivate_qru_dod' id='vst_user_reactivate_qru_dod2' order='desc'>天环比</a> | <a onclick='changeOrder(this)' orderBy='vst_user_reactivate_qru_wow' id='vst_user_reactivate_qru_wow2' order='desc'>周环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_uptime' id='vst_user_reactivate_uptime2' order='desc'>更新时间</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_user_reactivate_operator' id='vst_user_reactivate_operator2' order='desc'>操作人</a></th>";
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var druDodColor = "green";
			var druWowColor = "green";
			var qruDodColor = "green";
			var qruWowColor = "green";
			
			if(parseFloat(n.vst_user_reactivate_dru_dod)<0){
				druDodColor = "red";
			}
			if(parseFloat(n.vst_user_reactivate_dru_wow)<0){
				druWowColor = "red";
			}
			if(parseFloat(n.vst_user_reactivate_qru_dod)<0){
				qruDodColor = "red";
			}
			if(parseFloat(n.vst_user_reactivate_qru_wow)<0){
				qruWowColor = "red";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			if(typeof(n.vst_user_reactivate_date)=="undefined")	table += "<td></td>";
			else	table += "<td>"+getDateWeek(n.vst_user_reactivate_date+'', 'yyyyMMdd')+"</td>";
			if(typeof(n.vst_user_reactivate_dim_name1)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_user_reactivate_dim_name1+"</td>";
			if(typeof(n.vst_user_reactivate_dim_value1)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_user_reactivate_dim_value1+"</td>";
			if(typeof(n.vst_user_reactivate_dim_name2)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_user_reactivate_dim_name2+"</td>";
			if(typeof(n.vst_user_reactivate_dim_value2)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_user_reactivate_dim_value2+"</td>";
			
			if(typeof(n.vst_user_reactivate_dru)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_user_reactivate_dru)+" | ";
			if(typeof(n.vst_user_reactivate_dru_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+druDodColor+"'>"+n.vst_user_reactivate_dru_dod+"</span> | ";
			if(typeof(n.vst_user_reactivate_dru_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+druWowColor+"'>"+n.vst_user_reactivate_dru_wow+"</span></td>";
			
			if(typeof(n.vst_user_reactivate_qru)=="undefined")	table += "<td> | ";
			else	table += "<td>"+formatNumber(n.vst_user_reactivate_qru)+" | ";
			if(typeof(n.vst_user_reactivate_qru_dod)=="undefined")	table += "| ";
			else	table += "<span class='"+qruDodColor+"'>"+n.vst_user_reactivate_qru_dod+"</span> | ";
			if(typeof(n.vst_user_reactivate_qru_wow)=="undefined")	table += "</td>";
			else	table += "<span class='"+qruWowColor+"'>"+n.vst_user_reactivate_qru_wow+"</span></td>";
			
			if(typeof(n.vst_user_reactivate_uptime)=="undefined")	table += "<td></td>";
			else	table += "<td>"+formatDate(n.vst_user_reactivate_uptime, "yyyy-MM-dd HH:mm:ss")+"</td>";
			if(typeof(n.vst_user_reactivate_operator)=="undefined")	table += "<td></td>";
			else	table += "<td>"+n.vst_user_reactivate_operator+"</td>";

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
			params += "&vst_user_reactivate_dim_name1=" + $("#vst_user_reactivate_dim_name1").val();
			params += "&vst_user_reactivate_dim_value1=" + $("#vst_user_reactivate_dim_value1").val();
			params += "&vst_user_reactivate_dim_name2=" + $("#vst_user_reactivate_dim_name2").val();
			params += "&vst_user_reactivate_dim_value2=" + $("#vst_user_reactivate_dim_value2").val();
			
			$.ajax({
				type: "POST",
				data: params,
				async : true,
				cache : false,
				url:"${ctx}/report/json?code=user_reactivate_export",
				dataType:"json",
				success:function(msg){
					if(msg.code == 100){
						location.href= "${ctx}/userReactivate/exportData.action?data="+JSON.stringify(msg.data)
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
				<form action="${ctx}/userReactivate/findUserReactivate.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 用户统计 > 二次激活用户</span>
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
												<span class="sertitle">维度：</span>
												<select class="list-input1" id="vst_user_reactivate_dim_name1" name="vst_user_reactivate_dim_name1">
													<c:forEach items="${dimName1}" var="dim1">
														<option value="${dim1.key }">${dim1.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">维度值：</span>
												<input class="list-input1" type="text" id="vst_user_reactivate_dim_value1" name="vst_user_reactivate_dim_value1" />
											</li>
											<li class="dim2Condition" style="display: none;">
												<span class="sertitle">维度2：</span>
												<select class="list-input1" id="vst_user_reactivate_dim_name2" name="vst_user_reactivate_dim_name2">
													<option value="">请选择</option>
													<c:forEach items="${dimName2}" var="dim2">
														<option value="${dim2.key }">${dim2.value }</option>
													</c:forEach>
												</select>
											</li>
											<li class="dim2Condition" style="display: none;">
												<span class="sertitle">维度值2：</span>
												<input class="list-input1" type="text" id="vst_user_reactivate_dim_value2" name="vst_user_reactivate_dim_value2" />
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
					
					<!-- 柱状图 -->
					<section class="content" id="barChartBlock" style="display: none;">
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
			                        <div class="chart" id="bar-chart" >
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
			                    <div id="loading_bar" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
			                </div>
			            </div>
			        </div>
					</section>
					
					<!-- 饼状图 -->
					<section class="content" id="pieChartBlock" style="display: none;">
					<div class="row">
						<div class="col-xs-12">
			                <!-- PIE CHART -->
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
			                        <div class="chart" id="pie-chart" >
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
			                    <div id="loading_pie" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
			                </div>
			            </div>
			        </div>
					</section>
					
					<!-- 地图 -->
					<section class="content" id="mapChartBlock" style="display: none;">
					<div class="row">
						<div class="col-xs-12">
			                <!-- PIE CHART -->
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
			                        <div class="chart" id="map-chart" >
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
			                    <div id="loading_map" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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