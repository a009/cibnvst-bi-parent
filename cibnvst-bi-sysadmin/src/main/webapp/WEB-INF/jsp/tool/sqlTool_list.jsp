<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
<title>报表管理中心</title>

<script type="text/javascript">

	/* ----------------------- SQL查询 ----------------------- */
	//查询sql语句
	function querySQL(){
		var sql_currPage = $.trim($("#sql_currPage").val());
		var sql_singleCount = $.trim($("#sql_singleCount").val());
		var reg = /^[1-9][0-9]*$/;
		if(reg.test(sql_currPage) && reg.test(sql_singleCount)){
			if(sql_singleCount > 0 && sql_singleCount <= 100){
				var sql_script = $.trim($("#sql_script").val());
				if(sql_script.toLowerCase().indexOf("select") >= 0){
					$.ajax({
						url : "${ctx}/sqlTool/ajaxQuery.action",
						data : {sql_currPage : sql_currPage, sql_singleCount : sql_singleCount, sql_script : sql_script},
						type : "POST",
						cache : false,
						async : false,
						dataType : "json",
						success : function(result){
							if(result.code == 100){
								var data = result.data;
								if(data != null){
									$("#sql_result").val(JSON.stringify(data));
									$("#sql_result_count").html(data.length);
									$("#sql_result_time").html(result.time);
								}else{
									alert("查询失败！");
								}
							}else{
								alert(result.msg);
							}
						},
						error : function (data, status, e){
					   		alert("异常信息："+e);
					    }
					});
				}else{
					alert("查询语句不合法！");
				}
			}else{
				alert("条数只能在1~100！");
			}
		}else{
			alert("分页信息，必须是正整数！");
		}
	}

	//复制查询结果
	function copyData(){
		var sql_result = $.trim($("#sql_result").val());
		if(sql_result == null || sql_result == ""){
			alert("复制内容为空！");
		}else{
			var obj = document.getElementById("sql_result");
			obj.select();
			document.execCommand("Copy");
			alert("复制成功！");
		}
	}

	//导出查询结果
	function exportData(){
		var sql_result = $.trim($("#sql_result").val());
		if(sql_result == null || sql_result == ""){
			alert("导出内容为空！");
		}else{
            // 将字符串用txt的格式报存 ie中会出现中文乱码的问题
            var saveAs = saveAs || (function (view) {
                "use strict";
				// IE <10 is explicitly unsupported
				if (typeof view === "undefined" || typeof navigator !== "undefined" && /MSIE [1-9]\./.test(navigator.userAgent)) {
					return;
				}
				var doc = view.document,
					get_URL = function () {
						return view.URL || view.webkitURL || view;
					},
					save_link = doc.createElementNS("http://www.w3.org/1999/xhtml", "a"),
					can_use_save_link = "download" in save_link,
					click = function (node) {
						var event = new MouseEvent("click");
						node.dispatchEvent(event);
					},
					is_safari = /constructor/i.test(view.HTMLElement) || view.safari,
					is_chrome_ios = /CriOS\/[\d]+/.test(navigator.userAgent),
					throw_outside = function (ex) {
						(view.setImmediate || view.setTimeout)(function () {
							throw ex;
						}, 0);
					},
					force_saveable_type = "application/octet-stream",
					arbitrary_revoke_timeout = 1000 * 40, // in ms
					revoke = function (file) {
						var revoker = function () {
							if (typeof file === "string") { // file is an object URL
								get_URL().revokeObjectURL(file);
							} else { // file is a File
								file.remove();
							}
						};
						setTimeout(revoker, arbitrary_revoke_timeout);
					},
					dispatch = function (filesaver, event_types, event) {
						event_types = [].concat(event_types);
						var i = event_types.length;
						while (i--) {
							var listener = filesaver["on" + event_types[i]];
							if (typeof listener === "function") {
								try {
									listener.call(filesaver, event || filesaver);
								} catch (ex) {
									throw_outside(ex);
								}
							}
						}
					},
					auto_bom = function (blob) {
						// prepend BOM for UTF-8 XML and text/* types (including HTML)
						// note: your browser will automatically convert UTF-16 U+FEFF to EF BB BF
						if (/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(blob.type)) {
							return new Blob([String.fromCharCode(0xFEFF), blob], {type: blob.type});
						}
						return blob;
					},
					FileSaver = function (blob, name, no_auto_bom) {
						if (!no_auto_bom) {
							blob = auto_bom(blob);
						}
						// First try a.download, then web filesystem, then object URLs
						var filesaver = this,
							type = blob.type,
							force = type === force_saveable_type,
							object_url,
							dispatch_all = function () {
								dispatch(filesaver, "writestart progress write writeend".split(" "));
							},
							// on any filesys errors revert to saving with object URLs
							fs_error = function () {
								if ((is_chrome_ios || (force && is_safari)) && view.FileReader) {
									// Safari doesn't allow downloading of blob urls
									var reader = new FileReader();
									reader.onloadend = function () {
										var url = is_chrome_ios ? reader.result : reader.result.replace(/^data:[^;]*;/, 'data:attachment/file;');
										var popup = view.open(url, '_blank');
										if (!popup) view.location.href = url;
										url = undefined; // release reference before dispatching
										filesaver.readyState = filesaver.DONE;
										dispatch_all();
									};
									reader.readAsDataURL(blob);
									filesaver.readyState = filesaver.INIT;
									return;
								}
								// don't create more object URLs than needed
								if (!object_url) {
									object_url = get_URL().createObjectURL(blob);
								}
								if (force) {
									view.location.href = object_url;
								} else {
									var opened = view.open(object_url, "_blank");
									if (!opened) {
										// Apple does not allow window.open, see https://developer.apple.com/library/safari/documentation/Tools/Conceptual/SafariExtensionGuide/WorkingwithWindowsandTabs/WorkingwithWindowsandTabs.html
										view.location.href = object_url;
									}
								}
								filesaver.readyState = filesaver.DONE;
								dispatch_all();
								revoke(object_url);
							};
						filesaver.readyState = filesaver.INIT;

						if (can_use_save_link) {
							object_url = get_URL().createObjectURL(blob);
							setTimeout(function () {
								save_link.href = object_url;
								save_link.download = name;
								click(save_link);
								dispatch_all();
								revoke(object_url);
								filesaver.readyState = filesaver.DONE;
							});
							return;
						}

						fs_error();
					},
					FS_proto = FileSaver.prototype,
					saveAs = function (blob, name, no_auto_bom) {
						return new FileSaver(blob, name || blob.name || "download", no_auto_bom);
					};
				// IE 10+ (native saveAs)
				if (typeof navigator !== "undefined" && navigator.msSaveOrOpenBlob) {
					return function (blob, name, no_auto_bom) {
						name = name || blob.name || "download";

						if (!no_auto_bom) {
							blob = auto_bom(blob);
						}
						return navigator.msSaveOrOpenBlob(blob, name);
					};
				}

				FS_proto.abort = function () {};
				FS_proto.readyState = FS_proto.INIT = 0;
				FS_proto.WRITING = 1;
				FS_proto.DONE = 2;

				FS_proto.error =
					FS_proto.onwritestart =
						FS_proto.onprogress =
							FS_proto.onwrite =
								FS_proto.onabort =
									FS_proto.onerror =
										FS_proto.onwriteend =
											null;

				return saveAs;
			}(
				typeof self !== "undefined" && self
				|| typeof window !== "undefined" && window
				|| this.content
			));

            if (typeof module !== "undefined" && module.exports) {
                module.exports.saveAs = saveAs;
            } else if ((typeof define !== "undefined" && define !== null) && (define.amd !== null)) {
                define("FileSaver.js", function () {
                    return saveAs;
                });
            }

            // 引入上边的js后，就可以调用生成文本的方法 另外ie下会有中文乱码的问题
            var blob = new Blob([sql_result], {type: "text/plain;charset=utf-8"});
            saveAs(blob, "sql_data.txt");
		}
	}
</script>

<style>
.tool-title{
	font-size:20px;
}

input[type="text"]{
	width: 1000px;
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
	width: 1000px;
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
		
		<div class="content-wrapper" style="background-color: #ecf0f5;">
			<div class="content-roll">
				<form action="" id="listForm" method="post">
					<%@include file="../share/sharForm.jsp"%>
					<section class="content-header">
						<i class="fa fa-home"></i>
						<a href="${ctx}/sysMain/index.action">首页</a>
						<span> > 常用工具 > SQL工具</span>
					</section>
	
					<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box ">
								<div class="web-line"></div>
								<div class="box-header">
									<i class="fa   fa-refresh"></i>
									<span class="tool-title">SQL查询</span>
								</div>
								<div class="box-body">
									<table>
										<form>
											<tr>
												<td><span class="td-title" style="margin-bottom:10px;">分页条件：</span></td>
												<td>
													<div style="margin-bottom:10px;">
														页码：
														<input type="text" id="sql_currPage" value="1"
															style="clear:both; width:100px;" />
														&nbsp;&nbsp;&nbsp;&nbsp;
														条数：
														<input type="text" id="sql_singleCount" value="10"
															style="clear:both; width:100px;" />
													</div>
												</td>
												<td></td>
											</tr>
											<tr>
												<td><span class="td-title">SQL语句：</span></td>
												<td>
													<textarea rows="5" cols="100" id="sql_script"
														placeholder="查询的是报表库，只能做查询操作"></textarea>
												</td>
												<td>
													<input type="button" value="执行" onclick="javascript:querySQL();" />
												</td>
											</tr>
											<tr>
												<td><span class="td-title">查询结果：</span></td>
												<td>
													<textarea rows="20" cols="100" id="sql_result"></textarea>
												</td>
												<td>
													<input type="button" value="复制" onclick="javascript:copyData();" />
													<br/>
													<input type="button" value="导出" onclick="javascript:exportData();" />
												</td>
											</tr>
											<tr>
												<td></td>
												<td>
													查询条数：<span id="sql_result_count" style="color:blue; margin-right:50px;">0</span>
													查询时间：<span id="sql_result_time" style="color:red;">0</span>ms
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
				</form>
			</div>
		</div>
		<%@include file="../share/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
</html>