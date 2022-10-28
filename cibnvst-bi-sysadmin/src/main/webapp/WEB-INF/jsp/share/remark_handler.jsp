<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div style="widht:100%;" title="<c:out value='${param.remark}'/>">
<c:choose>
<c:when test="${!empty param.remark}">
<c:choose>
<c:when test="${fn:length(param.remark) > 30}">
${fn:substring(param.remark, 0, 30)}...
</c:when>
<c:otherwise>
${param.remark}
</c:otherwise>
</c:choose>
</c:when>
</c:choose>
</div>