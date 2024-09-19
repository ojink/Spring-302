package kr.co.smart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.smart.board.BoardMapper;
import kr.co.smart.board.BoardVO;
import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller @RequestMapping("/board")
public class BoardController {
	private final BoardMapper mapper;
	private final CommonUtility common;
	
	//방명록 수정화면 요청
	@RequestMapping("/modify")
	public String modify(int id, PageVO page, Model model, HttpServletRequest request) {
		//해당 정보를 DB에서 조죄해와 수정화면에 출력: Model객체에 담기
		BoardVO vo = mapper.getOneBoard(id);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		
		common.fileExist(vo.getFileList(), model, request);
		return "board/modify";
	}
	
	
	//방명록 첨부파일 다운로드 요청
	@RequestMapping("/download")
	public void download(int id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		FileVO vo = mapper.getOneFile(id);
		common.fileDownload(vo.getFilepath(), vo.getFilename(), request, response);
	}
	
	//방명록 정보화면 요청
	@RequestMapping("/info")
	public String info(int id, PageVO page, Model model, HttpServletRequest request) {
		mapper.updateReadCount(id);
		
		//선택한 방명록 글을 DB에서 조회해 정보화면에 출력할 수 있게 Model객체에 담기
		BoardVO vo = mapper.getOneBoard(id);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		model.addAttribute("crlf", "\r\n");
		
		//첨부파일의 물리적 존재여부
		common.fileExist(vo.getFileList(), model, request);
		
		return "board/info";
	}
	
	
	
	//방명록 등록 저장처리 요청
	@PostMapping("/register")
	public String register(BoardVO vo, MultipartFile[] files
							, Authentication auth
							, HttpServletRequest request) {
		//화면에서 입력한 정보로 DB에 신규저장 후 목록화면으로 연결
		vo.setWriter(auth.getName());
		vo.setFileList( common.fileUpload("board", files, request));
		
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
