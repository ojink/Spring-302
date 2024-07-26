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
					<form method="post" action="smartLogin">
						<div class="form-floating mb-3">
							<input class="form-control" name="userid" type="text"
								placeholder="아이디"> <label>아이디</label>
						</div>
						<div class="form-floating mb-3">
							<input class="form-control" name="userpw" type="password"
								placeholder="비밀번호"> <label>비밀번호</label>
						</div>
						<!--  
						<div class="form-check mb-3">
							<input class="form-check-input" id="inputRememberPassword"
								type="checkbox" value=""> <label
								class="form-check-label" for="inputRememberPassword">Remember
								Password</label>
						</div>
						-->
						<button class="btn btn-primary form-control py-3">로그인</button>
						
					</form>
				</div>
				<div class="card-footer text-center py-3">
					<div class="d-flex align-items-center justify-content-between my-2">
						<a class="small" href="findPassword">비밀번호찾기</a> 
						<a class="small" href="join">회원가입</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>






