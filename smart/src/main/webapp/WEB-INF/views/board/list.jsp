<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.user" var="auth"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function test(){
	$("form")
// 	.append(`<input type="hidden" name="_method" value="delete">`)
	 .append(`<input type="hidden" name="id" value="384">`)
	 .append(`<input type="hidden" name="pageNo" value="${page.pageNo}">`)
	 .attr("action", "test")
	 .submit();
}
</script>
</head>
<body>
<h3 class="my-4">방명록 목록</h3>
<a href="javascript:test()">테스트</a>


<div class="d-flex mb-2 justify-content-between">
	<div class="col">
	<form method="post" action="list" class="d-flex justify-content-between me-2">
		<div class="input-group w-px500">
			<select name="search" class="form-select">
				<option value="s1" >전체</option>
				<option value="s2" ${page.search == "s2" ? "selected" : ""}>제목</option>
				<option value="s3" <c:if test="${page.search eq 's3'}">selected</c:if> >내용</option>
				<option value="s4" <c:if test="${page.search eq 's4'}">selected</c:if> >작성자</option>
			</select>
			<input type="text" name="keyword" value="${page.keyword }" class="form-control w-px300">
			<button class="btn btn-primary"><i class="fa-solid fa-magnifying-glass"></i></button>	
		</div>
		
		<div class="col-auto">
			<select name="listSize" class="form-select">
				<c:forEach var="i" begin="1" end="5">
				<option value="${10*i}">${ 10*i }개씩</option>
				</c:forEach>				
			</select>
		</div>
	</form>
	</div>
	
	<!-- 관리자로 로그인되어 있는 경우만 글쓰기 가능 -->
	<sec:authorize access="isAuthenticated()">
	<button class="btn btn-primary" onclick="location='register'">글쓰기</button>
	</sec:authorize>

</div>


<table class="table tb-list">
<colgroup>
	<col width="100px">
	<col width="">
	<col width="130px">
	<col width="130px">
	<col width="90px">
</colgroup>
<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일자</th><th>조회수</th></tr>

<c:if test="${empty page.list}">
<tr><td colspan="5" class="text-center">방명록 글이 없습니다</td></tr>
</c:if>

<c:forEach items="${page.list}" var="vo">
<tr>
	<td>${vo.no}</td>
	<td><a class="text-link" href="javascript:info( ${vo.id } )">${vo.title }</a>
		<c:if test="${vo.filecnt > 0}">
		<i class="fa-solid fa-paperclip"></i>
		</c:if>
		
		<c:if test="${auth.userid == vo.writer and vo.notifycnt > 0}"> <!-- 본인의 글에 대한 미확인댓글이 있는 글에 깜빡임 처리 -->
		<i class="text-danger fa-solid fa-comment-dots fa-fade"></i>
		</c:if>
	</td>
	<td>${vo.name }</td>
	<td>${vo.writedate }</td>
	<td>${vo.readcnt }</td>
</tr>
</c:forEach>

</table>

<jsp:include page="/WEB-INF/views/include/page.jsp"/>

<script>
function info( id ){
	//form태그에 search, keyword, listSize 있음
	$("form").append(`<input type="hidden" name="id" value="\${id}">`)
			 .append(`<input type="hidden" name="pageNo" value="${page.pageNo}">`)
			 .attr("action", "info")
			 .submit();
}


$("[name=listSize]").on("change", function(){
	$("form").submit()
})
$("[name=listSize]").val( ${page.listSize} ).prop("selected", true)

</script>
</body>
</html>