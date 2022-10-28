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
  	
  	// 新增
	function button_add(){
		$("#listForm").attr("action", "${ctx}/sysButton/toAdd.action");
		$("#listForm").submit();
	}
	
	// 修改
	function button_edit(){
		var modifyChecked="";			
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else if(modifyChecked.indexOf(",") != -1){
	    	alert("每次最多选择一条记录进行该操作!");
			return false;
	    }else{
	    	$("#recordId").val(modifyChecked);
	        $("#listForm").attr("action", "${ctx}/sysButton/toEdit.action");
			$("#listForm").submit();
	    }
	}
	
	// 批量删除
	function button_delete(){
		var modifyChecked="";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
		});
	    modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
	    if(modifyChecked==""){
	       	alert("您还没有选择记录!");
			return false;
	    }else{
	    	if(window.confirm("您确定要删除所选的"+modifyChecked.split(',').length+"条记录吗?")){
		    	$("#recordId").val(modifyChecked);
		        $("#listForm").attr("action", "${ctx}/sysButton/deleteButton.action");
				$("#listForm").submit();
			}
	    }
	}
	
	// 批量禁用
	function button_stop(){
		var modifyChecked="";
		var state = "";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
			state = state+ $(this).attr("state") + ",";
		});
		modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
		state = state.substring(0,state.length-1);
		if(modifyChecked == ""){
			alert("您还没有选中记录！");
			return false;
		}
		var states = state.split(",");
		for(var i=0; i<states.length; i++){
			if(states[i] == 2){
				alert("所选记录中存在已禁用的记录不能再禁用，请检查！");
				return false;
			}
		}
		if(confirm("您确定要禁用所选的"+modifyChecked.split(',').length+"条记录吗？")){
			$("#recordId").val(modifyChecked);
			$("#recordState").val(2);
			$("#listForm").attr("action", "${ctx}/sysButton/modifyButtonState.action");
			$("#listForm").submit();
		}
	}
	
	// 批量启用
	function button_resume(){
		var modifyChecked="";
		var state = "";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() + ",";
			state = state+ $(this).attr("state") + ",";
		});
		modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
		state = state.substring(0,state.length-1);
		if(modifyChecked == ""){
			alert("您还没有选中记录！");
			return false;
		}
		var states = state.split(",");
		for(var i=0; i<states.length; i++){
			if(states[i] == 1){
				alert("所选记录中存在已启用的记录不能再启用，请检查！");
				return false;
			}
		}
		if(confirm("您确定要启用所选的"+modifyChecked.split(',').length+"条记录吗？")){
			$("#recordId").val(modifyChecked);
			$("#recordState").val(1);
			$("#listForm").attr("action", "${ctx}/sysButton/modifyButtonState.action");
			$("#listForm").submit();
		}
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
                    startDate: moment().subtract(1, 'hours'),
                    endDate: moment(),
                    maxDate: moment(),
                    alwaysShowCalendars: true
                },
                function (start, end) {
                    if (!ele.html()) {
                        ele.val(start.format('YYYY-M-DD') + '至' + end.format('YYYY-M-DD'));
                        $('.dashboard-widget-timerange').html(start.format('YYYY-M-DD') + ' 至 ' + end.format('YYYY-M-DD'));
                    } else {
                        ele.html(start.format('YYYY-M-DD') + '至' + end.format('YYYY-M-DD'));
                    }
                },
                ele.val(moment().subtract(2, 'hours').format('YYYY-M-DD') + '至' + moment().format('YYYY-M-DD'))
            ).bind('datepicker-apply', function (event, obj) {
                console.log(obj);
            });
            //初始化显示当前时间
            if (!ele.html()) {
                ele.val(moment().subtract(2, 'hours').format('YYYY-M-DD') + '至' + moment().format('YYYY-M-DD'));
            } else {
                ele.html(moment().subtract(2, 'hours').format('YYYY-M-DD') + '至' + moment().format('YYYY-M-DD'));
            }
            $('.daterangepicker .ranges ul li:nth-child(1)').addClass('active');
        },
     }
     g_fun.init_daterangepicker($('#inputDate'));
     
    });
</script>


	</head>

	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<%@include file="../share/header.jsp"%>
			<%@include file="../share/leftMenu.jsp"%>
			<div class="content-wrapper" style="background-color: #ecf0f5;">
				<div class="content-roll">
					<form action="${ctx}/sysButton/findButtons.action" id="listForm"
						method="post">
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
												<input class="list-input1" type="text" name="vst_button_id"
													value="${cutPage._queryParam.vst_button_id }" />
											</li>
											<li>
												<span class="sertitle">按钮名称：</span>
												<input class="list-input1" type="text"
													name="vst_button_name"
													value="${cutPage._queryParam.vst_button_name }" />
											</li>
											<li>
												<span class="sertitle">状态：</span>
												<input type="hidden" id="hidden_button_state"
													value="${cutPage._queryParam.vst_button_state }" />
												<select class="list-input1" id="vst_button_state"
													name="vst_button_state">
													<option value="">
														请选择
													</option>
													<option value="1">
														正常
													</option>
													<option value="2">
														禁用
													</option>
												</select>
											</li>
											<li>
												<span class="sertitle">事件：</span>
												<input class="list-input1" type="text"
													name="vst_button_onclick"
													value="${cutPage._queryParam.vst_button_onclick }" />
											</li>
											</ul>
											<c:choose>
												<c:when test="${hidden_search=='1' || hidden_search==1}">
													<div class="search-up">
														<input type="hidden" id="hidden_search"
															name="hidden_search" />
														<div class="search-up-button"
															onclick="javascript:search_open();">
															<img src="${ctx}/pub/listPages/images/search_open.png" />
														</div>
													</div>
												</c:when>
												<c:otherwise>
													<div class="search-up">
														<input type="hidden" id="hidden_search"
															name="hidden_search" />
														<div class="search-up-button"
															onclick="javascript:search_open();">
															<img src="${ctx}/pub/listPages/images/search_close.png" />
														</div>
													</div>
												</c:otherwise>
											</c:choose>
										</div>
										<div style="text-align: center;">
											<input type="submit" id="queryBtn" class="queryBtn"
												value="查询" onclick="javascript:validQuery();" />
											<input type="reset" class="resetBtn" value="重置" />
											<input type="button" id="doIndex" class="resetBtn"
												value="排序生效" onclick="makeIndex()" disabled="disabled" />
										</div>
									</div>
								</div>
							</div>
						</div>
						</section>

						<section class="content">
						<div class="row">
							<div class="col-xs-12">
								<div class="box ">
									<!--神策事件分析-->
									<section class="report-chart segmentation-chart">
									<header>
									<div class="report-date-picker-wrap">
										<span class="icon-date-picker"></span>
										<input id="inputDate" type="text" class="form-control"
											readonly="readonly" data-placement="top"
											data-container="body" title=""
											data-original-title="事件发生的时间范围"
											data-calendar-status-refresh-time="1505894693">
									</div>
									<div class="report-name-wrap" style="width: calc(100% - 363px) !important;">
										<h4 id="reportName" class="report-name">
											任意事件的总次数
										</h4>
									</div>
									<div class="report-config btn-group">
										
										<div class="dropdown">
											<button id="charts-type" type="button"
												class="btn-selector dropdown-toggle" data-toggle="dropdown"
												data-value="stack">
												线图
											</button>
											<ul id="chartsTypeul" class="dropdown-menu pull-right" role="menu" style="border:1px solid rgba(0,0,0,0.15);">
												<li class="active">
													<a data-value="line" class="line"><span data-value="line"
														class="icon-line-chart"></span><span>线图</span>
													</a>
												</li>
												<li>
													<a data-value="bar" class="bar"><span data-value="bar"
														class="icon-bar-chart"></span><span>柱图</span>
													</a>
												</li>
												<li>
													<a data-value="pie" class="pie"><span data-value="pie"
														class="icon-pie-chart"></span><span>饼图</span>
													</a>
												</li>
											</ul>
										</div>
										<div class="dropdown">
											<button id="input-unit" type="button"
												class="btn-selector dropdown-toggle" data-toggle="dropdown"
												data-value="minute">
												<span id="inputUnit" class="selected" data-value="day">按分钟</span>
											</button>
											<ul class="dropdown-menu pull-right" role="menu" style="border:1px solid rgba(0,0,0,0.15);">
												<li class="active">
													<a data-value="minute">按分钟</a>
												</li>
												<li>
													<a data-value="hour">按小时</a>
												</li>
												<li>
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
									</div>
									</header>
									<div class="report-no-data" style="display: none;">
										<div class="report-no-data-msg">
											<h5>
												没有查找到数据
											</h5>
											<p>
												请尝试调整时间段或筛选条件
											</p>
										</div>
									</div>
									</section>
									
									
									<!-- 图表开始 -->
									<div id="chart" style="height:521px;"></div>
									<!-- 图表结束 -->
									

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
	
		
	<script src="${ctx}/pub/plugins/echarts/eventChart.js"></script>
	
</html>