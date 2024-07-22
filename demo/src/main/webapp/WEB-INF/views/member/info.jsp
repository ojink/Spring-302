<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="./">홈으로</a>
<hr>
<h3>회원정보 [${method}]</h3>
<table border="1">
<tr><th>성명</th>
	<td>${name }</td>
</tr>
<tr><th>성별</th>
	<td>${gender }</td>
</tr>
<tr><th>나이</th>
	<td>${age }</td>
</tr>
</table>
<hr>
<table border="1">
<tr><th>성명</th>
	<td>${vo.name }</td>
</tr>
<tr><th>성별</th>
	<td>${vo.gender }</td>
</tr>
<tr><th>나이</th>
	<td>${vo.age }</td>
</tr>
</table>


<a href="member">회원가입</a>
</body>
</html>









