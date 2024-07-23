<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">고객등록</h3>

<form method="post" action="register">
<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th>고객명</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="name" class="form-control" autofocus>
			</div>
		</div>
	</td>
</tr>
<tr><th>성별</th>
	<td><div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" type="radio" name="gender" value="남">남
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label" >
		  	<input class="form-check-input" type="radio" name="gender" value="여" checked>여
		  </label>
		</div>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="phone" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th>이메일</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="email" class="form-control">
			</div>
		</div>
	</td>
</tr>
</table>

<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary px-4">저장</button>
	<button type="button" class="btn btn-outline-primary px-4" onclick="location='list' ">취소</button>
</div>

</form>


</body>
</html>