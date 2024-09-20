<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" ></form>

<script>
$("form")
 .attr("action", "<c:url value='/${url}'/>")
 .append(`<input type="hidden" name="id" value="${id}" >`)
 .append(`<input type="hidden" name="pageNo" value="${page.pageNo}" >`)
 .append(`<input type="hidden" name="search" value="${page.search}" >`)
 .append(`<input type="hidden" name="keyword" value="${page.keyword}" >`)
 .append(`<input type="hidden" name="listSize" value="${page.listSize}" >`)
 .submit()
</script>