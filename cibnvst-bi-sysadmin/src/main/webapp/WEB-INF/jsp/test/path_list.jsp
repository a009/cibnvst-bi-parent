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

<!-- echarts -->
<script src="https://cdn.bootcss.com/echarts/3.7.1/echarts.min.js"></script>

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
						<span> > 神策数据 > 用户路径</span>
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
												<span class="sertitle">选择参与分析的事件：</span>
												<input class="list-input1" type="text" name="vst_button_id" value="${cutPage._queryParam.vst_button_id }" />
											</li>
											<li>
												<span class="sertitle">设置：</span>
												<input type="hidden" id="hidden_button_state" value="${cutPage._queryParam.vst_button_state }" />
												<select class="list-input1" id="vst_button_state" name="vst_button_state">
													<option value="">请选择</option>
													<option value="1">起始事件</option>
													<option value="2">结束事件</option>
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
										<input type="submit" id="queryBtn" class="queryBtn" value="查询" />
										<input type="reset" class="resetBtn" value="重置" />
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
								<div class="box-header">
									 <div class="report-date-picker-wrap">
				                        <span class="icon-date-picker"></span>
				                        <input id="inputDate" type="text" class="form-control" readonly="readonly" data-placement="top" data-container="body" title="" data-original-title="事件发生的时间范围" data-calendar-status-refresh-time="1504838356">
				                    </div>
								</div>
								<div class="box-body">
									<div class="echarts_sankey_container">
							        	<div class="line">
								        	<div id="main" style="height:500px;"></div>
								    	</div>
								    </div>
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
	
	<script src="${ctx}/pub/js/shence_path.js"></script>
</body>
</script>
</html>