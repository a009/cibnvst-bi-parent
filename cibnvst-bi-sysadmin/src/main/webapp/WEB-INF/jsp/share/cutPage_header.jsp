<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	function jumpAlert2() {
		if(!(new RegExp("^\\d{1,4}$")).test($('#page').val())) { 
			alert('请输入数字页码!');
		} else {
		    $('#currPage').val($('#page').val())  ;
		    //$('form:last').submit();
		    $('#listForm').submit();
		}
	}
	
	function jumpPage2(pageNo, no, totalPages) {
		if(no > 0){	//只有当前页小于总页数，才能点下一页
			if(pageNo < totalPages){
				$("#currPage").val(parseInt(pageNo)+no);
				//$('form:last').submit();
				$('#listForm').submit();
			}
		}else if(no < 0){ //只有当前页大于1，才能点上一页
			if(pageNo > 1){
				$("#currPage").val(parseInt(pageNo)+no);
				//$('form:last').submit();
				$('#listForm').submit();
			}
		}
	}
	
	function jumpGivePage2() {
		//$('form:last').submit();
		$('#listForm').submit();
	}
	
	$(document).ready(function(){
		$("#page").unbind("keypress").keypress(function(event){
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
<style type="text/css">
	#pageright_header{
		float: right;
	}
	
	#pageright_header a{
		border: 1px solid #dfdfdf;
		padding: 5px 12px;
		border-radius: 3px;
	}
</style>

<div id="pageright_header">
	<lable style="color:#fb6a00;">${cutPage._currPage }</lable>
	/ ${cutPage._totalPages }
	<a href="javascript:jumpPage2(${cutPage._currPage },-1,${cutPage._totalPages });">&lt;</a>
	<a href="javascript:jumpPage2(${cutPage._currPage },1,${cutPage._totalPages });">&gt;</a>
</div>
