<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<form method="post" action="tempPassword">
						<div class="form-floating mb-3">
							<input class="form-control" name="userid" type="text"
								placeholder="아이디"> <label>아이디</label>
						</div>
						<div class="form-floating mb-3">
							<input class="form-control" name="email" type="email"
								placeholder="이메일"> <label>이메일</label>
						</div>
						
						<div class="d-flex gap-4">
							<button class="btn btn-primary form-control py-3">확인</button>
							<button type="button" onclick="location='login'" 
								class="btn btn-outline-primary form-control py-3">취소</button>
						</div>
						
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>






