<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">방명록 정보</h3>

<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
	<col width="200px">
	<col width="140px">
	<col width="200px">
	<col width="100px">
</colgroup>
<tr><th>제목</th>
	<td colspan="5">${vo.title }</td>
</tr>
<tr><th>작성자</th>
	<td>${vo.name }</td>
	<th>작성일자</th>
	<td>${vo.writedate }</td>
	<th>조회수</th>
	<td>${vo.readcnt }</td>
</tr>
<tr><th>내용</th>
	<td colspan="5">${fn: replace(vo.content, crlf, "<br>") }</td>
</tr>
<tr><th>첨부파일</th>
	<td colspan="5">
		<c:forEach items="${vo.fileList}" var="f">
		<div class="row py-1">
			<c:if test="${files[f.id]}">
			<label role="button" data-file="${f.id }" class="d-flex col-auto text-link gap-3 file-download">
				<span>${f.filename }</span>
				<i class="fs-3 fa-regular fa-circle-down"></i>
			</label>
			</c:if>
			
			<c:if test="${! files[f.id]}">
			<del class="text-danger">${f.filename }</del>
			</c:if>			
		</div>
		</c:forEach>
	</td>
</tr>
</table>

<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary" id="btn-list">목록으로</button>

	<sec:authorize access="isAuthenticated()"> <!-- 인증(로그인)된 경우 -->
		<sec:authentication property="principal.user" var="auth"/>
	<!-- 로그인한 사용자가 쓴 글에 대해서만 수정/삭제 가능 -->
	<c:if test="${auth.userid == vo.writer}">
	<button class="btn btn-primary" id="btn-modify">정보수정</button>
	<button class="btn btn-primary" id="btn-delete">정보삭제</button>
	</c:if>
	
	</sec:authorize>
	
</div>

<script>
$(".file-download").on("click", function(){
	var id = $(this).data("file");
	location = `<c:url value='/board/download?id=\${id}'/>`
})

// $("#btn-delete").on("click", function(){
// 	if( confirm("정말 삭제하시겠습니까?") ){
// 		$("<form method='post' action='delete'></form>")
// 		 .appendTo("body")
// 		 .append(`<input type="hidden" name="_method" value="delete">`)
// 		 .append( addToForm(info) )
// 		 .submit()
// 	}
// })

var info = {  id: 		"${vo.id}" 
			, pageNo: 	"${page.pageNo}"	
			, search: 	"${page.search}"	
			, keyword: 	"${page.keyword}"	
			, listSize: "${page.listSize}"	
			}

$("#btn-list, #btn-modify, #btn-delete").on("click", function(){
	var id = $(this).attr("id"); //btn-list, btn-modify, btn-delete
	id = id.substr( id.indexOf("-")+1 ) // list, modify, delete
	
	$(`<form method="post" action="\${id}"></form>`)
	.appendTo("body")
	.append( addToForm(info) );
	
	if( id=="delete"){
		if( confirm("정말 삭제하시겠습니까?") ){
			$("form")//.append(`<input type="hidden" name="_method" value="delete">`)
					 .submit();
		}
	}else{
		$("form").submit()
	}
})
</script>

</body>
</html>