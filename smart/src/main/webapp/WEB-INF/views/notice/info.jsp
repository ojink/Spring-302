<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">공지글 정보</h3>

<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
	<col width="200px">
	<col width="140px">
	<col width="200px">
	<col width="100px">
</colgroup>
<tr><th>제목</th>
	<td colspan="5">${vo.title }</td>
</tr>
<tr><th>작성자</th>
	<td>${vo.name }</td>
	<th>작성일자</th>
	<td>${vo.writedate }</td>
	<th>조회수</th>
	<td>${vo.readcnt }</td>
</tr>
<tr><th>내용</th>
	<td colspan="5">${fn: replace(vo.content, crlf, "<br>") }</td>
</tr>
<tr><th>첨부파일</th>
	<td colspan="5">
		<div class="d-flex">
			<label role="button" class="d-flex col-auto text-link gap-3">
				<span>${vo.filename }</span>
				<i class="fs-3 fa-regular fa-circle-down"></i>
			</label>
		</div>
	</td>
</tr>
</table>

<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary" id="btn-list">목록으로</button>
	
	<!-- 로그인한 사용자가 쓴 글에 대해서만 수정/삭제 가능 -->
	<c:if test="${loginInfo.userid == vo.writer}">
	<button class="btn btn-primary" id="btn-list">정보수정</button>
	<button class="btn btn-primary" id="btn-list">정보삭제</button>
	</c:if>
	
</div>

<script>
$("#btn-list").on("click", function(){
	location = "list"
			+ "?pageNo=${page.pageNo}&search=${page.search}&keyword=${page.keyword}"
})
</script>

</body>
</html>