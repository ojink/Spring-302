/**
 *  회원관련처리 
 */

$("#btn-post").on("click", function(){
	findPost( $("[name=post]"), $("[name=address1]"), $("[name=address2]") )
})

var member = {
	//태그별로 상태확인
	tagStatus: function( tag, keyup ){
		if( tag.is("[name=userpw]") )			return this.userpwStatus( tag.val(), keyup );
		else if( tag.is("[name=userpw_ck]") )	return this.userpwCheckStatus( tag.val() );
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
	showStatus: function( tag ){
		var status = this.tagStatus( tag, true );  //키보드입력여부 추가
		tag.closest(".input-check").find(".desc")
								   .text( status.desc )
								   .removeClass("text-success text-danger")
								   .addClass( status.is ? "text-success" : "text-danger" );
	}
	
}