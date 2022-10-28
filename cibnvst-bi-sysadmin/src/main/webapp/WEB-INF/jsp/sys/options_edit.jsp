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

		$("#vst_option_key").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator({
			min:1,
			max:40,
			onerror:"配置关键字不能为空，且最大长度为40个字符！"
		});
		
		$("#vst_option_value").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).functionValidator({
			fun: checkOptionValue,
			onerror:"待上传图片格式不对！"
		});
		
		$("#vst_option_desc").formValidator({
			empty:true,
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator({
			min:0,
			max:255,
			onerror:"备注最大长度为255个字符！"
		});
		
		$("#uploadPic1").click(function(){
			$("#vst_option_value").trigger("focus");
		}).change(function(){
			$("#vst_option_value").trigger("blur");
		});
	});
	
	// 校验配置内容
	function checkOptionValue(){
		var value1 = $.trim($("#vst_option_value").val());
		var value2 = $.trim($("#uploadPic1").val());
		
		if(value2 != ""){
			var type = value2.substring(value2.lastIndexOf(".")+1);
			var reg = new RegExp("(gif|jpg|jpeg|png|bmp|tiff|GIF|JPG|JPEG|PNG|BMP|TIFF)$");
			if(!reg.test(type)){
				return false;
			}
		}/*else if(value1 == ""){
			return false;
		}*/
		
		return true;
	}
	
	// ajax上传图片
	function ajaxUpload(type){
		var uploadPic = $("#uploadPic"+ type).val();
		if(uploadPic == ""){
			alert("您还没有选择文件！");
		}else{
			var fileType = uploadPic.substring(uploadPic.lastIndexOf(".")+1);
			var reg = new RegExp("(gif|jpg|jpeg|png|bmp|tiff|GIF|JPG|JPEG|PNG|BMP|TIFF)$");
			if(!reg.test(fileType)){
				alert("文件格式错误！");
				return false;
			}
			$.ajaxFileUpload({
				url:'${ctx}/options/ajaxUploadPicFile.action?type='+type, //需要链接到服务器地址
				type:"POST",
				secureuri:false,
				fileElementId : 'uploadPic' + type, //文件选择框的id属性
				dataType: 'json',
				success: function (data, status){
					if(data.code==1){
						alert("上传图片失败！");
					}else if(data.code==2){
						alert("服务器异常，请稍后重试！");
					}else if(data.code==0){
						if(type == 1){	//遮挡图片
							$("#vst_option_value").val(data.url);
						}else{
							$("#vst_option_value").val(data.url);
						}
						alert("上传图片成功！");
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
	}
	
	// 查看图片
	function view(id){
		var url = $.trim($("#"+id).val());
		if(url != ""){
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
				<form id="listForm" action="${ctx}/options/editOptions.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 控制面板修改</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">配置关键字：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_option_key" name="vst_option_key" value="${formMap.vst_option_key }" placeholder="请输入配置关键字"
												readonly="readonly" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_option_keyTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">配置内容：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_option_value" name="vst_option_value"
												style="resize: none; height: 100px;" placeholder="请输入配置内容">${formMap.vst_option_value }</textarea>
										</div>
										<div class="add_m_r">
											<a style="cursor: pointer;" class="file">
												选择文件<input type="file" style="cursor: pointer;" name="upload" id="uploadPic1" theme="simple" size="20" />
											</a>
											<input type="button" class="inputImgbtn01" id="uploadButton1" onclick="ajaxUpload(1)" value="上传"/>
											<input type="button" class="inputImgbtn02" id="viewButton1" onclick="view('vst_option_value')" value="查看" />
										</div>
									</div>
									<div class="add_r">
										<span id="vst_option_valueTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_option_desc" name="vst_option_desc" placeholder="请输入备注"
												style="resize: none; height: 100px;">${formMap.vst_option_desc }</textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_option_descTip"></span>
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