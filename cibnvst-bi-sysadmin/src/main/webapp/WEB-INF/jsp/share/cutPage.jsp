 <%@ page language="java" pageEncoding="UTF-8"%>
	<input type="hidden" name="orderBy" id="orderBy" value="${cutPage._queryParam.orderBy }"/>
	<input type="hidden" name="order" id="order" value="${cutPage._queryParam.order }"/>
	<input type="hidden" name="_currPage" id="currPage" value="${cutPage._currPage }"/>
	<input type="hidden" name="_totalPages" id="totalPages" value="${cutPage._totalPages }"/>
<script>
	function jumpAlert() {
		if(!(new RegExp("^\\d{1,4}$")).test($('#page').val())) { 
			alert('请输入数字页码!');
		} else {
		    $('#currPage').val($('#page').val());
		   // $('form:last').submit();
		   $('#listForm').submit();
		}
	}

	function jumpLastPage(){
		var currPage = parseInt($("#currPage").val());
		var totalPage = parseInt($("#totalPages").val());
		if(currPage != totalPage){
			$("#currPage").val(totalPage);
			$('#listForm').submit();
		}
	}

	function jumpFirstPage(){
		var currPage = parseInt($("#currPage").val());
		if(currPage != 1){
			$("#currPage").val(1);
			$('#listForm').submit();
		}
	}
	
	function jumpPage(no) {
		var currPage = parseInt($("#currPage").val()) + no;
		var totalPage = parseInt($("#totalPages").val());
		if(currPage >= 1 && currPage <= totalPage){
			$("#currPage").val(currPage);
			$('#listForm').submit();
		}
	}
	
	function jumpGivePage() {
		$('#currPage').val(1);
		$('#listForm').submit();
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
	
  	$(function(){
  		var hidden_singleCount = $("#hidden_singleCount").val();
  		if(hidden_singleCount != null && hidden_singleCount != ""){
  			$("#singleCount").val(hidden_singleCount);
  		}
  	});
</script>

<div class="pagelist">

    <div class="pageleft">
                        共有<span style="color:#fb6a00">${cutPage._totalResults }</span>条记录
		每页显示
			<span class="red">
				<input type="hidden" id="hidden_singleCount" value="${cutPage._singleCount }" />
				<select style="color:#fb6a00" name="_singleCount" id="singleCount" 
					onChange="jumpGivePage();">
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
					<option value="25">25</option>
					<option value="30">30</option>
					<option value="50">50</option>
					<option value="100">100</option>
				</select>
			</span>
		条, 
		当前第<span style="color:#fb6a00">
				${cutPage._currPage }
			</span> / ${cutPage._totalPages }
		页
    </div>
    
    <div class="pageright">
    	<a href="javascript:jumpFirstPage();">首页</a>
		<a href="javascript:jumpPage(-1);">上一页</a>
		<a href="javascript:jumpPage(1);">下一页</a>
		<a href="javascript:jumpLastPage();">尾页</a>
      	<span> 
			转到第
			<input name="page" id="page" type="text" size="4" maxlength="4" onpaste="return false"
					style="width: 30px; border: 1px solid #999999;margin:0px;" autocomplete = "off"/>
			页			
		</span>
		<a href="javascript:jumpAlert();" style="text-decoration: none;">确定</a>
    </div>
    
</div>