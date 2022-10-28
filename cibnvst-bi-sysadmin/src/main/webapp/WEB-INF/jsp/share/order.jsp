<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="javascript:sort('${param.orderBy}','desc');">${param.fieldName}</a>
<c:choose>
<c:when test="${cutPage._queryParam['orderBy'] == param.orderBy}">
<c:choose>
<c:when test="${cutPage._queryParam['order'] =='desc'}">
<img src="${pageContext.request.contextPath}/pub/images/desc.gif" width="9" height="5">
</c:when>
<c:otherwise>
<img src="${pageContext.request.contextPath}/pub/images/asc.gif" width="9" height="5">
</c:otherwise>
</c:choose>
</c:when>
</c:choose>