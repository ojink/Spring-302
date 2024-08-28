/**
 *  회원관련처리 
 */


$(function(){
	//생년월일자를 13세이상으로 선택가능하게 제한하기
	var endDay = new Date()
	endDay.setFullYear( endDay.getFullYear() - 13 );
	$("[name=birth]").datepicker( "option", "maxDate", endDay );
})

//각 태그입력유효성 재확인
function validStatus(){
	var valid = true;
	
	$(".check-item").each(function(){
		var desc = $(this).closest(".input-check").find(".desc")
		//아이디는 사용가능 아닌 경우 유효하지 않음
		if( $(this).is("[name=userid]") && ! desc.text().includes("사용가능")  ){
			valid = false;
		}else if( ! desc.hasClass("text-success") ){
			valid = false;
		}
		
		if( ! valid ){ //회원가입불가 이유 표시하기
			alert("회원가입 불가!\n" + $(this).attr("title") + " " + desc.text() )
			$(this).focus()
			return valid;
		}
	})
	
	return valid;
}


$("#btn-post").on("click", function(){
	findPost( $("[name=post]"), $("[name=address1]"), $("[name=address2]") )
})


//키보드입력시 바로 입력태그상태 표시하기
$(".check-item").on("keyup", function(e){
	//아이디에서 엔터하는 경우 중복확인하기
	if( $(this).is("[name=userid]") && e.keyCode==13 ){
		idCheck()
	}else
		member.showStatus( $(this) );
})


var member = {
	//태그별로 상태확인
	tagStatus: function( tag, keyup ){
		if( tag.is("[name=userpw]") )			return this.userpwStatus( tag.val(), keyup );
		else if( tag.is("[name=userpw_ck]") )	return this.userpwCheckStatus( tag.val() );
		else if( tag.is("[name=userid]") )		return this.useridStatus( tag.val() )
		else if( tag.is("[name=email]") )		return this.emailStatus( tag.val() );
	},
	
	//이메일상태 확인
	emailStatus: function( email ){
		var reg = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/i;
		if( email == "" )				return this.common.empty;
		else if( reg.test(email) )		return this.email.valid;
		else                        	return this.email.invalid;
	},
	
	//이메일상태값
	email: {
		valid:   { is: true,    desc: "형식이 유효합니다" },
		invalid: { is: false,   desc: "형식이 유효하지 않습니다" }
	},
	
	//아이디상태 확인
	useridStatus: function( id ){
		var reg = /[^a-z0-9]/g
		if( id=="" )				return this.common.empty;
		else if( reg.test(id) )		return this.userid.invalid;
		else if( id.length<5 )		return this.common.min;
		else if( id.length>10 )		return this.common.max;
		else						return this.userid.valid;
	},
	
	//아이디의 상태값
	userid: {
		valid:    { is: true,    desc: "중복확인하세요" },
		invalid:  { is: false,   desc: "영문 소문자,숫자만 입력하세요" },
		usable:   { is: true,    desc: "사용가능 합니다" },
		unUsable: { is: false,   desc: "이미 사용중입니다" }
	},
	
	//공통 상태값
	common: {
		empty: { is: false,   desc: "입력하세요" },
		min:   { is: false,   desc: "5자이상 입력하세요" },
		max:   { is: false,   desc: "10자이내 입력하세요" },
		space: { is: false,   desc: "공백없이 입력하세요" }
	},
	
	//비밀번호 상태값
	userpw: {
		valid:   { is: true,   desc: "사용가능합니다" },
		invalid: { is: false,  desc: "영문 대/소문자, 숫자만 입력하세요" },
		lack:    { is: false,  desc: "영문 대/소문자, 숫자를 모두 포함해야 합니다"},
		equal:   { is: true,   desc: "비밀번호와 일치합니다" },
		notEqual:{ is: false,  desc: "비밀번호와 일치하지 않습니다" },
	},
	
	space: /\s/g,
	
	//비밀번호 입력상태 확인: 영문 대/소문자, 숫자 조합 5자~10자
	userpwStatus: function( pw, keyup ){
		if( keyup ){  //비번입력시 비번확인은 초기화하기
			$("[name=userpw_ck]").val("")
								 .closest(".input-check").find(".desc")
								 .removeClass("text-success text-danger")
								 .text("");
		}
		
		var reg = /[^a-zA-Z0-9]/g, upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		if( pw == "" )					return this.common.empty;
		else if( pw.match(this.space) )	return this.common.space;
		else if( reg.test(pw) )			return this.userpw.invalid;
		else if( pw.length < 5 )		return this.common.min;
		else if( pw.length > 10 )		return this.common.max;
		else if( ! upper.test(pw) || ! lower.test(pw) || ! digit.test(pw) )		
										return this.userpw.lack;
		else                			return this.userpw.valid;
	},
	
	//입력 비번확인(재입력) 상태확인
	userpwCheckStatus: function( ck ){
		if( ck == $("[name=userpw]").val() )	return this.userpw.equal;
		else 									return this.userpw.notEqual;
	},
	
	
	//입력상태표시
	showStatus: function( tag, status ){
		if( status == undefined ){
			status = this.tagStatus( tag, true );  //키보드입력여부 추가
		}
		tag.closest(".input-check").find(".desc")
								   .text( status.desc )
								   .removeClass("text-success text-danger")
								   .addClass( status.is ? "text-success" : "text-danger" );
	}
	
}