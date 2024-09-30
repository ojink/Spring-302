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

