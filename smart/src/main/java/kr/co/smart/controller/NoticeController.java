package kr.co.smart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.notice.NoticeMapper;
import kr.co.smart.notice.NoticeVO;
import lombok.RequiredArgsConstructor;

@Controller @RequestMapping("/notice") @RequiredArgsConstructor
public class NoticeController {
	private final NoticeMapper mapper;

	
	//공지글쓰기 화면 요청
	@RequestMapping("/register")
	public String register() {
		return "notice/register";
	}
	
	//공지글 목록 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "no");
		
		//DB에서 공지글목록을 조회해오기 -> 화면에 출력할 수 있도록 Model객체에 담기
		List<NoticeVO> list = mapper.getListOfNotice();
		model.addAttribute("list", list);
		
		return "notice/list";
	}
}
