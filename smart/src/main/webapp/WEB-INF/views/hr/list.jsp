<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">사원목록</h3>

<div class="row mb-3">
	<div class="col-auto d-flex align-items-center">
		<label class="me-3">부서명</label>
		<form method="post" action="list">
		<select class="form-select w-px300" name="department_id" onchange="submit()">
			<option value="-1">전체</option>
			<c:forEach items="${departments}" var="d">
			<option <c:if test="${department_id eq d.department_id}">selected</c:if>  value="${d.department_id }">${d.department_name }</option>
			</c:forEach>
		</select>
		</form>
	</div>
</div>

<table class="table tb-list">
<colgroup>
	<col width="100px">
	<col width="250px">
	<col width="150px">
	<col width="200px">
	<col width="300px">
</colgroup>
<tr><th>사번</th>
	<th>성명</th>
	<th>입사일자</th>
	<th>부서</th>
	<th>업무</th>
</tr>
<c:if test="${empty list}">
<tr><td class="text-center" colspan="5">사원정보가 없습니다</td></tr>
</c:if>

<c:forEach items="${list}" var="vo">
<tr><td>${vo.employee_id }</td>
<%-- 	<td>${vo.last_name } ${vo.first_name }</td> --%>
	<td><a class="text-link" href="info?id=${vo.employee_id }">${vo.name }</a></td>
	<td>${vo.hire_date }</td>
	<td>${vo.department_name }</td>
	<td>${vo.job_title }</td>
</tr>
</c:forEach>

</table>

</body>
</html>