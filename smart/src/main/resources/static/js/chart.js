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
