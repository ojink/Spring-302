<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.user" var="auth"/>
</sec:authorize>

<div class="row justify-content-center mt-5">
	<div class="w-75 comment">
		<div class="comment-title mb-2 d-flex align-items-center justify-content-between d-none h-px40">
			<label><span>댓글작성 [ </span><span class="writing">0</span><span> / 200 ]</span></label>
			<a class="btn btn-outline-primary btn-sm d-none" id="btn-register">댓글등록</a>
		</div>
		
		<div id="comment-input" role="button">
			<div class="form-control text-center py-4 guide">
			${empty auth ? "댓글을 입력하려면 여기를 클릭후 로그인하세요" : "댓글을 입력하세요" }
			</div>
		</div>
	</div>
</div>

<div class="row justify-content-center my-4">
	<div class="w-75" id="comment-list" data-writer="${vo.writer}" data-id="${vo.id}"></div>
</div>


<script>
$(document)
.on("keyup", "#comment-input textarea", function(){
	writing( $(this) )
	
	//입력글자수가 있으면/없으면 등록버튼 보이게/안보이게
	if( $(this).val().length > 0 ) 	$("#btn-register").removeClass("d-none");
	else 							$("#btn-register").addClass("d-none")
})
.on("contextmenu", "#comment-input textarea", function( e ){
	//마우스 우클릭으로 클립보드에 있던 문자 붙여넣기 방지
	e.preventDefault();
})
.on("blur", "#comment-input textarea", function(){
	//입력글자 없이 커서가 다른곳으로 가면 댓글등록 초기화
	if( $(this).val().length==0 ) initComment()
})
.on("keyup", "#comment-list textarea", function(){
	writing( $(this) )
})
.on("blur", "#comment-list textarea", function(){
	if( $(this).val().trim().length==0 ) viewStatus( $(this).closest(".comment") ) 	
})
.on("click", ".pagination a", function(){
	//클릭한 페이지 댓글목록 조회
	if( ! $(this).hasClass("active") ) commentList( $(this).data("page") )
})



$("#btn-register").on("click", function(){
	$.ajax({
		type: "post",
		url: "comment/register",
		data: { parent_id: ${vo.id}, content: $("#comment-input textarea").val() }
	}).done(function( response ){
		if( response ) {
			//댓글등록시 미확인 댓글이 발생했음을 알리자			
			var info =  { userid : "${vo.writer}", board_id : ${vo.id} } 
			publishNotify( info )
			
			initComment()
			commentList(1)
		}
		else alert("댓글등록 실패ㅠㅠ")
	})
})

$(function(){
	commentList(1)	
})

//댓글목록조회
function commentList(pageNo){
	$.ajax({
		url: "comment/list/${vo.id}/" + pageNo
	}).done(function(response){
		$("#comment-list").html( response )
	})
}

function initComment(){
	$(".comment .comment-title").addClass("d-none")
	$(".comment .writing").text(0)
	
	$("#comment-input").html(
		`<div class="form-control text-center py-4 guide">
			${empty auth ? "댓글을 입력하려면 여기를 클릭후 로그인하세요" : "댓글을 입력하세요" }
		 </div>`)
}


function writing( tag ){
	var input = tag.val();
	if( input.length > 200 ){
		input = input.substr(0,200);
		tag.val( input )
		alert( "최대 200자까지 입력할 수 있습니다" )
	}
	tag.closest(".comment").find(".writing").text( input.length ) //입력글자수 표시
}

$("#comment-input").on("click", function(){
	if( ${empty auth} ){
		if( confirm("로그인 하시겠습니까?") ){
			$(`<form action="<c:url value='/member/login'/>" method="post"></form>`)
			.appendTo('body')
			.append(`<input type="hidden" name="redirect" value="true">`)
			.append( addToForm(info) )
			.submit()
		}
	}else{
		//클릭할때마다 textarea 가 추가되지 않게 guide 있을때만 추가
		if( $(this).children(".guide").length > 0 ){
			$(`<textarea class="form-control h-px110"></textarea>`)
			.appendTo( $(this) )
			.focus()
			.siblings(".guide").remove();
			
			$(".comment-title").removeClass("d-none")
		}
	}
})
</script>