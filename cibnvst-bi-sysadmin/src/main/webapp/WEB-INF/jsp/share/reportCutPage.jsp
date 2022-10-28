<%@ page language="java" pageEncoding="UTF-8"%>

<input type="hidden" name="cutPage._queryParam['orderBy']" id="orderBy" />
<input type="hidden" name="cutPage._queryParam['order']" id="order" />
<input type="hidden" name="cutPage._currPage" id="currPage" value="1" />
<input type="hidden" name="cutPage._totalPages" id="totalPages" />
<script>
	function jumpAlert() {
		if(!(new RegExp("^\\d{1,4}$")).test($('#page').val())) { 
			alert('请输入正整数页码!');
		} else {
		    $('#currPage').val($('#page').val());
		    ajaxGetCutPage();
		    //$('form:last').submit();
		}
	}

	function jumpLastPage(){
		var currPage = parseInt($("#currPage").val());
		var totalPage = parseInt($("#totalPages").val());
		if(currPage != totalPage){
			$("#currPage").val(totalPage);
			ajaxGetCutPage();
		}
		
	}

	function jumpFirstPage(){
		var currPage = parseInt($("#currPage").val());
		if(currPage != 1){
			$("#currPage").val(1);
			ajaxGetCutPage();
		}
	}
	
	function jumpPage(no) {
		var currPage = parseInt($("#currPage").val()) + no;
		var totalPage = parseInt($("#totalPages").val());
		if(currPage >= 1 && currPage <= totalPage){
			$("#currPage").val(currPage);
			ajaxGetCutPage();
		//$('form:last').submit();
		}
	}
	
	function jumpGivePage() {
		$("#currPage").val(1);
		ajaxGetCutPage();
	}

	function showPageInfo(totalSize, singleSize){
		$("#showTotalSize").text(totalSize);
		$("#showCurrPage").text($("#currPage").val());
		$("#showTotalPage").text(Math.ceil(totalSize / singleSize));
		$("#totalPages").val(Math.ceil(totalSize / singleSize));
	}

	function changeOrder(obj){
		var orderBy = $(obj).attr("orderBy");
		$("#orderBy").val(orderBy);
		var order = $("#order").val();
		if(order == "desc"){
			$(obj).attr("order","asc");
			$("#order").val("asc");
		}else{
			$(obj).attr("order","desc");
			$("#order").val("desc");
		}
		ajaxGetCutPage();
	}

	function showOrder(orderBy, order){
		if(order == "desc"){
			$("#"+orderBy+"2").append("&nbsp;&nbsp;<img src='${pageContext.request.contextPath}/pub/images/desc.gif' width='9' height='5'>");
		}else{
			$("#"+orderBy+"2").append("&nbsp;&nbsp;<img src='${pageContext.request.contextPath}/pub/images/asc.gif' width='9' height='5'>");
		}
	}
	
	$(document).ready(function(){
		$("#page").unbind("keypress").keypress(function(event){
			 if (13 == event.which) {
			 	jumpAlert();
				event.preventDefault();
				return false;			 	
			 } else if ((event.charCode && (event.charCode !=8 || event.charCode !=8))||
		     				(event.keyCode && (event.keyCode !=8 || event.keyCode !=8))){
				if ((event.charCode && (event.charCode < 48 || event.charCode > 57)) || 
					 (event.keyCode && (event.keyCode < 48 || event.keyCode > 57))) {
					event.preventDefault();
					return false;
				} 
			 }
		});
	});
</script>

<style>
@media screen and (max-width: 1024px) { 
.pageleft{text-align:center;margin:10px 0;}
.pageright{text-align:center;}
} 
@media screen and (min-width: 1024px) { 
.pageleft{float:left;}
.pageright{float:right;}
} 

</style>

<div class="page2">
	<div class="pageleft">
		共<span style="color: #fb6a00""><span id="showTotalSize"></span></span>条
		&nbsp;每页
		<span class="red">
			<select name="cutPage._singleCount"
					id="singleCount" onChange="jumpGivePage();"
					style="width:50px !important; color:#fb6a00;">
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
				<option value="25">25</option>
				<option value="30">30</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
		</span>条
		&nbsp;第
		<span style="color: #fb6a00"><span id="showCurrPage"></span>/<span id='showTotalPage'></span></span>页
	</div>

	<div class="pageright">
		<a href="javascript:jumpFirstPage();">首页</a>
		<a href="javascript:jumpPage(-1);">上一页</a>
		<a href="javascript:jumpPage(1);">下一页</a>
		<a href="javascript:jumpLastPage();">尾页</a>
		<span>跳<input name="page" id="page" type="text" size="4"
				maxlength="4" onpaste="return false"
				style="width: 30px; border: 1px solid #999999; margin: 0px;"
				autocomplete="off" />页
		</span>
		<a href="javascript:jumpAlert();">确定</a>
	</div>
</div>
