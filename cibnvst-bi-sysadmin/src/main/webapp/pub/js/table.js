// 去所有文本框、密码框、文本域及FILE控件两端空格
function trimBothSidesSpaces() {
	$(":text, :password, textarea, :file").each(function() {
		$(this).val($.trim($(this).val()));
	});
}

//全选
function checkAll(){
	$(":checkbox[name=recordIds]").each(function(){
		$(this).prop("checked", $("#chkall").prop("checked"));
	});		
}

function sel() {
	$("#queryTable").toggle();
	$("#queryDiv").toggle();
	if ($("#queryTable").css("display") == "none") {
		$("#showImg").prop("src", ctx + "/pub/images/display.gif");
	} else {
		$("#showImg").prop("src", ctx + "/pub/images/hide.gif");
	};
}

$(document).ready(function(){
	$('#dataListTbl').children().children("tr[id!=nc]").each(function(elem, index){
		var highlightcolor = '#e1e5ee';
		var clickcolor='#51b2f6';
		var originalBg = $(this).css("backgroundColor");
		$(this).hover(
				function(){
					if($(this).css("backgroundColor") != clickcolor) {
						$(this).css("backgroundColor", highlightcolor);
					}
				},
				function(){
					if($(this).css("backgroundColor") != clickcolor) {
						$(this).css("backgroundColor", originalBg);
					}
				}
		);
	});
	
	
	
	//鼠标经过表格背景颜色（如查询、新增、修改列表）
	$(".table .table-tbody ul").mouseover(function () {
		$(this).css("background","#dcdddd");//深灰
	});
	$(".table .table-tbody ul").mouseout(function () {
		//非列头所有tr
		//$(this).css("background","#fff");//白
		//$(this).css("background","#ecf0f5");//浅灰
		
		//新增、修改、弹框等
		//$("#example2 tr").css("background","#fff");//白
		$(".line03 tr").css("background","#fff");//白
		
		//查询列表
		$(".row2").css("background","#fff");//白
		$(".row1").css("background","#f3f3f3");//浅粉
	});
	
	//鼠标经过表格背景颜色（如查询、新增、修改列表）
	//--待删除
	$(".table tbody tr").mouseover(function () {
		$(this).css("background","#dcdddd");//深灰
	});
	$(".table tbody tr").mouseout(function () {
		$(".line03 tr").css("background","#fff");//白
		//查询列表
		$(".row2").css("background","#fff");//白
		$(".row1").css("background","#f3f3f3");//浅粉
	});
	
	//点击列头全勾选（如查询列表）
	//$("#rowHead").click(function(){
	/*$("#tableExample1").on("click", "tr[id^=rowHead]", function () {
		var input = $(this).find("input");
	    if (!$(input).prop("checked")) {
	        $(input).prop("checked",true);
	        $(":checkbox[name=recordIds]").each(function(){
				$(this).prop("checked", true);
			});
	    }else{
	        $(input).prop("checked",false);
	        $(":checkbox[name=recordIds]").each(function(){
				$(this).prop("checked", false);
			});
	    }
	});*/
	
	//点击非列头单个勾选（如查询列表）
	$("#tableExample1").on("click", ".table .table-tbody ul", function () {
	    var input = $(this).find("input");
	    if (!$(input).prop("checked")) {
	        $(input).prop("checked",true);
	    }else{
	        $(input).prop("checked",false);
	    }
	});
	
	//点击非列头单个勾选（如查询列表）
	//--待删除
	$("#tableExample1").on("click", ".table tbody tr", function () {
		var input = $(this).find("input");
		if (!$(input).prop("checked")) {
			$(input).prop("checked",true);
		}else{
			$(input).prop("checked",false);
		}
	});
	
	//多选框 防止事件冒泡
    $("#tableExample1").on("click", "input", function (event) {
	    event.stopImmediatePropagation();
	});
	
});

function jumpPage(pageNo) {
	$("#currPage").val(pageNo);
	$('form:last').submit();
}

function sort(orderBy, defaultOrder) {
	if ($("#orderBy").val() == orderBy) {
		if ($("#order").val() == "") {
			$("#order").val(defaultOrder);
		} else if ($("#order").val() == "desc") {
			$("#order").val("asc");
		} else if ($("#order").val() == "asc") {
			$("#order").val("desc");
		}
	} else {
		$("#orderBy").val(orderBy);
		$("#order").val(defaultOrder);
	}
	$('form:last').submit();
}

var curEventObj = "";

//当前页面初始化查询值
var queryConditions = {};

$(function(){
	$("#buttonListDiv").bind("contextmenu", function(e) {
		return false;
	});
	
	$(document).bind("keydown", function(e) {
		curEventObj = e;
	});
	
	// 包含下载功能时收集初始化查询值
	if(isContainDownFun()) {
		queryConditions = getQueryConditions();
	}
})

// 获取查询条件
function getQueryConditions() {
	var qcTemp = {};
	$("#queryTable input[type=text], #queryTable select").each(function(){
		qcTemp[$(this).prop("id")] = $(this).val();
	});
	return qcTemp;
}

var downTimeout;

function document_readyState(){
	if("interactive" == document.readyState) {
		$("#loading").hide();
		clearTimeout(downTimeout);
		return;
	}
	downTimeout = setTimeout("document_readyState()",1);
}

var isDownSuccess = "";

function downProgressHandler() {
	if(null != $.cookie('isDownSuccess') && isDownSuccess != $.cookie('isDownSuccess')) {
		jQuery.unblockUI();
		clearTimeout(downTimeout);
		return;
	}
	downTimeout = setTimeout("downProgressHandler()",100);
}

function showDownExcelProgress() {
	$.blockUI({message:$("#progressDiv"), fadeIn:0}); 
	if(null != $.cookie('isDownSuccess')) {
		isDownSuccess = $.cookie('isDownSuccess');
	}
	downProgressHandler();
}

// 监控查询条件是否变化
function queryConditionChgListener() {
	var flag = false;
	// 是否包含下载功能
	$.each(getQueryConditions(), function(k, v) {
		if(v != queryConditions[k]) {
			flag = true;
			return;
		}
	});
	return flag;
}



// 是否包含下载功能
function isContainDownFun() {
	var flag = false;
	$("#buttonListDiv img").each(function(){
		if($(this).prop("src").indexOf("download.") > -1){
			flag = true;
			return;
		}
	});
	return flag;
}

// 设定光标停留在文本域的最后面
$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
}