<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<table class="table tb-list" id="pharmacy">
	<colgroup>
		<col width="250px">
		<col width="200px">
		<col>
	</colgroup>
	<thead>
		<tr><th>약국명</th><th>전화번호</th><th>주소</th></tr>
	</thead>
	<tbody>
		<c:if test="${pharmacy.totalCount==0}">
		<tr><td colspan="3" class="text-center">약국목록이 없습니다</td></tr>
		</c:if>
		
		<c:forEach items="${pharmacy.items.item}" var="item">
		<tr><td><a class="map text-link" data-x="${item.XPos}" data-y="${item.YPos}" 
					href="javascript:void(0)">${item.yadmNm}</a></td>
			<td>${item.telno}</td>
			<td>${item.addr}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/WEB-INF/views/include/subPage.jsp" />












