<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<%@include file="../share/biPlugin.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<script src="${ctx}/pub/plugins/echarts/echarts4.js"></script>
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
								<i class="fa fa-home"></i><span style="font-size:14px;">实时统计 > 分类播放统计 > 导出</span>
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
			
			<div class="content-roll">
				<form action="${ctx}/realMovieClassifyPlay/findRealMovieClassifyPlay.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 实时统计 > 分类播放统计
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
                                                <input id="layuiDateInputDate" type="text" class="list-input1" readonly="readonly" style="width: 450px!important;">
                                            </li>
											<li style="display: none;">
												<span class="sertitle">起始日期：</span>
												<input type="text" class="list-input1" id="startDate" name="startDate"
													   placeholder="yyyy-MM-dd HH:mm:ss" readonly="readonly"/>
											</li>
											<li style="display: none;">
												<span class="sertitle">结束日期：</span>
												<input type="text" class="list-input1" id="endDate" name="endDate"
													   placeholder="yyyy-MM-dd HH:mm:ss" readonly="readonly"/>
											</li>
											<li>
												<span class="sertitle">专区类型：</span>
												<select class="list-input1" id="vst_rmcp_special_type" name="vst_rmcp_special_type">
													<c:forEach items="${specialTypes}" var="specialType">
														<option value="${specialType.key }">${specialType.value }</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<span class="sertitle">影片分类：</span>
												<select class="list-input1" id="vst_rmcp_cid" name="vst_rmcp_cid">
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
			                        <h3 class="box-title" id="line-title"></h3>
			                        <div class="box-tools pull-right switch-form">
										<div class="switch-form-item">
											<label class="switch-form-label" style="box-sizing: content-box;">实时开关</label>
											<div class="switch-input-block">
												<input type="checkbox" lay-skin="switch" lay-filter="switchTest" lay-text="开|关">
												<div class="switch-unselect switch-form-switch" lay-skin="_switch"><em>关</em><i></i></div>
											</div>
										</div>
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
	
	<!--日历-->
	<script type="text/javascript">
        $(function () {
            laydate.render({
                elem: '#layuiDateInputDate', //指定元素
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                theme: 'molv',
                range: true,
                done: function(value, date, endDate){
                    $("#startDate").val(value.split(' - ')[0]);
                    $("#endDate").val(value.split(' - ')[1]);
                }
            });
            // 实时图开关点击事件
            $('.box-tools .switch-input-block .switch-form-switch[lay-skin="_switch"]').click(function () {
                if(!realSwitchAllow){
                    return false;
				}
                realSwitchAllow = false;
                // 清空5分钟定时器
                fiveMinute.clear();
				if($(this).hasClass('switch-form-onswitch')){
				    $(this).removeClass('switch-form-onswitch');
                    $('.box-tools .switch-input-block input[lay-skin="switch"]').prop("checked","flase");
                    $(this).find('em').html($('.box-tools .switch-input-block input[lay-skin="switch"]').attr('lay-text').split('|')[1]);

                    realQueryTimes = 0;
                    timerAllow = false;
                    line(storageLineData,storageStartTime,storageEndTime);
                    realSwitchAllow = true;
				}else{
                    $(this).addClass('switch-form-onswitch');
                    $('.box-tools .switch-input-block input[lay-skin="switch"]').prop("checked","true");
                    $(this).find('em').html($('.box-tools .switch-input-block input[lay-skin="switch"]').attr('lay-text').split('|')[0]);

                    timerAllow = true;
                    $("#line-chart").css({"display":"none"});
                    $("#loading_line").removeAttr("style");
                    realQueryLineData();
				}
            })
        })
	  	// 起止日期的初始值
	  	$("#layuiDateInputDate").val(getNDateTime("yyyy-MM-dd",0)+" 00:00:00"+" - "+getNDateTime("yyyy-MM-dd",0)+" 23:59:59");
	  	$("#startDate").val(getNDateTime("yyyy-MM-dd",0)+" 00:00:00");
	  	$("#endDate").val(getNDateTime("yyyy-MM-dd",0)+" 23:59:59");
	</script>
</body>

<script type="text/javascript">
	$(document).ready(function(){
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
		if($("#startDate").val() == '' || $("#endDate").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
            // 清空5分钟定时器
            fiveMinute.clear();

			ajaxGetReport();// 按日期统计(折线图)
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	// 按日期统计(折线图)
	function ajaxGetReport(){
	    // 将实时图开关关闭
        $('.box-tools .switch-input-block .switch-form-switch[lay-skin="_switch"]').removeClass('switch-form-onswitch');
        $('.box-tools .switch-input-block input[lay-skin="switch"]').prop("checked","flase");
        $('.box-tools .switch-input-block .switch-form-switch[lay-skin="_switch"] em').html($('.box-tools .switch-input-block input[lay-skin="switch"]').attr('lay-text').split('|')[1]);
        realQueryTimes = 0;
        timerAllow = false;

        $("#line-chart").parent().html('<div class="chart" id="line-chart"></div>')


		$("#loading_line").removeAttr("style");
		$("#line-chart").empty();

		var startTime = new Date($("#startDate").val().replace(/-/g,'/')).getTime()/1000;
		var endTime = new Date($("#endDate").val().replace(/-/g,'/')).getTime()/1000;

		var params = "startDay=" + moment(startTime*1000).format('YYYYMMDD');
		params += "&endDay=" + moment(endTime*1000).format('YYYYMMDD');
		params += "&startTime=" + startTime;
		params += "&endTime=" + endTime;
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_rmcp_special_type=" + $("#vst_rmcp_special_type").val();
		params += "&vst_rmcp_cid=" + $("#vst_rmcp_cid").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			//url : "${ctx}/report/json?code=real_movie_classify_play_line",
			url : "${ctx}/realMovieClassifyPlay/ajaxGetReportLine.action",
			dataType : "json",
			success : function(msg){
				$("#loading_line").attr("style","display:none");
				if(msg.code == 100){
                    line(msg,startTime,endTime);
					//储存查询的参数和数据
                    storageLineData = msg;
                    storageStartTime = startTime;
                    storageEndTime = endTime;
				}else{
					$("#line-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error : function(msg){
				$("#loading_line").attr("style","display:none");
			}
		});
	}

    var myChart = '';
	var timerAllow = false; //允许实时请求定时器
	var realSwitchAllow = true; //允许点击实时开关
	var realQueryTimes= 0; //切换成实时数据的请求次数
	var storageLineData = []; //储存静态的line查询的数据
	var storageStartTime = []; //储存静态的line查询的开始时间
	var storageEndTime = []; //储存静态的line查询的结束时间
	// 5分钟定时器
	var fiveMinute = {
	    timer:'',
		create:function () {
            fiveMinute.timer = setTimeout(function () {
                $('.box-tools .switch-input-block .switch-form-switch[lay-skin="_switch"]').click();
            },5*60*1000)
        },
		clear:function () {
			clearTimeout(fiveMinute.timer)
        }
	}

    //每二十秒请求一次数据
	function realQueryLineData() {
	    if(!timerAllow){
	        return false;
		}
		var realStartTime =parseInt((new Date().getTime()-60*60*1000)/1000);
		var realEndTime =parseInt((new Date().getTime())/1000);
		$.ajax({
			type : "POST",
			data : {
                startDay:moment(realStartTime*1000).format('YYYYMMDD'),
                endDay:moment(realEndTime*1000).format('YYYYMMDD'),
				startTime:realStartTime,
				endTime:realEndTime,
				pkgName:$("#pkgName").val(),
				vst_rmcp_special_type:$("#vst_rmcp_special_type").val(),
				vst_rmcp_cid:$("#vst_rmcp_cid").val()
			},
			cache : false,
			//url : "${ctx}/report/json?code=real_movie_classify_play_line",
			url : "${ctx}/realMovieClassifyPlay/ajaxGetReportLine.action",
			dataType : "json",
			success : function(msg){
			    if(realQueryTimes==0){
                    $("#line-chart").css({"display":"block"});
                    $("#loading_line").attr("style","display:none");
                    // 创建5分钟定时器
                    fiveMinute.create();
				}
                if(!timerAllow){
                    return false;
                }
				if(msg.code == 100){
                    line(msg,realStartTime,realEndTime);
                    realQueryTimes++;
                    setTimeout(function () {
                        realQueryLineData();
                    },20000)
				}else{
					$("#line-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
                realSwitchAllow = true;
			},
			error : function(msg){
                $("#loading_line").attr("style","display:none");
			}
		});

    }


    function line(msg,startTime,endTime){
        var data = msg.data;
        var dateArr = [];
        var xAxisTimeArr = []; //x轴的时间间隔
        var xAxisName = 0; // 0表示时间，1表示日期,2表示月份，3表示年,4表示分钟
        var xRotate = 0; //x轴刻度名字的角度
        var xInterval = 2; //x轴刻度的间隔
        var startTime = startTime;
        var endTime = endTime;
        var newTime = startTime;
        var vst_rmcp_vv = [];
        var vst_rmcp_vv1 = [];
        var vst_rmcp_uv = [];
        var vst_rmcp_uv1 = [];

        //判断查询的数据是否大于1天
        var cha=endTime-startTime;
        if(cha>24*60*60 && cha<30*24*60*60){
            //大于1天 小于1个月
            xInterval = 71;
            xAxisName = 1;
        }else if(cha>=30*24*60*60 && cha<12*30*24*60*60){
            //大于1天 小于1年
            xInterval = 71*30;
            xAxisName = 2;
        }else if(cha>=12*30*24*60*60){
            //大于1年
            xInterval = 71*30*12;
            xAxisName = 3;
        }else if(cha<=60*60){
            //小于等于1个小时
            xInterval = 20;
            xAxisName = 4;
        }
        if(xAxisName == 4){
            // 查询起止日期时间间隔的数组（间隔20秒）
            while (newTime<endTime){
                if(newTime%20==0){
                    xAxisTimeArr.push(moment(new Date(newTime*1000)).format('YYYY-MM-DD HH:mm:ss'))
                }
                newTime++;
            }
            $.each(data,function(i,n){
                dateArr.push(n.vst_rmcp_date.toString().slice(0,4)+'-'+n.vst_rmcp_date.toString().slice(4,6)+'-'+n.vst_rmcp_date.toString().slice(6,8)+' '+(n.vst_rmcp_hour< 10 ? "0"+n.vst_rmcp_hour:n.vst_rmcp_hour)+ ':' + (n.vst_rmcp_minute< 10 ? "0"+n.vst_rmcp_minute:n.vst_rmcp_minute)+ ':' + (n.vst_rmcp_seconds< 10 ? "0"+n.vst_rmcp_seconds:n.vst_rmcp_seconds));
                vst_rmcp_vv.push(n.vst_rmcp_vv);
                vst_rmcp_uv.push(n.vst_rmcp_uv);
            });
        }else{
            // 查询起止日期时间间隔的数组（间隔20分钟）
            while (newTime<endTime){
                xAxisTimeArr.push(moment(new Date(newTime*1000)).format('YYYY-MM-DD HH:mm'))
                newTime += 20*60;
            }
            // 对请求回来的数据进行筛选（分钟为20，秒钟为00的数据）
            $.each(data,function(i,n){
                if(n.vst_rmcp_minute % 20 == 0 && n.vst_rmcp_seconds == 0) {
                    dateArr.push(n.vst_rmcp_date.toString().slice(0,4)+'-'+n.vst_rmcp_date.toString().slice(4,6)+'-'+n.vst_rmcp_date.toString().slice(6,8)+' '+(n.vst_rmcp_hour< 10 ? "0"+n.vst_rmcp_hour:n.vst_rmcp_hour)+ ':' + (n.vst_rmcp_minute< 10 ? "0"+n.vst_rmcp_minute:n.vst_rmcp_minute));
                    vst_rmcp_vv.push(n.vst_rmcp_vv);
                    vst_rmcp_uv.push(n.vst_rmcp_uv);
                }
            });
        }

        // 将筛选的数据按照 起止日期事件的间隔xAxisTimeArr进行排序，没有数据的补0
        $.each(xAxisTimeArr,function (i,v) {
            var hasData = false;
            var hasDataData = [0,0];
            $.each(dateArr,function (index,value) {
                if(v == value){
                    hasData = true;
                    hasDataData[0] = vst_rmcp_vv[index];
                    hasDataData[1] = vst_rmcp_uv[index];
                    return false;
                }
            })
            vst_rmcp_vv1[i]=hasDataData[0];
            vst_rmcp_uv1[i]=hasDataData[1];
        })

        if(realQueryTimes==0){
            $("#line-chart").parent().html('<div class="chart" id="line-chart"></div>')
            $("#line-chart").css("height","300px");
            myChart = echarts.init(document.getElementById('line-chart'));
            $(window).resize(function () {
                myChart.resize();
            })
		}

        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: '#ddd'
                    }
                },
                backgroundColor: 'rgba(255,255,255,1)',
                padding: [5, 10],
                textStyle: {
                    color: '#7588E4',
                },
                extraCssText: 'box-shadow: 0 0 5px rgba(0,0,0,0.3)'
            },
            legend: {
                show: true,
                shadowColor: "#999",
                shadowBlur: 5,
                shadowOffsetX: 5,
                data: ['播放量','独立用户'],
                top: '5%',
                textStyle: {
                    color: '#999',
                    fontSize: 14
                },
                itemHeight: 10
            },
            grid: {
                left: '60px',
                right: '40px'
            },
            xAxis: [{
                type: 'category',
                data: xAxisTimeArr,
                boundaryGap: false,
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: true
                },
                axisLine: {
                    lineStyle: {
                        color: '#ccc'
                    }
                },
                axisLabel: {
                    margin: 10,
                    textStyle: {
                        fontSize: 14,
                        color:'#666'
                    },
                    interval:xInterval,
                    rotate:xRotate,
                    formatter: function (value, index) {
                        if(xAxisName==0){
                            // 显示小时
                            return value.split(' ')[1]
                        }else if(xAxisName==1){
                            var getDay = new Date(value.replace(/-/g, '/')).getDay();
                            var currentDay = value.split(' ')[0].split('-')[1]+'-'+value.split(' ')[0].split('-')[2];
                            // 显示月-日+周末
                            if(getDay == 0){
                                return currentDay + "{weekend| 周日}";
                            }else if(getDay == 6){
                                return currentDay + "{weekend| 周六}";
                            }else{
                                return currentDay;
                            }
                        }else if(xAxisName==2){
                            // 显示月
                            return value.split(' ')[0].split('-')[1]
                        }else if(xAxisName==3){
                            // 显示年
                            return value.split(' ')[0].split('-')[0]
                        }else if(xAxisName==4){
                            return value.split(' ')[1].split(':')[0]+':'+value.split(' ')[1].split(':')[1]
                        }
                    },
                    rich:{
                        weekend:{
                            color: 'green',
						}
					}

                }
            }],
            yAxis: {
                type: 'value',
                splitLine: {
                    show: true,
                    lineStyle: {
                        color:'#eee'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#ccc'
                    }
                },
                axisLabel: {
                    margin: 10,
                    textStyle: {
                        fontSize: 14,
                        color:'#666'
                    }
                }
            },
            series: [{
                name: '播放量',
                type: 'line',
                smooth: true,
                showSymbol: false,
                symbol: 'circle',
                symbolSize: 1,
                data: vst_rmcp_vv1,
                itemStyle:{
                    color:'#00a7d0'
                }
            }, {
                name: '独立用户',
                type: 'line',
                smooth: true,
                showSymbol: false,
                symbol: 'circle',
                symbolSize: 1,
                data: vst_rmcp_uv1
            }]
        }
        myChart.setOption(option);
    }
	
	// 查询数据(显示分页信息)
	function ajaxGetCutPage(){
		$("#move_loading").removeAttr("style");
		$("#move").empty();
		
		var startTime = new Date($("#startDate").val().replace(/-/g,'/')).getTime()/1000;
		var endTime = new Date($("#endDate").val().replace(/-/g,'/')).getTime()/1000;
		
		var params = "orderBy=" + $("#orderBy").val() + "&order=" + $("#order").val();
		params += "&currPage=" + $("#currPage").val() + "&singleCount=" + $("#singleCount").val();
		params += "&startTime=" + startTime;
		params += "&endTime=" + endTime;
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_rmcp_special_type=" + $("#vst_rmcp_special_type").val();
		params += "&vst_rmcp_cid=" + $("#vst_rmcp_cid").val();

		$.ajax({
			type : "POST",
			data : params,
			async : true,
			cache : false,
			url : "${ctx}/report/json?code=real_movie_classify_play_table",
			dataType : "json",
			success : function(msg){
				$("#move_loading").attr("style","display:none");
				if(msg.code == 100){
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
		var totalCount = msg.info.totalCount;
		var singleCount = msg.info.singleCount;
		$("#currPage").val(msg.info.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='10%'>日期</th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_special_type' id='vst_rmcp_special_type2' order='desc'>专区类型</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_cid' id='vst_rmcp_cid2' order='desc' style='color:orange'>影片分类</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_vv' id='vst_rmcp_vv2' order='desc'>播放量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_uv' id='vst_rmcp_uv2' order='desc'>独立用户</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_avg' id='vst_rmcp_avg2' order='desc'>人均播放量</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_playtime' id='vst_rmcp_playtime2' order='desc'>播放时长(小时)</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_rmcp_playtime_avg' id='vst_rmcp_playtime_avg2' order='desc'>人均播放时长(分钟)</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";
			
			var date = parseDate(n.vst_rmcp_date+'') + " " + _formatFull(n.vst_rmcp_hour) + ":" + _formatFull(n.vst_rmcp_minute) + ":" + _formatFull(n.vst_rmcp_seconds);
			table += "<td>"+date+"</td>";
			table += "<td>"+getMapValue('${specialTypes}', n.vst_rmcp_special_type)+"</td>";
			table += "<td data-title='新增时间："+formatDate(n.vst_rmcp_addtime, "yyyy-MM-dd HH:mm:ss")
						+"<br>创建人："+toString(n.vst_rmcp_creator)
						+"<br>修改时间："+formatDate(n.vst_rmcp_uptime, "yyyy-MM-dd HH:mm:ss")
						+"<br>修改人："+toString(n.vst_rmcp_updator)
						+"<br>备注："+toString(n.vst_rmcp_summary)+"' class='enlargeTextTitle'>"
					+getMapValue('${classifys}', n.vst_rmcp_cid)+"</td>";
			
			table += "<td>"+formatNumber(n.vst_rmcp_vv)+"</td>";
			table += "<td>"+formatNumber(n.vst_rmcp_uv)+"</td>";
			table += "<td>"+formatNumber(n.vst_rmcp_avg)+"</td>";
			table += "<td>"+toHour(n.vst_rmcp_playtime*1000)+"</td>";
			table += "<td>"+toMinute(n.vst_rmcp_playtime_avg*1000)+"</td>";
			
			table += "</tr>";
		});
		table+="</tbody></table>";
		$("#move").html(table);
		showOrder(orderBy, order);
	}

	/****************************导出******************************/
	// 导出数据
	function button_export(){
		var startTime = new Date($("#startDate").val().replace(/-/g,'/')).getTime()/1000;
		var endTime = new Date($("#endDate").val().replace(/-/g,'/')).getTime()/1000;
		
		if(!isNotEmpty($("#startDate").val()) || !isNotEmpty($("#endDate").val()) || (endTime-startTime) > 7948800){
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
					var startTime = new Date($("#startDate").val().replace(/-/g,'/')).getTime()/1000;
					var endTime = new Date($("#endDate").val().replace(/-/g,'/')).getTime()/1000;
					
					var params = "startTime=" + startTime + "&endTime=" + endTime;
					params += "&pkgName=" + $("#pkgName").val();
					params += "&vst_rmcp_special_type=" + $("#vst_rmcp_special_type").val();
					params += "&vst_rmcp_cid=" + $("#vst_rmcp_cid").val();
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/realMovieClassifyPlay/exportData.action?" + params
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
</script>
</html>