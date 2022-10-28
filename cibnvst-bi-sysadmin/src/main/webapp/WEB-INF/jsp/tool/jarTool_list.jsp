<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>报表管理中心</title>

<style type="text/css">
	.loadImg{
		position: absolute;
	    top: 0;
	    bottom: 0;
	    left: 0;
	    right: 0;
	    margin: auto;
	    z-index:9999;
	    display:none;
	}
</style>

<script type="text/javascript">
	// 上传JAR包
	function uploadJar(){
		var upload = $("#jar_upload").val();
		if(upload == ""){
			alert("您还没有选择文件！");
		}else{
			var fileType = upload.substring(upload.lastIndexOf(".")+1);
			var reg = new RegExp("(jar|JAR)$");
			if(!reg.test(fileType)){
				alert("文件格式错误！");
				return false;
			}
			if(confirm("如果之前该文件已存在，将覆盖掉原文件，是否继续？")){
				$.ajaxFileUpload({
					url : '${ctx}/jarTool/ajaxUploadJar.action', //需要链接到服务器地址
					type : "POST",
					secureuri : false,
					fileElementId : 'jar_upload', //文件选择框的id属性  
					dataType : 'json',
					success : function (data, status){
						if(data.code == 100){
							$("#jar_url").val(data.url);
						}else{
							$("#jar_url").val('');
						}
						alert(data.msg);
					},
					error : function (data, status, e){//相当于java中catch语句块的用法  
						$("#jar_url").val(data.msg);
						alert("服务器异常");
					}
				});
			}
		}
	}

	$(function(){
		$("#sql_type").val(2);
		$("#sql_sysParam").val('--master spark://bj-bi-slave2:7077,bj-bi-slave3:7077,bj-bi-slave4:7077 —total-executor-cores 10 —executor-memory 2g —class com.vst.Main');
		// $("#sql_appParam").val('/data/upload/jar/export-simple-1.0-SNAPSHOT.jar execSql "{sql}" {type} {fileName} -1 /tmp/export/sql /data/download true');
		$("#sql_appParam").val('/data/upload/jar/export-simple-1.0-SNAPSHOT.jar execSql "{sql}" {type} {fileName} -1 /tmp/export/sql {downloadDir} true');
		$("#sql_content").val('SELECT * FROM 1');
		$("#sql_url").val('/data/download/test.txt');

		$("#sql_appParam").change(function(){
			$.ajax({
				url : "${ctx}/jarTool/ajaxGetSparkExecParam.action",
				data : "sql_type="+$("#sql_type").val()+"&sql_sysParam="+$("#sql_sysParam").val()+"&sql_appParam="+$("#sql_appParam").val()+"&sql_content="+$("#sql_content").val(),
				type : "POST",
				cache : false,
				async : false,
				dataType : "json",
				success : function(data){
					if(data.code == 100){
						$("#sql_result").val(data.result);
					}else{
						$("#sql_result").val(data.msg);
					}
				},
				error : function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		});
  	});
	
	// 执行SQL
	function executeSql(){
		var sql_type = $("#sql_type").val();
		var sql_sysParam = $("#sql_sysParam").val();
		var sql_appParam = $("#sql_appParam").val();
		var sql_content = $("#sql_content").val();
		
		if(sql_type == null || sql_type == ""){
			alert("保存类型不能为空！");
		}else if(sql_sysParam == null || sql_sysParam == ""){
			alert("系统参数不能为空！");
		}else if(sql_appParam == null || sql_appParam == ""){
			alert("应用参数不能为空！");
		}else if(sql_content == null || sql_content == ""){
			alert("SQL内容不能为空！");
		}else{
			$.ajax({
				url : "${ctx}/jarTool/ajaxExecuteSql.action",
				data : "sql_type="+sql_type+"&sql_sysParam="+sql_sysParam+"&sql_appParam="+sql_appParam+"&sql_content="+sql_content,
				type : "POST",
				cache : false,
				async : false,
				dataType : "json",
				success : function(data){
					if(data.code == 100){
						$("#sql_result").val(data.result);
					}else{
						$("#sql_result").val(data.msg);
					}
				},
				error : function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		}
	}

	// 下载SQL文件
	function downloadSql(){
		var sql_url = $("#sql_url").val();
		if(sql_url == null || sql_url == ''){
			alert("下载路径不能为空！");
		}else{
			if(confirm("下载速度，由数据大小决定，请耐心等待。。")){
				location.href = "${ctx}/jarTool/downloadSql.action?url="+sql_url;
			}
		}
	}
</script>

<style>
.tool-title{
	font-size:20px;
}

input[type="text"]{
	width: 300px;
    height: 30px!important;
    line-height: 30px;
    font-size: 14px;
    border: solid 1px #999;
    border-radius: 3px;
    outline: none;
    padding-left:10px;
    margin-bottom:10px;
}
input[type="button"]{
	width: 150px;
    height: 30px;
    line-height: 24px;
    background: #00a7d0;
    color: #fff;
    border: none;
    font-size: 16px;
    outline: none;
    border-radius: 3px;
    margin: 10px 0 10px 20px;
    position: relative;
}
input[type="button"]:active{
	top: 1px;
	background: gray;
}
.td-title{
    width: 150px;
    float: left;
    text-align: right;
    height: 30px;
    line-height: 30px;
}
textarea{
	width: 1000px;
 	font-size: 14px;
    border: solid 1px #999;
    border-radius: 3px;
    outline: none;
    padding-left:10px;
    margin-bottom:10px;
}
select{
	width: 300px;
	height: 30px!important;
    line-height: 30px;
    font-size: 14px;
    border: solid 1px #999;
    border-radius: 3px;
    outline: none;
    padding-left:10px;
    margin-bottom:10px;
}
.web-line{
	border-bottom:1px solid #ddd;
	margin:0 10px;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../share/header.jsp"%>
		<%@include file="../share/leftMenu.jsp"%>
		
		<!-- 请求加载中 -->
		<img class="loadImg" src="${ctx}/pub/images/loading1.gif">
		
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form action="" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 常用工具 > JAR包工具</span>
					</section>
					
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box ">
								<div class="box-header">
									<i class="fa fa-refresh"></i>
									<span class="tool-title">上传JAR包</span>
								</div>
								
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">选择JAR：</span></td>
												<td>
													<input type="file" id="jar_upload" name="upload" />
												</td>
												<td>
													<input type="button" value="上传" onclick="javascript:uploadJar();" />
												</td>
											</tr>
											<tr>
												<td><span class="td-title">上传地址：</span></td>
												<td>
													<textarea rows="5" cols="100" id="jar_url"></textarea>
												</td>
												<td></td>
											</tr>
										</form>
									</table>
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
									<i class="fa fa-refresh"></i>
									<span class="tool-title">SQL执行</span>
								</div>
								
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">保存类型：</span></td>
												<td>
													<select id="sql_type">
														<option value="1">console</option>
														<option value="2">csv</option>
														<option value="3">json</option>
														<option value="4">text</option>
													</select>
												</td>
												<td></td>
											</tr>
											<tr>
												<td><span class="td-title">系统参数：</span></td>
												<td>
													<textarea rows="5" cols="100" id="sql_sysParam"></textarea>
												</td>
												<td></td>
											</tr>
											<tr>
												<td><span class="td-title">应用参数：</span></td>
												<td>
													<textarea rows="5" cols="100" id="sql_appParam"></textarea>
												</td>
												<td></td>
											</tr>
											<tr>
												<td><span class="td-title">SQL内容：</span></td>
												<td>
													<textarea rows="5" cols="100" id="sql_content"></textarea>
												</td>
												<td>
													<input type="button" value="执行" onclick="javascript:executeSql();" />
												</td>
											</tr>
											<tr>
												<td><span class="td-title">执行结果：</span></td>
												<td>
													<textarea rows="5" cols="100" id="sql_result"></textarea>
												</td>
												<td></td>
											</tr>
											<tr>
												<td><span class="td-title">下载路径：</span></td>
												<td>
													<input type="text" id="sql_url" />
												</td>
												<td>
													<input type="button" value="下载" onclick="javascript:downloadSql();" />
												</td>
											</tr>
										</form>
									</table>
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
	laydate.render( {
		elem : '#jar_time',
		type : 'datetime',
		format : 'yyyy-MM-dd HH:mm:ss',
		theme : 'molv'
	});
</script>

</body>
</html>