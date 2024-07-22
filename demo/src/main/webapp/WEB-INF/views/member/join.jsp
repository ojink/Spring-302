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
<h3>회원가입화면</h3>
<form method="post" action="joinRequest">
<table border="1">
<tr><th>성명</th>
	<td><input type="text" name="name"></td>
</tr>
<tr><th>성별</th>
	<td>
		<label><input type="radio" name="gender" value="남" checked>남</label>
		<label><input type="radio" name="gender" value="여">여</label>
	</td>
</tr>
<tr><th>나이</th>
	<td><input type="text" name="age"></td>
</tr>
</table>
<button>회원가입(HttpServletRequest)</button>
<button onclick="action='joinParam'">회원가입(@RequestParam)</button>
<button onclick="action='joinModel'">회원가입(@ModelAttribute)</button>
</form>

</body>
</html>






