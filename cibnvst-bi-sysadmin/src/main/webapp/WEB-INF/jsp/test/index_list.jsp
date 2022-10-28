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
<script type="text/javascript" src="${ctx}/pub/plugins/shence/dashboard.js"></script>

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
						<section>
							<div id="sa-main">
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
						                <div class="dashboard-blank-slate">
						                    <div class="dashboard-blank-slate-img"></div>
						                    <h4>还未添加指标组件</h4>
						                    <p data-email="no">通过右上角的「+」添加组件</p>
						                    <p data-email="no">或在保存书签时选择「同时添加到数据概览」</p>
						                </div>
						            </section>
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