<!--<s:if test="1 == returnCode">-->
<!--<div id="prompt">-->
<!--  <img alt="" src="${ctx}/pub/images/onCorrect.gif" align="top" style="margin-right:5px;vertical-align: middle">${returnInfo}-->
<!--</div>-->
<!--</s:if>-->
<!--<s:elseif test="2 == returnCode">-->
<!--<div id="prompt">-->
<!--  <img alt="" src="${ctx}/pub/images/onError.gif" align="top" style="margin-right:5px;vertical-align: middle">${returnInfo}-->
<!--</div>-->
<!--</s:elseif>-->
<!--<s:elseif test="3 == returnCode">-->
<!--<div id="prompt">-->
<!--  <img alt="" src="${ctx}/pub/images/onFocus.gif" align="top" style="margin-right:5px;vertical-align: middle">${returnInfo}-->
<!--</div>-->
<!--</s:elseif>-->
<c:choose>
	<c:when test="${1 == returnCode}">
		<div id="prompt" class="prompt_ok">
		  <img alt="" src="${ctx}/pub/images/t1_msg_ok.png" align="top" style="margin-right:5px;vertical-align: middle">${returnInfo}
		</div>
	</c:when>
	<c:when test="${2 == returnCode}">
		<div id="prompt"  class="prompt_error">
		  <img alt="" src="${ctx}/pub/images/t1_msg_error.png" align="top" style="margin-right:5px;vertical-align: middle">${returnInfo}
		</div>
	</c:when>
	<c:when test="${3 == returnCode}">
		<div id="prompt"  class="prompt_focus">
		  <img alt="" src="${ctx}/pub/images/t1_msg_focus.png" align="top" style="margin-right:5px;vertical-align: middle">${returnInfo}
		</div>
	</c:when>
</c:choose>
