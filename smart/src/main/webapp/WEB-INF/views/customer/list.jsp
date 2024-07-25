<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">고객목록</h3>

<div class="row mb-3 justify-content-between">
	<div class="col-auto">
		<form method="post" action="list">
		<div class="input-group">
			<label class="col-form-label me-3">고객명</label>
			<input type="text" value="${name}" name="name" class="form-control">
			<button class="btn btn-primary">검색</button>
		</div>
		</form>
	</div>

	<div class="col-auto">
		<button class="btn btn-primary" onclick="location='register'">고객등록</button>
	</div>
</div>

<table class="table tb-list">
<colgroup>
	<col width="200px">
	<col width="300px">
	<col width="400px">
</colgroup>
<tr><th>고객명</th><th>전화번호</th><th>이메일</th></tr>
<c:if test="${empty list}">
<tr><td colspan="3" class="text-center">고객정보가 없습니다</td></tr>
</c:if>

<c:forEach items="${list}" var="vo">
<tr><td><a class="text-link" href="info?id=${vo.id}">${vo.name }</a></td>
	<td>${vo.phone }</td>
	<td>${vo.email }</td>
</tr>
</c:forEach>

</table>

</body>
</html>