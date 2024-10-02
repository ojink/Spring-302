package kr.co.smart.controller;

import java.util.HashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import kr.co.smart.common.LowerKeyMap;
import kr.co.smart.notify.NotifyMapper;
import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class NotifyController {
	private final NotifyMapper mapper;

	@MessageMapping("/notify") //클라이언트에서 /hello 로 publish할때 연결되는 메시지수신
	@SendTo("/topic/notify") //수신메시지를 보낵 구독자 지정
	public Object greeting(HashMap<String,Object> map) throws Exception {
		LowerKeyMap lower = mapper.countOfUncheckedComment(map);
		lower.put("userid", map.get("userid"));
		return lower;
	}

}