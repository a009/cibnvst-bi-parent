<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<head>
<title>报表管理中心</title>

<script type="text/javascript">
	//下拉列表框，赋值
	$(function(){
		$("#vst_sys_job").val(5);
	});

	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "listForm",
			wideword : false,
			onerror : function(msg) {
			},
			onsuccess : doSubmit
		});
		
		$("#vst_sys_name").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min:1,
			max:30,
			onerror :"用户昵称长度在1~30个字符！"
		}).regexValidator({
			regexp :"^(.*){1,30}$",
			datatype :"string",
			onerror :"用户昵称格式错误！"
		});

		$("#vst_sys_username").formValidator({
	  		onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator({
			min:5,
			max:15,
			onerror:"登录帐号的长度在5~15之间！"
		}).regexValidator({
			regexp:"username",
			datatype:"enum",
			onerror:"登录帐号格式错误！"
		}).functionValidator({
			fun :checkUserName,
			onerror :"登录帐号已存在，换一个试试！"
		});
	
		$("#vst_sys_password").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :6,
			max :15,
			empty : {
				leftempty :false,
				rightempty :false,
				emptyerror :"密码不能为空！"
			},
			onerror :"密码长度在6~15个字符之间，包括6和15！"
		}).regexValidator({
			regexp :"password",
			datatype:"enum",
			onerror :"密码不能全是数字或者字母！"
		});
	
		$("#confirm_sys_password").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :1,
			onerror :"确认密码不能为空！"
		}).compareValidator( {
			desid :"vst_sys_password",
			operateor :"=",
			onerror :"两次输入的密码不一致，请重新输入确认密码！"
		});

		$("#vst_role_id").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :1,
			onerror :"您还没有选择角色！"
		});
		
		$("#vst_sys_division").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :1,
			onerror :"您还没有选择所属部门！"
		});
		
		$("#vst_sys_job").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :1,
			onerror :"您还没有选择职位！"
		});

		$("#vst_sys_channel").formValidator( {
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :1,
			max :150,
			onerror :"渠道号不可为空，且最长为150个字符"
		});
		
		$("#vst_sys_photo").formValidator({
			onshow :"",
			onfocus :"",
			oncorrect :""
		}).inputValidator( {
			min :0,
			max :150,
			onerror :"头像最长为150个字符"
		}).functionValidator({
			fun :checkPhoto,
			onerror :"您输入的头像格式错误，请检查！"
		});
		
		$("#vst_sys_summary").formValidator( {
			empty :true,
			onshow :"",
			onfocus :"",
			oncorrect :"",
			onempty :"备注为空！"
		}).inputValidator( {
			max :500,
			onerror :"备注最长为500个字符"
		});
	});
	
	//检测登录帐号是否存在
	function checkUserName(){
		var result = true;
		var vst_sys_username = $.trim($("#vst_sys_username").val());
		$.ajax({
			url:"${ctx}/sysUser/ajaxCheckUsername.action",
			data:"vst_sys_username=" + vst_sys_username,
			type:"POST",
			cache:false,
			async:false,
			dataType:"json",
			success:function(data){
				if(data == true){
					result = false;
				}
			}
		});
		return result;
	}

	// 校验图片格式
	function checkPhoto(){
		var url1 = $.trim($("#vst_sys_photo").val());
		var url2 = $.trim($("#uploadPic1").val());
		if(url2 != ""){
			var type = url2.substring(url2.lastIndexOf(".")+1);
			var reg = new RegExp("(gif|jpg|jpeg|png|bmp|tiff)$");
			if(!reg.test(type)){
				return false;
			}
		}		
		if(url1 != "" && url1.indexOf("http:")!=0){
			var type = url1.substring(url1.lastIndexOf(".")+1);
			var reg = new RegExp("(gif|jpg|jpeg|png|bmp|tiff)$");
			if(!reg.test(type)){
				return false;
			}
		}		
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
				url:'${ctx}/sysUser/ajaxUploadPicFile.action?type=' + type, //需要链接到服务器地址
				type:"POST",
				secureuri:false,
				fileElementId:'uploadPic'+ type, //文件选择框的id属性  
				dataType: 'json',
				success: function (data, status){
					if(data.code==1){
						alert("上传图片失败！");
					}else if(data.code==2){
						alert("服务器异常，请稍后重试！");
					}else if(data.code==0){
						if(type == 1){
							$("#vst_sys_photo").val(data.url);
						}else{
							$("#vst_sys_photo").val(data.url);
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
		$("#btnAdd").attr("disabled", "disabled");
		return true;
	}

	//查找角色列表
	function openSelectRole() {
		var url = "${ctx}/sysRole/getRoleList.action";

		var returnValue = "";
		if(navigator.userAgent.indexOf("Chrome") >0){// 这里是为了兼容谷歌浏览器
			window.open(url, '', 'dialogWidth:800px;dialogHeight=500px;center=yes;status:yes;help:no;');
		}else{
			returnValue = window.showModalDialog(url, '', 'dialogWidth:800px;dialogHeight=500px;center=yes;status:yes;help:no;');
		}

		checkValue(returnValue);
	}

	function checkValue(returnValue){
		if (returnValue != undefined) {
			if (returnValue[0] != undefined) {
				$("#vst_role_id").val(returnValue[0]);
				$("#roleNames").val(returnValue[1]);
				$("#roleNames").focus();
			} else {
				$("#vst_role_id").val("");
				$("#roleNames").val("");
				$("#roleNames").focus();
			}
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
				<form id="listForm" action="${ctx}/sysUser/addUser.action" method="post" enctype="multipart/form-data">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 系统管理 > 系统用户新增</span>
					</section>
					<section class="content">
					<div class="body-content">
						<div id="move">
							<ul id='example2' class="line03">
								<li>
									<div class="add_l">用户名称：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sys_name" name="vst_sys_name" placeholder="请输入用户名称" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_nameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">登录帐号：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sys_username" name="vst_sys_username" placeholder="请输入登录帐号" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_usernameTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">登录密码：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="password" class="input" id="vst_sys_password" name="vst_sys_password" placeholder="请输入登录密码" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_passwordTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">确认密码：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="password" class="input" id="confirm_sys_password" name="confirm_sys_password" placeholder="请输入确认密码" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="confirm_sys_passwordTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">头像：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sys_photo" name="vst_sys_photo"
												style="resize: none; height: 100px;" placeholder="请输入头像"></textarea>
										</div>
										<div class="add_m_r">
											<a style="cursor: pointer;" class="file">
												选择文件<input type="file" style="cursor: pointer;" name="upload" id="uploadPic1" theme="simple" size="20" />
											</a>
											<input type="button" class="inputImgbtn01" id="uploadButton1" onclick="ajaxUpload(1)" value="上传"/>
											<input type="button" class="inputImgbtn02" id="viewButton1" onclick="view('vst_sys_photo')" value="查看" />
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_photoTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">角色：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="hidden" id="vst_role_id" name="vst_role_id" />
							        		<input class="input" type="text" id="roleNames" readonly="readonly" placeholder="请选择角色" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
						 					<img src="${ctx}/pub/images/search.gif" width="18" height="20" align="absmiddle" style="cursor:pointer;" onClick="openSelectRole();" />
										</div>
									</div>
									<div class="add_r">
										<span id="vst_role_idTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">所属部门：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select class="input" id="vst_sys_division" name="vst_sys_division">
							    				<option value="">请选择</option>
							 					<c:forEach items="${divisions}" var="division">
						  	 						<option value="${division.key }">${division.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_divisionTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">职位：</div>
									<div class="add_m">
										<div class="add_m_l">
											<select	class="input" id="vst_sys_job" name="vst_sys_job">
							 					<option value="">请选择</option>
							 					<c:forEach items="${jobs}" var="job">
						  	 						<option value="${job.key }">${job.value }</option>
						  	 					</c:forEach>
							    			</select>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_jobTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">渠道号：</div>
									<div class="add_m">
										<div class="add_m_l">
											<input type="text" class="input" id="vst_sys_channel" name="vst_sys_channel" value="all" placeholder="all代表全部，多个用逗号分割" />
										</div>
										<div class="add_m_r">
											<span class="red">*</span>
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_channelTip"></span>
									</div>
								</li>
								<li>
									<div class="add_l">备注：</div>
									<div class="add_m">
										<div class="add_m_l">
											<textarea class="input" id="vst_sys_summary" name="vst_sys_summary"
												style="resize: none; height: 100px;" placeholder="请输入备注"></textarea>
										</div>
										<div class="add_m_r">
											
										</div>
									</div>
									<div class="add_r">
										<span id="vst_sys_summaryTip"></span>
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