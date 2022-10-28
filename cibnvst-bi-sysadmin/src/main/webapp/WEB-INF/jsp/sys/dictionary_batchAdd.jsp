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
		
		$("#contents").formValidator({
			onshow :"请填写地址, 地址不可以为空",
			onfocus :"一行一个字典数据,填写格式为:“表名|字段名|属性键|属性值|排序(选)|包名(选)”如：vst_table|vst_column|vst_key|vst_value|10|net.myvst.v2",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :0,
			max :1024,
			onerror :"地址长度不能超过1024位！"
		}).functionValidator({
			fun :checkFormat,
			onerror :"地址格式错误，地址，排序值必须为数字！"
		});
	});
	
	//检查地址格式
	function checkFormat(){
		var url = $.trim($("#contents").val());
		if(url == null || url == ""){
			return false;
		}else{		
			var rows = url.split("\n");
			if(rows.length == 0) return false; 

			var reg = new RegExp("^[0-9]+$");
			for(var i = 0; i < rows.length; i++){	
				var row = rows[i].split("|");	
				if(row.length < 4 || row.length > 6){
					return false;
				}
				if(row.length >= 5){
					if(!reg.test(row[4])){
						return false;
					}
				}
			}
		}
		return true;
	}

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
				<form id="listForm" action="${ctx}/dictionary/batchAddDictionary.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 字典批量添加</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<table id='example2' class="line03">
								<tr>
									<td width="20%" class="tright">地址：</td>
									<td width="25%">
										<textarea class="input" id="contents" name="contents"
											style="width: 88%; height: 100px;"></textarea>
										<span class="red">*</span>
										<br/>
									</td>
									<td width="28%">
										<span id="contentsTip"></span>
									</td>
								</tr>
							</table>
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
