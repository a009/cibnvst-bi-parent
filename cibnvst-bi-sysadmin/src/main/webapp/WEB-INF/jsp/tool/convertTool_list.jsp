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
	/* ----------------------- 直播pid转换 ----------------------- */
	// 转换直播pid
	function getStreamId(){
		var live_pid = $.trim($("#live_pid").val());
		if(live_pid == null || live_pid == ""){
			alert("不允许空值");
		}else{
			$.ajax({
				url:"${ctx}/convertTool/ajaxConvertPid.action",
				data:"live_pid="+live_pid,
				type:"POST",
				cache:false,
				async:false,
				dataType:"json",
				success:function(data){
					if(data.streamId != null){
						$("#result1").val(data.streamId);
					}else{
						$("#result1").val("不存在");
					}
				},
				error: function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		}
	}
	
	
	/* ----------------------- uuid转换 ----------------------- */
	// 加密uuid
	function encryptUUID(){
		var uuid = $.trim($("#uuid").val());
		if(uuid == null || uuid == ""){
			alert("不允许空值");
		}else{
			$.ajax({
				url:"${ctx}/convertTool/ajaxEncryptUUID.action",
				data:"uuid="+uuid,
				type:"POST",
				cache:false,
				async:false,
				dataType:"json",
				success:function(data){
					if(data.result != null){
						$("#result2").val(data.result);
					}else{
						$("#result2").val("加密失败");
					}
				},
				error: function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		}
	}
	
	// 解密uuid
	function decodeUUID(){
		var result = $.trim($("#result2").val());
		if(result == null || result == ""){
			alert("不允许空值");
		}else{
			$.ajax({
				url:"${ctx}/convertTool/ajaxDecodeUUID.action",
				data:"result="+result,
				type:"POST",
				cache:false,
				async:false,
				dataType:"json",
				success:function(data){
					if(data.uuid != null){
						$("#uuid").val(data.uuid);
					}else{
						$("#uuid").val("解密失败");
					}
				},
				error: function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		}
	}
	
	
	/* ----------------------- 时间戳转换 ----------------------- */
	// 获取北京时间
	function getBjTime(){
		var timeStamp = $.trim($("#timeStamp").val());
		var reg = /^[0-9]{1,}$/;
		if(!reg.test(timeStamp)){
			alert("时间戳不可为空，必须是整数");
		}else{
			$.ajax({
				url:"${ctx}/convertTool/ajaxGetBjTime.action",
				data:"timeStamp="+timeStamp,
				type:"POST",
				cache:false,
				async:false,
				dataType:"json",
				success:function(data){
					if(data.bjTime != null){
						$("#bjTime").val(data.bjTime);
					}else{
						$("#bjTime").val("转换失败");
					}
				},
				error: function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		}
	}
	
	// 获取时间戳
	function getTimeStamp(){
		var bjTime = $.trim($("#bjTime").val());
		if(bjTime == null || bjTime == ""){
			alert("北京时间不能为空");
		}else{
			$.ajax({
				url:"${ctx}/convertTool/ajaxGetTimeStamp.action",
				data:"bjTime="+bjTime,
				type:"POST",
				cache:false,
				async:false,
				dataType:"json",
				success:function(data){
					if(data.timeStamp != null){
						$("#timeStamp").val(data.timeStamp);
					}else{
						$("#timeStamp").val("转换失败");
					}
				},
				error: function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
		}
	}
	
	/* ----------------------- 上传图片 ----------------------- */
	// ajax上传图片
	function ajaxUpload(){
		var url1 = $.trim($("#upload").val());
		var type = "";
		if(url1 != ""){
			type = "upload";
		}
		$.ajaxFileUpload({
			url:'${ctx}/convertTool/ajaxUploadPicFile.action', //需要链接到服务器地址
			type:"POST",
			secureuri:false,
			fileElementId: type, //文件选择框的id属性
			dataType: 'json',
			success: function (data, status){
				if(data.code==1){
					alert("上传图片失败！");
				}else if(data.code==2){
					alert("服务器异常，请稍后重试！");
				}else if(data.code==4){
					alert("文件格式不对，请重新选择！");
				}else if(data.code==0){
					if(type == "upload"){
						$("#vst_img").val(data.url);
						alert("上传图片成功！");
					}
				}else if(data.code==3){
					alert("您还没有选择文件！");
				}else if(data.code==5){
					alert("文件大小不能超过"+data.maxSize+"！");
				}
			},
			error: function (data, status, e){//相当于java中catch语句块的用法
				alert("上传图片失败！");
			}
		});
	}
	
	//查看图片
	function view(id){
		var url=$("#"+id).val();	
		if(url!=null&&url!=""){
			if(url.indexOf("http:")>=0){
			    //此方法只支持谷歌
				window.open('javascript:window.name;', '<script>location.replace("'+url+'")<\/script>');	
			}else{
				url=url.replace("[img]/","https://img.cp33.ott.cibntv.net/").replace("[img]", "https://img.cp33.ott.cibntv.net/");
				var a = document.createElement("a");
			    a.setAttribute("href", url);
			    a.setAttribute("target", "_blank");
			    document.body.appendChild(a);
			    if(a.click){
			        a.click();
			    }
			}			
		}
	}
	
	/* ----------------------- 获取腾讯图片 ----------------------- */
	//获取腾讯图片
	function getTencentPic(){
		var videoUrl = $.trim($("#videoUrl").val());
		if(videoUrl == null || videoUrl == ""){
			alert("视频地址不能为空！");
		}else{
			$.ajax({
				url:"${ctx}/convertTool/ajaxGetTencentPic.action",
				data:"videoUrl="+videoUrl,
				type:"POST",
				cache:false,
				async:false,
				dataType:"json",
				success:function(data){
					if(data.imgUrl != null){
						$("#imgUrl").val(data.imgUrl);
					}else{
						$("#imgUrl").val("找不到！");
					}
				},
				error: function (data, status, e){
			   		alert("异常信息："+e);
			    }
			});
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
	width: 300px;
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
						<span> > 常用工具 > 转换工具</span>
					</section>
	
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box ">
								<div class="box-header">
									<i class="fa   fa-refresh"></i>
									<span class="tool-title">直播pid转换</span>
								</div>
	
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">直播pid：</span></td>
												<td><input type="text" id="live_pid" /></td>
												<td><input type="button" value="转换" onclick="javascript:getStreamId();" /></td>
											</tr>
											<tr>
												<td><span class="td-title">转换值：</span></td>
												<td><input type="text" id="result1" /></td>
												<td><input type="button" value="转换" onclick="javascript:getStreamId();" /></td>
											</tr>
										</form>
									</table>
								</div>
								
								<div class="web-line"></div>
								<div class="box-header">
									<i class="fa   fa-refresh"></i>
									<span class="tool-title">uuid转换</span>
								</div>
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">uuid：</span></td>
												<td><input type="text" id="uuid" /></td>
												<td><input type="button" value="加密" onclick="javascript:encryptUUID();" /></td>
											</tr>
											<tr>
												<td><span class="td-title">转换值：</span></td>
												<td><textarea id="result2" rows="3"></textarea></td>
												<td><input type="button" value="解密" onclick="javascript:decodeUUID();" /></td>
											</tr>
										</form>
									</table>
								</div>
								
								<div class="web-line"></div>
								<div class="box-header">
									<i class="fa   fa-refresh"></i>
									<span class="tool-title">时间戳转换</span>
								</div>
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">时间戳：</span></td>
												<td><input type="text" id="timeStamp" /></td>
												<td><input type="button" value="时间戳转北京时间" onclick="javascript:getBjTime();" /></td>
											</tr>
											<tr>
												<td><span class="td-title">北京时间：</span></td>
												<td>
													<input type="text" class="ic-calendar" id="bjTime" readonly="readonly"
														placeholder="yyyy-MM-dd HH:mm:ss" maxlength="19" />
									       		</td>
												<td><input type="button" value="北京时间转时间戳" onclick="javascript:getTimeStamp();" /></td>
											</tr>
										</form>
									</table>
								</div>
								
								<div class="web-line"></div>
								<div class="box-header">
									<i class="fa   fa-refresh"></i>
									<span class="tool-title">活动图片上传</span>
								</div>
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">图片：</span></td>
												<td>
													<textarea id="vst_img" rows="3" cols="25"></textarea>
												</td>
												<td>
													<input type="button" id="uploadButton" onclick="ajaxUpload()" value="上传" />
												</td>
											</tr>
											<tr>
												<td><span class="td-title"></span></td>
												<td>
													<input type="file" name="upload" id="upload" theme="simple" size="20" />
												</td>
												<td>
													<input type="button" id="viewButton" onclick="view('vst_img')" value="查看" />
												</td>
											</tr>
										</form>
									</table>
								</div>
								
								<div class="web-line"></div>
								<div class="box-header">
									<i class="fa   fa-refresh"></i>
									<span class="tool-title">获取腾讯图片</span>
								</div>
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title">视频链接：</span></td>
												<td>
													<textarea rows="3" cols="25" id="videoUrl"></textarea>
												</td>
												<td>
													<input type="button" value="获取" onclick="javascript:getTencentPic();" />
												</td>
											</tr>
											<tr>
												<td><span class="td-title">图片地址：</span></td>
												<td>
													<textarea rows="3" cols="25" id="imgUrl"></textarea>
												</td>
												<td>
													<input type="button" id="viewButton" onclick="view('imgUrl')" value="查看" />
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
		elem : '#bjTime',
		type : 'datetime',
		format : 'yyyy-MM-dd HH:mm:ss',
		theme : 'molv'
	});
</script>

</body>
</html>