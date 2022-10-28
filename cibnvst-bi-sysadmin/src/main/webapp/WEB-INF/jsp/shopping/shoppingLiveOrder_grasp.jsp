<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	$(function(){
		// 参数赋值
		var queryParam = '${queryParam}';
		if(queryParam == null || queryParam == ''){
			$("#startDay").val(getNDateTime("yyyy-MM-dd",1));
			$("#endDay").val(getNDateTime("yyyy-MM-dd",1));
		}else{
			$("#startDay").val('${queryParam.startDay}');
			$("#endDay").val('${queryParam.endDay}');
			$("#channel").val('${queryParam.channel}');
			$("#cookie").val('${queryParam.cookie}');
		}
	});

	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});

		$("#startDay").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 1,
			onerror : "起始日期必须选择！"
		});
		
		$("#endDay").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 1,
			onerror : "结束日期必须选择！"
		});
		
		$("#channel").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 0,
			onerror : "渠道必须选择！"
		});
		
		$("#Cookie").formValidator( {
			onshow : "",
			onfocus : "",
			oncorrect : ""
		}).inputValidator( {
			min : 0,
			onerror : "Cookie允许为空！"
		});
	});

	// 提交处理
	function doSubmit() {
		$("#btnSubmit").attr("disabled", "disabled");
		return true;
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper">
			<div class="content-roll">
				<form id="listForm" action="${ctx}/shoppingLiveOrder/graspData.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 全球购管理 > 直播订单抓取</span>
					</section>
					<%@include file="../share/message.jsp"%>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">起始日期：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input class="input ic-calendar" id="startDay" name="startDay"
												style="width: 166px;" readonly="readonly" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="startDayTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">结束日期：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input class="input ic-calendar" id="endDay" name="endDay"
												style="width: 166px;" readonly="readonly" />
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="endDayTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">商品渠道：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="channel" name="channel">
						  	 					<c:forEach items="${channels}" var="channel">
						  	 						<option value="${channel.key }">${channel.value }</option>
						  	 					</c:forEach>
											</select>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="channelTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">Cookie：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="cookie" name="cookie"
												style="resize: none; height: 100px;" placeholder="请输入Cookie"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="cookieTip"></span>
									</div>
								</li>
							</ul>
						</div>
						<%@ include file="../share/submitButton.jsp"%>
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
	laydate.render( {
		elem : '#startDay',
		type : 'date',
		format : 'yyyy-MM-dd',
		theme : 'molv'
	});

	laydate.render( {
		elem : '#endDay',
		type : 'date',
		format : 'yyyy-MM-dd',
		theme : 'molv'
	});
</script>

</body>
</html>