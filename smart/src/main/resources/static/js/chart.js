/**
 * 시각화 차트 관련 
 */

Chart.defaults.font.size = 16;
Chart.defaults.plugins.legend.position = 'bottom'; //범례위치
Chart.defaults.layout.padding.top = 30;  //위쪽 여백
Chart.register(ChartDataLabels); 
Chart.defaults.set('plugins.datalabels', {
  	color: '#000',
	anchor: 'end',
	offset: -25,
	align: 'start',
});
const autocolors = window['chartjs-plugin-autocolors'];
Chart.register(autocolors);

const colors = [ "#06d63e", "#c91e04", "#f5ed02", "#0a0df0", "#a404b0", "#f53b02" ];

/*
 1~10: 0   9: *0.1=0.9 -> 1 
11~20: 1  20: *0.1=2.0 -> 2
21~30: 2  23: *0.1=2.3 -> 3
31~40: 3
41~50: 4  49: *0.1=4.9 -> 5
51~  : 5
 */

function makeLegend(max){
	//54 *0.1=5.4 -> 6
	max = Math.ceil(max*0.1)-1; 
	var tag = '';  
	for(var i=0; i<=max; i++){
		tag += `<li class="d-flex align-items-center gap-1" >
					<span></span>
					<label>${i*10+1}명~${(i+1)*10}명</label>
				</li>`
	}
	tag = `<ul class="d-flex justify-content-center my-2 mt-5 gap-3" id="legend">${tag}</ul>`
	$("#tab-content").after( tag )
	
	//각 범례에 맞는 색상 지정
	$("#legend span").each(function(idx){
		$(this).css("background-color", colors[idx])
	})
}


function top3Chart(info){
	
	info.type = info.unit == 'month' ? 'line' : 'bar'
	info.datasets = []
	for(var idx=0; idx<info.data.length; idx++ ){
		var department = {}
		department.label = info.name[idx];
		department.data = info.data[idx];
		department.backgroundColor = colors[idx]; //bar 인 경우
		department.borderColor =  colors[idx];  // line 인 경우
		info.datasets.push( department )
	}
	
	new Chart( $("#chart"), {
		type: info.type,
		data: {
			labels: info.labels,
			datasets: info.datasets,
		},
		options: setOptions(`${info.unit=="year"?"년도":"월"}별 채용인원수`)
	} )
	
}

function unitChart( info ){
	
	info.color = info.data.map(function(data){
		return colors[ Math.ceil(data*0.1)-1 ]
	})
	
	new Chart( $("#chart"), {
		type: 'bar',
		data: {
			labels: info.labels,
			datasets: [{
				data: info.data,
				barPercentage: 0.5,
				backgroundColor: info.color
			}]
		},
		
		options: setOptions( (info.unit=='year' ? '년도별' : '월별') + ' 채용인원수' )	
		
	} )
	
	makeLegend( Math.max(...info.data) )
}



function donutChart( info ){
	//전체사원수
	var sum = 0;
	for(var data of info.data){
		sum += data;
	}
	info.pct = info.data.map(function(data){
		return (data / sum * 100).toFixed(1)  //소수점 1자리 표시하기
	})
	console.log('pct:', info.pct)
	
	
	new Chart( $("#chart"), {
		type: 'doughnut',
		data: {
			labels: info.labels,
			datasets: [{
				label: '부서별 사원수',
				data: info.data,
				hoverOffset: 20,
			}] 
		},
		
		options: {
			cutout: '60%',
			plugins: {
				autocolors: { mode: 'data' },
				datalabels: {
					anchor: 'middle',
					formatter: function(value, item){
						//console.log('value: ', value, ' item: ', item)
						item = `${value}` < 5 ? '' : `${value}명\n(${info.pct[item.dataIndex]}%)`
						return `${item}`
					},
				}
			}
			
		}
	
	} )
	
}

function lineChart( info ){
	
	new Chart( $("#chart"), {
		type: 'line',
		data: {
			labels: info.labels,
			datasets: [{
				label: '부서별 사원수',
				data: info.data,
				borderColor: "#0000ff",
				pointBackgroundColor: "#ff0000",
				pointRadius: 5,
				tension: 0
			}]
		},
		options: setOptions('부서별 사원수')
	} )
	
}

function setOptions( title ){

	return {
	    scales: {
	        y: {
	          title: {
	            color: '#000',
	            display: true,
	            text:  title
	          }
	        }
	    },
			
		plugins: {
			legend: { display: false, },
		},
	}		
	
}

function barChart( info ){
	//배열로 새로운 배열을 만들어내는 함수: map
	info.color = info.data.map(function( data ){
		return colors[ Math.ceil(data*0.1)-1 ]
	})
	
	new Chart( $("#chart"), {
		type: 'bar',
		data: {
			labels: info.labels,
			datasets: [{
				label: '부서별 사원수',
				data: info.data,
				borderWidth: 0,
				barPercentage: 0.5,
				backgroundColor: info.color,
			}]
		},
		options: setOptions('부서별 사원수')		
	} )
	
	makeLegend( Math.max(...info.data) )
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
