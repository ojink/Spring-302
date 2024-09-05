<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">공지글 목록</h3>


<div class="d-flex mb-2 justify-content-between">
	<form method="post" action="list">
		<div class="input-group">
			<select name="search" class="form-select w-px100">
				<option value="" >전체</option>
				<option value="title" ${page.search == "title" ? "selected" : ""}>제목</option>
				<option value="content" <c:if test="${page.search eq 'content'}">selected</c:if> >내용</option>
			</select>
			<input type="text" name="keyword" value="${page.keyword }" class="form-control w-px300">
			<button class="btn btn-primary"><i class="fa-solid fa-magnifying-glass"></i></button>	
		</div>
	</form>
	<!-- 관리자로 로그인되어 있는 경우만 글쓰기 가능 -->
	<c:if test="${loginInfo.role == 'ADMIN'}">
	<button class="btn btn-primary" onclick="location='register'">글쓰기</button>
	</c:if>
</div>


<table class="table tb-list">
<colgroup>
	<col width="100px">
	<col width="">
	<col width="130px">
	<col width="130px">
	<col width="90px">
	<col width="70px">
</colgroup>
<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일자</th><th>조회수</th><th>첨부</th></tr>

<c:if test="${empty page.list}">
<tr><td colspan="6" class="text-center">공지글이 없습니다</td></tr>
</c:if>

<c:forEach items="${page.list}" var="vo">
<tr>
	<td>${vo.no}</td>
	<td><span style="margin-left:${15*vo.indent}px"></span>
		<c:if test="${vo.indent>0}"><i class="fa-regular fa-comment-dots"></i></c:if>
		<a class="text-link" href="${vo.indent>0 ? 'reply/info' : 'info'}?id=${vo.id}&pageNo=${page.pageNo}&search=${page.search}&keyword=${page.keyword}">${vo.title }</a></td>
	<td>${vo.name }</td>
	<td>${vo.writedate }</td>
	<td>${vo.readcnt }</td>
	<td><c:if test="${ ! empty vo.filename }">
		<i class="fa-solid fa-paperclip"></i></c:if></td>
</tr>
</c:forEach>

</table>

<jsp:include page="/WEB-INF/views/include/page.jsp"/>

</body>
</html>