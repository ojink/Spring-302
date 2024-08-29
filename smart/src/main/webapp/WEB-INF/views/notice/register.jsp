<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">공지글 등록</h3>
<form>
<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th>제목</th>
	<td><input type="text" name="title" class="form-control"> </td>
</tr>
<tr><th>내용</th>
	<td><textarea name="content" class="form-control"></textarea></td>
</tr>
<tr><th>첨부파일</th>
	<td></td>
</tr>
</table>
</form>

</body>
</html>