<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">비밀번호 변경</h3>

<table class="table tb-row">
<colgroup>
	<col width="200px">
	<col>
</colgroup>
<tr><th>현재 비밀번호</th>
	<td><div class="row">
			<div class="col-auto">
				<input type="password" name="current" class="form-control">
			</div>
		</div>
	</td>
</tr>
<tr><th>새 비밀번호</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="password" name="userpw" class="form-control check-item">
			</div>
			<div class="col-auto fw-bold desc"></div>
			<div class="mt-2">비밀번호는 영문 대/소문자, 숫자 조합 5자~10자</div>
		</div>
	</td>
</tr>
<tr><th>새 비밀번호 확인</th>
	<td><div class="row input-check">
			<div class="col-auto">
				<input type="password" name="userpw_ck" class="form-control check-item">
			</div>
			<div class="col-auto fw-bold desc"></div>
		</div>
	</td>
</tr>
</table>

<div class="btn-toolbar justify-content-center">
	<button class="btn btn-primary px-4" id="btn-save">변경</button>
</div>

<script src="<c:url value='/js/member.js'/>"></script>
<script>

function resetPassword(){
	$.ajax({
		url: "resetPassword",
		data: { userpw: $("[name=userpw]").val(), userid: "${loginInfo.userid}" }
	}).done(function( response ){
		if( response ){
			alert("비밀번호가 변경되었습니다")
			location = "<c:url value='/'/>"
		}else{
			alert("비밀번호 변경 실패ㅠㅠ")
		}
	})
}

$("#btn-save").on("click", function(){
	if( tagIsValid() ){
		//현재 비번이 정확인지 확인
		$.ajax({
			url: "correctPassword",
			data: { userpw: $("[name=current]").val(), userid: "${loginInfo.userid}"  }
		}).done(function( response ){
			//console.log( response )
			if( response ){
				//새비번 변경은 현재 비번과 다른 경우만 처리   
				if( $("[name=userpw]").val() == $("[name=current]").val() ){
					alert( "새 비밀번호가 현재 비밀번호와 같습니다\n" 
							+ "새 비밀번호를 다시 입력하세요")
					$("[name=userpw]").val("").focus().trigger("keyup")
					
				}else{				
					//DB에 새 비번으로 변경
					resetPassword()
				}
				
			}else{
				alert("현재 비밀번호가 정확하지 않습니다")
				$("[name=current]").val("").focus()
			}
		})
	}
})

//키보드입력시 바로 입력태그상태 표시하기
$(".check-item").on("keyup", function(){
	member.showStatus( $(this) );
})

//입력태그값이 유효한 상태인지 확인
function tagIsValid(){
	var ok = true;
	
	if( $("[name=current]").val()=="" ){
		alert("현재 비밀번호를 입력하세요!");
		$("[name=current]").focus();
		ok = false;
	}else{
		//비번 입력체크할 항목들
		$(".check-item").each(function(){
			var status = member.tagStatus( $(this) );
			console.log( status )
			if( ! status.is ){
				alert( "비밀번호 변경 불가\n" + status.desc );
				$(this).focus();
				ok = false;
				return ok;
			}
		})
		
	}
	return ok;
}


</script>



</body>
</html>