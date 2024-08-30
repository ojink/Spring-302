package kr.co.smart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.PageVO;
import kr.co.smart.notice.NoticeMapper;
import kr.co.smart.notice.NoticeVO;
import lombok.RequiredArgsConstructor;

@Controller @RequestMapping("/notice") @RequiredArgsConstructor
public class NoticeController {
	private final NoticeMapper mapper;
	private final CommonUtility common;

	//공지글 저장처리 요청
	@PostMapping("/register")
	public String register(NoticeVO vo, MultipartFile file, HttpServletRequest request) {
		//첨부파일 있는 경우
		if( !file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload("notice", file, request) );
		}
		
		//화면에서 입력한 정보로 DB에 신규저장처리 -> 응답화면:목록
		mapper.registerNotice(vo);
		return "redirect:list";
	}
	
	//공지글쓰기 화면 요청
	@GetMapping("/register")
	public String register() {
		return "notice/register";
	}
	
	//공지글 목록 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, PageVO page) {
		session.setAttribute("category", "no");
		
		//DB에서 공지글목록을 조회해오기 -> 화면에 출력할 수 있도록 Model객체에 담기
		//List<NoticeVO> list = mapper.getListOfNotice();
		//model.addAttribute("list", list);
		page.setTotalList( mapper.countOfNotice() );
		page.setList( mapper.getListOfNotice(page) );
		model.addAttribute("page", page);
		
		return "notice/list";
	}
}
