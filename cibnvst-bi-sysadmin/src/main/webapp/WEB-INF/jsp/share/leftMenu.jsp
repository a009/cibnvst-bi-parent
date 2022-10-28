<%@ page language="java" pageEncoding="UTF-8"%>
<!-- Left side column. contains the logo and sidebar -->


<div class="s-bar">
	<div class="s-wrap">
		<c:if test="${null != sessionScope.session_key_user}">
			<c:set var="curmid" value="${moduleId }" />
			<c:choose>
				<c:when test="${curmid == null}">
					<c:set var="num" value="-1" />
				</c:when>
				<c:otherwise>
					<c:forEach items="${sessionScope.session_key_user.modules}" var="parent" varStatus="st">
						<c:forEach items="${parent.value}" var="child" varStatus="sta">
							<c:if test="${curmid == child.vst_module_id}">
								<c:set var="num" value="${st.index }" />
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</c:if>
		<ul class="s-menu">
		    <li id="divclick_home" class="treeview">
		    	<div class="s-list">
		    		<a href="${ctx}/sysMain/mainPage.action">
		        		<input type="hidden" id="divclick_home_value" value="${home_value}"/>
		        		<div class="s-icon">
			   				<i class="fa fa-home"></i> 
			   			</div>
		            	<span>首页</span>
		        	</a>
		    	</div>
		    </li>
			<c:if test="${null != sessionScope.session_key_user}">
			   	<c:forEach items="${sessionScope.session_key_user.modules}" var="parent" varStatus="st">
			   		<li id="divclick_${parent.key.vst_module_id }"
			   			<c:choose>
			   				<c:when test="${num == st.index }">
<%--			   					onclick="changeClass('showdiv_${parent.key.vst_module_id }','divclick_${parent.key.vst_module_id }','top');" --%>
			   					class="active treeview"
			   				</c:when>
			   				<c:otherwise>
<%--			   					onclick="changeClass('showdiv_${parent.key.vst_module_id }','divclick_${parent.key.vst_module_id }','');" --%>
			   					class="treeview"
			   				</c:otherwise>
			   			</c:choose>
			   		>
			   			<div class="s-list">
			   				<div class="s-icon">
			   					<!-- <i class="fa fa-link"></i> -->
			   					<i class="fa ${parent.key.vst_module_icon }"></i>
			   				</div> 
			   				<span>${parent.key.vst_module_name }</span>
		                    <span class="pull-right-container">
		                      <i class="fa fa-angle-right pull-right"></i>
		                    </span>
		                </div>
					</li>
			   	</c:forEach>
			</c:if> 
		</ul>
	</div>
	<div class="s-sub" style="display:none;">
		<c:if test="${null != sessionScope.session_key_user}">
		   	<c:forEach items="${sessionScope.session_key_user.modules}" var="parent" varStatus="st">
				<ul class="treeview-menu divclick_${parent.key.vst_module_id }">
					<c:forEach items="${parent.value}" var="child" varStatus="sta">
						<li id="divclick_child_${child.vst_module_id }">
							<a href="${ctx}${child.vst_module_url }.action?moduleId=${child.vst_module_id }&flag=1">
								<!-- <i class="fa fa-circle-o"></i> -->
								<i class="fa ${child.vst_module_icon }"></i>
								${child.vst_module_name }
							</a>
						</li>
					</c:forEach>
				</ul>
		   	</c:forEach>
		</c:if>
	</div>
</div>
<script  type="text/javascript">
	$(function() {
		var divclick_home_value = $("#divclick_home_value").val();
		if(divclick_home_value == 'login'){//登录进来
			$("#divclick_home").removeClass("treeview").addClass("active treeview");//选中样式
		}
		
		var moduleId = $("#moduleId").val();
		if(moduleId != ''){//点击模块后
			var abc=$("#divclick_child_"+moduleId).removeClass("").addClass("active");//选中样式
		}
	});
	/*function changeClass(a,b,c){//点击父模块
		var mid = a.split("_")[1];
		$("li[id^=divclick][id!=divclick_" + mid + "]").removeClass().addClass("treeview");
		$("#divclick_home").removeClass("active treeview").addClass("treeview");
	}*/
</script>


<script>
	//侧边栏效果
	$(function (){
		$(".s-bar").hover(function(){
			$(".s-wrap").css({
				"width":"140px",
			});
		},function(){
			$(".s-sub ul").hide();
			$(".s-sub").hide();
			$(".s-wrap").css({
				"width":"30px",
			});
		});
		$(".s-menu>li").hover(function(){
			if($(this).attr("id")==="divclick_home"){
				$(".s-sub").hide();
				$(".s-sub ul").hide();
			}else{
				$(".s-sub").show();
				$(".s-sub ul").hide();
				$("."+$(this).attr("id")).show();
			}
			
		},function(){
			
		});
	});
</script>