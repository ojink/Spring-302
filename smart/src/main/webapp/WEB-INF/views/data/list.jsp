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
<h3 class="my-4">공공데이터</h3>

<ul class="nav nav-pills justify-content-center mb-3 data">
	<li class="nav-item"><a role="button" class="nav-link">약국조회</a></li>
	<li class="nav-item"><a role="button" class="nav-link">구조동물조회</a></li>
	<li class="nav-item"><a role="button" class="nav-link">기타1</a></li>
	<li class="nav-item"><a role="button" class="nav-link">기타2</a></li>
</ul>

<div class="d-flex mb-2 justify-content-between">
	<div class="col-auto animal-top d-flex gap-2"></div>
	<div class="col-auto">
		<select id="listSize" class="form-select">
			<c:forEach var="no" begin="1" end="5">
			<option value="${10*no}">${10*no}개씩</option>
			</c:forEach>
		</select>
	</div>
</div>

<div id="data-list"></div>

<jsp:include page="/WEB-INF/views/include/modal.jsp" />
<jsp:include page="/WEB-INF/views/include/loading.jsp" />

<script type="text/javascript" 
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c7ee079eba00994e85447a1b099dc049"></script>
<script type="text/javascript" 
	src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=jsbfs5dqif"></script>
<script type="text/javascript" src="<c:url value='/js/animal.js'/>"></script>	
<script>
$(function(){
	$(".data li").eq(1).trigger("click");  //약국조회 되도록 클릭 강제발생 시키기
})

$(document)
.on("click", ".pagination a", function(){
	if( $("table#pharmacy").length > 0 ){
		pharmacyList( $(this).data("page"), $("#listSize option:selected").val() )
	}else{
		animalList( $(this).data("page"), $("#listSize option:selected").val() )
	}
	window.scrollTo(0, 100);
})
.on("click", ".map", function(){ //약국명 클릭시 지도에 위치표시하기
	//showKakaoMap( $(this) );
	showNaverMap( $(this) );
})


//네이버지도
function showNaverMap( tag ){
	$("#map").remove();
	$("#modal").after(`<div id="map" style="width:670px;height:700px;"></div>`);
	
	var xy = new naver.maps.LatLng( tag.data("y"), tag.data("x") );
	var mapOptions = {
	    center: xy,
	    zoom: 15
	};
	var map = new naver.maps.Map('map', mapOptions);
	
	var marker = new naver.maps.Marker({
	    position: xy,
	    map: map
	});
	
	var infowindow = new naver.maps.InfoWindow({
	    content: `<div class="p-2 fw-bold text-primary">\${tag.text()}</div>`
	});
    infowindow.open(map, marker);
	
	//지도가 있는 #map 태그를 모달창에 옮기기
	$("#modal .modal-body").html( $("#map") )
	new bootstrap.Modal( $("#modal") ).show()
	
}

//카카오지도
function showKakaoMap( tag ){
	$("#map").remove();
	$("#modal").after(`<div id="map" style="width:670px;height:700px;"></div>`);
	
	var xy = new kakao.maps.LatLng( tag.data("y"), tag.data("x") );
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: xy, //지도의 중심좌표.
		level: 4 //지도의 레벨(확대, 축소 정도)
	};
	//"XPos": 126.7243667, "YPos": 37.4000007,
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: xy
	});
	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	
	
	var iwContent = `<div class="p-2 fw-bold text-primary">\${tag.text()}</div>`; 
	// 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({
	    position : xy, 
	    content : iwContent 
	});
	// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
	infowindow.open(map, marker); 	
	
	//지도가 있는 #map 태그를 모달창에 옮기기
	$("#modal .modal-body").html( $("#map") )
	new bootstrap.Modal( $("#modal") ).show()
}



$("#listSize").on("change", function(){
	if( $("table#pharmacy").length > 0 ){
		pharmacyList( 1, $(this).val() )
	}else
		animalList( 1, $(this).val() )
})

$(".data li").on("click", function(){
	$(".data li a").removeClass("active")
	$(this).children("a").addClass("active")
	$("#data-list").empty()
	
	var idx = $(this).index()
	if( idx==0 ) 		pharmacyList( 1, $("#listSize option:selected").val() ); 
	else if( idx==1 ) 	animalList( 1, $("#listSize option:selected").val() ); 
})

//약국 조회
function pharmacyList( pageNo, listSize ){
	$(".animal-top").empty()
	$(".loading").removeClass("d-none") //로딩중..
	$.ajax({
		url: "pharmacy",
		data: { pageNo: pageNo, listSize: listSize }
	}).done(function(response){
		$("#data-list").html( response )
		$(".loading").addClass("d-none")
	})
	
/*	
	$("#data-list").html(
		`<table class="table tb-list" id="pharmacy">
			<colgroup><col width="250px"><col width="200px"><col></colgroup>
			<thead>
				<tr><th>약국명</th><th>전화번호</th><th>주소</th></tr>
			</thead>
			<tbody></tbody>
		 </table>`)
	
	$.ajax({
		url: "pharmacy",
	}).done(function(response){
		console.log( response )
		var tag = "";
		if( response.totalCount==0 ){
			tag = `<tr><td colspan="3" class="text-center">약국목록이 없습니다</td></tr>`
		}else{
			for(var item of response.items.item){
				tag += `<tr><td>\${item.yadmNm}</td>
							<td>\${item.telno ? item.telno : ''}</td>
							<td>\${item.addr}</td>
						</tr>`
			}
		}

		$("#pharmacy tbody").html( tag )
	})
*/	
}

</script>

</body>
</html>