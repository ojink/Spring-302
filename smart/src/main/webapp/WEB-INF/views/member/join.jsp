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
		<div class="col-lg-7">
			<div class="card shadow-lg border-0 rounded-lg my-5">
				<div class="card-body p-5">
					<div class="d-flex justify-content-between">
						<a href="<c:url value='/'/>"><img src="<c:url value='/images/logo.png'/>"></a>
						<h3 class="my-4">회원가입</h3>
					</div>
			
<div class="mb-2 text-danger fw-bold">* 는 필수입력항목입니다</div>
<form method="post" action="register" enctype="multipart/form-data">
<!-- 
파일업로드하기 위한 설정
1. form 태그의 method: post
2. form 태그로 파일전송하도록 지정 : enctype="multipart/form-data"
 -->
<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th><span>*</span>성명</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="name" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>아이디</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="text" name="userid" title="아이디" class="form-control check-item">
			</div>
			<div class="col-auto">
				<a class="btn btn-primary" id="btn-userid">중복확인</a>
			</div>
			<div class="col-auto desc fw-bold"></div>
			<div class="mt-2">아이디는 영문 소문자나 숫자 5자~10자</div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>비밀번호</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="password" name="userpw" title="비밀번호" class="form-control check-item">
			</div>
			<div class="col-auto desc fw-bold"></div>
			<div class="mt-2">비밀번호는 영문 대/소문자,숫자 조합 5자~10자</div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>비밀번호확인</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="password" name="userpw_ck" title="비밀번호확인" class="form-control check-item">
			</div>
			<div class="col-auto desc fw-bold"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>이메일</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="text" name="email" title="이메일" class="form-control check-item">
			</div>
			<div class="col-auto desc fw-bold"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>성별</th>
	<td><div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" type="radio" name="gender" value="남">남
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label" >
		  	<input class="form-check-input" type="radio" name="gender" value="여" checked>여
		  </label>
		</div>
	</td>
</tr>
<tr><th>프로필이미지</th>
	<td><div class="row">
			<div class="d-flex align-items-center gap-3 file-info">
				<!-- 기본이미지 지정 -->
				<div class="file-preview profile px80 ">
					<i class="font-profile fa-solid fa-circle-user"></i>
				</div>
				<label>
					<a class="btn btn-outline-primary">프로필선택</a>
					<input type="file" name="file" 
						class="d-none form-control image-only file-single" accept="image/*">
				</label>
				<a class="btn btn-outline-danger d-none file-remove">삭제</a>
			</div>
		</div>
	</td>
</tr>
<tr><th>생년월일</th>
	<td><div class="row">
			<div class="col-auto d-flex align-items-center">
				<input type="text" name="birth" class="date form-control">
				<i role="button" class="fs-3 fa-regular fa-calendar-xmark text-danger date-remove d-none"></i>
			</div>
		</div>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="phone" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th>주소</th>
	<td><div class="row">
			<div class="col-auto d-flex gap-3">
				<a class="btn btn-primary" id="btn-post">주소찾기</a>
				<input type="text" name="post" class="w-px80 form-control" readonly>
			</div>
		</div>
		<div class="mt-2">
			<input type="text" name="address1" class="form-control mb-1" readonly>
			<input type="text" name="address2" class="form-control">
		</div>
	</td>
</tr>

</table>	
</form>
					
					<div class="btn-toolbar justify-content-center gap-2">
						<button class="btn btn-primary" id="btn-join">회원가입</button>
						<button class="btn btn-outline-primary px-4" id="btn-cancel">취소</button>
					</div>
				</div>
			</div>
		</div>
	</div>

<script src="<c:url value='/js/member.js' />"></script>
<script>
$("#btn-join").on("click", function(){
	
	if( $("[name=name]").val()=="" ){
		alert("성명을 입력하세요!")
		$("[name=name]").focus()
		return;
	}
	
	//유효성확인되면 서브밋하기
	if( validStatus() ) $("form").submit()
})


$("#btn-userid").on("click", function(){
	idCheck()
})

//아이디 중복확인
function idCheck(){
	var id = $("[name=userid]")
	//입력이 유효한지 확인 후 유효하지 않으면 DB에서 확인 불필요
	var status = member.tagStatus( id )
	if( status.is ){
		$.ajax({
			url: "idCheck",
			data: { userid: id.val() }
		}).done(function( response ){
			//사용가능:true   사용중:false
			var status = response ? member.userid.usable 
								  : member.userid.unUsable;
			member.showStatus( id, status )
		})
		
		
	}else{
		alert("아이디 중복확인 불필요!\n" + status.desc)
		id.focus()
	}
}

$("table th span").addClass("text-danger fw-bold me-2")

$("#btn-cancel").on("click", function(){
	location = "<c:url value='/'/>"
})
</script>	
	
</body>
</html>










