<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">고객정보수정</h3>

<form method="post" action="update">
<input type="hidden" name="id" value="${vo.id }">
<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th>고객명</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="name" value="${vo.name }" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th>성별</th>
	<td><div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" ${vo.gender eq "남" ? "checked" : ""} type="radio" name="gender" value="남">남
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label" >
		  	<input class="form-check-input" <c:if test="${vo.gender == '여'}">checked</c:if>  type="radio" name="gender" value="여">여
		  </label>
		</div>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="tel" name="phone" value="${vo.phone }" 
				maxlength="13" 
				 placeholder="010-1234-5678" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
				class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th>이메일</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="email" value="${vo.email }" class="form-control">
			</div>
		</div>
	</td>
</tr>
</table>

<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary px-4">저장</button>
	<button type="button" class="btn btn-outline-primary px-4" onclick="location='info?id=${vo.id}' ">취소</button>
</div>

</form>


</body>
</html>