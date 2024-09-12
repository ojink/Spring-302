<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">방명록 등록</h3>
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
	<td>
		<label>
			<a class="btn btn-outline-primary">파일선택</a>
			<input type="file" name="files" multiple 
					class="d-none form-control file-multiple">
		</label>
		<!-- 드래그드랍으로 파일첨부할 영역 -->
		<div class="form-control file-drag mt-2 py-3">
			<div class="py-1 text-center">첨부할 파일을 마우스로 끌어 오세요</div>
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
		Files.transfer()
		$("form").submit()	
})

$("#btn-cancel").on("click", function(){
	location = "list"
})

</script>

</body>
</html>