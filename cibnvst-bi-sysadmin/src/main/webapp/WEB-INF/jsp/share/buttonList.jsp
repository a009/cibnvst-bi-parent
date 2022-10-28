<%@ page language="java" pageEncoding="UTF-8"%>
<ul class="contlist" style="cursor: pointer;">
	<c:forEach items="${buttonList}" var="button">
		<li class="action">
			<img src="${ctx}${button.vst_button_img}" width="14" height="14" alt="${button.vst_button_summary }">
			<a onclick="${button.vst_button_onclick}">${button.vst_button_name }</a>
		</li>
	</c:forEach>
</ul>
