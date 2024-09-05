<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="row justify-content-center">
		<div class="col-lg-5">
			<div class="card shadow-lg border-0 rounded-lg mt-5">
				<div class="card-header">
					<h3 class="text-center font-weight-light my-4">
						<a href="<c:url value='/'/>"><img src="<c:url value='/images/logo.png'/>"></a>
					</h3>
				</div>
				<div class="card-body p-5">
					<h5>죄송합니다.<br>내부적인 오류가 발생했습니다.</h5>
					<p><strong class="text-danger fs-5">${error}</strong></p>					
					<p>
					관련 문의사항은 네이버 고객센터에 알려주시면 친절하게 안내해 드리겠습니다.<br>
					감사합니다.</p>				
				</div>
			</div>
		</div>
	</div>
</body>
</html>