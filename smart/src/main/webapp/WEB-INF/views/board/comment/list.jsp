<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!-- 댓글이 없는 경우 -->
<c:if test="${empty list }">
<div class="text-center">
	<div>댓글이 없습니다</div>
	<div class="fs-5">첫번째 댓글을 남겨주세요</div>
</div>
</c:if>

<!-- 댓글이 있는 경우 -->
<c:forEach items="${list }" var="vo">

<div class="comment py-3 border-bottom">
	<div class="d-flex gap-2 align-items-center mb-2">
		<label class="profile px40">
			<c:choose>
				<c:when test="${empty vo.profile}">
					<i class="font-profile fa-solid fa-circle-user"></i>
				</c:when>
				<c:otherwise><img src="${vo.profile}"></c:otherwise>
			</c:choose>
		</label>
		<span>${vo.name } [ ${vo.writedate} ]</span>
	</div>
	<div class="content">${fn: replace(  fn:replace(vo.content, lf, "<br>"), crlf, "<br>") }</div>
</div>
</c:forEach>


<!-- 
<div class="comment py-3 border-bottom">
	<div class="d-flex gap-2 align-items-center mb-2">
		<label class="profile px40">
			<i class="font-profile fa-solid fa-circle-user"></i>
		</label>
		<span>홍길동 [ 2024-09-23 17:11:50 ]</span>
	</div>
	<div class="content">2022년 10월 29일 밤 10시 15분, 서울 이태원에서 압사 참사가 발생했다</div>
</div>

<div class="comment py-3 border-bottom">
	<div class="d-flex gap-2 align-items-center mb-2">
		<label class="profile px40">
			<i class="font-profile fa-solid fa-circle-user"></i>
		</label>
		<span>홍길동 [ 2024-09-23 17:11:50 ]</span>
	</div>
	<div class="content">2022년 10월 29일 밤 10시 15분, 서울 이태원에서 압사 참사가 발생했다</div>
</div>
-->