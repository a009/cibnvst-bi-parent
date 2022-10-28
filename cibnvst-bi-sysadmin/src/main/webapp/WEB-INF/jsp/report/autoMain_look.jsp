<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript" src="${ctx}/pub/js/dtree.js"></script>
<script type="text/javascript">
	// 测试接口
	function testAPI(){
		var params = "";	//条件参数
		var isCall = true;	//是否调用
		
		$("input.vst_arg").each(function(){
			var vst_condition_arg = $(this).attr("vst_condition_arg");
			var vst_condition_need = $(this).attr("vst_condition_need");
			var vst_condition_type = $(this).attr("vst_condition_type");
			var value = $(this).val();
			
			if(value != ''){
				if(vst_condition_type == "int"){
					var reg = /^-?\d+$/;
					if(!reg.test(value)){
						alert(vst_condition_arg + "参数必须是整数");
						isCall = false;
						return false;
					}
				}else if(vst_condition_type == "double"){
					var reg = /^(-?\d+)(\.\d+)?$/;
					if(!reg.test(value)){
						alert(vst_condition_arg + "参数必须是数字");
						isCall = false;
						return false;
					}
				}
				params += "&" + vst_condition_arg + "=" + value;
			}
	  	});
		
		if(isCall){
			var url = "${ctx}/report/json?code=" + $("#vst_code").val() + params;
			window.open(url);
		}
	}
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		<div class="content-wrapper">
			<div class="content-roll">
				<form id="listForm" action="" method="post">
					<input type="hidden" id="vst_code" value="${vst_code }" />
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 自动化管理 > 自动化(主表)查看</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<table id='example2' style="width: 100%;">
								<tr>
									<td width="30%" class="tright">SQL语句：</td>
									<td width="70%">
										<script type="text/javascript">
									        var data = JSON.parse('${data}');
									        
									        d = new dTree('d','${ctx}');
											
									        d.add(0, -1, '');
									        
									        if(data.mains != null && data.mains.length > 0){
									        	d.add(1001, 0, '主表', '');
									        	for(var i=0; i<data.mains.length; i++){
										        	d.add(1011+i, 1001, data.mains[i], '');
										        }
									        }
									        
									        if(data.conditions != null && data.conditions.length > 0){
										        d.add(2001, 0, '条件(参数、是否必填、类型)', '');
										        for(var i=0; i<data.conditions.length; i++){
										        	var bean = data.conditions[i];
										        	
										        	var vst_condition_need = "否";
										        	if(bean.vst_condition_need == 1){
										        		vst_condition_need = "<font class='red'>是</font>";
										        	}
										        	
										        	var str = "<span style='width:350px;display:inline-block;'>" + bean.vst_condition_script + "</span><span style='width:250px;display:inline-block;margin-left:50px;'>" + bean.vst_condition_arg + "</span><span style='width:50px;display:inline-block;margin-left:20px;'>" + vst_condition_need + "</span><span style='width:50px;display:inline-block;margin-left:20px;'>" + bean.vst_condition_type + "</span>";
										        	str += "<span style='width:50px;display:inline-block;margin-left:20px;'><input type='text' class='vst_arg' vst_condition_arg='" + bean.vst_condition_arg + "' vst_condition_need='" + bean.vst_condition_need + "' vst_condition_type='" + bean.vst_condition_type + "' placeholder='测试参数' /></span>"
										        	
										        	d.add(2011+i, 2001, str, '');
										        }
										    }
									        
									        if(data.overlays != null && data.overlays.length > 0){
										        d.add(3001, 0, '补充', '');
										        for(var i=0; i<data.overlays.length; i++){
										        	d.add(3011+i, 3001, data.overlays[i], '');
										        }
									        }
									        
									        document.write(d);
										</script>
									</td>
								</tr>
							</table>
						</div>
						<div class="btnfooter">
						    <input type="button" class="btnSubmit" onclick="javascript:testAPI();" value="测试" />
						    <input type="button" class="btnReset" onclick="javascript:window.history.go(-1)" value="返回" />
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