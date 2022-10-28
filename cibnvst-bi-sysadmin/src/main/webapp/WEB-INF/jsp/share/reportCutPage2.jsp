<%@ page language="java" pageEncoding="UTF-8"%>

<input type="hidden" name="cutPage._queryParam['orderBy']" id="orderBy2" />
<input type="hidden" name="cutPage._queryParam['order']" id="order2" />
<input type="hidden" name="cutPage._currPage" id="currPage2" value="1" />
<input type="hidden" name="cutPage._totalPages" id="totalPages2" />
<script>
	function jumpAlert2() {
		if(!(new RegExp("^\\d{1,4}$")).test($('#page2').val())) { 
			alert('请输入正整数页码!');
		} else {
		    $('#currPage2').val($('#page2').val());
		    ajaxGetCutPage2();
		    //$('form:last').submit();
		}
	}

	function jumpLastPage2(){
		var currPage = parseInt($("#currPage2").val());
		var totalPage = parseInt($("#totalPages2").val());
		if(currPage != totalPage){
			$("#currPage2").val(totalPage);
			ajaxGetCutPage2();
		}
		
	}

	function jumpFirstPage2(){
		var currPage = parseInt($("#currPage2").val());
		if(currPage != 1){
			$("#currPage2").val(1);
			ajaxGetCutPage2();
		}
	}
	
	function jumpPage2(no) {
		var currPage = parseInt($("#currPage2").val()) + no;
		var totalPage = parseInt($("#totalPages2").val());
		if(currPage >= 1 && currPage <= totalPage){
			$("#currPage2").val(currPage);
			ajaxGetCutPage2();
		//$('form:last').submit();
		}
	}
	
	function jumpGivePage2() {
		ajaxGetCutPage2();
	}

	function showPageInfo2(totalSize, singleSize){
		$("#showTotalSize2").text(totalSize);
		$("#showCurrPage2").text($("#currPage2").val());
		$("#showTotalPage2").text(Math.ceil(totalSize / singleSize));
		$("#totalPages2").val(Math.ceil(totalSize / singleSize));
	}

	function changeOrder2(obj){
		var orderBy = $(obj).attr("orderBy");
		$("#orderBy2").val(orderBy);
		var order = $("#order2").val();
		if(order == "desc"){
			$(obj).attr("order","asc");
			$("#order2").val("asc");
		}else{
			$(obj).attr("order","desc");
			$("#order2").val("desc");
		}
		ajaxGetCutPage2();
	}

	function showOrder2(orderBy, order){
		if(order == "desc"){
			$("#"+orderBy+"2").append("&nbsp;&nbsp;<img src='${pageContext.request.contextPath}/pub/images/desc.gif' width='9' height='5'>");
		}else{
			$("#"+orderBy+"2").append("&nbsp;&nbsp;<img src='${pageContext.request.contextPath}/pub/images/asc.gif' width='9' height='5'>");
		}
	}
	
	$(document).ready(function(){
		$("#page2").unbind("keypress").keypress(function(event){
			 if (13 == event.which) {
			 	jumpAlert2();
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
		共<span style="color: #fb6a00""><span id="showTotalSize2"></span></span>条
		&nbsp;每页
		<span class="red">
			<select name="cutPage._singleCount"
					id="singleCount2" onChange="jumpGivePage2();"
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
		<span style="color: #fb6a00"><span id="showCurrPage2"></span>/<span id='showTotalPage2'></span></span>页
	</div>

	<div class="pageright">
		<a href="javascript:jumpFirstPage2();">首页</a>
		<a href="javascript:jumpPage2(-1);">上一页</a>
		<a href="javascript:jumpPage2(1);">下一页</a>
		<a href="javascript:jumpLastPage2();">尾页</a>
		<span>跳<input name="page" id="page2" type="text" size="4"
				maxlength="4" onpaste="return false"
				style="width: 30px; border: 1px solid #999999; margin: 0px;"
				autocomplete="off" />页
		</span>
		<a href="javascript:jumpAlert2();">确定</a>
	</div>
</div>
