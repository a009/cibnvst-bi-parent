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
			onfocus :"一行一个配置数据,填写格式为:“任务ID*[!]筛选Key*[!]筛选Value*[!]排序[!]匹配类型[!]匹配上处理类型[!]未匹配处理类型[!]操作类型[!]动作Key[!]动作Value[!]适用包名[!]屏蔽包名[!]备注”如：sqlID[!]key[!]value[!]10[!]1[!]1[!]1[!]0[!]key[!]value[!]pkg1[!]pkg2[!]summary",
			oncorrect :"验证通过！"
		}).inputValidator({
			min :0,
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

			var reg = new RegExp("^[0-9]*$");
			for(var i = 0; i < rows.length; i++){	
				var row = rows[i].split("[!]");	
				if(row.length < 3 || row.length > 13){
					return false;
				}
				if(row.length >= 4){
					if(!reg.test(row[3])){
						return false;
					}
				}
				if(row.length >= 5){
					if(!reg.test(row[4])){
						return false;
					}
				}
				if(row.length >= 6){
					if(!reg.test(row[5])){
						return false;
					}
				}
				if(row.length >= 7){
					if(!reg.test(row[6])){
						return false;
					}
				}
				if(row.length >= 8){
					if(!reg.test(row[7])){
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
				<form id="listForm" action="${ctx}/sqlFilter/batchAddSqlFilter.action" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 配置管理 > sql筛选配置批量新增
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
