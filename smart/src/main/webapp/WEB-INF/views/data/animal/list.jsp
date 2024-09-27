<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:if test="${animal.totalCount==0}">
<table class="table tb-list">
<tr><th class="text-center">구조동물</th></tr>
<tr><td class="text-center">${empty response.errorMsg ? '구조동물이 없습니다' : response.errorMsg}</td></tr>
</table>
</c:if>

<c:forEach items="${animal.items.item}" var="vo">
<table class="table tb-list animal" >
<colgroup>
	<col width="120px">
	<col width="100px"><col width="60px">
	<col width="70px"><col width="160px">
	<col width="70px"><col width="120px">
	<col width="70px"><col width="160px">
	<col width="110px"><col width="110px">
</colgroup>
<tr><th rowspan="3" class="text-center">
	<c:if test="${fn: contains(vo.filename, 'files')}">
		<img role="button" class="popfile" src="${vo.filename}" 
				data-popfile="${vo.popfile}"> 
	</c:if>
	<c:if test="${ ! fn: contains(vo.filename, 'files')}">
		<i class="fa-solid fa-paw fs-1"></i>
	</c:if>
	</th>
	<th>성별</th><td>${vo.sexCd }</td>
	<th>나이</th><td>${vo.age }</td>
	<th>체중</th><td>${vo.weight }</td>
	<th>색상</th><td>${vo.colorCd }</td>
	<th>접수일자</th><td>${vo.happenDt }</td>
</tr>
<tr><th>특징</th>
	<td colspan="9">${vo.specialMark }</td>
</tr>
<tr><th>발견장소</th>
	<td colspan="8">${vo.happenPlace }</td>
	<th>${vo.processState }</th>
</tr>
<tr><td colspan="2">${vo.careNm }</td>
	<td colspan="7">${vo.careAddr }</td>
	<td colspan="2">${vo.careTel }</td>
</tr>
</table>
</c:forEach>

<jsp:include page="/WEB-INF/views/include/subPage.jsp"/> 

<style>
.animal img.popfile { width:120px; height:120px }
</style>