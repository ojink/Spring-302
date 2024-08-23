/**
 * 공통처리함수 
 */
"use strict"

$(function() {
	var today = new Date();
	var range = today.getFullYear()-100 + ":" + today.getFullYear(); //년도선택범위
	
	$.datepicker.setDefaults({
		dateFormat: "yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		showMonthAfterYear: true,
		monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월"
						, "7월", "8월", "9월", "10월", "11월", "12월" ],
		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		maxDate: today,  //최대선택가능날짜
		yearRange: range, 
	})
	
	$(".date").attr("readonly", true); //날짜 입력불가(달력선택만가능)
    $(".date").datepicker();
	
	$(".date").on("change", function(){
		$(this).next(".date-remove").removeClass("d-none")
	})
	
});

$(document)
.on("click", ".date + .date-remove", function(){
	//폰트이미지가 동적으로 만들어지므로 문서에 이벤트 등록
	$(this).addClass("d-none").prev(".date").val("");
})


//우편번호 주소찾기처리
function findPost( post, address1, address2 ){
	new daum.Postcode({
	    oncomplete: function(data) {
	    	//console.log( data )
	    	post.val( data.zonecode )
	    	var address = data.userSelectedType == "R" ? data.roadAddress : data.jibunAddress
	    	if( data.buildingName != "" ) address += " ("+data.buildingName+")"
	    	address1.val( address )
	    	address2.val("")
	    }
	}).open();		
}











