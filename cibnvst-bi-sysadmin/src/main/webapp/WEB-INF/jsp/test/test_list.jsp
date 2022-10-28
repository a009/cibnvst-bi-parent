<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>
<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
<script type="text/javascript">
  	$(function(){
  		$.ajax({
			url:"${ctx}/report/chart",
			type:"POST",
			data:"code=test_line",
			dataType:"text",
			async:false,
			cache:false,
			success:function(data){
				var json = JSON.parse(data);
				if(json.code == 100){
					for(var i=0; i<json.data.length; i++){
						var chart = json.data[i];
						
						//请求api，拿到数据
						var data = getApiData(chart.api);
						var xaxis = [];
						var clicks = [];
						var plays = [];
						var durations = [];
						$.each(data,function(i,n){
							xaxis[i] = n.date;
							clicks[i] = n.clicks;
							plays[i] = n.plays;
							durations[i] = n.durations;
						});
						
						/*alert(xaxis);
						alert(clicks);
						alert(plays);
						alert(durations);*/
						
						var str = "<div id='container"+i+"' style='min-width:400px;height:400px'></div>";
						$("#page_chart").html($("#page_chart").html()+str);
						//$("#page_chart").html(str);
						var jsonStr = chart.json.replace("xaxis",xaxis).replace("clicks",clicks).replace("plays",plays).replace("durations",durations);
						//var ch = new Highcharts.Chart('container'+i, jsonStr);
						var ch = new Highcharts.Chart('container'+i, JSON.parse(jsonStr));
					}
				}
			},
		    error:function (XMLHttpRequest, textStatus, e) {
		    	alert("错误信息：" + e);
		   	}
		});
  	});
  	
  	
  	function getApiData(api){
  		var result = null;
		$.ajax({
			url:api,
			type:"POST",
			data:null,
			dataType:"text",
			async:false,
			cache:false,
			success:function(data){
				//alert(data);
				var json = JSON.parse(data);
				if(json.code == 100){
					result = json.data;
				}
			}
		});
		return result;
  	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form action="${ctx}/sysButton/findButtons.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统按钮查询</span>
					</section>
					
					<!-- 条件 -->
					<section class="content" id="page_condition">
						
					</section>
					
					<!-- 统计图 -->
					<section class="content" id="page_chart">
						
					</section>
					
					<!-- 表格 -->
					<section class="content" id="page_table">
						
					</section>
				</form>
			</div>
		</div>
		<%@include file="../share/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
</script>
</html>