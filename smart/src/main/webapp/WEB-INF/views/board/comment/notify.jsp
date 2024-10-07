<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h6 class="px-3 py-2">방명록 댓글 알림</h6>
<div class="bg-light navbar-nav-scroll overflow-auto h-max520">

	<c:forEach items="${list}" var="vo">
	<div class="dropdown-item">
		<div class="d-flex justify-content-between">
			<span>${vo.name }</span>
			<span>${vo.writedate}</span>
		</div>
		<div class="notify-comment fw-bold">${vo.content}</div>
		<div class="dropdown-divider"></div>
	</div>
	</c:forEach>
	
</div>