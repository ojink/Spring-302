<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">사원정보분석</h3>
<ul class="nav nav-tabs" id="visual">
	<li class="nav-item"><a class="px-5 nav-link" >부서원수</a></li>
	<li class="nav-item"><a class="px-5 nav-link" >채용인원수</a></li>
	<li class="nav-item"><a class="px-5 nav-link" >기타</a></li>
</ul>

<div id="tab-content" class="py-3 h-px600">
	<canvas id="chart" class="h-100 m-auto"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script src="<c:url value='/js/chart.js'/>"></script>
<script>
$("#visual li").on("click", function(){
	$("#visual li a").removeClass("active fw-bold")
	$(this).children("a").addClass("active fw-bold")
	
	var idx = $(this).index()
	if( idx==0 )		department();
	else if( idx==1 )	hirement();
	else if( idx==2 )	{}
})

//부서원수 조회
function department(){
	//sampleChart()
	
	$.ajax({
		url: "department"
	}).done(function( response ){
		//console.log( response )
		var info = {};
		info.labels = [], info.data = []
		
		for( var item of response ){
			info.labels.push( item.DEPARTMENT_NAME ) //['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange']
			info.data.push( item.COUNT ) //[12, 19, 3, 5, 2, 3]
		}
		console.log( info )
		barChart( info ); //막대차트 그리기
	})
}

function barChart( info ){
	new Chart( $("#chart"), {
		type: 'bar',
		data: {
			labels: info.labels,
			datasets: [{
				label: '부서별 사원수',
				data: info.data,
				borderWidth: 0,
				barPercentage: 0.5,
			}]
		},
		options: {
		    scales: {
		        y: {
		          title: {
		            color: 'red',
		            display: true,
		            text: '부서별 사원수'
		          }
		        }
		      }			
			/*  
			plugins: {
				legend: {
					position: 'bottom',
				}
			}
			*/
		}
	} )
}

function sampleChart(){
	  new Chart( $("#chart"), {
	    type: 'bar',
	    data: {
	      labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
	      datasets: [{
	        label: '# of Votes',
	        data: [12, 19, 3, 5, 2, 3],
	        borderWidth: 1
	      }]
	    },
	    options: {
	      scales: {
	        y: {
	          beginAtZero: true
	        }
	      }
	    }
	  });	
}

//채용인원수 조회
function hirement(){
	
}

$(function(){
	$("#visual li").eq(0).trigger("click") //부서원수 선택
})

</script>

</body>
</html>