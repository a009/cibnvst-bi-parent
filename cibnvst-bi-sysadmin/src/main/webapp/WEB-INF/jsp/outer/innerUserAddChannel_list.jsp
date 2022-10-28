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

			<!-- 修改弹窗 -->
			<div class="modal fade" id="edit_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog modalGrap">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h4 class="modal-title">
								<i class="fa fa-home"></i><a href="#">市场数据</a><span>
									> 新增渠道用户(对内) > 修改</span>
								</section>
							</h4>
						</div>
						<form id="editBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
									<td class="tright" width="20%">新增用户：</td>
									<td width="50%">
										<input class="list-input" id="vst_uac_amount4" name="vst_uac_amount" />
										<span class="red">*</span>
										<span id="newAmount" style="font-weight: bold; color: red;"></span>
									</td>
									<td width="28%"><span id="vst_uac_amount4Tip"></span></td>
								</tr>
								<tr>
									<td class="tright" width="20%">调整系数：</td>
									<td width="50%">
										<input class="list-input" id="vst_uac_modulus4" name="vst_uac_modulus" value="1" />
										<span class="red">*</span>
									</td>
									<td width="28%"><span id="vst_uac_modulus4Tip"></span></td>
								</tr>
							</table>
							<div style="padding-bottom:10px; margin-right:10px; text-align:right;">
								<input type="submit" class="btnSubmit" id="editSubmit" value="提交" />
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- 修改弹窗 -->

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
									> 新增渠道用户(对内) > 批量修改</span>
								</section>
		                  	</h4>
		                </div>
		                <form id="batchUpdateBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
							 		<td class="tright" width="20%">调整系数：</td>
							 		<td width="50%">
							 			<input class="list-input" id="vst_uac_modulus1" name="vst_uac_modulus" value="0.35" />
							 			<span class="red">*</span>
							 		</td>
							 		<td width="28%"><span id="vst_uac_modulus1Tip"></span></td>
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
		    
		    <!-- 导入弹窗 -->
		    <div class="modal fade" id="import_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		        <div class="modal-dialog modalGrap">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                    	&times;
		                  	</button>
		                  	<h4 class="modal-title">
		                     	<i class="fa fa-home"></i><a href="#">市场数据</a><span>
									> 新增渠道用户(对内) > 导入</span>
								</section>
		                  	</h4>
		                </div>
		                <form id="importBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
							 		<td class="tright" width="20%">日期：</td>
							 		<td width="50%">
							 			<input type="text" class="list-input" id="vst_uac_date" name="vst_uac_date"
											placeholder="yyyy-MM-dd" readonly="readonly" />
							 		</td>
							 		<td width="28%"><span id="vst_uac_dateTip"></span></td>
							 	</tr>
							</table>
							<div style="padding-bottom:10px; margin-right:10px; text-align:right;">
								<button type="submit" class="btn btn-primary" id="importSubmit" data-loading-text="导入中..." autocomplete="off">提交</button>
							</div>
						</form>
		            </div>
		        </div>
		    </div>
		    <!-- 导入弹窗 -->
		    
		    <!-- 同步弹窗 -->
		    <div class="modal fade" id="sync_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		        <div class="modal-dialog modalGrap">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                    	&times;
		                  	</button>
		                  	<h4 class="modal-title">
		                     	<i class="fa fa-home"></i><a href="#">市场数据</a><span>
									> 新增渠道用户(对内)统计 > 同步</span>
								</section>
		                  	</h4>
		                </div>
		                <form id="syncBoxForm" method="post" enctype="multipart/form-data">
							<table class="line03">
								<tr>
							 		<td class="tright" width="20%">开始日期：</td>
							 		<td width="50%">
							 			<input type="text" class="list-input" id="startDate" name="startDate"
											placeholder="yyyy-MM-dd" readonly="readonly" />
							 		</td>
							 		<td width="28%"><span id="startDateTip"></span></td>
							 	</tr>
							 	<tr>
							 		<td class="tright">结束日期：</td>
							 		<td>
							 			<input type="text" class="list-input" id="endDate" name="endDate"
											placeholder="yyyy-MM-dd" readonly="readonly" />
							 		</td>
							 		<td><span id="endDateTip"></span></td>
							 	</tr>
							</table>
							<div style="padding-bottom:10px; margin-right:10px; text-align:right;">
								<button type="submit" class="btn btn-primary" id="syncSubmit" data-loading-text="同步中..." autocomplete="off">提交</button>
							</div>
						</form>
		            </div>
		        </div>
		    </div>
		    <!-- 同步弹窗 -->
			
			<div class="content-roll">
				<form action="${ctx}/innerUserAddChannel/findInnerUserAddChannel.action" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 市场数据 > 新增渠道用户(对内)统计
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
												<input class="list-input1" type="text" id="vst_uac_channel" name="vst_uac_channel" />
											</li>
											<li>
												<span class="sertitle">审核状态：</span>
												<select class="list-input1" id="vst_uac_state" name="vst_uac_state">
													<option value="">请选择</option>
													<option value="1">未审核</option>
													<option value="2">已审核</option>
												</select>
											</li>
											<li>
												<span class="sertitle">修改人：</span>
												<input class="list-input1" type="text" id="vst_uac_updator" name="vst_uac_updator" />
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

					<!-- 统计图 -->
					<section class="content">
						<div class="row">
							<div class="col-xs-12">
								<div class="box box-primary">
									<div class="box-body chart-responsive">
										<ul class="nav nav-pills" role="tablist">
											<li role="presentation" style="float: right;"><a href="#tab-pie" aria-controls="messages" role="tab" data-toggle="tab">饼状图</a></li>
											<li role="presentation" class="active" style="float: right;"><a href="#tab-bar" aria-controls="profile" role="tab" data-toggle="tab">柱状图</a></li>
											<li role="presentation" style="float: right;"><a href="#tab-line" aria-controls="home" role="tab" data-toggle="tab">折线图</a></li>
										</ul>
										<div class="tab-content" style="width: 100%;">
											<!-- LINE CHART -->
											<div role="tabpanel" class="tab-pane" id="tab-line" style="width: 100%;">
												<div id="loading_line" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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
											<!-- BAR CHART -->
											<div role="tabpanel" class="tab-pane active" id="tab-bar" style="width: 100%;">
												<div id="loading_bar" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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
											<!-- PIE CHART -->
											<div role="tabpanel" class="tab-pane" id="tab-pie" style="width: 100%;">
												<div id="loading_pie" class="loding_img" style="display: none;"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
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
										</div>
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
		laydate.render({
		  	elem: '#vst_uac_date', //指定元素
		  	type: 'date',
		  	format: 'yyyy-MM-dd',
		  	theme: 'molv'
	  	});
	  	
		laydate.render({
		  	elem: '#startDate', //指定元素
		  	type: 'date',
		  	format: 'yyyy-MM-dd',
		  	theme: 'molv'
	  	});
	  	
	  	laydate.render({
		  	elem: '#endDate', //指定元素
		  	type: 'date',
		  	format: 'yyyy-MM-dd',
		  	theme: 'molv'
	  	});
	  	
	  	$("#vst_uac_date").val(getNDateTime("yyyy-MM-dd",0));
	  	$("#startDate").val(getNDateTime("yyyy-MM-dd",0));
	  	$("#endDate").val(getNDateTime("yyyy-MM-dd",0));
	</script>
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
		$("#vst_uac_channel").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$("#vst_uac_updator").keydown(function(event){
        	if(event.keyCode == "13"){
        		button_getdata();
        	}
    	});
		$.enlargePic();
		//切换tab重绘highcharts图
		$('ul.nav > li').click(function () {
			setTimeout(function () {
				$('#line-chart').highcharts().reflow();
				$('#bar-chart').highcharts().reflow();
				$('#pie-chart').highcharts().reflow();
			},50)
		});
	});

	//点击查询按钮
	function button_getdata(){
		if($("#startDay").val() == '' || $("#endDay").val() == ''){
			alert("查询日期不可为空！");
		}else{
			//当前页置1
			$("#currPage").val(1);
			ajaxGetReport();// 按日期统计(折线图)
			ajaxGetReportByDim();// 按维度统计
			ajaxGetCutPage();// 查询数据(显示分页信息)
		}
	}

	// 删除
	function button_delete(){
		var startDay = $("#startDay").val();
		var endDay = $("#endDay").val();
		if(startDay == '' || endDay == ''){
			alert("必选指定日期范围进行删除！");
		}else{
			if(confirm("确认要删除当前选定条件的数据吗？")){
		        var params = "startDay="+startDay+"&endDay="+endDay
						+"&vst_uac_channel="+$("#vst_uac_channel").val()
						+"&vst_uac_state="+$("#vst_uac_state").val()
						+"&vst_uac_updator="+$("#vst_uac_updator").val()
	        			+"&moduleId="+$("#moduleId").val();
		        $.ajax({
					type: "POST",
					data: params,
					async : true,
					cache : false,
					url:"${ctx}/innerUserAddChannel/ajaxDeleteData.action",
					dataType:"json",
					success:function(msg){
						if(msg.code == 100){
							alert("删除数据成功"+msg.result+"条，请点击查询刷新数据！");
						}else{
							alert("删除数据失败！");
						}
					},
					error:function(msg){
						alert(msg);
					}
				});
			}
		}
	}

	// 审核
	function button_audit(){
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
				alert("所选记录中存在已审核的记录不能再审核，请检查！");
				return false;
			}
		}
		if(confirm("您确定要审核所选的"+modifyChecked.split(',').length+"条记录吗？")){
			var params = "recordId=" + modifyChecked + "&recordState=2" + "&moduleId=" + $("#moduleId").val();
			$.ajax({
				type: "POST",
				data: params,
				async : true,
				cache : false,
				url:"${ctx}/innerUserAddChannel/ajaxAuditData.action",
				dataType:"json",
				success:function(msg){
					if(msg.code == 100){
						alert("审核成功，请点击查询刷新数据！");
					}else{
						alert("审核失败！");
					}
				},
				error:function(msg){
					alert(msg);
				}
			});
		}
	}
	
	// 批量审核
	function button_batchAudit(){
		var startDay = $("#startDay").val();
		var endDay = $("#endDay").val();
		if(startDay == '' || endDay == ''){
			alert("必选指定日期范围进行审核！");
		}else{
			if(confirm("确认要审核当前选定条件的数据吗？")){
		        var params = "startDay="+startDay+"&endDay="+endDay
						+"&vst_uac_channel="+$("#vst_uac_channel").val()
						+"&vst_uac_state="+$("#vst_uac_state").val()
						+"&vst_uac_updator="+$("#vst_uac_updator").val()
	        			+"&moduleId="+$("#moduleId").val();
		        $.ajax({
					type: "POST",
					data: params,
					async : true,
					cache : false,
					url:"${ctx}/innerUserAddChannel/ajaxBatchAuditData.action",
					dataType:"json",
					success:function(msg){
						if(msg.code == 100){
							alert("批量审核数据成功"+msg.result+"条，请点击查询刷新数据！");
						}else{
							alert("批量审核数据失败！");
						}
					},
					error:function(msg){
						alert(msg);
					}
				});
			}
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
  			validatorgroup:"1",
	  		formid:"batchUpdateBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
		$("#vst_uac_modulus1").formValidator({
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
	            if(!patrn.exec($("#vst_uac_modulus1").val())){
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
				var modulus = $("#vst_uac_modulus1").val();
				var recordId = $("#recordId").val();
				var moduleId = $("#moduleId").val();
				$.ajax({
					url:"${ctx}/innerUserAddChannel/ajaxBatchUpdate.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"recordId="+recordId+"&modulus="+modulus+"&moduleId="+moduleId,
					success:function(data){
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
		$("#importSubmit").attr("disabled", "disabled");
		$("#syncSubmit").attr("disabled", "disabled");
		$("#editSubmit").attr("disabled", "disabled");
		return true;
	}
	/****************************批量修改******************************/

	
	/****************************导入******************************/
	function button_imp(){
		// 打开弹窗
		$("#import_myModal").attr("class","modal fade in");
		$("#import_myModal").attr("aria-hidden","false");
		$("#import_myModal").show();
		$("#importBoxForm")[0].reset();//重置
		$("#importSubmit").removeAttr("disabled");//解除提交按钮禁用状态
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"2",
	  		formid:"importBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
		$("#vst_uac_date").formValidator({
			validatorgroup : "2",
	  		onshow : "请选择日期！",
	  		onfocus : "日期必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "日期必须选择！"
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#import_myModal").hide();
		});
		
		$("#importSubmit").click(function(){
			if(!confirm("导入时，将会先删除已选日期的数据，然后再进行导入。是否确认？")){
				return false;
			}
			var flag = $.formValidator.pageIsValid("2");// 触发全局校验
			if(flag){
				var date = $("#vst_uac_date").val();
                var $btn = $(this).button('loading');
				$.ajax({
					url:"${ctx}/innerUserAddChannel/ajaxImportData.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"date="+date+"&moduleId="+$("#moduleId").val(),
					success:function(data){
						if(data.code == 100){
							// 关闭弹窗
							$("#import_myModal").hide();
							alert("导入成功，成功添加"+data.result+"条数据！");
						}else if(data.code == 200){
							alert("导入数据为空！");
						}else{
							alert("操作失败！请重试，若还是不成功，请联系VST后台管理人员！");
						}
                        $btn.button('reset');
					}
				});
			}else{
				return false;
			}
		});
	});
	/****************************导入******************************/
	
	/****************************同步******************************/
	function button_sync(){
		// 打开弹窗
		$("#sync_myModal").attr("class","modal fade in");
		$("#sync_myModal").attr("aria-hidden","false");
		$("#sync_myModal").show();
		$("#syncBoxForm")[0].reset();//重置
		$("#syncSubmit").removeAttr("disabled");//解除提交按钮禁用状态
		$("#startDate").val(getNDateTime("yyyy-MM-dd",0));
		$("#endDate").val(getNDateTime("yyyy-MM-dd",0));
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"3",
	  		formid:"syncBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
		$("#startDate").formValidator({
			validatorgroup : "3",
	  		onshow : "请选择日期！",
	  		onfocus : "开始日期必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "开始日期必须选择！"
		});
		
		$("#endDate").formValidator({
			validatorgroup : "3",
	  		onshow : "请选择日期！",
	  		onfocus : "结束日期必须选择！",
	  		oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			onerror : "结束日期必须选择！"
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#sync_myModal").hide();
		});
		
		$("#syncSubmit").click(function(){
			if(!confirm("同步时，将会先删除已选日期的数据，然后再进行导入。是否确认？")){
				return false;
			}
			var flag = $.formValidator.pageIsValid("2");// 触发全局校验
			if(flag){
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
                var $btn = $(this).button('loading');
				$.ajax({
					url:"${ctx}/innerUserAddChannel/ajaxSyncData.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"startDate="+startDate+"&endDate="+endDate+"&moduleId="+$("#moduleId").val(),
					success:function(data){
						if(data.code == 200){
							// 关闭弹窗
							$("#sync_myModal").hide();
							alert(data.msg);
						}else{
							alert(data.msg);
						}
                        $btn.button('reset');
					}
				});
			}else{
				return false;
			}
		});
	});
	/****************************同步******************************/

	/****************************修改******************************/
	function button_edit(){
		var modifyChecked="";
		$("input:checked[type=checkbox][name=recordIds]").each(function(){
			modifyChecked = modifyChecked + $(this).val() +",";
		});
		modifyChecked = modifyChecked.substring(0,modifyChecked.length-1);
		if(modifyChecked == ""){
			alert("您还没有选择记录！");
			return false;
		}else if(modifyChecked.indexOf(",") != -1){
			alert("每次最多选择一条记录进行该操作!");
			return false;
		}
		$("#recordId").val(modifyChecked);
		// 打开弹窗
		$("#edit_myModal").attr("class","modal fade in");
		$("#edit_myModal").attr("aria-hidden","false");
		$("#edit_myModal").show();
		$("#editBoxForm")[0].reset();//重置
		$("#editSubmit").removeAttr("disabled");//解除提交按钮禁用状态
	}

	$(document).ready(function(){
		$.formValidator.initConfig({
			validatorgroup:"4",
			formid:"editBoxForm",
			wideword: false,
			onerror : function(msg) {},
			onsuccess:doSubmit
		});

		$("#vst_uac_amount4").formValidator({
			validatorgroup : "4",
			onshow : "请填写新增用户！",
			onfocus : "新增用户不能为空，必须为数字！",
			oncorrect : "验证通过！"
		}).inputValidator({
			min : 1,
			max : 13,
			onerror : "新增用户长度在1~13位！"
		}).regexValidator({
			regexp :"^[0-9]{1,}$",
			datatype :"string",
			onerror :"格式错误，必须为纯数字，不能带有小数！"
		});

		$("#vst_uac_modulus4").formValidator({
			validatorgroup : "4",
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
				if(!patrn.exec($("#vst_uac_modulus4").val())){
					return false;
				}
				return true;
			},
			onerror :"格式错误！必须是数字，最多保留两位小数！"
		});

		// 失去焦点，计算新的新增用户
		$("#vst_uac_amount4").blur(function(){
			var amount = $("#vst_uac_amount4").val();
			var modulus = $("#vst_uac_modulus4").val();
			if(amount != '' && modulus != ''){
				var result = Math.round(amount * modulus);
				$("#newAmount").html(result);
			}
		});

		// 关闭弹框事件
		$(".close").click(function(){
			$("#edit_myModal").hide();
		});

		$("#editSubmit").click(function(){
			var flag = $.formValidator.pageIsValid("4");// 触发全局校验
			if(flag){
				var amount = $("#vst_uac_amount4").val();
				var modulus = $("#vst_uac_modulus4").val();
				var recordId = $("#recordId").val();
				var moduleId = $("#moduleId").val();
				$.ajax({
					url:"${ctx}/innerUserAddChannel/ajaxEditData.action",
					type:"POST",
					dataType:"json",
					cache:false,
					data:"recordId="+recordId+"&amount="+amount+"&modulus="+modulus+"&moduleId="+moduleId,
					success:function(data){
						if(data.code == 100){
							// 关闭弹窗
							$("#edit_myModal").hide();
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
	/****************************修改******************************/
	

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
		params += "&vst_uac_channel=" + $("#vst_uac_channel").val();
		params += "&vst_uac_state=" + $("#vst_uac_state").val();
		params += "&vst_uac_updator=" + $("#vst_uac_updator").val();
		
		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/innerUserAddChannel/ajaxGetReportByDate.action",
			dataType:"json",
			success:function(msg){
				$("#loading_line").attr("style","display:none");
				if(msg.result == "success"){
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
		var vst_uac_amount = [];

		$.each(data,function(i,n){
			xaxis[i] = getDateWeek(n.date+'', 'yyyyMMdd');
			vst_uac_amount[i] = n.vst_uac_amount;
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
				name : '新增用户数',
				data : vst_uac_amount,
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

	// 按维度统计
	function ajaxGetReportByDim(){
		$("#loading_bar").removeAttr("style");
		$("#bar-chart").empty();
		$("#loading_pie").removeAttr("style");
		$("#pie-chart").empty();

		var startDays = $("#startDay").val().split("-");
		var startDay = startDays[0] + startDays[1] + startDays[2];
		var endDays = $("#endDay").val().split("-");
		var endDay = endDays[0] + endDays[1] + endDays[2];

		var params = "startDay=" + startDay;
		params += "&endDay=" + endDay;
		params += "&pkgName=" + $("#pkgName").val();
		params += "&vst_uac_channel=" + $("#vst_uac_channel").val();
		params += "&vst_uac_state=" + $("#vst_uac_state").val();
		params += "&vst_uac_updator=" + $("#vst_uac_updator").val();

		$.ajax({
			type:"POST",
			data:params,
			async : true,
			cache : false,
			url:"${ctx}/innerUserAddChannel/ajaxGetReportByDim.action",
			dataType:"json",
			success:function(msg){
				$("#loading_bar").attr("style","display:none");
				$("#loading_pie").attr("style","display:none");
				if(msg.result == "success"){
					bar(msg);
					pie(msg);
				}else{
					$("#bar-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
					$("#pie-chart").html("<table width='100%'><tr><td align='center' height='80px;'>没有找到相关记录，请重新输入条件进行查询</td></tr></table>");
				}
			},
			error:function(msg){
				$("#loading_bar").attr("style","display:none");
				$("#loading_pie").attr("style","display:none");
			}
		});
	}

	function bar(msg){
		var data = msg.data;
		var top10data = data;
		var xaxis = [];
		var series = [];
		if(data.length > 10){
			top10data = data.slice(0, 10);
		}
		$.each(top10data,function(i,n){
			xaxis[i] = getValue(n["value"]);
			series[i] = n.vst_uac_amount;
		});

		$("#bar-chart").highcharts({
			chart: {
				type: 'bar',
				height: 360
			},
			title: {
				text: false
			},
			subtitle: {
				text: false
			},
			xAxis: {
				categories: xaxis
			},
			yAxis: {
				min: 0,
				title: {
					text: false,
					rotation: 0,
				},
				labels: {
					overflow: 'justify'
				}
			},
			plotOptions: {
				bar: {
					dataLabels: {
						enabled: true,
						allowOverlap: true
					}
				}
			},
			legend: {
				align: 'center',
				verticalAlign: 'top',
				labelFormat: '{name}Top10'
			},
			credits: {
				enabled: false
			},
			series: [
				{
					name: '新增用户总量',
					data: series
				}
			]
		});
	}

	function pie(msg){
		var data = msg.data;
		var top20data = data;
		if(data.length > 20){
			top20data = data.slice(0, 20);
		}

		var series = "[";

		$.each(top20data,function(i,n){
			series +="{name:\""+getValue(n["value"])+"："+n.vst_uac_amount+"\",y:"+n.vst_uac_amount+"},";
		});

		series += "]";
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
				pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
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
				enabled: false
			},
			series: [{
				type: 'pie',
				name: '比重',
				data: series
			}]
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
		params += "&vst_uac_channel=" + $("#vst_uac_channel").val();
		params += "&vst_uac_state=" + $("#vst_uac_state").val();
		params += "&vst_uac_updator=" + $("#vst_uac_updator").val();

		$.ajax({
			type: "POST",
			data: params,
			async : true,
			cache : false,
			url:"${ctx}/innerUserAddChannel/ajaxGetCutPage.action",
			dataType:"json",
			success:function(msg){
				$("#move_loading").attr("style","display:none");
				if(msg.result == "success"){
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
		var totalCount = msg.totalSize;
		var singleCount = msg.singleSize;
		$("#currPage").val(msg.currPage);
		
		showPageInfo(totalCount, singleCount);
		var orderBy = $("#orderBy").val();
		var order = $("#order").val();
		var table = "<table id='example1' class='table table-bordered table-striped'><thead><tr>";
		table += "<th width='5%'><input type='checkbox' name='chkall' id='chkall' onclick='checkAll()' />全选</th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_date' id='vst_uac_date2' order='desc'>日期</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_channel' id='vst_uac_channel2' order='desc' style='color:orange;'>渠道</a></th>";
		table += "<th width='15%'><a onclick='changeOrder(this)' orderBy='vst_uac_amount' id='vst_uac_amount2' order='desc'>新增用户</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_dod' id='vst_uac_dod2' order='desc'>天环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_wow' id='vst_uac_wow2' order='desc'>周环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_mom' id='vst_uac_mom2' order='desc'>月环比</a></th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_modulus' id='vst_uac_modulus2' order='desc'>调整系数</a></th>";
		table += "<th width='15%'>新增用户*调整系数</th>";
		table += "<th width='10%'><a onclick='changeOrder(this)' orderBy='vst_uac_state' id='vst_uac_state2' order='desc'>审核状态</a></th>";
		
		table += "</tr></thead><tbody>";
		
		$.each(data,function(i,n){
			var row = i%2+1;
			var dodColor = "green";
			var wowColor = "green";
			var momColor = "green";
			
			if(parseFloat(n.vst_uac_dod)<0){
				dodColor = "red";
			}
			if(parseFloat(n.vst_uac_wow)<0){
				wowColor = "red";
			}
			if(parseFloat(n.vst_uac_mom)<0){
				momColor = "red";
			}
			// 审核状态
			var vst_uac_state = n.vst_uac_state;
			if(n.vst_uac_state == 1){
				vst_uac_state = "<span class='red'>未审核<span>";
			}else if(n.vst_uac_state == 2){
				vst_uac_state = "<span class='green'>已审核<span>";
			}
			
			//table += "<tr class='row"+row+"'>";
			table += "<tr>";

			table += "<td><input type='checkbox' id='recordIds' name='recordIds' "
							+ "value='" + n.vst_uac_id + "' "
							+ "state='" + n.vst_uac_state + "' "
							+ "modulus='" + n.vst_uac_modulus + "' "
							+ " /></td>";
			table += "<td>"+getDateWeek(n.vst_uac_date+'', 'yyyyMMdd')+"</td>";
			table += "<td data-title='同步时间："+formatDate(n.vst_uac_synctime, "yyyy-MM-dd HH:mm:ss")
						+"<br>新增时间："+toString(n.vst_uac_addtime)
						+"<br>创建人："+toString(n.vst_uac_creator)
						+"<br>修改时间："+toString(n.vst_uac_uptime)
						+"<br>修改人："+toString(n.vst_uac_updator)
						+"<br>备注："+toString(n.vst_uac_summary)+"' class='enlargeTextTitle'>"
					+toString(n.vst_uac_channel)+"</td>";
			table += "<td>"+formatNumber(n.vst_uac_amount)+"</td>";
			table += "<td class='"+dodColor+"'>"+toString(n.vst_uac_dod)+"</td>";
			table += "<td class='"+wowColor+"'>"+toString(n.vst_uac_wow)+"</td>";
			table += "<td class='"+momColor+"'>"+toString(n.vst_uac_mom)+"</td>";
			table += "<td class='myClick'>"+toString(n.vst_uac_modulus)+"</td>";
			table += "<td>"+formatNumber(Math.round(n.vst_uac_amount * n.vst_uac_modulus))+"</td>";
			table += "<td>"+toString(vst_uac_state)+"</td>";
			
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
			params += "&vst_uac_channel=" + $("#vst_uac_channel").val();
			params += "&vst_uac_state=" + $("#vst_uac_state").val();
			params += "&vst_uac_updator=" + $("#vst_uac_updator").val();
			
			location.href = "${ctx}/innerUserAddChannel/exportData.action?" + params
					+ "&moduleId=" + $("#moduleId").val();
		}
	}
</script>
</html>