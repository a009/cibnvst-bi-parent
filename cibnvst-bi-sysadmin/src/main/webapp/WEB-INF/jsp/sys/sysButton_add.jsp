<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});

		$("#vst_button_name").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 50,
			onerror : "按钮名称不可为空，且最大长度为50个字符！"
		});
		
		$("#vst_button_img").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 250,
			onerror : "按钮图片不可为空，且最大长度为250个字符！"
		});
		
		$("#vst_button_onclick").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 100,
			onerror : "按钮事件不可为空，且最大长度为100个字符！"
		});

		$("#vst_button_index").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 1,
			max : 11,
			onerror : "排序格式错误！至少需要1位数字且不超过11位数字！"
		}).regexValidator( {
			regexp : "^[0-9]{0,}$",
			datatype : "string",
			onerror : "排序格式错误，必须为纯数字，不能带有小数！"
		});

		$("#vst_button_summary").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min : 0,
			max : 512,
			onerror : "备注最大长度为512个字符！"
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
				<form id="listForm" action="${ctx}/sysButton/addButton.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统按钮新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">按钮名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_button_name" name="vst_button_name" placeholder="请输入按钮名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_button_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">按钮图片：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_button_img" name="vst_button_img" placeholder="请输入按钮图片" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_button_imgTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">按钮事件：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_button_onclick" name="vst_button_onclick" placeholder="请输入按钮事件" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_button_onclickTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">排序：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_button_index" name="vst_button_index" value="0" placeholder="请输入排序" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_button_indexTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_button_summary" name="vst_button_summary" placeholder="请输入备注"
												style="resize: none; height: 100px;"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_button_summaryTip"></span>
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
</body>
</html>