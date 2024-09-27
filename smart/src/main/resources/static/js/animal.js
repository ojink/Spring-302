/**
 * 구조동물 관련 
 */


//시도조회
function animalSido(){
	$.ajax({
		url: "animal/sido"
	}).done(function( response ){
		$(".animal-top").html( response )
		animalType() //축종추가
	})
}


//구조동물 조회
function animalList( page, size ){
	//시도조회
	if( $("#sido").length==0 ) animalSido();
	
	var animal = {};
	animal.sido = $("#sido").length==0 ? "" : $("#sido").val(); //시도
	animal.sigungu = $("#sigungu").length==0 ? "" : $("#sigungu").val(); //시군구
	animal.shelter = $("#shelter").length==0 ? "" : $("#shelter").val(); //보호소
	animal.upkind = $("#upkind").length==0 ? "" : $("#upkind").val(); //축종
	animal.kind = $("#kind").length==0 ? "" : $("#kind").val(); //품종
	
	$(".loading").removeClass("d-none")
	$.ajax({
		url: `animal/list/${page}/${size}`,
		data: JSON.stringify( animal ),
		type: 'post',
		contentType: 'application/json'
		//data: { pageNo: page, listSize: size }
	}).done(function( response ){
		$("#data-list").html( response )
		$(".loading").addClass("d-none")
	})
}

//축종태그
function animalType(){
//	축종코드
//	 - 개 : 417000
//	 - 고양이 : 422400
//	 - 기타 : 429900
	$(".animal-top").append(
		`<select class="form-select w-px150" id="upkind">
			<option value="">축종 선택</option>
			<option value="417000">개</option>
			<option value="422400">고양이</option>
			<option value="429900">기타</option>
		</select>`)
}

//시군구 조회
function animalSigungu(){
	$("#sigungu").remove()
	$("#shelter").remove()
	if( $("#sido").val()=="" ) return;
	
	$.ajax({
		url: "animal/sigungu",
		data: { sido: $("#sido").val() }
	}).done(function(response){
		$("#sido").after( response )
	})
}

//보호소 조회
function animalShelter(){
	$("#shelter").remove()
	if( $("#sigungu").val()=="" ) return;
	
	$.ajax({
		url: "animal/shelter",
		data: { sido: $("#sido").val(), sigungu: $("#sigungu").val() }
	}).done(function( response ){
		$("#sigungu").after( response )
	})
	
}

//축종에 대한 품종 조회
function animalKind(){
	$("#kind").remove()
	
	$.ajax({
		url: "animal/kind",
		data: { upkind: $("#upkind").val() }
	}).done(function(response){
		$("#upkind").after( response )
	})
	
}

$(document)
.on("click", "img.popfile", function(){
	var pop = $(this).data("popfile")
	$("#modal .modal-body").html( `<img src="${pop}">` )
	$("#modal img").addClass("w-100")
	new bootstrap.Modal( $("#modal") ).show()
})
.on("change", "#sido", function(){
	animalSigungu(); //시군구 조회
	animalList( 1, $("#listSize option:selected").val() )

})
.on("change", "#sigungu", function(){
	animalShelter(); //보호소 조회
	animalList( 1, $("#listSize option:selected").val() )

})
.on("change", "#shelter", function(){
	animalList( 1, $("#listSize option:selected").val() )
	
})
.on("change", "#upkind", function(){
	animalKind(); //품종 조회
	animalList( 1, $("#listSize option:selected").val() )
	
})
.on("change", "#kind", function(){
	animalList( 1, $("#listSize option:selected").val() )
	
})





