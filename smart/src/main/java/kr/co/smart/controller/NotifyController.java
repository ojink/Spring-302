package kr.co.smart.controller;

import java.util.HashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.common.LowerKeyMap;
import kr.co.smart.notify.NotifyMapper;
import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class NotifyController {
	private final NotifyMapper mapper;

	@MessageMapping("/notify") //클라이언트에서 /hello 로 publish할때 연결되는 메시지수신
	@SendTo("/topic/notify") //수신메시지를 보낵 구독자 지정
	public Object greeting(HashMap<String,Object> map) throws Exception {
		LowerKeyMap lower = mapper.countOfUncheckedCommentOfUser(map);
		lower.put("userid", map.get("userid"));
		lower.put("board_id", map.get("board_id"));
		
		if( map.get("board_id") != null ) { //등록/삭제
			lower.put("notifycnt", mapper.countUncheckedCommentOfBoard(map));
		}
		return lower;
	}

	
	//미확인댓글 목록 조회 요청
	@RequestMapping("/board/comment/notify")
	public String commentNotify(Authentication auth, Model model) {
		//DB에서 전체 미확인 댓글목록을 조회해오기
		model.addAttribute("list", mapper.getListOfUncheckedComment(auth.getName()) );
		//읽음(확인)처리
		mapper.updateUncheckedComment( auth.getName() );
		return "board/comment/notify";
	}
	
	
}