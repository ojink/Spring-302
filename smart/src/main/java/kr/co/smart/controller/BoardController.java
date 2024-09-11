package kr.co.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.board.BoardMapper;
import kr.co.smart.board.BoardVO;
import kr.co.smart.common.PageVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller @RequestMapping("/board")
public class BoardController {
	private final BoardMapper mapper;

	
	//방명록 등록 저장처리 요청
	@PostMapping("/register")
	public String register(BoardVO vo, Authentication auth) {
		//화면에서 입력한 정보로 DB에 신규저장 후 목록화면으로 연결
		vo.setWriter(auth.getName());
		mapper.registerBoard(vo);
		
		return "redirect:list";
	}
	
	
	//방명록 등록화면 요청
	@GetMapping("/register")
	public String register() {
		return "board/register";
	}
	
	
	//방명록 목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, PageVO page) {
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해와 화면에 출력할 수 있도록 Model객체에 담기
		page.setTotalList( mapper.getCountOfBoard(page) );
		page.setList( mapper.getListOfBoard(page) );
		model.addAttribute("page", page);
		return "board/list";
	}
	
}
