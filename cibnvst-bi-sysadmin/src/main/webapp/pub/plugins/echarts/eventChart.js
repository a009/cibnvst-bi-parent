var chartType = "line";

$(document).ready(function() {
	createChart();
	$('#chartsTypeul li').click(function() {
		$(this).addClass('active').siblings().removeClass('active');
		$("#charts-type").text($(this).text());
		chartType = $(this).find('a').attr('class');
		createChart();
	});	
});

function createChart() {
	var myChart = echarts.init(document.getElementById('chart'));

	// 自适应宽高
	var myChartContainer = function() {
		myChart.width = window.innerWidth + 'px';
		myChart.height = window.innerHeight + 'px';
	};
	myChartContainer();

	var option = {
		title : {
			text : '世界人口总量',
			subtext : '数据来自网络'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			data : [ '2011年', '2012年' ]
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'category',
			data : [ '巴西', '印尼', '美国', '印度', '中国', '世界人口(万)' ]
		},
		yAxis : {
			type : 'value',
		    boundaryGap : [ 0, 0.01 ]         
		},
		series : [ {
			name : '2011年',
			type : chartType,
			data : [ 18203, 23489, 29034, 104970, 131744, 630230 ]
		}, {
			name : '2012年',
			type : chartType,
			data : [ 19325, 23438, 31000, 121594, 134141, 681807 ]
		} ]
	};

	myChart.setOption(option);

	// 浏览器大小改变时重置大小
	window.onresize = function() {
		myChartContainer();
		myChart.resize();
	};

}
