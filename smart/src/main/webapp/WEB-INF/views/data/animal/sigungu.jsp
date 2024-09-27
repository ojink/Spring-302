<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<select class="form-select w-px200" id="sigungu">
	<option value="">시군구 선택</option>
	<c:forEach items="${list.items.item}" var="vo">
	<option value="${vo.orgCd }">${vo.orgdownNm}</option>
	</c:forEach>
</select>
