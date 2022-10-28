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
			onfocus : "一行条数据,填写格式为:“所属包名|所属专区|所属分类|特征类型|特征名称|特征值|排序(选)|备注(选)”<br/>如：net.myvst.v2|0|1|1|动作|0.1234",
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
			var reg2 = new RegExp("[0-9]+(\.[0-9]{1,4})?");
			for(var i = 0; i < rows.length; i++){	
				var row = rows[i].split("|");	
				if(row.length < 6 || row.length > 8){
					return "行数据分割的长度不对！";
				}
				if(!reg.test(row[1])){
					return "所属专区必须是整数！";
				}
				if(!reg.test(row[2])){
					return "所属分类必须是整数！";
				}
				if(!reg.test(row[3])){
					return "特征类型必须是整数！";
				}
				if(!reg2.test(row[5])){
					return "特征值必须是数字，最多四位小数";
				}
				if(row.length >= 7){
					if(!reg.test(row[6])){
						return "排序必须是整数！";
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
				<form id="listForm" action="${ctx}/featuresConfig/batchAddFeaturesConfig.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > 用户指标特征配置批量添加</span>
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
