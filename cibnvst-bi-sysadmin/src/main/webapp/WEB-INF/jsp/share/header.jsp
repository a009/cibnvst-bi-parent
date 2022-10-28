<%@ page language="java" pageEncoding="UTF-8"%>

<script language="JavaScript">
	// 退出登录
	function logout(){
		if(confirm("您确定要退出吗?")){
			top.location = "${ctx}/sysMain/logout.action";
			//top.location = "${ctx}";
		}else{
			return false;
		}
	};

	// 切换首页
	function changeHome(){
		var homeType = $("#homeType").val();
		if(homeType != 2){
			var username = '${sessionScope.session_key_user.loginInfo.loginName}';
			var password = '${sessionScope.session_key_user.loginInfo.loginPassword}';
			if(username != null && password != null){
				var checkUrl;
				var homeUrl;
				if(homeType == 1){
					checkUrl = "https://admin.cibnvst.com/cibnvst-sysadmin/sysMain/checkLoginUser.action";
					homeUrl = "https://admin.cibnvst.com/cibnvst-sysadmin/sysMain/changeHome.action";
				}else if(homeType == 2){
					checkUrl = "https://bi.cibnvst.com/cibnvst-bi-sysadmin/sysMain/checkLoginUser.action";
					homeUrl = "https://bi.cibnvst.com/cibnvst-bi-sysadmin/sysMain/changeHome.action";
				}else if(homeType == 3){
					checkUrl = "https://admin.user.cibnvst.com/cibnvst-user-sysadmin/sysMain/checkLoginUser.action";
					homeUrl = "https://admin.user.cibnvst.com/cibnvst-user-sysadmin/sysMain/changeHome.action";
				}else if(homeType == 4){
					checkUrl = "http://admin.xiaomaocloud.com/kitten-sysadmin/admin/checkLoginUser.html";
					homeUrl = "http://admin.xiaomaocloud.com/kitten-sysadmin/admin/changeHome.html";
				}
				if(checkUrl != null){
					$.ajax({
						url : checkUrl,
						data : {username : username, password : password},
						type : "POST",
						cache : false,
						async : false,
						dataType : "json",
						success : function(data){
							if(data.code == 100){
								$("#home_username").val(username);
								$("#home_password").val(password);
								$("#homeForm").attr("action", homeUrl);
								$("#homeForm").submit();
							}else{
								alert(data.msg);
							}
						}
					});
				}
			}
		}
	}
	
	// 进入可视化
	function goVisual(){
		var url = "https://admin.cibnvst.com/cibnvst-sysadmin/visualhome/index.action";
		/*var username = '${sessionScope.session_key_user.vstSysUser.vst_sys_username}';
		var password = '${sessionScope.session_key_user.vstSysUser.vst_sys_password}';
		url += "?username="+username+"&password="+password;*/
		window.open(url);
	}
</script>

<script type="text/javascript" language="javascript">
	function clockon() {
		var now = new Date();
		var year = now.getFullYear();//年
		var month = now.getMonth();//月
		var date = now.getDate();//日
		var day = now.getDay();//天
		var hour = now.getHours();//小时
		var minu = now.getMinutes();//分钟
		var sec = now.getSeconds();//秒钟
		
		month = checkTime(month+1);
		date = checkTime(date);
		hour = checkTime(hour);
		minu = checkTime(minu);
		sec = checkTime(sec);
		
		var time = year + "-" + month + "-" + date + " " + hour + ":" + minu + ":" + sec;
		//lable标签id="bgclock"
		//$("#bgclock").html(time);
		document.getElementById("bgclock").innerText = time;
	}
	 
	function checkTime(i) {
		if(i < 10){
			i = "0" + i;
		}
		return i;
	}
	
	//设置1秒调用一次clockon函数
	setInterval("clockon()", 1000);//1000毫秒=1秒
</script>

<script>
	// ajax上传图片
	function ajaxUploadSysPhoto(){
		var url1 = $.trim($("#img-upload-file").val());
		var type = "";
		if(url1 != ""){
			var style = url1.substring(url1.lastIndexOf(".")+1);
			var reg = new RegExp("(gif|jpg|jpeg|png|bmp|tiff|GIF|JPG|JPEG|PNG|BMP|TIFF)$");
			if(!reg.test(style)){
				alert("您选择的头像格式不支持，请重新选择！");
				return false;
			}
			type = "img-upload-file";
		}else{
			alert("您还没有选择头像！");
			return false;
		}
		$.ajaxFileUpload({
			url:'${ctx}/sysUser/ajaxUploadPicFile.action?type=' + 0, //需要链接到服务器地址
			type:"POST",
			secureuri:false,
			fileElementId: type, //文件选择框的id属性
			dataType: 'json',
			success: function (data, status){
				if(data.code==1){
					alert("上传头像失败！");
				}else if(data.code==2){
					alert("服务器异常，请稍后重试！");
				//}else if(data.code==4){
					//alert("您选择的头像格式不支持，请重新选择！");
				}else if(data.code==0){
					if(type == "img-upload-file"){
						//location.reload();//刷新页面
						$(".user-image").attr("src",data.url);
						$(".img-circle").attr("src",data.url);
						alert("上传头像成功！");
					}
				}else if(data.code==3){
					alert("您还没有选择头像！");
				}else if(data.code==5){
					alert("文件大小不能超过"+data.maxSize+"！");
				}
			},
			error: function (data, status, e){//相当于java中catch语句块的用法  
				alert("上传头像失败！");  
			}
		});
	}
</script>

<!-- 包名选择 -->
<script>
	$(function () {
		var pkgNameMenuLi = $(".pkgName-menu>li");
		if(pkgNameMenuLi.length && pkgNameMenuLi.length>0){
			var oldPkg = '${sessionScope.pkgName}';
			if(oldPkg != null && oldPkg != ''){	//如果保存了包名，就选中保存的包名
	            for(var i=0; i<pkgNameMenuLi.length; i++){
	                if(pkgNameMenuLi.eq(i).attr('data-value') == oldPkg){
	                	pkgNameMenuLi.eq(i).addClass("active");
	    	            $("#pkgName").val(pkgNameMenuLi.eq(i).attr("data-value"));
	    	            $(".pkgName .pkgName-text").html(pkgNameMenuLi.eq(i).html());
	    	            break;
	                }
	            }
			}else{	//如果没有保存，就选中首项
				pkgNameMenuLi.eq(0).addClass("active");
	            $("#pkgName").val(pkgNameMenuLi.eq(0).attr("data-value"));
	            $(".pkgName .pkgName-text").html(pkgNameMenuLi.eq(0).html());
			}
		}
		// 当包名发生改变时，保存改变后的包名
	    pkgNameMenuLi.click(function () {
	        pkgNameMenuLi.removeClass("active");
	        $(this).addClass("active");
	        $("#pkgName").val($(this).attr("data-value"));
            $(".pkgName .pkgName-text").html($(this).html());

            $.ajax({
    			type: "POST",
    			data: "pkgName=" + $("#pkgName").val(),
    			async : true,
    			cache : false,
    			url:"${ctx}/sysMain/savePkgName.action",
    			dataType:"json",
    			success:function(msg){
    				
    			}
    		});
	    });
        // 切换系统
        var dropDownMenuLi = $(".header-dropDown-menu>li");
        dropDownMenuLi.click(function () {
            dropDownMenuLi.removeClass("active");
            $(this).addClass("active");
            $("#homeType").val($(this).attr("data-value"));
            $(".header-dropDown .header-dropDown-text").html($(this).html());
            changeHome();
        });
	});
</script>

<!-- 轮询消息 -->
<script>
    //下载文件
    function download_file(url) {
        console.log(url);
        var iframe = document.createElement("iframe")
        iframe.style.display = "none";
        iframe.src = url;
        document.body.appendChild(iframe);
    }
    
    $(function () {
        function getRealtimeMessage() {
        	$.ajax({
    			type : "POST",
    			async : true,
    			cache : false,
    			url : "${ctx}/exportTask/getMessage.action",
    			dataType : "json",
    			success : function(data){
    				if(data.code == 100){
                        layer.closeAll();
                        var html = '<table class="table"><thead><tr><th>时间</th><th>文件名</th><th>操作</th></tr></thead><tbody>';
                        $.each(data.taskSuccess, function (index, value) {
                            var url = '${ctx}/exportTask/downloadFile.action?url='+value.vst_task_file_path
                            html += '<tr><td>'+moment(new Date(value.vst_task_uptime)).format('YYYY-MM-DD HH:MM:ss')+'</td><td>'+value.vst_task_file_name+'</td><td><a href="'+url+'" style="color: #00a7d0;">下载</a></td></tr>';
                        });
                        html += '</tbody></table>';
                        //边缘弹出
                        layer.open({
                            type : 1,
                            offset : 'rb',
                            area : ['500px', 'auto'],
                            anim : 2,	//从最底部往上滑入
                            content : '<div style="padding: 10px;max-height: 400px;overflow: auto;">'+html+'</div>',
                            btn : '',
                            btnAlign : 'c',	//按钮居中
                            shade : 0,	//不显示遮罩
                            yes : function(){
                                layer.closeAll();
                            }
                        });
                    }
    				getRealtimeMessage();
    			}
    		});
    		
            /*$.getJSON('${ctx}/exportTask/getMessage.action', {}, function(data){
                console.log(data);
                if (data.code == 100) {
                    layer.closeAll();
                    var html = '<table class="table"><thead><tr><th>时间</th><th>文件名</th><th>操作</th></tr></thead><tbody>';
                    $.each(data.taskSuccess,function (index, value) {
                        var url = '${ctx}/exportTask/downloadFile.action?url='+value.vst_task_file_path
                        html += '<tr><td>'+moment(new Date(value.vst_task_uptime)).format('YYYY-MM-DD HH:MM:ss')+'</td><td>'+value.vst_task_file_name+'</td><td><a href="'+url+'" style="color: #00a7d0;">下载</a></td></tr>';
                    });
                    html += '</tbody></table>';
                    //边缘弹出
                    layer.open({
                        type : 1,
                        offset : 'rb',
                        area : ['500px', 'auto'],
                        anim : 2,	//从最底部往上滑入
                        content : '<div style="padding: 10px;max-height: 400px;overflow: auto;">'+html+'</div>',
                        btn : '',
                        btnAlign : 'c',	//按钮居中
                        shade : 0,	//不显示遮罩
                        yes : function(){
                            layer.closeAll();
                        }
                    });
                } else {

                }
                getRealtimeMessage();
            });*/
        }

        // 需要轮训的时候，再去掉注释
        // getRealtimeMessage();
    });
</script>

<!-- Main Header -->
<header class="main-header">
    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
    	<div class="navbar-logo">
    		<img style="float: left;margin-right: 10px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFkAAAAgCAYAAAB5JtSmAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyhpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OUM0N0M5RkQ1ODhEMTFFN0FFN0ZDQjVFNUI0NDg0NjkiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OUM0N0M5RkU1ODhEMTFFN0FFN0ZDQjVFNUI0NDg0NjkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo5QzQ3QzlGQjU4OEQxMUU3QUU3RkNCNUU1QjQ0ODQ2OSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5QzQ3QzlGQzU4OEQxMUU3QUU3RkNCNUU1QjQ0ODQ2OSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Ptp8RJgAAAuBSURBVHja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"/>
    	</div>
        <div class="pkgName" style="float: left;margin-right: 10px;">
            <div class="pkgName-icon">
                <span class="pkgName-text"></span>
                <i class="fa fa-caret-down"></i>
            </div>
            <ul class="pkgName-menu">
                <c:forEach items="${pkgNames}" var="pkg">
                    <li data-value="${pkg.key }">${pkg.value }</li>
                </c:forEach>
            </ul>
            <input type="hidden" id="pkgName" value="net.myvst.v2" />
        </div>
        <div class="navbar-custom-menu">
        	<ul class="nav navbar-nav">
        		<li>
					<a onclick="goVisual()"><i class="fa fa-eye" style="line-height: 40px;font-size: 30px;"></i></a>
				</li>
        		<li style="height: 50px;">
                    <div class="header-dropDown">
                        <div class="header-dropDown-icon">
                            <span class="header-dropDown-text">报表后台</span>
                            <i class="fa fa-caret-down"></i>
                        </div>
                        <ul class="header-dropDown-menu">
                            <li data-value="1">管理后台</li>
                            <li data-value="2" class="active">报表后台</li>
                            <li data-value="3">积分系统</li>
                        </ul>
                        <input type="hidden" id="homeType" value="2" />
                    </div>
		    		<form id="homeForm" method="post" style="display: none;">
		    			<input type="hidden" id="home_username" name="username" />
		    			<input type="hidden" id="home_password" name="password" />
		    		</form>
				</li> 
        	</ul>
        	
            <ul class="nav navbar-nav">
                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <!-- The user image in the navbar-->
                        <c:choose>
		    				<c:when test="${sessionScope.session_key_user.vstSysUser.vst_sys_photo!=null && sessionScope.session_key_user.vstSysUser.vst_sys_photo!=''}">
		    					<img src="${sessionScope.session_key_user.vstSysUser.vst_sys_photo}" class="user-image" alt="User Image">
		    				</c:when>
		    				<c:otherwise>
		    					<img src="${ctx}/pub/images/nophoto.jpg" class="user-image" alt="User Image">
		    				</c:otherwise>
		    			</c:choose>
                        <span class="hidden-xs">${sessionScope.session_key_user.vstSysUser.vst_sys_name}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- The user image in the menu -->
                        <li class="user-header">
                            <c:choose>
			    				<c:when test="${sessionScope.session_key_user.vstSysUser.vst_sys_photo!=null && sessionScope.session_key_user.vstSysUser.vst_sys_photo!=''}">
			    					<img src="${sessionScope.session_key_user.vstSysUser.vst_sys_photo}" class="img-circle" alt="User Image">
			    				</c:when>
			    				<c:otherwise>
			    					<img src="${ctx}/pub/images/nophoto.jpg" class="img-circle" alt="User Image">
			    				</c:otherwise>
			    			</c:choose>

                            <p>
                                ${sessionScope.session_key_user.vstSysUser.vst_sys_name}
                                <small>
                            		<label id="bgclock"><script language="javascript">clockon();</script></label>
                            	</small>
                            </p>
                        </li>
                        <!-- Menu Body -->
                        <li class="user-body">
                            <div class="pull-left">
                                <input type="file" style="display: none" id="img-upload-file" name="upload" theme="simple" size="20" />
                                <div class="btn btn-default btn-flat" onclick="$('#img-upload-file').click()">修改头像</div>
                            </div>
                            <div class="pull-right">
                                <div class="btn btn-default btn-flat" onclick="ajaxUploadSysPhoto()">保存头像</div>
                            </div>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">

                            <div class="pull-right">
                                <p onClick="logout()" class="btn btn-default btn-flat">退出登录<p>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>
