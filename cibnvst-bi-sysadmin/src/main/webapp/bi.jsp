<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
// 读取数据库配置
String rpt_code = "movie_vv";

// 读取数据库成功，得到如下配置
int seq = 2; // 图表序号
String jsonUrl = ""; // 图表数据远程地址
String chartType = "line"; // 折线图
String others = ""; // 其余的highcharts参数。。。。。。

List<Map<String, String>> charts = new ArrayList<Map<String, String>>();
Map<String, String> chart1 = new HashMap<String, String>();
Map<String, String> chart2 = new HashMap<String, String>();
Map<String, String> chart3 = new HashMap<String, String>();
charts.add(chart1);
charts.add(chart2);
charts.add(chart3);
%>

<html>

<head>
<title>自动化报表DEMO</title>
<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>

</head>

<body>

<%
// 筛选块


%>


<%





// 图表块
for (int i = 0; i < charts.size(); i++) {
	Map<String, String> chart = charts.get(i);

	out.println("<div id='container"+i+"' style='min-width:400px;height:400px'></div>");
	
	StringBuffer sql = new StringBuffer();	
	sql.append("<script>");
	sql.append(" var chart = new Highcharts.Chart('container"+i+"', {");
	sql.append("     title: {");
	sql.append("         text: false,");
	sql.append("         x: -20");
	sql.append("     },");
	sql.append("     subtitle: {");
	sql.append("         text: false,");
	sql.append("         x: -20");
	sql.append("     },");
	sql.append("     xAxis: {");
	sql.append("         categories: [20170901,20170902,20170903,20170904,20170905,20170906,20170907]");
	sql.append("     },");
	sql.append("     yAxis: {");
	sql.append("         title: {");
	sql.append("             text: false");
	sql.append("         },");
	sql.append("         plotLines: [{");
	sql.append("             value: 0,");
	sql.append("             width: 1,");
	sql.append("             color: '#808080'");
	sql.append("         }]");
	sql.append("     },");
	//sql.append("     tooltip: {");
	//sql.append("         valueSuffix: '°C'");
	//sql.append("     },");
	sql.append("     legend: {");
	sql.append("         layout: 'vertical',");
	sql.append("         align: 'right',");
	sql.append("         verticalAlign: 'middle',");
	sql.append("         borderWidth: 0");
	sql.append("     },");
	sql.append("     series: [{");
	sql.append("         name: '总播放量',");
	sql.append("         data: [10,15,30,25,18,20,40]");
	sql.append("     }]");
	sql.append(" });");
	sql.append("");
	sql.append("</script>");
	
	out.println(sql.toString());
		
}


%>




<%
// 表格块

%>

</body>
</html>
