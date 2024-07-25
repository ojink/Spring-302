<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">사원정보수정</h3>

<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th>사번</th>
	<td>${vo.employee_id }</td>
</tr>
<tr><th>사원명</th>
	<td>${vo.name }</td>
</tr>
<tr><th>이메일</th>
	<td>${vo.email }</td>
</tr>
<tr><th>전화번호</th>
	<td>${vo.phone_number }</td>
</tr>
<tr><th>입사일자</th>
	<td>${vo.hire_date }</td>
</tr>
<tr><th>급여</th>
	<td>${vo.salary }</td>
</tr>
<tr><th>부서</th>
	<td>${vo.department_name }</td>
</tr>
<tr><th>업무</th>
	<td>${vo.job_title }</td>
</tr>
<tr><th>매니저</th>
	<td>${vo.manager_name }</td>
</tr>
</table>

<div class="btn-toolbar gap-2 justify-content-center">
	<button class="btn btn-primary px-4">저장</button>
	<button class="btn btn-outline-primary px-4">취소</button>
</div>

</body>
</html>











