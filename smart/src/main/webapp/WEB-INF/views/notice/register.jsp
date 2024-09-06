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
<form method="post" action="register" enctype="multipart/form-data">
<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th>제목</th>
	<td><input type="text" name="title" title="제목" class="check-empty form-control"> </td>
</tr>
<tr><th>내용</th>
	<td><textarea name="content" title="내용" class="check-empty form-control"></textarea></td>
</tr>
<tr><th>첨부파일</th>
	<td><div class="row">
			<div class="d-flex align-items-center gap-3 file-info">
				<label>
					<a class="btn btn-outline-primary">파일선택</a>
					<input type="file" name="file" 
						class="d-none form-control file-single">
				</label>
				<span class="file-name"></span>
				<i role="button" 
					class="fs-3 fa-solid fa-xmark text-danger file-remove d-none"></i>
			</div>
		</div>
	</td>
</tr>
</table>
</form>

<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary px-4" id="btn-save">저장</button>
	<button class="btn btn-outline-primary px-4" id="btn-cancel">취소</button>
</div>

<script>

$("#btn-save").on("click", function(){
	if( isNotEmpty() ) 
		$("form").submit()	
})

$("#btn-cancel").on("click", function(){
	location = "list"
})

</script>

</body>
</html>