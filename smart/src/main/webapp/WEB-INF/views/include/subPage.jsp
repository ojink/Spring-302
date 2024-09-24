<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="page" value="${subPage}"/>

<nav>
  <ul class="pagination justify-content-center mt-4">
  	<c:if test="${page.prev}">
    <li class="page-item"><a class="page-link" data-page="1"><i class="fa-solid fa-angles-left"></i></a></li>
    <li class="page-item"><a class="page-link" data-page="${page.beginPage-page.pageSize}"><i class="fa-solid fa-angle-left"></i></a></li>
   	</c:if>
    
	<c:forEach var="no" begin="${page.beginPage}" end="${page.endPage}" step="1">
	<c:if test="${no eq page.pageNo}">
    	<li class="page-item"><a class="page-link active">${no }</a></li>
	</c:if>
	<c:if test="${no ne page.pageNo }">
	    <li class="page-item"><a class="page-link" data-page="${no}">${no }</a></li>
	</c:if>
	</c:forEach>	    
    
    <c:if test="${page.next}">
    <li class="page-item" ><a class="page-link" data-page="${page.endPage+1}"><i class="fa-solid fa-angle-right"></i></a></li>
    <li class="page-item"><a class="page-link" data-page="${page.totalPage}"><i class="fa-solid fa-angles-right"></i></a></li>
    </c:if>
  </ul>
</nav>

<script>
</script>