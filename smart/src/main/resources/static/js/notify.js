/**
 * STOMP 관련
 */

const stompClient = new StompJs.Client({
    brokerURL: socketURL
});

$(function(){
	
	stompClient.activate();
	stompClient.onConnect = (frame) => {
	    console.log('Connected: ' + frame);
	    stompClient.subscribe('/topic/notify', (notify) => { //수신
			//console.log('subscribe notify> ', JSON.parse(notify.body) )
	        showNotify( JSON.parse(notify.body) );
	    });
		publishNotify(); //송신
	};

	//미확인 댓글수 알림 보여주기
	function showNotify( notify ){
		console.log( 'show notify> ',notify )
		
		if( notify.comments == 0 ){
			
		}else{
			
		}
	}
		
	stompClient.onWebSocketError = (error) => {
	    console.error('Error with websocket', error);
	};

	stompClient.onStompError = (frame) => {
	    console.error('Broker reported error: ' + frame.headers['message']);
	    console.error('Additional details: ' + frame.body);
	};

	//메시지 송신처리: 로그인유저의 미확인 댓글수를 조회하도록 사용자id를 보내기
	function publishNotify() {
	    stompClient.publish({
	        destination: context + "/app/notify",
	        body: JSON.stringify({ userid : authID })
	    });
	}
	
/*
	function sendName() {
		console.log("send")
	    stompClient.publish({
	        destination: "/smart/app/hello",
	        body: JSON.stringify({'name': $("#name").val()})
	    });
	}
	$("#name").on("keyup", function(e){
		if( e.keyCode==13 ) sendName()
	})
*/	

	
})