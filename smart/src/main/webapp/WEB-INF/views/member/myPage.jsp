<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
					
<h3 class="my-4">내 정보</h3>
			
<div class="mb-2 text-danger fw-bold">* 는 필수입력항목입니다</div>
<form method="post" action="myPage/modify" enctype="multipart/form-data">
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
<tr><th>아이디</th>
	<td>${vo.userid }</td>
</tr>
<tr><th><span>*</span>성명</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="name" value="${vo.name}" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>이메일</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="text" name="email" value="${vo.email}" title="이메일" class="form-control check-item">
			</div>
			<div class="col-auto desc fw-bold"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>성별</th>
	<td><div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" type="radio" name="gender" 
		  				value="남" <c:if test='${vo.gender == "남" }'>checked</c:if> >남
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label" >
		  	<input class="form-check-input" type="radio" name="gender" 
		  				value="여" ${vo.gender == "여" ? "checked" : ""} >여
		  </label>
		</div>
	</td>
</tr>
<tr><th>프로필이미지</th>
	<td><div class="row">
			<div class="d-flex align-items-center gap-3 file-info">
				<div class="file-preview profile px80 ">
					<c:choose>
						<c:when test="${empty vo.profile }">  <!-- 기본이미지 지정 -->
							<i class="font-profile fa-solid fa-circle-user"></i>
						</c:when>
						<c:otherwise> <!-- 실제프로필 지정 -->							
							<img src="${vo.profile }">
						</c:otherwise>
					</c:choose>
				</div>
				<label>
					<a class="btn btn-outline-primary">프로필선택</a>
					<input type="file" name="file" 
						class="d-none form-control image-only file-single" accept="image/*">
				</label>
				<a class="btn btn-outline-danger ${empty vo.profile ? 'd-none' : ''} file-remove">삭제</a>
			</div>
		</div>
	</td>
</tr>
<tr><th>생년월일</th>
	<td><div class="row">
			<div class="col-auto d-flex align-items-center">
				<input type="text" name="birth" value="${vo.birth }" class="date form-control">
				<i role="button" class="fs-3 fa-regular fa-calendar-xmark text-danger date-remove ${empty vo.birth ? 'd-none' : ''}"></i>
			</div>
		</div>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="text" name="phone" value="${vo.phone }" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th>주소</th>
	<td><div class="row">
			<div class="col-auto d-flex gap-3">
				<a class="btn btn-primary" id="btn-post">주소찾기</a>
				<input type="text" name="post"  value="${vo.post }" class="w-px80 form-control" readonly>
			</div>
		</div>
		<div class="mt-2">
			<input type="text" name="address1" value="${vo.address1 }" class="form-control mb-1" readonly>
			<input type="text" name="address2" value="${vo.address2 }" class="form-control">
		</div>
	</td>
</tr>

</table>	
</form>

<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary px-4" id="btn-save">저장</button>
	<button class="btn btn-outline-primary px-4" id="btn-cancel">취소</button>
</div>


<script src="<c:url value='/js/member.js' />"></script>
<script>
$("#btn-save").on("click", function(){
	if( $("[name=name]").val()=="" ){
		alert("성명을 입력하세요!")
		return;
	}	
	
	if( validStatus() ) {
		//원래O -> 화면프로필O -> img태그O
		//원래O -> 화면기본O ->  img태그X
		var img = $(".file-preview").find("img").length==1 ? true : false;
		
		$("form").append( `<input type='hidden' name="userid" value="${vo.userid}"> `)
				 .append( `<input type="hidden" name="_method" value="put" >` )
				 .append( `<input type="hidden" name="img" value="\${img}" >` )
				 .submit()
	}
})

$("#btn-cancel").on("click", function(){
	location = "<c:url value='/'/>"
})

$(function(){
	//이메일에 keyup 이벤트 강제발생 시키기
	$("[name=email]").trigger("keyup")
})

</script>	
	
</body>
</html>










