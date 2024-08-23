<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<c:choose>
	<c:when test="${category eq 'login'}"> <c:set var="title" value="- 로그인"/>  </c:when>
	<c:when test="${category eq 'find'}"> <c:set var="title" value="- 비밀번호찾기"/>  </c:when>
	<c:when test="${category eq 'join'}"> <c:set var="title" value="- 회원가입"/>  </c:when>
</c:choose>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>스마트 IoT ${title}</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="<c:url value='/assets/favicon.ico'/>" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="<c:url value='/css/styles.css'/>" rel="stylesheet" />
        <link href="<c:url value='/css/common.css'/>" rel="stylesheet" />
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        
		<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>
   		<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"></script>
  		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script src="<c:url value='/js/common.js'/>"></script>
    </head>
    <body>
        <div class="d-flex">
        
            <!-- Page content wrapper-->
            <div id="page-content-wrapper">
            
                <!-- Page content-->
                <div class="container-fluid my-4">
                	<tiles:insertAttribute name="container" />
                </div>
                
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="<c:url value='/js/scripts.js'/>"></script>
    </body>
</html>











