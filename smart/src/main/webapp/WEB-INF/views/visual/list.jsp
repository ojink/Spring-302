<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#legend span { width: 44px; height:17px }
</style>
<link rel="stylesheet" href="<c:url value='/css/yearpicker.css'/>">
<script type="text/javascript" src="<c:url value='/js/yearpicker.js'/>"></script>
</head>

<body>
<h3 class="my-4">사원정보분석</h3>
<ul class="nav nav-tabs" id="visual">
	<li class="nav-item"><a class="px-5 nav-link" >부서원수</a></li>
	<li class="nav-item"><a class="px-5 nav-link" >채용인원수</a></li>
	<li class="nav-item"><a class="px-5 nav-link" >기타</a></li>
</ul>

<div id="tab-content" class="py-3 h-px600">
	<div class="tab text-center">
		<div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" type="radio" name="chart" value="bar" checked>막대그래프
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label" >
		  	<input class="form-check-input" type="radio" name="chart" value="donut">도넛그래프
		  </label>
		</div>		
	</div>
	
	<div class="tab text-center">
		<div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" type="checkbox" id="top3">TOP3부서
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label">
		  	<input class="form-check-input" type="radio" name="unit" value="year" checked>년도별
		  </label>
		  <div class="d-inline-block year-range">
		  	<div class="d-flex align-items-center gap-2">
			  	<input type="text" class="form-control w-px80" id="begin" readonly>
			  	<span>~</span>
			  	<input type="text" class="form-control w-px80" id="end" readonly>		  
		  	</div>
		  </div>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label" >
		  	<input class="form-check-input" type="radio" name="unit" value="month">월별
		  </label>
		</div>		
	</div>

	<canvas id="chart" class="h-100 m-auto"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-autocolors"></script>
<script src="<c:url value='/js/chart.js'/>"></script>
<script>


$("#visual li").on("click", function(){
	$("#visual li a").removeClass("active fw-bold")
	$(this).children("a").addClass("active fw-bold")
	
	
	$("#tab-content .tab").addClass("d-none")
	
	var idx = $(this).index()
	$("#tab-content .tab").eq(idx).removeClass("d-none")
	
	if( idx==0 )		department();
	else if( idx==1 )	hirement();
	else if( idx==2 )	{}
})

function initChart(){
	console.log('차트초기화')
	$("#legend").remove()
	$("#chart").remove()
	$("#tab-content").append( `<canvas id="chart" class="h-100 m-auto"></canvas>` )
}

//부서원수 조회
function department(){
	//sampleChart()
	initChart()
	
	$.ajax({
		url: "department"
	}).done(function( response ){
		//console.log( response )
		var info = {};
		info.labels = [], info.data = []
		
		for( var item of response ){
			info.labels.push( item.department_name ) //['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange']
			info.data.push( item.count ) //[12, 19, 3, 5, 2, 3]
		}
		//console.log( info )
		if( $("[name=chart]:checked").val()=="bar" )
			barChart( info ); //막대차트 그리기
		else
			donutChart( info ) //도넛차트 
// 		lineChart( info ); //선차트 그리기
	})
}

$("[name=chart]").on("change", function(){
	department()
})

//채용인원수 조회
function hirement(){
	initChart()
	
	var unit = $("[name=unit]:checked").val()
	$.ajax({
		url: "hirement/" + unit,
		data: JSON.stringify( { begin: $("#begin").val(), end: $("#end").val() } ),
		type: 'post',
		contentType: 'application/json',
	}).done(function(response){
		console.log( response )
		var info = {}
		info.data = [], info.labels = [], info.unit = unit;
		for( var item of response ){
			info.data.push( item.count )
			info.labels.push( item.unit )
		}
		console.log('info> ', info )
		unitChart( info )
	})
}


//TOP3부서 체크여부에 따라
$("[name=unit], #top3").on("change", function(){
	if( $("[name=unit]:checked").val()=="year" )
		$(".year-range").removeClass("d-none")
	else
		$(".year-range").addClass("d-none")
		
	
	if( $("#top3").prop("checked") ) 	hirement_top3()
	else 								hirement()
})

//TOP3 부서에 대한 년도별/월별 채용인원수 조회
function hirement_top3(){
	initChart()
	
	var unit = $("[name=unit]:checked").val()
	
	$.ajax({
		url: "hirement/top3/" + unit,
		data: JSON.stringify( { begin: $("#begin").val(), end: $("#end").val() } ),
		type: 'post',
		contentType: 'application/json',
	}).done(function(response){
// 		console.log('list> ', response.list )
// 		console.log('labels> ', response.labels )
		
		var info = {}
		info.data = [], info.name = [], info.unit = unit; 
		info.labels = response.labels.map(function(yymm){
			return yymm +  (unit=='year'? '년':'월')
		})
		
		for(var d of response.list){
			info.name.push(d.department_name);
			var data = response.labels.map(function(unit){
				return d[unit]
			})
			info.data.push( data )
		}
		console.log('info> ', info)
		top3Chart(info)	
	})
	
}

$(document)
.on("click", ".yearpicker-items", function(){
	if( $("#begin").val() > $("#end").val() ) $("#begin").val( $("#end").val() )
	if( $("#top3").prop("checked") ) 	hirement_top3()
	else 								hirement()
})

$(function(){
	//기본년도범위 지정
	var thisYear = new Date().getFullYear();
	$("#end").yearpicker({
		endYear: thisYear,
		startYear: thisYear-100,
	})
	$("#begin").yearpicker({
		year: thisYear-9,
		endYear: thisYear,
		startYear: thisYear-100,
	})
	
	$("#visual li").eq(1).trigger("click") //부서원수 선택
})

</script>

</body>
</html>