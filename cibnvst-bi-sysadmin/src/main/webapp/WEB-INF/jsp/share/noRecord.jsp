<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!-- 
<input type="hidden" id="down_noQuery" value="<s:property value='pages.query' />" />
<input type="hidden" id="down_noRecord" value="<s:property value='pages.resultCount==0' />" />
<input type="hidden" id="down_OverMax" value="<s:property value='%{pages.resultCount > maxDownRecords}' />" />
 -->
<div style="display: none; text-align: center;">
	<div id="progressDiv">
		<p>
			<img src="${ctx}/pub/images/down_progressbar.gif" />
		</p>
		<p>
			数据正在下载中，请等待...
		</p>
	</div>
</div>

<c:choose>
	<c:when test="${(cutPage == null || cutPage._totalResults==0) && cutPage._isQuery}">
		<div>
			<div style="line-height:40px;text-align:center;color:#f39c12;font-size: 16px;font-weight: 700;">
				<i class="icon fa fa-warning"></i>
				没有找到相关记录，请重新输入条件进行查询
			</div>
		</div>
	</c:when>
	<c:when test="${!cutPage._isQuery}">
		<div>
			<div style="line-height:40px;text-align:center;color:#00a65a;font-size: 16px;font-weight: 700;">
				<i class="icon fa fa-warning"></i>
				请输入查询条件，点击查询记录
			</div>
		</div>
	</c:when>
</c:choose>

<!--<s:if test="(cutPage == null || cutPage._totalResults==0) && cutPage._isQuery">-->
<!--	<table>-->
<!--		<tr>-->
<!--			<td align="center" height="80px;">-->
<!--				没有找到相关记录，请重新输入条件进行查询-->
<!--			</td>-->
<!--		</tr>-->
<!--	</table>-->
<!--</s:if>-->
<!--<s:elseif test="!cutPage._isQuery">-->
<!--	<table>-->
<!--		<tr>-->
<!--			<td align="center" height="80px;">-->
<!--				请输入查询条件，点击查询记录-->
<!--			</td>-->
<!--		</tr>-->
<!--	</table>-->
<!--</s:elseif>-->