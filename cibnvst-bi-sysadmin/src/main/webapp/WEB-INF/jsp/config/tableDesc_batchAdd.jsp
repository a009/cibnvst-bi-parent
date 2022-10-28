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
			onshow : "请填写地址, 地址不可以为空",
			onfocus : "一行条数据,填写格式为:“表名|属性|内容|排序|备注(选)”<br/>如：vst_custom_event|日期|每小时统计一次|98",
			oncorrect : "验证通过！"
		}).inputValidator({
			min : 0,
			max : 1024,
			onerror : "地址长度不能超过1024位！"
		}).functionValidator({
			fun : checkFormat,
			onerror : "地址格式错误，请检查格式是否正确！"
		});
	});
	
	//检查地址格式
	function checkFormat(){
		var url = $.trim($("#contents").val());
		if(url == null || url == ""){
			return "地址不能为空！";
		}else{
			var rows = url.split("\n");
			if(rows.length == 0) return "行数据不能为空！"; 

			var reg = new RegExp("^[0-9]+$");
			for(var i = 0; i < rows.length; i++){	
				var row = rows[i].split("|");	
				if(row.length < 4 || row.length > 5){
					return "行数据分割的长度不对！";
				}
				if(row[0] == ''){
					return "表名不能为空！";
				}
				if(!reg.test(row[3])){
					return "排序必须是整数！";
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
				<form id="listForm" action="${ctx}/tableDesc/batchAddTableDesc.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > 表算法描述批量添加</span>
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
