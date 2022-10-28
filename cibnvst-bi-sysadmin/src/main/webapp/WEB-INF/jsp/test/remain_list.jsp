<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<%@include file="../test/shence.jsp"%>

<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>报表管理中心</title>
<script type="text/javascript">
  	$(function(){
  		//下拉列表框，赋值
  		$("#vst_button_state").val($("#hidden_button_state").val());
  		
  		//展开/隐藏 查询条件
  		var hidden_search = '${hidden_search}';
  		if(hidden_search == 1 || hidden_search == '1'){	//隐藏
			$("#hidden_search").val(1);
		}else{	//展开
			$("#hidden_search").val(2);
		}
		
		//展开/隐藏 tablelist
		var hidden_tablelist = '${hidden_tablelist}';
  		if(hidden_tablelist == 2 || hidden_tablelist == '2'){	//展开
			$("#hidden_tablelist").val(2);
			$("#tableExample1 .table .table-tbody").removeClass("maxHeight").slideDown();
		}else{	//隐藏
			$("#hidden_tablelist").val(1);
			$("#tableExample1 .table .table-tbody").addClass("maxHeight");
		}
  	});
  
  	// 查询
  	function validQuery(){
		$("#currPage").val(1);
		$("#queryBtn").attr("disabled", "disabled");
		$("#listForm").submit();
	}
	
	// 初始化map实例
	var cahceMap = new HashMap(); 
	$(function(){
		$(".myClick").bind("click",function(){
			// 清除所有的.myClick样式下，所有的input文本域
			$(".myClick > input").each(function(i){
				$(this).parent("li").html($(this).val());
			});
			var value = "";
			// 首先判断有没有input子节点
			if($(".myClick > input").length > 0){
				value = $(".myClick > input").val();
			}else{
				value = $(this).text();
			}
			$(this).html("<input type='text' value='"+value+"'/>");
			$(".myClick > input").focusEnd();
			
		});
		
		$(document).on("blur", ".myClick > input", function() {
			// 清除所有的.myClick样式下，所有的input文本域
			$(".myClick > input").each(function(i){
				var $ul = $(this).parents("ul");
				var newValue = $(this).val();
				$(this).parent("li").html(newValue);
				var firstTD = $ul.find("li:eq(0) :input[type=checkbox][name=recordIds]");
				var oldValue = $(firstTD).attr("index");
				if(newValue != oldValue){
					$("#doIndex").attr("disabled",false);
					$("#doIndex").attr("class","rankBtn");
					cahceMap.put($(firstTD).attr("value"), newValue);
				}
			});
		});
	});
	
	// 生效排序
	function makeIndex(){
		if($("#doIndex").attr("disabled") == undefined || $("#doIndex").attr("disabled") == ""){
			if(!checkIndexs()){
				alert("非常抱歉！您修改的排序结果中有非数字的排序，请认真检查！");
			}else{
				$.ajax({
					url:"${ctx}/sysButton/modifyButtonIndexs.action",
					type:"POST",
					data:"ids="+getKeys()+"&indexs="+getValues()+"&moduleId="+$("#moduleId").val(),
					dataType:"text",
					async:false,
					cache:false,
					success:function(data){
						if(data != null && data == "true"){
							alert("修改成功！请点击查询后查看结果！");
							$("#doIndex").attr("disabled", "disabled");
							$("#doIndex").attr("class","resetBtn");
						}else{
							alert("修改失败！");
						}
					},
				   error:function (XMLHttpRequest, textStatus, e) {
						   alert("修改失败！失败信息：" + e);
					}
				});
			}
		}
	}
	
	// 校验数据是否合法
    function checkIndexs(){
	    var values = cahceMap.values();
	    var reg = /^[0-9]{1,}$/;
		for(var i in values){
			if(!reg.test(values[i])){
				return false;
			}
		}
		return true;
	}

	function getKeys(){
		 var keys = cahceMap.keySet(); 
		 var result = "";
		 for(var i in keys){
			 result += keys[i] + ",";
		 }
		 return result;
	}

	function getValues(){
		 var values = cahceMap.values(); 
		 var result = "";
		 for(var i in values){
			result += values[i] + ",";
		 }
		 return result;
	}
	 
	// 展开/隐藏 查询条件
	function search_open(){
		if($(".searchBar").hasClass("active")){	//展开
			$(".search-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_close.png");
			$(".searchBar").removeClass("active").slideDown();
			$("#hidden_search").val(2);
		}else{	//隐藏
			$(".search-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_open.png");
			$(".searchBar").addClass("active").slideUp(300);
			$("#hidden_search").val(1);
		}
	}
	
	// 展开/隐藏 tablelist
	function tablelist_open(){
		if($("#tableExample1 .table .table-tbody").hasClass("maxHeight")){	//展开
			$(".tablelist-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_close.png");
			$("#tableExample1 .table .table-tbody").removeClass("maxHeight").slideDown();
			$("#hidden_tablelist").val(2);
		}else{	//隐藏
			$(".tablelist-up-button").find("img").attr("src","${ctx}/pub/listPages/images/search_open.png");
			$("#tableExample1 .table .table-tbody").addClass("maxHeight");
			$("#hidden_tablelist").val(1);
		}
	}
</script>
<script type="text/javascript">
$(function(){
    var g_fun = {
        init_daterangepicker: function (ele) {
            //定义locale汉化插件
            var locale = {
                "separator": " 至 ",
                "applyLabel": "确定",
                "cancelLabel": "取消",
                "fromLabel": "起始时间",
                "toLabel": "结束时间'",
                'startOfWeek': "monday",
                "customRangeLabel": "自定义",
                "weekLabel": "W",
                "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                "firstDay": 1
            };
            //日期控件初始化
            ele.daterangepicker(
                {
                    'locale': locale,
                    "separator": " 至 ",
                    //汉化按钮部分
                    ranges: {
                        '今日': [moment(), moment()],
                        '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        '本周': [moment().startOf('week').add(1, 'days'), moment()],
                        '上周': [moment().subtract(1, 'week').startOf('week').add(1, 'days'), moment().subtract(1, 'week').endOf('week').add(1, 'days')],
                        '本月': [moment().startOf('month'), moment()],
                        '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                        '本年': [moment().startOf('year'), moment()],
                        '去年': [moment().subtract(1, 'year').startOf('year'), moment().subtract(1, 'year').endOf('year')],
                        '过去7天': [moment().subtract(6, 'days'), moment()],
                        '过去30天': [moment().subtract(29, 'days'), moment()],
                        '上线至今': [moment().subtract(29, 'days'), moment()]
                    },
                    startDate: moment().subtract(2, 'hours'),
                    endDate: moment(),
                    maxDate: moment(),
                    alwaysShowCalendars: true
                },
                function (start, end) {
                    if (!ele.html()) {
                        ele.val(start.format('YYYY-M-DD') + ' 至 ' + end.format('YYYY-M-DD'));
                        $('.dashboard-widget-timerange').html(start.format('YYYY-M-DD') + ' 至 ' + end.format('YYYY-M-DD'));
                    } else {
                        ele.html(start.format('YYYY-M-DD') + ' 至 ' + end.format('YYYY-M-DD'));
                    }
                },
                ele.val(moment().subtract(2, 'hours').format('YYYY-M-DD') + ' 至 ' + moment().format('YYYY-M-DD'))
            );
            //初始化显示当前时间
            if (!ele.html()) {
                ele.val(moment().subtract(2, 'hours').format('YYYY-M-DD') + ' 至 ' + moment().format('YYYY-M-DD'));
            } else {
                ele.html(moment().subtract(2, 'hours').format('YYYY-M-DD') + ' 至 ' + moment().format('YYYY-M-DD'));
            }
            $('.daterangepicker .ranges ul li:nth-child(1)').addClass('active').siblings().removeClass('active');;
        },
     }
     g_fun.init_daterangepicker($('#inputDaterangepicker'));
     
    });
</script>
<script type="text/javascript">
$(function(){
	    var charts = [];
        var widgetChart = document.getElementById('widget-chart-container');
        var dashboardChart = echarts.init(widgetChart);

        function callback() {
            
        }
        var option = {
            grid: {show: false, top: 25, bottom: 0, right: 4, left: 4, containLabel: true},
            color: ["#559FF0", "#AACF44", "#FF9945", "#3AD1C5", "#FACF2A", "#FC6772", "#788CF0", "#2DCA93", "#EF717A", "#98AAD4", "#A5C63A", "#79302A", "#34485E", "#BDC3C7", "#A5C63A", "#84C1E9", "#81E0A9", "#F0B17A", "#AB9EDC", "#F0938A", "#FFE166", "#E5DABF", "#75D6C3", "#F5A9AF", "#C1CCE5", "#C9DC88", "#AE827F", "#85919E", "#D7DBDD", "#C9DC88"],
            calculable: true,
            xAxis: {
                type: "category",
                data: ['9-4', '9-5', '9-6', '9-7', '9-8', '9-9', '9-10'],
                splitLine: {show: false},
                axisLine: {lineStyle: {color: "#ccc"}},
                axisTick: {show: true, lineStyle: {color: "#ccc"}, alignWithLabel: true},
                axisLabel: {
                    textStyle: {
                        color: "#7F7F7F",
                        fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                        fontSize: 12,
                        fontWeight: 300
                    }
                }
            },
            tooltip: {
                borderColor: '#85919E',
                trigger: "axis",//是否节点触发
                borderWidth: 1,
                formatter: function (params) {
                    console.log(params);
                    $(".dashboard-widget-toolbar-date-text").html(params[0].name);
                    $(".dashboard-widget-key ").html(params[0].value+'<span class="dashboard-widget-unit"></span>');
                    return "";
                }
            },
            radar: {
                triggerEvent: true
            },
            yAxis: {
                axisLine: {show: false},
                axisTick: {show: false},
                splitNumber: 3.5,
                type: 'value',
                scale: true,
                minInterval: 1,
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: "#7F7F7F",
                        fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                        fontSize: 12,
                        fontWeight: 300
                    }
                }
            },
            axisPointer: {
                color: '#555'
            },
            series: [
                {
                    name: '人均粉丝数',
                    type: 'line',
                    data: [4684.42, 5485.42, 4934.05, 4549.65, 5003.68, 4963.17, 5281.0],
                    symbol: 'circle',
                    symbolSize: 6,
                    barWidth: 25,
                    itemStyle: {
                        normal: {
                            color: "#559ff0",//图标颜色
                            lineStyle: {
                                color: '#559ff0'
                            }
                        }
                    }
                }
            ]
        };
        dashboardChart.setOption(option, true);
        charts.push(dashboardChart);
        console.log(dashboardChart);

        function charts_type() {
            $('.segmentation-chart-type').each(function (index, item) {
                $(item).find('span').each(function (i, m) {
                    $(m).click(function () {
                        $(m).addClass('active').siblings().removeClass('active');
                        console.log(option.series[index]);
                        var _this = $('.dashboard-widget-chart').eq(index);

                        if (_this.find('table').length != 0) {
                            _this.find('table').remove();
                        }
                        console.log($(m).attr('data-value'));
                        if ($(m).attr('data-value') == 'column') {
                            _this.children('div').show();
                            option.series[index].type = 'bar';
                            _this.siblings('.dashboard-widget-toolbar-container').removeClass('display-none')
                                .siblings('.dashboard-widget-keyinfo-container').removeClass('display-none');
                            _this.find('table').hide();
                            dashboardChart.setOption(option);

                        } else if ($(m).attr('data-value') == 'line') {
                            _this.children('div').show();
                            option.series[index].type = 'line';
                            _this.siblings('.dashboard-widget-toolbar-container').removeClass('display-none')
                                .siblings('.dashboard-widget-keyinfo-container').removeClass('display-none');
                            _this.find('table').hide();
                            dashboardChart.setOption(option);

                        } else if ($(m).attr('data-value') == 'table') {
                            _this.children('div').hide();
                            _this.siblings('.dashboard-widget-toolbar-container').addClass('display-none')
                                .siblings('.dashboard-widget-keyinfo-container').addClass('display-none');
                            var table = '<table class="table table-striped no-compare-table" style="display: block">\n' +
                                '                                <thead>\n' +
                                '                                    <tr>\n' +
                                '                                        <th>日期</th>\n' +
                                '                                        <th title="粉丝数的均值">粉丝数的均值</th>\n' +
                                '                                    </tr>\n' +
                                '                                </thead>\n' +
                                '                                <tbody>\n' +
                                '                                    <tr>\n' +
                                '                                        <td><span class="td-container">9-12</span></td>\n' +
                                '                                        <td><span class="td-container">4,437.06</span></td>\n' +
                                '                                    </tr>\n' +
                                '                                    <tr>\n' +
                                '                                        <td><span class="td-container">9-12</span></td>\n' +
                                '                                        <td><span class="td-container">4,437.06</span></td>\n' +
                                '                                    </tr>\n' +
                                '                                </tbody>\n' +
                                '                            </table>';
                            _this.append(table);
                        }
                    })
                })
            });

        }

        charts_type();
        window.onresize = function () {
            dashboardChart.resize();
            charts_type();
        };
})
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
												<span class="sertitle">按钮ID：</span>
												<input class="list-input1" type="text" name="vst_button_id" value="${cutPage._queryParam.vst_button_id }" />
											</li>
											<li>
												<span class="sertitle">按钮名称：</span>
												<input class="list-input1" type="text" name="vst_button_name" value="${cutPage._queryParam.vst_button_name }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_button_state" value="${cutPage._queryParam.vst_button_state }" />
												<select class="list-input1" id="vst_button_state" name="vst_button_state">
													<option value="">请选择</option>
													<option value="1">正常</option>
													<option value="2">禁用</option>
												</select>
											</li>
											<li>
												<span class="sertitle">事件：</span>
												<input class="list-input1" type="text" name="vst_button_onclick" value="${cutPage._queryParam.vst_button_onclick }" />
											</li>
										</ul>
										<c:choose>
						    				<c:when test="${hidden_search=='1' || hidden_search==1}">
												<div class="search-up">
													<input type="hidden" id="hidden_search" name="hidden_search" />
													<div class="search-up-button" onclick="javascript:search_open();">
														<img src="${ctx}/pub/listPages/images/search_open.png"/>
													</div>
												</div>
						    				</c:when>
						    				<c:otherwise>
						    					<div class="search-up">
													<input type="hidden" id="hidden_search" name="hidden_search" />
													<div class="search-up-button" onclick="javascript:search_open();">
														<img src="${ctx}/pub/listPages/images/search_close.png"/>
													</div>
												</div>
						    				</c:otherwise>
						    			</c:choose>
									</div>
									<div style="text-align: center;">
										<input type="submit" id="queryBtn" class="queryBtn" value="查询" onclick="javascript:validQuery();" />
										<input type="reset" class="resetBtn" value="重置" />
										<input type="button" id="doIndex" class="resetBtn" value="排序生效" onclick="makeIndex()" disabled="disabled" />
									</div>
								</div>
							</div>
						</div>
					</div>
					</section>
					<section class="content">
						<div class="row">
							<div class="col-xs-12">
								<div class="box">		
									<div class="sa-report">
										 <!--大更新加载中效果-->
										    <div id="ajax-loader">
										        <ul>
										            <li></li>
										            <li></li>
										            <li></li>
										            <li></li>
										            <li></li>
										            <li></li>
										        </ul>
										        <p>正在加载数据<!--{en}loading data--></p>
										    </div>
										  <!--大更新加载中效果-->
										
										  <!--小更新加载中效果-->
									        <section class="loader loader-in-widget display-none">
									            <span class="loader loader-circles"></span>
									        </section>
										  <!--小更新加载中效果-->
						                <section class="report-chart">
						                    <header>
						                        <div class="report-date-picker-wrap">
						                            <span class="icon-date-picker"></span>
						                            <input id="inputDaterangepicker" type="text" class="form-control" readonly="readonly"
						                                   data-container="body" data-placement="top" title="" data-original-title="事件发生的时间范围"
						                                   data-calendar-status-refresh-time="1505719340">
						                        </div>
						                        <div class="report-name-wrap">
						                            <h4 id="reportName" class="report-name">用户一周内进行任意事件的天数</h4>
						                        </div>
						                        <div class="report-config selector" id="inputDuration">
						                            <button type="button" class="btn btn-selector dropdown-toggle" data-toggle="dropdown"
						                                    data-value="week">一周内
						                            </button>
						                            <ul class="dropdown-menu" role="menu">
						                                <li style="display:none;"><a data-value="rollup">全部</a></li>
						                                <li><a data-value="day">一天内</a></li>
						                                <li class="active"><a data-value="week">一周内</a></li>
						                                <li><a data-value="month">一个月内</a></li>
						                            </ul>
						                        </div>
						                    </header>
						                    <div class="report-no-data" style="display:none;">
						                        <div class="report-no-data-msg">
						                            <h5>没有查找到数据</h5>
						                            <p>请尝试调整时间段或筛选条件</p>
						                        </div>
						                    </div>
						                </section>
						                <section class="report-chart retention-table-info" style="position: relative;">
						                    <div id="group-container" class="retention-by">
						                        <div class="group-control-item">
						
						                            <div class="selector property">
						                                <select class="btn-selector" style="display: none;">
						                                    <option value="" data-type="" data-dict="">用户行为日期</option>
						                                    <optgroup label="事件属性" data-icon="icon-event-property">
						                                        <option value="event.$Anything.$ip" data-type="string" data-dict="false">IP
						                                        </option>
						                                        <option value="event.$Anything.$country" data-type="string" data-dict="false">
						                                            国家
						                                        </option>
						                                        <option value="event.$Anything.$screen_width" data-type="number"
						                                                data-dict="false">屏幕宽度
						                                        </option>
						                                        <option value="event.$Anything.$screen_height" data-type="number"
						                                                data-dict="false">屏幕高度
						                                        </option>
						                                        <option value="event.$Anything.$os" data-type="string" data-dict="false">操作系统
						                                        </option>
						                                        <option value="event.$Anything.$os_version" data-type="string"
						                                                data-dict="false">操作系统版本
						                                        </option>
						                                        <option value="event.$Anything.$browser" data-type="string" data-dict="false">
						                                            浏览器
						                                        </option>
						                                        <option value="event.$Anything.$browser_version" data-type="string"
						                                                data-dict="false">浏览器版本
						                                        </option>
						                                        <option value="event.$Anything.$province" data-type="string" data-dict="false">
						                                            省份
						                                        </option>
						                                        <option value="event.$Anything.$city" data-type="string" data-dict="false">城市
						                                        </option>
						                                    </optgroup>
						                                    <optgroup label="用户属性" data-icon="icon-user-property">
						                                        <option value="user.guildID" data-type="string" data-dict="false">主播所属机构ID
						                                        </option>
						                                        <option value="user.guildName" data-type="string" data-dict="false">主播所属机构名称
						                                        </option>
						                                        <option value="user.yearOfBirth" data-type="number" data-dict="false">出生年份
						                                        </option>
						                                        <option value="user.$name" data-type="string" data-dict="false">姓名</option>
						                                        <option value="user.$utm_medium" data-type="string" data-dict="false">广告系列媒介
						                                        </option>
						                                        <option value="user.$utm_source" data-type="string" data-dict="false">广告系列来源
						                                        </option>
						                                        <option value="user.sex" data-type="string" data-dict="false">性别</option>
						                                        <option value="user.recentRechargeDay" data-type="datetime" data-dict="false">
						                                            最近充值时间
						                                        </option>
						                                        <option value="user.recentCostDay" data-type="datetime" data-dict="false">
						                                            最近花费时间
						                                        </option>
						                                        <option value="user.recentVisitDay" data-type="datetime" data-dict="false">
						                                            最近访问时间
						                                        </option>
						                                        <option value="user.$signup_time" data-type="datetime" data-dict="false">注册时间
						                                        </option>
						                                        <option value="user.$first_browser_language" data-type="string"
						                                                data-dict="false">浏览器语言
						                                        </option>
						                                        <option value="user.$utm_matching_type" data-type="string" data-dict="false">
						                                            渠道追踪匹配模式
						                                        </option>
						                                        <option value="user.birthday" data-type="string" data-dict="false">生日</option>
						                                        <option value="user.nickname" data-type="string" data-dict="false">用户昵称</option>
						                                        <option value="user.level" data-type="number" data-dict="false">用户等级</option>
						                                        <option value="user.userType" data-type="string" data-dict="false">用户类型</option>
						                                        <option value="user.$province" data-type="string" data-dict="false">省份</option>
						                                        <option value="user.$city" data-type="string" data-dict="false">城市</option>
						                                        <option value="user.email" data-type="string" data-dict="false">邮箱</option>
						                                        <option value="user.firstRechargeDay" data-type="datetime" data-dict="false">
						                                            首次充值时间
						                                        </option>
						                                        <option value="user.$first_referrer" data-type="string" data-dict="false">
						                                            首次前向地址
						                                        </option>
						                                        <option value="user.$first_referrer_host" data-type="string" data-dict="false">
						                                            首次前向域名
						                                        </option>
						                                        <option value="user.firstChannelSource" data-type="string" data-dict="false">
						                                            首次渠道来源
						                                        </option>
						                                        <option value="user.firstCostDay" data-type="datetime" data-dict="false">
						                                            首次花费时间
						                                        </option>
						                                        <option value="user.firstVisitDay" data-type="datetime" data-dict="false">
						                                            首次访问时间
						                                        </option>
						                                        <option value="user.$first_visit_time" data-type="datetime" data-dict="false">
						                                            首次访问时间
						                                        </option>
						                                    </optgroup>
						                                    <optgroup label="用户分群" data-icon="icon-profiling">
						                                        <option value="user.Lostfrompaymentprocessinjune" data-type="bool"
						                                                data-dict="false">6月充值流失人群
						                                        </option>
						                                        <option value="user.fenqun" data-type="bool" data-dict="false">fenqun</option>
						                                        <option value="user.zhubo" data-type="bool" data-dict="false">主播</option>
						                                        <option value="user.BestUser" data-type="bool" data-dict="false">优质用户</option>
						                                        <option value="user.lostfromlastpay" data-type="bool" data-dict="false">充值流失人群
						                                        </option>
						                                        <option value="user.wei" data-type="bool" data-dict="false">微分享</option>
						                                        <option value="user.campus" data-type="bool" data-dict="false">校园群体</option>
						                                        <option value="user.liushi" data-type="bool" data-dict="false">流失永固</option>
						                                        <option value="user.LOSEUEUE" data-type="bool" data-dict="false">流失用户1</option>
						                                        <option value="user.lostUser" data-type="bool" data-dict="false">流失用户分组</option>
						                                        <option value="user.ceshi" data-type="bool" data-dict="false">测试</option>
						                                        <option value="user.yuce1" data-type="number" data-dict="true">预测1</option>
						                                        <option value="user.gaochongzhipinlvyonghu" data-type="bool" data-dict="false">
						                                            高频花费用户
						                                        </option>
						                                    </optgroup>
						                                </select>
						                                <div class="">
						                                    <button class="btn-selector multiselect" data-toggle="dropdown"
						                                            aria-expanded="false"><span class="icon-user-property"></span><span
						                                            class="multiselect-selected-text">广告系列来源</span></button>
						                                    <ul class="multiselect-container dropdown-menu"
						                                        style="max-height: 490px; overflow-y: auto; overflow-x: hidden;">
						                                        <li class="multiselect-item filter" value="0"><input class="multiselect-search"
						                                                                                             type="text"
						                                                                                             placeholder="搜索"></li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="" data-dict="" data-type=""
						                                                    style="display: none;"> 用户行为日期</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item multiselect-group"><span
						                                                class="icon-event-property"></span><label>事件属性</label></li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$ip" data-dict="false"
						                                                    data-type="string" style="display: none;"> IP</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$country" data-dict="false"
						                                                    data-type="string" style="display: none;"> 国家</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$screen_width" data-dict="false"
						                                                    data-type="number" style="display: none;"> 屏幕宽度</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$screen_height"
						                                                    data-dict="false" data-type="number" style="display: none;">
						                                                屏幕高度</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$os" data-dict="false"
						                                                    data-type="string" style="display: none;"> 操作系统</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$os_version" data-dict="false"
						                                                    data-type="string" style="display: none;"> 操作系统版本</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$browser" data-dict="false"
						                                                    data-type="string" style="display: none;"> 浏览器</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$browser_version"
						                                                    data-dict="false" data-type="string" style="display: none;">
						                                                浏览器版本</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$province" data-dict="false"
						                                                    data-type="string" style="display: none;"> 省份</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="event.$Anything.$city" data-dict="false"
						                                                    data-type="string" style="display: none;"> 城市</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item multiselect-group"><span
						                                                class="icon-user-property"></span><label>用户属性</label></li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.guildID" data-dict="false"
						                                                    data-type="string" style="display: none;"> 主播所属机构ID</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.guildName" data-dict="false"
						                                                    data-type="string" style="display: none;"> 主播所属机构名称</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.yearOfBirth" data-dict="false"
						                                                    data-type="number" style="display: none;"> 出生年份</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$name" data-dict="false" data-type="string"
						                                                    style="display: none;"> 姓名</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$utm_medium" data-dict="false"
						                                                    data-type="string" style="display: none;"> 广告系列媒介</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item active">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$utm_source" data-dict="false"
						                                                    data-type="string" style="display: none;"> 广告系列来源</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.sex" data-dict="false" data-type="string"
						                                                    style="display: none;"> 性别</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.recentRechargeDay" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 最近充值时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.recentCostDay" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 最近花费时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.recentVisitDay" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 最近访问时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$signup_time" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 注册时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$first_browser_language" data-dict="false"
						                                                    data-type="string" style="display: none;"> 浏览器语言</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$utm_matching_type" data-dict="false"
						                                                    data-type="string" style="display: none;"> 渠道追踪匹配模式</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.birthday" data-dict="false"
						                                                    data-type="string" style="display: none;"> 生日</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.nickname" data-dict="false"
						                                                    data-type="string" style="display: none;"> 用户昵称</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.level" data-dict="false" data-type="number"
						                                                    style="display: none;"> 用户等级</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.userType" data-dict="false"
						                                                    data-type="string" style="display: none;"> 用户类型</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$province" data-dict="false"
						                                                    data-type="string" style="display: none;"> 省份</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$city" data-dict="false" data-type="string"
						                                                    style="display: none;"> 城市</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.email" data-dict="false" data-type="string"
						                                                    style="display: none;"> 邮箱</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.firstRechargeDay" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 首次充值时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$first_referrer" data-dict="false"
						                                                    data-type="string" style="display: none;"> 首次前向地址</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$first_referrer_host" data-dict="false"
						                                                    data-type="string" style="display: none;"> 首次前向域名</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.firstChannelSource" data-dict="false"
						                                                    data-type="string" style="display: none;"> 首次渠道来源</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.firstCostDay" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 首次花费时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.firstVisitDay" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 首次访问时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.$first_visit_time" data-dict="false"
						                                                    data-type="datetime" style="display: none;"> 首次访问时间</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item multiselect-group"><span
						                                                class="icon-profiling"></span><label>用户分群</label></li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.Lostfrompaymentprocessinjune"
						                                                    data-dict="false" data-type="bool" style="display: none;"> 6月充值流失人群</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.fenqun" data-dict="false" data-type="bool"
						                                                    style="display: none;"> fenqun</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.zhubo" data-dict="false" data-type="bool"
						                                                    style="display: none;"> 主播</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.BestUser" data-dict="false"
						                                                    data-type="bool" style="display: none;"> 优质用户</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.lostfromlastpay" data-dict="false"
						                                                    data-type="bool" style="display: none;"> 充值流失人群</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.wei" data-dict="false" data-type="bool"
						                                                    style="display: none;"> 微分享</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.campus" data-dict="false" data-type="bool"
						                                                    style="display: none;"> 校园群体</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.liushi" data-dict="false" data-type="bool"
						                                                    style="display: none;"> 流失永固</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.LOSEUEUE" data-dict="false"
						                                                    data-type="bool" style="display: none;"> 流失用户1</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.lostUser" data-dict="false"
						                                                    data-type="bool" style="display: none;"> 流失用户分组</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.ceshi" data-dict="false" data-type="bool"
						                                                    style="display: none;"> 测试</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.yuce1" data-dict="true" data-type="number"
						                                                    style="display: none;"> 预测1</label><span class=""></span></a>
						                                        </li>
						                                        <li class="multiselect-item">
						                                            <a tabindex="0"><span class=""></span><label class="radio"><input
						                                                    type="radio" value="user.gaochongzhipinlvyonghu" data-dict="false"
						                                                    data-type="bool" style="display: none;"> 高频花费用户</label><span
						                                                    class=""></span></a>
						                                        </li>
						                                    </ul>
						                                </div>
						                            </div>
						                            <span data-method="bucket" data-bucket-value=""
						                                  class="icon-setting report-bucket btn-icon btn-link" data-toggle="tooltip"
						                                  data-placement="top" title="" data-bucket-type="" style="display: none;"
						                                  data-original-title="选择如何分组"></span>
						
						                        </div>
						                    </div>
						                    <div id="tableContainer" class="table-wrap">
						                        <table class="table table-bordered heat-map">
						                            <thead>
						                            <tr>
						                                <th></th>
						                                <th>总人数</th>
						                                <th>至少2小时</th>
						                                <th>至少3小时</th>
						                                <th>至少4小时</th>
						                                <th>至少5小时</th>
						                                <th>至少6小时</th>
						                                <th>至少7小时</th>
						                                <th>至少8小时</th>
						                                <th>至少9小时</th>
						                                <th>至少10小时</th>
						                                <th>至少11小时</th>
						                                <th>至少12小时</th>
						                                <th>至少13小时</th>
						                                <th>至少14小时</th>
						                                <th>至少15小时</th>
						                                <th>至少16小时</th>
						                                <th>至少17小时</th>
						                                <th>至少18小时</th>
						                                <th>至少19小时</th>
						                                <th>至少20小时</th>
						                                <th>至少21小时</th>
						                                <th>至少22小时</th>
						                                <th>至少23小时</th>
						                                <th>至少24小时</th>
						                            </tr>
						                            </thead>
						                            <tbody>
						                            <tr data-index="0">
						                                <td class="by-overflow" style="width:200px;">
						                                    百度
						                                </td>
						                                <td style="width:100px;">
						                                    <span class="icon-user-line"></span>
						                                    <span data-method="user-list">11,691</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-4" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有8,040人平均每天进行了至少2 小时任意事件。">
						                                    <span data-method="user-list">8,040</span>
						                                    <span class="retention-ratio">68.8%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有410人平均每天进行了至少3 小时任意事件。">
						                                    <span data-method="user-list">410</span>
						                                    <span class="retention-ratio">3.5%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有60人平均每天进行了至少4 小时任意事件。">
						                                    <span data-method="user-list">60</span>
						                                    <span class="retention-ratio">0.5%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有5人平均每天进行了至少5 小时任意事件。">
						                                    <span data-method="user-list">5</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有3人平均每天进行了至少6 小时任意事件。">
						                                    <span data-method="user-list">3</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少7 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少8 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少9 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少10 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少11 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少12 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少13 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少14 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少15 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少16 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少17 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少18 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少19 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少20 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少21 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少22 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少23 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有0人平均每天进行了至少24 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                            </tr>
						                            <tr data-index="1">
						                                <td class="by-overflow" style="width:200px;">
						                                    36kr
						                                </td>
						                                <td style="width:100px;">
						                                    <span class="icon-user-line"></span>
						                                    <span data-method="user-list">4,582</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-4" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有3,145人平均每天进行了至少2 小时任意事件。">
						                                    <span data-method="user-list">3,145</span>
						                                    <span class="retention-ratio">68.6%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有171人平均每天进行了至少3 小时任意事件。">
						                                    <span data-method="user-list">171</span>
						                                    <span class="retention-ratio">3.7%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有23人平均每天进行了至少4 小时任意事件。">
						                                    <span data-method="user-list">23</span>
						                                    <span class="retention-ratio">0.5%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有2人平均每天进行了至少5 小时任意事件。">
						                                    <span data-method="user-list">2</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有1人平均每天进行了至少6 小时任意事件。">
						                                    <span data-method="user-list">1</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少7 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少8 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少9 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少10 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少11 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少12 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少13 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少14 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少15 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少16 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少17 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少18 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少19 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少20 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少21 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少22 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少23 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有0人平均每天进行了至少24 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                            </tr>
						                            <tr data-index="2">
						                                <td class="by-overflow" style="width:200px;">
						                                    今日头条
						                                </td>
						                                <td style="width:100px;">
						                                    <span class="icon-user-line"></span>
						                                    <span data-method="user-list">3,376</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-4" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有2,324人平均每天进行了至少2 小时任意事件。">
						                                    <span data-method="user-list">2,324</span>
						                                    <span class="retention-ratio">68.8%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有122人平均每天进行了至少3 小时任意事件。">
						                                    <span data-method="user-list">122</span>
						                                    <span class="retention-ratio">3.6%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有25人平均每天进行了至少4 小时任意事件。">
						                                    <span data-method="user-list">25</span>
						                                    <span class="retention-ratio">0.7%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有1人平均每天进行了至少5 小时任意事件。">
						                                    <span data-method="user-list">1</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少6 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少7 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少8 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少9 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少10 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少11 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少12 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少13 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少14 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少15 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少16 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少17 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少18 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少19 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少20 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少21 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少22 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少23 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有0人平均每天进行了至少24 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                            </tr>
						                            <tr data-index="3">
						                                <td class="by-overflow" style="width:200px;">
						                                    微信
						                                </td>
						                                <td style="width:100px;">
						                                    <span class="icon-user-line"></span>
						                                    <span data-method="user-list">2,347</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-4" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有1,614人平均每天进行了至少2 小时任意事件。">
						                                    <span data-method="user-list">1,614</span>
						                                    <span class="retention-ratio">68.8%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有77人平均每天进行了至少3 小时任意事件。">
						                                    <span data-method="user-list">77</span>
						                                    <span class="retention-ratio">3.3%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有12人平均每天进行了至少4 小时任意事件。">
						                                    <span data-method="user-list">12</span>
						                                    <span class="retention-ratio">0.5%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有1人平均每天进行了至少5 小时任意事件。">
						                                    <span data-method="user-list">1</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少6 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少7 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少8 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少9 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少10 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少11 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少12 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少13 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少14 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少15 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少16 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少17 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少18 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少19 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少20 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少21 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少22 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少23 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有0人平均每天进行了至少24 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                            </tr>
						                            <tr data-index="4">
						                                <td class="by-overflow" style="width:200px;">
						                                    地推
						                                </td>
						                                <td style="width:100px;">
						                                    <span class="icon-user-line"></span>
						                                    <span data-method="user-list">1,175</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-4" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有822人平均每天进行了至少2 小时任意事件。">
						                                    <span data-method="user-list">822</span>
						                                    <span class="retention-ratio">70%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有37人平均每天进行了至少3 小时任意事件。">
						                                    <span data-method="user-list">37</span>
						                                    <span class="retention-ratio">3.2%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有12人平均每天进行了至少4 小时任意事件。">
						                                    <span data-method="user-list">12</span>
						                                    <span class="retention-ratio">1%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-10" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有1人平均每天进行了至少5 小时任意事件。">
						                                    <span data-method="user-list">1</span>
						                                    <span class="retention-ratio">0.1%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少6 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少7 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少8 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少9 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少10 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少11 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少12 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少13 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少14 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少15 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少16 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少17 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少18 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少19 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少20 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少21 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少22 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少23 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                                <td style="width:50px; position:relative" class="hm-11" title="" data-toggle="tooltip"
						                                    data-placement="top"
						                                    data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有0人平均每天进行了至少24 小时任意事件。">
						                                    <span data-method="user-list">0</span>
						                                    <span class="retention-ratio">0%</span>
						                                </td>
						                            </tr>
						                            </tbody>
						                        </table>
						                        <div class="__div_fixed_column"
						                             style="position: absolute; top: 0px; overflow: hidden; width: 161px;">
						                            <table class="table table-bordered heat-map">
						                                <thead>
						                                <tr>
						                                    <th></th>
						                                    <th>总人数</th>
						                                    <th>至少2小时</th>
						
						
						                                </tr>
						                                </thead>
						                                <tbody>
						                                <tr data-index="0">
						                                    <td class="by-overflow" style="width:200px;">
						                                        百度
						                                    </td>
						                                    <td style="width:100px;">
						                                        <span class="icon-user-line"></span>
						                                        <span data-method="user-list">11,691</span>
						                                    </td>
						                                    <td style="width:50px; position:relative" class="hm-4" title=""
						                                        data-toggle="tooltip" data-placement="top"
						                                        data-original-title="在选定时间段内有11,691位广告系列来源是百度的人进行了任意事件。其中，有8,040人平均每天进行了至少2 小时任意事件。">
						                                        <span data-method="user-list">8,040</span>
						                                        <span class="retention-ratio">68.8%</span>
						                                    </td>
						
						
						                                </tr>
						                                <tr data-index="1">
						                                    <td class="by-overflow" style="width:200px;">
						                                        36kr
						                                    </td>
						                                    <td style="width:100px;">
						                                        <span class="icon-user-line"></span>
						                                        <span data-method="user-list">4,582</span>
						                                    </td>
						                                    <td style="width:50px; position:relative" class="hm-4" title=""
						                                        data-toggle="tooltip" data-placement="top"
						                                        data-original-title="在选定时间段内有4,582位广告系列来源是36kr的人进行了任意事件。其中，有3,145人平均每天进行了至少2 小时任意事件。">
						                                        <span data-method="user-list">3,145</span>
						                                        <span class="retention-ratio">68.6%</span>
						                                    </td>
						
						
						                                </tr>
						                                <tr data-index="2">
						                                    <td class="by-overflow" style="width:200px;">
						                                        今日头条
						                                    </td>
						                                    <td style="width:100px;">
						                                        <span class="icon-user-line"></span>
						                                        <span data-method="user-list">3,376</span>
						                                    </td>
						                                    <td style="width:50px; position:relative" class="hm-4" title=""
						                                        data-toggle="tooltip" data-placement="top"
						                                        data-original-title="在选定时间段内有3,376位广告系列来源是今日头条的人进行了任意事件。其中，有2,324人平均每天进行了至少2 小时任意事件。">
						                                        <span data-method="user-list">2,324</span>
						                                        <span class="retention-ratio">68.8%</span>
						                                    </td>
						
						
						                                </tr>
						                                <tr data-index="3">
						                                    <td class="by-overflow" style="width:200px;">
						                                        微信
						                                    </td>
						                                    <td style="width:100px;">
						                                        <span class="icon-user-line"></span>
						                                        <span data-method="user-list">2,347</span>
						                                    </td>
						                                    <td style="width:50px; position:relative" class="hm-4" title=""
						                                        data-toggle="tooltip" data-placement="top"
						                                        data-original-title="在选定时间段内有2,347位广告系列来源是微信的人进行了任意事件。其中，有1,614人平均每天进行了至少2 小时任意事件。">
						                                        <span data-method="user-list">1,614</span>
						                                        <span class="retention-ratio">68.8%</span>
						                                    </td>
						
						
						                                </tr>
						                                <tr data-index="4">
						                                    <td class="by-overflow" style="width:200px;">地推</td>
						                                    <td style="width:100px;">
						                                        <span class="icon-user-line"></span>
						                                        <span data-method="user-list">1,175</span>
						                                    </td>
						                                    <td style="width:50px; position:relative" class="hm-4" title=""
						                                        data-toggle="tooltip" data-placement="top"
						                                        data-original-title="在选定时间段内有1,175位广告系列来源是地推的人进行了任意事件。其中，有822人平均每天进行了至少2 小时任意事件。">
						                                        <span data-method="user-list">822</span>
						                                        <span class="retention-ratio">70%</span>
						                                    </td>
						                                  </tr>
						                                </tbody>
						                            </table>
						                        </div>
						                    </div>
						                    <div id="table-pagination"></div>
						                </section>
						            </div>
									<div class="sa-dashboard">
            <header class="sa-dashboard-head section-header">
                <div class="pull-right" data-email="no">
                    <div class="dropdown selector">
                        <button id="segmentation-unit" type="button"
                                class="btn btn-default btn-selector dropdown-toggle" data-toggle="dropdown"
                                data-value="day">
                            <span class="selected" data-value="day">天<!--{en}Day--></span>
                        </button>
                        <ul class="dropdown-menu pull-right" role="menu">
                            <li><a data-value="rollup">合计<!--{en}Total--></a></li>
                            <li><a data-value="minute">分钟<!--{en}Minute--></a></li>
                            <li><a data-value="hour">小时<!--{en}Hour--></a></li>
                            <li><a data-value="day">天<!--{en}Day--></a></li>
                            <li><a data-value="week">周<!--{en}Week--></a></li>
                            <li><a data-value="month">月<!--{en}Month--></a></li>
                        </ul>
                    </div>
                    <div class="btn-group">
                        <button id="my_dashboard_refresh_btn"
                                class="btn btn-default btn-icon"><span class="icon-refresh"></span></button>
                        <button class="btn btn-default btn-icon hide no-mobile" id="my_dashboard_export_btn">
                            <span class="icon-download"></span>
                        </button>
                        <button id="dashboard-edit" class="btn btn-default btn-icon hide no-mobile">
                            <span class="icon-edit"></span>
                        </button>
                        <button id="dashboard-email" class="btn btn-default btn-icon hide no-mobile">
                            <span class="icon-envelope"></span>
                        </button>
                    </div>
                    <button id="my_dashboard_add_btn" class="btn btn-primary btn-icon hide no-mobile">
                        <span class="icon-add"></span>
                    </button>
                </div>
                <div>
                    <span class="mr-10" id="my_dashboard_head_name">数据概览<!--{en}Dashboards--></span>
                    <div class="report-date-picker-wrap no-mobile">
                        <span class="icon-date-picker"></span>
                        <input id="dashboard-date-range" type="text" class="form-control params active"
                               readonly="readonly">
                    </div>
                </div>
            </header>
            <section class="sa-dashboard-content clearfix" id="my_dashboard_main">
                <article class="dashboard-widget" data-bookmarkid="9">
                    <header class="dashboard-widget-header">
                        <div class="dashboard-widget-header-in">
                            <h4 title="主播人均粉丝数"
                                data-href="/segmentation/#measures%5B0%5D%5Bevent_name%5D=personalDetailsPage&amp;measures%5B0%5D%5Baggregator%5D=AVG&amp;measures%5B0%5D%5Bfield%5D=event.personalDetailsPage.fansNumebr&amp;unit=day&amp;filter%5Bconditions%5D%5B0%5D%5Bfield%5D=user.userType&amp;filter%5Bconditions%5D%5B0%5D%5Bfunction%5D=equal&amp;filter%5Bconditions%5D%5B0%5D%5Bparams%5D%5B%5D=%E4%B8%BB%E6%92%AD&amp;chartsType=line&amp;sampling_factor=64&amp;from_date=2017-08-31&amp;to_date=2017-09-06&amp;bookmarkid=9&amp;tType=y&amp;ratio=n&amp;axis_config%5BisNormalize%5D=false&amp;rollup_date=false">
                                主播人均粉丝数</h4>
                            <div class="dashboard-widget-timerange" data-calendar-status-refresh-time="1504748643">
                                <span class="timerange">2017-8-31至2017-9-6</span>
                                <span class="icon-edit"></span>
                                <span class="icon-remove"></span>
                            </div>
                        </div>
                        <div class="dashboard-widget-config">
                            <div class="dashboard-widget-dropdown" data-email="no">
                                <div class="widget-config-top dropdown segmentation-unit">
                                    <div class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
    <span id="widgetUnit" class="selected" data-value="day">
      按天
    </span>
                                        <span class="icon-edit"></span><span class="icon-remove"></span>
                                    </div>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li class="multiselect-item">
                                            <a data-value="rollup">合计</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li class="disabled">
                                            <a data-value="minute">按分钟</a>
                                        </li>
                                        <li>
                                            <a data-value="hour">按小时</a>
                                        </li>
                                        <li class="active">
                                            <a data-value="day">按天</a>
                                        </li>
                                        <li>
                                            <a data-value="week">按周</a>
                                        </li>
                                        <li>
                                            <a data-value="month">按月</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="widget-config-bottom segmentation-chart-type" id="widgetType">
                                    <span data-method="widgetType" data-value="number" title="数字"
                                          class="icon-num btn-icon " style="display:none;"></span>
                                    <span data-method="widgetType" data-value="line" title="线图"
                                          class="icon-line-chart btn-icon active"></span>
                                    <span data-method="widgetType" data-value="column" title="柱图"
                                          class="icon-bar-chart btn-icon "></span>
                                    <span data-method="widgetType" data-value="pie" title="饼图"
                                          class="icon-pie-chart btn-icon " style="display:none;"></span>
                                    <span data-method="widgetType" data-value="table" title="表格"
                                          class="icon-table btn-icon "></span>
                                    <span data-method="widgetType" data-value="stack" title="累积"
                                          class="icon-stack-chart btn-icon " style="display:none;"></span>
                                </div>
                            </div>
                        </div>
                    </header>
                    <div class="dashboard-widget-content">
                        <div id="toolbar-container" class="dashboard-widget-toolbar-container">
                            <span class="dashboard-widget-toolbar-date-text">9-4</span>

                        </div>
                        <div id="keyinfo-container" class="dashboard-widget-keyinfo-container">
                            <ul class="dashboard-widget-key-wrap with-chart">
                                <li>
                                    <div class="dashboard-widget-key">5,242.85<span
                                            class="dashboard-widget-unit"></span>
                                    </div>
                                    <p class="dashboard-widget-key-caption">
                                    </p>
                                </li>
                            </ul>
                        </div>
                        <div id="widget-chart-container" class="dashboard-widget-chart"
                             style="-webkit-tap-highlight-color: transparent; user-select: none; position: relative; background: transparent;overflow: hidden;">
                        </div>
                    </div>
                </article>
            </section>

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
</script>
</html>